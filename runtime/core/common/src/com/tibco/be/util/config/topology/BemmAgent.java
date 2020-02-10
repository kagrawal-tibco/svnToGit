/**
 * 
 */
package com.tibco.be.util.config.topology;

/**
 * @author Nick
 *
 */
public class BemmAgent {
	
	private String fqname;
	private String type;
	private String agentGroupName;
	
	public BemmAgent(String type, String agentGroupName){
		this.type = type;
		this.agentGroupName = agentGroupName;
		this.fqname = agentGroupName;
	}

	public String getFqName() {
		return fqname;
	}

    /**
     * Builds a tokenized string of agent fqname and agent type. The token is the character ','
     * @return tokenized string of agent fqname and agent type.
     * */
    public String getFqNameWithType() {
		return fqname + "," + type;
	}

	public void setFqName(String prefix) {
		this.fqname = prefix + "/" + this.agentGroupName;
	}

	public String getType() {
		return type;
	}

	public String getAgentGroupName() {
		return agentGroupName;
	}




}
