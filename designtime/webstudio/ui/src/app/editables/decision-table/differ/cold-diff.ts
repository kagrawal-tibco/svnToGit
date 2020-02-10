import { I18n } from '@ngx-translate/i18n-polyfill';

import { DiffResult } from './diff-result';
import { CELL, COLUMN, DiffItem, DELETE, EDIT, META, NEW, PROP, RULE } from './differ';

import { Logger } from '../../../core/logger.service';
import { Rule } from '../rule';

const log = new Logger();

export class ColdDiff implements DiffResult {

  // cell id to diff item
  private cellDiff = new Map<string, DiffItem>();
  // ET cell id to diff item
  private etCellDiff = new Map<string, DiffItem>();
  // prop name to diff item
  private propDiff = new Map<string, DiffItem>();
  // rule id to diff item
  private ruleDiff = new Map<string, DiffItem>();
  // ET rule id to diff item
  private etRuleDiff = new Map<string, DiffItem>();
  // meta name to diff item
  private metaDiff = new Map<string, DiffItem>();
  // col id to diff item
  private colDiff = new Map<string, DiffItem>();
  // ET col id to diff item
  private etColDiff = new Map<string, DiffItem>();

  constructor(
    public a: any,
    public b: any,
    public raw: any[],
    public i18n: I18n,
  ) {
    this.init();
  }

  getCellItems() {
    return this.cellDiff;
  }

  getETCellItems() {
    return this.etCellDiff;
  }

  setCellItems(items: Map<string, DiffItem>) {
    this.cellDiff = items;
  }

  setETCellItems(items: Map<string, DiffItem>) {
    this.etCellDiff = items;
  }

  getCellItem(id: string): DiffItem {
    return this.cellDiff.get(id);
  }

  getETCellItem(id: string): DiffItem {
    return this.etCellDiff.get(id);
  }

  getPropItems() {
    return this.propDiff;
  }

  setPropItems(items: Map<string, DiffItem>) {
    this.propDiff = items;
  }

  getPropItem(propName: string): DiffItem {
    return this.propDiff.get(propName);
  }

  getMetaItems() {
    return this.metaDiff;
  }

  setMetaItems(items: Map<string, DiffItem>) {
    this.metaDiff = items;
  }

  getMetaItem(metaName: string): DiffItem {
    return this.metaDiff.get(metaName);
  }

  getRuleItems() {
    return this.ruleDiff;
  }

  getETRuleItems() {
    return this.etRuleDiff;
  }

  getRuleItem(ruleId: string): DiffItem {
    return this.ruleDiff.get(ruleId);
  }

  getETRuleItem(ruleId: string): DiffItem {
    return this.etRuleDiff.get(ruleId);
  }

  setRuleItems(items: Map<string, DiffItem>) {
    this.ruleDiff = items;
  }

  setETRuleItems(items: Map<string, DiffItem>) {
    this.etRuleDiff = items;
  }

  getColumnItems() {
    return this.colDiff;
  }

  getETColumnItems() {
    return this.etColDiff;
  }

  setETColumnItems(items: Map<string, DiffItem>) {
    this.etColDiff = items;
  }

  setColumnItems(items: Map<string, DiffItem>) {
    this.colDiff = items;
  }

  getColumnItem(colId: string): DiffItem {
    return this.colDiff.get(colId);
  }

  getETColumnItem(colId: string): DiffItem {
    return this.etColDiff.get(colId);
  }

  getLhs() {
    return this.a;
  }

  getRhs() {
    return this.b;
  }

  size(): number {
    return this.colDiff.size + this.propDiff.size + this.cellDiff.size + this.metaDiff.size + this.ruleDiff.size;
  }

  private init() {
    if (this.raw) {
      this.raw.forEach(record => {
        const path = record['path'];
        if (path[0] === 'rulesObj') {
          if (path.length >= 4) {
            this.processCellDiff(record);
          } else {
            this.processRuleDiff(record);
          }
        } else if (path[0] === 'rulesObjET') {
          if (path.length >= 4) {
            this.processETCellDiff(record);
          } else {
            this.processETRuleDiff(record);
          }
        } else if (path[0] === 'propertiesObj') {
          this.processPropDiff(record);
        } else if (path[0] === 'columnsObj') {
          this.processColumnDiff(record);
        } else if (path[0] === 'columnsObjET') {
          this.processETColumnDiff(record);
        } else if (path.length === 1) {
          this.processMetaDiff(record);
        } else {
          log.warn(this.i18n('Unrecognized path:') + JSON.stringify(path));
        }
      });
    }
  }

