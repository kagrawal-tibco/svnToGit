
import { Injectable } from '@angular/core';

import { map } from 'rxjs/operators';

import { AuditTrailRecord } from 'app/models/dto';

import { AuditTrail } from './audit-trail';

import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';

@Injectable()
export class AuditTrailService {

  constructor(
    private log: Logger,
    private rest: RestService
  ) {

  }

  getAuditTrailDelta(actionTimeBefore?: number, actionTimeAfter?: number): Promise<AuditTrail[]> {
    return this.getAuditTrail(null, null, null, null, null, actionTimeBefore, actionTimeAfter);
  }

  getAuditTrail(userName?: string, projectName?: string, artifactPath?: string,
    artifactType?: string, actionType?: string[], actionTimeBefore?: number, actionTimeAfter?: number): Promise<AuditTrail[]> {
    const url = this.getUrl(userName, projectName, artifactPath, artifactType, actionType, actionTimeBefore, actionTimeAfter);

    return this.rest.get(url).pipe(
      map(res => {
        if (res.ok()) {
          return res.record.map(entry => {
            return this.recordToAuditTrail(entry);
          });
        }
      }))
      .toPromise();
  }

  getLogfile(userName?: string, projectName?: string, artifactPath?: string,
    artifactType?: string, actionType?: string[], actionTimeBefore?: number, actionTimeAfter?: number) {
    const url = this.getUrl(userName, projectName, artifactPath, artifactType, actionType, actionTimeBefore, actionTimeAfter, true);
    this.rest.download(url, 'audit-trail.log', 'text/plain');
  }

  private getUrl(userName?: string, projectName?: string, artifactPath?: string,
    artifactType?: string, actionTypes?: string[], actionTimeBefore?: number, actionTimeAfter?: number, logFile?: boolean): string {
    let url = 'auditTrail.json';

    const hasFilter = (userName || projectName || artifactPath || artifactType || actionTypes || actionTimeBefore || actionTimeAfter || logFile);
    if (hasFilter) { url += '?'; }

    if (userName) {
      url += ('userName=' + userName);
    }

    if (projectName) {
      if (!url.endsWith('&') && !url.endsWith('?')) { url += '&'; }
      url += ('projectName=' + projectName);
    }

    if (artifactPath) {
      if (!url.endsWith('&') && !url.endsWith('?')) { url += '&'; }
      url += ('artifactPath=' + artifactPath);
    }

    if (artifactType) {
      if (!url.endsWith('&') && !url.endsWith('?')) { url += '&'; }
      url += ('artifactType=' + artifactType);
    }

    if (actionTypes) {
      if (!url.endsWith('&') && !url.endsWith('?')) { url += '&'; }
      url += ('actionType=' + actionTypes.join(','));
    }

    if (actionTimeBefore) {
      if (!url.endsWith('&') && !url.endsWith('?')) { url += '&'; }
      url += ('beforeDate=' + actionTimeBefore);
    }

    if (actionTimeAfter) {
      if (!url.endsWith('&') && !url.endsWith('?')) { url += '&'; }
      url += ('afterDate=' + actionTimeAfter);
    }

    if (logFile) {
      if (!url.endsWith('&') && !url.endsWith('?')) { url += '&'; }
      url += ('logFile=' + logFile);
    }

    return url;
  }

  private recordToAuditTrail(auditTrailRecord: AuditTrailRecord): AuditTrail {
    return {
      projectName: auditTrailRecord.projectName,
      userName: auditTrailRecord.userName,
      artifactPath: auditTrailRecord.artifactPath,
      artifactType: auditTrailRecord.artifactType,
      actionType: auditTrailRecord.actionType,
      actionTime: auditTrailRecord.actionTime,
      comment: auditTrailRecord.comment
    };
  }

}
