import { Evaluation } from './evaluation';

//evaluation - 0 is bad, 1 is good
export const EVALUATIONS: Evaluation[] = [
    {id:0, evaluation: false, description: "Very bad mock service."},
    {id:1, evaluation:true, description: "Very good mock service."},
    {id:2, evaluation:true, description: "Very good use of angular2."},
    {id:3, evaluation:false, description: "Your skills need improvement."},
    {id:4, evaluation:false, description: "You really don't understand anything about this."}
]