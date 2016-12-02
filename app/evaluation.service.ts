import { Injectable } from '@angular/core';

import { Evaluation } from './evaluation';
import { EVALUATIONS } from './mock-evaluations';

@Injectable()
export class EvaluationService {
	getEvaluations(): Promise<Evaluation[]> {
		return Promise.resolve(EVALUATIONS);
	}

}