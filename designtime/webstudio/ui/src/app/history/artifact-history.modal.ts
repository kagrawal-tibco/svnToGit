import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { ArtifactService } from '../core/artifact.service';
import { LifecycleService } from '../core/lifecycle.service';
import { ModalService } from '../core/modal.service';
import { ArtifactHistoryEntry } from '../models/artifact-history-entry';
import { ResizableModal } from '../shared/resizablemodal';

export class ArtifactHistoryContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-lg modal-extra-wide';
  constructor(
    public entries: ArtifactHistoryEntry[]
  ) {
    super();
  }
}

@Component({
  templateUrl: './artifact-history.modal.html',
})
export class ArtifactHistoryModal extends ResizableModal implements ModalComponent<ArtifactHistoryContext> {
  entries: ArtifactHistoryEntry[];
  constructor(
    public dialog: DialogRef<ArtifactHistoryContext>,
    private artifact: ArtifactService,
    private modal: ModalService,
    private lifecycle: LifecycleService,
    public i18n: I18n
  ) {
    super(dialog.context, dialog.context.dialogClass);
    this.entries = dialog.context.entries;
  }

  onClose() {
    this.dialog.close();
  }

}
