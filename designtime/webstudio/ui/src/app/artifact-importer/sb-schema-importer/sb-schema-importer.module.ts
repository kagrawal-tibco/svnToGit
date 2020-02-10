import { NgModule } from '@angular/core';

import { FileUploadModule } from 'ng2-file-upload';
import { TabsModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-modialog';

import { DecisionTableFiller } from './decision-table-filler.component';
import { SbSchemaImporter } from './sb-schema-importer.component';
import { SchemaSelectorModal, SchemaSelectorModalContext } from './schema-selector.modal';

import { SharedModule } from '../../shared/shared.module';
@NgModule({
  imports: [
    SharedModule,
    TabsModule,
    FileUploadModule,
    ModalModule.withComponents([
      SchemaSelectorModal
    ])
  ],
  declarations: [
    DecisionTableFiller,
    SbSchemaImporter,
    SchemaSelectorModal,
  ],
  exports: [
    DecisionTableFiller,
  ]
})
export class SbSchemaImporterModule { }
