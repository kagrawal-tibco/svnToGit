
import { Component, Input, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { timer as observableTimer, BehaviorSubject, Observable } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

import { DeploymentService } from './deployment.service';

import { ArtifactService } from '../core/artifact.service';
import { ModalService } from '../core/modal.service';
import { TakeUntilDestroy } from '../core/take.until.destroy';
import { EditorLoaderModal } from '../editors/editor-loader.modal';
import { DeploymentHistoryRecord } from '../models/dto';

@Component({
  selector: 'deployment-info',
  templateUrl: './deployment-info.component.html'
})
export class DeploymentInfoComponent extends TakeUntilDestroy implements OnInit {
  @Input()
  input: DeploymentHistoryRecord;

  @Input()
  artifactId: string;

  private _duration = new BehaviorSubject<string>('');

  constructor(
    private service: DeploymentService,
    private modal: ModalService,
    private artifactService: ArtifactService,
    public i18n: I18n
  ) {
    super();
  }

  ngOnInit() {
    observableTimer(0, 1000).pipe(takeUntil(this.whenDestroyed)).subscribe(() => this._duration.next(this.service.getDuration(this.input)));
  }

  onViewContent() {

    this.artifactService.getArtifactRevisionWithContent(this.artifactId, this.input.deploymentDescriptor.amsDecisionTableVersion)
      .then(
        (success) => {
          this.modal.open(EditorLoaderModal, EditorLoaderModal.default(success, null, `Content: ${success.name}`));
        },
        (fail) => { return fail; }
      ).catch(
        (fail) => fail
      ).then(
        ref => ref
      ).then(
        (never) => { },
        (error) => { if (error) { throw error; } }
      );

    // return Promise.resolve()
    // .then(() => this.artifact.isCheckedOutArtifact ? this.artifact.parentId : this.artifact.id)
    // .then(id => this.artifactService.getArtifactRevisionWithContent(id, this.input.deploymentDescriptor.amsDecisionTableVersion))
    // .then(updated => this.modal.open(EditorLoaderModal,
    // EditorLoaderModal.default(
    // updated,
    // null,
    // `Content: ${updated.name}`)))
    // .then(() => { }, err => {
    // if (err) {
    // throw err;
    // }
    // });
  }

  onCancelDeployment() {
    this.modal.confirm()
      .message(this.i18n('Please confirm you want to cancel the scheduled deployment.'))
      .open().result
      .then(ok => {
        if (ok) {
          this.service.cancelDeployment(this.input.entityId)
            .then(updated => {
              if (updated) {
                Object.assign(this.input, updated);
              }
            });
        }
      }, err => { if (err) { throw err; } });
  }

  get summary() {
    switch (this.input.deployStatus) {
      case 'SUCCESS':
        return this.i18n('Deployment succeeded at {{0}}.', { 0: this.service.getLastUpdateTime(this.input) });
      case 'FAILURE':
        return this.i18n('Deployment failed at {{0}}.', { 0: this.service.getLastUpdateTime(this.input) });
      case 'IN_PROGRESS':
        return this.i18n('Deployment started at {{0}}.', { 0: this.service.getLastUpdateTime(this.input) });
      case 'PENDING':
        return this.i18n('Deployment is still pending.');
      case 'CANCELLED':
        return this.i18n('Deployment was cancelled at {{0}}', { 0: this.service.getLastUpdateTime(this.input) });
      default:
        throw Error(this.i18n('Unable to recognize status: {{err}}', { 0: this.input.deployStatus }));
    }
  }

  get labelStyle() {
    return this.service.getLabelStyle(this.input) + ' callout-lg';
  }

  get summaryStyle() {
    switch (this.input.deployStatus) {
      case 'SUCCESS':
        return 'text-success';
      case 'FAILURE':
        return 'text-danger';
      case 'PENDING':
        return 'text-info';
      case 'IN_PROGRESS':
        return 'text-warning';
      case 'CANCELLED':
        return 'text-default';
      default:
        throw Error(this.i18n('Unable to recognize status: {{err}}', { err: this.input.deployStatus }));
    }
  }

  get indicator() {
    return this.service.getIndicator(this.input);
  }

  get createTime() {
    return this.service.getCreateTime(this.input);
  }

  get targetTime() {
    return this.service.getDeployTime(this.input);
  }

  get lastUpdateTime() {
    return this.service.getLastUpdateTime(this.input);
  }

  get artifactPath() {
    return this.input.deploymentDescriptor.amsDecisionTablePath;
  }

  get revision() {
    return this.service.getRevisionString(this.input.deploymentDescriptor.amsDecisionTableVersion);
  }

  get status() {
    return this.service.getStatusBrief(this.input);
  }

  get duration(): Observable<string> {
    return this._duration;
  }

  get isFailure() {
    return this.input.deployStatus === 'FAILURE';
  }

  get isComplete() {
    return this.input.deployStatus === 'FAILURE' || this.input.deployStatus === 'SUCCESS';
  }

  get isPending() {
    return this.input.deployStatus === 'PENDING';
  }

  get failureReason() {
    return this.input.deployMessage;
  }
}
