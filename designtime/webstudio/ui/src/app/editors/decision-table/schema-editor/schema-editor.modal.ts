import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { SBSchema } from '../../../artifact-importer/sb-schema-importer/sb-schema-importer.component';
import { Column, ColumnType } from '../../../editables/decision-table/column';
import { ResizableModal } from '../../../shared/resizablemodal';

export class SchemaEditorContext extends BSModalContext {
    dialogClass = 'modal-dialog modal-extra-wide';
    constructor(
        public title: string,
        public columns?: Column[],
        public schema?: SBSchema,
    ) {
        super();
    }
}

@Component({
    selector: 'schema-editor-modal',
    templateUrl: 'schema-editor.modal.html'
})

export class SchemaEditorModal extends ResizableModal implements ModalComponent<SchemaEditorContext>, OnInit {
    public context: SchemaEditorContext;
    public schemas: any[] = [];

    constructor(
        public dialog: DialogRef<SchemaEditorContext>,
        public i18n: I18n
    ) {
        super(dialog.context, dialog.context.dialogClass);
        this.context = dialog.context;
    }

    ngOnInit() {
        // Map this.dialog.context to the proper schemas
        if (this.dialog.context.columns) {
            this.schemas.push({
                name: ColumnType.CONDITION,
                fields: this.dialog.context.columns.filter(
                    (column) => {
                        if (column.columnType === ColumnType.CONDITION
                            || column.columnType === ColumnType.EXPR_CONDITION) {
                            return true;
                        }
                        return false;
                    }
                ).map(
                    (column) => {
                        return {
                            name: column.name,
                            type: column.propertyType,
                            id: column.getId(),
                            exp: column.columnType === ColumnType.EXPR_CONDITION,
                        };
                    }
                ),
            });
            this.schemas.push({
                name: ColumnType.ACTION,
                fields: this.dialog.context.columns.filter(
                    (column) => {
                        if (column.columnType === ColumnType.ACTION
                            || column.columnType === ColumnType.EXPR_ACTION) {
                            return true;
                        }
                        return false;
                    }
                ).map(
                    (column) => ({
                        name: column.name,
                        type: column.propertyType,
                        id: column.getId(),
                        exp: column.columnType === ColumnType.EXPR_ACTION,
                    })
                )
            });
        }
    }

    handleChangedSchema(schema) {
        this.schemas.forEach((originalSchema) => {
            if (originalSchema.name === schema.name) {
                originalSchema.fields = schema.fields;
            }
        });
    }

    onSubmit(finished) {
        if (finished) {
            const fields = [];
            this.schemas.forEach(schema => schema.fields.forEach(field => {
                let col = schema.name;
                if (field.exp) {
                    col = schema.name === ColumnType.CONDITION ? ColumnType.EXPR_CONDITION : ColumnType.EXPR_ACTION;
                }
                // if (!mapped[col.value]) {
                // mapped[col.value] = [];
                // }
                fields.push({
                    name: field.name,
                    type: field.type,
                    id: field.id,
                    columnType: col,
                });
            }));
            this.dialog.close(fields);
        } else {
            this.dialog.dismiss();
        }
    }
}
