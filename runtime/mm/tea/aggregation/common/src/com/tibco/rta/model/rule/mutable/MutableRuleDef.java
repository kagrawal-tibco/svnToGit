package com.tibco.rta.model.rule.mutable;

import java.util.Calendar;

import com.tibco.rta.model.mutable.MutableMetadataElement;
import com.tibco.rta.model.rule.ActionDef;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.query.QueryDef;

/**
 *
 * Mutable interface useful for creating a rule definition during design time.
 *
 */
public interface MutableRuleDef extends RuleDef, MutableMetadataElement{

	/**
	 * Associate user name with this rule.
	 * @param userName
	 */
	void setUserName(String userName);

	/**
	 * Set the set condition filter. The set actions will be performed when this filter passes.
	 * @param setCondition
	 */
	void setSetCondition(QueryDef setCondition);

	/**
	 * Set the clear condition filter. The clear actions will be performed when this filter passes.
	 * @param clearCondition
	 */
	void setClearCondition(QueryDef clearCondition);

	/**
	 * Set a schedule name.
	 * @param scheduleName
	 */
	void setScheduleName(String scheduleName);

	/**
	 * Add an action to execute when the set filter passes.
	 * @param descriptor
	 */
	@Deprecated
	void addSetActionDef(ActionDef descriptor);
	
	/**
	 * Add an action to execute when the clear filter passes.
	 * @param descriptor
	 */
	@Deprecated
	void addClearActionDef(ActionDef descriptor);

	/**
	 * removes all set actions
	 * @param
	 */
	void removeAllSetActionDefs();

	/**
	 * removes all clear action
	 * @param
	 */
	void removeAllClearActionDefs();


	/**
	 * Set a version to this rule.
	 * @param version
	 */
	void setVersion(String version);

	/**
	 * Sets the creation date/time of this rule.
	 * @param createdDateTime
	 */
	void setCreatedDate(Calendar createdDateTime);

	/**
	 * Sets the modification date/time of this rule.
	 * @param modifiedDateTime
	 */
	void setModifiedDate(Calendar modifiedDateTime);

	/**
	 * Sets the description of this rule.
	 * @param description
	 */
	void setDescription(String description);
	
	void setPriority(int priority);

	/**
	 * set streaming query flag to identify the rule as streaming query
	 * @param streamingQuery
	 */
	void setAsStreamingQuery(boolean streamingQuery);
	
	/**
	 * Enable/disable a rule
	 * @param isEnabled Set to true to enable a rule, false to disable it.
	 */
	void setEnabled(boolean isEnabled);

	
	/**
     *  Marks the rule if set atleast once
     *  @param isSetOnce Set to true if set atleast once , defaults to false
     */
	void setSetOnce(boolean isSetOnce);

}
