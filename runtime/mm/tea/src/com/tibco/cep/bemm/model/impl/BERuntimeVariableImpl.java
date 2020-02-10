package com.tibco.cep.bemm.model.impl;

import com.tibco.cep.bemm.model.BERuntimeVariable;

/**
 * Class to hold a Global Variable / System Variable
 * 
 * @author vdhumal
 *
 */
public class BERuntimeVariableImpl implements BERuntimeVariable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3738625567196267553L;
	private String name;
	private String value;
	private String defaultValue;

	public BERuntimeVariableImpl() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.BERuntimeVariable#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.BERuntimeVariable#setName(java.lang
	 * .String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tibco.tea.agent.be.model.impl.BERuntimeVariable#getValue()
	 */
	@Override
	public String getValue() {
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.BERuntimeVariable#setValue(java.lang
	 * .String)
	 */
	@Override
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.BERuntimeVariable#getDefaultValue()
	 */
	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tibco.tea.agent.be.model.impl.BERuntimeVariable#setDefaultValue(java
	 * .lang.String)
	 */
	@Override
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		BERuntimeVariableImpl other = (BERuntimeVariableImpl) obj;

		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;

		return true;
	}

}
