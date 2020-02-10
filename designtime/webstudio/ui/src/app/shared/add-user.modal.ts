import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { SignupForm } from '../models/signup-form';

export class AddUserContext extends BSModalContext {
  constructor(
    public addUserForm: SignupForm
  ) {
    super();
  }
}
@Component({
  templateUrl: './add-user.modal.html'
})
export class AddUserModal implements ModalComponent<AddUserContext> {
  context: AddUserContext;

  constructor(
    public dialog: DialogRef<AddUserContext>,
    public i18n: I18n
  ) {
    this.context = dialog.context;
  }

  onSubmit(form: SignupForm) {
    this.dialog.close(form);
  }

  onCancel() {
    this.dialog.dismiss();
  }
}
