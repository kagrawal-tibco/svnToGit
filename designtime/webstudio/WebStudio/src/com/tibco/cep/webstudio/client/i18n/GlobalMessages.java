package com.tibco.cep.webstudio.client.i18n;

import java.util.Map;

/**
 * This class routes all GlobalMessages properties. It enables the use of dynamic(created post war compile) properties files.<br/>
 * If the requested property is not found in custom properties it simply uses the property strings from locale property files.
 * 
 * @author moshaikh
 *
 */
public class GlobalMessages implements I18nMessages {
	
	private Map<String, String> globalMessages;
	
	public GlobalMessages(Map<String, String> globalMessages) {
		this.globalMessages = globalMessages;
	}
	
	/**
	 * Returns value(if available) for the specified key from the messages passed. Return null if custom properties file or the key is not available.
	 * @param messages
	 * @param key
	 * @param replaceValues
	 * @return
	 */
	public static String getPropertyValue(Map<String, String> messages, String key, String... replaceValues) {
		String message = messages.get(key);
		if (message != null && replaceValues != null) {
			for (int i=0; i<replaceValues.length; i++) {
				message = message.replace("{" + i + "}", replaceValues[i]);
			}
		}
		return message;
	}
	
	private String getPropertyValue(String key, String... replaceValues) {
		return getPropertyValue(globalMessages, key, replaceValues);
	}
	
	public String button_ok() {
		return getPropertyValue("button_ok");
	}
	
	public String button_cancel() {
		return getPropertyValue("button_cancel");
	}
	
	public String button_apply() {
		return getPropertyValue("button_apply");
	}
	
	public String button_reset() {
		return getPropertyValue("button_reset");
	}
	
	public String message_noData() {
		return getPropertyValue("message_noData");
	}
	
	public String message_browseButton_emptyMessage(){
		return getPropertyValue("message_browseButton_emptyMessage");
	}
	public String text_files() {
		return getPropertyValue("text_files");
	}
	
	public String text_comments() {
		return getPropertyValue("text_comments");
	}
	
	public String text_welcome() {
		return getPropertyValue("text_welcome");
	}
	
	public String text_signout() {
		return getPropertyValue("text_signout");
	}
	
	public String text_myWorkspace() {
		return getPropertyValue("text_myWorkspace");
	}
	
	public String text_dashboard() {
		return getPropertyValue("text_dashboard");
	}
	
	public String text_settings() {
		return getPropertyValue("text_settings");
	}
	
	public String text_problems() {
		return getPropertyValue("text_problems");
	}
	
	public String text_properties() {
		return getPropertyValue("text_properties");
	}
	
	public String text_warningsCount() {
		return getPropertyValue("text_warningsCount");
	}
	
	public String text_progress() {
		return getPropertyValue("text_progress");
	}
	
	public String text_errorsCount() {
		return getPropertyValue("text_errorsCount");
	}
	
	public String text_note() {
		return getPropertyValue("text_note");
	}
	
	public String text_warning() {
		return getPropertyValue("text_warning");
	}
	
	public String text_optional() {
		return getPropertyValue("text_optional");
	}
	
	public String text_required() {
		return getPropertyValue("text_required");
	}
	
	public String text_recommended() {
		return getPropertyValue("text_recommended");
	}
	
	public String text_priority() {
		return getPropertyValue("text_priority");
	}
	
	public String text_highest() {
		return getPropertyValue("text_highest");
	}
	
	public String text_lowest() {
		return getPropertyValue("text_lowest");
	}
	
	public String text_description() {
		return getPropertyValue("text_description");
	}
	
	public String text_configuration() {
		return getPropertyValue("text_configuration");
	}
	
	public String text_declaration() {
		return getPropertyValue("text_declaration");
	}
	
	public String text_refresh() {
		return getPropertyValue("text_refresh");
	}
	
	public String text_analyze() {
		return getPropertyValue("text_analyze");
	}
	
	public String text_showCoverage() {
		return getPropertyValue("text_showCoverage");
	}
	
