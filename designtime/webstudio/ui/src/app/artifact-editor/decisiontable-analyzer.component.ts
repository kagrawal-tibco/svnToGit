
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { map } from 'rxjs/operators';

import { ArtifactProblemsService } from './artifact-problems.service';
import { ArtifactTestDataService } from './artifact-testdata.service';
import { BEDecisionTableTestDataContext, BEDecisionTableTestDataModal } from './decision-table-testdata.modal';

import { AlertService } from '../core/alert.service';
import { ModalService } from '../core/modal.service';
import { RestService } from '../core/rest.service';
import { BEDecisionTableEditorComponent } from '../editors/decision-table/be-decisiontable-editor.component';
import { ArtifactDetail, ArtifactItem, ColumnFilter, DecisionTableAnalyzer } from '../models-be/decision-table-be/decision-table-analyzer-be';
import { Artifact } from '../models/artifact';
import { ArtifactValidationRecord, ValidationProblemRecord } from '../models/dto';
@Component({
  selector: 'table-analyzer',
  templateUrl: './decision-table-analyzer.html',
  styleUrls: ['./decision-table-analyzer.css'],
})
export class DecisionTableAnalyzerComponent implements OnInit {
  @Input()
  input: Artifact;

  @Input()
  public editorComponent: BEDecisionTableEditorComponent;

  @Output()
  toggle = new EventEmitter<boolean>();

  objectAnalyzer: DecisionTableAnalyzer;
  valuesAnalyzer: ColumnFilter[];
  selectedValues: Array<string> = new Array<string>();

  constructor(public rest: RestService,
    private problemsService: ArtifactProblemsService,
    private testdataService: ArtifactTestDataService,
    private modal: ModalService,
    private alert: AlertService,
    public i18n: I18n) {
  }

  ngOnInit() {
    this.showAnalyzer();
  }

  showAnalyzer() {
    this.rest.get('decisiontable/analyzerValues.json?projectName=' + this.editorComponent.params.editBuffer.getBuffer().projectName + '&artifactPath=' + this.editorComponent.params.editBuffer.getBuffer().artifactPath + '&artifactExtension=rulefunctionimpl').pipe(
      map(res => {
        if (res.ok()) {
          // console.log(res.record[0]);
          this.processColumnFilter(res.record[0].artifactDetails);
        }
      }))
      .toPromise();
  }

  processColumnFilter(argObject: any) {

    const filterArray: Array<ColumnFilter> = new Array<ColumnFilter>();
    if (argObject.filter) {
      for (const cFilter of argObject.filter) {
        const svalueArray: Array<string> = new Array<string>();
        if (cFilter.isRangeFilter) {
          const filter = new ColumnFilter(cFilter.columnName, true, cFilter.minValue, cFilter.maxValue, undefined, cFilter.minValue, cFilter.maxValue, undefined);
          filterArray.push(filter);
        } else {
          const filter = new ColumnFilter(cFilter.columnName, false, undefined, undefined, cFilter.value, undefined, undefined, svalueArray);
          filterArray.push(filter);
        }
      }
    }

    const recordArray: Array<ArtifactDetail> = new Array<ArtifactDetail>();
    if (argObject.artifactRecord) {
      for (const aRecord of argObject.artifactRecord) {
        const artifactDetail = new ArtifactDetail(aRecord.artifactPath, aRecord.baseArtifactPath, aRecord.fileExtension, aRecord.artifactType);
        recordArray.push(artifactDetail);
      }
    }

    const dtAnalyzer = new DecisionTableAnalyzer(argObject.projectName, argObject.artifactPath, filterArray, recordArray);
    this.valuesAnalyzer = dtAnalyzer.filter;
    this.objectAnalyzer = dtAnalyzer;
  }

  updateList(entry: string, cFilter: ColumnFilter) {
    if (cFilter.isRangeFilter) {
    } else {
      if (cFilter.svalue.indexOf(entry) !== -1) {
        cFilter.svalue.splice(cFilter.svalue.indexOf(entry), 1);
      } else {
        cFilter.svalue.push(entry);
      }
      if (this.selectedValues.indexOf(entry) !== -1) {
        this.selectedValues.splice(this.selectedValues.indexOf(entry), 1);
      } else {
        this.selectedValues.push(entry);
      }
    }
  }

  showCoverage() {
    if (this.validateEnmptyRange()) {
      const url = 'decisiontable/coverage.json';
      const finalFilter: Array<ColumnFilter> = new Array<ColumnFilter>();
      for (const filter of this.objectAnalyzer.filter) {
        if (filter.isRangeFilter) {
          const saveFilter = new ColumnFilter(filter.columnName, filter.isRangeFilter, filter.sminValue, filter.smaxValue, undefined, undefined, undefined, undefined);
          finalFilter.push(saveFilter);
        } else {
          if (filter.svalue.length > 0) {
            const saveFilter = new ColumnFilter(filter.columnName, filter.isRangeFilter, undefined, undefined, filter.svalue, undefined, undefined, undefined);
            finalFilter.push(saveFilter);
          } else {
            const saveFilter = new ColumnFilter(filter.columnName, filter.isRangeFilter, undefined, undefined, filter.value, undefined, undefined, undefined);
            finalFilter.push(saveFilter);
          }
        }
      }
      const finalSaveObject = new DecisionTableAnalyzer(this.objectAnalyzer.projectName, this.objectAnalyzer.artifactPath, finalFilter, undefined);
      const artifactItem = new ArtifactItem(finalSaveObject);

      const payload = JSON.parse(JSON.stringify(artifactItem));

      this.rest.post(url, payload)
        .toPromise()
        .then(res => {
          if (res.ok()) {
            // console.log(res.record[0]);
            if (res.record[0].artifactDetails.record) {
              this.editorComponent.decorator.clearCoverageRules();
              for (const entry of res.record[0].artifactDetails.record) {
                this.editorComponent.decorator.updateCoverageRules(entry.coveredRuleId);
              }
            } else {
              this.editorComponent.decorator.clearCoverageRules();
              this.editorComponent.decorator.updateCoverageRules(new Array<string>());
            }
            this.editorComponent.updateAgGrid();
          } else {
            console.log('Failure');
          }
        });
    } else {
      this.alert.flash(this.i18n('Range filter values should not be empty'), 'error', true);
    }
  }

