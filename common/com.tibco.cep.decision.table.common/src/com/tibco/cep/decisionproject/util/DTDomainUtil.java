/**
 * 
 */
package com.tibco.cep.decisionproject.util;


import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.domain.Single;
import com.tibco.cep.designtime.core.model.domain.impl.RangeImpl;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * @author aathalye
 *
 */
public class DTDomainUtil {
	
	public static final boolean IS_STRICT = false;
	public static final String [] RANGE_OPERATORS = {">=" , "<=" , ">" , "<"};
	
	public static final String SEPARATOR = " || ";//$NON-NLS-N$
	public static final String EMPTY = "<NA>";//$NON-NLS-N$
	public static final String VALUE = "VALUE";//$NON-NLS-N$
	public static final String DESC = "DESCRIPTION";//$NON-NLS-N$
	public static final String ASTERISK = "*";
	
	/**
	 * Get the domain values selected in the cell
	 * @param domainInstances
	 * @param projectName
	 * @param trv
	 * @return
	 */
	public static final List<?> getSelectedDomainEntries (List<DomainInstance> domainInstances, 
										                       String projectName,
										                       TableRuleVariable tableRuleVariable) {
		
		//Fill the Domain Entries
		return getSelectedDomainEntries(getDomainEntries(domainInstances, projectName), tableRuleVariable);
	}
	
	@SuppressWarnings("unchecked")
	public static List<DomainEntry> getDomainMatches(Map<String, DomainEntry> domainEntries, TableRuleVariable tableRuleVariable) {
		return (List<DomainEntry>)convertTRV(domainEntries, tableRuleVariable, false);
	}
	
	public static List<?> getSelectedDomainEntries(Map<String, DomainEntry> domainEntries, TableRuleVariable tableRuleVariable) {
		return convertTRV(domainEntries, tableRuleVariable, false);
	}
	
	
	private static final List<?> convertTRV(Map<String, DomainEntry> domainEntries, TableRuleVariable tableRuleVariable, boolean keepUnmatched) {
		List<Object> retVal = new ArrayList<Object>();
		String expr = tableRuleVariable.getExpr().trim();
		
		//Filter the values in trv to get selected ones
		StringTokenizer st = new StringTokenizer(expr, SEPARATOR.trim());
		while (st.hasMoreTokens()) {
			String token = st.nextToken().trim();
//			if (token.startsWith("==")) {
//				token = token.substring(2).trim();
//			}
			if (domainEntries.containsKey(token)) {
				retVal.add(domainEntries.get(token));
			} else if (keepUnmatched) {
				retVal.add(token);
			}
		}
		return retVal;
	}
	
	public static final Map<String, DomainEntry> getDomainEntries(String projectName, Column col) {
		return getDomainEntries(getDomains(col.getPropertyPath(), projectName), projectName);
	}
	
	public static final Map<String, DomainEntry> getDomainEntries(List<DomainInstance> domainInstances, String projectName) {
		Map<String, DomainEntry> domainEntries = new HashMap<String, DomainEntry>();
		//Dont care entry
		Single dontCare = DomainFactory.eINSTANCE.createSingle();
		dontCare.setValue(ASTERISK);
		domainEntries.put(ASTERISK, dontCare);
		if (domainInstances != null && domainInstances.size() > 0) {
			for (DomainInstance domainInstance : domainInstances) {
				String domainPath = domainInstance.getResourcePath();
				Domain domain = CommonIndexUtils.getDomain(projectName, domainPath);
				Domain superDomain = domain;
				
				while (superDomain != null) {
					List<DomainEntry> entries = superDomain.getEntries();
					for (DomainEntry entry : entries) {
						if (entry instanceof RangeImpl) {
							String strRange = getStringValueFromRangeInfo((Range)entry, domain);
							domainEntries.put(strRange, entry);
						} else {
							domainEntries.put(entry.getValue().toString(), entry);
						}
					}
					String superDomainPath = superDomain.getSuperDomainPath();
					superDomain = CommonIndexUtils.getDomain(projectName, superDomainPath);
				}
			}
		}
		return domainEntries;
	}
	
