import { Action, Condition } from './action-condition-be';
import { Argument, Arguments } from './arguments-be';
import { Metadata, Property } from './metadata-be';
import { Column, DecisionTableColumns, ExceptionTableColumns } from './table-columns-be';
import { DecisionTablePage, ExceptionTablePage, TableRule } from './table-page-be';

import { BEDecisionTable } from '../../editables/decision-table/be-decision-table';
import { RULE_PRIORITY } from '../../editables/decision-table/decision-table';

export class DecisionTableSave {
  projectName: string;
  artifactPath: string;
  implementsPath: string;
  isSyncMerge: string;
  arguments: Arguments;
  decisionTableColumns: DecisionTableColumns;
  decisionTablePage: DecisionTablePage;
  exceptionTableColumns: ExceptionTableColumns;
  exceptionTablePage: ExceptionTablePage;
  metadata: Metadata;
  decisionTableTotalPages: string;
  exceptionTableTotalPages: string;
  decisionTableLastRuleId: string;
  exceptionTableLastRuleId: string;
  decisionTableSinglePageView: string;

  constructor(
    projectNameParam: string,
    artifactPathParam: string,
    implementsPathParam: string,
    isSyncMergeParam: string,
    argumentsParam: Arguments,
    decisionTableColumnsParam: DecisionTableColumns,
    decisionTablePageParam: DecisionTablePage,
    exceptionTableColumnsParam: ExceptionTableColumns,
    exceptionTablePageParam: ExceptionTablePage,
    metadataParam: Metadata) {

    this.projectName = projectNameParam;
    this.artifactPath = artifactPathParam;
    this.implementsPath = implementsPathParam;
    this.isSyncMerge = isSyncMergeParam;
    this.arguments = argumentsParam;
    this.decisionTableColumns = decisionTableColumnsParam;
    this.decisionTablePage = decisionTablePageParam;
    this.exceptionTableColumns = exceptionTableColumnsParam;
    this.exceptionTablePage = exceptionTablePageParam;
    this.metadata = metadataParam;

  }

