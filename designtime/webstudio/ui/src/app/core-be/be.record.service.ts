import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Logger } from '../core/logger.service';
import { RecordService } from '../core/record.service';
import { RestService } from '../core/rest.service';
import {
  ArtifactContent,
  ArtifactDetails
} from '../models-be/artifact-be';
import { BECommitCandidate } from '../models-be/commit-candidate-be';
import { DecisionTableSave } from '../models-be/decision-table-be/decision-table-be-save';
import { Artifact, ArtifactStatus, ArtifactType } from '../models/artifact';
import { ArtifactHistoryEntry } from '../models/artifact-history-entry';
import { CandidateStatus } from '../models/commit-candidate';
import {
  ArtifactValidationRecord,
  ArtifactVersionsRecordDetails,
  BEArtifactHistoryRecord,
  BEArtifactItem,
  BEArtifactRecord,
  BEArtifactRecordDetails,
  BECheckedOutArtifactRecord,
  BECheckoutArtifactsRequest,
  BEGroupRecord,
  BEWorkListRecord,
  CheckoutRecord,
  ProjectRecord,
  ValidationProblemRecord,
  WorkListItemRecord
} from '../models/dto';
import { Group } from '../models/group';
import { ProjectMeta } from '../models/project-meta';
import { WorkListEntry, WorkListEntryStatus } from '../models/workitem';

@Injectable()
export class BERecordService extends RecordService {

  constructor(
    protected rest: RestService,
    protected log: Logger,
    public i18n: I18n
  ) {
    super(log, i18n);
  }

  recordToProjectMeta(r: ProjectRecord): ProjectMeta {
    const meta = new ProjectMeta();
    meta.projectId = r.projectName;
    meta.projectName = r.projectName;
    return meta;
  }

  recordToArtifact(r: BEArtifactRecord, editable?: boolean, forceContent?: boolean, projectName?: string): Artifact {
    const a = new Artifact();

    if (projectName) { a.projectId = projectName; }

    let artifactPath = r.artifactPath;
    if (projectName && artifactPath.startsWith(projectName + '/')) {
      artifactPath = artifactPath.substring((projectName + '/').length - 1, artifactPath.indexOf('.'));
    }
    a.path = artifactPath;

    let artifactExtension = r.artifactType;
    if (!artifactExtension) {
      const extnIndex = r.artifactPath.indexOf('.');
      if (extnIndex > 0) {
        artifactExtension = r.artifactPath.substring(extnIndex + 1, r.artifactPath.length);
      }
    }
    if (artifactExtension) { a.type = ArtifactType.fromExtension(artifactExtension); }
    if (a.type === ArtifactType.OTHER) { this.log.err('[Set to OTHER] - ArtifactPath - ' + artifactPath + ' & ArtifactExtension - ' + artifactExtension); }

    this.setArtifactId(a);

    let artifactStatus = <ArtifactStatus>'CLEAN';
    if (r.changeType) {
      switch (r.changeType) {
        case 'Create':
        case 'Created':
          artifactStatus = <ArtifactStatus>'ADDED';
          break;
        case 'Modify':
        case 'Modified':
          artifactStatus = <ArtifactStatus>'MODIFIED';
          break;
        case 'Delete':
        case 'Deleted':
        case 'Added': // case for deleted -> revert
          artifactStatus = <ArtifactStatus>'DELETED';
          break;
        default: artifactStatus = <ArtifactStatus>'CLEAN';
      }
    }
    a.status = artifactStatus;

    a.locked = r.locked;

    if (r.baseArtifactPath) {
      a.baseArtifactPath = r.baseArtifactPath;
    }
    return a;
  }

  checkoutRecordToProjectMeta(r: CheckoutRecord): ProjectMeta {
    const meta = new ProjectMeta();
    meta.projectId = r.projectName;
    meta.projectName = r.projectName;
    meta.checkoutId = r.entityId;
    meta.checkedOutArtifactIds = r.artifacts ? r.artifacts.map(a => a.entityId) : null;
    return meta;
  }

  artifactToItem(a: Artifact): BEArtifactItem {
    return {
      artifactPath: a.fullPath(),
      description: a.description,
      artifactType: a.type.defaultExtension,
      fileExtension: a.type.defaultExtension,
      baseArtifactPath: a.baseArtifactPath ? a.baseArtifactPath : '',
      path: undefined,
      type: undefined,
      content: undefined,
      encoding: undefined,
      metadata: undefined,
      artifactContent: undefined,
      lockRequestor: undefined,
      changeType: undefined,
      lockRequestorId: undefined,
    };
  }