	public static final ArrayList<String> getDomainEntriesList(List<DomainInstance> domainInstances, String projectName) {
		Map<String, DomainEntry> domainEntries = new HashMap<String, DomainEntry>();
		ArrayList<String> entriesList = new ArrayList<String>();
		
		//Dont care entry
		Single dontCare = DomainFactory.eINSTANCE.createSingle();
		dontCare.setValue(ASTERISK);
		domainEntries.put(ASTERISK, dontCare);
		if (domainInstances != null && domainInstances.size() > 0) {
			for (DomainInstance domainInstance : domainInstances) {
				String domainPath = domainInstance.getResourcePath();
				Domain domain = CommonIndexUtils.getDomain(projectName, domainPath);
				Domain superDomain = domain;
				
				while (superDomain != null) {
					List<DomainEntry> entries = superDomain.getEntries();
					for (DomainEntry entry : entries) {
						if (entry instanceof RangeImpl) {
							String strRange = getStringValueFromRangeInfo((Range)entry, domain);
							domainEntries.put(strRange, entry);
							entriesList.add(strRange);
						} else {
							domainEntries.put(entry.getValue().toString(), entry);
							entriesList.add(entry.getValue().toString());
						}
					}
					String superDomainPath = superDomain.getSuperDomainPath();
					superDomain = CommonIndexUtils.getDomain(projectName, superDomainPath);
				}
			}
		}
		return entriesList;
	}
	
	/**
	 * 
	 * @param domainInstances
	 * @param resourcePath
	 * @param projectName
	 * @param substitutionFormat
	 * @param showDomainDescription
	 * @param filterRanges
	 * @return
	 */
	public static String[] getDomainEntryStrings(List<DomainInstance> domainInstances, 
                                                 String resourcePath,
                                                 String projectName,
                                                 String substitutionFormat,
                                                 boolean showDomainDescription,
                                                 boolean filterRanges) {
		List<String> valList = new ArrayList<String>();
		
		if (domainInstances != null && domainInstances.size() > 0) {
			
			if (!filterRanges) {
				if (showDomainDescription) {
					valList.add(DESC);
					valList.add(EMPTY);
				} else {
					valList.add(VALUE);
					valList.add(ASTERISK);
				}
			} else {
				if (showDomainDescription) {
					valList.add(DESC);
				} else {
					valList.add(VALUE);
				}
			}
			
			for (DomainInstance domainInstance : domainInstances) {
				//Get the domain corresponding to this instance
				String domainPath = domainInstance.getResourcePath();
				Domain domain = CommonIndexUtils.getDomain(projectName, domainPath);
				
				//Do for all super domains
				Domain superDomain = domain;
				while (superDomain != null) {
					List<String> vals = getValArray(superDomain, 
							                        superDomain.getEntries(), 
							                        substitutionFormat, 
							                        showDomainDescription,
							                        filterRanges);
					valList.addAll(vals);
					String superDomainPath = superDomain.getSuperDomainPath();
					/**
					 * This is done to get updated value from index, 
					 * and not the cached in-memory one
					 */
					superDomain = CommonIndexUtils.getDomain(projectName, superDomainPath);
				}
			}
		}
		String[] stringValues = new String[valList.size()];
		return valList.toArray(stringValues);
	}
	
	/**
	 * Get a {@link List} of string values for domain entries
	 * @param domain
	 * @param domainEntries
	 * @param substitutionFormat
	 * @param showDomainDescription
	 * @param filterRanges -> Should range values be filtered
	 * @return
	 */
	public static List<String> getValArray(Domain domain, 
			                               List<DomainEntry> domainEntries,
			                               String substitutionFormat,
			                               boolean showDomainDescription,
			                               boolean filterRanges) {
		if (domain == null) return new ArrayList<String>(0);
		List<DomainEntry> entries = domainEntries;		
		if (entries.size() != 0) {
			List<String> values = new ArrayList<String>();
			MessageFormat mf = null;
			if (!substitutionFormat.isEmpty()) {
				mf = new MessageFormat(substitutionFormat);
			}
			for (int j = 0; j < entries.size(); j++) {
				DomainEntry entry = entries.get(j);
				if (entry instanceof Single) {
					String str = "";
					if (showDomainDescription) {
						str = ((Single)entry).getDescription();
						if (str.isEmpty()) {
							str = EMPTY;
						}
					} 
					if (!showDomainDescription || str == null || str.isEmpty()) {
						str = (String)((Single)entry).getValue().toString();
						if (str == null || !str.isEmpty()) {
							Object value = ((Single)entry).getValue();
							str = value.toString();
							if (mf != null) {
								try {
									Object[] substitutes = mf.parse(str);
									if (substitutes.length < 1) {
										continue;
									}
								} catch (ParseException pe) {
									continue;
								}
							}
						}
					}
					if (containsWhiteSpace(str)){
						str = "\"" + str + "\"";
					}
					else if (domain.getDataType() == DOMAIN_DATA_TYPES.STRING && !(str.startsWith("\"") && str.endsWith("\""))) {
						str = "\"" + str + "\"";
					}
							
					values.add(str);
					
				} else if (entry instanceof Range && !filterRanges) {
					String str = "";
					if (showDomainDescription) {
						str = ((Range)entry).getDescription();
						if (str.isEmpty()) {
							str = EMPTY;
						}
					}
					if (!showDomainDescription || str.isEmpty()) {
				
						str = getStringValueFromRangeInfo((Range)entry, domain);
						if (mf != null) {
							try {
								Object[] substitutes = mf.parse(str);
								String formattedString = MessageFormat.format(substitutionFormat, substitutes);
								if (!formattedString.equals(str)) {
									continue;
								}
								if (substitutes.length < 1) {
									continue;
								}
							} catch (ParseException pe) {
								continue;
							}
						}

					}
					values.add(str);
				}
			}
			return values;
		}
		return new ArrayList<String>(0);
	}
	
