package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.Iterator;
import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.LocalPropertyConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalTextVisualization extends LocalVisualization {

	private static final String THIS_TYPE = BEViewsElementNames.TEXT_VISUALIZATION;

    public LocalTextVisualization() {
		super(THIS_TYPE);
	}

	public LocalTextVisualization(LocalElement parentElement,
			BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalTextVisualization(LocalElement parentConfig,
			String name) {
		super(parentConfig, THIS_TYPE, name);
	}

	@Override
	public List<SynProperty> getPropertyList() {
		if (isGlobalPagesetVisualization()) {
			return configReader.getPropertyList(BEViewsElementNames.GLOBAL_PAGESET_VISUALIZATION);
		}
		return super.getPropertyList();
	}

	@Override
	public LocalPropertyConfig getPropertyHelper(SynProperty prop) {
		if (isGlobalPagesetVisualization()) {
			return configReader.getPropertyHelper(BEViewsElementNames.GLOBAL_PAGESET_VISUALIZATION, prop.getName());
		}
		return super.getPropertyHelper(prop);
	}

	@Override
	public LocalPropertyConfig getPropertyHelper(String propertyName) {
		if (isGlobalPagesetVisualization()) {
			return configReader.getPropertyHelper(BEViewsElementNames.GLOBAL_PAGESET_VISUALIZATION, propertyName);
		}
		return super.getPropertyHelper(propertyName);
	}

	@Override
	public boolean isValid() throws Exception {
		super.isValid();
		LocalConfig parent = (LocalConfig) getParent();
		if (parent.getInsightType().equals(BEViewsElementNames.PAGE_SELECTOR_COMPONENT)
				&& parent.getChildren(BEViewsElementNames.TEXT_VISUALIZATION).size() > 1) {
			addValidationMessage(new SynValidationErrorMessage(
        			"Only one TextVisualization allowed in a PageSetSelectorComponent"));
		}
		return (null == getValidationMessage());
	}

	//@Override
	protected void performRefresh(BEViewsElement mdElement) throws Exception {
		try {
            setDeliver(false);
            this.bulkOperation = true;
	        /*
	         * Reset all the properties in case they have been set already The
	         * properties will be lazily re-parsed when they are called upon
	         */
	        this.setPropertiesAlreadySet(false);
	        // Special case: Series Config is handled in the Editor
	        // so we need not handle it here
	        // This code is required until and unless the Series editor is there in architect.
	        removeAllChildrenExceptSeries();
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

	private void removeAllChildrenExceptSeries() {
		for (Iterator<LocalParticle> iter = getParticles(true).iterator(); iter.hasNext();) {
            LocalParticle particle = (LocalParticle) iter.next();
            if (true == particle.isInitialized()
            		&& !particle.getName().equals(BEViewsElementNames.TEXT_SERIES_CONFIG)) {
                particle.removeAll();
            }

        }
	}

	private boolean isGlobalPagesetVisualization() {
		LocalElement parentElement = getParent();
		if (parentElement != null && parentElement instanceof LocalConfig) {
			LocalConfig parentConfig = (LocalConfig) parentElement;
			return parentConfig.getInsightType().equals(BEViewsElementNames.PAGE_SELECTOR_COMPONENT);
		}
		return false;
	}

}
