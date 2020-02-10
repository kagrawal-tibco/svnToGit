package com.tibco.cep.studio.debug.core.model.impl;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.tibco.cep.studio.debug.core.model.IResourcePosition;
import com.tibco.xml.DomChild;
import com.tibco.xml.DomQName;

/*
@author ssailapp
@date Jul 21, 2009
*/

public class ResourcePosition implements IResourcePosition {
    protected static DomQName QNAME_POSITION = DomQName.makeName("position");
    protected static String ATTRIBUTE_LINE = "line";
    protected static String ATTRIBUTE_RESOURCE = "resource";

    private int lineNumber;
    private int length;
    private String resourceName;

    public ResourcePosition(int lineNumber,String resourceName) {
        this(lineNumber,-1,resourceName);
    }
    
    public ResourcePosition(int lineNumber,int length, String resourceName) {
        this.resourceName = resourceName;
        this.length = length;
        this.lineNumber = lineNumber;
    }


    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    

    public void deserialize(Node n) {
        String resource = new String(DomChild.getChildAttributeStringValue(n, ATTRIBUTE_RESOURCE));
        setResourceName(resource);
        int line = new Integer(DomChild.getChildAttributeStringValue(n, ATTRIBUTE_LINE));
        setLineNumber(line);
        
    }

    public Node serialize(Document doc) {
        Element pNode = doc.createElement(QNAME_POSITION.getLocalPart());
        pNode.setAttribute(ATTRIBUTE_RESOURCE, getResourceName());
        String line = Integer.toString(getLineNumber());
        pNode.setAttribute(ATTRIBUTE_LINE, line);
        return pNode;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		result = prime * result + lineNumber;
		result = prime * result + ((resourceName == null) ? 0 : resourceName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ResourcePosition)) {
			return false;
		}
		ResourcePosition other = (ResourcePosition) obj;
		if (length != other.length) {
			return false;
		}
		if (lineNumber != other.lineNumber) {
			return false;
		}
		if (resourceName == null) {
			if (other.resourceName != null) {
				return false;
			}
		} else if (!resourceName.equals(other.resourceName)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ResourcePosition [lineNumber=" + lineNumber + ", length=" + length + ", " + (resourceName != null ? "resourceName=" + resourceName : "") + "]";
	}
	
	
    
    

}
