import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';

import { Game } from '../game.model';
import { GameService } from '../game.service';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent implements OnInit, OnDestroy {

  games: Game[];

  subscription: Subscription;

  constructor(private gameService: GameService,
              private router: Router,
              private route: ActivatedRoute) {}

  ngOnInit(): void {

    this.gameService.getGames().subscribe(games => {
      this.games = games;
    });

    this.subscription = this.gameService.gamesChanged.subscribe(() => {
      this.gameService.getGames().subscribe(games => {
        this.games = [];
        this.games = games;
      });
    });

  }

  onGameClick(position: number) {
    window.scroll(0, 0);
    const index = this.games[position].id;
    this.router.navigate([index], {relativeTo: this.route});
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
