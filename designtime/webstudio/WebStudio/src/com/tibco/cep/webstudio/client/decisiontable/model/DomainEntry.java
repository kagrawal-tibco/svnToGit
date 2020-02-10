package com.tibco.cep.webstudio.client.decisiontable.model;

/**
 * 
 * @author sasahoo
 *
 */
public class DomainEntry {

	private String value;
	private String desc;
	private boolean single = true;

	public DomainEntry (String value, String desc) {
		this.value = value;
		this.desc = desc;
	}
	
	public String getValue() {
	  return value;	
	}

	public void setValue(String value) {
		this.value = value;
	}


	public String getDescription() {
		return desc;
	}

	public void setDescription(String desc) {
		this.desc = desc;
	}
	
	public boolean isSingle() {
		return single;
	}

	public void setSingle(boolean single) {
		this.single = single;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof DomainEntry))
			return false;

		DomainEntry that = (DomainEntry) o;

		if (desc != null ? !desc.equals(that.desc) : that.desc != null)
			return false;
		if (value != null ? !value.equals(that.value) : that.value != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = 0;
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (desc != null ? desc.hashCode() : 0);
		result = 31 * result + (single ? 1 : 0);
		return result;
	}
}
