import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { WorkListEntry } from 'app/models/workitem';

import { ResizableModal } from './../shared/resizablemodal';
import { DashboardService } from './dashboard.service';

export class WorklistModalContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-wide';
  constructor(
    public projectName: string,
    public entries: WorkListEntry[],
    public service: DashboardService,
    public mode?: string
  ) {
    super();
  }

}

@Component({
  templateUrl: './worklist.modal.html'
})
export class WorklistModal { // extends ResizableModal { // implements ModalComponent<WorklistModalContext> {
  context: WorklistModalContext;
  constructor(
    public dialogRef: MatDialogRef<WorklistModal>,
    @Inject(MAT_DIALOG_DATA) public data: WorklistModalContext,
    // dashboardService: DashboardService,
    // public dialog: DialogRef<WorklistModalContext>,
    public i18n: I18n
  ) {
    // super(dialog.context, dialog.context.dialogClass);
    this.context = data;
  }

  onCancel() {
    this.dialogRef.close();
  }

  onWorklist() {
    this.dialogRef.close();
  }

  get title() {
    return 'Pending worklist items';
  }

}
