import { Action, Condition } from './action-condition-be';
import { Metadata } from './metadata-be';

export class DecisionTablePage {
    pageNumber: string;
    tableRule: TableRule[];

    constructor(pageNumber: string, tableRule: TableRule[]) {
        this.pageNumber = pageNumber;
        this.tableRule = tableRule;
    }
}

export class ExceptionTablePage {
    pageNumber: string;
    tableRule: TableRule[];

    constructor(pageNumber: string, tableRule: TableRule[]) {
        this.pageNumber = pageNumber;
        this.tableRule = tableRule;
    }
}

export class TableRule {
    action: Action[];
    condition: Condition[];
    metadata: Metadata;
    ruleId: string;

    constructor(action: Action[], condition: Condition[], metadata: Metadata, ruleId: string) {
        this.action = action;
        this.condition = condition;
        this.metadata = metadata;
        this.ruleId = ruleId;
    }
}
