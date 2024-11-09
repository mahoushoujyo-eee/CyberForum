let register_form;


window.onload = function()
{
    turnToPageBind();


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
            alert('You must agree to the terms and conditions');
            return;
        }

        if(password !== confirm_password)
        {
            alert('Passwords do not match');
            return;
        }

        if (password === '')
        {
            alert('Password cannot be empty');
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
                alert('User registered successfully');
                window.location.href = '/logIn.html';
            }
            else
            {
                alert('Error registering user');
            }
        }
    })

    register_form.addEventListener('input', function()
    {
        console.log('register_form input');
    })
}

function turnToPageBind()
{
    let register_icon = document.getElementById('register_icon');
    register_icon.addEventListener('click', function()
    {
        window.location.href = '/';
    })
}


