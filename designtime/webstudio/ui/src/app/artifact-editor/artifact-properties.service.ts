import { Injectable } from '@angular/core';

import { Observable, Subject } from 'rxjs';

import { Properties } from './properties';

import { BEDecisionTable } from '../editables/decision-table/be-decision-table';
import { EditBuffer } from '../editables/edit-buffer';
import { BEDecisionTableEditorComponent } from '../editors/decision-table/be-decisiontable-editor.component';
import { EditorComponent } from '../editors/editor.component';
import { RuleTemplateInstanceBuilder } from '../editors/rule-template-instance-builder/rule-template-instance-builder.component';
import { RTIViewClient } from '../editors/rule-template-instance-view/RTIViewClient';

@Injectable()
export class ArtifactPropertiesService {

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

  static artifactComponentMap: Map<string, BEDecisionTableEditorComponent | RuleTemplateInstanceBuilder | RTIViewClient> = new Map<string, BEDecisionTableEditorComponent | RuleTemplateInstanceBuilder | RTIViewClient>();

  properties: Observable<any>;

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
  private propSubject: Subject<any>;
  constructor() {
    this.propSubject = new Subject<any>();
    this.properties = this.propSubject.asObservable();
  }

  public setTableProperties(ruleInfo: any, editbuff: EditBuffer<BEDecisionTable>, beComponent: BEDecisionTableEditorComponent, isET: boolean) {
    if (editbuff.getBuffer()) {
      this.ruleInfo = ruleInfo;
      this.beComponent = beComponent;
      this._exceptionTable = isET;

      this._lastmodifiedG = '';
      this._versionG = '';
      if (this.beComponent.editBuffer.getBuffer()) {
        this._implementsG = this.beComponent.editBuffer.getBuffer().implementsPath;
      }
      this._ruleID = ruleInfo.data.getId();

      if (isET) {
        if (editbuff.getBuffer().rulesObjET[ruleInfo.data.getId()]) {
          this._rulePriority = editbuff.getBuffer().rulesObjET[ruleInfo.data.getId()].getCell('PRIORITY') ? editbuff.getBuffer().rulesObjET[ruleInfo.data.getId()].getCell('PRIORITY') : 5;
          this._ruleComments = editbuff.getBuffer().rulesObjET[ruleInfo.data.getId()].ruleComment;
          this._cellEnabled = !(editbuff.getBuffer().rulesObjET[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId) ? editbuff.getBuffer().rulesObjET[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId)._disabled : true);
          this._cellComments = editbuff.getBuffer().rulesObjET[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId) ? editbuff.getBuffer().rulesObjET[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId)._comment : '';
        } else {
          this._rulePriority = '5';
          this._ruleComments = '';
          this._cellEnabled = false;
          this._cellComments = '';
        }
      } else {
        if (editbuff.getBuffer().rulesObj[ruleInfo.data.getId()]) {
          this._rulePriority = editbuff.getBuffer().rulesObj[ruleInfo.data.getId()].getCell('PRIORITY') ? editbuff.getBuffer().rulesObj[ruleInfo.data.getId()].getCell('PRIORITY') : 5;
          this._ruleComments = editbuff.getBuffer().rulesObj[ruleInfo.data.getId()].ruleComment;
          this._cellEnabled = !(editbuff.getBuffer().rulesObj[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId) ? editbuff.getBuffer().rulesObj[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId)._disabled : true);
          this._cellComments = editbuff.getBuffer().rulesObj[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId) ? editbuff.getBuffer().rulesObj[ruleInfo.data.getId()].getCell(ruleInfo.colDef.colId)._comment : '';
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
      this.propSubject.next(props);

      const artifactPath = beComponent.params.editBuffer.getBuffer().artifactPath;
      ArtifactPropertiesService.artifactComponentMap.set(artifactPath, beComponent);
    }
  }

  public setRTIBuilderProperties(rtiBuilderComponent: RuleTemplateInstanceBuilder) {
    this._rtiBuilderComponent = rtiBuilderComponent;
    const artifactPath = rtiBuilderComponent.params.artifact.path;
    ArtifactPropertiesService.artifactComponentMap.set(artifactPath, rtiBuilderComponent);
  }

  public setRTIViewProperties(rtiViewComponent: RTIViewClient) {
    this._rtiViewComponent = rtiViewComponent;
    const artifactPath = rtiViewComponent.params.artifact.path;
    ArtifactPropertiesService.artifactComponentMap.set(artifactPath, rtiViewComponent);
  }

  public updateCellComment(newComment: string, artifactPath: string) {
    if (this.ruleInfo && this.ruleInfo.data.getCell(this.ruleInfo.colDef.colId)) {
      this.beComponent = <BEDecisionTableEditorComponent>ArtifactPropertiesService.artifactComponentMap.get(artifactPath);
      if (this._exceptionTable) {
        const action = this.beComponent.service.cellCommentEditActionET(this.ruleInfo.data.getId(), this.ruleInfo.colDef.colId, this.ruleInfo.value, !this._cellEnabled, this.ruleInfo.data.getCell(this.ruleInfo.colDef.colId)._comment ? this.ruleInfo.data.getCell(this.ruleInfo.colDef.colId)._comment : '', newComment);
        this.beComponent.execute(action);
      } else {
        const action = this.beComponent.service.cellCommentEditAction(this.ruleInfo.data.getId(), this.ruleInfo.colDef.colId, this.ruleInfo.value, !this._cellEnabled, this.ruleInfo.data.getCell(this.ruleInfo.colDef.colId)._comment ? this.ruleInfo.data.getCell(this.ruleInfo.colDef.colId)._comment : '', newComment);
        this.beComponent.execute(action);
      }
      this._cellComments = newComment;
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
      this.propSubject.next(props);
    }
  }

  public updateCellEnabled(enabled: boolean, artifactPath: string) {
    if (this.ruleInfo && this.ruleInfo.data.getCell(this.ruleInfo.colDef.colId)) {
      this.beComponent = <BEDecisionTableEditorComponent>ArtifactPropertiesService.artifactComponentMap.get(artifactPath);
      if (this._exceptionTable) {
        const action = this.beComponent.service.cellEnabledEditActionET(this.ruleInfo.data.getId(), this.ruleInfo.colDef.colId, this.ruleInfo.value, this._cellComments, this.ruleInfo.data.getCell(this.ruleInfo.colDef.colId)._disabled ? this.ruleInfo.data.getCell(this.ruleInfo.colDef.colId)._disabled : '', !enabled);
        this.beComponent.execute(action);
      } else {
        const action = this.beComponent.service.cellEnabledEditAction(this.ruleInfo.data.getId(), this.ruleInfo.colDef.colId, this.ruleInfo.value, this._cellComments, this.ruleInfo.data.getCell(this.ruleInfo.colDef.colId)._disabled ? this.ruleInfo.data.getCell(this.ruleInfo.colDef.colId)._disabled : '', !enabled);
        this.beComponent.execute(action);
      }
      this._cellEnabled = enabled;
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
      this.propSubject.next(props);
    }
  }

  public updateRuleComment(newComment: string, artifactPath: string) {
    if (this.ruleInfo) {
      this.beComponent = <BEDecisionTableEditorComponent>ArtifactPropertiesService.artifactComponentMap.get(artifactPath);
      if (this._exceptionTable) {
        const action = this.beComponent.service.ruleCommentEditActionET(this.ruleInfo.data.getId(), this.ruleInfo.data.ruleComment ? this.ruleInfo.data.ruleComment : '', newComment);
        this.beComponent.execute(action);
      } else {
        const action = this.beComponent.service.ruleCommentEditAction(this.ruleInfo.data.getId(), this.ruleInfo.data.ruleComment ? this.ruleInfo.data.ruleComment : '', newComment);
        this.beComponent.execute(action);
      }
      this._ruleComments = newComment;
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
      this.propSubject.next(props);
    }
  }

  public updateCellCommentUndoRedo(newComment: string) {
    this._cellComments = newComment;
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
    this.propSubject.next(props);
  }

  public updateCellEnabledUndoRedo(enabled: boolean) {
    this._cellEnabled = enabled;
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
    this.propSubject.next(props);

  }

  public updateRuleCommentUndoRedo(newComment: string) {
    this._ruleComments = newComment;
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
    this.propSubject.next(props);
  }

  public updateRTIBuilderProperties(rtiName: string, newPriority: number, newDescription: string, artifactPath: string) {
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
    this._rtiBuilderComponent = <RuleTemplateInstanceBuilder>ArtifactPropertiesService.artifactComponentMap.get(artifactPath);
    oldProps.rtiPriority = this._rtiBuilderComponent.base.getRulepriortiy();
    oldProps.rtiDescription = this._rtiBuilderComponent.base.getDescription();

    this.propSubject.next(newProps);
    const action = this._rtiBuilderComponent.editorService.modifyProperties(oldProps, newProps);
    action.execute(this._rtiBuilderComponent);
  }

  public updateRTIViewProperties(rtiName: string, newPriority: number, newDescription: string, artifactPath: string) {
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
    this._rtiViewComponent = <RTIViewClient>ArtifactPropertiesService.artifactComponentMap.get(artifactPath);
    oldProps.rtiPriority = this._rtiViewComponent.base.getRulepriortiy();
    oldProps.rtiDescription = this._rtiViewComponent.base.getDescription();

    this.propSubject.next(newProps);
    const action = this._rtiViewComponent.editorService.modifyProperties(oldProps, newProps);
    action.execute(this._rtiViewComponent);
  }
}
