import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { VerifyConfig } from './verify.service';

export class VerifyConfigModalContext extends BSModalContext {
  constructor() {
    super();
  }

}

@Component({
  templateUrl: './verify-config.modal.html',
})
export class VerifyConfigModal implements ModalComponent<VerifyConfigModalContext>, OnInit {
  config: VerifyConfig;

  constructor(
    public dialog: DialogRef<VerifyConfigModalContext>,
    public i18n: I18n
  ) { }

  static context() { return new VerifyConfigModalContext(); }

  ngOnInit() {
    this.config = new VerifyConfig();
    this.config.showJSONResult = true;
    this.config.showSimpleResult = true;
  }

  onConfirm() {
    this.dialog.close(this.config);
  }

  onCancel() {
    this.dialog.dismiss();
  }

}
