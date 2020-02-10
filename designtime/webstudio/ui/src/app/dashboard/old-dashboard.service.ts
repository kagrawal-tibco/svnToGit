import { Injectable } from '@angular/core';

import { ProjectService } from 'app/core/project.service';
import { RestService } from 'app/core/rest.service';

import { environment } from '../../environments/environment';
import { LifecycleService } from '../core/lifecycle.service';
import { Commit } from '../models/commit';
import { WorkListEntry } from '../models/workitem';

export enum DashboardMode {
  PENDING,
  RESOLVED
}

export enum DashboardSubscriptions {
  UNRESOLVED,
  PENDING,
  APPROVED,
  REJECTED,
  STALE
}

@Injectable()
export class OldDashboardService {
  mode: DashboardMode = DashboardMode.PENDING;
  unresolvedItems: WorkListEntry[] = [];
  resolvedItems: WorkListEntry[] = [];
  pendingCommits: Commit[] = [];
  rejectedCommits: Commit[] = [];
  approvedCommits: Commit[] = [];
  staleCommits: Commit[] = [];

  private subscribers: Map<DashboardSubscriptions, (() => {})[]> = new Map();

  constructor(
    private lifecycle: LifecycleService,
    private projectService: ProjectService,
    private rest: RestService
  ) {
    this.subscribers.set(DashboardSubscriptions.APPROVED, []);
    this.subscribers.set(DashboardSubscriptions.PENDING, []);
    this.subscribers.set(DashboardSubscriptions.REJECTED, []);
    this.subscribers.set(DashboardSubscriptions.STALE, []);
    this.subscribers.set(DashboardSubscriptions.UNRESOLVED, []);
  }

  addSubscriber(sub: DashboardSubscriptions, callback: () => {}) {
    this.subscribers.get(sub).push(callback);
  }

  init() {
    this.updateAll();
  }

  clear() {
    this.unresolvedItems = [];
    this.pendingCommits = [];
  }

  updateAll() {
    const promises: Promise<Commit[] | WorkListEntry[]>[] = [];
    if (!environment.enableBEUI) {
      promises.push(this.updatePendingCommits());
      promises.push(this.updateStaleCommits());
    }
    promises.push(this.updateUnresolvedItems());
    promises.push(this.updateRejectedCommits());
    promises.push(this.updateApprovedCommits());
    return Promise.all(promises);
  }

  updateUnresolvedItems() {
    return this.lifecycle.getWorkList()
      .then(items => this.unresolvedItems = items).then(w => {
        this.subscribers.get(DashboardSubscriptions.UNRESOLVED).forEach(c => c());
        return w;
      }
      );
  }

  updatePendingCommits() {
    return this.lifecycle.getPendingCommits()
      .then(cmts => this.pendingCommits = cmts).then(
        c => {
          this.subscribers.get(DashboardSubscriptions.PENDING).forEach(p => p());
          return c;
        }
      );
  }

  updateApprovedCommits() {
    this.approvedCommits.length = 0;
    return this.lifecycle.getApprovedCommits()
      .then(cmts => this.approvedCommits = cmts).then(
        c => {
          this.subscribers.get(DashboardSubscriptions.APPROVED).forEach(a => a());
          return c;
        }
      );
  }

  updateRejectedCommits() {
    this.rejectedCommits.length = 0;
    return this.lifecycle.getRejectedCommits()
      .then(cmts => this.rejectedCommits = cmts).then(
        c => {
          this.subscribers.get(DashboardSubscriptions.REJECTED).forEach(r => r());
          return c;
        }
      );
  }

  updateStaleCommits() {
    return this.lifecycle.getStaleCommits()
      .then(cmts => this.staleCommits = cmts).then(
        c => {
          this.subscribers.get(DashboardSubscriptions.STALE).forEach(s => s());
          return c;
        }
      );
  }

  removeFromPending(id: string) {
    const idx = this.pendingCommits.findIndex(cmt => cmt.id === id);
    if (idx !== -1) {
      this.pendingCommits.splice(idx, 1);
    }
  }

  removeFromUnresolved(id: string) {
    const idx = this.unresolvedItems.findIndex(item => item.commit.id === id);
    if (idx !== -1) {
      this.unresolvedItems.splice(idx, 1);
    }
  }

  showBEUI(): boolean {
    return environment.enableBEUI;
  }

}
