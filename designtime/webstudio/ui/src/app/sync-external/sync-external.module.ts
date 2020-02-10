import { NgModule } from '@angular/core';

import { TabsModule } from 'ngx-bootstrap';

import { SyncExternalComponent } from './sync-external.component';
import { SyncExternalModal } from './sync-external.modal';

import { ArtifactImporterModule } from '../artifact-importer/artifact-importer.module';
import { EditorsModule } from '../editors/editors.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [SharedModule, ArtifactImporterModule, EditorsModule, TabsModule],
  declarations: [SyncExternalModal, SyncExternalComponent],
  exports: [SyncExternalModal]
})
export class SyncExternalModule {

}
