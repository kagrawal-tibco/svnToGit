import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ColDef, GridOptions, MenuItemDef } from 'ag-grid-community/main';

import { ArtifactProblemsService } from './artifact-problems.service';
import { ErrorRenderer } from './error-renderer.component';

import { environment } from '../../environments/environment';
import { ModalService } from '../core/modal.service';
import { Result } from '../models/dto';

@Component({
  selector: 'artifact-problems',
  templateUrl: './artifact-problems.component.html',
  styleUrls: ['./artifact-problems.component.css']
})
export class ArtifactProblemsComponent {
  public gridOptions: GridOptions = {
    defaultColDef: {
      resizable: true,
      sortable: true
    }
  };
  public columnDefs: Array<ColDef> = new Array<ColDef>();
  public _rowData: Array<any> = new Array<any>();
  _problems: Result[];
  private gridApi;
  private gridColumnApi;
  private rowSelection;
  private getContextMenuItems;
  private frameWorkComponents;
  private overlayNoRowsTemplate;

  constructor(
    private service: ArtifactProblemsService,
    private modal: ModalService,
    public i18n: I18n
  ) {
    this._problems = [];
    this.gridOptions = <GridOptions>{};
    this.frameWorkComponents = { errorRenderer: ErrorRenderer };
    this.columnDefs = [
      { headerName: '#', field: 'problemType', width: 50, cellRenderer: 'errorRenderer' },
      { headerName: this.i18n('Description'), field: 'description' },
      { headerName: this.i18n('Project'), field: 'project' },
      { headerName: this.i18n('Path'), field: 'path' },
      { headerName: this.i18n('Location'), field: 'location' },
      { headerName: this.i18n('Type'), field: 'type' }];
    this.rowSelection = 'single';
    const overlayText = this.i18n('No rows to show');
    this.overlayNoRowsTemplate = '<span i18n>' + overlayText + '</span>';

    this.getContextMenuItems = (params) => {
      return [
        {
          name: this.i18n('Delete'),
          action: () => {
            this.onDelete(params);
          },
          tooltip: this.i18n('Delete selected error/warning.'),
        },
        {
          name: this.i18n('Delete All Problems.'),
          action: () => {
            this.onDeleteAll(params);
          },
          tooltip: this.i18n('Delete all errors and warnings.')
        }
      ];
    };
  }

  onClick(r: Result) {
    this.service.onEntryClick(r);
  }

  icon(entry: Result) {
    if (entry.error) {
      return 'fa fa-times-circle error';
    } else {
      return 'fa fa-exclamation-triangle warning';
    }
  }

  onDeleteAll(params: any) {
    const entry: Result = <Result>params.node.data.result;
    this.modal.confirm()
      .message(this.i18n('Do you want to delete all entries? Deleted entries can be recreated by validating corresponding artifact/project or may not be recoverable.'))
      .open().result
      .then(
        () => {
          this.service.deleteAllProblems();
        },
        () => { }
      );
  }

  onDelete(params: any) {
    const entry: Result = <Result>params.node.data.result;
    this.modal.confirm()
      .message(this.i18n('Do you want to delete selected entries? Deleted entries can be recreated by validating corresponding artifact or may not be recoverable.'))
      .open().result
      .then(
        () => {
          this.service.deleteProblem(entry);
          if (entry.error) {
            const errors = this.service.errors;
            for (let i = 0; i < errors.length; i++) {
              if (errors[i] === entry) {
                errors.splice(i, 1);
              }
            }
          } else {
            for (let i = 0; i < this.warnings.length; i++) {
              if (this.warnings[i] === entry) {
                this.warnings.splice(i, 1);
              }
            }
          }
        },
        () => { }
      );
  }

  get hasProblems() {
    return this.service.hasProblems;
  }

  get problems() {
    if (!this.isBEUI()) {
      return this.service.problems;
    }
  }

  get rowData() {
    return this.service.rowData;
  }

  get hasWarnings() {
    return this.service.hasWarnings;
  }

  get warnings() {
    return this.service.warnings;
  }

  isBEUI(): boolean {
    return environment.enableBEUI;
  }

  onSelectionChanged() {
    const selectedRows = this.gridApi.getSelectedRows();
    this.onClick(selectedRows[0].result);
  }

  onGridReady(params) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    this.gridColumnApi.autoSizeColumns();
    this.gridApi.setSideBarVisible(false);
  }

  get disableDelete(): boolean {
    return this._rowData.length === 0;
  }
  isRtl(): Boolean {
    return (navigator.language.search('ar') !== -1 ? true : false);
  }

}
