import * as _ from 'lodash';

import { ColdDiff } from './cold-diff';
import { ConflictItem, ConflictResult, ConflictType, CELL, COLUMN, DiffItem, DELETE, META, NEW, PROP, RULE } from './differ';

import { environment } from '../../../../environments/environment';

export class ColdConflict implements ConflictResult {
  private cellItems = new Map<string, ConflictItem>();
  private etCellItems = new Map<string, ConflictItem>();
  private propItems = new Map<string, ConflictItem>();
  private columnItems = new Map<string, ConflictItem>();
  private etColumnItems = new Map<string, ConflictItem>();
  private metaItems = new Map<string, ConflictItem>();
  private ruleItems = new Map<string, ConflictItem>();
  private etRuleItems = new Map<string, ConflictItem>();

  constructor(
    private d1: ColdDiff,
    private d2: ColdDiff
  ) {
    this.init();
  }

  getPropItems(): Map<string, ConflictItem> {
    return this.propItems;
  }
  getCellItems(): Map<string, ConflictItem> {
    return this.cellItems;
  }
  getETCellItems(): Map<string, ConflictItem> {
    return this.etCellItems;
  }
  getMetaItems(): Map<string, ConflictItem> {
    return this.metaItems;
  }
  getRuleItems(): Map<string, ConflictItem> {
    return this.ruleItems;
  }
  getETRuleItems(): Map<string, ConflictItem> {
    return this.etRuleItems;
  }
  getColumnItems(): Map<string, ConflictItem> {
    return this.columnItems;
  }
  getETColumnItems(): Map<string, ConflictItem> {
    return this.etColumnItems;
  }
  getLhsDiff(): ColdDiff {
    return this.d1;
  }
  getRhsDiff(): ColdDiff {
    return this.d2;
  }

  isEmpty(): boolean {
    // clear resolved items
    return [
      this.getPropItems(),
      this.getMetaItems(),
      this.getColumnItems(),
      this.getRuleItems(),
      this.getCellItems(),
    ].map(items => {
      const toDelete = [];
      items.forEach((v, k) => {
        if (v.resolved) {
          toDelete.push(k);
        }
      });
      toDelete.forEach(id => items.delete(id));
      return items.size;
    }).reduce((n, i) => n + i) === 0;
  }

