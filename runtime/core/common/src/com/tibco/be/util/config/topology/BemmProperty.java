/**
 * 
 */
package com.tibco.be.util.config.topology;

import com.tibco.xml.datamodel.XiNode;

/**
 * @author Nick
 *
 */
public class BemmProperty {
	private String name;
	private String type;
	private String value;
	
	public BemmProperty(XiNode containerNode){
		this.name = containerNode.getAttributeStringValue(TopologyNS.Attributes.NAME);
		this.type = containerNode.getAttributeStringValue(TopologyNS.Attributes.TYPE);
		this.value = containerNode.getAttributeStringValue(TopologyNS.Attributes.VALUE);
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}
}
