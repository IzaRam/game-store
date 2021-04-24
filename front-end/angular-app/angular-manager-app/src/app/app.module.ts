import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { GameListComponent } from './games/game-list/game-list.component';
import { GameDetailComponent } from './games/game-detail/game-detail.component';
import { GameAddComponent } from './games/game-add/game-add.component';
import { DropdownDirective } from './shared/dropdown.directive';
import { GameComponent } from './games/game.component';
import { GameEditComponent } from './games/game-edit/game-edit.component';
import { NotFoundComponent } from './not-found/not-found.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    GameListComponent,
    GameDetailComponent,
    GameAddComponent,
    GameComponent,
    DropdownDirective,
    GameEditComponent,
    NotFoundComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
