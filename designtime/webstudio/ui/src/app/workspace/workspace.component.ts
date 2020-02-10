import { Component, OnInit } from '@angular/core';

import { Observable } from 'rxjs';

import { WorkspaceService, WorkspaceStatus } from './workspace.service';

import { environment } from '../../environments/environment';
import { AlertService } from '../core/alert.service';
import { Logger } from '../core/logger.service';
import { ModalService } from '../core/modal.service';
import { ProjectService } from '../core/project.service';
import { ProjectCheckoutModal, ProjectCheckoutModalContext } from '../project-checkout/project-checkout.modal';
import { ProjectImporterModal, ProjectImporterModalContext } from '../project-importer/project-importer.modal';

@Component({
  selector: 'workspace',
  templateUrl: './workspace.component.html',
  styleUrls: ['./workspace.component.css'],
})
export class WorkspaceComponent implements OnInit {
  public sidebarState = 'restored';
  public panes = {
    pane1size: 25,
    pane2size: 75,
  };
  constructor(
    private log: Logger,
    private project: ProjectService,
    private alert: AlertService,
    private modal: ModalService,
    private service: WorkspaceService
  ) { }

  get status(): Observable<WorkspaceStatus> {
    return this.service.status;
  }

  ngOnInit() {
    this.service.refresh().then(() => {
      //      this.service.navigateMode = NavigateMode.ARTIFACTS;
    });
  }

  onImportProject() {
    this.project.getAllProjects()
      .then(metas => metas.map(meta => meta.projectName))
      .then(names => this.modal.open(ProjectImporterModal, new ProjectImporterModalContext(names)))
      .then(() => this.service.refresh(), err => { if (err) { throw err; } });
  }

  onCheckoutArtifacts() {
    this.modal.open(ProjectCheckoutModal, new ProjectCheckoutModalContext())
      .then(() => { }, err => { if (err) { throw err; } });
  }

  onClick(e: Event) {
    this.log.log(e);
  }

  showCreateProject() {
    return !environment.enableBEUI;
  }

  gutterClick(e: { gutterNum: number, sizes: Array<number> }) {
    setTimeout(() => {
      if (this.panes.pane1size > 0) {
        this.panes = {
          pane1size: 0,
          pane2size: 100,
        };
      } else {
        this.panes = {
          pane1size: 25,
          pane2size: 75,
        };
      }
    }, 0);
  }

  dragEnd(e: { gutterNum: number, sizes: Array<number> }) {
    this.panes.pane1size = e.sizes[0];
    this.panes.pane2size = e.sizes[1];
  }
}
