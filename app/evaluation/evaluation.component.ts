import { Component } from '@angular/core';
import { Evaluation } from './evaluation';

@Component ({
    //selector:
    //templateUrl:

})

export class EvaluationComponent {
    //variables
    evaluations: Array<Evaluation>;

    constructor() {
        this.evaluations = new Array<Evaluation>();
    } 


}