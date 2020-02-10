import { Logger } from '../../../core/logger.service';
import { Cell } from '../../../editables/decision-table/cell';
import * as differ from '../../../editables/decision-table/differ/differ';
import { Rule } from '../../../editables/decision-table/rule';

export class DecisionTableDecoratorBase {

  protected log: Logger = new Logger();

  getCellId(params): string {
    return this.getRuleId(params) + '_' + this.getColId(params);
  }

  getRuleId(params): string {
    const rule: Rule = params.data;
    if (rule) {
      return rule.getId();
    } else {
      return null;
    }
  }

  getColId(params): string {
    const col: any = params.colDef;
    if (this.isIdColumn(params)) {
      return '-1';
    } else {
      return col.colId;
    }
  }

  getIds(cellId: string) {
    const parts = cellId.split('_');
    return {
      ruleId: parts[0],
      colId: parts[1]
    };
  }

  safe(s: any) {
    if (!s) {
      return '';
    }

    if (typeof s !== 'string') {
      return s.toString();
    }
    return s;
  }

  safeExpr(cell: Cell) {
    return cell ? this.safe(cell.getExpr()) : '';
  }

  getCellTemplateByDiffItems(val: string, cellItem: differ.DiffItem, ruleItem: differ.DiffItem, colItem: differ.DiffItem) {
    let deleted: boolean;
    let ret: string;
    if (ruleItem && ruleItem.kind === differ.DELETE) {
      deleted = true;
    } else if (colItem && colItem.kind === differ.DELETE) {
      deleted = true;
    } else {
      deleted = false;
    }
    if (cellItem) {
      switch (cellItem.kind) {
        case differ.NEW:
          // rule or column deletion overrides cell editing
          if (deleted) {
            ret = '';
          } else {
            ret = this.safe(cellItem.rhs.getExpr());
          }
          break;
        case differ.EDIT:
          // rule or column deletion overrides cell editing
          if (deleted) {
            ret = '';
          } else {
            // temporary change
            // ret = `${this.safe(cellItem.rhs.getExpr())} <span class='pull-right diff-oldvalue'><i>was ${cellItem.lhs.getExpr()}</i></span>`;
            ret = `${this.safe(val)} <span class='pull-right diff-oldvalue'><i>was ${cellItem.lhs.getExpr()}</i></span>`;
          }
          break;
        case differ.DELETE:
          ret = `<span class='pull-right diff-oldvalue'><i>was ${cellItem.lhs.getExpr()}</i></span>`;
          break;
      }
    } else if (deleted) {
      ret = '';
    } else {
      ret = this.safe(val);
    }
    let tooltip = this.safe(val) as string;
    if (!tooltip.startsWith('"')) {
      tooltip = tooltip.replace(/"/g, '&quot;');
      tooltip = '"'.concat(tooltip).concat('"');
    }

    tooltip = `${tooltip}`;
    return `<span title=${tooltip}>${ret}</span>`;
  }

  getDiffClassByItems(cellItem: differ.DiffItem, ruleItem: differ.DiffItem, colItem: differ.DiffItem) {
    if (this.isDeletedCell(ruleItem, colItem)) {
      return 'diff-delete';
    } else if (this.isCreatedCell(ruleItem, colItem)) {
      return 'diff-new';
    } else if (cellItem) {
      return this.getDiffClassByKind(cellItem.kind, true);
    } else {
      return '';
    }
  }

  isDeletedCell(ruleItem: differ.DiffItem, colItem: differ.DiffItem) {
    return ((ruleItem && ruleItem.kind === differ.DELETE) || (colItem && colItem.kind === differ.DELETE));
  }
  isCreatedCell(ruleItem: differ.DiffItem, colItem: differ.DiffItem) {
    return ((ruleItem && ruleItem.kind === differ.NEW) || (colItem && colItem.kind === differ.NEW));
  }

  getDiffClassByKind(kind: string, deleteAsEdit?: boolean) {
    switch (kind) {
      case differ.NEW:
        return 'diff-new';
      case differ.EDIT:
        return 'diff-edit';
      case differ.DELETE:
        return deleteAsEdit ? 'diff-edit' : 'diff-delete';
    }
  }

  isIdColumn(params) {
    const col: any = params.colDef;
    return (col.headerName === 'ID');
  }
}
