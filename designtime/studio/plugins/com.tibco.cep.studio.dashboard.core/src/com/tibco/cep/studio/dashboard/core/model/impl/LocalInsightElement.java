package com.tibco.cep.studio.dashboard.core.model.impl;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;

/**
 * @ *
 */
public abstract class LocalInsightElement extends LocalEntity {

	public LocalInsightElement() {
		super();
	}

	protected LocalInsightElement(boolean setup) {
		super(setup);
	}

	public LocalInsightElement(LocalElement parentElement) {
		super(parentElement);
	}

	public LocalInsightElement(LocalElement parentElement, BEViewsElement entity) {
		super(parentElement, entity);
	}

	public LocalInsightElement(LocalParticle parentParticle) {
		super(parentParticle);
	}

	public LocalInsightElement(LocalElement parentElement, String name) {
		super(parentElement, name);
	}

	// ===================================================================
	// Initialization
	// ===================================================================

	@Override
	public BEViewsElement getEObject() {
		return (BEViewsElement) super.getEObject();
	}

	@Override
	protected void synchronizeElement(EObject object) {
		super.synchronizeElement(object);
		if (object instanceof BEViewsElement) {
			BEViewsElement beViewsElement = (BEViewsElement) object;
			beViewsElement.setVersion(Long.toString(System.currentTimeMillis()));
		}
	}
}