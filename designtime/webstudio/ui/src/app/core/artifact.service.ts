
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from 'environments/environment';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { AlertService } from './alert.service';
import { Logger } from './logger.service';
import { RecordService } from './record.service';
import { RestService } from './rest.service';
import { SettingsService } from './settings.service';

import { Artifact, ArtifactType } from '../models/artifact';
import { ArtifactHistoryEntry } from '../models/artifact-history-entry';
import { CommitCandidate } from '../models/commit-candidate';
import { ArtifactValidationRecord, LockRecord, ValidateArtifactRequest } from '../models/dto';
import { ProjectMeta } from '../models/project-meta';

const LATEST_VERSION = -1;

export enum SynchronizeStrategy {
  LATEST,
  MERGE,
}

export interface StateChange {
  // the artifact that has state changes. this can be null, means all artifacts are affected.
  artifact?: Artifact;
  // optionally the latest state of the artifact. if it's null, the consumer shall figure out how to get the latest state.
  updated?: Artifact;
  // optionally the ProjectMeta tied to this state change.  Required if the artifact is null
  project?: ProjectMeta;
  // if the artifact cannot be retrieved, then send a notification by artifact id
  id?: string;
  /**
   * UPDATED: need to talk to server to re-fetch the artifact info.
   * DELETED: artifact is deleted, shall be hidden from UI.
   * DISPOSED: checked-out artifact is disposed, shall replace it with its parent's latest version.
   * RERENDER: just re-render the artifact. No need to talk the the server.
   * CHECKED-OUT: artifact is checked out, the artifact field contains is the checked-out artifact.
   * ADDED: artifact that newly added
   *
   */
  state: 'UPDATED' | 'DELETED' | 'DISPOSED' | 'RERENDER' | 'CHECKED-OUT' | 'ADDED' | 'MODIFIED';
}

@Injectable()
export class ArtifactService {
  // only cache artifact with content
  public checkedOutArtifactCache = new Map<string, Artifact>();

  // umbrella artifact id -> revision -> id
  public artifactRevisionCache = new Map<string, Map<number, Artifact>>();

  // artifact revision id -> object
  public idToRevisionCache = new Map<string, Artifact>();

  private _stateChanges: Subject<StateChange> = new Subject<StateChange>();

  constructor(
    protected rest: RestService,
    protected record: RecordService,
    protected settings: SettingsService,
    protected alert: AlertService,
    protected log: Logger,
    public i18n: I18n
  ) { }

  init() {
    this.initImpl();
  }

  clear() {
    this.checkedOutArtifactCache.clear();
    this.artifactRevisionCache.clear();
    this.idToRevisionCache.clear();
  }

  getCheckedOutArtifactWithContent(id: string): Promise<Artifact> {
    return this.getCheckedOutArtifact(id, true);
  }

  getCheckedOutArtifact(id: string, includeContent: boolean): Promise<Artifact> {
    if (this.checkedOutArtifactCache.has(id)) {
      const lhs = this.checkedOutArtifactCache.get(id);
      if (lhs.cacheStale) {
        return this.requestCheckedOutArtifact(id);
      } else {
        return Promise.resolve(this.checkedOutArtifactCache.get(id));
      }
    } else {
      return this.requestCheckedOutArtifact(id, includeContent);
    }
  }

  getArtifactLockValueCache(id: string): boolean {
    if (this.checkedOutArtifactCache.has(id)) {
      const lhs = this.checkedOutArtifactCache.get(id);
      return lhs.locked ? lhs.locked : false;
    }
    return false;
  }

  getArtifactRevisionWithContent(id: string, revision: number): Promise<Artifact> {
    return this.getArtifactRevision(id, revision, true);
  }

  getArtifactRevision(id: string, revision: number, includeContent: boolean): Promise<Artifact> {
    if (!revision) {
      return Promise.resolve(null);
    } else {
      if (!this.artifactRevisionCache.has(id)) {
        this.artifactRevisionCache.set(id, new Map<number, Artifact>());
      }
      const revisions = this.artifactRevisionCache.get(id);
      if (revision !== LATEST_VERSION
        && revisions.has(revision)
        && (revisions.get(revision).isBinary || revisions.get(revision).content)
      ) {
        return Promise.resolve(revisions.get(revision));
      } else {
        return this.requestArtifactRevision(id, revision, includeContent)
          .then(artifact => {
            revisions.set(artifact.revisionNumber, artifact);
            return artifact;
          });
      }
    }
  }