  testDataCoverage() {
    // console.log('testing test data coverage');

    this.modal
      .open(BEDecisionTableTestDataModal, new BEDecisionTableTestDataContext(this.editorComponent.editBuffer.getContent(), this.objectAnalyzer))
      .then((testDataInfo: any) => {
        this.testDataCoverageFinal(testDataInfo.selectedTestData);
      }, () => { });

  }

  testDataCoverageFinal(selectedTestData: string) {
    let testingArtifactDetail: ArtifactDetail;
    const testingArtifactDetailArray: Array<ArtifactDetail> = new Array<ArtifactDetail>();
    for (const artifactDetails of this.objectAnalyzer.artifactRecord) {
      if (artifactDetails.artifactPath === selectedTestData) {
        testingArtifactDetail = artifactDetails;
      }
    }

    const url = 'decisiontable/coverage.json';

    const payload = {
      artifactItem: {
        projectName: this.objectAnalyzer.projectName,
        artifactPath: this.objectAnalyzer.artifactPath,
        artifactRecord: [{
          artifactPath: testingArtifactDetail.artifactPath + '.' + testingArtifactDetail.fileExtension,
          baseArtifactPath: testingArtifactDetail.baseArtifactPath
        }]
      }
    };

    this.rest.post(url, payload)
      .toPromise()
      .then(res => {
        if (res.ok()) {
          // console.log(res.record[0]);
          this.testdataService.refresh(res.record[0].artifactDetails);
          this.toggle.emit(true);
        } else {
          console.log(this.i18n('Failure'));
        }
      });

  }

  analyzeTable() {
    // console.log('analyzing the decision table');

    this.rest.get('decisiontable/analyze.json?projectName=' + this.editorComponent.params.editBuffer.getBuffer().projectName + '&artifactPath=' + this.editorComponent.params.editBuffer.getBuffer().artifactPath + '&artifactExtension=rulefunctionimpl&currentPage=1').pipe(
      map(res => {
        if (res.ok()) {
          // console.log(res.record[0]);
          const testing = this.recordToArtifactValidationRecord(res.record[0]);

          this.input.analyzeResult = testing.analyzeResult.map(entry => JSON.parse(entry));

          this.problemsService.refresh(this.input);

          // console.log(this.input);

        }
      }))
      .toPromise();
  }

  clearHighlights() {
    for (const filter of this.valuesAnalyzer) {
      if (filter.isRangeFilter) {
        filter.sminValue = filter.minValue;
        filter.smaxValue = filter.maxValue;
      } else {
        filter.svalue = new Array<string>();
      }
    }
    this.editorComponent.decorator.clearCoverageRules();
    this.editorComponent.decorator.updateCoverageRules(new Array<string>());
    this.editorComponent.updateAgGrid();
    this.selectedValues = new Array<string>();
    this.showAnalyzer();
  }

  isEntrySelected(entry: string) {
    if (this.selectedValues.indexOf(entry) !== -1) {
      return true;
    } else {
      return false;
    }
  }

  validateEnmptyRange(): boolean {
    for (const filter of this.valuesAnalyzer) {
      if (filter.isRangeFilter) {
        if (filter.sminValue === '' || filter.smaxValue === '') {
          return false;
        }
      }
    }
    return true;
  }

  recordToArtifactValidationRecord(record: ValidationProblemRecord): ArtifactValidationRecord {
    const problems = [];

    if (record.artifactDetails && record.artifactDetails.problem && record.artifactDetails.problem.length > 0) {
      record.artifactDetails.problem.forEach(element => {
        const rowIndex: number = isNaN(element.location) ? 0 : element.location;
        // let colIndex: number = isNaN(element.columnName) ? 0 : element.columnName;
        const columnName: string = (element.columnUIName) ? element.columnUIName : '' + element.location;
        let location = ' Location - ';
        if (element.errorCode) {
          if (element.errorCode < 200) { location = location + '[When] '; } else if (element.errorCode > 200 && element.errorCode < 300) { location = location + '[Then] '; }
        }
        let err = true;
        if (element.severity === 1) {
          err = false;
        }
        location = location + columnName;
        const problemMsg: string = element.errorMessage + location + ', Type - ' + element.problemType;
        const result = {
          message: problemMsg,
          locations: [{ 0: rowIndex, 1: undefined }],
          error: err
        };
        problems.push(JSON.stringify(result));
      });
    }

    return {
      entityId: '',
      analyzeResult: problems
    };
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
