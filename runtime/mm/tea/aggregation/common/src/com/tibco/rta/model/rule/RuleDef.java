package com.tibco.rta.model.rule;

import java.util.Calendar;
import java.util.Collection;

import com.tibco.rta.model.MetadataElement;
import com.tibco.rta.query.QueryDef;

/**
 * 
 * A design time representation of a rule.
 *
 */

public interface RuleDef extends MetadataElement {
	
	/**
	 * Get the user name who created this rule.
	 * @return the user name who created this rule.
	 */
	String getUserName();
	
	/**
	 * Get the set condition associated with this rule.
	 * @return the set condition associated with this rule.
	 */
	QueryDef getSetCondition();
	
	/**
	 * Get the clear condition associated with this rule.
	 * 
	 * @return the clear condition associated with this rule.
	 */
	QueryDef getClearCondition();
	
	/**
	 * Get the schedule associated with this rule.
	 * @return the associated schedule.
	 */
	String getScheduleName();
	
	/**
	 * Get the set of actions associated with the set condition. When the set condition 
	 * matches, these actions will be executed.
	 * @return the set of actions associates with the set condition.
	 *
	 */
	Collection<ActionDef> getSetActionDefs();
	
//	ActionDef getSetActionDef(String name);
	
	/**
	 * Get the set of clear actions associated with the clear condition. When the clear condition 
	 * matches, these actions will be executed.
	 * @return the set of actions associates with the clear condition.
	 *
	 */
	
	Collection<ActionDef> getClearActionDefs();
	
//	ActionDef getClearActionDef();
	
	/**
	 * Version associated with the rule.
	 * @return version of 
	 */
	String getVersion();
	
	/**
	 * Date/time when the rule was first created.
	 * @return
	 */
	
	Calendar getCreatedDate();
	
	/**
	 * Date/time when the rule was updated.
	 * @return
	 */
	Calendar getModifiedDate();
	
	/**
	 * Get description of 
	 * @return
	 */	
	String getDescription();

    /**
     * Priority associated rule
     * @return
     */
    int getPriority();
    
    /**
     *  Type of rule 
     *  @return whether it is streaming rule 
     */
    boolean isStreamingQuery();
    
    /*
     * Enable/disable Rule
     * @return whether rule is enabled/disabled
     */
    boolean isEnabled();
    
    /**
     *  Denotes the rule is set once or not 
     *  @return whether the rule is set atleast once or not 
     */
	boolean isSetOnce();
	
}
