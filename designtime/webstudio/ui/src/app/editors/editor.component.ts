import { Component, Input } from '@angular/core';

import { EditAction } from './edit-action';
import { EditorParams } from './editor-params';

import { TakeUntilDestroy } from '../core/take.until.destroy';

@Component({
  template: ''
})
export class EditorComponent<T> extends TakeUntilDestroy {
  @Input()
  public params: EditorParams<T>;

  protected actions: EditAction<T>[] = [];
  protected actionIdx = -1;
  protected actionCap = 100;

  supportUndoRedo() {
    return true;
  }

  undo() {
    if (this.canUndo()) {
      const action = this.actions[this.actionIdx--];
      action.revert(this);
    }
  }

  redo() {
    if (this.canRedo()) {
      const action = this.actions[++this.actionIdx];
      action.execute(this);
    }
  }

  canUndo() {
    return this.actionIdx >= 0;
  }

  canRedo() {
    return this.actionIdx < this.actions.length - 1;
  }

  execute(action: EditAction<T>) {
    action.execute(this);
    this.recordAction(action);
  }

  focusOnLocations(locations: any[]) {
    /** By default NOOP */
  }

  protected recordAction(action: EditAction<T>) {
    if (this.actionIdx >= this.actionCap - 1) {
      this.actions.shift();
      this.actionIdx--;
    }
    this.actionIdx++;
    this.actions.splice(this.actionIdx, 0, action);
    if (this.actions.length - 1 > this.actionIdx) {
      this.actions = this.actions.slice(0, this.actionIdx + 1);
    }
  }
}
