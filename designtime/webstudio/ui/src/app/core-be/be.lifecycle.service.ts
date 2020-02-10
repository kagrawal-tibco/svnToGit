
import { Injectable } from '@angular/core';

import { of as observableOf, Observable } from 'rxjs';
import { filter, map, mergeMap, take, toArray } from 'rxjs/operators';

import { ArtifactService } from '../core/artifact.service';
import { LifecycleService } from '../core/lifecycle.service';
import { Logger } from '../core/logger.service';
import { DeployStatus, ProjectService } from '../core/project.service';
import { RecordService } from '../core/record.service';
import { RestService } from '../core/rest.service';
import { SettingsService } from '../core/settings.service';
import { BECommitCandidate } from '../models-be/commit-candidate-be';
import { BESettings } from '../models-be/settings-be';
import { Artifact, ArtifactStatus, ArtifactType } from '../models/artifact';
import { Commit, CommitStatus } from '../models/commit';
import { CommitCandidate } from '../models/commit-candidate';
import { BEArtifactItem } from '../models/dto';
import { WorkListEntry } from '../models/workitem';

@Injectable()
export class BELifecycleService extends LifecycleService {

  constructor(
    protected log: Logger,
    protected record: RecordService,
    protected rest: RestService,
    protected artifact: ArtifactService,
    protected _project: ProjectService,
    protected settings: SettingsService
  ) {
    super(log, record, rest, artifact, _project);
  }

  getCommitCandidates(projectId: string, artifactPath?: string, isRevert?: boolean): Observable<CommitCandidate[]> {
    const payload = {
      request: {
        data: {
          project: [{
            name: projectId,
            operation: (isRevert) ? 'Revert' : undefined,
            artifactItem: (artifactPath) ? [{ 'artifactPath': artifactPath }] : []
          }]
        }
      }
    };
    return this.rest.post(`projects/${projectId}/committables.json`, payload, undefined, true).pipe(
      map(res => res.record
        .map(r => this.record.recordToCheckedOutArtifact(r, false, projectId))
        .map(artifact => {
          if (artifact.status !== 'DELETED') {
            artifact.locked = this.artifact.getArtifactLockValueCache(artifact.id);
            this.artifact.getCheckedOutArtifact(artifact.id, false).then(old => {
              if (old) {
                old.updateMeta(artifact);
              }
            });
          }
          return this.record.artifactToCommitCandidate(artifact);
        })
      ));
  }

  commitCheckout(projectId: string, ids: string[], message: string): Promise<boolean> {
    const payload = {
      request: {
        data: {
          commitComments: message,
          project: [{
            name: projectId,
            artifactItem: this.getArtifactItems(ids)
          }]
        }
      }
    };
    return this.rest.post(`projects/${projectId}/commit.json`, payload, undefined, true).pipe(
      map(res => res.ok()))
      .toPromise();
  }

  getCommit(id: string): Observable<Commit> {
    return this.rest.get('worklist.json').pipe(
      mergeMap(res => {
        if (res.ok() && res.record) {

          const entries = res.record
            .map(record => this.record.recordToWorkListEntry(record))
            .sort((a, b) => b.commit.commitTime.getTime() - a.commit.commitTime.getTime())
            .filter(f => f.commit.id === id);

          return this.getWorkListItems(entries[0]).pipe(
            map(e => e.commit));
        }
      }));
  }

  getWorkList(): Promise<WorkListEntry[]> {
    return this.rest.get('worklist.json').toPromise()
      .then(result => {
        if (result.ok() && result.record) {

          const entries = result.record
            .map(record => this.record.recordToWorkListEntry(record));

          return observableOf(...entries).pipe(
            mergeMap(e => this.getWorkListItems(e)),
            filter(e => e.commit.status === CommitStatus.PENDING),
            take(entries.length),
            toArray())
            .toPromise()
            .then(records => {
              records.sort((a, b) => b.commit.commitTime.getTime() - a.commit.commitTime.getTime());
              return records;
            });
        }
      });
  }

  getPendingCommits(): Promise<Commit[]> {
    return this.fetchWorkList(CommitStatus.PENDING);
  }

  getApprovedCommits(): Promise<Commit[]> {
    return this.fetchWorkList(CommitStatus.APPROVED);
  }

  getRejectedCommits(): Promise<Commit[]> {
    return this.fetchWorkList(CommitStatus.REJECTED);
  }

  getStaleCommits(): Promise<Commit[]> {
    return this.fetchWorkList(CommitStatus.STALE);
  }

