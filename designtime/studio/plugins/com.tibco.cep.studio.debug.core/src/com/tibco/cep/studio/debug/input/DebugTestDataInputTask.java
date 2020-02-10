package com.tibco.cep.studio.debug.input;

import org.eclipse.debug.core.model.IDebugTarget;

/**
 * 
 * @author sasahoo
 *
 */
public class DebugTestDataInputTask extends AbstractVmResponseTask {
    
	String[] entityURI;
    String[] xmlData;
    String ruleSessionName;
    String[] destinationURI;
    String testerSessionName;


	/**
	 * @param target
	 * @param xmlData
	 * @param entityURI
	 * @param destinationURI
	 * @param ruleSessionName
	 * @param testerSessionName
	 */
	public DebugTestDataInputTask(IDebugTarget target,
			                      String[] xmlData, 
			                      String[] entityURI, 
			                      String[] destinationURI, 
			                      String ruleSessionName, 
			                      String testerSessionName) {
    	super(target);
        this.destinationURI = destinationURI;
        this.entityURI = entityURI;
        this.ruleSessionName = ruleSessionName;
        this.xmlData = xmlData;
        this.testerSessionName = testerSessionName;
    }

    public String[] getDestinationURI() {
        return destinationURI;
    }

    public void setDestinationURI(String[] destinationURI) {
        this.destinationURI = destinationURI;
    }

    public String[] getEntityURI() {
        return entityURI;
    }

    public void setEntityURI(String[] entityURI) {
        this.entityURI = entityURI;
    }

    public String getRuleSessionName() {
        return ruleSessionName;
    }

    public void setRuleSessionName(String ruleSessionName) {
        this.ruleSessionName = ruleSessionName;
    }

    public String[] getXmlData() {
        return xmlData;
    }

    public void setXmlData(String[] xmlData) {
        this.xmlData = xmlData;
    }

    public String getTesterSessionName() {
		return testerSessionName;
	}

	public void setTesterSessionName(String testerSessionName) {
		this.testerSessionName = testerSessionName;
	}
    
    @Override
    public boolean hasResponse() {
    	return true;
    }
    
    @Override
    public Object getKey() {
    	return hashCode();
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destinationURI == null) ? 0 : destinationURI.hashCode());
		result = prime * result
				+ ((entityURI == null) ? 0 : entityURI.hashCode());
		result = prime * result
				+ ((ruleSessionName == null) ? 0 : ruleSessionName.hashCode());
		result = prime * result + ((xmlData == null) ? 0 : xmlData.hashCode());
		result = prime * result
				+ ((testerSessionName == null) ? 0 : testerSessionName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DebugTestDataInputTask other = (DebugTestDataInputTask) obj;
		if (destinationURI == null) {
			if (other.destinationURI != null)
				return false;
		} else if (destinationURI != other.destinationURI)
			return false;
		if (entityURI == null) {
			if (other.entityURI != null)
				return false;
		} else if (entityURI != other.entityURI)
			return false;
		if (ruleSessionName == null) {
			if (other.ruleSessionName != null)
				return false;
		} else if (!ruleSessionName.equals(other.ruleSessionName))
			return false;
		
		if (testerSessionName == null) {
			if (other.testerSessionName != null)
				return false;
		} else if (!testerSessionName.equals(other.testerSessionName))
			return false;
		
		if (xmlData == null) {
			if (other.xmlData != null)
				return false;
		} else if (xmlData != other.xmlData)
			return false;
		return true;
	}
	
	
    
}