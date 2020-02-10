import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as CodeMirror from 'codemirror';
import 'codemirror/addon/merge/merge';
import 'codemirror/mode/meta';

import { ArtifactService, SynchronizeStrategy } from 'app/core/artifact.service';

import { Artifact, ArtifactType } from '../models/artifact';

@Component({
  selector: 'synchronize',
  templateUrl: './synchronize.component.html',
  styleUrls: ['./synchronize.component.css'],
})
export class Synchronize implements OnInit {

  @Input()
  base: Artifact;

  @Input()
  rhs: Artifact;

  @Input()
  lhs: Artifact;

  @Output()
  done: EventEmitter<Artifact> = new EventEmitter<Artifact>();

  @ViewChild('codeMirror', { static: false, read: ElementRef })
  codeMirror: ElementRef;

  editor: CodeMirror.Editor;

  constructor(
    private serviceArtifact: ArtifactService,
    public i18n: I18n
  ) {

  }

  ngOnInit() {
    const options: CodeMirror.MergeView.MergeViewEditorConfiguration = {
      origLeft: this.lhs.content,
      value: this.base.content,
      orig: this.base.content,
      origRight: this.rhs.content,
      lineNumbers: true,
      mode: 'text/html',
      showDifferences: true,
      collapseIdentical: true,
    };

    this.editor = CodeMirror.MergeView(this.codeMirror.nativeElement, options).editor();
  }

  canConfirm(): boolean {
    return true;
  }

  onConfirm() {
    const content: string = this.editor.getValue();
    this.serviceArtifact
      .synchronize(this.rhs, this.lhs, SynchronizeStrategy.MERGE, content)
      .then(res => {
        if (res) {
          this.serviceArtifact.markAsStale(this.base.id);
          this.done.emit(res);
        }
      });
  }

  onCancel() {
    this.done.emit(null);
  }

  getMessage(): string {
    const msg = this.i18n('Apply repository changes between revisions {{0}} and {{1}} to your working copy', { 0: this.base.checkedOutFromRevision, 1: this.base.latestRevision });
    return msg;
  }
}
