
import { animate, keyframes, state, style, transition, trigger } from '@angular/animations';
import { DatePipe } from '@angular/common';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from 'environments/environment';
import { Observable, Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';

import { AuditTrail } from 'app/audit-trail/audit-trail';
import { CommitDelegateContext, CommitDelegateModal } from 'app/commit-shared/commit-delegate.modal';
import { ModalService } from 'app/core/modal.service';
import { RestService } from 'app/core/rest.service';
import { Activity, MultiActivity } from 'app/models/activity';
import { CommitResolution, CommitStatus } from 'app/models/commit';
import { WorkListEntry, WorkListEntryStatus } from 'app/models/workitem';

import { BECommitCandidate } from './../../models-be/commit-candidate-be';
import { Commit } from './../../models/commit';
import { ActivityStreamFilter } from './activity-stream-filter.component';

import { ActionType } from '../../audit-trail/audit-trail.component';
import { AuditTrailService } from '../../audit-trail/audit-trail.service';
import { AlertService, AlertType } from '../../core/alert.service';
import { LifecycleService } from '../../core/lifecycle.service';
import { Notification } from '../../models/notification';
import { NotificationCenterService, NotifyChange } from '../../notification-center/notification-center.service';
import { ActivityDelta, CommitDelta, DashboardService, ProjectSnapshotDelta, WorkListDelta } from '../dashboard.service';
export enum Status {
  EMPTY,
  UPDATING,
  UPDATED
}

@Component({
  selector: 'activity-stream',
  templateUrl: './activity-stream.component.html',
  styleUrls: ['./activity-stream.component.css'],
  animations: [
    trigger('filtered', [
      state('filtered', style({
        opacity: '0',
        overflow: 'hidden',
        height: '0px',
        'margin-bottom': '0px',
        padding: '0px',
      })),
      state('unfiltered', style({
        height: '*',
        overflow: 'visible',
        padding: '0px',
        'margin-bottom': '20px',
      })),
      transition(':enter', [
        animate('.5s', keyframes([
          style({
            visibility: 'visible',
            opacity: 0,
            transform: 'translateX(100%)',
            offset: 0
          }),
          style({
            transform: 'translateX(-25px)',
            opacity: 1,
            offset: .30
          }),
          style({
            transform: 'translateX(0)',
            offset: 1
          })
        ]))
      ]),
      transition(':leave', [
        animate('.3s ease-out', keyframes([
          style({
            transform: 'translateX(0)',
            offset: 0
          }),
          style({
            opacity: 1,
            transform: 'translateX(25px)',
            offset: .70
          }),
          style({
            opacity: 0,
            transform: 'translateX(-100%)',
            offset: 1
          })
        ]))
      ]),
      transition('filtered => unfiltered', [
        animate('.5s', keyframes([
          style({
            visibility: 'visible',
            overflow: 'visible',
            opacity: .5,
            transform: 'translateX(100%)',
            offset: 0,
            height: '0px'
          }),
          style({
            transform: 'translateX(-25px)',
            opacity: 1,
            offset: .30,
            height: '*'
          }),
          style({
            transform: 'translateX(0)',
            offset: 1,
            height: '*'
          })
        ]))
      ]),
      transition('unfiltered => filtered', [
        animate('.3s ease-out', keyframes([
          style({
            transform: 'translateX(0)',
            offset: 0
          }),
          style({
            opacity: .5,
            transform: 'translateX(25px)',
            offset: .70
          }),
          style({
            opacity: 0,
            transform: 'translateX(-100%)',
            offset: 1,
            height: '0px'
          })
        ]))])
    ]),
  ]
})
export class ActivityStreamComponent implements OnInit, OnDestroy {

  public collapsed = true;

  activityFilter: ActivityStreamFilter;

  lastUpdate: Date;
  today = new Date();
  showActive: boolean;
  activitySubcription: Subscription;
  notificationSubcription: Subscription;

  // scrolling constants
  throttle = 300;
  scrollDistance = 1;
  displayCount = 0;

  private display_activities: Activity[];
  private activities: Activity[];
  private status = Status.EMPTY;
  private updating = false; // waiting for the audit trail call to return

  constructor(private _notificationService: NotificationCenterService,
    private _alertService: AlertService,
    private _auditTrailService: AuditTrailService,
    private _lifecycleService: LifecycleService,
    private _dashboardService: DashboardService,
    private _modalService: ModalService,
    private _rest: RestService,
    private _matDialog: MatDialog,
    private _datePipe: DatePipe,
    public i18n: I18n) { }

  ngOnInit() {
    this.activityFilter = this.createFilter();

    this.activitySubcription = this._dashboardService.activityChanges()
      .subscribe(change => {
        setTimeout(() => {
          this.processChanges(change);
        }, 0);
      });
    this.notificationSubcription = this._notificationService.notify()
      .subscribe(change => {
        setTimeout(() => {
          this.processNotify(change);
        }, 0);
      });
  }

  ngOnDestroy() {
    if (this.activitySubcription) {
      this.activitySubcription.unsubscribe();
    }
  }

  createFilter(): ActivityStreamFilter {
    // possibly extract to Preference page
    return new ActivityStreamFilter();
  }

  refreshAll() {
    this.display_activities = [];
    this.activities = [];
    this.status = Status.EMPTY;
  }

  enableTCEUI() {
    return environment.enableTCEUI;
  }

  get allActivity(): Activity[] {
    this.today = new Date();
    if (this.status === Status.UPDATED || this.status === Status.UPDATING) {
      this.applyFilter(this.display_activities);
      return this.display_activities;
    }
    if ((!this.activities || this.activities.length === 0) && this.status === Status.EMPTY) {
      this.status = Status.UPDATING;
      this.activities = [];
      this.display_activities = [];
      this._dashboardService.updateAll();
      if (!environment.enableBEUI) {
        this._notificationService.getNotifications().forEach(item => {
          const activity: Activity = <Activity>{
            id: item.id,
            requiresAction: true,
            details: item.content,
            link: ['/dashboard'],
            timestamp: Number.parseInt(item.timestamp),
            title: item.title,
            icon: 'fa fa-list',
            severity: 'info',
            type: 'AUDIT',
            data: item
          };
          this.activities.push(activity);
        });
      }
      if (this._dashboardService.worklist) {
        this._dashboardService.worklist.forEach(item => {
          this.insertWorklistEntry(item);
        });
      }
      if (this._dashboardService.commits) {
        this._dashboardService.commits.forEach(item => {
          this.insertWorklistEntry(<WorkListEntry>{ id: item.id, commit: item });
        });
      }
      if (environment.enableBEUI) {
        this.updateAuditTrail().then((result) => {
          this.addItems();
          this.status = Status.UPDATED;
        }).catch((err) => {
          this.status = Status.UPDATED;
        });
      } else {
        this.addItems();
        this.status = Status.UPDATED;
      }
    }
    this.applyFilter(this.display_activities);
    return this.display_activities;
  }

  testFilter(act: Activity) {
    if (act.type === 'COMMIT' && this.showActive) {
      const cmt = act.data as WorkListEntry;
      if (cmt.commit.changeList) {
        cmt.commit.changeList.forEach(c => {
          if (environment.enableBEUI || environment.enableTCEUI) {
            if ((<BECommitCandidate>c).reviewStatus === 'BuildAndDeploy' ||
              (<BECommitCandidate>c).applicableStages === 'BuildAndDeploy' ||
              (<BECommitCandidate>c).applicableStages === 'Approve,Reject') {
              act.state = this.activityFilter.test(act) ? 'unfiltered' : 'filtered';
              // act.state = 'unfiltered';
            } else {
              act.state = 'filtered';
            }
          }
        });
      }
    } else if (this.showActive && !act.requiresAction) {
      act.state = 'filtered';
    } else {
      act.state = this.activityFilter.test(act) ? 'unfiltered' : 'filtered';
    }
  }

  applyFilter(activities: Activity[]) {
    if (!activities) {
      return;
    }
    let count = 0;
    const visible = [];
    for (let i = 0; i < this.activities.length; ++i) {
      const act = this.activities[i];
      this.testFilter(act);
      if (act.state === 'unfiltered') {
        count++;
        visible.push(act);
        if (count >= this.displayCount) {
          this.display_activities = visible;
          return;
        }
      }
    }
    this.display_activities = visible;
    if (count < this.displayCount) {
      // reset display count to avoid full repopulation
      this.displayCount = count + 5; // give it a 5 item buffer to avoid empty stream
    }
    return;
  }

  processNotify(change: NotifyChange) {
    if (change.notification) {
      const item = change.notification;
      const activity: Activity = <Activity>{
        id: item.id,
        requiresAction: true,
        details: item.content,
        link: ['/dashboard'],
        timestamp: Number.parseInt(item.timestamp),
        title: item.title,
        icon: 'fa fa-list',
        severity: 'info',
        type: 'AUDIT',
        data: item
      };
      const idx = this.activities.findIndex(arrItem => {
        return arrItem.timestamp < activity.timestamp;
      });
      this.activities.splice(idx, 0, activity);
    }
  }

  processChanges(change: ActivityDelta) {
    this.status = Status.UPDATING;
    if (change.worklistDelta) {

    }
    if (change.commitDelta) {
      const delta = change.commitDelta;
      if (delta.changed) {
        const entries = delta.changed.map(c => <WorkListEntry>{ id: c.id, commit: c });
        entries.forEach(entry => {
          this.insertWorklistEntry(entry);
        });
      }
      if (delta.removed) {
        const entries = delta.removed.map(c => <WorkListEntry>{ id: c.id, commit: c });
        entries.forEach(entry => {
          this.removeItem(entry);
        });
      }
      if (delta.added) {
        const entries = delta.added.map(c => <WorkListEntry>{ id: c.id, commit: c });
        entries.forEach(entry => {
          this.insertWorklistEntry(entry);
        });
      }
    }
    if (change.worklistDelta) {
      const delta = change.worklistDelta;
      if (delta.changed) {
        delta.changed.forEach(entry => {
          this.insertWorklistEntry(entry);
        });
      }
      if (delta.removed) {
        delta.removed.forEach(entry => {
          this.removeItem(entry);
        });
      }
      if (delta.added) {
        delta.added.forEach(entry => {
          this.insertWorklistEntry(entry);
        });
      }
    }
    if (environment.enableBEUI) {
      this.updateAuditTrail().then((result) => {
        this.status = Status.UPDATED;
      }).catch((err) => {
        this.status = Status.UPDATED;
      });
    } else {
      this.status = Status.UPDATED;
    }
    // this.status = Status.UPDATED;
  }

  toggleShowActive() {
    this.status = Status.EMPTY;
    this.showActive = !this.showActive;
    this.applyFilter(this.display_activities);
  }

  getInsertionIndex(arr: Activity[], activity: Activity) {
    if (arr.length === 0) {
      return 0;
    }
    return arr.findIndex(arrItem => {
      return arrItem.timestamp < activity.timestamp;
    });
  }

  insertWorklistEntry(item: WorkListEntry): any {
    const title = this.getTitle(item);
    const activity: Activity = <Activity>{
      id: item.id,
      userName: item.commit.committer.userName,
      projectName: item.commit.projectName,
      requiresAction: item.commit.status === CommitStatus.PENDING,
      details: item.commit.message,
      link: ['/dashboard'],
      timestamp: item.commit.commitTime.getTime(),
      title: title,
      icon: 'fa fa-list',
      severity: 'warning',
      type: 'COMMIT',
      data: item
    };
    this.insertActivity(activity);
  }

  getTitle(item: WorkListEntry) {
    if (item.status === WorkListEntryStatus.UNRESOLVED) {
      return this.i18n('Pending review');
    }
    if (item.commit.status === CommitStatus.PENDING) {
      return this.i18n('Pending review');
    }
    if (item.commit.resolver) {
      return `${item.commit.status.name} [by ${item.commit.resolver.userName}]`;
      // at ${this._datePipe.transform(item.commit.resolveTime)}`;
    }

    return undefined;
  }

  insertActivity(activity: Activity): any {
    if (this.activities.length > 20) {
      // return;
    }
    if (activity !== undefined) {
      if (activity.details && activity.subType === ActionType.CREATE.value
        && activity.details.startsWith('Group') && activity.details.endsWith('Created')) {
        const groupname = activity.details.split('Group')[1].split('Created')[0];
        activity.details = this.i18n('Group{{0}}Created', { 0: groupname });
      }
    }
    let curr = this.activities.find(act => act.id === activity.id);
    let idx = 0;
    if (curr) {
      // found an exact match, replace the existing entry
      idx = this.activities.indexOf(curr);
      if (idx < 0) {
        this.activities.push(activity);
      } else {
        // check whether any attributes have changed.  If not, no need to replace
        if (curr.requiresAction !== activity.requiresAction
          || curr.severity !== activity.severity
          || curr.link !== activity.link
          || curr.details !== activity.details
          || curr.data !== activity.data) {
          this.activities.splice(idx, 1, activity);
        } else {
          // this.activity.push(activity);
        }
      }
      return;
    } else if (activity.type === 'AUDIT') {
      // look for a 'category' match to group similar items
      curr = this.activities.find(act => {
        return act.projectName === activity.projectName
          && act.userName === activity.userName
          && act.subType === activity.subType
          && (act.timestamp / 1000).toFixed(0) === (activity.timestamp / 1000).toFixed(0);
      });
      if (curr instanceof MultiActivity) {
        // replace the existing one
        const existing = curr.activities.find(act => act.id === activity.id);
        if (existing) {
          if (existing.requiresAction !== activity.requiresAction
            || existing.severity !== activity.severity
            || existing.link !== activity.link
            || existing.details !== activity.details
            || existing.data !== activity.data) {
            curr.activities.splice(idx, 1, activity);
          }
        } else {
          curr.activities.push(activity);
        }
        return;
      } else if (curr) {
        const mult = new MultiActivity();
        // mult.id = activity.id;
        mult.projectName = activity.projectName;
        mult.userName = activity.userName;
        mult.type = activity.type;
        mult.subType = activity.subType;
        mult.timestamp = activity.timestamp;
        mult.icon = activity.icon;
        mult.severity = activity.severity;
        mult.summary = activity.summary;
        mult.userName = activity.userName;
        mult.userImg = activity.userImg;
        mult.activities = [];
        mult.activities.push(curr);
        mult.activities.push(activity);
        idx = this.activities.indexOf(curr);
        // let idx = this.getInsertionIndex(this.activity, mult);
        if (idx < 0) {
          this.activities.push(mult);
        } else {
          this.activities.splice(idx, 1, mult);
        }
        return;
      }
    }
    idx = this.getInsertionIndex(this.activities, activity);
    if (idx < 0) {
      this.activities.push(activity);
    } else {
      this.activities.splice(idx, 0, activity);
    }
  }

  insertAuditTrailEntry(entry: AuditTrail) {
    const info = this.getActionInfo(entry);
    const activity: Activity = <Activity>{
      type: 'AUDIT',
      subType: entry.actionType,
      projectName: entry.projectName,
      id: entry.userName + '$' + entry.actionType + '$' + entry.artifactPath + '$' + entry.actionTime,
      userName: entry.userName,
      requiresAction: false,
      details: info.message,
      timestamp: entry.actionTime,
      icon: info.icon,
      userImg: 'fa fa-user-circle-o',
      severity: info.level,
      summary: info.summary,
      // data: entry
    };
    this.insertActivity(activity);
  }

  updateAuditTrail(): Promise<boolean> {
    if (this.updating) {
      return Promise.resolve(true);
    }
    this.updating = true;
    const last = this.lastUpdate ? this.lastUpdate.valueOf() : undefined;
    return new Promise((resolve, reject) => {
      this._auditTrailService.getAuditTrailDelta(null, last)
        .then(auditTrail => {
          this.lastUpdate = new Date();
          if (auditTrail) {
            auditTrail.map(entry => {
              this.insertAuditTrailEntry(entry);
            });
          }
          this.updating = false;
          resolve(true);
        }, err => {
          console.log('unable to retrieve audit trail ' + err);
          this.updating = false;
          resolve(false);
          // throw err;
        });
    });
  }

  getActionInfo(entry: AuditTrail): any {
    const type = ActionType.getByValue(entry.actionType);
    switch (type) {
      case ActionType.LOGIN:
        return {
          icon: 'fa fa-sign-in',
          level: 'info',
          message: this.i18n('Logged in')
        };
      case ActionType.CREATE:
        return {
          icon: 'fa fa-plus-circle',
          level: 'info',
          message: !entry.artifactPath && entry.comment ? entry.comment : this.i18n('Created artifact {{artifactPath}}', { artifactPath: entry.artifactPath })
        };
      case ActionType.MODIFY:
        return {
          icon: 'fa fa-edit',
          level: 'info',
          message: this.i18n('Modified {{artifactPath}}', { artifactPath: entry.artifactPath })
        };
      case ActionType.COMMIT:
        return {
          icon: 'fa fa-share-alt-square',
          level: 'info',
          message: this.i18n('Committed {{artifactPath}}', { artifactPath: entry.artifactPath })
        };
      case ActionType.CHECKOUT:
        return {
          icon: 'fa fa-cloud-download',
          level: 'info',
          summary: this.i18n('Checked out artifacts in {{name}}', { name: entry.projectName }),
          message: /*this.i18n(' checked out ') + */entry.artifactPath
        };
      case ActionType.APPROVE:
        return {
          icon: 'fa fa-thumbs-up',
          level: 'info',
          message: !entry.comment ? this.i18n('Approved {{artifactPath}}', { artifactPath: entry.artifactPath })
            : this.i18n('Approved {{artifactPath}} with the comment {{comment}}', { artifactPath: entry.artifactPath, comment: entry.comment })
        };
      case ActionType.REJECT:
        return {
          icon: 'fa fa-thumbs-down',
          level: 'info',
          message: !entry.comment ? this.i18n('Rejected {{artifactPath}}', { artifactPath: entry.artifactPath })
            : this.i18n('Rejected {{artifactPath}} with the comment {{comment}}', { artifactPath: entry.artifactPath, comment: entry.comment })
        };
      case ActionType.VALIDATE:
        return {
          icon: 'fa fa-check-circle',
          level: 'info',
          message: entry.artifactPath ? this.i18n('Validated artifact  {{artifactPath}}', { artifactPath: entry.artifactPath }) :
            this.i18n('Validated project {{project}}', { project: entry.projectName })
        };
      case ActionType.REVERT:
        return {
          icon: 'fa fa-undo',
          level: 'info',
          message: this.i18n('Reverted artifact {{artifactPath}}', { artifactPath: entry.artifactPath })
        };
      case ActionType.DELETE:
        return {
          icon: 'fa fa-times-circle',
          level: 'info',
          message: entry.artifactPath ? this.i18n('Deleted artifact  {{artifactPath}}', { artifactPath: entry.artifactPath }) :
            entry.projectName ? this.i18n('Deleted project ') + ' ' + entry.projectName : entry.comment
        };
      case ActionType.SYNC:
        return {
          icon: 'fa fa-codepen',
          level: 'info',
          message: this.i18n('Synchronized {{project}}', { project: entry.projectName })
        };
      case ActionType.LOGOUT:
        return {
          icon: 'fa fa-sign-out',
          level: 'info',
          message: this.i18n('Logged out')
        };
      case ActionType.BUILDANDDEPLOY:
        return {
          icon: 'fa fa-building',
          level: 'info',
          message: this.i18n('Built and Deployed {{projectName}}', { projectName: entry.projectName })
        };
      case ActionType.GENERATEDEPLOYABLE:
        return {
          icon: 'fa fa-archive',
          level: 'info',
          summary: this.i18n('Generated deployable for {{projectName}}', { projectName: entry.projectName }),
          message: entry.comment ? entry.comment : ''
        };
      case ActionType.EXTERNALSYNC:
        return {
          icon: 'fa fa-download',
          level: 'info',
          message: entry.artifactPath ? ` synchronized '${entry.artifactPath}' externally` :
            ` synchronized '${entry.projectName}' externally`
        };
      case ActionType.GROUP:
        return {
          icon: 'fa fa-briefcase',
          level: 'info',
          message: this.i18n('Created group {{path}}', { path: entry.artifactPath })
        };
      case ActionType.PREFERENCES:
        return {
          icon: 'fa fa-user-o',
          level: 'info',
          message: this.i18n('Modified user settings')
        };
      case ActionType.DEPLOY_CONFIG:
        return {
          icon: 'fa fa-cog',
          level: 'info',
          message: this.i18n('Changed deployment configuration(s)')
        };
      case ActionType.DELEGATE:
        return {
          icon: 'fa fa-arrow-circle-right',
          level: 'info',
          message: this.i18n('Delegated a worklist item')
        };
      case ActionType.LOCK:
        return {
          icon: 'fa fa-lock',
          level: 'info',
          message: this.i18n('Locked artifact {{path}}', { path: entry.artifactPath })
        };
      case ActionType.UNLOCK:
        return {
          icon: 'fa fa-unlock',
          level: 'info',
          message: this.i18n('Unlocked artifact {{path}}', { path: entry.artifactPath })
        };
      case ActionType.ACL:
        return {
          icon: 'fa fa-user-secret',
          level: 'info',
          message: this.i18n('Modified security settings')
        };
      case ActionType.USER:
        return {
          icon: 'fa fa-user-circle-o',
          level: 'info',
          message: entry.comment ? entry.comment : this.i18n('User information has been modified')
        };
      case ActionType.IMPORT:
        const artifact = (entry.artifactPath) ? entry.artifactPath : entry.projectName;
        return {
          icon: 'fa fa-download',
          level: 'info',
          message: this.i18n('Imported {{artifact}}', { artifact: artifact })
        };
      case ActionType.EXPORT:
        return {
          icon: 'fa fa-share-square-o',
          level: 'info',
          message: this.i18n('Exported {{path}}', { path: entry.artifactPath })
        };
      case ActionType.WORKLIST:
        return {
          icon: 'fa fa-list',
          level: 'info',
          message: entry.comment ? ` ${entry.comment} in project ${entry.projectName}` : ` accessed their worklist`
        };
      case ActionType.RENAME:
        return {
          icon: 'fa fa-rename',
          level: 'info',
          message: this.i18n('Renamed artifact {{path}}', { path: entry.artifactPath })
        };

      default:
        break;
    }
    return '';
  }

  openDetails(notification: Notification) {
    let severity = 'info' as AlertType;
    switch (notification.icon) {
      case 'danger':
        severity = 'error';
        break;
      case 'success':
        severity = 'success';
        break;
      case 'warning':
        severity = 'warning';
        break;
    }
    this._alertService.flashDetail(notification, severity, true, -1);
  }

  getMode(c: WorkListEntry): string {
    if (c.commit.status === CommitStatus.PENDING) {
      return undefined;
    }
    return 'NORMAL';
  }

  onApprove(c: WorkListEntry, comment?: string): Promise<boolean> {
    return this._lifecycleService.approveWorkItem(c, comment);
  }

  onReject(c: WorkListEntry, comment?: string): Promise<boolean> {
    return this._lifecycleService.rejectWorkItem(c, comment);
  }

  onResolve(c: WorkListEntry, resolutionEvent: CommitResolution) {
    let result: Promise<boolean>;
    if (resolutionEvent.status) {
      result = this.onApprove(c, resolutionEvent.comment);
    } else {
      result = this.onReject(c, resolutionEvent.comment);
    }
    result.then(ok => {
      if (ok) {
        // no need to remove - it will be removed upon dashboard
        // service notification
        // this.removing = c;
        // setTimeout(() => {
        //   this.removeItem(c);
        //   // this.removing = null;
        // }, 350);
        this._dashboardService.updateAll();
      }
    }, err => this._alertService.flash(err, 'warning'));
  }

  onWithdraw(c: WorkListEntry) {
    this._modalService
      .confirm()
      .message(this.i18n('Please confirm you want to withdraw the commit.'))
      .okBtn('Confirm')
      .cancelBtn('Cancel')
      .open().result
      .then(() => this._lifecycleService.withdrawCommit(c.commit.id), () => false)
      .then(ok => {
        if (ok) {
          this.removeCommit(c);
        }
      }, err => this._alertService.flash(err, 'warning'));
  }

  onDelegate(c: WorkListEntry) {
    const ref = this._matDialog.open(CommitDelegateModal, {
      width: '400px',
      height: '40vh',
      data: <CommitDelegateContext>{
        cc: c.commit
      }
    });
    ref.afterClosed().subscribe(ok => {
      if (ok) {
        this.removeCommit(c);
      }
    }, err => this._alertService.flash(err, 'warning'));
    // this._modalService.open(CommitDelegateModal, new CommitDelegateContext(c.commit))
    //   .then(ok => {
    //     if (ok) {
    //       this.removeCommit(c);
    //     }
    //   }, err => this._alertService.flash(err, 'warning'));
  }

  onDelete(c: WorkListEntry, revisionId: string) {
    this._modalService
      .confirm()
      .message(this.i18n('Do you want to delete entry with revisionId ') + revisionId + '?')
      .open().result
      .then(
        () => {
          const payload = {
            request: {
              data: {
                worklist: {
                  revisions: {
                    revisionId: [revisionId]
                  }
                }
              }
            }
          };
          this._rest.delete(`worklist/delete.json`, payload, undefined, true).pipe(
            map(response => response.ok()))
            .toPromise()
            .then((ok) => {
              if (ok) {
                this.removeCommit(c);
              }
            });
        },
        () => { }
      );
  }

  removeItem(c: WorkListEntry) {
    const current = this.activities.find(act => act.id === c.id);
    if (current && current.data) {
      // check to see if the worklist status is the same
      // if not, then assume that this item has already
      // been replaced by a newer activity
      if (current.data && c.status !== current.data.status) {
        return;
      }
    }
    const idx = this.activities.findIndex(act => act.id === c.id);
    if (idx !== -1) {
      this.activities.splice(idx, 1);
    }
  }

  removeCommit(c: WorkListEntry) {
    this.removeItem(c);
    // let idx = this.input.indexOf(c);
    // if (idx !== -1) {
    //   this.removing = c;
    //   setTimeout(() => {
    //     this.input.splice(idx, 1);
    //     this.removing = null;
    //   }, 350);
    // }
  }

  searchChanged(filter: string) {
    if (filter) {
      filter = filter.toUpperCase();
      if (!filter.startsWith('*')) {
        filter = '*' + filter;
      }
      filter = filter.replace(/\*/g, '.*');
      filter = filter.replace(/\?/g, '.?');
      try {
        this.activityFilter.currentFilter = new RegExp(filter);
      } catch (Exception) {
        // this.alert.flash('invalid filter expression', 'warning');
        return;
      }

      this.applyFilter(this.display_activities);
    } else {
      this.clearFilter();
    }
  }

  clearFilter() {
    this.activityFilter.currentFilter = undefined;
    this.applyFilter(this.display_activities);
  }

  onScrollDown() {
    this.addItems();
  }

  addItems() {
    // add another 5 items
    const start = this.displayCount;
    this.displayCount += 5;
    if (this.displayCount > this.activities.length) {
      this.displayCount = this.activities.length;
    }
    for (let i = start; i < this.displayCount; ++i) {
      this.display_activities.push(this.activities[i]);
    }
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }
}
