package com.tibco.cep.bpmn.ui.graph.model;

import org.eclipse.emf.ecore.EClass;

/**
 * encapsulate node type details
 * @author majha
 *
 */
public class PropertyNodeType {
	private EClass nodeType;
	private EClass nodeExtType;

	public PropertyNodeType(EClass type, EClass extType) {
		nodeType = type;
		nodeExtType = extType;
	}	
	
	public EClass getNodeExtType() {
		return nodeExtType;
	}
	
	public EClass getNodeType() {
		return nodeType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nodeExtType == null) ? 0 : nodeExtType.hashCode());
		result = prime * result
				+ ((nodeType == null) ? 0 : nodeType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PropertyNodeType other = (PropertyNodeType) obj;
		if (nodeExtType == null) {
			if (other.nodeExtType != null)
				return false;
		} else if (!nodeExtType.equals(other.nodeExtType))
			return false;
		if (nodeType == null) {
			if (other.nodeType != null)
				return false;
		} else if (!nodeType.equals(other.nodeType))
			return false;
		return true;
	}
	
	public String toString() {
		StringBuilder desc = new StringBuilder();
		if(nodeType != null) {
			desc.append(nodeType.getName());
		}
		if(nodeExtType != null){
			desc.append(":").append(nodeExtType.getName());
		}
		return desc.toString();
	}
	
}