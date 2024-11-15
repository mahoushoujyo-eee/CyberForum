let administratorCount = 0;
let isAdministrator = false;

window.onload = function()
{
    ifAdministrator()

    if (getCookie("userId") === null)
    {
        window.location.href = "/login.html";
        return;
    }
    else
    {
        const username = getCookie("username");
        addToNavigator(username);
    }



    const params = new URLSearchParams(window.location.search);
    const forumId = params.get('forumId');
    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/get_administrator/" + forumId);
    xhr.send();
    xhr.onload = function()
    {
        if (xhr.status === 200)
        {
            const data = JSON.parse(xhr.responseText);
            const administrator_name = document.getElementById("administrator_list");

            administratorCount = data.length;
            for (let i = 0; i < data.length; i++)
            {
                console.log(data[i]);
                const administrator_element = document.createElement("li");

                const label = document.createElement("label");
                label.innerHTML = data[i].userName;

                const hiddenSpan = document.createElement("span");
                hiddenSpan.innerHTML = data[i].id;
                hiddenSpan.style.display = "none";

                label.appendChild(hiddenSpan)

                const delete_administrator_button = document.createElement("input");
                delete_administrator_button.type = "button";
                delete_administrator_button.value = "删除";
                delete_administrator_button.id = "delete_administrator_button";
                delete_administrator_button.className = "delete_administrator_button";

                delete_administrator_button.addEventListener("click", deleteAdministrator);

                administrator_element.appendChild(label)
                administrator_element.appendChild(delete_administrator_button);

                administrator_name.appendChild(administrator_element);
            }
        }
        else
        {
            alert("获取管理员信息失败");
        }
    }
    initialize();
}

function initialize()
{
    BundleAddAdministratorButton();
}

function BundleAddAdministratorButton()
{
    const add_administrator_button = document.getElementById("add_administrator_button");
    add_administrator_button.addEventListener("click", function ()
    {
        const add_administrator_name = document.getElementById("administrator_name").value;

        if (administratorCount >= 3)
        {
            alert("最多只能添加三个管理员");
            return;
        }
        if (add_administrator_name.value === "")
        {
            alert("请输入管理员用户名");
            return;
        }

        const params = new URLSearchParams(window.location.search);
        const forumId = params.get('forumId');
        console.log(add_administrator_button.parentNode.children[0].children[0].value);

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/add_administrator/" + forumId);
        xhr.setRequestHeader("Content-Type", "application/json");
        const data =
            {
                userName: add_administrator_name,
                forumId: forumId
            }
        xhr.send(JSON.stringify(data));
        xhr.onload = function ()
        {
            if (xhr.status === 200)
            {
                if (xhr.responseText === 'true')
                {
                    alert("添加管理员成功");
                    location.reload();
                }
                else if (xhr.responseText === 'false')
                {
                    alert("管理员已存在了");
                }
                else if (xhr.responseText === 'null')
                {
                    alert("确认输入了正确的管理员用户名");
                }
            }
            else
            {
                alert("请确认输入了正确的管理员用户名");
            }
        }
    })
}

function deleteAdministrator()
{

    {
        const params = new URLSearchParams(window.location.search);
        const forumId = params.get('forumId');

        const userId = this.parentNode.children[0].children[0].innerHTML;
        const xhr = new XMLHttpRequest();
        xhr.open("DELETE", "/delete_administrator/" + forumId);
        xhr.setRequestHeader("Content-Type", "application/json");
        const data =
            {
                userId: userId,
                forumId: forumId
            }
        xhr.send(JSON.stringify(data));
        xhr.onload = function ()
        {
            if (xhr.status === 200)
            {
                alert("删除管理员成功");
                location.reload();
            }
            else
            {
                alert("删除管理员失败");
            }
        }
    }
}

function BundleDeleteAdministratorButton()
{
    const delete_administrator_buttons = document.getElementById("delete_administrator_button");

    console.log(delete_administrator_buttons);



    alert("length :" + delete_administrator_buttons.length)
    for (let i = 0; i < delete_administrator_buttons.length; i++)
    {
        alert("add :" + i)
        delete_administrator_buttons[i].addEventListener("click", function ()
        {
            const params = new URLSearchParams(window.location.search);
            const forumId = params.get('forumId');

            const administrator_name = delete_administrator_buttons[i].parentNode.children[0].children[0].value;
            const xhr = new XMLHttpRequest();
            xhr.open("POST", "/delete_administrator/" + forumId);
            xhr.setRequestHeader("Content-Type", "application/json");
            const data =
                {
                    userName: administrator_name
                }
                xhr.send(JSON.stringify(data));
            xhr.onload = function ()
            {
                if (xhr.status === 200)
                {
                    alert("删除管理员成功");
                    location.reload();
                }
                else
                {
                    alert("删除管理员失败");
                }
            }
        })
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
            if(xhr.responseText === "true")
            {

            }
            else
            {
                alert("您不是管理员，无法进行操作");
                window.location.href = '/';
            }
        }
        else
        {
            alert("error");
            return false;
        }
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

function addToNavigator(element)
{
    let username = document.getElementById("username");
    username.innerHTML = element;
}