	public String text_showTestData_Coverage() {
		return getPropertyValue("text_showTestData_Coverage");
	}
	
	public String toolbar_save() {
		return getPropertyValue("toolbar_save");
	}
	
	public String toolbar_delete() {
		return getPropertyValue("toolbar_delete");
	}
	
	public String toolbar_cut() {
		return getPropertyValue("toolbar_cut");
	}
	
	public String toolbar_paste() {
		return getPropertyValue("toolbar_paste");
	}
	
	public String toolbar_copy() {
		return getPropertyValue("toolbar_copy");
	}
	
	public String toolbar_execute() {
		return getPropertyValue("toolbar_execute");
	}
	
	public String toolbar_analyse() {
		return getPropertyValue("toolbar_analyse");
	}
	
	public String toolbar_validate() {
		return getPropertyValue("toolbar_validate");
	}
	
	public String toolbar_rms_checkout() {
		return getPropertyValue("toolbar_rms_checkout");
	}
	
	public String toolbar_rms_commit() {
		return getPropertyValue("toolbar_rms_commit");
	}
	
	public String toolbar_rms_update() {
		return getPropertyValue("toolbar_rms_update");
	}
	
	public String toolbar_rms_worklist() {
		return getPropertyValue("toolbar_rms_worklist");
	}
	
	public String toolbar_deploy() {
		return getPropertyValue("toolbar_deploy");
	}
	
	public String toolbar_export() {
		return getPropertyValue("toolbar_export");
	}
	
	public String toolbar_import() {
		return getPropertyValue("toolbar_import");
	}
	
	public String menu_new() {
		return getPropertyValue("menu_new");
	}
	
	public String menu_newStudioProject() {
		return getPropertyValue("menu_newStudioProject");
	}
	
	public String menu_newResource() {
		return getPropertyValue("menu_newResource");
	}
	
	public String menu_newResource_concept() {
		return getPropertyValue("menu_newResource_concept");
	}
	
	public String menu_newResource_event() {
		return getPropertyValue("menu_newResource_event");
	}
	
	public String menu_newResource_channel() {
		return getPropertyValue("menu_newResource_channel");
	}
	
	public String menu_newResource_scorecard() {
		return getPropertyValue("menu_newResource_scorecard");
	}
	
	public String menu_newResource_rule() {
		return getPropertyValue("menu_newResource_rule");
	}
	
	public String menu_newResource_rulefunction() {
		return getPropertyValue("menu_newResource_rulefunction");
	}
	
	public String menu_edit() {
		return getPropertyValue("menu_edit");
	}
	
	public String menu_editCut() {
		return getPropertyValue("menu_editCut");
	}
	
	public String menu_editCopy() {
		return getPropertyValue("menu_editCopy");
	}
	
	public String menu_editPaste() {
		return getPropertyValue("menu_editPaste");
	}
	
	public String menu_editDelete() {
		return getPropertyValue("menu_editDelete");
	}
	
	public String menu_export() {
		return getPropertyValue("menu_export");
	}
	
	public String menu_import() {
		return getPropertyValue("menu_import");
	}
	
	public String menu_importStudioProject() {
		return getPropertyValue("menu_importStudioProject");
	}
	
	public String menu_importDecisionTable() {
		return getPropertyValue("menu_importDecisionTable");
	}
	
	public String menu_project() {
		return getPropertyValue("menu_project");
	}
	
	public String menu_projectValidate() {
		return getPropertyValue("menu_projectValidate");
	}
	
	public String menu_projectAnalyze() {
		return getPropertyValue("menu_projectAnalyze");
	}
	
	public String menu_projectView() {
		return getPropertyValue("menu_projectView");
	}
	
	public String menu_table() {
		return getPropertyValue("menu_table");
	}

	public String menu_tableOpen() {
		return getPropertyValue("menu_tableOpen");
	}

	public String menu_tableAnalyze() {
		return getPropertyValue("menu_tableAnalyze");
	}

