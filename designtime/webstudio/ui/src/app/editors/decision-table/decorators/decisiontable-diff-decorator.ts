import { DiffResult } from './../../../editables/decision-table/differ/diff-result';
import { CellClassRules, DecisionTableDecorator, LabelClasses } from './decisiontable-decorator';
import { DecisionTableRegularDecorator } from './decisiontable-regular-decorator';

import { BEDecisionTable } from '../../../editables/decision-table/be-decision-table';
import { Column } from '../../../editables/decision-table/column';
import { DecisionTable } from '../../../editables/decision-table/decision-table';
import { DELETE, EDIT, NEW } from '../../../editables/decision-table/differ/differ';
import { Rule } from '../../../editables/decision-table/rule';
import { EditBuffer } from '../../../editables/edit-buffer';

export class DecisionTableDiffDecorator extends DecisionTableRegularDecorator implements DecisionTableDecorator {
  private rows: Rule[];
  private columns: Column[];
  private ETrows: Rule[];
  private ETcolumns: Column[];
  private lhs: any;

  constructor(
    private diffResult: DiffResult,
    table: EditBuffer<DecisionTable>,
    protected editable: boolean
  ) {
    super(table, editable);
    this.lhs = this.diffResult.getLhs();
    this.refresh();
  }

  refresh() {
    this.rows = [];
    this.columns = [];
    this.ETrows = [];
    this.ETcolumns = [];
    const deletedRules = this.lhs.getRules()
      .filter(r => {
        // find out rules missing in current buffer
        const ruleItem = this.diffResult.getRuleItem(r.getId());
        return ruleItem && ruleItem.kind === DELETE;
      })
      .reduce((rules, rule) => rules.set(rule.getId(), rule), new Map<string, Rule>());
    this.rows = this.table.getContent().getRules().concat(Array.from(deletedRules.values()));
    const deletedColumns = this.lhs.getColumns()
      .filter(col => {
        const colItem = this.diffResult.getColumnItem(col.getId());
        return colItem && colItem.kind === DELETE;
      })
      .reduce((cols, col) => cols.set(col.getId(), col), new Map<string, Column>());
    this.columns = this.table.getContent().getColumns().concat(Array.from(deletedColumns.values()));

    // Exception table
    if (this.lhs instanceof BEDecisionTable) {
      const deletedETRules = this.lhs.getETRules()
        .filter(r => {
          // find out rules missing in current buffer
          const ruleItem = this.diffResult.getETRuleItem(r.getId());
          return ruleItem && ruleItem.kind === DELETE;
        })
        .reduce((rules, rule) => rules.set(rule.getId(), rule), new Map<string, Rule>());
      this.ETrows = this.table.getContent().getETRules().concat(Array.from(deletedETRules.values()));
      const deletedETColumns = this.lhs.getETColumns()
        .filter(col => {
          const colItem = this.diffResult.getETColumnItem(col.getId());
          return colItem && colItem.kind === DELETE;
        })
        .reduce((cols, col) => cols.set(col.getId(), col), new Map<string, Column>());
      this.ETcolumns = this.table.getContent().getETColumns().concat(Array.from(deletedETColumns.values()));
    }
  }

  getRows() {
    return this.rows;
  }

  getColumns() {
    return this.columns;
  }

  getETRows() {
    return this.ETrows;
  }

  getETColumns() {
    return this.ETcolumns;
  }

  getColHeaderClass(colId: string) {
    const item = this.diffResult.getColumnItem(colId);
    if (item) {
      if (this.getDiffClassByKind(item.kind) === 'diff-new') {
        return 'ws-diff-background-add';
      } else if (this.getDiffClassByKind(item.kind) === 'diff-edit') {
        return 'ws-diff-background-modify';
      } else if (this.getDiffClassByKind(item.kind) === 'diff-delete') {
        return 'ws-diff-background-delete';
      }
    } else {
      return '';
    }
  }

  getCellRenderer(col: Column) {
    return (params) => {
      const colDef: any = params.colDef;
      if (!colDef) {
        return params.value;
      }
      if (colDef.headerName === 'ID') {
        return params.value;
      } else {
        const cellItem = this.getDiffItem(params);
        const ruleItem = this.getRuleItem(params);
        const colItem = this.getColumnItem(params);
        return this.getCellTemplateByDiffItems(params.value, cellItem, ruleItem, colItem);
      }
    };
  }

