import { NgModule } from '@angular/core';

import { ModalModule } from 'ngx-modialog';

import { ArtifactHistoryEntryComponent } from './artifact-history-entry.component';
import { ArtifactHistoryModal } from './artifact-history.modal';
import { ProjectHistoryModal } from './project-history.modal';

import { CommitItemModal } from '../commit-shared/commit-item.modal';
import { CommitSharedModule } from '../commit-shared/commit-shared.module';
import { EditorLoaderModal } from '../editors/editor-loader.modal';
import { EditorsModule } from '../editors/editors.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    EditorsModule,
    CommitSharedModule,
    ModalModule.withComponents([
      EditorLoaderModal,
      ProjectHistoryModal,
      CommitItemModal
    ])
  ],
  declarations: [ArtifactHistoryModal, ProjectHistoryModal, ArtifactHistoryEntryComponent],
  exports: [ArtifactHistoryEntryComponent]
})
export class HistoryModule {

}