  getAllCommits(): Promise<Commit[]> {
    return this.fetchWorkList();
  }

  getWorkListItems(worklistEntry: WorkListEntry): Observable<WorkListEntry> {
    const worklistId = worklistEntry.id;
    return this.rest.get(`worklist/${worklistId}/details.json`).pipe(
      map(res => {
        res.record.map(r => this.addCommitCandidateToWorklist(worklistEntry,
          <BECommitCandidate>this.record.recordToCommitCandidate(r)));
        return worklistEntry;
      }));
  }

  fetchWorkList(status?: CommitStatus) {
    return this.rest.get('worklist.json').toPromise()
      .then(result => {
        if (result.ok() && result.record) {
          const entries = result.record
            .map(record => this.record.recordToWorkListEntry(record));
          return observableOf(...entries).pipe(
            mergeMap(e => this.getWorkListItems(e)),
            filter(e => status ? e.commit.status === status : true),
            map(c => c.commit),
            take(entries.length),
            toArray())
            .toPromise()
            .then(records => {
              entries.sort((a, b) => b.commit.commitTime.getTime() - a.commit.commitTime.getTime());
              return records;
            });
        }
      });
  }

  approveWorkItem(worklistEntry: WorkListEntry, comment?: string): Promise<boolean> {
    const action = 'Approve';
    return this.statusChange(worklistEntry, action, comment)
      .then(resp => {
        if ((<BESettings>this.settings.latestSettings).autoUnLockOnReview) {
          this.unlockOnStatusChange(worklistEntry, action);
        }
        return resp.status;
      });
  }

  rejectWorkItem(worklistEntry: WorkListEntry, comment?: string): Promise<boolean> {
    const action = 'Reject';
    return this.statusChange(worklistEntry, action, comment)
      .then(resp => {
        if ((<BESettings>this.settings.latestSettings).autoUnLockOnReview) {
          this.unlockOnStatusChange(worklistEntry, action);
        }
        return resp.status;
      });
  }

  unlockOnStatusChange(worklistEntry: WorkListEntry, action: String) {
    if (worklistEntry.commit.changeList) {
      this._project.getCheckedOutProjects().then(coMetas => {
        for (const no in worklistEntry.commit.changeList) {
          const changeEntry = worklistEntry.commit.changeList[no];

          const projectName = changeEntry.id.split('@')[1];
          let projectCheckedOut = false;
          if (coMetas) {
            coMetas.forEach(meta => {
              if (meta.projectName === projectName) {
                projectCheckedOut = true;
              }
            });
          }

          if (projectCheckedOut) {
            // if its approve and delete, then the artifact itself is no longer there for unlocking
            if (action === 'Approve' && changeEntry.status === 'DELETED') { continue; }

            this.artifact.getCheckedOutArtifactWithContent(changeEntry.id)
              .then((artifact) => {
                if (artifact != null) {
                  artifact.locked = false;
                }
              });
          }
        }
      });
    }
  }

  deployArtifact(commitCandidate: BECommitCandidate): Promise<DeployStatus> {
    const worklistItems = [];

    const worklistItem = {
      artifactPath: commitCandidate.path,
      artifactType: commitCandidate.type.defaultExtension,
      managedProjectName: commitCandidate.id.split('@')[1],
      reviewStatus: 'BuildAndDeploy',
      deployEnvironments: commitCandidate.deployEnvironments,
      reviewComments: commitCandidate.deployComments
    };

    worklistItems.push({ revisionId: commitCandidate.revisionId, worklistItem: [worklistItem] });

    return this.statusChange(null, null, null, worklistItems, commitCandidate.id);
  }

  delegateCommits(commitIds: string[], roles: string[]): Promise<boolean> {
    const payload = {
      request: {
        data: {
          worklist: {
            revisions: {
              revisionId: commitIds
            },
            roles: {
              role: roles
            }
          }
        }
      }
    };
    return this.rest.put(`worklist/delegate.json`, payload, undefined, true).pipe(
      map(res => res.ok()))
      .toPromise();
  }

