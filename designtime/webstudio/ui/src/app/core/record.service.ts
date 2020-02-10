import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Logger } from './logger.service';

import { Artifact, ArtifactStatus, ArtifactType } from '../models/artifact';
import { ArtifactHistoryEntry } from '../models/artifact-history-entry';
import { Commit, CommitStatus } from '../models/commit';
import { CandidateStatus, CommitCandidate } from '../models/commit-candidate';
import {
  ArtifactHistoryRecord,
  ArtifactItem,
  ArtifactRecord,
  ArtifactValidationRecord,
  CheckedOutArtifactRecord,
  CheckoutArtifactsRequest,
  CheckoutRecord,
  CommitCandidateRecord,
  CommitRecord,
  GroupRecord,
  ProjectRecord,
  Result,
  UserRecord,
  ValidationProblemRecord,
  WorkListRecord
} from '../models/dto';
import { Group } from '../models/group';
import { ProjectMeta } from '../models/project-meta';
import { User } from '../models/user';
import { WorkListEntry, WorkListEntryStatus } from '../models/workitem';

@Injectable()
export class RecordService {
  public static TITLE_LENGTH = 32;

  constructor(
    protected log: Logger,
    public i18n: I18n
  ) {

  }

  recordToAnayzeResult(raw: string[]): Result[] {
    if (raw) {
      try {
        return raw.map(entry => JSON.parse(entry));
      } catch (e) {
        this.log.err(this.i18n('Unable to deserialize Artifact Analysis Result: {{raw}}', { raw: raw }));
        this.log.err('Error: ', e);
        return [];
      }
    } else {
      return [];
    }
  }

  recordToArtifact(r: ArtifactRecord, isCheckedOut?: boolean, forceContent?: boolean): Artifact {
    const a = new Artifact();
    a.id = r.entityId;
    a.description = r.description;
    a.path = r.path;
    a.projectId = r.projectId;
    a.type = ArtifactType.fromExtension(r.type);
    a.content = r.content;
    a.status = <ArtifactStatus>r.status;
    a.isCheckedOutArtifact = isCheckedOut;
    a.revisionNumber = r.revisionNumber;
    a.revisionId = r.revisionId;
    a.locked = r.locked;
    a.content = r.content;
    a.encoding = r.encoding;
    a.metadata = r.metadata;
    // if something wrong, present the node in root directory
    if (!a.path) {
      a.path = '/';
    }

    if (r.analyzeResult) {
      a.analyzeResult = this.recordToAnayzeResult(r.analyzeResult);
    }

    return a;
  }

  recordToCheckedOutArtifact(r: CheckedOutArtifactRecord, forceContent?: boolean, projectName?: string, isSync?: boolean): Artifact {
    const ca = this.recordToArtifact(r, true, forceContent);
    ca.parentId = r.parentId;
    ca.checkedOutFromRevision = r.checkedOutFromRevision;
    ca.latestRevision = r.latestRevision;
    ca.stale = r.stale;
    ca.disposed = r.disposed;
    ca.encoding = r.encoding;
    return ca;
  }

  recordToProjectMeta(r: ProjectRecord): ProjectMeta {
    const meta = new ProjectMeta();
    meta.projectId = r.entityId;
    meta.projectName = r.projectName;
    meta.type = <'SUBVERSION' | 'GIT' | 'NATIVE'>r.projectType;
    return meta;
  }

  checkoutRecordToProjectMeta(r: CheckoutRecord): ProjectMeta {
    const meta = new ProjectMeta();
    meta.projectId = r.projectId;
    meta.projectName = r.projectName;
    meta.checkoutId = r.entityId;
    meta.checkedOutArtifactIds = r.artifacts ? r.artifacts.map(a => a.entityId) : [];
    return meta;
  }

  recordToArtifactHistoryEntry(artifactId: string, r: ArtifactHistoryRecord): ArtifactHistoryEntry {
    return <ArtifactHistoryEntry>{
      userName: r.userName,
      commitId: r.commitId,
      artifactId: artifactId,
      version: r.revisionNumber,
      commitMessage: r.commitMessage,
    };
  }

