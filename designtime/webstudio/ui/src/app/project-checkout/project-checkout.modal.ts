
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';
import { map } from 'rxjs/operators';

import { ProjectCheckout } from './project-checkout.component';

import { ArtifactService } from '../core/artifact.service';
import { RestService } from '../core/rest.service';
import { EditorInterface } from '../editors/editor-interface';
import { ProjectSummary } from '../editors/project-summary/project-summary';
import { Artifact, ArtifactType } from '../models/artifact';
import { ProjectMeta } from '../models/project-meta';
import { ResizableModal } from '../shared/resizablemodal';
import { MultitabEditorService } from '../workspace/multitab-editor/multitab-editor.service';
import { WorkspaceService } from '../workspace/workspace.service';

export class ProjectCheckoutModalContext extends BSModalContext {
  constructor(
    public preset?: ProjectMeta,
  ) {
    super();
  }
}

@Component({
  templateUrl: './project-checkout.modal.html'
})
export class ProjectCheckoutModal extends ResizableModal implements ModalComponent<ProjectCheckoutModalContext>, OnInit {
  context: ProjectCheckoutModalContext;
  form: ProjectCheckout;
  allowSwitchProject: boolean;
  disableConfirm: boolean;
  constructor(
    public dialog: DialogRef<ProjectCheckoutModalContext>,
    private workspace: WorkspaceService,
    private artifact: ArtifactService,
    private rest: RestService,
    private router: Router,
    private multiTab: MultitabEditorService,
    public i18n: I18n
  ) {
    super(dialog.context, dialog.context.dialogClass);
    this.context = dialog.context;
    this.disableConfirm = false;
  }

  ngOnInit() {
    this.form = new ProjectCheckout();
    if (this.context.preset) {
      this.form.projectId = this.context.preset.projectId;
      this.form.projectName = this.context.preset.projectName;
      this.allowSwitchProject = false;
    } else {
      this.allowSwitchProject = true;
    }
  }

  onClose() {
    this.dialog.dismiss();
  }

  onConfirm() {
    this.disableConfirm = true;
    this.artifact.checkoutArtifacts(this.form.artifacts, this.form.projectName)
      .then(result => {
        if (result.length > 0) {
          this.workspace.refresh();
          this.dialog.close();
          // until the project summary page is polished, don't show it post-checkout
          // this.welcomeProject();
          this.router.navigate(['/workspace']);
        }
      }).catch(
        error => { /* Do nothing as it is already logged. */ }
      );
  }

  get title() {
    return this.i18n('Check Out Artifacts');
  }

  welcomeProject() {
    const artifact = new Artifact();
    artifact.id = 'Project@' + this.form.projectName + '@Summary';
    artifact.path = '';
    artifact.projectId = this.form.projectName;
    artifact.type = ArtifactType.PROJECT_SUMMARY;

    this.rest.get('projects/' + this.form.projectName + '/summary.json').pipe(
      map(res => {
        if (res.ok()) {
          artifact.content = this.createProjectSummary(res.record[0], this.form.projectName);
          this.multiTab.activateTab(artifact, EditorInterface.PROJECT_SUMMARY);
        }
      }))
      .toPromise();
  }

  createProjectSummary(record: any, project: string): string {
    const name = project;
    const description = '';
    const size = record.size;
    const artifactsCount = record.artifactsCount;
    const lastCheckoutBy = record.lastCheckoutBy;
    const lastCheckoutTime = record.lastCheckoutTime;
    const lastCommitBy = record.lastCommitBy;
    const lastCommitTime = record.lastCommitTime;
    const lastGenerateDeployableBy = record.lastGenerateDeployableBy;
    const lastGenerateDeployableTime = record.lastGenerateDeployableTime;
    const lastSyncBy = record.lastSyncBy;
    const lastSyncTime = record.lastSyncTime;
    const lastValidateBy = record.lastValidateBy;
    const lastValidateTime = record.lastValidateTime;
    const totalCommits = record.totalCommits;
    const totalApprovals = record.totalApprovals;
    const totalDeployments = record.totalDeployments;
    const totalRejections = record.totalRejections;
    const pSummary = new ProjectSummary(name, description, size, artifactsCount, lastCheckoutBy,
      lastCheckoutTime, lastCommitBy, lastCommitTime, lastGenerateDeployableBy, lastGenerateDeployableTime, lastSyncBy,
      lastSyncTime, lastValidateBy, lastValidateTime, totalCommits, totalApprovals, totalDeployments, totalRejections);
    return JSON.stringify(pSummary);
  }

}
