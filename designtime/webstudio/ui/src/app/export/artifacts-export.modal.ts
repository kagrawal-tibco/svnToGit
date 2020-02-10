import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { ArtifactsExport } from './artifacts-export.component';

import { ArtifactService } from '../core/artifact.service';
import { RestService } from '../core/rest.service';
import { Artifact, ArtifactType } from '../models/artifact';
import { ProjectMeta } from '../models/project-meta';
import { ResizableModal } from '../shared/resizablemodal';
import { WorkspaceService } from '../workspace/workspace.service';

export class ArtifactsExportModalContext extends BSModalContext {
  constructor(
    public pname: string,
    public pid: string
  ) {
    super();
  }
}

@Component({
  templateUrl: './artifacts-export.modal.html'
})
export class ArtifactsExportModal extends ResizableModal implements ModalComponent<ArtifactsExportModalContext>, OnInit {
  context: ArtifactsExportModalContext;
  form: ArtifactsExport;
  constructor(
    public dialog: DialogRef<ArtifactsExportModalContext>,
    private workspace: WorkspaceService,
    private rest: RestService,
    public i18n: I18n
  ) {
    super(dialog.context, dialog.context.dialogClass);
    this.context = dialog.context;
  }

  ngOnInit() {
    this.form = new ArtifactsExport();
    this.form.projectName = this.context.pname;
    this.form.projectId = this.context.pid;
  }

  onClose() {
    this.dialog.dismiss();
  }

  onConfirm() {
    this.rest.multiExport(this.getUrl(), this.form.projectName, this.getPayload(), 'application/zip');
    this.dialog.close();
  }

  get title() {
    return this.i18n('Export Artifacts from - ') + this.context.pname;
  }

  getUrl() {
    return 'projects/' + this.form.projectName + '/multiexport.json';
  }

  getPayload(): any {
    let dtString: String;
    let rtiString: String;
    for (const artifact of this.form.artifacts) {
      if (artifact.type === ArtifactType.BE_DECISION_TABLE) {
        if (dtString != undefined) {
          dtString = dtString + ',' + artifact.fullPath();
        } else {
          dtString = artifact.fullPath();
        }
      } else if (artifact.type === ArtifactType.RULE_TEMPLATE_INSTANCE) {
        if (rtiString != undefined) {
          rtiString = rtiString + ',' + artifact.fullPath();
        } else {
          rtiString = artifact.fullPath();
        }
      }
    }

    const payload = {
      request: {
        data: {
          artifactPathDT: dtString,
          artifactPathRTI: rtiString
        }
      }
    };

    return payload;
  }

}
