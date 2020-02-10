import { I18n } from '@ngx-translate/i18n-polyfill';

import { ColdConflict } from './cold-conflict';
import { ColdDiff } from './cold-diff';
import * as differ from './differ';
import { MergeResult } from './merge-result';

import { environment } from '../../../../environments/environment';
import { Cell } from '../cell';
import { Column } from '../column';
import { DecisionTable } from '../decision-table';
import { Rule } from '../rule';

export type DiffSrc = 'lhs' | 'rhs' | 'both' | 'resolved';

export class ColdMerge implements MergeResult {
  private conflict: ColdConflict;
  private mergedResult: any;
  // mapping of old col id to new col, used for merge
  private columnDrift: Map<string, Column>;
  private columnDriftET: Map<string, Column>;
  // mapping of old rule id to new rule, used for merge
  private ruleDrift: Map<string, Rule>;
  private ruleDriftET: Map<string, Rule>;
  // mapping of old cell id to new cell, used for merge
  private cellDrift: Map<string, Cell>;
  private cellDriftET: Map<string, Cell>;
  // mapping of pending rule deletions
  private pendingRule: Map<string, Rule>;
  private pendingRuleET: Map<string, Rule>;
  // mapping of pending column deletions
  private pendingCol: Map<string, Column>;
  private pendingColET: Map<string, Column>;

  constructor(
    private base: DecisionTable,
    private lhs: DecisionTable,
    private rhs: DecisionTable,
    private differ: differ.Differ,
    public i18n: I18n,
  ) {
    this.init();
  }

  getMerged(): DecisionTable {
    return this.mergedResult;
  }

  getConflict(): ColdConflict {
    return this.conflict;
  }

  getLhsDiff() {
    return this.conflict.getLhsDiff();
  }

  getRhsDiff() {
    return this.conflict.getRhsDiff();
  }

  resolvePropertyConflict(prop: string, resolution: differ.Resolution) {
    const item = this.conflict.getPropItems().get(prop);
    if (item) {
      const lhs = this.lhs.getProperty(prop);
      const rhs = this.rhs.getProperty(prop);
      if (resolution === differ.Resolution.LHS) {
        this.mergedResult.setProperty(prop, lhs);
        this.getRhsDiff().getPropItems().delete(prop);
      } else if (resolution === differ.Resolution.RHS) {
        this.mergedResult.setProperty(prop, rhs);
        this.getLhsDiff().getPropItems().delete(prop);
      }
      item.resolved = true;
    }
  }

  resolveCellConflict(cellId: string, resolution: differ.Resolution) {
    const item = this.conflict.getCellItems().get(cellId);
    if (item) {
      item.resolved = true;
      switch (resolution) {
        case differ.Resolution.LHS:
          switch (item.type) {
            case differ.ConflictType.CELL:
              this.mergedResult.setCell(cellId, this.safeExpr(item.a.rhs));
              this.getRhsDiff().getCellItems().delete(cellId);
              break;
            case differ.ConflictType.RULE_CELL:
              this.doRuleAndColumnDeletion('lhs', 'rule', cellId);
              break;
            case differ.ConflictType.CELL_RULE:
              this.revertRuleAndColumnDeletion('rhs', 'rule', cellId);
              break;
            case differ.ConflictType.BOTH_CELL:
              this.doRuleAndColumnDeletion('lhs', 'both', cellId);
              break;
            case differ.ConflictType.CELL_BOTH:
              this.revertRuleAndColumnDeletion('rhs', 'both', cellId);
              break;
            case differ.ConflictType.COLUMN_CELL:
              this.doRuleAndColumnDeletion('lhs', 'col', cellId);
              break;
            case differ.ConflictType.CELL_COLUMN:
              this.revertRuleAndColumnDeletion('rhs', 'col', cellId);
              break;
          }
          break;
        case differ.Resolution.RHS:
          switch (item.type) {
            case differ.ConflictType.CELL:
              this.mergedResult.setCell(cellId, this.safeExpr(item.b.rhs));
              this.getLhsDiff().getCellItems().delete(cellId);
              break;
            case differ.ConflictType.RULE_CELL:
              this.revertRuleAndColumnDeletion('lhs', 'rule', cellId);
              break;
            case differ.ConflictType.CELL_RULE:
              this.doRuleAndColumnDeletion('rhs', 'rule', cellId);
              break;
            case differ.ConflictType.BOTH_CELL:
              this.revertRuleAndColumnDeletion('lhs', 'both', cellId);
              break;
            case differ.ConflictType.CELL_BOTH:
              this.doRuleAndColumnDeletion('rhs', 'both', cellId);
              break;
            case differ.ConflictType.COLUMN_CELL:
              this.revertRuleAndColumnDeletion('lhs', 'col', cellId);
              break;
            case differ.ConflictType.CELL_COLUMN:
              this.doRuleAndColumnDeletion('rhs', 'col', cellId);
              break;
          }
      }
    }
  }

