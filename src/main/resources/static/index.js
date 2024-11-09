window.onload = function ()
{
    const cookie = getCookie('username');
    if (cookie !== null)
    {
        alert(cookie + ', welcome back')
        addLogInElements(cookie)
    }
    else
    {
        addLogOutElements()
    }

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
            console.log(data);
            data = JSON.parse(data);
            console.log(data);
            console.log(data[0].name);
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



class forum
{
    forumName;
    forumId;
    masterId;

    constructor(forumName, forumId, masterId)
    {
        this.forumName = forumName;
        this.forumId = forumId;
        this.masterId = masterId;
    }

    getForumName()
    {
        return this.forumName;
    }

    getForumId()
    {
        return this.forumId;
    }

    getMasterId()
    {
        return this.masterId;
    }

    setForumName(forumName)
    {
        this.forumName = forumName;
    }

    setForumId(forumId)
    {
        this.forumId = forumId;
    }

    setMasterId(masterId)
    {
        this.masterId = masterId;
    }

    toString()
    {
        return `Forum Name: ${this.forumName}, Forum Id: ${this.forumId}, Master Id: ${this.masterId}`;
    }
}





