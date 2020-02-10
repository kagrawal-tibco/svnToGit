import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { AgGridColumn, AgGridModule, } from 'ag-grid-angular';
import { ModalModule } from 'ngx-modialog';

import { SchemaEditor } from './schema-editor.component';
import { SchemaEditorModal } from './schema-editor.modal';

import { SharedModule } from '../../../shared/shared.module';

@NgModule({
    imports: [
        CommonModule,
        ModalModule.withComponents([
            SchemaEditorModal,
        ]),
        AgGridModule.withComponents([
            AgGridColumn,
        ]),
    ],
    exports: [],
    declarations: [SchemaEditor, SchemaEditorModal],
    providers: [],
})
export class SchemaEditorModule { }
