package com.tibco.cep.studio.dashboard.ui.chartcomponent.types;

public class ChartSubTypeGroup {

	private String id;
	private String name;
	private ChartSubType[] subTypes;
	
	public ChartSubTypeGroup(String id, String name, ChartSubType[] subTypes) {
		this.id = id;
		this.name = name;
		this.subTypes = subTypes;
	}
	
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public ChartSubType[] getSubTypes() {
		return subTypes;
	}
	
	public ChartSubType getSubType(String id){
		for (ChartSubType subType : subTypes) {
			if (subType.getId().equals(id) == true){
				return subType;
			}
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ChartSubTypeGroup other = (ChartSubTypeGroup) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("ChartSubTypeGroup[id=");
		sb.append(id);
		sb.append("]");
		return sb.toString();
	}
}
