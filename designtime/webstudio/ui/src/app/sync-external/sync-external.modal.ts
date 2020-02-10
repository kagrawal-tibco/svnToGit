import { Component, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { SyncExternalComponent } from './sync-external.component';

import { Artifact } from '../models/artifact';

export class SyncExternalModalContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-maximize'; // editor needs wider modal
  constructor(
    public artifact: Artifact
  ) {
    super();
  }
}

@Component({
  templateUrl: './sync-external.modal.html',
  styleUrls: ['./sync-external.modal.css'],
})
export class SyncExternalModal implements ModalComponent<SyncExternalModalContext> {

  set showDiff(val: boolean) {
    this.syncExternal.showDiff = val;
  }

  get showDiff() {
    return this.syncExternal.showDiff;
  }
  public context: SyncExternalModalContext;
  @ViewChild('syncExternal', { static: false })
  private syncExternal: SyncExternalComponent;
  private isValid: boolean;

  constructor(
    public dialog: DialogRef<SyncExternalModalContext>,
    public i18n: I18n
  ) {
    this.context = dialog.context;
  }

  public static context(artifact: Artifact) {
    return new SyncExternalModalContext(artifact);
  }

  onCancel() {
    this.dialog.dismiss();
  }

  onConfirm() {
    this.syncExternal.submit()
      .then(ok => {
        if (ok) {
          this.dialog.close();
        }
      });
  }

  onReset() {
    this.syncExternal.reset();
  }

  onValidChange(val: boolean) {
    this.isValid = val;
  }

  canConfirm() {
    return this.isValid;
  }

  getMessage(): string {
    return this.i18n('Synchronize external changes to {{0}}', { 0: this.context.artifact.name });
  }
}