	/**
	 * Checks for white space for the given string
	 * @param str
	 * @return
	 */
	public static boolean containsWhiteSpace(final String str){
	    if (str != null) {
	        for (int i = 0; i < str.length(); i++) {
	            if(Character.isWhitespace(str.charAt(i))){
	                return true;
	            }
	        }
	    }
	    return false;
	}

	/**
	 * Get the string representation of the {@link Range} object passed
	 * @param range
	 * @return
	 */
	public static String getStringValueFromRangeInfo(Range range, Domain domain){
		String lower = range.getLower();
		String upper = range.getUpper();
		
//		if (domain.getDataType() == DOMAIN_DATA_TYPES.DATE_TIME) {
//			lower = new StringBuilder(DATE_TIME_PREFIX).
//            append(lower).
//            append("\", ").
//            append(UTC_FORMAT).
//            append(")").
//            toString();
//			
//			upper = new StringBuilder(DATE_TIME_PREFIX).
//            append(upper).
//            append("\", ").
//            append(UTC_FORMAT).
//            append(")").
//            toString();
//		}
		
		boolean includeLower = range.isLowerInclusive();
		boolean includeUpper = range.isUpperInclusive();
		return getStringValueFromRangeInfo(includeLower, includeUpper, lower, upper);
	}

	/**
	 * @param includeLower
	 * @param includeUpper
	 * @param lower
	 * @param upper
	 * @return
	 */
	public static String getStringValueFromRangeInfo(boolean includeLower, boolean includeUpper, String lower, String upper) {
		String lowerExp = "Undefined"
				.equalsIgnoreCase(lower) ? ""
						: (includeLower ? ">= " : "> ")
					+ lower;
		String upperExp = "Undefined"
				.equalsIgnoreCase(upper) ? ""
						: (includeUpper ? "<= " : "< ")
						+ upper;		
		String str = upperExp.length() != 0
		&& lowerExp.length() != 0 ? lowerExp
				+ " && " + upperExp
				: (lowerExp.length() != 0 ? lowerExp
						: (upperExp.length() != 0 ? upperExp
								: ""));
		
		return str;
	}
	
	/*
	 * Moved here from DecisionTableUtil
	 */
	public static PropertyDefinition getPropertyDefinition(String propertyPath,
			                                               String projectName) {
		if (null == propertyPath) {
			return null;
		}
		//Tokenize property path
		int lastIndex = propertyPath.lastIndexOf('/');
		if (lastIndex == -1) {
			//This may be a primitive
			return null;
		}
		String entityPath = propertyPath.substring(0, lastIndex);
		String propertyName = propertyPath.substring(lastIndex + 1, propertyPath.length());
		String[] splits = propertyName.split("\\[\\]");
		//Get first value
		propertyName = splits[0];
		
		//Check if the entity exists
		Entity entity = CommonIndexUtils.getEntity(projectName, entityPath);
		PropertyDefinition pd = null;
		//Get property def for this
		if (entity instanceof com.tibco.cep.designtime.core.model.element.Concept) {
			com.tibco.cep.designtime.core.model.element.Concept concept = (com.tibco.cep.designtime.core.model.element.Concept)entity;
			List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> allProps = concept.getAllProperties();
			for (com.tibco.cep.designtime.core.model.element.PropertyDefinition propertyDefinition : allProps) {
				if (propertyDefinition.getName().equals(propertyName)) {
					//Found
					pd = propertyDefinition;
					break;
				}
			}
		}
		if (entity instanceof com.tibco.cep.designtime.core.model.event.Event) {
			com.tibco.cep.designtime.core.model.event.Event event = (com.tibco.cep.designtime.core.model.event.Event)entity;
			List<com.tibco.cep.designtime.core.model.element.PropertyDefinition> allProps = event.getAllUserProperties();
			for (com.tibco.cep.designtime.core.model.element.PropertyDefinition propertyDefinition : allProps) {
				if (propertyDefinition.getName().equals(propertyName)) {
					//Found
					pd = propertyDefinition;
					break;
				}
			}
		}
		return pd;
	}
	
