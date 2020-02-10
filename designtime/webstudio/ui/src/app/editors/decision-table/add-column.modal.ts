import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext, Modal } from 'ngx-modialog/plugins/bootstrap';

import { Logger } from '../../core/logger.service';
import { BEDecisionTable } from '../../editables/decision-table/be-decision-table';
import { ColumnType, PropertyType } from '../../editables/decision-table/column';
import { DecisionTable } from '../../editables/decision-table/decision-table';

export const ADD_COLUMN = 'ADD_COLUMN';

class ColumnForm {
  columnName: string;
  columnType: ColumnType; // columnType = ColumnType.CONDITION.value; // default
  propertyType: PropertyType; // propertyType = PropertyType.STRING.value; // default
}

export class AddColumnContext extends BSModalContext {
  constructor(
    public table: DecisionTable
  ) {
    super();
  }

}

export class AddETColumnContext extends BSModalContext {
  constructor(
    public table: BEDecisionTable
  ) {
    super();
  }

}

@Component({
  templateUrl: './add-column.modal.html',
})
export class AddColumnModal implements ModalComponent<AddColumnContext>, OnInit, AfterViewInit {

  get propertyTypes() {
    return PropertyType.TYPES.sort((a, b) => a.name.localeCompare(b.name));
  }

  get columnTypes() {
    return ColumnType.TYPES;
  }

  get existingColumns() {
    const colType = this.form.columnType;
    const res = this.table.getColumns()
      .filter(col => colType.isSameCategory(col.columnType))
      .map(col => col.name);
    return res;
  }

  get columnNameDuplicate() {
    return this.existingColumns.indexOf(this.form.columnName) !== -1;
  }

  form: ColumnForm;
  table: DecisionTable;

  @ViewChild('columnNameInput', { static: false })
  private columnNameInput: ElementRef;

  constructor(
    private log: Logger,
    private modal: Modal,
    public dialog: DialogRef<AddColumnContext>,
    public i18n: I18n
  ) {
    this.table = dialog.context.table;
  }

  limitPropertyTypes(columnType: ColumnType): (type: PropertyType) => boolean {
    return (type: PropertyType) => { return columnType.allowedTypes.includes(type); };
  }

  limitColumnTypes(columnType: ColumnType): boolean {
    return columnType !== ColumnType.PROPERTY;
  }

  onColumnTypeChange(columnType: ColumnType) {
    if (!this.limitPropertyTypes(columnType)(this.form.propertyType)) {
      this.form.propertyType = columnType.allowedTypes[0];
    }
  }

  ngOnInit() {
    this.form = new ColumnForm();
    this.form.columnType = ColumnType.CONDITION;
    this.form.propertyType = this.form.columnType.allowedTypes[0];
  }

  ngAfterViewInit() {
    this.columnNameInput.nativeElement.focus();
  }

  onCancel() {
    this.dialog.dismiss();
  }

  onSubmit() {
    this.dialog.close({
      columnName: this.form.columnName,
      columnType: this.form.columnType,
      propertyType: this.form.propertyType
    });
  }

  getMessage(): string {
    const msg = this.i18n('Column {{columnName}} already exists.', { columnName: this.form.columnName });
    return msg;
  }
}
