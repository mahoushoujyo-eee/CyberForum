let register_form;


window.onload = function()
{
    bindTurnToPage();


    register_form = document.getElementById('register_form');
    console.log('register.js loaded');
    register_form.addEventListener('submit', function(e)
    {
        console.log('register_form submitted');
        e.preventDefault();
        let username = document.getElementById('username').value;
        let password = document.getElementById('password').value;
        let confirm_password = document.getElementById('confirm_password').value;
        let email = document.getElementById('email').value;
        let agree = document.getElementById('agreement').checked;

        if(!agree)
        {
            alert('请同意用户协议');
            return;
        }

        if(password !== confirm_password)
        {
            alert('请确认两次密码相同');
            return;
        }

        const result = ifPasswordValid(password);

        switch (result)
        {
            case 'space':
                alert('密码中不能包含空格');
                return;
            case 'short':
                alert('密码长度不能小于8');
                return;
            case 'long':
                alert('密码长度不能大于16');
                return;
            case 'invalid':
                alert('密码中只能包含字母和数字');
                return;
        }

        if (!email.includes('@'))
        {
            alert('邮箱格式不正确');
            return;
        }

        let data =
            {
                userName: username,
                password: password,
                email: email
            }

        let xhr = new XMLHttpRequest();
        xhr.open('POST', '/register');
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(JSON.stringify(data));
        xhr.onload = function()
        {
            if(xhr.status === 200)
            {
                const response = JSON.parse(xhr.responseText);
                if (response.success === true)
                {
                    alert('注册成功！请前往登录');
                    window.location.href = '/logIn.html';
                }
                else
                {
                    if (response.message === 'username exists')
                        alert('用户名已存在');
                    else if (response.message === 'invalid email')
                        alert('邮箱格式不正确');
                    else
                        alert(response.message);
                }
            }
            else
            {
                alert('注册出现故障');
            }
        }
    })

    register_form.addEventListener('input', function()
    {
        console.log('register_form input');
    })
}

function bindTurnToPage()
{
    let register_icon = document.getElementById('register_icon');
    register_icon.addEventListener('click', function()
    {
        window.location.href = '/';
    })
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




