import {
  TestBed
} from '@angular/core/testing';

import * as _ from 'lodash';

import { Differ, DiffResult, DELETE, EDIT, NEW } from './differ';

import { Logger } from '../../../core/logger.service';
import { ColumnType, PropertyType } from '../column';
import { DecisionTable } from '../decision-table';

const dtXml = `<?xml version="1.0" encoding="UTF-8"?>
<Table:Table xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI"
xmlns:Table="http:///com/tibco/cep/decision/table/model/DecisionTable.ecore" name="SB-29153.sbdt" folder="/">
<md>
<prop name="Priority" value="5"/>
<prop name="EffectiveDate" type="String" value="2016-05-02 12:22:06-0400"/>
<prop name="ExpiryDate" type="String" value="2016-05-02 12:22:08-0500"/>
<prop name="Version" type="String" value="1.0"/>
</md>
<decisionTable>
<rule id="1">
  <cond id="1_0" colId="0" expr="abc || def"/>
  <cond id="1_1" colId="1" expr="10"/>
  <cond id="1_2" colId="2" expr=""/>
  <cond id="1_3" colId="3" expr=""/>
  <act id="1_4" colId="4" expr="ABC"/>
  <cond id="1_8" colId="8" expr="false"/>
  <cond id="1_9" colId="9" expr="10 || 20"/>
</rule>
<rule id="2">
  <cond id="2_0" colId="0" expr="def"/>
  <cond id="2_1" colId="1" expr="10"/>
  <cond id="2_3" colId="3" expr=""/>
</rule>
<rule id="3">
  <cond id="3_0" colId="0" expr="def"/>
  <cond id="3_1" colId="1" expr="10"/>
  <cond id="3_2" colId="2" expr=""/>
  <cond id="3_3" colId="3" expr=""/>
  <act id="3_4" colId="4" expr="ABC"/>
  <act id="3_5" colId="5" expr=""/>
  <cond id="3_9" colId="9" expr="20"/>
</rule>
<columns>
  <column id="0" name="STR_COND_FLD" propertyPath="STR_COND_FLD" columnType="CONDITION"/>
  <column id="1" name="INT_COND_FLD" propertyPath="INT_COND_FLD" propertyType="1" columnType="CONDITION"/>
  <column id="2" name="TS_COND_FLD" propertyPath="TS_COND_FLD" propertyType="5" columnType="CONDITION"/>
  <column id="3" name="TUP_COND_FLD.NESTED_INT_FLD" propertyPath="TUP_COND_FLD.NESTED_INT_FLD" propertyType="1" columnType="CONDITION"/>
  <column id="8" name="BOOL_FLD" propertyPath="BOOL_FLD" propertyType="4" columnType="CONDITION"/>
  <column id="9" name="DOUBLE_FLD" propertyPath="DOUBLE_FLD" propertyType="3" columnType="CONDITION"/>
  <column id="4" name="STR_FLD" propertyPath="STR_FLD" columnType="ACTION"/>
  <column id="5" name="INT_FLD" propertyPath="INT_FLD" propertyType="1" columnType="ACTION"/>
  <column id="6" name="TS_FLD" propertyPath="TS_FLD" propertyType="5" columnType="ACTION"/>
  <column id="7" name="TUP_FLD.NEST_INT_FLD" propertyPath="TUP_FLD.NEST_INT_FLD" propertyType="1" columnType="ACTION"/>
</columns>
</decisionTable>
</Table:Table>`;

