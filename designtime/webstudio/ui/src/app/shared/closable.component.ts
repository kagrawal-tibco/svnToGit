import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'closable',
  template: `
  <div class=''>
    <i (click)='onClose()' class="fa fa-close fa-sm clickable pull-right"></i>
  </div>`,
  styles: [
    `div {
      width: 100%;
      height: 10px;
      padding: 0 5px 0 0;
      background-color: #EEEEEE;
      vertical-align: middle;
      overflow: hidden;
    }`,
    `i {
      position: relative;
      top: -3px;
    }`
  ]
})
export class ClosableComponent {
  @Output()
  private close = new EventEmitter<boolean>();

  constructor() {
  }

  onClose() {
    this.close.emit(true);
  }

}
