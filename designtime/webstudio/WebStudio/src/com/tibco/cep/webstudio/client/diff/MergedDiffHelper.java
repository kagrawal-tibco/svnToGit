package com.tibco.cep.webstudio.client.diff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.tibco.cep.webstudio.client.diff.DiffHelper.DTDiffType;
import com.tibco.cep.webstudio.client.i18n.GlobalMessages;
import com.tibco.cep.webstudio.client.i18n.I18nRegistry;
import com.tibco.cep.webstudio.client.model.ruletemplate.BindingInfo;
import com.tibco.cep.webstudio.model.rule.instance.Filter;
import com.tibco.cep.webstudio.model.rule.instance.MultiFilter;

/**
 * This class provides various helper methods required for merged diff functionality.
 * 
 * @author moshaikh
 *
 */
public class MergedDiffHelper {
	
	private static GlobalMessages globalMsg = (GlobalMessages)I18nRegistry.getResourceBundle(I18nRegistry.GLOBAL_MESSAGES);
	
	/**
	 * Process diff between 3 versions of a DT</br>
	 * viz: User's base copy[v1], user's working copy(local changes)[v2], server copy[v3].</br>
	 * First compares v1 with v2 and populates modifications map. Then compares v1 with v3.
	 * @param dt_v1
	 * @param dt_v2
	 * @param dt_v3
	 * @param modifications
	 */
	public static Node processDTMergedDiff(Node dt_v1, Node dt_v2, Node dt_v3, Map<String, ModificationEntry> modifications) {
		Map<String, ModificationEntry> tempModifications = new HashMap<String, ModificationEntry>();
		processDTMergedDiffInternal(dt_v1, dt_v2, dt_v3, tempModifications, DTDiffType.DECISION_TABLE);
		for (Entry<String, ModificationEntry> entry : tempModifications.entrySet()) {
			modifications.put(DTDiffType.DECISION_TABLE.getKeyPrefix() + entry.getKey(), entry.getValue());
		}
		tempModifications.clear();
		processDTMergedDiffInternal(dt_v1, dt_v2, dt_v3, tempModifications, DTDiffType.EXCEPTION_TABLE);
		for (Entry<String, ModificationEntry> entry : tempModifications.entrySet()) {
			modifications.put(DTDiffType.EXCEPTION_TABLE.getKeyPrefix() + entry.getKey(), entry.getValue());
		}
		return dt_v3;
	}
	
	/**
	 * 
	 * @param dt_v1
	 * @param dt_v2
	 * @param dt_v3
	 * @param modifications
	 * @param diffType
	 * @return
	 */
	private static void processDTMergedDiffInternal(Node dt_v1, Node dt_v2, Node dt_v3, Map<String, ModificationEntry> modifications, DTDiffType diffType) {
		Node temp_dt_v1 = dt_v1.cloneNode(true);
		Node temp_dt_v2 = dt_v2.cloneNode(true);
		Node temp_dt_v3 = dt_v3.cloneNode(true);
		
		Map<String, ModificationEntry> modifications_i1 = new LinkedHashMap<String, ModificationEntry>();
		DiffHelper.processDTDiffInternal(dt_v1, dt_v2, modifications_i1, diffType);//Collect diff between v1 & v2. Also merge v1, v2 into v2
		//Now dt_v2 is the merged copy of v1 and v2
		Map<String, ModificationEntry> modifications_i2 = new LinkedHashMap<String, ModificationEntry>();
		DiffHelper.processDTDiffInternal(temp_dt_v1, temp_dt_v3, modifications_i2, diffType);//Only collect diff between v1 and v3. (merged copy is collected in tempV3 and is to be ignored.)
		
		Map<String, ModificationEntry> modifications_i3 = new LinkedHashMap<String, ModificationEntry>();//between v2, v3 (to capture changes in rules added locally and on server)
		temp_dt_v3 = dt_v3.cloneNode(true);
		DiffHelper.processDTDiffInternal(temp_dt_v2, temp_dt_v3, modifications_i3, diffType);
		
		Map<String, ModificationEntry> ignore = new LinkedHashMap<String, ModificationEntry>();
		DiffHelper.processDTDiffInternal(dt_v2, dt_v3, ignore, diffType);//Just merge (v1 & v2) into v3, modifications are already collected hence ignored.
		
		mergeDTDiffs(modifications_i1, modifications_i2, modifications_i3, modifications);
	}
	
