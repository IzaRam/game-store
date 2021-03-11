document.getElementById("loginForm").addEventListener("submit", function(e){

    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;

    var body = JSON.stringify({
        username: username,
        email: "default",
        password: password
    });

    var url = "http://localhost:8080/users/verify";

    var response = Post(url, body);

    if(response == "false") {
        document.getElementById("result").textContent = "Invalid Username or Password";
    } else {
        localStorage.setItem("isLogged", true);
        localStorage.setItem("username", username);
        window.location = "index.html";
    }

    e.preventDefault();

});


function Post(url, body){
    var httpreq = new XMLHttpRequest(); 
    httpreq.open("POST",url,false);
    httpreq.setRequestHeader("Content-Type", "application/json;charset=utf-8")
    httpreq.send(body);
    return httpreq.responseText;          
}