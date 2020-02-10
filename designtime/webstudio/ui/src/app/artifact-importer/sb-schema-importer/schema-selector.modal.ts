import { Component, DoCheck, OnInit } from '@angular/core';

import { SelectModule } from 'ng-select';
import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { Column, ColumnType, PropertyType } from './../../editables/decision-table/column';
import { SBSchema } from './sb-schema-importer.component';

import { DecisionTable } from '../../editables/decision-table/decision-table';

export class SchemaSelectorModalContext extends BSModalContext implements DoCheck {
  public parsed: SBSchema;
  public error;

  unparsed: string;

  constructor(
    public type: ColumnType,
    public schema?: any,
  ) {
    super();
    this.isBlocking = true;
    this.keyboard = null;
    if (this.schema) {
      this.unparsed = JSON.stringify(this.schema,
        function (key, value) {
          if (key === 'columnType') {
            return undefined;
          } else if (value instanceof Column) {
            return {
              name: value.name,
              type: { type: value.propertyType.name }
            };
          } else {
            return value;
          }
        }
        , 5);
    }
  }

  ngDoCheck() {
    if (this.unparsed) {
      this.setUnparsed(this.unparsed);
    }
  }

  setUnparsed(blob: string) {
    try {
      // Is it JSON?
      this.error = undefined;
      const buffer = JSON.parse(blob);
      if (buffer && buffer.fields) {
        this.parsed = buffer as SBSchema;
      }
    } catch (error) {
      this.error = error;
      this.parsed = undefined;
    }
  }

  getUnparsed() {
    return this.unparsed;
  }
}

@Component({
  template: `
<div class='card panel-sm page'>
    <div class='card-header'>
      Please paste the {{context.type.name}} Schema in the text-box below.
    </div>
  <div class='card-body page-body'>
    <div class='alert alert-danger' *ngIf='context.error'>
      {{context.error}}
    </div>
    <textarea style='height: 350px; resize: none;' class='form-control' type='textarea'
    [(ngModel)]='context.unparsed'
    (input)='context.setUnparsed($event.target.value)'>
    </textarea>
  </div>
</div>
<div class='left-pad right-pad bottom-pad top-pad'>
  <span class='col-xs-6 text-left'>
    <button class='btn btn-outline-primary'
      type='cancel'
      (click)='handleResult(undefined)'
      > Cancel </button>
  </span>
  <span class='col-xs-6 text-right'>
    <button class='btn btn-primary'
      type='button'
      (click)='handleResult(context.parsed)'
      [disabled]='!context.parsed'
    > Import Schema </button>
    </span>
</div>
 `,
  styles: [
    '.alert { position: relative; }'
  ],
})
export class SchemaSelectorModal implements ModalComponent<SchemaSelectorModalContext> {
  context: SchemaSelectorModalContext;
  constructor(
    public dialog: DialogRef<SchemaSelectorModalContext>,
  ) {
    this.context = dialog.context;
  }

  handleResult(schema: SBSchema) {
    if (schema) {
      this.dialog.close(this.context.parsed);
    } else {
      this.dialog.dismiss();
    }
  }

}
