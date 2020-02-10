
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { ArtifactService } from './artifact.service';
import { Logger } from './logger.service';
import { DeployStatus, ProjectService } from './project.service';
import { RecordService } from './record.service';
import { RestService } from './rest.service';

import { Commit } from '../models/commit';
import { CommitCandidate } from '../models/commit-candidate';
import { WorkListEntry } from '../models/workitem';

@Injectable()
export class LifecycleService {
  constructor(
    protected log: Logger,
    protected record: RecordService,
    protected rest: RestService,
    protected artifact: ArtifactService,
    protected _project: ProjectService
  ) { }

  getCommitCandidates(projectId: string, artifactPath?: string, isRevert?: boolean): Observable<CommitCandidate[]> {
    return this._project.getProjectCheckoutId(projectId).pipe(
      mergeMap(id => this.rest.get(`lifecycle/checkouts/${id}/committables`)),
      map(res => res.record
        .map(r => this.record.recordToCheckedOutArtifact(r))
        .map(artifact => this.record.artifactToCommitCandidate(artifact))
      ));
  }

  commitCheckout(projectId: string, ids: string[], message: string): Promise<boolean> {
    const payload = {
      commitMessage: message,
      checkedOutArtifactIds: ids,
    };
    return this._project.getProjectCheckoutId(projectId).pipe(
      mergeMap(id => this.rest.post(`/lifecycle/checkouts/${id}/commit`, payload)),
      map(res => res.ok()))
      .toPromise();
  }

  getWorkList(): Promise<WorkListEntry[]> {
    return this.rest.get('/worklist').pipe(
      map(res => res.record.map(r => this.record.recordToWorkListEntry(r))),
      map(items => items.sort((a, b) => b.commit.commitTime.getTime() - a.commit.commitTime.getTime())))
      .toPromise();
  }

  getAllCommits(): Promise<Commit[]> {
    return this.rest.get('/lifecycle/commits').pipe(
      map(res => res.record.map(r => this.record.recordToCommit(r))),
      map(cmts => cmts.sort((a, b) => b.commitTime.getTime() - a.commitTime.getTime())))
      .toPromise();
  }

  getPendingCommits(): Promise<Commit[]> {
    return this.rest.get('/lifecycle/commits?status=PENDING').pipe(
      map(res => res.record.map(r => this.record.recordToCommit(r))),
      map(cmts => cmts.sort((a, b) => b.commitTime.getTime() - a.commitTime.getTime())))
      .toPromise();
  }

  getApprovedCommits(): Promise<Commit[]> {
    return this.rest.get('/lifecycle/commits?status=APPROVED').pipe(
      map(res => res.record.map(r => this.record.recordToCommit(r))),
      map(cmts => cmts.sort((a, b) => b.commitTime.getTime() - a.commitTime.getTime())))
      .toPromise();
  }

  getRejectedCommits(): Promise<Commit[]> {
    return this.rest.get('/lifecycle/commits?status=REJECTED').pipe(
      map(res => res.record.map(r => this.record.recordToCommit(r))),
      map(cmts => cmts.sort((a, b) => b.commitTime.getTime() - a.commitTime.getTime())))
      .toPromise();
  }

  getStaleCommits(): Promise<Commit[]> {
    return this.rest.get('/lifecycle/commits?status=STALE').pipe(
      map(res => res.record.map(r => this.record.recordToCommit(r))),
      map(cmts => cmts.sort((a, b) => b.commitTime.getTime() - a.commitTime.getTime())))
      .toPromise();
  }

  getCommit(id: string): Observable<Commit> {
    return this.rest.get(`/lifecycle/commits/${id}`).pipe(
      map(res => this.record.recordToCommit(res.record[0])));
  }

  withdrawCommit(id: string): Promise<boolean> {
    return this.rest.put(`/lifecycle/commit/${id}/cancel`, {}).pipe(
      map(res => res.ok()))
      .toPromise();
  }

  approveWorkItem(w: WorkListEntry, comment?: string): Promise<boolean> {
    const id = w.id;
    return this.rest.put(`/worklist/${id}/approve`, {}, { params: { comment: comment } }).pipe(
      map(res => res.ok()))
      .toPromise();
  }

  rejectWorkItem(w: WorkListEntry, comment?: string): Promise<boolean> {
    const id = w.id;
    return this.rest.put(`/worklist/${id}/reject`, {}, { params: { comment: comment } }).pipe(
      map(res => res.ok()))
      .toPromise();
  }

  delegateCommits(commitIds: string[], roles: string[]): Promise<boolean> {
    return;
  }

  getSyncCandidates(projectId: string, artifactPath?: string, artifactType?: string): Observable<CommitCandidate[]> {
    return this.getCommitCandidates(projectId);
  }

  deployArtifact(commitCandidate: CommitCandidate): Promise<DeployStatus> {
    return;
  }

  getExternalSyncCandidates(projectId: string): Observable<CommitCandidate[]> {
    return;
  }

  synchronizeExternalArtifacts(projectId: string, ids: string[]): Promise<boolean> {
    return;
  }
}
