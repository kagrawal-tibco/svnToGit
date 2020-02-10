import { Type } from '@angular/core';

import { BEDecisionTableEditorComponent } from './decision-table/be-decisiontable-editor.component';
import { DecisionTableEditorComponent } from './decision-table/decisiontable-editor.component';
import { DomainModelComponent } from './domain-model/domainmodel-editor.component';
import { EditorComponent } from './editor.component';
import { MetadataEditorComponent } from './metadata/metadata-editor.component';
import { ProjectSummaryComponent } from './project-summary/project-summary.component';
import { RuleTemplateInstanceBuilder } from './rule-template-instance-builder/rule-template-instance-builder.component';
import { RTIViewClient } from './rule-template-instance-view/RTIViewClient';
import { TextEditorComponent } from './text/text-editor.component';

import { EditBuffer } from '../editables/edit-buffer';
import { Artifact } from '../models/artifact';
import { CommitCandidate } from '../models/commit-candidate';

export class EditorInterface {
  public static get SB_DECISION_TABLE() {
    if (!EditorInterface._DECISION_TABLE) {
      EditorInterface._DECISION_TABLE = new EditorInterface(DecisionTableEditorComponent, 'Decision Table Editor');
    }
    return EditorInterface._DECISION_TABLE;
  }

  public static get BE_DECISION_TABLE() {
    if (!EditorInterface._BE_DECISION_TABLE) {
      EditorInterface._BE_DECISION_TABLE = new EditorInterface(BEDecisionTableEditorComponent, 'BE Decision Table Editor');
    }
    return EditorInterface._BE_DECISION_TABLE;
  }

  public static get TEXT() {
    if (!EditorInterface._TEXT) {
      EditorInterface._TEXT = new EditorInterface(TextEditorComponent, 'Text Editor');
    }
    return EditorInterface._TEXT;
  }

  public static get PROJECT_SUMMARY() {
    if (!EditorInterface._PROJECT_SUMMARY) {
      EditorInterface._PROJECT_SUMMARY = new EditorInterface(ProjectSummaryComponent, 'Summary Editor');
    }
    return EditorInterface._PROJECT_SUMMARY;
  }

  public static get METADATA() {
    if (!EditorInterface._METADATA) {
      EditorInterface._METADATA = new EditorInterface(MetadataEditorComponent, 'Metadata Editor');
    }
    return EditorInterface._METADATA;
  }

  public static get RULE_TEMPLATE_INSTANCE_BUILDER() {
    if (!EditorInterface._RULE_TEMPLATE_INSTANCE_BUILDER) {
      EditorInterface._RULE_TEMPLATE_INSTANCE_BUILDER = new EditorInterface(RuleTemplateInstanceBuilder, 'Rule Template Instance Builder Editor');
    }
    return EditorInterface._RULE_TEMPLATE_INSTANCE_BUILDER;
  }

  public static get RULE_TEMPLATE_INSTANCE_VIEW() {
    if (!EditorInterface._RULE_TEMPLATE_INSTANCE_VIEW) {
      EditorInterface._RULE_TEMPLATE_INSTANCE_VIEW = new EditorInterface(RTIViewClient, 'Rule Template Instance View Editor');
    }
    return EditorInterface._RULE_TEMPLATE_INSTANCE_VIEW;
  }

  public static get DOMAIN_MODEL() {
    if (!EditorInterface._DOMAIN_MODEL) {
      EditorInterface._DOMAIN_MODEL = new EditorInterface(DomainModelComponent, 'Domain Model Editor');
    }
    return EditorInterface._DOMAIN_MODEL;
  }

  static REGISTRY = [
    EditorInterface.SB_DECISION_TABLE,
    EditorInterface.BE_DECISION_TABLE,
    EditorInterface.TEXT,
    EditorInterface.METADATA,
    EditorInterface.RULE_TEMPLATE_INSTANCE_BUILDER,
    EditorInterface.RULE_TEMPLATE_INSTANCE_VIEW,
    EditorInterface.DOMAIN_MODEL,
    EditorInterface.PROJECT_SUMMARY
  ];
  private static _DECISION_TABLE: EditorInterface;
  private static _BE_DECISION_TABLE: EditorInterface;
  private static _TEXT: EditorInterface;
  private static _METADATA: EditorInterface;
  private static _DOMAIN_MODEL: EditorInterface;
  private static _PROJECT_SUMMARY: EditorInterface;
  private static _RULE_TEMPLATE_INSTANCE_BUILDER: EditorInterface;
  private static _RULE_TEMPLATE_INSTANCE_VIEW: EditorInterface;

  constructor(
    private _type: Type<EditorComponent<any>>,
    private _title: string
  ) { }

  public get type(): Type<EditorComponent<any>> {
    return this._type;
  }

  public get title() {
    return this._title;
  }

  public makeEditBuffer(artifact: Artifact | CommitCandidate, editable: boolean) {
    if (this === EditorInterface.METADATA) {
      return this.makeEmptyBuffer().init(artifact.metadata, editable);
    } else {
      return this.makeEmptyBuffer().init(artifact.content, editable);
    }
  }

  public makeEmptyBuffer() {
    switch (this) {
      case EditorInterface.SB_DECISION_TABLE:
        return EditBuffer.decisionTable();
      case EditorInterface.BE_DECISION_TABLE:
        return EditBuffer.beDecisionTable();
      case EditorInterface.DOMAIN_MODEL:
        return EditBuffer.domainModel();
      case EditorInterface.PROJECT_SUMMARY:
        return EditBuffer.projectSummary();
      case EditorInterface.RULE_TEMPLATE_INSTANCE_VIEW:
        return EditBuffer.ruleTemplateInstanceView();
      case EditorInterface.RULE_TEMPLATE_INSTANCE_BUILDER:
        return EditBuffer.ruleTemplateInstanceBuilder();
      case EditorInterface.METADATA:
        return EditBuffer.metadata();
      default:
        return EditBuffer.text();
    }
  }
}
