/**
 * @Author: Rahil Khera
 * @Date:   2017-08-01T15:50:18+05:30
 * @Last modified by:   Rahil Khera
 * @Last modified time: 2017-08-01T15:51:06+05:30
 */

import { Component } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ProjectsComponent } from './projects.component';

import { ModalService } from '../../core/modal.service';
import { ProjectService } from '../../core/project.service';
import { SettingsService } from '../../core/settings.service';
import { WorkspaceService } from '../../workspace/workspace.service';

@Component({
  selector: 'be-project-preferecences',
  templateUrl: './be-projects.component.html',
})

export class BEProjectsComponent extends ProjectsComponent {

  constructor(protected service: SettingsService,
    protected modal: ModalService,
    protected workspace: WorkspaceService,
    protected project: ProjectService,
    public i18n: I18n) {
    super(service, modal, workspace, project, i18n);

  }

  onTabSelection(tabIndex: number) {
    if (tabIndex === 0) {
      console.log(this.i18n('Operator Setting Selected'));
    } else if (tabIndex === 1) {
      console.log(this.i18n('Notification Setting Selected'));
    }
  }

}
