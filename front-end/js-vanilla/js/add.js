console.log(localStorage.getItem("id"));

        // Send form data

        urlP = "http://localhost:8080/api/add"

        

        // console.log(id);

        document.getElementById("myForm").addEventListener("submit", function(e){

            body = JSON.stringify({
                name: document.getElementById("name").value,
                year: document.getElementById("year").value,
                description: document.getElementById("description").value,
                image_url: document.getElementById("image").value
            });

            Post(urlP, body);
        });


        function Post(urlP, body){
            var httpreq = new XMLHttpRequest(); 
            httpreq.open("POST",urlP,false);
            httpreq.setRequestHeader("Content-Type", "application/json;charset=utf-8")
            httpreq.send(body);
            return httpreq.responseText;          
        }