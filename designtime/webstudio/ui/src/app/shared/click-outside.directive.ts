import { Directive, ElementRef, EventEmitter, HostListener, Output } from '@angular/core';

@Directive({
  selector: '[outsideClick]'
})
export class ClickOutsideDirective {
  @Output()
  public outsideClick = new EventEmitter<boolean>();

  constructor(
    private _elementRef: ElementRef
  ) {
  }

  @HostListener('document:click', ['$event'])
  public onClick(event: Event) {
    const clickedInside = this._elementRef.nativeElement.contains(event.target);
    if (!clickedInside) {
      this.outsideClick.emit(true);
    }
  }
}
