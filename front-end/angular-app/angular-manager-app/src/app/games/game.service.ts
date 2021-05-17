import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";

import { Observable, Subject } from "rxjs";

import { Game } from "./game.model";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  gamesChanged = new Subject<void>();

  constructor(private httpClient: HttpClient) { }

  getGames(): Observable<Game[]> {
    return this.httpClient.get<Game[]>("http://localhost:8080/api/v1/games/all");
  }

  getGame(id: number): Observable<Game> {
    return this.httpClient.get<Game>("http://localhost:8080/api/v1/games/" + id);
  }

  addNewGame(game: Game) {
    this.httpClient.post("http://localhost:8080/api/v1/games/add", game)
        .subscribe(game => {
          console.log(game);
          this.gamesChanged.next();
        });
  }

  removeGame(game: Game) {
    this.httpClient.delete("http://localhost:8080/api/v1/games/del/" + game.id)
        .subscribe(game => {
          console.log(game);
          this.gamesChanged.next();
        })
  }

  updateGame(index: number, newGame: Game) {
    newGame.id = index;
    this.httpClient.put("http://localhost:8080/api/v1/games/edit/" + index, newGame)
          .subscribe(game => {
              console.log(game);
              this.gamesChanged.next();
          });
  }

}
