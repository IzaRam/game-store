document.getElementById("btn-check").addEventListener('click', () => {

    if (isLogged == "true") {

        var url = "http://localhost:8080/api/v1/users/name/"+username;
        var user = JSON.parse(Get(url));


        // Add Sale
        var sale = {
            user_id: user.user_id,
            gameList: user.gameListCart
        }
        var body = JSON.stringify(sale);
        var url = "http://localhost:8080/api/v1/orders/add";
        Post(url, body);


        // Clear User Cart
        url = "http://localhost:8080/api/v1/users/edit/del/" + user.user_id;
        body = JSON.stringify(user);
        Put(url, body);

        window.location = "./checkout.html";

    }


});


function Post(url, body){
    var httpreq = new XMLHttpRequest(); 
    httpreq.open("POST",url,false);
    httpreq.setRequestHeader("Content-Type", "application/json;charset=utf-8")
    httpreq.send(body);
    return httpreq.status;          
}