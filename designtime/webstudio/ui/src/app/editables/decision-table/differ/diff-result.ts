import { DiffItem } from './differ';

import { DecisionTable } from '../decision-table';

export interface DiffResult {
  getCellItem(id: string): DiffItem;

  getETCellItem(id: string): DiffItem;

  getPropItem(propName: string): DiffItem;

  getRuleItem(ruleId: string): DiffItem;

  getETRuleItem(ruleId: string): DiffItem;

  getColumnItem(colId: string): DiffItem;

  getETColumnItem(colId: string): DiffItem;

  getMetaItem(metaName: string): DiffItem;

  getLhs(): DecisionTable;

  getRhs(): DecisionTable;
}