  resolveETCellConflict(cellId: string, resolution: differ.Resolution) {
    const item = this.conflict.getETCellItems().get(cellId);
    if (item) {
      item.resolved = true;
      switch (resolution) {
        case differ.Resolution.LHS:
          switch (item.type) {
            case differ.ConflictType.CELL:
              this.mergedResult.setETCell(cellId, this.safeExpr(item.a.rhs));
              this.getRhsDiff().getETCellItems().delete(cellId);
              break;
            case differ.ConflictType.RULE_CELL:
              this.doETRuleAndColumnDeletion('lhs', 'rule', cellId);
              break;
            case differ.ConflictType.CELL_RULE:
              this.revertETRuleAndColumnDeletion('rhs', 'rule', cellId);
              break;
            case differ.ConflictType.BOTH_CELL:
              this.doETRuleAndColumnDeletion('lhs', 'both', cellId);
              break;
            case differ.ConflictType.CELL_BOTH:
              this.revertETRuleAndColumnDeletion('rhs', 'both', cellId);
              break;
            case differ.ConflictType.COLUMN_CELL:
              this.doETRuleAndColumnDeletion('lhs', 'col', cellId);
              break;
            case differ.ConflictType.CELL_COLUMN:
              this.revertETRuleAndColumnDeletion('rhs', 'col', cellId);
              break;
          }
          break;
        case differ.Resolution.RHS:
          switch (item.type) {
            case differ.ConflictType.CELL:
              this.mergedResult.setETCell(cellId, this.safeExpr(item.b.rhs));
              this.getLhsDiff().getCellItems().delete(cellId);
              break;
            case differ.ConflictType.RULE_CELL:
              this.revertETRuleAndColumnDeletion('lhs', 'rule', cellId);
              break;
            case differ.ConflictType.CELL_RULE:
              this.doETRuleAndColumnDeletion('rhs', 'rule', cellId);
              break;
            case differ.ConflictType.BOTH_CELL:
              this.revertETRuleAndColumnDeletion('lhs', 'both', cellId);
              break;
            case differ.ConflictType.CELL_BOTH:
              this.doETRuleAndColumnDeletion('rhs', 'both', cellId);
              break;
            case differ.ConflictType.COLUMN_CELL:
              this.revertETRuleAndColumnDeletion('lhs', 'col', cellId);
              break;
            case differ.ConflictType.CELL_COLUMN:
              this.doETRuleAndColumnDeletion('rhs', 'col', cellId);
              break;
          }
      }
    }
  }

  hasConflict(): boolean {
    return !this.conflict.isEmpty();
  }

  private init() {
    // clone the raw result
    this.conflict = this.differ.conflict(this.base, this.lhs, this.rhs);

    // process lhs diff mapping and inital mergedResult
    this.prepareMergedResult();

    this.mergeLhs();
    this.mergeRhs();
  }

  private mergeLhs() {
    // then we try to merge diff from rhs to it
    // columns the first
    this.mergeColumnsDiff(this.conflict.getLhsDiff());
    // ET columns
    this.mergeETColumnsDiff(this.conflict.getLhsDiff());
    // rules the second
    this.mergeRulesDiff(this.conflict.getLhsDiff());
    // ET rules
    this.mergeETRulesDiff(this.conflict.getLhsDiff());
    // cells the third
    this.mergeCellDiff(this.getLhsDiff());
    // ET cells
    this.mergeETCellDiff(this.getLhsDiff());
    // properties the last
    this.mergePropertyDiff(this.getLhsDiff());
    // fix diff item drift
    this.fixDiffItemDrift(this.conflict.getLhsDiff());
  }

  private fixDiffItemDrift(diffResult: ColdDiff) {
    const columnItems = new Map<string, differ.DiffItem>();
    diffResult.getColumnItems().forEach((v, k) => {
      const drifted = this.columnDrift.get(k);
      if (drifted) {
        columnItems.set(drifted.getId(), { kind: differ.NEW, type: differ.COLUMN, lhs: null, rhs: drifted });
      } else {
        columnItems.set(k, v);
      }
    });
    diffResult.setColumnItems(columnItems);

    const ruleItems = new Map<string, differ.DiffItem>();
    diffResult.getRuleItems().forEach((v, k) => {
      const drifted = this.ruleDrift.get(k);
      if (drifted) {
        ruleItems.set(drifted.getId(), { kind: differ.NEW, type: differ.RULE, lhs: null, rhs: drifted });
      } else {
        ruleItems.set(k, v);
      }
    });
    diffResult.setRuleItems(ruleItems);

    const cellItems = new Map<string, differ.DiffItem>();
    diffResult.getCellItems().forEach((v, k) => {
      const drifted = this.cellDrift.get(k);
      if (drifted) {
        cellItems.set(drifted.getId(), { kind: differ.NEW, type: differ.CELL, lhs: null, rhs: drifted });
      } else {
        cellItems.set(k, v);
      }
    });
    diffResult.setCellItems(cellItems);

    const columnItemsET = new Map<string, differ.DiffItem>();
    diffResult.getETColumnItems().forEach((v, k) => {
      const drifted = this.columnDriftET.get(k);
      if (drifted) {
        columnItemsET.set(drifted.getId(), { kind: differ.NEW, type: differ.COLUMN, lhs: null, rhs: drifted });
      } else {
        columnItemsET.set(k, v);
      }
    });
    diffResult.setETColumnItems(columnItemsET);

    const ruleItemsET = new Map<string, differ.DiffItem>();
    diffResult.getETRuleItems().forEach((v, k) => {
      const drifted = this.ruleDriftET.get(k);
      if (drifted) {
        ruleItemsET.set(drifted.getId(), { kind: differ.NEW, type: differ.RULE, lhs: null, rhs: drifted });
      } else {
        ruleItemsET.set(k, v);
      }
    });
    diffResult.setETRuleItems(ruleItemsET);

    const cellItemsET = new Map<string, differ.DiffItem>();
    diffResult.getETCellItems().forEach((v, k) => {
      const drifted = this.cellDriftET.get(k);
      if (drifted) {
        cellItemsET.set(drifted.getId(), { kind: differ.NEW, type: differ.CELL, lhs: null, rhs: drifted });
      } else {
        cellItemsET.set(k, v);
      }
    });
    diffResult.setETCellItems(cellItemsET);
  }

