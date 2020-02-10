import { Component } from '@angular/core';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { SCMService } from 'app/core/scm.service';

import { ProjectService } from '../core/project.service';
import { Project } from '../models/project';

export class ProjectImporterModalContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-lg modal-extra-wide';
  constructor(
    public existingProjectNames: string[]
  ) {
    super();
    this.isBlocking = true;
    this.keyboard = null;
  }

}

@Component({
  template: `
  <project-importer class="modal-lg modal-extra-wide"
    [existingProjectNames]='context.existingProjectNames'
    (result)='handleResult($event)'
  ></project-importer>
  `,
})
export class ProjectImporterModal implements ModalComponent<ProjectImporterModalContext> {
  context: ProjectImporterModalContext;
  constructor(
    public dialog: DialogRef<ProjectImporterModalContext>,
    private project: ProjectService,
    private scm: SCMService,
  ) {
    this.context = dialog.context;
    this.context.dialogClass = 'modal-dialog modal-lg modal-extra-wide';
  }

  handleResult(result: Project) {
    if (result) {
      let promise;
      if (result.getMeta().type !== 'NATIVE') {
        promise = this.scm.importProject(result.getMeta().scmParams.repo.name,
          result.getMeta().projectName, result.getMeta().scmParams.root.path);
      } else {
        promise = this.project.addProject(result);
      }
      promise.then(ok => {
        if (ok) {
          this.dialog.close();
        }
      });
    } else {
      this.dialog.dismiss();
    }
  }
}
