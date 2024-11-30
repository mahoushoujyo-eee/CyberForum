
window.onload = function ()
{
    let find_password_form = document.getElementById("find_password_form");
    console.log(find_password_form);
    find_password_form.onsubmit = function (e)
    {
        e.preventDefault();
        let username = document.getElementById("username").value;
        let email = document.getElementById("email").value;
        let new_password = document.getElementById("new_password").value;

        if (ifPasswordValid(new_password) !== 'valid')
        {
            alert("密码不合法，请重新输入");
            return;
        }

        let data =
            {
                userName: username,
                email: email,
                password: new_password
            }
        let xhr = new XMLHttpRequest();
        xhr.open("POST", "/find_password");
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.send(JSON.stringify(data));
        xhr.onload = function ()
        {
            if (xhr.status === 200)
            {
                const response = JSON.parse(xhr.responseText);
                if (response.success === "true")
                {
                    alert("修改密码成功");
                    window.location.href = "/logIn.html";
                }
                else
                    alert("请检查填写的邮箱和用户名正确，同时保证密码不为空！");
            }
            else
                alert("系统故障");
        }
    }
}


function ifPasswordValid(password)
{
    for (let i = 0; i < password.length; i++)
        if (password[i] === ' ')
            return 'space';

    if (password.length < 8)
        return 'short';
    else if (password.length > 16)
        return 'long';
    else if ( /^[a-zA-Z0-9]+$/.test(password))
        return 'valid';
    else
        return 'invalid';

}