  private mergeRhs() {
    // then we try to merge diff from rhs to it
    // columns the first
    this.mergeColumnsDiff(this.getRhsDiff());
    // ET columns
    this.mergeETColumnsDiff(this.getRhsDiff());
    // rules the second
    this.mergeRulesDiff(this.getRhsDiff());
    // ET rules
    this.mergeETRulesDiff(this.getRhsDiff());
    // cells the third
    this.mergeCellDiff(this.getRhsDiff());
    // ET cells
    this.mergeETCellDiff(this.getRhsDiff());
    // properties the last
    this.mergePropertyDiff(this.getRhsDiff());
    // fix diff item drift
    this.fixDiffItemDrift(this.conflict.getRhsDiff());
  }

  private prepareMergedResult() {
    // we use lhs as merge base
    this.mergedResult = this.base.clone();
  }

  private mergeColumnsDiff(toMerge: ColdDiff) {
    this.columnDrift = new Map<string, Column>();
    this.pendingCol = new Map<string, Column>();
    toMerge.getColumnItems().forEach((item, id) => {
      const conflictItem = this.conflict.getColumnItems().get(id);
      if (conflictItem) {
        switch (item.kind) {
          // add them sequentially
          case differ.NEW:
            const col: Column = item.rhs;
            // add to merged table
            if (this.mergedResult.hasColumn(id)) {
              const real = this.mergedResult.createAndAddColumn(col.name, col.propertyType, col.columnType);
              // record the column id drift, for further cell diff merge
              this.columnDrift.set(id, real);
            } else {
              this.mergedResult.createAndAddColumnWithId(col.getId(), col.name, col.propertyType, col.columnType);
            }
            // it's resolved now
            conflictItem.resolved = true;
            break;
          case differ.EDIT:
            conflictItem.resolved = false;
            break;
          case differ.DELETE:
            // if a column is edited and deleted respectively, we leave it unresolved.
            this.pendingCol.set(id, item.lhs);
            conflictItem.resolved = false;
            break;
        }
      } else {
        switch (item.kind) {
          case differ.NEW:
          case differ.EDIT:
            const col: Column = item.rhs;
            // update the same-id column in merged table
            // or simply add it if the id is new
            this.mergedResult.createAndAddColumnWithId(col.getId(), col.name, col.propertyType, col.columnType);
            break;
          case differ.DELETE:
            if (this.mergedResult.hasColumn(item.lhs.getId())) {
              this.mergedResult.clearColumn(item.lhs.getId());
            }
            break;
        }
      }
    });
  }

  private mergeETColumnsDiff(toMerge: ColdDiff) {
    this.columnDriftET = new Map<string, Column>();
    this.pendingColET = new Map<string, Column>();
    toMerge.getETColumnItems().forEach((item, id) => {
      const conflictItem = this.conflict.getETColumnItems().get(id);
      if (conflictItem) {
        switch (item.kind) {
          // add them sequentially
          case differ.NEW:
            const col: Column = item.rhs;
            // add to merged table
            if (this.mergedResult.hasETColumn(id)) {
              const real = this.mergedResult.createAndAddETColumn(col.name, col.propertyType, col.columnType);
              // record the column id drift, for further cell diff merge
              this.columnDriftET.set(id, real);
            } else {
              this.mergedResult.createAndAddETColumnWithId(col.getId(), col.name, col.propertyType, col.columnType);
            }
            // it's resolved now
            conflictItem.resolved = true;
            break;
          case differ.EDIT:
            conflictItem.resolved = false;
            break;
          case differ.DELETE:
            // if a column is edited and deleted respectively, we leave it unresolved.
            this.pendingColET.set(id, item.lhs);
            conflictItem.resolved = false;
            break;
        }
      } else {
        switch (item.kind) {
          case differ.NEW:
          case differ.EDIT:
            const col: Column = item.rhs;
            // update the same-id column in merged table
            // or simply add it if the id is new
            this.mergedResult.createAndAddColumnWithId(col.getId(), col.name, col.propertyType, col.columnType);
            break;
          case differ.DELETE:
            if (this.mergedResult.hasColumn(item.lhs.getId())) {
              this.mergedResult.clearColumn(item.lhs.getId());
            }
            break;
        }
      }
    });
  }

