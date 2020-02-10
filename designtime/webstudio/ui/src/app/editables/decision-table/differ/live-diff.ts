import { DiffResult } from './diff-result';
import { CELL, COLUMN, DiffItem, DELETE, EDIT, META, NEW, PROP, RULE } from './differ';

export class LiveDiff implements DiffResult {

  constructor(
    public a: any,
    public b: any
  ) {
  }

  getMetaItem(metaName: string): DiffItem {
    const a = this.a[metaName];
    const b = this.b[metaName];
    if (a === b) {
      return null;
    } else {
      return {
        kind: EDIT,
        lhs: a,
        rhs: b,
        type: META,
      };
    }
  }

  getColumnItem(colId: string): DiffItem {
    const a = this.a.getColumn(colId);
    const b = this.b.getColumn(colId);
    if (a && !b) {
      return {
        kind: DELETE,
        lhs: a,
        type: COLUMN,
      };
    } else if (!a && b) {
      return {
        kind: NEW,
        type: COLUMN,
        rhs: b
      };
    } else if (JSON.stringify(a) !== JSON.stringify(b)) {
      return {
        kind: EDIT,
        type: COLUMN,
        lhs: a,
        rhs: b
      };
    } else {
      return null;
    }
  }

  getCellItem(id: string): DiffItem {
    const parts = id.split('_');
    const ruleId = parts[0];
    const colId = parts[1];

    const ruleA = this.a.getRule(ruleId);
    const ruleB = this.b.getRule(ruleId);

    const cellA = ruleA ? ruleA.getCell(colId) : null;
    const cellB = ruleB ? ruleB.getCell(colId) : null;

    const exprA = cellA ? cellA.getExpr() : '';
    const exprB = cellB ? cellB.getExpr() : '';

    if (exprA === exprB) {
      return null;
    } else if (!exprA) {
      return {
        kind: NEW,
        type: CELL,
        rhs: cellB
      };
    } else if (!exprB) {
      return {
        kind: DELETE,
        type: CELL,
        lhs: cellA,
      };
    } else {
      return {
        kind: EDIT,
        type: CELL,
        lhs: cellA,
        rhs: cellB
      };
    }
  }
  getPropItem(propName: string): DiffItem {
    const a = this.a.getProperty(propName);
    const b = this.b.getProperty(propName);
    if (!a && !b || a === b) {
      return null;
    } else if (!a) {
      return {
        kind: NEW,
        type: PROP,
        rhs: b
      };
    } else if (!b) {
      return {
        type: PROP,
        kind: DELETE,
        lhs: a
      };
    } else {
      return {
        kind: EDIT,
        type: PROP,
        lhs: a,
        rhs: b
      };
    }
  }

  getRuleItem(ruleId: string): DiffItem {
    const a = this.a.getRule(ruleId);
    const b = this.b.getRule(ruleId);
    if (!a && b) {
      return {
        kind: NEW,
        type: RULE,
        rhs: b
      };
    } else if (a && !b) {
      return {
        kind: DELETE,
        type: RULE,
        lhs: a
      };
    } else {
      // otherwise we don't care
      return null;
    }
  }

  // Exception table
  getETColumnItem(colId: string): DiffItem {
    const a = this.a.getETColumn(colId);
    const b = this.b.getETColumn(colId);
    if (a && !b) {
      return {
        kind: DELETE,
        lhs: a,
        type: COLUMN,
      };
    } else if (!a && b) {
      return {
        kind: NEW,
        type: COLUMN,
        rhs: b
      };
    } else if (JSON.stringify(a) !== JSON.stringify(b)) {
      return {
        kind: EDIT,
        type: COLUMN,
        lhs: a,
        rhs: b
      };
    } else {
      return null;
    }
  }

  getETCellItem(id: string): DiffItem {
    const parts = id.split('_');
    const ruleId = parts[0];
    const colId = parts[1];

    const ruleA = this.a.getETRule(ruleId);
    const ruleB = this.b.getETRule(ruleId);

    const cellA = ruleA ? ruleA.getCell(colId) : null;
    const cellB = ruleB ? ruleB.getCell(colId) : null;

    const exprA = cellA ? cellA.getExpr() : '';
    const exprB = cellB ? cellB.getExpr() : '';

    if (exprA === exprB) {
      return null;
    } else if (!exprA) {
      return {
        kind: NEW,
        type: CELL,
        rhs: cellB
      };
    } else if (!exprB) {
      return {
        kind: DELETE,
        type: CELL,
        lhs: cellA,
      };
    } else {
      return {
        kind: EDIT,
        type: CELL,
        lhs: cellA,
        rhs: cellB
      };
    }
  }

  getETRuleItem(ruleId: string): DiffItem {
    const a = this.a.getETRule(ruleId);
    const b = this.b.getETRule(ruleId);
    if (!a && b) {
      return {
        kind: NEW,
        type: RULE,
        rhs: b
      };
    } else if (a && !b) {
      return {
        kind: DELETE,
        type: RULE,
        lhs: a
      };
    } else {
      // otherwise we don't care
      return null;
    }
  }

  getLhs() {
    return this.a;
  }

  getRhs() {
    return this.b;
  }
}
