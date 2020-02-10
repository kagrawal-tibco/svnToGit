
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';

import { ArtifactService } from '../core/artifact.service';
import { GroupService } from '../core/group.service';
import { Logger } from '../core/logger.service';
import { DeployStatus, ProjectService } from '../core/project.service';
import { RecordService } from '../core/record.service';
import { RestService } from '../core/rest.service';
import { Artifact } from '../models/artifact';
import { ArtifactValidationRecord } from '../models/dto';
import { ProjectMeta } from '../models/project-meta';

@Injectable()
export class BEProjectService extends ProjectService {

  loadGroups: boolean;

  constructor(
    protected log: Logger,
    protected artifact: ArtifactService,
    protected rest: RestService,
    protected record: RecordService,
    protected group: GroupService,
    public i18n: I18n
  ) {
    super(log, artifact, rest, record, i18n);
  }

  clear() {
    this.projectMetaCache.clear();
    this.loadGroups = false;
  }

  getCheckedOutProjects(): Promise<ProjectMeta[]> {
    return this.rest.get('workspace.json').toPromise()
      .then(result => {
        // create groups when checking for workspace
        if (!this.loadGroups) {
          this.group.getGroups();
          this.loadGroups = true;
        }

        if (result.ok()) {
          const metas = result.record
            .map(record => this.record.checkoutRecordToProjectMeta(record));
          metas.forEach(p => this.projectMetaCache.set(p.projectId, p));
          return metas.sort((a, b) => a.projectName.localeCompare(b.projectName));
        }
      });
  }

  getAllProjects(): Promise<ProjectMeta[]> {
    return this.rest.get('projects.json').pipe(
      filter(res => res.ok()),
      map(res => {
        const metas = res.record.map(r => this.record.recordToProjectMeta(r));
        this.projectMetaCache.clear();
        metas.forEach(p => this.projectMetaCache.set(p.projectId, p));
        return metas.sort((a, b) => a.projectName.localeCompare(b.projectName));
      }))
      .toPromise();
  }

  getAllRepoProjects(): Promise<ProjectMeta[]> {
    return this.rest.get('allProjects.json').pipe(
      filter(res => res.ok()),
      map(res => {
        const metas = res.record.map(r => this.record.recordToProjectMeta(r));
        this.projectMetaCache.clear();
        metas.forEach(p => this.projectMetaCache.set(p.projectId, p));
        return metas.sort((a, b) => a.projectName.localeCompare(b.projectName));
      }))
      .toPromise();
  }

  getProjectsForLockMgmt(): Promise<ProjectMeta[]> {
    return this.rest.get('projectsLockMgmt.json').pipe(
      filter(res => res.ok()),
      map(res => {
        const metas = res.record.map(r => this.record.recordToProjectMeta(r));
        this.projectMetaCache.clear();
        metas.forEach(p => this.projectMetaCache.set(p.projectId, p));
        return metas.sort((a, b) => a.projectName.localeCompare(b.projectName));
      }))
      .toPromise();
  }

  getAllThenMerge(id: string): Promise<Artifact[]> {
    const projectName = this.getProjectMeta(id).projectName;
    return this.rest.get(`projects/${projectName}/artifacts.json`).pipe(
      map(res => {
        const artifacts = res.record
          .map(r => {
            const a = this.record.recordToArtifact(r);
            return a;
          })
          .sort((a, b) => a.name.localeCompare(b.name));
        return artifacts;
      }))
      .toPromise();
  }

  getCheckedOutArtifacts(id: string, includeMarkDeleted?: boolean): Observable<Artifact[]> {
    const projectName = this.getProjectMeta(id).projectName;
    return this.group.getGroupArtifacts('Projects', projectName, includeMarkDeleted);
  }

  addToCheckout(projectId: string, artifact: Artifact): Promise<Artifact> {
    artifact.projectId = projectId;
    return this.artifact.updateCheckoutArtifact(artifact)
      .then(result => {
        if (result) {
          artifact.isCheckedOutArtifact = true;
          this.record.setArtifactId(artifact);
          this.artifact.updateMeta(artifact);
          this.artifact.markAsAdded(artifact);
          return artifact;
        } else {
          return null;
        }
      });
  }

  deleteProject(p: ProjectMeta): Promise<boolean> {
    const payload = {
      request: {
        data: {
          project: [{
            name: p.projectName
          }]
        }
      }
    };

    // return this.getAllThenMerge(p.projectId)
    //   .then((artifacts: Artifact[]) =>
    //     this.rest.delete(`artifact/delete.json`, payload).toPromise().then(res => {
    //       if (res.ok()) {
    //         artifacts.forEach(a => this.artifact.markAsDeleted(a));
    //         this.projectMetaCache.delete(p.projectId);
    //       }
    //       return res.ok();
    //     }));

    return this.getCheckedOutArtifacts(p.projectName).toPromise().then((artifacts: Artifact[]) => {
      return this.rest.delete(`artifact/delete.json`, payload).toPromise().then(res => {
        if (res.ok()) {
          artifacts.forEach(a => this.artifact.markAsDeleted(a));
          this.projectMetaCache.delete(p.projectId);
        }
        return res.ok();
      });
    });
  }

  generateDeployable(projectId: string, generateClasses?: boolean, doEarHotDeployment?: boolean): Promise<DeployStatus> {
    const queryString = '?buildClassesOnly=' + generateClasses + '&doEarHotDeployment=' + doEarHotDeployment;
    return this.rest.get(`projects/${projectId}/generateDeployable.json${queryString}`, undefined, true).toPromise()
      .then(res => {
        const deployStatus = new DeployStatus();
        deployStatus.status = res.ok();
        if (res.record && res.record.length === 1 && res.record[0].errorMessage) {
          deployStatus.warningCnt = 1;
          deployStatus.warningMsg = res.record[0].errorMessage;
        }
        return deployStatus;
      });
  }

  validateProject(projectId: string): Promise<ArtifactValidationRecord[]> {
    return this.rest
      .get(`artifact/validate.json?projectName=${projectId}`, undefined, true).pipe(
        map(res => {
          if (res.ok()) {
            return res.record.map(r => this.record.recordToArtifactValidationRecord(r, true));
          }
        }))
      .toPromise();
  }
}
