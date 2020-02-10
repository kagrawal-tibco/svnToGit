import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { timer as observableTimer, Observable } from 'rxjs';

import { CommitItemDeployContext, CommitItemDeployModal } from './commit-item-deploy.modal';

import { environment } from '../../environments/environment';
import { SECONDS_OF_DAY, SECONDS_OF_HOUR, SECONDS_OF_MINUTE } from '../commit-shared/commit-item.component';
import { AlertService } from '../core/alert.service';
import { ArtifactService, SynchronizeStrategy } from '../core/artifact.service';
import { LifecycleService } from '../core/lifecycle.service';
import { ModalService } from '../core/modal.service';
import { RecordService } from '../core/record.service';
import { RestService } from '../core/rest.service';
import { EditorLoaderModal } from '../editors/editor-loader.modal';
import { BECommitCandidate } from '../models-be/commit-candidate-be';
import { Artifact, ArtifactType } from '../models/artifact';
import { CommitCandidate } from '../models/commit-candidate';
import { SynchronizeEditorContext, SynchronizeEditorModal } from '../synchronize-editor/synchronize-editor.modal';

export type ViewerMode = 'REVIEW' | 'COMMIT' | 'REVERT' | 'SYNC' | 'HISTORY' | 'EXTERNAL_SYNC';

@Component({
  selector: 'change-list',
  templateUrl: './change-list.component.html',
})
export class ChangeListComponent implements OnInit {

  /**
   * This will return the CommitCandidates[] once it has resolved.
   */
  get candidates() {
    if (!(this.input instanceof Observable)) {
      return this.input;
    }
  }

  set candidates(cand: CommitCandidate[]) {
    this.input = cand;
  }

  get btnText() {
    if (this.mode === 'SYNC') {
      return 'Synchronize';
    } else {
      return 'View Detail';
    }
  }

  get enableSelect() {
    /**
     * By including this.candidates in the boolean, if this.candidates has not yet been populated,
     * the select all toggle will not be rendered yet. This means that when it is rendered, it will
     * properly display whether or not the current state is with all selected.
     */
    return this.candidates && (this.mode !== 'REVIEW') && (this.mode !== 'HISTORY') && (this.mode !== 'SYNC');
  }

  get isClickable() {
    return this.enableSelect && this.mode !== 'EXTERNAL_SYNC';
  }

  get selectAll() {
    return this.enableSelect
      && this.candidates
      && this.candidates
        .filter(c => this.canSelect(c))
        .map(c => c.selected)
        .reduce((a, b) => a && b, true);
  }

  set selectAll(val) {
    if (this.enableSelect && this.candidates) {
      Promise.resolve().then(() => this.candidates.filter(c => this.canSelect(c)).forEach(c => c.selected = val));
    }
  }
  get enableTCEUI() {
    return environment.enableTCEUI;
  }
  ccToDeployTimeMessage: Map<string, string> = new Map<string, string>();

  @Input()
  input: Observable<CommitCandidate[]> | CommitCandidate[];

  @Input()
  mode: ViewerMode;

  @Input()
  emptyMsg: string;

  @Input()
  title: string;

  deployInProgress: boolean;

  constructor(
    private artifact: ArtifactService,
    private record: RecordService,
    private alert: AlertService,
    private modal: ModalService,
    private matDialog: MatDialog,
    private lifecycleService: LifecycleService,
    private rest: RestService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    if (!this.emptyMsg) {
      this.emptyMsg = this.i18n('No available artifact for review.');
    }
    if (!this.title) {
      this.title = this.i18n('Artifacts');
    }

    if (!this.lookingForCandidates()) {
      if (this.input instanceof Observable) {
        this.input.subscribe(
          (candidates) =>
            this.candidates = (this.mode === 'COMMIT' || this.mode === 'REVERT') ?
              candidates.filter(this.canSelect.bind(this))
                .map(c => {
                  c.selected = true;
                  return c;
                })
              : candidates
        );
      } else {
        this.candidates =
          (this.input && this.mode === 'COMMIT') ?
            this.input
              .filter(this.canSelect.bind(this))
              .map(c => {
                c.selected = true;
                return c;
              })
            : this.input;
      }
    }

    if (environment.enableBEUI) {
      observableTimer(0, 1000)
        .subscribe(t => {
          this.processDeployTimeMessages();
        });
    }
  }

