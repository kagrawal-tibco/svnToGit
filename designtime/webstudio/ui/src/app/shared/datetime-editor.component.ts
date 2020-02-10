import { Component, EventEmitter, Input, OnChanges, Output } from '@angular/core';

import * as moment from 'moment';

import { environment } from '../../environments/environment';

const FORMAT = 'YYYY-MM-DD HH:mm:ssZZ';
const FORMAT_NO_TIMEZONE = 'YYYY-MM-DD HH:mm:ss';
const FORMAT_NO_TIMEZONE_BE = 'YYYY-MM-DDTHH:mm:ss';

@Component({
  selector: 'datetime-editor',
  templateUrl: './datetime-editor.component.html'
})
export class DatetimeEditorComponent implements OnChanges {

  get datetime(): Date {
    return this.value;
  }

  set datetime(v: Date) {
    if (this.showPicker) {
      this._datetimeInputString = moment(v).format(this.format);
      this.value = v;
      this.valueChange.emit(v);
    }
  }

  get showPicker() {
    return this.edit && this._showPicker;
  }

  get datetimeInputString() {
    if (!this._datetimeInputString && this.value) {
      this._datetimeInputString = moment(this.value).format(this.format);
    }
    return this._datetimeInputString;
  }

  set datetimeInputString(v: string) {
    if (this.showPicker) {
      if (!v) {
        this.clear();
      } else {
        this._datetimeInputString = v;
      }
    }
  }

  @Input()
  value: Date;

  @Input()
  edit: boolean;

  @Input()
  allowPastValues: boolean;

  @Input()
  hideTimezone: boolean;

  @Input()
  forceReset: boolean;

  @Input()
  hint?: string;

  @Input()
  floatLabel: string;

  @Output()
  valueChange = new EventEmitter<Date>();

  format: string;
  invalid: boolean;

  private _datetimeInputString: string;
  private _showPicker: boolean;

  constructor() {

  }

  enablePicker() {
    this._showPicker = true;
  }

  disablePicker() {
    this.endEditingFromInputBox();
    this._showPicker = false;
  }

  ngOnChanges() {
    if (this.hideTimezone) {
      if (environment.enableBEUI) {
        this.format = FORMAT_NO_TIMEZONE_BE;
      } else {
        this.format = FORMAT_NO_TIMEZONE;
      }
    } else {
      this.format = FORMAT;
    }
    if (this.value) {
      this._datetimeInputString = moment(this.value).format(this.format);
    } else if (this.forceReset) {
      this._datetimeInputString = ''; // keep the invalid value to allow the user to fix it, unless forceReset is set
    }
  }

  endEditingFromInputBox() {
    if (this.showPicker) {
      const m = moment(this._datetimeInputString, [moment.ISO_8601, this.format], true);
      if (m.isValid()) {
        this.value = m.toDate();
      } else {
        this.value = undefined;
      }
      this.valueChange.emit(this.value);
    }
  }

  clear() {
    if (this.edit) {
      this._datetimeInputString = '';
      this.value = undefined;
      this.disablePicker();
      this.valueChange.emit(this.value);
    }
  }

  handleClick(e: Event) {
    e.stopImmediatePropagation();
    e.stopPropagation();
  }
}
