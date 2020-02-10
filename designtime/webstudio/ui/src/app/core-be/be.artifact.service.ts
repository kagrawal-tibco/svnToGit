
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { map } from 'rxjs/operators';

import { AlertService } from '../core/alert.service';
import { ArtifactService, SynchronizeStrategy } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { RecordService } from '../core/record.service';
import { RestService } from '../core/rest.service';
import { SettingsService } from '../core/settings.service';
import { BECommitCandidate } from '../models-be/commit-candidate-be';
import { Artifact, ArtifactType } from '../models/artifact';
import { ArtifactHistoryEntry } from '../models/artifact-history-entry';
import { ArtifactValidationRecord, BEArtifactItem, BELockRecord } from '../models/dto';
import { environment } from 'environments/environment';

@Injectable()
export class BEArtifactService extends ArtifactService {

  constructor(
    protected rest: RestService,
    protected record: RecordService,
    protected settings: SettingsService,
    protected alert: AlertService,
    protected log: Logger,
    public i18n: I18n
  ) {
    super(rest, record, settings, alert, log, i18n);
  }

  checkoutArtifacts(artifacts: Artifact[], projectName?: string): Promise<Artifact[]> {
    const payload = {
      request: {
        data: {
          project: [this.record.artifactsToCheckoutRequest(projectName, artifacts)]
        }
      }
    };
    return this.rest.post(`projects/${projectName}/checkout.json`, payload).pipe(
      map(res => this.updateArtifact(res.record[0], artifacts))).toPromise();
  }

  updateArtifact(record: any, r: Artifact[]): Artifact[] {
    r.map(a => a.isCheckedOutArtifact = true);
    return r;
  }

  updateCheckoutArtifact(artifact: Artifact): Promise<boolean> {
    const projectName = artifact.projectId;

    const payload = {
      request: {
        data: {
          project: [{
            name: projectName,
            artifactItem: [this.record.artifactToSaveRequest(artifact)]
          }]
        }
      }
    };

    return this.rest.post(`artifact/save.json`, payload, undefined, true).pipe(
      map(res => {
        this.markAsModified(artifact, artifact);
        if (artifact.type.defaultExtension === ArtifactType.DOMAIN_MODEL.defaultExtension) {
          this.markAllDTAndRTIStale();
        }
        return res.ok();
      }))
      .toPromise();
  }

  revertArtifact(ids: string[]): Promise<boolean> {
    const projectName = this.getProjectName(ids[0]);

    const payload = {
      request: {
        data: {
          project: [{
            name: projectName,
            artifactItem: this.getArtifactItems(ids)
          }]
        }
      }
    };
    return this.rest.post(`projects/${projectName}/revert.json`, payload).pipe(
      map(res => {
        if (res.ok()) {
          ids.forEach(element => this.checkedOutArtifactCache.delete(element));
          return res.ok();
        }
      }))
      .toPromise();
  }

  deleteArtifact(artifact: Artifact[]): Promise<boolean> {
    const projectName = artifact[0].projectId;

    const payload = {
      request: {
        data: {
          project: [{
            name: projectName,
            artifactItem: []
          }]
        }
      }
    };
    artifact.forEach(element => {
      payload.request.data.project[0].artifactItem.push(this.record.artifactToItem(element));
    });

    return this.rest.delete(`artifact/delete.json`, payload, undefined, true).pipe(
      map(res => res.ok()))
      .toPromise();
  }

  getArtifactHistory(artifact: Artifact): Promise<ArtifactHistoryEntry[]> {
    const projectName = artifact.projectId;
    const artifactPath = artifact.path;
    const artifactType = artifact.type.defaultExtension;

    return this.rest
      .get(`artifact/history.json?projectName=${projectName}&artifactPath=${artifactPath}` +
        `&artifactType=${artifactType}&artifactExtension=${artifactType}`).pipe(
          map(res => res.record.map(r => this.record.recordToArtifactHistoryEntry(artifact.id, r))),
          map(entries => entries.sort((a, b) => b.version - a.version)))
      .toPromise()
      .then(entries => {
        if (entries.length > 0) {
          entries[0].highlighted = true;
          entries[0].highlightReason = this.i18n('current');
        }
        return entries;
      });
  }

  validateArtifact(artifact: Artifact): Promise<ArtifactValidationRecord> {
    const validateUri = `artifact/validate.json?projectName=${artifact.projectId}&artifactPath=${artifact.path}` +
      `&artifactType=${artifact.type.defaultExtension}&artifactExtension=${artifact.type.defaultExtension}`;

    return this.rest
      .get(validateUri, undefined, true).pipe(
        map(res => {
          if (res.ok()) {
            return this.record.recordToArtifactValidationRecord(res.record[0], false, artifact.type.defaultExtension);
          }
        }))
      .toPromise();
  }

