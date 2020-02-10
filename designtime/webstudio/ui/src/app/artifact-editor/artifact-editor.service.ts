
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { filter } from 'rxjs/operators';

import { ArtifactService } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { ModalService } from '../core/modal.service';
import { EditBuffer } from '../editables/edit-buffer';
import { EditorInterface } from '../editors/editor-interface';
import { Artifact } from '../models/artifact';
import { TriButtonConfirmContext, TriButtonConfirmModal } from '../shared/tri-button-confirm.modal';
import { I18nImpl } from '../widgets/i18n-impl';
/**
 * Persist buffer info across components
 */
@Injectable()
export class ArtifactEditorService {
  private buffers = new Map<string, EditBuffer<any>>();

  constructor(
    private artifact: ArtifactService,
    private modal: ModalService,
    private log: Logger,
    public i18n: I18n
  ) {
    // every time a specific artifact is mark as stale, we clear it's buffer.
    this.artifact.stateChanges().pipe(filter(change => change.state !== 'MODIFIED')) // don't clear 'MODIFIED' entries
      .subscribe(change => {
        if (change.artifact) {
          this.clearBuffer(change.artifact);
        }
      });
  }

  getBuffer(artifact: Artifact): EditBuffer<any> {
    return this.buffers.get(artifact.id);
  }

  makeBuffer(artifact: Artifact, editorInterface?: EditorInterface, editable?: boolean): EditBuffer<any> {
    if (!editorInterface) {
      editorInterface = artifact.type.defaultInterface;
    }
    switch (editorInterface) {
      case EditorInterface.TEXT:
        return EditBuffer.text().init(artifact.content, editable);
      case EditorInterface.SB_DECISION_TABLE:
        return EditBuffer.decisionTable().init(artifact.content, editable);
      case EditorInterface.BE_DECISION_TABLE:
        const contentPart = JSON.parse(artifact.content);
        contentPart.projectName = artifact.projectId ? artifact.projectId : '';
        contentPart.artifactPath = artifact.path ? artifact.path : '';
        new I18nImpl(this.i18n);
        return EditBuffer.beDecisionTable().init(JSON.stringify(contentPart), editable);
      case EditorInterface.METADATA:
        return EditBuffer.metadata().init(artifact.metadata, editable);
      case EditorInterface.RULE_TEMPLATE_INSTANCE_BUILDER:
        return EditBuffer.ruleTemplateInstanceBuilder().init(artifact.content, editable);
      case EditorInterface.RULE_TEMPLATE_INSTANCE_VIEW:
        return EditBuffer.ruleTemplateInstanceView().init(artifact.content, editable);
      case EditorInterface.DOMAIN_MODEL:
        return EditBuffer.domainModel().init(artifact.content, editable);
      case EditorInterface.PROJECT_SUMMARY:
        return EditBuffer.projectSummary().init(artifact.content, editable);
      default:
        throw Error(this.i18n('Unable to recognize editor interface') + ': ' + editorInterface.title);
    }
  }

  setBuffer(artifact: Artifact, buffer: EditBuffer<any>) {
    this.buffers.set(artifact.id, buffer);
  }

  clearBuffer(artifact: Artifact) {
    this.buffers.set(artifact.id, null);
  }

  /**
   * Resolve to true when user choose to save the changes.
   * When buffer is clean or user choose to discard chagnes, resolve to false.
   * Reject when chose cancel.
   */
  public promptIfDirty(artifact: Artifact): Promise<boolean> {
    const buffer = this.getBuffer(artifact);
    if (buffer && buffer.isDirty()) {
      const msg = this.i18n('{{name}} has changes, do you want to save them?', { name: artifact.name });
      return this.modal.open(TriButtonConfirmModal, new TriButtonConfirmContext(msg,
        'Save', 'btn btn-sm btn-narrow btn-primary',
        'Don\'t save', 'btn btn-sm btn-wide btn-outline-primary',
        'Cancel', 'btn btn-sm btn-narrow btn-outline-primary'))
        .then(save => {
          if (save) {
            const oldContent = artifact.content;
            artifact.content = buffer.serialize();
            return this.artifact.updateCheckoutArtifact(artifact)
              .then(ok => {
                if (ok) {
                  buffer.onSave();
                } else {
                  artifact.content = oldContent;
                }
                return ok;
              });
          } else {
            buffer.onDiscardChanges();
            return false;
          }
        });
    } else {
      return Promise.resolve(false);
    }
  }
}
