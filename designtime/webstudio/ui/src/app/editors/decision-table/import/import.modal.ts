import {
  Component,
  OnInit,
} from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { FileUploader } from 'ng2-file-upload';
import { DialogRef, ModalComponent } from 'ngx-modialog';
import { BSModalContext } from 'ngx-modialog/plugins/bootstrap';

import { AlertService } from '../../../core/alert.service';
import { ArtifactService } from '../../../core/artifact.service';
import { ModalService } from '../../../core/modal.service';
import { ProjectService } from '../../../core/project.service';
import { RecordService } from '../../../core/record.service';
import { RestService } from '../../../core/rest.service';
import { Artifact, ArtifactStatus, ArtifactType } from '../../../models/artifact';
import { ResizableModal } from '../../../shared/resizablemodal';
import { SynchronizeEditorContext, SynchronizeEditorModal } from '../../../synchronize-editor/synchronize-editor.modal';

export class ImportArtifactContext extends BSModalContext {
  dialogClass = 'modal-dialog modal-lg';
  constructor(
    public artifactId: string,
    public checkedOutArtifactList: Artifact[]
  ) {
    super();
  }
}

@Component({
  templateUrl: './import.modal.html',
})
export class ImportArtifactModal extends ResizableModal implements ModalComponent<ImportArtifactContext>, OnInit {
  artifactId: string;
  resourceName: string;
  resourceFolderPath: string;
  selectedFile: string;
  checkedOutArtifactList: Artifact[];
  selectedAction: string;
  overwrite: boolean;

  uploader: FileUploader;
  apiInProgress = false;

  constructor(
    public dialog: DialogRef<ImportArtifactContext>,
    public projectService: ProjectService,
    public restService: RestService,
    public artifactService: ArtifactService,
    public modal: ModalService,
    public record: RecordService,
    //    public workspace: WorkspaceService,
    public alert: AlertService,
    public i18n: I18n
  ) {
    super(dialog.context, dialog.context.dialogClass);
    this.artifactId = dialog.context.artifactId;
    this.resourceFolderPath = this.extractBasePath();
    this.checkedOutArtifactList = dialog.context.checkedOutArtifactList;
    this.selectedAction = 'Overwrite';
    this.overwrite = true;
  }

  ngOnInit() {
    const importUrl = this.restService.url('decisiontable/import.json');

    this.uploader = new FileUploader({ url: importUrl, itemAlias: 'selectResource' });
    this.uploader.autoUpload = false;
  }

  onClose() {
    this.dialog.close();
  }

  onConfirm() {

    if (this.properFileFormat(this.selectedFile)) {
      this.updateResourceName();

      const artifactItems = this.artifactId.split('@');

      const newResourcePath = this.resourceFolderPath + '/' + this.resourceName;

      if (artifactItems[2] !== newResourcePath) {

        this.uploader.onBuildItemForm = (item, form) => {
          form.append('projectName', artifactItems[1]);
          form.append('artifactPath', artifactItems[2]);
          form.append('artifactExtension', artifactItems[3]);
          form.append('resourceName', this.resourceName);
          form.append('resourceFolderPath', this.resourceFolderPath);
          form.append('overwrite', this.isOverwrite());
        };

        this.uploader.onSuccessItem = (item, string, status, header) => {
          const data = JSON.parse(string);

          if (this.isOverwrite()) {
            if (data.response.status !== '-1') {
              const artifact = this.createArtifact(artifactItems[1]);
              if (this.checkDuplicates()) {
                this.artifactService.markAsModified(artifact, artifact);
              } else {
                this.artifactService.markAsAdded(artifact);
              }
              this.alert.flash(this.i18n('Artifact [{{resourceName}}] was successfully imported', { resourceName: this.resourceName }), 'success');
              this.artifactService.clear();
              this.dialog.close();
            } else {
              this.alert.flash(this.i18n('Error Importing Decision Table from Excel'), 'error');
              this.dialog.close();
            }

          } else {
            if (data.response.status !== '-1') {
              const url = `artifact/compare.json?` + this.getQueryStringFromId(artifactItems);
              this.restService.get(url)
                .toPromise()
                .then(res => {
                  let versions: Artifact[] = [];
                  if (res.status !== -1) {
                    versions = this.record.recordDetailsToArtifactVersions(res.record[0], artifactItems[1]);
                  } else {
                    versions.push(this.getArtifact(artifactItems[1], data.response.data.record[0].artifactPath, data.response.data.record[0].artifactType, data.response.data.record[0].previousVersionContents));
                    versions.push(this.getArtifact(artifactItems[1], data.response.data.record[0].artifactPath, data.response.data.record[0].artifactType, data.response.data.record[0].previousVersionContents));
                  }

                  const server = this.getArtifact(artifactItems[1], data.response.data.record[0].artifactPath, data.response.data.record[0].artifactType, data.response.data.record[0].currentVersionContents);

                  Promise.resolve()
                    .then(() => {
                      return this.modal.open(SynchronizeEditorModal, new SynchronizeEditorContext(versions[1], server, versions[0]));
                    })
                    .then((updated: Artifact) => {
                      if (updated) {
                        this.artifactService.updateCheckoutArtifact(updated);
                        this.dialog.close();
                      } else {
                        this.dialog.close();
                      }

                    }, () => { });

                });

            } else {
              this.alert.flash(this.i18n('Error Importing Decision Table from Excel'), 'error');
              this.dialog.close();
            }
          }
        };

        this.uploader.onErrorItem = (item, string, status, header) => {
          this.alert.flash(this.i18n('Failed importing artifact [{{resourceName}}]', { resourceName: this.resourceName }), 'success');
          this.dialog.close();
        };

        this.uploader.uploadItem(this.uploader.queue[0]);
        this.apiInProgress = true;
      } else {
        this.alert.flash(this.i18n('Resource already present :{{newResourcePath}}', { newResourcePath: newResourcePath }), 'error');
        this.dialog.close();
      }
    } else {
      this.alert.flash(this.i18n('Invalid file type. Please select excel file to import'), 'error', true);
      this.selectedFile = undefined;
      if (this.uploader.queue.length > 0) {
        this.uploader.removeFromQueue(this.uploader.queue[0]);
      }
    }
  }