  artifactsToCheckoutRequest(projectName: string, artifacts: Artifact[]): BECheckoutArtifactsRequest {
    return {
      projectId: undefined,
      artifactIds: undefined,
      ignoreAlreadyCheckedOutArtifacts: undefined,
      name: projectName,
      artifactItem: artifacts.map(a => this.artifactToItem(a))
    };
  }

  recordToWorkListEntry(record: BEWorkListRecord): WorkListEntry {
    return {
      id: record.revisionId,
      title: record.checkinComments.slice(0, RecordService.TITLE_LENGTH),
      commit: {
        id: record.revisionId,
        projectName: record.managedProjectName,
        message: record.checkinComments,
        status: null,
        changeList: [],
        commitTime: new Date(record.checkinTime),
        committer: { userName: record.username, id: null },
        resolver: null,
        version: null,
      },
      status: WorkListEntryStatus.UNRESOLVED
    };
  }

  recordToCommitCandidate(record: WorkListItemRecord): BECommitCandidate {
    const idx = record.artifactPath.lastIndexOf('/');
    const name = record.artifactPath.substring(idx + 1);
    return {
      id: null,
      name: name,
      parentId: null,
      parentRevision: null,
      committedFrom: null,
      committed: true,
      path: record.artifactPath,
      content: null,
      stale: false,
      type: ArtifactType.fromExtension(record.artifactType),
      metadata: null,
      status: this.getCandidateStatus(record.commitOperation),
      msg: null,
      selected: false,
      artifactType: record.artifactType,
      artifactFileExtn: record.artifactFileExtn,
      applicableStages: record.applicableStages,
      reviewStatus: record.reviewStatus,
      applicableEnvironments: record.applicableEnvironments,
      deployEnvironments: record.deployEnvironments,
      reviewComments: record.reviewComments,
      revisionId: record.revisionId,
      deployComments: record.deployComments,
      lastDeployTime: record.lastDeployTime,
      deployerName: record.deployerName,
      reviewerName: record.reviewerName
    };
  }

  getCandidateStatus(recordStatus: string): CandidateStatus {
    let candidateStatus;

    switch (recordStatus) {
      case 'Create': candidateStatus = 'ADDED'; break;
      case 'Modify': candidateStatus = 'MODIFIED'; break;
      case 'Delete': candidateStatus = 'DELETED'; break;
      default: candidateStatus = 'CLEAN'; break;
    }

    return <CandidateStatus>candidateStatus;
  }

  recordToCheckedOutArtifact(r: BECheckedOutArtifactRecord, forceContent?: boolean, projectName?: string, isSync?: boolean): Artifact {
    if (!projectName) { projectName = r.artifactPath.substring(0, r.artifactPath.indexOf('/')); }
    const ca = this.recordToArtifact(r, true, forceContent, projectName);
    ca.isCheckedOutArtifact = true;
    // case for Add + Sync
    if (isSync && r.changeType === 'Added') { ca.status = <ArtifactStatus>'ADDED'; }

    return ca;
  }

  recordDetailsToCheckedOutArtifact(record: BEArtifactRecordDetails, projectName?: string): Artifact {
    const a = new Artifact();
    if (record.artifactDetails) {
      a.path = record.artifactDetails.artifactPath;
      if (record.artifactDetails.artifactType) {
        a.type = ArtifactType.fromExtension(record.artifactDetails.artifactType);
        a.content = JSON.stringify(record.artifactDetails.artifactContent, this.systemPropertyReplacer);
      } else {
        if (record.artifactDetails.artifactPath) {
          a.type = ArtifactType.fromExtension('rulefunctionimpl');
          a.content = DecisionTableSave.toSaveJson(JSON.stringify(record.artifactDetails, this.systemPropertyReplacer));
        } else {
          a.type = ArtifactType.fromExtension('rulefunction');
          a.content = JSON.stringify(record.artifactDetails, this.systemPropertyReplacer);
        }
      }
    }
    a.status = <ArtifactStatus>'CLEAN';
    a.isCheckedOutArtifact = true;
    if (projectName) { a.projectId = projectName; }
    this.setArtifactId(a);
    a.projectId = projectName;

    return a;
  }

