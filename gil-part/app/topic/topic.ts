import { Evaluation } from '../evaluation/evaluation';
export class Topic {
    public id: number;
    public name: string;

    constructor(id:number, name:string) {
        this.id = id;
        this.name = name;
    }

    //evaluations: Array<Evaluation>;
    /*
    constructor(
        public id: number,
        public name: string
    ) {
        //this.evaluations = new Array<Evaluation>();
    }
    */
}