import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { Logger } from '../../../core/logger.service';
import { ColumnType } from '../../../editables/decision-table/column';
import { DecisionTable } from '../../../editables/decision-table/decision-table';
import * as diff from '../../../editables/decision-table/differ/differ';
import { EditBuffer } from '../../../editables/edit-buffer';

enum Mode {
  CHOOSE,
  CONFIRM,
}

export class DeleteResolverContext extends BSModalContext {
  constructor(
    public ruleID: string
  ) {
    super();
  }
}

@Component({
  selector: 'delete-resolver',
  templateUrl: './delete-resolver.modal.html',
})
export class DeleteResolverModal implements ModalComponent<DeleteResolverContext> {
  context: DeleteResolverContext;
  deleteRule: string;
  explanation: string;
  lhsDescription: string;
  rhsDescription: string;
  title: string;

  constructor(
    private log: Logger,
    public dialog: DialogRef<DeleteResolverContext>,
    public i18n: I18n
  ) {
    this.context = dialog.context;
    this.init();
  }

  init() {
    // add prefixes first
    this.lhsDescription = '';
    this.rhsDescription = '';

    this.lhsDescription += 'Delete Rule ' + this.context.ruleID;
    this.rhsDescription += 'Keep Rule ' + this.context.ruleID;

    this.explanation = this.i18n('Rule' + this.context.ruleID + ' is deleted. Please select one of the following to resolve');

    this.title = this.i18n('Deleted Rule') + this.context.ruleID;

  }
  onSelectedChange(idx: number) {
    if (idx === 0) {
      this.deleteRule = 'Y';
    } else if (idx === 1) {
      this.deleteRule = 'N';
    } else {
      this.deleteRule = null;
    }
  }

  onClose() {
    this.dialog.dismiss();
  }
  onConfirm() {
    this.dialog.close(this.deleteRule);
  }

  canConfirm() {
    return this.deleteRule != null;
  }
}
