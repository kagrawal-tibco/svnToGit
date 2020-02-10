import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from 'environments/environment';
import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { AlertService, AlertType } from '../core/alert.service';
import { ProjectService } from '../core/project.service';

export class GenerateDeployableContext extends BSModalContext {
  constructor(
    public project: string
  ) {
    super();
    this.size = 'lg';
  }
}

@Component({
  templateUrl: './generate-deployable.modal.html',
})
export class GenerateDeployableModal implements ModalComponent<GenerateDeployableContext> {
  project: string;
  isGenerateClass = false;
  doEarDeployment = false;
  apiInProgress = false;

  constructor(
    public dialog: DialogRef<GenerateDeployableContext>,
    public projectService: ProjectService,
    public alert: AlertService,
    public i18n: I18n
  ) {
    this.project = dialog.context.project;
  }

  onClose() {
    this.dialog.close();
  }

  onConfirm() {
    this.projectService.generateDeployable(this.project, this.isGenerateClass, this.doEarDeployment)
      .then(res => {
        if (res.status) {
          if (res.warningCnt === 1 && res.warningMsg) {
            this.alert.flash(res.warningMsg, 'warning');
          }
        }
        this.dialog.close();
      });
    this.apiInProgress = true;
  }

  canConfirm(): boolean {
    return this.project && !this.apiInProgress;
  }

  get generateClasses() {
    return this.isGenerateClass;
  }

  set generateClasses(val: boolean) {
    this.isGenerateClass = val;
  }

  get doEarHotDeployment() {
    return this.doEarDeployment;
  }

  set doEarHotDeployment(val: boolean) {
    this.doEarDeployment = val;
  }

  get enableTCEUI() {
    return environment.enableTCEUI;
  }

  projectList() {
    const projectList: string[] = [];
    this.projectService.projectMetaCache.forEach(entry => {
      projectList.push(entry.projectName);
    });
    return projectList;
  }
}