  // if input is null should wait for candidates e.g for external sync
  lookingForCandidates() {
    if (environment.enableBEUI && !this.input) {
      this.waitForCandidates();
      return true;
    } else {
      return false;
    }
  }

  waitForCandidates() {
    if (this.candidates) {
      this.candidates.filter(this.canSelect.bind(this)).map(c => c.selected = c.status !== 'CLEAN');
    } else {
      setTimeout(this.waitForCandidates.bind(this), 250);
    }
  }

  setClasses(c: CommitCandidate) {
    const classes = {
      'commit-candidate': true,
      'list-group-item': true,
      'list-group-item-success': c.status === 'ADDED',
      'list-group-item-warning': c.status === 'MODIFIED',
      'list-group-item-danger': c.status === 'DELETED',
    };
    return classes;
  }

  isValid(c: CommitCandidate) {
    return !c.stale && c.status !== 'CLEAN';
  }

  isStale(c: CommitCandidate): boolean {
    return this.mode === 'SYNC' && c.stale;
  }

  canSelect(c: CommitCandidate) {
    return this.mode === 'EXTERNAL_SYNC' || this.mode === 'REVERT' || (this.mode === 'COMMIT' && this.isValid(c));
  }

  isEntryNormal(c: CommitCandidate) {
    // In review mode, all entries are normal
    // Otherwise, non-selectable entries are shown as disabled.
    return this.mode === 'REVIEW' || this.mode === 'HISTORY' || this.isStale(c) || this.canSelect(c);
  }

  canManage(c): boolean {
    if (environment.enableBEUI) {
      if (c.type === ArtifactType.RULE_TEMPLATE_INSTANCE || c.type === ArtifactType.BE_DECISION_TABLE) {
        return true;
      } else if (c.type === ArtifactType.DOMAIN_MODEL) {
        if (this.mode === 'SYNC') {
          this.modal.confirm()
            .message(this.i18n('This artifact\'s type does not support synchronization. \
                Would you like to discard you local changes and replace the local artifact with the latest revision from the server?'))
            .open().result
            .then(() => this.onSync(c),
              () => { /* noop */ });
        } else {
          this.alert.flash(this.i18n('ViewDiff is supported for Decision table and Business rule only.'), 'warning', true);
        }
        return false;
      } else {
        // this.alert.flash('ViewDiff is supported for Decision table and Business rule only.', 'warning', true);
        return false;
      }
    } else {
      return true;
    }
  }

  onViewDiff(c: CommitCandidate) {
    return Promise.resolve()
      .then(() => {
        return this.artifact.fetchVersionsForDiff(c);
      })
      .then(diffEntries => {
        if (diffEntries) {
          const lhs: Artifact = diffEntries[0];
          const rhs: Artifact = diffEntries[1];
          let ctx;
          switch (rhs.type) {
            case ArtifactType.RULE_TEMPLATE_INSTANCE:
              const artifactJsonObject = JSON.parse(rhs.content);
              if (artifactJsonObject.view != null
                && artifactJsonObject.view.bindingInfo != null
                && artifactJsonObject.view.bindingInfo.length > 0) {
                ctx = EditorLoaderModal.ruleTemplateInstanceView(rhs, lhs,
                  lhs ? this.i18n('Changes to Artifact ') + rhs.path : this.i18n('New Artifact ') + rhs.path);
              } else {
                ctx = EditorLoaderModal.ruleTemplateInstanceBuilder(rhs, lhs,
                  lhs ? this.i18n('Changes to Artifact ') + rhs.path : this.i18n('New Artifact ') + rhs.path);
              }
              break;
            case ArtifactType.DOMAIN_MODEL:
              ctx = EditorLoaderModal.domainModel(rhs, lhs, lhs ? this.i18n('Changes to Artifact ') + rhs.path : this.i18n('New Artifact ') + rhs.path);
              break;
            case ArtifactType.BE_DECISION_TABLE:
              ctx = EditorLoaderModal.beDecisionTable(rhs, lhs, lhs ? this.i18n('Changes to Artifact ') + rhs.path : this.i18n('New Artifact ') + rhs.path);
              break;
            default:
              ctx = EditorLoaderModal.default(rhs, lhs, lhs ? this.i18n('Changes to Artifact ') + rhs.path : this.i18n('New Artifact ') + rhs.path);
          }
          this.modal.open(EditorLoaderModal, ctx);
        }
      });
  }

