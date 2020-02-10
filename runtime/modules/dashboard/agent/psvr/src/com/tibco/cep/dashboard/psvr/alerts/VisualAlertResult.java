package com.tibco.cep.dashboard.psvr.alerts;


/**
 * @author apatil
 * 
 */
public final class VisualAlertResult extends AlertResult {

	public static final String TRANSFORMATION_FORMAT_KEY = "TransformationFormat";

	public static final String TOOLTIP_FORMAT_KEY = "ToolTipFormat";

	public static final String FONTSTYLE_FORMAT_KEY = "FontStyle";

	public static final String FONTCOLOR_FORMAT_KEY = "FontColor";

	public static final String COLOR_FORMAT_KEY = "Color";

	public static final String HIGHLIGHT_COLOR_FORMAT_KEY = "HightlighColor";

	public static final String[] STANDARD_KEYS = new String[] { TRANSFORMATION_FORMAT_KEY, TOOLTIP_FORMAT_KEY, FONTSTYLE_FORMAT_KEY, FONTCOLOR_FORMAT_KEY, COLOR_FORMAT_KEY, HIGHLIGHT_COLOR_FORMAT_KEY };

	public static final String DEFAULT_HIGHLIGHT_COLOR = "FFFFFF";

	public VisualAlertResult() {
		setValue(HIGHLIGHT_COLOR_FORMAT_KEY, DEFAULT_HIGHLIGHT_COLOR);
	}
	
}