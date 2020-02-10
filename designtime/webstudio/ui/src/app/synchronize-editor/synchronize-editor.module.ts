import { NgModule } from '@angular/core';

import { S_IFBLK } from 'constants';
import { BsDropdownModule } from 'ngx-bootstrap';

import { SynchronizeDecisionTableEditorComponent } from './synchronize-decision-table-editor.component';
import { SynchronizeEditorModal } from './synchronize-editor.modal';
import { SynchronizeStatusResolverComponent } from './synchronize-status-resolver.component';
import { Synchronize } from './synchronize.component';

import { EditorsModule } from '../editors/editors.module';
import { SharedModule } from '../shared/shared.module';
@NgModule({
  imports: [
    SharedModule,
    BsDropdownModule,
    EditorsModule
  ],
  declarations: [
    SynchronizeEditorModal,
    SynchronizeDecisionTableEditorComponent,
    SynchronizeStatusResolverComponent,
    Synchronize,
  ],
  exports: [SynchronizeEditorModal]
})
export class SynchronizeEditorModule {

}
