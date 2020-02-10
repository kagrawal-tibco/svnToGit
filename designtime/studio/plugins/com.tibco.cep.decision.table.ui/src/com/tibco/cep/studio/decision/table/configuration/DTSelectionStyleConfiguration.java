package com.tibco.cep.studio.decision.table.configuration;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.selection.config.DefaultSelectionStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle;
import org.eclipse.nebula.widgets.nattable.style.BorderStyle.LineStyleEnum;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;

import com.tibco.cep.studio.decision.table.editor.DecisionTableDesignViewer;
import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/** 
 * Sets up default selection styling for the decision/exception tables
 * 
 * @author rhollom
 *
 */
public class DTSelectionStyleConfiguration extends
		DefaultSelectionStyleConfiguration {

	private static IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();

	@Override
	protected void configureSelectionStyle(IConfigRegistry configRegistry) {
		configure();
		String conditionFont = prefStore.getString(PreferenceConstants.COND_DATA_FONT);
		String actionFont = prefStore.getString(PreferenceConstants.ACTION_DATA_FONT);
		FontData condFontData = new FontData(conditionFont);
		FontData actionFontData = new FontData(actionFont);
		condFontData.setStyle(SWT.BOLD | SWT.ITALIC);
		actionFontData.setStyle(SWT.BOLD | SWT.ITALIC);
		Style condFgStyle = new Style();
		condFgStyle.setAttributeValue(CellStyleAttributes.FONT, GUIHelper.getFont(condFontData));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, condFgStyle,DisplayMode.SELECT,DecisionTableDesignViewer.CONDITIONS_GROUP_LABEL);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, condFgStyle,DisplayMode.SELECT,DecisionTableDesignViewer.CONDITIONS_GROUP_ALT_LABEL);

		Style actionFgStyle = new Style();
		actionFgStyle.setAttributeValue(CellStyleAttributes.FONT, GUIHelper.getFont(actionFontData));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, actionFgStyle,DisplayMode.SELECT,DecisionTableDesignViewer.ACTIONS_GROUP_LABEL);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, actionFgStyle,DisplayMode.SELECT,DecisionTableDesignViewer.ACTIONS_GROUP_ALT_LABEL);
		super.configureSelectionStyle(configRegistry);
	}
	
	public DTSelectionStyleConfiguration() {
		super();
	}

	private void configure() {
		Color selectedBg = GUIHelper.getColor(PreferenceConstants.convertToRGB(prefStore.getString(PreferenceConstants.SELECTION_COLOR)));
		Color selectedFg = GUIHelper.getColor(PreferenceConstants.convertToRGB(prefStore.getString(PreferenceConstants.SELECTION_FG_COLOR)));
		this.selectionBgColor = selectedBg;
		this.selectionFgColor = selectedFg;
		this.anchorBorderStyle = new BorderStyle(1, selectedFg, LineStyleEnum.DASHED);
		this.anchorBgColor = GUIHelper.COLOR_DARK_GRAY;
		this.anchorFgColor = GUIHelper.COLOR_WHITE;

		this.fullySelectedHeaderBgColor = selectedBg;
		this.selectedHeaderBgColor = selectedBg;
		this.selectedHeaderFgColor = selectedFg;
		this.selectedHeaderBorderStyle = new BorderStyle(1, selectedFg, LineStyleEnum.DASHED);
		this.anchorGridBorderStyle = new BorderStyle(1, GUIHelper.COLOR_BLACK, LineStyleEnum.DOTTED);
		String headerFont = prefStore.getString(PreferenceConstants.COLUMN_HEADER_FONT);
		FontData headerFontData = new FontData(headerFont);
		headerFontData.setStyle(SWT.BOLD | SWT.ITALIC);
		this.selectedHeaderFont = GUIHelper.getFont(headerFontData);
	}

}
