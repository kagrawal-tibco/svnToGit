import { AfterViewInit, Component, ElementRef, EventEmitter, Input, OnDestroy, Output, ViewChild } from '@angular/core';

import * as CodeMirror from 'codemirror';
import 'codemirror/addon/edit/closebrackets';
import 'codemirror/addon/edit/closetag';
import 'codemirror/addon/edit/matchtags';
import 'codemirror/addon/fold/xml-fold';
import 'codemirror/addon/hint/xml-hint';
import 'codemirror/mode/xml/xml';

import { EditorComponent } from '../editor.component';

@Component({
  selector: 'text-editor',
  styles: ['.text-editor { height: calc(100%); max-height: inherit; }',
    `.text-editor > .CodeMirror {
      height: 100% !important;
      max-height: inherit;
    }`],
  templateUrl: './text-editor.component.html',
})
export class TextEditorComponent extends EditorComponent<string> implements AfterViewInit, OnDestroy {
  @Input()
  protected codeMirrorMode: any;

  @Output()
  protected edit = new EventEmitter<string>();

  @ViewChild('editorHolder', { static: false })
  private editorHolder: ElementRef;
  private cm: CodeMirror.EditorFromTextArea;

  constructor() {
    super();
    this.codeMirrorMode = 'text/xml';
  }
  ngAfterViewInit() {
    this.cm = CodeMirror.fromTextArea(this.editorHolder.nativeElement, <CodeMirror.EditorConfiguration>{
      mode: this.codeMirrorMode,
      lineNumbers: true,
      lineWrapping: true,
      tabSize: 2,
      readOnly: this.params.editorMode === 'display',
      matchTags: { bothTags: true },
      autoCloseBrackets: true,
      autoCloseTags: true,
    });
    this.cm.on('changes', (instance, changes) => {
      this.content = instance.getValue();
      this.edit.emit(this.content);
    });
  }

  ngOnDestroy() {
    this.cm.toTextArea();
  }

  supportUndoRedo() {
    return false;
  }

  set content(c: string) {
    this.params.editBuffer.replaceBuffer(c);
    this.params.editBuffer.markForDirtyCheck();
  }

  get content() {
    return this.params.editBuffer.getContent();
  }
}
