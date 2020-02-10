package com.tibco.tea.agent.be.util;

public enum BEEntityDimensions {

    app("Cluster",1,"apphealth"),
    pu("Processing Unit",0,"na"),
    instance("Processing Unit Instance",2,"instancehealth"),
    agentType("Agent type",0,"na"),
    agent("Agent",3,"instancehealth"),
	inference("Agent",3,"instancehealth"),
	cache("Agent",3,"instancehealth");

    private final String name;
    private final int entityLevel;
    private final String entityHealthParam;

    private BEEntityDimensions(String name, int entityLevel, String entityHealthParam) {
        this.name = name;
        this.entityLevel = entityLevel;
        this.entityHealthParam = entityHealthParam;
    }

	public String getName() {
		return name;
	}

	public int getEntityLevel() {
		return entityLevel;
	}

	public String getEntityHealthParam() {
		return entityHealthParam;
	}

    
}
