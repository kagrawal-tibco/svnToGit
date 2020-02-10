package com.tibco.cep.studio.ui.forms.extendedPropTreeViewer;

import com.tibco.cep.studio.ui.forms.components.ExtendedPropertiesMap;

public class PropTreeModelChild {
	
	public PropTreeModelChild(String name, String value, PropTreeModelParent parent,ExtendedPropTreeViewer invokingclass) {
		
		Name = name;
		Value = value;
		this.setParent(parent);
		map=new ExtendedPropertiesMap();
		map.put(this.Name, this.Value);
		this.extendedTreeViewer = invokingclass;
	}
	
	public PropTreeModelChild() {
		// TODO Auto-generated constructor stub
	}

	private String Name;
	private String Value;
	private PropTreeModelParent parent;
	private ExtendedPropTreeViewer extendedTreeViewer;
	private ExtendedPropertiesMap map;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}
	public ExtendedPropertiesMap getMap() {
		return map;
	}
	public void setMap(ExtendedPropertiesMap map) {
		this.map = map;
	}

	public PropTreeModelParent getParent() {
		return parent;
	}

	public void setParent(PropTreeModelParent parent) {
		this.parent = parent;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + getOuterType().hashCode();
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((Value == null) ? 0 : Value.hashCode());
		result = prime * result
				+ ((getParent() == null) ? 0 : getParent().hashCode());
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
		PropTreeModelChild other = (PropTreeModelChild) obj;
		if (!getOuterType().equals(other.getOuterType()))
			return false;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (Value == null) {
			if (other.Value != null)
				return false;
		} else if (!Value.equals(other.Value))
			return false;
		if (getParent() == null) {
			if (other.getParent() != null)
				return false;
		} else if (!getParent().equals(other.getParent()))
			return false;
		return true;
	}

	private ExtendedPropTreeViewer getOuterType() {
		return 	this.extendedTreeViewer;
	}
	public ExtendedPropTreeViewer getExtendedTreeViewer() {
		return extendedTreeViewer;
	}
	public void setExtendedTreeViewer(ExtendedPropTreeViewer extendedTreeViewer) {
		this.extendedTreeViewer = extendedTreeViewer;
	}

}
