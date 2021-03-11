document.getElementById("registerForm").addEventListener('submit', (e) => {

    var username = document.getElementById("username").value;
    
    var body = JSON.stringify({
        username: username,
        email: document.getElementById("email").value,
        password: document.getElementById("password").value
    })

    var url = "http://localhost:8080/users/add";

    var result = Post(url, body);

    if (result == 200) {
        localStorage.setItem("isLogged", true);
        localStorage.setItem("username", username);
        window.location = "index.html";
    } else if (result == 400) {
        document.getElementById("result").textContent = "Username or email already exists!";
    } else {
        document.getElementById("result").textContent = "An error occurred during your registration!";
    }

    e.preventDefault();

});

function Post(url, body){
    var httpreq = new XMLHttpRequest(); 
    httpreq.open("POST",url,false);
    httpreq.setRequestHeader("Content-Type", "application/json;charset=utf-8")
    httpreq.send(body);
    return httpreq.status;          
}