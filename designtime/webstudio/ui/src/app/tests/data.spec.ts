export let APPLICANT_SIMPLE = `<?xml version="1.0" encoding="UTF-8"?>
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
