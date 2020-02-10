import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { SelectModule } from 'ng-select';
import { FileUploadModule } from 'ng2-file-upload';

import { BEImportProjectComponent } from './be-project-importer.modal';

import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [SharedModule, SelectModule, FormsModule, FileUploadModule],
  declarations: [BEImportProjectComponent],
  exports: [],
  entryComponents: [BEImportProjectComponent]
})
export class BEProjectImportModule {
}