	public String menu_rule() {
		return getPropertyValue("menu_rule");
	}

	public String menu_ruleAnalyze() {
		return getPropertyValue("menu_ruleAnalyze");
	}

	public String menu_process() {
		return getPropertyValue("menu_process");
	}

	public String menu_processGenerateCode() {
		return getPropertyValue("menu_processGenerateCode");
	}

	public String menu_rms() {
		return getPropertyValue("menu_rms");
	}

	public String menu_rmsCheckout() {
		return getPropertyValue("menu_rmsCheckout");
	}
	
	public String menu_rmsCommit() {
		return getPropertyValue("menu_rmsCommit");
	}
	
	public String menu_rmsUpdate() {
		return getPropertyValue("menu_rmsUpdate");
	}
	
	public String menu_rmsHistory() {
		return getPropertyValue("menu_rmsHistory");
	}
	
	public String menu_rmsWorkList() {
		return getPropertyValue("menu_rmsWorkList");
	}
	
	public String menu_rmsDeployable() {
		return getPropertyValue("menu_rmsDeployable");
	}
	
	public String menu_rmsManageLocks() {
		return getPropertyValue("menu_rmsManageLocks");
	}
	
	public String menu_rmsLock() {
		return getPropertyValue("menu_rmsLock");
	}
	
	public String menu_rmsUnLock() {
		return getPropertyValue("menu_rmsUnLock");
	}
	
	public String menu_rmsRename() {
		return getPropertyValue("menu_rmsRename");
	}
	
	public String menu_rmsDiff() {
		return getPropertyValue("menu_rmsDiff");
	}
	
	public String menu_rmsShowDiff() {
		return getPropertyValue("menu_rmsShowDiff");
	}
	
	public String menu_tibbr() {
		return getPropertyValue("menu_tibbr");
	}
	
	public String menu_tibbrLogin() {
		return getPropertyValue("menu_tibbrLogin");
	}
	
	public String menu_help() {
		return getPropertyValue("menu_help");
	}
	
	public String menu_helpOnthisPage() {
		return getPropertyValue("menu_helpOnthisPage");
	}
	
	public String menu_helpContents() {
		return getPropertyValue("menu_helpContents");
	}
	
	public String menu_helpAbout() {
		return getPropertyValue("menu_helpAbout");
	}
	
	public String projectExplorer_favourites() {
		return getPropertyValue("projectExplorer_favourites");
	}
	
	public String projectExplorer_ruleTemplate() {
		return getPropertyValue("projectExplorer_ruleTemplate");
	}
	
	public String projectExplorer_rf_arguments() {
		return getPropertyValue("projectExplorer_rf_arguments");
	}
	
	public String projectExplorer_rf_hide_arguments() {
		return getPropertyValue("projectExplorer_rf_hide_arguments");
	}
	
	public String projectExplorer_new_dt() {
		return getPropertyValue("projectExplorer_new_dt");
	}
	
	public String projectExplorer_new_folder() {
		return getPropertyValue("projectExplorer_new_folder");
	}
	
	public String projectExplorer_new_process() {
		return getPropertyValue("projectExplorer_new_process");
	}
	
	public String button_add() {
		return getPropertyValue("button.add");
	}
	
	public String button_delete() {
		return getPropertyValue("button.delete");
	}
	
	public String button_login() {
		return getPropertyValue("button_login");
	}
	
	public String button_close() {
		return getPropertyValue("button_close");
	}
	
	public String wsErrorDialogDescription(String error) {
		return getPropertyValue("ws.error.dialog.exception.desc", error);
	}
	
	public String wsErrorDialogDetailsOn() {
		return getPropertyValue("ws.error.dialog.details.on");
	}
	
	public String wsErrorDialogDetailsOff() {
		return getPropertyValue("ws.error.dialog.details.off");
	}
	
	public String wsErrorDialogTitle() {
		return getPropertyValue("ws.error.dialog.title");
	}
	
	public String wsErrorDialogOKButton() {
		return getPropertyValue("ws.error.dialog.button");
	}
	