  onClick(c: CommitCandidate) {
    if (this.isEntryNormal(c) && this.canManage(c)) {
      if (this.mode === 'SYNC') {
        if (c.type.supportSync) {
          this.onSync(c);
        } else {
          this.artifact.fetchVersionsForSync(c)
            .then(
              (artifacts) =>
                this.modal.confirm()
                  .message(this.i18n('This artifact\'s type does not support synchronization. \
                Would you like to discard your local changes and replace the local artifact with the latest revision from the server?'))
                  .okBtn('Replace')
                  .showClose(true)
                  .open().result
                  .then(
                    confirmed => this.artifact.getArtifactLatest(c.parentId)
                  ).then(
                    parent => this.artifact.synchronize(artifacts[2], artifacts[0], SynchronizeStrategy.LATEST, undefined)
                  ).then(
                    updated => {
                      c.stale = updated.stale;
                      c.status = updated.status;
                      c.disposed = updated.disposed;
                    }
                  )
            ).catch(
              error => {
                if (error) {
                  this.alert.flash(error.message, 'error', true);
                }
              }
            );
        }
      } else {
        if (c.status !== 'DELETED') {
          this.onViewDiff(c);
        } else {
          this.alert.flash(this.i18n('The commit candidate has been deleted.'), 'warning');
        }
      }
    }
  }

  onSync(c: CommitCandidate) {
    if (c.committed) {
      throw Error(this.i18n('Something is wrong. Cannot sync commit candidate which is in committed status'));
    }

    if ((c.status === 'ADDED' ||
      c.status === 'CLEAN' ||
      c.status === 'DELETED' ||
      (c.type === ArtifactType.DOMAIN_MODEL && c.status === 'MODIFIED')) &&
      environment.enableBEUI) {
      const artifact: Artifact = this.record.commitCandidateToArtifact(c);

      this.artifact.synchronize(undefined, artifact, SynchronizeStrategy.LATEST, undefined)
        .then(res => {
          c.stale = false;
          c.status = artifact.status;
          c.disposed = artifact.disposed;
        });

    } else {
      Promise.resolve()
        .then(() => {
          return this.artifact.fetchVersionsForSync(c)
            .then(mergeEntries => {
              const rhs: Artifact = mergeEntries[0];
              const base: Artifact = mergeEntries[1];
              const lhs: Artifact = mergeEntries[2];

              // if the server content is missing, set it back to the base content
              if (lhs.content === '{}') { lhs.content = base.content; }

              return this.modal.open(SynchronizeEditorModal, new SynchronizeEditorContext(base, lhs, rhs));
            });
        })
        .then((updated: Artifact) => {
          if (updated) {
            c.stale = false;
            c.status = updated.status;
            c.disposed = updated.disposed;
          }
        }, () => { });
    }
  }

  getDescription(c: CommitCandidate) {
    let suffix: string;
    if (this.mode !== 'SYNC' && c.stale) {
      suffix = ', stale';
    } else {
      suffix = '';
    }
    if (c.status === 'CLEAN') {
      return `${c.path} (no local changes${suffix})`;
    } else {
      return `${c.path} (${c.status.toLowerCase()}${suffix})`;
    }
  }

  isBtnEnabled(c: CommitCandidate) {
    return this.mode !== 'SYNC' || c.stale;
  }

