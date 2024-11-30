window.onload = function()
{
    const searchInput = document.getElementById("search_input");
    console.log(searchInput)

    const params = new URLSearchParams(window.location.search);
    const searchText = params.get('searchText');
    const forumId = params.get('forumId');
    let pageIndex = params.get('pageIndex');

    if (pageIndex === null)
        pageIndex = 1;

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
        xhr.open("GET", "/search_forum?searchText=" + searchText + "&pageIndex=" + pageIndex);
        xhr.send();
    }
    else if (searchType === 'blog')
    {
        xhr.open("GET", "/search_blog?searchText=" + searchText + "&pageIndex=" + pageIndex);
        xhr.send();
    }
    else if (searchType === 'blogOfForum')
    {
        xhr.open("GET", "/search_blog_of_forum?searchText=" + searchText + "&" + "forumId=" + forumId + "&pageIndex=" + pageIndex);
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
            const response = JSON.parse(xhr.responseText);
            const results = response.data.data;
            const pageSize = response.data.pageSize;

            document.getElementById("search_result_num").innerHTML = results.length;
            addTurnPageElement(response.data.pageCount, pageIndex, searchText, searchType, forumId);
            showSearchResult(searchType, results, pageIndex, pageSize);
        }
        else
        {
            alert("搜索失败");
        }
    }

    searchEventBind();
}

function showSearchResult(searchType, results, pageIndex, pageSize)
{
    const mainContentBox = document.getElementById("main_content_box");
    for (let i = pageSize * (pageIndex - 1); i < Math.min(results.length, pageSize * pageIndex); i++)
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
            <p>作者：${blog.username}</p>
        </div>
        <div class="blog_forumname_div">
        <p>论坛名：${blog.forumName}</p>
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

function addTurnPageElement(pageCount, pageIndex, searchText, searchType, forumId)
{
    const turnPageDiv = document.getElementById("turn_page_div");
    turnPageDiv.innerHTML=`<p>共${pageCount}页</p>`;
    if (pageCount <= 1)
    {
        return;
    }
    console.log("pageIndex:", pageIndex)
    for(let i = 1; i <= pageCount; i++)
    {
        const turnPageButton = document.createElement("button");
        turnPageButton.innerHTML = i+'';
        turnPageButton.onclick = function ()
        {
            window.location.href = "search_result.html?searchText=" + searchText + "&forumId=" + forumId + "&searchType=" + searchType + "&pageIndex=" + i;
        }
        if (i == pageIndex)
        {
            turnPageButton.classList.add("active");
        }

        turnPageDiv.appendChild(turnPageButton);
    }
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