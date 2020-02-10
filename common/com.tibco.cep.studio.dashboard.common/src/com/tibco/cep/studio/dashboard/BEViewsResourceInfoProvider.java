package com.tibco.cep.studio.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import com.tibco.cep.addon.IExternalResourceInfoProvider;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;

public class BEViewsResourceInfoProvider implements IExternalResourceInfoProvider {
	
	private Map<String,EPackage> registry;
	
	private Map<String, ELEMENT_TYPES> fileExtToElementType;
	
	private Map<ELEMENT_TYPES,String> elementTypeToFileExt;
	
	public BEViewsResourceInfoProvider(){
		registry = new HashMap<String, EPackage>();
		registry.put("http://www.tibco.com/be/designer",BEViewsConfigurationPackage.eINSTANCE);

		fileExtToElementType = new HashMap<String, ELEMENT_TYPES>();
		fileExtToElementType.put("chart",ELEMENT_TYPES.CHART_COMPONENT);
		fileExtToElementType.put("smcomponent",ELEMENT_TYPES.STATE_MACHINE_COMPONENT);
		fileExtToElementType.put("pageselector",ELEMENT_TYPES.PAGE_SELECTOR_COMPONENT);
		fileExtToElementType.put("alert",ELEMENT_TYPES.ALERT_COMPONENT);
		fileExtToElementType.put("contextactionruleset",ELEMENT_TYPES.CONTEXT_ACTION_RULE_SET);
		fileExtToElementType.put("blueprint",ELEMENT_TYPES.BLUE_PRINT_COMPONENT);
		fileExtToElementType.put("querymanager",ELEMENT_TYPES.QUERY_MANAGER_COMPONENT);
		fileExtToElementType.put("searchview",ELEMENT_TYPES.SEARCH_VIEW_COMPONENT);
		fileExtToElementType.put("relatedassets",ELEMENT_TYPES.RELATED_ASSETS_COMPONENT);
		fileExtToElementType.put("drilldown",ELEMENT_TYPES.DRILLDOWN_COMPONENT);
		fileExtToElementType.put("query",ELEMENT_TYPES.QUERY);
		fileExtToElementType.put("view",ELEMENT_TYPES.VIEW);
		fileExtToElementType.put("dashboardpage",ELEMENT_TYPES.DASHBOARD_PAGE);
		fileExtToElementType.put("assetpage",ELEMENT_TYPES.ASSET_PAGE);
		fileExtToElementType.put("searchpage",ELEMENT_TYPES.SEARCH_PAGE);
		fileExtToElementType.put("pageset",ELEMENT_TYPES.PAGE_SET);
		fileExtToElementType.put("seriescolor",ELEMENT_TYPES.SERIES_COLOR);
		fileExtToElementType.put("textcolorset",ELEMENT_TYPES.TEXT_COMPONENT_COLOR_SET);
		fileExtToElementType.put("chartcolorset",ELEMENT_TYPES.CHART_COMPONENT_COLOR_SET);
		fileExtToElementType.put("header",ELEMENT_TYPES.HEADER);
		fileExtToElementType.put("login",ELEMENT_TYPES.LOGIN);
		fileExtToElementType.put("skin",ELEMENT_TYPES.SKIN);
		fileExtToElementType.put("rolepreference",ELEMENT_TYPES.ROLE_PREFERENCE);
		fileExtToElementType.put("datasource",ELEMENT_TYPES.DATA_SOURCE);

		elementTypeToFileExt = new HashMap<ELEMENT_TYPES, String>();
		for (Map.Entry<String, ELEMENT_TYPES> entry : fileExtToElementType.entrySet()) {
			elementTypeToFileExt.put(entry.getValue(), entry.getKey());
		}
		
	}
	
	@Override
	public Map<String,EPackage> getPackageRegistry() {
		return registry;
	}	

//	@Override
//	public boolean canHandle(String fileExtension) {
//		return fileExtToElementType.containsKey(fileExtension);
//	}
//	
//	@Override
//	public ELEMENT_TYPES getType(String fileExtension) {
//		return fileExtToElementType.get(fileExtension);
//	}
//
//	@Override
//	public String getExtension(EObject eObject) {
//		ELEMENT_TYPES type = getType(eObject);
//		if (type != null){
//			return elementTypeToFileExt.get(type);
//		}
//		return null;
//	}
//
//	@Override
//	public ELEMENT_TYPES getType(EObject eObject) {
//		return ELEMENT_TYPES.getByName(eObject.eClass().getName());
//	}

}