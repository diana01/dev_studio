import { Component } from '@angular/core';

import { Evaluation } from './evaluation';
import { EvaluationService } from './evaluation.service';

@Component ({
    selector: 'my-evaluation',
    templateUrl: './app/evaluation/evaluation.component.html'

})

export class EvaluationComponent {
    //variables
    evaluations: Array<Evaluation>;
    idCount: number;
    model: Evaluation;

//    addEvaluation(evaluation:number, description:string) {
//        this.evaluations.push(new Evaluation(this.idCount, evaluation, description));
//        this.idCount += 1;
//    }

    constructor(private evaluationService: EvaluationService) { 
        this.idCount = 0;
        this.model = {id:this.idCount++, evaluation:null, description:''};
    }
	
	ngOnInit(): void{
		this.getEvaluations();
	}
	
	getEvaluations(): void {
		this.evaluationService.getEvaluations().then(evaluations => this.evaluations = evaluations);
	}

    onSubmit(): void{
        this.evaluations.push(this.model);
        this.model = new Evaluation(this.idCount++, false, '');
    }

}