  static toSaveJson(dtSave: string): string {

    const jsonObject = JSON.parse(dtSave);
    const argumentArray: Array<Argument> = new Array<Argument>();
    const propertyArray: Array<Property> = new Array<Property>();
    const dtColumnsArray: Array<Column> = new Array<Column>();
    const etColumnsArray: Array<Column> = new Array<Column>();
    const dtRulesArray: Array<TableRule> = new Array<TableRule>();
    const etRulesArray: Array<TableRule> = new Array<TableRule>();

    const projectName = jsonObject.projectName;
    const artifactPath = jsonObject.artifactPath;
    const implementsPath = jsonObject.implementsPath;
    const isSyncMerge = jsonObject.isSyncMerge ? jsonObject.isSyncMerge : 'false';

    // fetching  arguments
    if (jsonObject.arguments.argument) {
      for (const arg of jsonObject.arguments.argument) {
        const argument = new Argument(arg.argumentAlias, arg.direction, arg.isArray, arg.path, arg.resourceType);
        argumentArray.push(argument);
      }
    }

    const argumentsObject = new Arguments(argumentArray);

    // fetching metadata
    if (jsonObject.metadata.property) {
      for (const prop of jsonObject.metadata.property) {
        const property = new Property(prop.name, prop.type, prop.value);
        propertyArray.push(property);
      }
    }

    const metadataObject = new Metadata(propertyArray);

    // fetching decison table columns
    if (jsonObject.decisionTableColumns.column) {
      for (const col of jsonObject.decisionTableColumns.column) {
        const column = new Column(col.associatedDM,
          col.columnAlias,
          col.columnId,
          col.columnType,
          col.isArrayProperty,
          col.isSubstitution,
          col.name,
          col.property,
          String(col.propertyType),
          col.defaultCellText ? col.defaultCellText : '');
        dtColumnsArray.push(column);
      }
    }

    const dtColumnObject = new DecisionTableColumns(dtColumnsArray);

    // fetching exception table columns
    if (jsonObject.exceptionTableColumns && jsonObject.exceptionTableColumns.column) {
      for (const col of jsonObject.exceptionTableColumns.column) {
        const column = new Column(col.associatedDM,
          col.columnAlias,
          col.columnId,
          col.columnType,
          col.isArrayProperty,
          col.isSubstitution,
          col.name,
          col.property,
          String(col.propertyType),
          col.defaultCellText ? col.defaultCellText : '');
        etColumnsArray.push(column);
      }
    }

    const etColumnObject = new ExceptionTableColumns(etColumnsArray);

    // fetching decision table page
    const dtPageNumber = jsonObject.decisionTablePage.pageNumber;
    if (jsonObject.decisionTablePage.tableRule) {
      for (const trule of jsonObject.decisionTablePage.tableRule) {
        const actionArray: Array<Action> = new Array<Action>();
        const conditionArray: Array<Condition> = new Array<Condition>();
        const propsArray: Array<Property> = new Array<Property>();
        if (trule.action) {
          for (const act of trule.action) {
            const action = new Action(act.varId, act.columnId, act.expression, act.isEnabled, act.ruleId, act.comment ? act.comment : '');
            actionArray.push(action);
          }
        }
        if (trule.condition) {
          for (const cond of trule.condition) {
            const condition = new Condition(cond.varId, cond.columnId, cond.expression, cond.isEnabled, cond.ruleId, cond.comment ? cond.comment : '');
            conditionArray.push(condition);
          }
        }
        if (trule.metadata) {
          if (trule.metadata.property) {
            for (const prop of trule.metadata.property) {
              const property = new Property(prop.name, prop.type, prop.value);
              propsArray.push(property);
            }
          }
        }

        const tableRule = new TableRule(actionArray, conditionArray, new Metadata(propsArray), trule.ruleId);
        dtRulesArray.push(tableRule);
      }
    }

    const dtPageObject = new DecisionTablePage(dtPageNumber, dtRulesArray);

    // fetching exception table page
    const etPageNumber = (jsonObject.exceptionTablePage && jsonObject.exceptionTablePage.pageNumber) ? jsonObject.exceptionTablePage.pageNumber : 1;
    if (jsonObject.exceptionTablePage && jsonObject.exceptionTablePage.tableRule) {
      for (const trule of jsonObject.exceptionTablePage.tableRule) {
        const actionArray: Array<Action> = new Array<Action>();
        const conditionArray: Array<Condition> = new Array<Condition>();
        const propsArray: Array<Property> = new Array<Property>();
        if (trule.action) {
          for (const act of trule.action) {
            const action = new Action(act.varId, act.columnId, act.expression, act.isEnabled, act.ruleId, act.comment ? act.comment : '');
            actionArray.push(action);
          }
        }
        if (trule.condition) {
          for (const cond of trule.condition) {
            const condition = new Condition(cond.varId, cond.columnId, cond.expression, cond.isEnabled, cond.ruleId, cond.comment ? cond.comment : '');
            conditionArray.push(condition);
          }
        }
        if (trule.metadata) {
          if (trule.metadata.property) {
            for (const prop of trule.metadata.property) {
              const property = new Property(prop.name, prop.type, prop.value);
              propsArray.push(property);
            }
          }
        }

        const tableRule = new TableRule(actionArray, conditionArray, new Metadata(propsArray), trule.ruleId);
        etRulesArray.push(tableRule);
      }
    }
    const etPageObject = new ExceptionTablePage(etPageNumber, etRulesArray);

    const dtSaveObject = new DecisionTableSave(projectName, artifactPath, implementsPath, isSyncMerge, argumentsObject, dtColumnObject, dtPageObject, etColumnObject, etPageObject, metadataObject);
    dtSaveObject.decisionTableTotalPages = jsonObject.decisionTableTotalPages;
    dtSaveObject.exceptionTableTotalPages = jsonObject.exceptionTableTotalPages;
    dtSaveObject.decisionTableLastRuleId = jsonObject.decisionTableLastRuleId;
    dtSaveObject.exceptionTableLastRuleId = jsonObject.exceptionTableLastRuleId;
    dtSaveObject.decisionTableSinglePageView = jsonObject.decisionTableSinglePageView;

    return JSON.stringify(dtSaveObject);

  }

