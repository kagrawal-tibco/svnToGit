
import { Component, ElementRef, EventEmitter, Input, Output } from '@angular/core';

import { fromEvent as observableFromEvent, Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';

@Component({
  selector: 'input-debounce',
  template: `<mat-form-field style="width: 100%">
    <span matPrefix><i class="fa fa-search {{isRtl() ? 'left-pad' : 'right-pad' }}"></i></span>
    <input matInput type="text" [placeholder]="placeholder" [(ngModel)]="inputValue"/>
    <button mat-button *ngIf="value" matSuffix mat-icon-button aria-label="Clear" (click)="clearValue()">
    <i class="fa fa-close"></i>
    </button>
  </mat-form-field>`
})
export class InputDebounceComponent {
  @Input() placeholder: string;
  @Input() hint: string;
  @Input() delay = 300;
  @Output() value: EventEmitter<string> = new EventEmitter();

  public inputValue: string;

  constructor(private elementRef: ElementRef) {
    const eventStream = observableFromEvent(elementRef.nativeElement, 'keyup').pipe(
      map(() => this.inputValue),
      debounceTime(this.delay),
      distinctUntilChanged());

    eventStream.subscribe(input => this.value.emit(input));
  }

  clearValue() {
    this.inputValue = '';
    this.value.emit(this.inputValue);
  }

  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

}
