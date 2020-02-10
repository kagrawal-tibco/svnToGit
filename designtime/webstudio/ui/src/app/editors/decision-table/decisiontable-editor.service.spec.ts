import { TestBed } from '@angular/core/testing';

import { DecisionTableEditorComponent } from './decisiontable-editor.component';
import { DecisionTableEditorService } from './decisiontable-editor.service';

import { Cell } from '../../editables/decision-table/cell';
import { Column, ColumnType, PropertyType } from '../../editables/decision-table/column';
import { DecisionTable } from '../../editables/decision-table/decision-table';
import { Rule } from '../../editables/decision-table/rule';
describe('DecisionTable Editor Service', () => {
  let service: DecisionTableEditorService;
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DecisionTableEditorService]
    });
    service = TestBed.get(DecisionTableEditorService);
  });

  describe('it can handle timestamp', () => {
    it('is able to parse correct timestamp', () => {
      const timestamp = '2015-01-30 12:12:12';
      const parsed = service.parseTimestamp(timestamp);
      expect(parsed).not.toBeNull();
      expect(timestamp).toBe(service.serializeDateTime(parsed));
    });

    it('is able to know when the timestamp is invalid', () => {
      const timestamp = '2015-1-30 12:12:12';
      const parsed = service.parseTimestamp(timestamp);
      expect(parsed).toBeNull();
    });
  });

  describe('it can create decision table editing actions', () => {
    let column: Column;
    let row: Rule;
    let rowNode: { data: Rule };
    let cell: Cell;
    let dt: DecisionTable;
    let context: DecisionTableEditorComponent;
    beforeEach(() => {
      column = {
        getId: () => { }
      } as Column;
      row = {
        getId: () => 'row_id', // tests expect this as the row id
        getCell: (id) => cell
      } as Rule;
      rowNode = { data: row };
      cell = {
        getId: () => { },
        getExpr: () => { }
      } as Cell;
      dt = <DecisionTable>{
        setCell: (id, val) => cell,
        addColumn: (col) => column,
        createAndAddColumnWithId: (colId, name, propType, colType) => column,
        createAndAddRule: (id) => row,
        clearRule: (id) => { },
        addRule: (rule) => { },
        getRule: (id) => row,
        getRules: () => [row],
        getColumn: (id) => column,
        clearColumn: (id) => { }
      };
      context = <DecisionTableEditorComponent>{
        updateAgGrid: () => { },
        decorator: {
          setProperty: (key, val) => { }
        },
        editBuffer: {
          getBuffer: () => dt,
          markForDirtyCheck: () => { }
        },
        gridOptions: {
          api: {
            refreshView: () => { },
            // ag-grid updates
            redrawRows: () => { },
            rowDataChanged: (row) => { },
            updateRowData: (transaction) => { },
            addItems: (items) => { },
            removeItems: (nodes) => { },
            forEachNode: (cb: (node) => {}) => { cb({ data: row }); },
          }
        }
      };
      spyOn(dt, 'setCell').and.callThrough();
      spyOn(dt, 'addColumn').and.callThrough();
      spyOn(dt, 'createAndAddColumnWithId').and.callThrough();
      spyOn(dt, 'createAndAddRule').and.callThrough();
      spyOn(dt, 'getRule').and.callThrough();
      spyOn(dt, 'addRule').and.callThrough();
      spyOn(dt, 'clearRule').and.callThrough();
      spyOn(dt, 'clearColumn').and.callThrough();
      spyOn(row, 'getCell').and.callThrough();
      spyOn(context, 'updateAgGrid').and.callThrough();
      spyOn(context.gridOptions.api, 'refreshView').and.callThrough();
      // ag-grid updates
      spyOn(context.gridOptions.api, 'redrawRows').and.callThrough();
      spyOn(context.gridOptions.api, 'rowDataChanged').and.callThrough();
      spyOn(context.gridOptions.api, 'updateRowData').and.callThrough();
      spyOn(context.gridOptions.api, 'addItems').and.callThrough();
      spyOn(context.gridOptions.api, 'removeItems').and.callThrough();
      spyOn(context.decorator, 'setProperty').and.callThrough();
      spyOn(context.editBuffer, 'getBuffer').and.callThrough();
      spyOn(context.editBuffer, 'markForDirtyCheck').and.callThrough();
    });

    it('is able to create cell editing actions', () => {
      const action = service.cellEditAction('row_id', '1', 'old', 'new');

      action.execute(context);
      expect(context.editBuffer.getBuffer).toHaveBeenCalledTimes(1);
      // ag-grid updates
      // expect(context.gridOptions.api.updateRowData).toHaveBeenCalledTimes(1);
      //      expect(context.gridOptions.api.refreshView).toHaveBeenCalledTimes(1);
      expect(context.editBuffer.markForDirtyCheck).toHaveBeenCalledTimes(1);
      expect(dt.setCell).toHaveBeenCalledWith('row_id_1', 'new');

      action.revert(context);
      expect(context.editBuffer.getBuffer).toHaveBeenCalledTimes(2);
      // ag-grid updates
      expect(context.gridOptions.api.redrawRows).toHaveBeenCalledTimes(1);
      //      expect(context.gridOptions.api.refreshView).toHaveBeenCalledTimes(2);
      expect(context.editBuffer.markForDirtyCheck).toHaveBeenCalledTimes(2);
      expect(dt.setCell).toHaveBeenCalledWith('row_id_1', 'old');
    });

    it('is able to create property editing actions', () => {
      const action = service.propertyEditAction('prop_key', 'old', 'new');

      // property editing is done through decorator. Therefore no need to tell editBuffer to check for dirty
      // or ask the view to refresh
      action.execute(context);
      expect(context.decorator.setProperty).toHaveBeenCalledWith('prop_key', 'new');
      action.revert(context);
      expect(context.decorator.setProperty).toHaveBeenCalledWith('prop_key', 'old');
    });

    it('is able to create row creation actions', () => {
      const action = service.rowCreateAction('row_id');

      spyOn(row, 'getId').and.returnValue('row_id');

      action.execute(context);
      expect(dt.createAndAddRule).toHaveBeenCalledWith('row_id');
      expect(context.gridOptions.api.updateRowData).toHaveBeenCalledWith({ add: [row], addIndex: null, update: null, remove: null });
      action.revert(context);
      expect(dt.clearRule).toHaveBeenCalledWith('row_id');
      expect(context.gridOptions.api.updateRowData).toHaveBeenCalledWith({ add: null, addIndex: null, update: null, remove: [row] });
    });

    it('is able to create row deletion actions', () => {
      const action = service.rowDeleteAction('row_id');

      spyOn(row, 'getId').and.returnValue('row_id');

      action.execute(context);
      expect(dt.clearRule).toHaveBeenCalledWith('row_id');
      expect(context.updateAgGrid).toHaveBeenCalledTimes(1);
      action.revert(context);
      expect(dt.addRule).toHaveBeenCalledWith(row);
      expect(context.updateAgGrid).toHaveBeenCalledTimes(2);
    });

    it('is able to create column creation actions', () => {
      const action = service.columnCreateAction('col_id', 'col_name', PropertyType.STRING, ColumnType.CONDITION);

      action.execute(context);
      expect(dt.createAndAddColumnWithId).toHaveBeenCalledWith('col_id', 'col_name', PropertyType.STRING, ColumnType.CONDITION);
      expect(context.updateAgGrid).toHaveBeenCalledTimes(1);

      action.revert(context);
      expect(dt.clearColumn).toHaveBeenCalledWith('col_id');
      expect(context.updateAgGrid).toHaveBeenCalledTimes(2);
    });

    it('is able to create column deletion actions', () => {
      spyOn(column, 'getId').and.returnValue('col_id');
      spyOn(cell, 'getId').and.returnValue('1_col_id');
      spyOn(cell, 'getExpr').and.returnValue('cell_expr');
      const action = service.columnDeleteAction('col_id');

      action.execute(context);
      expect(row.getCell).toHaveBeenCalledWith('col_id');
      expect(dt.clearColumn).toHaveBeenCalledWith('col_id');
      expect(context.updateAgGrid).toHaveBeenCalledTimes(1);

      action.revert(context);
      expect(dt.addColumn).toHaveBeenCalledWith(column);
      expect(dt.setCell).toHaveBeenCalledWith('1_col_id', 'cell_expr');
      expect(context.updateAgGrid).toHaveBeenCalledTimes(2);
    });
  });
});
