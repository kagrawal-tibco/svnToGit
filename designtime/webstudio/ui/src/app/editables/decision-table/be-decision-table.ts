import { Cell } from './cell';
import { Column, ColumnType, PropertyType } from './column';
import { DecisionTable } from './decision-table';
import { Property } from './property';
import { Rule } from './rule';

import { Argument } from '../../models-be/decision-table-be/arguments-be';
import { DecisionTableSave } from '../../models-be/decision-table-be/decision-table-be-save';
import { BEDecisionTableUtil } from '../../models-be/decision-table-be/decision-table-util';
import { I18nImpl } from '../../widgets/i18n-impl';
export const RULE_PRIORITY = 'PRIORITY';

export class BEDecisionTable extends DecisionTable {
  public projectName: string;
  public artifactPath: string;
  public implementsPath: string;
  public isSyncMerge: string;
  public rulesObjET: Object = {};
  public columnsObjET: Object = {};
  public argumentObj: Object = {};

  public decisionTableCurrentPage: string;
  public decisionTableTotalPages: string;
  public exceptionTableTotalPages: string;
  public decisionTableLastRuleId: string;
  public exceptionTableLastRuleId: string;
  public decisionTableSinglePageView: string;

  static fromXml(dtContent: string): BEDecisionTable {
    const priorityText = I18nImpl.getPriorityText();
    if (dtContent === '') {
      return new BEDecisionTable('', '', [], priorityText);
    }
    const jsonObject = JSON.parse(dtContent);
    const name = jsonObject.tableName ? jsonObject.tableName : '';
    const folder = jsonObject.folderPath ? jsonObject.folderPath : '';

    const dt = new BEDecisionTable('', '', [], priorityText);
    dt.projectName = jsonObject.projectName;
    dt.artifactPath = jsonObject.artifactPath;
    dt.implementsPath = jsonObject.implementsPath;
    dt.isSyncMerge = jsonObject.isSyncMerge ? jsonObject.isSyncMerge : 'false';

    // Table argument data
    if (jsonObject.arguments.argument) {
      for (const arg of jsonObject.arguments.argument) {
        const argument = new Argument(arg.argumentAlias, arg.direction, arg.isArray, arg.path, arg.resourceType);
        dt.argumentObj[argument.argumentAlias] = argument;
      }
    }

    // Table Meta data
    if (jsonObject.metadata.property) {
      for (const prop of jsonObject.metadata.property) {
        dt.addProperty(new Property(prop.name, prop.value, prop.type));
      }
    }

    // Decision table column definition
    if (jsonObject.decisionTableColumns.column) {
      for (const col of jsonObject.decisionTableColumns.column) {
        const column = Column.createColumn(col.columnId, col.name, String(col.propertyType), col.columnType, col.property, col.isArrayProperty, col.isSubstitution, col.associatedDM ? col.associatedDM : false, (col.columnAlias === '') ? undefined : col.columnAlias, col.defaultCellText !== 'null' ? col.defaultCellText : '');
        dt.columnsObj[column.id] = column;
      }
    }

    // Decision table rules creation
    if (jsonObject.decisionTablePage.tableRule) {
      dt.decisionTableCurrentPage = jsonObject.decisionTablePage.pageNumber;
      for (const rule of jsonObject.decisionTablePage.tableRule) {
        const ruleId = rule.ruleId;
        let priority: number;
        let commentR = '';
        if (rule.metadata && rule.metadata.property) {
          for (const prop of rule.metadata.property) {
            if (prop.name === 'Priority') {
              priority = Number(prop.value);
            } else if (prop.name === 'Description') {
              commentR = prop.value;
            }
          }
        }

        const newRule = new Rule(ruleId, commentR ? commentR : '', priority ? priority : 5);
        dt.setRule(newRule);

        if (rule.condition) {
          for (const condition of rule.condition) {
            const col = dt.getColumn(condition.columnId);
            if (col) {
              newRule.setCell(condition.columnId, condition.expression, col.columnType.cellType, condition.isEnabled === 'false' || condition.isEnabled === false ? true : false, condition.comment ? condition.comment : '');
            }
          }
        }
        if (rule.action) {
          for (const action of rule.action) {
            const col = dt.getColumn(action.columnId);
            if (col && col.id !== 'PRIORITY') {
              newRule.setCell(action.columnId, action.expression, col.columnType.cellType, action.isEnabled === 'false' || action.isEnabled === false ? true : false, action.comment ? action.comment : '');
            }
          }
        }
      }
    }

    // Exception table column definition
    const priorityColumn = new Column(RULE_PRIORITY, 'Priority', PropertyType.INT, ColumnType.PROPERTY);
    dt.columnsObjET[priorityColumn.id] = priorityColumn;
    if (jsonObject.exceptionTableColumns.column) {
      for (const col of jsonObject.exceptionTableColumns.column) {
        const column = Column.createColumn(col.columnId, col.name, col.propertyType, col.columnType, col.property, col.isArrayProperty, col.isSubstitution, col.associatedDM ? col.associatedDM : false, (col.columnAlias === '') ? undefined : col.columnAlias, col.defaultCellText !== 'null' ? col.defaultCellText : '');
        dt.columnsObjET[column.id] = column;
      }
    }

    // Exception table rules creation
    if (jsonObject.exceptionTablePage.tableRule) {
      for (const rule of jsonObject.exceptionTablePage.tableRule) {
        const ruleId = rule.ruleId;
        let priority: number;
        let commentR = '';

        if (rule.metadata && rule.metadata.property) {
          for (const prop of rule.metadata.property) {
            if (prop.name === 'Priority') {
              priority = Number(prop.value);
            } else if (prop.name === 'Description') {
              commentR = prop.value;
            }
          }
        }

        const newRule = new Rule(ruleId, commentR ? commentR : '', priority ? priority : 5);
        dt.rulesObjET[newRule.getId()] = newRule;
        if (rule.condition) {
          for (const condition of rule.condition) {
            const col = dt.columnsObjET[condition.columnId];
            if (col) {
              newRule.setCell(condition.columnId, condition.expression, col.columnType.cellType, condition.isEnabled === 'false' || condition.isEnabled === false ? true : false, condition.comment ? condition.comment : '');
            }
          }
        }
        if (rule.action) {
          for (const action of rule.action) {
            const col = dt.columnsObjET[action.columnId];
            if (col) {
              newRule.setCell(action.columnId, action.expression, col.columnType.cellType, action.isEnabled === 'false' || action.isEnabled === false ? true : false, action.comment ? action.comment : '');
            }
          }
        }
      }
    }

    dt.decisionTableTotalPages = jsonObject.decisionTableTotalPages;
    dt.exceptionTableTotalPages = jsonObject.exceptionTableTotalPages;
    dt.decisionTableLastRuleId = jsonObject.decisionTableLastRuleId;
    dt.exceptionTableLastRuleId = jsonObject.exceptionTableLastRuleId;
    dt.decisionTableSinglePageView = jsonObject.decisionTableSinglePageView;

    return dt;
  }

