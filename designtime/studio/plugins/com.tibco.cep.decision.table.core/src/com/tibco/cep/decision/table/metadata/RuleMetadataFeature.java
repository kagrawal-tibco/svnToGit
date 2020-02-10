/**
 * 
 */
package com.tibco.cep.decision.table.metadata;

import com.tibco.cep.decision.table.command.IMetadataFeature;

/**
 * @author aathalye
 *
 */
public enum RuleMetadataFeature implements IMetadataFeature {
	
	/**
	 * Single row execution flag
	 */
	DESCRIPTION("Description"),
	
	/**
	 * Row Priority
	 */
	PRIORITY("Priority");
	
	private static final RuleMetadataFeature[] VALUES_ARRAY =
		new RuleMetadataFeature[] {
			DESCRIPTION,
			PRIORITY
		};
	
	private RuleMetadataFeature(String featureName) {
		this.featureName = featureName;
	}
	
	private final String featureName;
	
	/**
	 * 
	 * @return
	 */
	public String getFeatureName() {
        return featureName;
    }

    public String toString() {
        return featureName;
    }
    
    /**
     * 
     * @param featureName
     * @return
     */
    public static RuleMetadataFeature getByFeatureName(String featureName) {
		for (RuleMetadataFeature metadataFeature : VALUES_ARRAY) {
			if (metadataFeature.getFeatureName().equals(featureName)) {
				return metadataFeature;
			}
		}
		return null;
	}
}
