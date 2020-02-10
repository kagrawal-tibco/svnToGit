package com.tibco.cep.studio.dashboard.core.insight.model.configs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsElement;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynPrimitiveType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class LocalVisualAlertAction extends LocalAlertAction {

	private static final String THIS_TYPE = BEViewsElementNames.VISUAL_ALERT_ACTION;

	public static final String PROP_KEY_FONT_SIZE = "FontSize";

	public static final String PROP_KEY_FONT_COLOR = "FontColor";

	public static final String PROP_KEY_FILL_COLOR = "FillColor";

	public static final String PROP_KEY_FONT_STYLE = "FontStyle";

	public static final String PROP_KEY_DISPLAY_FORMAT = "DisplayFormat";

	public static final String PROP_KEY_TOOLTIP_FORMAT = "TooltipFormat";

	public LocalVisualAlertAction() {
		super(THIS_TYPE);
	}

	public LocalVisualAlertAction(LocalElement parentElement, BEViewsElement mdElement) {
		super(parentElement, THIS_TYPE, mdElement);
	}

	public LocalVisualAlertAction(LocalElement parentElement, String name) {
		super(parentElement, THIS_TYPE, name);
	}

	@Override
	public List<Object> getEnumerations(String propName) {
		if (propName.equals(PROP_KEY_FONT_SIZE)) {
			try {
				SynPrimitiveType primitiveType = (SynPrimitiveType) getProperty(propName).getTypeDefinition();
				return rangeFill(primitiveType.getMinInclusive(), primitiveType.getMaxInclusive());
			} catch (Exception e) {
				e.printStackTrace();
				return Collections.emptyList();
			}
		}
		return super.getEnumerations(propName);
	}

	private static List<Object> rangeFill(String minInclusive, String maxInclusive) {
		List<Object> values = new ArrayList<Object>();
		if (minInclusive == null || minInclusive.length() == 0)
			return values;
		if (maxInclusive == null || maxInclusive.length() == 0)
			return values;

		int min = Integer.parseInt(minInclusive);
		int max = Integer.parseInt(maxInclusive);

		for (int i = min; i <= max; i++) {
			values.add(String.valueOf(i));
		}

		return values;
	}
}