  private processMetaDiff(record: any) {
    const path = record['path'];
    const meta = path[0];
    const lhs = record['lhs'];
    const rhs = record['rhs'];
    const kind = record['kind'];
    this.metaDiff.set(meta, {
      kind: kind,
      lhs: lhs,
      rhs: rhs,
      type: META,
    });
  }

  private processRuleDiff(record: any) {
    const lhs: Rule = record['lhs'];
    const rhs: Rule = record['rhs'];
    const kind = record['kind'];
    switch (kind) {
      case NEW:
        // New cells
        this.ruleDiff.set(rhs.getId(), { kind: NEW, rhs: rhs, type: RULE });
        rhs.getCells().forEach(c => this.cellDiff.set(c.getId(), { kind: NEW, rhs: c, type: CELL }));
        return;
      case DELETE:
        // Delete cells
        this.ruleDiff.set(lhs.getId(), { kind: DELETE, lhs: lhs, type: RULE });
        lhs.getCells().forEach(c => this.cellDiff.set(c.getId(), { kind: DELETE, lhs: c, type: CELL }));
        return;
      case EDIT:
        log.warn(this.i18n('Should not go into this path, something is wrong:'), record);
        return null;
    }
  }

  private processETRuleDiff(record: any) {
    const lhs: Rule = record['lhs'];
    const rhs: Rule = record['rhs'];
    const kind = record['kind'];
    switch (kind) {
      case NEW:
        // New cells
        this.etRuleDiff.set(rhs.getId(), { kind: NEW, rhs: rhs, type: RULE });
        rhs.getCells().forEach(c => this.etCellDiff.set(c.getId(), { kind: NEW, rhs: c, type: CELL }));
        return;
      case DELETE:
        // Delete cells
        this.etRuleDiff.set(lhs.getId(), { kind: DELETE, lhs: lhs, type: RULE });
        lhs.getCells().forEach(c => this.etCellDiff.set(c.getId(), { kind: DELETE, lhs: c, type: CELL }));
        return;
      case EDIT:
        log.warn(this.i18n('Should not go into this path, something is wrong:'), record);
        return null;
    }
  }

  private processColumnDiff(record: any) {
    const path = record['path'];
    const colId = path[1];
    const lhs = record['lhs'];
    const rhs = record['rhs'];
    const kind = record['kind'];
    switch (kind) {
      case NEW:
        this.colDiff.set(colId, { kind: kind, rhs: rhs, type: COLUMN });
        break;
      case DELETE:
        this.colDiff.set(colId, { kind: kind, lhs: lhs, type: COLUMN });
        break;
      case EDIT:
        this.colDiff.set(colId, {
          kind: kind,
          lhs: this.a.getColumn(colId),
          rhs: this.b.getColumn(colId),
          type: COLUMN,
        });
        break;
    }
  }

  private processETColumnDiff(record: any) {
    const path = record['path'];
    const colId = path[1];
    const lhs = record['lhs'];
    const rhs = record['rhs'];
    const kind = record['kind'];
    switch (kind) {
      case NEW:
        this.etColDiff.set(colId, { kind: kind, rhs: rhs, type: COLUMN });
        break;
      case DELETE:
        this.etColDiff.set(colId, { kind: kind, lhs: lhs, type: COLUMN });
        break;
      case EDIT:
        this.etColDiff.set(colId, {
          kind: kind,
          lhs: this.a.getETColumn(colId),
          rhs: this.b.getETColumn(colId),
          type: COLUMN,
        });
        break;
    }
  }

