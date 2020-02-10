package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.LocalConfigHelperImpl;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalPartition extends LocalConfig {

	private static final String THIS_TYPE = BEViewsElementNames.PARTITION;

	public LocalPartition() {
		super(THIS_TYPE);
	}

	public LocalPartition(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalPartition(LocalElement parentElement, String configType, BEViewsElement mdElement) {
		super(parentElement, configType, mdElement);
	}

	public LocalPartition(LocalElement parentConfig, String name) {
		super(parentConfig, THIS_TYPE, name);
	}

	@Override
	protected void synchronizeChildren() {
		super.synchronizeChildren();
		LocalConfigHelperImpl.getInstance().setParticleChildrenOrder(this, getParticle(BEViewsElementNames.PANEL));
	}

    @Override
    protected void validateProperty(SynProperty prop) throws Exception {
    	if ("SpanPercentage".equals(prop.getName()) == true) {
    		try {
    			double span = parseSpan();
    			if (span <= 0) {
    				addValidationErrorMessage("Invalid 'Span' specified for "+getDisplayableName());
    			}
    		} catch (NumberFormatException e) {
    			addValidationErrorMessage("Invalid 'Span' specified for "+getDisplayableName());
    		}
    	}
    }

	@Override
	protected void validateParticle(LocalParticle particle) throws Exception {
		super.validateParticle(particle);
		if (BEViewsElementNames.PANEL.equals(particle.getName()) == true) {
			List<LocalElement> panels = getChildren(BEViewsElementNames.PANEL);
			double totalSpan = 0;
			for (LocalElement panel : panels) {
				totalSpan = totalSpan + ((LocalPanel)panel).getSpan();
			}
			if (totalSpan > 100) {
				addValidationErrorMessage("'Span' of all panels exceeds 100% in "+getDisplayableName());
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
