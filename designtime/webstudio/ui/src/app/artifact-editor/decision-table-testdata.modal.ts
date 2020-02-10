import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext, Modal } from 'ngx-modialog/plugins/bootstrap';

import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';
import { BEDecisionTable } from '../editables/decision-table/be-decision-table';
import { ArtifactDetail, DecisionTableAnalyzer } from '../models-be/decision-table-be/decision-table-analyzer-be';

class ColumnForm {
  argument: string;
  testdata: string;
}

export class BEDecisionTableTestDataContext extends BSModalContext {
  constructor(
    public table: BEDecisionTable,
    public analyzer: DecisionTableAnalyzer
  ) {
    super();
  }

}

@Component({
  templateUrl: './decision-table-testdata.modal.html',
  styleUrls: ['decision-table-testdata.modal.css']
})
export class BEDecisionTableTestDataModal implements ModalComponent<BEDecisionTableTestDataContext>, OnInit, AfterViewInit {

  form: ColumnForm;
  table: BEDecisionTable;
  analyzer: DecisionTableAnalyzer;
  argumentsArray: Array<string> = new Array<string>();

  @ViewChild('columnNameInput', { static: false })
  private columnNameInput: ElementRef;

  constructor(
    private artifact: RestService,
    private log: Logger,
    private modal: Modal,
    public i18n: I18n,
    public dialog: DialogRef<BEDecisionTableTestDataContext>
  ) {
    this.table = dialog.context.table;
    this.analyzer = dialog.context.analyzer;

    for (const key in this.table.argumentObj) {
      const argument = this.table.argumentObj[key];
      this.argumentsArray.push(argument.path);
    }
  }

  ngOnInit() {
    this.form = new ColumnForm();
    this.form.argument = this.argumentsArray[0];
    this.onArgumentChange(this.form.argument);
  }

  ngAfterViewInit() {

  }

  onCancel() {
    this.dialog.dismiss();
  }

  onSubmit() {
    this.dialog.close({
      selectedTestData: this.form.testdata
    });
  }

  getEntryArray() {
    // console.log(this.analyzer);
    const artifactDetailArray: Array<ArtifactDetail> = new Array<ArtifactDetail>();
    if (this.analyzer) {
      for (const artifactDetail of this.analyzer.artifactRecord) {
        if (this.form.argument === artifactDetail.baseArtifactPath) {
          artifactDetailArray.push(artifactDetail);
        }
      }
    }

    return artifactDetailArray;
  }

  canSubmit(): boolean {
    const entryArray = this.getEntryArray();
    if (entryArray.length > 0) {
      return false;
    } else {
      return true;
    }
  }

  onArgumentChange(argument: string) {
    const artifactDetailArray: Array<ArtifactDetail> = new Array<ArtifactDetail>();
    if (this.analyzer) {
      for (const artifactDetail of this.analyzer.artifactRecord) {
        if (argument === artifactDetail.baseArtifactPath) {
          artifactDetailArray.push(artifactDetail);
        }
      }
    }
    if (artifactDetailArray.length > 0) {
      this.form.testdata = artifactDetailArray[0].artifactPath;
    }
  }

}
