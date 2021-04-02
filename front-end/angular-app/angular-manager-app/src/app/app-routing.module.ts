import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameAddComponent } from './games/game-add/game-add.component';
import { GameDetailComponent } from './games/game-detail/game-detail.component';
import { GameEditComponent } from './games/game-edit/game-edit.component';
import { GameComponent } from './games/game.component';
import { NotFoundComponent } from './not-found/not-found.component';

const routes: Routes = [
  {path: '', redirectTo: 'games', pathMatch: 'full'},
  {path: 'games', component: GameComponent, children: [
    {path: ':id', component: GameDetailComponent},
    {path: ':id/edit', component: GameEditComponent}
  ]},
  {path: 'new', component: GameAddComponent},
  {path: 'not-found', component: NotFoundComponent},
  {path: '**', redirectTo: 'not-found'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
