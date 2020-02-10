import { Input } from '@angular/core';
import { Component } from '@angular/core';

import { ICellRendererAngularComp } from 'ag-grid-angular/main';
import { IAfterGuiAttachedParams } from 'ag-grid-community';

import { Artifact } from '../models/artifact';

@Component({
  selector: 'add-remove-button',
  template: `<div class="products-actions">
        <a *ngIf="folder()" (click)="invokeEditMethod()"
            class="fa fa-plus-circle action-icon"
            title="Add new artifact"
            style="font-size: 16px; padding-left: 3px"
            aria-hidden="true">
        </a>
        <span class="sr-only">Add</span>
        <a (click)="invokeDeleteMethod()"
            class="fa fa-trash text-danger"
            title="Remove artifact"
            style="font-size: 16px; padding-left: 3px"
            aria-hidden="true">
        </a>
        <span class="sr-only">Delete</span>
    </div>`
})
export class AddRemoveButtonComponent implements ICellRendererAngularComp {
  public params: any;

  agInit(params: any): void {
    this.params = params;
  }
  public folder() {
    return false; // this.params.data.children !== undefined;
  }

  public invokeDeleteMethod() {
    const rows = this.getRowsToRemove(this.params.node);
    this.params.api.updateRowData({ remove: rows });
  }

  getRowsToRemove(node) {
    let res = [];
    for (let i = 0; i < node.childrenAfterGroup.length; i++) {
      res = res.concat(this.getRowsToRemove(node.childrenAfterGroup[i]));
    }

    // ignore nodes that have no data, i.e. 'filler groups'
    return node.data ? res.concat([node.data]) : res;
  }

  public invokeEditMethod() {
    this.params.api.startEditingCell({
      rowIndex: this.params.node.rowIndex,
      colKey: 'filename',
    }
    );
  }

  refresh(params: any): boolean {
    // TODO Auto-generated method stub
    return;
  }
  afterGuiAttached(params?: IAfterGuiAttachedParams): void {
    // TODO Auto-generated method stub
    return;
  }

}
