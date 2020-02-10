package com.tibco.cep.webstudio.client.i18n;

import java.util.HashMap;
import java.util.List;

import com.tibco.cep.webstudio.client.WebStudio;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentData;
import com.tibco.cep.webstudio.client.decisiontable.model.ArgumentResource;
import com.tibco.cep.webstudio.client.model.ruletemplate.SymbolInfo;
import com.tibco.cep.webstudio.client.widgets.AbstractRelatedLink;
import com.tibco.cep.webstudio.model.rule.instance.DisplayModel;
import com.tibco.cep.webstudio.model.rule.instance.DisplayProperty;

public class DisplayUtils {
	
	public static boolean isHidden(AbstractRelatedLink contextLink, SymbolInfo field) {
		String entPath = contextLink.getModel().getLinkType();
		if (entPath.indexOf('.') != -1) {
			entPath = entPath.substring(0, entPath.lastIndexOf('.'));
		}
		DisplayModel dispModel = DisplayPropertiesManager.getInstance().getDisplayModel(WebStudio.get().getCurrentlySelectedProject(), entPath);
		if (dispModel != null) {
			return isHidden(dispModel, field.getAlias());
		}
		return false;
	}
	
	private static boolean isHidden(DisplayModel dispModel, String alias) {
		List<DisplayProperty> displayProperties = dispModel.getDisplayProperties();
		for (DisplayProperty displayProperty : displayProperties) {
			if (displayProperty.getId().equals(alias)) {
				return displayProperty.isHidden();
			}
		}
		return false;
	}

	public static String getLinkText(HashMap<String, String> displayProps, AbstractRelatedLink link) {
		String qName = getQualifiedName(link, null);
		if (displayProps.containsKey(qName)) {
			return displayProps.get(qName);
		}
		
		String entPath = null;
		if (link.getPreviousLink() != null) {
			entPath = link.getPreviousLink().getModel().getLinkType();
		} else {
			entPath = link.getModel().getLinkType();
		}
		return getLinkText(link, entPath);
	}

	public static String getLinkText(AbstractRelatedLink link, String entityPath) {
		String displayName = link.getModel().getLinkText();

		if (entityPath != null && entityPath.indexOf('.') != -1) {
			entityPath = entityPath.substring(0, entityPath.lastIndexOf('.'));
		}
		DisplayModel dispModel = DisplayPropertiesManager.getInstance().getDisplayModel(WebStudio.get().getCurrentlySelectedProject(), entityPath);
		if (dispModel != null) {
			if (link.getPreviousLink() == null && dispModel.getDisplayText() != null && entityPath.equals(dispModel.getEntity())) {
				return dispModel.getDisplayText();
			}
			return translateProperty(dispModel, displayName);
		}
		return displayName;
	}
		
	/**
	 * Display the attributes in a different manner
	 * 
	 * @param symbolName
	 * @return
	 */
	public static String getDisplayText(HashMap<String, String> displayProps, SymbolInfo field, AbstractRelatedLink contextLink, boolean useFieldType) {
		String symbolName = field.getAlias();
		if (symbolName.equals("id") || symbolName.equals("extId")
				|| symbolName.equals("length") || symbolName.equals("payload") || symbolName.equals("ttl")) {
			return "<i>" + symbolName + "</i>";
		} else {
			// Check if the display properties are overridden in the RTI
			String qName = getQualifiedName(contextLink, field.getAlias());
			if (displayProps.containsKey(qName)) {
				return displayProps.get(qName);
			}
			
			// Check if the display Properties are available at the project level.
			String entPath = null;
			if (useFieldType) {
				entPath = field.getType();
			} else {
				entPath = contextLink.getModel().getLinkType();
			}
			return getSymbolText(field, contextLink, entPath, useFieldType);
		}
	}

	public static String getSymbolText(SymbolInfo field,
			AbstractRelatedLink contextLink, String entPath, boolean checkFieldType) {
		if (entPath.indexOf('.') != -1) {
			entPath = entPath.substring(0, entPath.lastIndexOf('.'));
		}
		DisplayModel dispModel = DisplayPropertiesManager.getInstance().getDisplayModel(WebStudio.get().getCurrentlySelectedProject(), entPath);
		if (dispModel != null) {
			if (contextLink.getPreviousLink() == null && dispModel.getDisplayText() != null && (checkFieldType && field.getType().indexOf(dispModel.getEntity()) != -1)) {
				return dispModel.getDisplayText();
			}
			return translateProperty(dispModel, field.getAlias());
		}
		
		// finally if nothing matches return the alias of the original property
		return field.getAlias();
	}
	
	private static String translateProperty(DisplayModel dispModel, String alias) {
		List<DisplayProperty> displayProperties = dispModel.getDisplayProperties();
		for (DisplayProperty displayProperty : displayProperties) {
			if (displayProperty.getId().equals(alias)) {
				return displayProperty.getValue() != null ? displayProperty.getValue() : alias;
			}
		}
		return alias;
	}

