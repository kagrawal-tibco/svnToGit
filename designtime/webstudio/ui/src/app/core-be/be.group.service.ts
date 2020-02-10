
import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ArtifactService } from '../core/artifact.service';
import { GroupService } from '../core/group.service';
import { Logger } from '../core/logger.service';
import { RecordService } from '../core/record.service';
import { RestService } from '../core/rest.service';
import { Artifact } from '../models/artifact';

@Injectable()
export class BEGroupService extends GroupService {

  constructor(
    protected log: Logger,
    protected rest: RestService,
    protected record: RecordService,
    protected artifact: ArtifactService
  ) {
    super(log, rest, record);
  }

  getGroups(): Promise<any> {
    return this.rest.get('groups.json').toPromise();
    // .then(result => {
    //   if (result.ok() && result.record) {
    //     return result.record.map(record => this.record.recordToGroup(record));
    //   }
    // });
  }

  getGroupArtifacts(groupName: string, projectName?: string, ignoreDeleteStatus?: boolean): Observable<Artifact[]> {
    let filter = this.rest.userName + '@';
    if (projectName) { filter += projectName; }

    let url = `groups/${groupName}/artifacts.json`;
    if (projectName) { url += `?projectName=${projectName}`; }
    if (ignoreDeleteStatus) {
      url += (url.indexOf('?') !== -1) ? '&' : '?';
      url += `ignoreDeleteStatus=${ignoreDeleteStatus}`;
    }

    return this.rest
      .get(url).pipe(
        map(res => res.record
          .map(r => this.record.recordToCheckedOutArtifact(r, false))
          .filter(ar => ar.id.startsWith(filter))
          .map(a => {
            this.artifact.cacheCheckedOutArtifact(a);
            a.cacheStale = true;
            return a;
          })
        ));
  }
}
