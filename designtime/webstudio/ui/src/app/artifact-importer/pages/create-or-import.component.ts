import { Component, OnInit } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { environment } from '../../../environments/environment';
import { ArtifactService } from '../../core/artifact.service';
import { Logger } from '../../core/logger.service';
import { Artifact } from '../../models/artifact';
import { NavigableWizardPageComponent } from '../../navigable-wizard/navigable-wizard-page.component';
import { ArtifactImporterContext } from '../artifact-importer-context';

@Component({
  selector: 'create-or-import',
  templateUrl: './create-or-import.component.html',
})
export class CreateOrImportComponent extends NavigableWizardPageComponent<ArtifactImporterContext> implements OnInit {

  get uploadedArtifacts() {
    if (this.params.result) {
      return [this.params.result];
    } else {
      return [];
    }
  }

  set uploadedArtifacts(uploaded: Artifact[]) {
    if (uploaded.length > 0) {
      this.params.result = uploaded[0];
      this.enableNextStep(true);
    } else {
      this.params.result = null;
      this.enableNextStep(false);
    }
  }

  get title() {
    if (this.inCreate()) {
      return this.i18n('Create Artifact');
    } else if (this.inImport()) {
      return this.i18n('Import Artifact');
    }
  }
  private dirty = false;

  constructor(
    private log: Logger,
    private artifact: ArtifactService,
    public i18n: I18n
  ) {
    super();
  }

  ngOnInit() {
  }

  setInCreate() {
    if (!this.inCreate()) {
      this.clearResult();
      this.params.method = 'CREATE';
      this.params.result = this.artifact.createArtifactInfo(false);
    }
  }

  setInImport() {
    if (!this.inImport()) {
      this.clearResult();
      this.params.method = 'IMPORT';
    }
  }

  inCreate() {
    return this.params.method === 'CREATE';
  }

  inImport() {
    return this.params.method === 'IMPORT';
  }

  onValidPath(valid: boolean) {
    this.inCreate() ? this.enableFinish(valid && this.dirty) : this.enableNextStep(valid);
  }

  onDirty(dirty: boolean) {
    this.dirty = dirty;
  }

  showImport() {
    return !environment.enableBEUI;
  }

  isOnlyCreate(): boolean {
    if (environment.enableBEUI) {
      return true;
    } else {
      return false;
    }
  }

  private clearResult() {
    this.enableFinish(false);
    this.enableNextStep(false);
    this.params.result = null;
  }

}