  synchronize(lhs: Artifact, rhs: Artifact, strategy: SynchronizeStrategy, content: string): Promise<Artifact> {
    const artifact: Artifact = rhs;
    if (content) {
      artifact.content = content;
    }

    const artifactitem = (strategy === SynchronizeStrategy.LATEST)
      ? this.record.artifactToItem(artifact)
      : this.record.artifactToSaveRequest(artifact);

    let changeType = 'Modified';
    if (rhs.status === 'ADDED') {
      changeType = 'Added';
    } else if (rhs.status === 'DELETED') {
      changeType = 'Deleted';
    }
    (<BEArtifactItem>artifactitem).changeType = changeType;

    const payload = {
      request: {
        data: {
          project: [{
            name: rhs.projectId,
            artifactItem: [artifactitem]
          }]
        }
      }
    };

    const url = (strategy === SynchronizeStrategy.LATEST)
      ? `projects/${rhs.projectId}/synchronize.json`
      : `artifact/save.json`;

    return this.rest.post(url, payload)
      .toPromise()
      .then(res => {
        if (rhs.status === 'ADDED') {
          // this.explorer.addArtifactToExplorer(artifact);
          this.markAsAdded(artifact);
        } else if (rhs.status === 'DELETED') {
          this.markAsDeleted(artifact);
          // this.project.removeArtifactFromExplorer(artifact);
        } else {
          this.markAsStale(artifact.id);
        }
        if (artifact.type.defaultExtension === ArtifactType.DOMAIN_MODEL.defaultExtension) {
          this.markAllDTAndRTIStale();
        }
        artifact.status = rhs.status;
        artifact.disposed = true;
        return artifact;
      });
  }

  fetchVersionsForSync(c: BECommitCandidate): Promise<Artifact[]> {
    const artifactVersions: Artifact[] = [];
    const projectName = c.id.split('@')[1];

    return this.rest.get(`artifact/compare.json?` + this.getQueryStringFromId(c.id) + '&diffMode=1')
      .toPromise()
      .then(res => {
        if (res.status !== -1) {
          const versions = this.record.recordDetailsToArtifactVersions(res.record[0], projectName);
          if (versions.length === 3) {
            versions.forEach(artifact => {
              artifact.status = c.status;
              artifactVersions.push(artifact);
            });
            return artifactVersions;
          }
        } else {
          this.alert.flash(res.errorMessage, 'error');
          c.stale = true;
          c.disposed = true;
        }
      });
  }

  fetchVersionsForDiff(c: BECommitCandidate): Promise<any[]> {
    const artifactVersions = [];
    const projectName = c.id.split('@')[1];

    if (c.status !== 'ADDED') {
      let url = `artifact/compare.json?` + this.getQueryStringFromId(c.id);
      if (c.reviewStatus) { url += '&revisionId=' + c.revisionId; }
      return this.rest.get(url)
        .toPromise()
        .then(res => {
          if (res.status !== -1) {
            const versions = this.record.recordDetailsToArtifactVersions(res.record[0], projectName);
            if (versions.length === 2) {
              artifactVersions.push(versions[1]);
              artifactVersions.push(this.record.artifactToCommitCandidate(versions[0]));
            }
            return artifactVersions;
          } else {
            this.alert.flash(res.errorMessage, 'error');   
          }
        });
    } else {
      return Promise.resolve()
        .then(() => {
          artifactVersions.push(null);
          if (c.revisionId) {
            return this.rest.get(`worklist/review.json?` + this.getQueryStringFromId(c.id) + '&revisionId=' + c.revisionId, undefined, true).pipe(
              map(res => {
                if (res.ok()) {
                  return this.record.recordDetailsToCheckedOutArtifact(res.record[0], projectName);
                }
              }))
              .toPromise();
          } else {
            return this.getCheckedOutArtifactWithContent(c.id);
          }
        })
        .then(currentVersion => {
          artifactVersions.push(this.record.artifactToCommitCandidate(currentVersion));
          return artifactVersions;
        });
    }
  }

  export(artifact: Artifact) {
    if (artifact.type === ArtifactType.RULE_TEMPLATE_INSTANCE) {
      this.rest.download('artifact/export.json?' + this.getQueryStringFromId(artifact.id), artifact.name + '.' + artifact.type.defaultExtension, 'application/xml');
    } else {
      this.rest.download('artifact/export.json?' + this.getQueryStringFromId(artifact.id), artifact.name + '.xls', 'application/vnd.ms-excel');
    }
  }

  lockArtifact(artifact: Artifact, tag?: string): Promise<any> {
    return this.lockUnlockArtifact(artifact, true, false);
  }

  unlockArtifact(artifact: Artifact, force?: boolean): Promise<any> {
    return this.lockUnlockArtifact(artifact, false, force);
  }

  // State overrides
  markAsStale(id?: string) {
    if (id) {
      const ca = this.checkedOutArtifactCache.get(id);
      if (ca) {
        ca.cacheStale = true;
      } else {
        this.log.warn(this.i18n('Unable to find checked-out artifact for id: ') + id);
      }
    } else {
      this.checkedOutArtifactCache.forEach(v => v.cacheStale = true);
    }
  }

