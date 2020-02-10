package com.tibco.cep.studio.decision.table.configuration;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.nebula.widgets.nattable.util.GUIHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

import com.tibco.cep.studio.decision.table.preferences.PreferenceConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;

/**
 * Default style constants used in styling the decision table editor
 * 
 * @author rhollom
 *
 */
public class DTStyleConstants {

	public static final String CLASSIC_PALETTE = "153,180,209;181,200,221;255,255,215;30,30,30;30,30,30;240,240,240;181,200,221;25,76,150";
	public static final String STANDARD_PALETTE = "48,118,174;0,85,150;181,204,222;255,255,255;33,33,33;240,240,240;190,120,0;255,255,255";
	public static final String BASIC_PALETTE_NO_ACCENT = "48,118,174;0,85,150;181,204,222;255,255,255;33,33,33;240,240,240;0,85,150;255,255,255";
//	public static final String COLOR_BLIND_PALETTE = "86,180,233;0,114,178;240,228,66;255,255,255;30,30,30;240,240,240;0,158,115;255,255,255";
	public static final String COLOR_BLIND_PALETTE = "213,94,0;0,158,115;240,228,66;255,255,255;30,30,30;240,240,240;230,159,0;255,255,255";
//	public static final String INDIGO_PALETTE = "63,81,181;48,63,159;197,202,233;255,255,255;33,33,33;240,240,240;83,109,254;255,255,255";
	public static final String TEAL_PALETTE = "0,150,136;0,121,107;178,223,219;255,255,255;33,33,33;240,240,240;0,121,107;255,255,255";
//	public static final String GREEN_PALETTE = "76,175,80;56,142,60;200,230,201;255,255,255;33,33,33;240,240,240;76,175,80;255,255,255";
//	public static final String PURPLE_PALETTE = "124,77,255;81,45,168;209,196,233;255,255,255;33,33,33;240,240,240;124,77,255;255,255,255";
	public static final String RED_PALETTE = "244,67,54;211,47,47;255,205,210;255,255,255;33,33,33;240,240,240;255,82,82;255,255,255";
	
	public static final String TEAL_ON_GRAY_PALETTE = "0,150,136;0,121,107;178,223,219;255,255,255;33,33,33;240,240,240;117,117,117;255,255,255";
//	public static final String GREEN_ON_PURPLE_PALETTE = "76,175,80;56,142,60;200,230,201;255,255,255;33,33,33;240,240,240;124,77,255;255,255,255";
//	public static final String PURPLE_ON_GREEN_PALETTE = "124,77,255;81,45,168;209,196,233;255,255,255;33,33,33;240,240,240;76,175,80;255,255,255";
//	public static final String RED_ON_GREEN_PALETTE = "244,67,54;211,47,47;255,205,210;255,255,255;33,33,33;240,240,240;76,175,80;255,255,255";
	public static final String MUTED_PALETTE = "101,106,117;86,93,107;214,216,221;255,255,255;33,33,33;240,240,240;160,149,124;255,255,255";
//	public static final String SB_PALETTE = "134,53,146;111,44,137;218,194,222;255,255,255;33,33,33;240,230,240;164,104,173;255,255,255";

	public static final String[] BUILT_IN_PALETTES = new String[9];
	
	public static final int IDX_PRIMARY_COLOR = 0;
	public static final int IDX_DARK_PRIMARY_COLOR = 1;
	public static final int IDX_LIGHT_PRIMARY_COLOR = 2;
	public static final int IDX_TEXT_ON_PRIMARY_COLOR = 3;
	public static final int IDX_TEXT_COLOR = 4;
	public static final int IDX_BORDER_COLOR = 5;
	public static final int IDX_ACCENT_COLOR = 6;
	public static final int IDX_ACCENT_FG_COLOR = 7;