  getSyncCandidates(projectId: string, artifactPath?: string, artifactType?: string): Observable<CommitCandidate[]> {
    const payload = {
      request: {
        data: {
          project: [{
            name: projectId,
            artifactItem: (artifactPath && artifactType) ?
              [{ 'artifactPath': artifactPath, 'artifactType': artifactType }] :
              (artifactPath) ?
                [{ 'artifactPath': artifactPath }] :
                []
          }]
        }
      }
    };
    return this.rest.post(`projects/${projectId}/synchronizables.json`, payload).pipe(
      map(res => res.record
        .map(r => this.record.recordToCheckedOutArtifact(r, false, projectId, true))
        .map(artifact => this.record.artifactToCommitCandidate(artifact))
        .map(cc => this.markCommitCandidateAsStale(cc))
      ));
  }

  getExternalSyncCandidates(projectId: string): Observable<CommitCandidate[]> {
    return this.rest.get(`projects/${projectId}/repositorySyncArtifacts.json`).pipe(
      map(res => res.record
        .map(r => this.record.recordToCheckedOutArtifact(r, false, projectId))
        .map(artifact => this.record.artifactToCommitCandidate(artifact))
        .map(cc => this.markCommitCandidateAsModified(cc))
      ));
  }

  synchronizeExternalArtifacts(projectId: string, ids: string[]): Promise<boolean> {
    const payload = {
      request: {
        data: {
          project: [{
            name: projectId,
            artifactItem: this.getArtifactItems(ids)
          }]
        }
      }
    };
    return this.rest.post(`projects/${projectId}/repositorySync.json`, payload).pipe(
      map(res => res.ok()))
      .toPromise();
  }

  addCommitCandidateToWorklist(worklistEntry: WorkListEntry, commitCandidate: BECommitCandidate): WorkListEntry {
    if (worklistEntry.commit.status == null) {
      worklistEntry.commit.status = this.getCommitStatus(commitCandidate.reviewStatus);
    }
    if (commitCandidate.id == null) {
      commitCandidate.id = (this.rest.userName + '@' + worklistEntry.commit.projectName + '@' + commitCandidate.path + '@' + commitCandidate.type.defaultExtension);
    }

    if (!worklistEntry.commit.resolver && commitCandidate.reviewerName) { worklistEntry.commit.resolver = { userName: commitCandidate.reviewerName, id: null }; }
    if (!worklistEntry.commit.resolutionComment && commitCandidate.reviewComments) { worklistEntry.commit.resolutionComment = commitCandidate.reviewComments; }

    worklistEntry.commit.changeList.push(commitCandidate);
    return worklistEntry;
  }

  getCommitStatus(commitStatus: string): CommitStatus {
    let status;

    switch (commitStatus) {
      case 'Committed': status = CommitStatus.PENDING; break;
      case 'Approve':
      case 'BuildAndDeploy':
        status = CommitStatus.APPROVED; break;
      case 'Reject': status = CommitStatus.REJECTED; break;
      default: status = CommitStatus.PENDING; break;
    }
    return status;
  }

  private statusChange(worklistEntry: WorkListEntry, status: string, comment: string, worklistItems?: any[], commitId?: string): Promise<DeployStatus> {
    const payload = {
      request: {
        data: {
          worklist: (worklistItems) ? worklistItems : this.record.workListEntryToItem(worklistEntry, status, comment)
        }
      }
    };
    return this.rest.post(`worklist/statusChange.json`, payload, undefined, true).pipe(
      map(res => {
        const deployStatus = new DeployStatus();
        deployStatus.status = res.ok();

        if (!res.ok()) {
          const record = res.record[0];
          if (record && record.artifactDetails && record.artifactDetails.problem && record.artifactDetails.problem.length > 0) {
            deployStatus.warningCnt = deployStatus.errorCnt = 0;
            record.artifactDetails.problem.forEach(element => {
              if (element.isWarning) { deployStatus.warningCnt++; } else { deployStatus.errorCnt++; }
            });
          }
        }
        return deployStatus;
      }))
      .toPromise();
  }

  private markCommitCandidateAsStale(cc: CommitCandidate): CommitCandidate {
    cc.stale = true;
    return cc;
  }

  private markCommitCandidateAsModified(cc: CommitCandidate): CommitCandidate {
    cc.status = <ArtifactStatus>'MODIFIED';
    return cc;
  }

  private getArtifactItems(artifactIds: string[]): BEArtifactItem[] {
    const beArtifactItems = [];
    for (const id in artifactIds) {
      const idSplit = artifactIds[id].split('@');

      const artifact = new Artifact();
      artifact.path = idSplit[2];
      artifact.type = ArtifactType.fromExtension(idSplit[3]);

      beArtifactItems.push(this.record.artifactToItem(artifact));
    }

    return beArtifactItems;
  }
}
