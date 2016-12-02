import { Component } from '@angular/core';
import { Evaluation } from './evaluation';

@Component ({
    selector: 'my-evaluation',
    templateUrl: './app/evaluation/evaluation.component.html'

})

export class EvaluationComponent {
    //variables
    evaluations: Array<Evaluation>;
    idCount: number;

    addEvaluation(evaluation:number, description:string) {
        this.evaluations.push(new Evaluation(this.idCount, evaluation, description));
        this.idCount += 1;
    }

    constructor() {
        this.evaluations = new Array<Evaluation>();
        this.idCount = 0;
    } 


}