  private mergeRulesDiff(toMerge: ColdDiff) {
    this.ruleDrift = new Map<string, Rule>();
    this.pendingRule = new Map<string, Rule>();
    toMerge.getRuleItems().forEach((item, id) => {
      const conflictItem = this.conflict.getRuleItems().get(id);
      if (conflictItem) {
        switch (item.kind) {
          case differ.NEW:
            const rule: Rule = item.rhs;
            if (this.mergedResult.hasRule(id)) {
              // record id drift
              const real = this.mergedResult.createRuleWithAutoId();
              this.ruleDrift.set(rule.getId(), real);
            } else {
              this.mergedResult.createAndAddRule(id);
            }
            conflictItem.resolved = true;
            break;
          case differ.EDIT:
            // might both edit rule properties
            conflictItem.resolved = false;
            break;
          case differ.DELETE:
            conflictItem.resolved = false;
            this.pendingRule.set(id, item.lhs);
            break;
        }
      } else {
        switch (item.kind) {
          case differ.NEW:
            this.mergedResult.createAndAddRule(id);
            break;
          case differ.EDIT:
            // rule properties has been modified, we shall adjust the properties, but now there is none.
            break;
          case differ.DELETE:
            this.mergedResult.clearRule(item.lhs.getId());
            break;
        }
      }
    });
  }

  private mergeETRulesDiff(toMerge: ColdDiff) {
    this.ruleDriftET = new Map<string, Rule>();
    this.pendingRuleET = new Map<string, Rule>();
    if (toMerge.getETRuleItems()) {
      toMerge.getETRuleItems().forEach((item, id) => {
        const conflictItem = this.conflict.getETRuleItems().get(id);
        if (conflictItem) {
          switch (item.kind) {
            case differ.NEW:
              const rule: Rule = item.rhs;
              if (this.mergedResult.hasETRule(id)) {
                // record id drift
                const real = this.mergedResult.createETRuleWithAutoId();
                this.ruleDriftET.set(rule.getId(), real);
              } else {
                this.mergedResult.createAndAddETRule(id);
              }
              conflictItem.resolved = true;
              break;
            case differ.EDIT:
              // might both edit rule properties
              conflictItem.resolved = false;
              break;
            case differ.DELETE:
              conflictItem.resolved = false;
              this.pendingRuleET.set(id, item.lhs);
              break;
          }
        } else {
          switch (item.kind) {
            case differ.NEW:
              this.mergedResult.createAndAddETRule(id);
              break;
            case differ.EDIT:
              // rule properties has been modified, we shall adjust the properties, but now there is none.
              break;
            case differ.DELETE:
              this.mergedResult.clearETRule(item.lhs.getId());
              break;
          }
        }
      });
    }
  }

  private mergeCellDiff(toMerge: ColdDiff) {
    this.cellDrift = new Map<string, Cell>();
    toMerge.getCellItems().forEach((item, id) => {
      const conflictItem = this.conflict.getCellItems().get(id);
      if (conflictItem) {
        conflictItem.resolved = false;
        switch (conflictItem.type) {
          case differ.ConflictType.CELL:
            break;
          case differ.ConflictType.CELL_IN_NEW:
            {
              const cell = item.rhs;
              const cellId = this.getDriftedCellId(cell);
              const real = this.mergedResult.setCell(cellId, cell.getExpr());
              // only remove the cell conflict when it's drifted
              if (cellId !== id) {
                this.cellDrift.set(id, real);
                this.conflict.getCellItems().delete(id);
              }
            }
            break;
          case differ.ConflictType.CELL_COLUMN:
          case differ.ConflictType.CELL_RULE:
          case differ.ConflictType.RULE_CELL:
          case differ.ConflictType.COLUMN_CELL:
            {
              // modifications are still useful, we keep then
              // however we don't apply deletions
              if (item.kind === differ.NEW || item.kind === differ.EDIT) {
                const cell = item.rhs;
                const cellId = this.getDriftedCellId(cell);
                const real = this.mergedResult.setCell(cellId, cell.getExpr());
                if (cellId !== id) {
                  this.cellDrift.set(id, real);
                  this.conflict.getCellItems().delete(id);
                  this.conflict.getCellItems().set(cellId, conflictItem);
                }
              }
            }
            break;
        }
      } else {
        const ids = this.getIds(id);
        switch (item.kind) {
          case differ.NEW:
          case differ.EDIT:
            const cell: Cell = item.rhs;
            const cellId = this.getDriftedCellId(cell);
            this.mergedResult.setCell(cellId, cell.getExpr());
            if (cellId !== cell.getId()) {
              // record the id drift
              this.cellDrift.set(id, this.mergedResult.getCell(cellId));
            }
            break;
          case differ.DELETE:
            // since rule/column deletion is postponed, we also hold on the cell deletion
            if (!this.pendingRule.has(ids.ruleId) && !this.pendingCol.has(ids.colId)) {
              try {
                this.mergedResult.clearCell(id);
              } catch (e) {
                // deletion could fail because it can be deleted by lhs already, but it's ok.
              }
            }
            break;
        }
      }
    });
  }