  getCommitCandidateWithContent(id: string, committed?: boolean): Promise<CommitCandidate> {
    return (committed ? this.getArtifactRevisionById(id) : this.getCheckedOutArtifactWithContent(id))
      .then(artifact => this.record.artifactToCommitCandidate(artifact), (err) => null);
  }

  cacheCheckedOutArtifact(artifact: Artifact): Artifact {
    if (!this.checkedOutArtifactCache.has(artifact.id)) {
      this.checkedOutArtifactCache.set(artifact.id, artifact);
    }
    return artifact;
  }

  removeCheckedOutArtifactFromCache(artifactId: string) {
    if (this.checkedOutArtifactCache.has(artifactId)) {
      this.checkedOutArtifactCache.delete(artifactId);
    }
  }

  // update metadata and the diff base if available.
  updateMeta(artifact: Artifact, includeBuffer?: boolean): Promise<any> {
    if (artifact.isCheckedOutArtifact) {
      if (!this.checkedOutArtifactCache.has(artifact.id)) {
        this.checkedOutArtifactCache.set(artifact.id, artifact);
      } else {
        const lhs = this.checkedOutArtifactCache.get(artifact.id);
        return this.requestCheckedOutArtifact(lhs.id)
          .then(rhs => {
            lhs.updateMeta(rhs);
            lhs.cacheStale = false;
            lhs.content = rhs.content;
          });
      }
    } else {
      if (!this.artifactRevisionCache.has(artifact.id)) {
        throw this.i18n('Cannot update artifact which is not cached.');
      } else {
        const revisions = this.artifactRevisionCache.get(artifact.id);
        return this.requestArtifactRevision(artifact.id, artifact.revisionNumber, false)
          .then(updated => revisions.set(artifact.revisionNumber, updated));
      }
    }
  }

  syncWithCache(artifact: Artifact): Artifact {
    if (artifact) {
      // do this anyway so that the artifact gets cached
      const cur = artifact.status;
      let ret: Artifact;
      if (artifact.isCheckedOutArtifact) {
        ret = this.checkedOutArtifactCache.get(artifact.id);
        if (!ret && artifact.content) { // can't cache without content -- but still need it cached.  What to do?
          this.cacheCheckedOutArtifact(artifact);
        }
        if (ret && artifact.locked != undefined) {
          ret.locked = artifact.locked ? true : false;
        }
      } else {
        const revisions = this.artifactRevisionCache.get(artifact.id);
        if (revisions) {
          ret = revisions.get(artifact.revisionNumber);
        } else {
          const revMap = new Map<number, Artifact>();
          this.artifactRevisionCache.set(artifact.id, revMap);
          revMap.set(artifact.revisionNumber, artifact);
        }
      }
      if (ret && artifact !== ret) {
        if (artifact.revisionNumber === ret.revisionNumber) {
          /* Below line of code has created an issue with saving of the BE artifacts for the first time.
             Hence, removing it for BE artifacts.
        */
          if (!environment.enableBEUI) {
            ret.updateMeta(artifact, artifact.content !== undefined);
          }
          return ret;
        }
        if (artifact.latestRevision && ret.latestRevision) {
          return ret.latestRevision > artifact.latestRevision ? ret : artifact;
        }
      }
      return artifact;
    }
  }

  stateChanges(): Observable<StateChange> {
    return this._stateChanges;
  }

  markArtifactsStaleByProject(project: string) {
    this.checkedOutArtifactCache.forEach(entry => {
      if (entry.id.indexOf(project) !== -1) {
        entry.cacheStale = true;
      }
    });
  }

  createArtifactInfo(includeContent?: boolean, artifactType?: ArtifactType, artifactContent?: string, baseArtifactPath?: string): Artifact {
    const res = new Artifact();
    res.path = '';
    res.encoding = 'NONE';
    if (baseArtifactPath) {
      res.baseArtifactPath = baseArtifactPath;
    }
    return this.repopulateArtifactInfo(res, includeContent, artifactType, artifactContent);
  }

