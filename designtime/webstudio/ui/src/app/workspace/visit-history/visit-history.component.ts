import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { VisitEntry, VisitHistoryService } from './visit-history.service';

import { ProjectService } from '../../core/project.service';
import { WorkspaceService } from '../workspace.service';
@Component({
  selector: 'visit-history',
  templateUrl: './visit-history.component.html',
  styleUrls: ['./visit-history.component.css'],
})
export class VisitHistoryComponent {
  constructor(
    private service: VisitHistoryService,
    private workspace: WorkspaceService,
    private project: ProjectService,
    public i18n: I18n
  ) { }

  history() {
    return this.service.history;
  }

  select(e: VisitEntry) {
    if (e !== this.service.selected) {
      this.workspace.activateAll(e.artifact);
    }
  }

  selected() {
    return this.service.selected;
  }

  projectName(projectId: string) {
    return this.project.getProjectMeta(projectId).projectName;
  }
}
