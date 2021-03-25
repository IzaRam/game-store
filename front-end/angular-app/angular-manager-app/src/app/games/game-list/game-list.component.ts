import { Component, OnInit } from '@angular/core';
import { Game } from '../game.model';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent implements OnInit {

  games: Game[] = [
    new Game(1, "Tetris", 2000, "A cool game", "https://knoow.net/wp-content/uploads/2019/04/tetris-jogos-eletr%C3%B3nicos-mais-vendidos-de-sempre-lista-ranking-top-10-jogos-mais-vendidos.jpg"),
    new Game(1, "Tetris", 2000, "A cool game", "https://knoow.net/wp-content/uploads/2019/04/tetris-jogos-eletr%C3%B3nicos-mais-vendidos-de-sempre-lista-ranking-top-10-jogos-mais-vendidos.jpg")
  ]

  constructor() { }

  ngOnInit(): void {
  }

}
