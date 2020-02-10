import { NgModule } from '@angular/core';

import { ArtifactRevisionSelectorModal } from './artifact-revision-selector.modal';

import { HistoryModule } from '../history/history.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [SharedModule, HistoryModule],
  declarations: [ArtifactRevisionSelectorModal],
  exports: [ArtifactRevisionSelectorModal]
})
export class ArtifactRevisionSelectorModule {

}
