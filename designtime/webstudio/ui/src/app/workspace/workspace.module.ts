import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatTooltipModule } from '@angular/material/tooltip';
import { RouterModule } from '@angular/router';

import { AngularSplitModule } from 'angular-split';
import { FileUploadModule } from 'ng2-file-upload';
import { TabsModule } from 'ngx-bootstrap';
import { ModalModule } from 'ngx-modialog';

import { ContentGroupModule } from './content-group/content-group.module';
import { MultitabEditorModule } from './multitab-editor/multitab-editor.module';
import { OpenEditorsModule } from './open-editors/open-editors.module';
import { ProjectExplorerModule } from './project-explorer/project-explorer.module';
import { VisitHistoryModule } from './visit-history/visit-history.module';
import { WelcomeComponent } from './welcome/welcome.component';
import { WorkspaceComponent } from './workspace.component';
import { WorkspaceService } from './workspace.service';

import { BEProjectImportModule } from '../be-project-importer/be-project-importer.module';
import { SidebarComponent } from '../layout/sidebar.component';
import { ProjectCheckoutModal } from '../project-checkout/project-checkout.modal';
import { ProjectCheckoutModule } from '../project-checkout/project-checkout.module';
import { ProjectImporterModal } from '../project-importer/project-importer.modal';
import { ProjectImporterModule } from '../project-importer/project-importer.module';

@NgModule({
  imports: [
    CommonModule,
    ProjectExplorerModule,
    ContentGroupModule,
    MultitabEditorModule,
    VisitHistoryModule,
    OpenEditorsModule,
    ProjectImporterModule,
    ProjectCheckoutModule,
    AngularSplitModule,
    TabsModule,
    RouterModule,
    FileUploadModule,
    BEProjectImportModule,
    MatButtonModule,
    MatMenuModule,
    MatIconModule,
    MatCardModule,
    MatListModule,
    MatTooltipModule,
    MatExpansionModule,
    ModalModule.withComponents([ProjectImporterModal, ProjectCheckoutModal, BEProjectImportModule]),
  ],
  declarations: [WorkspaceComponent, WelcomeComponent, SidebarComponent],
  providers: [
    WorkspaceService
  ]
})
export class WorkspaceModule {

}
