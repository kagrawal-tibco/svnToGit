
import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { of as observableOf, Observable, Subject } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ContentGroup, ProjectsGroup } from './content-group';

import { environment } from '../../../environments/environment';
import { ArtifactService } from '../../core/artifact.service';
import { ModalService } from '../../core/modal.service';
import { ProjectService } from '../../core/project.service';
import { RestService } from '../../core/rest.service';
import { Artifact } from '../../models/artifact';
import { ProjectMeta } from '../../models/project-meta';

export interface GroupChange {
  group: ContentGroup;
  // the artifact that has state changes. this can be null, means all artifacts are affected.
  artifact: Artifact;
  // for updates, the oldArtifact can be specified for better handling
  oldArtifact?: Artifact;
  /**
   * UPDATED: the artifact has been modified/replaced
   * DELETED: artifact is deleted, shall be hidden from UI.
   * ADDED: artifact that newly added
   */
  state: 'UPDATED' | 'DELETED' | 'ADDED';
}
@Injectable()
export class ContentModelService {
  projectsGroup: ProjectsGroup;
  groups: ContentGroup[] = new Array<ContentGroup>();

  private _groupChanges: Subject<GroupChange> = new Subject<GroupChange>();

  constructor(
    protected _projectService: ProjectService,
    protected _artifactService: ArtifactService,
    protected _modal: ModalService,
    protected _restService: RestService,
    public i18n: I18n) {
    this._artifactService.stateChanges().subscribe(change => {
      switch (change.state) {
        case 'RERENDER':
        case 'UPDATED':
          if (change.artifact) {
            return this.replaceFromCache(change.artifact);
          } else if (change.id) {
            // fetch latest artifact and then update
            return this.replaceByID(change.id);
          }
          return Promise.resolve(false);
        case 'DELETED':
          if (change.artifact) {
            return this.remove(change.artifact);
          } else if (change.project) {
            return this.removeProject(change.project);
          }
          return Promise.resolve(false);
        case 'DISPOSED':
          return this._artifactService.getArtifactLatest(change.artifact.parentId)
            .then(latest => this.replace(change.artifact, latest));
        case 'CHECKED-OUT':
          if (change.artifact && change.updated) {
            return this.replace(change.artifact, change.updated);
          } else {
            return Promise.resolve(false);
          }
        case 'ADDED':
          if (change.artifact) {
            this.add(change.artifact);
            return Promise.resolve(true);
          } else if (change.project) {
            return this.addProject(change.project);
          }
          return Promise.resolve(true);
        case 'MODIFIED':
          if (change.artifact) {
            this.replace(change.artifact, change.updated);
            return Promise.resolve(true);
          }
      }
    });
  }

  groupChanges(): Subject<GroupChange> {
    return this._groupChanges;
  }

  clear() {
    this.clearImpl();
    this._groupChanges.next({
      artifact: null,
      group: null,
      state: 'UPDATED'
    });
  }

  clearImpl() {
    this.groups = [];
    this.projectsGroup = null;
  }

  preInit() {
  }

  init(): Promise<any> {
    this.preInit();
    return this.getAllGroups(false).then(
      response => {
        if (environment.enableBEUI) {
          response.record.forEach(rec => {
            /*
            * Not supporting processes, so Process group should not be created.
            */
            if (rec.fileType !== 'beprocess') {
              this.createContentGroup(rec, false);
            }
          });
        } else {
          response.record.forEach(rec => { this.createContentGroup(rec, false); });
        }
      }
    ).then(() => {
      return this.populateGroups();
    });
  }

  artifactIDs(artifact?: Artifact) {
    if (artifact) {
      if (artifact.parentId) {
        return artifact.parentId;
      }
      return artifact.id;
    }
  }

  createGroup(groupName: string, filter: string, artifact?: Artifact): Promise<any> {
    const url = `/groups/create`;
    const body = this.buildBody(groupName, filter, artifact);
    return this._restService.post(url, body).toPromise();
  }