	public String wsErrorDialogWithDetailsDescription(String error) {
		return getPropertyValue("ws.error.dialog.with.details.desc", error);
	}
	
	public String wsErrorDialogWithNoDetailsDescription(String error) {
		return getPropertyValue("ws.error.dialog.with.no.details.desc", error);
	}
	
	public String wsErrorDialogWithNoDetailsButtonDescription(String error) {
		return getPropertyValue("ws.error.dialog.exception.details.button.desc", error);
	}
	
	public String wsShowNoIssues() {
		return getPropertyValue("ws.show.problems.no.issues");
	}
	
	public String wsShowIssues(Object param1, Object param2) {
		return getPropertyValue("ws.show.problems.issues", param1.toString(), param2.toString());
	}
 	
	public String wsShowTAIssues(Object param1, Object param2) {
		return getPropertyValue("ws.show.ta.problems.issues", param1.toString(), param2.toString());
	}
	
	public String explorer_OpenResource_error() {
		return getPropertyValue("explorer_OpenResource_error");
	}
	
	public String contextMenu_groups() {
		return getPropertyValue("contextMenu_groups");
	}
	
	public String contextMenu_subMenu_addTo() {
		return getPropertyValue("contextMenu_subMenu_addTo");
	}
	
	public String contextMenu_subMenu_removeFrom() {
		return getPropertyValue("contextMenu_subMenu_removeFrom");
	}
	
	public String contextMenu_delete() {
		return getPropertyValue("contextMenu_delete");
	}
	
	public String dialog_importartifact_title(String artifactType) {
		return getPropertyValue("dialog_importartifact_title", artifactType);
	}
	
	public String dialog_createartifact_title(String artifactType) {
		return getPropertyValue("dialog_createartifact_title", artifactType);
	}
	
	public String dialog_renameartifact_title() {
		return getPropertyValue("dialog_renameartifact_title");
	}
	
	public String dialog_renameartifact_message(String artifactName) {
		return getPropertyValue("dialog_renameartifact_message", artifactName);
	}
	
	public String createNew_resource_rti() {
		return getPropertyValue("createNew_resource_rti");
	}
	
	public String createNew_resource_dt() {
		return getPropertyValue("createNew_resource_dt");
	}
	
	public String createNew_resource_folder() {
		return getPropertyValue("createNew_resource_folder");
	}
	
	public String createNew_resource_parentFolder() {
		return getPropertyValue("createNew_resource_parentFolder");
	}
	
	public String createNew_resource_existingResourceErrorMessage(String resourceName) {
		return getPropertyValue("createNew_resource_existingResourceErrorMessage", resourceName);
	}
	
	public String createNew_resource_emptyResourceErrorMessage(String resourceName) {
		return getPropertyValue("createNew_resource_emptyResourceErrorMessage", resourceName);
	}
	
	public String createNew_resource_invalidResourceErrorMessage(String value, String resourceName) {
		return getPropertyValue("createNew_resource_invalidResourceErrorMessage", value, resourceName);
	}
	
	public String createNew_resourceName(String resourceName) {
		return getPropertyValue("createNew_resourceName", resourceName);
	}
	
	public String serverConnection_error() {
		return getPropertyValue("serverConnection_error");
	}
	
	public String decisionManagerInstall_error() {
		return getPropertyValue("decisionManagerInstall_error");
	}
	
	public String groupDelete_confirmMessage() {
		return getPropertyValue("groupDelete_confirm_message");
	}
	
	public String userNameText() {
		return getPropertyValue("userNameText");
	}
	
	public String passwordText() {
		return getPropertyValue("passwordText");
	}
	
	public String userName_mandatoryField_message() {
		return getPropertyValue("userName_mandatoryField_message");
	}
	
	public String password_mandatoryField_message() {
		return getPropertyValue("password_mandatoryField_message");
	}
	
	public String force_userlogin_text() {
		return getPropertyValue("force_userlogin_text");
	}
	
