
import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { AlertService } from '../core/alert.service';
import { ArtifactService } from '../core/artifact.service';
import { LifecycleService } from '../core/lifecycle.service';
import { Logger } from '../core/logger.service';
import { ModalService } from '../core/modal.service';
import { CommitCandidate } from '../models/commit-candidate';
import { ProjectMeta } from '../models/project-meta';

export class CheckoutSyncContext extends BSModalContext {
  constructor(
    public project: ProjectMeta,
    public artifactPath?: string,
    public artifactType?: string
  ) {
    super();
    this.isBlocking = true;
  }
}

@Component({
  templateUrl: './checkout-sync.component.html',
})
export class CheckoutSyncComponent implements ModalComponent<CheckoutSyncContext>, OnInit {
  project: ProjectMeta;
  artifactPath: string;
  artifactType: string;
  candidates: CommitCandidate[];
  candidatesObs: Observable<CommitCandidate[]>;

  constructor(
    public dialog: DialogRef<CheckoutSyncContext>,
    private lifecycleService: LifecycleService,
    private artifactService: ArtifactService,
    private log: Logger,
    private alert: AlertService,
    private modal: ModalService,
    public i18n: I18n
  ) {
    this.project = dialog.context.project;
    this.artifactPath = dialog.context.artifactPath;
    this.artifactType = dialog.context.artifactType;
    if (!this.project) {
      this.log.err(this.i18n('Project shall not be empty'));
    }
  }

  ngOnInit() {
    if (environment.enableBEUI) {
      this.candidatesObs = this.lifecycleService
        .getSyncCandidates(this.project.projectId, this.artifactPath, this.artifactType).pipe(
          map(res => this.candidates = res));
    } else {
      this.candidatesObs = this.lifecycleService
        .getSyncCandidates(this.project.projectId).pipe(
          map(res => this.candidates = res));
    }
  }

  onCancel() {
    this.dialog.dismiss();
  }
}
