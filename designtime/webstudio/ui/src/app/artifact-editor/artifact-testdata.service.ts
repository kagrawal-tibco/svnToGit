import { Injectable } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import { TestDataCoverage, TestDataRecord } from '../models-be/decision-table-be/testdata-coverage';

@Injectable()
export class ArtifactTestDataService {

    testDataCoverage: TestDataCoverage;
    testData: Observable<any>;
    private testDataSubject: Subject<any>;

    constructor() {
        this.testDataSubject = new Subject<any>();
        this.testData = this.testDataSubject.asObservable();
    }

    public refresh(testdataResponseObject: any) {
        const columnArray: Array<string> = new Array<string>();
        const recordArray: Array<TestDataRecord> = new Array<TestDataRecord>();
        // console.log('Testing test data service' + testdataResponseObject);

        for (const colunm of testdataResponseObject.columnName) {
            columnArray.push(colunm);
        }

        if (testdataResponseObject.record != null) {
            for (const entry of testdataResponseObject.record) {
                const valueArray: Array<string> = new Array<string>();
                const coveredArray: Array<string> = new Array<string>();

                for (const val of entry.value) {
                    valueArray.push(val);
                }

                if (entry.coveredRuleId) {
                    for (const covered of entry.coveredRuleId) {
                        coveredArray.push(covered);
                    }
                }

                const record = new TestDataRecord(coveredArray, valueArray);

                recordArray.push(record);
            }
        }

        this.testDataCoverage = new TestDataCoverage(columnArray, recordArray);
        this.testDataSubject.next(this.testDataCoverage);
    }

    getTestDataResult(): TestDataCoverage {
        return this.testDataCoverage;
    }

    testDataPresent() {
        if (this.testDataCoverage) {
            return true;
        } else {
            return false;
        }
    }

    getTestDataColumns() {
        return this.testDataCoverage.columnName;
    }

    getTestDataRecords() {
        return this.testDataCoverage.record;
    }

    getTestDataValues(record: TestDataRecord) {
        return record.value;
    }
}
