import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Observable, Subscription } from 'rxjs';
import { from } from 'rxjs/observable/from';
import { debounceTime, map, startWith, switchMap, tap } from 'rxjs/operators';

import { BEImportProjectComponent, BEImportProjectContext } from 'app/be-project-importer/be-project-importer.modal';
import { ModalService } from 'app/core/modal.service';
import { ProjectService } from 'app/core/project.service';
import { UserService } from 'app/core/user.service';
import { EditorInterface } from 'app/editors/editor-interface';
import { ProjectSummary } from 'app/editors/project-summary/project-summary';
import { BEManagementService } from 'app/management/be-management.service';
import { BEUser } from 'app/models-be/user-be';
import { Artifact, ArtifactType } from 'app/models/artifact';
import { CommitCandidate } from 'app/models/commit-candidate';
import { ProjectMeta } from 'app/models/project-meta';
import { ProjectCheckoutModal, ProjectCheckoutModalContext } from 'app/project-checkout/project-checkout.modal';
import { ProjectImporterModal, ProjectImporterModalContext } from 'app/project-importer/project-importer.modal';
import { TreeManager } from 'app/widgets/tree-view/tree-manager';
import { TreeNode } from 'app/widgets/tree-view/tree-node';
import { ContentModelService } from 'app/workspace/content-group/content-model.service';
import { MultitabEditorService } from 'app/workspace/multitab-editor/multitab-editor.service';
import { ContextMenuService } from 'app/workspace/project-explorer/context-menu.service';
import { EditMenubuilderService } from 'app/workspace/project-explorer/edit-menubuilder-service';
import { WorkspaceService } from 'app/workspace/workspace.service';

import { BECommitCandidate } from './../models-be/commit-candidate-be';
import { ActivityDelta, CommitDelta, DashboardService, ProjectSnapshot, ProjectSnapshotDelta, WorkListDelta } from './dashboard.service';
import { WorklistModal, WorklistModalContext } from './worklist.modal';

import { environment } from '../../environments/environment';
import { LifecycleService } from '../core/lifecycle.service';
import { Logger } from '../core/logger.service';
import { WorkListEntry } from '../models/workitem';

export class HelpOption {
  constructor(public desc: string, public id: string) { }
}

@Component({
  selector: 'dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
})
export class DashboardComponent implements OnInit, OnDestroy {
  public projectSnapshots: ProjectSnapshot[] = [];
  public visibleSnapshots: ProjectSnapshot[] = [];
  projectActions: Map<string, any> = new Map<string, any>();

  helpOptions: HelpOption[] = [
    {
      desc: this.i18n('Check out an existing project'),
      id: 'project_checkout'
    },
    {
      desc: this.i18n('Change user settings'),
      id: 'user_prefs'
    },
    {
      desc: this.i18n('Edit artifacts'),
      id: 'edit_artifacts'
    }
  ];

  activitySubcription: Subscription;
  filteredOptions: Observable<HelpOption[]>;
  filteredArtifacts: Observable<Artifact[]>;
  helpControl: FormControl = new FormControl();
  snapshotsControl: FormControl = new FormControl();
  deployableTimeSlice: string;
  projectFilter: RegExp;
  hideNonCheckedOut: boolean;
  timerId: NodeJS.Timer;
  manager: TreeManager = new TreeManager(this.i18n);

  constructor(
    private log: Logger,
    private service: DashboardService,
    private lifecycle: LifecycleService,
    private projectService: ProjectService,
    private workspaceService: WorkspaceService,
    private contentModelService: ContentModelService,
    private managementService: BEManagementService,
    private modal: ModalService,
    private matDialog: MatDialog,
    private userService: UserService,
    private routerService: Router,
    private multiTab: MultitabEditorService,
    private editMenubuilder: EditMenubuilderService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    if (!environment.enableTCEUI) {
      this.helpOptions.push(
        {
          desc: this.i18n('Import a project from a ZIP file'),
          id: 'project_import'
        }
      );
    }
    this.activitySubcription = this.service.activityChanges()
      .subscribe(change => {
        setTimeout(() => {
          this.processChanges(change);
        }, 0);
      });

    // refresh with the interval of 20 seconds
    this.timerId = setInterval(() => this.service.updateAll(), 20000);
    this.resetFilteredArtifactStream();
    this.snapshotsControl.valueChanges
      .pipe(
        startWith(''),
        map(val => this.searchChanged(val))
      ).subscribe();
    this.filteredOptions = this.helpControl.valueChanges
      .pipe(
        startWith(''),
        map(val => this.filter(val))
      );
  }
  resetFilteredArtifactStream() {
    this.filteredArtifacts = this.snapshotsControl.valueChanges
      .pipe(
        debounceTime(500),
        switchMap(value => this.contentModelService.search(value)),
      );
  }

