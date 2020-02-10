import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';

import { ModalModule } from 'ngx-modialog';

import { AuditTrailModal } from 'app/audit-trail/audit-trail.modal';
import { AuditTrailModule } from 'app/audit-trail/audit-trail.module';

import { EditMenubuilderService } from './edit-menubuilder-service';
import { LockArtifactModal } from './lock-artifact.modal';
import { ProjectExplorerComponent } from './project-explorer.component';
import { ProjectExplorerService } from './project-explorer.service';

import { ArtifactDeploymentModule } from '../../artifact-deployment/artifact-deployment.module';
import { DeploymentCreationModal } from '../../artifact-deployment/deployment-creation.modal';
import { DeploymentHistoryModal } from '../../artifact-deployment/deployment-history.modal';
import { DescriptorManageModal } from '../../artifact-deployment/descriptor-manage.modal';
import { ArtifactProblemsService } from '../../artifact-editor/artifact-problems.service';
import { BERenameModal } from '../../artifact-editor/rename-artifact.modal';
import { ArtifactImporterModal } from '../../artifact-importer/artifact-importer.modal';
import { ArtifactImporterModule } from '../../artifact-importer/artifact-importer.module';
import { ArtifactInfoModal } from '../../artifact-importer/artifact-info.modal';
import { ArtifactRevisionSelectorModal } from '../../artifact-revision-selector/artifact-revision-selector.modal';
import { ArtifactRevisionSelectorModule } from '../../artifact-revision-selector/artifact-revision-selector.module';
import { CheckoutCommitComponent } from '../../checkout-lifecycle/checkout-commit.component';
import { CheckoutExternalSyncComponent } from '../../checkout-lifecycle/checkout-external-sync.component';
import { CheckoutLifecycleModule } from '../../checkout-lifecycle/checkout-lifecycle.module';
import { CheckoutRevertComponent } from '../../checkout-lifecycle/checkout-revert.component';
import { CheckoutSyncComponent } from '../../checkout-lifecycle/checkout-sync.component';
import { GenerateDeployableModal } from '../../deployment/generate-deployable.modal';
import { GenerateDeployableModule } from '../../deployment/generate-deployable.module';
import { ImportArtifactModal } from '../../editors/decision-table/import/import.modal';
import { ImportArtifactModule } from '../../editors/decision-table/import/import.module';
import { ArtifactsExportModal } from '../../export/artifacts-export.modal';
import { ArtifactsExportModule } from '../../export/artifacts-export.module';
import { ArtifactHistoryModal } from '../../history/artifact-history.modal';
import { HistoryModule } from '../../history/history.module';
import { ProjectHistoryModal } from '../../history/project-history.modal';
import { ProjectInfoModal } from '../../project-importer/project-info.modal';
import { SyncExternalModal } from '../../sync-external/sync-external.modal';
import { SyncExternalModule } from '../../sync-external/sync-external.module';
import { TreeViewModule } from '../../widgets/tree-view/tree-view.module';

@NgModule({
  declarations: [ProjectExplorerComponent, LockArtifactModal],
  exports: [ProjectExplorerComponent],
  imports: [
    TreeViewModule,
    CommonModule,
    CheckoutLifecycleModule,
    HistoryModule,
    ArtifactImporterModule,
    ArtifactDeploymentModule,
    SyncExternalModule,
    ArtifactRevisionSelectorModule,
    GenerateDeployableModule,
    ImportArtifactModule,
    MatDialogModule,
    AuditTrailModule,
    ArtifactsExportModule,
    ModalModule.withComponents([
      ArtifactInfoModal,
      ArtifactHistoryModal,
      ProjectInfoModal,
      ProjectHistoryModal,
      ArtifactImporterModal,
      DeploymentCreationModal,
      DeploymentHistoryModal,
      DescriptorManageModal,
      SyncExternalModal,
      ArtifactRevisionSelectorModal,
      CheckoutCommitComponent,
      CheckoutRevertComponent,
      CheckoutSyncComponent,
      CheckoutExternalSyncComponent,
      GenerateDeployableModal,
      ImportArtifactModal,
      LockArtifactModal,
      BERenameModal,
      AuditTrailModal,
      ArtifactsExportModal
    ])
  ],
  providers: [ProjectExplorerService, EditMenubuilderService, ArtifactProblemsService]
})
export class ProjectExplorerModule {

}
