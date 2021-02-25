window.onload = function() {
    listAll();
}

function listAll() {

    var json_obj = JSON.parse(Get("http://localhost:8080/api/games"));
    console.log(json_obj);

    var gameCard = document.getElementById("game");

    var content = "";

    for (let elem of json_obj) {

        content += `<img src="${elem.image_url}">
        <p>${elem.name}</p>
        <p>${elem.year}</p>`

    }

    gameCard.innerHTML = content;

}


function Get(url){
    var httpreq = new XMLHttpRequest(); 
    httpreq.open("GET",url,false);
    httpreq.send(null);
    return httpreq.responseText;          
}