import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { ProjectMeta } from '../models/project-meta';

export class ProjectInfoModalContext extends BSModalContext {
  constructor(
    public project: ProjectMeta
  ) {
    super();
  }

}

@Component({
  templateUrl: './project-info.modal.html'
})
export class ProjectInfoModal implements ModalComponent<ProjectInfoModalContext> {
  context: ProjectInfoModalContext;
  constructor(
    public dialog: DialogRef<ProjectInfoModalContext>,
    public i18n: I18n
  ) {
    this.context = dialog.context;
  }

  onClose() {
    this.dialog.dismiss();
  }

  get title() {
    return this.context.project.projectName;
  }

}
