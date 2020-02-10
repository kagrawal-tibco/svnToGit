import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { NavigableWizardPageComponent } from '../../navigable-wizard/navigable-wizard-page.component';
import { ProjectImporterContext } from '../project-importer-context';

@Component({
  templateUrl: './project-info-edit.component.html'
})
export class ProjectInfoEditComponent extends NavigableWizardPageComponent<ProjectImporterContext> implements OnInit {
  constructor(public i18n: I18n) {
    super();
  }

  ngOnInit() {

  }

  isProjectNameValid(valid: boolean) {
    this.enableNextStep(valid);
    this.enableFinish(valid);
  }
}
