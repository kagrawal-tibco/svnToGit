import { DecisionTableSave } from './decision-table-be-save';
export class DecisionTableSaveJson {
    implementsPath: string;
    isSyncMerge: string;
    decisionTableSave: DecisionTableSave;

    constructor(implementsPath: string, isSyncMerge: string, decisionTableSave: DecisionTableSave) {
        this.implementsPath = implementsPath;
        this.isSyncMerge = isSyncMerge;
        this.decisionTableSave = decisionTableSave;
    }

}
