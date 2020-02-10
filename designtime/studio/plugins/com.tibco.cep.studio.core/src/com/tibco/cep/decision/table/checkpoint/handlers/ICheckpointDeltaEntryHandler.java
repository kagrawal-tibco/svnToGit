/**
 * 
 */
package com.tibco.cep.decision.table.checkpoint.handlers;

/**
 * Interface to be implemented to handle checkpoint delta entries.
 * <p>
 * This will be used whenever a checkpoint is reached.
 * </p>
 * @author aathalye
 *
 */
public interface ICheckpointDeltaEntryHandler {
	
	/**
	 * 
	 */
	void handleEntry();
}
