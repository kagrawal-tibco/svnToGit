import { Injectable } from '@angular/core';

import * as moment from 'moment';

import { BEDecisionTableEditorComponent } from './be-decisiontable-editor.component';
import { DecisionTableEditorComponent } from './decisiontable-editor.component';

import { environment } from '../../../environments/environment';
import { BEDecisionTable } from '../../editables/decision-table/be-decision-table';
import { Cell } from '../../editables/decision-table/cell';
import { Column, ColumnType, PropertyType } from '../../editables/decision-table/column';
import { DecisionTable } from '../../editables/decision-table/decision-table';
import { Rule } from '../../editables/decision-table/rule';
import { EditAction } from '../edit-action';

const FORMAT = 'YYYY-MM-DD HH:mm:ss';
const BE_FORMAT = 'YYYY-MM-DDTHH:mm:ss';

@Injectable()
export class DecisionTableEditorService {
  parseTimestamp(t: string): Date {
    if (environment.enableBEUI) {
      const m = moment(t, [moment.ISO_8601, BE_FORMAT], true);
      return m.isValid() ? m.toDate() : null;
    } else {
      const m = moment(t, [moment.ISO_8601, FORMAT], true);
      return m.isValid() ? m.toDate() : null;
    }
  }

  serializeDateTime(dateTime: Date): string {
    if (environment.enableBEUI) {
      return moment(dateTime).format(BE_FORMAT);
    } else {
      return moment(dateTime).format(FORMAT);
    }
  }

