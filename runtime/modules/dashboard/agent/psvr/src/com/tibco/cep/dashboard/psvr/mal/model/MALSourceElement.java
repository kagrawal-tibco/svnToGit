package com.tibco.cep.dashboard.psvr.mal.model;

import java.beans.PropertyChangeListener;

import com.tibco.cep.dashboard.common.utils.SUID;
import com.tibco.cep.dashboard.psvr.mal.MALFieldMetaInfo;

public abstract class MALSourceElement extends MALElement {

	private static final String DEFINITION_TYPE = "SourceElement";

	protected Object sourceElement;

	public MALSourceElement(Object sourceElement) {
		setId("MALSRCELEM:"+SUID.createId());
		setDefinitionType(DEFINITION_TYPE);
		setName(DEFINITION_TYPE);
		this.sourceElement = sourceElement;
	}

	public Object getSource() {
		return sourceElement;
	}

	@Override
	public final void addPropertyChangeListener(PropertyChangeListener listener) {
		throw new UnsupportedOperationException("addPropertyChangeListener");
	}

	@Override
	public final boolean removePropertyChangeListener(PropertyChangeListener listener) {
		throw new UnsupportedOperationException("removePropertyChangeListener");
	}

	public abstract String getScopeName();

	public abstract MALFieldMetaInfo[] getFields();

	public abstract MALFieldMetaInfo getField(String name);

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getScopeName() == null) ? 0 : getScopeName().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MALSourceElement other = (MALSourceElement) obj;
		if (getScopeName() == null) {
			if (other.getScopeName() != null)
				return false;
		} else if (!getScopeName().equals(other.getScopeName()))
			return false;
		return true;
	}

}