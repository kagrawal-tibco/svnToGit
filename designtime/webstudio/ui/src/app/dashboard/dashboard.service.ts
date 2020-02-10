
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { ProjectService } from 'app/core/project.service';
import { RestService } from 'app/core/rest.service';

import { environment } from '../../environments/environment';
import { LifecycleService } from '../core/lifecycle.service';
import { Commit } from '../models/commit';
import { WorkListEntry } from '../models/workitem';

export class ProjectSnapshot {
  name: string;
  description: string;
  size: string;
  artifactsCount: number;
  lastCheckoutBy: string;
  lastCheckoutTime: string;
  lastCommitBy: string;
  lastCommitTime: string;
  lastGenerateDeployableBy: string;
  lastGenerateDeployableTime: string;
  lastSyncBy: string;
  lastSyncTime: string;
  lastValidateBy: string;
  lastValidateTime: string;
  totalCommits: number;
  totalApprovals: number;
  totalDeployments: number;
  totalRejections: number;
}

export interface WorkListDelta {
  added?: WorkListEntry[];
  changed?: WorkListEntry[];
  removed?: WorkListEntry[];
}

export interface CommitDelta {
  added?: Commit[];
  changed?: Commit[];
  removed?: Commit[];
}

export interface ProjectSnapshotDelta {
  added?: ProjectSnapshot[];
  changed?: ProjectSnapshot[];
  removed?: ProjectSnapshot[];
}

export interface ActivityDelta {
  worklistDelta?: WorkListDelta;
  snapshotDelta?: ProjectSnapshotDelta;
  commitDelta?: CommitDelta;
}

@Injectable()
export class DashboardService {
  worklist: WorkListEntry[] = [];
  commits: Commit[] = [];
  projectSnapshots: ProjectSnapshot[] = [];

  private _subscribers: Subject<ActivityDelta>
    = new Subject<ActivityDelta>();

  constructor(
    private lifecycle: LifecycleService,
    private projectService: ProjectService,
    private rest: RestService,
    public i18n: I18n
  ) {
    // this.init();
  }

  activityChanges(): Subject<ActivityDelta> {
    return this._subscribers;
  }

  init() {
    this.refresh();
  }

  refresh() {
    this.clear();
    this.updateAll();
  }

  clear() {
    this.worklist = [];
    this.commits = [];
    this.projectSnapshots = [];
  }

  updateAll() {
    this.updateCommits();
    this.updateWorklist();
    this.updateProjectSnapshots();
  }

  removeWorklistEntry(id: string) {
    const idx = this.worklist.findIndex(cmt => cmt.id === id);
    if (idx !== -1) {
      this.worklist.splice(idx, 1);
    }
  }

  removeCommit(id: string) {
    const idx = this.commits.findIndex(cmt => cmt.id === id);
    if (idx !== -1) {
      this.commits.splice(idx, 1);
    }
  }

  insertWorklistEntry(entry: WorkListEntry) {
    const idx = this.worklist.findIndex(cmt => cmt.id === entry.id);
    if (idx !== -1) {
      this.worklist.splice(idx, 1);
    }
  }

  insertCommit(commit: Commit) {
    const idx = this.commits.findIndex(cmt => cmt.id === commit.id);
    if (idx !== -1) {
      this.commits.splice(idx, 1);
    }
  }

  updateWorklist() {
    this.lifecycle.getWorkList().then(worklist => {
      const oldList = this.worklist;
      this.worklist = worklist;
      this.mergeWorkLists(worklist, oldList, true);
    });
  }

  mergeCommits(newList: Commit[], oldList: Commit[], notify: boolean) {
    const changed = [];
    const removed = [];
    const added = [];
    if (!newList) {
      return;
    }
    if (notify && newList && newList.length > 0 && (!oldList || oldList.length === 0)) {
      const delta: CommitDelta = <CommitDelta>{
        added: newList
      };
      this._subscribers.next(
        <ActivityDelta>{
          commitDelta: delta
        }
      );
      return;
    }
    newList.forEach(entry => {
      const idx = oldList.findIndex(e => e.id === entry.id);
      if (idx === -1) {
        added.push(entry);
      } else {
        if (oldList[idx].status !== entry.status) {
          // status changed, replace the commit
          // this.worklist.splice(idx, 1, entry);
          changed.push(entry);
        }
      }
    });
    oldList.forEach(entry => {
      const idx = newList.findIndex(e => e.id === entry.id);
      if (idx === -1) {
        // item was removed
        removed.push(entry);
      }
    });
    if (notify && (changed.length > 0 || removed.length > 0 || added.length > 0)) {
      const delta: CommitDelta = <CommitDelta>{
        added: added,
        changed: changed,
        removed: removed
      };
      this._subscribers.next(
        <ActivityDelta>{
          commitDelta: delta
        }
      );
    }
  }

  mergeWorkLists(newList: WorkListEntry[], oldList: WorkListEntry[], notify: boolean) {
    const changed = [];
    const removed = [];
    const added = [];
    if (!newList) {
      return;
    }
    newList.forEach(entry => {
      const idx = oldList.findIndex(e => e.id === entry.id);
      if (idx === -1) {
        // this.worklist.push(entry);
        added.push(entry);
      } else {
        if (oldList[idx].status !== entry.status) {
          // status changed, replace the commit
          // this.worklist.splice(idx, 1, entry);
          changed.push(entry);
        }
      }
    });
    oldList.forEach(entry => {
      const idx = newList.findIndex(e => e.id === entry.id);
      if (idx === -1) {
        // item was removed
        removed.push(entry);
      }
    });
    if (notify && (changed.length > 0 || removed.length > 0 || added.length > 0)) {
      const delta: WorkListDelta = <WorkListDelta>{
        added: added,
        changed: changed,
        removed: removed
      };
      this._subscribers.next(
        <ActivityDelta>{
          worklistDelta: delta
        }
      );
    }
  }

