import { NgModule } from '@angular/core';

import { TabsModule } from 'ngx-bootstrap';

import { CreateSCMProject } from './create-scm-project.component';
import { SandboxArtifactImporter } from './sandbox-artifact-importer.component';

import { SharedModule } from '../shared/shared.module';
import { TreeViewModule } from '../widgets/tree-view/tree-view.module';
@NgModule({
  imports: [
    TreeViewModule,
    SharedModule,
    TabsModule,
  ],
  declarations: [
    SandboxArtifactImporter,
    CreateSCMProject,
  ],
  exports: [
    SandboxArtifactImporter,
    CreateSCMProject
  ]
})
export class SCMIntegrationModule {

}
