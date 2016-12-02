import { Component } from '@angular/core';
import { Evaluation } from './evaluation';

@Component ({
    selector: 'my-evaluation',
    templateUrl: './app/evaluation/evaluation.component.html'

})

export class EvaluationComponent {
    //variables
    evaluations: Array<Evaluation>;

    constructor() {
        this.evaluations = new Array<Evaluation>();
    } 


}