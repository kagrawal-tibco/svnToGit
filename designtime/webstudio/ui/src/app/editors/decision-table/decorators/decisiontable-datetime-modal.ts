import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as moment from 'moment';
import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { AlertService } from '../../../core/alert.service';
import { Logger } from '../../../core/logger.service';
import { ColumnType } from '../../../editables/decision-table/column';
import { DecisionTable } from '../../../editables/decision-table/decision-table';
import * as diff from '../../../editables/decision-table/differ/differ';
import { EditBuffer } from '../../../editables/edit-buffer';

export class DateTimeModalContext extends BSModalContext {
  constructor(
    public dateTime: string
  ) {
    super();
  }
}

@Component({
  selector: 'date-time',
  templateUrl: './decisiontable-datetime-modal.html',
})
export class DateTimeModal implements ModalComponent<DateTimeModalContext> {
  context: DateTimeModalContext;
  dateTime: Date;
  beforeDateTime: Date;
  afterDateTime: Date;
  beginDateTime: Date;
  endDateTime: Date;
  actionToPerform: String = this.i18n('Matches');

  actionHeader = [{ name: this.i18n('Matches') }, { name: this.i18n('Between') }, { name: this.i18n('Before') }, { name: this.i18n('After') }];

  constructor(
    private log: Logger,
    public alert: AlertService,
    public dialog: DialogRef<DateTimeModalContext>,
    public i18n: I18n
  ) {
    this.context = dialog.context;
    this.init();
  }

  init() {

    this.parseDateString();
    const m = moment(this.context.dateTime, [moment.ISO_8601, 'YYYY-MM-DDTHH:mm:ss'], true);
    this.dateTime = m.isValid() ? m.toDate() : null;
  }

  parseDateString() {

    let allString: String[];

    this.context.dateTime.indexOf('&&') !== -1 ? allString = this.context.dateTime.split('&&') : allString = this.context.dateTime.split('||');

    if (this.context.dateTime.indexOf('&&') !== -1 || this.context.dateTime.indexOf('||') !== -1) {
      this.actionToPerform = this.i18n('Between');
    } else if (this.context.dateTime.startsWith('>') || this.context.dateTime.startsWith('>=')) {
      this.actionToPerform = this.i18n('After');
    } else if (this.context.dateTime.startsWith('<') || this.context.dateTime.startsWith('<=')) {
      this.actionToPerform = this.i18n('Before');
    } else {
      this.actionToPerform = this.i18n('Matches');
    }

    for (const item of allString) {

      let itemString = item.trim();

      if (itemString.startsWith('>') && !itemString.startsWith('>=')) {
        itemString = itemString.substring(itemString.indexOf('>') + 1, itemString.length);
        itemString = itemString.trim();
        const m = moment(itemString, [moment.ISO_8601, 'YYYY-MM-DDTHH:mm:ss'], true);
        this.afterDateTime = m.isValid() ? m.toDate() : null;
        this.beginDateTime = m.isValid() ? m.toDate() : null;

      } else if (itemString.startsWith('>=')) {
        itemString = itemString.substring(itemString.indexOf('>=') + 2, itemString.length);
        itemString = itemString.trim();
        const m = moment(itemString, [moment.ISO_8601, 'YYYY-MM-DDTHH:mm:ss'], true);
        this.afterDateTime = m.isValid() ? m.toDate() : null;
        this.beginDateTime = m.isValid() ? m.toDate() : null;

      } else if (itemString.startsWith('<') && !itemString.startsWith('<=')) {
        itemString = itemString.substring(itemString.indexOf('<') + 1, itemString.length);
        itemString = itemString.trim();
        const m = moment(itemString, [moment.ISO_8601, 'YYYY-MM-DDTHH:mm:ss'], true);
        this.beforeDateTime = m.isValid() ? m.toDate() : null;
        this.endDateTime = m.isValid() ? m.toDate() : null;

      } else if (itemString.startsWith('<=')) {
        itemString = itemString.substring(itemString.indexOf('<=') + 2, itemString.length);
        itemString = itemString.trim();
        const m = moment(itemString, [moment.ISO_8601, 'YYYY-MM-DDTHH:mm:ss'], true);
        this.beforeDateTime = m.isValid() ? m.toDate() : null;
        this.endDateTime = m.isValid() ? m.toDate() : null;

      } else {
        const m = moment(itemString, [moment.ISO_8601, 'YYYY-MM-DDTHH:mm:ss'], true);
        this.dateTime = m.isValid() ? m.toDate() : null;
      }
    }
  }

  canConfirm() {

  }

  onCancel() {
    this.dialog.dismiss();
  }

  onSubmit() {

    if (this.actionToPerform === this.i18n('Between') && this.beginDateTime > this.endDateTime) {
      this.alert.flash(this.i18n('End date should not be less than Begin date.'), 'error');
      this.dialog.dismiss();
    } else {
      this.dialog.close({
        cellDateValue: this.getFinalDateString()
      });
    }
  }

  invalidDateCheck(): boolean {
    if (this.beginDateTime > this.endDateTime) {
      return true;
    } else {
      return false;
    }
  }

  getFinalDateString(): String {
    if (this.actionToPerform === this.i18n('Matches')) {
      return moment(this.dateTime).format('YYYY-MM-DDTHH:mm:ss');
    } else if (this.actionToPerform === this.i18n('Before')) {
      return '<=' + moment(this.beforeDateTime).format('YYYY-MM-DDTHH:mm:ss');
    } else if (this.actionToPerform === this.i18n('After')) {
      return '>=' + moment(this.afterDateTime).format('YYYY-MM-DDTHH:mm:ss');
    } else if (this.actionToPerform === this.i18n('Between')) {
      return '>=' + moment(this.beginDateTime).format('YYYY-MM-DDTHH:mm:ss') + ' && <=' + moment(this.endDateTime).format('YYYY-MM-DDTHH:mm:ss');
    }
  }

  canDisplayMatches(): boolean {
    if (this.actionToPerform === this.i18n('Matches')) {
      return true;
    } else {
      return false;
    }
  }

  canDisplayBefore(): boolean {
    if (this.actionToPerform === this.i18n('Before')) {
      return true;
    } else {
      return false;
    }
  }

  canDisplayAfter(): boolean {
    if (this.actionToPerform === this.i18n('After')) {
      return true;
    } else {
      return false;
    }
  }

  canDisplayBeginEnd(): boolean {
    if (this.actionToPerform === this.i18n('Between')) {
      return true;
    } else {
      return false;
    }
  }

}
