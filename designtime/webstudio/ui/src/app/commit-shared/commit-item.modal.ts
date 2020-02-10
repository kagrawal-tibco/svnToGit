import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { environment } from '../../environments/environment';
import { Commit } from '../models/commit';

export class CommitItemContext extends BSModalContext {
  constructor(
    public commit: Commit
  ) {
    super();
    this.size = 'lg';
  }
}

@Component({
  templateUrl: './commit-item.modal.html',
})
export class CommitItemModal { // implements ModalComponent<CommitItemContext> {
  commit: Commit;
  mode: string;

  constructor(
    public dialogRef: MatDialogRef<CommitItemModal>,
    @Inject(MAT_DIALOG_DATA) public data: CommitItemContext,
    public i18n: I18n,
    // public dialog: DialogRef<CommitItemContext>
  ) {
    this.commit = data.commit;
    this.mode = (environment.enableBEUI) ? 'FOR_HISTORY' : 'NORMAL';
  }

  onClose() {
    this.dialogRef.close();
  }
}
