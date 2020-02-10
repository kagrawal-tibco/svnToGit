import { Component, Inject } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
// import { IMultiSelectOption, IMultiSelectSettings, IMultiSelectTexts } from 'angular-2-dropdown-multiselect';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { AlertService } from '../core/alert.service';
import { LifecycleService } from '../core/lifecycle.service';
import { RestService } from '../core/rest.service';
import { Commit } from '../models/commit';

export class CommitDelegateContext extends BSModalContext {
  constructor(
    public cc: Commit
  ) {
    super();
  }
}

@Component({
  templateUrl: './commit-delegate.modal.html',
})
export class CommitDelegateModal { // implements ModalComponent<CommitDelegateContext>{
  targetRoles = new FormControl();

  cc: Commit;
  // roles: string;
  // delegateRoleSettings: IMultiSelectSettings;
  delegateRoleOptions: Array<any>;
  // delegateTexts: IMultiSelectTexts;
  selectedRoles: Array<number>;

  constructor(
    public dialogRef: MatDialogRef<CommitDelegateModal>,
    @Inject(MAT_DIALOG_DATA) public data: CommitDelegateContext,
    // public dialog: DialogRef<CommitDelegateContext>,
    public lifecycleService: LifecycleService,
    public alert: AlertService,
    public rest: RestService,
    public i18n: I18n
  ) {
    this.cc = data.cc;
    this.init();
  }

  init() {
    // this.delegateRoleSettings = {
    //   enableSearch: false,
    //   checkedStyle: 'checkboxes',
    //   buttonClasses: 'btn btn-outline-primary btn-block',
    //   showCheckAll: false,
    //   showUncheckAll: false,
    //   dynamicTitleMaxItems: 3,
    //   displayAllSelectedText: false
    // };
    // this.selectedRoles = new Array<number>();
    // this.delegateTexts = {
    //   checkAll: this.i18n('Select all'),
    //   uncheckAll: this.i18n('Unselect all'),
    //   checked: this.i18n('item selected'),
    //   checkedPlural: this.i18n('items selected'),
    //   searchPlaceholder: this.i18n('Find'),
    //   searchNoRenderText: this.i18n('Type in search box to see results...'),
    //   searchEmptyResult: this.i18n('Nothing found...'),
    //   defaultTitle: '',
    //   allSelected: this.i18n('All Enviornment selected'),
    // };

    this.generateDelegateRoleOptions()
      .then((options: Array<any>) => {
        this.delegateRoleOptions = options;
      });
  }
  onClose() {
    this.dialogRef.close();
  }

  onConfirm() {
    const roles = this.targetRoles.value;
    if (this.cc && roles && roles.length > 0) {
      this.lifecycleService.delegateCommits([this.cc.id], roles)
        .then(res => {
          if (res) {
            this.alert.flash(this.i18n('Commit with revision Id of [{{id}}] is successfully delegated', { id: this.cc.id }), 'success');
            this.dialogRef.close(true);
          }
          this.dialogRef.close(false);
        });
    } else {
      this.alert.flash(this.i18n('Missing roles to delegate to.'), 'error');
      this.dialogRef.close(false);
    }
  }

  delegateToRoles(): Promise<Array<string>> {
    return this.rest.get('delegateRoles.json')
      .toPromise()
      .then((response) => {
        if (response.ok()) {
          const record = response.record;
          if (record) {
            const roles: Array<string> = new Array<string>();
            for (let i = 0; i < record.length; i++) {
              roles.push(record[i].role);
            }
            return roles;
          }
        }
      });
  }

  // onChangeRoles(rolesEvent: Array<number>) {
  //   let roles;
  //   if (rolesEvent && rolesEvent.length > 0) {
  //       roles = this.delegateRoleOptions[rolesEvent[0]]['name'];
  //       for (let i = 1; i < rolesEvent.length; i++) {
  //         roles += ',' + this.delegateRoleOptions[rolesEvent[i]]['name'];
  //       }
  //   }
  //   this.roles = roles;
  // }

  canConfirm(): boolean {
    return (this.cc && (this.targetRoles.value && this.targetRoles.value.length > 0)) ? true : false;
  }

  generateDelegateRoleOptions(): Promise<Array<any>> {
    const delegateRoleOptions: Array<any> = new Array<any>();
    return this.delegateToRoles().then((roles: Array<string>) => {
      for (let i = 0; i < roles.length; i++) {
        const option = { id: i, name: roles[i] };
        delegateRoleOptions.push(option);
      }
      return delegateRoleOptions;
    });
  }
}