  rowCreateAction(rowId: string): EditAction<DecisionTable> {
    return {
      execute: (context: DecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          const rule = dt.createAndAddRule(rowId);
          context.editBuffer.markForDirtyCheck();
          context.gridOptions.api.updateRowData({ add: [rule], addIndex: null, update: null, remove: null });
        }
      },
      revert: (context: DecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          dt.clearRule(rowId);
          context.editBuffer.markForDirtyCheck();
          const toDelete = [];
          context.gridOptions.api.forEachNode(rowNode => {
            if (rowNode.data.getId() === rowId) {
              toDelete.push(rowNode.data);
            }
          });
          context.gridOptions.api.updateRowData({ add: null, addIndex: null, update: null, remove: toDelete });
        }
      }
    };
  }

  rowCreateActionBE(rowId: string): EditAction<BEDecisionTable> {
    return {
      execute: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          const rule = dt.createAndAddRule(rowId, 5);
          this.updateWithDefaultValues(rule, dt);
          // context.editBuffer.markForDirtyCheck();
          context.gridOptions.api.updateRowData({ add: [rule], addIndex: null, update: null, remove: null });
        }
      },
      revert: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          dt.clearRule(rowId);
          context.editBuffer.markForDirtyCheck();
          const toDelete = [];
          context.gridOptions.api.forEachNode(rowNode => {
            if (rowNode.data.getId() === rowId) {
              toDelete.push(rowNode.data);
            }
          });
          context.gridOptions.api.updateRowData({ add: null, addIndex: null, update: null, remove: toDelete });
        }
      }
    };
  }

  updateWithDefaultValues(rule: Rule, dt: BEDecisionTable) {
    for (const col of dt.getColumns()) {
      if (rule) {
        if (col) {
          const cell = rule.getCell(col.getId());
          if (!cell && col.defaultCellText !== '') {
            rule.setCell(col.getId(), col.defaultCellText, col.columnType.cellType);
          }
        }
      }
    }
  }

  rowDeleteAction(rowId: string): EditAction<DecisionTable> {
    let deletedRow: Rule;
    return {
      execute: (context: DecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          deletedRow = dt.getRule(rowId);
          if (deletedRow) {
            dt.clearRule(rowId);
            context.editBuffer.markForDirtyCheck();
            context.updateAgGrid();
          }
        }
      },
      revert: (context: DecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt && deletedRow) {
          dt.addRule(deletedRow);
          context.editBuffer.markForDirtyCheck();
          context.updateAgGrid();
        }
      }
    };
  }

  rowDuplicateAction(row: Rule): EditAction<DecisionTable> {
    const originalRow = row;
    let duplicatedRow: Rule;
    return {
      execute: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        const maxtId = (context.getMaxId(dt) + 1).toString();
        if (dt) {
          duplicatedRow = dt.createAndAddRule(maxtId);
          if (duplicatedRow) {
            this.updateDuplicateRow(originalRow, duplicatedRow);
            context.editBuffer.markForDirtyCheck();
            context.updateAgGrid();
          }
        }
      },
      revert: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          dt.clearRule(duplicatedRow.getId());
          context.editBuffer.markForDirtyCheck();
          const toDelete = [];
          context.gridOptions.api.forEachNode(rowNode => {
            if (rowNode.data.getId() === duplicatedRow.getId()) {
              toDelete.push(rowNode.data);
            }
          });
          context.gridOptions.api.updateRowData({ add: null, addIndex: null, update: null, remove: toDelete });
        }

      }
    };
  }

  rowDuplicateActionET(row: Rule): EditAction<BEDecisionTable> {
    const originalRow = row;
    let duplicatedRow: Rule;
    return {
      execute: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        const maxtId = (context.getMaxId(dt) + 1).toString();
        if (dt) {
          duplicatedRow = dt.createAndAddETRule(maxtId);
          if (duplicatedRow) {
            this.updateDuplicateRow(originalRow, duplicatedRow);
            context.editBuffer.markForDirtyCheck();
            context.updateExceptionTable();
          }
        }
      },
      revert: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          dt.clearRule(duplicatedRow.getId());
          context.editBuffer.markForDirtyCheck();
          const toDelete = [];
          context.gridOptions.api.forEachNode(rowNode => {
            if (rowNode.data.getId() === duplicatedRow.getId()) {
              toDelete.push(rowNode.data);
            }
          });
          context.gridOptions.api.updateRowData({ add: null, addIndex: null, update: null, remove: toDelete });
        }

      }
    };
  }

  updateDuplicateRow(orule: Rule, drule: Rule) {
    for (const cellObject of orule.getCells()) {
      drule.createOrUpdateCell(cellObject.getColId(), cellObject.getExpr(), cellObject.getType(), cellObject.isDisabled(),
       cellObject.getComment());
    }
  }

  columnCreateAction(colId: string, name: string, propType: PropertyType, colType: ColumnType): EditAction<DecisionTable> {
    return {
      execute: (context: DecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          dt.createAndAddColumnWithId(colId, name, propType, colType);
          context.updateAgGrid();
        }
      },
      revert: (context: DecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          dt.clearColumn(colId);
          context.updateAgGrid();
        }
      }
    };
  }

  columnCreateActionBE(colId: string, name: string, propType: PropertyType, colType: ColumnType,
     isDM: boolean, cPath: string, propName: string): EditAction<DecisionTable> {
    return {
      execute: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          dt.createAndAddColumnWithIdBE(colId, name, propType, colType, isDM, cPath, propName, '', '');
          context.updateAgGrid();
        }
      },
      revert: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          dt.clearColumn(colId);
          context.updateAgGrid();
        }
      }
    };
  }

  columnDeleteAction(colId: string) {
    const deletedCells: Cell[] = [];
    let deletedColumn: Column;
    return {
      execute: (context: DecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          deletedColumn = dt.getColumn(colId);
          if (deletedColumn) {
            dt.getRules().map(rule => rule.getCell(colId))
              .filter(cell => cell != null)
              .forEach(cell => deletedCells.push(cell));
            dt.clearColumn(colId);
            context.editBuffer.markForDirtyCheck();
            context.updateAgGrid();
          }
        }
      },
      revert: (context: DecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt && deletedColumn) {
          dt.addColumn(deletedColumn);
          deletedCells.forEach(cell => dt.setCell(cell.getId(), cell.getExpr()));
          context.editBuffer.markForDirtyCheck();
          context.updateAgGrid();
        }
      }
    };
  }

  propertyEditAction(propName: string, oldVal: string, val: string): EditAction<DecisionTable> {
    return {
      execute: (context: DecisionTableEditorComponent) => {
        context.decorator.setProperty(propName, val);
      },
      revert: (context: DecisionTableEditorComponent) => {
        context.decorator.setProperty(propName, oldVal);
      }
    };
  }

  cellEditAction(ruleId: string, colId: string, oldVal: string, val: string): EditAction<DecisionTable> {
    const cellId = `${ruleId}_${colId}`;
    const execute = (context: DecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setCell(cellId, val);
        context.editBuffer.markForDirtyCheck();
        // ag-grid updates
        // this is not needed.  The cell contents are already changed by the cellEditor component
        //        context.gridOptions.api.forEachNode(rowNode => {
        //          if (rowNode.data.getId() === ruleId) {
        //            context.gridOptions.api.updateRowData({ add: null, addIndex: null, update: [rowNode.data], remove: null });
        //          }
        //        });
      }
    };
    const revert = (context: DecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setCell(cellId, oldVal);
        context.editBuffer.markForDirtyCheck();
        //        context.gridOptions.api.refreshView();
        // ag-grid updates
        context.gridOptions.api.redrawRows();
      }
    };
    return {
      execute: execute,
      revert: revert
    };
  }

  cellCommentEditAction(ruleId: string, colId: string, expr: string, enabled: boolean,
     oldVal: string, val: string): EditAction<DecisionTable> {
    const cellId = `${ruleId}_${colId}`;
    const execute = (context: DecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setCellCommentOrDisabled(cellId, expr, enabled, val);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateCellCommentUndoRedo(val);
      }
    };
    const revert = (context: DecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setCellCommentOrDisabled(cellId, expr, enabled, oldVal);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateCellCommentUndoRedo(oldVal);
      }
    };
    return {
      execute: execute,
      revert: revert
    };
  }

  ruleCommentEditAction(ruleId: string, oldVal: string, val: string): EditAction<DecisionTable> {
    const execute = (context: DecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.getRule(ruleId).setRuleComment(val);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateRuleCommentUndoRedo(val);
      }
    };
    const revert = (context: DecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.getRule(ruleId).setRuleComment(oldVal);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateRuleCommentUndoRedo(oldVal);
      }
    };
    return {
      execute: execute,
      revert: revert
    };
  }

  cellCommentEditActionET(ruleId: string, colId: string, expr: string, enabled: boolean,
     oldVal: string, val: string): EditAction<DecisionTable> {
    const cellId = `${ruleId}_${colId}`;
    const execute = (context: BEDecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setETCellCommentOrDisabled(cellId, expr, enabled, val);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateCellCommentUndoRedo(val);
      }
    };
    const revert = (context: BEDecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setETCellCommentOrDisabled(cellId, expr, enabled, oldVal);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateCellCommentUndoRedo(oldVal);
      }
    };
    return {
      execute: execute,
      revert: revert
    };
  }

  ruleCommentEditActionET(ruleId: string, oldVal: string, val: string): EditAction<DecisionTable> {
    const execute = (context: BEDecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.getETRule(ruleId).setRuleComment(val);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateRuleCommentUndoRedo(val);
      }
    };
    const revert = (context: BEDecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.getETRule(ruleId).setRuleComment(oldVal);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateRuleCommentUndoRedo(oldVal);
      }
    };
    return {
      execute: execute,
      revert: revert
    };
  }

  cellEnabledEditAction(ruleId: string, colId: string, expr: string, comment: string, oldVal: boolean,
     val: boolean): EditAction<DecisionTable> {
    const cellId = `${ruleId}_${colId}`;
    const execute = (context: DecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setCellCommentOrDisabled(cellId, expr, val, comment);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateCellEnabledUndoRedo(val);
      }
    };
    const revert = (context: DecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setCellCommentOrDisabled(cellId, expr, oldVal, comment);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateCellEnabledUndoRedo(oldVal);
      }
    };
    return {
      execute: execute,
      revert: revert
    };
  }

  cellEnabledEditActionET(ruleId: string, colId: string, expr: string, comment: string,
     oldVal: boolean, val: boolean): EditAction<DecisionTable> {
    const cellId = `${ruleId}_${colId}`;
    const execute = (context: BEDecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setETCellCommentOrDisabled(cellId, expr, val, comment);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateCellEnabledUndoRedo(val);
      }
    };
    const revert = (context: BEDecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setETCellCommentOrDisabled(cellId, expr, oldVal, comment);
        context.editBuffer.markForDirtyCheck();
        context.propertiesService.updateCellEnabledUndoRedo(oldVal);
      }
    };
    return {
      execute: execute,
      revert: revert
    };
  }

  rowCreateActionET(rowId: string): EditAction<BEDecisionTable> {
    return {
      execute: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          const rule = dt.createAndAddETRule(rowId, 5);
          this.updateWithDefaultValuesET(rule, dt);
          // context.editBuffer.markForDirtyCheck();
          // this is not needed.  The cell contents are already changed by the cellEditor component
          context.gridOptionsNew.api.updateRowData({ add: [rule], addIndex: null, update: null, remove: null });
        }
      },
      revert: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          dt.clearETRule(rowId);
          context.editBuffer.markForDirtyCheck();
          const toDelete = [];
          context.gridOptionsNew.api.forEachNode(rowNode => {
            if (rowNode.data.getId() === rowId) {
              toDelete.push(rowNode.data);
            }
          });
          context.gridOptionsNew.api.updateRowData({ add: null, addIndex: null, update: null, remove: toDelete });
        }
      }
    };
  }

  updateWithDefaultValuesET(rule: Rule, dt: BEDecisionTable) {
    for (const col of dt.getETColumns()) {
      if (rule) {
        if (col) {
          const cell = rule.getCell(col.getId());
          if (!cell && col.defaultCellText !== '') {
            rule.setCell(col.getId(), col.defaultCellText, col.columnType.cellType);
          }
        }
      }
    }
  }

  rowDeleteActionET(rowId: string): EditAction<BEDecisionTable> {
    let deletedRow: Rule;
    return {
      execute: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          deletedRow = dt.getETRule(rowId);
          if (deletedRow) {
            dt.clearETRule(rowId);
            context.editBuffer.markForDirtyCheck();
            context.updateExceptionTable();
          }
        }
      },
      revert: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt && deletedRow) {
          dt.addETRule(deletedRow);
          context.editBuffer.markForDirtyCheck();
          context.updateExceptionTable();
        }
      }
    };
  }

  cellEditActionET(ruleId: string, colId: string, oldVal: string, val: string): EditAction<BEDecisionTable> {
    const cellId = `${ruleId}_${colId}`;
    const execute = (context: BEDecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setETCell(cellId, val);
        context.editBuffer.markForDirtyCheck();
        //        context.gridOptionsNew.api.refreshView();
        // ag-grid updates
        // this is not needed.  The cell contents are already changed by the cellEditor component
        //        context.gridOptionsNew.api.forEachNode(rowNode => {
        //          if (rowNode.data.getId() === ruleId) {
        //            context.gridOptionsNew.api.updateRowData({ add: null, addIndex: null, update: [rowNode.data], remove: null });
        //          }
        //        });
      }
    };
    const revert = (context: BEDecisionTableEditorComponent) => {
      const dt = context.editBuffer.getBuffer();
      if (dt) {
        dt.setETCell(cellId, oldVal);
        context.editBuffer.markForDirtyCheck();
        //        context.gridOptionsNew.api.refreshView();
        // ag-grid updates
        context.gridOptionsNew.api.redrawRows();
      }
    };
    return {
      execute: execute,
      revert: revert
    };
  }

  columnCreateActionET(colId: string, name: string, propType: PropertyType, colType: ColumnType,
     isDM: boolean, cPath: string, propName: string): EditAction<BEDecisionTable> {
    return {
      execute: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          dt.createAndAddETColumnWithId(colId, name, propType, colType, isDM, cPath, propName, '', '');
          context.updateExceptionTable();
        }
      },
      revert: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          dt.clearColumn(colId);
          context.updateExceptionTable();
        }
      }
    };
  }

  columnDeleteActionET(colId: string) {
    const deletedCells: Cell[] = [];
    let deletedColumn: Column;
    return {
      execute: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt) {
          deletedColumn = dt.getETColumn(colId);
          if (deletedColumn) {
            dt.getETRules().map(rule => rule.getCell(colId))
              .filter(cell => cell != null)
              .forEach(cell => deletedCells.push(cell));
            dt.clearETColumn(colId);
            context.editBuffer.markForDirtyCheck();
            context.updateExceptionTable();
          }
        }
      },
      revert: (context: BEDecisionTableEditorComponent) => {
        const dt = context.editBuffer.getBuffer();
        if (dt && deletedColumn) {
          dt.addETColumn(deletedColumn);
          deletedCells.forEach(cell => dt.setETCell(cell.getId(), cell.getExpr()));
          context.editBuffer.markForDirtyCheck();
          context.updateExceptionTable();
        }
      }
    };
  }
}
