
window.onload = function()
{
    turnToPageBind();

    let login_form = document.getElementById('login_form');

    login_form.addEventListener('submit', function(e)
    {
        e.preventDefault();
        let username = document.getElementById('username').value;
        let password = document.getElementById('password').value;

        let data =
            {
                userName: username,
                password: password
            }

        let xhr = new XMLHttpRequest();
        xhr.open('POST', '/logIn');
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(JSON.stringify(data));
        xhr.onload = function()
        {
            if(xhr.status === 200)
            {
                let response = JSON.parse(xhr.responseText);
                if (response.success === 'false')
                {
                    alert('用户名与密码不匹配');
                    return;
                }
                else
                {
                    if (response.data === 'false')
                    {
                        alert('用户名和密码不匹配');
                        return;
                    }
                    alert('登录成功');
                    window.location.href = '/';
                }
            }
            else
            {
                alert('出现故障');
            }
        }
    });
}

function turnToPageBind()
{
    let login_icon = document.getElementsByClassName('login_icon')[0];
    login_icon.addEventListener('click', function()
    {
        window.location.href = '/';
    });
}