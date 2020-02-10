package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalChartComponentColorSet extends LocalConfig {

	private static final String THIS_TYPE = BEViewsElementNames.CHART_COLOR_SET;

	public LocalChartComponentColorSet() {
		super(THIS_TYPE);
	}

	public LocalChartComponentColorSet(LocalElement parentElement,
			BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalChartComponentColorSet(LocalElement parentElement,
			String name) {
		super(parentElement, THIS_TYPE, name);
	}
	
	@Override
	public List<Object> getEnumerations(String propName) {
		try {
			if (BEViewsElementNames.SERIES_COLOR.equals(propName) == true){
				return new LinkedList<Object>(getChildren(BEViewsElementNames.SERIES_COLOR));
			}
			return super.getEnumerations(propName);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}
	
}
