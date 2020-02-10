/**
 * @Author: Rahil Khera
 * @Date:   2017-09-18T14:12:45+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-09-18T14:13:34+05:30
 */

import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { BEProjectService } from '../../core-be/be.project.service';
import { RestService } from '../../core/rest.service';
import { BELockManagementModal } from '../../models-be/lock-management-be.modal';
import { Artifact } from '../../models/artifact';
import { ProjectMeta } from '../../models/project-meta';
import { EditMenubuilderService } from '../../workspace/project-explorer/edit-menubuilder-service';
import { environment } from 'environments/environment';

@Component({
  selector: 'be-lock-management',
  templateUrl: './be-lock-management.component.html',
  styleUrls: ['./be-lock-management.component.css']
})

export class LockManagement implements OnInit {

  projectList: Array<ProjectMeta>;
  selectedProject: ProjectMeta;
  artifactList: Array<BELockManagementModal>;
  oldArtifactList: Array<BELockManagementModal>;
  selectedArtifact: BELockManagementModal;
  unlockAll = false;

  constructor(private rest: RestService,
    private project: BEProjectService,
    private cdRef: ChangeDetectorRef,
    private menuBuilderService: EditMenubuilderService,
    public i18n: I18n) {
    this.init();
  }

  ngOnInit() {
    this.artifactList = new Array<BELockManagementModal>();
    this.project.getProjectsForLockMgmt().
      then((projectList: Array<ProjectMeta>) => {
        this.projectList = projectList;
        if (projectList) {
          this.onProjectSelection(this.projectList[0]);
        }
      });
  }

  // ngAfterViewChecked() {
  //   this.cdRef.detectChanges();
  // }

  init() {
    this.projectList = new Array<ProjectMeta>();
    this.selectedArtifact = new BELockManagementModal();
  }

  onProjectSelection(project: ProjectMeta) {
    if (project) {
      this.selectedProject = project;
      this.artifactList = new Array<BELockManagementModal>();
      this.rest.get(`projects/${this.selectedProject.projectName}/artifactLocks.json`)
        .toPromise()
        .then((response) => {
          if (response.ok()) {
            const jsonLockedArtifactList = response.record;
            const lockedArtifactList: Array<BELockManagementModal> = new Array<BELockManagementModal>();
            for (let i = 0; i < jsonLockedArtifactList.length; i++) {
              const jsonArtifact = jsonLockedArtifactList[i];
              const artifactDetails: BELockManagementModal = new BELockManagementModal();
              const artifactPath = jsonArtifact.artifactPath.split('\/');
              artifactDetails.name = artifactPath[artifactPath.length - 1];
              artifactDetails.path = jsonArtifact.artifactPath;
              artifactDetails.locked = true;
              artifactDetails.fileType = jsonArtifact.fileExtension;
              artifactDetails.lockOwner = jsonArtifact.lockOwner;
              if (environment.enableTCEUI) {
                artifactDetails.lockOwnerDisplayName = jsonArtifact.lockedOwnerDisplayName;
              }
              artifactDetails.lockTime = jsonArtifact.lockedTime;
              lockedArtifactList.push(artifactDetails);
            }
            this.artifactList = lockedArtifactList;
            if (this.artifactList.length > 0) {
              this.onArtifactSelection(this.artifactList[0]);
            }
            this.oldArtifactList = _.cloneDeep(this.artifactList);
          }
        });
    }
  }

  onArtifactSelection(artifact: BELockManagementModal) {
    this.selectedArtifact = artifact;
  }

  updateLocks() {
    this.project.getCheckedOutArtifacts(this.selectedProject.projectId)
      .toPromise()
      .then((checkedOutArtifactList: Artifact[]) => {
        let allArtifactsUnlocked = false;
        checkedOutArtifactList.forEach((artifact) => {
          for (let i = 0; i < this.artifactList.length; i++) {
            if (!this.artifactList[i].locked && _.isEqual(this.artifactList[i].path, artifact.path)) {
              this.menuBuilderService.performUnlock(artifact, true);
            }
          }
          allArtifactsUnlocked = true;

        });
        return allArtifactsUnlocked;
      }).then((allArtifactsUnlocked: boolean) => {
        console.log(allArtifactsUnlocked);
        if (allArtifactsUnlocked) {
          this.oldArtifactList = _.cloneDeep(this.artifactList);
          this.onProjectSelection(this.selectedProject);
        }
      });
  }

  get disableApply(): boolean {
    return _.isEqual(this.artifactList, this.oldArtifactList);
  }

  onSelectAll(unlock: boolean) {
    for (let i = 0; i < this.artifactList.length; i++) {
      this.artifactList[i].locked = !unlock;
    }
  }

  onToggleLock(status: MatCheckboxChange) {
    if (status.checked && this.unlockAll) {
      this.unlockAll = false;
    }
  }

  isTCEUI() : boolean {
    return environment.enableTCEUI;
  }
}
