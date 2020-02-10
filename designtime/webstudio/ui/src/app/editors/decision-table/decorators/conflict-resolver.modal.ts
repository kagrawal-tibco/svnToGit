import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { Logger } from '../../../core/logger.service';
import { ColumnType } from '../../../editables/decision-table/column';
import { DecisionTable } from '../../../editables/decision-table/decision-table';
import { ConflictItem, ConflictType, DiffItem, DELETE, EDIT, NEW, Resolution } from '../../../editables/decision-table/differ/differ';
import { EditBuffer } from '../../../editables/edit-buffer';

enum Mode {
  CHOOSE,
  CONFIRM,
}

export class ConflictResolverContext extends BSModalContext {
  constructor(
    public base: DecisionTable,
    public buffer: EditBuffer<DecisionTable>,
    public item: ConflictItem,
    public lhsTitle: string,
    public rhsTitle: string
  ) {
    super();
  }
}

@Component({
  selector: 'conflict-resolver',
  templateUrl: './conflict-resolver.modal.html',
})
export class ConflictResolverModal implements ModalComponent<ConflictResolverContext> {
  context: ConflictResolverContext;
  resolution: Resolution;
  explanation: string;
  lhsDescription: string;
  rhsDescription: string;
  mode: Mode;
  lhs: DiffItem;
  rhs: DiffItem;
  title: string;

  constructor(
    private log: Logger,
    public dialog: DialogRef<ConflictResolverContext>,
    public i18n: I18n
  ) {
    this.context = dialog.context;
    this.init();
  }

  init() {
    this.lhs = this.context.item.a;
    this.rhs = this.context.item.b;
    // add prefixes first
    this.lhsDescription = '';
    this.rhsDescription = '';

    this.processItem(this.context.item);
  }

  processItem(conflictItem: ConflictItem) {
    this.title = `Conflict in ${this.entityName}`;
    switch (this.context.item.type) {
      case ConflictType.CELL:
      case ConflictType.COLUMN:
      case ConflictType.META:
      case ConflictType.PROP:
      case ConflictType.RULE:
        this.setInChoose();
        break;
      case ConflictType.CELL_BOTH:
      case ConflictType.CELL_RULE:
      case ConflictType.CELL_COLUMN:
        this.setInConfirm('rhs');
        break;
      case ConflictType.BOTH_CELL:
      case ConflictType.RULE_CELL:
      case ConflictType.COLUMN_CELL:
        this.setInConfirm('lhs');
        break;
      default:
        this.log.warn('Should not have other kind of conflict:', conflictItem);
        break;
    }
  }

  setInChoose() {
    this.mode = Mode.CHOOSE;
    this.explanation = this.prepareChooseExplanation();
    this.lhsDescription += 'Repository: ' + this.prepareDiffDescription(this.lhs);
    this.rhsDescription += 'Working Copy: ' + this.prepareDiffDescription(this.rhs);
  }

  setInConfirm(deleteFrom: 'lhs' | 'rhs') {
    this.mode = Mode.CONFIRM;
    this.explanation = this.prepareConfirmExplanation(deleteFrom);

    if (deleteFrom === 'lhs') {
      this.lhsDescription += `Delete ${this.entityName}`;
      this.rhsDescription += `Update ${this.entityName}`;
    } else {
      this.lhsDescription += `Update ${this.entityName}`;
      this.rhsDescription += `Delete ${this.entityName}`;
    }
  }

  prepareChooseExplanation() {
    return `${this.entityName} was set to different values in the repository and your working copy. \
    Please select one of them to resolve the conflict.`;
  }

  get entityName() {
    let entityName: string;
    const ids = this.getIds(this.context.item.id);
    switch (this.context.item.type) {
      case ConflictType.PROP:
        entityName = `Property ${this.context.item.id}`;
        break;
      case ConflictType.RULE:
        entityName = `Rule ${this.context.item.id}`;
        break;
      case ConflictType.RULE_CELL:
      case ConflictType.CELL_RULE:
        entityName = `Rule ${ids.ruleId}`;
        break;
      case ConflictType.META:
        entityName = `Meta ${this.context.item.id}`;
        break;
      case ConflictType.COLUMN:
        entityName = `Column ${this.context.item.id}`;
        break;
      case ConflictType.COLUMN_CELL:
      case ConflictType.CELL_COLUMN:
        entityName = `Column ${ids.colId}`;
        break;
      case ConflictType.CELL:
        const col = this.context.base.getColumn(ids.colId);
        if (col.columnType === ColumnType.PROPERTY) {
          entityName = `${col.name} of Rule ${ids.ruleId}`;
        } else {
          entityName = `Cell at Rule ${ids.ruleId}, Column ${col ? col.name : 'UNKNOWN'}`;
        }
        break;
      case ConflictType.CELL_BOTH:
      case ConflictType.BOTH_CELL:
        entityName = `Rule ${ids.ruleId} and Column ${ids.colId}`;
        break;
    }
    return entityName;
  }

  getIds(id: string) {
    const parts = id.split('_');
    return {
      ruleId: parts[0],
      colId: parts[1],
    };
  }

  prepareConfirmExplanation(deleteFrom: 'lhs' | 'rhs') {
    const conflictItem = this.context.item;
    let first: string;
    let second: string;
    let item: DiffItem;
    if (deleteFrom === 'lhs') {
      first = 'the repository';
      second = 'your working copy';
      item = conflictItem.a;
    } else {
      first = 'your working copy';
      second = 'the repository';
      item = conflictItem.b;
    }
    return `${this.entityName} was deleted in ${first}.
    ${this.entityName} was updated in ${second}. \
    Please select the change to apply to your working copy.`;
  }

  prepareDiffDescription(item: DiffItem) {
    switch (item.kind) {
      case NEW:
      case EDIT:
        return `${item.rhs.toString()}`;
      case DELETE:
        return '(content deleted)';
    }
  }

  onSelectedChange(idx: number) {
    if (idx === 0) {
      this.resolution = Resolution.LHS;
    } else if (idx === 1) {
      this.resolution = Resolution.RHS;
    } else {
      this.resolution = null;
    }
  }

  onClose() {
    this.dialog.dismiss();
  }
  onConfirm() {
    this.dialog.close(this.resolution);
  }

  canConfirm() {
    return this.resolution != null;
  }
}