	public static final String[] DEFAULT_PALETTE = new String[IDX_ACCENT_FG_COLOR+1];
	public static final String DEFAULT_DARK_PRIMARY_COLOR = "181,200,221";
	public static final String DEFAULT_PRIMARY_COLOR = "153,180,209";
	public static final String DEFAULT_LIGHT_PRIMARY_COLOR = "255,255,215";
	public static final String DEFAULT_TEXT_ON_PRIMARY_COLOR = "25,76,150";
	public static final String DEFAULT_TEXT_COLOR = "30,30,30";
	public static final String DEFAULT_BORDER_COLOR = "188,188,188";
	public static final String DEFAULT_ACCENT_COLOR = "153,180,209";
//	public static final String DEFAULT_DARK_PRIMARY_COLOR = "13,62,91";
//	public static final String DEFAULT_PRIMARY_COLOR = "50,103,134";
//	public static final String DEFAULT_LIGHT_PRIMARY_COLOR = "196,200,233";
//	public static final String DEFAULT_TEXT_ON_PRIMARY_COLOR = "230,240,250";
//	public static final String DEFAULT_TEXT_COLOR = "20,20,20";
//	public static final String DEFAULT_BORDER_COLOR = "188,188,188";
//	public static final String DEFAULT_ACCENT_COLOR = "181, 117, 38";

	static {
		DEFAULT_PALETTE[IDX_PRIMARY_COLOR] = DEFAULT_PRIMARY_COLOR;
		DEFAULT_PALETTE[IDX_DARK_PRIMARY_COLOR] = DEFAULT_DARK_PRIMARY_COLOR;
		DEFAULT_PALETTE[IDX_LIGHT_PRIMARY_COLOR] = DEFAULT_LIGHT_PRIMARY_COLOR;
		DEFAULT_PALETTE[IDX_TEXT_ON_PRIMARY_COLOR] = DEFAULT_TEXT_ON_PRIMARY_COLOR;
		DEFAULT_PALETTE[IDX_TEXT_COLOR] = DEFAULT_TEXT_COLOR;
		DEFAULT_PALETTE[IDX_BORDER_COLOR] = DEFAULT_BORDER_COLOR;
		DEFAULT_PALETTE[IDX_ACCENT_COLOR] = DEFAULT_ACCENT_COLOR;
		
		BUILT_IN_PALETTES[0] = CLASSIC_PALETTE;
		BUILT_IN_PALETTES[1] = STANDARD_PALETTE;
		BUILT_IN_PALETTES[2] = BASIC_PALETTE_NO_ACCENT;
		BUILT_IN_PALETTES[3] = TEAL_PALETTE;
		BUILT_IN_PALETTES[4] = TEAL_ON_GRAY_PALETTE;
		BUILT_IN_PALETTES[5] = COLOR_BLIND_PALETTE;
		BUILT_IN_PALETTES[6] = MUTED_PALETTE;
//		BUILT_IN_PALETTES[7] = COLOR_BLIND_PALETTE;

//		BUILT_IN_PALETTES[0] = CLASSIC_PALETTE;
//		BUILT_IN_PALETTES[1] = BASIC_PALETTE;
//		BUILT_IN_PALETTES[2] = INDIGO_PALETTE;
//		BUILT_IN_PALETTES[3] = TEAL_PALETTE;
//		BUILT_IN_PALETTES[4] = GREEN_PALETTE;
//		BUILT_IN_PALETTES[5] = PURPLE_PALETTE;
//		BUILT_IN_PALETTES[6] = RED_PALETTE;
//		BUILT_IN_PALETTES[7] = COLOR_BLIND_PALETTE;
//		BUILT_IN_PALETTES[8] = TEAL_ON_GRAY_PALETTE;
//		BUILT_IN_PALETTES[9] = GREEN_ON_PURPLE_PALETTE;
//		BUILT_IN_PALETTES[10] = PURPLE_ON_GREEN_PALETTE;
//		BUILT_IN_PALETTES[11] = RED_ON_GREEN_PALETTE;
//		BUILT_IN_PALETTES[12] = MUTED_PALETTE;
//		BUILT_IN_PALETTES[13] = SB_PALETTE;
	}
	
	private static IPreferenceStore prefStore = DecisionTableUIPlugin.getDefault().getPreferenceStore();

	public static final Color FILTER_ROW_BG_COLOR = GUIHelper.getColor(204, 217, 232);
	public static final Color FILTER_ROW_FG_COLOR = GUIHelper.COLOR_DARK_GRAY;
	public static final String FONT_NAME = "Tahoma";
	public static final Font DEFAULT_FONT = GUIHelper.getFont(new FontData(FONT_NAME, 8, SWT.NORMAL));

	public static void setDTColorPalette(String colorString) {
		setDTColorPalette(colorString, prefStore, false);
	}
	
	public static void setDefaultDTColorPalette(String colorString) {
		setDTColorPalette(colorString, prefStore, true);
	}
	