  repopulateArtifactInfo(res: Artifact, includeContent?: boolean, artifactType?: ArtifactType, artifactContent?: string): Artifact {
    switch (artifactType) {
      case ArtifactType.RULE_TEMPLATE_INSTANCE:
        res.type = ArtifactType.RULE_TEMPLATE_INSTANCE;
        if (artifactContent) {
          res.content = artifactContent;
        } else {
          res.content = includeContent ? ArtifactType.RULE_TEMPLATE_INSTANCE.defaultContent : '';
        }
        break;
      case ArtifactType.RULE_TEMPLATE:
        res.type = ArtifactType.RULE_TEMPLATE;
        res.content = '';
        break;
      case ArtifactType.DOMAIN_MODEL:
        res.type = ArtifactType.DOMAIN_MODEL;
        res.content = includeContent ? ArtifactType.DOMAIN_MODEL.defaultContent : '';
        break;
      case ArtifactType.BE_DECISION_TABLE:
        res.type = ArtifactType.BE_DECISION_TABLE;
        if (artifactContent) {
          res.content = artifactContent;
        } else {
          res.content = includeContent ? ArtifactType.BE_DECISION_TABLE.defaultContent : '';
        }
        break;
      default:
        res.type = artifactType ? artifactType : ArtifactType.SB_DECISION_TABLE;
        if (includeContent) {
          res.content = (artifactContent ? artifactContent : res.type.defaultContent);
        } else {
          res.content = '';
        }
    }
    return res;
  }

  getArtifactLatest(artifactId: string, includeContent?: boolean): Promise<Artifact> {
    return this.getArtifactRevision(artifactId, LATEST_VERSION, includeContent);
  }

  getArtifactRevisionById(id: string): Promise<Artifact> {
    if (this.idToRevisionCache.has(id) && !this.idToRevisionCache.get(id).cacheStale) {
      return Promise.resolve(this.idToRevisionCache.get(id));
    } else {
      return this.rest.get(`/artifacts/revisions/${id}`).pipe(
        map(res => {
          if (res.ok()) {
            const artifact = this.record.recordToArtifact(res.record[0]);
            this.idToRevisionCache.set(artifact.revisionId, artifact);
            return artifact;
          }
        }))
        .toPromise();
    }
  }

  getUserLocks(all?: boolean): Promise<LockRecord[]> {
    const url = all ? `/artifacts/locks?all=true` : `/artifacts/locks`;
    return this.rest.get(url).pipe(
      map(res => {
        if (res.ok()) {
          return res.record;
        }
      }))
      .toPromise();
  }

  getLockOwner(artifact: Artifact): Promise<string> {
    return this.getUserLocks(true)
      .then(res => {
        const lockedArt = res.find(rec => rec.artifactId === artifact.parentId);
        if (lockedArt) {
          return Promise.resolve(lockedArt.user);
        }
        return Promise.resolve('');
      });
  }

  lockArtifact(artifact: Artifact, tag?: string): Promise<any> {
    const payload = {
      tag: tag,
    };
    const id = artifact.isCheckedOutArtifact ? artifact.parentId : artifact.id;

    return this.rest.put(`/artifacts/${id}/lock`, payload).pipe(
      map(res => {
        if (res.ok()) {
          return { success: true, message: res.responseMessage };
        } else {
          return { success: false, message: res.errorMessage };
        }
        // console.log(res.responseMessage);
      }))
      .toPromise();
  }

  unlockArtifact(artifact: Artifact, force?: boolean): Promise<any> {
    const id = artifact.isCheckedOutArtifact ? artifact.parentId : artifact.id;
    let url = `/artifacts/${id}/unlock`;
    if (force) {
      url += '?force=true';
    }
    return this.rest.put(url, null).pipe(
      map(res => {
        if (res.ok()) {
          return { success: true, message: res.responseMessage };
        } else {
          return { success: false, message: res.errorMessage };
        }
        // console.log(res.responseMessage);
      }))
      .toPromise();
  }

