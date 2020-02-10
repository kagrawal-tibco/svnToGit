/**
 * 
 */
package com.tibco.cep.webstudio.client.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.shared.EventHandler;
import com.smartgwt.client.types.Autofit;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.menu.Menu;
import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.editor.RuleTemplateInstanceEditorFactory;
import com.tibco.cep.webstudio.client.http.HttpFailureEvent;
import com.tibco.cep.webstudio.client.http.HttpFailureHandler;
import com.tibco.cep.webstudio.client.http.HttpSuccessEvent;
import com.tibco.cep.webstudio.client.http.HttpSuccessHandler;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.i18n.RTIMessages;
import com.tibco.cep.webstudio.client.model.NavigatorResource;
import com.tibco.cep.webstudio.client.model.RequestParameter;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.client.model.ruletemplate.DomainInfo;
import com.tibco.cep.webstudio.client.util.ProjectExplorerUtil.ARTIFACT_TYPES;
import com.tibco.cep.webstudio.model.rule.instance.RelatedLink;

/**
 * Common utility methods needed across various artifacts.
 * 
 * @author Vikram Patil
 */
public class ArtifactUtil {
	
	private static boolean isDMInstalled;
	private static boolean isBPMNInstalled; 

	private static boolean isLockingEnabled = false;
	
	public static ErrorMessageDialog errorDialog;
	
	protected static GlobalMessages globalMsg = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	protected static RTIMessages rtiMsgBundle = (RTIMessages)I18nRegistry.getResourceBundle(I18nRegistry.RTI_MESSAGES);

