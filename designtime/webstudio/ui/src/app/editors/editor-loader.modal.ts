import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { EditorInterface } from './editor-interface';
import { EditorParams } from './editor-params';

import { EditBuffer } from '../editables/edit-buffer';
import { Artifact } from '../models/artifact';
import { CommitCandidate } from '../models/commit-candidate';
import { ResizableModal } from '../shared/resizablemodal';

export class TypedEditorContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-lg modal-editor modal-extra-wide';
  constructor(
    public params: EditorParams<any>,
    public heading?: string
  ) {
    super();
  }
}

@Component({
  templateUrl: './editor-loader.modal.html',
})
export class EditorLoaderModal extends ResizableModal implements ModalComponent<TypedEditorContext> {
  context: TypedEditorContext;
  heading: string;
  constructor(
    public dialog: DialogRef<TypedEditorContext>,
    public i18n: I18n
  ) {
    super(dialog.context, dialog.context.dialogClass);
    this.context = dialog.context;
    this.heading = this.context.heading ? this.context.heading : 'Preview: ';
  }

  public static default(artifact: Artifact | CommitCandidate, base?: Artifact, heading?: string) {
    return new TypedEditorContext({
      editorInterface: artifact.type.defaultInterface,
      showChangeSet: base ? 'rhs' : 'none',
      base: base ? base.content : null,
      editBuffer: artifact.type.defaultInterface.makeEditBuffer(artifact, false),
      editorMode: 'display'
    }, heading);
  }

  public static decisionTable(artifact: Artifact, base?: Artifact, heading?: string) {
    return new TypedEditorContext({
      editorInterface: EditorInterface.SB_DECISION_TABLE,
      showChangeSet: base ? 'rhs' : 'none',
      base: base ? base.content : null,
      editBuffer: EditBuffer.decisionTable().init(artifact.content, false),
      editorMode: 'display'
    }, heading);
  }

  public static beDecisionTable(artifact: Artifact, base?: Artifact, heading?: string) {
    return new TypedEditorContext({
      editorInterface: EditorInterface.BE_DECISION_TABLE,
      showChangeSet: base ? 'rhs' : 'none',
      base: base ? base.content : null,
      editBuffer: EditBuffer.beDecisionTable().init(artifact.content, false),
      editorMode: 'display'
    }, heading);
  }

  public static metadata(artifact: Artifact, base?: Artifact, heading?: string) {
    return new TypedEditorContext({
      editorInterface: EditorInterface.TEXT,
      showChangeSet: base ? 'rhs' : 'none',
      base: base ? base.metadata : null,
      editBuffer: EditBuffer.text().init(artifact.metadata, false),
      editorMode: 'display'
    }, heading);
  }

  public static text(artifact: Artifact, base?: Artifact, heading?: string) {
    return new TypedEditorContext({
      editorInterface: EditorInterface.TEXT,
      showChangeSet: base ? 'rhs' : 'none',
      base: base ? base.content : null,
      editBuffer: EditBuffer.text().init(artifact.content, false),
      editorMode: 'display'
    }, heading);
  }

  public static domainModel(artifact: Artifact, base?: Artifact, heading?: string) {
    return new TypedEditorContext({
      editorInterface: EditorInterface.DOMAIN_MODEL,
      showChangeSet: base ? 'rhs' : 'none',
      base: base.content,
      editBuffer: EditBuffer.domainModel().init(artifact.content, false),
      editorMode: 'display'
    }, heading);
  }

  public static projectSummary(artifact: Artifact, base?: Artifact, heading?: string) {
    return new TypedEditorContext({
      editorInterface: EditorInterface.PROJECT_SUMMARY,
      showChangeSet: base ? 'rhs' : 'none',
      base: base.content,
      editBuffer: EditBuffer.projectSummary().init(artifact.content, false),
      editorMode: 'display'
    }, heading);
  }

  public static ruleTemplateInstanceBuilder(artifact: Artifact, base?: Artifact, heading?: string) {
    return new TypedEditorContext({
      editorInterface: EditorInterface.RULE_TEMPLATE_INSTANCE_BUILDER,
      showChangeSet: base ? 'rhs' : 'none',
      base: base ? base.content : null,
      editBuffer: EditBuffer.ruleTemplateInstanceBuilder().init(artifact.content, false),
      editorMode: 'display',
      artifact: artifact
    }, heading);
  }

  public static ruleTemplateInstanceView(artifact: Artifact, base?: Artifact, heading?: string) {
    return new TypedEditorContext({
      editorInterface: EditorInterface.RULE_TEMPLATE_INSTANCE_VIEW,
      showChangeSet: base ? 'rhs' : 'none',
      base: base ? base.content : null,
      editBuffer: EditBuffer.ruleTemplateInstanceView().init(artifact.content, false),
      editorMode: 'display',
      artifact: artifact
    }, heading);
  }

  onClose() {
    this.dialog.close();
  }
}
