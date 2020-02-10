import { Injectable } from '@angular/core';

import { RestService } from 'app/core/rest.service';
import { Subject } from 'rxjs';

@Injectable()
export class DownloadProjectsService {

  constructor(private rest: RestService) { }

  fetchPrivateProjects(): Promise<Array<string>> {
    return this.rest.get('privateProjects.json')
      .toPromise().then((response) => {
        if (response.ok()) {
          if (response.record.length > 0 && response.record[0].projectNames) {
            const availablePrivateProject: string[] = response.record[0].projectNames;
            return availablePrivateProject;
          } else {
            return new Array<string>();
          }
        }
        return null;
      });
  }

  downloadProjects(projectList: string[]): Subject<boolean> {
    this.rest.backup(`backupProject.json?projectNamesString=${projectList.toString()}`, 'TCE_WebStudio_Projects_Backup', 'application/zip');
    return this.rest.downloadSuccess;
  }
}
