/**
 * 
 */
package com.tibco.cep.studio.tester.utilities;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.widgets.Table;

import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

/**
 * @author mgujrath
 *
 */
public class PropertyTableConstants {

	
	static final String NAME = "Name";
	static final String TYPE = "Type";
	static final String DOMAIN = "Domain";
	static final String MULTIPLE = "Multiple";
	static final String POLICY = "Policy";
	static final String HISTORY = "History";
	static final String booleanItems[]={"true","false"};
	static final String NEW_PROPERTY_NAME="Property_Name";
	public static final String ICON_STRING = "/icons/iconString16.gif";
	static final String ICON_STRING_MULTIPLE = "/icons/iconStringM16.gif";
	public static final String ICON_INTEGER ="/icons/iconInteger16.gif";
	static final String ICON_INTEGER_MULTIPLE ="/icons/iconIntegerM16.gif";
	public static final String ICON_BOOLEAN ="/icons/iconBoolean16.gif";
	static final String ICON_BOOLEAN_MULTIPLE ="/icons/iconBooleanM16.gif";
	static final String ICON_PAYLOAD="/icons/payload.gif";
	static final String ICON_USE="/icons/ok_10x10.png";
	public static final String ICON_EXTID="/icons/icon@extid.gif";
	public static final String ICON_LONG ="/icons/iconLong16.gif";
	static final String ICON_LONG_MULTIPLE ="/icons/iconLongM16.gif";
	public static final String ICON_DOUBLE ="/icons/iconReal16.gif";
	static final String ICON_DOUBLE_MULTIPLE ="/icons/iconRealM16.gif";
	public static final String ICON_DATE ="/icons/iconDate16.gif";
	static final String ICON_DATE_MULTIPLE ="/icons/iconDateM16.gif";
	public static final String ICON_CONCEPT="/icons/iconConcept16.gif";
	static final String ICON_CONCEPT_MULTIPLE="/icons/iconConceptM16.gif";
	public static final String ICON_CONCEPTREFERENCE="/icons/iconConceptRef16.gif";
	static final String ICON_CONCEPTREFERENCE_MULTIPLE="/icons/iconConceptRefM16.gif";
	static final String ICON_TOOLBAR_CHECK	= "/icons/checked.png";
	static final String ICON_TOOLBAR_UNCHECK="/icons/unchecked.png";
	static final String ICON_HASHID="/icons/#.gif";
	

	
	public static String getPropertyName(String entityName, Table model) {
        String name = entityName + "_property_";
        List<Integer> noList = new ArrayList<Integer>();
        for (int row = 0; row < model.getItemCount(); row++) {
               PropertyDefinition pr =(PropertyDefinition) model.getItem(row).getData();
               if (pr.getName().startsWith(name)) {
                     String subname =pr.getName().substring(entityName.length() + 10);
//                     if (EditorUtils.isNumeric(subname)) {
//                            noList.add(Integer.parseInt(subname));
//                     }
               }
        }
        try {
               if (noList.size() > 0) {
                     java.util.Arrays.sort(noList.toArray());
                     int no = noList.get(noList.size() - 1).intValue();
                     no++;
                     return name + no;
               }
        } catch (Exception e) {
               e.printStackTrace();
        }

        return name + "0";

 }
	
	public static String validate(String type){
		if("String".equalsIgnoreCase(type)|| "long".equalsIgnoreCase(type)|| "double".equalsIgnoreCase(type)|| "Int".equalsIgnoreCase(type)|| "boolean".equalsIgnoreCase(type)){
			return type;
		}
			
		return null;
		
	}

}
