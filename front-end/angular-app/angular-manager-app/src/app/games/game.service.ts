import { Subject } from "rxjs";
import { Game } from "./game.model";

export class GameService {

  private games: Game[] = [
    new Game(1,
      "The Witcher 3: Wild Hunt - Blood and Wine",
      2016,
      "Geralt is in the southern province of Toussaint where a monstrous serial killer is targeting knights with a dark past. Geralt and his old vampire friend investigate the killer's motives.",
      "https://m.media-amazon.com/images/M/MV5BMTg2ZmY0MGUtZmFjZS00YjkxLTlmMWEtMDE0ZWQwYzBlODA2XkEyXkFqcGdeQXVyMzUwNzgzNzg@._V1_UY268_CR13,0,182,268_AL_.jpg"),
    new Game(2,
      "Red Dead Redemption II",
      2018,
      "Amidst the decline of the Wild West at the turn of the 19th century, outlaw Arthur Morgan and his gang struggle to cope with the loss of their way of life.",
      "https://m.media-amazon.com/images/M/MV5BMThiMGJkNDUtYjIxYy00ZTRhLWE5NmUtNzI4NTJlOGI4ZTMwXkEyXkFqcGdeQXVyNTk1ODMyNjA@._V1_UY268_CR17,0,182,268_AL_.jpg")
  ]

  getGames() {
    return this.games;
  }

  getGame(id: number) {
    return this.games[id];
  }

  addNewGame(game: Game) {
    this.games.push(game);
  }

  removeGame(game: Game) {
    const index = this.games.indexOf(game);
    this.games.splice(index, 1);
  }

  updateGame(index: number, newGame: Game) {
    this.games[index] = newGame;
  }

}
