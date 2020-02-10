package com.tibco.cep.studio.dashboard.core.model.XSD.interfaces;

import java.util.List;

public interface ISynXSDModelGroup extends ISynXSDTerm {

	/**
	 * @return List A List of ISynXSDParticle
	 * @see com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDParticle
	 */
	public List<ISynXSDParticle> getParticles();

	public void addParticle(ISynXSDParticle element);

	public void removeParticle(ISynXSDParticle element);

	public ISynXSDModelGroup getRef();

	public void setRef(ISynXSDModelGroup ref);

	public ISynXSDModelGroup getConcreteModelGroup();

}
