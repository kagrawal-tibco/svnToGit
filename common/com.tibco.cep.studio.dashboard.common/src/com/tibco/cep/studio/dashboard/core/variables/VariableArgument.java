package com.tibco.cep.studio.dashboard.core.variables;

public class VariableArgument {

	private String id;
	
	private String name;
	
	public VariableArgument(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
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
		VariableArgument other = (VariableArgument) obj;
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
		StringBuilder sb = new StringBuilder(VariableArgument.class.getSimpleName());
		sb.append("[id=");
		sb.append(id);
		sb.append(",name=");
		sb.append(name);
		sb.append("]");
		return sb.toString();
	}
}
