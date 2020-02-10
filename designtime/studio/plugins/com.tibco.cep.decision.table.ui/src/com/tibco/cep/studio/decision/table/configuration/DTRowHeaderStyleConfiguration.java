package com.tibco.cep.studio.decision.table.configuration;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.config.DefaultRowHeaderStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.painter.cell.GradientBackgroundPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.LineBorderDecorator;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle.LineStyleEnum;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/** This class is responsible for styling the row header layer (i.e. the
 * column that display the rule IDs).
 * 
 * @author rhollom
 *
 */
public class DTRowHeaderStyleConfiguration extends DefaultRowHeaderStyleConfiguration {

	public DTRowHeaderStyleConfiguration() {
		super();
	}

    protected ICellPainter createTextPainter() {
    	boolean useGradients = DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.USE_GRADIENTS);
		TextPainter txtPainter = new TextPainter(false, !useGradients, 3);
		txtPainter.setCalculateByTextHeight(true);
		if (useGradients) {
			GradientBackgroundPainter painter = new GradientBackgroundPainter(true);
			painter.setWrappedPainter(txtPainter);
			LineBorderDecorator decorator = new LineBorderDecorator(painter);
			return decorator;
		}
		return txtPainter;
    }

	@Override
	public void configureRegistry(IConfigRegistry configRegistry) {
		String headerFont = DecisionTableUIPlugin.getDefault().getPreferenceStore().getString(PreferenceConstants.COLUMN_HEADER_FONT);
		FontData headerFontData = new FontData(headerFont);
		font = GUIHelper.getFont(headerFontData); // use the column header font

		cellPainter = createTextPainter();
//		ICellPainter painter = createTextPainter();
//		LineBorderDecorator lbd = new LineBorderDecorator(painter, borderStyle);
//		cellPainter = lbd;

		Style cellStyle = new Style();
		IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
		String hdgBgColor = prefStore.getString(PreferenceConstants.HEADER_BACK_GROUND_COLOR);
//		String hdgFgColor = prefStore.getString(PreferenceConstants.HEADER_FORE_GROUND_COLOR);
//		this.fgColor = GUIHelper.getColor(PreferenceConstants.convertToRGB(hdgFgColor));
		this.fgColor = GUIHelper.getColor(PreferenceConstants.convertToRGB(prefStore.getString(PreferenceConstants.SELECTION_FG_COLOR)));//GUIHelper.getColor(PreferenceConstants.convertToRGB(hdgFgColor));
		cellStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR, fgColor);
		this.bgColor = GUIHelper.getColor(PreferenceConstants.convertToRGB(hdgBgColor));
		cellStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, bgColor);
		cellStyle.setAttributeValue(CellStyleAttributes.BORDER_STYLE, new BorderStyle(1, GUIHelper.getColor(PreferenceConstants.convertToRGB(prefStore.getString(PreferenceConstants.SELECTION_COLOR))), LineStyleEnum.DASHED));
		RGB rgb = bgColor.getRGB();
		this.gradientBgColor = bgColor;
		this.gradientFgColor = getGradientColor(rgb);
//		cellStyle.setAttributeValue(CellStyleAttributes.GRADIENT_BACKGROUND_COLOR, bgColor);
//		cellStyle.setAttributeValue(CellStyleAttributes.GRADIENT_FOREGROUND_COLOR, getGradientColor(rgb));

//		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.SELECT, GridRegion.ROW_HEADER);
//		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cellStyle, DisplayMode.HOVER, GridRegion.ROW_HEADER);
		
//		Style cornerStyle = new Style();
//		cornerStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, DTStyleConstants.FILTER_ROW_FG_COLOR);
//		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cornerStyle, DisplayMode.NORMAL, GridRegion.CORNER);
		super.configureRegistry(configRegistry);
	}
	
	private Color getGradientColor(RGB rgb) {
		double adj = 1.1;
		int red = Math.min((int)(rgb.red/adj), 255);
		int green = Math.min((int)(rgb.green/adj), 255);
		int blue = Math.min((int)(rgb.blue/adj), 255);
		return GUIHelper.getColor(new RGB(red, green, blue));
	}
}