  private mergeETCellDiff(toMerge: ColdDiff) {
    this.cellDriftET = new Map<string, Cell>();
    if (toMerge.getETCellItems()) {
      toMerge.getETCellItems().forEach((item, id) => {
        const conflictItem = this.conflict.getETCellItems().get(id);
        if (conflictItem) {
          conflictItem.resolved = false;
          switch (conflictItem.type) {
            case differ.ConflictType.CELL:
              break;
            case differ.ConflictType.CELL_IN_NEW:
              {
                const cell = item.rhs;
                const cellId = this.getDriftedCellId(cell);
                const real = this.mergedResult.setETCell(cellId, cell.getExpr());
                // only remove the cell conflict when it's drifted
                if (cellId !== id) {
                  this.cellDriftET.set(id, real);
                  this.conflict.getETCellItems().delete(id);
                }
              }
              break;
            case differ.ConflictType.CELL_COLUMN:
            case differ.ConflictType.CELL_RULE:
            case differ.ConflictType.RULE_CELL:
            case differ.ConflictType.COLUMN_CELL:
              {
                // modifications are still useful, we keep then
                // however we don't apply deletions
                if (item.kind === differ.NEW || item.kind === differ.EDIT) {
                  const cell = item.rhs;
                  const cellId = this.getDriftedCellId(cell);
                  const real = this.mergedResult.setETCell(cellId, cell.getExpr());
                  if (cellId !== id) {
                    this.cellDriftET.set(id, real);
                    this.conflict.getETCellItems().delete(id);
                    this.conflict.getETCellItems().set(cellId, conflictItem);
                  }
                }
              }
              break;
          }
        } else {
          const ids = this.getIds(id);
          switch (item.kind) {
            case differ.NEW:
            case differ.EDIT:
              const cell: Cell = item.rhs;
              const cellId = this.getDriftedCellId(cell);
              this.mergedResult.setETCell(cellId, cell.getExpr());
              if (cellId !== cell.getId()) {
                // record the id drift
                this.cellDriftET.set(id, this.mergedResult.getETCell(cellId));
              }
              break;
            case differ.DELETE:
              // since rule/column deletion is postponed, we also hold on the cell deletion
              if (!this.pendingRuleET.has(ids.ruleId) && !this.pendingColET.has(ids.colId)) {
                try {
                  this.mergedResult.clearETCell(id);
                } catch (e) {
                  // deletion could fail because it can be deleted by lhs already, but it's ok.
                }
              }
              break;
          }
        }
      });
    }
  }

  private mergePropertyDiff(toMerge: ColdDiff) {
    toMerge.getPropItems().forEach((item, key) => {
      const conflictItem = this.conflict.getPropItems().get(key);
      if (conflictItem) {
        conflictItem.resolved = false;
      } else {
        const val: string = item.rhs;
        switch (item.kind) {
          case differ.NEW:
          case differ.EDIT:
            this.mergedResult.setProperty(key, val);
            break;
          case differ.DELETE:
            this.mergedResult.clearProperty(key);
            break;
        }
      }
    });
  }

