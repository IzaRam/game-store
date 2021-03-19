
// HEADER MENU CONFIG
isLogged = localStorage.getItem("isLogged");
username = localStorage.getItem("username");

var login = document.getElementById("login");
var logout = document.getElementById("logout");
var cart = document.getElementById("cart");
var drop = document.getElementById("dropdown");

window.onload = () => {
    if (isLogged == "true") {
        logout.classList.toggle("logged-in");
        cart.classList.toggle("logged-in");
        drop.classList.toggle("drop");
    } else {
        logout.classList.toggle("logged-out");
        cart.classList.toggle("logged-out");
        drop.classList.toggle("log");
    }

    listAllGames();
}

if(isLogged == "true") {
    login.textContent = username;
    login.href = "#";
    logout.classList.toggle("logged-in");
    cart.classList.toggle("logged-in");
    drop.classList.toggle("drop");
}

logout.addEventListener("click", () => {
    localStorage.setItem("isLogged", false);
    localStorage.setItem("username", null);
    username = null;
    isLogged = false;
    login.textContent = "LOGIN";
    login.href = "login.html";
    logout.classList.toggle("logged-out");
    cart.classList.toggle("logged-out");
    drop.classList.toggle("log");
});



// LIST ALL GAMES
function listAllGames() {

    var url = "http://localhost:8080/api/v1/games/all";

    var response = JSON.parse(Get(url));

    var content = "";

    var cont = 1;

    for (let obj of response) {

        content += `<div class="game"><img src="${obj.image_url}" alt="${obj.id}" /><p>${obj.name}</p><p>R$ ${obj.price}</p></div>`;

        if (cont % 5 == 0) {
            content += "<br>"
        }

        cont++;

    }

    document.getElementById("content").innerHTML = content;

}



// ADD GAME TO CART
document.getElementById("content").addEventListener('click', (e) => {
    e = e || window.event;
    var target = e.target || e.srcElement; 

    var game_id = target.alt;

    addGameCart(game_id);

});



// Add Game in the Banner to Cart
document.getElementById("btn-buy").addEventListener('click', () => {

    var game_id = 2;

    addGameCart(game_id);

})



// Function Add Game to Cart
function addGameCart(game_id) {
    var game = JSON.parse(Get("http://localhost:8080/api/v1/games/"+game_id));

    if(isLogged == "true") {

        var user = JSON.parse(Get("http://localhost:8080/api/v1/users/name/"+username));

        user.gameListCart.push(game);

        url = "http://localhost:8080/api/v1/users/edit/"+user.user_id;
        body = JSON.stringify(user);
        var response = Put(url, body);
        console.log(response);


        var snackbar = document.getElementById("snackbar");
        snackbar.className = "show";
        setTimeout(function(){ 
            snackbar.className = snackbar.className.replace("show", ""); 
        }, 3000);

    } else {
        window.location = "login.html";
    }

}



// HTTP REQUESTS
function Get(url){
    var httpreq = new XMLHttpRequest(); 
    httpreq.open("GET",url,false);
    httpreq.send(null);
    return httpreq.responseText;          
}

function Put(url, body){
    var httpreq = new XMLHttpRequest(); 
    httpreq.open("PUT",url,false);
    httpreq.setRequestHeader("Content-Type", "application/json;charset=utf-8")
    httpreq.send(body);
    return httpreq.responseText;          
}