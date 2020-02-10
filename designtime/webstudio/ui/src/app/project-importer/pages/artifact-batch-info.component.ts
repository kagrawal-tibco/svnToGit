
import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { timer as observableTimer, Observable } from 'rxjs';

import { ArtifactService } from '../../core/artifact.service';
import { Logger } from '../../core/logger.service';
import { Artifact } from '../../models/artifact';
import { NavigableWizardPageComponent } from '../../navigable-wizard/navigable-wizard-page.component';
import { ProjectImporterContext } from '../project-importer-context';
@Component({
  templateUrl: './artifact-batch-info.component.html',
})
export class ArtifactBatchInfoComponent extends NavigableWizardPageComponent<ProjectImporterContext> implements OnInit {

  get heading() {
    if (this.currArtifact === this.newArtifact) {
      return (this.i18n('Create more empty artifacts in Project ') + this.params.projectInfo.projectName);
    } else if (this.currArtifact) {
      return (this.i18n('Set the path and type of Artifact ') + this.currArtifact.name);
    } else {
      return (this.i18n('Edit Artifact'));
    }
  }
  public currArtifact: Artifact;
  public newArtifact: Artifact;
  public newArtifactValid: boolean;
  public tabs: Artifact[];
  private artifactValid: boolean[];
  private showInfo = new Map<Artifact, boolean>();
  private initializing: boolean;
  private newArtifactDirty: boolean;

  constructor(
    private log: Logger,
    private artifact: ArtifactService,
    public i18n: I18n
  ) {
    super();
  }

  ngOnInit() {
    this.newArtifact = this.artifact.createArtifactInfo(false);
    this.artifactValid = [];
    this.params.artifacts.forEach((artifact, idx) => {
      this.artifactValid.push(true);
      this.showInfo.set(artifact, true);
    });
    this.enableFinish(!this.artifactValid.includes(false));
    this.tabs = this.params.artifacts.concat([this.newArtifact]);
    this.initializing = true;
    let curr: Artifact;
    if (this.currArtifact) {
      curr = this.currArtifact;
    } else if (this.params.artifacts.length > 0) {
      curr = this.params.artifacts[0];
    } else {
      curr = this.newArtifact;
    }
    observableTimer(0).toPromise().then(() => this.onSelect(curr));
  }

  setArtifactValid(artifact: Artifact, valid: boolean) {
    if (artifact === this.newArtifact) {
      this.newArtifactValid = valid;
    } else {
      const idx = this.findIdx(artifact);
      this.artifactValid[idx] = valid;
      if (artifact === this.currArtifact) {
        this.showInfo.forEach((_, other) => {
          if (other !== artifact) {
            this.showInfo.set(other, false);
          }
        });
        observableTimer(0).toPromise().then(() => {
          this.showInfo.forEach((_, i) => this.showInfo.set(i, true));
        });
      }
    }
    const allValid = (this.artifactValid.filter(v => !v).length === 0) && (!this.newArtifactDirty || this.newArtifactValid);
    if (!allValid) { this.enableFinish(false); }
  }

  setDirty(artifact: Artifact, dirty: boolean) {
    if (this.newArtifact === artifact) { this.newArtifactDirty = dirty; }
    if (this.newArtifactDirty) { this.enableFinish(false); }
  }

  isArtifactValid(artifact: Artifact) {
    if (artifact === this.newArtifact) {
      return this.newArtifactValid;
    } else {
      const idx = this.findIdx(artifact);
      return this.artifactValid[idx];
    }
  }

  onRemove(artifact: Artifact) {
    const idx = this.findIdx(artifact);
    this.artifactValid.splice(idx, 1);
    this.params.artifacts.splice(idx, 1);
    this.tabs = this.params.artifacts.concat([this.newArtifact]);
    this.showInfo.delete(artifact);
    if (this.params.artifacts.length === 0) {
      this.enableFinish(true);
    }
    this.showInfo.forEach((_, i) => this.showInfo.set(i, false));
    observableTimer(0).toPromise().then(() => this.showInfo.forEach((_, i) => this.showInfo.set(i, true)));
  }

  onCreateNewArtifact() {
    const idx = this.params.artifacts.length;
    this.params.artifacts.push(this.newArtifact);
    this.newArtifact = this.artifact.createArtifactInfo(false);
    this.newArtifactValid = false;
    this.artifactValid[idx] = true;
    this.showInfo.set(this.params.artifacts[idx], true);
    this.tabs = this.params.artifacts.concat([this.newArtifact]);
    const allValid = this.artifactValid.filter(v => !v).length === 0;
    this.enableFinish(allValid);
  }

  onCancelCreateNewArtifact() {
    this.newArtifact = this.artifact.createArtifactInfo();
    this.tabs[this.tabs.length - 1] = this.newArtifact;
    if (this.params.artifacts.length > 0) {
      observableTimer(0).toPromise().then(() => this.onSelect(this.params.artifacts[0]));
    }
    this.enableFinish(!this.artifactValid.includes(false));
  }

  getExistingArtifactPaths(artifact: Artifact): string[] {
    return this.params.artifacts.filter((v, i) => v !== artifact).map(v => v.path);
  }

  onSelect(artifact: Artifact) {
    if (artifact !== this.currArtifact) {
      this.currArtifact = artifact;
    }
  }

  findIdx(artifact: Artifact) {
    return this.params.artifacts.findIndex(a => a === artifact);
  }
}
