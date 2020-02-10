package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.Iterator;
import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.designtime.core.model.beviewsconfig.Panel;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalPanel extends LocalConfig {

	private static final String THIS_TYPE = BEViewsElementNames.PANEL;

    private static String[] LayoutProperties = new String[] { "LayoutRepositioningAllowed", "LayoutComponentWidth", "LayoutComponentHeight" };

    public LocalPanel() {
		super(THIS_TYPE);
	}

	public LocalPanel(LocalElement parentElement,
			BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalPanel(LocalElement parentConfig, String name) {
		super(parentConfig, THIS_TYPE, name);
	}

	@Override
	public boolean addElement(String particleName, LocalElement localElement) {
		boolean bAdd = super.addElement(particleName, localElement);
		getParent().setModified();
		return bAdd;
	}

	@Override
	protected void preSynchronizeElement(BEViewsElement mdElement) {
		boolean bSet = setLayout();
		if (!bSet) {
			//layout is not needed, so remove it
			((Panel)mdElement).setLayout(null);
		}
	}

	@Override
	protected void synchronizeConfigReferenceChildren(BEViewsElement mdElement, LocalParticle particle) {
		if(particle.getName().equals(BEViewsElementNames.COMPONENT)) {
			List<LocalElement> elements = particle.getElements(true, false);
			configHelper.setConfigReferenceChild(this, mdElement, particle.getPath(), particle.getMaxOccurs(), elements);
		} else {
			super.synchronizeConfigReferenceChildren(mdElement, particle);
		}
	}

    /**
     * Returns true if any component in the panel has layout need. If yes then we assume that panel
     * layout is configured.
     *
     * @param panelConfig
     * @throws Exception
     */
    private boolean setLayout() {
        List<LocalElement> l = getChildren(BEViewsElementNames.COMPONENT);
        if (l.size() == 0){
            addPanelLayout();
            return true;
        }
        for (Iterator<LocalElement> iter = l.iterator(); iter.hasNext();) {
        	LocalConfig component = (LocalConfig) iter.next();
            if (needPanelLayout(component) == true) {
                addPanelLayout();
                return true;
            }
        }
        removePanelLayout();
        return false;
    }

    /**
     * Adds the panel layout to the panel.
     *
     * @param panelConfig
     * @throws Exception
     */
    private void addPanelLayout() {
        for (int i = 0; i < LayoutProperties.length; ++i) {
            SynProperty p = (SynProperty) getProperty(LayoutProperties[i]);
            if (p == null) {
                List<SynProperty> propertyList = getPropertyList();
                Iterator<SynProperty> propertyListIterator = propertyList.iterator();
                while (propertyListIterator.hasNext()) {
                    SynProperty property = (SynProperty) propertyListIterator.next();
                    if (property.getName().equals(LayoutProperties[i]) == true) {
                        SynProperty propToBeAdded = (SynProperty) property.clone();
                        addProperty(propToBeAdded);
                        p = propToBeAdded;
                        break;
                    }
                }
            }
            if (p != null) {
                p.setValue(p.getDefault());
            }
        }
    }

    public boolean isMetricPanel() {
    	List<LocalElement> components = getChildren(BEViewsElementNames.COMPONENT);
    	if (components.isEmpty() == true){
    		//empty panels are assumed to be metric panels
    		return true;
    	}
    	//get the first component in the list
    	LocalElement component = components.get(0);
    	if (BEViewsElementNames.getChartOrTextComponentTypes().contains(component.getElementType()) == true){
    		//we have metric panel components in the list , so we are dealing with a metric panel
    		return true;
    	}
        return false;
    }

    /**
     * Removes the layout from the panel.
     *
     * @param panelConfig
     * @throws Exception
     */
    private void removePanelLayout() {
        for (int i = 0; i < LayoutProperties.length; ++i) {
            SynProperty p = (SynProperty) getProperty(LayoutProperties[i]);
            if (p != null) {
                removeProperty(LayoutProperties[i]);
            }
        }
    }

    /**
     * checks for a component if layout for the panel needed.
     *
     * @param component
     * @return
     * @throws Exception
     */
    private boolean needPanelLayout(LocalConfig component) {
        String type = component.getInsightType();
        return (type.equals(BEViewsElementNames.CHART_COMPONENT) || type.equals(BEViewsElementNames.TEXT_COMPONENT));
    }

    @Override
    protected void validateProperty(SynProperty prop) throws Exception {
    	if ("SpanPercentage".equals(prop.getName()) == true) {
    		try {
    			double span = parseSpan();
    			if (span <= 0) {
    				addValidationErrorMessage("Invalid 'SpanPercentage' specified for "+getDisplayableName());
    			}
    		} catch (NumberFormatException e) {
    			addValidationErrorMessage("Invalid 'SpanPercentage' specified for "+getDisplayableName());
    		}
    	}
    }

    public double getSpan() throws Exception{
    	try {
			return parseSpan();
		} catch (NumberFormatException e) {
			return -1;
		}
    }

    private double parseSpan() throws Exception{
    	String value = getPropertyValue("SpanPercentage");
		if (value.endsWith("%") == true) {
			value = value.substring(0,value.length()-1);
		}
		return Double.parseDouble(value);
    }

}