	/**
	 * 
	 * @param propertyPath
	 * @param projectName
	 * @return
	 */
	public static List<DomainInstance> getDomains(String propertyPath,
                                                  String projectName) {
		PropertyDefinition pd = getPropertyDefinition(propertyPath, projectName);
		if (pd != null) return pd.getDomainInstances();
		else return null;
	}
	
	/**
	 * @param domainInstances
	 * @param resourcePath
	 * @param projectName
	 * @param substitutionFormat
	 * @param filterRanges
	 * @return
	 */
	public static List<List<String>> getDomainEntryDropDownStrings(List<DomainInstance> domainInstances, 
																   String resourcePath,
																   String projectName,
																   String substitutionFormat,
																   boolean filterRanges,
																   boolean includeHeaders) {
		
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> valList = new ArrayList<String>();
		List<String> descList = new ArrayList<String>();
		List<Boolean> isRangeTypeList = new ArrayList<Boolean>();
		
		if (includeHeaders) {
			valList.add(VALUE);
			descList.add(DESC);
		}
		
		if (domainInstances != null && domainInstances.size() > 0) {

			if (!filterRanges) {
				valList.add(ASTERISK);
				descList.add(EMPTY);
			}

			for (DomainInstance domainInstance : domainInstances) {
				//Get the domain corresponding to this instance
				String domainPath = domainInstance.getResourcePath();
				Domain domain = CommonIndexUtils.getDomain(projectName, domainPath);

				//Do for all super domains
				Domain superDomain = domain;
				while (superDomain != null) {
					getValDescDropDownArray(superDomain, 
											superDomain.getEntries(), 
											substitutionFormat, 
											valList,
											descList,
											isRangeTypeList,
											filterRanges);
					String superDomainPath = superDomain.getSuperDomainPath();
					/**
					 * This is done to get updated value from index, 
					 * and not the cached in-memory one
					 */
					superDomain = CommonIndexUtils.getDomain(projectName, superDomainPath);
				}
			}
		}
		list.add(valList);
		list.add(descList);
		
		return list;
	}
	
	public static void getValDescDropDownArray(Domain domain, 
														List<DomainEntry> domainEntries,
														String substitutionFormat,
														List<String> valList, 
														List<String> descList,
														List<Boolean> isRangeTypeList,
														boolean filterRanges) {
		if (domain == null) return;
		List<DomainEntry> entries = domainEntries;		
		if (entries.size() != 0) {
			MessageFormat mf = null;
			if (!substitutionFormat.isEmpty()) {
				mf = new MessageFormat(substitutionFormat);
			}
			for (int j = 0; j < entries.size(); j++) {
				DomainEntry entry = entries.get(j);
				String empty = EMPTY;
				String val = "";
				String desc = "";
				if (entry instanceof Single) {
					Single single = (Single)entry;
					desc = single.getDescription();
					if (single.getValue() != null) {
						val = (String)((Single)entry).getValue().toString();
						if (val == null || !val.isEmpty()) {
							Object value = ((Single)entry).getValue();
							val = value.toString();
							if (mf != null) {
								try {
									Object[] substitutes = mf.parse(val);
									if (substitutes.length < 1) {
										continue;
									}
								} catch (ParseException pe) {
									continue;
								}
							}
						}
						if (containsWhiteSpace(val)) {
							val = "\""+ val + "\"";
						}
						else if (domain.getDataType() == DOMAIN_DATA_TYPES.STRING && !(val.startsWith("\"") && val.endsWith("\""))) {
							val = "\"" + val + "\"";
						}
						valList.add(val);
						if(desc == null) {
							descList.add(empty);
						} else {
							descList.add(desc.isEmpty() ? empty : desc);
						}
						isRangeTypeList.add(false);
					}
				} else if (entry instanceof Range && !filterRanges) {
					Range range = (Range)entry;
					desc = range.getDescription();
					val = getStringValueFromRangeInfo((Range)entry, domain);
					if (mf != null) {
						try {
							Object[] substitutes = mf.parse(val);
							String formattedString = MessageFormat.format(substitutionFormat, substitutes);
							if (!formattedString.equals(val)) {
								continue;
							}
							if (substitutes.length < 1) {
								continue;
							}
						} catch (ParseException pe) {
							continue;
						}
					}
					valList.add(val);
					descList.add(desc.isEmpty() ? empty : desc);
					isRangeTypeList.add(true);
				}
				
			}
		
		}
	}
	
