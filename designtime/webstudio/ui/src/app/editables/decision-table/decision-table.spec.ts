import { I18n } from '@ngx-translate/i18n-polyfill';

import * as _ from 'lodash';

import { DecisionTable, RULE_PRIORITY } from './decision-table';
import { Differ } from './differ/differ';

import { Logger } from '../../core/logger.service';

const xml = `<?xml version="1.0" encoding="UTF-8"?>
<Table:Table xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" \
xmlns:Table="http:///com/tibco/cep/decision/table/model/DecisionTable.ecore" name="Applicant_Simple.sbdt" folder="/">
  <md>
    <prop name="Priority" value="5"/>
  </md>
  <decisionTable>
    <rule id="1">
      <cond id="1_0" colId="0" expr="&quot;John Doe&quot;"/>
      <cond id="1_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
      <act id="1_2" colId="2" expr="true"/>
      <act id="1_3" colId="3" expr="2500"/>
      <act id="1_4" colId="4" expr="Visa Granted"/>
    </rule>
    <rule id="2">
      <cond id="2_0" colId="0" expr="&quot;Sandra W&quot;"/>
      <cond id="2_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
      <act id="2_3" colId="3" expr="0"/>
      <act id="2_4" colId="4" expr="Loan Rejected"/>
    </rule>
    <rule id="3">
      <cond id="3_0" colId="0" expr="&quot;Prakash Y&quot;"/>
      <cond id="3_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
      <act id="3_2" colId="2" expr="true"/>
      <act id="3_3" colId="3" expr="7500"/>
      <act id="3_4" colId="4" expr="Pending"/>
    </rule>
    <rule id="4">
      <cond id="4_0" colId="0" expr="&quot;Jane Doe&quot;"/>
      <cond id="4_1" colId="1" expr="> 30"/>
      <act id="4_2" colId="2" expr="true"/>
      <act id="4_3" colId="3" expr="25000"/>
      <act id="4_4" colId="4" expr="Visa Granted"/>
    </rule>
    <rule id="5">
      <cond id="5_0" colId="0" expr="&quot;Jane Doe&quot;"/>
      <act id="5_2" colId="2" expr="true"/>
      <act id="5_3" colId="3" expr="25000"/>
      <act id="5_4" colId="4" expr="Visa Granted"/>
    </rule>
    <columns>
      <column id="0" name="Name" propertyPath="Name" columnType="CONDITION"/>
      <column id="1" name="Age" propertyPath="Age" propertyType="1" columnType="CONDITION"/>
      <column id="2" name="Eligible" propertyPath="Eligible" propertyType="4" columnType="ACTION"/>
      <column id="3" name="CreditLimit" propertyPath="CreditLimit" propertyType="1" columnType="ACTION"/>
      <column id="4" name="Status" propertyPath="Status" columnType="ACTION"/>
    </columns>
  </decisionTable>
</Table:Table>`;

const logger = new Logger();

const differ = new Differ(logger, null);

