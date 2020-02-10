import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material';
import { I18n } from '@ngx-translate/i18n-polyfill';

import { UserService } from 'app/core/user.service';

import { environment } from '../../environments/environment';
import { CommitItemContext, CommitItemModal } from '../commit-shared/commit-item.modal';
import { AlertService } from '../core/alert.service';
import { ArtifactService } from '../core/artifact.service';
import { LifecycleService } from '../core/lifecycle.service';
import { ModalService } from '../core/modal.service';
import { EditorLoaderModal } from '../editors/editor-loader.modal';
import { ArtifactHistoryEntry } from '../models/artifact-history-entry';

@Component({
  selector: 'artifact-history-entry',
  template: `
        <div class="row left-pad right-pad">
          <div class="col-sm-2">
            {{input.version}}<span *ngIf='highlighted'> <i>({{highlightReason}})</i></span>
          </div>
          <div class='col-sm-4'>
            {{input.commitMessage}}
          </div>
          <div class="col-sm-2">
            {{input.userName}}
          </div>
        <div class="col-sm-4">
            <button
              type="button"
              class='btn btn-xs'
              [class.btn-primary]='highlighted'
              [class.btn-outline-primary]='!highlighted'
              (click)='onViewCommit($event)' i18n
              >{{i18n('View Commit')}}</button>
            <button
              type="button"
              class='btn btn-xs'
              [class.btn-primary]='highlighted'
              [class.btn-outline-primary]='!highlighted'
              (click)='onViewContent($event)' i18n
              >{{i18n('View Content')}}</button>
            <button *ngIf='showViewMetadata()'
              type="button"
              class="btn btn-xs"
              [class.btn-primary]='highlighted'
              [class.btn-outline-primary]='!highlighted'
              (click)="onViewMetadata($event)" i18n
              >{{i18n('View Metadata')}}</button>
          </div>
         </div>
  `
})
export class ArtifactHistoryEntryComponent {

  @Input()
  input: ArtifactHistoryEntry;

  @Input()
  highlighted: boolean;

  @Input()
  highlightReason: string;

  constructor(
    private artifact: ArtifactService,
    private lifecycle: LifecycleService,
    private matDialog: MatDialog,
    private modal: ModalService,
    private alert: AlertService,
    private userService: UserService,
    public i18n: I18n
  ) {

  }

  onViewContent(e: Event) {
    e.stopPropagation();
    e.preventDefault();
    this.artifact.getArtifactRevisionWithContent(this.input.artifactId, this.input.version)
      .then((a) => {
        if (environment.enableBEUI && a.type.defaultExtension === 'ruletemplateinstance') {
          if (JSON.parse(a.content).view != null) {
            this.modal.open(EditorLoaderModal, EditorLoaderModal.ruleTemplateInstanceView(a, null, `${a.name} (${this.input.version})`));
          } else {
            this.modal.open(EditorLoaderModal, EditorLoaderModal.ruleTemplateInstanceBuilder(a, null, `${a.name} (${this.input.version})`));
          }

        } else {
          this.modal.open(EditorLoaderModal, EditorLoaderModal.default(a, null, `${a.name} (${this.input.version})`));
        }
      });
  }

  onViewMetadata(e: Event) {
    e.stopPropagation();
    e.preventDefault();
    this.artifact.getArtifactRevisionWithContent(this.input.artifactId, this.input.version)
      .then(
        (artifact) => {
          this.modal.open(EditorLoaderModal, EditorLoaderModal.metadata(artifact, null, `${artifact.name} (${this.input.version})`));
        }
      );
  }

  onViewCommit(e: Event) {
    this.userService.hasAdminRole()
      .then((res) => {
        if (res) {
          e.stopPropagation();
          e.preventDefault();
          this.lifecycle.getCommit(this.input.commitId)
            .toPromise()
            .then(c => {
              this.matDialog.open(CommitItemModal, {
                width: '60vw',
                height: '60vh',
                data: <CommitItemContext>{
                  commit: c
                }
              });
            });
        } else {
          this.alert.flash('User does not have permission to view the commit', 'warning');
        }
      },
        err => this.alert.flash('User does not have permission to view the commit', 'warning'));
  }

  showViewMetadata() {
    return !environment.enableBEUI;
  }

}
