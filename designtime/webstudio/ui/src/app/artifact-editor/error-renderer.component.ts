import { Component, OnDestroy } from '@angular/core';

import { ICellRendererAngularComp } from 'ag-grid-angular';

@Component({
  selector: 'error-warning',
  template: `<i [class]='icon()'></i>`
})
export class ErrorRenderer implements ICellRendererAngularComp, OnDestroy {
  private params: any;

  agInit(params: any): void {
    this.params = params;
  }

  public icon(): string {
    if (this.params.value === 'Error') {
      return 'fa fa-times-circle error';
    } else {
      return 'fa fa-exclamation-triangle warning';
    }
  }

  ngOnDestroy() {

  }

  refresh(): boolean {
    return false;
  }
}
