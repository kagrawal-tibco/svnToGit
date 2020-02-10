import { NgModule } from '@angular/core';

import { TabsModule } from 'ngx-bootstrap';

import { ArtifactBatchImportComponent } from './pages/artifact-batch-import.component';
import { ArtifactBatchInfoComponent } from './pages/artifact-batch-info.component';
import { ArtifactInfoTreeComponent } from './pages/artifact-info-tree.component';
import { ProjectInfoEditComponent } from './pages/project-info-edit.component';
import { ProjectImporterComponent } from './project-importer.component';
import { ProjectImporterModal } from './project-importer.modal';
import { ProjectInfoComponent } from './project-info.component';
import { ProjectInfoModal } from './project-info.modal';

import { AddRemoveButtonComponent } from '../artifact-importer/add-remove-button.component';
import { ArtifactImporterModule } from '../artifact-importer/artifact-importer.module';
import { NavigableWizardModule } from '../navigable-wizard/navigable-wizard.module';
import { CreateSCMProject } from '../scm-integration/create-scm-project.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    ProjectInfoComponent,
    ProjectInfoModal,
    ProjectImporterComponent,
    ProjectImporterModal,
    ProjectInfoEditComponent,
    ArtifactBatchImportComponent,
    ArtifactBatchInfoComponent,
    ArtifactInfoTreeComponent,
  ],
  imports: [
    SharedModule,
    TabsModule,
    ArtifactImporterModule,
    NavigableWizardModule.withComponent([
      ProjectInfoEditComponent,
      ArtifactBatchImportComponent,
      ArtifactBatchInfoComponent,
      ArtifactInfoTreeComponent,
      CreateSCMProject
    ])
  ],
})
export class ProjectImporterModule {

}