  private revertRuleAndColumnDeletion(src: 'lhs' | 'rhs', ruleOrCol: 'rule' | 'col' | 'both', cellId: string) {
    const ids = this.getIds(cellId);
    let diffResult: differ.ColdDiff;
    let otherResult: differ.ColdDiff;
    if (src === 'lhs') {
      diffResult = this.getLhsDiff();
      otherResult = this.getRhsDiff();
    } else if (src === 'rhs') {
      diffResult = this.getRhsDiff();
      otherResult = this.getLhsDiff();
    }
    if (this.isRule(ruleOrCol)) {
      const ruleConflict = environment.enableBEUI ? this.getConflict().getRuleItems().get(JSON.parse(ids.ruleId)) : this.getConflict().getRuleItems().get(ids.ruleId);
      if (this.isConflict(ruleConflict)) {
        ruleConflict.resolved = true;
      } else {
        throw Error(this.i18n('Cannot find the rule conflict item for the cell/rule conflict, something must be wrong'));
      }
      // remove the rule deletion record
      diffResult.getRuleItems().delete(environment.enableBEUI ? JSON.parse(ids.ruleId) : ids.ruleId);
    }
    if (this.isCol(ruleOrCol)) {
      const colConflict = environment.enableBEUI ? this.getConflict().getColumnItems().get(JSON.parse(ids.colId)) : this.getConflict().getColumnItems().get(ids.colId);
      if (this.isConflict(colConflict)) {
        colConflict.resolved = true;
      } else {
        throw Error(this.i18n('Cannot find the rule conflict item for the cell/column conflict, something must be wrong'));
      }
      // remove the col deletion record
      diffResult.getColumnItems().delete(environment.enableBEUI ? JSON.parse(ids.colId) : ids.colId);
    }

    // resovle the cell conflicts inside the reverted row/column deletion
    // however, some cell conflicts belongs to both row and column deletion,
    // we need to transform them
    otherResult.getCellItems().forEach((v, k) => {
      const otherIds = this.getIds(k);
      if (this.isRule(ruleOrCol) && otherIds.ruleId === ids.ruleId) {
        const cellConflict = this.getConflict().getCellItems().get(k);
        if (this.isConflict(cellConflict)) {
          const colItem = diffResult.getColumnItem(otherIds.colId);
          if (cellConflict.type === differ.ConflictType.BOTH_CELL) {
            cellConflict.type = differ.ConflictType.COLUMN_CELL;
            cellConflict.a = colItem;
          } else if (cellConflict.type === differ.ConflictType.CELL_BOTH) {
            cellConflict.type = differ.ConflictType.CELL_COLUMN;
            cellConflict.b = colItem;
          } else {
            this.conflict.getCellItems().delete(k);
          }
        }
      }
      if (this.isCol(ruleOrCol) && otherIds.colId === ids.colId) {
        const cellConflict = this.getConflict().getCellItems().get(k);
        if (this.isConflict(cellConflict)) {
          const ruleItem = diffResult.getRuleItem(otherIds.ruleId);
          if (cellConflict.type === differ.ConflictType.BOTH_CELL) {
            cellConflict.type = differ.ConflictType.RULE_CELL;
            cellConflict.a = ruleItem;
          } else if (cellConflict.type === differ.ConflictType.CELL_BOTH) {
            cellConflict.type = differ.ConflictType.CELL_RULE;
            cellConflict.b = ruleItem;
          } else {
            this.conflict.getCellItems().delete(k);
          }
        }
      }
    });

    // as we have reverted the row/column deletion, now remove 'this-hand-side' cell diff inside
    // the rule or column, as long as its conflict is fully resolved.
    diffResult.getCellItems().forEach((v, k) => {
      const cellIds = this.getIds(k);
      const cellConflict = this.conflict.getCellItems().get(k);
      if (!this.isConflict(cellConflict)) {
        if (this.isRule(ruleOrCol) && cellIds.ruleId === ids.ruleId) {
          diffResult.getCellItems().delete(k);
        } else if (this.isCol(ruleOrCol) && cellIds.colId === ids.colId) {
          diffResult.getCellItems().delete(k);
        }
      }
    });
  }

  private revertETRuleAndColumnDeletion(src: 'lhs' | 'rhs', ruleOrCol: 'rule' | 'col' | 'both', cellId: string) {
    const ids = this.getIds(cellId);
    let diffResult: differ.ColdDiff;
    let otherResult: differ.ColdDiff;
    if (src === 'lhs') {
      diffResult = this.getLhsDiff();
      otherResult = this.getRhsDiff();
    } else if (src === 'rhs') {
      diffResult = this.getRhsDiff();
      otherResult = this.getLhsDiff();
    }
    if (this.isRule(ruleOrCol)) {
      const ruleConflict = this.getConflict().getETRuleItems().get(JSON.parse(ids.ruleId));
      if (this.isConflict(ruleConflict)) {
        ruleConflict.resolved = true;
      } else {
        throw Error(this.i18n('Cannot find the rule conflict item for the cell/rule conflict, something must be wrong'));
      }
      // remove the rule deletion record
      diffResult.getETRuleItems().delete(JSON.parse(ids.ruleId));
    }
    if (this.isCol(ruleOrCol)) {
      const colConflict = this.getConflict().getETColumnItems().get(ids.colId);
      if (this.isConflict(colConflict)) {
        colConflict.resolved = true;
      } else {
        throw Error(this.i18n('Cannot find the rule conflict item for the cell/column conflict, something must be wrong'));
      }
      // remove the col deletion record
      diffResult.getETColumnItems().delete(ids.colId);
    }

    // resovle the cell conflicts inside the reverted row/column deletion
    // however, some cell conflicts belongs to both row and column deletion,
    // we need to transform them
    otherResult.getETCellItems().forEach((v, k) => {
      const otherIds = this.getIds(k);
      if (this.isRule(ruleOrCol) && otherIds.ruleId === ids.ruleId) {
        const cellConflict = this.getConflict().getETCellItems().get(k);
        if (this.isConflict(cellConflict)) {
          const colItem = diffResult.getETColumnItem(otherIds.colId);
          if (cellConflict.type === differ.ConflictType.BOTH_CELL) {
            cellConflict.type = differ.ConflictType.COLUMN_CELL;
            cellConflict.a = colItem;
          } else if (cellConflict.type === differ.ConflictType.CELL_BOTH) {
            cellConflict.type = differ.ConflictType.CELL_COLUMN;
            cellConflict.b = colItem;
          } else {
            this.conflict.getETCellItems().delete(k);
          }
        }
      }
      if (this.isCol(ruleOrCol) && otherIds.colId === ids.colId) {
        const cellConflict = this.getConflict().getETCellItems().get(k);
        if (this.isConflict(cellConflict)) {
          const ruleItem = diffResult.getETRuleItem(otherIds.ruleId);
          if (cellConflict.type === differ.ConflictType.BOTH_CELL) {
            cellConflict.type = differ.ConflictType.RULE_CELL;
            cellConflict.a = ruleItem;
          } else if (cellConflict.type === differ.ConflictType.CELL_BOTH) {
            cellConflict.type = differ.ConflictType.CELL_RULE;
            cellConflict.b = ruleItem;
          } else {
            this.conflict.getETCellItems().delete(k);
          }
        }
      }
    });

    // as we have reverted the row/column deletion, now remove 'this-hand-side' cell diff inside
    // the rule or column, as long as its conflict is fully resolved.
    diffResult.getETCellItems().forEach((v, k) => {
      const cellIds = this.getIds(k);
      const cellConflict = this.conflict.getETCellItems().get(k);
      if (!this.isConflict(cellConflict)) {
        if (this.isRule(ruleOrCol) && cellIds.ruleId === ids.ruleId) {
          diffResult.getETCellItems().delete(k);
        } else if (this.isCol(ruleOrCol) && cellIds.colId === ids.colId) {
          diffResult.getETCellItems().delete(k);
        }
      }
    });
  }

