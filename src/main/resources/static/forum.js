let isAdministrator = false;

window.onload = function()
{
    searchButtonBundle()

    const params = new URLSearchParams(window.location.search);
    const forumId = params.get('forumId');

    const username = getCookie("username");
    const userId = getCookie("userId");
    if(username != null)
    {
        addToNavigator(username);
        ifAdministrator();
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
            let forum_title = document.getElementById("forum_title");
            let title = document.getElementsByTagName("title")[0];
            console.log(data);
            title.innerHTML = data.name;
            forum_title.innerHTML = data.name;
            if (data.ownerId.toString() === userId)
            {
                addOwnerChoice();
            }
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
    postChoice.href = "/put_post_out.html?forum_id=" + forumId;
    console.log("postChoice:")
    console.log(postChoice)
}

function addOwnerChoice()
{
    const params = new URLSearchParams(window.location.search);
    const forumId = params.get('forumId');

    const ownerChoice = document.getElementById("owner_choice_box");
    ownerChoice.innerHTML =
        `
            <div class="owner_fix_box">
                <p>添加管理员</p>
            </div>
        `;
    ownerChoice.href = "add_administrator.html?forumId=" + forumId;
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
                    `
                        <div class="blog_title" id="blog_title">
                        <h3>
                            <a href="blog.html?blog_id=${data[i].id}&forum_id=${data[i].forumId}" class="blog_a" target="_blank">
                                ${data[i].title}
                            </a>
                        </h3>
                        </div>
                        <div class="blog_content">
                            <p>${data[i].content}</p>
                        </div>
                        <div class="blog_author">
                            <p>${data[i].username}</p>             
                        </div>
                        <div>
                            <p>${getTimeString(data[i].createTime)}</p>
                        </div>
                        <hr>
                    `;
                document.getElementById("blog_content_div").appendChild(blogDiv);
                if (isAdministrator)
                {
                    addToTopElement(blogDiv.children[0].children[0], data[i])
                    if (data[i].top)
                    {
                        blogDiv.children[0].children[0].children[1].addEventListener("click",deleteToTop)
                    }
                    else
                    {
                        blogDiv.children[0].children[0].children[1].addEventListener("click",turnToTop)
                    }

                }
                else
                {
                    addTopData(blogDiv.children[0].children[0], data[i])
                }
            }
        }
    }
}

function ifAdministrator()
{
    const userId = getCookie("userId");
    const params = new URLSearchParams(window.location.search);
    const forumId = params.get('forumId');
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/is_administrator_by_forum_id/" + forumId);
    xhr.setRequestHeader("Content-Type", "application/json");
    const data =
        {
            userId: userId,
            forumId: forumId
        }
    xhr.send(JSON.stringify(data));
    xhr.onload = function()
    {
        if (xhr.status === 200)
        {
            if (xhr.responseText === "true")
            {
                isAdministrator = true;
            }
            else
            {
            }
        }
        else
        {
            alert("出现故障");
        }
    }
}



function addToTopElement(blog, blogData)
{

    const deleteToTopInfo = document.createElement("button");
    const turnToTopInfo = document.createElement("button");
    deleteToTopInfo.innerHTML = "取消置顶";
    turnToTopInfo.innerHTML = "置顶";


    if (blogData.top)
    {
        blog.innerHTML += deleteToTopInfo.outerHTML;
    }
    else
    {
        blog.innerHTML += turnToTopInfo.outerHTML;
    }

}

function addTopData(blog, blogData)
{
    if (blogData.top)
    {
        const button = document.createElement("button");
        button.innerHTML = "置顶";
        button.disabled = true;
        button.style.backgroundColor = "silver";
        blog.innerHTML += button.outerHTML;
    }
}

function turnToTop()
{
    const param = new URL(this.parentNode.children[0].href).searchParams;
    const blogId = param.get("blog_id");
    const xhr = new XMLHttpRequest();
    xhr.open("PUT", "/put_top/" + blogId);
    xhr.send();
    xhr.onload = function()
    {
        if (xhr.status === 200)
        {
            alert("置顶成功");
            location.reload();
        }
        else
        {
            alert("置顶失败");
        }
    }
}

function deleteToTop()
{
    const param = new URL(this.parentNode.children[0].href).searchParams;
    const blogId = param.get("blog_id");
    const xhr = new XMLHttpRequest();
    xhr.open("PUT", "/delete_top/" + blogId);
    xhr.send();
    xhr.onload = function()
    {
        if (xhr.status === 200)
        {
            alert("取消置顶成功");
            location.reload();
        }
        else
        {
            alert("取消置顶失败");
        }
    }
}

function searchButtonBundle()
{
    const searchButton = document.getElementById("search_button");
    searchButton.addEventListener("click", searchBlogs);
}

function searchBlogs()
{
    const searchInput = document.getElementById("search_input").value;
    const params = new URLSearchParams(window.location.search);
    const forumId = params.get('forumId');
    const searchText = searchInput.trim();
    location.href = "/search_result.html?searchText=" + searchText + "&searchType=blogOfForum&" + "forumId=" + forumId;
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