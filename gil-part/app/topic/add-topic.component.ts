import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Topic } from './topic';
import { TopicService } from './topic.service';


@Component ({
    moduleId: module.id,
    selector: 'add-topic',
    templateUrl: 'add-topic.component.html'

})

export class AddTopicComponent {
    topics: Topic[];
    model: Topic;

    constructor(
        private service: TopicService,
        private router: Router
    ){
        this.model = new Topic(0, '');
    }

    add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.service.create(name)
      .then(topic => {
        this.topics.push(topic);
      });
  }

  //verifies if the form was submitted
  submitted = false;
  onSubmit() {this.submitted = true}

   get diagnostic() {
        return JSON.stringify(this.model);
    }

}