  private processCellDiff(record: any) {
    const path = record['path'];
    const lhs = record['lhs'];
    const rhs = record['rhs'];
    const kind = record['kind'];

    const ruleId = path[1];
    const colId = path[3];
    const cellId = `${ruleId}_${colId}`;
    if (kind === EDIT) {
      const last = path[path.length - 1];
      if (last === '_expr') {
        // Addtional process based on kind
        let item: DiffItem;
        if (lhs && !rhs) {
          item = {
            kind: DELETE,
            lhs: this.a.getCell(cellId),
            type: CELL,
          };
        } else if (!lhs && rhs) {
          item = {
            kind: NEW,
            type: CELL,
            rhs: this.b.getCell(cellId)
          };
        } else {
          item = {
            kind: kind,
            type: CELL,
            lhs: this.a.getCell(cellId),
            rhs: this.b.getCell(cellId)
          };
        }
        this.cellDiff.set(cellId, item);
      } else {
        if (last === '_comment' || last === '_disabled') {
          console.log(this.i18n('Comment or disabled related path'));
        } else {
          throw this.i18n('Unrecognized path: ') + path;
        }
      }
    } else if (kind === NEW) {
      const rhsExpr = rhs['_expr'];
      if (rhsExpr) {
        this.cellDiff.set(cellId, { kind: kind, rhs: this.b.getCell(cellId), type: CELL });
      } else {
        // NOOP as the new value is empty
      }
    } else if (kind === DELETE) {
      const lhsExpr = lhs['_expr'];
      if (lhsExpr) {
        this.cellDiff.set(cellId, { kind: kind, lhs: this.a.getCell(cellId), type: CELL });
      }
    }
  }

  private processETCellDiff(record: any) {
    const path = record['path'];
    const lhs = record['lhs'];
    const rhs = record['rhs'];
    const kind = record['kind'];

    const ruleId = path[1];
    const colId = path[3];
    const cellId = `${ruleId}_${colId}`;
    if (kind === EDIT) {
      const last = path[path.length - 1];
      if (last === '_expr') {
        // Addtional process based on kind
        let item: DiffItem;
        if (lhs && !rhs) {
          item = {
            kind: DELETE,
            lhs: this.a.getETCell(cellId),
            type: CELL,
          };
        } else if (!lhs && rhs) {
          item = {
            kind: NEW,
            type: CELL,
            rhs: this.b.getETCell(cellId)
          };
        } else {
          item = {
            kind: kind,
            type: CELL,
            lhs: this.a.getETCell(cellId),
            rhs: this.b.getETCell(cellId)
          };
        }
        this.etCellDiff.set(cellId, item);
      } else {
        if (last === '_comment' || last === '_disabled') {
          console.log(this.i18n('Comment or disabled related path'));
        } else {
          throw this.i18n('Unrecognized path: ') + path;
        }
      }
    } else if (kind === NEW) {
      const rhsExpr = rhs['_expr'];
      if (rhsExpr) {
        this.etCellDiff.set(cellId, { kind: kind, rhs: this.b.getETCell(cellId), type: CELL });
      } else {
        // NOOP as the new value is empty
      }
    } else if (kind === DELETE) {
      const lhsExpr = lhs['_expr'];
      if (lhsExpr) {
        this.etCellDiff.set(cellId, { kind: kind, lhs: this.a.getETCell(cellId), type: CELL });
      }
    }
  }

  private processPropDiff(record: any) {
    const kind = record['kind'];
    const lhs = record['lhs'];
    const rhs = record['rhs'];
    const path = record['path'];
    const propName = path[1];
    if (propName === 'Version') {
      // we ignore Version field
      return;
    }
    if (kind === EDIT) {
      let item: DiffItem;
      if (lhs && !rhs) {
        item = {
          kind: DELETE,
          lhs: lhs,
          type: PROP,
        };
      } else if (!lhs && rhs) {
        item = {
          kind: NEW,
          type: PROP,
          rhs: rhs
        };
      } else {
        item = {
          kind: EDIT,
          type: PROP,
          lhs: lhs,
          rhs: rhs
        };
      }
      this.propDiff.set(propName, item);
    } else if (kind === NEW) {
      const val = rhs.val;
      if (val) {
        if (rhs.type === 'Boolean') {
          this.propDiff.set(propName, { kind: EDIT, lhs: 'false', rhs: val, type: PROP });
        } else {
          this.propDiff.set(propName, { kind: NEW, rhs: val, type: PROP });
        }
      } else {
        // New property but no actual value, just noop
      }
    } else if (kind === DELETE) {
      const val = lhs.val;
      if (lhs) {
        this.propDiff.set(propName, { kind: DELETE, lhs: val, type: PROP });
      } else {
        // Property deleted but the previous one is empty value, just noop
      }
    }
  }
}
