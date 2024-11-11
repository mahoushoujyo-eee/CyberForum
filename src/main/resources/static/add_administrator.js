window.onload = function()
{
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

            for (let i = 0; i < data.length; i++)
            {
                console.log(data[i]);
                const administrator_element = document.createElement("li");

                const label = document.createElement("label");
                label.innerHTML = data[i].userName;

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
        const params = new URLSearchParams(window.location.search);
        const forumId = params.get('forumId');
        const administrator_name = add_administrator_button.parentNode.children[0].children[0].value;
        console.log(add_administrator_button.parentNode.children[0].children[0].value);

        const xhr = new XMLHttpRequest();
        xhr.open("POST", "/add_administrator/" + forumId);
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
                alert("添加管理员成功");
                location.reload();
            }
            else
            {
                alert("添加管理员失败");
            }
        }
    })
}

function deleteAdministrator()
{

    {
        const params = new URLSearchParams(window.location.search);
        const forumId = params.get('forumId');

        const administrator_name = this.parentNode.children[0].innerHTML;
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