  onSelect(c: CommitCandidate) {
    if (this.canSelect(c)) {
      c.selected = !c.selected;
    }
  }

  showDeployOptions(c: CommitCandidate) {
    return environment.enableBEUI
      && this.mode !== 'HISTORY'
      && (c.type === ArtifactType.RULE_TEMPLATE_INSTANCE || c.type === ArtifactType.BE_DECISION_TABLE)
      && ('reviewStatus' in c)
      && ((<BECommitCandidate>c).reviewStatus === 'Approve' || (<BECommitCandidate>c).reviewStatus === 'BuildAndDeploy');
  }

  inlineDeploy(c: CommitCandidate, comment: string) {
    this.deployInProgress = true;

    comment = 'Deployed';
    const cc = (<BECommitCandidate>c);
    const clonecc = Object.assign({}, cc);
    if (clonecc) {
      clonecc.deployComments = comment;
    }
    this.lifecycleService.deployArtifact(clonecc)
      .then(res => {
        if (res.status) {
          cc.reviewStatus = 'BuildAndDeploy';
          cc.lastDeployTime = (new Date()).getTime();
          cc.deployerName = this.rest.displayName;
          cc.deployComments = comment;
          this.alert.flash('Artifact [' + cc.path + '] successfully deployed', 'success');
        } else {
          if (res.errorCnt || res.errorCnt) {
            this.alert.flash(`There are ${res.errorCnt} error(s) and ${res.warningCnt} warning(s) found. Open the artifact and run validate for more information.`, 'error', true, -1);
          }
        }
        this.deployInProgress = false;
      });
  }
  onDeploy(c: CommitCandidate, comment: string) {
    if (environment.enableTCEUI) {
      this.inlineDeploy(c, comment);
      return;
    }
    if (!(<BECommitCandidate>c).applicableEnvironments) {
      this.alert.flash(this.i18n('No deployment environment configured for this project.'), 'error', true);
    } else {
      const dialogRef = this.matDialog.open(CommitItemDeployModal, {
        width: '650px',
        height: '50vh',
        data: <CommitItemDeployContext>{
          cc: c
        }
      });
      // this.modal.open(CommitItemDeployModal, new CommitItemDeployContext(<BECommitCandidate>c));
    }
  }

  showLastDeployMessage(c: CommitCandidate) {
    return environment.enableBEUI
      && this.mode !== 'HISTORY'
      && (c.type === ArtifactType.RULE_TEMPLATE_INSTANCE || c.type === ArtifactType.BE_DECISION_TABLE)
      && ('reviewStatus' in c)
      && ((<BECommitCandidate>c).reviewStatus === 'BuildAndDeploy');
  }

  isDeployInProgress() {
    return this.deployInProgress;
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

  private processDeployTimeMessages() {
    if (this.candidates) {
      this.candidates.forEach(cc => {
        this.ccToDeployTimeMessage.set(cc.id, this.produceMessage((<BECommitCandidate>cc).lastDeployTime));
      });
    }
  }

  private produceMessage(deployTime: number) {
    const t = new Date(deployTime);
    const now = new Date();
    const elapsed = (now.getTime() - t.getTime()) / 1000;
    if (elapsed > SECONDS_OF_DAY * 10) {
      return `on ${t.toDateString()}`;
    } else if (elapsed >= SECONDS_OF_DAY) {
      const days = elapsed / SECONDS_OF_DAY;
      return this.formatMessage('day', days);
    } else if (elapsed >= SECONDS_OF_HOUR) {
      const hours = elapsed / SECONDS_OF_HOUR;
      return this.formatMessage('hour', hours);
    } else if (elapsed >= SECONDS_OF_MINUTE) {
      const minutes = elapsed / SECONDS_OF_MINUTE;
      return this.formatMessage('minute', minutes);
    } else {
      return this.formatMessage('second', elapsed);
    }
  }

  private formatMessage(key: string, val: number) {
    val = Math.floor(val);
    return `${val} ${key}${val > 1 ? 's' : ''} ago`;
  }

}
