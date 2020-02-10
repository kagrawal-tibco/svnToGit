
import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable } from 'rxjs';
import { mergeMap, take } from 'rxjs/operators';

import { LifecycleService } from 'app/core/lifecycle.service';

import { ContentModelService } from './content-group/content-model.service';
import { MultitabEditorService } from './multitab-editor/multitab-editor.service';
import { ProjectExplorerService } from './project-explorer/project-explorer.service';
import { VisitHistoryService } from './visit-history/visit-history.service';

import { environment } from '../../environments/environment';
import { ArtifactService } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { ProjectService } from '../core/project.service';
import { SettingsService } from '../core/settings.service';
import { Artifact } from '../models/artifact';
import { ProjectMeta } from '../models/project-meta';
import { TreeNode } from '../widgets/tree-view/tree-node';

export type WorkspaceStatus = 'NEED_IMPORT' | 'NEED_CHECKOUT' | 'NEED_BOTH' | 'NORMAL';

@Injectable()
export class WorkspaceService {

  //  replaceAll(lhs: Artifact, rhs: Artifact) {
  //    return Promise.resolve().then(() => {
  //      this.multitab.replaceTab(lhs, rhs);
  //      this.explorer.replaceExplorer(lhs, rhs).then(() => {
  //        this.history.replace(lhs, rhs);
  //        this.groups.replace(lhs, rhs);
  //      });
  //
  //      return true;
  //    });
  //  }
  //
  //  removeFromAll(artifact: Artifact): Promise<boolean> {
  //    return this.explorer.removeArtifactFromExplorer(artifact)
  //      .then(() => this.multitab.markToRemove(tab => {
  //        let a = tab.payload;
  //        return a.id === artifact.id;
  //      }))
  //      .then(() => this.groups.remove(artifact))
  //      .then(() => this.history.remove(artifact));
  //  }
  //
  //  addArtifactToExplorer(artifact: Artifact): Promise<any> {
  //    this.explorer.addArtifactToExplorer(artifact);
  //    return Promise.resolve(true);
  //  }
  //
  //  removeProjectFromAll(project: ProjectMeta) {
  //    this.explorer.removeProjectFromExplorer(project);
  //    this.groups.removeProject(project);
  //    this.multitab.markToRemove(tab => tab.payload.projectId === project.projectId);
  //    this.history.removeIf(entry => entry.artifact.projectId === project.projectId);
  //    if (!this.explorer.hasVisibleProjects) {
  //      let onlyDisplayCheckedOut: boolean;
  //      this.settings.uiSettings.take(1).toPromise()
  //        .then(settings => {
  //          onlyDisplayCheckedOut = settings.onlyDisplayCheckedOutArtifacts;
  //          if (onlyDisplayCheckedOut) {
  //            return this.project.getCheckedOutProjects();
  //          } else {
  //            return this.project.getAllProjects();
  //          }
  //        })
  //        .then(projects => this.emitWorkspaceStatus(projects, onlyDisplayCheckedOut));
  //    }
  //  }

  get status(): Observable<WorkspaceStatus> {
    return this._status;
  }
  public active = new BehaviorSubject<Artifact>(null);
  private _status = new BehaviorSubject<WorkspaceStatus>('NORMAL');

  constructor(
    private settings: SettingsService,
    private explorer: ProjectExplorerService,
    private history: VisitHistoryService,
    private groups: ContentModelService,
    private artifact: ArtifactService,
    private multitab: MultitabEditorService,
    private project: ProjectService,
    private lifecycle: LifecycleService,
    private log: Logger
  ) {
    this.explorer.leafActivateHandler = (node: TreeNode): Promise<boolean> => {
      const a: Artifact = node.payload;
      return this.activateAll(a);
    };

    this.explorer.rootExpandHandler = (node: TreeNode): Promise<boolean> => {
      const root: ProjectMeta = node.payload;
      return Promise.resolve(this.settings.latestSettings)
        .then(setting => {
          if (setting.onlyDisplayCheckedOutArtifacts) {
            return this.project.getCheckedOutArtifacts(root.projectId).toPromise();
          } else {
            return this.project.getAllThenMerge(root.projectId);
          }
        })
        .then(artifacts => {
          node.resetChildren();
          artifacts.forEach(a => this.explorer.addArtifactToRoot(node, a));
          return true;
        }, () => false);
    };

    // setup subscription
    this.active.pipe(mergeMap(a => this.multitab.activateTab(a))).subscribe(() => { });
    this.active.pipe(mergeMap(a => this.explorer.activateExplorer(a))).subscribe(() => { });
    this.active.pipe(mergeMap(a => this.history.activate(a))).subscribe(() => { });
  }

  refresh(): Promise<any> {
    return this.refreshImpl();
  }

  clear() {
    this.active.next(null);
    this.explorer.clear();
    this.groups.clear();
    this.history.clear();
    this.multitab.clear();
  }

  activateAll(a: Artifact): Promise<any> {
    if (a) {
      return Promise.resolve()
        .then(() => {
          if (a.isCheckedOutArtifact) {
            return this.artifact.getCheckedOutArtifactWithContent(a.id);
          } else {
            return this.artifact.getArtifactRevisionWithContent(a.id, a.revisionNumber);
          }
        })
        .then(updated => {
          this.active.next(updated);
          return true;
        });
    } else {
      return Promise.resolve().then(() => this.active.next(null));
    }
  }

  private refreshImpl(): Promise<any> {
    let onlyDisplayCheckedOutArtifacts: boolean;
    return this.settings.uiSettings.pipe(take(1)).toPromise()
      .then(settings => {
        onlyDisplayCheckedOutArtifacts = settings.onlyDisplayCheckedOutArtifacts;
        if (onlyDisplayCheckedOutArtifacts) {
          return this.project.getCheckedOutProjects();
        } else {
          return this.project.getAllProjects();
        }
      })
      .then(projects => {
        if (!projects) {
          projects = [];
        }
        if (environment.enableBEUI) {
          projects.forEach(proj => {
            // this is done to update artifact's status - i.e. modified, added, etc
            this.lifecycle.getCommitCandidates(proj.projectId)
              .toPromise().then(() => {
                // no-op, let the call itself update the artifact metadata
              });
          });
        }
        this.explorer.refresh(projects.filter(p => !this.settings.latestSettings.hideInExplorer[p.projectId]));
        return this.emitWorkspaceStatus(projects, onlyDisplayCheckedOutArtifacts);
      })
      .then(() => this.groups.refresh(true))
      .then(() => this.history.refresh())
      .then(() => this.multitab.refresh());
  }

  private emitWorkspaceStatus(projects: ProjectMeta[], onlyShowCheckeOut: boolean) {
    if (projects.length > 0) {
      this._status.next('NORMAL');
      return Promise.resolve();
    } else {
      this.explorer.refresh([]);
      if (onlyShowCheckeOut) {
        return this.project.getAllProjects()
          .then(all => {
            if (all && all.length === 0) {
              if (environment.enableBEUI) {
                this._status.next('NEED_CHECKOUT');
              } else {
                this._status.next('NEED_BOTH');
              }
            } else {
              this._status.next('NEED_CHECKOUT');
            }
          });
      } else {
        this._status.next('NEED_IMPORT');
        return Promise.resolve();
      }
    }
  }
}
