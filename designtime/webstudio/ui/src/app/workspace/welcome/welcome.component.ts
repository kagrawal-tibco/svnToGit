import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Observable } from 'rxjs';

import { UserService } from 'app/core/user.service';

import { environment } from '../../../environments/environment';
import { BEImportProjectComponent, BEImportProjectContext } from '../../be-project-importer/be-project-importer.modal';
import { ModalService } from '../../core/modal.service';
import { ProjectCheckoutModal, ProjectCheckoutModalContext } from '../../project-checkout/project-checkout.modal';
import { ProjectImporterModal, ProjectImporterModalContext } from '../../project-importer/project-importer.modal';
import { WorkspaceService, WorkspaceStatus } from '../workspace.service';

@Component({
  selector: 'welcome',
  templateUrl: './welcome.component.html',
})

/*
* Don't remove UserService object.
* The property 'isAdmin' of UserService is used in the template.
*/

export class WelcomeComponent {
  constructor(
    private workspace: WorkspaceService,
    private modal: ModalService,
    public user: UserService,
    public i18n: I18n
  ) { }

  onImportProject() {
    this.modal.open(ProjectImporterModal, new ProjectImporterModalContext([]))
      .then(() => this.workspace.refresh(), err => {
        if (err) {
          throw err;
        }
      }).then(() => { });
  }

  onImportBEProject() {
    this.modal.open(BEImportProjectComponent, new BEImportProjectContext())
      .then(() => { }, err => {
        if (err) {
          throw err;
        }
      });
  }

  onCheckoutArtifacts() {
    this.modal.open(ProjectCheckoutModal, new ProjectCheckoutModalContext())
      .then(() => { }, err => { if (err) { throw err; } });
  }

  get status(): Observable<WorkspaceStatus> {
    return this.workspace.status;
  }

  get productName() {
    if (environment.enableTCEUI) {
      return 'Cloud Events WebStudio';
    }
    return (environment.enableBEUI) ? 'BusinessEvents WebStudio' : 'Artifact Management Server';
  }
  get enableTCEUI() {
    return environment.enableTCEUI;
  }
}