  systemPropertyReplacer(key: string, value: string): any {
    if (key === 'attributes') { return undefined; }
    if (key === 'visited') { return undefined; }
    return value;
  }

  artifactToSaveRequest(artifact: Artifact): BEArtifactItem {
    const artifactItem = {
      artifactPath: artifact.path,
      artifactType: artifact.type.defaultExtension,
      baseArtifactPath: undefined,
      fileExtension: artifact.type.defaultExtension,
      implementsPath: undefined,
      isSyncMerge: undefined,
      artifactContent: undefined,
      content: undefined,
      description: undefined,
      path: undefined,
      type: undefined,
      encoding: undefined,
      metadata: undefined,
      lockRequestor: undefined,
      changeType: undefined,
      rulePriority: undefined,
      lockRequestorId: undefined
    };

    if (artifact.content) {
      const parsedJSON = JSON.parse(artifact.content);

      if (artifact.type === ArtifactType.DOMAIN_MODEL) {
        artifactItem.artifactContent = artifact.content;

      } else if (artifact.type === ArtifactType.RULE_TEMPLATE_INSTANCE) {
        if (parsedJSON) {
          if (parsedJSON.view && parsedJSON.view.bindingInfo.length > 0) {
            const viewContentJSON = JSON.parse(JSON.stringify(parsedJSON.view, this.createRTIViewArtifactContentReplacer));
            const viewJSON = JSON.parse('{}');
            viewJSON.view = viewContentJSON;
            artifactItem.artifactContent = JSON.stringify(viewJSON);
          } else {
            artifactItem.artifactContent = JSON.stringify(this.createRTIBuilderArtifactContent(parsedJSON), this.createRTIBuilderArtifactContentReplacer);
          }
          artifactItem.description = parsedJSON.description,
            artifactItem.rulePriority = parsedJSON.rulePriority;
        }
      } else if (artifact.type === ArtifactType.BE_DECISION_TABLE) {
        artifactItem.artifactContent = JSON.stringify(this.createDTArtifactContent(parsedJSON));
      }

      artifactItem.implementsPath = parsedJSON.implementsPath ? parsedJSON.implementsPath : undefined;
      artifactItem.isSyncMerge = parsedJSON.isSyncMerge ? parsedJSON.isSyncMerge : 'false';
      artifactItem.artifactContent = artifactItem.artifactContent.replace(/\\"/g, '\\"');
    }
    return artifactItem;
  }

  recordToArtifactHistoryEntry(artifactId: string, r: BEArtifactHistoryRecord): ArtifactHistoryEntry {
    return <ArtifactHistoryEntry>{
      commitId: r.revisionId + '',
      artifactId: artifactId,
      version: +r.revisionId,
      commitMessage: r.checkinComments
    };
  }

  createRTIViewArtifactContentReplacer(key: string, value: string): any {
    if (key === 'style'
      || key === 'type'
      || key === 'domainInfo'
      || key === 'htmlText') { return undefined; }
    return value;
  }

  createRTIBuilderArtifactContent(parsedJSON): any {
    const artifactContentJson = {
      builder: {
        conditions: parsedJSON.conditions,
        commands: parsedJSON.commands
      }
    };

    return artifactContentJson;
  }

  createRTIBuilderArtifactContentReplacer(key: string, value: string): any {
    if (key === 'symbols') { return undefined; }
    return value;
  }

  createDTArtifactContent(parsedJSON): any {
    const artifactContentJson = {
      decisionTableSave: parsedJSON
    };

    return artifactContentJson;
  }

  workListEntryToItem(worklistEntry: WorkListEntry, status: string, comment: string): any[] {
    const worklistItems = [];

    if (worklistEntry.commit.changeList) {
      for (const no in worklistEntry.commit.changeList) {
        const changeEntry = worklistEntry.commit.changeList[no];

        const worklistItem = {
          artifactPath: changeEntry.path,
          artifactType: changeEntry.type.defaultExtension,
          managedProjectName: worklistEntry.commit.projectName,
          reviewStatus: status,
          reviewComments: comment
        };

        worklistItems.push({ revisionId: worklistEntry.id, worklistItem: [worklistItem] });
      }
    }

    return worklistItems;
  }

  setArtifactId(artifact: Artifact) {
    artifact.id = (this.rest.userName + '@' + artifact.projectId + '@' + artifact.path + '@' + artifact.type.defaultExtension);
  }

  recordToGroup(groupRecord: BEGroupRecord): Group {
    return {
      id: groupRecord.entityId,
      name: groupRecord.name,
      type: groupRecord.fileType,
      artifacts: groupRecord.artifactIds,
      shared: false
    };
  }

  recordToArtifactValidationRecord(record: ValidationProblemRecord, addArtifactPath?: boolean, artifactExtension?: string): ArtifactValidationRecord {
    const problems = [];

    if (record.artifactDetails && record.artifactDetails.problem && record.artifactDetails.problem.length > 0) {
      record.artifactDetails.problem.forEach(element => {
        const rowIndex: number = isNaN(element.location) ? 0 : element.location;
        const colIndex: number = isNaN(element.columnName) ? 0 : element.columnName;
        let columnName: string = (element.columnUIName) ? element.columnUIName : '' + element.location;
        let location = 'Location - ';
        if (element.errorCode) {
          if (element.errorCode < 200) { location = location + '[When] '; } else if (element.errorCode > 200 && element.errorCode < 300) { location = location + '[Then] '; } else if (element.errorCode === 311) { columnName = element.errorMessage.split('(')[1].split(')')[0]; }
        }
        let err = true;
        if (element.severity === 1 || element.isWarning) {
          err = false;
        }
        location = location + columnName;
        let problemMsg = '';
        problemMsg = record.artifactDetails.artifactPath + '@';
        problemMsg += element.errorMessage + '@' + location + '@' + 'Type - ' + element.problemType;
        if (record.artifactDetails.artifactType === ArtifactType.BE_DECISION_TABLE.defaultExtension) {
          const result = {
            message: problemMsg,
            locations: [{ 0: rowIndex, 1: colIndex }],
            error: err,
            artifactPath: record.artifactDetails.artifactPath,
            projectName: record.artifactDetails.projectName,
            artifactType: record.artifactDetails.artifactType ? record.artifactDetails.artifactType : artifactExtension ? artifactExtension : null
          };
          problems.push(JSON.stringify(result));
        } else {
          const result = {
            message: problemMsg,
            locations: [{ rowIndex: columnName }],
            error: err,
            artifactPath: record.artifactDetails.artifactPath,
            projectName: record.artifactDetails.projectName,
            artifactType: record.artifactDetails.artifactType ? record.artifactDetails.artifactType : artifactExtension ? artifactExtension : null
          };
          problems.push(JSON.stringify(result));
        }
      });
    }

    return {
      entityId: '',
      analyzeResult: problems
    };
  }

  recordDetailsToArtifactVersions(record: ArtifactVersionsRecordDetails, projectName: string): Artifact[] {
    const artifactVersions: Artifact[] = [];
    if (record.currentVersionContents) { artifactVersions.push(this.getArtifact(projectName, record.artifactPath, record.artifactType, record.currentVersionContents)); }
    if (record.previousVersionContents) { artifactVersions.push(this.getArtifact(projectName, record.artifactPath, record.artifactType, record.previousVersionContents)); }
    if (record.serverContents) { artifactVersions.push(this.getArtifact(projectName, record.artifactPath, record.artifactType, record.serverContents)); }
    return artifactVersions;
  }

  commitCandidateToArtifact(commitCandidate: BECommitCandidate): Artifact {
    const artifact: Artifact = new Artifact();

    artifact.id = commitCandidate.id;
    artifact.path = commitCandidate.path;
    artifact.type = commitCandidate.type;
    artifact.description = '';
    artifact.isCheckedOutArtifact = true;
    artifact.projectId = commitCandidate.id.split('@')[1];
    artifact.status = (commitCandidate.status === 'CLEAN' || commitCandidate.status === 'ADDED') ?
      'ADDED' : (commitCandidate.status === 'MODIFIED') ?
        'MODIFIED' : 'DELETED';
    return artifact;
  }

  private getArtifact(projectName: string, artifactPath: string, artifactType: string, content: any) {
    const a = new Artifact();
    a.path = artifactPath;
    a.type = ArtifactType.fromExtension(artifactType);
    a.content = JSON.stringify(content, this.systemPropertyReplacer);
    a.isCheckedOutArtifact = true;
    if (projectName) { a.projectId = projectName; }
    this.setArtifactId(a);
    a.projectId = projectName;

    return a;
  }
}