  static dtObjectToJson(dtObject: BEDecisionTable): string {

    const argumentArray: Array<Argument> = new Array<Argument>();
    const propertyArray: Array<Property> = new Array<Property>();
    const dtColumnsArray: Array<Column> = new Array<Column>();
    const etColumnsArray: Array<Column> = new Array<Column>();
    const dtRulesArray: Array<TableRule> = new Array<TableRule>();
    const etRulesArray: Array<TableRule> = new Array<TableRule>();

    const projectName = dtObject.projectName;
    const artifactPath = dtObject.artifactPath;
    const implementsPath = dtObject.implementsPath;
    const isSyncMerge = dtObject.isSyncMerge ? dtObject.isSyncMerge : 'false';

    for (const key in dtObject.argumentObj) {
      const argument = dtObject.argumentObj[key];
      argumentArray.push(argument);
    }

    const argumentsObject = new Arguments(argumentArray);

    for (const key in dtObject.propertiesObj) {
      const property = dtObject.propertiesObj[key];
      const dtProperty = new Property(property.key, property.type, property.val);
      propertyArray.push(dtProperty);
    }

    const metadataObject = new Metadata(propertyArray);

    //  fetch DT column object
    for (const columnObject of dtObject.getColumns()) {
      const column = new Column(columnObject.associatedDM, columnObject.alias, columnObject.id, columnObject.columnType.value,
         columnObject.isArrayProperty, columnObject.isSubstitution, columnObject.name, columnObject.property, columnObject.propertyType.value, columnObject.defaultCellText);
      dtColumnsArray.push(column);
    }

    const dtColumnObject = new DecisionTableColumns(dtColumnsArray);

    // fetch DT rules object

    for (const ruleObject of dtObject.getRules()) {
      const actionArray: Array<Action> = new Array<Action>();
      const conditionArray: Array<Condition> = new Array<Condition>();
      for (const cellObject of ruleObject.getCells()) {
        if (cellObject.getType() === 'cond') {
          const condition = new Condition(cellObject.getId(), cellObject.getColId(), cellObject.getExpr(), (!cellObject.isDisabled()).toString(), cellObject.getRuleId(), cellObject.getComment() ? cellObject.getComment() : '');
          conditionArray.push(condition);
        } else {
          const action = new Action(cellObject.getId(), cellObject.getColId(), cellObject.getExpr(), (!cellObject.isDisabled()).toString(), cellObject.getRuleId(), cellObject.getComment() ? cellObject.getComment() : '');
          actionArray.push(action);
        }
      }
      const propsArray: Array<Property> = new Array<Property>();
      const priority = ruleObject.getCell(RULE_PRIORITY) ? ruleObject.getCell(RULE_PRIORITY).getExpr() : '5';
      const priorityProp = new Property('Priority', 'prop', priority);
      propsArray.push(priorityProp);
      const description = ruleObject.getRuleComment();
      const descProp = new Property('Description', 'string', description);
      propsArray.push(descProp);

      const tableRule = new TableRule(actionArray, conditionArray, new Metadata(propsArray), ruleObject.getId());
      dtRulesArray.push(tableRule);
    }

    //  fetch ET column object
    for (const columnObject of dtObject.getETColumns()) {
      const column = new Column(columnObject.associatedDM, columnObject.alias, columnObject.id, columnObject.columnType.value,
         columnObject.isArrayProperty, columnObject.isSubstitution, columnObject.name, columnObject.property, columnObject.propertyType.value, columnObject.defaultCellText);
      etColumnsArray.push(column);
    }
    const etColumnObject = new DecisionTableColumns(etColumnsArray);

    // fetch ET rules object

    for (const ruleObject of dtObject.getETRules()) {
      const actionArray: Array<Action> = new Array<Action>();
      const conditionArray: Array<Condition> = new Array<Condition>();
      for (const cellObject of ruleObject.getCells()) {
        if (cellObject.getType() === 'cond') {
          const condition = new Condition(cellObject.getId(), cellObject.getColId(), cellObject.getExpr(), (!cellObject.isDisabled()).toString(), cellObject.getRuleId(), cellObject.getComment() ? cellObject.getComment() : '');
          conditionArray.push(condition);
        } else {
          const action = new Action(cellObject.getId(), cellObject.getColId(), cellObject.getExpr(), (!cellObject.isDisabled()).toString(), cellObject.getRuleId(), cellObject.getComment() ? cellObject.getComment() : '');
          actionArray.push(action);
        }
      }

      const propsArray: Array<Property> = new Array<Property>();
      const priority = ruleObject.getCell(RULE_PRIORITY) ? ruleObject.getCell(RULE_PRIORITY).getExpr() : '5';
      const priorityProp = new Property('Priority', 'prop', priority);
      propsArray.push(priorityProp);
      const description = ruleObject.getRuleComment();
      const descProp = new Property('Description', 'string', description);
      propsArray.push(descProp);

      const tableRule = new TableRule(actionArray, conditionArray, new Metadata(propsArray), ruleObject.getId());
      etRulesArray.push(tableRule);
    }

    const dtPageObject = new DecisionTablePage(dtObject.decisionTableCurrentPage, dtRulesArray);

    const etPageObject = new DecisionTablePage('1', etRulesArray);

    const saveDTObject = new DecisionTableSave(projectName, artifactPath, implementsPath, isSyncMerge, argumentsObject, dtColumnObject, dtPageObject, etColumnObject, etPageObject, metadataObject);
    saveDTObject.decisionTableTotalPages = dtObject.decisionTableTotalPages;
    saveDTObject.exceptionTableTotalPages = dtObject.exceptionTableTotalPages;
    saveDTObject.decisionTableLastRuleId = dtObject.decisionTableLastRuleId;
    saveDTObject.exceptionTableLastRuleId = dtObject.exceptionTableLastRuleId;
    saveDTObject.decisionTableSinglePageView = dtObject.decisionTableSinglePageView;
    return JSON.stringify(saveDTObject);
  }

