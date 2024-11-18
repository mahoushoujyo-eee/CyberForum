window.onload = function()
{
    const params = new URLSearchParams(window.location.search);
    const forumId = params.get('forum_id');

    if(getCookie("username") === null)
    {
        alert("请先登录");

        location.href = "/login.html";
    }
    addToNavigator(getCookie("username"));

    const put_out_form = document.getElementById("put_out_form");
    put_out_form.addEventListener("submit", function(e)
    {
        e.preventDefault();
        let title = document.getElementById("edit_title").value;
        if(title.length > 30)
        {
            alert("请保持标题在三十字以内");
            return;
        }
        if(title.length === 0)
        {
            alert("标题不能为空");
            return;
        }

        let content = document.getElementById("edit_text").value;
        if(content.length > 500)
        {
            alert("请保持帖子信息在五百字以内");
            return;
        }
        if(content.length === 0)
        {
            alert("帖子内容不能为空");
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
                if (xhr.responseText === "false")
                {

                }
                alert("发帖成功");
                window.location.href = "/forum.html?forumId=" + forumId;
            }
            else
            {
                alert("系统故障");
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