import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgCookies} from '@'


import { AppComponent } from './app.component';
import {HttpClientModule} from "@angular/common/http";


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    ngCookies

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
