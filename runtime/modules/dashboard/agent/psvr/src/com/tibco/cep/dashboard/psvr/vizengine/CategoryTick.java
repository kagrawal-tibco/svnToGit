package com.tibco.cep.dashboard.psvr.vizengine;

public class CategoryTick {

	private String id;

	private String displayValue;

	private String value;

	public CategoryTick(String id, String displayValue, String value) {
		super();
		this.id = id;
		this.displayValue = displayValue;
		this.value = value;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CategoryTick other = (CategoryTick) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("CategoryValue[");
		sb.append("id=");
		sb.append(id);
		sb.append(",displayvalue=");
		sb.append(displayValue);
		sb.append(",value=");
		sb.append(value);
		sb.append("]");
		return sb.toString();
	}

}