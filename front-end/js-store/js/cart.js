var isLogged = localStorage.getItem("isLogged");
var username = localStorage.getItem("username");

if (isLogged == "true") {
    var url = "http://localhost:8080/users/name/"+username;
    var user = JSON.parse(Get(url));

    var id = [];
    var total = 0;
    var count = 0;
    var content = "";
    for(let obj of user.gameListCart) {
        content += `<div class="game-cart">
        <img src="${obj.image_url}" alt="${obj.id}" /> <br>
        <div class="game-cart-info">
            <p class="cart-name">${obj.name}</p>
            <p class="cart-year">${obj.year}</p>
            <p class="cart-desc">${obj.description}</p>
            <button class="btn-delete-item">Remove</button>
        </div>
        <div class="cart-price">
            <p>R$ ${obj.price}</p>
        </div>
        </div>
        <hr>`

        total += parseFloat(obj.price);
        count += 1;

        id.push(obj.id);
    }

    document.getElementById("total").textContent = "R$" + total.toFixed(2);
    document.getElementById("items").textContent = count;

    document.getElementById("my-games").innerHTML = content;

    var btnSelection = document.getElementsByClassName("btn-delete-item");

    for (let i=0; i<btnSelection.length; i++) {
        btnSelection[i].addEventListener('click', () => {

            game_id = id[i];
            var userDelete = JSON.parse(JSON.stringify(user));
            userDelete.gameListCart = [];

            for (let obj of user.gameListCart) {
                if (obj.id == game_id) {
                    userDelete.gameListCart.push(obj);
                }
            }


            url = "http://localhost:8080/users/edit/del/"+user.user_id;
            var body = JSON.stringify(userDelete);
            var response = Put(url, body);

            window.location.reload();
        });
    }

    // document.getElementsByClassName("my-games").addEventListener('click', (e) => {
    //     e = e || window.event;
    //     var target = e.target || e.srcElement;

    //     var game_id = target.alt;

    //     var userDelete = JSON.parse(JSON.stringify(user));
    //     userDelete.gameListCart = [];

    //     for (let obj of user.gameListCart) {
    //         if (obj.id == game_id) {
    //             userDelete.gameListCart.push(obj);
    //         }
    //     }


    //     url = "http://localhost:8080/users/edit/del/"+user.user_id;
    //     var body = JSON.stringify(userDelete);
    //     var response = Put(url, body);

    //     window.location.reload();

    // });


}