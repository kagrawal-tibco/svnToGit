import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroupDirective, NgForm, Validators, ValidatorFn } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { environment } from '../../../../environments/environment';
import { AlertService } from '../../../core/alert.service';
import { Artifact } from '../../../models/artifact';
import { ContentModelService } from '../content-model.service';

export class CreateGroupModalContext extends BSModalContext {
  constructor(
    public projectName: string,
    public artifact: Artifact,
    public service: ContentModelService
  ) {
    super();
  }

}

export class GroupErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }

}

@Component({
  templateUrl: './create-group.modal.html'
})
export class CreateGroupModal implements ModalComponent<CreateGroupModalContext> {

  get title() {
    return this.i18n('Create New Artifact Group');
  }

  get showFilter(): boolean {
    return !environment.enableBEUI;
  }

  get disableCreateGroup(): boolean {
    return this.groupNameFormControl.errors != null;
  }
  context: CreateGroupModalContext;
  public groupName: string;
  public filter: string;

  groupNameFormControl = new FormControl('', [
    Validators.required,
    this.nameValidator(),
  ]);

  matcher = new GroupErrorStateMatcher();

  constructor(
    groupService: ContentModelService,
    public alert: AlertService,
    public dialog: DialogRef<CreateGroupModalContext>,
    public i18n: I18n
  ) {
    this.context = dialog.context;
  }

  nameValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      const regExp = new RegExp('^[a-zA-Z0-9_ ]*$');
      // let regExp = new RegExp("[\\w ]+");
      const value: String = control.value;
      let forbidden = false;
      forbidden = value != undefined ? value.indexOf('\\') > -1 : false;
      forbidden = forbidden || (value != undefined && value.trim().length === 0);
      forbidden = forbidden || !regExp.test(control.value);
      return forbidden ? { 'name': { value: control.value } } : null;
    };
  }

  onCancel() {
    this.dialog.dismiss();
  }

  onCreateGroup() {
    if (environment.enableBEUI) {
      this.context.service.getAllGroups(false).then(
        response => {
          const groups = response.record;
          const idx = groups.findIndex(grp => {
            return grp.name === this.groupName.trim();
          });
          if (idx > -1) {
            this.alert.flash(this.i18n('The group with name {{groupName}} already exists.', { groupName: this.groupName }), 'error');
          } else {
            this.context.service.createGroup(this.groupName, this.filter, this.context.artifact).then(
              data => {
                this.context.service.refreshGroups();
                if (data && data.response) {
                  console.log(data.response);
                }
              }
            );
            this.dialog.dismiss();
          }
        }
      );
    } else {
      this.context.service.createGroup(this.groupName, this.filter, this.context.artifact).then(
        data => {
          this.context.service.refreshGroups();
          if (data && data.response) {
            console.log(data.response);
          }
        }
      );
      this.dialog.dismiss();
    }
  }

  getMessage(): string {
    const msg = this.i18n('Group name is {{0}}required{{1}}.', { 0: `<strong>`, 1: `</strong>` });
    return msg;
  }

}
