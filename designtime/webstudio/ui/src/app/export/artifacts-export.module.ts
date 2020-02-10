import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { SelectModule } from 'ng-select';

import { ArtifactsExportComponent } from './artifacts-export.component';
import { ArtifactsExportModal } from './artifacts-export.modal';

import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [SharedModule, SelectModule, FormsModule],
  declarations: [ArtifactsExportModal, ArtifactsExportComponent],
  exports: [ArtifactsExportModal]
})
export class ArtifactsExportModule {
}
