console.log(localStorage.getItem("id"));

// Complete form
var id = localStorage.getItem("id");
var json_obj = JSON.parse(Get("http://localhost:8080/api/v1/games/"+id));

document.getElementById("name").value = json_obj.name;
document.getElementById("year").value = json_obj.year;
document.getElementById("description").value = json_obj.description;
document.getElementById("image").value = json_obj.image_url;
document.getElementById("quantity").value = json_obj.quantity;
document.getElementById("price").value = json_obj.price;

function Get(url){
    var httpreq = new XMLHttpRequest(); 
    httpreq.open("GET",url,false);
    httpreq.send(null);
    return httpreq.responseText;          
}

// Send form data

urlP = "http://localhost:8080/api/v1/games/edit/"+id;

document.getElementById("myForm").addEventListener("submit", function(e){

    body = JSON.stringify({
        id: id,
        name: document.getElementById("name").value,
        year: document.getElementById("year").value,
        description: document.getElementById("description").value,
        image_url: document.getElementById("image").value,
        quantity: document.getElementById("quantity").value,
        price: document.getElementById("price").value
    });

        console.log(body);

    Put(urlP, body);
});


function Put(urlP, body){
    var httpreq = new XMLHttpRequest(); 
    httpreq.open("PUT",urlP,false);
    httpreq.setRequestHeader("Content-Type", "application/json;charset=utf-8")
    httpreq.send(body);
    return httpreq.responseText;          
}