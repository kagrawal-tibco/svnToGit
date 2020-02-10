package com.tibco.cep.webstudio.client.i18n;

import java.util.Map;

/**
 * This class routes all DTMessages properties. It enables the use of dynamic(created post war compile) properties files.<br/>
 * If the requested property is not found in custom properties it simply uses the property strings from locale property files.
 * 
 * @author moshaikh
 *
 */
public class DTMessages implements I18nMessages {
	
	private Map<String, String> dTMessages;
	
	public DTMessages(Map<String, String> dTMessages) {
		this.dTMessages = dTMessages;
	}

	private String getPropertyValue(String key, String... replaceValues) {
		return GlobalMessages.getPropertyValue(dTMessages, key, replaceValues);
	}
	
	public String condition() {
		return getPropertyValue("condition");
	}
	
	public String decisionTable() {
		return getPropertyValue("dt.table");
	}
	
	public String exceptionTable() {
		return getPropertyValue("exp.table");
	}
	
	public String declarationTable() {
		return getPropertyValue("decl.table");
	}
	
	public String argument() {
		return getPropertyValue("args");
	}
	
	public String name() {
		return getPropertyValue("name");
	}
	
	public String alias() {
		return getPropertyValue("alias");
	}
	
	public String array() {
		return getPropertyValue("array");
	}
	
	public String tableAnalyzer_title() {
		return getPropertyValue("dt.table.analyzer_title");
	}
	
	public String button_custom() {
		return getPropertyValue("button.custom");
	}
	
	public String button_custom_add_cond() {
		return getPropertyValue("button.custom.add.cond");
	}
	
	public String button_custom_add_action() {
		return getPropertyValue("button.custom.add.action");
	}
	
	public String loading_decision_table() {
		return getPropertyValue("loading.decision.table");
	}
	
	public String loading_decision_table_sucess() {
		return getPropertyValue("loading.decision.table.success");
	}
	
	public String dtPropertyTabGeneral() {
		return getPropertyValue("dt.propertytab.title.general");
	}
	
	public String dtPropertyTabRule() {
		return getPropertyValue("dt.propertytab.title.tab.rule");
	}
	
	public String dtPropertyTabCell() {
		return getPropertyValue("dt.propertytab.title.cell");
	}
	
	public String dtPropertyTabName() {
		return getPropertyValue("dt.propertytab.name");
	}
	
	public String dtPropertyTabeffectdate() {
		return getPropertyValue("dt.propertytab.effdate");
	}
	
	public String dtPropertyTabexpirydate() {
		return getPropertyValue("dt.propertytab.expdate");
	}
	
	public String dtPropertyTabrowatmost() {
		return getPropertyValue("dt.propertytab.rowatmost");
	}
	
	public String dtPropertyTabpriority() {
		return getPropertyValue("dt.propertytab.priority");
	}
	
	public String dtPropertyTablastmodfiedby() {
		return getPropertyValue("dt.propertytab.lastmodf");
	}
	
	public String dtPropertyTabVersion() {
		return getPropertyValue("dt.propertytab.version");
	}
	
	public String dtPropertyTabRfImplements() {
		return getPropertyValue("dt.propertytab.rfimplements");
	}
	
	public String dtPropertyTabId() {
		return getPropertyValue("dt.propertytab.id");
	}
	
	public String dtPropertyTabComments() {
		return getPropertyValue("dt.propertytab.comments");
	}
	
	public String dtPropertyTabEnabled() {
		return getPropertyValue("dt.propertytab.enabled");
	}
	
	public String dtPropertyTabValue() {
		return getPropertyValue("dt.propertytab.val");
	}
	
	public String dtRemoveRow() {
		return getPropertyValue("dt.remove.row");
	}
	
	public String dtDuplicateRow() {
		return getPropertyValue("dt.duplicate.row");
	}
	
	public String dtDuplicateRowToolTip() {
		return getPropertyValue("dt.duplicate.row.tooltip");
	}
	
	public String dtsaveeditordialogtitle() {
		return getPropertyValue("dt.save.confirm.title");
	}
	
	public String dtsaveeditordialogdesc() {
		return getPropertyValue("dt.save.confirm.desc");
	}
	
	public String dtRemoveRowdialogtitle() {
		return getPropertyValue("dt.remove.record.confirm.title");
	}
	
	public String dtRemoveRowdialogdesc() {
		return getPropertyValue("dt.remove.record.confirm.desc");
	}
	
	public String dtRemoveColumndialogtitle() {
		return getPropertyValue("dt.remove.column.confirm.title");
	}
	
	public String dtRemoveColumndialogdesc() {
		return getPropertyValue("dt.remove.column.confirm.desc");
	}
	
