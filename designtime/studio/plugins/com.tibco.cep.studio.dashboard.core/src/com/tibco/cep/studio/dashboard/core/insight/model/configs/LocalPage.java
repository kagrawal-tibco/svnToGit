package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.LocalConfigHelperImpl;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalPage extends LocalConfig {

	public LocalPage(String configType) {
		super(configType);
	}

	public LocalPage(LocalElement parentElement,
			String configType, BEViewsElement mdElement) {
		super(parentElement, configType, mdElement);
	}

	public LocalPage(LocalElement parentElement,
			String configType, String name) {
		super(parentElement, configType, name);
	}

	@Override
    public void synchronizeChildren() {
    	super.synchronizeChildren();
    	LocalConfigHelperImpl.getInstance().setParticleChildrenOrder(this, getParticle(BEViewsElementNames.PARTITION));
    }

	@Override
	public void refresh(EObject mdElement) {
		LocalParticle partitionParticle = getParticle(BEViewsElementNames.PARTITION);
		if(partitionParticle.isInitialized()) {
			List<LocalElement> partitions = partitionParticle.getElements(true);
			for (LocalElement partition : partitions) {
				partition.refresh(partition.getEObject());
			}
		}
		super.refresh(mdElement);
	}

}
