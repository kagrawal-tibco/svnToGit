package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.Iterator;
import java.util.logging.Logger;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalPageSelectorComponent extends LocalComponent {

	private static final String THIS_TYPE = BEViewsElementNames.PAGE_SELECTOR_COMPONENT;

	private static final Logger logger = Logger.getLogger(LocalPageSelectorComponent.class.getName());

	public LocalPageSelectorComponent() {
		super(THIS_TYPE);
	}

	public LocalPageSelectorComponent(LocalElement parentElement,
			BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalPageSelectorComponent(LocalElement parentConfig,
			String name) {
		super(parentConfig, THIS_TYPE, name);
	}

	@Override
    protected LocalElement convertMdConfigToLocalConfig(Entity emfElement, String expectedType) {
        String configTypeName = emfElement.eClass().getName();
        if (!configTypeName.equals(expectedType)) {
        	//For TextVisualization separation from PageSetVisualization
        	return null;
        }
        return super.convertMdConfigToLocalConfig(emfElement, expectedType);
    }

	//@Override
	//TODO refresh code
	protected void performRefresh(BEViewsElement mdElement) throws Exception {
		try {
            setDeliver(false);
            this.bulkOperation = true;
	        /*
	         * Reset all the properties in case they have been set already The
	         * properties will be lazily re-parsed when they are called upon
	         */
	        this.setPropertiesAlreadySet(false);
	        // Special case: Visualization is handled in the Editor
	        // so we need not handle it here
	        // This code is required until and unless the Visualization editor is there in architect.
	        removeAllChildrenExceptVisualization();
	        if ( mdElement != null ) {
		        /*
		         * These are the basic properties that all elements can have and
		         * is aligned with the initial properties returned by MDS for
		         * each element.
		         *
		         * At the minimum these properties are required to be parsed so
		         * that the framework can properly handle them; all other
		         * properties can be lazily parsed.
		         */
		        // Now, re-fetch information from mdElement if mdElement is not null.
		        this.setElementLocatorKey(mdElement);
		        SynProperty property = (SynProperty) this.getProperty(PROP_KEY_GUID);
		        property.setValue(mdElement.getGUID());
		        property.setAlreadySet(true);

		        // After refresh, all properties and children should have status existing.
		        setInternalStatus(InternalStatusEnum.StatusExisting,false);
	        }
        }
	    finally {
	        this.bulkOperation = false;
	        setDeliver(true);
	    }
	    refreshExtra(mdElement);
	}

	private void removeAllChildrenExceptVisualization() throws Exception {
		for (Iterator<LocalParticle> iter = getParticles(true).iterator(); iter.hasNext();) {
            LocalParticle particle = (LocalParticle) iter.next();
            if (true == particle.isInitialized()
            		&& !particle.getName().equals(BEViewsElementNames.PAGESET_VISUALIZATION)
            		&& !particle.getName().equals(BEViewsElementNames.TEXT_VISUALIZATION)) {
                logger.fine("Removing all children of: " + getFullPath() + "::" + particle.getName());
                particle.removeAll();
            }

        }
	}
}