	public String dtRemoveIdColumn() {
		return getPropertyValue("dt.id.remove.column");
	}
	
	public String dtDomainAssociationON() {
		return getPropertyValue("dt.domain.association.on");
	}
	
	public String dtDomainAssociationOFF() {
		return getPropertyValue("dt.domain.association.off");
	}
	
	public String dtDomainAssociationFetchProgress() {
		return getPropertyValue("dt.domain.fetch.process");
	}
	
	public String dtDomainAssociationFetchProgressDone() {
		return getPropertyValue("dt.domain.fetch.process.done");
	}
	
	public String dtArgumentFetchProgress() {
		return getPropertyValue("dt.argument.fetch.process");
	}
	
	public String dtArgumentFetchProgressDone() {
		return getPropertyValue("dt.argument.fetch.process.done");
	}
	
	public String dtArgumentFetched() {
		return getPropertyValue("dt.argument.fetched");
	}
	
	public String dtcreatecolumn() {
		return getPropertyValue("dt.create.column");
	}
	
	public String dtcreatecolumnerror(String colName) {
		return getPropertyValue("dt.create.primitive.column.error.desc", colName);
	}
	
	public String dtcreatecolumndesc() {
		return getPropertyValue("dt.create.column.desc");
	}
	
	public String dtcreatecolumnemptydesc() {
		return getPropertyValue("dt.create.column.empty.msg.desc");
	}
	
	public String dtisvirtialerrordesc(String rfName) {
		return getPropertyValue("dt.is.virtual.error.desc", rfName);
	}
	
	public String dtduplicatecolumn(String colName, String areaType) {
		return getPropertyValue("dt.duplicate.column.desc", colName, areaType);
	}
	
	public String wsshowdtanalyzer() {
		return getPropertyValue("DecisionTable.show.analyzer.message");
	}
	
	public String wsdtanalyze() {
		return getPropertyValue("DecisionTable.analyze.decisiontable.message");
	}
	
	public String wsdtanalyzerefreshProblems() {
		return getPropertyValue("DecisionTable.analyze.decisiontable.refresh.problems.message");
	}
	
	public String wsdtanalyzeconflictingActions(Object param1, Object param2) {
		return getPropertyValue("DecisionTable.analyze.conflictingActions.message", param1 == null ? "" : param1.toString(), param2 == null ? "" : param2.toString());
	}
	
	public String wsdtanalyzeequalRules(Object param1, Object param2) {
		return getPropertyValue("DecisionTable.analyze.equalRules.message", param1 == null ? "" : param1.toString(), param2 == null ? "" : param2.toString());
	}
	
	public String wsdtanalyzeoverlappingRange(Object param1, Object param2) {
		return getPropertyValue("DecisionTable.analyze.overlappingRange.message", param1 == null ? "" : param1.toString(), param2 == null ? "" : param2.toString());
	}
	
	public String wsdtanalyzeuncoveredUnboundedRange(Object param1,
			Object param2, Object param3, Object param4) {
		return getPropertyValue("DecisionTable.analyze.uncoveredUnboundedRange.message", param1 == null ? "" : param1.toString(), param2 == null ? "" : param2.toString(), param3 == null ? "" : param3.toString(), param4 == null ? "" : param4.toString());
	}
	
	public String wsdtanalyzeuncoveredUnboundedRangeMax(Object param1,
			Object param2, Object param3, Object param4) {
		return getPropertyValue("DecisionTable.analyze.uncoveredUnboundedRangeMax.message", param1 == null ? "" : param1.toString(), param2 == null ? "" : param2.toString(), param3 == null ? "" : param3.toString(), param4 == null ? "" : param4.toString());
	}
	
	public String wsdtanalyzeuncoveredBoundedRange(Object param1,
			Object param2, Object param3, Object param4, Object param5) {
		return getPropertyValue("DecisionTable.analyze.uncoveredBoundedRange.message", param1 == null ? "" : param1.toString(), param2 == null ? "" : param2.toString(), param3 == null ? "" : param3.toString(), param4 == null ? "" : param4.toString(), param5 == null ? "" : param5.toString());
	}
	
	public String wsdtanalyzemissingEqualsCriterion(Object param1, Object param2) {
		return getPropertyValue("DecisionTable.analyze.missingEqualsCriterion.message", param1 == null ? "" : param1.toString(), param2 == null ? "" : param2.toString());
	}
	
	public String wsdtanalyzeRangeOverlapsRanges(Object param1, Object param2, Object param3, Object param4) {
		return getPropertyValue("DecisionTable.analyze.rangeOverlapsRanges.message", param1 == null ? "" : param1.toString(), param2 == null ? "" : param2.toString(), param3 == null ? "" : param3.toString(), param4 == null ? "" : param4.toString());
	}
	
