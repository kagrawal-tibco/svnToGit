/**
 * 
 */
package com.tibco.be.functions.domainModel;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;
import static com.tibco.be.model.functions.FunctionDomain.BUI;
import static com.tibco.be.model.functions.FunctionDomain.CONDITION;
import static com.tibco.be.model.functions.FunctionDomain.QUERY;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.studio.common.StudioProjectCache;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * Utility functions to get details around Domain Models
 * 
 * @author vpatil
 */
@com.tibco.be.model.functions.BEPackage(
		catalog = "Studio",
        category = "DomainModel",
        synopsis = "Utility functions to get details around Domain Models")
public class DomainModelHelper {
	
	 @com.tibco.be.model.functions.BEFunction(
        name = "getValues",
        signature = "String[] getValues(String uri, boolean checkParent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the Domain Model."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "checkParent", type = "boolean", desc = "Check for values in parent Domain if any.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String[]", desc = "An array containing all values associated to the Domain Model."),
        version = "5.2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an array of the values associated to the given Domain Model",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
	public static String[] getValues(String uri, boolean checkParent) {
		List<String> domainValues = new ArrayList<String>();
		
		Domain domainModel = getDomainModel(uri);				
		if (domainModel != null) {
			for (DomainEntry domainEntry : getDomainEntries(domainModel, checkParent)) {				
				domainValues.add(getDomainValue(domainModel.getDataType(), domainEntry));
			}
		} else {
			throw new IllegalArgumentException("Lookup for domain model[" + uri + "] values failed.");
		}
		return domainValues.toArray(new String[domainValues.size()]);
	}
	
