import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';
@Component({
  selector: 'confirm-deleter',
  templateUrl: './confirm-deleter.component.html',
})
export class ConfirmDeleterComponent implements OnInit, AfterViewInit {
  @Input()
  msg: string;

  @Input()
  input: string;

  @Output()
  output: EventEmitter<boolean> = new EventEmitter<boolean>();

  typed: string;

  @ViewChild('typedInput', { static: false })
  private typedInput: ElementRef;

  constructor(public i18n: I18n) {
  }

  ngOnInit() {
    if (!this.msg) {
      this.msg = 'Please repeat ' + this.input + ' to confirm.';
    }
  }

  ngAfterViewInit() {
    this.typedInput.nativeElement.focus();
  }

  canConfirm(): boolean {
    return this.typed === this.input;
  }

  onConfirm() {
    this.output.emit(true);
  }

  onCancel() {
    this.output.emit(false);
  }

  getMessage(): string {
    const msg = this.i18n('This action {{0}}cannot{{1}} be undone. This will permanently remove\
    {{0}}ALL{{1}} data for project{{0}}{{input}}{{1}}.The deletion is {{0}}unrecoverable{{1}}.\
  {{2}}\
  {{2}}\
  Please type the name of this project to confirm the deletion.', { 0: `<b>`, 1: `</b>`, input: this.input, 2: `<br>` });
    return msg;
  }
}
