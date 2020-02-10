import { TestBed } from '@angular/core/testing';

import { EditAction } from './edit-action';
import { EditorComponent } from './editor.component';

const ACTION_CAP = 10;
class MockEditorComponent extends EditorComponent<any> {
  constructor() {
    super();
    this.actionCap = ACTION_CAP;
  }
}
describe('EditorComponent', () => {
  let component: EditorComponent<any>;
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [{
        provide: EditorComponent,
        useClass: MockEditorComponent
      }]
    });
    component = TestBed.get(EditorComponent);
  });

  it('can execute action, undo, and redo', () => {
    const action = <EditAction<any>>{
      execute: () => { },
      revert: () => { }
    };
    spyOn(action, 'execute');
    spyOn(action, 'revert');

    expect(component.canUndo()).toBe(false);
    expect(component.canRedo()).toBe(false);

    component.execute(action);

    expect(action.execute).toHaveBeenCalledTimes(1);
    expect(component.canUndo()).toBe(true);
    expect(component.canRedo()).toBe(false);

    component.undo();

    expect(action.revert).toHaveBeenCalledTimes(1);
    expect(component.canUndo()).toBe(false);
    expect(component.canRedo()).toBe(true);
  });

  describe('when keep track of many actions', () => {
    let actions: EditAction<any>[];
    beforeEach(() => {
      const cnt = Math.random() * 100 + ACTION_CAP;
      actions = [];
      for (let i = 0; i < cnt; i++) {
        const action = {
          execute: () => { },
          revert: () => { }
        };
        spyOn(action, 'execute');
        spyOn(action, 'revert');
        actions.push(action);
      }
    });

    it('can execute any actions but only keep track limited number of them', () => {
      actions.forEach(action => component.execute(action));
      actions.forEach(action => expect(action.execute).toHaveBeenCalledTimes(1));

      let undoCnt = 0;
      while (component.canUndo()) {
        undoCnt++;
        component.undo();
      }
      expect(undoCnt).toBe(ACTION_CAP);
      actions.forEach((action, i) => {
        if (i < actions.length - ACTION_CAP) {
          expect(action.revert).toHaveBeenCalledTimes(0);
        } else {
          expect(action.revert).toHaveBeenCalledTimes(1);
        }
      });

      let redoCnt = 0;
      while (component.canRedo()) {
        redoCnt++;
        component.redo();
      }
      expect(redoCnt).toBe(ACTION_CAP);
      actions.forEach((action, i) => {
        if (i < actions.length - ACTION_CAP) {
          expect(action.revert).toHaveBeenCalledTimes(0);
          expect(action.execute).toHaveBeenCalledTimes(1);
        } else {
          expect(action.revert).toHaveBeenCalledTimes(1);
          expect(action.execute).toHaveBeenCalledTimes(2);
        }
      });
    });

    it('can wipe off redo log if a new action was performed in the middle of the undo/redo log', () => {
      actions.forEach(action => component.execute(action));
      const undoCnt = ACTION_CAP / 2;
      for (let i = 0; i < undoCnt; i++) {
        component.undo();
      }
      const action = <EditAction<any>>{
        execute: () => { },
        revert: () => { }
      };
      component.execute(action);
      expect(component.canRedo()).toBe(false);
    });
  });
});
