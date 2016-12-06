import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { Topic } from './topic';
import { TopicService } from './topic.service'; //remove after testing

@Component ({
    moduleId: module.id,
    selector: 'topics',
    templateUrl: '/app/topic/topic.component.html',
})

export class TopicComponent implements OnInit{
   
    topics: Topic[];
  
    constructor(
        private service: TopicService,
        private router: Router
    ){}

    getTopics(): void {
        this.service
            .getTopics()
            .then(topics => this.topics = topics);
    }

    ngOnInit(): void {
        this.getTopics();
    }

      /*
    constructor(private topicService: TopicService) {
        this.model = new Topic(0, '');
    }
    */

    /*
    getTopics(): void {
        this.topicService.getTopics().then(topics => this.topics = topics);
    }
    */
    
    /*
    addTopic(name:string): void {
        this.topicService.addTopic(name);
    }
    
    get diagnostic() {
        return JSON.stringify(this.model);
    }
    */

    

}
