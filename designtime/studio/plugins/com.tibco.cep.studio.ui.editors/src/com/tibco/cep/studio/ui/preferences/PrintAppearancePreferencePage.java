package com.tibco.cep.studio.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.resource.StringConverter;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.tibco.cep.studio.core.OverlayKey;
import com.tibco.cep.studio.core.OverlayKey.OVERLAYKEYTYPES;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class PrintAppearancePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

	private OverlayPreferenceStore overlayPreferenceStore;
	private static String[] colorPreferences = new String[1];
	private static String[] colorPreferenceNames = new String[1];
	private List printList;
	private ColorSelector 	colorSelector;
	private Label printFontNameLabel;
	
	static {
		colorPreferences[0] = StudioPreferenceConstants.PRINT_COLOR;
		colorPreferenceNames[0] = Messages.getString("studio.print.preference.diagram.color");
	}
	
	public PrintAppearancePreferencePage(){
		super(GRID);
		setPreferenceStore(EditorsUIPlugin.getDefault().getPreferenceStore());
		overlayPreferenceStore = new OverlayPreferenceStore(getPreferenceStore(), getOverlayKeys());
	}	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		Composite parent =getFieldEditorParent();
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PRINT_CAPTION,
				Messages.getString("studio.print.preference.diagram.font.caption"),parent));
		addField(new TextAreaFieldEditor(StudioPreferenceConstants.PRINT_CAPTION_TEXT,"",50,parent));

		String[][] entryNamesAndValues = { {Messages.getString("studio.print.preference.diagram.caption.pos.topleft"),StudioPreferenceConstants.PRINT_CAPTION_POSITION_TOP_LEFT },
				{ Messages.getString("studio.print.preference.diagram.caption.pos.topright"), StudioPreferenceConstants.PRINT_CAPTION_POSITION_TOP_RIGHT }, 
				{ Messages.getString("studio.print.preference.diagram.caption.pos.bottomleft"), StudioPreferenceConstants.PRINT_CAPTION_POSITION_BOTTOM_LEFT},
				{ Messages.getString("studio.print.preference.diagram.caption.pos.bottomright"), StudioPreferenceConstants.PRINT_CAPTION_POSITION_BOTTOM_RIGHT} };
		new Composite(parent,SWT.NONE);
		Composite composite = new Composite(parent,SWT.NONE);
		composite.setLayout(new GridLayout(2,false));
		ComboFieldEditor positionEditor = new ComboFieldEditor(StudioPreferenceConstants.PRINT_CAPTION_POSITION,
				Messages.getString("studio.print.preference.diagram.font.position"), entryNamesAndValues, composite);
		addField(positionEditor);
		
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PRINT_BORDER,
				Messages.getString("studio.print.preference.diagram.color.border"),parent));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PRINT_BACKGROUND,
				Messages.getString("studio.print.preference.diagram.color.background"),parent));
		addField(new BooleanFieldEditor(StudioPreferenceConstants.PRINT_GRID,	
				Messages.getString("studio.print.preference.diagram.color.grid"),parent));
		
		createColorGroup(parent);
		new Composite(parent,SWT.NONE);
		createFontsGroup(parent);
	}

	private OverlayKey[] getOverlayKeys() {
		OverlayKey[] keys = new OverlayKey[colorPreferences.length + 1];
		for (int i = 0; i < colorPreferences.length; i++) {
			keys[i] = new OverlayKey(colorPreferences[i], OVERLAYKEYTYPES.STRING);
		}
		keys[colorPreferences.length] = new OverlayKey(StudioPreferenceConstants.PRINT_FONT, OVERLAYKEYTYPES.STRING);
		return keys;
	}
	
	/**
	 * @param parent
	 */
	private void createColorGroup(Composite parent) {

		Group group = new Group(parent, SWT.NULL);
		group.setText(Messages.getString("studio.print.preference.diagram.color.title"));
		group.setLayout(new GridLayout(2, false));
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		group.setLayoutData(data);

		printList = new List(group, SWT.BORDER);
		printList.setSize(200, 200);
		printList.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		printList.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				updateColorSelector();
			}
		});

		colorSelector = new ColorSelector(group);
		colorSelector.setColorValue(new RGB(200,200,200));
		colorSelector.addListener(new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				int index = printList.getSelectionIndex();
				if (index != -1) {
					RGB rgb = colorSelector.getColorValue();
					overlayPreferenceStore.setValue(colorPreferences[index], StringConverter.asString(rgb));
				}
			}

		});

		printList.setItems(colorPreferenceNames);
		printList.select(0);
	}
	
	private void update() {
		updateColorSelector();
		updateFonts();
	}

	private void updateFonts() {
		String currentCondFont = overlayPreferenceStore.getString(StudioPreferenceConstants.PRINT_FONT);
		if (currentCondFont == null || currentCondFont.length() == 0) {
			currentCondFont = getPreferenceStore().getString(StudioPreferenceConstants.PRINT_FONT);
		}
		final FontData fontData = new FontData(currentCondFont);
		String condFontString = StringConverter.asString(fontData);
		printFontNameLabel.setText(condFontString);
		
	}
	
	private void updateColorSelector() {
		int selectionIndex = printList.getSelectionIndex();
		String rgbValue = overlayPreferenceStore.getString(colorPreferences[selectionIndex]);
		RGB rgb = StringConverter.asRGB(rgbValue);
		colorSelector.setColorValue(rgb);
	}

	private void createFontsGroup(Composite composite) {
		Group group = new Group(composite, SWT.NULL);
		group.setText(Messages.getString("studio.print.preference.diagram.font.title"));
		group.setLayout(new GridLayout(3, false));
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label fontLabel = new Label(group, SWT.NULL);
		fontLabel.setText(Messages.getString("studio.print.preference.diagram.font"));
		printFontNameLabel = new Label(group, SWT.NULL);
		printFontNameLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		String currentCondFont = overlayPreferenceStore.getString(StudioPreferenceConstants.PRINT_FONT);
		if (currentCondFont == null || currentCondFont.length() == 0) {
			currentCondFont = getPreferenceStore().getString(StudioPreferenceConstants.PRINT_FONT);
		}
		FontData fontData = new FontData(currentCondFont);
		String condFontString = StringConverter.asString(fontData);
		printFontNameLabel.setText(condFontString);
		final Button fontButton = new Button(group, SWT.NULL);
		fontButton.setText(Messages.getString("studio.print.preference.diagram.font.change"));
		fontButton.addSelectionListener(new SelectionAdapter() {
		
			public void widgetSelected(SelectionEvent e) {
				String currentCondFont = overlayPreferenceStore.getString(StudioPreferenceConstants.PRINT_FONT);
				if (currentCondFont == null || currentCondFont.length() == 0) {
					currentCondFont = getPreferenceStore().getString(StudioPreferenceConstants.PRINT_FONT);
				}
				FontDialog dialog = new FontDialog(fontButton.getShell());
				final FontData fontData = new FontData(currentCondFont);
				FontData[] currentFont = new FontData[] { fontData };
				dialog.setFontList(currentFont);
				String rgbString = overlayPreferenceStore.getString(StudioPreferenceConstants.PRINT_COLOR);
				RGB rgb = StringConverter.asRGB(rgbString);
				dialog.setRGB(rgb);

				FontData selectedFont = dialog.open();
				if (selectedFont != null) {
					selectedFont.toString();
                    RGB color = dialog.getRGB();
                    if (color != null) {
                    	overlayPreferenceStore.setValue(StudioPreferenceConstants.PRINT_COLOR, StringConverter.asString(color));
                    	updateColorSelector();
                    }
                    overlayPreferenceStore.setValue(StudioPreferenceConstants.PRINT_FONT, selectedFont.toString());
                    printFontNameLabel.setText(StringConverter.asString(selectedFont));
				}
			}
		});
	}
	
	@Override
	protected void performApply() {
		performOk();
	}
	
	@Override
	public boolean performOk() {
		boolean b = super.performOk();
		if (b) {
			return overlayPreferenceStore.performOk();
		}
		return b;
	}
	
	public void init(IWorkbench workbench) {
		overlayPreferenceStore.load();
	}
	@Override
	protected void performDefaults() {
		overlayPreferenceStore.performDefaults();
		super.performDefaults();
		update();
	}
}