  shareGroup(groupId: string, share: boolean): Promise<any> {
    const url = `/groups/${groupId}/share?enable=${share}`;

    return this._restService.put(url, {}).toPromise();
  }

  deleteGroup(group: ContentGroup): Promise<any> {
    return new Promise((resolve, reject) => {
      this._modal.confirm().message(this.i18n('Delete artifact group ') + group.groupName)
        .open().result
        .then(result => {
          if (result) {
            const url = `/groups/${group.groupId}`;
            this._restService.delete(url).toPromise().then(() => { resolve(true); });
          } else {
            resolve(false);
          }
        }, err => { if (err) { throw err; } });
    });
  }

  updateGroup(groupId: string, newName: string, newFilter: string, artifact: Artifact, isDelete: boolean): Promise<any> {
    const opType = isDelete ? 'REMOVE' : 'ADD';
    const body = this.buildUpdatesBody(newName, newFilter, opType, artifact);
    const url = `/groups/${groupId}/update`;
    return this._restService.put(url, body).toPromise();
  }

  getGroupArtifacts(cg: ContentGroup): Promise<any> {
    return this._restService.get(`/groups/${cg.groupId}/artifacts`).toPromise();
  }

  getAllGroups(repeat: boolean): Promise<any> {
    const url = '/groups';
    return this._restService.get(url).toPromise();
  }

  createContentGroup(element, processFilter: boolean) {
    const groupId = (element.entityId) ? element.entityId : element.name.replace(' ', '$');
    if (this.groupExists(groupId)) {
      return;
    }
    const name = element.name;
    if (name === 'Projects') {
      this.projectsGroup = new ProjectsGroup();
      this.groups.push(this.projectsGroup);
    } else {
      const shared = (element.shared) ? element.shared : false;
      const isSystemGroup: boolean = (element.systemGroup) ? element.systemGroup : false;
      const elementType = (element.type) ? element.type : element.fileType;
      const contentGroup = new ContentGroup(
        isSystemGroup,
        groupId,
        element.name,
        null,
        null,
        elementType,
        shared);
      this.groups.push(contentGroup);
      if (processFilter && contentGroup.fileType) {
        this.processFilter(contentGroup, contentGroup.fileType);
      }
    }
  }
  groupExists(groupId: string) {
    const idx = this.groups.findIndex(grp => {
      return grp.groupId === groupId;
    });
    return idx >= 0;
  }

  populateGroups(): Promise<any> {
    if (!this.projectsGroup) {
      this.projectsGroup = new ProjectsGroup();
      this.groups.push(this.projectsGroup);
      // this.projectsGroup.active = true;
    }
    return new Promise((resolve, reject) => {
      let projectsToProcess = this._projectService.projectMetaCache.size;
      this.getProjects()
        .then(metas => {
          if (metas) {
            projectsToProcess = metas.length;
            metas.forEach(meta => {
              this.getProjectArtifacts(meta.projectId)
                .then((artifacts: Artifact[]) => {
                  artifacts.forEach(art => {
                    if (this.projectsGroup) {
                      this.projectsGroup.addToGroups(meta.projectId,
                        this._artifactService.syncWithCache(art));
                    }
                  });
                }).then(() => {
                  this.addToTypedGroups();
                  projectsToProcess--;
                  if (projectsToProcess === 0) {
                    resolve();
                  }
                });
            });
          }
        });
    });
  }

  getProjects(): Promise<ProjectMeta[]> {
    const metas: ProjectMeta[] = [];

    this._projectService.projectMetaCache.forEach(meta => {
      metas.push(meta);
    });
    return Promise.resolve(metas);
  }

  getProjectArtifacts(projectId: string): Promise<Artifact[]> {
    return this._projectService.getAllThenMerge(projectId);
  }

  addToTypedGroups() {
    // process all filters for 'typed' groups
    this.groups.forEach(grp => {
      const filter = grp.fileType;
      if (filter) {
        this.processFilter(grp, filter);
      }
    });
  }