	/**
	 * Checks for primitive vs object
	 * 
	 * @param artifactType
	 * @return
	 */
	public static boolean isPrimitive(String artifactType) {
		if (artifactType.toLowerCase().indexOf("concept") != -1 || artifactType.toLowerCase().indexOf("event") != -1 || artifactType.toLowerCase().indexOf("/") != -1) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Retrieves symbols for a specific type of concept with matching alias
	 * 
	 * @param type
	 * @param alias
	 * @param symbols
	 * @return
	 */
	public static List<SymbolInfo> getSymbols(String type, String alias, List<SymbolInfo> symbols) {
		return getSymbols(type, alias, symbols, true);
	}
	
	/**
	 * Retrieves symbols for a specific type of concept with matching alias
	 * 
	 * @param type
	 * @param alias
	 * @param symbols
	 * @param checkAlias
	 * @return
	 */

	public static List<SymbolInfo> getSymbols(String type, String alias, List<SymbolInfo> symbols, boolean checkAlias) {
		List<SymbolInfo> containedSymbols = null;
		for (SymbolInfo symbol : symbols) {
			if (symbol.getType().equals(type) && (!checkAlias || (checkAlias && symbol.getAlias().equals(alias)))) {
				containedSymbols = symbol.getContainedSymbols();
			} else if (symbol.getContainedSymbols() != null && symbol.getContainedSymbols().size() > 0) {
				containedSymbols = getSymbols(type, alias, symbol.getContainedSymbols());
			}

			if (containedSymbols != null && containedSymbols.size() > 0)
				return containedSymbols;
		}
		return null;
	}
	
	/**
	 * Retieves command symbols specific to a type of entity
	 * 
	 * @param type
	 * @param alias
	 * @param cmdSymbols
	 * @return
	 */
	public static List<SymbolInfo> getCommandSymbols(String type, String alias, Map<String, List<SymbolInfo>> cmdSymbols) {
		List<SymbolInfo> containedSymbols = null;

		for (String cmd : cmdSymbols.keySet()) {
			if (cmd.equals(alias)) return cmdSymbols.get(cmd);
			
			containedSymbols = getSymbols(type, alias, cmdSymbols.get(cmd));

			if (containedSymbols != null && containedSymbols.size() > 0) {
				return containedSymbols;
			}
		}
		return null;
	}
	
	/**
	 * Filter Symbols to remove attributes
	 * 
	 * @param type
	 * @param alias
	 * @param symbols
	 * @return
	 */
	public static List<SymbolInfo> filterSymbolsToRemoveAttributes(String type, String alias, String actionType, List<SymbolInfo> symbols) {
		List<SymbolInfo> symbolList = ArtifactUtil.getSymbols(type, alias, symbols, false);
		
		List<SymbolInfo> filteredSymbols = new ArrayList<SymbolInfo>();
		filterOutArtifacts(symbolList, actionType, filteredSymbols);
		
		return filteredSymbols;
	}
	
	/**
	 * Filter Symbols
	 * 
	 * @param symbols
	 * @param filteredSymbols
	 */
	private static void filterOutArtifacts(List<SymbolInfo> symbols, String actionType, List<SymbolInfo> filteredSymbols) {
		if (symbols != null) {
			for (SymbolInfo symbol : symbols) {
				if (!symbol.getAlias().equalsIgnoreCase("id") && !symbol.getAlias().equals("length") && (!RuleTemplateInstanceEditorFactory.COMMAND_ACTIONTYPE_MODIFY.equalsIgnoreCase(actionType) || (RuleTemplateInstanceEditorFactory.COMMAND_ACTIONTYPE_MODIFY.equalsIgnoreCase(actionType) && !symbol.getAlias().equalsIgnoreCase("extId")))) {
					filteredSymbols.add(symbol);
				} else if (symbol.getContainedSymbols() != null && symbol.getContainedSymbols().size() > 0) {
					filteredSymbols.add(symbol);
					filterOutArtifacts(symbols, actionType, filteredSymbols);
				}
			}
		}
	}

	/**
	 * Get the appropriate type
	 * 
	 * @param type
	 * @return
	 */
	public static String getClientType(String type) {
		if (type.equalsIgnoreCase("string")) {
			type = "text";
		} else if (type.equalsIgnoreCase("int") || type.equalsIgnoreCase("long")) {
			type = "integer";
		} else if (type.equalsIgnoreCase("float") || type.equalsIgnoreCase("double")) {
			type = "float";
		} else if (type.equalsIgnoreCase("datetime")){
			type = "datetime";
		}
		return type;
	}

	/**
	 * Get the appropriate server type
	 * 
	 * @param type
	 * @return
	 */
	public static String getServerType(String type) {
		if (type.equalsIgnoreCase("text")) {
			type = "String";
		} else if (type.equalsIgnoreCase("integer") || type.equalsIgnoreCase("long")) {
			type = "int";
		} else if (type.equalsIgnoreCase("float") || type.equalsIgnoreCase("double")) {
			type = "double";
		}
		return type;
	}

	/**
	 * Added necessary Http Handlers
	 * 
	 * @param handler
	 */
	public static void addHandlers(EventHandler handler) {
		WebStudio.get().getEventBus().addHandler(HttpSuccessEvent.TYPE, (HttpSuccessHandler) handler);
		WebStudio.get().getEventBus().addHandler(HttpFailureEvent.TYPE, (HttpFailureHandler) handler);
	}

	/**
	 * Removes Http Handlers
	 * 
	 * @param handler
	 */
	public static void removeHandlers(EventHandler handler) {
		WebStudio.get().getEventBus().removeHandler(HttpSuccessEvent.TYPE, (HttpSuccessHandler) handler);
		WebStudio.get().getEventBus().removeHandler(HttpFailureEvent.TYPE, (HttpFailureHandler) handler);
	}
	
	/**
	 * Conditionally add scrolling only if the menu contains more than 10 items
	 * 
	 * @param items
	 * @param menu
	 */
	public static void setMenuScroll(int items, Menu menu) {
		if (items > 20) {
			menu.setHeight(200);
			menu.setBodyOverflow(Overflow.AUTO);
			menu.setAutoFitData(Autofit.HORIZONTAL);
		}
	}
	
	/**
	 * Creates a GET url for exporting a Rule template instance/Decision Table
	 * 
	 * @param selectedResource
	 * @return
	 */
	public static String getArtifactURL(NavigatorResource selectedResource) {
		String projectName = selectedResource.getId().substring(0,
				selectedResource.getId().indexOf("$"));
		if (projectName == null) {
			return null;
		}
		String path = selectedResource
				.getId()
				.substring(selectedResource.getId().indexOf("$"),
						selectedResource.getId().length()).replace("$", "/");
		String ruleTemplatePath = path.substring(path.indexOf("/"),
				path.lastIndexOf('.'));
		String type = path.substring(path.lastIndexOf('.') + 1, path.length());

		StringBuilder url = new StringBuilder();
		url.append(ServerEndpoints.RMS_GET_ARTIFACT_EXPORT.getURL() + "?");
		url.append(RequestParameter.REQUEST_PROJECT_NAME + "=" + projectName);
		url.append("&" + RequestParameter.REQUEST_PARAM_PATH + "="
				+ ruleTemplatePath);
		url.append("&" + RequestParameter.REQUEST_PARAM_TYPE + "="
				+ ARTIFACT_TYPES.valueOf(type.toUpperCase()).getValue());
		url.append("&" + RequestParameter.REQUEST_PARAM_FILE_EXTN + "="
				+ type);

		return url.toString();
	}
	
	/**
	 * Check if the artifact selected is one of the supported types. Currently
	 * only Rule Template Instance and Decision Table are supported
	 * 
	 * @param record
	 * @return
	 */
	public static boolean isSupportedArtifact(NavigatorResource record) {
		if (record != null) {
			return (record.getType() != null && (record.getType().equals(ARTIFACT_TYPES.RULETEMPLATEINSTANCE.getValue().toLowerCase()) 
					                            || record.getType().equals(ARTIFACT_TYPES.RULEFUNCTIONIMPL.getValue().toLowerCase())
					                            || record.getType().equals(ARTIFACT_TYPES.PROCESS.getValue().toLowerCase())
					                            || record.getType().equals(ARTIFACT_TYPES.DOMAIN.getValue().toLowerCase())));
		} else {
			return false;
		}
	}


	/**
	 * Check if Decision Manager is installed
	 * 
	 * @param showMessage
	 * @return
	 */
	public static boolean isDecisionManagerInstalled(boolean showMessage) {
		if (!isDMInstalled) {
			if (showMessage) {
				SC.clearPrompt();
				if (errorDialog != null && errorDialog.isVisible()) {
					errorDialog.destroy();
					errorDialog = null;
				}
				if (errorDialog == null) {
					errorDialog = ErrorMessageDialog.showError(globalMsg.decisionManagerInstall_error());	
				}
			}
		}
		return isDMInstalled;
	}

	/**
	 * Set DM Installation status.
	 * 
	 * @param isDMInstalled
	 */
	public static void setDecisionManagerInstalled(boolean isDMInstalled) {
		ArtifactUtil.isDMInstalled = isDMInstalled;
	}
	
	/**
	 * Check if BPMN is installed
	 * 
	 * @param showMessage
	 * @return
	 */
	public static boolean isBPMNInstalled(boolean showMessage) {
		if (!isBPMNInstalled) {
			if (showMessage) {
				SC.clearPrompt();
				if (errorDialog != null && errorDialog.isVisible()) {
					errorDialog.destroy();
					errorDialog = null;
				}
				if (errorDialog == null) {
					errorDialog = ErrorMessageDialog.showError(globalMsg.bpmnaddonInstall_error());	
				}
			}
		}
		return isBPMNInstalled;
	}

	/**
	 * Set BPMN Installation status.
	 * 
	 * @param isBPMNInstalled
	 */
	public static void setBPMNInstalled(boolean isBPMNInstalled) {
		ArtifactUtil.isBPMNInstalled = isBPMNInstalled;
	}
	
	public static boolean isLockingEnabled() {
		return isLockingEnabled;
	}

	public static void setLockingEnabled(boolean isLockingEnabled) {
		ArtifactUtil.isLockingEnabled = isLockingEnabled;
	}

	/**
	 * Get the appropriate Icon based on the entity type
	 * 
	 * @param entityType
	 * @return
	 */
	public static String getEntityIcon(String entityType) {
		String type = (entityType.indexOf(".") != -1) ? entityType.substring(entityType.indexOf(".")+1) : entityType;
		
		String entityIcon = (entityType.indexOf(".") != -1) ? ProjectExplorerUtil.getIcon(type) : getPropertiesIcon(type);
		if (entityIcon.indexOf("file.png") != -1) {
			entityIcon = "";
		}
		return entityIcon;
	}
	
	/**
	 * Get property type icons
	 * @param type
	 */
	private static String getPropertiesIcon(String type) {
		String icon = ProjectExplorerUtil.ICON_PATH;
		if (type.equals("int")) {
			icon += "iconInteger16.gif";
		} else if (type.equals("double")) {
			icon += "iconReal16.gif";
		} else if (type.equals("DateTime")) {
			icon += "iconDate16.gif";
		} else if (type.equals("boolean")) {
			icon += "iconBoolean16.gif";
		} else if (type.equals("String")) {
			icon += "iconString16.gif";
		} else if (type.equals("long")) {
			icon += "iconLong16.gif";
		}

		return icon;
	}
	
	/**
	 * Checks whether the artifact name is valid or not, valid characters are word letters from all supported languages, numbers and underscore.
	 * @param name
	 * @return
	 */
	public static boolean isValidArtifactName(String name) {
		if (name == null || (name = name.trim()).isEmpty() || name.contains(" ")
				|| name.matches("^[0-9].*$")) {//if the name starts with a number return false.
			return false;
		}
		if (name.matches("^[\\w\\u00A2-\\u00A5\\u00AA\\u00B5\\u00BA\\u00C0-\\u00D6\\u00D8-\\u00F6\\u00F8-\\u00FF\\u0100-\\u017F\\u0180-\\u024F\\u0250-\\u02AF\\u1D00-\\u1D7F\\u1E00-\\u1EFF\\u2C60-\\u2C7F\\u3005-\\u3007\\u3021-\\u3029\\u3031-\\u3035\\u3400-\\u4DB5\\u4E00-\\u9FCB\\uF900-\\uFA2D\\uFA30-\\uFA6D\\uFA70-\\uFAD9\\u1100-\\u11FF\\u3131-\\u318E\\uAC00-\\uD7A3\\u3041-\\u3096\\u309D-\\u309F\\u30A1-\\u30FA\\u30FC-\\u30FF\\u31F0-\\u31FF\\u0621-\\u064A\\u0660-\\u0669]{1,64}$")) {//Allow letters, numbers, underscores totalling up to length 64
			return true;
		}
		return false;
	}
	
	/**
	 * Check all symbols for matching type and return associated domain model
	 * 
	 * @param links
	 * @param symbols
	 * @return
	 */
	public static DomainInfo getAssociatedDomainInfo(List<RelatedLink> links, List<SymbolInfo> symbols) {
		for (RelatedLink link : links) {
			if (!isPrimitive(link.getLinkType())) {
				List<SymbolInfo> linkSymbols = getSymbols(link.getLinkType(), link.getLinkText(), symbols);
				symbols = linkSymbols;
				continue;
			} else {
				for (SymbolInfo symbol : symbols) {
					if (symbol.getAlias().equals(link.getLinkText()) && symbol.getType().equals(link.getLinkType()) && symbol.getDomainInfo() != null) {
						return symbol.getDomainInfo();
					}
				}
			}
		}
		return null;
	}
}
