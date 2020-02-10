import { Component, Input } from '@angular/core';

@Component({
  selector: 'permission-instance',
  template: `
    <i *ngIf='!isWildcard' class="fa fa-terminal" aria-hidden="true"></i>
    <i *ngIf='isWildcard' class="fa fa-globe" aria-hidden="true"></i>
    <span class='monospace'><small>{{ isWildcard ? 'All' : input }}</small></span>
  `

})
export class PermissionInstanceComponent {
  @Input()
  input: string;

  get isWildcard() {
    return this.input === '*';
  }

}
