/**
 * 
 */
package com.tibco.cep.decision.table.command;

/**
 * To be implemented by all enums which represent
 * various features of decision table and its children's metadata.
 * @author aathalye
 *
 */
public interface IMetadataFeature {
	
	/**
	 * Get the feature name as is in the metadata.
	 * @return
	 */
	String getFeatureName();
}
