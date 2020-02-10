package com.tibco.cep.studio.tester.ui.preferences;

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

import com.tibco.cep.studio.core.OverlayKey;
import com.tibco.cep.studio.core.OverlayKey.OVERLAYKEYTYPES;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.tester.ui.utils.Messages;
import com.tibco.cep.studio.ui.preferences.OverlayPreferenceStore;

public class TesterAppearancePreferencePage extends PreferencePage implements IWorkbenchPreferencePage, SelectionListener{

	private static String[] colorPreferences = new String[2];
	private static String[] colorPreferenceNames = new String[2];
	
	static {
		
		colorPreferences[0] = TesterPreferenceConstants.TEST_RESULT_CHANGED_VALUE_BACK_GROUND_COLOR;
		colorPreferences[1] = TesterPreferenceConstants.TEST_RESULT_CHANGED_VALUE_FORE_GROUND_COLOR;

		colorPreferenceNames[0] = Messages.getString("TESTRESULT_CHANGED_VALUE_BACK_GROUND_COLOR");
		colorPreferenceNames[1] = Messages.getString("TESTRESULT_CHANGED_VALUE_FORE_GROUND_COLOR");
	}

	private OverlayPreferenceStore overlayPreferenceStore;
	private List typesList;

	private ColorSelector textColorSelector;
	private Label modifiedDataFontNameLabel;
	private Button changeModifiedValueFontButton;

	public TesterAppearancePreferencePage() {
		super();
		setPreferenceStore(StudioTesterUIPlugin.getDefault().getPreferenceStore());
		overlayPreferenceStore = new OverlayPreferenceStore(getPreferenceStore(), getOverlayKeys());
	}

	private OverlayKey[] getOverlayKeys() {
		OverlayKey[] keys = new OverlayKey[colorPreferences.length + 1];
		for (int i = 0; i < colorPreferences.length; i++) {
			keys[i] = new OverlayKey(colorPreferences[i], OVERLAYKEYTYPES.STRING);
		}
		keys[colorPreferences.length] = new OverlayKey(TesterPreferenceConstants.TEST_RESULT_CHANGED_FONT, OVERLAYKEYTYPES.STRING);
		return keys;
	}

	protected void createColorGroup(Composite parent) {
		Group composite = new Group(parent, SWT.NULL);
		composite.setText(Messages.getString("DT_COLORS"));
		composite.setLayout(new GridLayout(2, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		composite.setLayoutData(data);

		typesList = new List(composite, SWT.BORDER);
		typesList.setSize(200, 200);
		typesList.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		typesList.addSelectionListener(this);

		Composite group = new Composite(composite, SWT.NULL);
		GridData groupData = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(groupData);
		group.setLayout(new GridLayout());

		Label colorLabel = new Label(group, SWT.NULL);
		colorLabel.setText(Messages.getString("DT_TEXT_COLOR"));

		textColorSelector = new ColorSelector(group);
		textColorSelector.setColorValue(new RGB(200, 200, 200));
		textColorSelector.addListener(new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				int index = typesList.getSelectionIndex();
				if (index != -1) {
					RGB rgb = textColorSelector.getColorValue();
					overlayPreferenceStore.setValue(colorPreferences[index],
							StringConverter.asString(rgb)); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		});
		typesList.setItems(colorPreferenceNames);
		typesList.select(0);
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

	private void updateColorSelector() {
		int selectionIndex = typesList.getSelectionIndex();
		String rgbValue = overlayPreferenceStore.getString(colorPreferences[selectionIndex]);
		RGB rgb = StringConverter.asRGB(rgbValue);
		textColorSelector.setColorValue(rgb);
	}
	
	private void updateFonts(){
		String currentCondFont = overlayPreferenceStore.getString(TesterPreferenceConstants.TEST_RESULT_CHANGED_FONT);
		if (currentCondFont == null || currentCondFont.length() == 0) {
			currentCondFont = getPreferenceStore().getString(TesterPreferenceConstants.TEST_RESULT_CHANGED_FONT);
		}
		FontData fontData = new FontData(currentCondFont);
		String fontString = StringConverter.asString(fontData);
		modifiedDataFontNameLabel.setText(fontString);
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
		label.setText(Messages.getString("Test_Result_Appearance"));
		GridData lData = new GridData(GridData.FILL_HORIZONTAL);
		label.setLayoutData(lData);

		Group group = new Group(composite, SWT.NULL);
		group.setText("Result Viewer");
		group.setLayout(new GridLayout());
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		createColorGroup(group);
		createFontsGroup(group);
		
		update();
		
		return composite;
	}
	
	/**
	 * @param composite
	 */
	private void createFontsGroup(Composite composite) {
		Group fonts = new Group(composite, SWT.NULL);
		fonts.setLayout(new GridLayout(3, false));
		fonts.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fonts.setText(Messages.getString("result.font"));

		// condition data font:
		Label modifiedDataFontLabel = new Label(fonts, SWT.NULL);
		modifiedDataFontLabel.setText(Messages.getString("result.modified.property.font"));
		modifiedDataFontNameLabel = new Label(fonts, SWT.NULL);
		modifiedDataFontNameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		String currentCondFont = overlayPreferenceStore.getString(TesterPreferenceConstants.TEST_RESULT_CHANGED_FONT);
		if (currentCondFont == null || currentCondFont.length() == 0) {
			currentCondFont = getPreferenceStore().getString(TesterPreferenceConstants.TEST_RESULT_CHANGED_FONT);
		}
		FontData fontData = new FontData(currentCondFont);
		String fontString = StringConverter.asString(fontData);
		modifiedDataFontNameLabel.setText(fontString);
		
		changeModifiedValueFontButton = new Button(fonts, SWT.NULL);
		changeModifiedValueFontButton.setText(Messages.getString("result.font.change"));
		changeModifiedValueFontButton.addSelectionListener(this);
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		if (e.getSource() == changeModifiedValueFontButton) {
			String currentFontString = overlayPreferenceStore.getString(TesterPreferenceConstants.TEST_RESULT_CHANGED_FONT);
			if (currentFontString == null || currentFontString.length() == 0) {
				currentFontString = getPreferenceStore().getString(TesterPreferenceConstants.TEST_RESULT_CHANGED_FONT);
			}
			FontDialog dialog = new FontDialog(changeModifiedValueFontButton.getShell());
			final FontData fontData = new FontData(currentFontString);
			FontData[] currentFont = new FontData[] { fontData };
			dialog.setFontList(currentFont);
			
			String rgbString = overlayPreferenceStore.getString(TesterPreferenceConstants.TEST_RESULT_CHANGED_VALUE_FORE_GROUND_COLOR);
			RGB rgb = StringConverter.asRGB(rgbString);
			dialog.setRGB(rgb);

			FontData selectedFont = dialog.open();
			if (selectedFont != null) {
				selectedFont.toString();
                RGB color = dialog.getRGB();
                if (color != null) {
                	overlayPreferenceStore.setValue(TesterPreferenceConstants.TEST_RESULT_CHANGED_VALUE_FORE_GROUND_COLOR, StringConverter.asString(color));
                	updateColorSelector();
                }
                overlayPreferenceStore.setValue(TesterPreferenceConstants.TEST_RESULT_CHANGED_FONT, selectedFont.toString());
                modifiedDataFontNameLabel.setText(StringConverter.asString(selectedFont));
			}
		}
		
		if (e.getSource() == typesList) {
			updateColorSelector();
		}
	}
}