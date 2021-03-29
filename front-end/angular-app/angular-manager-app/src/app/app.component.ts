import { Component, OnInit } from '@angular/core';
import { Game } from './games/game.model';
import { GameService } from './games/game.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [GameService]
})
export class AppComponent implements OnInit{

  optionSelected: string = 'list';
  gameDetail: Game;

  constructor(private gameService: GameService) {}

  ngOnInit(): void {

    this.gameService.gameSelected.subscribe((game) => {
      this.gameDetail = game;
    });

    this.gameService.gameAdded.subscribe((opt) => {
      this.optionSelected = opt;
    })

  }

  optMenuSelected(option: string) {
    this.optionSelected = option;
  }

}
