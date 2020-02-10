package com.tibco.cep.studio.decision.table.configuration;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.config.DefaultColumnHeaderStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.painter.cell.GradientBackgroundPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TextPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.LineBorderDecorator;
import org.eclipse.nebula.widgets.nattable.sort.painter.SortableHeaderTextPainter;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle.LineStyleEnum;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.style.VerticalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;

import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/** 
 * This class is responsible for styling the column header layer of the 
 * decision/exception table.  This includes the column header, column header
 * group, as well as the filter row.  It is also responsible for setting
 * up the selection styling.
 * 
 * @author rhollom
 *
 */
public class DTColumnHeaderStyleConfiguration extends DefaultColumnHeaderStyleConfiguration {
	private static IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();
	private Font grpFont;

	public DTColumnHeaderStyleConfiguration() {
	}

	@Override
	public void configureRegistry(IConfigRegistry configRegistry) {
		String hdForeColor = prefStore.getString(PreferenceConstants.HEADER_FORE_GROUND_COLOR);
		bgColor = GUIHelper.getColor(PreferenceConstants.convertToRGB(prefStore.getString(PreferenceConstants.SELECTION_COLOR)));
		fgColor= GUIHelper.getColor(PreferenceConstants.convertToRGB(hdForeColor));

		// default styling
		FontData fd = new FontData(prefStore.getString(PreferenceConstants.COLUMN_HEADER_FONT));
		font = GUIHelper.getFont(fd);
		grpFont = GUIHelper.getFont(new FontData(fd.getName(), fd.getHeight(), fd.getStyle() | SWT.BOLD | SWT.ITALIC));
		borderStyle = new BorderStyle(1, GUIHelper.COLOR_DARK_GRAY, LineStyleEnum.SOLID);

		this.hAlign = HorizontalAlignmentEnum.CENTER;
		this.vAlign = VerticalAlignmentEnum.MIDDLE;
		
		addNormalModeStyling(configRegistry);
		addSelectedModeStyling(configRegistry);
		
		// Shade the filter row to be slightly darker than the blue background.
		final Style rowStyle = new Style();
		rowStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, DTStyleConstants.FILTER_ROW_BG_COLOR);
		rowStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR, DTStyleConstants.FILTER_ROW_FG_COLOR);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, rowStyle, DisplayMode.NORMAL, GridRegion.FILTER_ROW);
		String hdBackColor = prefStore.getString(PreferenceConstants.HEADER_BACK_GROUND_COLOR);

		String hdgBgColor = prefStore.getString(PreferenceConstants.HEADER_GROUP_BACK_GROUND_COLOR);
		String hdgForeColor = prefStore.getString(PreferenceConstants.HEADER_GROUP_FORE_GROUND_COLOR);
		super.configureRegistry(configRegistry);

		// Column Group styling
		final Style cgStyle = new Style();
		cgStyle.setAttributeValue(CellStyleAttributes.FONT, getGroupHeaderFont());
		cgStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(PreferenceConstants.convertToRGB(hdgBgColor)));
		cgStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR,GUIHelper.getColor(PreferenceConstants.convertToRGB(hdgForeColor)));
		RGB rgb = PreferenceConstants.convertToRGB(hdgBgColor);
		cgStyle.setAttributeValue(CellStyleAttributes.GRADIENT_BACKGROUND_COLOR, GUIHelper.getColor(rgb));
		cgStyle.setAttributeValue(CellStyleAttributes.GRADIENT_FOREGROUND_COLOR, getGradientColor(rgb));
		cgStyle.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, HorizontalAlignmentEnum.CENTER);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cgStyle, DisplayMode.NORMAL, GridRegion.COLUMN_GROUP_HEADER);

		// Column header styling
		final Style clStyle = new Style();
		clStyle.setAttributeValue(CellStyleAttributes.FONT, getColumnHeaderFont());
		clStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, GUIHelper.getColor(PreferenceConstants.convertToRGB(hdBackColor)));
		clStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR,GUIHelper.getColor(PreferenceConstants.convertToRGB(hdForeColor)));
		rgb = PreferenceConstants.convertToRGB(hdBackColor);
		clStyle.setAttributeValue(CellStyleAttributes.GRADIENT_BACKGROUND_COLOR, GUIHelper.getColor(rgb));
		clStyle.setAttributeValue(CellStyleAttributes.GRADIENT_FOREGROUND_COLOR,  getGradientColor(rgb));
		clStyle.setAttributeValue(CellStyleAttributes.BORDER_STYLE, this.borderStyle);
		clStyle.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, HorizontalAlignmentEnum.CENTER);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, clStyle, DisplayMode.NORMAL, GridRegion.COLUMN_HEADER);

		Style cornerStyle = new Style();
		cornerStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, bgColor);
		cornerStyle.setAttributeValue(CellStyleAttributes.FOREGROUND_COLOR, fgColor);
		rgb = bgColor.getRGB();
		cornerStyle.setAttributeValue(CellStyleAttributes.GRADIENT_BACKGROUND_COLOR, bgColor);
		cornerStyle.setAttributeValue(CellStyleAttributes.GRADIENT_FOREGROUND_COLOR,  getGradientColor(rgb));
		cornerStyle.setAttributeValue(CellStyleAttributes.FONT, font);
		cornerStyle.setAttributeValue(CellStyleAttributes.BORDER_STYLE, this.borderStyle);
		cornerStyle.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, HorizontalAlignmentEnum.CENTER);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, cornerStyle, DisplayMode.NORMAL, GridRegion.CORNER);
		
		// configure the painter
