import { Component } from '@angular/core';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

export class ConfirmDeleterContext extends BSModalContext {
  constructor(
    public input: string,
    public msg?: string
  ) {
    super();
  }
}

@Component({
  template: `
  <confirm-deleter
    [input]='context.input'
    [msg]='context.msg'
    (output)='onOutput($event)'
  ></confirm-deleter>
  `,
})
export class ConfirmDeleterModal implements ModalComponent<ConfirmDeleterContext> {
  context: ConfirmDeleterContext;
  constructor(public dialog: DialogRef<ConfirmDeleterContext>) {
    this.context = dialog.context;
  }

  onOutput(result: boolean) {
    if (result) {
      this.dialog.close();
    } else {
      this.dialog.dismiss();
    }
  }
}
