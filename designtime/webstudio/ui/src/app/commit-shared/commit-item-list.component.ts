
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { map } from 'rxjs/operators';

import { CommitDelegateContext, CommitDelegateModal } from './commit-delegate.modal';

import { AlertService } from '../core/alert.service';
import { LifecycleService } from '../core/lifecycle.service';
import { ModalService } from '../core/modal.service';
import { RestService } from '../core/rest.service';
import { DashboardService } from '../dashboard/dashboard.service';
import { CommitResolution } from '../models/commit';
import { WorkListEntry, } from '../models/workitem';

@Component({
  selector: 'commit-item-list',
  templateUrl: './commit-item-list.component.html',
})
export class CommitItemListComponent implements OnInit {

  @Input()
  input: WorkListEntry[];

  @Input()
  title: string;

  @Input()
  emptyMsg: string;

  @Input()
  mode: string;

  private removing: WorkListEntry;

  constructor(
    private lifecycle: LifecycleService,
    private alert: AlertService,
    private modal: ModalService,
    private dashboard: DashboardService,
    private rest: RestService,
    private matDialog: MatDialog,
    public i18n: I18n
  ) { }

  ngOnInit() {
  }

  onApprove(c: WorkListEntry, comment?: string): Promise<boolean> {
    return this.lifecycle.approveWorkItem(c, comment);
  }

  onReject(c: WorkListEntry, comment?: string): Promise<boolean> {
    return this.lifecycle.rejectWorkItem(c, comment);
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
        this.removing = c;
        setTimeout(() => {
          this.removeItem(c);
          this.removing = null;
        }, 350);
        this.dashboard.updateAll();
      }
    }, err => this.alert.flash(err, 'warning'));
  }

  removeItem(c: WorkListEntry) {
    const idx = this.input.indexOf(c);
    if (idx !== -1) {
      this.input.splice(idx, 1);
    }
  }

  onWithdraw(c: WorkListEntry) {
    this.modal
      .confirm()
      .message(this.i18n('Please confirm you want to withdraw the commit.'))
      .okBtn('Confirm')
      .cancelBtn('Cancel')
      .open().result
      .then(() => this.lifecycle.withdrawCommit(c.commit.id), () => false)
      .then(ok => {
        if (ok) {
          this.dashboard.updateAll();
          this.removeCommit(c);
        }
      }, err => this.alert.flash(err, 'warning'));
  }

  onDelegate(c: WorkListEntry) {
    const ref = this.matDialog.open(CommitDelegateModal, {
      width: '450px',
      height: '40vh',
      data: <CommitDelegateContext>{
        cc: c.commit
      }
    });
    ref.afterClosed().subscribe(ok => {
      // this.modal.open(CommitDelegateModal, new CommitDelegateContext(c.commit))
      // .then(ok => {
      if (ok) {
        this.dashboard.updateAll();
        this.removeCommit(c);
      }
    }, err => this.alert.flash(err, 'warning'));
  }

  onDelete(c: WorkListEntry, revisionId: string) {
    this.modal
      .confirm()
      .message(this.i18n('Do you want to delete entry with revisionId ') + revisionId + '?')
      .okBtn(this.i18n('Confirm'))
      .cancelBtn(this.i18n('Cancel'))
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
          this.rest.delete(`worklist/delete.json`, payload, undefined, true).pipe(
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

  removeCommit(c: WorkListEntry) {
    const idx = this.input.indexOf(c);
    if (idx !== -1) {
      this.removing = c;
      setTimeout(() => {
        this.input.splice(idx, 1);
        this.removing = null;
      }, 350);
    }
  }
}