  filterNode(group: ContentGroup, node: Artifact, filter: string) {
    if (!filter.startsWith('*')) {
      filter = '*' + filter;
    }
    filter = filter.replace(/\*/g, '.*');
    filter = filter.replace(/\?/g, '.?');
    const regExp = new RegExp(filter);
    if (regExp.test(node.name)) {
      group.resources.push(node);
      this._groupChanges.next({
        artifact: node,
        group: group,
        state: 'ADDED'
      }
      );
    }
  }

  processFilter(group: ContentGroup, filter: string) {
    if (!filter.startsWith('*')) {
      filter = '*' + filter;
    }
    filter = filter.replace(/\*/g, '.*');
    filter = filter.replace(/\?/g, '.?');
    const regExp = new RegExp(filter);
    group.resources = this.projectsGroup.resources.filter(s => {
      let name = s.name;
      const ext = s.type.extensions.find(e => name.endsWith(e));
      if (ext) {
        name += '.' + ext;
      }
      return regExp.test(name);
    });
  }

  refresh(notify?: boolean): Promise<any> {
    const activeGroupIds = this.groups.length === 0 ? undefined : this.groups.filter(grp => grp.active).map(grp => grp.groupId);
    this.clearImpl();
    return this.init().then(() => {
      if (activeGroupIds && activeGroupIds.length > 0) {
        const groups = this.groups;
        for (let i = 0; i < groups.length; i++) {
          groups[i].active = activeGroupIds.includes(groups[i].groupId);
        }
      }
      if (notify) {
        this._groupChanges.next({
          artifact: null,
          group: null,
          state: 'UPDATED'
        });
      }
    });
  }

  refreshGroups() {
    this.getAllGroups(false).then(
      response => {
        if (response.ok()) {
          response.record.filter(element => {
            const groupId = (element.entityId) ? element.entityId : element.name;
            return !this.groupExists(groupId);
          }).forEach(grp => {
            if (environment.enableBEUI) {
              if (grp.fileType !== 'beprocess') {
                this.createContentGroup(grp, true);
              }
            } else {
              this.createContentGroup(grp, true);
            }

          });
        }
      }
    );
  }

  removeFromGroup(art: Artifact, group: ContentGroup) {
    const idx = group.resources.indexOf(art);
    if (idx >= 0) {
      group.resources.splice(idx, 1);
    }
  }

  remove(art: Artifact) {
    this.projectsGroup.removeArtifactItemById(this.artifactIDs(art));
    this._groupChanges.next({
      artifact: art,
      group: this.projectsGroup,
      state: 'DELETED'
    });
    this.groups.forEach(grp => {
      this.removeFromGroup(art, grp);
    });
  }

  add(art: Artifact) {
    // need to check whether the artifact already exists in groups, if so, replace it
    // this can happen for newly created and approved artifacts upon first approval
    const id = art.isCheckedOutArtifact ? art.parentId : art.id;
    const exItem = this.projectsGroup.resources.find(res => {
      return res.parentId ? id === res.parentId : id === res.id;
    });

    if (exItem) {
      this.replace(exItem, art);
    } else {
      this._add(art);
    }
  }

  _add(art: Artifact) {
    this.projectsGroup.addToGroups(art.projectId,
      this._artifactService.syncWithCache(art));
    this.groups.forEach(grp => {
      if (grp.fileType) {
        this.filterNode(grp, art, grp.fileType);
      }
    });
    this._groupChanges.next({
      artifact: art,
      group: this.projectsGroup,
      state: 'ADDED'
    });
  }

  fetchLatest(exItem: Artifact) {
    if (exItem.isCheckedOutArtifact) {
      return this._projectService.getCheckedOutArtifacts(exItem.projectId).pipe(mergeMap(arts => {
        return arts.filter(ca => ca.parentId === exItem.parentId);
      })).toPromise();
    } else {
      return this._artifactService.getArtifactLatest(exItem.id);
    }
  }

