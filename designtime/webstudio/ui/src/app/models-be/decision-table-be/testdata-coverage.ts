export class TestDataCoverage {
    columnName: string[];
    record: TestDataRecord[];

    constructor(columnName: string[],
        record: TestDataRecord[]) {
        this.columnName = columnName;
        this.record = record;
    }
}

export class TestDataRecord {
    coveredRuleId: string[];
    value: string[];

    constructor(
        coveredRuleId: string[],
        value: string[]) {

        this.coveredRuleId = coveredRuleId;
        this.value = value;
    }
}
