import { Injectable } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import { Properties } from '../artifact-editor/properties';
import { BEDecisionTable } from '../editables/decision-table/be-decision-table';
import { EditBuffer } from '../editables/edit-buffer';
import { BEDecisionTableEditorComponent } from '../editors/decision-table/be-decisiontable-editor.component';
import { RuleTemplateInstanceBuilder } from '../editors/rule-template-instance-builder/rule-template-instance-builder.component';
import { RTIViewClient } from '../editors/rule-template-instance-view/RTIViewClient';

@Injectable()
export class ReviewPropertiesService {

  get getRuleId() {
    return this._ruleID;
  }

  get getRulePriority() {
    return this._rulePriority;
  }

  get getRuleComment() {
    return this._ruleComments;
  }

  get getImplPath() {
    return this._implementsG;
  }

  get getCellEnabled() {
    return this._cellEnabled;
  }

  get getCellComments() {
    return this._cellComments;
  }

  get getVersionG() {
    return this._versionG;
  }

  get getLastModifiedG() {
    return this._lastmodifiedG;
  }

  properties1: Observable<any>;

  // DT properties
  private _lastmodifiedG: string;
  private _versionG: string;
  private _implementsG: string;
  private _ruleID: string;
  private _rulePriority: string;
  private _ruleComments: string;
  private _cellEnabled: boolean;
  private _cellComments: string;
  private _exceptionTable: boolean;
  private beComponent: BEDecisionTableEditorComponent;
  private ruleInfo: any;

  // RTI properties
  private _rtiName: string;
  private _rtiPriority: number;
  private _rtiDescription: string;
  private _rtiBuilderComponent: RuleTemplateInstanceBuilder;
  private _rtiViewComponent: RTIViewClient;
  private propSubject1: Subject<any>;

  constructor() {
    this.propSubject1 = new Subject<any>();
    this.properties1 = this.propSubject1.asObservable();
  }

  public setTableProperties(ruleInfo: any, editbuff: EditBuffer<BEDecisionTable>, beComponent: BEDecisionTableEditorComponent, isET: boolean) {
    if (editbuff.getBase()) {
      this.ruleInfo = ruleInfo;
      this.beComponent = beComponent;
      this._exceptionTable = isET;

      this._lastmodifiedG = '';
      this._versionG = '';
      if (this.beComponent.editBuffer.getBuffer()) {
        this._implementsG = this.beComponent.editBuffer.getBuffer().implementsPath;
      } else if (this.beComponent.editBuffer.getBase()) {
        this._implementsG = this.beComponent.editBuffer.getBase().implementsPath;
      }
      this._ruleID = ruleInfo.data.getId();

      if (isET) {
        if (editbuff.getBase().rulesObjET[ruleInfo.data.getId()]) {
          this._rulePriority = editbuff.getBase().rulesObjET[ruleInfo.data.getId()].getCell('PRIORITY') ? editbuff.getBase().rulesObjET[ruleInfo.data.getId()].getCell('PRIORITY') : 5;
          this._ruleComments = editbuff.getBase().rulesObjET[ruleInfo.data.getId()].ruleComment;
          this._cellEnabled = !(editbuff.getBase().rulesObjET[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId) ? editbuff.getBase().rulesObjET[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId)._disabled : true);
          this._cellComments = editbuff.getBase().rulesObjET[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId) ? editbuff.getBase().rulesObjET[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId)._comment : '';
        } else {
          this._rulePriority = '5';
          this._ruleComments = '';
          this._cellEnabled = false;
          this._cellComments = '';
        }
      } else {
        if (editbuff.getBase().rulesObj[ruleInfo.data.getId()]) {
          this._rulePriority = editbuff.getBase().rulesObj[ruleInfo.data.getId()].getCell('PRIORITY') ? editbuff.getBase().rulesObj[ruleInfo.data.getId()].getCell('PRIORITY') : 5;
          this._ruleComments = editbuff.getBase().rulesObj[ruleInfo.data.getId()].ruleComment;
          this._cellEnabled = !(editbuff.getBase().rulesObj[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId) ? editbuff.getBase().rulesObj[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId)._disabled : true);
          this._cellComments = editbuff.getBase().rulesObj[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId) ? editbuff.getBase().rulesObj[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId)._comment : '';
        } else {
          this._rulePriority = '5';
          this._ruleComments = '';
          this._cellEnabled = false;
          this._cellComments = '';
        }
      }
      const props = new Properties();
      props.lastmodifiedG = this._lastmodifiedG;
      props.versionG = this._versionG;
      props.implementsG = this._implementsG;
      props.ruleID = this._ruleID;
      props.rulePriority = this._rulePriority;
      props.ruleComments = this._ruleComments;
      props.cellEnabled = this._cellEnabled;
      props.cellComments = this._cellComments;
      props.isDT = true;
      this.propSubject1.next(props);
    }
  }

  public setRTIBuilderProperties(rtiBuilderComponent: RuleTemplateInstanceBuilder) {
    this._rtiBuilderComponent = rtiBuilderComponent;
  }

  public setRTIViewProperties(rtiViewComponent: RTIViewClient) {
    this._rtiViewComponent = rtiViewComponent;
  }

  public updateRTIBuilderProperties(rtiName: string, newPriority: number, newDescription: string) {
    this._rtiName = rtiName;
    this._rtiPriority = newPriority;
    this._rtiDescription = newDescription;
    const newProps = new Properties();
    newProps.rtiName = this._rtiName;
    newProps.rtiPriority = this._rtiPriority;
    newProps.rtiDescription = this._rtiDescription;
    newProps.isRTI = true;

    const oldProps = new Properties();
    oldProps.rtiName = this._rtiName;
    oldProps.rtiPriority = this._rtiBuilderComponent.base.getRulepriortiy();
    oldProps.rtiDescription = this._rtiBuilderComponent.base.getDescription();

    this.propSubject1.next(newProps);
    const action = this._rtiBuilderComponent.editorService.modifyProperties(oldProps, newProps);
    action.execute(this._rtiBuilderComponent);
  }

  public updateRTIViewProperties(rtiName: string, newPriority: number, newDescription: string) {
    this._rtiName = rtiName;
    this._rtiPriority = newPriority;
    this._rtiDescription = newDescription;
    const newProps = new Properties();
    newProps.rtiName = this._rtiName;
    newProps.rtiPriority = this._rtiPriority;
    newProps.rtiDescription = this._rtiDescription;
    newProps.isRTI = true;

    const oldProps = new Properties();
    oldProps.rtiName = this._rtiName;
    oldProps.rtiPriority = this._rtiViewComponent.base.getRulepriortiy();
    oldProps.rtiDescription = this._rtiViewComponent.base.getDescription();

    this.propSubject1.next(newProps);
    const action = this._rtiViewComponent.editorService.modifyProperties(oldProps, newProps);
    action.execute(this._rtiViewComponent);
  }
}
