import { Cell, CellType } from './cell';
import { RULE_PRIORITY } from './decision-table';
export class Rule {
  // colId to Cell, we use Object instead of Map because otherwise Diff
  // will not work
  private cellMap: Object = {};

  /**
   * Should not call this constructor directly.
   * Use facctory method DecisionTable#createAndAddRule instead.
   */
  constructor(
    private id: string,
    private ruleComment?: string,
    priority?: number
  ) {
    if (priority != null) {
      this.setCell(RULE_PRIORITY, priority.toString(), 'prop');
    }
  }

  /**
   * Create a cell for the rule or update the expr if it already exists
   */
  createOrUpdateCell(colId: string, expr: string, type: CellType, disabled?: boolean, comment?: string): Cell {
    disabled = disabled ? true : false;
    const found: Cell = this.cellMap[colId];
    if (found) {
      found.setExpr(expr);
      found.setDisabled(disabled);
      found.setComment(comment);
    } else {
      const cell = new Cell(this.id, colId, expr, type, disabled, comment);
      this.cellMap[colId] = cell;
      return cell;
    }
  }

  // set the value of the cell. If expr is empty/null, remove the cell from rule
  setCell(colId: string, expr: string, type: CellType, disabled?: boolean, comment?: string): Cell {
    if (expr && expr.trim()) {
      return this.createOrUpdateCell(colId, expr, type, disabled ? disabled : false, comment ? comment : '');
    } else {
      this.clearCell(colId);
    }
  }

  setCellCommentOrDisabled(colId: string, expr: string, type: CellType, disabled: boolean, comment: string): Cell {
    if (expr && expr.trim()) {
      return this.createOrUpdateCell(colId, expr, type, disabled ? disabled : false, comment ? comment : '');
    } else {
      this.clearCell(colId);
    }
  }

  setRuleComment(comment: string) {
    this.ruleComment = comment;
  }

  getRuleComment() {
    return this.ruleComment;
  }

  getId() {
    return this.id;
  }

  getCell(colId: string): Cell {
    return this.cellMap[colId];
  }

  getCells(): Cell[] {
    const ret = [];
    for (const k in this.cellMap) {
      if (this.cellMap.hasOwnProperty(k)) {
        ret.push(this.cellMap[k]);
      }
    }
    return ret;
  }

  isEmpty(): boolean {
    for (const k in this.cellMap) {
      if (this.cellMap.hasOwnProperty(k)) {
        const cell: Cell = this.cellMap[k];
        if (!cell.isEmpty()) {
          return false;
        }
      }
    }
    return true;
  }

  clearCell(colId: string) {
    delete this.cellMap[colId];
  }

  toXml(indent: number) {
    let priorityStr: string;
    const priorityCell = this.getCell(RULE_PRIORITY);
    if (priorityCell) {
      const spaces = ' '.repeat(indent);
      priorityStr = `
  ${spaces}<md>
  ${spaces}  <prop name="Priority" type="Integer" value="${priorityCell.getExpr()}"/>
  ${spaces}</md>`;
    } else {
      priorityStr = '';
    }
    const rule = `${' '.repeat(indent)}<rule id="${this.getId()}">${priorityStr}
${this.getCells()
        .filter(c => c.getColId() !== RULE_PRIORITY)
        .sort((c1, c2) => {
          if (c1.getType() === 'cond' && c2.getType() === 'act') {
            return -1;
          }
          if (c1.getType() === 'act' && c2.getType() === 'cond') {
            return 1;
          }
          return c1.getColId().localeCompare(c2.getColId());
        }
        )
        .map(c => c.toXml(indent + 2)).join('\n')}
${' '.repeat(indent)}</rule>`;
    return rule;
  }
}
