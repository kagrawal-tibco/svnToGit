package com.tibco.cep.dashboard.psvr.mal;

import com.tibco.cep.dashboard.psvr.mal.model.MALBackgroundFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALElement;
import com.tibco.cep.dashboard.psvr.mal.model.MALFormatStyle;
import com.tibco.cep.dashboard.psvr.mal.model.MALIndicatorFieldFormat;
import com.tibco.cep.dashboard.psvr.mal.model.MALTextFieldFormat;
import com.tibco.cep.dashboard.psvr.mal.model.types.AnchorPositionEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.FontStyleEnum;
import com.tibco.cep.dashboard.psvr.mal.model.types.GradientDirectionEnum;

/**
 * @author apatil
 *
 */
public class MALCreatorUtils {

	public static final MALFormatStyle createFormatStyle(MALElement parent, String name, int fontSize, FontStyleEnum fontStyle) throws MALException {
		MALFormatStyle formatStyle = (MALFormatStyle) MALElementManagerFactory.getInstance().getManager("FormatStyle").create(parent, name);
		// formatStyle.setName(name);
		if (fontSize > 0) {
			formatStyle.setFontSize(fontSize);
		}
		if (fontStyle != null) {
			formatStyle.setFontStyle(fontStyle);
		}
		return formatStyle;
	}

	public static final MALBackgroundFormat createBackgroundFormat(MALElement parent, String name, GradientDirectionEnum gradientDirection) throws MALException {
		MALBackgroundFormat bgFormat = (MALBackgroundFormat) MALElementManagerFactory.getInstance().getManager("BackgroundFormat").create(parent, name);
		if (gradientDirection != null) {
			bgFormat.setGradientDirection(gradientDirection);
		}
		return bgFormat;
	}

	public static final MALTextFieldFormat createTextFieldFormat(MALElement parent, String name, String displayFormat, String tooltipFormat, int fontSize, FontStyleEnum fontStyle) throws MALException {
		MALTextFieldFormat formatter = (MALTextFieldFormat) MALElementManagerFactory.getInstance().getManager("TextFieldFormat").create(parent, name);
		formatter.setName(name);
		formatter.setToolTipFormat(tooltipFormat);
		formatter.setDisplayFormat(displayFormat);
		formatter.setFormatStyle(MALCreatorUtils.createFormatStyle(parent, name + "_formatstyle", fontSize, fontStyle));
		return formatter;
	}

	public static final MALIndicatorFieldFormat createIndicatorFieldFormat(MALElement parent, String name, String displayFormat, String tooltipFormat, boolean showTextValue, AnchorPositionEnum textAnchor, int fontSize,
			FontStyleEnum fontStyle) throws MALException {
		MALIndicatorFieldFormat formatter = (MALIndicatorFieldFormat) MALElementManagerFactory.getInstance().getManager("IndicatorFieldFormat").create(parent, name);
		formatter.setName(name);
		formatter.setToolTipFormat(tooltipFormat);
		formatter.setDisplayFormat(displayFormat);
		formatter.setFormatStyle(MALCreatorUtils.createFormatStyle(parent, name + "_formatstyle", fontSize, fontStyle));
		if (showTextValue == true) {
			formatter.setShowTextValue(showTextValue);
			formatter.setTextValueAnchor(textAnchor);
		}
		return formatter;
	}

//	public static final MALProgressBarFieldFormat createProgressBarFieldFormat(MALElement parent, String name, String displayFormat, String tooltipFormat, boolean showTextValue, AnchorPositionEnum textAnchor, int fontSize,
//			FontStyleEnum fontStyle, int minValue, int maxValue, int threshold) throws MALException {
//		MALProgressBarFieldFormat formatter = (MALProgressBarFieldFormat) MALElementManagerFactory.getInstance().getManager("ProgressBarFieldFormat").create(parent, name);
//		formatter.setName(name);
//		formatter.setToolTipFormat(tooltipFormat);
//		formatter.setDisplayFormat(displayFormat);
//		formatter.setFormatStyle(MALCreatorUtils.createFormatStyle(parent, name + "_formatstyle", fontSize, fontStyle));
//		if (showTextValue == true) {
//			formatter.setShowTextValue(showTextValue);
//			formatter.setTextValueAnchor(textAnchor);
//		}
//		formatter.setThreshold(threshold);
//		formatter.setMinValue(minValue);
//		formatter.setMaxValue(maxValue);
//		return formatter;
//	}
}
