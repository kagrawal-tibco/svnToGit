import { AfterViewInit, Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

@Component({
  selector: 'input-with-confirm',
  templateUrl: './input-with-confirm.component.html',
  styleUrls: ['./input-with-confirm.component.css']
})
export class InputWithConfirmComponent implements AfterViewInit {

  @Input()
  public placeholder: string;

  @Input()
  public required: boolean;

  @Input()
  public value: string;

  @Input()
  public checkbox: boolean;

  @Input()
  public checkboxLabel: string;

  @Input()
  public checkedValue: string;

  @Output()
  public result = new EventEmitter<string>();

  @Output()
  public cancel = new EventEmitter<boolean>();

  @ViewChild('inputEle', { static: false })
  public inputEle: ElementRef;

  private _checked: boolean;

  constructor(public i18n: I18n) {

  }

  ngAfterViewInit() {
    if (this.inputEle) {
      this.inputEle.nativeElement.focus();
    }
  }

  onConfirm() {
    if (this.checkbox && this.checked) {
      this.result.emit(this.checkedValue);
    } else {
      this.result.emit(this.value);
    }
  }

  canConfirm() {
    if (this.checkbox) {
      return this.checked || this.value;
    } else {
      return this.value;
    }
  }

  onCancel() {
    this.cancel.emit(true);
  }

  get checked() {
    return this._checked;
  }

  set checked(val: boolean) {
    this._checked = val;
    this.value = '';
    if (!val && this.inputEle) {
      setTimeout(() => {
        this.inputEle.nativeElement.focus();
      }, 0);
    }
  }

}
