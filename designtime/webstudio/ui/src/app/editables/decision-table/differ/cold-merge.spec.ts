import {
  TestBed
} from '@angular/core/testing';

import { Differ, DiffResult, DELETE, MergeResult, Resolution } from './differ';

import { Logger } from '../../../core/logger.service';
import { Column, ColumnType, PropertyType } from '../column';
import { DecisionTable } from '../decision-table';
import { Rule } from '../rule';

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
  let base: DecisionTable;
  let lhs: DecisionTable;
  let rhs: DecisionTable;

  beforeEach(() => {
    TestBed.configureTestingModule({ providers: [Logger, Differ] });
    differ = TestBed.get(Differ);
    base = DecisionTable.fromXml(dtXml);
    lhs = DecisionTable.fromXml(dtXml);
    rhs = DecisionTable.fromXml(dtXml);
  });

  const testsuite = (lhsFirst: boolean) => {
    /** shall not use it in new tests */
    let lop;
    let rop;
    const exec = () => {
      const run = (op) => {
        if (op) {
          op();
        }
      };
      if (lhsFirst) {
        run(lop);
        run(rop);
      } else {
        run(rop);
        run(lop);
      }
      lop = null;
      rop = null;
    };

    /** shall not use it in new tests */
    const runDiffCheck = (merge: MergeResult, diffCheck: (lhs: DiffResult, rhs: DiffResult) => void) => {
      if (lhsFirst) {
        diffCheck(merge.getLhsDiff(), merge.getRhsDiff());
      } else {
        diffCheck(merge.getRhsDiff(), merge.getLhsDiff());
      }
    };

    /** result checking util function */
    const runCheck = (check: (first: any, second: any) => void, first, second) => {
      if (lhsFirst) {
        check(first, second);
      } else {
        check(second, first);
      }
    };

    const merge = () => lhsFirst ? differ.merge(base, lhs, rhs) : differ.merge(base, rhs, lhs);

    const diffResult = (result: MergeResult) => {
      if (lhsFirst) {
        return {
          lhs: result.getLhsDiff(),
          rhs: result.getRhsDiff()
        };
      } else {
        return {
          lhs: result.getRhsDiff(),
          rhs: result.getLhsDiff()
        };
      }
    };

    describe('when there is no conflicts', () => {
      it('can merge non-conflict property modifications', () => {
        lhs.setProperty(DecisionTable.PRIORITY_PARAM, '9');
        rhs.setProperty(DecisionTable.EXPIRY_TIME_PARAM, '5.23');

        const result = merge();
        const merged = result.getMerged();
        // make sure we see property changes are merged
        expect(merged.getProperty(DecisionTable.PRIORITY_PARAM)).toBe('9');
        expect(merged.getProperty(DecisionTable.EXPIRY_TIME_PARAM)).toBe('5.23');

        // make sure all others are intact
        lop = () => base.setProperty(DecisionTable.PRIORITY_PARAM, '9');
        rop = () => base.setProperty(DecisionTable.EXPIRY_TIME_PARAM, '5.23');
        exec();
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('can merge modification if they are the same', () => {
        // modification on same property but they are the same...
        lhs.setProperty(DecisionTable.EXPIRY_TIME_PARAM, '200-10-10');
        rhs.setProperty(DecisionTable.EXPIRY_TIME_PARAM, '200-10-10');

        const result = merge();
        const merged = result.getMerged();

        // it should be recognized as non-conflict
        expect(result.getConflict().getPropItems().get(DecisionTable.EXPIRY_TIME_PARAM)).toBeFalsy();

        expect(merged.getProperty(DecisionTable.EXPIRY_TIME_PARAM)).toBe('200-10-10');
      });

      it('can merge column modifications', () => {
        const suffix = '_MODIFIED';
        lhs.getColumn('0').name += suffix;
        rhs.getColumn('1').name += suffix;
        const result = merge();
        const merged = result.getMerged();

        // it should be recognized as non-conflict
        expect(result.getConflict().getColumnItems().size).toBe(0);

        // modify base to have both update
        lop = () => base.getColumn('0').name += suffix;
        rop = () => base.getColumn('1').name += suffix;
        exec();

        // expect they are the same
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('can merge column deletions', () => {
        lhs.clearColumn('0');
        rhs.clearColumn('1');

        const result = merge();
        const merged = result.getMerged();

        // it should be recognized as non-conflict
        expect(result.getConflict().getColumnItems().size).toBe(0);

        // modify base to have both update
        lop = () => base.clearColumn('0');
        rop = () => base.clearColumn('1');
        exec();

        // expect they are the same
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('can merge same column deletion', () => {
        lhs.clearColumn('0');
        rhs.clearColumn('0');

        const result = merge();
        const merged = result.getMerged();

        // it should be recognized as non-conflict
        expect(result.getConflict().getColumnItems().size).toBe(0);
        expect(result.getConflict().getCellItems().size).toBe(0);

        base.clearColumn('0');
        // expect they are the same
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('can merge multiple column addition with their cells', () => {
        const lhsCol = lhs.createAndAddColumn('NEWLY_ADDED_BY_LHS', PropertyType.STRING, ColumnType.EXPR_CONDITION);
        const rhsCol = rhs.createAndAddColumn('NEWLY_ADDED_BY_RHS', PropertyType.STRING, ColumnType.EXPR_CONDITION);

        // they shall have same col id, as they are added separately into different table.
        expect(lhsCol.getId()).toBe(rhsCol.getId());

        lhs.getRule('1').setCell(lhsCol.getId(), 'SET_BY_LHS', lhsCol.columnType.cellType);
        rhs.getRule('1').setCell(rhsCol.getId(), 'SET_BY_RHS', rhsCol.columnType.cellType);

        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        expect(result.getConflict().getColumnItems().size).toBe(1);
        expect(result.getConflict().getCellItems().size).toBe(0);
        expect(result.getConflict().getColumnItems().get(lhsCol.getId()).resolved).toBe(true, 'Column conflict shall be resolved');

        // same modifications go to base
        let col1: Column;
        lop = () => {
          col1 = base.createAndAddColumn('NEWLY_ADDED_BY_LHS', PropertyType.STRING, ColumnType.EXPR_CONDITION);
          base.getRule('1').setCell(col1.getId(), 'SET_BY_LHS', col1.columnType.cellType);
        };
        let col2: Column;
        rop = () => {
          col2 = base.createAndAddColumn('NEWLY_ADDED_BY_RHS', PropertyType.STRING, ColumnType.EXPR_CONDITION);
          base.getRule('1').setCell(col2.getId(), 'SET_BY_RHS', col2.columnType.cellType);
        };
        exec();

        // validate diff shall also be corrected during resolution
        const diffCheck = (ld, rd) => {
          expect(ld.getColumnItem(col1.getId()).rhs).toEqual(col1);
          expect(rd.getColumnItem(col2.getId()).rhs).toEqual(col2);
          expect(ld.getCellItem(`1_${col1.getId()}`).rhs.getExpr()).toBe('SET_BY_LHS');
          expect(rd.getCellItem(`1_${col2.getId()}`).rhs.getExpr()).toBe('SET_BY_RHS');
        };
        runDiffCheck(result, diffCheck);

        expect(merged.toXml()).toBe(base.toXml());
      });

      it('can merge multiple row addition with their cells', () => {
        const lhsRule = lhs.createRuleWithAutoId();
        lhsRule.setCell('0', 'SET_BY_LHS', lhs.getColumn('0').columnType.cellType);
        const rhsRule = rhs.createRuleWithAutoId();
        rhsRule.setCell('0', 'SET_BY_RHS', rhs.getColumn('0').columnType.cellType);

        expect(lhsRule.getId()).toBe(rhsRule.getId());

        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // it should be recognized as conflict but resolved
        expect(result.getConflict().getRuleItems().size).toBe(1);
        expect(result.getConflict().getCellItems().size).toBe(0);
        expect(result.getConflict().getRuleItems().get(lhsRule.getId()).resolved).toBe(true);

        const ruleCnt = base.getRules().length;
        let rule1: Rule;
        lop = () => {
          rule1 = base.createRuleWithAutoId();
          rule1.setCell('0', 'SET_BY_LHS', base.getColumn('0').columnType.cellType);
          expect(base.getCell(`${rule1.getId()}_0`).getExpr()).toBe('SET_BY_LHS');
        };

        let rule2: Rule;
        rop = () => {
          rule2 = base.createRuleWithAutoId();
          rule2.setCell('0', 'SET_BY_RHS', base.getColumn('0').columnType.cellType);
          expect(base.getCell(`${rule2.getId()}_0`).getExpr()).toBe('SET_BY_RHS');
        };
        exec();
        // validate diff shall also be corrected during resolution
        const diffCheck = (ld, rd) => {
          expect(ld.getRuleItem(rule1.getId()).rhs.toXml(0)).toEqual(rule1.toXml(0));
          expect(rd.getRuleItem(rule2.getId()).rhs.toXml(0)).toEqual(rule2.toXml(0));
          expect(ld.getCellItem(`${rule1.getId()}_0`).rhs.getExpr()).toBe('SET_BY_LHS');
          expect(rd.getCellItem(`${rule2.getId()}_0`).rhs.getExpr()).toBe('SET_BY_RHS');
        };

        runDiffCheck(result, diffCheck);

        expect(base.getRules().length).toBe(ruleCnt + 2);
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('can merge rows deletions', () => {
        lhs.clearRule('1');
        rhs.clearRule('2');

        const result = merge();
        const merged = result.getMerged();

        // there should be no conflict
        expect(result.getConflict().getRuleItems().size).toBe(0);

        // check result
        lop = () => base.clearRule('1');
        rop = () => base.clearRule('2');
        exec();
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('can merge same row deletions', () => {
        lhs.clearRule('1');
        rhs.clearRule('1');

        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // there should be no conflict
        expect(result.getConflict().getRuleItems().size).toBe(0);

        // check result
        lop = () => base.clearRule('1');
        rop = () => base.clearRule('1');
        exec();
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('shall be able to merge modifications on different cells', () => {
        lhs.setCell('1_1', 'SET_BY_LHS');
        rhs.setCell('1_2', 'SET_BY_RHS');

        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // there should be no conflict
        expect(result.getConflict().getCellItems().size).toBe(0);

        // check result
        base.setCell('1_1', 'SET_BY_LHS');
        base.setCell('1_2', 'SET_BY_RHS');
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('shall be able to merge same-cell update if they are the same', () => {
        lhs.setCell('1_2', 'SET_BY_TEST');
        rhs.setCell('1_2', 'SET_BY_TEST');

        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // there should be no conflict
        expect(result.getConflict().getCellItems().size).toBe(0);

        // check result
        base.setCell('1_2', 'SET_BY_TEST');
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('shall be able to merge same-cell deletion if they are the same', () => {
        lhs.clearCell('1_0');
        rhs.clearCell('1_0');

        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // there should be no conflict
        expect(result.getConflict().getCellItems().size).toBe(0);

        // check result
        base.clearCell('1_0');
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('shall be able to merge row deletion with column deletion', () => {
        lhs.clearRule('1');
        rhs.clearColumn('0');

        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // there should be no conflict
        expect(result.getConflict().getCellItems().size).toBe(0);

        // check result
        base.clearRule('1');
        base.clearColumn('0');
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('shall be able to merge row addition and column addition with new cells', () => {
        const lhsRule = lhs.createRuleWithAutoId();
        lhsRule.setCell('0', 'SET_BY_LHS', lhs.getColumn('0').columnType.cellType);
        const rhsCol = rhs.createAndAddColumn('NEWLY_ADDED_BY_RHS', PropertyType.STRING, ColumnType.EXPR_CONDITION);
        rhs.setCell(`1_${rhsCol.getId()}`, 'SET_BY_RHS');

        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // there should be no conflict
        expect(result.getConflict().getCellItems().size).toBe(0);

        // check result
        base.createRuleWithAutoId();
        // shall have same rule id
        base.setCell(`${lhsRule.getId()}_0`, 'SET_BY_LHS');
        base.createAndAddColumn('NEWLY_ADDED_BY_RHS', PropertyType.STRING, ColumnType.EXPR_CONDITION);
        // shall have same col id
        base.setCell(`1_${rhsCol.getId()}`, 'SET_BY_RHS');
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('shall be able to merge row addition and column deletion', () => {
        const lhsRule = lhs.createRuleWithAutoId();
        lhsRule.setCell('0', 'SET_BY_LHS', lhs.getColumn('0').columnType.cellType);
        rhs.clearColumn('1');

        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // there should be an unresolved conflict
        expect(result.getConflict().getCellItems().size).toBe(0);

        const addedRule = base.createRuleWithAutoId();
        addedRule.setCell('0', 'SET_BY_LHS', base.getColumn('0').columnType.cellType);
        base.clearColumn('1');
        expect(merged.toXml()).toBe(base.toXml());
      });

      it('shall be able to merge multiple row additions', () => {
        const n1 = Math.floor(Math.random() * 100);
        const n2 = Math.floor(Math.random() * 100);
        for (let i = 0; i < n1; i++) {
          lhs.createRuleWithAutoId();
        }
        for (let i = 0; i < n2; i++) {
          rhs.createRuleWithAutoId();
        }
        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // there should be an unresolved conflict
        expect(result.getConflict().getCellItems().size).toBe(0);
        expect(merged.getRules().length).toBe(base.getRules().length + n1 + n2);

        for (let i = 0; i < n1 + n2; i++) {
          base.createRuleWithAutoId();
        }
        expect(merged.toXml()).toBe(base.toXml());
        runCheck((i, j) => {
          expect(result.getLhsDiff().getRuleItems().size).toBe(i);
          expect(result.getRhsDiff().getRuleItems().size).toBe(j);
        }, n1, n2);
      });

      it('shall be able to merge multiple column additions', () => {
        const n1 = Math.floor(Math.random() * 100);
        const n2 = Math.floor(Math.random() * 100);
        for (let i = 0; i < n1; i++) {
          lhs.createAndAddColumn('col' + i,
            PropertyType.TYPES[i % PropertyType.TYPES.length],
            ColumnType.TYPES[i % ColumnType.TYPES.length]);
        }
        for (let i = 0; i < n2; i++) {
          rhs.createAndAddColumn('col' + i,
            PropertyType.TYPES[i % PropertyType.TYPES.length],
            ColumnType.TYPES[i % ColumnType.TYPES.length]);
        }
        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // there should be an unresolved conflict
        expect(result.getConflict().getCellItems().size).toBe(0);
        expect(merged.getColumns().length).toBe(base.getColumns().length + n1 + n2);

        const mergeCheck = (i, j) => {
          for (let k = 0; k < i; k++) {
            base.createAndAddColumn('col' + k,
              PropertyType.TYPES[k % PropertyType.TYPES.length],
              ColumnType.TYPES[k % ColumnType.TYPES.length]);
          }
          for (let k = 0; k < j; k++) {
            base.createAndAddColumn('col' + k,
              PropertyType.TYPES[k % PropertyType.TYPES.length],
              ColumnType.TYPES[k % ColumnType.TYPES.length]);
          }
          expect(result.getLhsDiff().getColumnItems().size).toBe(i);
          expect(result.getRhsDiff().getColumnItems().size).toBe(j);
          expect(merged.toXml()).toBe(base.toXml());
        };
        runCheck(mergeCheck, n1, n2);
      });

      it('shall be able to merge rule additions whose content are the same', () => {
        const lhsRule = lhs.createRuleWithAutoId();
        lhsRule.setCell('2', 'SAME', lhs.getColumn('2').columnType.cellType);
        const rhsRule = rhs.createRuleWithAutoId();
        rhsRule.setCell('2', 'SAME', rhs.getColumn('2').columnType.cellType);

        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // however the value shall still appear in the merged table because later
        // conflict resolution might just choose to recover the deletion
        const mergeCheck = (first, second) => {
          expect(merged.getCell(`${first}_2`).getExpr()).toBe('SAME');
          expect(merged.getCell(`${second}_2`).getExpr()).toBe('SAME');

          expect(result.getConflict().getCellItems().size).toBe(0, 'shall not have cell conflict');
          expect(result.getConflict().getCellItems().get(`${first}_2`)).toBeFalsy('shall have resolved cell conflict');
          expect(result.getConflict().getRuleItems().get(first).resolved).toBeTruthy('shall have rule conflict resolved');
        };

        runCheck(mergeCheck, lhsRule.getId(), rhsRule.getId());
      });

      it('shall be able to merge column addition and row deletion', () => {
        const lhsCol = lhs.createAndAddColumn('NEWLY_ADDED_BY_LHS', PropertyType.STRING, ColumnType.EXPR_CONDITION);
        lhs.setCell(`1_${lhsCol.getId()}`, 'SET_BY_RHS');
        rhs.clearRule('2');

        // now do the merge
        const result = merge();
        const merged = result.getMerged();

        // there should be an unresolved conflict
        expect(result.getConflict().getCellItems().size).toBe(0);

        // check result
        const addedCol = base.createAndAddColumn('NEWLY_ADDED_BY_LHS', PropertyType.STRING, ColumnType.EXPR_CONDITION);
        base.setCell(`1_${addedCol.getId()}`, 'SET_BY_RHS');
        base.clearRule('2');
        expect(merged.toXml()).toBe(base.toXml());
      });
    });

    describe('when there are deleted columns with modified cells', () => {
      let result: MergeResult;
      beforeEach(() => {
        lhs.clearColumn('0');
        rhs.setCell('1_0', 'SET_BY_RHS');
        // now do the merge
        result = merge();
      });

      it('shall have conflicts after merge', () => {
        // it should be recognized as conflict and unresolved
        expect(result.getConflict().getCellItems().size).toBe(1);
        expect(result.getConflict().getCellItems().get('1_0').resolved).toBe(false);
      });

      it('and we shall be able to resolve the conflict by choosing lhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_0', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getColumn('0')).toBeFalsy();
          result.getMerged().getRules().forEach(rule => {
            expect(rule.getCell('0')).toBeFalsy();
            const diff = diffResult(result);
            expect(diff.lhs.getColumnItem('0').kind).toBe(DELETE);
            expect(diff.rhs.getCellItem('1_0')).toBeFalsy();
          });
        }, Resolution.LHS, Resolution.RHS);
      });

      it('and we shall be able to resolve the conflict by choosing rhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_0', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getColumn('0')).toBeTruthy('the column deletion shall have been recovered');
          expect(result.getMerged().getCell('1_0').getExpr()).toBe('SET_BY_RHS');
          const diff = diffResult(result);
          expect(diff.lhs.getColumnItem('0')).toBeFalsy();
          expect(diff.rhs.getCellItem('1_0')).toBeTruthy();
        }, Resolution.RHS, Resolution.LHS);
      });
    });

    describe('when there are deleted rules with modified cells', () => {
      let result: MergeResult;
      beforeEach(() => {
        lhs.clearRule('1');
        rhs.setCell('1_0', 'SET_BY_RHS');
        // now do the merge
        result = merge();
      });

      it('shall have conflicts after merge', () => {
        // there should be a conflict and is unresolved
        expect(result.getConflict().getCellItems().size).toBe(1);
        expect(result.getConflict().getCellItems().get('1_0').resolved).toBe(false);
      });

      it('and we shall be able to resolve the conflicts by choosing lhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_0', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getRule('1')).toBeFalsy();
          const diff = diffResult(result);
          expect(diff.lhs.getRuleItem('1')).toBeTruthy();
          expect(diff.rhs.getCellItem('1_0')).toBeFalsy();
        }, Resolution.LHS, Resolution.RHS);
      });

      it('and we shall be able to resolve the conflicts by choosing rhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_0', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getRule('1')).toBeTruthy();
          expect(result.getMerged().getCell('1_0').getExpr()).toBe('SET_BY_RHS');
          if (resolution === Resolution.RHS) {
            expect(result.getLhsDiff().getRuleItem('1')).toBeFalsy();
            expect(result.getRhsDiff().getCellItem('1_0')).toBeTruthy();
          } else {
            expect(result.getRhsDiff().getRuleItem('1')).toBeFalsy();
            expect(result.getLhsDiff().getCellItem('1_0')).toBeTruthy();
          }
        }, Resolution.RHS, Resolution.LHS);
      });
    });

    describe('when there are different modifications on the same cell', () => {
      let result: MergeResult;
      beforeEach(() => {
        lhs.setCell('1_2', 'SET_BY_LHS');
        rhs.setCell('1_2', 'SET_BY_RHS');
        // now do the merge
        result = merge();
      });

      it('shall have merge conflict', () => {
        // there should be an unresolved conflict
        expect(result.getConflict().getCellItems().size).toBe(1);
        expect(result.getConflict().getCellItems().get('1_2').resolved).toBe(false);
      });

      it('shall be able to resolve the conflict by choosing lhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_2', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getCell('1_2').getExpr()).toBe('SET_BY_LHS');
          const diff = diffResult(result);
          expect(diff.lhs.getCellItem('1_2')).toBeTruthy();
          expect(diff.rhs.getCellItem('1_2')).toBeFalsy();
        }, Resolution.LHS, Resolution.RHS);
      });

      it('shall be able to resolve the conflict by choosing rhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_2', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getCell('1_2').getExpr()).toBe('SET_BY_RHS');
          const diff = diffResult(result);

          expect(diff.lhs.getCellItem('1_2')).toBeFalsy();
          expect(diff.rhs.getCellItem('1_2')).toBeTruthy();
        }, Resolution.RHS, Resolution.LHS);
      });
    });

    describe('when there are column deletion with cell edited', () => {
      let result: MergeResult;
      beforeEach(() => {
        lhs.clearColumn('0');
        rhs.setCell('1_0', 'SET_BY_RHS');
        // now do the merge
        result = merge();
      });

      it('shall have conflicts after merge', () => {
        expect(result.getConflict().getCellItems().size).toBe(1, 'Shall have 1 cell conflict');
        expect(result.getConflict().getColumnItems().size).toBe(1, 'Shall have 1 column conflict');
      });

      it('shall be able to resolve conflicts by choosing lhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_0', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getColumn('0')).toBeFalsy();
          expect(result.getMerged().getCell('1_0')).toBeFalsy();
          const diff = diffResult(result);
          expect(diff.lhs.getColumnItem('0')).toBeTruthy();
          expect(diff.rhs.getCellItem('1_0')).toBeFalsy();
        }, Resolution.LHS, Resolution.RHS);
      });

      it('shall be able to resolve conflicts by choosing rhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_0', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getColumn('0')).toBeTruthy();
          expect(result.getMerged().getCell('1_0').getExpr()).toBe('SET_BY_RHS');
          const diff = diffResult(result);
          expect(diff.lhs.getColumnItem('0')).toBeFalsy();
          expect(diff.rhs.getCellItem('1_0')).toBeTruthy();
        }, Resolution.RHS, Resolution.LHS);
      });

    });

    describe('when there are row addtion with cell update whose column is deleted', () => {
      let result: MergeResult;
      let lhsRule: Rule;
      beforeEach(() => {
        lhsRule = lhs.createRuleWithAutoId();
        lhsRule.setCell('0', 'SET_BY_LHS', lhs.getColumn('0').columnType.cellType);
        rhs.clearColumn('0');
        // now do the merge
        result = merge();
      });
      it('shall have conflicts after merge', () => {
        // there should be an unresolved conflict
        expect(result.getConflict().getCellItems().size).toBe(1);
        expect(result.getConflict().getCellItems().get(`${lhsRule.getId()}_0`).resolved).toBe(false);
      });

      it('shall be able to resolve conflicts by choosing lhs', () => {
        runCheck((resolution, _) => {
          const cellId = `${lhsRule.getId()}_0`;
          result.resolveCellConflict(cellId, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getRule(lhsRule.getId())).toBeTruthy();
          expect(result.getMerged().getCell(cellId).getExpr()).toBe('SET_BY_LHS');
          expect(result.getMerged().getColumn('0')).toBeTruthy();
          const diff = diffResult(result);
          expect(diff.lhs.getRuleItem(lhsRule.getId())).toBeTruthy();
          expect(diff.lhs.getCellItem(cellId)).toBeTruthy();
          expect(diff.rhs.getColumnItem('0')).toBeFalsy();
        }, Resolution.LHS, Resolution.RHS);
      });

      it('shall be able to resolve conflicts by choosing rhs', () => {
        runCheck((resolution, _) => {
          const cellId = `${lhsRule.getId()}_0`;
          result.resolveCellConflict(cellId, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getRule(lhsRule.getId())).toBeTruthy();
          expect(result.getMerged().getCell(cellId)).toBeFalsy();
          expect(result.getMerged().getColumn('0')).toBeFalsy();
          const diff = diffResult(result);
          expect(diff.lhs.getRuleItem(lhsRule.getId())).toBeTruthy();
          expect(diff.lhs.getCellItem(cellId)).toBeFalsy();
          expect(diff.rhs.getColumnItem('0')).toBeTruthy();
        }, Resolution.RHS, Resolution.LHS);
      });
    });

    describe('when there are row addition with cell creation whose column is deleted', () => {
      let result: MergeResult;
      let lhsRule: Rule;
      let merged: DecisionTable;
      beforeEach(() => {
        lhsRule = lhs.createRuleWithAutoId();
        lhsRule.setCell('2', 'SET_BY_LHS', lhs.getColumn('2').columnType.cellType);
        rhs.clearColumn('2');

        // now do the merge
        result = merge();
        merged = result.getMerged();
      });

      it('shall have conflicts after merge', () => {
        // there should be an unresolved conflict
        expect(result.getConflict().getCellItems().size).toBe(1);
        expect(result.getConflict().getCellItems().get(`${lhsRule.getId()}_2`).resolved).toBe(false);

        // however the value shall still appear in the merged table because later
        // conflict resolution might just choose to recover the deletion
        expect(merged.getCell(`${lhsRule.getId()}_2`).getExpr()).toBe('SET_BY_LHS');

      });

      it('shall be able to resolve conflicts by choosing lhs', () => {
        runCheck((resolution, _) => {
          const cellId = `${lhsRule.getId()}_2`;
          result.resolveCellConflict(cellId, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getRule(lhsRule.getId())).toBeTruthy();
          expect(result.getMerged().getCell(cellId).getExpr()).toBe('SET_BY_LHS');
          expect(result.getMerged().getColumn('2')).toBeTruthy();
          const diff = diffResult(result);
          expect(diff.lhs.getRuleItem(lhsRule.getId())).toBeTruthy();
          expect(diff.lhs.getCellItem(cellId)).toBeTruthy();
          expect(diff.rhs.getColumnItem('2')).toBeFalsy();
        }, Resolution.LHS, Resolution.RHS);
      });

      it('shall be able to resolve conflicts by choosing rhs', () => {
        runCheck((resolution, _) => {
          const cellId = `${lhsRule.getId()}_2`;
          result.resolveCellConflict(cellId, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getRule(lhsRule.getId())).toBeTruthy();
          expect(result.getMerged().getCell(cellId)).toBeFalsy();
          expect(result.getMerged().getColumn('2')).toBeFalsy();
          const diff = diffResult(result);
          expect(diff.lhs.getRuleItem(lhsRule.getId())).toBeTruthy();
          expect(diff.lhs.getCellItem(cellId)).toBeFalsy();
          expect(diff.rhs.getColumnItem('2')).toBeTruthy();
        }, Resolution.RHS, Resolution.LHS);
      });
    });

    describe('when there are multiple row addtions with new cell whose column is deleted', () => {
      let result: MergeResult;
      let lhsRule: Rule;
      let merged: DecisionTable;
      beforeEach(() => {
        lhsRule = lhs.createRuleWithAutoId();
        lhsRule.setCell('2', 'SET_BY_LHS', lhs.getColumn('2').columnType.cellType);
        rhs.createRuleWithAutoId();
        rhs.clearColumn('2');

        // now do the merge
        result = merge();
        merged = result.getMerged();
      });

      it('shall have conflicts after merge', () => {
        const check = (first, second) => {
          // however the value shall still appear in the merged table because later
          // conflict resolution might just choose to recover the deletion
          expect(merged.getCell(`${first}_2`).getExpr()).toBe('SET_BY_LHS');
          expect(result.getConflict().getCellItems().get(`${first}_2`).resolved).toBe(false);
          expect(result.getConflict().getColumnItems().get('2').resolved).toBe(false);
        };
        runCheck(check, lhsRule.getId(), (parseInt(lhsRule.getId(), 10) + 1).toString());
      });

      it('shall be able to resolve conflicts by choosing lhs', () => {
        runCheck((resolution, _) => {
          let cellId: string;
          let ruleId: string;
          if (lhsFirst) {
            ruleId = `${lhsRule.getId()}`;
          } else {
            ruleId = `${parseInt(lhsRule.getId(), 10) + 1}`;
          }
          cellId = `${ruleId}_2`;
          result.resolveCellConflict(cellId, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getRule(ruleId)).toBeTruthy();
          expect(result.getMerged().getCell(cellId).getExpr()).toBe('SET_BY_LHS');
          expect(result.getMerged().getColumn('2')).toBeTruthy();
          const diff = diffResult(result);
          expect(diff.lhs.getRuleItem(ruleId)).toBeTruthy();
          expect(diff.lhs.getCellItem(cellId)).toBeTruthy();
          expect(diff.rhs.getColumnItem('2')).toBeFalsy();
        }, Resolution.LHS, Resolution.RHS);
      });

      it('shall be able to resolve conflicts by choosing rhs', () => {
        runCheck((resolution, _) => {
          let cellId: string;
          let ruleId: string;
          if (lhsFirst) {
            ruleId = `${lhsRule.getId()}`;
          } else {
            ruleId = `${parseInt(lhsRule.getId(), 10) + 1}`;
          }
          cellId = `${ruleId}_2`;
          result.resolveCellConflict(cellId, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getRule(ruleId)).toBeTruthy();
          expect(result.getMerged().getCell(cellId)).toBeFalsy();
          expect(result.getMerged().getColumn('2')).toBeFalsy();
          const diff = diffResult(result);
          expect(diff.lhs.getRuleItem(ruleId)).toBeTruthy();
          expect(diff.lhs.getCellItem(cellId)).toBeFalsy();
          expect(diff.rhs.getColumnItem('2')).toBeTruthy();
        }, Resolution.RHS, Resolution.LHS);
      });
    });

    describe('when there are rule deletion with cell modification', () => {
      let deleted: Rule;
      let result: MergeResult;
      let merged: DecisionTable;
      beforeEach(() => {
        deleted = lhs.getRule('1');
        lhs.clearRule('1');
        rhs.setCell('1_0', 'SET_BY_RHS');

        // now do the merge
        result = merge();
        merged = result.getMerged();
      });
      it('shall have conflicts after merge', () => {
        expect(result.getConflict().getCellItems().size).toBe(1);
        expect(result.getConflict().getRuleItems().size).toBe(1);

        runCheck((ld, rd) => {
          deleted.getCells().forEach(cell => {
            if (cell.getColId() === '0') {
              expect(merged.getCell(cell.getId()).getExpr()).toBe('SET_BY_RHS');
            } else {
              expect(ld.getCellItem(cell.getId()).kind).toBe(DELETE);
              expect(merged.getCell(cell.getId()).getExpr()).toBe(deleted.getCell(cell.getColId()).getExpr());
            }
          });
        }, result.getLhsDiff(), result.getRhsDiff());
        expect(result.getMerged().getRule('1').getCells().length).toBe(deleted.getCells().length);
      });

      it('shall be able to resolve conflicts by choosing lhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_0', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getRule('1')).toBeFalsy();
          expect(result.getMerged().getCell('1_0')).toBeFalsy();
          const diff = diffResult(result);
          expect(diff.lhs.getRuleItem('1')).toBeTruthy();
          expect(diff.rhs.getCellItem('1_0')).toBeFalsy();
        }, Resolution.LHS, Resolution.RHS);
      });

      it('shall be able to resolve conflicts by choosing rhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_0', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getRule('1')).toBeTruthy();
          expect(result.getMerged().getCell('1_0').getExpr()).toBe('SET_BY_RHS');
          const diff = diffResult(result);
          expect(diff.lhs.getRuleItem('1')).toBeFalsy();
          expect(diff.rhs.getCellItem('1_0')).toBeTruthy();
        }, Resolution.RHS, Resolution.LHS);
      });
    });

    describe('when there are column deletions with cell modification', () => {
      let result: MergeResult;
      beforeEach(() => {
        lhs.clearColumn('0');
        rhs.setCell('1_0', 'SET_BY_RHS');

        // now do the merge
        result = merge();
      });
      it('shall have conflicts after merge', () => {
        expect(result.getConflict().getCellItems().size).toBe(1);
        expect(result.getConflict().getColumnItems().size).toBe(1);
        result.getMerged().getRules().forEach(r => {
          if (r.getId() === '1') {
            expect(r.getCell('0').getExpr()).toBe('SET_BY_RHS');
          } else {
            expect(r.getCell('0').getExpr()).toBe(rhs.getCell(`${r.getId()}_0`).getExpr());
            runCheck((ld, rd) => {
              expect(ld.getCellItem(`${r.getId()}_0`).kind).toBe(DELETE);
            }, result.getLhsDiff(), result.getRhsDiff());
          }
        });
      });

      it('shall be able to resolve conflicts by choosing lhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_0', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getColumn('0')).toBeFalsy();
          const diff = diffResult(result);
          expect(diff.lhs.getColumnItem('0')).toBeTruthy();
          expect(diff.rhs.getCellItem('1_0')).toBeFalsy();
        }, Resolution.LHS, Resolution.RHS);
      });

      it('shall be able to resolve conflicts by choosing rhs', () => {
        runCheck((resolution, _) => {
          result.resolveCellConflict('1_0', resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getColumn('0')).toBeTruthy();
          expect(result.getMerged().getCell('1_0').getExpr()).toBe('SET_BY_RHS');
          const diff = diffResult(result);
          expect(diff.lhs.getColumnItem('0')).toBeFalsy();
          expect(diff.rhs.getCellItem('1_0')).toBeTruthy();
        }, Resolution.RHS, Resolution.LHS);
      });
    });

    describe('when there are column addition with new cell whose row is deleted', () => {
      let lhsCol: Column;
      let result: MergeResult;
      let merged: DecisionTable;
      beforeEach(() => {
        lhsCol = lhs.createAndAddColumn('NEWLY_ADDED_BY_LHS', PropertyType.STRING, ColumnType.EXPR_CONDITION);
        lhs.setCell(`1_${lhsCol.getId()}`, 'SET_BY_LHS');
        rhs.clearRule('1');

        // now do the merge
        result = merge();
        merged = result.getMerged();
      });

      it('shall have conflicts after merge', () => {
        // there should be an unresolved conflict
        expect(result.getConflict().getCellItems().size).toBe(1);
        expect(result.getConflict().getCellItems().get(`1_${lhsCol.getId()}`).resolved).toBe(false);

        // however the value shall still appear in the merged table because later
        // conflict resolution might just choose to recover the deletion
        expect(merged.getCell(`1_${lhsCol.getId()}`).getExpr()).toBe('SET_BY_LHS');
      });

      it('shall be able to resolve conflicts by choosing lhs', () => {
        runCheck((resolution, _) => {
          const cellId = `1_${lhsCol.getId()}`;
          result.resolveCellConflict(cellId, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getColumn(lhsCol.getId())).toBeTruthy();
          expect(result.getMerged().getRule('1')).toBeTruthy();
          expect(result.getMerged().getCell(cellId).getExpr()).toBe('SET_BY_LHS');
          const diff = diffResult(result);
          expect(diff.lhs.getColumnItem(lhsCol.getId())).toBeTruthy();
          expect(diff.lhs.getCellItem(cellId)).toBeTruthy();
          expect(diff.rhs.getRuleItem('1')).toBeFalsy();
        }, Resolution.LHS, Resolution.RHS);
      });

      it('shall be able to resolve conflicts by choosing rhs', () => {
        runCheck((resolution, _) => {
          const cellId = `1_${lhsCol.getId()}`;
          result.resolveCellConflict(cellId, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getColumn(lhsCol.getId())).toBeTruthy();
          expect(result.getMerged().getRule('1')).toBeFalsy();
          expect(result.getMerged().getCell(cellId)).toBeFalsy();
          const diff = diffResult(result);
          expect(diff.lhs.getColumnItem(lhsCol.getId())).toBeTruthy();
          expect(diff.lhs.getCellItem(cellId)).toBeFalsy();
          expect(diff.rhs.getRuleItem('1')).toBeTruthy();
        }, Resolution.RHS, Resolution.LHS);
      });
    });

    describe('when there are multiple column additions with new cell whose row is deleted', () => {
      let lhsCol: Column;
      let result: MergeResult;
      let merged: DecisionTable;
      beforeEach(() => {
        lhsCol = lhs.createAndAddColumn('NEWLY_ADDED_BY_LHS', PropertyType.STRING, ColumnType.EXPR_CONDITION);
        lhs.setCell(`1_${lhsCol.getId()}`, 'SET_BY_LHS');
        // we add multiple column thus we have id drifts whick makes things different
        rhs.createAndAddColumn('NEWLY_ADDED_BY_RHS', PropertyType.STRING, ColumnType.EXPR_CONDITION);
        rhs.clearRule('1');

        // now do the merge
        result = merge();
        merged = result.getMerged();
      });
      it('shall have conflicts after merge', () => {
        // however the value shall still appear in the merged table because later
        // conflict resolution might just choose to recover the deletion
        const mergeCheck = (first, second) => {
          expect(merged.getCell(`1_${first}`).getExpr()).toBe('SET_BY_LHS');
          expect(result.getConflict().getCellItems().get(`1_${first}`).resolved).toBe(false, 'shall have cell conflict');
          expect(result.getConflict().getRuleItems().get('1').resolved).toBe(false, 'shall have rule conflict');
        };
        runCheck(mergeCheck, lhsCol.id, (parseInt(lhsCol.id, 10) + 1).toString());
      });

      it('shall be able to resolve conflicts by choosing lhs', () => {
        runCheck((resolution, _) => {
          let colId: string;
          if (lhsFirst) {
            colId = lhsCol.getId();
          } else {
            colId = `${parseInt(lhsCol.getId(), 10) + 1}`;
          }
          const cellId = `1_${colId}`;
          result.resolveCellConflict(cellId, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getColumn(colId)).toBeTruthy();
          expect(result.getMerged().getCell(cellId).getExpr()).toBe('SET_BY_LHS');
          expect(result.getMerged().getRule('1')).toBeTruthy();
          const diff = diffResult(result);
          expect(diff.lhs.getColumnItem(colId)).toBeTruthy();
          expect(diff.lhs.getCellItem(cellId)).toBeTruthy();
          expect(diff.rhs.getRuleItem('1')).toBeFalsy();
        }, Resolution.LHS, Resolution.RHS);
      });

      it('shall be able to resolve conflicts by choosing rhs', () => {
        runCheck((resolution, _) => {
          let colId: string;
          if (lhsFirst) {
            colId = lhsCol.getId();
          } else {
            colId = `${parseInt(lhsCol.getId(), 10) + 1}`;
          }
          const cellId = `1_${colId}`;
          result.resolveCellConflict(cellId, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getColumn(colId)).toBeTruthy();
          expect(result.getMerged().getCell(cellId)).toBeFalsy();
          expect(result.getMerged().getRule('1')).toBeFalsy();
          const diff = diffResult(result);
          expect(diff.lhs.getColumnItem(colId)).toBeTruthy();
          expect(diff.lhs.getCellItem(cellId)).toBeFalsy();
          expect(diff.rhs.getRuleItem('1')).toBeTruthy();
        }, Resolution.RHS, Resolution.LHS);
      });
    });

    describe('when there are multiple property modifications', () => {
      let result: MergeResult;
      let merged: DecisionTable;
      beforeEach(() => {
        // non-conflict
        lhs.setProperty(DecisionTable.PRIORITY_PARAM, '9');
        rhs.setProperty(DecisionTable.EFFECTIVE_TIME_PARAM, '5.23');
        // conflict
        lhs.setProperty(DecisionTable.EXPIRY_TIME_PARAM, '200-10-10');
        rhs.setProperty(DecisionTable.EXPIRY_TIME_PARAM, '100-10-10');

        result = merge();
        merged = result.getMerged();
      });

      it('shall be able to merge non-conflict property modification but leave others unresolved', () => {
        // make sure we see property changes are merged
        expect(merged.getProperty(DecisionTable.PRIORITY_PARAM)).toBe('9');
        expect(merged.getProperty(DecisionTable.EFFECTIVE_TIME_PARAM)).toBe('5.23');

        // make sure conflictive updates are unresolved
        expect(result.getConflict().getPropItems().get(DecisionTable.EXPIRY_TIME_PARAM).resolved).toBe(false);
      });

      it('and resolve conflicts by choosing lhs', () => {
        runCheck((resolution, _) => {
          result.resolvePropertyConflict(DecisionTable.EXPIRY_TIME_PARAM, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getProperty(DecisionTable.EXPIRY_TIME_PARAM)).toBe('200-10-10');
          const diff = diffResult(result);
          expect(diff.lhs.getPropItem(DecisionTable.EXPIRY_TIME_PARAM)).toBeTruthy();
          expect(diff.rhs.getPropItem(DecisionTable.EXPIRY_TIME_PARAM)).toBeFalsy();
        }, Resolution.LHS, Resolution.RHS);
      });

      it('and resolve conflicts by choosing rhs', () => {
        runCheck((resolution, _) => {
          result.resolvePropertyConflict(DecisionTable.EXPIRY_TIME_PARAM, resolution);
          expect(result.hasConflict()).toBe(false);
          expect(result.getMerged().getProperty(DecisionTable.EXPIRY_TIME_PARAM)).toBe('100-10-10');
          const diff = diffResult(result);
          expect(diff.lhs.getPropItem(DecisionTable.EXPIRY_TIME_PARAM)).toBeFalsy();
          expect(diff.rhs.getPropItem(DecisionTable.EXPIRY_TIME_PARAM)).toBeTruthy();
        }, Resolution.RHS, Resolution.LHS);
      });
    });
  };

  describe('can merge changes done by different copies...', () => {
    describe('lhs change happens first...', () => {
      testsuite(true);
    });
    describe('rhs change happens first...', () => {
      testsuite(false);
    });
  });
});
