import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { Game } from '../game.model';

import { GameService } from '../game.service';

@Component({
  selector: 'app-game-edit',
  templateUrl: './game-edit.component.html',
  styleUrls: ['./game-edit.component.css']
})
export class GameEditComponent implements OnInit {

  recipeForm: FormGroup = new FormGroup({
    'name': new FormControl("", Validators.required),
    'year': new FormControl("", [Validators.required, Validators.pattern(/^[1-2]+[0-9]{3}$/)]),
    'description': new FormControl("", Validators.required),
    'image_url': new FormControl("", Validators.required)
  });

  id: number;

  game: Game;

  constructor(private gameService: GameService,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {

    this.route.params
      .subscribe(
        (params: Params) => {
          this.id = +params['id'];
          this.initForm();
        }
      );
  }

  initForm() {
    this.gameService.getGame(this.id).subscribe(game => {
      this.recipeForm = new FormGroup({
        'name': new FormControl(game.name, Validators.required),
        'year': new FormControl(game.year, [Validators.required, Validators.pattern(/^[1-2]+[0-9]{3}$/)]),
        'description': new FormControl(game.description, Validators.required),
        'image_url': new FormControl(game.image_url, Validators.required)
      });
    });



  }

  onSubmit() {
    this.gameService.updateGame(this.id, this.recipeForm.value);
    this.router.navigate(['games', this.id]);
  }

  onCancelEditGame() {
    this.router.navigate(['games', this.id]);
  }


}
