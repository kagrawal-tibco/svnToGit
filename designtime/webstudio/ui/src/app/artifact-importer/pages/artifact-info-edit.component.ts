import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { NavigableWizardPageComponent } from '../../navigable-wizard/navigable-wizard-page.component';
import { ArtifactImporterContext } from '../artifact-importer-context';
@Component({
  templateUrl: './artifact-info-edit.component.html',
})
export class ArtifactInfoEditComponent extends NavigableWizardPageComponent<ArtifactImporterContext> implements OnInit {
  constructor(public i18n: I18n) {
    super();
  }

  ngOnInit() {
  }

  isValidPath(valid: boolean) {
    this.enableFinish(valid);
  }

}