  artifactToItem(a: Artifact): ArtifactItem {
    return <ArtifactItem>{
      path: a.fullPath(),
      description: a.description,
      type: a.type.defaultExtension,
      content: a.content,
      encoding: a.encoding,
      metadata: a.metadata
    };
  }

  recordToWorkListEntry(record: WorkListRecord): WorkListEntry {
    const commit = this.recordToCommit(record.commit);
    return {
      id: record.entityId,
      title: commit.message.slice(0, RecordService.TITLE_LENGTH),
      commit: commit,
      status: WorkListEntryStatus.fromVal(record.workListStatus)
    };
  }

  recordToCommit(record: CommitRecord): Commit {
    return {
      id: record.entityId,
      projectName: record.projectName,
      message: record.message,
      status: CommitStatus.fromVal(record.status),
      changeList: record.changeList.map(r => this.recordToCommitCandidate(r)),
      commitTime: new Date(record.commitTime),
      committer: { userName: record.committerName, id: record.committerId },
      resolutionComment: record.resolutionComment ? record.resolutionComment : null,
      resolver: record.resolverName ? { userName: record.resolverName, id: record.resolverId } : null,
      version: record.version,
    };
  }

  recordToCommitCandidate(record: CommitCandidateRecord): CommitCandidate {
    const idx = record.path.lastIndexOf('/');
    const name = record.path.substring(idx + 1);
    return {
      id: record.entityId,
      name: name,
      parentId: record.parentId,
      parentRevision: record.parentRevision,
      committedFrom: record.committedFrom,
      committed: true,
      path: record.path,
      type: ArtifactType.fromExtension(record.artifactType),
      content: record.content,
      metadata: record.metadata,
      stale: false,
      status: <CandidateStatus>record.status,
      msg: record.status,
      selected: false,
    };
  }

  artifactToCommitCandidate(artifact: Artifact): CommitCandidate {
    return <CommitCandidate>{
      id: artifact.id,
      name: artifact.name,
      committedFrom: null,
      committed: !artifact.isCheckedOutArtifact,
      parentId: artifact.parentId,
      parentRevision: artifact.checkedOutFromRevision,
      status: artifact.status,
      type: artifact.type,
      stale: artifact.stale,
      path: artifact.fullPath(),
      content: artifact.content,
      metadata: artifact.metadata,
      msg: artifact.status,
      selected: false,
    };
  }

  workListEntryRecordToCommit(record: any): Commit {
    return this.recordToCommit(record.commit);
  }

  userRecordToUser(record: UserRecord): User {
    return {
      id: record.entityId,
      userName: record.username,
      email: record.email,
      roles: record.roles
    };
  }

  artifactsToCheckoutRequest(projectName: string, artifacts: Artifact[]): CheckoutArtifactsRequest {
    return {
      projectId: undefined,
      artifactIds: undefined,
      ignoreAlreadyCheckedOutArtifacts: undefined
    };
  }

  recordToGroup(groupRecord: GroupRecord): Group {
    return {
      id: groupRecord.entityId,
      name: groupRecord.name,
      type: groupRecord.type,
      shared: groupRecord.shared,
      artifacts: groupRecord.artifacts
    };
  }

  recordDetailsToCheckedOutArtifact(record: ArtifactRecord, projectName: string): Artifact {
    const a = new Artifact();
    return a;
  }

  artifactToSaveRequest(artifact: Artifact): ArtifactItem {
    return;
  }

  workListEntryToItem(worklistEntry: WorkListEntry, status: string, comment: string): any[] {
    return;
  }

  setArtifactId(artifact: Artifact) {
  }

  recordToArtifactValidationRecord(record: ValidationProblemRecord, addArtifactPath?: boolean, artifactExtension?: string): ArtifactValidationRecord {
    return;
  }

  recordDetailsToArtifactVersions(record: ArtifactRecord, projectName: string): Artifact[] {
    return [];
  }

  commitCandidateToArtifact(commitCandidate: CommitCandidate): Artifact {
    return;
  }

}
