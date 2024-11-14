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
            <a href="/forum.html?forumId=${forum.id}" target="_blank">${forum.name}</a>
        </div>
    </div>
    <hr>
    `
    return html;
}