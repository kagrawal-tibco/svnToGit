package com.tibco.cep.decision.table.model.domainmodel.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;
import com.tibco.cep.decision.table.model.domainmodel.DomainModelUtil;
import com.tibco.cep.decision.table.model.domainmodel.EntryValue;
import com.tibco.cep.decision.table.model.domainmodel.RangeInfo;
import com.tibco.cep.decisionprojectmodel.DomainModel;

/**
 * singleton class to cache Domain for a specific property
 * @author rmishra
 *
 */
public class DMCache implements DMListener {
	private static DMCache dmCache ;
	private Map<String, DomainInfo> propDomainInfoMap = new HashMap<String, DomainInfo>();
	
	private DMCache(){
		
	}
	
	synchronized public static DMCache getInstance(){
		if (dmCache == null){
			dmCache = new DMCache();
	
		}
		return dmCache;
	}
	/**
	 * caches the domain model for faster access
	 * @param dm
	 */
	public void cache(final DomainModel dm){
		if (dm == null) return;
		for (Domain domain : dm.getDomain()){
			String resource = domain.getResource();
			List<String> valList = new ArrayList<String>();
			List<String> desList = new ArrayList<String>();
			DomainModelUtil.populateValAndDescriptionSet(domain, valList, desList);
			DomainInfo domainInfo = new DomainInfo(domain , valList ,desList);
			propDomainInfoMap.put(resource, domainInfo);
	
		}
	}
	/**
	 * Notify if Domain Model is updated
	 */
	public void notify(DMEvent dmEvent){
		if (DMEventType.ADD == dmEvent.getDmEventType()){
			domainEntryAdded(dmEvent);
		} else if (DMEventType.DELETE == dmEvent.getDmEventType()){
			domainEntryRemoved(dmEvent);
		} else if (DMEventType.MODIFY == dmEvent.getDmEventType()){
			domainEntryModified(dmEvent);
		} else if (DMEventType.ADD_DBREF == dmEvent.getDmEventType()){
			dbRefAdded(dmEvent);
		} else if (DMEventType.DELETE_ALL == dmEvent.getDmEventType()){
			deleteAllEntries(dmEvent);
		}
		
	}
	/**
	 * DB concept property reference added
	 * @param dmEvent
	 */
	private void dbRefAdded(DMEvent dmEvent){
		Domain source = dmEvent.getSource();
		String propPath = source.getResource();
		DomainInfo dmInfo = propDomainInfoMap.get(propPath);
		if (dmInfo != null){
			// already present in cache
			String dbRef = source.getDbRef();
			Domain domain = dmInfo.getDomain();
			if (domain != null){
				domain.setDbRef(dbRef);
			}
		} else {
			// not present in cache
			List<String> valList = new ArrayList<String>();
			List<String> desList = new ArrayList<String>();
			dmInfo = new DomainInfo(source,valList,desList);
			propDomainInfoMap.put(propPath, dmInfo);
		}
	}
	/**
	 * All domain entries deleted 
	 * @param dmEvent
	 */
	private void deleteAllEntries(DMEvent dmEvent){
		Domain source = dmEvent.getSource();	
		String propPath = source.getResource();
		DomainInfo dmInfo = propDomainInfoMap.get(propPath);
		if (dmInfo != null){			
			dmInfo.getValList().clear();
			dmInfo.getDesList().clear();	
		}
	}
	/**
	 * 
	 * @param dmEvent
	 */
	private void domainEntryAdded(DMEvent dmEvent){
		Domain source = dmEvent.getSource();
		DomainEntry oldEntry = dmEvent.getOldVal();
		DomainEntry newEntry = dmEvent.getNewVal();
		if (newEntry == null) return;
		String propPath = source.getResource();
		DomainInfo dmInfo = propDomainInfoMap.get(propPath);
		if (dmInfo == null){
			// new Domain is added for that property
			String val = null;
			String des = null;			
			EntryValue entryValue = newEntry.getEntryValue();
			des = newEntry.getEntryName();
			if (entryValue instanceof RangeInfo){
				val = DomainModelUtil.getStringValueFromRangeInfo((RangeInfo)entryValue);				
			} else {
				val = entryValue.getValue();
			}
			if (des == null || "".equals(des.trim())){
				des = val;
			}
			List<String> valList = new ArrayList<String>();
			List<String> desList = new ArrayList<String>();
			valList.add(val);
			desList.add(des);
			dmInfo = new DomainInfo(source , valList , desList);
			propDomainInfoMap.put(propPath, dmInfo);
			
			return;
		} else {
			// Domain is already present
			String val = null;
			String des = null;
			EntryValue entryValue = newEntry.getEntryValue();
			des = newEntry.getEntryName();
			if (entryValue instanceof RangeInfo){
				val = DomainModelUtil.getStringValueFromRangeInfo((RangeInfo)entryValue);				
			} else {
				val = entryValue.getValue();
			}
			if (des == null || "".equals(des.trim())){
				des = val;
			}
			List<String> valList = dmInfo.getValList();
			List<String> desList = dmInfo.getDesList();
			valList.add(val);
			desList.add(des);
			
			
		}
		
	}
	/**
	 * 
	 * @param dmEvent
	 */
	private void domainEntryRemoved(DMEvent dmEvent){
		Domain source = dmEvent.getSource();
		DomainEntry oldEntry = dmEvent.getOldVal();
		if (oldEntry == null) return;
		DomainEntry newEntry = dmEvent.getNewVal();		
		String propPath = source.getResource();
		DomainInfo dmInfo = propDomainInfoMap.get(propPath);
		if (dmInfo != null){
			String val = null;
			String des = null;			
			EntryValue entryValue = oldEntry.getEntryValue();
			des = oldEntry.getEntryName();
			if (entryValue instanceof RangeInfo){
				val = DomainModelUtil.getStringValueFromRangeInfo((RangeInfo)entryValue);				
			} else {
				val = entryValue.getValue();
			}
			List<String> valList = dmInfo.getValList();
			List<String> desList = dmInfo.getDesList();
			//boolean removed = valList.remove(val);
			int index = valList.indexOf(val);
			if (index != -1){
				valList.remove(index);
				desList.remove(index);
			}
			
		}
	}
	/**
	 * 
	 * @param dmEvent
	 */
	private void domainEntryModified(DMEvent dmEvent){
		Domain source = dmEvent.getSource();
		DomainEntry oldEntry = dmEvent.getOldVal();
		DomainEntry newEntry = dmEvent.getNewVal();
		String propPath = source.getResource();
		DomainInfo dmInfo = propDomainInfoMap.get(propPath);
		if (dmInfo != null){
			String oldval = null;
			String olddes = null;
			String newval = null;
			String newdes = null;
			EntryValue oldEntryValue = oldEntry.getEntryValue();
			olddes = oldEntry.getEntryName();
			if (oldEntryValue instanceof RangeInfo){
				oldval = DomainModelUtil.getStringValueFromRangeInfo((RangeInfo)oldEntryValue);				
			} else {
				oldval = oldEntryValue.getValue();
			}
			newdes = newEntry.getEntryName();
			EntryValue newEntryValue = newEntry.getEntryValue();
			if (newEntryValue instanceof RangeInfo){
				newval = DomainModelUtil.getStringValueFromRangeInfo((RangeInfo)newEntryValue);				
			} else {
				newval = newEntryValue.getValue();
			}
			if (newdes == null || "".equals(newdes.trim())){
				newdes = newval;
			}
			List<String> valList = dmInfo.getValList();
			List<String> desList = dmInfo.getDesList();
			int index = valList.indexOf(oldval);
			if (index != -1){
				valList.set(index, newval);
				desList.set(index, newdes);
			}
			
		}
	}
	

	public static final class DomainInfo {	
		private Domain domain;
		private List<String> valList;
		private List<String> desList;
		DomainInfo(Domain domain , List<String> valList , List<String> desList ){		
			this.domain = domain;
			this.valList = valList;
			this.desList = desList;
		}
		public List<String> getValList() {
			return valList;
		}
		public List<String> getDesList() {
			return desList;
		}
		public Domain getDomain() {
			return domain;
		}


	}


	public Map<String, DomainInfo> getPropDomainInfoMap() {
		return propDomainInfoMap;
	}
}