  getETRules(): Rule[] {
    const ret: Rule[] = [];
    for (const key in this.rulesObjET) {
      if (this.rulesObjET.hasOwnProperty(key)) {
        ret.push(this.rulesObjET[key]);
      }
    }
    return ret;
  }

  getETColumns(): Column[] {
    const ret: Column[] = [];
    Object.keys(this.columnsObjET).filter(id => id !== RULE_PRIORITY).forEach(id => ret.push(this.columnsObjET[id]));
    return ret.sort((a, b) => parseInt(a.id, 10) - parseInt(b.id, 10));
  }

  getETCell(cellId: string) {
    const parts = cellId.split('_');
    const ruleId = parts[0];
    const colId = parts[1];
    const rule = this.getETRule(ruleId);

    if (rule) {
      return rule.getCell(colId);
    } else {
      return null;
    }
  }

  setETCell(cellId: string, expr): Cell {
    const parts = cellId.split('_');
    const ruleId = parts[0];
    const colId = parts[1];
    const rule = this.getETRule(ruleId);
    const col = this.getETColumn(colId);

    if (rule && col) {
      return rule.setCell(colId, expr, col.columnType.cellType);
    } else {
      throw Error('cell ' + cellId + ' does not exist');
    }
  }

  setETCellCommentOrDisabled(cellId: string, expr: string, disabled: boolean, comment: string): Cell {
    const parts = cellId.split('_');
    const ruleId = parts[0];
    const colId = parts[1];
    const rule = this.getETRule(ruleId);
    const col = this.getETColumn(colId);

    if (rule && col) {
      return rule.setCellCommentOrDisabled(colId, expr, col.columnType.cellType, disabled, comment);
    } else {
      throw Error('cell ' + cellId + ' does not exist');
    }
  }

  getETRule(ruleId: string): Rule {
    return this.rulesObjET[ruleId];
  }

  getETColumn(colId: string): Column {
    return this.columnsObjET[colId];
  }

  addETColumn(col: Column): Column {
    const existed = this.columnsObjET[col.getId()];
    if (existed) {
      throw Error('Column with id ' + col.getId() + ' already exists');
    } else {
      return this.createAndAddETColumnWithId(col.getId(), col.name, col.propertyType, col.columnType, false, '', '', '', '');
    }
  }

  createAndAddETRule(id: string, priority?: number): Rule {
    const rule = new Rule(id, '', priority);
    this.setETRule(rule);
    return rule;
  }

  createETRuleWithAutoId(): Rule {
    const id = this.maxETRuleId() + 1;
    return this.createAndAddETRule(id.toString());
  }

  clearETRule(id: string) {
    delete this.rulesObjET[id];
  }

  clearETCell(cellId: string) {
    const parts = cellId.split('_');
    const ruleId = parts[0];
    const colId = parts[1];
    const rule = this.getETRule(ruleId);
    if (rule) {
      rule.clearCell(colId);
    } else {
      throw Error('cell ' + cellId + ' does not exist');
    }
  }

