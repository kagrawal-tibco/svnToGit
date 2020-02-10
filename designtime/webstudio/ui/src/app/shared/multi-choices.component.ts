import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'multi-choices',
  templateUrl: './multi-choices.component.html',
  styles: ['.customCard { border: none !important; }']
})
export class MultiChoicesComponent {
  @Input()
  title: string;

  @Input()
  description: string;

  @Input()
  choices: string[];

  @Input()
  selected: number;

  @Output()
  selectedChange: EventEmitter<number> = new EventEmitter<number>();

  constructor() {

  }

  onSelect(idx: number, val: boolean) {
    if (val) {
      this.selected = idx;
    } else if (idx === this.selected) {
      this.selected = -1;
    }
    this.selectedChange.emit(this.selected);
  }

  isSelected(idx: number) {
    return this.selected === idx;
  }
}
