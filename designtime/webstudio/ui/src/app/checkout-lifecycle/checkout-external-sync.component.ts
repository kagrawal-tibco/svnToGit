
import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { AlertService } from '../core/alert.service';
import { ArtifactService } from '../core/artifact.service';
import { LifecycleService } from '../core/lifecycle.service';
import { Logger } from '../core/logger.service';
import { Differ } from '../editables/decision-table/differ/differ';
import { CommitCandidate } from '../models/commit-candidate';
import { ProjectMeta } from '../models/project-meta';
import { ContentModelService } from '../workspace/content-group/content-model.service';

export class CheckoutExternalSyncContext extends BSModalContext {
  constructor(
    public project: ProjectMeta
  ) {
    super();
    this.isBlocking = true;
  }
}

@Component({
  templateUrl: './checkout-external-sync.component.html',
})
export class CheckoutExternalSyncComponent implements ModalComponent<CheckoutExternalSyncContext>, OnInit {
  project: ProjectMeta;
  candidates: CommitCandidate[];
  message: string;
  candidatesObs: Observable<CommitCandidate[]>;

  constructor(
    public dialog: DialogRef<CheckoutExternalSyncContext>,
    private lifecycleService: LifecycleService,
    private artifactService: ArtifactService,
    private contentModelService: ContentModelService,
    private log: Logger,
    private alert: AlertService,
    private differ: Differ,
    public i18n: I18n
  ) {
    this.project = dialog.context.project;
    if (!this.project) {
      this.log.err(this.i18n('Project shall not be empty'));
    }
  }

  ngOnInit() {
    this.candidatesObs = this.lifecycleService
      .getExternalSyncCandidates(this.project.projectId).pipe(
        map(res => this.candidates = res));
  }

  onSubmit() {
    const ids = this.candidates.filter(c => c.selected).map(c => c.id);
    this.lifecycleService.synchronizeExternalArtifacts(this.project.projectId, ids)
      .then(ok => {
        // clear the cached artifacts and force server load
        this.artifactService.clear();
        this.contentModelService.refresh();
        this.dialog.close(true);
      }, err => this.alert.flash(err, 'warning'));
  }

  canSubmit() {
    return this.candidates && this.candidates.filter(c => c.selected).length > 0;
  }

  onCancel() {
    this.dialog.dismiss();
  }
}
