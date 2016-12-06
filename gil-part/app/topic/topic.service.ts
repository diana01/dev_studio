import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

import { Topic } from './topic';


@Injectable() 
export class TopicService {

    private headers = new Headers({'Content-type': 'application/json'});
    private topicsUrl = 'api/topics';   //URL to web api
 
    constructor(private http: Http) {}

    getTopics(): Promise<Topic[]> {
        return this.http.get(this.topicsUrl)
                .toPromise()
                .then(response => response.json().data as Topic[])
                .catch(this.handleError);
    }

    create(name: string): Promise<Topic> {
    return this.http
      .post(this.topicsUrl, JSON.stringify({name: name}), {headers: this.headers})
      .toPromise()
      .then(res => res.json().data)
      .catch(this.handleError);
  }

    handleError(error: any): Promise<any> {
        console.error('An error occurred', error);
        return Promise.reject(error.message || error);
    }

    /*
    topics: Topic[] = [{ id: 0, name: 'Topic1' }];
    idCount: number = 0;
    model: Topic = new Topic(this.idCount, 'Topico1');
    


    addTopic(name:string): void {
        this.topics.push(new Topic(this.idCount, name));
        this.idCount++;
    }
    */
}