	public String dtMinGreaterThanMaxMessage() {
		return getPropertyValue("dt.analyze.minGreaterThanMax.message");
	}
	
	public String dtMaxLessThanMinMessage() {
		return getPropertyValue("dt.analyze.maxLessThanMin.message");
	}
	
	public String declTableSection_tooltip() {
		return getPropertyValue("decl.table.section_tooltip");
	}
	
	public String decisionTableSection_tooltip() {
		return getPropertyValue("dt.table.section_tooltip");
	}
	
	public String exceptionTableSection_tooltip() {
		return getPropertyValue("exp.table.section_tooltip");
	}

	public String decisionTableAnalyzer_tooltip() {
		return getPropertyValue("dt.table.analyzer_tooltip");
	}

	public String dtCannotRemoveConditionColumn_message() {
		return getPropertyValue("dt.cannotRemoveConditionColumn.message");
	}
	
	public String dt_atleastOne_condition_message() {
		return getPropertyValue("dt_atleastOne_condition_message");
	}
	
	public String dtHasUnsavedChanges_message() {
		return getPropertyValue("dt.editor.hasUnsavedChanges.message");
	}

	public String wsdtimportwarningtitle() {
		return getPropertyValue("DecisionTable.import.warning.title");
	}

	public String wsdtimportoverwriteoption() {
		return getPropertyValue("DecisionTable.import.overwrite.option");
	}

	public String wsdtimportmergeoption() {
		return getPropertyValue("DecisionTable.import.merge.option");
	}

	public String dtProblemPaneRecordDelConfMsg() {
		return getPropertyValue("DecisionTable.ProblemPane.record.delete.confirmation.message");
	}

	public String dtProblemPaneRecordDelMenuTitle() {
		return getPropertyValue("DecisionTable.ProblemPane.record.delete.menu.title");
	}

	public String dtProblemPaneRecordDelConfDialogTitle() {
		return getPropertyValue("DecisionTable.ProblemPane.record.delete.confirmation.dialog.title");
	}

	public String dt_tableAnalyzer_min() {
		return getPropertyValue("dt_tableAnalyzer_min");
	}

	public String dt_tableAnalyzer_max() {
		return getPropertyValue("dt_tableAnalyzer_max");
	}

	public String dt_filter_menu_title() {
		return getPropertyValue("dt_filter_menu_title");
	}

	public String dt_filter_remove() {
		return getPropertyValue("dt_filter_remove");
	}

	public String dt_problems_gridHeader_description() {
		return getPropertyValue("dt_problems_gridHeader_description");
	}
	
	public String dt_problems_gridHeader_resource() {
		return getPropertyValue("dt_problems_gridHeader_resource");
	}
	
	public String dt_problems_gridHeader_project() {
		return getPropertyValue("dt_problems_gridHeader_project");
	}

	public String dt_problems_gridHeader_path() {
		return getPropertyValue("dt_problems_gridHeader_path");
	}

	public String dt_problems_gridHeader_location() {
		return getPropertyValue("dt_problems_gridHeader_location");
	}

	public String dt_problems_gridHeader_type() {
		return getPropertyValue("dt_problems_gridHeader_type");
	}
	
	public String dt_problems_gridHeader_problemType() {
		return getPropertyValue("dt_problems_gridHeader_problemType");
	}
	
	public String dt_problems_items() {
		return getPropertyValue("dt_problems_items");
	}
	
	public String dt_declarations_gridHeader_path() {
		return getPropertyValue("dt_declarations_gridHeader_path");
	}
	
	public String dt_declarations_gridHeader_alias() {
		return getPropertyValue("dt_declarations_gridHeader_alias");
	}
	
	public String dt_declarations_gridHeader_array() {
		return getPropertyValue("dt_declarations_gridHeader_array");
	}
	
	public String dt_conditions_spanHeader() {
		return getPropertyValue("dt_conditions_spanHeader");
	}
	
	public String dt_actions_spanHeader() {
		return getPropertyValue("dt_actions_spanHeader");
	}
	
	public String dt_column_customCondition() {
		return getPropertyValue("dt_column_customCondition");
	}
	
	public String dt_column_customAction() {
		return getPropertyValue("dt_column_customAction");
	}
	
	public String dt_fieldSettings_menu() {
		return getPropertyValue("dt_fieldSettings_menu");
	}
	
	public String dt_fieldSettings_dialog_title() {
		return getPropertyValue("dt_fieldSettings_dialog_title");
	}
	
	public String dt_fieldSettings_dialog_nameField() {
		return getPropertyValue("dt_fieldSettings_dialog_nameField");
	}
	
