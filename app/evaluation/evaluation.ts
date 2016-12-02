export class Evaluation {
    constructor(
        public id: number,
        public evaluation: number,
        public description: string

    ){}

    getId(): number {
        return this.id;
    } 

    getEvaluation(): number {
        return this.evaluation;
    }

    getDescription(): string {
        return this.description;
    }
}