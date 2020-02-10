import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AgGridModule } from 'ag-grid-angular/main';
import { AngularSplitModule } from 'angular-split';
import { ButtonsModule, TabsModule } from 'ngx-bootstrap';
import { AccordionModule, TooltipModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-modialog';

import { ArtifactEditorComponent } from './artifact-editor.component';
import { ArtifactEditorService } from './artifact-editor.service';
import { ArtifactPanelComponent } from './artifact-panel.component';
import { ArtifactProblemsComponent } from './artifact-problems.component';
import { ArtifactPropertiesComponent } from './artifact-properties.component';
import { ArtifactPropertiesService } from './artifact-properties.service';
import { ArtifactStatusBarComponent } from './artifact-status-bar.component';
import { ArtifactTestDataComponent } from './artifact-testdata.component';
import { ArtifactTestDataService } from './artifact-testdata.service';
import { BEDecisionTableTestDataModal } from './decision-table-testdata.modal';
import { DecisionTableAnalyzerComponent } from './decisiontable-analyzer.component';
import { ErrorRenderer } from './error-renderer.component';
import { BERenameModal } from './rename-artifact.modal';

import { CheckoutCommitComponent } from '../checkout-lifecycle/checkout-commit.component';
import { CheckoutLifecycleModule } from '../checkout-lifecycle/checkout-lifecycle.module';
import { EditorsModule } from '../editors/editors.module';
import { SharedModule } from '../shared/shared.module';
import { SyncExternalModal } from '../sync-external/sync-external.modal';
import { SyncExternalModule } from '../sync-external/sync-external.module';
import { SynchronizeEditorModal } from '../synchronize-editor/synchronize-editor.modal';
import { SynchronizeEditorModule } from '../synchronize-editor/synchronize-editor.module';
import { VerifyConfigModal } from '../verify/verify-config.modal';
import { VerifyModule } from '../verify/verify.module';
import { MultitabEditorService } from '../workspace/multitab-editor/multitab-editor.service';
@NgModule({
  declarations: [
    ArtifactEditorComponent,
    ArtifactPanelComponent,
    ArtifactStatusBarComponent,
    ArtifactProblemsComponent,
    ArtifactTestDataComponent,
    ArtifactPropertiesComponent,
    DecisionTableAnalyzerComponent,
    BEDecisionTableTestDataModal,
    BERenameModal,
    ErrorRenderer
  ],
  exports: [ArtifactEditorComponent,
    VerifyModule,
    BERenameModal,
    ArtifactProblemsComponent,
    ArtifactPanelComponent,
    ArtifactStatusBarComponent],
  imports: [
    SharedModule,
    CommonModule,
    EditorsModule,
    SynchronizeEditorModule,
    SyncExternalModule,
    CheckoutLifecycleModule,
    AngularSplitModule,
    VerifyModule,
    TabsModule,
    AccordionModule.forRoot(),
    TooltipModule,
    AccordionModule.forRoot(),
    ModalModule.withComponents([
      CheckoutCommitComponent,
      SynchronizeEditorModal,
      SyncExternalModal,
      VerifyConfigModal,
      BEDecisionTableTestDataModal
    ]),
    AgGridModule.withComponents([ErrorRenderer
    ]),
    ButtonsModule.forRoot(),
    FormsModule,
  ],
  providers: [ArtifactEditorService, MultitabEditorService, ArtifactPropertiesService, ArtifactTestDataService]
})
export class ArtifactEditorModule {

}
