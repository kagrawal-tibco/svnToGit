import { Component, EventEmitter, Output } from '@angular/core';

import { EditorComponent } from '../editor.component';

@Component({
  selector: 'metadata-editor',
  template: `<text-editor [params]='params' [codeMirrorMode]='codeMirrorMode' (edit)='onEdit($event)'></text-editor>`
})
export class MetadataEditorComponent extends EditorComponent<string> {
  public codeMirrorMode = {
    name: 'json',
    json: true,
  };

  @Output()
  public valid = new EventEmitter<boolean>();

  supportUndoRedo() {
    return false;
  }

  onEdit(val: string) {
    try {
      this.valid.emit(!!JSON.parse(val));
    } catch (e) {
      this.valid.emit(false);
    }
  }
}
