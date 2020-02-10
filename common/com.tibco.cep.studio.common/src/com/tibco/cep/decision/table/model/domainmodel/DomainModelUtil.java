package com.tibco.cep.decision.table.model.domainmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.tibco.cep.decisionprojectmodel.DomainModel;

public class DomainModelUtil {
	public static final boolean IS_STRICT = false;
	public static final String SEPARATOR = "; ";
	
	public static String[] getDomainEntryStrings(DomainModel domainModel, String resourcePath) {
		return getDomainEntryStrings(domainModel, resourcePath, null);
	}
	public static String[] getDomainEntryStrings(DomainModel domainModel, String resourcePath, ArrayList<DomainEntry> domainEntries) {
		if (domainModel != null) {
			//Domain domain = argumentProperty.getDomain();				

			List<Domain> domainList = domainModel.getDomain();
			for (Domain domain : domainList) {
				if (domain != null && resourcePath != null && domain.getResource().equals(resourcePath)) {
					/*
					java.util.List<DomainEntry> entries = domain
							.getDomainEntry();					
					if (entries.size() != 0) {
						String[] values = new String[entries.size() + 1];
						values[0] = "*";
						for (int j = 0; j < entries.size(); j++) {
							DomainEntry entry = entries.get(j);
							if(domainEntries != null) domainEntries.add(entry);
							EntryValue entryValue = entry
									.getEntryValue();
							if (entryValue instanceof SingleValue) {
								values[j + 1] = entryValue.getValue();
							} else if (entryValue instanceof RangeInfo) {
								String lower = ((RangeInfo) entryValue)
										.getLowerBound();
								String upper = ((RangeInfo) entryValue)
										.getUpperBound();
								boolean includeLower = ((RangeInfo) entryValue)
										.isLowerBoundIncluded();
								boolean includeUpper = ((RangeInfo) entryValue)
										.isUpperBoundIncluded();
								String lowerExp = "Undefined"
										.equalsIgnoreCase(lower) ? ""
										: (includeLower ? ">= " : "> ")
												+ lower;
								String upperExp = "Undefined"
										.equalsIgnoreCase(upper) ? ""
										: (includeUpper ? "<= " : "< ")
												+ upper;
								values[j + 1] = upperExp.length() != 0
										&& lowerExp.length() != 0 ? lowerExp
										+ " && " + upperExp
										: (lowerExp.length() != 0 ? lowerExp
												: (upperExp.length() != 0 ? upperExp
														: ""));
							}
						}
	
						
						return values;
					}
		*/
					
					String[] vals = getValArray(domain, domainEntries,null);
					List<String> valList = Arrays.asList(vals); 
					if (valList != null && valList.size() > 0){
						String dbRef = domain.getDbRef();
						if (dbRef != null && !dbRef.trim().equals("")){
							// get domain from dbref
							Domain dm = getDomain(dbRef, domainModel);
							String[] valArr = getValArray(dm, domainEntries,valList);
							// merge both the arrays
							String[] compArray = new String[vals.length + valArr.length];
							System.arraycopy(vals, 0, compArray, 0, vals.length);
							System.arraycopy(valArr, 0, compArray, vals.length, valArr.length);
							return compArray;
						} else {
							return vals;
						}
						
					} else {
						String dbRef = domain.getDbRef();
						if (dbRef != null && !dbRef.trim().equals("")){
							// get domain from dbref
							Domain dm = getDomain(dbRef, domainModel);
							String[] valArr = getValArray(dm, domainEntries,null);
							return valArr;
						} else {
							return vals;
						}
					}
				}
			}
		}
		return null;
	}
	/**
	 * get domain for a property
	 * @param propPath
	 * @param domainModel
	 * @return
	 */
	public static Domain getDomain(String propPath,DomainModel domainModel){
		if (propPath == null || domainModel == null) return null;
		for (Domain domain : domainModel.getDomain()){
			String resPath = domain.getResource();
			if (propPath.equals(resPath)){
				return domain;
			}
		}
		return null;
	}
	/**
	 * returns the domain entries string array (duplicate values will be removed)
	 * @param domain
	 * @param domainEntries
	 * @param matchList --> list of values which are retrieved from Normal Concept Properties's domain Entries	 * 
	 * @return
	 */
	public static String[] getValArray(Domain domain ,List<DomainEntry> domainEntries,List<String> matchList){
		if (domain == null) return new String[0];
		
		java.util.List<DomainEntry> entries = domain.getDomainEntry();		
		if (entries.size() != 0) {
			List<String> values = new ArrayList<String>();
			if (matchList == null){
				values.add("*");
			}
			for (int j = 0; j < entries.size(); j++) {
				DomainEntry entry = entries.get(j);				
				EntryValue entryValue = entry.getEntryValue();
				if (entryValue instanceof SingleValue) {
					//values[j + 1] = entryValue.getValue();
					String str = entryValue.getValue();
					if (matchList != null){
						if (str != null && !matchList.contains(str)){
							values.add(str);
							if(domainEntries != null) domainEntries.add(entry);
						}
					} else {
						values.add(str);
						if(domainEntries != null) domainEntries.add(entry);
					}
				} else if (entryValue instanceof RangeInfo) {
					String str = getStringValueFromRangeInfo((RangeInfo)entryValue);
					if (matchList != null){
						if (str != null && !matchList.contains(str)){
							values.add(str);
						}
					} else {
						values.add(str);
						if(domainEntries != null) domainEntries.add(entry);
					}
				}
			}
			String[] valArray = new String [values.size()];
			return values.toArray(valArray);
		}
		
		return new String[0];
	}
	
