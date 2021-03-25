# API-GAME-STORE

API Rest usando Java com Spring e banco de dados MySQL.

Este é um exemplo de aplicação Java/Maven/Spring Boot
para a criação de um microservice para uma Game Store.

## Como Inicializar

* Faça um clone do projeto:

    ```
    $ git clone https://github.com/IzaRam/game-store.git 
    ```
  
* Talvez seja necessário configurar o arquivo application.properties
  de acordo com as suas configurações do MySQL.
  
    ```
    spring.jpa.hibernate.ddl-auto=update
    spring.datasource.url=jdbc:mysql://localhost:3306/*DATABASE_NAME*
    spring.datasource.username=*USERNAME*
    spring.datasource.password=*PASSWORD*
    ```

* Dentro do diretório do projeto, inicialize a aplicação
  utilizando o maven:
  
    ```
    $ ./mvnw spring-boot:run
    ```
  
* Após a aplicação inicializar, pode-se testar utilizando:

    ```
    $ curl -v localhost:8080/api/v1/games/all
    ```
    Que irá retornar algo semelhante à:

    ```
    *   Trying 127.0.0.1:8080...
    * TCP_NODELAY set
    * Connected to localhost (127.0.0.1) port 8080 (#0)
    > GET /api/v1/games/all HTTP/1.1
    > Host: localhost:8080
    > User-Agent: curl/7.68.0
    > Accept: */*
    >
      < HTTP/1.1 200
      < Content-Type: application/json
      < Transfer-Encoding: chunked
      < Date: Thu, 25 Mar 2021 15:20:32 GMT
        ...
    ```

## Sobre o Serviço

O serviço é apenas um REST Service para uma loja de games.
Ele usa um banco de dados MySQL e JPA com Hibernate para 
mapeamento objeto-relacional.

O que esta aplicação contem:
* Integração total com o Spring Framework mais recente: 
  inversão de controle, injeção de dependência, etc.
  
* Um serviço REST usando anotação.

* Mapeamento das Excpetions de acordo com as exceções da
  aplicação para a resposta HTTP correta com detalhes da 
  exceção no body da response.
  
* Integração do Spring Data com JPA/Hibernate com 
  apenas algumas linhas de configuração e anotações.
  
* Funcionalidade CRUD automática em relação à base de 
  dados usando o padrão Spring Repository.
  
* Demonstração da estrutura de teste MockMVC 
  com bibliotecas associadas.
  
* Total cobertura de testes em todas as camadas 
  (Repository, Service, Controller).
  
Foi implementado as ações GET, POST, PUT, DELETE para 
api games, users e orders.
  
Alguns end-points da aplicação:

### Obter informações sobre Games, Users, Orders

```
http://localhost:8080/api/v1/games/all
http://localhost:8080/api/v1/users/all
http://localhost:8080/api/v1/orders/all
```

### Criar um game

```
POST /api/v1/games/add
Accept: application/json
Content-Type: application/json

{
    "name": "The Witcher 3: Wild Hunt - Blood and Wine",
    "year": 2016,
    "description": "Geralt is in the southern province of Toussaint where a monstrous serial killer is targeting knights with a dark past. Geralt and his old vampire friend investigate the killer's motives.",
    "image_url": "https://m.media-amazon.com/images/M/MV5BMTg2ZmY0MGUtZmFjZS00YjkxLTlmMWEtMDE0ZWQwYzBlODA2XkEyXkFqcGdeQXVyMzUwNzgzNzg@._V1_UY268_CR13,0,182,268_AL_.jpg",
    "quantity": 3,
    "price": 49.99
}

RESPONSE: HTTP 201 (Created)
Location header: http://localhost:8090/example/v1/hotels/1
```

### Atualizar um Game

```
PUT /api/v1/games/1
Accept: application/json
Content-Type: application/json

{
    "name": "The Witcher 3: Wild Hunt - Blood and Wine",
    "year": 2016,
    "description": "Geralt is in the southern province of Toussaint where a monstrous serial killer is targeting knights with a dark past. Geralt and his old vampire friend investigate the killer's motives.",
    "image_url": "https://m.media-amazon.com/images/M/MV5BMTg2ZmY0MGUtZmFjZS00YjkxLTlmMWEtMDE0ZWQwYzBlODA2XkEyXkFqcGdeQXVyMzUwNzgzNzg@._V1_UY268_CR13,0,182,268_AL_.jpg",
    "quantity": 10,
    "price": 19.99
}

RESPONSE: HTTP 200 (OK)
```

### Excluir um Game

```
DELETE /api/v1/games/del/1
RESPONSE: HTTP 200 (OK)
```

## Autores:
* **[IzaRam](https://github.com/IzaRam)**