  getETCellRenderer(col: Column) {
    return (params) => {
      const colDef: any = params.colDef;
      if (colDef.headerName === 'ID') {
        return params.value;
      } else {
        const cellItem = this.getETDiffItem(params);
        const ruleItem = this.getETRuleItem(params);
        const colItem = this.getETColumnItem(params);
        return this.getCellTemplateByDiffItems(params.value, cellItem, ruleItem, colItem);
      }
    };
  }

  getCellClassRules(col: Column) {
    return <CellClassRules>{
      'ws-diff-background-modify': (params) => {
        return this.getDiffClassByParams(params) === 'diff-edit';
      },
      'ws-diff-background-add': (params) => {
        return this.getDiffClassByParams(params) === 'diff-new';
      },
      'ws-diff-background-delete': (params) => {
        return this.getDiffClassByParams(params) === 'diff-delete';
      },
    };
  }

  getETCellClassRules() {
    return <CellClassRules>{
      'ws-diff-background-modify': (params) => {
        return this.getETDiffClassByParams(params) === 'diff-edit';
      },
      'ws-diff-background-add': (params) => {
        return this.getETDiffClassByParams(params) === 'diff-new';
      },
      'ws-diff-background-delete': (params) => {
        return this.getETDiffClassByParams(params) === 'diff-delete';
      },
    };
  }

  getCellEditor(col: Column) {
    return super.getCellEditor(col);
  }

  getCellEditable(col: Column) {
    return (params) => {
      const ruleItem = this.getRuleItemById(params.node.data.getId());
      const colItem = this.getColumnItem(params);
      const deleted: boolean = this.isDeletedCell(ruleItem, colItem);
      return this.editable && !deleted;
    };
  }

  getPropertyLabelClasses(prop: string): LabelClasses {
    const item = this.diffResult.getPropItem(prop);
    const classes = super.getPropertyLabelClasses(prop);
    classes['diff-new'] = item && item.kind === NEW;
    classes['diff-edit'] = item && item.kind === EDIT;
    classes['diff-delete'] = item && item.kind === DELETE;
    return classes;
  }

  getPropertyDecorationText(prop: string) {
    const item = this.diffResult.getPropItem(prop);
    if (item && item.kind !== NEW) {
      return `was ${item.lhs}`;
    } else {
      return '';
    }
  }

  private getDiffClassByParams(params) {
    const ruleItem = this.getRuleItem(params);
    const colItem = this.getColumnItem(params);
    const cellItem = this.getDiffItem(params);
    return this.getDiffClassByItems(cellItem, ruleItem, colItem);
  }

  private getETDiffClassByParams(params) {
    const ruleItem = this.getETRuleItem(params);
    const colItem = this.getETColumnItem(params);
    const cellItem = this.getETDiffItem(params);
    return this.getDiffClassByItems(cellItem, ruleItem, colItem);
  }

  private getDiffItem(params) {
    if (this.diffResult && params.data) {
      return this.diffResult.getCellItem(this.getCellId(params));
    } else {
      return null;
    }
  }

  private getRuleItem(params) {
    if (this.diffResult) {
      return this.diffResult.getRuleItem(this.getRuleId(params));
    }
  }

  private getColumnItem(params) {
    if (this.diffResult) {
      return this.diffResult.getColumnItem(this.getColId(params));
    }
  }

  private getRuleItemById(id: string) {
    if (this.diffResult) {
      return this.diffResult.getRuleItem(id);
    }
  }

  // Exception table
  private getETDiffItem(params) {
    if (this.diffResult && params.data) {
      return this.diffResult.getETCellItem(this.getCellId(params));
    } else {
      return null;
    }
  }

  private getETRuleItem(params) {
    if (this.diffResult) {
      return this.diffResult.getETRuleItem(this.getRuleId(params));
    }
  }

  private getETColumnItem(params) {
    if (this.diffResult) {
      return this.diffResult.getETColumnItem(this.getColId(params));
    }
  }

  private getETRuleItemById(id: string) {
    if (this.diffResult) {
      return this.diffResult.getETRuleItem(id);
    }
  }

}
