
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { of as observableOf, Observable } from 'rxjs';
import { filter, map, mergeMap } from 'rxjs/operators';

import { ArtifactService } from './artifact.service';
import { Logger } from './logger.service';
import { RecordService } from './record.service';
import { RestService } from './rest.service';

import { Artifact } from '../models/artifact';
import { Commit } from '../models/commit';
import { ArtifactValidationRecord } from '../models/dto';
import { Project } from '../models/project';
import { ProjectMeta } from '../models/project-meta';

export class DeployStatus {
  status: boolean;
  errorCnt: number;
  warningCnt: number;
  errorMsg: string;
  warningMsg: string;
}

@Injectable()
export class ProjectService {

  projectMetaCache = new Map<string, ProjectMeta>();

  constructor(
    protected log: Logger,
    protected artifact: ArtifactService,
    protected rest: RestService,
    protected record: RecordService,
    public i18n: I18n
  ) {
  }

  clear() {
    this.projectMetaCache.clear();
  }

  getCheckedOutProjects(): Promise<ProjectMeta[]> {
    const url = `/lifecycle/checkouts/`;
    return this.rest.get(url).toPromise()
      .then(result => {
        if (result.ok()) {
          const metas = result.record
            .map(record => this.record.checkoutRecordToProjectMeta(record))
            .filter(meta => meta.hasCheckedOutArtifact);
          metas.forEach(p => this.projectMetaCache.set(p.projectId, p));
          return metas.sort((a, b) => a.projectName.localeCompare(b.projectName));
        }
      });
  }

  getAllProjects(): Promise<ProjectMeta[]> {
    const url = `/projects/`;
    return this.rest.get(url).pipe(
      filter(res => res.ok()),
      map(res => {
        const metas = res.record.map(r => this.record.recordToProjectMeta(r));
        this.projectMetaCache.clear();
        metas.forEach(p => this.projectMetaCache.set(p.projectId, p));
        return metas.sort((a, b) => a.projectName.localeCompare(b.projectName));
      }))
      .toPromise();
  }

  getProjectMeta(id: string): ProjectMeta {
    if (this.projectMetaCache.has(id)) {
      return this.projectMetaCache.get(id);
    } else {
      throw Error(this.i18n('Unable to find the project meta in cache, something is wrong'));
    }
  }

  addProject(p: Project): Promise<boolean> {
    const payload = {
      artifacts: p.getArtifacts().map(a => this.record.artifactToItem(a)),
    };
    const validateMeta = p.getArtifacts().map(a => this.artifact.validateMetadata(a.name, a.metadata)).reduce((x, y) => x && y, true);
    if (validateMeta) {
      const url = `/projects/${p.getMeta().projectName}/import`;
      return this.rest.post(url, payload).pipe(map(res => {
        if (res.ok()) {
          const updated = this.record.recordToProjectMeta(res.record[0]);
          this.projectMetaCache.set(updated.projectId, updated);
          this.artifact.markProjectAsAdded(updated);
        }
        return res.ok();
      })).toPromise();
    } else {
      return Promise.resolve(false);
    }
  }

  deleteProject(p: ProjectMeta): Promise<boolean> {
    return this.getAllThenMerge(p.projectId)
      .then((artifacts: Artifact[]) =>
        this.rest.delete(`/projects/${p.projectId}`)
          .toPromise()
          .then(res => {
            if (res.ok()) {
              artifacts.forEach(a => this.artifact.markAsDeleted(a));
              this.projectMetaCache.delete(p.projectId);
              this.artifact.markProjectAsDeleted(p);
              return res.ok();
            } else {
              return false;
            }
          }));
  }

  getProjectCommits(projectId: string): Promise<Commit[]> {
    return this.rest.get(`/projects/${projectId}/commits?status=APPROVED`, {}).pipe(
      map(res => res.record.map(r => this.record.recordToCommit(r))),
      map(cmts => cmts.sort((a, b) => b.commitTime.getTime() - a.commitTime.getTime())))
      .toPromise();
  }

  addToCheckout(projectId: string, artifact: Artifact): Promise<Artifact> {
    // get the checkout id
    // create the artifact in the checkout
    if (this.artifact.validateMetadata(artifact.name, artifact.metadata)) {
      return this.getProjectCheckoutId(projectId).pipe(
        mergeMap(checkoutId =>
          this.rest.post(`/artifacts/${checkoutId}/add`, { artifact: this.record.artifactToItem(artifact) })),
        map(res => {
          if (res.ok()) {
            const updated = this.record.recordToCheckedOutArtifact(res.record[0]);
            this.artifact.updateMeta(updated);
            this.artifact.markAsAdded(updated);
            return updated;
          } else {
            return null;
          }
        }))
        .toPromise();
    } else {
      return Promise.resolve(null);
    }
  }

  /**
   * Get all artifacts, including checked-out and non-checked-out,
   * then merge then into same umberalla, not including content
   * @return {Observable<Artifact[]>}              [description]
   */
  getAllThenMerge(id: string): Promise<Artifact[]> {
    return this.getCheckedOutArtifacts(id).pipe(
      mergeMap(cas => {
        return this.getUmbrellaArtifacts(id).pipe(
          map(uas => {
            const valid = uas.filter(a => {
              const found = cas.find(ca => ca.parentId === a.id);
              return found == null;
            });
            return cas
              .filter(ca => ca.status !== 'DELETED')
              .concat(valid)
              .sort((a, b) => a.name.localeCompare(b.name));
          }));
      }))
      .toPromise();
  }

  /**
   * Not including content
   * includeMarkDeleted conditionally include any checkedout but marked for deleted artifacts as well
   * @return {Observable<Artifact[]>}              [description]
   */
  getCheckedOutArtifacts(id: string, includeMarkDeleted?: boolean): Observable<Artifact[]> {
    return this.getProjectCheckoutId(id).pipe(
      mergeMap(checkoutId => { // another request to get artifacts in the checkout
        if (checkoutId) {
          return this.rest
            .get(`lifecycle/checkouts/${checkoutId}`).pipe(
              map(res => res.record[0].artifacts.map(r => this.record.recordToCheckedOutArtifact(r))));
        } else {
          return observableOf([]);
        }
      }));
  }

  getProjectCheckoutId(id: string): Observable<string> {
    const meta = this.getProjectMeta(id);
    if (meta.checkoutId) {
      return observableOf(meta.checkoutId);
    } else {
      return this.rest
        .get(`projects/${id}/checkout`).pipe(
          map(res => {
            if (res.ok()) {
              return res.record[0].entityId;
            }
          }));
    }
  }

  /**
   * Not including content
   */
  getUmbrellaArtifacts(id: string): Observable<Artifact[]> {
    const url = `/projects/${id}/artifacts`;
    return this.rest
      .get(url).pipe(
        map(res => {
          const artifacts = res.record
            .map(r => {
              const a = this.record.recordToArtifact(r);
              return a;
            })
            .sort((a, b) => a.name.localeCompare(b.name));
          return artifacts;
        }));
  }

  generateDeployable(projectId: string, generateClasses?: boolean, doEarHotDeployment?: boolean): Promise<DeployStatus> {
    return;
  }

  validateProject(projectId: string): Promise<ArtifactValidationRecord[]> {
    return;
  }
}
