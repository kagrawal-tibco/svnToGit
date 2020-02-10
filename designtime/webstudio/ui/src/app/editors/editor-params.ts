import { MergeResult } from './../editables/decision-table/differ/merge-result';
import { ShowChangeSetOption } from './changeset-options';
import { EditorInterface } from './editor-interface';

import { EditBuffer } from '../editables/edit-buffer';
import { MergedDiffModificationEntry } from '../editables/rule-template-instance/differ/MergedDiffModificationEntry';
import { BindingInfo } from '../editors/rule-template-instance-view/BindingInfo';
import { Artifact } from '../models/artifact';
export type ControlOption = 'save' | 'reset';
export type EditorMode = 'display' | 'edit' | 'sync';

export class EditorParams<T> {
  editorName?: string;
  editorInterface?: EditorInterface;
  editorMode: EditorMode;
  showChangeSet: ShowChangeSetOption;
  editBuffer: EditBuffer<T>;
  mergeResult?: MergeResult;
  mergeResultRTI?: Map<string, MergedDiffModificationEntry>;
  mergeResultRTIView?: Map<BindingInfo, MergedDiffModificationEntry>;
  base?: string;
  lhs?: string;
  artifact?: Artifact; // Required for the legend to be visible in the decision table editor.
}