	public static void setDTColorPalette(String colorString,IPreferenceStore prefStore, boolean defaultVals) {
		String[] colors = colorString.split(";");
		for (int i = 0; i < colors.length; i++) {
			DEFAULT_PALETTE[i] = colors[i].trim();
		}
		if (defaultVals) {
			setDefaultPreferencesFromPalette();
		} else {
			setPreferencesFromPalette(prefStore);
		}
	}
	
	public static void resetPreferencesFromPalette() {
		setPreferencesFromPalette(prefStore);
	}

	public static void setPreferencesFromPalette(IPreferenceStore prefStore) {
		prefStore.setValue(PreferenceConstants.HEADER_BACK_GROUND_COLOR, DEFAULT_PALETTE[IDX_PRIMARY_COLOR]);
		prefStore.setValue(PreferenceConstants.HEADER_GROUP_BACK_GROUND_COLOR, DEFAULT_PALETTE[IDX_DARK_PRIMARY_COLOR]);
		prefStore.setValue(PreferenceConstants.HEADER_FORE_GROUND_COLOR, DEFAULT_PALETTE[IDX_TEXT_ON_PRIMARY_COLOR]);
		prefStore.setValue(PreferenceConstants.HEADER_GROUP_FORE_GROUND_COLOR, DEFAULT_PALETTE[IDX_TEXT_ON_PRIMARY_COLOR]);
		prefStore.setValue(PreferenceConstants.COND_DATA_BACK_GROUND_COLOR, DEFAULT_PALETTE[IDX_LIGHT_PRIMARY_COLOR]);
		prefStore.setValue(PreferenceConstants.ACTION_DATA_BACK_GROUND_COLOR, DEFAULT_PALETTE[IDX_BORDER_COLOR]);
		prefStore.setValue(PreferenceConstants.COND_DATA_FORE_GROUND_COLOR, DEFAULT_PALETTE[IDX_TEXT_COLOR]);
		prefStore.setValue(PreferenceConstants.ACTION_DATA_FORE_GROUND_COLOR, DEFAULT_PALETTE[IDX_TEXT_COLOR]);
		prefStore.setValue(PreferenceConstants.SELECTION_COLOR, DEFAULT_PALETTE[IDX_ACCENT_COLOR]);
		prefStore.setValue(PreferenceConstants.SELECTION_FG_COLOR, DEFAULT_PALETTE[IDX_ACCENT_FG_COLOR]);
	}
	
	public static void setDefaultPreferencesFromPalette() {
		prefStore.setDefault(PreferenceConstants.HEADER_BACK_GROUND_COLOR, DEFAULT_PALETTE[IDX_PRIMARY_COLOR]);
		prefStore.setDefault(PreferenceConstants.HEADER_GROUP_BACK_GROUND_COLOR, DEFAULT_PALETTE[IDX_DARK_PRIMARY_COLOR]);
		prefStore.setDefault(PreferenceConstants.HEADER_FORE_GROUND_COLOR, DEFAULT_PALETTE[IDX_TEXT_ON_PRIMARY_COLOR]);
		prefStore.setDefault(PreferenceConstants.HEADER_GROUP_FORE_GROUND_COLOR, DEFAULT_PALETTE[IDX_TEXT_ON_PRIMARY_COLOR]);
		prefStore.setDefault(PreferenceConstants.COND_DATA_BACK_GROUND_COLOR, DEFAULT_PALETTE[IDX_LIGHT_PRIMARY_COLOR]);
		prefStore.setDefault(PreferenceConstants.ACTION_DATA_BACK_GROUND_COLOR, DEFAULT_PALETTE[IDX_BORDER_COLOR]);
		prefStore.setDefault(PreferenceConstants.COND_DATA_FORE_GROUND_COLOR, DEFAULT_PALETTE[IDX_TEXT_COLOR]);
		prefStore.setDefault(PreferenceConstants.ACTION_DATA_FORE_GROUND_COLOR, DEFAULT_PALETTE[IDX_TEXT_COLOR]);
		prefStore.setDefault(PreferenceConstants.SELECTION_COLOR, DEFAULT_PALETTE[IDX_ACCENT_COLOR]);
		prefStore.setDefault(PreferenceConstants.SELECTION_FG_COLOR, DEFAULT_PALETTE[IDX_ACCENT_FG_COLOR]);
	}
	
	
}
