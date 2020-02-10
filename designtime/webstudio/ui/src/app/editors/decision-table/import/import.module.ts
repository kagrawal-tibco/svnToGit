import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { FileUploadModule } from 'ng2-file-upload';

import { ImportArtifactModal } from './import.modal';

import { SharedModule } from '../../../shared/shared.module';

@NgModule({
  imports: [
    CommonModule,
    SharedModule,
    FileUploadModule
  ],
  declarations: [ImportArtifactModal],
  exports: [ImportArtifactModal]
})
export class ImportArtifactModule {

}
