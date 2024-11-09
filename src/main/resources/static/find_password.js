
window.onload = function ()
{
    let find_password_form = document.getElementById("find_password_form");
    console.log("find_password.js loaded");
    console.log(find_password_form);
    find_password_form.onsubmit = function (e)
    {
        e.preventDefault();
        let username = document.getElementById("username").value;
        let email = document.getElementById("email").value;
        let new_password = document.getElementById("new_password").value;

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
                if (xhr.responseText === "true")
                {
                    alert("Password changed successfully");
                    window.location.href = "/logIn.html";
                }
                else
                    alert("Invalid username or email");
            }
            else
                alert("Invalid username or email");
        }
    }
}