  setETRule(rule: Rule): Rule {
    this.rulesObjET[rule.getId()] = rule;
    return rule;
  }

  addETRule(rule: Rule): Rule {
    if (this.rulesObjET[rule.getId()]) {
      throw Error('Rule with id ' + rule.getId() + ' already exists');
    } else {
      this.rulesObjET[rule.getId()] = rule;
      return rule;
    }
  }

  hasETRule(id: string): boolean {
    return this.rulesObjET[id] !== undefined;
  }

  maxETRuleId(): number {
    return this.getETRules()
      .map(r => {
        const id = parseInt(r.getId(), 10);
        return isNaN(id) ? -1 : id;
      })
      .reduce((max, i) => max > i ? max : i, 0);
  }

  maxETColumnId(): number {
    return this.getETColumns()
      .map(col => {
        const id = parseInt(col.getId(), 10);
        return isNaN(id) ? -1 : id;
      })
      .reduce((max, i) => max > i ? max : i, 0);
  }

  createAndAddETColumn(name: string, propType: PropertyType, colType: ColumnType): Column {
    if (!(name && propType && colType)) {
      return null;
    }
    const cols = this.getETColumns();
    const id = cols
      .map(col => +col.id)
      .reduce((a, b) => a > b ? a : b, -1) + 1;
    const col = new Column(
      id.toString(),
      name,  // columnName
      propType,
      colType
    );
    this.columnsObjET[col.id] = col;
    return col;
  }

  createAndAddETColumnWithId(id: string, name: string, propType: PropertyType, colType: ColumnType, isDM: boolean, ownerPath: string, propName: string, alias: string, defaultCellText: string): Column {
    if (!(id && name && propType && colType)) {
      return null;
    }

    let isSubstitution = false;
    if (BEDecisionTableUtil.isSubstitutionField(name)) {
      isSubstitution = true;
    }

    const col = new Column(
      id.toString(),
      name,  // columnName
      propType,
      colType,
      ownerPath ? ownerPath + '/' + propName : propName,
      false,
      isSubstitution,
      isDM,
      alias,
      defaultCellText
    );
    this.columnsObjET[col.id] = col;
    return col;
  }

  clearETColumn(colId: string) {
    const found = this.columnsObjET[colId];
    if (found) {
      delete this.columnsObjET[colId];
      // clear the corresponding cells as well
      this.getETRules().forEach(r => r.clearCell(colId));
    } else {
      throw Error('Unable to find column with id: ' + colId);
    }
  }

  hasETColumn(colId: string): boolean {
    return this.columnsObjET[colId] != null;
  }

  clone(): BEDecisionTable {
    return BEDecisionTable.fromXml(this.toXml());
  }

  seralize() {
    return this.toXml();
  }

  toXml(): string {
    // console.log(this);

    const saveString = DecisionTableSave.dtObjectToJson(this);

    return saveString;
  }

  updateDTNextPage(artifactDetails: any) {
    // console.log(artifactDetails);
    // console.log(this.rulesObj);

    for (const key in this.rulesObj) {
      this.clearRule(this.rulesObj[key].getId());
    }

    if (artifactDetails.decisionTablePage.tableRule) {
      this.decisionTableCurrentPage = artifactDetails.decisionTablePage.pageNumber;
      this.decisionTableTotalPages = artifactDetails.decisionTableTotalPages;
      this.decisionTableLastRuleId = artifactDetails.decisionTableLastRuleId;
      this.decisionTableSinglePageView = artifactDetails.decisionTableSinglePageView;
      for (const rule of artifactDetails.decisionTablePage.tableRule) {
        const ruleId = rule.ruleId;
        let priority: number;
        let commentR = '';
        if (rule.metadata && rule.metadata.property) {
          for (const prop of rule.metadata.property) {
            if (prop.name === 'Priority') {
              priority = Number(prop.value);
            } else if (prop.name === 'Description') {
              commentR = prop.value;
            }
          }
        }

        const newRule = new Rule(ruleId, commentR ? commentR : '', priority ? priority : 5);
        // dt.setRule(newRule);
        this.rulesObj[newRule.getId()] = newRule;

        if (rule.condition) {
          for (const condition of rule.condition) {
            const col = this.columnsObj[condition.columnId];
            if (col) {
              newRule.setCell(condition.columnId, condition.expression, col.columnType.cellType, condition.isEnabled === 'false' || condition.isEnabled === false ? true : false, condition.comment ? condition.comment : '');
            }
          }
        }
        if (rule.action) {
          for (const action of rule.action) {
            const col = this.columnsObj[action.columnId];
            if (col) {
              newRule.setCell(action.columnId, action.expression, col.columnType.cellType, action.isEnabled === 'false' || action.isEnabled === false ? true : false, action.comment ? action.comment : '');
            }
          }
        }
      }
    }
  }
}