	/**
	 * @category Tester.
	 * @TODO later it will be implemented for Domain Value/Description drop down  
	 * This method returns both domain value and descriptions array
	 * associate DB concept
	 * @param domainModel
	 * @param resourcePath
	 * @return
	 */
	public static String[][] getDomainEntryValuesAndDescriptions(DomainModel domainModel, String resourcePath ,boolean forDT) {
		return getDomainEntryValuesAndDescriptions(domainModel, resourcePath, null , forDT);
	}
	
	/**
	 * @category Tester
	 * @TODO later it will be implemented for Domain Value/Description drop down in Decision Table
	 * This method returns both domain value and descriptions array
	 * associate DB concept
	 * @param domainModel
	 * @param resourcePath
	 * @param domainEntries
	 * @return
	 */
	public static String[][] getDomainEntryValuesAndDescriptions(DomainModel domainModel, String resourcePath, ArrayList<DomainEntry> domainEntries , boolean forDT) {
		if (domainModel != null) {
			List<Domain> domainList = domainModel.getDomain();
			for (Domain domain : domainList) {
				if (domain != null && resourcePath != null && domain.getResource().equals(resourcePath)) {
					String[][] vals = getValueDescriptionArray(domain, domainEntries,null,forDT);
					List<String[]> valList = Arrays.asList(vals); 
					if (valList != null && valList.size() > 0){
						String dbRef = domain.getDbRef();
						if (dbRef != null && !dbRef.trim().equals("")){
							// get domain from dbref
							Domain dm = getDomain(dbRef, domainModel);
							String[][] valArr = getValueDescriptionArray(dm, domainEntries,valList , forDT);
							// merge both the arrays
							String[][] compArray = new String[vals.length + valArr.length][2];
							System.arraycopy(vals, 0, compArray, 0, vals.length);
							System.arraycopy(valArr, 0, compArray, vals.length, valArr.length);
							return compArray;
						} else {
							return vals;
						}
						
					} else {
						String dbRef = domain.getDbRef();
						if (dbRef != null && !dbRef.trim().equals("")){
							// get domain from dbref
							Domain dm = getDomain(dbRef, domainModel);
							String[][] valArr = getValueDescriptionArray(dm, domainEntries,null , forDT);
							return valArr;
						} else {
							return vals;
						}
					}
				}
			}
		}
		return null;
	}
	
	
	/**
	 * @category Tester
	 * @TODO later it will be implemented for Domain Value/Description drop down in Decision Table
	 * This method returns both domain value and descriptions array
	 * associate DB concept
	 * @param domain
	 * @param domainEntries
	 * @param matchList
	 * @return
	 */
	public static String[][] getValueDescriptionArray(Domain domain ,List<DomainEntry> domainEntries,List<String[]> matchList,boolean forDT){
		if (domain == null) return new String[0][0];
		
		java.util.List<DomainEntry> entries = domain.getDomainEntry();		
		if (entries.size() != 0) {
			List<String[]> values = new ArrayList<String[]>();
			if (forDT){
				if (matchList == null){
					values.add(new String[]{"*","*"});
				}
			}
			for (int j = 0; j < entries.size(); j++) {
				DomainEntry entry = entries.get(j);				
				EntryValue entryValue = entry.getEntryValue();
				if (entryValue instanceof SingleValue) {
					//values[j + 1] = entryValue.getValue();
					String str = entryValue.getValue();
					if (matchList != null){
						if (str != null && !matchList.contains(str)){
							values.add(new String[]{str,(entry.getEntryName() == null || 
									"".equals(entry.getEntryName().trim())) ? str : entry.getEntryName()});
							if(domainEntries != null) domainEntries.add(entry);
						}
					} else {
						values.add(new String[]{str,(entry.getEntryName() == null || 
								"".equals(entry.getEntryName().trim())) ? str : entry.getEntryName()});
						if(domainEntries != null) domainEntries.add(entry);
					}
				} else if (entryValue instanceof RangeInfo) {
					String str = getStringValueFromRangeInfo((RangeInfo)entryValue);
					if (matchList != null){
						if (str != null && !matchList.contains(str)){
							values.add(new String[]{str,(entry.getEntryName() == null || 
									"".equals(entry.getEntryName().trim())) ? str : entry.getEntryName()});
						}
					} else {
						values.add(new String[]{str,(entry.getEntryName() == null || 
										"".equals(entry.getEntryName().trim())) ? str : entry.getEntryName()});
						if(domainEntries != null) domainEntries.add(entry);
					}
				}
			}
			String[][] valArray = new String [values.size()][2];
			return values.toArray(valArray);
		}
		return new String[0][0];
	}
	
