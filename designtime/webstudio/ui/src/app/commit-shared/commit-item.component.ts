
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { timer as observableTimer, Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';

import { environment } from '../../environments/environment';
import { UserService } from '../core/user.service';
import { BECommitCandidate } from '../models-be/commit-candidate-be';
import { Commit, CommitResolution, CommitStatus } from '../models/commit';
import { WorkListEntry } from '../models/workitem';

export const SECONDS_OF_DAY = 60 * 60 * 24;
export const SECONDS_OF_MINUTE = 60;
export const SECONDS_OF_HOUR = 60 * 60;
export const TITLE_LENGTH = 32;

export type CommitItemMode = 'NORMAL' | 'FOR_REVIEW' | 'FOR_CANCEL' | 'FOR_HISTORY';

@Component({
  selector: 'commit-item',
  templateUrl: './commit-item.component.html',
  styleUrls: ['./commit-item.component.css'],
})
export class CommitItemComponent implements OnInit {
  oldMsg: string;
  timeMessage: Observable<string>;
  oldResolvedMsg: string;
  resolvedTimeMessage: Observable<string>;
  changeListMode: string;
  can: {
    approve: boolean,
    reject: boolean,
    withdraw: boolean,
    delegate: boolean,
    delete: boolean
  };

  @Input()
  expanded: boolean;

  @Input()
  commitItem: Commit | WorkListEntry;

  @Input()
  mode: CommitItemMode;

  @Output()
  approvalResult = new EventEmitter<CommitResolution>();

  @Output()
  expandedChange = new EventEmitter<boolean>();

  @Output()
  cancelCommit = new EventEmitter<boolean>();

  @Output()
  delegateCommit = new EventEmitter<boolean>();

  @Output()
  deleteCommit = new EventEmitter<string>();

  constructor(
    private serviceUser: UserService,
    public i18n: I18n
  ) {
    this.can = { approve: false, reject: false, withdraw: false, delegate: false, delete: false };
  }

  get commit() {
    if (this.commitItem.hasOwnProperty('commit')) {
      return (<WorkListEntry>this.commitItem).commit;
    } else {
      return <Commit>this.commitItem;
    }
  }

  ngOnInit() {
    this.timeMessage = observableTimer(0, 1000).pipe(
      map(() => this.produceMessage()),
      filter(curr => curr !== this.oldMsg),
      map(curr => this.oldMsg = curr));

    this.resolvedTimeMessage = Observable.timer(0, 1000)
      .map(() => this.produceResolvedMessage())
      .filter(curr => curr !== this.oldResolvedMsg)
      .map(curr => this.oldResolvedMsg = curr);

    if (this.mode !== 'NORMAL' && this.mode !== 'FOR_HISTORY') {
      if (environment.enableBEUI) {
        this.serviceUser.hasPermission(this.commit.projectName + ':approval')
          .then((canApprove) => this.can.approve = this.can.reject = canApprove);
        this.can.delegate = true;
        this.can.withdraw = false;
        this.can.delete = false;
      } else {
        this.can.withdraw = this.commit.committer.id === this.serviceUser.currentUser().id;
        this.serviceUser.hasPermission('project:approve:' + this.commit.projectName)
          .then((canApprove) => this.can.approve = canApprove);
        this.serviceUser.hasPermission('project:approve:' + this.commit.projectName)
          .then((canReject) => this.can.reject = canReject);
      }
    }

    if (this.mode === 'NORMAL' && (this.commit.status === CommitStatus.APPROVED || this.commit.status === CommitStatus.REJECTED)) {
      if (environment.enableBEUI) {
        this.can.delete = true;
      }
    }
    this.changeListMode = (environment.enableBEUI && this.mode === 'FOR_HISTORY') ? 'HISTORY' : 'REVIEW';
  }

  forReview(): boolean {
    return this.mode === 'FOR_CANCEL';
  }

  forDisplay(): boolean {
    return this.mode === 'NORMAL';
  }

  forApproval(): boolean {
    return this.mode === 'FOR_REVIEW';
  }

  isNotStale() {
    return this.commit.status === CommitStatus.PENDING;
  }

  enableTCEUI() {
    return environment.enableTCEUI;
  }

  get title() {
    if (this.expanded) {
      const suffix = this.commit.version > 0 ? `(${this.commit.version})` : '';
      return `${this.commit.projectName}${suffix}:`;
    } else {
      const suffix = this.commit.version > 0 ? `(${this.commit.version})` : '';
      return `${this.commit.projectName}${suffix}: ` + this.commit.message;
    }
  }

  expand() {
    this.expanded = true;
    this.expandedChange.emit(true);
  }

  toggleExpand() {
    if (this.expanded) {
      this.collapse();
    } else {
      this.expand();
    }
  }

  collapse() {
    this.expanded = false;
  }

  approve(comment?: string) {
    this.approvalResult.emit({ status: true, comment: comment });
  }

  reject(comment?: string) {
    this.approvalResult.emit({ status: false, comment: comment });
  }

  cancel() {
    this.cancelCommit.emit(true);
  }

  delegate() {
    this.delegateCommit.emit(true);
  }

  delete() {
    if (environment.enableBEUI) {
      this.deleteCommit.emit((<BECommitCandidate>this.commit.changeList[0]).revisionId);
    }
  }

  reviewMessage(): string {
    let reviewMsg: string = this.commit.message;

    const cc = this.commit.changeList[0];
    if ('reviewStatus' in cc) {
      const becc: BECommitCandidate = <BECommitCandidate>cc;
      if (becc.reviewStatus !== 'Committed') {
        reviewMsg = (becc.reviewStatus === 'Approve' || becc.reviewStatus === 'BuildAndDeploy') ? 'Approved' : 'Rejected';
        reviewMsg += ' by '
          + becc.reviewerName
          + ' | Comments : '
          + becc.reviewComments;
      }
    }
    return reviewMsg;
  }

  isCommitPending() {
    return this.commit.status === CommitStatus.PENDING;
  }

  commitStatus() {
    return (this.commit.status === CommitStatus.APPROVED) ? 'Approved' : 'Rejected';
  }

  private produceMessage() {
    const c = this.commit;
    const t = c.commitTime;
    return this.formatTime(t);
  }

  private produceResolvedMessage() {
    const c = this.commit;
    const t = c.resolveTime;
    if (t) {
      return this.formatTime(t);
    }
  }

  private formatTime(t: Date) {
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
    switch (key) {
      case 'day':
        return (val > 1 ? this.i18n('{{val}} days ago', { val: val }) : this.i18n('{{val}} day ago', { val: val }));
      case 'hour':
        return (val > 1 ? this.i18n('{{val}} hours ago', { val: val }) : this.i18n('{{val}} hour ago', { val: val }));
      case 'minute':
        return (val > 1 ? this.i18n('{{val}} minutes ago', { val: val }) : this.i18n('{{val}} minute ago', { val: val }));
      case 'second':
        return (val > 1 ? this.i18n('{{val}} seconds ago', { val: val }) : this.i18n('{{val}} second ago', { val: val }));
    }
  }
}
