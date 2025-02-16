let isAuthor = false;


window.onload = function ()
{
    const username = getCookie('username');

    if (username !== null)
    {
        addToNavigator(username)
    }
    else
    {
        addToNavigator('游客')
    }

    const params = new URLSearchParams(window.location.search);
    const blogId = params.get('blog_id');
    const forumId = params.get('forum_id');
    let pageIndex = params.get('page_index');

    if (pageIndex === null || pageIndex === '')
        pageIndex = 1;

    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/blog/" + blogId);
    xhr.send();
    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            const response = JSON.parse(xhr.responseText);
            if (response.success === false && response.message === 'blog not found')
            {
                alert("帖子已被删除");
                location.href = "/forum.html?forumId=" + forumId;
            }
            const blog = response.data;

            const blog_title = document.getElementById("blog_title");
            const page_title = document.getElementsByTagName("title")[0];
            page_title.innerHTML = blog.title;
            blog_title.innerHTML = blog.title;
            const blog_author = document.getElementById("blog_author");
            blog_author.innerHTML = blog.username;
            const blog_date = document.getElementById("blog_time");
            blog_date.innerHTML = getTimeString(blog.creationTime);
            const blog_content = document.getElementById("blog_content");
            blog_content.innerHTML = blog.content;
            const userId = getCookie("userId");
            if (userId !== null)
            {
                if (userId === blog.userId.toString())
                {
                    isAuthor = true;
                }
                addDeleteBlogChoice(isAuthor)
                addCommentElements(pageIndex);
            }
        }
        else
        {
            alert("页面请求失败");
            location.href = "/";
        }
    }

    addEventListenerOfComment();
}

function addToNavigator(element)
{
    let username = document.getElementById("username");
    username.innerHTML = element;
}

function addCommentElements(pageIndex)
{
    const params = new URLSearchParams(window.location.search);
    const blogId = params.get('blog_id');
    const xhr = new XMLHttpRequest();
    let pageSize = 10;
    xhr.open("GET", "/comment/" + blogId + '/' + pageSize + '/' + pageIndex);
    xhr.send();
    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            const response = JSON.parse(xhr.responseText);
            if (response.success === 'false' && response.message === 'blog not found')
            {
                alert("帖子不存在");
                location.href = "/";
            }

            const comment_list = document.getElementById("comment_list");
            const comments = response.data.data;
            const pageSize = response.data.pageSize;
            console.log(response)

            addTurnPageElement(response.data.pageCount, params.get('forum_id'), blogId, pageIndex)

            console.log("pageSize:", pageSize)
            console.log("commentLength:", comments.length)
            for (let i = 0; i < Math.min(comments.length, pageSize); i++)
            {
                const comment = comments[i];
                const comment_element = document.createElement("div");
                comment_element.innerHTML =
                    `<div class="comment_author">
                        <p>${comment.username}</p>
                        <p id="comment_id" style="display:none">${comment.id}</p>
                     </div>
                     <div class="comment_content">
                        <p>${comment.content}</p>
                     </div>
                     `;
                if (isAuthorOfComment(comment.username))
                {
                    const delete_div = document.createElement("div");
                    delete_div.innerHTML = `<a href="#">删除评论</a>`;
                    comment_element.append(delete_div)
                    delete_div.addEventListener("click", deleteComment)
                }
                comment_element.append(document.createElement("hr"))
                comment_list.appendChild(comment_element);

                const topOption = getTopOptions(comment)

                if (topOption !== null)
                {
                    comment_element.children[0].children[0].append(topOption)
                }
            }
        }
    }
}

function addTurnPageElement(pageCount, forumId, blogId, pageIndex)
{
    const turnPageDiv = document.getElementById("turn_page_div");
    if (pageCount <= 1)
    {
        return;
    }
    turnPageDiv.innerHTML=`<p>共${pageCount}页</p>`;
    console.log("pageIndex:", pageIndex)
    for(let i = 1; i <= pageCount; i++)
    {
        const turnPageButton = document.createElement("button");
        turnPageButton.innerHTML = i+'';
        turnPageButton.onclick = function ()
        {
            window.location.href = "blog.html?blog_id=" + blogId + "&forum_id=" + forumId + "&page_index=" + i;
        }
        if (i == pageIndex)
        {
            turnPageButton.classList.add("active");
        }

        turnPageDiv.appendChild(turnPageButton);
    }
}

