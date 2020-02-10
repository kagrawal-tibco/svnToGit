import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { Artifact } from '../models/artifact';

export class ArtifactInfoModalContext extends BSModalContext {
  constructor(
    public projectName: string,
    public artifact: Artifact
  ) {
    super();
  }

}

@Component({
  templateUrl: './artifact-info.modal.html'
})
export class ArtifactInfoModal implements ModalComponent<ArtifactInfoModalContext> {
  context: ArtifactInfoModalContext;
  constructor(
    public dialog: DialogRef<ArtifactInfoModalContext>,
    public i18n: I18n
  ) {
    this.context = dialog.context;
  }

  onClose() {
    this.dialog.dismiss();
  }

  get title() {
    return this.context.artifact.name;
  }

}
