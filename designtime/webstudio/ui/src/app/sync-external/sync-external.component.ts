import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as CodeMirror from 'codemirror';
import 'codemirror/addon/merge/merge';
import 'codemirror/mode/meta';

import { ArtifactUploaderComponent } from '../artifact-importer/artifact-uploader.component';
import { AlertService } from '../core/alert.service';
import { ArtifactService } from '../core/artifact.service';
import { Logger } from '../core/logger.service';
import { ProjectService } from '../core/project.service';
import { EditorInterface } from '../editors/editor-interface';
import { EditorParams } from '../editors/editor-params';
import { Artifact } from '../models/artifact';

type Mode = 'META' | 'CONTENT';

@Component({
  selector: 'sync-external',
  templateUrl: './sync-external.component.html',
  styleUrls: ['./sync-external.component.css']
})
export class SyncExternalComponent implements OnInit {

  get showDiff() {
    return this._showDiff;
  }

  set showDiff(val: boolean) {
    if (val !== this._showDiff) {
      this._showDiff = val;

      this.contentParams = Object.assign({}, this.contentParams);
      this.contentParams.showChangeSet = val ? 'rhs' : 'none';
    }
  }
  @Input()
  input: Artifact;

  @Output()
  valid = new EventEmitter<boolean>();

  @ViewChild('codemirror', { static: false, read: ElementRef })
  diffView: ElementRef;
  mirror: CodeMirror.Editor;

  isReady = false;

  content: string;
  meta: string;
  contentParams: EditorParams<any>;

  @ViewChild('uploader', { static: false })
  private uploader: ArtifactUploaderComponent;

  private _showDiff = true;

  constructor(
    private log: Logger,
    private artifact: ArtifactService,
    private project: ProjectService,
    private alert: AlertService,
    public i18n: I18n
  ) { }

  ngOnInit() {
    this.reset();
  }

  onUpload(artifacts: Artifact[]) {
    if (artifacts && artifacts.length > 0) {
      const artifact = artifacts[0];
      try {
        if (this.input.content !== artifact.content) {
          this.content = artifact.content;
          this.contentParams = this.makeContentParams(this.content, false, true);
          const options: CodeMirror.MergeView.MergeViewEditorConfiguration = {
            value: this.input.content,
            orig: this.input.content,
            origRight: artifact.content,
            lineNumbers: true,
            mode: 'text/html',
            showDifferences: true,
            revertButtons: false,
            readOnly: true,
            collapseIdentical: true,
          };

          this.mirror = CodeMirror.MergeView(this.diffView.nativeElement, options).editor();
          this.isReady = true;
        } else {
          this.resetUploader();
          this.alert.flash(this.i18n('Artifact content is the same. Please upload a different artifact.'), 'warning');
        }
      } catch (e) {
        this.isReady = false;
        this.content = '';
        this.contentParams = null;
        this.resetUploader();
        this.alert.flash(this.i18n('Unable to parse {{name}} because: ', { name: artifact.name }) + e, 'warning');
      }
    }
    this.valid.emit(this.isReady);
  }

  submit(): Promise<boolean> {
    if (this.isReady) {
      const projectMeta = this.project.getProjectMeta(this.input.projectId);
      return this.artifact.syncExternalContentChanges(
        projectMeta.projectName,
        this.input, this.content);
    } else {
      return Promise.resolve(false);
    }
  }

  reset() {
    this.isReady = false;
    this.content = '';

    this.contentParams = this.makeContentParams(this.input.content, false, false);
    this.valid.emit(this.isReady);
  }

  private makeContentParams(content: string, editable: boolean, showDiff: boolean): EditorParams<any> {
    const itfc = this.input.type.defaultInterface === EditorInterface.METADATA ? EditorInterface.TEXT : this.input.type.defaultInterface;
    return {
      base: this.input.content,
      editBuffer: itfc.makeEmptyBuffer().init(content, editable),
      editorInterface: itfc,
      editorMode: editable ? 'edit' : 'display',
      showChangeSet: showDiff ? 'rhs' : 'none',
    };
  }

  private resetUploader() {
    if (this.uploader) {
      this.uploader.reset();
    } else {
      this.log.warn(this.i18n('Uploader is not found. Something must be wrong.'));
    }
  }
}