	public String force_userlogin_title() {
		return getPropertyValue("force_userlogin_title");
	}
	
	public String message_noResourceSelected() {
		return getPropertyValue("message_noResourceSelected");
	}
	
	public String message_unsupportedBrowser() {
		return getPropertyValue("message_unsupportedBrowser");
	}
	
	public String message_recommendedBrowser() {
		return getPropertyValue("message_recommendedBrowser");
	}
	
	public String message_detectedBrowser(String browserName) {
		return getPropertyValue("message_detectedBrowser", browserName);
	}
	
	public String groupAdd_tooltip() {
		return getPropertyValue("groupAdd_tooltip");
	}
	
	public String groupAddNew_title() {
		return getPropertyValue("groupAddNew_title");
	}
	
	public String groupDelete_tooltip() {
		return getPropertyValue("groupDelete_tooltip");
	}
	
	public String groupDelete_denied() {
		return getPropertyValue("groupDelete_denied");
	}
	
	public String groupAdd_nameText() {
		return getPropertyValue("groupAdd_nameText");
	}
	
	public String groupAdd_typeText() {
		return getPropertyValue("groupAdd_typeText");
	}
	
	public String groups_sectionTitle() {
		return getPropertyValue("groups_sectionTitle");
	}
	
	public String groupContents_sectionTitle() {
		return getPropertyValue("groupContents_sectionTitle");
	}
	
	public String groupContents_showAsTree() {
		return getPropertyValue("groupContents_showAsTree");
	}
	
	public String groupContents_showAsList() {
		return getPropertyValue("groupContents_showAsList");
	}
	
	public String group_alreadyExists_message(String groupName) {
		return getPropertyValue("group_alreadyExists_message", groupName);
	}
	
	public String groupAdd_selectArtifacts() {
		return getPropertyValue("groupAdd_selectArtifacts");
	}
	
	public String groupAdd_availableArtifacts() {
		return getPropertyValue("groupAdd_availableArtifacts");
	}
	
	public String groupAdd_selectedArtifacts() {
		return getPropertyValue("groupAdd_selectedArtifacts");
	}
	
	public String text_dashboardAppearance() {
		return getPropertyValue("text_dashboardAppearance");
	}
	
	public String text_numberOfColumns() {
		return getPropertyValue("text_numberOfColumns");
	}
	
	public String text_dashboardPortlets() {
		return getPropertyValue("text_dashboardPortlets");
	}
	
	public String text_preferences() {
		return getPropertyValue("text_preferences");
	}
	
	public String text_numberOfRecentItems() {
		return getPropertyValue("text_numberOfRecentItems");
	}
	
	public String text_defaultURLForCustomWebpage() {
		return getPropertyValue("text_defaultURLForCustomWebpage");
	}
	
	public String text_scsUserName() {
		return getPropertyValue("text_scsUserName");
	}
	
	public String text_scsUserPassword() {
		return getPropertyValue("text_scsUserPassword");
	}
	
	public String removeSelectedDashboardPortlets_button() {
		return getPropertyValue("removeSelectedDashboardPortlets_button");
	}
	
	public String addDashboardPortlet_button() {
		return getPropertyValue("addDashboardPortlet_button");
	}
	
	public String addWebPagePortlet_button() {
		return getPropertyValue("addWebPagePortlet_button");
	}
	
	public String addWebPagePortlet_tooltip() {
		return getPropertyValue("addWebPagePortlet_tooltip");
	}
	
	public String addWebPagePortler_enterURL_message() {
		return getPropertyValue("addWebPagePortler_enterURL_message");
	}
	
	public String selectRecordBeforeAction_message() {
		return getPropertyValue("selectRecordBeforeAction_message");
	}
	
	public String button_select_all() {
		return getPropertyValue("button_selectAll_message");
	}
	
	public String button_deselect_all() {
		return getPropertyValue("button_deselectAll_message");
	}
	
	public String button_finish() {
		return getPropertyValue("button_finish");
	}
	