  markAllDTAndRTIStale() {
    this.checkedOutArtifactCache.forEach((value, key) => {
      if (key.indexOf(ArtifactType.BE_DECISION_TABLE.defaultExtension) !== -1 ||
        key.indexOf(ArtifactType.RULE_TEMPLATE.defaultExtension) !== -1 ||
        key.indexOf(ArtifactType.RULE_TEMPLATE_INSTANCE.defaultExtension) !== -1) {
        value.cacheStale = true;
      }
    });
  }

  protected requestCheckedOutArtifact(id: string): Promise<Artifact> {
    const projectName = id.split('@')[1];
    return this.rest.get(`artifact.json?` + this.getQueryStringFromId(id), undefined, true).pipe(
      map(res => {
        if (res.ok()) {
          return this.record.recordDetailsToCheckedOutArtifact(res.record[0], projectName);
        }
      }))
      .toPromise()
      .then(artifact => {
        if (artifact) {
          if (this.checkedOutArtifactCache.get(artifact.id) != null) {
            const art = this.checkedOutArtifactCache.get(artifact.id);
            artifact.locked = art.locked;
          }
          this.checkedOutArtifactCache.set(artifact.id, artifact);
          return artifact;
        }
      });
  }

  protected requestArtifactRevision(id: string, version: number): Promise<Artifact> {
    const projectName = id.split('@')[1];
    return this.rest.get(`artifact/compare.json?` + this.getQueryStringFromId(id) + '&revisionId=' + version)
      .toPromise()
      .then(res => {
        if (res.status !== -1) {
          const versions = this.record.recordDetailsToArtifactVersions(res.record[0], projectName);
          return versions[0];
        } else {
          this.alert.flash(res.errorMessage, 'error');
        }
      });
  }

  private getQueryStringFromId(id: string): string {
    let queryString;
    const artifactId = id.split('@');

    queryString = 'projectName=' + artifactId[1];
    queryString += ('&artifactPath=' + artifactId[2]);
    queryString += ('&artifactExtension=' + artifactId[3]);

    return queryString;
  }

  private lockUnlockArtifact(artifact: Artifact, lock: boolean, actionForcibly: boolean): Promise<any> {
    const artifactitem = this.record.artifactToItem(artifact);
    (<BEArtifactItem>artifactitem).lockRequestor = this.rest.userName;
    (<BEArtifactItem>artifactitem).lockRequestorId = this.rest.displayName;
    const payload = {
      request: {
        data: {
          actionForcibly: actionForcibly,
          project: [{
            name: artifact.projectId,
            artifactItem: [artifactitem]
          }]
        }
      }
    };

    const lockUnlock = (lock) ? 'lock' : 'unlock';

    return this.rest.post(`artifact/${lockUnlock}.json`, payload).pipe(
      map(res => {
        if (res.ok()) {
          return this.processLockUnlockMessage(res.record[0].artifactLockResponse, lock);
        }
      }))
      .toPromise();
  }

  private processLockUnlockMessage(lockRecord: BELockRecord, lock: boolean) {
    let message: string;
    let success: boolean;

    if (lockRecord) {
      if (!lockRecord.lockingEnabled) {
        message = this.i18n('Locking not enabled');
        success = false;
      } else {
        if (lock) {
          if (lockRecord.lockAcquired) {
            message = this.i18n('Lock successfully acquired');
            success = true;
          } else if (!environment.enableTCEUI && lockRecord.lockOwner) {
            message = this.i18n('Failed to acquire Lock. Lock already acquired by  \'{{0}}\'', { 0: lockRecord.lockOwner });
            success = false;
          } else if (environment.enableTCEUI && lockRecord.lockOwnerDisplayId) {
            message = this.i18n('Failed to acquire Lock. Lock already acquired by  \'{{0}}\'', { 0: lockRecord.lockOwnerDisplayId });
            success = false;
          } else {
            message = this.i18n('Failed to acquire Lock');
            success = false;
          }
        } else {
          if (lockRecord.lockReleased) {
            message = this.i18n('Lock successfully released');
            success = true;
          } else {
            message = this.i18n('Failed to release the Lock');
            success = false;
          }
        }
      }
    }

    return { success: success, message: message };
  }

  private getArtifactItems(artifactIds: string[]): BEArtifactItem[] {
    const beArtifactItems = [];
    for (const id in artifactIds) {
      if (id) {
        const idSplit = artifactIds[id].split('@');

        const artifact = new Artifact();
        artifact.path = idSplit[2];
        artifact.type = ArtifactType.fromExtension(idSplit[3]);

        beArtifactItems.push(this.record.artifactToItem(artifact));
      }
    }

    return beArtifactItems;
  }

  private getProjectName(artifactId: string): string {
    return artifactId.split('@')[1];
  }
}
