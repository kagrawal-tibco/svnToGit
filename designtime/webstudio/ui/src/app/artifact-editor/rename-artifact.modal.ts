import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext, Modal } from 'ngx-modialog/plugins/bootstrap';

import { AlertService } from '../core/alert.service';
import { ArtifactService } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { RestService } from '../core/rest.service';
import { Artifact } from '../models/artifact';

class RenameForm {
  name: string;
  oldName: string;
}

export class BERenameContext extends BSModalContext {
  constructor(
    public artifact: Artifact,
    public implementsPath: string
  ) {
    super();
  }

}

@Component({
  templateUrl: './rename-artifact.modal.html',
})
export class BERenameModal implements ModalComponent<BERenameContext>, OnInit, AfterViewInit {
  private static validName = new RegExp('^([a-zA-Z]+[a-zA-Z0-9_]*\/)*([a-zA-Z]+[a-zA-Z0-9_]*)$');
  form: RenameForm;
  artifact: Artifact;
  implementsPath: string;

  @ViewChild('newNameInput', { static: false })
  private columnNameInput: ElementRef;

  constructor(
    private log: Logger,
    private modal: Modal,
    private restService: RestService,
    private alertService: AlertService,
    private artifactService: ArtifactService,
    public i18n: I18n,
    public dialog: DialogRef<BERenameContext>
  ) {
    this.artifact = dialog.context.artifact;
    this.implementsPath = dialog.context.implementsPath;
  }

  ngOnInit() {
    this.form = new RenameForm();
    this.form.name = this.artifact.name;
    this.form.oldName = this.artifact.name;
  }

  ngAfterViewInit() {

  }

  onCancel() {
    this.dialog.dismiss();
  }

  onSubmit() {
    if (BERenameModal.validName.test(this.form.name) && this.form.name.length <= 64) {
      const renamedArtifactPath = this.getRenamePath(this.artifact.path, this.form.name);

      return this.restService.put('artifact/rename.json?projectName=' + this.artifact.projectId +
        '&artifactPath=' + this.artifact.path + '&artifactExtension=' + this.artifact.type.defaultExtension +
        '&artifactType=' + this.artifact.type.defaultExtension + '&artifactRenameToPath=' + renamedArtifactPath +
        '&implementsPath=' + this.implementsPath, null).toPromise()
        .then(resp => {
          if (resp.ok()) {
            // remove existing/old artifact from cache
            this.artifactService.removeCheckedOutArtifactFromCache(this.artifact.id);
            // update artifact details
            this.artifact.path = renamedArtifactPath;
            const artifactIdParts = this.artifact.id.split('@');
            artifactIdParts[2] = renamedArtifactPath;
            this.artifact.id = artifactIdParts.join('@');
            // put it back within the local cache
            this.artifactService.cacheCheckedOutArtifact(this.artifact);
          } else {
            this.alertService.flash(this.i18n('An error occurred while renaming artifact.'), 'error');
          }
          this.dialog.close({
            artifact: this.artifact
          });
        });
    } else {
      this.alertService.flash(this.i18n('Resource name can contain letters, numbers, and underscore only. It should start with a letter and be less than 64 characters'), 'error');
    }
  }

  canRename(): boolean {
    if (this.form.name === this.form.oldName || this.form.name === '') {
      return false;
    } else {
      return true;
    }
  }

  getRenamePath(path: string, renamed: string): string {
    return path.substring(0, path.lastIndexOf('/') + 1) + renamed;
  }
}