	public String portlet_myFavorites_title() {
		return getPropertyValue("portlet_myFavorites_title");
	}
	
	public String portlet_recentlyOpened_title() {
		return getPropertyValue("portlet_recentlyOpened_title");
	}
	
	public String portlet_myProjects_title() {
		return getPropertyValue("portlet_myProjects_title");
	}
	
	public String portlet_customWebpage_title() {
		return getPropertyValue("portlet_customWebpage_title");
	}
	
	public String portlet_notifications_title() {
		return getPropertyValue("portlet_notifications_title");
	}
	
	public String portlet_worklist_title() {
		return getPropertyValue("portlet_worklist_title");
	}
	
	public String portlet_preferences_title() {
		return getPropertyValue("portlet_preferences_title");
	}
	
	public String portlet_configuration_title() {
		return getPropertyValue("portlet_configuration_title");
	}
	
	public String portlet_groups_title() {
		return getPropertyValue("portlet_groups_title");
	}
	
	public String portlet_documentation_title() {
		return getPropertyValue("portlet_documentation_title");
	}
	
	public String portlet_confirm_close_text() {
		return getPropertyValue("portlet_confirm_close_text");
	}
	
	public String portlet_confirm_close_title() {
		return getPropertyValue("portlet_confirm_close_title");
	}
	
	public String portlet_remove_project() {
		return getPropertyValue("portlet_remove_project");
	}
	
	public String portlet_tibbr_title() {
		return getPropertyValue("portlet_tibbr_title");
	}
	
	public String toolbar_option_edit() {
		return getPropertyValue("toolbar_option_edit");
	}
	
	public String toolbar_option_tools() {
		return getPropertyValue("toolbar_option_tools");
	}
	
	public String toolbar_option_rms() {
		return getPropertyValue("toolbar_option_rms");
	}
	
	public String groups_projects_title() {
		return getPropertyValue("groups_projects_title");
	}
	
	public String groups_businessRules_title() {
		return getPropertyValue("groups_businessRules_title");
	}
	
	public String groups_decisionTables_title() {
		return getPropertyValue("groups_decisionTables_title");
	}
	
	public String preferences_SCS_Validation_Error_Message() {
		return getPropertyValue("preferences_SCS_Validation_Error_Message");
	}
	
	public String preferences_project_view_tree() {
		return getPropertyValue("preferences_project_view_tree");
	}
	
	public String preferences_project_view_list() {
		return getPropertyValue("preferences_project_view_list");
	}
	
	public String preferences_autofit_default_option() {
		return getPropertyValue("preferences_autofit_default_option");
	}
	
	public String preferences_autofit_value_option() {
		return getPropertyValue("preferences_autofit_value_option");
	}
	
	public String preferences_autofit_title_option() {
		return getPropertyValue("preferences_autofit_title_option");
	}
	
	public String preferences_autofit_both_option() {
		return getPropertyValue("preferences_autofit_both_option");
	}
	
	public String preferences_rti_startingFilter_MatchAny() {
		return getPropertyValue("preferences_rti_startingFilter_MatchAny");
	}
	
	public String preferences_rti_startingFilter_MatchAll() {
		return getPropertyValue("preferences_rti_startingFilter_MatchAll");
	}
	
	public String preferences_rti_startingFilter_MatchNone() {
		return getPropertyValue("preferences_rti_startingFilter_MatchNone");
	}
	
	public String text_previousValue() {
		return getPropertyValue("text_previousValue");
	}
	
	public String text_confirm() {
		return getPropertyValue("text_confirm");
	}
	
	public String text_fields_of_an_entity(String entity) {
		return getPropertyValue("text_fields_of_an_entity", entity);
	}
	
	public String toolbar_rms_revert() {
		return getPropertyValue("toolbar_rms_revert");
	}
	
	public String menu_rmsRevert() {
		return getPropertyValue("menu_rmsRevert");
	}
	
	public String menu_rmsSyncToRepository() {
		return getPropertyValue("menu_rmsSyncToRepository");
	}
	
