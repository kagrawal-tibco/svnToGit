package com.tibco.cep.webstudio.client.portal;

import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;

/**
 * Enum for different types of Portlets supported
 * @author vpatil
 */
public enum DASHBOARD_PORTLETS {
	
	MY_FAVORITES(((GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES)).portlet_myFavorites_title()), 
	RECENTLY_OPENED(((GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES)).portlet_recentlyOpened_title()),
	AVALIABLE_PROJECTS(((GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES)).portlet_myProjects_title()), 
	CUSTOM_WEBPAGE(((GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES)).portlet_customWebpage_title()), 
	TIBBR(((GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES)).portlet_tibbr_title()), 
	NOTIFICATIONS(((GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES)).portlet_notifications_title()), 
	WORKLIST(((GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES)).portlet_worklist_title()), 
	PREFERENCES(((GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES)).portlet_preferences_title()), 
	CONFIGURATION(((GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES)).portlet_configuration_title()),
	GROUPS(((GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES)).portlet_groups_title()), 
	DOCUMENTATION(((GlobalMessages) I18nRegistry
			.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES)).portlet_documentation_title());
	
    private String title;

    DASHBOARD_PORTLETS(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}