function addEventListenerOfComment()
{
    const commentButton = document.getElementById("comment_button");
    commentButton.addEventListener("click", function ()
    {
        if (getCookie("username") === null)
        {
            alert("请先登录");
            return;
        }

        const params = new URLSearchParams(window.location.search);
        const blogId = params.get('blog_id');

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/add_comment");
        xhr.setRequestHeader("Content-Type", "application/json");
        const commentContent = document.getElementById("comment_text").value;

        if (commentContent.length > 500)
        {
            alert("评论内容过长");
            return;
        }
        if (commentContent.length === 0)
        {
            alert("评论内容不能为空");
            return;
        }

        const data =
            {
                userId: getCookie("userId"),
                blogId: blogId,
                content: commentContent
            }
        xhr.send(JSON.stringify(data));
        xhr.onload = function ()
        {
            if (xhr.status === 200)
            {
                const response = JSON.parse(xhr.responseText);
                if (response.success === true)
                    alert("评论成功");
                else
                    alert("评论失败");
                location.reload();
            }
            else
            {
                alert("出现故障")
            }

        }
    });
}

function addDeleteBlogChoice(isAuthor)
{
    const params = new URLSearchParams(window.location.search);
    const blogId = params.get('blog_id');
    let isAdded = false;
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/is_administrator");

    const data =
        {
            id: getCookie("userId"),
            blogId: blogId
        }

    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(data));



    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            const response = JSON.parse(xhr.responseText);
            const data = response.data;
            if (data === true)
            {
                const delete_blog = document.getElementById("delete_blog");
                delete_blog.innerHTML =
                    `<div class="right_fix_box">
                        <p>删除帖子</p>
                     </div>`;

                delete_blog.addEventListener("click", deleteBlog)
            }
            isAdded = true;
        }
    }

    if (!isAdded)
    {
        if (isAuthor)
        {
            const delete_blog = document.getElementById("delete_blog");
            delete_blog.innerHTML =
                `<div class="right_fix_box">
                    <p>删除帖子</p>
                 </div>`;

            delete_blog.addEventListener("click", deleteBlog)
        }
    }
}

function getTopOptions(comment)
{
    if (!isAuthor)
        return null;

    const topOption = document.createElement("button");

    if (comment.top)
    {
         topOption.innerHTML = "取消置顶";
         topOption.addEventListener("click", cancelTop)
    }
    else
    {
         topOption.innerHTML = "置顶";
         topOption.addEventListener("click", putTop)
    }

    return topOption;
}

function deleteBlog()
{
    if (confirm("确定要删除帖子吗?") === false)
    {
        return;
    }
    const params = new URLSearchParams(window.location.search);
    const blogId = params.get('blog_id');
    const forumId = params.get('forum_id');
    const xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/delete_blog/" + blogId);
    xhr.send();

    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            const response = JSON.parse(xhr.responseText);
            if (response.success === "false")
            {
                alert("删除失败");
                return;
            }
            alert("删除成功");
            location.href = "/forum.html?forumId=" + forumId;
        }
        else
        {
            alert("出现故障");
        }
    }
}

function deleteComment()
{
    const commentId = this.parentNode.querySelector("#comment_id").innerText;
    const xhr = new XMLHttpRequest();
    xhr.open("DELETE", "/delete_comment/" + commentId);
    xhr.setRequestHeader("Content-Type", "application/json");

    xhr.send();
    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            location.reload();
        }
        else
        {
            alert("出现故障");
        }
    }
}

function putTop()
{
    const commentId = this.parentNode.parentNode.querySelector("#comment_id").innerText;
    const xhr = new XMLHttpRequest();
    xhr.open("PUT", "/put_comment_top/" + commentId);
    xhr.send();
    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            location.reload();
        }
        else
        {
            alert("置顶失败");
        }
    }
}

function cancelTop()
{
    const commentId = this.parentNode.parentNode.querySelector("#comment_id").innerText;
    const xhr = new XMLHttpRequest()
    xhr.open("PUT", "/cancel_comment_top/" + commentId);
    xhr.send();
    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            location.reload();
        }
        else
        {
            alert("取消置顶失败");
        }
    }
}

function isAuthorOfComment(comment_user_name)
{
    return (getCookie("username") === comment_user_name)
}

function getTimeString(date)
{
    date = new Date(date);
    const year = date.getFullYear();
    const month = date.getMonth() + 1;
    const day = date.getDate();
    const hour = date.getHours();
    const minute = date.getMinutes();
    const second = date.getSeconds();
    return `${year}-${month}-${day} ${hour}:${minute}:${second}`;
}


function getCookie(name)
{
    const cookies = document.cookie.split('; ');
    for (const cookie of cookies)
    {
        const [cookieName, cookieValue] = cookie.split('=');
        if (cookieName === name)
        {
            return decodeURIComponent(cookieValue);
        }
    }
    return null; // 如果找不到指定的Cookie，返回null
}

function deleteCookie(name)
{
    document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
}