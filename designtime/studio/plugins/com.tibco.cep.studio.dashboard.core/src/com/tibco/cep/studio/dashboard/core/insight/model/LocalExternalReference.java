package com.tibco.cep.studio.dashboard.core.insight.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;

public class LocalExternalReference extends LocalEntity {

	public LocalExternalReference(Entity externalReference) {
		super(null, externalReference);
	}

	@Override
	public Object cloneThis() {
		throw new UnsupportedOperationException("cloneThis");
	}

	@Override
	public LocalElement createLocalElement(String elementType) {
		throw new UnsupportedOperationException("createLocalElement");
	}

	@Override
	public EObject createMDChild(LocalElement localElement) {
		throw new UnsupportedOperationException("createMDChild");
	}

	@Override
	public void deleteMDChild(LocalElement localElement) {
		throw new UnsupportedOperationException("deleteMDChild");
	}

	@Override
	public String getElementType() {
		return persistedElement.eClass().getName();
	}

	@Override
	public void loadChild(String childrenType, String childName) {
//		throw new UnsupportedOperationException("loadChild");
	}

	@Override
	public void loadChildByID(String childrenType, String childID) {
//		throw new UnsupportedOperationException("loadChildByID");
	}

	@Override
	public void loadChildren(String childrenType) {
//		throw new UnsupportedOperationException("loadChildren");
	}

	@Override
	public void setupProperties() {
//		throw new UnsupportedOperationException("setupProperties");
	}

}
