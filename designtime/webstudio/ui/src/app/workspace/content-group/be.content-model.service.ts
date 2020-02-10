import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ContentGroup } from './content-group';
import { ContentModelService } from './content-model.service';

import { ArtifactService } from '../../core/artifact.service';
import { ModalService } from '../../core/modal.service';
import { ProjectService } from '../../core/project.service';
import { RestService } from '../../core/rest.service';
import { Artifact } from '../../models/artifact';
import { ProjectMeta } from '../../models/project-meta';

@Injectable()
export class BEContentModelService extends ContentModelService {

  constructor(
    projectService: ProjectService,
    artifactService: ArtifactService,
    modalService: ModalService,
    restService: RestService,
    public i18n: I18n) {
    super(projectService, artifactService, modalService, restService, i18n);
  }

  preInit() {
    this.groups.length = 0;
  }

  createGroup(groupName: string, filter: string, artifact?: Artifact): Promise<any> {
    const url = `groups/${groupName}/add.json`;
    const body = this.buildBody(groupName, filter, artifact);
    return this._restService.post(url, body).toPromise();
  }

  deleteGroup(group: ContentGroup): Promise<any> {
    const groupName: string = group.groupId.replace('$', ' ');
    return new Promise((resolve, reject) => {
      this._modal.confirm().message(`Delete artifact group ${group.groupName}?`)
        .open().result
        .then(ok => {
          if (ok) {
            const url = `groups/${groupName}/delete.json`;
            this._restService.delete(url).toPromise().then(() => { resolve(true); });
          } else {
            resolve(false);
          }
        }, err => { if (err) { throw err; } });
    });
  }

  updateGroup(groupId: string, newName: string, newFilter: string, artifact: Artifact, isDelete: boolean): Promise<any> {
    groupId = groupId.replace('$', ' ');
    const opType: string = isDelete ? 'DELETE_ARTIFACT' : 'ADD_ARTIFACT';
    const artifactPath: string = artifact.projectId + artifact.path + '.' + artifact.type.defaultExtension;
    const url = `groups/${groupId}/update.json?artifactPath=${artifactPath}&operationType=${opType}`;
    return this._restService.put(url, null).toPromise();
  }

  getProjectArtifacts(projectId: string): Promise<Artifact[]> {
    return this._projectService.getCheckedOutArtifacts(projectId).toPromise();
  }

  getGroupArtifacts(cg: ContentGroup): Promise<any> {
    const groupName: string = cg.groupId.replace('$', ' ');
    return this._restService.get(`groups/${groupName}/artifacts.json?groupType=${cg.fileType}`).toPromise();
  }

  getAllGroups(repeat: boolean): Promise<any> {
    return this._restService.get('groups.json').toPromise();
  }

  getProjects(): Promise<ProjectMeta[]> {
    return this._projectService.getCheckedOutProjects();
  }

  buildBody(groupName: string, type: string, artifact?: Artifact): any {
    const artifacts: string[] = (artifact) ? [artifact.projectId + artifact.path + '.' + artifact.type.defaultExtension] : [];

    const payload = {
      request: {
        data: {
          userGroup: {
            name: groupName,
            groupItem: {
              artifact: artifacts
            }
          }
        }
      }
    };

    return payload;
  }

  processFilter(group: ContentGroup, filter: string) {
    if (!filter.startsWith('*')) {
      filter = '*' + filter;
    }
    filter = filter.replace(/\*/g, '.*');
    filter = filter.replace(/\?/g, '.?');
    const regExp = new RegExp(filter);
    group.resources = this.projectsGroup.resources.filter(s => regExp.test(s.name + '.' + s.type.defaultExtension));
  }
}