//		configRegistry.registerConfigAttribute(
//				CellConfigAttributes.CELL_PAINTER, this.cellPainter,
//				DisplayMode.NORMAL, GridRegion.COLUMN_HEADER);
//		configRegistry.registerConfigAttribute(
//				CellConfigAttributes.CELL_PAINTER, this.cellPainter,
//				DisplayMode.NORMAL, GridRegion.COLUMN_GROUP_HEADER);
//		configRegistry.registerConfigAttribute(
//				CellConfigAttributes.CELL_PAINTER, this.cellPainter,
//				DisplayMode.NORMAL, GridRegion.CORNER);

	}

	private Color getGradientColor(RGB rgb) {
		double adj = 1.1;
		int red = Math.min((int)(rgb.red/adj), 255);
		int green = Math.min((int)(rgb.green/adj), 255);
		int blue = Math.min((int)(rgb.blue/adj), 255);
		return GUIHelper.getColor(new RGB(red, green, blue));
	}

	protected Font getGroupHeaderFont() {
		return grpFont;
	}

	protected Font getColumnHeaderFont() {
		return font;
	}

	private void addSelectedModeStyling(IConfigRegistry configRegistry) {
		// If sorting is enabled we still want the sort icon to be drawn.
		//			SortableHeaderTextPainter selectedHeaderPainter = new SortableHeaderTextPainter(decorator, false, false);
		//			configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, selectedHeaderPainter, DisplayMode.SELECT, GridRegion.COLUMN_HEADER);

	}

	private void addNormalModeStyling(IConfigRegistry configRegistry) {

    	boolean useGradients = DecisionTableUIPlugin.getDefault().getPreferenceStore().getBoolean(PreferenceConstants.USE_GRADIENTS);
		TextPainter txtPainter = new TextPainter(false, !useGradients, 3);
		txtPainter.setCalculateByTextHeight(true);
		ICellPainter wrappedPainter = null;
		if (useGradients) {
			GradientBackgroundPainter painter = new GradientBackgroundPainter(true);
			painter.setWrappedPainter(txtPainter);
			wrappedPainter = painter;
		} else {
			wrappedPainter = txtPainter;
		}
		LineBorderDecorator decorator = new LineBorderDecorator(wrappedPainter);
		SortableHeaderTextPainter headerPainter = new SortableHeaderTextPainter(decorator, CellEdgeEnum.RIGHT, true, 10, !true);
		this.cellPainter = headerPainter;
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, headerPainter, DisplayMode.NORMAL, GridRegion.COLUMN_HEADER);
	}
}