import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-game-detail',
  templateUrl: './game-detail.component.html',
  styleUrls: ['./game-detail.component.css']
})
export class GameDetailComponent implements OnInit {

  url = "https://knoow.net/wp-content/uploads/2019/04/tetris-jogos-eletr%C3%B3nicos-mais-vendidos-de-sempre-lista-ranking-top-10-jogos-mais-vendidos.jpg";

  constructor() { }

  ngOnInit(): void {
  }

}
