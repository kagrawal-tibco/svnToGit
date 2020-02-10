package com.tibco.cep.dashboard.psvr.mal.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;

import com.tibco.cep.dashboard.common.utils.SUID;

public class MALExternalReference extends MALElement {
	
	private static final String DEFINITION_TYPE = "ExternalReference";

	private PropertyChangeSupport propertyChangeSupport;
	
	private Object externalReference;
	
	public MALExternalReference(Object externalReference) {
		setId("MALEXTREF:"+SUID.createId());
		setDefinitionType(DEFINITION_TYPE);
		setName(DEFINITION_TYPE);
		propertyChangeSupport = new PropertyChangeSupport(this);
		this.externalReference = externalReference;
		propertyChangeSupport.addPropertyChangeListener(this);
	}

	public final void setExternalReference(Object externalReference) {
		if (externalReference != null && externalReference.equals(this.externalReference) == false) {
			Object oldValue = this.externalReference;
			this.externalReference = externalReference;
			propertyChangeSupport.firePropertyChange("externalReference", oldValue, externalReference);
		}
	}
	
	public final Object getExternalReference() {
		return externalReference;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	@Override
	public boolean removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
		return true;
	}

	public MALExternalReference createCopy(boolean resetPrimaryProps) {
		MALExternalReference copy = new MALExternalReference(null);
		copy.setId(getId());
		copy.setDefinitionType(getDefinitionType());
		copy.setDescription(getDescription());
		//copy.setFolder(getFolder());
		copy.setName(getName());
		//copy.setNamespace(getNamespace());
		copy.setParent(getParent());
		copy.setPersistedObject(getPersistedObject());
		copy.setTopLevelElement(isTopLevelElement());
		Collection<MALElement> references = getReferences();
		for (MALElement reference : references) {
			copy.addReference(reference);
		}
		//we want to invoke the setExternalReference to get the copy to be dirty
		copy.setExternalReference(externalReference);
		return copy;
	}

}
