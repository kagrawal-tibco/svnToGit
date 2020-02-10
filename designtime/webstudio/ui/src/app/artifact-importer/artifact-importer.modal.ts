import { Component } from '@angular/core';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { environment } from '../../environments/environment';
import { ProjectService } from '../core/project.service';
import { Artifact, ArtifactType } from '../models/artifact';
import { ResizableModal } from '../shared/resizablemodal';

export class ArtifactImporterModalContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-lg modal-extra-wide';
  constructor(
    public projectId: string,
    public projectName: string,
    public baseDir: string,
    public existingArtifactPaths: string[],
    public artifactType: ArtifactType,
    public artifactContent?: string,
    public baseArtifactPath?: string
  ) {
    super();
    this.isBlocking = true;
    this.keyboard = null;
  }
}

@Component({
  template: `
    <artifact-importer
      [projectName]='context.projectName'
      [baseDir]='context.baseDir'
      [existingArtifactPaths]='context.existingArtifactPaths'
      [artifactType]='context.artifactType'
      [artifactContent]='context.artifactContent'
      [baseArtifactPath]='context.baseArtifactPath'
      (result)='handleResult($event)'
    ></artifact-importer>`
})
export class ArtifactImporterModal extends ResizableModal implements ModalComponent<ArtifactImporterModalContext> {
  context: ArtifactImporterModalContext;

  constructor(
    public dialog: DialogRef<ArtifactImporterModalContext>,
    private project: ProjectService
  ) {
    super(dialog.context, dialog.context.dialogClass);
    this.context = dialog.context;
  }

  handleResult(artifact: Artifact) {
    if (artifact) {
      const projId = artifact.projectId ? artifact.projectId : this.context.projectId;
      this.project.addToCheckout(projId, artifact)
        .then(res => {
          if (res) {
            this.dialog.close(res);
          } else if (environment.enableBEUI) {
            this.dialog.dismiss();
          }
        });
    } else {
      this.dialog.dismiss();
    }
  }
}