describe('DecisionTable', () => {
  it('should be able to be parsed from xml', () => {
    const table = DecisionTable.fromXml(xml);
    expect(table.getProperties().length).toBe(1);
    expect(table.getRules().length).toBe(5);
    expect(table.getColumns().length).toBe(5);
  });
  it('should be able to be serialized back to xml string', () => {
    const table = DecisionTable.fromXml(xml);
    const actual = table.toXml();
    expect(actual).toBe(xml);
  });
  it('should be able to update property', () => {
    const dtXml = `<?xml version="1.0" encoding="UTF-8"?>
<Table:Table xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" \
xmlns:Table="http:///com/tibco/cep/decision/table/model/DecisionTable.ecore" name="SB-29153.sbdt" folder="/">
  <md>
    <prop name="Priority" value="5"/>
    <prop name="EffectiveDate" type="String" value="1"/>
    <prop name="ExpiryDate" type="String" value="2016-05-02 12:22:08-0500"/>
    <prop name="SingleRowExecution" type="Boolean" value="true"/>
  </md>
  <decisionTable>
    <rule id="1">
      <cond id="1_0" colId="0" expr="abc || def"/>
      <cond id="1_1" colId="1" expr="10"/>
      <act id="1_4" colId="4" expr="ABC"/>
      <cond id="1_8" colId="8" expr="false"/>
      <cond id="1_9" colId="9" expr="10 || 20"/>
    </rule>
    <rule id="2">
      <md>
        <prop name="Priority" type="Integer" value="8"/>
      </md>
      <cond id="2_0" colId="0" expr="def"/>
      <cond id="2_1" colId="1" expr="10"/>
    </rule>
    <rule id="3">
      <cond id="3_0" colId="0" expr="def"/>
      <cond id="3_1" colId="1" expr="10"/>
      <act id="3_4" colId="4" expr="ABC"/>
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
    const dt = DecisionTable.fromXml(dtXml);
    dt.setProperty('Priority', '10');
    expect(dt.toXml()).toBe(DecisionTable.fromXml(dtXml.replace('value="5"', 'value="10"')).toXml());
  });
  it('should be able to be deeply compared', () => {
    const dt1 = DecisionTable.fromXml(xml);
    const dt2 = DecisionTable.fromXml(xml);
    expect(_.isEqual(dt1, dt2)).toBe(true);
    expect(differ.diff(dt1, dt2).size()).toBe(0);
    dt1.setProperty(DecisionTable.SINGLE_EXEC_PARAM, 'false');
    expect(_.isEqual(dt1, dt2)).toBe(true); // should still be same as this setting is ignored
    expect(differ.diff(dt1, dt2).size()).toBe(0);
    dt1.setProperty(DecisionTable.EFFECTIVE_TIME_PARAM, '');
    expect(_.isEqual(dt1, dt2)).toBe(false);
    expect(differ.diff(dt1, dt2).size()).toBe(1);
    dt1.setProperty(DecisionTable.EFFECTIVE_TIME_PARAM, '1');
    expect(_.isEqual(dt1, dt2)).toBe(false); // should still be false this time
  });
  it('should be able to clear empty property or cell', () => {
    const xml2 = `<?xml version="1.0" encoding="UTF-8"?>
    <Table:Table xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" \
    xmlns:Table="http:///com/tibco/cep/decision/table/model/DecisionTable.ecore" name="Applicant_Simple.sbdt" folder="/">
      <md>
        <prop name="Priority" value="5"/>
        <prop name="SingleRowExecution" value="false"/>
      </md>
      <decisionTable>
        <rule id="1">
          <cond id="1_0" colId="0" expr="&quot;John Doe&quot;"/>
          <cond id="1_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
          <act id="1_2" colId="2" expr="true"/>
          <act id="1_3" colId="3" expr="2500"/>
          <act id="1_4" colId="4" expr="Visa Granted"/>
        </rule>
        <rule id="2">
          <cond id="2_0" colId="0" expr="&quot;Sandra W&quot;"/>
          <cond id="2_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
          <act id="2_3" colId="3" expr="0"/>
          <act id="2_4" colId="4" expr="Loan Rejected"/>
        </rule>
        <rule id="3">
          <cond id="3_0" colId="0" expr="&quot;Prakash Y&quot;"/>
          <cond id="3_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
          <act id="3_2" colId="2" expr="true"/>
          <act id="3_3" colId="3" expr="7500"/>
          <act id="3_4" colId="4" expr="Pending"/>
        </rule>
        <rule id="4">
          <cond id="4_0" colId="0" expr="&quot;Jane Doe&quot;"/>
          <cond id="4_1" colId="1" expr="> 30"/>
          <act id="4_2" colId="2" expr="true"/>
          <act id="4_3" colId="3" expr="25000"/>
          <act id="4_4" colId="4" expr="Visa Granted"/>
        </rule>
        <rule id="5">
          <cond id="5_0" colId="0" expr="&quot;Jane Doe&quot;"/>
          <act id="5_2" colId="2" expr="true"/>
          <act id="5_3" colId="3" expr="25000"/>
          <act id="5_4" colId="4" expr="Visa Granted"/>
        </rule>
        <columns>
          <column id="0" name="Name" propertyPath="Name" columnType="CONDITION"/>
          <column id="1" name="Age" propertyPath="Age" propertyType="1" columnType="CONDITION"/>
          <column id="2" name="Eligible" propertyPath="Eligible" propertyType="4" columnType="ACTION"/>
          <column id="3" name="CreditLimit" propertyPath="CreditLimit" propertyType="1" columnType="ACTION"/>
          <column id="4" name="Status" propertyPath="Status" columnType="ACTION"/>
        </columns>
      </decisionTable>
    </Table:Table>`;

    const dt1 = DecisionTable.fromXml(xml);
    const dt2 = DecisionTable.fromXml(xml2);
    expect(_.isEqual(dt1, dt2)).toBe(true); // should still be same as the false SINGLE_EXEC_PARAM is ignored
    expect(differ.diff(dt1, dt2).size()).toBe(0);

    dt2.setCell('2_2', '');
    expect(_.isEqual(dt1, dt2)).toBe(true); // should still be same as the empty cell is ignored
    expect(differ.diff(dt1, dt2).size()).toBe(0);

  });

  it('should be able to clear cell', () => {
    const dt = DecisionTable.fromXml(xml);
    dt.clearProperty(DecisionTable.PRIORITY_PARAM);
    expect(dt.getProperty(DecisionTable.PRIORITY_PARAM)).toBe('');
  });

  it('should be able to set priority', () => {
    const dt = DecisionTable.fromXml(xml);

    const rule = dt.getRule('1');
    expect(rule.getCell(RULE_PRIORITY)).toBeFalsy();
    rule.setCell(RULE_PRIORITY, '10', 'prop');

    expect(rule.getCell(RULE_PRIORITY).getExpr()).toBe('10');

    const str = dt.toXml();
    const dt1 = DecisionTable.fromXml(str);
    expect(dt1.getRule('1').getCell(RULE_PRIORITY).getExpr()).toBe('10');
  });

  it('should be able to parse table when it has empty expr', () => {
    const xmlWithEmptyCellExpr = `<?xml version="1.0" encoding="UTF-8"?>
    <Table:Table xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" \
    xmlns:Table="http:///com/tibco/cep/decision/table/model/DecisionTable.ecore" name="Applicant_Simple.sbdt" folder="/">
      <md>
        <prop name="Priority" value="5"/>
        <prop name="SingleRowExecution" value="false"/>
      </md>
      <decisionTable>
        <rule id="1">
          <cond id="1_0" colId="0" expr="&quot;John Doe&quot;"/>
          <cond id="1_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
          <act id="1_2" colId="2" expr="true"/>
          <act id="1_3" colId="3" expr="2500"/>
          <act id="1_4" colId="4" expr="Visa Granted"/>
        </rule>
        <rule id="2">
          <cond id="2_0" colId="0" expr="&quot;Sandra W&quot;"/>
          <cond id="2_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
          <act id="2_3" colId="3" expr="0"/>
          <act id="2_4" colId="4" expr="Loan Rejected"/>
        </rule>
        <rule id="3">
          <cond id="3_0" colId="0" expr="&quot;Prakash Y&quot;"/>
          <cond id="3_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
          <act id="3_2" colId="2" expr="true"/>
          <act id="3_3" colId="3" expr="7500"/>
          <act id="3_4" colId="4" expr="Pending"/>
        </rule>
        <rule id="4">
          <cond id="4_0" colId="0" expr="&quot;Jane Doe&quot;"/>
          <cond id="4_1" colId="1" expr="> 30"/>
          <act id="4_2" colId="2" expr="true"/>
          <act id="4_3" colId="3" expr="25000"/>
          <act id="4_4" colId="4" expr="Visa Granted"/>
        </rule>
        <rule id="5">
          <cond id="5_0" colId="0" expr="&quot;Jane Doe&quot;"/>
          <act id="5_2" colId="2" expr="true"/>
          <act id="5_3" colId="3" />
          <act id="5_4" colId="4" expr="Visa Granted"/>
        </rule>
        <columns>
          <column id="0" name="Name" propertyPath="Name" columnType="CONDITION"/>
          <column id="1" name="Age" propertyPath="Age" propertyType="1" columnType="CONDITION"/>
          <column id="2" name="Eligible" propertyPath="Eligible" propertyType="4" columnType="ACTION"/>
          <column id="3" name="CreditLimit" propertyPath="CreditLimit" propertyType="1" columnType="ACTION"/>
          <column id="4" name="Status" propertyPath="Status" columnType="ACTION"/>
        </columns>
      </decisionTable>
    </Table:Table>`;

    const dt1 = DecisionTable.fromXml(xmlWithEmptyCellExpr);
    expect(dt1.getRule('5').getCells().length).toBe(3);
  });

  it('should be able to ignore empty property or cell from xml', () => {
    const xml2 = `<?xml version="1.0" encoding="UTF-8"?>
    <Table:Table xmi:version="2.0" xmlns:xmi="http://www.omg.org/XMI" \
    xmlns:Table="http:///com/tibco/cep/decision/table/model/DecisionTable.ecore" name="Applicant_Simple.sbdt" folder="/">
      <md>
        <prop name="Priority" value=""/>
      </md>
      <decisionTable>
        <rule id="1">
          <cond id="1_0" colId="0" expr="&quot;John Doe&quot;"/>
          <cond id="1_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
          <act id="1_2" colId="2" expr="true"/>
          <act id="1_3" colId="3" expr="2500"/>
          <act id="1_4" colId="4" expr="Visa Granted"/>
        </rule>
        <rule id="2">
          <cond id="2_0" colId="0" expr="&quot;Sandra W&quot;"/>
          <cond id="2_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
          <act id="1_2" colId="2" expr=""/>
          <act id="2_3" colId="3" expr="0"/>
          <act id="2_4" colId="4" expr="Loan Rejected"/>
        </rule>
        <rule id="3">
          <cond id="3_0" colId="0" expr="&quot;Prakash Y&quot;"/>
          <cond id="3_1" colId="1" expr=">= 20 &amp;&amp; &lt;= 30"/>
          <act id="3_2" colId="2" expr="true"/>
          <act id="3_3" colId="3" expr="7500"/>
          <act id="3_4" colId="4" expr="Pending"/>
        </rule>
        <rule id="4">
          <cond id="4_0" colId="0" expr="&quot;Jane Doe&quot;"/>
          <cond id="4_1" colId="1" expr="> 30"/>
          <act id="4_2" colId="2" expr="true"/>
          <act id="4_3" colId="3" expr="25000"/>
          <act id="4_4" colId="4" expr="Visa Granted"/>
        </rule>
        <rule id="5">
          <cond id="5_0" colId="0" expr="&quot;Jane Doe&quot;"/>
          <act id="5_2" colId="2" expr="true"/>
          <act id="5_3" colId="3" expr="25000"/>
          <act id="5_4" colId="4" expr="Visa Granted"/>
        </rule>
        <columns>
          <column id="0" name="Name" propertyPath="Name" columnType="CONDITION"/>
          <column id="1" name="Age" propertyPath="Age" propertyType="1" columnType="CONDITION"/>
          <column id="2" name="Eligible" propertyPath="Eligible" propertyType="4" columnType="ACTION"/>
          <column id="3" name="CreditLimit" propertyPath="CreditLimit" propertyType="1" columnType="ACTION"/>
          <column id="4" name="Status" propertyPath="Status" columnType="ACTION"/>
        </columns>
      </decisionTable>
    </Table:Table>`;

    const dt1 = DecisionTable.fromXml(xml);
    const dt2 = DecisionTable.fromXml(xml2);
    expect(_.isEqual(dt1, dt2)).toBe(false);

    dt2.setProperty(DecisionTable.PRIORITY_PARAM, '5');
    expect(dt2.getProperty(DecisionTable.PRIORITY_PARAM)).toBe('5');
    expect(_.isEqual(dt1, dt2)).toBe(true);
    expect(differ.diff(dt1, dt2).size()).toBe(0);

    dt2.setCell('2_2', '');
    expect(_.isEqual(dt1, dt2)).toBe(true); // should still be same as the empty cell is ignored
    expect(differ.diff(dt1, dt2).size()).toBe(0);

  });

  it('shall be able to get max column id', () => {
    const dt = DecisionTable.fromXml(xml);
    expect(dt.maxColumnId()).toBe(4);
  });

  it('shall be able to get max rule id', () => {
    const dt = DecisionTable.fromXml(xml);
    expect(dt.maxRuleId()).toBe(5);
  });

  it('shall be able to get priority column', () => {
    const dt = DecisionTable.fromXml(xml);
    const priority = dt.getPriorityColumn();
    expect(priority).toBeTruthy();
  });

  it('shall exclude priority column in getColumns', () => {
    const dt = DecisionTable.fromXml(xml);
    const priority = dt.getPriorityColumn();
    expect(dt.getColumns().indexOf(priority)).toBe(-1);
  });
});