  private doRuleAndColumnDeletion(src: 'lhs' | 'rhs', ruleOrCol: 'rule' | 'col' | 'both', cellId: string) {
    const ids = this.getIds(cellId);
    let diffResult: differ.ColdDiff;
    let otherResult: differ.ColdDiff;

    if (src === 'lhs') {
      diffResult = this.getLhsDiff();
      otherResult = this.getRhsDiff();
    } else {
      diffResult = this.getRhsDiff();
      otherResult = this.getLhsDiff();
    }
    if (this.isRule(ruleOrCol)) {
      this.mergedResult.clearRule(ids.ruleId);
      const ruleItem = this.getConflict().getRuleItems().get(environment.enableBEUI ? JSON.parse(ids.ruleId) : ids.ruleId);
      if (this.isConflict(ruleItem)) {
        ruleItem.resolved = true;
      } else {
        throw Error(this.i18n('Cannot find the rule conflict item for the cell/rule conflict, something must be wrong'));
      }
    }
    if (this.isCol(ruleOrCol)) {
      this.mergedResult.clearColumn(ids.colId);
      const colItem = this.getConflict().getColumnItems().get(ids.colId);
      if (this.isConflict(colItem)) {
        colItem.resolved = true;
      } else {
        throw Error(this.i18n('Cannot find the column conflict item for the cell/column conflict, something must be wrong'));
      }
    }

    // resovle the cell conflicts inside the exec-ed row/column deletion
    // however, some cell conflicts belongs to both row and column deletion,
    // we need to transform them
    otherResult.getCellItems().forEach((v, k) => {
      const cellIds = this.getIds(k);
      if (this.isRule(ruleOrCol) && cellIds.ruleId === ids.ruleId) {
        const cellConflict = this.getConflict().getCellItems().get(k);
        if (this.isConflict(cellConflict)) {
          const colItem = diffResult.getColumnItem(cellIds.colId);
          if (cellConflict.type === differ.ConflictType.BOTH_CELL) {
            cellConflict.type = differ.ConflictType.COLUMN_CELL;
            cellConflict.a = colItem;
          } else if (cellConflict.type === differ.ConflictType.CELL_BOTH) {
            cellConflict.type = differ.ConflictType.CELL_COLUMN;
            cellConflict.b = colItem;
          } else {
            this.conflict.getCellItems().delete(k);
          }
        }
      }
      if (this.isCol(ruleOrCol) && cellIds.colId === ids.colId) {
        const cellConflict = this.getConflict().getCellItems().get(k);
        if (this.isConflict(cellConflict)) {
          const ruleItem = diffResult.getRuleItem(cellIds.ruleId);
          if (cellConflict.type === differ.ConflictType.BOTH_CELL) {
            cellConflict.type = differ.ConflictType.RULE_CELL;
            cellConflict.a = ruleItem;
          } else if (cellConflict.type === differ.ConflictType.CELL_BOTH) {
            cellConflict.type = differ.ConflictType.CELL_RULE;
            cellConflict.b = ruleItem;
          } else {
            this.conflict.getCellItems().delete(k);
          }
        }
      }
    });

    // as we have exec-ed the row/column deletion, now remove 'the-other-hand-side' cell diff inside
    // the rule or column, as long as its conflict is fully resolved.
    otherResult.getCellItems().forEach((v, k) => {
      const cellIds = this.getIds(k);
      const cellConflict = this.getConflict().getCellItems().get(k);
      if (!this.isConflict(cellConflict)) {
        if (this.isRule(ruleOrCol) && cellIds.ruleId === ids.ruleId) {
          otherResult.getCellItems().delete(k);
        } else if (this.isCol(ruleOrCol) && cellIds.colId === ids.colId) {
          otherResult.getCellItems().delete(k);
        }
      }
    });
  }

