
import { AfterViewInit, Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Context, ContextMenuService } from './context-menu.service';
import { EditMenubuilderService } from './edit-menubuilder-service';
import { ProjectExplorerService } from './project-explorer.service';

import { ArtifactEditorService } from '../../artifact-editor/artifact-editor.service';
import { AlertService } from '../../core/alert.service';
import { ArtifactService } from '../../core/artifact.service';
import { LifecycleService } from '../../core/lifecycle.service';
import { Logger } from '../../core/logger.service';
import { ModalService } from '../../core/modal.service';
import { ProjectService } from '../../core/project.service';
import { SettingsService } from '../../core/settings.service';
import { EventType, TreeViewEvent } from '../../widgets/tree-view/tree-view-event';
import { MultitabEditorService } from '../multitab-editor/multitab-editor.service';
import { VisitHistoryService } from '../visit-history/visit-history.service';
import { WorkspaceService } from '../workspace.service';

@Component({
  selector: 'project-explorer',
  templateUrl: './project-explorer.component.html',
  providers: [ContextMenuService]
})
export class ProjectExplorerComponent implements OnInit, AfterViewInit {
  constructor(
    private project: ProjectService,
    private artifact: ArtifactService,
    private artifactEditorService: ArtifactEditorService,
    private lifecycle: LifecycleService,
    private visitHistory: VisitHistoryService,
    private log: Logger,
    private modal: ModalService,
    private service: ProjectExplorerService,
    private settings: SettingsService,
    private alert: AlertService,
    private workspace: WorkspaceService,
    private multiTab: MultitabEditorService,
    private ctxMenu: ContextMenuService,
    private editCtxMenu: EditMenubuilderService,
    public i18n: I18n
  ) {

  }

  getRoots() {
    return this.service.roots;
  }

  ngOnInit() {
  }

  ngAfterViewInit() {
    this.ctxMenu.initWithComponent(this, this.editCtxMenu.getMenuBuilder(false));
  }

  handleTreeEvent(event: TreeViewEvent) {
    const node = event.node;
    if (event.type === EventType.RIGHT_CLICK) {
      const e: MouseEvent = event.event;
      const ctx: Context = {
        data: node,
        event: e,
        manager: this.service.manager
      };
      this.ctxMenu.showMenu(ctx);
    }
  }

  getMessage(): string {
    const msg = this.i18n('There are no visible projects. Go to {{0}}Settings > Projects Configuration{{1}} to make projects visible here.', { 0: `<em>`, 1: `</em>` });
    return msg;
  }

}
