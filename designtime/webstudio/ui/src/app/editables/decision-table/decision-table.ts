import { Cell } from './cell';
import { Column, ColumnType, PropertyType } from './column';
import { Property } from './property';
import { Rule } from './rule';

import { BEDecisionTableUtil } from '../../models-be/decision-table-be/decision-table-util';

export const RULE_PRIORITY = 'PRIORITY';

export class DecisionTable {
  static SINGLE_EXEC_PARAM = 'SingleRowExecution';
  static PRIORITY_PARAM = 'Priority';
  static EXPIRY_TIME_PARAM = 'ExpiryDate';
  static EFFECTIVE_TIME_PARAM = 'EffectiveDate';
  static TIMESTAMP_AS_UTC = 'TimestampAsUTC';

  public rulesObj: Object = {};
  public propertiesObj: Object = {};
  public columnsObj: Object = {};

  constructor(
    public name: string,
    public basePath: string,
    _columns: Column[],
    priorityText?: string
  ) {
    _columns.forEach(col => {
      this.columnsObj[col.id] = col;
    });
    let priorityColumn;
    if (priorityText != null) {
      priorityColumn = new Column(RULE_PRIORITY, priorityText, PropertyType.INT, ColumnType.PROPERTY);
    } else {
      priorityColumn = new Column(RULE_PRIORITY, 'Priority', PropertyType.INT, ColumnType.PROPERTY);
    }
    this.columnsObj[priorityColumn.id] = priorityColumn;
  }

  static fromDom(dom: Element): DecisionTable {
    const name = dom.getAttribute('name');
    const folder = dom.getAttribute('folder');
    const dt = new DecisionTable(name, folder, []);
    // Table Meta data
    const tableMdDom = Array.from(dom.getElementsByTagName('md')).filter(e => e.parentElement === dom)[0];
    Array.from(tableMdDom.getElementsByTagName('prop')).forEach(p => {
      dt.addProperty(Property.fromDom(p));
    });

    const dtDom = Array.from(dom.getElementsByTagName('decisionTable')).filter(e => e.parentElement === dom)[0];

    // Column definition
    const columnDoms = dtDom.getElementsByTagName('column');
    Array.from(columnDoms).forEach((d, i) => {
      dt.createAndAddColumnFromDom(d);
    });

    const ruleDoms = dtDom.getElementsByTagName('rule');
    Array.from(ruleDoms).forEach(d => {
      dt.createAndAddRulefromDom(d);
    });
    return dt;
  }

  /**
   * Parse xml and return a DecisionTable instance
   */
  static fromXml(xml: string): DecisionTable {
    try {
      return this.fromXmlUnsafe(xml);
    } catch (e) {
      return new DecisionTable('', '', []);
    }
  }

  static fromXmlUnsafe(xml: string): DecisionTable {
    if (xml) {
      const parser = new DOMParser();
      const dom = parser.parseFromString(xml, 'text/xml').documentElement;
      return DecisionTable.fromDom(dom);
    } else {
      throw Error('Cannot deserialize an empty string');
    }
  }

  /**
   * Add the rule to the table, throw error if the id was already taken.
   */
  addRule(rule: Rule): Rule {
    if (this.rulesObj[rule.getId()]) {
      throw Error('Rule with id ' + rule.getId() + ' already exists');
    } else {
      this.rulesObj[rule.getId()] = rule;
      return rule;
    }
  }

  addETRule(rule: Rule): Rule {
    return null;
  }

  clearETRule(id: string) {
    // nothing
  }

  /**
   * Add the rule to the table, override the rule with same id if if it exists.
   */
  setRule(rule: Rule): Rule {
    this.rulesObj[rule.getId()] = rule;
    return rule;
  }

  addColumn(col: Column): Column {
    const existed = this.columnsObj[col.getId()];
    if (existed) {
      throw Error('Column with id ' + col.getId() + ' already exists');
    } else {
      return this.createAndAddColumnWithId(col.getId(), col.name, col.propertyType, col.columnType);
    }
  }

  setColumn(col: Column): Column {
    return this.createAndAddColumnWithId(col.getId(), col.name, col.propertyType, col.columnType);
  }

  hasColumn(colId: string): boolean {
    return this.columnsObj[colId] != null;
  }