  private init() {
    this.d1.getPropItems().forEach((v, k) => {
      const other = this.d2.getPropItem(k);
      if (other && this.isConflictDiff(v, other)) {
        this.propItems.set(k, { id: k, a: v, b: other, type: ConflictType.PROP });
      }
    });
    this.d1.getMetaItems().forEach((v, k) => {
      const other = this.d2.getMetaItem(k);
      if (other && this.isConflictDiff(v, other) && k !== 'decisionTableLastRuleId' && k !== 'exceptionTableLastRuleId') {
        this.metaItems.set(k, { id: k, a: v, b: other, type: ConflictType.META });
      }
    });
    this.d1.getColumnItems().forEach((v, k) => {
      const other = this.d2.getColumnItem(k);
      if (v.kind === DELETE) {
        // deleted column might contain modified cells
        this.d2.getCellItems().forEach((cellItem, cellId) => {
          const ids = this.getIds(cellId);
          if (ids.colId === k && cellItem.kind !== DELETE) {
            const w = this.d1.getRuleItem(ids.ruleId);
            if (this.isDelete(w)) {
              this.columnItems.set(ids.colId, { id: ids.colId, a: v, b: null, type: ConflictType.COLUMN });
              this.ruleItems.set(ids.ruleId, { id: ids.ruleId, a: w, b: null, type: ConflictType.RULE });
              this.cellItems.set(cellId, { id: cellId, a: null, b: cellItem, type: ConflictType.BOTH_CELL });
            } else {
              this.columnItems.set(k, { id: k, a: v, b: null, type: ConflictType.COLUMN });
              this.cellItems.set(cellId, { id: cellId, a: v, b: cellItem, type: ConflictType.COLUMN_CELL });
            }
          }
        });
      }
      // they might have modified the column differently
      if (other && this.isConflictDiff(v, other)) {
        this.columnItems.set(k, { id: k, a: v, b: other, type: ConflictType.COLUMN });
      }
    });

    if (this.d1.getETColumnItems()) {
      this.d1.getETColumnItems().forEach((v, k) => {
        const other = this.d2.getETColumnItem(k);
        if (v.kind === DELETE) {
          // deleted column might contain modified cells
          this.d2.getETCellItems().forEach((cellItem, cellId) => {
            const ids = this.getIds(cellId);
            if (ids.colId === k && cellItem.kind !== DELETE) {
              const w = this.d1.getETRuleItem(ids.ruleId);
              if (this.isDelete(w)) {
                this.etColumnItems.set(ids.colId, { id: ids.colId, a: v, b: null, type: ConflictType.COLUMN });
                this.etRuleItems.set(ids.ruleId, { id: ids.ruleId, a: w, b: null, type: ConflictType.RULE });
                this.etCellItems.set(cellId, { id: cellId, a: null, b: cellItem, type: ConflictType.BOTH_CELL });
              } else {
                this.etColumnItems.set(k, { id: k, a: v, b: null, type: ConflictType.COLUMN });
                this.etCellItems.set(cellId, { id: cellId, a: v, b: cellItem, type: ConflictType.COLUMN_CELL });
              }
            }
          });
        }
        // they might have modified the column differently
        if (other && this.isConflictDiff(v, other)) {
          this.etColumnItems.set(k, { id: k, a: v, b: other, type: ConflictType.COLUMN });
        }
      });
    }

    this.d2.getColumnItems().forEach((v, k) => {
      if (v.kind === NEW && !this.columnItems.get(k)) {
        this.columnItems.set(k, { id: k, a: null, b: v, type: ConflictType.COLUMN });
      }
    });

    if (this.d2.getETColumnItems()) {
      this.d2.getETColumnItems().forEach((v, k) => {
        if (v.kind === NEW && !this.etColumnItems.get(k)) {
          this.etColumnItems.set(k, { id: k, a: null, b: v, type: ConflictType.COLUMN });
        }
      });
    }

    this.d1.getRuleItems().forEach((v, k) => {
      const other = this.d2.getRuleItem(k);
      // deleted rule might contain modified cells
      if (v.kind === DELETE) {
        this.d2.getCellItems().forEach((cellItem, cellId) => {
          const ids = this.getIds(cellId);
          const w = this.d1.getColumnItem(ids.colId);
          const r = environment.enableBEUI ? JSON.parse(ids.ruleId) : ids.ruleId;
          if (r === k && cellItem.kind !== DELETE) {
            if (this.isDelete(w)) {
              this.ruleItems.set(ids.ruleId, { id: ids.ruleId, a: v, b: null, type: ConflictType.RULE });
              this.columnItems.set(ids.colId, { id: ids.colId, a: w, b: null, type: ConflictType.COLUMN });
              this.cellItems.set(cellId, { id: cellId, a: null, b: cellItem, type: ConflictType.BOTH_CELL });
            } else {
              this.ruleItems.set(k, { id: k, a: v, b: null, type: ConflictType.RULE });
              this.cellItems.set(cellId, { id: cellId, a: v, b: cellItem, type: ConflictType.RULE_CELL });
            }
          }
        });
      }
      // they might have modified rule differently
      if (other && this.isConflictDiff(v, other)) {
        this.ruleItems.set(k, { id: k, a: v, b: other, type: ConflictType.RULE });
      }
    });

    if (this.d1.getETRuleItems()) {
      this.d1.getETRuleItems().forEach((v, k) => {
        const other = this.d2.getETRuleItem(k);
        // deleted rule might contain modified cells
        if (v.kind === DELETE) {
          this.d2.getETCellItems().forEach((cellItem, cellId) => {
            const ids = this.getIds(cellId);
            const w = this.d1.getETColumnItem(ids.colId);
            const r = environment.enableBEUI ? JSON.parse(ids.ruleId) : ids.ruleId;
            if (r === k && cellItem.kind !== DELETE) {
              if (this.isDelete(w)) {
                this.etRuleItems.set(ids.ruleId, { id: ids.ruleId, a: v, b: null, type: ConflictType.RULE });
                this.etColumnItems.set(ids.colId, { id: ids.colId, a: w, b: null, type: ConflictType.COLUMN });
                this.etCellItems.set(cellId, { id: cellId, a: null, b: cellItem, type: ConflictType.BOTH_CELL });
              } else {
                this.etRuleItems.set(k, { id: k, a: v, b: null, type: ConflictType.RULE });
                this.etCellItems.set(cellId, { id: cellId, a: v, b: cellItem, type: ConflictType.RULE_CELL });
              }
            }
          });
        }
        // they might have modified rule differently
        if (other && this.isConflictDiff(v, other)) {
          this.etRuleItems.set(k, { id: k, a: v, b: other, type: ConflictType.RULE });
        }
      });
    }

    this.d2.getRuleItems().forEach((v, k) => {
      if (v.kind === NEW && !this.ruleItems.get(k)) {
        this.ruleItems.set(k, { id: k, a: null, b: v, type: ConflictType.RULE });
      }
      if (v.kind === DELETE) {
        this.d1.getCellItems().forEach((cellItem, cellId) => {
          const ids = this.getIds(cellId);
          const r = environment.enableBEUI ? JSON.parse(ids.ruleId) : ids.ruleId;
          if (r === k && cellItem.kind !== DELETE) {
            this.ruleItems.set(k, { id: k, a: null, b: v, type: ConflictType.RULE });
            this.cellItems.set(cellId, { id: cellId, a: cellItem, b: v, type: ConflictType.CELL_RULE });
          }
        });
      }
    });

    if (this.d2.getETRuleItems()) {
      this.d2.getETRuleItems().forEach((v, k) => {
        if (v.kind === NEW && !this.ruleItems.get(k)) {
          this.etRuleItems.set(k, { id: k, a: null, b: v, type: ConflictType.RULE });
        }
        if (v.kind === DELETE) {
          this.d1.getETCellItems().forEach((cellItem, cellId) => {
            const ids = this.getIds(cellId);
            const r = environment.enableBEUI ? JSON.parse(ids.ruleId) : ids.ruleId;
            if (r === k && cellItem.kind !== DELETE) {
              this.etRuleItems.set(k, { id: k, a: null, b: v, type: ConflictType.RULE });
              this.etCellItems.set(cellId, { id: cellId, a: cellItem, b: v, type: ConflictType.CELL_RULE });
            }
          });
        }
      });
    }

    this.d1.getCellItems().forEach((v, k) => {
      const ids = this.getIds(k);
      const colItem = this.d2.getColumnItem(ids.colId);
      const ruleItem = this.d2.getRuleItem(ids.ruleId);
      const other = this.d2.getCellItem(k);
      if (this.isDelete(colItem) && this.isDelete(ruleItem) && !this.isDelete(v)) {
        this.cellItems.set(k, { id: k, a: v, b: colItem, type: ConflictType.CELL_BOTH });
        this.columnItems.set(ids.colId, { id: ids.colId, a: null, b: colItem, type: ConflictType.COLUMN });
        this.ruleItems.set(ids.ruleId, { id: ids.ruleId, a: null, b: ruleItem, type: ConflictType.RULE });
      } else if (this.isDelete(colItem) && !this.isDelete(v)) {
        // the cell might belong to a deleted column
        this.cellItems.set(k, { id: k, a: v, b: colItem, type: ConflictType.CELL_COLUMN });
        this.columnItems.set(ids.colId, { id: ids.colId, a: null, b: colItem, type: ConflictType.COLUMN });
      } else if (this.isDelete(ruleItem) && !this.isDelete(v)) {
        // the cell might belong to a deleted rule
        this.cellItems.set(k, { id: k, a: v, b: ruleItem, type: ConflictType.CELL_RULE });
        this.ruleItems.set(ids.ruleId, { id: ids.ruleId, a: null, b: ruleItem, type: ConflictType.RULE });
      } else if (other && this.isConflictDiff(v, other) && (colItem && colItem.kind === NEW || ruleItem && ruleItem.kind === NEW)) {
        // cell changes could have been made in new rules or columns
        this.cellItems.set(k, { id: k, a: v, b: other, type: ConflictType.CELL_IN_NEW });
      } else if (other && this.isConflictDiff(v, other) && !this.cellItems.has(k)) {
        // make sure there is no other col/cell or rule/cell conflict first
        this.cellItems.set(k, { id: k, a: v, b: other, type: ConflictType.CELL });
      }
    });

    if (this.d1.getETCellItems()) {
      this.d1.getETCellItems().forEach((v, k) => {
        const ids = this.getIds(k);
        const colItem = this.d2.getETColumnItem(ids.colId);
        const ruleItem = this.d2.getETRuleItem(ids.ruleId);
        const other = this.d2.getETCellItem(k);
        if (this.isDelete(colItem) && this.isDelete(ruleItem) && !this.isDelete(v)) {
          this.etCellItems.set(k, { id: k, a: v, b: colItem, type: ConflictType.CELL_BOTH });
          this.etColumnItems.set(ids.colId, { id: ids.colId, a: null, b: colItem, type: ConflictType.COLUMN });
          this.etRuleItems.set(ids.ruleId, { id: ids.ruleId, a: null, b: ruleItem, type: ConflictType.RULE });
        } else if (this.isDelete(colItem) && !this.isDelete(v)) {
          // the cell might belong to a deleted column
          this.etCellItems.set(k, { id: k, a: v, b: colItem, type: ConflictType.CELL_COLUMN });
          this.etColumnItems.set(ids.colId, { id: ids.colId, a: null, b: colItem, type: ConflictType.COLUMN });
        } else if (this.isDelete(ruleItem) && !this.isDelete(v)) {
          // the cell might belong to a deleted rule
          this.etCellItems.set(k, { id: k, a: v, b: ruleItem, type: ConflictType.CELL_RULE });
          this.etRuleItems.set(ids.ruleId, { id: ids.ruleId, a: null, b: ruleItem, type: ConflictType.RULE });
        } else if (other && this.isConflictDiff(v, other) && (colItem && colItem.kind === NEW || ruleItem && ruleItem.kind === NEW)) {
          // cell changes could have been made in new rules or columns
          this.etCellItems.set(k, { id: k, a: v, b: other, type: ConflictType.CELL_IN_NEW });
        } else if (other && this.isConflictDiff(v, other) && !this.etCellItems.has(k)) {
          // make sure there is no other col/cell or rule/cell conflict first
          this.etCellItems.set(k, { id: k, a: v, b: other, type: ConflictType.CELL });
        }
      });
    }
  }

  private isConflictDiff(a: DiffItem, b: DiffItem) {
    if (_.isEqual(a.lhs, b.lhs) && a.kind === b.kind && a.type === b.type) {
      if (a.kind === DELETE) {
        return false;
      } else {
        switch (a.type) {
          case RULE:
            return a.kind === NEW || !_.isEqual(a.rhs, b.rhs);
          case COLUMN:
            return !_.isEqual(a.rhs, b.rhs);
          case CELL:
            return a.rhs.getExpr() !== b.rhs.getExpr();
          case PROP:
          case META:
            return !_.isEqual(a.rhs, b.rhs);
        }
      }
    }
    return true;
  }

  private isDelete(diffItem: DiffItem) {
    return diffItem && diffItem.kind === DELETE;
  }

  private getIds(cellId: string) {
    const parts = cellId.split('_');
    return {
      ruleId: parts[0],
      colId: parts[1]
    };
  }
}