	public static String getQualifiedName(AbstractRelatedLink link, String suffix) {
		StringBuilder b = new StringBuilder();
		
		AbstractRelatedLink prevLink = null;
		if (link != null) {
			b.append(link.getModel().getLinkText());
			prevLink = (AbstractRelatedLink) link.getPreviousLink();
		}
		
		while (prevLink != null) {
			b.insert(0, '.');
			b.insert(0, prevLink.getModel().getLinkText());
			prevLink = (AbstractRelatedLink) prevLink.getPreviousLink();
		}
		if (suffix != null) {
			if (b.length() > 0)	b.append('.');
			b.append(suffix);
		}
		return b.toString();
	}
	
	public static String getDisplayText(String projectName, String parentPath, String artifactName) {
		DisplayModel dispModel = DisplayPropertiesManager.getInstance().getDisplayModel(projectName, parentPath);
		
		if (dispModel != null) {
			if (artifactName != null && !artifactName.isEmpty()) {
				return translateProperty(dispModel, artifactName);
			} else {
				String text = dispModel.getDisplayText();
				if (text == null || text.trim().length() == 0) {
					return dispModel.getEntity();
				}
				return text;
			}
		} else {
			return (artifactName != null) ? artifactName : parentPath.substring(parentPath.lastIndexOf("/")+1);
		}
	}


	public static boolean isHidden(String projectName, String parentPath, String artifactName) {
		DisplayModel dispModel = DisplayPropertiesManager.getInstance().getDisplayModel(projectName, parentPath);
		if (dispModel != null) {
			return isHidden(dispModel, artifactName);
		} else {
			return false;
		}
	}
	
	public static String getDTColumnDisplayText(String propertyPath, String nameAlias, List<ArgumentData> arguments) {
		if (propertyPath == null || propertyPath.isEmpty()) {
			return nameAlias;
		}
		String projectName = WebStudio.get().getCurrentlySelectedProject();
		String separator = (nameAlias.indexOf("$$") != -1) ? "$$" : "$";
		
		StringBuffer translatedAlias = new StringBuffer();
		String[] nameAliases = nameAlias.split("\\$");
		if (nameAliases != null && nameAliases.length > 0) {
			// get the matching type from the arguments
			String baseAliasType = checkAndGetArgumentType(arguments, nameAliases[0]);
			if (baseAliasType == null) {
				// TODO - something really went wrong here
			}
			
			// add the root Alias to the translatedAlias
			translatedAlias.append(nameAliases[0]);
			translatedAlias.append(separator);
			
			// look for matching display model for the next token
			DisplayModel aliasDispModel = DisplayPropertiesManager.getInstance().getDisplayModel(projectName, baseAliasType);
			for (int i=1; i<nameAliases.length-1; i++) {
				if (nameAliases[i] != null && !nameAliases[i].isEmpty()) {
					if (aliasDispModel != null) {
						translatedAlias.append(translateProperty(aliasDispModel, nameAliases[i]));
					} else {
						translatedAlias.append(nameAliases[i]);
					}
					translatedAlias.append(separator);
				}
			}
			
			// parse the alias tail			
			String baseArtifactPath = (propertyPath.indexOf("/") != -1) ? propertyPath.substring(0, propertyPath.lastIndexOf("/")) : propertyPath; 
			String propertyName = nameAlias.substring(nameAlias.lastIndexOf("$") + 1);

			aliasDispModel = (baseAliasType.equals(baseArtifactPath)) ? aliasDispModel : DisplayPropertiesManager.getInstance().getDisplayModel(projectName, baseArtifactPath);
			if (aliasDispModel != null) {
				translatedAlias.append(translateProperty(aliasDispModel, propertyName));
			} else {
				translatedAlias.append(propertyName);
			}
		}
		
		return translatedAlias.toString();
		
	}
	
	private static String checkAndGetArgumentType(List<ArgumentData> arguments, String alias) {
		for (ArgumentData argument : arguments) {
			if (argument.getAlias().equals(alias)) return argument.getPropertyPath();
		}
		
		return null;
	}
	
	public static boolean parentDisplayModelExists(ArgumentResource argResource) {		
		String projectName = WebStudio.get().getCurrentlySelectedProject();
		
		String entityPath = argResource.getOwnerPath();
		DisplayModel dispModel = DisplayPropertiesManager.getInstance().getDisplayModel(projectName, entityPath);
		if (dispModel != null) return false;
		
		while (argResource.getParent() != null) {
			entityPath = argResource.getParent().getPath() + argResource.getParent().getName();
			dispModel = DisplayPropertiesManager.getInstance().getDisplayModel(projectName, entityPath);
			if (dispModel != null) {
				return true;
			}
			argResource = argResource.getParent();
		}
		
		return false;
	}
}