  checkoutArtifact(artifactId: string, showMessage?: boolean): Promise<Artifact> {
    const payload = {
      artifactIds: [
        artifactId
      ],
    };
    if (showMessage === undefined) {
      showMessage = true; // by default show a success message
    }
    return this.rest.put(`/artifacts/checkout`, payload, undefined, false, showMessage)
      .toPromise()
      .then(res => {
        if (res.ok()) {
          const checkout: Artifact = res.record[0].artifacts.map(
            r => this.record.recordToCheckedOutArtifact(r, true)
          )[0];
          return checkout;
        } else {
          return Promise.reject<Artifact>(res);
        }
      }
      );
  }

  validateArtifact(artifact: Artifact): Promise<ArtifactValidationRecord> {
    const payload = <ValidateArtifactRequest>{
      artifactId: artifact.id,
      isCheckedOut: artifact.isCheckedOutArtifact
    };

    return this.rest.post(`/artifacts/validate`, payload)
      .toPromise()
      .then(result => {
        if (result.ok()) {
          return result.record[0];
        }
      });
  }

  checkoutArtifacts(artifacts: Artifact[], projectName?: string): Promise<Artifact[]> {
    const payload = {
      artifactIds: artifacts.map(a => a.id),
    };
    return this.rest.put(`/artifacts/checkout`, payload).pipe(
      map(res => res.record[0].artifacts.map(r => this.record.recordToCheckedOutArtifact(r, true))))
      .toPromise();
  }
  updateCheckoutArtifact(artifact: Artifact): Promise<boolean> {
    if (this.validateMetadata(artifact.name, artifact.metadata)) {
      const payload = {
        artifact: this.record.artifactToItem(artifact),
        performValidation: this.settings.latestSettings.autoArtifactValidation
      };
      return this.rest.put(`/artifacts/${artifact.id}/update`, payload).pipe(
        map(res => {
          if (res.ok()) {
            const updated = this.record.recordToCheckedOutArtifact(res.record[0]);
            artifact.updateMeta(updated, false);
            this.markAsModified(artifact, updated); // updated
          }
          return res.ok();
        }))
        .toPromise();
    } else {
      return Promise.resolve(false);
    }
  }

  unCheckoutArtifact(ids: string[]): Promise<boolean> {
    const payload = {
      checkedOutArtifactIds: ids,
    };
    return this.rest.put(`/artifacts/uncheckout`, payload).pipe(
      map(res => res.ok())).toPromise();
  }

  // TODO for now keeping the same implementation as uncheckout, change later as needed
  revertArtifact(ids: string[]): Promise<boolean> {
    const payload = {
      checkedOutArtifactIds: ids,
    };
    return this.rest.put(`/artifacts/uncheckout`, payload).pipe(
      map(res => res.ok())).toPromise();
  }

  deleteArtifact(artifact: Artifact[]): Promise<boolean> {
    return Promise.resolve()
      .then(() => {
        if (artifact[0].isCheckedOutArtifact) {
          return artifact[0];
        } else {
          return this.checkoutArtifact(artifact[0].id, false);
        }
      })
      .then(
        (checkedOut) => {
          return this.rest.delete(`/artifacts/${checkedOut.id}/delete`).toPromise();
        },
        (rejected) => {
          return rejected;
        })
      .then(res => res.ok());
  }

  getArtifactHistory(artifact: Artifact): Promise<ArtifactHistoryEntry[]> {
    return Promise.resolve()
      .then(() => {
        if (artifact.isCheckedOutArtifact) {
          return this.getCheckedOutArtifactWithContent(artifact.id);
        } else {
          return this.getArtifactLatest(artifact.id);
        }
      })
      .then(updated => {
        const id: string = updated.isCheckedOutArtifact ? updated.parentId : updated.id;
        if (id) {
          return this.rest
            .get(`/artifacts/${id}/history`).pipe(
              map(res => res.record.map(r => this.record.recordToArtifactHistoryEntry(id, r))),
              map(entries => entries.sort((a, b) => b.version - a.version)))
            .toPromise()
            .then(entries => {
              if (updated.parentId) {
                const found = entries.find(e => e.version === updated.checkedOutFromRevision);
                if (found) {
                  found.highlighted = true;
                  found.highlightReason = 'base';
                } else {
                  this.log.warn(this.i18n('Fail to find parent of this checkout.'));
                }
              } else if (entries.length > 0) {
                entries[0].highlighted = true;
                entries[0].highlightReason = 'current';
              }
              return entries;
            });
        } else {
          return [];
        }
      });
  }