  performAction(clickEvent: any, action: HelpOption | Artifact) {
    if (!clickEvent.source.selected) {
      return;
    }
    setTimeout(() => this.helpControl.reset(), 350); // after valueChange debounce time
    const id = action.id;
    if (id === 'project_checkout') {
      this.modal.open(ProjectCheckoutModal, new ProjectCheckoutModalContext())
        .then(() => {
          this.routerService.navigate(['/workspace']);
        }, err => { if (err) { throw err; } });
      return;
    } else if (id === 'user_prefs') {
      this.routerService.navigate(['/settings']).then(() => {
      });
      return;
    } else if (id === 'edit_artifacts') {
      this.routerService.navigate(['/workspace']).then(() => {
      });
      return;
    } else if (id === 'project_import') {
      if (environment.enableBEUI) {
        this.onImportBEProject();
      } else {
        this.onImportProject();
      }
      return;
    }
    // assume it is an artifact to open
    this.routerService.navigate(['/workspace']).then(() => {
      this.multiTab.activateTab(action as Artifact);
    });
  }

  onImportBEProject() {
    this.modal.open(BEImportProjectComponent, new BEImportProjectContext())
      .then(() => {
        this.workspaceService.refresh().then(() => this.routerService.navigate(['/workspace']));
      }, err => {
        if (err) {
          throw err;
        }
      });
  }

  onImportProject() {
    this.projectService.getAllProjects()
      .then(metas => metas.map(meta => meta.projectName))
      .then(names => {
        this.modal.open(ProjectImporterModal, new ProjectImporterModalContext(names));
      })
      .then(() => {
        this.workspaceService.refresh().then(() => this.routerService.navigate(['/workspace']));
      },
        err => {
          if (err) {
            throw err;
          }
        });
  }

  filter(val: string): HelpOption[] {
    if (!val) {
      return this.helpOptions;
    }
    return this.helpOptions.filter(option =>
      option.desc.toLowerCase().indexOf(val.toLowerCase()) === 0);
  }

  ngOnDestroy(): void {
    clearInterval(this.timerId);
  }

  userName() {
    const user = <BEUser>this.userService.currentUser();
    let userName = user.userName;
    if (user.firstName) {
      userName = user.firstName;
    }
    return userName;
  }

  processChanges(change: ActivityDelta) {
    if (change.snapshotDelta) {
      const snaps = change.snapshotDelta.changed;
      if (!snaps) {
        return;
      }
      this.projectSnapshots = snaps.sort((a: ProjectSnapshot, b: ProjectSnapshot) => {
        // sort first by checked out projects, then by project name
        if (a.size !== '-1' && b.size === '-1') {
          return -1;
        }
        if (a.size === '-1' && b.size !== '-1') {
          return 1;
        }
        const item1 = a.name.toUpperCase();
        const item2 = b.name.toUpperCase();
        if (item1 < item2) {
          return -1;
        } else if (item1 > item2) {
          return 1;
        } else {
          return 0;
        }
      });
      this.applyFilter(this.projectSnapshots);
      this.rebuildProjectOptions();
    }
  }

  rebuildProjectOptions() {
    // rebuild the project options in case a project was checked out/deleted
    this.projectActions.clear();
  }

  getUnresolvedItems(projectName: string): WorkListEntry[] {
    return this.service.worklist.filter(it => it.commit && it.commit.projectName === projectName);
  }

  getUnresolvedWorkItems(): WorkListEntry[] {
    if (!this.service.worklist) {
      return [];
    }
    return this.service.worklist;
  }

  getDeployableArtifacts(projectName?: string): CommitCandidate[] {
    const deployables = [];
    if (!this.service.commits) {
      return [];
    }
    // for TCE, we want to show deployable artifacts since there is not a notion of 'pending' commits
    const commits = projectName ? this.service.commits.filter(cmt => cmt.projectName === projectName) : this.service.commits;
    if (commits) {
      commits.forEach(commit => {
        if (commit.changeList) {
          commit.changeList.forEach(c => {
            if (environment.enableBEUI || environment.enableTCEUI) {
              if ((<BECommitCandidate>c).reviewStatus === 'BuildAndDeploy'
                || (<BECommitCandidate>c).applicableStages === 'BuildAndDeploy') {
                deployables.push(c);
              }
            }
          });
        }
      });
      return deployables;
    }
    return [];
  }

