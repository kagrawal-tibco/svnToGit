import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { Artifact } from '../../models/artifact';
import { NavigableWizardPageComponent } from '../../navigable-wizard/navigable-wizard-page.component';
import { ValidPathDirective } from '../../shared/valid-path.directive';
import { ProjectImporterContext } from '../project-importer-context';

@Component({
  templateUrl: './artifact-batch-import.component.html'
})
export class ArtifactBatchImportComponent extends NavigableWizardPageComponent<ProjectImporterContext> implements OnInit {

  public valid = true;
  public message: string;
  public i18n: I18n;
  // constructor(public i18n:I18n){super()}

  ngOnInit() {
    this.params.projectInfo.type = 'NATIVE';
  }

  public noop() {
    return false;
  }

  get result() {
    return this.params.artifacts;
  }

  set result(artifacts: Artifact[]) {
    this.params.artifacts = artifacts;
    this.valid = this.validateArtifacts();
    this.enableFinish(this.valid);
  }

  get title() {
    return (this.i18n('Import Existing Artifacts into Project ') + this.params.projectInfo.projectName);
  }

  validateArtifacts(): boolean {
    const validator = new ValidPathDirective(this.i18n);
    validator.validPath = 'FULL_PATH_ALLOW_EXTENSION';
    const invalidArt = this.params.artifacts.find(art => {
      const path = art.name;
      const result = validator._validate(path);
      if (result) {
        this.message = result['invalidPath'];
        return true;
      }
      return false;
    });
    if (invalidArt) {
      // found an invalid artifact, return false
      return false;
    } else {
      this.message = ''; // clear the message
      return true;
    }
  }

}
