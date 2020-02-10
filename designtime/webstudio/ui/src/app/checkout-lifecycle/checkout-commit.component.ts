
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ArtifactStatus } from './../models/artifact';

import { environment } from '../../environments/environment';
import { AlertService } from '../core/alert.service';
import { ArtifactService } from '../core/artifact.service';
import { LifecycleService } from '../core/lifecycle.service';
import { Logger } from '../core/logger.service';
import { Differ } from '../editables/decision-table/differ/differ';
import { CommitCandidate } from '../models/commit-candidate';
import { ProjectMeta } from '../models/project-meta';
import { ResizableModal } from '../shared/resizablemodal';

export class CheckoutCommitContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-lg';
  constructor(
    public project: ProjectMeta,
    public artifactPath?: string,
    public mode?: 'COMMIT' | 'SYNC'
  ) {
    super();
    this.isBlocking = true;
  }
}

@Component({
  templateUrl: './checkout-commit.component.html',
})
export class CheckoutCommitComponent extends ResizableModal implements ModalComponent<CheckoutCommitContext>, OnInit, AfterViewInit {
  project: ProjectMeta;
  artifactPath: string;
  candidates: CommitCandidate[];
  message: string;
  candidatesObs: Observable<CommitCandidate[]>;
  mode: 'COMMIT' | 'SYNC';
  @ViewChild('messageInput', { static: false })
  messageInput: ElementRef;

  constructor(
    public dialog: DialogRef<CheckoutCommitContext>,
    private lifecycleService: LifecycleService,
    private artifactService: ArtifactService,
    private log: Logger,
    private alert: AlertService,
    private differ: Differ,
    public i18n: I18n
  ) {
    super(dialog.context, dialog.context.dialogClass);
    this.project = dialog.context.project;
    this.mode = dialog.context.mode || 'COMMIT';
    this.artifactPath = dialog.context.artifactPath;

    if (!this.project) {
      this.log.err(this.i18n('Project shall not be empty'));
    }
  }

  ngOnInit() {
    if (environment.enableBEUI) {
      this.candidatesObs = this.lifecycleService
        .getCommitCandidates(this.project.projectId, this.artifactPath).pipe(
          map(res => this.candidates = res));
    } else {
      this.candidatesObs = this.lifecycleService
        .getCommitCandidates(this.project.projectId).pipe(
          map(res => this.candidates = res));
    }
  }

  ngAfterViewInit() {
    this.messageInput.nativeElement.focus();
  }

  onSubmit() {
    const ids = this.candidates.filter(c => c.selected).map(c => c.id);
    const domainModelEntries = this.candidates.filter(c => c.selected && c.type.defaultExtension === 'domain');
    if (domainModelEntries.length > 0) {
      this.artifactService.clear();
    }
    this.lifecycleService.commitCheckout(this.project.projectId, ids, this.message)
      .then(ok => {
        const commits = <CommitCandidate[]>this.candidates.filter(c => !c.selected);
        this.dialog.close(commits);
        if (environment.enableBEUI && commits) {
          // for BE UI, we need to update the status of the newly committed artifacts
          // as we do not yet have full server side push/notification support for status changes
          this.lifecycleService.getCommitCandidates(this.project.projectId).toPromise().then(cc => {
            // update the status of the committed artifacts
            this.candidates
              .filter(c => c.selected)
              .filter(selected => !cc.find(c => c.id === selected.id))
              .forEach(toUpdate => {
                if (toUpdate.status !== <ArtifactStatus>'DELETED') {
                  this.artifactService.getCheckedOutArtifactWithContent(toUpdate.id)
                    .then(res => {
                      res.status = <ArtifactStatus>'CLEAN';
                      this.artifactService.markCheckoutAsRerender(res);
                    });
                }
              });
          });
        }
      }, err => this.alert.flash(err, 'warning'));
  }

  canSubmit() {
    return this.message && this.candidates && this.candidates.filter(c => c.selected).length > 0;
  }

  onCancel() {
    this.dialog.dismiss();
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

}
