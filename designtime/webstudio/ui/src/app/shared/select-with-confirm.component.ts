import { Component, EventEmitter, Input, Output } from '@angular/core';
// import { IOption } from 'ng-select';
import { MatSelectChange } from '@angular/material/select';
import { I18n } from '@ngx-translate/i18n-polyfill';

@Component({
  selector: 'select-with-confirm',
  templateUrl: './select-with-confirm.component.html',
  styleUrls: ['./select-with-confirm.component.css']
})
export class SelectWithConfirmComponent {

  @Input()
  public active: string[];

  @Input()
  public candidates: string[];

  @Input()
  public placeholder: string;

  @Output()
  public result = new EventEmitter<string[]>();

  @Output()
  public cancel = new EventEmitter<boolean>();

  // public values: IOption[];
  public values: MatSelectChange;
  constructor(public i18n: I18n) { }

  onConfirm() {
    // this.result.emit(this.values.map(value => value.value));
    this.result.emit(this.values.value);
  }

  onCancel() {
    this.cancel.emit(true);
  }
}