	public String button_remove() {
		return getPropertyValue("button.remove");
	}
	
	public String explorer_ViewResource_error() {
		return getPropertyValue("explorer_ViewResource_error");
	}
	
	public String explorer_DiffResource_error() {
		return getPropertyValue("explorer_DiffResource_error");
	}
	
	public String diff_legend_added() {
		return getPropertyValue("diff_legend_added");
	}
	
	public String diff_legend_deleted() {
		return getPropertyValue("diff_legend_deleted");
	}
	
	public String diff_legend_modified() {
		return getPropertyValue("diff_legend_modified");
	}
	
	public String diff_legend_dnd() {
		return getPropertyValue("diff_legend_dnd");
	}
	
	public String ask_value_dialog_title() {
		return getPropertyValue("ask_value_dialog_title");
	}
	
	public String text_itemViewPreference() {
		return getPropertyValue("text_itemViewPreference");
	}
	
	public String text_itemRtiStartingFilter() {
		return getPropertyValue("text_itemRtiStartingFilter");
	}
	
	public String text_decisionTablePageSize() {
		return getPropertyValue("text_decisionTablePageSize");
	}
	
	public String text_autoUnLockOnReview() {
		return getPropertyValue("text_autoUnLockOnReview");
	}
	
	public String text_groupRelatedArtifacts() {
		return getPropertyValue("text_groupRelatedArtifacts");
	}
	
	public String text_showColumnAliasIfPresent() {
		return getPropertyValue("text_showColumnAliasIfPresent");
	}
	
	public String text_autoFitAllColumnsforDecisiontable() {
		return getPropertyValue("text_autoFitAllColumnsforDecisiontable");
	}
	
	public String text_allowCustomDomainValues() {
		return getPropertyValue("text_allowCustomDomainValues");
	}
	
	public String groups_processes_title() {
		return getPropertyValue("groups_processes_title");
	}
	
	public String button_search() {
		return getPropertyValue("button_search");
	}
	
	public String search_hint() {
		return getPropertyValue("search_hint");
	}
	
	public String text_maximize() {
		return getPropertyValue("text_maximize");
	}
	
	public String text_restore() {
		return getPropertyValue("text_restore");
	}
	
	public String ws_aboutDialog_title() {
		return getPropertyValue("ws_aboutDialog_title");
	}
	
	public String dialog_importartifact_name() {
		return getPropertyValue("dialog_importartifact_name");
	}
	
	public String dialog_importartifact_select() {
		return getPropertyValue("dialog_importartifact_select");
	}
	
	public String dialog_importartifact_invalidFileMessage() {
		return getPropertyValue("dialog_importartifact_invalidFileMessage");
	}
	
	public String dialog_importartifact_noFileMessage() {
		return getPropertyValue("dialog_importartifact_noFileMessage");
	}
	
	public String message_confirm_discardChanges() {
		return getPropertyValue("message_confirm_discardChanges");
	}
	
	public String message_confirm_deleteArtifact(String type, String artifactName) {
		return getPropertyValue("message_confirm_deleteArtifact", type == null ? "" : type, artifactName == null ? "" : artifactName);
	}
	
	public String message_help_notImplemented() {
		return getPropertyValue("message_help_notImplemented");
	}
	
	public String portlet_availablePortlets_title() {
		return getPropertyValue("portlet_availablePortlets_title");
	}
	
	public String portlet_adduser_tooltip() {
		return getPropertyValue("portlet_adduser_tooltip");
	}
	
	public String portlet_removeuser_tooltip() {
		return getPropertyValue("portlet_removeuser_tooltip");
	}
	
	public String portlet_projectlist_title() {
		return getPropertyValue("portlet_projectlist_title");
	}
	
	public String portlet_resources_title() {
		return getPropertyValue("portlet_resources_title");
	}
	
	public String portlet_roles_grid_title() {
		return getPropertyValue("portlet_roles_grid_title");
	}
	