  replaceByID(id: string) {
    // need to find the artifact from groups, then re-fetch the content from the revision cache
    const exItem = this.projectsGroup.resources.find(res => {
      return res.parentId ? id === res.parentId : id === res.id;
    });

    if (exItem) {
      exItem.cacheStale = true;
      return this.fetchLatest(exItem)
        .then(newArt => this.replace(exItem, newArt));
    } else {
      return Promise.resolve(null);
    }

  }

  replaceFromCache(art: Artifact) {
    // need to find the artifact from groups, then re-fetch the content from the revision cache
    const id = art.isCheckedOutArtifact ? art.parentId : art.id;
    const exItem = this.projectsGroup.resources.find(res => {
      return res.parentId ? id === res.parentId : id === res.id;
    });

    if (exItem) {
      return this.fetchLatest(exItem)
        .then(newArt => this.replace(exItem, newArt));
    } else {
      return Promise.resolve(null);
    }

  }

  replace(lhs: Artifact, rhs: Artifact) {
    if (!rhs) {
      return Promise.resolve(false);
    }
    //    lhs.updateMeta(rhs); // don't do this, it causes issues for other listeners as the original metadata is changed
    if (lhs.isCheckedOutArtifact && !rhs.isCheckedOutArtifact) {
      // the artifact we're replacing is checked out, but the replacement is not.  Request a checkout
      this._artifactService.getCheckedOutArtifact(lhs.id, false).then(art => {
        if (art) {
          this.replace(lhs, art);
        }
      }
      );
    } else {
      this.projectsGroup.replaceItem(lhs, rhs);
      this.groups.forEach(grp => {
        grp.replaceItem(lhs, rhs);
      });
      this._groupChanges.next({
        artifact: rhs,
        oldArtifact: lhs,
        group: this.projectsGroup,
        state: 'UPDATED'
      });
    }
  }

  removeFromGroups(art: Artifact) {
    this.groups.forEach(grp => grp.removeItem(art));
  }

  removeProject(meta: ProjectMeta) {
    //    if (this.projectsGroup.projectToGroupMap.delete(meta.projectId)) {
    // TODO : Need to remove all of the project artifacts
    this.projectsGroup.resources.filter(res => res.projectId === meta.projectId)
      .forEach(art => {
        this.projectsGroup.removeArtifactItemById(art.id);
        this.removeFromGroups(art);
      });
    //    }
  }

  addProject(meta: ProjectMeta) {
    //    if (this.projectsGroup.projectToGroupMap.set(meta.projectId)) {
    // TODO : Need to remove all of the project artifacts
    return this.getProjectArtifacts(meta.projectId)
      .then((artifacts: Artifact[]) => {
        artifacts.forEach(art => {
          this.projectsGroup.addToGroups(meta.projectId,
            this._artifactService.syncWithCache(art));
        });
      }).then(() => {
        this.addToTypedGroups();
      });
    //    }
  }

  getUserName(): string {
    return this._restService.userName;
  }

  search(filter: string): Observable<Artifact[]> {
    if (!this.projectsGroup) {
      return;
    }
    if (!filter) {
      // return observableOf(this.projectsGroup.resources);
      return observableOf([]);
    }
    return observableOf(
      this.projectsGroup.resources.filter(res => res.type.canEdit && res.name.toLowerCase().includes(filter.toLowerCase())));
  }

  protected buildBody(groupName: string, type: string, artifact?: Artifact): any {
    if (artifact) {
      const p = {
        name: groupName,
        type: type,
        artifacts: [
          this.artifactIDs(artifact)
        ]
      };
      return p;
    }

    const payload = {
      name: groupName,
      type: type,
      artifacts: []
    };
    return payload;
  }

  private buildUpdatesBody(groupName: string, type: string, operationType: string, artifact: Artifact) {
    if (artifact) {
      const payload = {
        name: groupName,
        type: type,
        artifactUpdates: [{
          action: operationType,
          artifact: this.artifactIDs(artifact)
        }
        ]
      };
      return payload;
    } else {
      const payload = {
        name: groupName,
        type: type,
        artifactUpdates: []
      };
      return payload;
    }
  }
}
