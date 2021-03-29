import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Game } from '../game.model';
import { GameService } from '../game.service';

@Component({
  selector: 'app-game-add',
  templateUrl: './game-add.component.html',
  styleUrls: ['./game-add.component.css']
})
export class GameAddComponent implements OnInit {

  @ViewChild('nameInput', {static: false}) name: ElementRef;
  @ViewChild('yearInput', {static: false}) year: ElementRef;
  @ViewChild('descriptionInput', {static: false}) description: ElementRef;
  @ViewChild('imageInput', {static: false}) imageUrl: ElementRef;

  constructor(private gameService: GameService) { }

  ngOnInit(): void {
  }

  addNewGame() {
    const game = new Game(1, this.name.nativeElement.value, this.year.nativeElement.value,
                    this.description.nativeElement.value, this.imageUrl.nativeElement.value);
    this.gameService.addNewGame(game);
    this.gameService.gameAdded.emit('list');
  }

}
