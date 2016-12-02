import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';

import { AppComponent }  from './app.component';

import { EvaluationComponent } from './evaluation/evaluation.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule
  ],
  declarations: [
    AppComponent,
    EvaluationComponent
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }