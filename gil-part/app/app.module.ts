import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { HttpModule }    from '@angular/http';
import { RouterModule }  from '@angular/router';

import { InMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService }  from './topic/topics-in-memory.service';


import { AppComponent }  from './app.component';
import { TopicComponent } from './topic/topic.component';
import { TopicService } from './topic/topic.service';
import { AddTopicComponent} from './topic/add-topic.component';

//import { EvaluationComponent } from './evaluation/evaluation.component';

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    InMemoryWebApiModule.forRoot(InMemoryDataService),
    
    RouterModule.forRoot([
        {
          path: 'topics',
          component: TopicComponent
        },
        {
          path: 'add-topic',
          component: AddTopicComponent

        }
    ])
  
  ],
  declarations: [
    AppComponent,
    TopicComponent,
    AddTopicComponent
  ],
  providers: [ TopicService ],
  bootstrap: [ AppComponent ]
})
export class AppModule { }