describe('differ', () => {
  let differ: Differ;
  let a1: DecisionTable;
  let a2: DecisionTable;
  let live: DiffResult;
  beforeEach(() => {
    TestBed.configureTestingModule({ providers: [Logger, Differ] });
    differ = TestBed.get(Differ);
    a1 = DecisionTable.fromXml(dtXml);
    a2 = DecisionTable.fromXml(dtXml);
    live = differ.liveDiff(a1, a2);
  });

  describe('can find differences between two decision-tables', () => {
    it('can diff two decision-tables', () => {
      const result = differ.diff(a1, a2);
      expect(result).toBeTruthy();
    });

    it('can diff when cell value changed', () => {
      const cell = a2.getRule('1').getCell('0');
      cell.setExpr('Changed');

      const diff = differ.diff(a1, a2);
      const cellDiff1 = live.getCellItem('1_0');
      const cellDiff2 = diff.getCellItem('1_0');

      expect(diff.size()).toBe(1);
      expect(_.isEqual(cellDiff1, cellDiff2)).toBe(true);

      expect(cellDiff1.lhs.getExpr()).toBe('abc || def');
      expect(cellDiff1.rhs.getExpr()).toBe('Changed');
      expect(cellDiff1.kind).toBe(EDIT);
    });

    it('can diff when cell value set to empty', () => {
      const cell = a2.getRule('1').getCell('0');
      cell.setExpr('');

      const diff = differ.diff(a1, a2);
      const cellDiff1 = live.getCellItem('1_0');
      const cellDiff2 = diff.getCellItem('1_0');

      expect(diff.size()).toBe(1);
      expect(_.isEqual(cellDiff1, cellDiff2)).toBe(true);

      expect(cellDiff1.lhs.getExpr()).toBe('abc || def');
      expect(cellDiff1.rhs).toBeFalsy();
      expect(cellDiff1.kind).toBe(DELETE);
    });

    it('can diff when cell removed completely', () => {
      const rule = a2.getRule('1');
      rule.clearCell('0');
      const diff = differ.diff(a1, a2);
      const cellDiff1 = live.getCellItem('1_0');
      const cellDiff2 = diff.getCellItem('1_0');

      expect(diff.size()).toBe(1);
      expect(_.isEqual(cellDiff1, cellDiff2)).toBe(true);

      expect(cellDiff1.lhs.getExpr()).toBe('abc || def');
      expect(cellDiff1.rhs).toBeFalsy();
      expect(cellDiff1.kind === DELETE);
    });

    it('can diff when new cell added', () => {
      const rule = a2.getRule('1');
      rule.createOrUpdateCell('5', 'NEW_VALUE', a2.getColumn('5').columnType.cellType);
      const diff = differ.diff(a1, a2);
      const cellDiff1 = live.getCellItem('1_5');
      const cellDiff2 = diff.getCellItem('1_5');

      expect(diff.size()).toBe(1);
      expect(_.isEqual(cellDiff1, cellDiff2)).toBe(true);

      expect(cellDiff1.lhs).toBeFalsy();
      expect(cellDiff1.rhs.getExpr()).toBe('NEW_VALUE');
      expect(cellDiff1.kind).toBe(NEW);
    });

    it('can diff when new cell added but expr empty', () => {
      const rule = a2.getRule('1');
      rule.createOrUpdateCell('5', '', a2.getColumn('5').columnType.cellType);
      const diff = differ.diff(a1, a2);
      const cellDiff1 = live.getCellItem('1_5');
      const cellDiff2 = diff.getCellItem('1_5');

      expect(diff.size()).toBe(0);
      expect(cellDiff1).toBeFalsy();
      expect(cellDiff2).toBeFalsy();
    });

    it('can diff when meta changes', () => {
      a2.name = 'CHANGED';
      const diff = differ.diff(a1, a2);
      const d1 = live.getMetaItem('name');
      const d2 = diff.getMetaItem('name');
      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();
      expect(_.isEqual(d1, d2)).toBe(true);

      expect(d1.lhs).toBe('SB-29153.sbdt');
      expect(d2.rhs).toBe('CHANGED');
    });

    it('can diff when meta set to empty', () => {
      a2.basePath = '';
      const diff = differ.diff(a1, a2);
      const d1 = live.getMetaItem('basePath');
      const d2 = diff.getMetaItem('basePath');
      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();
      expect(JSON.stringify(d2)).toBe(JSON.stringify(d1));

      expect(d1.lhs).toBe('/');
      expect(d2.rhs).toBe('');
      expect(d1.kind).toBe(EDIT);
    });

    it('can diff when new property added', () => {
      a2.setProperty('Version_', '10');
      const diff = differ.diff(a1, a2);
      const d1 = live.getPropItem('Version_');
      const d2 = diff.getPropItem('Version_');
      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();
      expect(_.isEqual(d1, d2)).toBe(true);

      expect(d1.kind).toBe(NEW);
      expect(d2.rhs).toBe('10');
    });

    it('can diff when new property added but is boolean type', () => {
      a2.setProperty(DecisionTable.SINGLE_EXEC_PARAM, 'true');
      const diff = differ.diff(a1, a2);
      const d1 = live.getPropItem(DecisionTable.SINGLE_EXEC_PARAM);
      const d2 = diff.getPropItem(DecisionTable.SINGLE_EXEC_PARAM);
      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();
      expect(_.isEqual(d1, d2)).toBe(true);

      expect(d1.kind).toBe(EDIT);
      expect(d2.lhs).toBe('false');
      expect(d2.rhs).toBe('true');
    });

    it('can diff when new property added but val is empty', () => {
      a2.setProperty('Version_', '');
      const diff = differ.diff(a1, a2);
      const d1 = live.getPropItem('Version_');
      const d2 = diff.getPropItem('Version_');
      expect(d1).toBeFalsy();
      expect(d2).toBeFalsy();
    });

    it('can diff when property was changed', () => {
      a2.setProperty('Priority', '10');
      const diff = differ.diff(a1, a2);
      const d1 = live.getPropItem('Priority');
      const d2 = diff.getPropItem('Priority');
      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();
      expect(d1.kind).toBe(EDIT);
      expect(JSON.stringify(d2)).toBe(JSON.stringify(d1));
      expect(d2.rhs).toBe('10');
    });

    it('can diff when property was set to empty', () => {
      a2.setProperty('Priority', '');
      const diff = differ.diff(a1, a2);
      const d1 = live.getPropItem('Priority');
      const d2 = diff.getPropItem('Priority');
      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();
      expect(d1.kind).toBe(DELETE);
      expect(_.isEqual(d1, d2)).toBe(true);
      expect(d2.lhs).toBe('5');
    });

    it('can diff when property was removed completely', () => {
      a2.clearProperty('Priority');
      const diff = differ.diff(a1, a2);
      const d1 = live.getPropItem('Priority');
      const d2 = diff.getPropItem('Priority');
      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();
      expect(d1.kind).toBe(DELETE);
      expect(_.isEqual(d1, d2)).toBe(true);
      expect(d2.lhs).toBe('5');
    });

    it('can diff when new row added', () => {
      a2.createAndAddRule('4');
      const diff = differ.diff(a1, a2);
      const d1 = live.getRuleItem('4');
      const d2 = diff.getRuleItem('4');

      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();
      expect(d1.kind).toBe(NEW);
      expect(_.isEqual(d1, d2)).toBe(true);
      expect(d2.rhs).toBe(a2.getRule('4'));
    });

    it('can diff when new row added with new cells', () => {
      const rule = a2.createAndAddRule('4');
      rule.createOrUpdateCell('1', 'NEW_CELL', a2.getColumn('1').columnType.cellType);
      const diff = differ.diff(a1, a2);
      const d1 = live.getRuleItem('4');
      const d2 = diff.getRuleItem('4');

      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();
      expect(d1.kind).toBe(NEW);
      expect(_.isEqual(d1, d2)).toBe(true);
      expect(d2.rhs).toBe(a2.getRule('4'));

      // cells should be different as well
      const d3 = live.getCellItem('4_1');
      const d4 = diff.getCellItem('4_1');

      expect(d3).toBeTruthy();
      expect(d4).toBeTruthy();
      expect(_.isEqual(d3, d4)).toBe(true);
      expect(d3.kind).toBe(NEW);
      expect(d3.rhs).toBe(rule.getCell('1'));
    });

    it('can diff when row was deleted', () => {
      const id = '3';
      const lhs = a1.getRule(id);
      a2.clearRule(id);
      const diff = differ.diff(a1, a2);
      const d1 = live.getRuleItem('3');
      const d2 = diff.getRuleItem('3');

      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();
      expect(d1.kind).toBe(DELETE);
      expect(_.isEqual(d1, d2)).toBe(true);
      expect(d2.lhs).toBe(lhs);

      // cells should be different as well
      const d3 = live.getCellItem('3_1');
      const d4 = diff.getCellItem('3_1');

      expect(d3).toBeTruthy();
      expect(d4).toBeTruthy();
      expect(_.isEqual(d3.kind, d4.kind)).toBe(true);
      expect(d3.kind).toBe(DELETE);
      expect(d3.lhs).toBe(lhs.getCell('1'));
    });

    it('can diff when new column was added', () => {
      const colName = 'NEW_COLUMN';
      const col = a2.createAndAddColumn(colName, PropertyType.STRING, ColumnType.CONDITION);
      const diff = differ.diff(a1, a2);
      const d1 = live.getColumnItem(col.id);
      const d2 = diff.getColumnItem(col.id);

      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();
      expect(d1.kind).toBe(NEW);
      expect(_.isEqual(d1, d2)).toBe(true);
      expect(d2.rhs).toBe(col);
    });

    it('can diff when column was removed', () => {
      const colId = '0';
      const col = a1.getColumn(colId);
      expect(col).toBeTruthy();
      const cell = a1.getRule('1').getCell('0');
      expect(cell).toBeTruthy();

      a2.clearColumn(colId);
      const diff = differ.diff(a1, a2);

      const d1 = live.getColumnItem(colId);
      const d2 = diff.getColumnItem(colId);
      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();

      expect(d1.kind).toBe(DELETE);
      expect(_.isEqual(d1, d2)).toBe(true);
      expect(d1).toEqual(d2);
      expect(d2.lhs).toBe(col);

      const d3 = live.getCellItem('1_0');
      const d4 = diff.getCellItem('1_0');
      expect(d3).toBeTruthy();
      expect(d4).toBeTruthy();

      expect(d3.kind).toBe(DELETE);
      expect(_.isEqual(d3, d4)).toBe(true);
      expect(d3.lhs).toBe(cell);
    });

    it('can diff when column was modified', () => {
      const colId = '2';
      const col = a2.getColumn(colId);
      expect(col).toBeTruthy();

      col.propertyType = PropertyType.INT;
      const diff = differ.diff(a1, a2);

      const d1 = live.getColumnItem(colId);
      const d2 = diff.getColumnItem(colId);
      expect(d1).toBeTruthy();
      expect(d2).toBeTruthy();

      expect(d1.kind).toBe(EDIT);
      expect(_.isEqual(d1, d2)).toBe(true);
      expect(d1.lhs).toBe(d2.lhs);
      expect(d1.rhs).toBe(d2.rhs);
      expect(d1.lhs).toBe(a1.getColumn(colId));
      expect(d1.rhs).toBe(col);
    });
  });
});
