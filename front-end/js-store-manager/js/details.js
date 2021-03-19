// window.onload = function() {
//     info();
// }

function info() {

    var id = localStorage.getItem("id");
    var json_obj = JSON.parse(Get("http://localhost:8080/api/v1/games/"+id));


    var gameCard = document.getElementById("game-info");
    var content = "";
    content += ` <h1>${json_obj.name}</h1>`;
    content += ` <p>${json_obj.year}</p>`;
    content += ` <img src="${json_obj.image_url}" >`;
    content += ` <p>${json_obj.description}</p>`;
    gameCard.innerHTML = content;


    var edit = document.getElementById("btn_edit");
    edit.addEventListener('click', () => {
        window.location = "./html/edit.html";
    })

    var del = document.getElementById("btn_del");
    del.addEventListener('click', () => {
        Delete("http://localhost:8080/api/v1/games/del/"+id);
        window.location = "./index.html";
    })

}

function Delete(url){
    var httpreq = new XMLHttpRequest(); 
    httpreq.open("DELETE",url,false);
    httpreq.send(null);
    return httpreq.responseText;          
}

function Get(url){
    var httpreq = new XMLHttpRequest(); 
    httpreq.open("GET",url,false);
    httpreq.send(null);
    return httpreq.responseText;          
}