  computeSize(sizeString: string) {
    let idx = sizeString.indexOf(' ');
    if (idx === -1) {
      idx = sizeString.indexOf('.');
    }
    const sizeinKB = Number.parseFloat(sizeString.substring(0, idx));
    if (sizeinKB < 1024) {
      return sizeinKB.toFixed(2) + ' KB';
    }
    const sizeinMB = sizeinKB / 1024;
    return sizeinMB.toFixed(2) + ' MB';
  }

  showBEUI(): boolean {
    return environment.enableBEUI;
  }

  showTCEUI(): boolean {
    return environment.enableTCEUI;
  }

  openDeployablesDialog(projectName?: string) {
    // return (key, opt) => {
    // for TCE, we want to show deployable artifacts since there is not a notion of 'pending' commits
    const filtered = projectName ? this.service.commits.filter(entry => entry.projectName === projectName) : this.service.commits;
    const deployables = [];
    filtered.forEach(commit => {
      if (commit.changeList) {
        commit.changeList.forEach(c => {
          if (environment.enableBEUI || environment.enableTCEUI) {
            if ((<BECommitCandidate>c).reviewStatus === 'BuildAndDeploy' ||
              (<BECommitCandidate>c).applicableStages === 'BuildAndDeploy') {
              if (!deployables.includes(commit)) {
                deployables.push(commit);
              }
            }
          }
        });
      }
    });
    // let data =
    //   new WorklistModalContext(projectName, deployables.map(c => <WorkListEntry>{ id: c.id, commit: c }), this.service, 'NORMAL');
    this.matDialog.open(WorklistModal, {
      width: '60vw',
      height: '60vh',
      data: <WorklistModalContext>{
        projectName: projectName,
        entries: deployables.map(c => <WorkListEntry>{ id: c.id, commit: c }),
        service: this.service,
        mode: 'NORMAL'
        // new WorklistModalContext(projectName, deployables.map(c => <WorkListEntry>{ id: c.id, commit: c }), this.service, 'NORMAL')
      },
    });
    // this.matDialog.open(WorklistModal,
    //   new WorklistModalContext(projectName, deployables.map(c => <WorkListEntry>{ id: c.id, commit: c }), this.service, 'NORMAL'))
    //   .then(() => {
    //     this.service.updateAll();
    //   },
    //     err => {
    //       if (err) {
    //         throw err;
    //       }
    //     }).catch(() => {
    //     });
    // // };
  }

  openUnresolvedDialog(projectName?: string) {
    // return (key, opt) => {
    const filtered = projectName ? this.service.worklist.filter(entry => entry.commit.projectName === projectName) : this.service.worklist;
    this.matDialog.open(WorklistModal, {
      width: '60vw',
      height: '60vh',
      data: new WorklistModalContext(projectName, filtered, this.service)
    });
    // this.modal.open(WorklistModal, new WorklistModalContext(projectName, filtered, this.service))
    //   .then(() => {
    //     this.service.updateAll();
    //   },
    //     err => {
    //       if (err) {
    //         throw err;
    //       }
    //     }).catch(() => {
    //     });
    // };
  }

  openCheckoutProjectDialog(projectName?: string) {
    const meta = this.projectService.getProjectMeta(projectName);
    this.modal.open(ProjectCheckoutModal, new ProjectCheckoutModalContext(meta))
      .then(() => { this.service.updateAll(); },
        err => {
          if (err) {
            throw err;
          }
        }).catch(() => {
        });

    // return (key, opt) => {
    // let filtered = projectName ? this.service.worklist.filter(entry => entry.commit.projectName === projectName) : this.service.worklist;
    // this.modal.open(WorklistModal, new WorklistModalContext(projectName, filtered, this.service))
    //   .then(() => { },
    //     err => {
    //       if (err) {
    //         throw err;
    //       }
    //     }).catch(() => {
    //     });
    // };
  }

  openDetailsPage(snap: ProjectSnapshot) {
    const project: ProjectSummary = snap as ProjectSummary;
    const artifact = new Artifact();
    artifact.id = 'Project@' + project.name + '@Summary';
    artifact.path = '/' + project.name;
    artifact.projectId = project.name;
    artifact.type = ArtifactType.PROJECT_SUMMARY;
    artifact.content = JSON.stringify(project);
    this.routerService.navigate(['/workspace']).then(() =>
      this.multiTab.activateTab(artifact, EditorInterface.PROJECT_SUMMARY)
    );
  }