  getRules(): Rule[] {
    const ret: Rule[] = [];
    for (const key in this.rulesObj) {
      if (this.rulesObj.hasOwnProperty(key)) {
        ret.push(this.rulesObj[key]);
      }
    }
    return ret;
  }

  getRule(ruleId: string): Rule {
    return this.rulesObj[ruleId];
  }

  getColumns(): Column[] {
    const ret: Column[] = [];
    Object.keys(this.columnsObj).filter(id => id !== RULE_PRIORITY).forEach(id => ret.push(this.columnsObj[id]));
    return ret.sort((a, b) => parseInt(a.id, 10) - parseInt(b.id, 10));
  }

  getColumn(colId: string): Column {
    return this.columnsObj[colId];
  }

  getPriorityColumn(): Column {
    return this.columnsObj[RULE_PRIORITY];
  }

  clearColumn(colId: string) {
    const found = this.columnsObj[colId];
    if (found) {
      delete this.columnsObj[colId];
      // clear the corresponding cells as well
      this.getRules().forEach(r => r.clearCell(colId));
    } else {
      throw Error('Unable to find column with id: ' + colId);
    }
  }

  clearCell(cellId: string) {
    const parts = cellId.split('_');
    const ruleId = parts[0];
    const colId = parts[1];
    const rule = this.getRule(ruleId);
    if (rule) {
      rule.clearCell(colId);
    } else {
      throw Error('cell ' + cellId + ' does not exist');
    }
  }

  getCell(cellId: string) {
    const parts = cellId.split('_');
    const ruleId = parts[0];
    const colId = parts[1];
    const rule = this.getRule(ruleId);

    if (rule) {
      return rule.getCell(colId);
    } else {
      return null;
    }
  }

  setCell(cellId: string, expr): Cell {
    const parts = cellId.split('_');
    const ruleId = parts[0];
    const colId = parts[1];
    const rule = this.getRule(ruleId);
    const col = this.getColumn(colId);

    if (rule && col) {
      return rule.setCell(colId, expr, col.columnType.cellType);
    } else {
      throw Error('cell ' + cellId + ' does not exist');
    }
  }

  setCellCommentOrDisabled(cellId: string, expr: string, disabled: boolean, comment: string): Cell {
    const parts = cellId.split('_');
    const ruleId = parts[0];
    const colId = parts[1];
    const rule = this.getRule(ruleId);
    const col = this.getColumn(colId);

    if (rule && col) {
      return rule.setCellCommentOrDisabled(colId, expr, col.columnType.cellType, disabled, comment);
    } else {
      throw Error('cell ' + cellId + ' does not exist');
    }
  }

  createAndAddColumn(name: string, propType: PropertyType, colType: ColumnType): Column {
    if (!(name && propType && colType)) {
      return null;
    }
    const cols = this.getColumns();
    const id = cols
      .map(col => +col.id)
      .reduce((a, b) => a > b ? a : b, -1) + 1;
    const col = new Column(
      id.toString(),
      name,  // columnName
      propType,
      colType
    );
    this.columnsObj[col.id] = col;
    return col;
  }

  createAndAddColumnWithId(id: string, name: string, propType: PropertyType, colType: ColumnType): Column {
    if (!(id && name && propType && colType)) {
      return null;
    }
    const col = new Column(
      id.toString(),
      name,  // columnName
      propType,
      colType
    );
    this.columnsObj[col.id] = col;
    return col;
  }

  createAndAddColumnWithIdBE(id: string, name: string, propType: PropertyType, colType: ColumnType, isDM: boolean, ownerPath: string, propName: string, alias: string, defaultCellText: string): Column {
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
    this.columnsObj[col.id] = col;
    return col;
  }

  getProperties(): Property[] {
    const ret: Property[] = [];
    for (const key in this.propertiesObj) {
      if (this.propertiesObj.hasOwnProperty(key)) {
        ret.push(this.propertiesObj[key]);
      }
    }
    return ret.filter(p => p.val !== '' && p.val !== 'false');
  }

