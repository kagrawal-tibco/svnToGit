export class Action {
    varId: string;
    columnId: string;
    expression: string;
    isEnabled: string;
    ruleId: string;
    comment: string;

    constructor(
        varId: string,
        columnId: string,
        expression: string,
        isEnabled: string,
        ruleId: string,
        comment: string) {

        this.varId = varId;
        this.columnId = columnId;
        this.expression = expression;
        this.isEnabled = isEnabled;
        this.ruleId = ruleId;
        this.comment = comment;
    }

}

export class Condition {
    varId: string;
    columnId: string;
    expression: string;
    isEnabled: string;
    ruleId: string;
    comment: string;

    constructor(
        varId: string,
        columnId: string,
        expression: string,
        isEnabled: string,
        ruleId: string,
        comment: string) {

        this.varId = varId;
        this.columnId = columnId;
        this.expression = expression;
        this.isEnabled = isEnabled;
        this.ruleId = ruleId;
        this.comment = comment;
    }
}
