import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { AgGridAngular } from 'ag-grid-angular';
import {
    ColDef,
    ColGroupDef,
    GetContextMenuItemsParams,
    GetMainMenuItemsParams,
    GridOptions,
    GridOptionsWrapper,
    MenuItemDef,
} from 'ag-grid-community/main';
import { ClipboardService, LicenseManager } from 'ag-grid-enterprise/main';

import { SBSchema } from '../../../artifact-importer/sb-schema-importer/sb-schema-importer.component';
import { Column, ColumnType, PropertyType } from '../../../editables/decision-table/column';
import { DecisionTableEditorService } from '../decisiontable-editor.service';

LicenseManager.setLicenseKey('TIBCO_MultiApp_20Devs25_April_2020__MTU4Nzc2OTIwMDAwMA==adf17342675c57f75c6d590022e12646');

@Component({
    selector: 'schema-editor',
    templateUrl: 'schema-editor.component.html'
})
export class SchemaEditor implements OnInit {
    @Input()
    baseSchema?: any;

    @Output() schema = new EventEmitter<SBSchema>();

    gridOptions: GridOptions = {
        suppressCopyRowsToClipboard: true,
    };

    columnDefs: ColGroupDef[] = [];

    rowData = [];

    availableTypes = PropertyType.TYPES;

    constructor(
        protected serviceDecisionTable: DecisionTableEditorService,
        public i18n: I18n
    ) { }

    ngOnInit() {
        this.columnDefs = [
            {
                headerName: this.baseSchema.name.name,
                children: [
                    {
                        headerName: this.i18n('Name'),
                        field: 'name',
                        cellRenderer: (params) => {
                            return params.data.name;
                        },
                        onCellValueChanged: this.emitRowData(this.schema),
                        editable: true,
                        cellEditor: 'agTextCellEditor',
                    },
                    {
                        headerName: this.i18n('Type'),
                        field: 'type',
                        editable: true,
                        cellRenderer: (params) => {
                            return params.data.type.name;
                        },
                        onCellValueChanged: this.emitRowData(this.schema),
                        cellEditor: 'agRichSelectCellEditor',
                        cellEditorParams: {
                            values: this.availableTypes,
                            cellRenderer: (params) => {
                                return params.value.name;
                            }
                        }
                    },
                    {
                        headerName: this.i18n('Expression'),
                        field: 'exp',
                        cellRenderer: (params) => {
                            return params.data.exp;
                        },
                        cellEditor: 'agRichSelectCellEditor',
                        cellEditorParams: {
                            values: [true, false]
                        },
                        editable: false,
                    },
                ]
            }
        ];

        this.baseSchema = this.baseSchema || {
            name: '',
            fields: [],
        };

        this.baseSchema.fields.forEach(
            field => {
                this.rowData.push(
                    {
                        name: field.name,
                        type: field.type,
                        id: field.id,
                        exp: field.exp,
                    }
                );
            }
        );
    }

    removeField() {
        this.gridOptions.api.getSelectedRows().forEach(
            (row) => {
                this.gridOptions.api.updateRowData({
                    remove: [row]
                });
            }
        );
        this.emitRowData(this.schema)(this.gridOptions);
    }

    addField(type: boolean) {
        this.gridOptions.api.updateRowData({ add: [{ name: this.i18n('New field'), type: PropertyType.STRING, exp: type, }] });
        this.emitRowData(this.schema)(this.gridOptions);
    }

    // Emmitter creator. This will need to be excecuted later.
    emitRowData(emitter) {
        return (params) => emitter.emit(
            {
                name: this.i18n('Condition') === params.api.gridOptionsWrapper.gridOptions.columnDefs[0].headerName
                    ? ColumnType.CONDITION : ColumnType.ACTION,
                fields: params.api.getModel().rowsToDisplay.map(row => ({
                    name: row.data.name,
                    type: row.data.type,
                    id: row.data.id,
                    exp: row.data.exp,
                })),
            }
        );
    }

    onGridReady(event: Event) {
        this.gridOptions.api.sizeColumnsToFit();
    }

    isRtl(): Boolean {
        return (navigator.language.search('ar') !== -1 ? true : false);
    }

}
