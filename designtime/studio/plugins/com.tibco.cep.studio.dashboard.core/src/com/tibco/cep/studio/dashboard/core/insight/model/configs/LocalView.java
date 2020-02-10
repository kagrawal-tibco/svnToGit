package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.insight.model.LocalConfig;
import com.tibco.cep.studio.dashboard.core.insight.model.helpers.LocalConfigHelperExtensionImpl;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalView extends LocalConfig {

	public static final String PROP_KEY_SKIN = "Skin";

	private static final String THIS_TYPE = BEViewsElementNames.VIEW;

	public LocalView() {
		super(THIS_TYPE);
		configHelper = new LocalConfigHelperExtensionImpl();
	}

	public LocalView(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
		configHelper = new LocalConfigHelperExtensionImpl();
	}

	public LocalView(LocalElement parentElement, String name) {
		super(parentElement, THIS_TYPE, name);
		configHelper = new LocalConfigHelperExtensionImpl();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getEnumerations(String propName) {
		try {
			if (propName.equals(PROP_KEY_SKIN)) {
				return getRoot().getChildren(BEViewsElementNames.SKIN);
			}
		} catch (Exception e) {
			return Collections.emptyList();
		}
		return super.getEnumerations(propName);
	}

	public List<LocalElement> getComponents(List<String> types) throws Exception {
		HashSet<LocalElement> charts = new HashSet<LocalElement>();
		List<LocalElement> pages = getChildren(BEViewsElementNames.PAGE);
		for (LocalElement page : pages) {
			if (page.getElementType().equals(BEViewsElementNames.DASHBOARD_PAGE) == true) {
				List<LocalElement> partitions = page.getChildren(BEViewsElementNames.PARTITION);
				for (LocalElement partition : partitions) {
					List<LocalElement> panels = partition.getChildren(BEViewsElementNames.PANEL);
					for (LocalElement panel : panels) {
						List<LocalElement> components = panel.getChildren(BEViewsElementNames.COMPONENT);
						for (LocalElement component : components) {
							if (types.contains(component.getElementType()) == true) {
								charts.add(component);
							}
						}
					}
				}
			}
		}
		return new ArrayList<LocalElement>(charts);
	}

}
