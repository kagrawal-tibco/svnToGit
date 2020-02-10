package com.tibco.cep.webstudio.client.view;

import java.util.HashMap;

import com.google.gwt.i18n.client.LocaleInfo;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.webstudio.client.decisiontable.DecisionTableNavigatorResource;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RuleTemplateInstanceNavigatorResource;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil;
import com.tibco.cep.webstudio.model.rule.instance.DisplayModel;

public class WebStudioResourceCellFormatter implements CellFormatter {
	
	private static final HashMap<String, String> resourceTypeToDisplayName;
	
	private boolean showAsTree = false;
	
	static {
		resourceTypeToDisplayName = new HashMap<String, String>();
		resourceTypeToDisplayName.put(CommonIndexUtils.RULE_EXTENSION, "Rule");
		resourceTypeToDisplayName.put(CommonIndexUtils.RULEFUNCTION_EXTENSION, "Rule Function");
		resourceTypeToDisplayName.put(CommonIndexUtils.RULE_FN_IMPL_EXTENSION, "Decision Table");
		resourceTypeToDisplayName.put(CommonIndexUtils.RULE_TEMPLATE_EXTENSION, "Rule Template");
		resourceTypeToDisplayName.put(CommonIndexUtils.RULE_TEMPLATE_INSTANCE_EXTENSION, "Business Rule");
		resourceTypeToDisplayName.put(CommonIndexUtils.RULE_TEMPLATE_VIEW_EXTENSION, "Rule Template View");
		resourceTypeToDisplayName.put(CommonIndexUtils.EAR_EXTENSION, "Archive");
		resourceTypeToDisplayName.put(CommonIndexUtils.TIME_EXTENSION, "Time Event");
		resourceTypeToDisplayName.put(CommonIndexUtils.STATEMACHINE_EXTENSION, "State Model");
		resourceTypeToDisplayName.put(CommonIndexUtils.CONCEPT_EXTENSION, "Concept");
		resourceTypeToDisplayName.put(CommonIndexUtils.EVENT_EXTENSION, "Event");
		resourceTypeToDisplayName.put(CommonIndexUtils.SCORECARD_EXTENSION, "Scorecard");
		resourceTypeToDisplayName.put(CommonIndexUtils.DOMAIN_EXTENSION, "Domain Model");
		resourceTypeToDisplayName.put(CommonIndexUtils.CHANNEL_EXTENSION, "Channel");
		resourceTypeToDisplayName.put(CommonIndexUtils.GLOBAL_VAR_EXTENSION, "Global Variables");
		resourceTypeToDisplayName.put(CommonIndexUtils.PROCESS_EXTENSION, "Process");
	}
	
	public void setShowAsTree(boolean showAsTree) {
		this.showAsTree = showAsTree;
	}

	@Override
	public String format(Object value, ListGridRecord record, int rowNum,
			int colNum) {
		if (value instanceof String) {
			String resName = (String) value;
			int idx = resName.lastIndexOf('.');
			if (idx > 0) {
				String fileExt = resName.substring(idx+1);
				String fileName = resName.substring(0, idx);
				String parentFolder = null;
				if (showAsTree && ((record instanceof DecisionTableNavigatorResource) 
											|| (record instanceof RuleTemplateInstanceNavigatorResource))) {
					NavigatorResource navResource = (NavigatorResource) record;
					parentFolder = navResource.getId().substring(navResource.getId().indexOf("$"), 
													navResource.getId().lastIndexOf("$")).replace("$", "/");
					fileName = parentFolder + "/" + fileName;
				}
				if (record instanceof NavigatorResource) {
					DisplayModel model = ProjectExplorerUtil.getDisplayModelForResource((NavigatorResource)record);
					if (model != null && model.getDisplayText() != null) {
						fileName = model.getDisplayText();
					}
				}
				
				return formatFileName(fileName, fileExt);
			}
		}
		return value.toString();
	}

	private String formatFileName(String fileName, String fileExt) {
		String display = resourceTypeToDisplayName.get(fileExt.toLowerCase());
		/**
		 * latinCharactersWithArabicLocale should be removed, whenever BE supports multibyte character. 
		 */
		boolean latinCharactersWithArabicLocale = LocaleInfo.getCurrentLocale().isRTL() && fileName.matches("^[A-Za-z0-9_]+");
		StringBuilder builder = new StringBuilder();
		builder.append(fileName);
		if (latinCharactersWithArabicLocale) {
			builder.append("\u200F");
		}
		builder.append(" [");
		if (display != null) {
			builder.append(display);
		} else {
			builder.append(fileExt);
		}
		builder.append("]");
		if (latinCharactersWithArabicLocale) {
			builder.append("\u200F");
		}
		return builder.toString();
	}
}
