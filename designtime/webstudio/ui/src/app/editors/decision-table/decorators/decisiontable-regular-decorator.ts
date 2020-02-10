import { CellClassRules, DecisionTableDecorator, LabelClasses } from './decisiontable-decorator';
import { DecisionTableDecoratorBase } from './decisiontable-decorator-base';

import { environment } from '../../../../environments/environment';
import { Column, ColumnType, PropertyType } from '../../../editables/decision-table/column';
import { RULE_PRIORITY } from '../../../editables/decision-table/decision-table';
import { Rule } from '../../../editables/decision-table/rule';
import { EditBuffer } from '../../../editables/edit-buffer';

export class DecisionTableRegularDecorator extends DecisionTableDecoratorBase implements DecisionTableDecorator {

  coveredRules: Array<string> = new Array<string>();
  constructor(
    protected table: EditBuffer<any>,
    protected editable: boolean
  ) {
    super();
  }

  refresh() { }

  getRows() {
    return this.table.getContent().getRules();
  }

  getColumns() {
    return this.table.getContent().getColumns();
  }

  getETRows() {
    return this.table.getContent().getETRules();
  }

  getETColumns() {
    return this.table.getContent().getETColumns();
  }

  getCellValueGetter(col: Column) {
    return (params: any) => {
      const rule = <Rule>params.data;
      if (rule) {
        if (col) {
          const cell = rule.getCell(col.getId());
          return cell ? cell.getExpr() : '';
        } else {
          return rule.getId();
        }
      } else {
        return '';
      }
    };
  }

  getProperty(param: string) {
    return this.table.getContent().getProperty(param);
  }

  setProperty(param: string, val: string) {
    this.table.markForDirtyCheck();
    this.table.getContent().setProperty(param, val);
  }

  getColHeaderClass(col: string) {
    return '';
  }

  getCellRenderer(col: Column) {
    return (params) => {
      let tooltip = this.safe(params.value);
      if (!tooltip.startsWith('"')) {
        tooltip = '"'.concat(tooltip).concat('"');
      }
      tooltip = `${tooltip}`;
      tooltip = tooltip.replace(/</g, '/&lt;');
      tooltip = tooltip.replace(/>/g, '/&gt;');

      return `<span title=${tooltip}>${params.value}</span>`;
    };
  }

  getCellEditor(col: Column) {
    if (col.columnType === ColumnType.PROPERTY && col.getId() === RULE_PRIORITY) {
      if (environment.enableBEUI) {
        return {
          editor: 'agRichSelectCellEditor',
          params: { values: Array(10).fill('').map((_, i) => (i + 1).toString()) }
        };
      } else {
        return {
          editor: 'agRichSelectCellEditor',
          params: { values: Array(12).fill('').map((_, i) => i === 0 ? '' : (i - 1).toString()) }
        };
      }
    } else if ((col.columnType === ColumnType.CONDITION || col.columnType === ColumnType.ACTION) &&
      col.propertyType === PropertyType.BOOLEAN) {
      return {
        editor: 'agRichSelectCellEditor',
        params: { values: ['', 'true', 'false'] },
      };
    } else {
      return { editor: null, params: null };
    }
  }

  getCellEditable(col: Column) {
    return (params) => {
      const rule = <Rule>params.node.data;
      if (rule) {
        if (col) {
          const cell = rule.getCell(col.getId());
          return cell ? !cell.isDisabled() : true;
        } else {
          return true;
        }
      } else {
        return true;
      }
    };
  }

  getCellClassRules(col: Column) {
    if (environment.enableBEUI) {
      return <CellClassRules>{
        'diff-new': (params) => {
          return this.isCovered(params);
        },
        'ws-regular-background-disabled': (params) => {
          return this.isDisabledCell(params, col);
        }
      };
    } else {
      return null;
    }
  }

  getETCellClassRules(col: Column) {
    if (environment.enableBEUI) {
      return <CellClassRules>{
        'ws-regular-background-disabled': (params) => {
          return this.isDisabledCell(params, col);
        }
      };
    } else {
      return null;
    }
  }

  getETCellRenderer(col: Column) {
    return null;
  }

  getPropertyLabelClasses(prop: string) {
    return <LabelClasses>{
      'control-label': true,
      'control-label-normal': true,
      'prop-label': true,
    };
  }

  getPropertyDecorationText(prop: string) {
    return '';
  }

  getCellDoubleClickHandler() {
    return null;
  }

  getETCellDoubleClickHandler() {
    return null;
  }

  isPropertyLabelClickable(prop: string) {
    return false;
  }

  getPropertyLabelClickHandler(prop: string) {
    return null;
  }

  updateCoverageRules(ruleIds: string[]) {
    for (const id of ruleIds) {
      this.coveredRules.push(id);
    }
  }

  clearCoverageRules() {
    this.coveredRules.splice(0, this.coveredRules.length);
  }

  isCovered(paramsObject: any): boolean {
    if (this.coveredRules.length > 0) {
      if (this.coveredRules.indexOf(String(paramsObject.data.getId())) !== -1) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }

  }

  isDisabledCell(paramsObject: any, col: Column) {
    const rule = <Rule>paramsObject.node.data;
    if (rule) {
      if (col) {
        const cell = rule.getCell(col.getId());
        return cell ? cell.isDisabled() : false;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

}
