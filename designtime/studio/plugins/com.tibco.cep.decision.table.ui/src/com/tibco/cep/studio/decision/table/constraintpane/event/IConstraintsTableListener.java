/**
 * 
 */
package com.tibco.cep.studio.decision.table.constraintpane.event;

/**
 * @author aathalye
 *
 */
public interface IConstraintsTableListener {
	
	/**
	 * 
	 * @param event
	 */
	void cellAdded(ConstraintsTableEvent event);
	
	/**
	 * 
	 * @param event
	 */
	void cellUpdated(ConstraintsTableEvent event);
	
	/**
	 * 
	 * @param event
	 */
	void cellRemoved(ConstraintsTableEvent event);
	
	/**
	 * 
	 * @param event
	 */
	void aliasRemoved(ConstraintsTableEvent event);
	
	/**
	 * 
	 * @param event
	 */
	void aliasRenamed(ConstraintsTableEvent event);
}
