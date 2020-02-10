import { ColdConflict } from './cold-conflict';
import { ColdDiff } from './cold-diff';
import { Resolution } from './differ';

import { DecisionTable } from '../decision-table';

export interface MergeResult {
  getMerged(): DecisionTable;
  getLhsDiff(): ColdDiff;
  getRhsDiff(): ColdDiff;
  getConflict(): ColdConflict;
  hasConflict(): boolean;
  resolveCellConflict(cellId: string, resolution: Resolution);
  resolveETCellConflict(cellId: string, resolution: Resolution);
  resolvePropertyConflict(prop: string, resolution: Resolution);
}
