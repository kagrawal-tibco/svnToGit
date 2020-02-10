import { Component, EventEmitter, Input, OnInit, Output, Type } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { ArtifactImporterContext } from './artifact-importer-context';
import { ArtifactInfoEditComponent } from './pages/artifact-info-edit.component';
import { CreateOrImportComponent } from './pages/create-or-import.component';

import { ArtifactService } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { ProviderService } from '../core/provider.service';
import { RuleTemplateInstance, RuleTemplateInstanceImpl } from '../editables/rule-template-instance/rule-template-instance';
import { Artifact, ArtifactType } from '../models/artifact';
import { NavigableWizardPageComponent } from '../navigable-wizard/navigable-wizard-page.component';

@Component({
  selector: 'artifact-importer',
  templateUrl: './artifact-importer.component.html',
})
export class ArtifactImporterComponent implements OnInit {
  @Input()
  projectName: string;

  @Input()
  baseDir: string;

  @Input()
  existingArtifactPaths: string[];

  @Input()
  artifactType: ArtifactType;

  @Input()
  artifactContent?: string;

  @Input()
  baseArtifactPath?: string;

  @Output()
  result = new EventEmitter<Artifact>();

  pages: Type<NavigableWizardPageComponent<ArtifactImporterContext>>[] = [
    CreateOrImportComponent,
    ArtifactInfoEditComponent
  ];

  params: ArtifactImporterContext;
  position: number;

  constructor(
    private log: Logger,
    private artifact: ArtifactService,
    private provider: ProviderService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    this.position = 0;
    this.params = {
      canFinish: Promise.resolve(false),
      canNextStep: Promise.resolve(false),
      existingArtifactPaths: this.existingArtifactPaths,
      projectName: this.projectName,
      baseDir: this.baseDir,
      method: 'CREATE',
      result: this.artifact.createArtifactInfo(false, this.artifactType, this.artifactContent, this.baseArtifactPath),
    };
  }

  inCreate() {
    return this.params.method === 'CREATE';
  }

  inImport() {
    return this.params.method === 'IMPORT';
  }

  onFinish() {
    this.params.result = this.artifact.repopulateArtifactInfo(this.params.result, true, this.params.result.type, this.params.result.content);
    this.result.emit(this.params.result);
  }

  onNext() {
    this.position++;
  }

  onPrev() {
    this.position--;
  }

  onCancel() {
    this.result.emit(null);
  }
}
