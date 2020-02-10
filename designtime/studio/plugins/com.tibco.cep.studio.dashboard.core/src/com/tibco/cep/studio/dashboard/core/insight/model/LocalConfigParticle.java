package com.tibco.cep.studio.dashboard.core.insight.model;

import java.util.List;

import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalConfigParticle extends LocalParticle {

	public LocalConfigParticle(String name) {
		super(name);
	}

	public LocalConfigParticle(String name, long minOccurs, long maxOccurs) {
		super(name, minOccurs, maxOccurs);
	}

	public LocalConfigParticle(LocalElement parent, String name) {
		super(parent, name);
	}

	public LocalConfigParticle(LocalElement parent, String name,
			long minOccurs, long maxOccurs) {
		super(parent, name, minOccurs, maxOccurs);
	}
	
	@Override
	public Object clone() {
		LocalParticle localParticle = (LocalParticle) super.clone();
		localParticle.setPath(getPath());
		localParticle.setTypeName(getTypeName());
		localParticle.setMDConfigType(isMDConfigType());
		return localParticle;
	}
	
	@Override
	public List<LocalElement> getElements(boolean activeOnly, boolean sort) {
		if(getName().equals(BEViewsElementNames.PANEL)
				|| getName().equals(BEViewsElementNames.COMPONENT)) {
			return super.getElements(activeOnly, false);
		} else {
			return super.getElements(activeOnly, sort);
		}
	}

}
