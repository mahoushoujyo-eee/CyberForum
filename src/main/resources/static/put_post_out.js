window.onload = function()
{
    const params = new URLSearchParams(window.location.search);
    const forumId = params.get('forumId');

    if(getCookie("username") === null)
    {
        alert("please login first");
        return;
    }
    addToNavigator(getCookie("username"));

    const put_out_form = document.getElementById("put_out_form");
    put_out_form.addEventListener("submit", function(e)
    {
        e.preventDefault();
        let title = document.getElementById("edit_title").value;
        if(title.length > 30)
        {
            alert("title too long");
            return;
        }
        if(title.length === 0)
        {
            alert("title can not be empty");
            return;
        }

        let content = document.getElementById("edit_text").value;
        if(content.length > 500)
        {
            alert("content too long");
            return;
        }
        if(content.length === 0)
        {
            alert("content can not be empty");
            return;
        }
        let data =
        {
            title: title,
            content: content,
            forumId: forumId,
            userId: getCookie("userId")
        }
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "/put_blog");
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(JSON.stringify(data));
        xhr.onload = function()
        {
            if(xhr.status === 200)
            {
                alert("put out success");
                window.location.href = "/forum.html?forumId=" + forumId;
            }
            else
            {
                alert("error");
            }
        }
    })

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

function addToNavigator(element)
{
    let username = document.getElementById("username");
    username.innerHTML = element;
}