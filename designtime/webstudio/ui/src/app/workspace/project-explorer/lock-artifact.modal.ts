import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { AlertType } from '../../core/alert.service';
import { ArtifactService } from '../../core/artifact.service';
import { Artifact } from '../../models/artifact';

export class LockArtifactModalContext extends BSModalContext {
  constructor(
    public artifact: Artifact
  ) {
    super();
  }

}

@Component({
  templateUrl: './lock-artifact.modal.html'
})
export class LockArtifactModal implements ModalComponent<LockArtifactModalContext> {
  context: LockArtifactModalContext;
  constructor(
    private artifactService: ArtifactService,
    public dialog: DialogRef<LockArtifactModalContext>,
    public i18n: I18n
  ) {
    this.context = dialog.context;
  }

  onCancel() {
    this.dialog.dismiss();
  }

  onLockArtifact(filter: string) {
    const artifact: Artifact = this.context.artifact;
    if (artifact) {
      this.artifactService.lockArtifact(artifact, filter)
        .then(resp => {
          if (resp.success) {
            this.dialog.close({ success: true, message: resp.message });
          } else {
            this.dialog.close({ success: false, message: resp.message });
          }
        });
    }
    this.dialog.close({ success: true, message: this.i18n('Lock successfully acquired') });
  }

  get title() {
    return 'Lock artifact';
  }

}
