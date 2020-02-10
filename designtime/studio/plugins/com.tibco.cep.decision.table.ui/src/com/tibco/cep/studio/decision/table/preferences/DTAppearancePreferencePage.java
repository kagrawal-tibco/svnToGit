package com.tibco.cep.studio.decision.table.preferences;

import java.util.ArrayList;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.core.OverlayKey;
import com.tibco.cep.studio.core.OverlayKey.OVERLAYKEYTYPES;
import com.tibco.cep.studio.decision.table.configuration.DTStyleConstants;
import com.tibco.cep.studio.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.studio.decision.table.utils.DecisionTableUtil;

public class DTAppearancePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	
	public static final String Id = "com.tibco.cep.decision.table.dtAppearancePage";

	private static String[] colorPreferences = new String[10];
	private static String[] colorPreferenceNames = new String[10];
	
	static {
		colorPreferences[0] = PreferenceConstants.HEADER_GROUP_BACK_GROUND_COLOR;
		colorPreferences[1] = PreferenceConstants.HEADER_GROUP_FORE_GROUND_COLOR;
		colorPreferences[2] = PreferenceConstants.HEADER_BACK_GROUND_COLOR;
		colorPreferences[3] = PreferenceConstants.HEADER_FORE_GROUND_COLOR;
		colorPreferences[4] = PreferenceConstants.COND_DATA_BACK_GROUND_COLOR;
		colorPreferences[5] = PreferenceConstants.COND_DATA_FORE_GROUND_COLOR;
		colorPreferences[6] = PreferenceConstants.ACTION_DATA_BACK_GROUND_COLOR;
		colorPreferences[7] = PreferenceConstants.ACTION_DATA_FORE_GROUND_COLOR;
		colorPreferences[8] = PreferenceConstants.SELECTION_COLOR;
		colorPreferences[9] = PreferenceConstants.SELECTION_FG_COLOR;
		
		colorPreferenceNames[0] = Messages.getString("HEADER_GROUP_BACK_GROUND_COLOR");
		colorPreferenceNames[1] = Messages.getString("HEADER_GROUP_FORE_GROUND_COLOR");
		colorPreferenceNames[2] = Messages.getString("HEADER_BACK_GROUND_COLOR");
		colorPreferenceNames[3] = Messages.getString("HEADER_FORE_GROUND_COLOR");
		colorPreferenceNames[4] = Messages.getString("COND_DATA_BACK_GROUND_COLOR");
		colorPreferenceNames[5] = Messages.getString("COND_DATA_FORE_GROUND_COLOR");
		colorPreferenceNames[6] = Messages.getString("ACTION_DATA_BACK_GROUND_COLOR");
		colorPreferenceNames[7] = Messages.getString("ACTION_DATA_FORE_GROUND_COLOR");
		colorPreferenceNames[8] = Messages.getString("SELECTION_COLOR");
		colorPreferenceNames[9] = Messages.getString("SELECTION_FG_COLOR");
	}

	private OverlayPreferenceStore overlayPreferenceStore;
	private List dtTypesList;
	private Combo dtPalettesList;

	private ColorSelector 	textColorSelector;

	private Label condDataFontNameLabel;
	private Label actionDataFontNameLabel;

	private Label headerFontNameLabel;

	private Composite previewGroup;

	public DTAppearancePreferencePage() {
		super();
		setPreferenceStore(DecisionTableUIPlugin.getDefault().getPreferenceStore());
		overlayPreferenceStore = new OverlayPreferenceStore(getPreferenceStore(), getOverlayKeys());
	}

	private OverlayKey[] getOverlayKeys() {
		OverlayKey[] keys = new OverlayKey[colorPreferences.length + 6];
		for (int i = 0; i < colorPreferences.length; i++) {
			keys[i] = new OverlayKey(colorPreferences[i], OVERLAYKEYTYPES.STRING);
		}
		keys[colorPreferences.length] = new OverlayKey(PreferenceConstants.COND_DATA_FONT, OVERLAYKEYTYPES.STRING);
		keys[colorPreferences.length + 1] = new OverlayKey(PreferenceConstants.ACTION_DATA_FONT, OVERLAYKEYTYPES.STRING);
		keys[colorPreferences.length + 2] = new OverlayKey(PreferenceConstants.COLUMN_HEADER_FONT, OVERLAYKEYTYPES.STRING);
		keys[colorPreferences.length + 3] = new OverlayKey(PreferenceConstants.ALTERNATE_ROW_COLORS, OVERLAYKEYTYPES.BOOLEAN);
		keys[colorPreferences.length + 4] = new OverlayKey(PreferenceConstants.USE_GRADIENTS, OVERLAYKEYTYPES.BOOLEAN);
		keys[colorPreferences.length + 5] = new OverlayKey(PreferenceConstants.SELECTED_COLOR_PALETTE, OVERLAYKEYTYPES.STRING);
		return keys;
	}

	protected void createColorGroup(Composite parent) {
		
		Group composite = new Group(parent, SWT.NULL);
		composite.setText(Messages.getString("DT_COLORS"));
		composite.setLayout(new GridLayout(2, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(data);
		
		dtTypesList = new List(composite, SWT.BORDER);
		dtTypesList.setSize(200, 200);
		dtTypesList.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		dtTypesList.addSelectionListener(new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				updateColorSelector();
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
		
			}
		
		});
		
		Composite group = new Composite(composite, SWT.NULL);
		GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(groupData);
		group.setLayout(new GridLayout());

		Label colorLabel = new Label(group, SWT.NULL);
		colorLabel.setText(Messages.getString("DT_TEXT_COLOR"));
		
		textColorSelector = new ColorSelector(group);
		textColorSelector.setColorValue(new RGB(200,200,200));
		textColorSelector.addListener(new IPropertyChangeListener() {
		
			public void propertyChange(PropertyChangeEvent event) {
				int index = dtTypesList.getSelectionIndex();
				if (index != -1) {
					RGB rgb = textColorSelector.getColorValue();
					overlayPreferenceStore.setValue(colorPreferences[index], StringConverter.asString(rgb)); //$NON-NLS-1$ //$NON-NLS-2$
					overlayPreferenceStore.setValue(PreferenceConstants.SELECTED_COLOR_PALETTE, "");
				}
			}
		
		});
		
		dtTypesList.setItems(colorPreferenceNames);
		dtTypesList.select(0);
		
	}
	@Override
	protected void performApply() {
		performOk();
	}

	@Override
	protected void performDefaults() {
		overlayPreferenceStore.performDefaults();
		super.performDefaults();
		setPaletteSelectionFromStore();
		update();
	}

	private void setPaletteSelectionFromStore() {
		String selPal = overlayPreferenceStore.getString(PreferenceConstants.SELECTED_COLOR_PALETTE);
		if (selPal != null) {
			int idx = dtPalettesList.indexOf(selPal);
			if (idx != -1) {
				dtPalettesList.select(idx);
			}
		} else {
			dtPalettesList.select(0);
		}
	}

	@Override
	public boolean performOk() {
		boolean b = super.performOk();
		if (b) {
			b = overlayPreferenceStore.performOk();
			DecisionTableUtil.updateApparance();
		}
		return b;
	}

	private void update() {
		doUpdatePreview();
		updateColorSelector();
		updateFonts();
	}

	private void updateFonts() {
		String currentHeaderFont = overlayPreferenceStore.getString(PreferenceConstants.COLUMN_HEADER_FONT);
		if (currentHeaderFont == null || currentHeaderFont.length() == 0) {
			currentHeaderFont = getPreferenceStore().getString(PreferenceConstants.COLUMN_HEADER_FONT);
		}
		final FontData headerFontData = new FontData(currentHeaderFont);
		String headerFontString = StringConverter.asString(headerFontData);
		headerFontNameLabel.setText(headerFontString);
		
		String currentCondFont = overlayPreferenceStore.getString(PreferenceConstants.COND_DATA_FONT);
		if (currentCondFont == null || currentCondFont.length() == 0) {
			currentCondFont = getPreferenceStore().getString(PreferenceConstants.COND_DATA_FONT);
		}
		final FontData fontData = new FontData(currentCondFont);
		String condFontString = StringConverter.asString(fontData);
		condDataFontNameLabel.setText(condFontString);
		
		String currentActionFont = overlayPreferenceStore.getString(PreferenceConstants.ACTION_DATA_FONT);
		if (currentActionFont == null || currentActionFont.length() == 0) {
			currentActionFont = getPreferenceStore().getString(PreferenceConstants.ACTION_DATA_FONT);
		}
		final FontData actionFontData = new FontData(currentActionFont);
		String actionFontString = StringConverter.asString(actionFontData);
		actionDataFontNameLabel.setText(actionFontString);
		
	}

	private void updateColorSelector() {
		int selectionIndex = dtTypesList.getSelectionIndex();
		String rgbValue = overlayPreferenceStore.getString(colorPreferences[selectionIndex]);
		RGB rgb = StringConverter.asRGB(rgbValue);
		textColorSelector.setColorValue(rgb);
	}

	public void init(IWorkbench workbench) {
		overlayPreferenceStore.load();
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Label label = new Label(composite, SWT.NULL);
		label.setText(Messages.getString("DT_COLOR_FONTS"));
		GridData lData = new GridData(GridData.FILL_HORIZONTAL);
		label.setLayoutData(lData);
		
		final Button colorAlternateRowsBtn = new Button(composite, SWT.CHECK);
		colorAlternateRowsBtn.setText(Messages.getString("DT_ALTERNATE_COLORS"));
		colorAlternateRowsBtn.setSelection(overlayPreferenceStore.getBoolean(PreferenceConstants.ALTERNATE_ROW_COLORS));
		colorAlternateRowsBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				overlayPreferenceStore.setValue(PreferenceConstants.ALTERNATE_ROW_COLORS, colorAlternateRowsBtn.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		final Button useGradientsBtn = new Button(composite, SWT.CHECK);
		useGradientsBtn.setText(Messages.getString("DT_USE_GRADIENTS"));
		useGradientsBtn.setSelection(overlayPreferenceStore.getBoolean(PreferenceConstants.USE_GRADIENTS));
		useGradientsBtn.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				overlayPreferenceStore.setValue(PreferenceConstants.USE_GRADIENTS, useGradientsBtn.getSelection());
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		createPaletteGroup(composite);
		createColorGroup(composite);
		createFontsGroup(composite);
		update();

		return composite;

	}

	private void createPaletteGroup(Composite parent) {
		
		Group composite = new Group(parent, SWT.NULL);
		composite.setText(Messages.getString("DT_PALETTE"));
		composite.setLayout(new GridLayout(1, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(data);
		
		dtPalettesList = new Combo(composite, SWT.BORDER | SWT.DROP_DOWN | SWT.READ_ONLY);
		dtPalettesList.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));


		String[] palettes = new String[] {"Classic", "Standard", "Standard (no accent color)","Teal", "Teal on Gray", "Accessible (for the color blind)", "Muted", "System"};
		dtPalettesList.setItems(palettes);
		previewGroup = new Composite(composite, SWT.NULL);
		GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
		groupData.minimumHeight = 250;
		previewGroup.setLayoutData(groupData);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 7;
		previewGroup.setLayout(gridLayout);
		setPaletteSelectionFromStore();
		doUpdatePreview();
		dtPalettesList.addSelectionListener(new SelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				updatePreview();
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
		
			}
		
		});
		
	}

	protected void updatePreview() {
		if (dtPalettesList.getSelectionIndex() == -1) {
			return;
		}
		String selection = dtPalettesList.getItem(dtPalettesList.getSelectionIndex());
		overlayPreferenceStore.setValue(PreferenceConstants.SELECTED_COLOR_PALETTE, selection);

		if ("System".equals(selection)) {
			StringBuffer buf = new StringBuffer();
			buf.append(getRGBString(SWT.COLOR_TITLE_BACKGROUND_GRADIENT)).append(';').append(getRGBString(SWT.COLOR_TITLE_BACKGROUND)).append(';');
			buf.append(getRGBString(SWT.COLOR_INFO_BACKGROUND)).append(';').append(getRGBString(SWT.COLOR_INFO_FOREGROUND)).append(';').append(getRGBString(SWT.COLOR_TITLE_FOREGROUND)).append(';');
			buf.append(getRGBString(SWT.COLOR_TITLE_INACTIVE_BACKGROUND)).append(';').append(getRGBString(SWT.COLOR_LIST_SELECTION)).append(';').append(getRGBString(SWT.COLOR_TITLE_BACKGROUND));
			DTStyleConstants.setDTColorPalette(buf.toString(), overlayPreferenceStore, false);
//			overlayPreferenceStore.setValue(PreferenceConstants.HEADER_BACK_GROUND_COLOR, getRGBString(SWT.COLOR_TITLE_BACKGROUND_GRADIENT));
//			overlayPreferenceStore.setValue(PreferenceConstants.HEADER_GROUP_BACK_GROUND_COLOR, getRGBString(SWT.COLOR_TITLE_BACKGROUND));
//			overlayPreferenceStore.setValue(PreferenceConstants.HEADER_FORE_GROUND_COLOR, getRGBString(SWT.COLOR_TITLE_FOREGROUND));
//			overlayPreferenceStore.setValue(PreferenceConstants.HEADER_GROUP_FORE_GROUND_COLOR, getRGBString(SWT.COLOR_TITLE_FOREGROUND));
//			overlayPreferenceStore.setValue(PreferenceConstants.COND_DATA_BACK_GROUND_COLOR, getRGBString(SWT.COLOR_LIST_BACKGROUND));
//			overlayPreferenceStore.setValue(PreferenceConstants.ACTION_DATA_BACK_GROUND_COLOR, getRGBString(SWT.COLOR_LIST_BACKGROUND));
//			overlayPreferenceStore.setValue(PreferenceConstants.COND_DATA_FORE_GROUND_COLOR, getRGBString(SWT.COLOR_LIST_FOREGROUND));
//			overlayPreferenceStore.setValue(PreferenceConstants.ACTION_DATA_FORE_GROUND_COLOR, getRGBString(SWT.COLOR_LIST_FOREGROUND));
//			overlayPreferenceStore.setValue(PreferenceConstants.SELECTION_COLOR, getRGBString(SWT.COLOR_LIST_SELECTION));
//			overlayPreferenceStore.setValue(PreferenceConstants.SELECTION_FG_COLOR, getRGBString(SWT.COLOR_LIST_SELECTION_TEXT));

			overlayPreferenceStore.setDefault(PreferenceConstants.COLUMN_HEADER_FONT, JFaceResources.getBannerFont().getFontData()[0].toString());
			overlayPreferenceStore.setDefault(PreferenceConstants.COND_DATA_FONT, JFaceResources.getDefaultFont().getFontData()[0].toString());
			overlayPreferenceStore.setDefault(PreferenceConstants.ACTION_DATA_FONT, JFaceResources.getTextFont().getFontData()[0].toString());
		} else {
			DTStyleConstants.setDTColorPalette(DTStyleConstants.BUILT_IN_PALETTES[dtPalettesList.getSelectionIndex()], overlayPreferenceStore, false);
		}
		doUpdatePreview();
		updateColorSelector();
	}

	private String getRGBString(int colorIdx) {
		RGB rgb = Display.getDefault().getSystemColor(colorIdx).getRGB();
		return rgb.red+","+rgb.green+","+rgb.blue;
	}

	private void doUpdatePreview() {
		Control[] children = previewGroup.getChildren();
		for (Control control : children) {
			control.dispose();
		}
		
		java.util.List<RGB> displayedColors = new ArrayList<RGB>();
		for (int i=0; i<colorPreferences.length; i++) {
			RGB rgb = PreferenceConstants.convertToRGB(overlayPreferenceStore.getString(colorPreferences[i]));
			if (displayedColors.contains(rgb)) {
				continue;
			}
			displayedColors.add(rgb);
			Label l = new Label(previewGroup, SWT.BORDER);
			l.setSize(50,50);
			l.setLayoutData(new GridData(50, 50));
			l.setBackground(new Color(null, rgb));
		}
		previewGroup.layout();
	}

	private void createFontsGroup(Composite composite) {
		Group fonts = new Group(composite, SWT.NULL);
		fonts.setLayout(new GridLayout(3, false));
		fonts.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fonts.setText(Messages.getString("DT_FONTS"));

		// condition data font:
		Label headerFontLabel = new Label(fonts, SWT.NULL);
		headerFontLabel.setText(Messages.getString("COLUMN_HEADER_FONT"));
		headerFontNameLabel = new Label(fonts, SWT.NULL);
		headerFontNameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		String currentHeaderFont = overlayPreferenceStore.getString(PreferenceConstants.COLUMN_HEADER_FONT);
		if (currentHeaderFont == null || currentHeaderFont.length() == 0) {
			currentHeaderFont = getPreferenceStore().getString(PreferenceConstants.COLUMN_HEADER_FONT);
		}
		FontData headerFontData = new FontData(currentHeaderFont);
		String headerFontString = StringConverter.asString(headerFontData);
		headerFontNameLabel.setText(headerFontString);
		final Button changeHeaderFontButton = new Button(fonts, SWT.NULL);
		changeHeaderFontButton.setText("Change...");
		changeHeaderFontButton.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent e) {
				String currentHeaderFont = overlayPreferenceStore.getString(PreferenceConstants.COLUMN_HEADER_FONT);
				if (currentHeaderFont == null || currentHeaderFont.length() == 0) {
					currentHeaderFont = getPreferenceStore().getString(PreferenceConstants.COLUMN_HEADER_FONT);
				}
				FontDialog dialog = new FontDialog(changeHeaderFontButton.getShell());
				final FontData fontData = new FontData(currentHeaderFont);
				FontData[] currentFont = new FontData[] { fontData };
				dialog.setFontList(currentFont);
//				String rgbString = overlayPreferenceStore.getString(PreferenceConstants.COND_DATA_FORE_GROUND_COLOR);
//				RGB rgb = StringConverter.asRGB(rgbString);
//				dialog.setRGB(rgb);

				FontData selectedFont = dialog.open();
				if (selectedFont != null) {
					selectedFont.toString();
//					RGB color = dialog.getRGB();
//					if (color != null) {
//						overlayPreferenceStore.setValue(PreferenceConstants.COND_DATA_FORE_GROUND_COLOR, StringConverter.asString(color));
//						updateColorSelector();
//					}
					overlayPreferenceStore.setValue(PreferenceConstants.COLUMN_HEADER_FONT, selectedFont.toString());
					headerFontNameLabel.setText(StringConverter.asString(selectedFont));
				}
			}

			public void widgetDefaultSelected(SelectionEvent e) {
			}

		});

		// condition data font:
		Label condDataFontLabel = new Label(fonts, SWT.NULL);
		condDataFontLabel.setText(Messages.getString("COND_DATA_FONT"));
		condDataFontNameLabel = new Label(fonts, SWT.NULL);
		condDataFontNameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		String currentCondFont = overlayPreferenceStore.getString(PreferenceConstants.COND_DATA_FONT);
		if (currentCondFont == null || currentCondFont.length() == 0) {
			currentCondFont = getPreferenceStore().getString(PreferenceConstants.COND_DATA_FONT);
		}
		FontData fontData = new FontData(currentCondFont);
		String condFontString = StringConverter.asString(fontData);
		condDataFontNameLabel.setText(condFontString);
		final Button changeCondFontButton = new Button(fonts, SWT.NULL);
		changeCondFontButton.setText("Change...");
		changeCondFontButton.addSelectionListener(new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				String currentCondFont = overlayPreferenceStore.getString(PreferenceConstants.COND_DATA_FONT);
				if (currentCondFont == null || currentCondFont.length() == 0) {
					currentCondFont = getPreferenceStore().getString(PreferenceConstants.COND_DATA_FONT);
				}
				FontDialog dialog = new FontDialog(changeCondFontButton.getShell());
				final FontData fontData = new FontData(currentCondFont);
				FontData[] currentFont = new FontData[] { fontData };
				dialog.setFontList(currentFont);
				String rgbString = overlayPreferenceStore.getString(PreferenceConstants.COND_DATA_FORE_GROUND_COLOR);
				RGB rgb = StringConverter.asRGB(rgbString);
				dialog.setRGB(rgb);

				FontData selectedFont = dialog.open();
				if (selectedFont != null) {
					selectedFont.toString();
                    RGB color = dialog.getRGB();
                    if (color != null) {
                    	overlayPreferenceStore.setValue(PreferenceConstants.COND_DATA_FORE_GROUND_COLOR, StringConverter.asString(color));
                    	updateColorSelector();
                    }
                    overlayPreferenceStore.setValue(PreferenceConstants.COND_DATA_FONT, selectedFont.toString());
                    condDataFontNameLabel.setText(StringConverter.asString(selectedFont));
				}
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		
		});
		
		// action data font:
		Label actionDataFontLabel = new Label(fonts, SWT.NULL);
		actionDataFontLabel.setText(Messages.getString("ACTION_DATA_FONT"));
		actionDataFontNameLabel = new Label(fonts, SWT.NULL);
		String currentActionFont = overlayPreferenceStore.getString(PreferenceConstants.ACTION_DATA_FONT);
		if (currentActionFont == null || currentActionFont.length() == 0) {
			currentActionFont = getPreferenceStore().getString(PreferenceConstants.ACTION_DATA_FONT);
		}
		String actionFontString = StringConverter.asString(new FontData(currentActionFont));
		actionDataFontNameLabel.setText(actionFontString);
		final Button changeActionFontButton = new Button(fonts, SWT.NULL);
		changeActionFontButton.setText("Change...");
		changeActionFontButton.addSelectionListener(new SelectionListener() {
		
			public void widgetSelected(SelectionEvent e) {
				String currentActionFont = overlayPreferenceStore.getString(PreferenceConstants.ACTION_DATA_FONT);
				if (currentActionFont == null || currentActionFont.length() == 0) {
					currentActionFont = getPreferenceStore().getString(PreferenceConstants.ACTION_DATA_FONT);
				}
				FontDialog dialog = new FontDialog(changeActionFontButton.getShell());
				final FontData actionFontData = new FontData(currentActionFont);
				FontData[] currentFont = new FontData[] { actionFontData };
				dialog.setFontList(currentFont);
				String rgbString = overlayPreferenceStore.getString(PreferenceConstants.ACTION_DATA_FORE_GROUND_COLOR);
				RGB rgb = StringConverter.asRGB(rgbString);
				dialog.setRGB(rgb);

				FontData selectedFont = dialog.open();
				if (selectedFont != null) {
					selectedFont.toString();
                    RGB color = dialog.getRGB();
                    if (color != null) {
                    	overlayPreferenceStore.setValue(PreferenceConstants.ACTION_DATA_FORE_GROUND_COLOR, StringConverter.asString(color));
                    	updateColorSelector();
                    }
                    overlayPreferenceStore.setValue(PreferenceConstants.ACTION_DATA_FONT, selectedFont.toString());
                    actionDataFontNameLabel.setText(StringConverter.asString(selectedFont));
				}
			}
		
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		
		});
	}

}
