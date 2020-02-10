import { Component } from '@angular/core';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';
export class TriButtonConfirmContext extends BSModalContext {
  constructor(
    public msg: string,
    public positiveBtnText?: string,
    public positiveBtnClass?: string,
    public negativeBtnText?: string,
    public negativeBtnClass?: string,
    public cancelBtnText?: string,
    public cancelBtnClass?: string
  ) {
    super();
    if (!positiveBtnText) {
      this.positiveBtnText = 'Yes';
    }
    if (!positiveBtnClass) {
      this.positiveBtnClass = 'btn btn-sm btn-narrow btn-primary';
    }
    if (!negativeBtnText) {
      this.negativeBtnText = 'No';
    }
    if (!negativeBtnClass) {
      this.negativeBtnClass = 'btn btn-sm btn-narrow btn-outline-primary';
    }
    if (!cancelBtnText) {
      this.cancelBtnText = 'Cancel';
    }
    if (!cancelBtnClass) {
      this.cancelBtnClass = 'btn btn-sm btn-narrow btn-outline-primary';
    }
  }

}

@Component({
  templateUrl: './tri-button-confirm.modal.html',
  styleUrls: ['./tri-button-confirm.modal.css'],
})
export class TriButtonConfirmModal implements ModalComponent<TriButtonConfirmContext> {
  context: TriButtonConfirmContext;
  public constructor(
    public dialog: DialogRef<TriButtonConfirmContext>
  ) {
    this.context = dialog.context;
  }

  onCancel() {
    this.dialog.dismiss();
  }

  onPositive() {
    this.dialog.close(true);
  }

  onNegative() {
    this.dialog.close(false);
  }
}
