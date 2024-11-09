
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
                let data = xhr.responseText;
                if (data === 'true')
                {
                    window.location.href = '/';
                }
                else
                {
                    alert('Invalid username or password');
                }

            }
            else
            {
                alert('Invalid username or password');
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