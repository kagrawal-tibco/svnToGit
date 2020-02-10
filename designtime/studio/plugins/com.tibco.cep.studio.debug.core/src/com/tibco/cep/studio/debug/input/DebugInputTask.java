package com.tibco.cep.studio.debug.input;

import org.eclipse.debug.core.model.IDebugTarget;


public class DebugInputTask extends AbstractVmResponseTask {
    String entityURI;
    String xmlData;
    String ruleSessionURI;
    String destinationURI;

    public DebugInputTask(IDebugTarget target ,String xmlData, String entityURI, String destinationURI, String ruleSessionURI) {
    	super(target);
        this.destinationURI = destinationURI;
        this.entityURI = entityURI;
        this.ruleSessionURI = ruleSessionURI;
        this.xmlData = xmlData;
    }

    public String getDestinationURI() {
        return destinationURI;
    }

    public void setDestinationURI(String destinationURI) {
        this.destinationURI = destinationURI;
    }

    public String getEntityURI() {
        return entityURI;
    }

    public void setEntityURI(String entityURI) {
        this.entityURI = entityURI;
    }

    public String getRuleSessionURI() {
        return ruleSessionURI;
    }

    public void setRuleSessionURI(String ruleSessionURI) {
        this.ruleSessionURI = ruleSessionURI;
    }

    public String getXmlData() {
        return xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
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
				+ ((ruleSessionURI == null) ? 0 : ruleSessionURI.hashCode());
		result = prime * result + ((xmlData == null) ? 0 : xmlData.hashCode());
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
		DebugInputTask other = (DebugInputTask) obj;
		if (destinationURI == null) {
			if (other.destinationURI != null)
				return false;
		} else if (!destinationURI.equals(other.destinationURI))
			return false;
		if (entityURI == null) {
			if (other.entityURI != null)
				return false;
		} else if (!entityURI.equals(other.entityURI))
			return false;
		if (ruleSessionURI == null) {
			if (other.ruleSessionURI != null)
				return false;
		} else if (!ruleSessionURI.equals(other.ruleSessionURI))
			return false;
		if (xmlData == null) {
			if (other.xmlData != null)
				return false;
		} else if (!xmlData.equals(other.xmlData))
			return false;
		return true;
	}
	
	
    
}
