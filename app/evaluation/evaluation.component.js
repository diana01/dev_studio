"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var evaluation_service_1 = require('./evaluation.service');
var EvaluationComponent = (function () {
    //    addEvaluation(evaluation:number, description:string) {
    //        this.evaluations.push(new Evaluation(this.idCount, evaluation, description));
    //        this.idCount += 1;
    //    }
    function EvaluationComponent(evaluationService) {
        this.evaluationService = evaluationService;
        this.idCount = 0;
    }
    EvaluationComponent.prototype.ngOnInit = function () {
        this.getEvaluations();
    };
    EvaluationComponent.prototype.getEvaluations = function () {
        var _this = this;
        this.evaluationService.getEvaluations().then(function (evaluations) { return _this.evaluations = evaluations; });
    };
    EvaluationComponent = __decorate([
        core_1.Component({
            selector: 'my-evaluation',
            templateUrl: './app/evaluation/evaluation.component.html'
        }), 
        __metadata('design:paramtypes', [evaluation_service_1.EvaluationService])
    ], EvaluationComponent);
    return EvaluationComponent;
}());
exports.EvaluationComponent = EvaluationComponent;
//# sourceMappingURL=evaluation.component.js.map