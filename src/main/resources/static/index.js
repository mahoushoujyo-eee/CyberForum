window.onload = function ()
{
    const username = getCookie('username');
    const userId = getCookie('userId');
    if (username !== null)
    {
        alert(username + userId + ', welcome back')
        addLogInElements(username)
    }
    else
    {
        addLogOutElements()
    }

    getLatestBlogs();
    initializeForum();


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


function addLogInElements(username)
{
    let top_navigator = document.getElementById('top_navigator');
    top_navigator.innerHTML =
        "<a>" + username + "</a>" +
        "<a href='/' id='logout_href'>退出</a>";


    let logout_href = document.getElementById('logout_href');
    logout_href.addEventListener('click', function (e)
    {
        e.preventDefault();
        deleteCookie('username');
        window.location.href = '/';
    })
}

function addLogOutElements()
{
    let top_navigator = document.getElementById('top_navigator');
    top_navigator.innerHTML =
        `
        <a href="logIn.html">登录</a>
        <a href="register.html">注册</a>
        `;
}

function initializeForum()
{
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/initialize_forum');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            let data = xhr.responseText;
            data = JSON.parse(data);
            for (let i = 0; i < data.length; i++)
            {
                let forum = data[i];
                let forum_div = document.getElementById('forums');
                let forum_label = document.createElement('a');
                let forum_label_p = document.createElement('p');
                forum_label_p.innerHTML = forum.name;
                forum_label.appendChild(forum_label_p);
                forum_label.href = 'forum.html?forumId=' + forum.id;
                console.log(forum_label);
                forum_div.appendChild(forum_label);
            }
        }
    }
}

function getLatestBlogs()
{
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/get_latest_blogs');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send();
    xhr.onload = function ()
    {
        let blogs = document.getElementById('blogs');
        if (xhr.status === 200)
        {
            let data = JSON.parse(xhr.responseText);
            console.log(data);
            for (let i = 0; i < data.length; i++)
            {
                let blog = document.createElement('div');
                blog.innerHTML =
                    `
                    <a href="blog.html?blog_id=${data[i].id}" class="blog_a">
                    <div class="blog_title">
                        <h3>${data[i].title}</h3>
                    </div>
                    <div class="blog_content">
                        <p>${data[i].content}</p>
                    </div>
                    <div class="blog_author">
                        <p>${data[i].userId}</p>
                    </div>
                    <div class="blog_forum">
                        <p>${data[i].forumId}</p>
                    </div>
                    <hr>
                    </a>
                    `;
                blogs.appendChild(blog)
            }
        }
        resizeDivHeight();
    }
}


function resizeDivHeight()
{
    const blogs_div = document.getElementById('blogs');
    const forums_div = document.getElementById('forums');
    const height = Math.max(blogs_div.offsetHeight, forums_div.offsetHeight);
    blogs_div.style.height = height + 'px';
    forums_div.style.height = height + 'px';
}






