package com.tibco.cep.decision.table.metadata;

import com.tibco.cep.decision.table.command.IMetadataFeature;

/**
 * @author aathalye
 *
 */
public enum DecisionTableMetadataFeature implements IMetadataFeature {
	
	/**
	 * Effective Date property
	 */
	EFFECTIVE_DATE("EffectiveDate"),
	
	/**
	 * Expiry Date Property
	 */
	EXPIRY_DATE("ExpiryDate"),
	
	/**
	 * Single row execution flag
	 */
	SINGLE_ROW_EXECUTION("SingleRowExecution"),
	
	/**
	 * Treat all datetime strings as UTC
	 */
	TREAT_TIMESTAMP_AS_UTC("TimestampAsUTC"),
	
	/**
	 * Row Priority
	 */
	PRIORITY("Priority");
	
	private static final DecisionTableMetadataFeature[] VALUES_ARRAY =
		new DecisionTableMetadataFeature[] {
			EFFECTIVE_DATE,
			EXPIRY_DATE,
			SINGLE_ROW_EXECUTION,
			PRIORITY,
			TREAT_TIMESTAMP_AS_UTC
		};
	
	private DecisionTableMetadataFeature(String featureName) {
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
    public static DecisionTableMetadataFeature getByFeatureName(String featureName) {
		for (DecisionTableMetadataFeature metadataFeature : VALUES_ARRAY) {
			if (metadataFeature.getFeatureName().equals(featureName)) {
				return metadataFeature;
			}
		}
		return null;
	}
}
