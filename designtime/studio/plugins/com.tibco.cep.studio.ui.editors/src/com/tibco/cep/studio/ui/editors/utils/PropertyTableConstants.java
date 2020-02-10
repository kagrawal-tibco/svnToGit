/**
 * 
 */
package com.tibco.cep.studio.ui.editors.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Table;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * @author mgujrath
 *
 */
public class PropertyTableConstants {
		public static final String NAME = "Name";
		public static final String TYPE = "Type";
		public static final String DOMAIN = "Domain";
		public static final String MULTIPLE = "Multiple";
		public static final String POLICY = "Policy";
		public static final String HISTORY = "History";
		public static final String policyItems[]={"Changes Only","All  Values"};
		public static final String typeItems[]={"String","int","long","double","boolean","DateTime"};
		public static final String NEW_PROPERTY_NAME="Property_Name";
		public static final String ICON_TOOLBAR_STRING = "/icons/iconString16.gif";
		public static final String ICON_TOOLBAR_INTEGER ="/icons/iconInteger16.gif";
		public static final String ICON_TOOLBAR_BOOLEAN ="/icons/iconBoolean16.gif";
		public static final String ICON_TOOLBAR_LONG ="/icons/iconLong16.gif";
		public static final String ICON_TOOLBAR_DOUBLE ="/icons/iconReal16.gif";
		public static final String ICON_TOOLBAR_DATE ="/icons/iconDate16.gif";
		public static final String ICON_TOOLBAR_CONCEPT="/icons/iconConcept16.gif";
		public static final String ICON_TOOLBAR_CONCEPTREFERENCE="/icons/iconConceptRef16.gif";
		public static final String ICON_TOOLBAR_CHECK	= "/icons/checked.png";
		public static final String ICON_TOOLBAR_UNCHECK="/icons/unchecked.png";

	
		public static String getPropertyName(String entityName, Table model) {
			String propertyName = entityName + "_property_";
			List<Integer> noList = new ArrayList<Integer>();
			for (int row = 0; row < model.getItemCount(); row++) {
				PropertyDefinition pr =(PropertyDefinition) model.getItem(row).getData();
				if (pr.getName().startsWith(propertyName)) {
                     String subname =pr.getName().substring(entityName.length() + 10);
                     if (StudioUIUtils.isNumeric(subname)) {
                            noList.add(Integer.parseInt(subname));
                     }
				}
			}
			try {
				if (noList.size() > 0) {
                     java.util.Arrays.sort(noList.toArray());
                     int no = noList.get(noList.size() - 1).intValue();
                     no++;
                     return propertyName + no;
               }
			} catch (Exception e) {
				e.printStackTrace();
			}

        return propertyName + "0";
		}
		
		public static String validate(String type){
			if("String".equalsIgnoreCase(type)|| "long".equalsIgnoreCase(type)|| "double".equalsIgnoreCase(type)|| "Int".equalsIgnoreCase(type)|| "boolean".equalsIgnoreCase(type) || "datetime".equalsIgnoreCase(type)){
				return type;
			}
				
			return null;
			
		}
}
