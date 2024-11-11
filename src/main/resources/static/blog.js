window.onload = function ()
{
    const username = getCookie('username');
    const userId = getCookie('userId');
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
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/blog/" + blogId);
    xhr.send();
    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            const blog = JSON.parse(xhr.responseText);
            console.log(blog);
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
        } else
        {
            alert("error")
        }

    }

    addEventListenerOfComment();
    addCommentElements();
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
                     </div>
                     <div class="comment_content">
                        <p>${comment.content}</p>
                     </div>
                     <hr>`;
                comment_list.appendChild(comment_element);
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
            alert("please login first");
            return;
        }
        const params = new URLSearchParams(window.location.search);
        const blogId = params.get('blog_id');

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/add_comment/" + blogId);
        xhr.setRequestHeader("Content-Type", "application/json");
        const commentContent = document.getElementById("comment_text").value;
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
                alert("comment successfully");
                location.reload();
            }
            else
            {
                alert("error")
            }

        }
    });
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
