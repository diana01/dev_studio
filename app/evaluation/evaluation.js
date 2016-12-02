"use strict";
var Evaluation = (function () {
    function Evaluation(id, evaluation, description) {
        this.id = id;
        this.evaluation = evaluation;
        this.description = description;
    }
    Evaluation.prototype.getId = function () {
        return this.id;
    };
    Evaluation.prototype.getEvaluation = function () {
        return this.evaluation;
    };
    Evaluation.prototype.getDescription = function () {
        return this.description;
    };
    return Evaluation;
}());
exports.Evaluation = Evaluation;
//# sourceMappingURL=evaluation.js.map