  synchronize(lhs: Artifact, rhs: Artifact, strategy: SynchronizeStrategy, content: string): Promise<Artifact> {
    const payload = {
      artifactId: rhs.parentId,
      revision: rhs.latestRevision,
      strategy: SynchronizeStrategy[strategy],
      content: content,
    };
    return this.rest.put(`/artifacts/${rhs.id}/sync`, payload)
      .toPromise()
      .then(res => {
        const artifact = this.record.recordToCheckedOutArtifact(res.record[0]);
        if (lhs.status === 'DELETED' && strategy === SynchronizeStrategy.LATEST) {
          this.markAsDeleted(artifact);
        } else if (artifact.disposed) {
          this.markAsCheckoutDisposed(artifact);
        } else {
          this.markAsStale(artifact.id);
        }
        return artifact;
      });
  }

  syncExternalContentChanges(projectName: string, original: Artifact, artifactContent: string) {
    if (original.isCheckedOutArtifact) {
      original.content = artifactContent;
      return this.updateCheckoutArtifact(original).then(ok => {
        this.markCheckoutAsRerender(original);
        return ok;
      });
    } else {
      throw Error(this.i18n('Cannot invoke sync external for non-checked-out artifact.'));
    }
  }

  validateMetadata(artifactName: string, metadata: string) {
    try {
      if (metadata) {
        JSON.stringify(JSON.parse(metadata));
      }
      return true;
    } catch (e) {
      this.alert.flash(this.i18n('{{artifactName}} has invalid metadata: ', { artifactName: artifactName }) + e, 'warning');
      return false;
    }
  }

  fetchVersionsForSync(c: CommitCandidate): Promise<Artifact[]> {
    const artifactVersions: Artifact[] = [];
    let parentId: string;
    let latestRevision: number;

    return Promise.resolve()
      .then(() => {
        return this.getCheckedOutArtifactWithContent(c.id)
          .then(current => {
            artifactVersions.push(current);
            parentId = current.parentId;
            latestRevision = current.latestRevision;
            return this.getArtifactRevisionWithContent(current.parentId, current.checkedOutFromRevision)
              .then(base => {
                artifactVersions.push(base);
                return this.getArtifactRevisionWithContent(parentId, latestRevision)
                  .then(latest => {
                    artifactVersions.push(latest);
                    return artifactVersions;
                  });
              });
          });
      })
      .then(versions => {
        return artifactVersions;
      });
  }

  fetchVersionsForDiff(c: CommitCandidate): Promise<any[]> {
    const artifactVersions = [];

    return Promise.resolve()
      .then(() => {
        if (c.status !== 'ADDED') {
          return this.getArtifactRevisionWithContent(c.parentId, c.parentRevision)
            .then(lhs => {
              artifactVersions.push(lhs);
              return artifactVersions;
            });
        } else {
          artifactVersions.push(null);
          return artifactVersions;
        }
      })
      .then(versions => {
        return this.getCommitCandidateWithContent(c.id, c.committed)
          .then(rhs => {
            artifactVersions.push(rhs);
            return artifactVersions;
          });
      })
      .then(version => {
        return artifactVersions;
      });
  }

  export(artifact: Artifact) {
  }

  // ------------------ State management -------

  markAsStale(id?: string) {
    if (id) {
      const ca = this.checkedOutArtifactCache.get(id);
      if (ca) {
        ca.cacheStale = true;
        this._stateChanges.next({
          state: 'UPDATED',
          artifact: ca
        });
      } else {
        this.log.warn(this.i18n('Unable to find checked-out artifact for id: ') + id);
      }
    } else {
      this.checkedOutArtifactCache.forEach(v => v.cacheStale = true);
      this._stateChanges.next({
        artifact: null,
        state: 'UPDATED',
      });
    }
  }

  markAsAdded(artifact: Artifact) {
    // why only checked out artifacts?
    //    if (artifact.isCheckedOutArtifact) {
    this._stateChanges.next({
      state: 'ADDED',
      artifact: artifact
    });
    //    }
  }