	@com.tibco.be.model.functions.BEFunction(
	    name = "getDescription",
        signature = "String getDescription(String uri, Object value, boolean checkParent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the Domain Model."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "Domain Entry Value whose description needs to be retrieved."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "checkParent", type = "boolean", desc = "Check for values in parent Domain if any.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "String", desc = "Description of the domain entry match the given value."),
        version = "5.2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an domain entry description matching the given value",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
	public static String getDescription(String uri, Object value, boolean checkParent) {
		String domainDescription = null;
		
		String normalizedDomainValue = null;
		if (!(value instanceof Calendar)) {
			normalizedDomainValue = value.toString();
		} else {
			Calendar dateTime = (Calendar) value;
			normalizedDomainValue = ModelUtils.SIMPLE_DATE_FORMAT().format(dateTime.getTime());
		}
		
		Domain domainModel = getDomainModel(uri);		
		if (domainModel != null) {
			for (DomainEntry domainEntry : getDomainEntries(domainModel, checkParent)) {				
				if (getDomainValue(domainModel.getDataType(), domainEntry).equals(normalizedDomainValue)) {
					domainDescription = domainEntry.getDescription();
					break;
				}
			}
		} else {
			throw new IllegalArgumentException("Lookup for domain model[" + uri + "] description failed.");
		}
		return domainDescription;		
	}
	
	@com.tibco.be.model.functions.BEFunction(
	    name = "getModelMap",
        signature = "Object getModelMap(String uri, boolean checkParent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the Domain Model."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "checkParent", type = "boolean", desc = "Check for values in parent Domain if any.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "Map containing values-description pairs."),
        version = "5.2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns an Domain model map containing value-description pairs",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
	public static Object getModelMap(String uri, boolean checkParent) {
		Map<String, String> domainModelMap = new LinkedHashMap<String, String>();
	
		Domain domainModel = getDomainModel(uri);				
		if (domainModel != null) {
			for (DomainEntry domainEntry : getDomainEntries(domainModel, checkParent)) {				
				domainModelMap.put(getDomainValue(domainModel.getDataType(), domainEntry), domainEntry.getDescription());
			}
		} else {
			throw new IllegalArgumentException("Lookup for domain model[" + uri + "] map failed.");
		}
		
		return domainModelMap;
	}
	
	@com.tibco.be.model.functions.BEFunction(
	    name = "containsValue",
        signature = "boolean containsValue(String uri, Object value, boolean checkParent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uri", type = "String", desc = "URI of the Domain Model."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "value", type = "Object", desc = "Domain Entry Value to check for in the Domain Model."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "checkParent", type = "boolean", desc = "Check for values in parent Domain if any.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "boolean", desc = "Indicates if the value is part of the Domain Model."),
        version = "5.2.1",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "Returns if the value exists in the given Domain Model",
        cautions = "none",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
	public static boolean containsValue(String uri, Object value, boolean checkParent) {
		boolean bFound = false;
		
		String normalizedDomainValue = null;
		if (!(value instanceof Calendar)) {
			normalizedDomainValue = value.toString();
		} else {
			Calendar dateTime = (Calendar) value;
			normalizedDomainValue = ModelUtils.SIMPLE_DATE_FORMAT().format(dateTime.getTime());
		}
				
		Domain domainModel = getDomainModel(uri);				
		if (domainModel != null) {
			for (DomainEntry domainEntry : getDomainEntries(domainModel, checkParent)) {
				if (getDomainValue(domainModel.getDataType(), domainEntry).equals(normalizedDomainValue)) {
					bFound = true;
					break;
				}
			}
		} else {
			throw new IllegalArgumentException("Lookup for domain model[" + uri + "] map failed.");
		}
		
		return bFound;
	}
	
	/**
	 * Look up domain model from the Index
	 * 
	 * @param projectName
	 * @param uri
	 * @return
	 */
	private static Domain getDomainModel(String uri) {
		Domain domainModel = null;
		
		String projectName = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider().getProject().getName();
		
		DesignerProject index = StudioProjectCache.getInstance().getIndex(projectName);
        if (index != null) {        	
        	for (EntityElement element : index.getEntityElements()) {
        		if (element.getEntity() instanceof Domain && element.getEntity().getFullPath().equals(uri)) {
        			domainModel = (Domain) element.getEntity();
        			break;
        		}
        	}
        } else {
			throw new IllegalArgumentException("Project [" + projectName + "] index not found.");
		}
        
        return domainModel;
	}
	
	/**
	 * Fetch domain entry values based on the type
	 * 
	 * @param domainEntry
	 * @return
	 */
	private static String getDomainValue(DOMAIN_DATA_TYPES domainType, DomainEntry domainEntry) {
		String domainValue = null;
		if (domainEntry instanceof Range) {
			Range range = (Range) domainEntry;

			String uv = "Undefined".equalsIgnoreCase(range.getUpper()) ? "": normalizeValue(domainType, range.getUpper());
			String lv = "Undefined".equalsIgnoreCase(range.getLower()) ? "": normalizeValue(domainType, range.getLower());
			domainValue = (range.isLowerInclusive() ? "[" : "(")
					                              + lv
												  + ","
												  + uv
												  + (range.isUpperInclusive() ? "]" : ")");
		} else {
			domainValue = domainEntry.getValue();
		}
		
		return domainValue;
	}
	
	/**
	 * Normalizes the values of INTEGER, DOUBLE & LONG type domains to String
	 * @return Normalized value
	 */
	private static String normalizeValue(DOMAIN_DATA_TYPES domainType, String value) {
		if (value != null) {
			try {
				switch(domainType) {
				case INTEGER :				
					return Integer.valueOf(value).toString();
				case DOUBLE :
					return Double.valueOf(value).toString();
				case LONG :
					return Long.valueOf(value).toString();
				default :
					return value;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * Recursively get all domain Entries for parent domains 
	 * 
	 * @param domainModel
	 * @param checkParent
	 * @return
	 */
	private static List<DomainEntry> getDomainEntries(Domain domainModel, boolean checkParent) {
		List<DomainEntry> domainEntries = new ArrayList<DomainEntry>();
		
		if (domainModel.getSuperDomain() != null && checkParent) {
			domainEntries.addAll(getDomainEntries(domainModel.getSuperDomain(), checkParent));
		}
		if (domainModel.getEntries().size() > 0) {
			domainEntries.addAll(domainModel.getEntries());
		}
		
		return domainEntries;
	}

}
