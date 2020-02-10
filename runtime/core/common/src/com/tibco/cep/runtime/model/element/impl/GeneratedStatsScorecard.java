package com.tibco.cep.runtime.model.element.impl;

import com.tibco.cep.runtime.model.element.impl.property.PropertyImpl;

abstract public class GeneratedStatsScorecard extends GeneratedConceptImpl
{
	// \u2603 is this character. ☃ Compiler encoding is not guaranteed so use the escape.
	public static final String STATS_CONFIG_STRING = "\u2603STATS\u2603";
	
	protected GeneratedStatsScorecard() {
    }

    protected GeneratedStatsScorecard(long _id) {
        super(_id);
    }

    protected GeneratedStatsScorecard(long id, String extId) {
        super(id, extId);
    }

    protected GeneratedStatsScorecard(String extId) {
        super(extId);
    }
	
	public boolean modifyConcept(PropertyImpl property) {
		return false;
	}
	
	public void checkSession() {}
}