	/**
	 * Merges 2 diffs into a single one, used while computing diff between UserBase copy, UserModified Copy, Server Copy.
	 * @param modifications1	Modification collected during iteration1	Base vs User copy
	 * @param modifications2	Modification collected during iteration2	Base vs Server copy
	 * @param modifications3	Modification collected during iteration3	User vs Server copy
	 * @param mergedModifications
	 */
	private static void mergeDTDiffs(Map<String, ModificationEntry> modifications1, Map<String, ModificationEntry> modifications2, Map<String, ModificationEntry> modifications3, Map<String, ModificationEntry> mergedModifications) {
		handleColumnMerges(modifications1, modifications2, mergedModifications);
		for (Entry<String, ModificationEntry> entry : modifications1.entrySet()) {
			if (entry.getKey().matches("ruleCell[0-9].*")) {
				ModificationEntry me1 = entry.getValue();
				ModificationEntry me2 = modifications2.get(entry.getKey());
				if (me2 == null) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, ModificationType.MODIFIED, null);
					mergeEntry.setMessage("RuleVariable modified Locally.");
					mergeEntry.setBaseVersion(me1.getPreviousValue());
					mergeEntry.setLocalVersion(me1.getCurrentValue());
					mergedModifications.put(entry.getKey(), mergeEntry); modifications2.remove(entry.getKey());
				}
				else {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, ModificationType.MODIFIED, ModificationType.MODIFIED);
					mergeEntry.setMessage("RuleVariable modified Locally and on Server.");
					mergeEntry.setBaseVersion(me1.getPreviousValue());
					mergeEntry.setLocalVersion(me1.getCurrentValue());
					mergedModifications.put(entry.getKey(), mergeEntry); modifications2.remove(entry.getKey());
				}
			}
			else if (entry.getKey().matches("tableRule[0-9].*")) {
				ModificationEntry me1 = entry.getValue();
				ModificationEntry me2 = modifications2.get(entry.getKey());
				if (me2 == null) {
					if (me1.getModificationType() == ModificationType.ADDED) {
						MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.ADDED, ModificationType.ADDED, null);
						mergeEntry.setMessage("Rule added locally.");
						mergedModifications.put(entry.getKey(),  mergeEntry); modifications2.remove(entry.getKey());
					}
					else if (me1.getModificationType() == ModificationType.DELETED) {
						MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, null);
						mergeEntry.setMessage("Rule deleted locally.");
						mergedModifications.put(entry.getKey(),  mergeEntry); modifications2.remove(entry.getKey());
					}
				}
				else {
					if (me1.getModificationType() == ModificationType.ADDED && me2.getModificationType() == ModificationType.ADDED) {
						MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.ADDED, ModificationType.ADDED, ModificationType.ADDED);
						mergeEntry.setMessage("Rule added locally and on server.");
						mergedModifications.put(entry.getKey(),  mergeEntry); modifications2.remove(entry.getKey());
					}
					else if (me1.getModificationType() == ModificationType.DELETED && me2.getModificationType() == ModificationType.DELETED) {
						MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, ModificationType.DELETED);
						mergeEntry.setMessage("Rule deleted locally and on server.");
						mergedModifications.put(entry.getKey(),  mergeEntry); modifications2.remove(entry.getKey());
					}
				}
			}
		}
		
		for (Entry<String, ModificationEntry> entry : modifications2.entrySet()) {//Merge remaining entries of modifications2 into mergeModification (The changes that occurred on server side only)
			if (entry.getKey().matches("ruleCell[0-9].*")) {
				ModificationEntry me2 = entry.getValue();
				MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, null, ModificationType.MODIFIED);
				mergeEntry.setMessage("RuleVariable modified on Server.");
				mergeEntry.setBaseVersion(me2.getPreviousValue());
				mergedModifications.put(entry.getKey(), mergeEntry);
			}
			else if (entry.getKey().matches("tableRule[0-9].*")) {
				ModificationEntry me2 = entry.getValue();
				if (me2.getModificationType() == ModificationType.ADDED) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.ADDED, null, ModificationType.ADDED);
					mergeEntry.setMessage("Rule added on server.");
					mergedModifications.put(entry.getKey(),  mergeEntry);
				}
				else if (me2.getModificationType() == ModificationType.DELETED) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, null, ModificationType.DELETED);
					mergeEntry.setMessage("Rule deleted on server.");
					mergedModifications.put(entry.getKey(),  mergeEntry); 
				}
			}
		}
		
		//Show on hover of a conflicting cell in THE case of rule added locally and on server.
		for (Entry<String, ModificationEntry> entry : modifications3.entrySet()) {
			if (entry.getKey().matches("ruleCell[0-9].*") && entry.getValue().getModificationType() == ModificationType.MODIFIED) {
				ModificationEntry addition = mergedModifications.get("tableRule" + entry.getKey().substring(8, entry.getKey().indexOf('_')));
				if (addition != null && addition.getModificationType() == ModificationType.ADDED) { 
					ModificationEntry me3 = entry.getValue();
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, ModificationType.ADDED, ModificationType.ADDED);
					mergeEntry.setMessage("Rule added locally and on server."); 
					mergeEntry.setLocalVersion(me3.getPreviousValue());
					mergedModifications.put(entry.getKey(), mergeEntry);
				}
			}
		}
	}
	
	/**
	 * Handles all modifications related to column merges. Create
	 * MergedDiffModificationEntries in megedModifications, also removes
	 * processed entries from modifications1 and modifications2.
	 * 
	 * @param modifications1
	 * @param modifications2
	 * @param modifications3
	 * @param mergedModifications
	 */
	private static void handleColumnMerges(Map<String, ModificationEntry> modifications1,
			Map<String, ModificationEntry> modifications2, Map<String, ModificationEntry> mergedModifications) {
		Set<String> columnEntryKeys = new HashSet<String>();
		columnEntryKeys.addAll(DiffHelper.findMatchingEntries(modifications1.keySet(), "column(\\d+)"));
		columnEntryKeys.addAll(DiffHelper.findMatchingEntries(modifications2.keySet(), "column(\\d+)"));
		for (String key : columnEntryKeys) {
			ModificationEntry columnEntry1 = modifications1.get(key);
			ModificationEntry columnEntry2 = modifications2.get(key);
			if (columnEntry1 != null && columnEntry2 != null) {//Modification locally and on server
				if (columnEntry1.getModificationType() == ModificationType.ADDED && columnEntry2.getModificationType() == ModificationType.ADDED) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.ADDED, ModificationType.ADDED, ModificationType.ADDED);
					mergeEntry.setMessage("Condition/Action Added locally and on Server.");
					mergedModifications.put(key, mergeEntry);
				}
				else if (columnEntry1.getModificationType() == ModificationType.DELETED && columnEntry2.getModificationType() == ModificationType.DELETED) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, ModificationType.DELETED);
					mergeEntry.setMessage("Condition/Action Deleted locally and on Server.");
					mergedModifications.put(key, mergeEntry);
				}
				//Added/Deleted locally/server and renamed on server/locally
			}
			else if (columnEntry1 != null) {//Local change only
				if (columnEntry1.getModificationType() == ModificationType.ADDED) {//Column added locally
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.ADDED, ModificationType.ADDED, null);
					mergeEntry.setMessage("Condition/Action Added locally.");
					mergedModifications.put(key, mergeEntry);
				}
				else if (columnEntry1.getModificationType() == ModificationType.DELETED) {//Column deleted locally
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, null);
					mergeEntry.setMessage("Condition/Action Deleted locally.");
					mergedModifications.put(key, mergeEntry);
				}
				//else {}//Rename not possible via WS as of today.
			}
			else if (columnEntry2 != null) {//Serverside only change
				if (columnEntry2.getModificationType() == ModificationType.ADDED) {//Column added serverside
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.ADDED, null, ModificationType.ADDED);
					mergeEntry.setMessage("Condition/Action Added on Server.");
					mergedModifications.put(key, mergeEntry);
				}
				else if (columnEntry2.getModificationType() == ModificationType.DELETED) {//Column deleted server side
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, null, ModificationType.DELETED);
					mergeEntry.setMessage("Condition/Action Deleted on Server.");
					mergedModifications.put(key, mergeEntry);
				}
			}
			//Remove all column entries from modifications1 and modifications2
			modifications1.remove(key);
			modifications2.remove(key);
		}
		
		//For all columnModification replace corresponding cell modifications appropriately.
		for (String key : columnEntryKeys) {
			String columnId = key.substring(6);//Index-6 to match Column<NUM> etc
			Set<String> ruleCellEntryKeys = new HashSet<String>();
			ruleCellEntryKeys.addAll(DiffHelper.findMatchingEntries(modifications1.keySet(), "ruleCell(\\d+)(_)" + columnId));
			ruleCellEntryKeys.addAll(DiffHelper.findMatchingEntries(modifications2.keySet(), "ruleCell(\\d+)(_)" + columnId));
			
			for (String ruleCellEntryKey : ruleCellEntryKeys) {
				ModificationEntry ruleCellEntry1 = modifications1.get(ruleCellEntryKey);
				ModificationEntry ruleCellEntry2 = modifications2.get(ruleCellEntryKey);
				if (ruleCellEntry1 != null) {//Column deleted on server & ruleVar modified locally
					MergedDiffModificationEntry mergedEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, ModificationType.MODIFIED, ModificationType.DELETED);
					mergedEntry.setMessage("Condition/Action Deleted on Server, RuleVariable modified Locally.");
					mergedEntry.setLocalVersion(ruleCellEntry1.getCurrentValue());
					mergedEntry.setBaseVersion(ruleCellEntry1.getPreviousValue());
					mergedModifications.put(ruleCellEntryKey, mergedEntry);
				}
				else if (ruleCellEntry2 != null) {//Column deleted locally & ruleVar modified on server
					MergedDiffModificationEntry mergedEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, ModificationType.DELETED, ModificationType.MODIFIED);
					mergedEntry.setMessage("Condition/Action Deleted locally, RuleVariable modified on server.");
					mergedEntry.setBaseVersion(ruleCellEntry2.getPreviousValue());
					mergedModifications.put(ruleCellEntryKey, mergedEntry);
				}
				
				//Remove all ruleCell entries corresponding to columnModifications from modifications1 and modifications2 
				modifications1.remove(ruleCellEntryKey);
				modifications2.remove(ruleCellEntryKey);
			}
		}
	}
	
	/**
	 * Process diff between 3 versions of a RTI bindings</br>
	 * viz: User's base copy[v1], user's working copy(local changes)[v2], server copy[v3].</br>
	 * First compares v1 with v2 then compares v1 with v3.
	 * @param bindingInfos_v1
	 * @param bindingInfos_v2
	 * @param bindingInfos_v3
	 * @param modifications
	 */
	public static void processRTIViewMergedDiff(
			List<BindingInfo> bindingInfos_v1,
			List<BindingInfo> bindingInfos_v2,
			List<BindingInfo> bindingInfos_v3,
			Map<BindingInfo, ModificationEntry> modifications) {
		
		Map<BindingInfo, ModificationEntry> modifications_i1 = new LinkedHashMap<BindingInfo, ModificationEntry>();
		DiffHelper.processRTIViewDiff(bindingInfos_v1, bindingInfos_v2, modifications_i1);//Collect diff between v1 & v2.
		
		Map<BindingInfo, ModificationEntry> modifications_i2 = new LinkedHashMap<BindingInfo, ModificationEntry>();
		DiffHelper.processRTIViewDiff(bindingInfos_v1, bindingInfos_v3, modifications_i2);//Collect diff between v1 and v3.
		
		//Modifications map uses BindingInfo as key. For merged diff the key should always be instance of BindingInfo from server copy(v3). 
		//Create a mapping for BindingInfo-Id to BindingInfo(of server/v3 copy).
		Map<String, BindingInfo> mappings = new HashMap<String, BindingInfo>();
		for (BindingInfo bi : bindingInfos_v3) {
			mappings.put(bi.getId(), bi);
		}
		
		mergeRTIViewDiffs(modifications_i1, modifications_i2, mappings, modifications);
	}
	
	/**
	 * Merges modifications.
	 * @param modifications_i1	v1 vs v2
	 * @param modifications_i2	v1 vs v3
	 * @param mappings	Mapping for BindingInfo-Id to BindingInfo(of server/v3 copy).
	 * @param mergedModifications
	 */
	private static void mergeRTIViewDiffs(
			Map<BindingInfo, ModificationEntry> modifications_i1,
			Map<BindingInfo, ModificationEntry> modifications_i2,
			Map<String, BindingInfo> mappings,
			Map<BindingInfo, ModificationEntry> mergedModifications) {
		
		for (Entry<BindingInfo, ModificationEntry> entry : modifications_i2.entrySet()) {
			MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, null, ModificationType.MODIFIED);
			mergeEntry.setMessage("Value modified on Server.");
			mergeEntry.setBaseVersion(entry.getValue().getPreviousValue());
			mergedModifications.put(entry.getKey(), mergeEntry);
		}
		for (Entry<BindingInfo, ModificationEntry> entry : modifications_i1.entrySet()) {
			if (mergedModifications.containsKey(mappings.get(entry.getKey().getId()))) {
				MergedDiffModificationEntry mergeEntry = (MergedDiffModificationEntry)mergedModifications.get(mappings.get(entry.getKey().getId()));
				mergeEntry.setLocalChangeType(ModificationType.MODIFIED);
				mergeEntry.setServerChangeType(ModificationType.MODIFIED);
				mergeEntry.setMessage("Value modified Locally and on Server.");
				mergeEntry.setLocalVersion(entry.getValue().getCurrentValue());
			}
			else {
				MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, ModificationType.MODIFIED, null);
				mergeEntry.setMessage("Value modified Locally.");
				mergeEntry.setBaseVersion(entry.getValue().getPreviousValue());
				mergeEntry.setLocalVersion(entry.getValue().getCurrentValue());
				mergedModifications.put(mappings.get(entry.getKey().getId()), mergeEntry);
			}
		}
	}
	
	/**
	 * Process RTI MultiFilter Diff between 3 versions.
	 * viz: User's base copy[v1], user's working copy(local changes)[v2], server copy[v3].
	 * First compares v1 with v2 collecting modifications, then compares v1 with v3 collecting modification separately. Finally merges the modifications into one.
	 * @param multiFilter_v1
	 * @param multiFilter_v2
	 * @param multiFilter_v3
	 * @param modifications
	 * @return	Returns a MultiFilter that is merge of all versions.
	 */
	public static MultiFilter processMultiFilterMergedDiff(MultiFilter multiFilter_v1, MultiFilter multiFilter_v2, MultiFilter multiFilter_v3, Map<String, ModificationEntry> modifications) {
		/**
		 * Map to collect local changes i.e between v1 and v2.
		 */
		Map<String, ModificationEntry> modifications_i1 = new LinkedHashMap<String, ModificationEntry>();
		DiffHelper.processFiltersDiff(multiFilter_v1.getSubClause().getFilters(), multiFilter_v2.getSubClause().getFilters(), modifications_i1);//Find out Local changes.
		
		Map<String, ModificationEntry> modifications_i2 = new LinkedHashMap<String, ModificationEntry>();
		DiffHelper.processFiltersDiff(multiFilter_v1.getSubClause().getFilters(), multiFilter_v3.getSubClause().getFilters(), modifications_i2);//Find out server side changes
		checkDifferentDropPoisition(multiFilter_v2, modifications_i1, modifications_i2);
		{
			/**
			 * Map to collect server side changes i.e between v1 and v3.
			 */
			Map<String, ModificationEntry> ignore = new LinkedHashMap<String, ModificationEntry>();
			DiffHelper.processFiltersDiff(multiFilter_v2.getSubClause().getFilters(), multiFilter_v3.getSubClause().getFilters(), ignore);//Merge all Filters into server copy ignoring modifications.
		}
		
		mergeFilterDiffs(modifications_i1, modifications_i2, modifications);
		
		return multiFilter_v3;
	}
	/**
	 * For finding the drop entries on local and server copies.
	 * 
	 */
	private static void checkDifferentDropPoisition(MultiFilter multiFilter, Map<String, ModificationEntry> modifications1, Map<String, ModificationEntry> modifications2){
		ArrayList<String> processedIds = new ArrayList<String>();
		Map<String, String> idsToBeModified = new LinkedHashMap <String, String>();
		for (Entry<String, ModificationEntry> entry : modifications1.entrySet()) {
			String filterId = entry.getKey();
			if (!processedIds.contains(filterId)) {
				ModificationEntry me = entry.getValue();
				if (me.getModificationType() == ModificationType.DRAG_FROM) {
					String filterIdDnd = null;
					if (filterId.indexOf("_") > 0) {
						filterIdDnd = filterId.split("_")[0];
					} 
					else {
						filterIdDnd = filterId + "_dnd";
					}
					if (modifications1.containsKey(filterIdDnd) 
						&& modifications2.containsKey(filterId)
						&& modifications2.containsKey(filterIdDnd)) {
						if (modifications2.get(filterId).getModificationType() == ModificationType.DRAG_TO) {
							Filter dragFilter = getFilterByIdFromMultiFilter(multiFilter.getSubClause().getFilters(),
									filterId);
							dragFilter.setFilterId(filterIdDnd);
							idsToBeModified.put(filterId, filterIdDnd);
						}
						Filter dndFilter = getFilterByIdFromMultiFilter(multiFilter.getSubClause().getFilters(),
								filterIdDnd);
						if (dndFilter != null) {
							dndFilter.setFilterId(filterIdDnd + "_local");
							idsToBeModified.put(filterIdDnd, filterIdDnd + "_local");	
						}
					}
					processedIds.add(filterId);
					processedIds.add(filterIdDnd);
				} 
			}
		}
		for (Entry<String, String> entry : idsToBeModified.entrySet()) {
			String currentFilterId = entry.getKey();
			String newFitlerId = entry.getValue();
			ModificationEntry me = modifications1.get(currentFilterId);
			modifications1.remove(currentFilterId);
			modifications1.put(newFitlerId, me);
		}
	}
	
	private static Filter getFilterByIdFromMultiFilter(List<Filter> filterList, String id) {
		Filter filter = null;
		for (int i = 0; i < filterList.size(); i++ ) {
			Filter currentFilter = filterList.get(i);
			if (currentFilter.getFilterId().equals(id)) {
			    filter = currentFilter;
			}
			else if (currentFilter instanceof MultiFilter) {
				MultiFilter multifilter = (MultiFilter) currentFilter;
				filter = getFilterByIdFromMultiFilter(multifilter.getSubClause().getFilters(), id);
			}
			if (filter != null) {
				break;
			}
		}
		return filter;
	}
	/**
	 * Merges the local-DIFF and server side DIFF into <code>mergedModifications</code>.
	 * @param modifications_i1
	 * @param modifications_i2
	 * @param mergedModifications
	 */
	private static void mergeFilterDiffs(
			Map<String, ModificationEntry> modifications_i1,
			Map<String, ModificationEntry> modifications_i2,
			Map<String, ModificationEntry> mergedModifications) {
		
		for (Entry<String, ModificationEntry> entry : modifications_i1.entrySet()) {//All local changes
			final String filterId = entry.getKey();
			ModificationEntry modificationEntry = entry.getValue();
			if (modificationEntry.getModificationType() == ModificationType.MODIFIED) {//MODIFIED on local
				ModificationEntry serverModificationEntry = modifications_i2.get(filterId);
				if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.MODIFIED) {
					//modified locally and on server
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, ModificationType.MODIFIED, ModificationType.MODIFIED);
					mergeEntry.setMessage("Filter Modified Locally and on Server.");
					mergeEntry.setBaseVersion(modificationEntry.getPreviousValue());
					mergeEntry.setBaseVersionObj(modificationEntry.getPreviousValueObj());
					mergeEntry.setLocalVersion(modificationEntry.getCurrentValue());
					mergeEntry.setLocalVersionObj(modificationEntry.getCurrentValueObj());
					mergedModifications.put(filterId, mergeEntry);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DELETED) {
					//modified locally, deleted on server
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.MODIFIED, ModificationType.DELETED);
					mergeEntry.setMessage("Filter Modified Locally, Deleted on Server.");
					mergeEntry.setLocalVersion(modificationEntry.getCurrentValue());
					mergeEntry.setLocalVersionObj(modificationEntry.getCurrentValueObj());
					mergeEntry.setBaseVersion(modificationEntry.getPreviousValue());
					mergeEntry.setBaseVersionObj(modificationEntry.getPreviousValueObj());
					mergedModifications.put(filterId, mergeEntry);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DRAG_FROM) {
					//deleted locally, DnD on server
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DRAG_FROM, ModificationType.MODIFIED, ModificationType.DRAG_FROM);
					//mergeEntry.setMessage("Filter Modified Locally and Dragged on Server.");
					mergeEntry.setMessage("Filter Modified Locally, Deleted on Server.");
					mergeEntry.setBaseVersion(modificationEntry.getPreviousValue());
					mergeEntry.setBaseVersionObj(modificationEntry.getPreviousValueObj());
					mergeEntry.setLocalVersion(modificationEntry.getCurrentValue());
					mergeEntry.setLocalVersionObj(modificationEntry.getCurrentValueObj());
					mergedModifications.put(filterId, mergeEntry);
					String filterIdDnd = null;
					if (filterId.indexOf("_") > 0) {
						filterIdDnd = filterId.split("_")[0];
					}
					else {
						filterIdDnd = filterId + "_dnd";
					}
					MergedDiffModificationEntry mergeEntryDnd = new MergedDiffModificationEntry(ModificationType.DRAG_TO, ModificationType.MODIFIED, ModificationType.DRAG_TO);
					//mergeEntryDnd.setMessage("Filter Modified Locally and Dropped on Server.");
					mergeEntry.setMessage("Filter Modified Locally, Added on Server.");
					mergeEntryDnd.setBaseVersion(modificationEntry.getPreviousValue());
					mergeEntryDnd.setBaseVersionObj(modificationEntry.getPreviousValueObj());
					mergeEntryDnd.setLocalVersion(modificationEntry.getCurrentValue());
					mergeEntryDnd.setLocalVersionObj(modificationEntry.getCurrentValueObj());
					mergedModifications.put(filterIdDnd, mergeEntryDnd);
					modifications_i2.remove(filterIdDnd);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DRAG_TO) {
					//deleted locally, DnD on server
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DRAG_TO, ModificationType.MODIFIED, ModificationType.DRAG_FROM);
					mergeEntry.setMessage("Filter Modified Locally, Added on Server.");
					mergedModifications.put(filterId, mergeEntry);
					mergeEntry.setBaseVersion(modificationEntry.getPreviousValue());
					mergeEntry.setBaseVersionObj(modificationEntry.getPreviousValueObj());
					mergeEntry.setLocalVersion(modificationEntry.getCurrentValue());
					mergeEntry.setLocalVersionObj(modificationEntry.getCurrentValueObj());
					String filterIdDnd = null;
					if (filterId.indexOf("_") > 0) {
						filterIdDnd = filterId.split("_")[0];
					}
					else {
						filterIdDnd = filterId + "_dnd";
					}
					MergedDiffModificationEntry mergeEntryDnd = new MergedDiffModificationEntry(ModificationType.DRAG_FROM, ModificationType.MODIFIED, ModificationType.DRAG_FROM);
					mergeEntryDnd.setMessage("Filter Modified locally, Deleted on Server.");
					mergedModifications.put(filterIdDnd, mergeEntryDnd);
					mergeEntryDnd.setBaseVersion(modificationEntry.getPreviousValue());
					mergeEntryDnd.setBaseVersionObj(modificationEntry.getPreviousValueObj());
					mergeEntryDnd.setLocalVersion(modificationEntry.getCurrentValue());
					mergeEntryDnd.setLocalVersionObj(modificationEntry.getCurrentValueObj());
					modifications_i2.remove(filterIdDnd);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DND) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DND, ModificationType.MODIFIED, ModificationType.DND);
					//mergeEntry.setMessage("Filter Modified locally and Dragged & Dropped on Server.");
					mergeEntry.setMessage("Filter Modified locally and Reordered on Server.");
					mergedModifications.put(filterId, mergeEntry);
					mergeEntry.setBaseVersion(modificationEntry.getPreviousValue());
					mergeEntry.setBaseVersionObj(modificationEntry.getPreviousValueObj());
					mergeEntry.setLocalVersion(modificationEntry.getCurrentValue());
					mergeEntry.setLocalVersionObj(modificationEntry.getCurrentValueObj());
				}
				else {
					//modified locally
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, ModificationType.MODIFIED, null);
					mergeEntry.setMessage("Filter Modified Locally.");
					mergeEntry.setBaseVersion(modificationEntry.getPreviousValue());
					mergeEntry.setBaseVersionObj(modificationEntry.getPreviousValueObj());
					mergeEntry.setLocalVersion(modificationEntry.getCurrentValue());
					mergeEntry.setLocalVersionObj(modificationEntry.getCurrentValueObj());
					mergedModifications.put(filterId, mergeEntry);
				}
			}
			else if (modificationEntry.getModificationType() == ModificationType.ADDED) {//ADDED on local
				//added locally
				MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.ADDED, ModificationType.ADDED, null);
				mergeEntry.setMessage("Filter Added Locally.");
				mergedModifications.put(filterId, mergeEntry);
			}
			else if (modificationEntry.getModificationType() == ModificationType.DELETED) {//DELETED on local
				ModificationEntry serverModificationEntry = modifications_i2.get(filterId);
				if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DELETED) {
					//deleted locally and on server
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, ModificationType.DELETED);
					mergeEntry.setMessage("Filter deleted locally and on server.");
					mergedModifications.put(filterId, mergeEntry);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.MODIFIED) {
					//deleted locally, modified on server
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, ModificationType.MODIFIED);//TODO: Showing as DELETED if filter is deleted in either of the versions.
					mergeEntry.setMessage("Filter Deleted Locally, Modified on Server.");
					mergeEntry.setBaseVersion(serverModificationEntry.getPreviousValue());
					mergeEntry.setBaseVersionObj(serverModificationEntry.getPreviousValueObj());
					mergedModifications.put(filterId, mergeEntry);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DRAG_FROM) {
					//deleted locally, DnD on server
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, ModificationType.DRAG_FROM);
					//mergeEntry.setMessage("Filter deleted locally and dragged on server.");
					mergeEntry.setMessage("Filter deleted locally and on server.");
					mergedModifications.put(filterId, mergeEntry);
					String filterIdDnd = null;
					if (filterId.indexOf("_") > 0) {
						filterIdDnd = filterId.split("_")[0];
					}
					else {
						filterIdDnd = filterId + "_dnd";
					}
					MergedDiffModificationEntry mergeEntryDnd = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, ModificationType.DRAG_TO);
					//mergeEntry.setMessage("Filter deleted locally and dropped on server.");
					mergeEntry.setMessage("Filter deleted locally and added on server.");
					mergedModifications.put(filterIdDnd, mergeEntryDnd);
					modifications_i2.remove(filterIdDnd);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DRAG_TO) {
					//deleted locally, DnD on server
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, ModificationType.DRAG_TO);
					//mergeEntry.setMessage("Filter deleted locally and dropped on server.");
					mergeEntry.setMessage("Filter deleted locally and added on server.");
					mergedModifications.put(filterId, mergeEntry);
					String filterIdDnd = null;
					if (filterId.indexOf("_") > 0) {
						filterIdDnd = filterId.split("_")[0];
					}
					else {
						filterIdDnd = filterId + "_dnd";
					}
					MergedDiffModificationEntry mergeEntryDnd = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, ModificationType.DRAG_FROM);
					//mergeEntry.setMessage("Filter deleted locally and dragged on server.");
					mergeEntry.setMessage("Filter deleted locally and on server.");
					mergedModifications.put(filterIdDnd, mergeEntryDnd);
					modifications_i2.remove(filterIdDnd);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DND) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, ModificationType.DND);
					//mergeEntry.setMessage("Filter deleted locally and dragged & dropped on server.");
					mergeEntry.setMessage("Filter deleted locally and reordered on server.");
					mergedModifications.put(filterId, mergeEntry);
				}
				else {
					//deleted locally
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DELETED, null);
					mergeEntry.setMessage("Filter deleted locally.");
					mergedModifications.put(filterId, mergeEntry);
				}
			}
			else if (modificationEntry.getModificationType() == ModificationType.DRAG_FROM ) {
				String filterId1 = null;
				String filterId2 = null;
				if (filterId.indexOf("_") > 0) {
					filterId1 = filterId.split("_")[0];
					filterId2 = filterId;
				}
				else {
					filterId1 = filterId;
					filterId2 = filterId + "_dnd";
				}
				ModificationEntry serverModificationEntry = modifications_i2.get(filterId1);
				ModificationEntry serverModificationEntryDnd = modifications_i2.get(filterId2); 
				if ((serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DRAG_FROM) 
				 || (serverModificationEntryDnd != null && serverModificationEntryDnd.getModificationType() == ModificationType.DRAG_FROM)) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DRAG_FROM, ModificationType.DRAG_FROM, ModificationType.DRAG_FROM);
					//mergeEntry.setMessage("Filter dragged locally and on server");
					mergeEntry.setMessage("Filter deleted locally and  on server.");
					mergedModifications.put(filterId, mergeEntry);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DELETED) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DRAG_FROM, ModificationType.DELETED);
					//mergeEntry.setMessage("Filter is deleted on server, and dragged locally");
					mergeEntry.setMessage("Filter deleted locally and  on server.");
					mergedModifications.put(filterId, mergeEntry);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.MODIFIED) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DRAG_FROM, ModificationType.DRAG_FROM, ModificationType.MODIFIED);
					//mergeEntry.setMessage("Filter is modified on server, and dragged locally");
					mergeEntry.setMessage("Filter modified locally, deleted on server.");
					mergedModifications.put(filterId, mergeEntry);
				}
				else {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DRAG_FROM, ModificationType.DRAG_FROM, null);
					//mergeEntry.setMessage("Filter is dragged locally");
					mergeEntry.setMessage("Filter is deleted locally");
					mergedModifications.put(filterId, mergeEntry);
				}
				/*if (filterId.indexOf("_") > 0) {
					modifications_i2.remove(filterId1);
				}
				else {
					modifications_i2.remove(filterId2);
				}*/
			}
			else if (modificationEntry.getModificationType() == ModificationType.DRAG_TO ) {
				String filterId1 = null;
				String filterId2 = null;
				if (filterId.indexOf("_") > 0) {
					filterId1 = filterId.split("_")[0];
					filterId2 = filterId;
				}
				else {
					filterId1 = filterId;
					filterId2 = filterId + "_dnd";
				}
				ModificationEntry serverModificationEntry = modifications_i2.get(filterId1);
				ModificationEntry serverModificationEntryDnd = modifications_i2.get(filterId2); 
				if ((serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DRAG_TO) 
				 || (serverModificationEntryDnd != null && serverModificationEntryDnd.getModificationType() == ModificationType.DRAG_TO)) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DRAG_TO, ModificationType.DRAG_TO, ModificationType.DRAG_TO);
					//mergeEntry.setMessage("Filter dropped locally and on server");
					mergeEntry.setMessage("Filter added locally and on server");
					mergedModifications.put(filterId, mergeEntry);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DELETED) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DRAG_TO, ModificationType.DELETED);
					//mergeEntry.setMessage("Filter is deleted on server, and dropped locally");
					mergeEntry.setMessage("Filter is added locally, and deleted on server");
					mergedModifications.put(filterId, mergeEntry);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.MODIFIED) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DRAG_TO, ModificationType.DRAG_TO, ModificationType.MODIFIED);
					//mergeEntry.setMessage("Filter is modified on server, and dropped locally");
					mergeEntry.setMessage("Filter is modified on server, and added locally");
					mergedModifications.put(filterId, mergeEntry);
				}
				else {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DRAG_TO, ModificationType.DRAG_TO, null);
					//mergeEntry.setMessage("Filter is dropped locally");
					mergeEntry.setMessage("Filter is added locally");
					mergedModifications.put(filterId, mergeEntry);
				}
				/*if (filterId.indexOf("_") > 0) {
					modifications_i2.remove(filterId1);
				}
				else {
					modifications_i2.remove(filterId2);
				}*/
			}
			else if (modificationEntry.getModificationType() == ModificationType.DND) {
				ModificationEntry serverModificationEntry = modifications_i2.get(filterId);
				if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DND) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DND, ModificationType.DND, ModificationType.DND);
					//mergeEntry.setMessage("Filter dragged and dropped in the same filter locally and on server");
					mergeEntry.setMessage("Filter reordered in the same multi filter locally and on server");
					mergedModifications.put(filterId, mergeEntry);
				}
				else if (serverModificationEntry != null && serverModificationEntry.getModificationType() == ModificationType.DELETED ) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, ModificationType.DND, ModificationType.DELETED);
					//mergeEntry.setMessage("Filter dragged and dropped in the same filter locally but deleted on server");
					mergeEntry.setMessage("Filter reordered in the same multi filter locally but deleted on server");
					mergedModifications.put(filterId, mergeEntry);
				}
				else if (serverModificationEntry !=null && serverModificationEntry.getModificationType() == ModificationType.MODIFIED) {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DND, ModificationType.DND, ModificationType.MODIFIED);
					//mergeEntry.setMessage("Filter dragged and dropped in the same filter locally but modified on server");
					mergeEntry.setMessage("Filter reordered in the same multi filter locally but modified on server");
					mergedModifications.put(filterId, mergeEntry);
				}
				else {
					MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DND, ModificationType.DND, null);
					//mergeEntry.setMessage("Filter dragged and dropped in the same filter locally");
					mergeEntry.setMessage("Filter reordered in the same multi filter locally");
					mergedModifications.put(filterId, mergeEntry);
				}
			}
			modifications_i2.remove(filterId);
		}
		
		for (Entry<String, ModificationEntry> entry : modifications_i2.entrySet()) {//All server side changes
			String filterId = entry.getKey();
			ModificationEntry modificationEntry = entry.getValue();
			if (modificationEntry.getModificationType() == ModificationType.MODIFIED) {//MODIFIED on server
				MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.MODIFIED, null, ModificationType.MODIFIED);
				mergeEntry.setMessage("Filter Modified on Server.");
				mergeEntry.setBaseVersion(modificationEntry.getPreviousValue());
				mergeEntry.setBaseVersionObj(modificationEntry.getPreviousValueObj());
				mergedModifications.put(filterId, mergeEntry);
			}
			else if (modificationEntry.getModificationType() == ModificationType.ADDED) {
				MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.ADDED, null, ModificationType.ADDED);
				mergeEntry.setMessage("Filter Added on Server.");
				mergedModifications.put(filterId, mergeEntry);
			}
			else if (modificationEntry.getModificationType() == ModificationType.DELETED) {
				MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DELETED, null, ModificationType.DELETED);
				mergeEntry.setMessage("Filter Deleted on Server.");
				mergedModifications.put(filterId, mergeEntry);
			}
			else if (modificationEntry.getModificationType() == ModificationType.DRAG_FROM) {
				MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DRAG_FROM, null, ModificationType.DRAG_FROM);
				//mergeEntry.setMessage("Filter Dragged on Server.");
				mergeEntry.setMessage("Filter Deleted on Server.");
				mergedModifications.put(filterId, mergeEntry);
			}
			else if (modificationEntry.getModificationType() == ModificationType.DRAG_TO) {
				MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DRAG_TO, null, ModificationType.DRAG_TO);
				//mergeEntry.setMessage("Filter Dropped on Server.");
				mergeEntry.setMessage("Filter Added on Server.");
				mergedModifications.put(filterId, mergeEntry);
			}
			else if (modificationEntry.getModificationType() == ModificationType.DND) {
				MergedDiffModificationEntry mergeEntry = new MergedDiffModificationEntry(ModificationType.DND, null, ModificationType.DND);
				//mergeEntry.setMessage("Filter DnD in the same filter on Server.");
				mergeEntry.setMessage("Filter reordered in the same multi filter on Server.");
				mergedModifications.put(filterId, mergeEntry);
			}
		}
	}
	
	/**
	 * Processes Merged Diff for lists for filters.
	 * @param filterList_v1
	 * @param filterList_v2
	 * @param filterList_v3
	 * @param modifications
	 */
	public static void processFiltersMergedDiff(List<Filter> filterList_v1,
			List<Filter> filterList_v2, List<Filter> filterList_v3,
			Map<String, ModificationEntry> modifications) {
		
		Map<String, ModificationEntry> modifications_i1 = new LinkedHashMap<String, ModificationEntry>();
		DiffHelper.processFiltersDiff(filterList_v1, filterList_v2, modifications_i1);//Find out Local changes.
		
		Map<String, ModificationEntry> modifications_i2 = new LinkedHashMap<String, ModificationEntry>();
		DiffHelper.processFiltersDiff(filterList_v1, filterList_v3, modifications_i2);//Find out server side changes
		{
			Map<String, ModificationEntry> ignore = new LinkedHashMap<String, ModificationEntry>();
			DiffHelper.processFiltersDiff(filterList_v2, filterList_v3, ignore);//Merge all Filters into server copy ignoring modifications.
		}
		mergeFilterDiffs(modifications_i1, modifications_i2, modifications);
	}
	
	/**
	 * Creates a tooltip message for merged Diff flow.
	 * @param modificationEntries
	 * @return
	 */
	public static Canvas getTooltipCanvas(MergedDiffModificationEntry... modificationEntries) {
		Canvas canvas = null;
		if (modificationEntries.length > 0) {
			String content = getTooltipString(modificationEntries);
			if (content != null) {
				canvas = new Label(content);
				canvas.setHeight(25);
			}
		}
		return canvas;
	}
	
	/**
	 * Creates a tooltip message for merged Diff flow.
	 * @param modificationEntries
	 * @return
	 */
	public static String getTooltipString(MergedDiffModificationEntry... modificationEntries) {
		String content = null;
		if (modificationEntries.length > 0) {
			content = "<b><nobr>$MODIFICATION_MESSAGE_0$</nobr></b>"
					+ (modificationEntries.length > 1 ? "<br/><b><nobr>$MODIFICATION_MESSAGE_1$</nobr></b>" : "");
			for (int i=0; i < modificationEntries.length; i++) {
				MergedDiffModificationEntry modificationEntry = modificationEntries[i];
				content = content.replace("$MODIFICATION_MESSAGE_" + i + "$", modificationEntry.getMessage());
				if (i==0 && modificationEntry.getBaseVersion() != null || modificationEntry.getLocalVersion() != null) {
					content += "<hr/>" + (modificationEntry.getBaseVersion() != null ? "<nobr>Base copy: " + modificationEntry.getBaseVersion() + "</nobr>": "")
							+ (modificationEntry.getLocalVersion() != null ? (modificationEntry.getBaseVersion() != null ? "<br/>" : "")
									+ "<nobr>Local copy: " + modificationEntry.getLocalVersion() + "</nobr>": "");
				}
			}
		}
		return content;
	}
	
	/**
	 * Applies the DIFF related CSS styles to this filter.
	 * @param modificationEntry
	 * @param filterLayout
	 * @param showToolTip
	 */
	public static void applyDiffCSSStyle(ModificationEntry modificationEntry, HLayout filterLayout, boolean showToolTip) {
		if (modificationEntry != null) {
			switch(modificationEntry.getModificationType()) {
				//Show DIFF as faint for which no user action is needed. <cssClass>-faint
				case DRAG_TO: 
			    case ADDED: filterLayout.setStyleName("ws-diff-background-add" + (modificationEntry.isApplied() ? "-faint" : "")); break;
				case DRAG_FROM:
				case DELETED: filterLayout.setStyleName("ws-diff-background-remove"  + (modificationEntry.isApplied() ? "-faint" : "")); break;
				case DND: filterLayout.setStyleName("ws-diff-background-dnd-faint"); break;
				case MODIFIED: if (showToolTip) {
					filterLayout.setTooltip("<b>" + globalMsg.text_previousValue() + ":</b><br/><nobr>" + modificationEntry.getPreviousValue() + "</nobr>");
					filterLayout.setStyleName("ws-diff-background-modify"  + (modificationEntry.isApplied() ? "-faint" : ""));
				}
				break;
				case UNCHANGED: break;
			}
			if (modificationEntry instanceof MergedDiffModificationEntry) {
				String mergeDiffToolTip = getTooltipString((MergedDiffModificationEntry)modificationEntry);
				if (mergeDiffToolTip != null) {
					filterLayout.setTooltip(mergeDiffToolTip);
				}
			}
		}
	}
	
	/**
	 * Applies the DIFF related CSS styles to the widget.
	 * @param modificationEntry
	 * @param widget
	 */
	public static void applyDiffCSSStyle(ModificationEntry modificationEntry, Widget widget) {
		if (modificationEntry != null) {
			switch(modificationEntry.getModificationType()) {
			//Show DIFF as faint for which no user action is needed. <cssClass>-faint
			//Add/Delete not applicable for RTI view (hence for widget)
			case MODIFIED: widget.setStyleName("ws-diff-background-modify"  + (modificationEntry.isApplied() ? "-faint" : "")); break;
			default: break;
			}
		}
	}
}