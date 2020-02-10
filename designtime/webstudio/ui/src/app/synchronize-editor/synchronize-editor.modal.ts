import { Component } from '@angular/core';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { Artifact } from '../models/artifact';

export class SynchronizeEditorContext extends BSModalContext {
  needContentEditor = false;
  dialogClass = 'modal-dialog modal-maximize'; // editor needs wider modal
  constructor(
    public base: Artifact,
    public lhs: Artifact,
    public rhs: Artifact
  ) {
    super();
    if (lhs.status === 'DELETED' || rhs.status === 'DELETED') {
      this.needContentEditor = false;
      //      this.dialogClass = 'modal-dialog';
    } else if (lhs.status === 'ADDED' || rhs.status === 'ADDED') {
      this.needContentEditor = false;
      //      this.dialogClass = 'modal-dialog';
    } else {
      this.needContentEditor = true;
      //      this.dialogClass = 'modal-dialog modal-lg modal-editor modal-extra-wide'; // editor needs wider modal
    }
  }
}

@Component({
  templateUrl: './synchronize-editor.modal.html',
})
export class SynchronizeEditorModal implements ModalComponent<SynchronizeEditorContext> {

  needContentEditor: boolean;
  private context: SynchronizeEditorContext;
  constructor(
    public dialog: DialogRef<SynchronizeEditorContext>
  ) {
    this.context = dialog.context;
    this.needContentEditor = this.context.needContentEditor;
  }

  onClose(artifact: Artifact) {
    if (confirm) {
      this.dialog.close(artifact);
    } else {
      this.dialog.dismiss();
    }
  }

  editorUIDiff(): boolean {
    if (this.context.base.type.defaultExtension === 'ruletemplateinstance'
      || this.context.base.type.defaultExtension === 'rulefunctionimpl'
      || this.context.base.type.defaultExtension === 'sbdt') {
      return true;
    } else {
      return false;
    }
  }
}
