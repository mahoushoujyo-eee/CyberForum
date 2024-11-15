window.onload = function()
{
    const searchInput = document.getElementById("search_input");
    console.log(searchInput)

    const params = new URLSearchParams(window.location.search);
    const searchText = params.get('searchText');

    if (searchText === null || searchText === "")
        location.href = "/";

    const searchType = params.get('searchType');
    searchInput.value = searchText;
    searchInput.focus();

    const username = getCookie("username");

    if (username !== null)
    {
        document.getElementById("username").innerHTML = username;
    }
    else
    {
        document.getElementById("username").innerHTML = "游客";
    }

    const xhr = new XMLHttpRequest();

    if (searchType === 'forum')
    {
        xhr.open("GET", "/search/forum/" + searchText);
        xhr.send();
    }
    else if (searchType === 'blog')
    {
        xhr.open("GET", "/search_blog/" + searchText);
        xhr.send();
    }
    else if (searchType === 'blogOfForum')
    {
        const forumId = params.get('forumId');
        xhr.open("GET", "/search_blog_of_forum?searchText=" + searchText + "&" + "forumId=" + forumId);
        xhr.send();
    }
    else
    {
        alert("搜索类型错误")
    }



    xhr.onload = function ()
    {
        if (xhr.status === 200)
        {
            const results = JSON.parse(xhr.responseText);
            showSearchResult(searchType, results);
        }
        else
        {
            alert("搜索失败");
        }
    }

    searchEventBind();
}

function showSearchResult(searchType, results)
{
    const mainContentBox = document.getElementById("main_content_box");
    for (let i = 0; i < results.length; i++)
    {
        const result = results[i];

        const searchDataDiv = document.createElement("div");
        searchDataDiv.className = "search_data_div";
        if (searchType === 'forum')
            searchDataDiv.innerHTML = getForumHTML(result)
        else if (searchType === 'blog')
            searchDataDiv.innerHTML = getBlogHTML(result)
        else if (searchType === 'blogOfForum')
            searchDataDiv.innerHTML = getBlogHTML(result)

        mainContentBox.appendChild(searchDataDiv);
    }
}

function getBlogHTML(blog)
{
    const html = `
    <div class="blog_a">
        <div class="blog_title_div">
            <a href="/blog.html?blog_id=${blog.id}" target="_blank">
                <h3>${blog.title}</h3>
            </a>
        </div>
        <div class="blog_content_div">
            <p>${blog.content}</p>
        </div>
        <div class="blog_username_div">
            <p>${blog.username}</p>
        </div>
        <div class="blog_forumname_div">
        <p>${blog.forumName}</p>
        </div>
    </div>
    <hr>
    `
    return html;
}

function getForumHTML(forum)
{
    const html = `
    <div class="forum_a">
        <div class="forum_title_div">
            <a href="/forum.html?forumId=${forum.id}" target="_blank">
            <h3>${forum.name}</h3>
            </a>
        </div>
    </div>
    <hr>
    `
    return html;
}

function searchEventBind()
{
    const searchForumButton = document.getElementById("search_forum_button");
    searchForumButton.addEventListener("click", function ()
    {
        const searchText = document.getElementById("search_input").value;
        location.href = "/search_result.html?searchText=" + searchText + "&searchType=forum";
    });
    const searchBlogButton = document.getElementById("search_blog_button");
    searchBlogButton.addEventListener("click", function ()
    {
        const searchText = document.getElementById("search_input").value;
        location.href = "/search_result.html?searchText=" + searchText + "&searchType=blog";
    });
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