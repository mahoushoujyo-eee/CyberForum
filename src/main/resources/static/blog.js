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
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/blog/" + blogId);
    xhr.send();
    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            if (xhr.responseText === '')
            {
                alert("帖子已被删除");
                location.href = "/forum.html?forumId=" + forumId;
            }
            const blog = JSON.parse(xhr.responseText);

            const blog_title = document.getElementById("blog_title");
            const page_title = document.getElementsByTagName("title")[0];
            page_title.innerHTML = blog.title;
            blog_title.innerHTML = blog.title;
            const blog_author = document.getElementById("blog_author");
            blog_author.innerHTML = blog.username;
            const blog_date = document.getElementById("blog_time");
            blog_date.innerHTML = getTimeString(blog.createTime);
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
                addCommentElements();
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

function addCommentElements()
{
    const params = new URLSearchParams(window.location.search);
    const blogId = params.get('blog_id');
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/comment/" + blogId);
    xhr.send();
    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            const comment_list = document.getElementById("comment_list");
            const comments = JSON.parse(xhr.responseText);
            for (let i = 0; i < comments.length; i++)
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
        xhr.open("POST", "/add_comment/" + blogId);
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
                if (xhr.responseText === 'true')
                    alert("评论成功");
                else
                    alert("评论失败");
                location.reload();
            } else
            {
                alert("error")
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
    xhr.open("POST", "/is_administrator/" + blogId);

    const data =
        {
            id: getCookie("userId")
        }

    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(data));



    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            const data = JSON.parse(xhr.responseText);
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
            if (xhr.responseText === "false")
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