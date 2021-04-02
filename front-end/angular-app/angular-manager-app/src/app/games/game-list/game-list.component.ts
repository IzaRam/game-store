import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { Game } from '../game.model';
import { GameService } from '../game.service';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent implements OnInit {

  games: Game[];

  constructor(private gameService: GameService,
              private router: Router,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.games = this.gameService.getGames();
  }

  onGameClick(position: number) {
    this.router.navigate([position], {relativeTo: this.route});
  }

}
