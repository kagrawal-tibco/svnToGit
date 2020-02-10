import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ArtifactService } from '../../core/artifact.service';
import { Logger } from '../../core/logger.service';
import { Artifact } from '../../models/artifact';
import { NavigableWizardPageComponent } from '../../navigable-wizard/navigable-wizard-page.component';
import { ValidPathDirective } from '../../shared/valid-path.directive';
import { ProjectImporterContext } from '../project-importer-context';

@Component({
  selector: 'artifact-info-tree',
  templateUrl: './artifact-info-tree.component.html',
  styleUrls: ['./artifact-info-tree.component.css']
})
export class ArtifactInfoTreeComponent extends NavigableWizardPageComponent<ProjectImporterContext> implements OnInit {

  constructor(
    private log: Logger,
    private artifact: ArtifactService,
    public i18n: I18n
  ) {
    super();
  }

  ngOnInit() {
  }

  get heading() {
    return (this.i18n('Edit artifact info or create additional artifacts in Project ') + this.params.projectInfo.projectName);
  }

  get input() {
    return this.params.artifacts;
  }

  set input(artifacts: Artifact[]) {
    this.params.artifacts = artifacts;
    const valid = this.validateArtifacts();
    this.enableFinish(valid);
  }

  validateArtifacts(): boolean {
    const validator = new ValidPathDirective(this.i18n);
    validator.validPath = 'FULL_PATH_ALLOW_EXTENSION';
    const invalidArt = this.params.artifacts.find(art => {
      const path = art.name;
      const result = validator._validate(path);
      if (result) {
        //        this.message = result['invalidPath'];
        return true;
      }
      return false;
    });
    if (invalidArt) {
      // found an invalid artifact, return false
      return false;
    } else {
      //      this.message = ''; // clear the message
      return true;
    }
  }
}
