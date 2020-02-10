import { Component, DoCheck, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { SBSchema } from './sb-schema-importer.component';
import { SchemaSelectorModal, SchemaSelectorModalContext } from './schema-selector.modal';

import { ModalService } from '../../core/modal.service';
import { Column, ColumnType, PropertyType } from '../../editables/decision-table/column';
import { DecisionTable } from '../../editables/decision-table/decision-table';
import { Rule } from '../../editables/decision-table/rule';
import { SelectWithConfirmComponent } from '../../shared/select-with-confirm.component';

@Component({
    selector: 'decision-table-filler',
    templateUrl: './decision-table-filler.component.html'
})
export class DecisionTableFiller {
    filling = false;

    @Input()
    name: string;

    @Input()
    basePath: string;

    @Input()
    startingTable?: DecisionTable;

    @Output()
    fullTable = new EventEmitter<DecisionTable>();

    protected schemaMap: Map<string, SBSchema> = new Map<string, SBSchema>();

    constructor(
        protected modal: ModalService,
        public i18n: I18n
    ) {
        this.startingTable = this.startingTable || new DecisionTable(
            this.name,
            this.basePath,
            []
        );
    }

    set schemas(schemas: SBSchema[]) {
        this.schemaMap.clear();
        schemas.forEach((schema) => {
            this.schemaMap.set(schema.name, schema);
        });
    }

    selectCondition(type: number) {
        this.select(type === 0 ? ColumnType.CONDITION : ColumnType.EXPR_CONDITION);
    }

    selectAction(type: number) {
        this.select(type === 0 ? ColumnType.ACTION : ColumnType.EXPR_ACTION);
    }

    private select(type: ColumnType) {
        this.selectSchema(
            type,
            this.startingTable
                ? { name: type.name, fields: this.startingTable.getColumns().filter(c => c.columnType === type) }
                : undefined
        ).then(
            (schema) => {
                if (schema) {
                    // Remove all members of the column type being edited.
                    this.startingTable = new DecisionTable(
                        this.startingTable.name,
                        this.startingTable.basePath,
                        this.startingTable.getColumns().filter(col => col.columnType !== type)
                    );
                    this.handleSchema(type, schema).map(
                        (col) => {
                            col.id = Number.parseInt(col.id)
                                + (this.startingTable.maxColumnId() > 0
                                    ? this.startingTable.maxColumnId() + 1
                                    : this.startingTable.maxColumnId())
                                + '';
                            return col;
                        }
                    ).forEach(this.startingTable.addColumn.bind(this.startingTable));
                    this.fullTable.emit(this.startingTable);
                }
            }
        );
    }

    private handleSchema(columnType: ColumnType, schema: any, namePrefix?: string) {
        const tempTable = new DecisionTable('', '', []);
        schema.fields.forEach(
            (field) => {
                if (field.type.type && field.type.type === 'tuple') {
                    this.handleSchema(columnType, field.type.schema, namePrefix ? namePrefix + '.' + field.name : field.name)
                        .map((col) => {
                            col.id = Number.parseInt(col.id) + tempTable.maxColumnId() + 1 + '';
                            return col;
                        })
                        .forEach(tempTable.addColumn.bind(tempTable));
                } else {
                    tempTable.createAndAddColumn(
                        namePrefix ? namePrefix + '.' + field.name : field.name,
                        PropertyType.fromName(field.type.type),
                        columnType,
                    );
                }
            }
        );
        return tempTable.getColumns().filter((column) => column.getId() !== 'PRIORITY');
    }

    private selectSchema(type: ColumnType, schema?: any) {
        return this.modal.open(SchemaSelectorModal, new SchemaSelectorModalContext(type, schema))
            .then(
                (ref) => ref as any as SBSchema
            ).catch(
                (dismissed) => dismissed
            );
    }
}
