import { Component, EventEmitter, HostListener, Input, OnInit, Output, Type } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ScmRepositoryRecord } from 'app/models/dto';
import { NavigableWizardModule } from 'app/navigable-wizard/navigable-wizard.module';

import { ArtifactBatchImportComponent } from './pages/artifact-batch-import.component';
import { ArtifactBatchInfoComponent } from './pages/artifact-batch-info.component';
import { ArtifactInfoTreeComponent } from './pages/artifact-info-tree.component';
import { ProjectInfoEditComponent } from './pages/project-info-edit.component';
import { ProjectImporterContext } from './project-importer-context';

import { ArtifactService } from '../core/artifact.service';
import { SCMService } from '../core/scm.service';
import { Project } from '../models/project';
import { ProjectMeta } from '../models/project-meta';
import { NavigableWizardContext } from '../navigable-wizard/navigable-wizard-context';
import { NavigableWizardPageComponent } from '../navigable-wizard/navigable-wizard-page.component';
import { CreateSCMProject } from '../scm-integration/create-scm-project.component';
import { SandboxArtifactImporter } from '../scm-integration/sandbox-artifact-importer.component';

@Component({
  selector: 'project-importer',
  templateUrl: './project-importer.component.html',
})
export class ProjectImporterComponent implements OnInit {
  pages: Type<NavigableWizardPageComponent<NavigableWizardContext>>[] = [
    ProjectInfoEditComponent,
    ArtifactBatchImportComponent,
    //    ArtifactBatchInfoComponent,
    ArtifactInfoTreeComponent,
  ];
  params: ProjectImporterContext;
  position: number;

  @Input()
  existingProjectNames: string[];

  @Output()
  result = new EventEmitter<Project>();

  constructor(
    private artifact: ArtifactService,
    private scm: SCMService,
    public i18n: I18n
  ) {

  }

  ngOnInit() {
    this.position = 0;
    const meta = new ProjectMeta();
    meta.type = 'NATIVE'; // set the default as NATIVE, can be overridden for SCM
    this.params = {
      artifacts: [],
      repos: [],
      projectInfo: meta,
      existingProjectNames: this.existingProjectNames,
      canFinish: Promise.resolve(false),
      canNextStep: Promise.resolve(false)
    };
    this.scm.listRepositories().toPromise().then((records) =>
      this.params.repos = records.filter(r => r.isEnabled));
  }

  @HostListener('window:keydown', ['$event'])
  keyboardInput(event: KeyboardEvent) {
    // listen to the enter keydown event
    if (event.keyCode === 13) {
      const currPage = this.pages[this.position] as Type<NavigableWizardPageComponent<NavigableWizardContext>>;
      if (currPage === ArtifactInfoTreeComponent) {
        // don't automatically finish while editing in the file-tree
        return;
      }

      this.onFinish().then(success => {
        if (!success) {
          if (this.params.canNextStep
            && (this.position < this.pages.length - 1)) {
            this.onNext();
          }
        }
      });
    }
  }

  onNext() {
    this.params.projectInfo.type = 'NATIVE';
    return this.params.canNextStep.then(ok => ok && this.position++);
  }

  onNextSCM() {
    this.params.projectInfo.type = 'SUBVERSION';
    this.pages = [ProjectInfoEditComponent, CreateSCMProject];
    return this.params.canNextStep.then(ok => ok && this.position++);
  }

  onPrev() {
    if (this.position > 0) {
      this.position--;
    }
    if (this.position === 0) {
      this.pages = [
        ProjectInfoEditComponent,
        ArtifactBatchImportComponent,
        //        ArtifactBatchInfoComponent,
        ArtifactInfoTreeComponent,
      ];
    }
  }

  onCancel() {
    this.result.emit(null);
  }

  onFinish() {
    return this.params.canFinish.then(ok => {
      if (ok) {
        const project = new Project(this.params.projectInfo,
          this.params.artifacts.map(a => this.artifact.repopulateArtifactInfo(a, true, a.type, a.content)));
        this.result.emit(project);
      }
      return ok;
    });
  }

  showFinish() {
    return true; // why not let the user finish at any point?
    // this.params.projectInfo.type === 'NATIVE' ? this.position === 2 : this.position === 1;
  }

  showNext() {
    return this.params.projectInfo.type === 'NATIVE' ? this.position !== 2 : this.position !== 1;
  }

  hasRepos() {
    if (this.params.repos) {
      return this.params.repos.some((record) => record.isEnabled);
    }
    return false;
  }

  get nextBtnText() {
    if (this.position !== 1) {
      return 'Next';
    } else {
      if (this.params.artifacts.length === 0) {
        return 'Skip';
      } else {
        return 'Next';
      }
    }
  }
}
