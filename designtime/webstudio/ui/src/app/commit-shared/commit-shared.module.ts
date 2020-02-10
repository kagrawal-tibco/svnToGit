import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatOptionModule } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

import { ModalModule } from 'ngx-modialog';

import { ChangeListComponent } from './change-list.component';
import { CommitDelegateModal } from './commit-delegate.modal';
import { CommitItemDeployModal } from './commit-item-deploy.modal';
import { CommitItemListComponent } from './commit-item-list.component';
import { CommitItemComponent } from './commit-item.component';
import { CommitItemModal } from './commit-item.modal';

import { EditorLoaderModal } from '../editors/editor-loader.modal';
import { EditorsModule } from '../editors/editors.module';
import { SharedModule } from '../shared/shared.module';
import { SynchronizeEditorModal } from '../synchronize-editor/synchronize-editor.modal';
import { SynchronizeEditorModule } from '../synchronize-editor/synchronize-editor.module';

@NgModule({
  imports: [
    SharedModule,
    EditorsModule,
    MatInputModule,
    MatOptionModule,
    MatSelectModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    SynchronizeEditorModule,
    ModalModule.withComponents([EditorLoaderModal, SynchronizeEditorModal, CommitItemDeployModal, CommitDelegateModal])
  ],
  declarations: [
    ChangeListComponent,
    CommitItemComponent,
    CommitItemListComponent,
    CommitItemModal,
    CommitItemDeployModal,
    CommitDelegateModal
  ],
  exports: [ChangeListComponent, CommitItemComponent, CommitItemListComponent]
})
export class CommitSharedModule {
}
