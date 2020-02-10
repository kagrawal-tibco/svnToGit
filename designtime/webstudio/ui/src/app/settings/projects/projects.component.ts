import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from '../../../environments/environment';
import { ModalService } from '../../core/modal.service';
import { ProjectService } from '../../core/project.service';
import { SettingsService } from '../../core/settings.service';
import { ProjectMeta } from '../../models/project-meta';
import { ConfirmDeleterContext, ConfirmDeleterModal } from '../../shared/confirm-deleter.modal';
import { WorkspaceService } from '../../workspace/workspace.service';

@Component({
  templateUrl: './projects.component.html',
})
export class ProjectsComponent implements OnInit {
  projects: ProjectMeta[];

  constructor(
    protected service: SettingsService,
    protected modal: ModalService,
    protected workspace: WorkspaceService,
    protected project: ProjectService,
    public i18n: I18n
  ) {

  }

  ngOnInit() {
    if (environment.enableBEUI) {
      this.project
        .getCheckedOutProjects()
        .then(metas => this.projects = metas)
        .then(metas => {
          // we want to get rid of out-dated project settings every time we access settings
          const old = Object.assign({}, this.service.latestSettings.hideInExplorer);
          this.service.latestSettings.hideInExplorer = {};
          metas.forEach(meta => {
            if (old[meta.projectId]) {
              this.service.latestSettings.hideInExplorer[meta.projectId] = true;
            }
          });
        });
    } else {
      this.project
        .getAllProjects()
        .then(metas => this.projects = metas)
        .then(metas => {
          // we want to get rid of out-dated project settings every time we access settings
          const old = Object.assign({}, this.service.latestSettings.hideInExplorer);
          this.service.latestSettings.hideInExplorer = {};
          metas.forEach(meta => {
            if (old[meta.projectId]) {
              this.service.latestSettings.hideInExplorer[meta.projectId] = true;
            }
          });
        });
    }
  }

  onDelete(p: ProjectMeta) {
    this.modal.open(ConfirmDeleterModal, new ConfirmDeleterContext(p.projectName))
      .then((confirmed) => { return this.project.deleteProject(p); },
        (cancelled) => { /** User cancelled deletion, do nothing. */ })
      .then(ok => {
        if (ok) {
          const idx = this.projects.findIndex(project => project.projectId === p.projectId);
          if (idx !== -1) {
            this.projects.splice(idx, 1);
          }
        }
      })
      .catch(error => { });
  }

  isVisible(project: ProjectMeta) {
    return !this.service.latestSettings.hideInExplorer[project.projectId];
  }

  showBEUI(): boolean {
    return environment.enableBEUI;
  }

  setVisible(project: ProjectMeta, visible: boolean) {
    if (visible) {
      delete this.service.latestSettings.hideInExplorer[project.projectId];
    } else {
      this.service.latestSettings.hideInExplorer[project.projectId] = true;
    }
    this.workspace.refresh();
    this.service.onSaveUISettings();
  }
}
