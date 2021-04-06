import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

import { Game } from '../game.model';
import { GameService } from '../game.service';

@Component({
  selector: 'app-game-add',
  templateUrl: './game-add.component.html',
  styleUrls: ['./game-add.component.css']
})
export class GameAddComponent implements OnInit {

  constructor(private gameService: GameService,
              private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(form: NgForm) {
    const value = form.value;
    const game = new Game(1, value.name, value.year, value.description, value.image);
    this.gameService.addNewGame(game);
    this.router.navigate(['games']);
  }

  onClear(form: NgForm) {
    form.reset();
  }

}
