package com.tibco.cep.bpmn.runtime.activity.tasks;

import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
/**
 * This TaskContextType enumeration
 * @since 5.2.0
 * @version 5.2.0
 * @.category public-api
 */
public enum TaskContextType {
	 /**
     * Identifies the currently running task name from the context.
     * @.category public-api
     * @since 5.2.0
     */
	NAME("Name",String.class),
	 /**
     * Identifies the <code>RuleServiceProvider</code> from the context.
     * @.category public-api
     * @since 5.2.0
     */
	RULE_SERVICE_PROVIDER("RuleServiceProvider",RuleServiceProvider.class),
	 /**
     * Identifies the <code>RuleSession</code> from the context.
     * @.category public-api
     * @since 5.2.0
     */
	RULE_SESSION("RuleSession",RuleSession.class);
	
	private String typeName;
	private Class<?> className;
	
	private TaskContextType(String s,Class<?> clzName) {
		this.typeName = s;
		this.className = clzName;
	}
	
	public String getTypeName() { return this.typeName; }
	public Class<?> getClassType() { return this.className; }

}