  private doETRuleAndColumnDeletion(src: 'lhs' | 'rhs', ruleOrCol: 'rule' | 'col' | 'both', cellId: string) {
    const ids = this.getIds(cellId);
    let diffResult: differ.ColdDiff;
    let otherResult: differ.ColdDiff;

    if (src === 'lhs') {
      diffResult = this.getLhsDiff();
      otherResult = this.getRhsDiff();
    } else {
      diffResult = this.getRhsDiff();
      otherResult = this.getLhsDiff();
    }
    if (this.isRule(ruleOrCol)) {
      this.mergedResult.clearETRule(JSON.parse(ids.ruleId));
      const ruleItem = this.getConflict().getETRuleItems().get(JSON.parse(ids.ruleId));
      if (this.isConflict(ruleItem)) {
        ruleItem.resolved = true;
      } else {
        throw Error(this.i18n('Cannot find the rule conflict item for the cell/rule conflict, something must be wrong'));
      }
    }
    if (this.isCol(ruleOrCol)) {
      this.mergedResult.clearETColumn(JSON.parse(ids.colId));
      const colItem = this.getConflict().getETColumnItems().get(ids.colId);
      if (this.isConflict(colItem)) {
        colItem.resolved = true;
      } else {
        throw Error(this.i18n('Cannot find the column conflict item for the cell/column conflict, something must be wrong'));
      }
    }

    // resovle the cell conflicts inside the exec-ed row/column deletion
    // however, some cell conflicts belongs to both row and column deletion,
    // we need to transform them
    otherResult.getETCellItems().forEach((v, k) => {
      const cellIds = this.getIds(k);
      if (this.isRule(ruleOrCol) && cellIds.ruleId === ids.ruleId) {
        const cellConflict = this.getConflict().getETCellItems().get(k);
        if (this.isConflict(cellConflict)) {
          const colItem = diffResult.getETColumnItem(cellIds.colId);
          if (cellConflict.type === differ.ConflictType.BOTH_CELL) {
            cellConflict.type = differ.ConflictType.COLUMN_CELL;
            cellConflict.a = colItem;
          } else if (cellConflict.type === differ.ConflictType.CELL_BOTH) {
            cellConflict.type = differ.ConflictType.CELL_COLUMN;
            cellConflict.b = colItem;
          } else {
            this.conflict.getETCellItems().delete(k);
          }
        }
      }
      if (this.isCol(ruleOrCol) && cellIds.colId === ids.colId) {
        const cellConflict = this.getConflict().getETCellItems().get(k);
        if (this.isConflict(cellConflict)) {
          const ruleItem = diffResult.getETRuleItem(cellIds.ruleId);
          if (cellConflict.type === differ.ConflictType.BOTH_CELL) {
            cellConflict.type = differ.ConflictType.RULE_CELL;
            cellConflict.a = ruleItem;
          } else if (cellConflict.type === differ.ConflictType.CELL_BOTH) {
            cellConflict.type = differ.ConflictType.CELL_RULE;
            cellConflict.b = ruleItem;
          } else {
            this.conflict.getETCellItems().delete(k);
          }
        }
      }
    });

    // as we have exec-ed the row/column deletion, now remove 'the-other-hand-side' cell diff inside
    // the rule or column, as long as its conflict is fully resolved.
    otherResult.getETCellItems().forEach((v, k) => {
      const cellIds = this.getIds(k);
      const cellConflict = this.getConflict().getETCellItems().get(k);
      if (!this.isConflict(cellConflict)) {
        if (this.isRule(ruleOrCol) && cellIds.ruleId === ids.ruleId) {
          otherResult.getETCellItems().delete(k);
        } else if (this.isCol(ruleOrCol) && cellIds.colId === ids.colId) {
          otherResult.getETCellItems().delete(k);
        }
      }
    });
  }

  private getIds(cellId: string) {
    const parts = cellId.split('_');
    return {
      ruleId: parts[0],
      colId: parts[1],
    };
  }

  private getDriftedCellId(cell: Cell) {
    const colId = this.columnDrift.has(cell.getColId()) ? this.columnDrift.get(cell.getColId()).getId() : cell.getColId();
    const ruleId = this.ruleDrift.has(cell.getRuleId()) ? this.ruleDrift.get(cell.getRuleId()).getId() : cell.getRuleId();
    return `${ruleId}_${colId}`;
  }

  private safeExpr(cell: Cell) {
    return cell ? this.safe(cell.getExpr()) : '';
  }
  private safe(s: string) {
    return s ? s : '';
  }

  private isRule(ruleOrCol) {
    return ruleOrCol === 'rule' || ruleOrCol === 'both';
  }
  private isCol(ruleOrCol) {
    return ruleOrCol === 'col' || ruleOrCol === 'both';
  }
  private isConflict(conflict: differ.ConflictItem): boolean {
    return conflict && !conflict.resolved;
  }
}