  canConfirm(): boolean {
    return this.artifactId && this.resourceName && this.resourceFolderPath && !this.apiInProgress;
  }

  extractBasePath() {
    let artifactPath = this.artifactId.split('@')[2];
    const lastFolderIndex = artifactPath.lastIndexOf('/');
    if (lastFolderIndex > 0) {
      artifactPath = artifactPath.substring(0, lastFolderIndex);
    }
    return artifactPath;
  }

  extractName(path: string) {
    const lastFolderIndex = path.lastIndexOf('\\');
    if (lastFolderIndex > 0) {
      path = path.substring(lastFolderIndex + 1, path.length);
    }

    if (!this.selectedFile || this.selectedFile !== path) {
      this.selectedFile = path;
      if (path.indexOf('.') > 0) { setTimeout(_ => this.resourceName = path.substring(0, path.indexOf('.'))); }
    }

    return path;
  }

  checkDuplicates() {
    let duplicate = false;
    const newResourcePath = this.resourceFolderPath + '/' + this.resourceName;
    this.checkedOutArtifactList.forEach((artifact) => {
      if (artifact.path.toLowerCase() === newResourcePath.toLowerCase()) {
        duplicate = true;
      }
    });
    return duplicate;
  }

  updateResourceName() {
    const newResourcePath = this.resourceFolderPath + '/' + this.resourceName;
    this.checkedOutArtifactList.forEach((artifact) => {
      if (artifact.path.toLowerCase() === newResourcePath.toLowerCase()) {
        if (artifact.path.slice(artifact.path.lastIndexOf('/')) !== this.resourceName) {
          this.resourceName = artifact.path.slice(artifact.path.lastIndexOf('/') + 1);
        }
      }
    });
  }

  getresourceName() {
    let rname = this.resourceName;
    const newResourcePath = this.resourceFolderPath + '/' + this.resourceName;
    this.checkedOutArtifactList.forEach((artifact) => {
      if (artifact.path.toLowerCase() === newResourcePath.toLowerCase()) {
        rname = artifact.path.slice(artifact.path.lastIndexOf('/'));
      }
    });
    return rname;
  }

  isOverwrite() {
    if (this.selectedAction === 'Overwrite') {
      return true;
    } else {
      return false;
    }
  }

  createArtifact(projectId: string) {
    const artifactPath = this.resourceFolderPath + '/' + this.resourceName;

    const importArtifact = new Artifact();
    importArtifact.id = (this.restService.userName + '@' + projectId + '@' + artifactPath + '@' + ArtifactType.BE_DECISION_TABLE.defaultExtension);
    importArtifact.path = artifactPath;
    importArtifact.type = ArtifactType.BE_DECISION_TABLE;
    importArtifact.isCheckedOutArtifact = true;
    importArtifact.projectId = projectId;
    if (this.checkDuplicates()) {
      importArtifact.status = <ArtifactStatus>'MODIFIED'; // may be this needs to be conditional, depending on if there already exists DT with the same name
    } else {
      importArtifact.status = <ArtifactStatus>'ADDED'; // may be this needs to be conditional, depending on if there already exists DT with the same name
    }
    return importArtifact;
  }

  properFileFormat(value: string) {
    const extension = value.substring(value.indexOf('.') + 1, value.length);
    if (extension === 'xls') {
      return true;
    } else {
      return false;
    }
  }

  getActions() {
    const actions: Array<string> = new Array<string>();
    actions.push('Overwrite');
    actions.push('Merge');

    return actions;
  }

  getArtifact(projectName: string, artifactPath: string, artifactType: string, content: any) {
    const a = new Artifact();
    a.path = artifactPath;
    a.type = ArtifactType.fromExtension(artifactType);
    a.content = JSON.stringify(content, this.systemPropertyReplacer);
    a.isCheckedOutArtifact = true;
    if (projectName) { a.projectId = projectName; }
    a.projectId = projectName;

    return a;
  }

  systemPropertyReplacer(key: string, value: string): any {
    if (key === 'attributes') { return undefined; }
    if (key === 'visited') { return undefined; }
    return value;
  }

  private getQueryStringFromId(artifactId: any): string {
    let queryString;
    queryString = 'projectName=' + artifactId[1];
    queryString += ('&artifactPath=' + this.resourceFolderPath + '/' + this.resourceName);
    queryString += ('&artifactExtension=' + 'rulefunctionimpl');

    return queryString;
  }
}
