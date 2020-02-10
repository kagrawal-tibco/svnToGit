import { Component, Inject, Input, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ModalService } from 'app/core/modal.service';
import { ProjectService } from 'app/core/project.service';
import { RestService } from 'app/core/rest.service';
import { ProjectMeta } from 'app/models/project-meta';

import { DashboardMode, DashboardSubscriptions, OldDashboardService } from './old-dashboard.service';
import { WorklistModal, WorklistModalContext } from './worklist.modal';

import { environment } from '../../environments/environment';
import { LifecycleService } from '../core/lifecycle.service';
import { Logger } from '../core/logger.service';
import { WorkListEntry } from '../models/workitem';

@Component({
  selector: 'old-dashboard',
  templateUrl: './old-dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class OldDashboardComponent implements OnInit {
  public combinedCommits;
  public pendingCommits;
  public rejectedCommits;
  public approvedCommits;
  public staleCommits;

  userName: string;
  actionType: string;
  beforeDate: Date;
  afterDate: Date;
  isLogFile: boolean;

  @Input()
  artifactPath: string;
  @Input()
  artifactType: string;
  @Input()
  projectName: string;

  constructor(
    private log: Logger,
    private service: OldDashboardService,
    private lifecycle: LifecycleService,
    private projectService: ProjectService,
    private modal: ModalService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    this.service.updateAll().then(
      this.populateFromSubscription.bind(this)
    );
    // TODO: Clean up how this subscription is being provided and used.
    this.service.addSubscriber(DashboardSubscriptions.APPROVED, (() => { this.approvedCommits = this.getApprovedCommits(); }).bind(this));
    this.service.addSubscriber(DashboardSubscriptions.PENDING, (() => { this.pendingCommits = this.getPendingCommits(); }).bind(this));
    this.service.addSubscriber(DashboardSubscriptions.REJECTED, (() => { this.rejectedCommits = this.getRejectedCommits(); }).bind(this));
    this.service.addSubscriber(DashboardSubscriptions.STALE, (() => { this.staleCommits = this.getStaleCommits(); }).bind(this));
    // this.setInPending();
    this.setInResolved();
  }

  getPendingCommits(): WorkListEntry[] {
    return this.service.pendingCommits.map(c => <WorkListEntry>{ id: c.id, commit: c });
  }

  getRejectedCommits(): WorkListEntry[] {
    return this.service.rejectedCommits.map(c => <WorkListEntry>{ id: c.id, commit: c });
  }

  getApprovedCommits(): WorkListEntry[] {
    return this.service.approvedCommits.map(c => <WorkListEntry>{ id: c.id, commit: c });
  }

  getStaleCommits(): WorkListEntry[] {
    return this.service.staleCommits.map(c => <WorkListEntry>{ id: c.id, commit: c });
  }

  pendingCommitMode() {
    return 'FOR_CANCEL';
  }

  staleCommitMode() {
    return 'FOR_CANCEL';
  }

  approvedCommitMode() {
    return 'NORMAL';
  }

  combineCommits(): WorkListEntry[] {
    const combined: WorkListEntry[] = this.getUnresolvedWorkItems();
    this.getPendingCommits()
      .map(c => {
        if (!combined.find(w => w.commit.id === c.id)) {
          combined.push(c);
        }
      });
    return combined;
  }

  getUnresolvedItems(projectName: string): WorkListEntry[] {
    return this.service.unresolvedItems.filter(it => it.commit && it.commit.projectName === projectName);
  }

  getUnresolvedWorkItems(): WorkListEntry[] {
    return this.service.unresolvedItems;
  }

  getResolvedWorkItems(): WorkListEntry[] {
    return this.service.resolvedItems;
  }

  inPending(): boolean {
    return this.service.mode === DashboardMode.PENDING;
  }

  setInPending() {
    this.service.mode = DashboardMode.PENDING;
  }

  inResolved(): boolean {
    return this.service.mode === DashboardMode.RESOLVED;
  }

  setInResolved() {
    this.service.mode = DashboardMode.RESOLVED;
  }

  // get dashboardBodyHeight() {
  //   return window.window.innerHeight - 190;
  // }

  computeSize(sizeString: string) {
    let idx = sizeString.indexOf(' ');
    if (idx === -1) {
      idx = sizeString.indexOf('.');
    }
    const sizeinKB = Number.parseFloat(sizeString.substring(0, idx));
    if (sizeinKB < 1024) {
      return sizeinKB.toFixed(2) + ' KB';
    }
    const sizeinMB = sizeinKB / 1024;
    return sizeinMB.toFixed(2) + ' MB';
  }

  showBEUI(): boolean {
    return environment.enableBEUI;
  }

  showTCEUI(): boolean {
    return environment.enableTCEUI;
  }

  private populateFromSubscription() {
    this.combinedCommits = this.combineCommits();
    this.pendingCommits = this.getPendingCommits();
    this.rejectedCommits = this.getRejectedCommits();
    this.approvedCommits = this.getApprovedCommits();
    this.staleCommits = this.getStaleCommits();
  }

}