	public String portlet_addrole_tooltip() {
		return getPropertyValue("portlet_addrole_tooltip");
	}
	
	public String portlet_removerole_tooltip() {
		return getPropertyValue("portlet_removerole_tooltip");
	}
	
	public String noProjects_checkedOut_preText() {
		return getPropertyValue("noProjects_checkedOut_preText");
	}
	
	public String noProjects_checkedOut_linkText() {
		return getPropertyValue("noProjects_checkedOut_linkText");
	}
	
	public String createNew_resource_process() {		
		return getPropertyValue("createNew_resource_process");
	}
	
	public String bpmnaddonInstall_error() {		
		return getPropertyValue("bpmnaddonInstall_error");
	}
	
	public String application_preferences() {
		return getPropertyValue("application_preferences");
	}
	
	public String operator_preferences() {
		return getPropertyValue("operator_preferences");
	}
	
	public String filter_operators() {
		return getPropertyValue("filter_operators");
	}
	
	public String command_operators() {
		return getPropertyValue("command_operators");
	}
	
	public String field_type() {
		return getPropertyValue("field_type");
	}
	
	public String datepicker_empty_label() {
		return getPropertyValue("datepicker_empty_label");
	}
	
	public String datepicker_hours_tip() {
		return getPropertyValue("datepicker_hours_tip");
	}
	
	public String datepicker_minutes_tip() {
		return getPropertyValue("datepicker_minutes_tip");
	}
	
	public String datepicker_seconds_tip() {
		return getPropertyValue("datepicker_seconds_tip");
	}
	
	public String progressMessage_pleaseWait() {
		return getPropertyValue("progressMessage_pleaseWait");
	}
	
	public String progressMessage_coverageDT() {
		return getPropertyValue("progressMessage_coverageDT");
	}
	
	public String progressMessage_validatingBR() {
		return getPropertyValue("progressMessage_validatingBR");
	}
	
	public String progressMessage_validatingDT() {
		return getPropertyValue("progressMessage_validatingDT");
	}
	
	public String progressMessage_validatingProject() {
		return getPropertyValue("progressMessage_validatingProject");
	}
	
	public String selectResourceDialog_tilteMessage(){
		return getPropertyValue("dialog_selectResource_tilteMessage");
	}
	
	public String artifactNothaveApprovedChanges_errorMessage(){
		return getPropertyValue("artifactNothaveApprovedChanges_errorMessage");
	}
	
	public String artifactRename_successMessage(){
		return getPropertyValue("artifactRename_successMessage");
	}
	
	public String artifactRename_errorMessage(){
		return getPropertyValue("artifactRename_errorMessage");
	}
	
	public String editorTitleReadonly_Message() {
		return getPropertyValue("editorTitleReadonly_Message");
	}
	
	public String revisionInTitle_Tag() {
		return getPropertyValue("revisionInTitle_Tag");
	}
	
	public String noMatchingArtifactsFound_Message() {
		return getPropertyValue("noMatchingArtifactsFound_Message");
	}
	
	public String datepicker_select_date_time() {
		return getPropertyValue("datepicker_select_date_time");
	}
	
	public String aboutDialogBox_version() {
		return getPropertyValue("aboutDialogBox_version");
	}
	
	public String aboutDialogBox_copyright(String yearRange) {
		return getPropertyValue("aboutDialogBox_copyright", yearRange);
	}
	
	public String operatorSectionString(){
		return getPropertyValue("operator_section");
	}
	
	public String notificationSectionString(){
		return getPropertyValue("notification_section");
	}
	
	public String aclSectionString(){
		return getPropertyValue("acl_section");
	}
	
	public String notificationAction_list_String(){
		return getPropertyValue("notification_action_list");
	}
	
	public String notificationProject_list_String(){
		return getPropertyValue("notification_project_list");
	}
	
	public String notificationEmails_list_String(){
		return getPropertyValue("notification_emails_list");
	}
		
	public String aclUser_grid_title(){
		return getPropertyValue("acl_usergrid_title");
	}
	
}