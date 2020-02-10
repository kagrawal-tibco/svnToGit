
import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';
import { of as observableOf, Observable } from 'rxjs';
import { map, mergeMap, take, toArray } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { AlertService } from '../core/alert.service';
import { ArtifactService } from '../core/artifact.service';
import { LifecycleService } from '../core/lifecycle.service';
import { Logger } from '../core/logger.service';
import { ModalService } from '../core/modal.service';
import { SettingsService } from '../core/settings.service';
import { Differ } from '../editables/decision-table/differ/differ';
import { CommitCandidate } from '../models/commit-candidate';
import { ProjectMeta } from '../models/project-meta';
import { WorkspaceService } from '../workspace/workspace.service';

export class CheckoutRevertContext extends BSModalContext {
  constructor(
    public project: ProjectMeta,
    public artifactPath?: string
  ) {
    super();
    this.isBlocking = true;
  }
}

@Component({
  templateUrl: './checkout-revert.component.html',
})
export class CheckoutRevertComponent implements ModalComponent<CheckoutRevertContext>, OnInit {
  project: ProjectMeta;
  artifactPath: string;
  candidates: CommitCandidate[];
  candidatesObs: Observable<CommitCandidate[]>;
  title: string;

  constructor(
    public dialog: DialogRef<CheckoutRevertContext>,
    private settings: SettingsService,
    private lifecycleService: LifecycleService,
    private artifactService: ArtifactService,
    private workspace: WorkspaceService,
    private log: Logger,
    private alert: AlertService,
    private modal: ModalService,
    private differ: Differ,
    public i18n: I18n
  ) {
    this.project = dialog.context.project;
    if (!this.project) {
      this.log.err(this.i18n('Project shall not be empty'));
    }
    this.artifactPath = dialog.context.artifactPath;
  }

  ngOnInit() {
    if (environment.enableBEUI) {
      this.candidatesObs = this.lifecycleService
        .getCommitCandidates(this.project.projectId, this.artifactPath, true).pipe(
          map(res => this.candidates = res));
      this.title = this.i18n('Revert');
    } else {
      this.candidatesObs = this.lifecycleService
        .getCommitCandidates(this.project.projectId).pipe(
          map(res => this.candidates = res));
      this.title = this.i18n('Discard');
    }
  }

  onSubmit() {
    this.modal.confirm()
      .message(this.i18n('Click Confirm to revert changes to selected artifacts. Any local changes will be lost and cannot be recovered.'))
      .okBtn(this.i18n('Confirm'))
      .okBtnClass('btn btn-danger btn-sm')
      .cancelBtn('Cancel')
      .cancelBtnClass('btn btn-outline-primary btn-sm')
      .open().result
      .then(() => {
        if (environment.enableBEUI) {
          this.revertBEAction();
        } else {
          this.revertAction();
        }
      }, err => {
        if (err) {
          throw err;
        }
      });
  }

  canSubmit() {
    return this.candidates && this.candidates.filter(c => c.selected).length > 0;
  }

  onCancel() {
    this.dialog.dismiss();
  }

  revertAction() {
    const toRemove = this.candidates.filter(c => c.selected).map(c => c.id);
    observableOf(...toRemove).pipe(
      mergeMap(id => this.artifactService.getCheckedOutArtifactWithContent(id)),
      take(toRemove.length),
      toArray())
      .toPromise()
      .then(records => this.artifactService.unCheckoutArtifact(toRemove)
        .then(ok => {
          if (ok) {
            const onlyDisplayCheckedOutArtifact = this.settings.latestSettings.onlyDisplayCheckedOutArtifacts;
            // when all checked-out artifacts in the project are reverted,
            // we need to remove the project from view as well based on the user preference
            // if (onlyDisplayCheckedOutArtifact && this.project.checkedOutArtifactIds.length === records.length) {
            //                  this.workspace.removeProjectFromAll(this.project);
            // } else {
            records.forEach(a => {
              //                    this.artifactService.markAsDeleted(a);
              if (/*!onlyDisplayCheckedOutArtifact && */a.checkedOutFromRevision) {
                this.artifactService.getArtifactLatest(a.parentId)
                  .then(res => {
                    if (res.status !== 'DELETED') {
                      this.artifactService.markAsModified(a, res);
                      //                            this.workspace.addArtifactToExplorer(res);
                    }
                  });
              }
            });
            // }
            this.dialog.close(true);
          }
        }));
  }

  revertBEAction() {
    const toRemove = this.candidates.filter(c => c.selected).map(c => c.id);

    this.artifactService.revertArtifact(toRemove)
      .then(ok => {
        if (ok) {
          // TODO  make sure mark as modified is called only once, else it would refresh every single time
          toRemove.forEach(a => {
            this.artifactService.getCheckedOutArtifactWithContent(a)
              .then(res => {
                this.artifactService.markAsModified(res, res);
                this.artifactService.markCheckoutAsRerender(res);
              });
          });
          this.dialog.close(true);
        }
      });
  }
}
