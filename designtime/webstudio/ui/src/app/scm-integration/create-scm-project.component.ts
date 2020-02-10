import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ScmRepositoryRecord } from 'app/models/dto';

import { NavigableWizardPageComponent } from '../navigable-wizard/navigable-wizard-page.component';
import { ProjectImporterContext } from '../project-importer/project-importer-context';

@Component({
  templateUrl: './create-scm-project.component.html'
})
export class CreateSCMProject extends NavigableWizardPageComponent<ProjectImporterContext> implements OnInit {
  public repository: ScmRepositoryRecord;

  constructor(public i18n: I18n) {
    super();
  }

  ngOnInit() {
  }

  getRepos() {
    return this.params.repos;
  }

  getRepo() {
    return this.repository;
  }

  setInRepo(repo: ScmRepositoryRecord) {
    this.repository = repo;
    this.params.projectInfo.type = <'NATIVE' | 'SUBVERSION' | 'GIT'>repo.type;
  }

  handleRoot(dir) {
    this.params.projectInfo.scmParams = dir;
    this.enableFinish(true);
  }
}
