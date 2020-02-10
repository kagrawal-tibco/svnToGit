import { Injectable } from '@angular/core';
import { I18n } from '@ngx-translate/i18n-polyfill';

import * as diff from 'deep-diff';

import { ColdConflict } from './cold-conflict';
import { ColdDiff } from './cold-diff';
import { ColdMerge } from './cold-merge';
import { LiveDiff } from './live-diff';
import { MergeResult } from './merge-result';

import { Logger } from '../../../core/logger.service';
import { DecisionTable } from '../decision-table';

export const EDIT: DiffKind = 'E';
export const DELETE: DiffKind = 'D';
export const NEW: DiffKind = 'N';
export const ARRAY: DiffKind = 'A';
export const COLUMN: DiffType = 'COLUMN';
export const CELL: DiffType = 'CELL';
export const RULE: DiffType = 'RULE';
export const PROP: DiffType = 'PROP';
export const META: DiffType = 'META';

export { ColdConflict } from './cold-conflict';
export { ColdMerge } from './cold-merge';
export { ColdDiff } from './cold-diff';

export enum Resolution {
  LHS,
  RHS,
}

export enum ConflictType {
  CELL_IN_NEW, // cell added in new rule or column
  CELL,
  CELL_BOTH, // cell update conflicts with rule and column deletion
  BOTH_CELL, // rule and column deletion conflicts with cell update
  CELL_RULE,
  RULE_CELL,
  CELL_COLUMN,
  COLUMN_CELL,
  RULE,
  COLUMN,
  PROP,
  META,
}

export type DiffType = 'COLUMN' | 'CELL' | 'RULE' | 'PROP' | 'META';
export type DiffKind = 'E' | 'D' | 'N' | 'A';

export class DiffItem {
  type: DiffType;
  kind: DiffKind;
  lhs?: any;
  rhs?: any;
}

export class ConflictItem {
  id: string;
  type: ConflictType;
  a: DiffItem;
  b: DiffItem;
  resolved?: boolean;
}

export interface ConflictResult {
  getPropItems(): Map<string, ConflictItem>;
  getRuleItems(): Map<string, ConflictItem>;
  getCellItems(): Map<string, ConflictItem>;
  getColumnItems(): Map<string, ConflictItem>;
  getMetaItems(): Map<string, ConflictItem>;
  isEmpty(): boolean;
  getLhsDiff(): ColdDiff;
  getRhsDiff(): ColdDiff;
}

@Injectable()
export class Differ {
  constructor(
    private log: Logger,
    public i18n: I18n
  ) { }
  diff(a: DecisionTable, b: DecisionTable): ColdDiff {
    const filter = (path: string[], key: string): boolean => {
      if (path.length >= 1) {
        if (path[0] === 'rulesObj' && key === '_columns') {
          return true;
        }
      }
      if (path.length === 4) {
        if (path[0] === 'rulesObj' && path[2] === 'cellMap' && key === '_column') {
          return true;
        }
      }
      return false;
    };
    const raw = diff.diff(a, b, filter);
    return new ColdDiff(a, b, raw, this.i18n);
  }

  liveDiff(a: any, b: any): LiveDiff {
    return new LiveDiff(a, b);
  }

  /**
   * Patch the difference between source and target to target
   * (thus target becomes effectively the same as source)
   */
  patch(target: DecisionTable, source: DecisionTable) {
    diff.applyDiff(target, source, null);
  }

  conflict(base: DecisionTable, v1: DecisionTable, v2: DecisionTable): ColdConflict {
    const d1 = this.diff(base, v1);
    const d2 = this.diff(base, v2);
    return new ColdConflict(d1, d2);
  }

  merge(base: DecisionTable, v1: DecisionTable, v2: DecisionTable): MergeResult {
    return new ColdMerge(base, v1, v2, this, this.i18n);
  }

  resolve(conflict: ColdConflict): DecisionTable {
    throw 'not yet';
  }
}