	public static String getDescription(String propPath , DomainModel dm, String value){
		if (propPath == null || value == null) return null;
		if (dm == null){
			// get domain model from Decision Project
			//dm = DecisionProjectLoader.getInstance().getDecisionProject().getDomainModel();
		}
		if (dm == null) return null;
		// get Domain
		Domain foundDomain = null;
		for (Domain domain : dm.getDomain()){
			String resource = domain.getResource();
			if (propPath.equals(resource)){
				foundDomain = domain;
				break;
			}
		}
		if (foundDomain == null) return null;
		return null;
	}
	/**
	 * 
	 * @param rangeInfo
	 * @return
	 */
	public static String getStringValueFromRangeInfo(RangeInfo rangeInfo){
		String lower = rangeInfo.getLowerBound();
		String upper = rangeInfo.getUpperBound();
		boolean includeLower = rangeInfo.isLowerBoundIncluded();
		boolean includeUpper = rangeInfo.isUpperBoundIncluded();
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
	/**
	 * populates Value and Entry Name Set from Domain
	 * @param domain
	 * @param valSet
	 * @param entryNameSet
	 */
	public static void populateValAndDescriptionSet(Domain domain,
			List<String> valList, List<String> entryNameList) {
		if (domain == null)
			return;

		for (DomainEntry entry : domain.getDomainEntry()) {
			EntryValue entryValue = entry.getEntryValue();
			if (entryValue instanceof SingleValue) {				
				String str = entryValue.getValue();
				valList.add(str);
				String des = (entry.getEntryName() == null || "".equals(entry
						.getEntryName().trim())) ? str : entry.getEntryName();
				entryNameList.add(des);

			} else if (entryValue instanceof RangeInfo) {
				String str = getStringValueFromRangeInfo((RangeInfo) entryValue);
				valList.add(str);
				String des = (entry.getEntryName() == null || "".equals(entry
						.getEntryName().trim())) ? str : entry.getEntryName();
				entryNameList.add(des);
			}
		}

	}
	/**
	 * returns Rang Domain Entry from String represention of DM Editor
	 * @param rangeInfoValue
	 * @param entryName
	 * @param rangeValueSeparator
	 * @return
	 */
	public static DomainEntry getRangeDomainEntryFromString(String rangeInfoValue ,String entryName, String rangeValueSeparator) {
		if (rangeInfoValue == null) return null;
		DomainEntry dmEntry = DomainModelFactory.eINSTANCE.createDomainEntry();
		RangeInfo entryValue = DomainModelFactory.eINSTANCE.createRangeInfo();
		if (rangeInfoValue.startsWith("[") && rangeInfoValue.endsWith("]")) {
			((RangeInfo) entryValue).setLowerBoundIncluded(true);
			((RangeInfo) entryValue).setUpperBoundIncluded(true);
		}
		if (rangeInfoValue.startsWith("[") && rangeInfoValue.endsWith(")")) {
			((RangeInfo) entryValue).setLowerBoundIncluded(true);
			((RangeInfo) entryValue).setUpperBoundIncluded(false);
		}
		if (rangeInfoValue.startsWith("(") && rangeInfoValue.endsWith("]")) {
			((RangeInfo) entryValue).setLowerBoundIncluded(false);
			((RangeInfo) entryValue).setUpperBoundIncluded(true);
		}
		if (rangeInfoValue.startsWith("(") && rangeInfoValue.endsWith(")")) {
			((RangeInfo) entryValue).setLowerBoundIncluded(false);
			((RangeInfo) entryValue).setUpperBoundIncluded(false);
		}
		String lowerValue = rangeInfoValue.substring(1, rangeInfoValue
				.indexOf(rangeValueSeparator));
		String upperValue = rangeInfoValue.substring(rangeInfoValue
				.indexOf(rangeValueSeparator) + 1, rangeInfoValue.length() - 1);

		if (!lowerValue.trim().equalsIgnoreCase(""))
			((RangeInfo) entryValue).setLowerBound(lowerValue.trim());
		else
			((RangeInfo) entryValue).setLowerBound("Undefined");

		if (!upperValue.trim().equalsIgnoreCase(""))
			((RangeInfo) entryValue).setUpperBound(upperValue.trim());
		else
			((RangeInfo) entryValue).setUpperBound("Undefined");

		entryValue.setMultiple(true);

		dmEntry.setEntryName(entryName);
		dmEntry.setEntryValue(entryValue);
		return dmEntry;
	}
	/**
	 * return Domain Entry for Single value from DM editor
	 * @param singleValue
	 * @param entryName
	 * @return
	 */
	public static DomainEntry getSingleValueDomainEntryFromString(String singleValue , String entryName) {
		if (singleValue == null) return null;
		DomainEntry dmEntry = DomainModelFactory.eINSTANCE.createDomainEntry();
		EntryValue singleEntryValue = DomainModelFactory.eINSTANCE
				.createSingleValue();
		singleEntryValue.setMultiple(false);
		/*
		 * not sure whay is this logic
		if ((DomainValidate.isStringLong(rangeInfoValue)
				|| DomainValidate.isStringNumeric(rangeInfoValue) || DomainValidate
				.isStringDouble(rangeInfoValue))
				&& typeText.getText().equalsIgnoreCase("String")) {
			rangeInfoValue = "\"" + rangeInfoValue + "\"";
		}
		*/
		
		singleEntryValue.setValue(singleValue);
		dmEntry.setEntryName(entryName);
		dmEntry.setEntryValue(singleEntryValue);
		return dmEntry;

	}
	/**
	 * return Domain Entry from String representation DM Editor
	 * @param strVal
	 * @param entryName
	 * @param rangeValueSeparator
	 * @return
	 */
	public static DomainEntry getDomainEntryFromString(String strVal ,String entryName, String rangeValueSeparator){
		if (strVal == null) return null;
		if (rangeValueSeparator == null){
			return getSingleValueDomainEntryFromString(strVal , entryName);
		} else {
			if (strVal.contains(rangeValueSeparator)){
				return getRangeDomainEntryFromString(strVal, entryName, rangeValueSeparator);
			} else {
				return getSingleValueDomainEntryFromString(strVal , entryName);
			}
		}
		
	}
	

}