  setProperty(key: string, val: string) {
    val = val ? val.trim() : '';
    let type;
    switch (key) {
      case DecisionTable.SINGLE_EXEC_PARAM:
      case DecisionTable.TIMESTAMP_AS_UTC:
        type = 'Boolean';
        break;
      case DecisionTable.PRIORITY_PARAM:
        type = null;
        break;
      default:
        type = 'String';
        break;
    }
    if (val && !((key === DecisionTable.SINGLE_EXEC_PARAM || key === DecisionTable.TIMESTAMP_AS_UTC) && val === 'false')
      || key === DecisionTable.EFFECTIVE_TIME_PARAM || key === DecisionTable.EXPIRY_TIME_PARAM) {
      const found = this.propertiesObj[key];
      if (found) {
        found.val = val;
        found.type = type;
      } else {
        this.propertiesObj[key] = new Property(key, val, type);
      }
    } else {
      this.clearProperty(key);
    }
  }

  clearProperty(key: string) {
    delete this.propertiesObj[key];
  }

  addProperty(p: Property) {
    this.setProperty(p.key, p.val);
  }

  getProperty(key: string): string {
    const found = this.propertiesObj[key];
    if (found) {
      return found.val;
    } else if (key === DecisionTable.SINGLE_EXEC_PARAM || key === DecisionTable.TIMESTAMP_AS_UTC) {
      return 'false'; // this has default value false
    } else {
      return '';
    }
  }

  /**
   * Factory method to create a rule with the same column definition as this table.
   */
  createAndAddRule(id: string, priority?: number): Rule {
    const rule = new Rule(id, '', priority);
    this.setRule(rule);
    return rule;
  }

  createRuleWithAutoId(): Rule {
    const id = this.maxRuleId() + 1;
    return this.createAndAddRule(id.toString());
  }

  maxRuleId(): number {
    return this.getRules()
      .map(r => {
        const id = parseInt(r.getId(), 10);
        return isNaN(id) ? -1 : id;
      })
      .reduce((max, i) => max > i ? max : i, 0);
  }

  maxColumnId(): number {
    return this.getColumns()
      .map(col => {
        const id = parseInt(col.getId(), 10);
        return isNaN(id) ? -1 : id;
      })
      .reduce((max, i) => max > i ? max : i, 0);
  }

  clearRule(id: string) {
    delete this.rulesObj[id];
  }

  hasRule(id: string): boolean {
    return this.rulesObj[id] !== undefined;
  }

  hasETRule(id: string): boolean {
    return null;
  }

  createAndAddRulefromDom(dom: Element): Rule {
    const ruleId = dom.getAttribute('id');
    let priority: number;
    const mdDom = dom.querySelector('md');
    if (mdDom) {
      const priorityDom = dom.querySelector('md').querySelector('prop');
      if (priorityDom) {
        priority = parseInt(priorityDom.getAttribute('value'), 10);
      }
    }
    const rule = this.createAndAddRule(ruleId, priority);
    Array.from(dom.querySelectorAll('cond, act')).forEach(c => {
      const colId = c.getAttribute('colId');
      const expr = c.getAttribute('expr');
      const col = this.getColumn(colId);
      rule.setCell(colId, expr, col.columnType.cellType);
    });
    return rule;
  }

  createAndAddColumnFromDom(dom: Element): Column {
    const id = dom.getAttribute('id');
    const name = dom.getAttribute('name');
    const propertyType = dom.getAttribute('propertyType');
    const columnType = dom.getAttribute('columnType');
    const col = Column.createColumn(id, name, propertyType, columnType);
    this.columnsObj[col.id] = col;
    return col;
  }

  clone(): DecisionTable {
    return DecisionTable.fromXml(this.toXml());
  }

  toXml(): string {
    const dt = `<?xml version="1.0" encoding="UTF-8"?>
<Table:Table xmi:version="2.0" \
xmlns:xmi="http://www.omg.org/XMI" \
xmlns:Table="http:///com/tibco/cep/decision/table/model/DecisionTable.ecore" \
name="${this.name}" folder="${this.basePath}">
  <md>
${this.getProperties().map(p => p.toXml(4)).join('\n')}
  </md>
  <decisionTable>
${this.getRules().map(r => r.toXml(4)).join('\n')}
    <columns>
${this.getColumns().filter(col => col.id !== RULE_PRIORITY).map(c => c.toXml(6)).join('\n')}
    </columns>
  </decisionTable>
</Table:Table>`;
    return dt;
  }

  seralize() {
    return this.toXml();
  }
}
