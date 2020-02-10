import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { Commit } from '../models/commit';
import { WorkListEntry } from '../models/workitem';
import { ResizableModal } from '../shared/resizablemodal';

export class ProjectHistoryContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-lg modal-extra-wide';
  constructor(
    public commits: Commit[]
  ) {
    super();
  }
}

@Component({
  templateUrl: './project-history.modal.html',
})
export class ProjectHistoryModal extends ResizableModal implements ModalComponent<ProjectHistoryContext> {
  commits: WorkListEntry[];
  constructor(
    public dialog: DialogRef<ProjectHistoryContext>,
    public i18n: I18n
  ) {
    super(dialog.context, dialog.context.dialogClass);
    this.commits = dialog.context.commits.map(cmt => <WorkListEntry>{ id: cmt.id, commit: cmt });
  }

  onClose() {
    this.dialog.close();
  }

  itemMode() {
    return 'NORMAL';
  }
}
