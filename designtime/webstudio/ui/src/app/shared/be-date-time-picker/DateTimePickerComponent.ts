/**
 * @Author: Rahil Khera
 * @Date:   2017-04-20T10:07:56+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-04-20T10:07:56+05:30
 */

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { PopoverDirective } from 'ngx-bootstrap/popover';

@Component({
  selector: 'dateTimePicker',
  templateUrl: './DateTimePickerComponent.html',
  styleUrls: ['./DateTimePickerComponent.css']
})

export class DateTimePickerComponent implements OnInit {

  set id(elementId: string) {
    this._id = elementId;
  }

  get id(): string {
    return this._id;
  }

  get disableOkButton(): boolean {
    return this._disableOkButton;
  }
  dateValue: Date = new Date();
  timeValue: Date = new Date();
  dateTimeValue: Date = new Date();
  public dateTimePickerForm: FormGroup;
  selectedDateTimeValue: string;

  @Input()
  currentDate: string;

  @Input()
  disable: boolean;

  @Output()
  dateChange = new EventEmitter<string>();
  private _id = 'dateTimePicker';
  private _disableOkButton = false;

  constructor(private _formBuilder: FormBuilder, public i18n: I18n) {
    this.dateTimePickerForm = this._formBuilder.group({
      datePicker: [''],
      timePicker: [''],
      dateTimeInputPicker: ['']
    });
    this.dateTimePickerForm.get('timePicker').valueChanges.subscribe((value) => {
      console.log(value);
      if (value === null) {
        this._disableOkButton = true;
      } else {
        this._disableOkButton = false;
      }
    });
  }

  public static convertStringToDate(value: string): Date {
    const date = new Date();
    if (value && value.length > 0) {
      const dateTimeStingArray = value.split('T');
      const dateString = dateTimeStingArray[0];
      const timeString = dateTimeStingArray[1];
      const dateSplitArray = dateString.split('-');
      const year = dateSplitArray[0];
      const month = dateSplitArray[1];
      const day = dateSplitArray[2];
      date.setFullYear(parseInt(year), parseInt(month) - 1, parseInt(day));
      const timeSplitArray = timeString.split(':');
      const hours = timeSplitArray[0];
      const minutes = timeSplitArray[1];
      const seconds = timeSplitArray[2].split('.')[0];
      const milliseconds = '000';
      // if (milliseconds) {
      //     milliseconds = milliseconds.substring(0,2);
      // }
      date.setHours(parseInt(hours), parseInt(minutes), parseInt(seconds), parseInt(milliseconds));
    }
    return date;
  }

  ngOnInit() {
    if (this.currentDate != null && this.currentDate.length > 0) {
      this.dateTimeValue = DateTimePickerComponent.convertStringToDate(this.currentDate);
      this.selectedDateTimeValue = this.dateTimeValue.toString();
    } else {
      this.dateTimeValue = new Date();
      this.selectedDateTimeValue = '';
    }
  }

  setDateinTextBox(event: PopoverDirective): void {

    const month = this.dateValue.getMonth();
    const date = this.dateValue.getDate();
    const fullYear = this.dateValue.getFullYear();
    const hours = this.timeValue.getHours();
    const minutes = this.timeValue.getMinutes();
    const seconds = this.timeValue.getSeconds();
    this.dateTimeValue = void 0;
    this.dateTimeValue = new Date();
    this.dateTimeValue.setMonth(month);
    this.dateTimeValue.setDate(date);
    this.dateTimeValue.setFullYear(fullYear);
    this.dateTimeValue.setHours(hours);
    this.dateTimeValue.setMinutes(minutes);
    this.dateTimeValue.setSeconds(seconds);
    this.dateTimeValue.setMilliseconds(0);
    this.selectedDateTimeValue = this.dateTimeValue.toString();
    this.currentDate = this.formatDateTime(this.dateTimeValue);
    event.hide();
    this.dateChange.emit(this.currentDate);
  }
  formatDateTime(value: Date): string {
    let formatedDateTime = '';
    const month = value.getMonth() + 1;
    const timeZone = value.toString().match(/([-\+][0-9]+)\s/)[1];
    formatedDateTime = value.getFullYear() + '-'
      + month + '-'
      + value.getDate() + 'T'
      + value.getHours() + ':'
      + value.getMinutes() + ':'
      + value.getSeconds() + '.'
      + value.getMilliseconds() + timeZone;
    return formatedDateTime;
  }
}
