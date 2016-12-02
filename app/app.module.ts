import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';

import { AppComponent }  from './app.component';
import { EvaluationComponent } from './evaluation/evaluation.component';

import { EvaluationService } from './evaluation/evaluation.service';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule
  ],
  declarations: [
    AppComponent,
    EvaluationComponent
  ],
  providers: [
    EvaluationService
  ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }