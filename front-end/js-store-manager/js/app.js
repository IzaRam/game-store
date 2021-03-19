window.onload = function() {
    listAll();
}

function listAll() {

    var json_obj = JSON.parse(Get("http://localhost:8080/api/v1/games/all"));

    var gameCard = document.getElementById("game");

    var content = "";
    var obj = {};
    var cont = 1;
    for (let elem of json_obj) {

        content += ` <div class="container" id="game">
        <img src="${elem.image_url}" onError="this.onerror=null;this.src='https://upload.wikimedia.org/wikipedia/commons/0/0a/No-image-available.png';">
         <div class="info">
         <p>${elem.name}</p> 
         <!-- <p>${elem.year}</p> --> </div> 
         </div>`;

        if (cont % 7 == 0) {
            content += '<br>';
        }
        cont++;

        obj[elem.name] = elem.id;

    }

    gameCard.innerHTML = content;


    var imgClick = document.getElementById('game');
    imgClick.addEventListener('click', (e) => {
        e = e || window.event;
        var target = e.target || e.srcElement,
                    text = target.textContent || target.innerText;

        id = obj[target.textContent];
        if (id != undefined) {
            localStorage.setItem("id", id);

            document.getElementById("popup-1").classList.toggle("active");

            info();

            // window.location = './html/details.html';
        }
        
    })

    

    

}

function Get(url){
    var httpreq = new XMLHttpRequest(); 
    httpreq.open("GET",url,false);
    httpreq.send(null);
    return httpreq.responseText;          
}


function togglePopup() {
    document.getElementById("popup-1").classList.toggle("active");

    document.addEventListener('keydown', function(event){
        if(event.key === "Escape"){
            document.getElementById("popup-1").classList.toggle("active");
        }
    });
    
}