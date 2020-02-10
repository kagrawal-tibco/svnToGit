import { EditorComponent } from './editor.component';

export interface EditAction<T> {
  execute(context: EditorComponent<T>);
  revert(context: EditorComponent<T>);
}
