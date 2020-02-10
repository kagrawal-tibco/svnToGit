import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatExpansionModule } from '@angular/material';
import { MatButtonModule } from '@angular/material/button';
import { MatOptionModule } from '@angular/material/core';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { NgDragDropModule } from 'ng-drag-drop';
import { AccordionModule, TabsModule } from 'ngx-bootstrap';
import { TooltipModule } from 'ngx-bootstrap';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { ModalModule } from 'ngx-modialog';

import { BEContentModelService } from './be.content-model.service';
import { ContentGroupComponent } from './content-group.component';
import { ContentModelService } from './content-model.service';
import { ContentPaneModule } from './content-pane/content-pane-module';
import { ContentPaneComponent } from './content-pane/content-pane.component';
import { CreateGroupModal } from './create-group/create-group.modal';
import { EditGroupModal } from './edit-group/edit-group.modal';
import { InputDebounceComponent } from './input-debounce.component';

import { environment } from '../../../environments/environment';
import { BEProjectImportModule } from '../../be-project-importer/be-project-importer.module';
import { CommitSharedModule } from '../../commit-shared/commit-shared.module';
import { ArtifactService } from '../../core/artifact.service';
import { ModalService } from '../../core/modal.service';
import { ProjectService } from '../../core/project.service';
import { RestService } from '../../core/rest.service';
import { TreeViewModule } from '../../widgets/tree-view/tree-view.module';
import { OpenEditorsModule } from '../open-editors/open-editors.module';
import { ContextMenuService } from '../project-explorer/context-menu.service';
import { ProjectExplorerComponent } from '../project-explorer/project-explorer.component';

@NgModule({
  imports: [
    MatInputModule,
    MatSelectModule,
    MatOptionModule,
    MatIconModule,
    MatExpansionModule,
    MatTooltipModule,
    InfiniteScrollModule,
    CommonModule,
    OpenEditorsModule,
    FlexLayoutModule,
    TabsModule,
    CommitSharedModule,
    AccordionModule,
    FormsModule,
    ReactiveFormsModule,
    TreeViewModule,
    TooltipModule,
    MatListModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    ContentPaneModule,
    NgDragDropModule.forRoot(),
    ModalModule.withComponents([CreateGroupModal, EditGroupModal, BEProjectImportModule])],
  declarations: [
    ContentGroupComponent,
    CreateGroupModal,
    EditGroupModal,
    InputDebounceComponent
  ],
  exports: [ContentGroupComponent, InputDebounceComponent],
  providers: [
    // { provide: ContentModelService, useClass: BEContentModelService },
    {
      provide: ContentModelService,
      useFactory: contentModelServiceFactory,
      deps: [ProjectService, ArtifactService, ModalService, RestService, I18n]
    },
    I18n,
    ContentPaneComponent,
    ContextMenuService,
    ProjectExplorerComponent
  ],
})

export class ContentGroupModule {

}

export function contentModelServiceFactory(project: ProjectService, artifact: ArtifactService,
  modal: ModalService, rest: RestService, i18n: I18n) {
  if (environment.enableBEUI) {
    return new BEContentModelService(project, artifact, modal, rest, i18n);
  } else {
    return new ContentModelService(project, artifact, modal, rest, i18n);
  }
}