  getProjectList() {
    const projectList = [];
    this.projectService.projectMetaCache.forEach(entry => {
      projectList.push(entry.projectName);
    });
    return projectList;
  }

  getUserList() {
    const userList = [];
    this.managementService.fetchUsers()
      .then(users => {
        users.forEach(entry => {
          userList.push(entry.userName);
        });
      });
    return userList;
  }

  onDeleteProject(projectName: string) {
    const project = this.projectService.getProjectMeta(projectName);
    this.modal.confirm()
      .message(this.i18n('Click Confirm to delete project {{projectName}} from the workspace. You can recover the deletion if this project is already in the repository. However, any local changes will be lost.', { projectName: projectName }))
      .okBtn(this.i18n('Confirm'))
      .cancelBtn(this.i18n('Cancel'))
      .open().result
      .then(
        () => this.projectService.deleteProject(project),
        () => {/*noop*/ }
      )
      .then(deleted => {
        if (deleted) {
          if (project) {
            // this.artifact.markProjectAsDeleted(project);
            this.service.updateAll();
            // this.workspace.refresh();
          }
        }
      }, err => {/*this.alert.flash(err, 'warning')*/ }
      );
  }

  getToolTip(tooltipType: String, name: string): string {
    if (tooltipType.indexOf('deploy') !== -1) {
      return (this.i18n('{{getDeployableArtifacts}} deployable artifact(s)',
        { getDeployableArtifacts: this.getDeployableArtifacts(name).length }));
    }
    if (tooltipType.indexOf('unresolved') !== -1) {
      return (this.i18n('{{getUnresolvedItems}} unresolved worklist item(s)',
        { getUnresolvedItems: this.getUnresolvedItems(name).length }));
    }
  }

  searchChanged(filter: string) {
    if (filter) {
      filter = filter.toUpperCase();
      if (!filter.startsWith('*')) {
        filter = '*' + filter;
      }
      filter = filter.replace(/\*/g, '.*');
      filter = filter.replace(/\?/g, '.?');
      try {
        this.projectFilter = new RegExp(filter);
      } catch (Exception) {
        // this.alert.flash('invalid filter expression', 'warning');
        return;
      }

      this.applyFilter(this.projectSnapshots);
    } else {
      this.projectFilter = null;
      this.applyFilter(this.projectSnapshots);
      // this.clearFilter();
    }
  }

  clearFilter() {
    this.projectFilter = null;
    this.snapshotsControl.setValue('');
    // this.resetFilteredArtifactStream();
    this.applyFilter(this.projectSnapshots);
  }

  applyFilter(projectSnapshots: ProjectSnapshot[]) {
    this.visibleSnapshots = projectSnapshots.filter(snap => {
      if (snap.size === '-1' && this.hideNonCheckedOut) {
        return false;
      }
      if (this.projectFilter) {
        const found = this.projectFilter.test(snap.name.toUpperCase());
        return found;
      }
      return true;
    });
  }

  toggleShowCheckedOutProjs() {
    this.hideNonCheckedOut = !this.hideNonCheckedOut;
    this.applyFilter(this.projectSnapshots);
  }

  projectCallback(option: any) {
    const callback = option[1].callback;
    if (callback) {
      callback();
      if (option[0] === 'details') {
        // special case for the 'Details' action - navigate to the workspace tab
        this.routerService.navigate(['/workspace']);
      }
    }
  }

  projectOptions(projName: string) {
    const curr = this.projectActions.get(projName);
    if (curr) {
      return curr;
    }
    const meta = this.projectService.getProjectMeta(projName);
    const node = this.projectToRoot(meta);
    const items = this.editMenubuilder.buildItems(node);
    const opts = Array.from(items);
    const projSnap = this.projectSnapshots.find(snap => snap.name === projName);
    if (projSnap && projSnap.size === '-1') {
      // project is not checked out, limit options
      const filtered = opts.filter(value => {
        return value[0] === 'audit-trail'; // || value[0] === 'validate'; // don't allow validate for now if project is not checked out
      });
      this.projectActions.set(projName, filtered);
      return filtered;
    } else {
      this.projectActions.set(projName, opts);
    }

    return opts;
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

  private projectToRoot(project: ProjectMeta): TreeNode {
    const root = this.manager.root(project.projectId);
    root.payload = project;
    root.text = project.projectName;
    return root;
  }
}
