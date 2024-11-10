window.onload = function()
{
    const params = new URLSearchParams(window.location.search);
    const forumId = params.get('forumId');

    const cookie = getCookie("username");
    if(cookie != null)
    {
        addToNavigator(cookie);
        addPostChoice();
    }
    else
    {
        addToNavigator("游客");
    }

    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/forum/" + forumId);
    xhr.send();
    xhr.onload = function()
    {
        if(xhr.status === 200)
        {
            let data = JSON.parse(xhr.responseText);
            let title = document.getElementById("forum_title");
            console.log(data);
            title.innerHTML = data.name;
        }
    }
    logBlogOfForum();
}

function addToNavigator(element)
{
    let username = document.getElementById("username");
    username.innerHTML = element;
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

function addPostChoice()
{
    const postChoice = document.getElementById("post_choice");
    const params = new URLSearchParams(window.location.search);
    const forumId = params.get('forumId');
    postChoice.innerHTML =
        `
            <div class="right_fix_box">
                <p>我要发贴</p>
            </div>
        `;
    postChoice.href = "/put_post_out.html?forumId=" + forumId;
    console.log("postChoice:")
    console.log(postChoice)
}

function logBlogOfForum()
{
    const params = new URLSearchParams(window.location.search);
    const forumId = params.get('forumId');

    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/forum/" + forumId + "/blog");
    xhr.send();
    xhr.onload = function()
    {
        console.log("get response");
        if(xhr.status === 200)
        {
            let data = JSON.parse(xhr.responseText);
            console.log("blog data: ");
            console.log(data);
            for(let i = 0; i < data.length; i++)
            {
                const blogDiv = document.createElement("div");
                blogDiv.className = "blog_div";
                blogDiv.innerHTML =
                    `<a href="blog.html?blog_id=${data[i].id}" class="blog_a">
                        <div class="blog_title">
                            <h3>${data[i].title}</h3>
                        </div>
                        <div class="blog_content">
                            <p>${data[i].content}</p>
                        </div>
                        <div class="blog_author">
                            <p>${data[i].userId}</p>
                        </div>
                        <hr>
                     </a>
                    `;
                document.getElementById("blog_content_div").appendChild(blogDiv);
            }
        }
    }
}