	public String dt_fieldSettings_dialog_aliasField() {
		return getPropertyValue("dt_fieldSettings_dialog_aliasField");
	}
	
	public String dt_fieldSettings_dialag_idField() {
		return getPropertyValue("dt_fieldSettings_dialag_idField");
	}
	
	public String dt_fieldSettings_dialag_includeExisitingRules() {
		return getPropertyValue("dt_fieldSettings_dialag_includeExisitingRules");
	}
	
	public String dt_fieldSettings_dialag_defaultExpr() {
		return getPropertyValue("dt_fieldSettings_dialag_defaultExpr");
	}
	
	public String dt_moveColumn_menu() {
		return getPropertyValue("dt_moveColumn_menu");
	}
	
	public String dt_moveColumn_to_beginning() {
		return getPropertyValue("dt_moveColumn_to_beginning");
	}
	
	public String dt_moveColumn_to_left() {
		return getPropertyValue("dt_moveColumn_to_left");
	}
	
	public String dt_moveColumn_to_right() {
		return getPropertyValue("dt_moveColumn_to_right");
	}
	
	public String dt_moveColumn_to_end() {
		return getPropertyValue("dt_moveColumn_to_end");
	}
	
	public String dt_moveColumn_right_restricted_message() {
		return getPropertyValue("dt_moveColumn_right_restricted_message");
	}
	
	public String dt_moveColumn_left_restricted_message() {
		return getPropertyValue("dt_moveColumn_left_restricted_message");
	}
	
	public String dt_pagination_showAll() {
		return getPropertyValue("dt_pagination_showAll");
	}
	
	public String dt_pagination_showPages() {
		return getPropertyValue("dt_pagination_showPages");
	}
	
	public String dt_pagination_showByPage() {
		return getPropertyValue("dt_pagination_showByPage");
	}
	
	public String dt_pagination_showAllRows() {
		return getPropertyValue("dt_pagination_showAllRows");
	}
	
	public String dt_pagination_firstPage() {
		return getPropertyValue("dt_pagination_firstPage");
	}
	
	public String dt_pagination_firstPage_tooltip() {
		return getPropertyValue("dt_pagination_firstPage_tooltip");
	}
	
	public String dt_pagination_previousPage() {
		return getPropertyValue("dt_pagination_previousPage");
	}
	
	public String dt_pagination_previousPage_tooltip() {
		return getPropertyValue("dt_pagination_previousPage_tooltip");
	}
	
	public String dt_pagination_nexttPage() {
		return getPropertyValue("dt_pagination_nexttPage");
	}
	
	public String dt_pagination_nextPage_tooltip() {
		return getPropertyValue("dt_pagination_nextPage_tooltip");
	}
	
	public String dt_pagination_lastPage() {
		return getPropertyValue("dt_pagination_lastPage");
	}
	
	public String dt_pagination_lastPage_tooltip() {
		return getPropertyValue("dt_pagination_lastPage_tooltip");
	}
	
	public String dt_pagination_page() {
		return getPropertyValue("dt_pagination_page");
	}
	
	public String dt_pagination_page_tooltip() {
		return getPropertyValue("dt_pagination_page_tooltip");
	}
	
	public String dt_pagination_page_of() {
		return getPropertyValue("dt_pagination_page_of");
	}
	
	public String dt_pagination_pages() {
		return getPropertyValue("dt_pagination_pages");
	}
	
	public String dt_pagination_invalidPage_message() {
		return getPropertyValue("dt_pagination_invalidPage_message");
	}
	
	public String dt_pagination_discardPage_message() {
		return getPropertyValue("dt_pagination_discardPage_message");
	}
	
	public String dt_column() {
		return getPropertyValue("dt_column");
	}
	
	public String dt_validation_errorType_Syntax() {
		return getPropertyValue("dt_validation_errorType_Syntax");
	}
	
	public String dt_validation_errorType_Semantic() {
		return getPropertyValue("dt_validation_errorType_Semantic");
	}
	
	public String dt_validation_errorType_Warning() {
		return getPropertyValue("dt_validation_errorType_Warning");
	}
	
	public String dt_validation_errorType_Internal() {
		return getPropertyValue("dt_validation_errorType_Internal");
	}
	
	public String dt_analyze_problem() {
		return getPropertyValue("dt_analyze_problem");
	}
	
	public String dt_ruleCombination_problem() {
		return getPropertyValue("dt_ruleCombination_problem");
	}
	
	public String dt_dataSource_textField_value() {
		return getPropertyValue("dt_dataSource_textField_value");
	}
	
	public String dt_dataSource_textField_description() {
		return getPropertyValue("dt_dataSource_textField_description");
	}
}