  static defaultDtSave(vrfObject: any): string {

    const argumentArray: Array<Argument> = new Array<Argument>();
    const propertyArray: Array<Property> = new Array<Property>();
    const dtColumnsArray: Array<Column> = new Array<Column>();
    const etColumnsArray: Array<Column> = new Array<Column>();
    const dtRulesArray: Array<TableRule> = new Array<TableRule>();
    const etRulesArray: Array<TableRule> = new Array<TableRule>();
    const metadataObject = new Metadata(propertyArray);
    const dtColumnObject = new DecisionTableColumns(dtColumnsArray);
    const etColumnObject = new DecisionTableColumns(etColumnsArray);
    const dtPageObject = new DecisionTablePage('1', dtRulesArray);
    const etPageObject = new DecisionTablePage('1', etRulesArray);

    const implementsPath = vrfObject.implementsPath;
    const isSyncMerge = vrfObject.isSyncMerge ? vrfObject.isSyncMerge : 'false';

    for (const symb of vrfObject.symbol) {
      const argument = new Argument(symb.argumentAlias, 'BOTH', symb.isArray, symb.path, symb.resourceType);
      argumentArray.push(argument);
    }
    const argumentObject = new Arguments(argumentArray);

    const saveDTObject = new DecisionTableSave('', '', implementsPath, isSyncMerge, argumentObject, dtColumnObject, dtPageObject, etColumnObject, etPageObject, metadataObject);
    saveDTObject.decisionTableTotalPages = '1';
    saveDTObject.exceptionTableTotalPages = '1';
    saveDTObject.decisionTableLastRuleId = '0';
    saveDTObject.exceptionTableLastRuleId = '0';
    saveDTObject.decisionTableSinglePageView = 'true';
    return JSON.stringify(saveDTObject);
  }

}
