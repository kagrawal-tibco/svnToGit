import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { AlertService } from '../core/alert.service';
@Component({
  selector: 'datetime-picker',
  templateUrl: './datetime-picker.component.html',
  styles: [
    `div.datetime-picker {
       border-radius: 3px;
       border-bottom: 1px solid lightgray;
       border-left: 1px solid lightgray;
       border-right: 1px solid lightgray;
       position: absolute;
       z-index: 1;
       background-color: white
     }`,
  ]
})
export class DatetimePickerComponent implements OnChanges, OnInit {
  now: Date;

  @Input()
  value: Date;

  @Input()
  allowPastValues: Date;

  @Output()
  valueChange = new EventEmitter<Date>();

  constructor(private alert: AlertService, public i18n: I18n) {
  }

  ngOnInit() {
    this.now = new Date();
  }

  ngOnChanges() {
    if (!this.value) {
      //      this.value = new Date();
    }
  }

  get date() {
    return this.value;
  }

  set date(d: Date) {
    if (!this.value) {
      this.value = new Date();
    }
    this.value.setFullYear(d.getFullYear());
    this.value.setMonth(d.getMonth());
    this.value.setDate(d.getDate());
    this.valueChange.emit(this.value);
  }

  get time() {
    return this.value;
  }

  set time(t: Date) {
    if (t) {
      if (!this.value) {
        this.value = new Date();
      }
      this.value.setHours(t.getHours());
      this.value.setMinutes(t.getMinutes());
      this.value.setSeconds(t.getSeconds());
      this.valueChange.emit(this.value);
    } else {
      this.alert.flash(this.i18n('Invalid time value.'), 'error');
    }
  }

  onDateChange(value: Date): void {
    this.date = value;
  }

}
