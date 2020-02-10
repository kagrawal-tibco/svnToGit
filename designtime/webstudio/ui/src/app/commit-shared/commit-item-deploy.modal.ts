import { Component, Inject } from '@angular/core';
import { FormControl } from '@angular/forms';
// import { IMultiSelectOption, IMultiSelectSettings, IMultiSelectTexts } from 'angular-2-dropdown-multiselect';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from 'environments/environment';

import { AlertService } from '../core/alert.service';
import { LifecycleService } from '../core/lifecycle.service';
import { RestService } from '../core/rest.service';
import { BECommitCandidate } from '../models-be/commit-candidate-be';

export class CommitItemDeployContext {
  constructor(
    public cc: BECommitCandidate
  ) {
  }
}

@Component({
  templateUrl: './commit-item-deploy.modal.html',
})
export class CommitItemDeployModal {
  targetEnvs = new FormControl();

  cc: BECommitCandidate;
  comment = '';
  deployAPIInProgress = false;
  // environmentSettings: IMultiSelectSettings;
  environmentOptions: Array<any>;
  // environmentTexts: IMultiSelectTexts;
  // selectedEnvironment: Array<number>;
  // selectedEnvironments: Array<IMultiSelectOption>;
  // currentlySelectedEnvs: string;

  constructor(
    public dialogRef: MatDialogRef<CommitItemDeployModal>,
    @Inject(MAT_DIALOG_DATA) public data: CommitItemDeployContext,
    // public dialog: DialogRef<CommitItemDeployContext>,
    public lifecycleService: LifecycleService,
    public alert: AlertService,
    public rest: RestService,
    public i18n: I18n
  ) {
    this.cc = data.cc;
    // this.environmentSettings = {
    //   enableSearch: false,
    //   checkedStyle: 'checkboxes',
    //   buttonClasses: 'btn btn-outline-primary btn-block',
    //   showCheckAll: false,
    //   showUncheckAll: false,
    //   dynamicTitleMaxItems: 3,
    //   displayAllSelectedText: false
    // };
    // this.selectedEnvironment = new Array<number>();
    // this.environmentTexts = {
    //   checkAll: this.i18n('Select all'),
    //   uncheckAll: this.i18n('Unselect all'),
    //   checked: this.i18n('item selected'),
    //   checkedPlural: this.i18n('items selected'),
    //   searchPlaceholder: this.i18n('Find'),
    //   searchEmptyResult: this.i18n('Nothing found...'),
    //   searchNoRenderText: this.i18n('Type in search box to see results...'),
    //   defaultTitle: '',
    //   allSelected: this.i18n('All Enviornment selected'),
    // };
    if (!environment.enableTCEUI) {
      this.environmentOptions = this.generateEnvironmentOptions();
    }
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  onClose(): void {
    this.dialogRef.close();
  }

  onConfirm() {
    if (this.cc && (environment.enableTCEUI || this.targetEnvs.value)) {
      const clonecc = Object.assign({}, this.cc);
      if (clonecc) {
        clonecc.deployComments = this.comment;
        if (!environment.enableTCEUI) {
          const currentlySelectedEnvs = this.targetEnvs.value as Array<string>;
          clonecc.deployEnvironments = currentlySelectedEnvs.join(',');
        }
      }
      this.lifecycleService.deployArtifact(clonecc)
        .then(res => {
          if (res.status) {
            this.cc.reviewStatus = 'BuildAndDeploy';
            this.cc.lastDeployTime = (new Date()).getTime();
            this.cc.deployerName = this.rest.userName;
            if (!environment.enableTCEUI) {
              const currentlySelectedEnvs = this.targetEnvs.value as Array<string>;
              this.cc.deployEnvironments = currentlySelectedEnvs.join(',');
            }
            this.cc.deployComments = this.comment;
            this.alert.flash(this.i18n('Artifact [{{path}}] successfully deployed', { path: this.cc.path }), 'success');
          } else {
            if (res.errorCnt || res.warningCnt) {
              this.alert.flash(this.i18n('There are {{0}} error(s) and {{1}} warning(s) found. Open the artifact and run validate for more information.', { 0: res.errorCnt, 1: res.warningCnt }), 'error', true, -1);
            }
          }
          this.dialogRef.close();
        });
      this.deployAPIInProgress = true;
    } else {
      this.alert.flash(this.i18n('Missing deployment environment '), 'error');
      this.dialogRef.close();
    }
  }

  // onChangeEnvironment(envEvent: any[]) {
  //   this.currentlySelectedEnvs = '';

  //   let availableEnvironment: Array<string> = this.applicableEnvironments();
  //   if (envEvent.length > 0) {
  //     for (let i = 0; i < envEvent.length; i++) {
  //       this.currentlySelectedEnvs += availableEnvironment[envEvent[i]] + ',';
  //     }
  //     if (this.currentlySelectedEnvs.endsWith(','))  {
  //       this.currentlySelectedEnvs =
  //       this.currentlySelectedEnvs.substr(0, this.currentlySelectedEnvs.length-1);
  //     }
  //   } else {
  //     this.currentlySelectedEnvs = undefined;
  //   }
  // }

  applicableEnvironments(): string[] {
    return this.cc.applicableEnvironments.split(',');
  }

  generateEnvironmentOptions(): Array<any> {
    const environmentOptions: Array<any> = new Array<any>();
    const environments: string[] = this.applicableEnvironments();
    for (let i = 0; i < environments.length; i++) {
      const option = { id: i, name: environments[i] };
      environmentOptions.push(option);
    }
    return environmentOptions;
  }

  canConfirm(): boolean {
    return (this.cc && (this.targetEnvs.value &&
      (environment.enableTCEUI || this.targetEnvs.value.length > 0)) && !this.deployAPIInProgress) ? true : false;
  }

  onCommentChange($event): void {
    // console.log($event);
    this.comment = $event.target.value;
  }
}