	public static String toCompleteString(Object aobj, int valueIndex, Column col, String projectname, boolean b, boolean isDomDesc) {
        StringBuffer stringbuffer = new StringBuffer();
        if (aobj != null ) {
        	
        	List<String> valuesList = null;
        	
        	List<String> descsList = null;
        	
			List<DomainInstance> domainInstances = DTDomainUtil.getDomains(col.getPropertyPath(), projectname);

			if (domainInstances != null && domainInstances.size() > 0) { 
				String substitutionFormat = "";
				List<List<String>> values2 = DTDomainUtil.getDomainEntryDropDownStrings(domainInstances, 
						col.getPropertyPath(), 
						projectname, 
						substitutionFormat,
						col.getColumnType() == ColumnType.ACTION,
						false);
				valuesList = values2.get(0);
				descsList = values2.get(1);	
			}
        	
        	if (b) {
	        	if (aobj.getClass().isArray()) {
		            int length = Array.getLength(aobj);
		        	for(int i = 0; i < length; i++) {
		                Object o = Array.get(aobj, i);
			            stringbuffer.append(o.toString());
			            if(i != (length - 1))
			                stringbuffer.append(DTDomainUtil.SEPARATOR);
			        }
		        } else if (aobj instanceof List<?>) {
		        	List<?> list = (List<?>) aobj;
		        	Iterator<?> itr = list.iterator();
		        	while (itr.hasNext()) {
		        		Object obj = itr.next();
		        		if (obj instanceof List<?> && ((List<?>) obj).size() > 0) {
		        			if (valueIndex >= ((List<?>) obj).size())
		        				obj = ((List<?>) obj).get(0);
		        			else	
		        				obj = ((List<?>) obj).get(valueIndex);
		        		}
		        		if(isDomDesc){
		    				Object converted = converCustomMultiValueEntry(descsList,valuesList,obj);
		    				stringbuffer.append(converted);
		        			
		        		}else{
		        			stringbuffer.append(obj.toString());
		        		}
		        		
			            if (itr.hasNext())
			            	stringbuffer.append(DTDomainUtil.SEPARATOR);
		        	}	        	
		        }else {
		        	stringbuffer.append(aobj.toString());
		        }
        	} else {
        		Object obj = aobj;
        		if (aobj instanceof List<?>) {
		        	List<?> list = (List<?>) aobj;
	        		if (valueIndex < list.size()) {
	        			obj = list.get(valueIndex);
	        		} else if (list.size() == 1) {
	        			obj = list.get(0);
	        		}        	
		        }
        		
        		if(isDomDesc){
        			Object converted = converCustomMultiValueEntry(descsList,valuesList,obj);
        			stringbuffer.append(converted.toString());

        		}else{
        			stringbuffer.append(obj.toString());
        		}
        	}
        }
        return new String(stringbuffer);
	}
	
	public static Object converCustomMultiValueEntry(List<String> descsList, List<String> valuesList, Object object) {

		boolean isNotEqual = false;

		String descOnly = object.toString();

		if(object.toString().contains("!=")){
			isNotEqual = true;
		}
		if(isNotEqual){
			descOnly = object.toString().substring(2).trim();
		}
		int descIndx = descsList.indexOf(descOnly);

		if (descIndx >= 0) {
			if(isNotEqual){
				return "!="+valuesList.get(descIndx);
			}else{
				return valuesList.get(descIndx);
			}
		}else{

			if(isNotEqual){
				return "!="+descOnly;
			}else{
				return descOnly;
			}

		}	

	}
	
}
