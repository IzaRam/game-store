create database game_store;
use game_store;

drop table games;

create table games (
	id integer primary key auto_increment,
    name varchar(255),
    year integer,
    description text,
    image_url text,
    quantity integer,
    price double
);

select * from games;
select * from user;
select * from orders;

insert into game(name, year, description, image_url) values('The Witcher 3: Wild Hunt - Blood and Wine', 2016, 'Geralt is in the southern province of Toussaint where a monstrous serial killer is targeting knights with a dark past. Geralt and his old vampire friend investigate the killer\'s motives.', 'https://m.media-amazon.com/images/M/MV5BMTg2ZmY0MGUtZmFjZS00YjkxLTlmMWEtMDE0ZWQwYzBlODA2XkEyXkFqcGdeQXVyMzUwNzgzNzg@._V1_UY268_CR13,0,182,268_AL_.jpg');
insert into game(name, year, description, image_url) values('Red Dead Redemption II', 2018, 'Amidst the decline of the Wild West at the turn of the 19th century, outlaw Arthur Morgan and his gang struggle to cope with the loss of their way of life.', 'https://m.media-amazon.com/images/M/MV5BMThiMGJkNDUtYjIxYy00ZTRhLWE5NmUtNzI4NTJlOGI4ZTMwXkEyXkFqcGdeQXVyNTk1ODMyNjA@._V1_UY268_CR17,0,182,268_AL_.jpg');
insert into game(name, year, description, image_url) values('The Last of Us', 2013, 'In a hostile, post-pandemic world, Joel and Ellie, brought together by desperate circumstances, must rely on each other to survive a brutal journey across what remains of the United States.', 'https://m.media-amazon.com/images/M/MV5BMTkzMzk3MzYzMV5BMl5BanBnXkFtZTgwOTQzMDM2MTE@._V1_UY268_CR6,0,182,268_AL_.jpg');


LOAD DATA INFILE '~/Documentos/Projetos/game-store/web-scraping/python-imdb/games.csv'
INTO TABLE games
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '/n'
IGNORE 1 ROWS;
