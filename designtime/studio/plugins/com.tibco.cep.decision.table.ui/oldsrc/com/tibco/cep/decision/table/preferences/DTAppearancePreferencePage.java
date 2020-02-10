package com.tibco.cep.decision.table.preferences;

import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
import com.tibco.cep.decision.table.ui.utils.Messages;
import com.tibco.cep.studio.core.OverlayKey;
import com.tibco.cep.studio.core.OverlayKey.OVERLAYKEYTYPES;
import com.tibco.cep.studio.ui.preferences.OverlayPreferenceStore;

public class DTAppearancePreferencePage extends PreferencePage implements IWorkbenchPreferencePage {
	
	public static final String Id = "com.tibco.cep.decision.table.dtAppearancePage";

	private static String[] colorPreferences = new String[8];
	private static String[] colorPreferenceNames = new String[8];
	
	static {
		colorPreferences[0] = PreferenceConstants.COND_HEADER_BACK_GROUND_COLOR;
		colorPreferences[1] = PreferenceConstants.COND_HEADER_FORE_GROUND_COLOR;
		colorPreferences[2] = PreferenceConstants.ACTION_HEADER_BACK_GROUND_COLOR;
		colorPreferences[3] = PreferenceConstants.ACTION_HEADER_FORE_GROUND_COLOR;
		colorPreferences[4] = PreferenceConstants.VER_COND_DATA_BACK_GROUND_COLOR;
		colorPreferences[5] = PreferenceConstants.VER_COND_DATA_FORE_GROUND_COLOR;
//		colorPreferences[6] = PreferenceConstants.HORZ_COND_DATA_BACK_GROUND_COLOR;
//		colorPreferences[7] = PreferenceConstants.HORZ_COND_DATA_FORE_GROUND_COLOR;
		colorPreferences[6] = PreferenceConstants.ACTION_DATA_BACK_GROUND_COLOR;
		colorPreferences[7] = PreferenceConstants.ACTION_DATA_FORE_GROUND_COLOR;
		
		colorPreferenceNames[0] = Messages.getString("COND_HEADER_BACK_GROUND_COLOR");
		colorPreferenceNames[1] = Messages.getString("COND_HEADER_FORE_GROUND_COLOR");
		colorPreferenceNames[2] = Messages.getString("ACTION_HEADER_BACK_GROUND_COLOR");
		colorPreferenceNames[3] = Messages.getString("ACTION_HEADER_FORE_GROUND_COLOR");
		colorPreferenceNames[4] = Messages.getString("VER_COND_DATA_BACK_GROUND_COLOR");
		colorPreferenceNames[5] = Messages.getString("VER_COND_DATA_FORE_GROUND_COLOR");
//		colorPreferenceNames[6] = Messages.getString("HORZ_COND_DATA_BACK_GROUND_COLOR");
//		colorPreferenceNames[7] = Messages.getString("HORZ_COND_DATA_FORE_GROUND_COLOR");
		colorPreferenceNames[6] = Messages.getString("ACTION_DATA_BACK_GROUND_COLOR");
		colorPreferenceNames[7] = Messages.getString("ACTION_DATA_FORE_GROUND_COLOR");
	}

	private OverlayPreferenceStore overlayPreferenceStore;
	private List dtTypesList;

	private ColorSelector 	textColorSelector;

	private Label condDataFontNameLabel;
	private Label actionDataFontNameLabel;

	public DTAppearancePreferencePage() {
		super();
		setPreferenceStore(DecisionTableUIPlugin.getDefault().getPreferenceStore());
		overlayPreferenceStore = new OverlayPreferenceStore(getPreferenceStore(), getOverlayKeys());
	}

	private OverlayKey[] getOverlayKeys() {
		OverlayKey[] keys = new OverlayKey[colorPreferences.length + 2];
		for (int i = 0; i < colorPreferences.length; i++) {
			keys[i] = new OverlayKey(colorPreferences[i], OVERLAYKEYTYPES.STRING);
		}
		keys[colorPreferences.length] = new OverlayKey(PreferenceConstants.COND_DATA_FONT, OVERLAYKEYTYPES.STRING);
		keys[colorPreferences.length + 1] = new OverlayKey(PreferenceConstants.ACTION_DATA_FONT, OVERLAYKEYTYPES.STRING);
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
		update();
	}

	@Override
	public boolean performOk() {
		boolean b = super.performOk();
		if (b) {
			return overlayPreferenceStore.performOk();
		}
		return b;
	}

	private void update() {
		updateColorSelector();
		updateFonts();
	}

	private void updateFonts() {
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
		
		createColorGroup(composite);
		createFontsGroup(composite);
		update();

		return composite;

	}

	private void createFontsGroup(Composite composite) {
		Group fonts = new Group(composite, SWT.NULL);
		fonts.setLayout(new GridLayout(3, false));
		fonts.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fonts.setText(Messages.getString("DT_FONTS"));

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
				String rgbString = overlayPreferenceStore.getString(PreferenceConstants.VER_COND_DATA_FORE_GROUND_COLOR);
				RGB rgb = StringConverter.asRGB(rgbString);
				dialog.setRGB(rgb);

				FontData selectedFont = dialog.open();
				if (selectedFont != null) {
					selectedFont.toString();
                    RGB color = dialog.getRGB();
                    if (color != null) {
                    	overlayPreferenceStore.setValue(PreferenceConstants.VER_COND_DATA_FORE_GROUND_COLOR, StringConverter.asString(color));
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
