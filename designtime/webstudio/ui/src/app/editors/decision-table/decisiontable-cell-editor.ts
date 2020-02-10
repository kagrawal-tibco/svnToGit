import { ICellEditor, ICellEditorParams } from 'ag-grid-community/main';

export class DecisionTableCellEditor implements ICellEditor {
  init(params: ICellEditorParams) {

  }

  getGui(): HTMLElement {
    return null;
  }

  getValue(): any {
    return null;
  }

  destroy(): void {

  }
  /**
   * Gets called once after initialised. If you return true, the editor will appear in a popup, so is not constrained
   *  to the boundaries of the cell. This is great if you want to, for example, provide you own custom dropdown list
   *  for selection. Default is false (ie if you don't provide the method).
   */
  isPopup(): boolean {
    return false;
  }
  /**
   *  Gets called once after initialised. If you return true, the editor will not be used and the grid will continue
   *  editing. Use this to make a decision on editing inside the init() function, eg maybe you want to only start
   *  editing if the user hits a numeric key, but not a letter, if the editor is for numbers.
   */
  isCancelBeforeStart(): boolean {
    return false;
  }
  /**
   *  Gets called once after editing is complete. If your return true, then the new value will not be used. The
   *  editing will have no impact on the record. Use this if you do not want a new value from your gui, i.e. you
   *  want to cancel the editing.
   */
  isCancelAfterEnd(): boolean {
    return false;
  }
}