  markDTStale(artifactPath: string) {
    this.checkedOutArtifactCache.forEach((value, key) => {
      if (key.indexOf(ArtifactType.BE_DECISION_TABLE.defaultExtension) !== -1 && value.path === artifactPath) {
        value.cacheStale = true;
      }
    });
  }

  markProjectAsDeleted(project: ProjectMeta) {
    this._stateChanges.next({
      state: 'DELETED',
      artifact: null,
      project: project
    });
  }

  markProjectAsAdded(project: ProjectMeta) {
    this._stateChanges.next({
      state: 'ADDED',
      artifact: null,
      project: project
    });
  }

  markAsDeleted(artifact: Artifact) {
    if (artifact.isCheckedOutArtifact) {
      this.checkedOutArtifactCache.delete(artifact.id);
    } else {
      const versions = this.artifactRevisionCache.get(artifact.id);
      if (versions) {
        versions.delete(artifact.revisionNumber);
      }
    }
    this._stateChanges.next({
      state: 'DELETED',
      artifact: artifact
    });
  }

  markAsCheckedOut(artifact: Artifact, checkedOut: Artifact) {
    this._stateChanges.next({
      state: 'CHECKED-OUT',
      artifact: artifact,
      updated: checkedOut
    });
  }

  markAsCheckoutDisposed(artifact: Artifact) {
    if (artifact.isCheckedOutArtifact) {
      this.checkedOutArtifactCache.delete(artifact.id);
      this._stateChanges.next({
        state: 'DISPOSED',
        artifact: artifact
      });
    }
  }

  markAsRerender(id?: string, revision?: number) {
    if (id && revision) {
      const revisions = this.artifactRevisionCache.get(id);
      if (revisions) {
        const artifact = revisions.get(revision);
        if (artifact) {
          this._stateChanges.next({
            state: 'RERENDER',
            artifact: artifact,
          });
        }
      }
    } else {
      this._stateChanges.next({
        state: 'RERENDER',
        artifact: null
      });
    }
  }

  markCheckoutAsRerender(checkedOut: Artifact) {
    this._stateChanges.next({
      state: 'RERENDER',
      artifact: checkedOut
    });
  }

  markIDAsUpdated(id: string) {
    this._stateChanges.next({
      state: 'UPDATED',
      id: id
    });
  }

  markAsModified(modArtifact: Artifact, latest?: Artifact) {
    this._stateChanges.next({
      state: 'MODIFIED',
      artifact: modArtifact,
      updated: latest
    });
  }

  protected requestArtifactRevision(id: string, version: number, includeContent: boolean): Promise<Artifact> {
    // -1 for getting the latest
    const url = `/artifacts/${id}/fetch/${version}?performValidation=${this.settings.latestSettings.autoArtifactValidation}`; // &includeContent=${includeContent}`;
    return this.rest.get(url).pipe(
      map(res => {
        if (res.ok()) {
          const artifact = this.record.recordToArtifact(res.record[0], false, true);
          // Server is returning ArtifactRevision ID, make sure ID is still the umberalla artifact id
          artifact.id = id;
          return artifact;
        }
      }))
      .toPromise()
      .then(artifact => {
        if (artifact) {
          return artifact;
        } else {
          throw Error(this.i18n('Unable to get artifact revision for id: {{id}}, revision: ', { id: id }) + version);
        }
      });
  }

  protected requestCheckedOutArtifact(id: string, includeContent?: boolean): Promise<Artifact> {
    if (includeContent === undefined) {
      includeContent = true;
    }
    return this.rest.get(`/artifacts/${id}?performValidation=${this.settings.latestSettings.autoArtifactValidation}`).pipe(
      map(res => {
        if (res.ok()) {
          return this.record.recordToCheckedOutArtifact(res.record[0], true);
        }
      }))
      .toPromise()
      .then(artifact => {
        if (artifact) {
          this.checkedOutArtifactCache.set(artifact.id, artifact);
          return artifact;
        } else {
          throw this.i18n('Unable to get Checked-out Artifact for id {{id}}', { id: id });
        }
      });
  }
  // ------------------ State management ends -------

  private initImpl() {
    this.clear();
  }

}
