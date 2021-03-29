import { Component, Input, OnInit } from '@angular/core';
import { Game } from '../game.model';
import { GameService } from '../game.service';

@Component({
  selector: 'app-game-detail',
  templateUrl: './game-detail.component.html',
  styleUrls: ['./game-detail.component.css']
})
export class GameDetailComponent implements OnInit {

  @Input() game: Game;

  constructor(private gameService: GameService) { }

  ngOnInit(): void {

  }

  onDeleteGame(game: Game) {
    this.gameService.removeGame(game);
    this.gameService.gameSelected.emit(undefined);
  }

}
