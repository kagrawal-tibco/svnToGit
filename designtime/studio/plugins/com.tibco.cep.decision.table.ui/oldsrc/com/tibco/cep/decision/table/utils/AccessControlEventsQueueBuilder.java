/**
 * 
 */
package com.tibco.cep.decision.table.utils;

import java.util.List;
import java.util.StringTokenizer;

import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decisionproject.acl.AccessControlCellData;
import com.tibco.cep.decisionproject.acl.event.AccessControlEvent;
import com.tibco.cep.decisionproject.acl.event.AccessControlEventListener;
import com.tibco.cep.decisionproject.ontology.Implementation;

/**
 * @author aathalye
 * 
 */
public class AccessControlEventsQueueBuilder {
	
	
	/**
	 * Builds the access control event queue upon loading up of a Table when a
	 * project is opened.
	 * 
	 * @param evtListener
	 * @param impl
	 */
	public static <I extends Implementation> void buildAccessEvents(
			final AccessControlEventListener<AccessControlEvent, I> evtListener,
			final I impl) {
		if (evtListener == null) {
			throw new IllegalArgumentException("Event Listener not initialized");
		}
		if (impl == null) {
			throw new IllegalArgumentException(
					"The implementation cannot be null");
		}
		if (!impl.isModified()) {
			return;
		}
		// Build events
		if (!(impl instanceof Table)) {
			return;
		}
		Table table = (Table) impl;
//		TRACE.logDebug(CLASS, 
//				       "Building Access Events on Table {0}", table.getName());
		TableRuleSet dtSet = table.getDecisionTable();
		buildAccessEvents(evtListener, dtSet);
		TableRuleSet etSet = table.getExceptionTable();
		buildAccessEvents(evtListener, etSet);
	}

	/**
	 * Build Access Events for the passed {@link TableRuleSet}.
	 * <p>
	 * This cane be a
	 * <li>
	 * DecisionTable
	 * </li>
	 * <li>
	 * Exception Table
	 * </li>
	 * @param evtListener
	 * @param trSet
	 */
	private static <I extends Implementation> void buildAccessEvents(final AccessControlEventListener<AccessControlEvent, I> evtListener,
			                              final TableRuleSet trSet) {
		if (trSet == null) return;
		// Get the metadata
		MetaData metaData = trSet.getMd();
		Columns columns = trSet.getColumns();
//		TRACE.logDebug(CLASS, 
//			       "MetadaData obtained on Table {0}", metaData);
		// get the modified rules indexes from it
		if (metaData != null) {
			final Property property = metaData.search("modifiedRuleIndexList");
//			TRACE.logDebug(CLASS, 
//				       "Rule index modified property {0}", property);
			if (property == null) {
				return;
			}
			String value = property.getValue();
//			TRACE.logDebug(CLASS, 
//				       "Rule index modified property value {0}", value);
			if (value != null) {
				//Take care of all rules modified case
				if (value.intern() == "-1") {
					buildAllRulesAccessEvents(evtListener, trSet);
				} else {
					//Take care of selected indexes modification
					StringTokenizer sToken = new StringTokenizer(value, ",");
					final List<TableRule> tRules = trSet.getRule();
					while (sToken.hasMoreTokens()) {
	                    //get each index separately
	                    String token = sToken.nextToken();
	                    int index = Integer.parseInt(token);
//	                    TRACE.logDebug(CLASS, 
//	     				       "Rule index value {0}", index);
	                    //Fetch Table rule at this index
	                    // fix ,is some how Modified index is not populated properly
	                    if (tRules.size() > index){
	                    	TableRule ruleAtIndex = tRules.get(index);
	                    
//	                    	TRACE.logDebug(CLASS, 
//		     				       	"Table Rule obtained at index {0} is {1}", 
//		     				       	index, ruleAtIndex);
	                    	buildAccessEvents(evtListener, ruleAtIndex, columns);
	                    }
					}
				}
			}
		}
	}

	/**
	 * @param sToken
	 * @param input
	 * @param evtListener
	 */
	private static <I extends Implementation> void populateModifiablesList(final StringTokenizer sToken,
												final List<TableRuleVariable> input,
												final AccessControlEventListener<AccessControlEvent, I> evtListener,
												final Columns columns) {

		while (sToken.hasMoreTokens()) {
			String token = sToken.nextToken();
			int index = Integer.parseInt(token);
			// Fetch Table rule at this index
			// fix if modified list is not populated properly
			if (input.size() > index) {
				TableRuleVariable trvAtIndex = input.get(index);
				// Get the column matching the id of this TRV
				String columnId = trvAtIndex.getColId();
				Column associatedCol = columns.search(columnId);
				AccessControlCellData acd = new AccessControlCellData(
						associatedCol, trvAtIndex);
				AccessControlEvent event = new AccessControlEvent(acd);
				String trvId = trvAtIndex.getId();
//				TRACE.logDebug(CLASS,
//						"Building Access Control event for TableRule Variable"
//								+ " with id {0}", trvId);
				event.setUid(trvId);
				evtListener.enqueueEvent(event);
			}
		}
	}

	/**
	 * Build Access Events for all {@link TableRule} in this 
	 * {@link TableRuleSet}
	 * @param evtListener
	 * @param trSet
	 */
	private static <I extends Implementation> void buildAllRulesAccessEvents(final AccessControlEventListener<AccessControlEvent, I> evtListener,
			                                      final TableRuleSet trSet) {
		// Get all the rules
//		TRACE.logDebug(CLASS, 
//			       "Building access events for all rules");
		final List<TableRule> trs = trSet.getRule();
		Columns columns = trSet.getColumns();
		for (TableRule tr : trs) {
			buildAccessEvents(evtListener, tr, columns);
		}
	}
	
	/**
	 * Build access events for a single {@link TableRule}
	 * @param evtListener
	 * @param tr
	 */
	private static <I extends Implementation> void buildAccessEvents(final AccessControlEventListener<AccessControlEvent, I> evtListener,
            							  final TableRule tr, 
            							  final Columns columns) {
		List<TableRuleVariable> conditions = tr.getCondition();
		List<TableRuleVariable> actions = tr.getAction();
		// Check its metadata
		MetaData md = tr.getMd();
//		TRACE.logDebug(CLASS, 
//	                  "Metadata obtained for rule {0} is {1}",
//	                  tr, md);
		if (md != null) {
			Property condproperty = md.search("modifiedConditionIndexList");
//			TRACE.logDebug(CLASS, 
//				       "Condition List modified property obtained {0}", condproperty);
			if (condproperty != null && condproperty.getValue() != null) {
				String v1 = condproperty.getValue();
//				TRACE.logDebug(CLASS, 
//					       "Value of condition list property {0}", v1);
				StringTokenizer condToken = new StringTokenizer(v1, ",");
				populateModifiablesList(condToken, conditions, evtListener, columns);
			}
			
			Property actionproperty = md.search("modifiedActionIndexList");
//			TRACE.logDebug(CLASS, 
//				       "Action List modified property obtained {0}", actionproperty);
			if (actionproperty != null) {
				String v2 = actionproperty.getValue();
//				TRACE.logDebug(CLASS, 
//					       "Value of Action list property {0}", v2);
				StringTokenizer actionToken = new StringTokenizer(v2, ",");
				populateModifiablesList(actionToken, actions, evtListener, columns);
			}
		}
	}
}