  updateCommits() {
    this.lifecycle.getAllCommits().then(commits => {
      const oldCommits = this.commits;
      this.commits = commits;
      this.mergeCommits(commits, oldCommits, true);
      // if (!commits) {
      //   return;
      // }
      // let changed = [];
      // commits.forEach(c => {
      //   let idx = this.commits.findIndex(commit => c.id === commit.id);
      //   if (idx === -1) {
      //     this.commits.push(c);
      //     changed.push(c);
      //   } else {
      //     if (this.commits[idx].status !== c.status) {
      //       // status changed, replace the commit
      //       this.commits.splice(idx, 1, c);
      //       changed.push(c);
      //     }
      //   }
      // });
      // if (changed.length > 0) {
      //   this._subscribers.next({
      //     changedCommits: changed
      //   });
      // }
    });
  }

  updateProjectSnapshots() {
    this._updateProjectSnapshots().then(snapshots => {
      if (!snapshots) {
        return;
      }
      // always update the snapshots, since the details may have changed
      this.projectSnapshots = snapshots;
      const delta: ProjectSnapshotDelta = <ProjectSnapshotDelta>{
        changed: snapshots
      };
      this._subscribers.next(
        <ActivityDelta>{
          snapshotDelta: delta
        }
      );
    });
  }

  _updateProjectSnapshots(): Promise<ProjectSnapshot[]> {
    return new Promise((resolve, reject) => {
      this.projectSnapshots = [];
      this.projectService.getAllProjects().then(metas => {
        if (!metas) {
          resolve(this.projectSnapshots);
          return;
        }

        let count = metas.length;
        this.projectService.getCheckedOutProjects().then(coMetas => {
          if (coMetas) {
            coMetas.forEach(meta => {
              this.rest.get('projects/' + meta.projectName + '/summary.json').pipe(
                map(res => {
                  if (res.ok()) {
                    if (res.record[0]) {
                      const idx = this.projectSnapshots.findIndex(snap => snap.name === meta.projectName);
                      if (idx === -1) {
                        const snap = this.createProjectSnapshot(res.record[0], meta.projectName);
                        this.projectSnapshots.push(snap);
                        count--;
                        if (count === 0) {
                          resolve(this.projectSnapshots);
                        }
                      }
                    }
                  } else {
                    const snap: ProjectSnapshot = <ProjectSnapshot>{
                      name: meta.projectName,
                      description: this.i18n('Project is not checked out, no additional details are available'),
                      size: '-1'
                    };
                    this.projectSnapshots.push(snap);
                    count--;
                    if (count === 0) {
                      resolve(this.projectSnapshots);
                    }
                  }
                })).toPromise();
            });
          }
          const nonCheckedOutMetas = !coMetas ? metas : metas.filter(m => coMetas.findIndex(co => co.projectId === m.projectId) === -1);
          nonCheckedOutMetas.forEach(meta => {
            if (environment.enableBEUI) {
              if (!this.projectSnapshots.find(s => s.name === meta.projectName)) {
                const snap: ProjectSnapshot = <ProjectSnapshot>{
                  name: meta.projectName,
                  description: this.i18n('Project is not checked out, no additional details are available'),
                  size: '-1'
                };
                this.projectSnapshots.push(snap);
                count--;
                if (count === 0) {
                  resolve(this.projectSnapshots);
                }
              }
            } else {
              const snap: ProjectSnapshot = <ProjectSnapshot>{
                name: meta.projectName,
                artifactsCount: meta.checkedOutArtifactIds.length,
                size: 'unknown',
                lastCommitBy: 'unknown'
              };
              this.projectSnapshots.push(snap);
            }
          });
        });
        if (!environment.enableBEUI) {
          resolve(this.projectSnapshots);
        }
      }, err => {
        console.log('unable to retrieve project summaries ' + err);
        // throw err;
      });
    });
  }

  showBEUI(): boolean {
    return environment.enableBEUI;
  }

  private createProjectSnapshot(record: any, project: string): ProjectSnapshot {
    const snapshot: ProjectSnapshot = <ProjectSnapshot>{
      name: project,
      description: '',
      size: record.size,
      artifactsCount: record.artifactsCount,
      lastCheckoutBy: record.lastCheckoutBy,
      lastCheckoutTime: record.lastCheckoutTime,
      lastCommitBy: record.lastCommitBy,
      lastCommitTime: record.lastCommitTime,
      lastGenerateDeployableBy: record.lastGenerateDeployableBy,
      lastGenerateDeployableTime: record.lastGenerateDeployableTime,
      lastSyncBy: record.lastSyncBy,
      lastSyncTime: record.lastSyncTime,
      lastValidateBy: record.lastValidateBy,
      lastValidateTime: record.lastValidateTime,
      totalCommits: record.totalCommits,
      totalApprovals: record.totalApprovals,
      totalDeployments: record.totalDeployments,
      totalRejections: record.totalRejections
    };
    return snapshot;
  }

}
