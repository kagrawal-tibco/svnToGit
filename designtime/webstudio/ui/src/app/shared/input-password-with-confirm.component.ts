import { AfterViewInit, Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

@Component({
  selector: 'input-password-with-confirm',
  templateUrl: './input-password-with-confirm.component.html',
  styleUrls: ['./input-password-with-confirm.component.css']
})
export class InputPasswordWithConfirmComponent implements AfterViewInit {

  @Input()
  public placeholder: string;

  @Input()
  public required: boolean;

  @Input()
  public value: string;

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
    this.result.emit(this.value);
  }

  canConfirm() {
    return this.value;
  }

  onCancel() {
    this.cancel.emit(true);
  }

}
