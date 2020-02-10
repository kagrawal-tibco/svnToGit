import { NgModule } from '@angular/core';

import { DatepickerModule, TabsModule, TimepickerModule, TooltipModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-modialog';

import { ArtifactDeploymentRoutingModule } from './artifact-deployment-routing.module';
import { DeploymentCreationModal } from './deployment-creation.modal';
import { DeploymentHistoryComponent } from './deployment-history.component';
import { DeploymentHistoryModal } from './deployment-history.modal';
import { DeploymentInfoComponent } from './deployment-info.component';
import { DeploymentService } from './deployment.service';
import { DescriptorInfoComponent } from './descriptor-info.component';
import { DescriptorManageModal } from './descriptor-manage.modal';
import { CreateOrSelectComponent } from './pages/create-or-select.component';
import { DeployConfigComponent } from './pages/deploy-config.component';

import { EditorLoaderModal } from '../editors/editor-loader.modal';
import { EditorsModule } from '../editors/editors.module';
import { NavigableWizardModule } from '../navigable-wizard/navigable-wizard.module';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    SharedModule,
    TabsModule,
    TooltipModule,
    DatepickerModule,
    TimepickerModule,
    EditorsModule,
    ArtifactDeploymentRoutingModule,
    ModalModule.withComponents([
      EditorLoaderModal
    ]),
    NavigableWizardModule.withComponent([
      CreateOrSelectComponent,
      DeployConfigComponent
    ])
  ],
  declarations: [
    DeploymentCreationModal,
    DescriptorManageModal,
    DescriptorInfoComponent,
    DeploymentHistoryModal,
    DeploymentInfoComponent,
    CreateOrSelectComponent,
    DeployConfigComponent,
    DeploymentHistoryComponent
  ],
  exports: [],
  providers: [DeploymentService],
})
export class ArtifactDeploymentModule { }
