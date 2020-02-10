package com.tibco.cep.studio.ui.forms.extendedPropTreeViewer;

import java.util.List;

import com.tibco.cep.studio.ui.forms.components.ExtendedPropertiesMap;

public class PropTreeModelParent {


 	public PropTreeModelParent(){}
	public PropTreeModelParent(String name, String value, List<Object> obj, PropTreeModelParent parent,ExtendedPropTreeViewer invokingclass) {
			Name = name;
			Value = value;
			this.setObjList(obj);
			setParent(parent);
			this.extendedTreeViewer = invokingclass;
		}
	private ExtendedPropTreeViewer extendedTreeViewer;
	private String Name;
	private String Value;
	private ExtendedPropertiesMap map;
	private List<Object> objList;
	private PropTreeModelParent parent;
	public PropTreeModelParent getParent() {
		return parent;
	}

	public void setParent(PropTreeModelParent parent) {
		this.parent = parent;
	}

	public List<Object> getObjList() {
		return objList;
	}

	public void setObjList(List<Object> objList) {
		this.objList = objList;
	}
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
		PropTreeModelParent other = (PropTreeModelParent) obj;
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
	public ExtendedPropTreeViewer getExtendedTreeViewer() {
		return extendedTreeViewer;
	}
	public void setExtendedTreeViewer(ExtendedPropTreeViewer extendedTreeViewer) {
		this.extendedTreeViewer = extendedTreeViewer;
	}
	private ExtendedPropTreeViewer getOuterType() {
		return extendedTreeViewer;
	}
}
