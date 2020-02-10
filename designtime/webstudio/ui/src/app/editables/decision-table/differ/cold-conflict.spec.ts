import {
  TestBed,
} from '@angular/core/testing';

import { ConflictType, Differ, DiffResult, DELETE, EDIT, NEW } from './differ';

import { Logger } from '../../../core/logger.service';
import { ColumnType, PropertyType } from '../column';
import { DecisionTable } from '../decision-table';

const dtXml = `<?xml version="1.0" encoding="UTF-8"?>
<Table:Table xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" \
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
    TestBed.configureTestingModule({
      providers: [Logger, Differ]
    });

    differ = TestBed.get(Differ);
    a1 = DecisionTable.fromXml(dtXml);
    a2 = DecisionTable.fromXml(dtXml);
    live = differ.liveDiff(a1, a2);
  });

  describe('can find conflicts among multiple updated copies', () => {
    let a3: DecisionTable;
    beforeEach(() => {
      a3 = DecisionTable.fromXml(dtXml);
    });

    describe('cell conflicts', () => {
      it('can be detected when both copies edited the same cell to differnt values', () => {
        a2.getRule('1').getCell('0').setExpr('ChangedByA2');
        a3.getRule('1').getCell('0').setExpr('ChangedByA3');

        const c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(1);
        const conflict = c.getCellItems().get('1_0');
        expect(conflict).toBeTruthy();
        expect(conflict.a.kind).toBe(EDIT);
        expect(conflict.b.kind).toBe(EDIT);
        expect(conflict.a.lhs).toBe(conflict.b.lhs);
        expect(conflict.a.rhs.getExpr()).toBe('ChangedByA2');
        expect(conflict.b.rhs.getExpr()).toBe('ChangedByA3');
      });

      it('can pass when both copies edited the same cell to same value', () => {
        a2.getRule('1').getCell('0').setExpr('Changed');
        a3.getRule('1').getCell('0').setExpr('Changed');
        a3.getRule('1').getCell('1').setExpr('ANOTHER_VALUE');

        const c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(0);
        const conflict = c.getCellItems().get('1_0');
        expect(conflict).toBeFalsy();
      });

      it('can be detected when both copies created the same cell with different values', () => {
        a2.setCell('1_5', 'NEW_VALUE_BY_A2');
        a3.setCell('1_5', 'NEW_VALUE_BY_A3');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(1);
        const conflict = c.getCellItems().get('1_5');
        expect(conflict).toBeTruthy();
        expect(conflict.a.kind).toBe(NEW);
        expect(conflict.b.kind).toBe(NEW);
        expect(conflict.a.rhs.getExpr()).toBe('NEW_VALUE_BY_A2');
        expect(conflict.b.rhs.getExpr()).toBe('NEW_VALUE_BY_A3');
      });

      it('can pass when both copies created the same cell with same value', () => {
        a2.setCell('1_5', 'NEW_VALUE');
        a3.setCell('1_5', 'NEW_VALUE');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(0);
        const conflict = c.getCellItems().get('1_5');
        expect(conflict).toBeFalsy();
      });

      it('can be detected when a cell is deleted and edited respectively', () => {
        a2.getRule('1').getCell('0').setExpr('ChangedByA2');
        a3.getRule('1').getCell('0').setExpr('');
        let c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(1);
        let conflict = c.getCellItems().get('1_0');
        expect(conflict).toBeTruthy();
        expect(conflict.a.lhs).toBe(conflict.b.lhs);
        expect(conflict.a.kind).toBe(EDIT);
        expect(conflict.b.kind).toBe(DELETE);

        // flip a2 and a3, result should still be legit
        c = differ.conflict(a1, a3, a2);
        expect(c.getCellItems().size).toBe(1);
        conflict = c.getCellItems().get('1_0');
        expect(conflict).toBeTruthy();
        expect(conflict.a.lhs).toBe(conflict.b.lhs);
        expect(conflict.a.kind).toBe(DELETE);
        expect(conflict.b.kind).toBe(EDIT);
      });

      it('can be detected when a cell is created in new column but the rule is deleted', () => {
        const col = a2.createAndAddColumn('CREATED', PropertyType.STRING, ColumnType.CONDITION);
        a2.setCell(`1_${col.getId()}`, 'SET_BY_A2');
        a3.clearRule('1');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(1, 'Shall have 1 cell conflict');
        expect(c.getCellItems().get(`1_${col.getId()}`).type).toBe(ConflictType.CELL_RULE);
        expect(c.getRuleItems().size).toBe(1, 'Shall have 1 rule conflict');
        expect(c.getRuleItems().get('1').resolved).toBeFalsy();
        expect(c.getRuleItems().get('1').type).toBe(ConflictType.RULE);
      });

      it('can be detected when a cell is created in new rule but the column is deleted', () => {
        const rule = a2.createRuleWithAutoId();
        rule.setCell('0', 'SET_BY_A2', a2.getColumn('0').columnType.cellType);
        a3.clearColumn('0');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(1, 'Shall have 1 cell conflict');
        expect(c.getCellItems().get(`${rule.getId()}_0`).resolved).toBeFalsy();
        expect(c.getCellItems().get(`${rule.getId()}_0`).type).toBe(ConflictType.CELL_COLUMN);
        expect(c.getColumnItems().size).toBe(1, 'Shall have 1 column conflict');
        expect(c.getColumnItems().get('0').resolved).toBeFalsy();
        expect(c.getColumnItems().get('0').type).toBe(ConflictType.COLUMN);
      });

      it('shall find rule conflict when cells are created in the same new rule and content are the same', () => {
        a2.setCell('1_2', 'ONLY_A2_SEE_THIS');
        const r1 = a2.createRuleWithAutoId();
        const r2 = a3.createRuleWithAutoId();
        r1.setCell('0', 'SAME', a2.getColumn('0').columnType.cellType);
        r2.setCell('0', 'SAME', a3.getColumn('0').columnType.cellType);
        const c = differ.conflict(a1, a2, a3);
        expect(c.getRuleItems().size).toBe(1, 'Shall have rule conflict');
        expect(c.getCellItems().size).toBe(0, 'Shall not have cell conflict');
      });

      it('shall find rule conflict when adding new rules in different copies', () => {
        const n1 = Math.floor(Math.random() * 100);
        const n2 = Math.floor(Math.random() * 100);
        for (let i = 0; i < n1; i++) {
          a2.createRuleWithAutoId();
        }

        for (let i = 0; i < n2; i++) {
          a3.createRuleWithAutoId();
        }

        const c = differ.conflict(a1, a2, a3);
        expect(c.getRuleItems().size).toBe(n2);
      });

      it('can pass when a cell is deleted in both', () => {
        a2.getRule('1').clearCell('0');
        a3.getRule('1').getCell('0').setExpr('');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(0);
        const conflict = c.getCellItems().get('1_0');
        expect(conflict).toBeFalsy();
      });

    });
    describe('property conflicts', () => {
      it('can be detected when property is created by both copies with different values', () => {
        a2.setProperty('Version_', '2');
        a3.setProperty('Version_', '3');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getPropItems().size).toBe(1);
        const conflict = c.getPropItems().get('Version_');
        expect(conflict.a.kind).toBe(NEW);
        expect(conflict.b.kind).toBe(NEW);
        expect(conflict.a.rhs).toBe('2');
        expect(conflict.b.rhs).toBe('3');
      });
      it('can pass when property is created by both copies with same value', () => {
        a2.setProperty('Version_', '2');
        a3.setProperty('Version_', '2');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getPropItems().size).toBe(0);
      });
      it('can be detected when property is modified by both to different values', () => {
        a2.setProperty('Priority', '2');
        a3.setProperty('Priority', '3');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getPropItems().size).toBe(1);
        const conflict = c.getPropItems().get('Priority');
        expect(conflict.a.kind).toBe(EDIT);
        expect(conflict.b.kind).toBe(EDIT);
        expect(conflict.a.lhs).toBe(conflict.b.lhs);
        expect(conflict.a.rhs).toBe('2');
        expect(conflict.b.rhs).toBe('3');
      });
      it('can pass when property is modified by both to same value', () => {
        a2.setProperty('Priority', '2');
        a3.setProperty('Priority', '2');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getPropItems().size).toBe(0);
      });
      it('can be detected when property is modifed and deleted respectively', () => {
        a2.setProperty('Priority', '2');
        a3.setProperty('Priority', '');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getPropItems().size).toBe(1);
        const conflict = c.getPropItems().get('Priority');
        expect(conflict.a.lhs).toBe(conflict.b.lhs);
        expect(conflict.a.kind).toBe(EDIT);
        expect(conflict.a.rhs).toBe('2');
        expect(conflict.b.kind).toBe(DELETE);
      });
      it('can pass when property is deleted by both', () => {
        a2.clearProperty('Priority');
        a3.setProperty('Priority', '');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getPropItems().size).toBe(0);
      });
    });
    describe('meta conflicts', () => {
      it('can be detected when meta is edited by both copies to different values', () => {
        a2.basePath = '/MODIFIED_BY_A2';
        a3.basePath = '/MODIFIED_BY_A3';
        const c = differ.conflict(a1, a2, a3);
        expect(c.getMetaItems().size).toBe(1);
        const conflict = c.getMetaItems().get('basePath');
        expect(conflict.a.kind).toBe(EDIT);
        expect(conflict.b.kind).toBe(EDIT);
        expect(conflict.a.lhs).toBe(conflict.b.lhs);
        expect(conflict.a.rhs).toBe(a2.basePath);
        expect(conflict.b.rhs).toBe(a3.basePath);
      });
      it('can pass when meta is edited by both to same value', () => {
        a2.basePath = '/MODIFIED';
        a3.basePath = '/MODIFIED';
        const c = differ.conflict(a1, a2, a3);
        expect(c.getMetaItems().size).toBe(0);
      });
    });
    describe('column conflicts', () => {
      it('can be detected when column is created by both copies to different values', () => {
        const colA = a2.createAndAddColumn('CREATED_BY_A2', PropertyType.STRING, ColumnType.CONDITION);
        const colB = a3.createAndAddColumn('CREATED_BY_A3', PropertyType.STRING, ColumnType.CONDITION);
        const c = differ.conflict(a1, a2, a3);
        expect(c.getColumnItems().size).toBe(1);
        const conflict = c.getColumnItems().get('10');
        expect(conflict).toBeTruthy();
        expect(conflict.a.kind).toBe(NEW);
        expect(conflict.b.kind).toBe(NEW);
        expect(conflict.a.rhs).toBe(colA);
        expect(conflict.b.rhs).toBe(colB);
      });

      it('can pass when column is created by both to same value', () => {
        a2.createAndAddColumn('CREATED', PropertyType.STRING, ColumnType.CONDITION);
        a3.createAndAddColumn('CREATED', PropertyType.STRING, ColumnType.CONDITION);
        const c = differ.conflict(a1, a2, a3);
        expect(c.getColumnItems().size).toBe(1);
      });

      it('can be detected when column is modifed differently', () => {
        const colA = a2.getColumn('0');
        colA.propertyType = PropertyType.INT;
        const colB = a3.getColumn('0');
        colB.propertyType = PropertyType.LONG;
        const c = differ.conflict(a1, a2, a3);
        expect(c.getColumnItems().size).toBe(1);
        const conflict = c.getColumnItems().get('0');
        expect(conflict).toBeTruthy();
        expect(conflict.a.kind).toBe(EDIT);
        expect(conflict.b.kind).toBe(EDIT);
        expect(conflict.a.lhs).toBe(conflict.b.lhs);
        expect(conflict.a.rhs).toBe(colA);
        expect(conflict.b.rhs).toBe(colB);
      });
      it('can pass when column is modified to same value', () => {
        const colA = a2.getColumn('0');
        colA.propertyType = PropertyType.INT;
        const colB = a3.getColumn('0');
        colB.propertyType = PropertyType.INT;
        const c = differ.conflict(a1, a2, a3);
        expect(c.getColumnItems().size).toBe(0);
      });
      it('can be detected when column is modifed and deleted respectively', () => {
        const colA = a2.getColumn('0');
        colA.propertyType = PropertyType.INT;
        a3.clearColumn('0');
        let c = differ.conflict(a1, a2, a3);
        expect(c.getColumnItems().size).toBe(1);
        let conflict = c.getColumnItems().get('0');
        expect(conflict).toBeTruthy();
        expect(conflict.a.kind).toBe(EDIT);
        expect(conflict.b.kind).toBe(DELETE);
        expect(conflict.a.lhs).toBe(conflict.b.lhs);
        expect(conflict.a.rhs).toBe(colA);
        // flict a2 and a3 should still work
        c = differ.conflict(a1, a3, a2);
        expect(c.getColumnItems().size).toBe(1);
        conflict = c.getColumnItems().get('0');
        expect(conflict).toBeTruthy();
        expect(conflict.a.kind).toBe(DELETE);
        expect(conflict.b.kind).toBe(EDIT);
        expect(conflict.a.lhs).toBe(conflict.b.lhs);
        expect(conflict.b.rhs).toBe(colA);
      });
      it('can result in cell conflict as columns deletion implies cell deletion', () => {
        const cell = a2.getRule('1').getCell('0');
        cell.setExpr('REVISED');
        a3.clearColumn('0');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(1);
        expect(c.getCellItems().get('1_0').type).toBe(ConflictType.CELL_COLUMN);
      });
      it('can be delected as multiple cell conflicts because mulitple cells in a column', () => {
        a2.setCell('1_0', 'SET_BYA2');
        a2.setCell('2_0', 'SET_BYA3');
        a3.clearColumn('0');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getColumnItems().size).toBe(1);
        // we will got cell difference as well;
        expect(c.getCellItems().size).toBe(2);
      });
    });
    describe('rule conflicts', () => {
      it('can be detected when rule is created by both copies to different values', () => {
        const r1 = a2.createRuleWithAutoId();
        const r2 = a3.createRuleWithAutoId();
        r1.setCell('0', 'SET_BY_A2', a2.getColumn('0').columnType.cellType);
        r2.setCell('0', 'SET_BY_A3', a3.getColumn('0').columnType.cellType);
        const c = differ.conflict(a1, a2, a3);
        expect(c.getRuleItems().size).toBe(1);
        // we will got cell difference as well;
        expect(c.getCellItems().size).toBe(1);
      });
      it('can be delected as multiple cell conflicts because mulitple cells in a rule', () => {
        a2.setCell('1_0', 'SET_BYA2');
        a2.setCell('1_1', 'SET_BYA3');
        a3.clearRule('1');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getRuleItems().size).toBe(1);
        // we will got cell difference as well;
        expect(c.getCellItems().size).toBe(2);
      });
      it('can result in cell difference as rule deletion implies cell deletion', () => {
        const cell = a2.getRule('1').getCell('0');
        cell.setExpr('REVISED');
        a3.clearRule('1');
        const c = differ.conflict(a1, a2, a3);
        // we will got cell difference as well;
        expect(c.getCellItems().size).toBe(1);
        expect(c.getCellItems().get('1_0').type).toBe(ConflictType.CELL_RULE);
      });
    });
    describe('rule and column conflicts', () => {
      it('can be detected when rule is created with new cell but the columns is deleted', () => {
        const r1 = a2.createRuleWithAutoId();
        r1.setCell('0', 'SET_BY_A2', a2.getColumn('0').columnType.cellType);
        a3.clearColumn('0');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(1);
      });
      it('can pass when remove both row and column', () => {
        a2.clearColumn('0');
        a3.clearRule('1');
        const c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(0);
        expect(c.getLhsDiff().getCellItem('1_0')).toBeTruthy();
        expect(c.getRhsDiff().getCellItem('1_0')).toBeTruthy();
      });
      it('can be deleted as CELL_BOTH and BOTH_CELL conflict when a cell is removed by rule and column in the other copy', () => {
        a2.clearColumn('0');
        a2.clearRule('1');

        a3.setCell('1_0', 'SET_BY_A3');
        let c = differ.conflict(a1, a2, a3);
        expect(c.getCellItems().size).toBe(1);
        expect(c.getRuleItems().size).toBe(1);
        expect(c.getColumnItems().size).toBe(1);
        expect(c.getLhsDiff().getCellItem('1_0')).toBeTruthy();
        expect(c.getRhsDiff().getCellItem('1_0')).toBeTruthy();

        expect(c.getCellItems().get('1_0').type).toBe(ConflictType.BOTH_CELL);

        c = differ.conflict(a1, a3, a2);
        expect(c.getCellItems().get('1_0').type).toBe(ConflictType.CELL_BOTH);
      });
    });
  });
});
