import { NgModule } from '@angular/core';

import { AgGridModule } from 'ag-grid-angular';
import { FileUploadModule } from 'ng2-file-upload';
import { TabsModule } from 'ngx-bootstrap';
import { TooltipModule } from 'ngx-bootstrap';

import { AddRemoveButtonComponent } from './add-remove-button.component';
import { ArtifactImporterComponent } from './artifact-importer.component';
import { ArtifactImporterModal } from './artifact-importer.modal';
import { ArtifactInfoComponent } from './artifact-info.component';
import { ArtifactInfoModal } from './artifact-info.modal';
import { ArtifactUploaderComponent } from './artifact-uploader.component';
import { FileTreeComponent } from './file-tree.component';
import { ArtifactInfoEditComponent } from './pages/artifact-info-edit.component';
import { CreateOrImportComponent } from './pages/create-or-import.component';
import { SbSchemaImporterModule } from './sb-schema-importer/sb-schema-importer.module';

import { MetadataEditorModule } from '../editors/metadata/metadata-editor.module';
import { NavigableWizardModule } from '../navigable-wizard/navigable-wizard.module';
import { SCMIntegrationModule } from '../scm-integration/scm-integration.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    SCMIntegrationModule,
    SharedModule,
    TabsModule,
    FileUploadModule,
    MetadataEditorModule,
    SbSchemaImporterModule,
    TooltipModule,
    AgGridModule.withComponents([]),
    NavigableWizardModule.withComponent([
      CreateOrImportComponent,
      ArtifactInfoEditComponent
    ])
  ],
  exports: [ArtifactImporterComponent, ArtifactUploaderComponent, ArtifactInfoComponent, FileTreeComponent, AddRemoveButtonComponent],
  declarations: [
    ArtifactImporterComponent,
    ArtifactImporterModal,
    ArtifactInfoComponent,
    CreateOrImportComponent,
    ArtifactInfoEditComponent,
    ArtifactInfoModal,
    ArtifactUploaderComponent,
    FileTreeComponent,
    AddRemoveButtonComponent
  ],
  entryComponents: [AddRemoveButtonComponent],
})
export class ArtifactImporterModule { }
