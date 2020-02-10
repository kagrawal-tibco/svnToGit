import { Component, } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { ArtifactService } from '../core/artifact.service';
import { ArtifactHistoryEntry } from '../models/artifact-history-entry';
import { ResizableModal } from '../shared/resizablemodal';

export class ArtifactRevisionSelectorModalContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-lg modal-extra-wide';
  constructor(
    public artifactName: string,
    public history: ArtifactHistoryEntry[]
  ) {
    super();
  }
}

@Component({
  templateUrl: './artifact-revision-selector.modal.html',
  styleUrls: ['./artifact-revision-selector.modal.css']

})
export class ArtifactRevisionSelectorModal extends ResizableModal implements ModalComponent<ArtifactRevisionSelectorModalContext> {
  context: ArtifactRevisionSelectorModalContext;

  selectedRevision: number;

  constructor(
    public dialog: DialogRef<ArtifactRevisionSelectorModalContext>,
    private artifact: ArtifactService,
    public i18n: I18n
  ) {
    super(dialog.context, dialog.context.dialogClass);
    this.context = dialog.context;
  }

  static context(artifactName: string, history: ArtifactHistoryEntry[]) {
    return new ArtifactRevisionSelectorModalContext(artifactName, history);
  }

  onRevisionSelect(revision: number) {
    this.selectedRevision = revision;
  }

  canConfirm() {
    return this.selectedRevision != null;
  }

  onConfirm() {
    this.dialog.close(this.selectedRevision);
  }

  onCancel() {
    this.dialog.dismiss();
  }
}
