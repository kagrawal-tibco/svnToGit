import { Injectable } from '@angular/core';

import { Observable } from 'rxjs';

import { Logger } from './logger.service';
import { RecordService } from './record.service';
import { RestService } from './rest.service';

import { Artifact } from '../models/artifact';

@Injectable()
export class GroupService {

  constructor(
    protected log: Logger,
    protected rest: RestService,
    protected record: RecordService
  ) {
  }

  getGroups(): Promise<any> {
    return;
  }

  getGroupArtifacts(groupName: string, projectName?: string, ignoreDeleteStatus?: boolean): Observable<Artifact[]> {
    return;
  }
}
