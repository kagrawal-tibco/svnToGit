package com.tibco.cep.studio.dashboard.ui.forms;

import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.beviewsconfig.FontStyleEnum;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalVisualAlertAction;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.utils.SynColorUtils;

public class VisualAlertActionSettingsForm extends BaseForm {

//	private Button btn_Enabled;
//	private AbstractSelectionListener btn_EnabledSelectionListener;

	private ColorSelector clrsel_FillColor;
	private IPropertyChangeListener clrsel_FillColorChangeListener;

	private ColorSelector clrsel_FontColor;
	private IPropertyChangeListener clrsel_FontColorChangeListener;

	private FontStyleWidget fontStyleWidget;
	private PropertyChangeListener fontStyleWidget_PropertyChangeListener;

//	private Combo cmb_FontSize;
//	private ComboViewer cmbViewer_FontSize;
//	private AbstractSelectionListener cmb_FontSizeSelectionListener;

	private Text text_DisplayFormat;
	private ModifyListener txt_DisplayFormatModifyListener;

	private Text text_ScreenTipFormat;
	private ModifyListener txt_ScreenTipFormatModifyListener;

	private LocalVisualAlertAction localVisualAlertAction;

	private boolean enableDisplayControl;

	public VisualAlertActionSettingsForm(FormToolkit formToolKit, Composite parent) {
		super("Visual Alert Action", formToolKit, parent, true);
		enableDisplayControl = true;
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(2, false));

		// enabled
//		btn_Enabled = createButton(formComposite, "Enabled", SWT.CHECK);
//		btn_Enabled.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));

		// font styles & colors
		Composite fontStylesAndColorComposite = createComposite(formComposite, SWT.NONE);
		GridLayout fontStylesAndColorCompLayout = new GridLayout(/*8*/6, false);
		fontStylesAndColorCompLayout.marginHeight = 0;
		fontStylesAndColorCompLayout.marginWidth = 0;
		fontStylesAndColorComposite.setLayout(fontStylesAndColorCompLayout);

		// fill color
		createLabel(fontStylesAndColorComposite, "Fill Color:", SWT.NONE);
		clrsel_FillColor = new ColorSelector(fontStylesAndColorComposite);
		addControl(clrsel_FillColor.getButton());

		// font color
		createLabel(fontStylesAndColorComposite, "Font Color:", SWT.NONE);
		clrsel_FontColor = new ColorSelector(fontStylesAndColorComposite);
		addControl(clrsel_FontColor.getButton());

		// font style
		createLabel(fontStylesAndColorComposite, "Font Style:", SWT.NONE);
		fontStyleWidget = new FontStyleWidget(formToolKit,fontStylesAndColorComposite, SWT.NONE);
		fontStyleWidget.setFontStyle(FontStyleEnum.NORMAL);
		//add the font style widget as a control to enable/disable it
		addControl(fontStyleWidget);

		// font size
//		createLabel(fontStylesAndColorComposite, "Font Size:", SWT.NONE);
//		cmb_FontSize = createCombo(fontStylesAndColorComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
//		cmb_FontSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		fontStylesAndColorComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));

		// display format
		createLabel(formComposite, "Display Format:", SWT.NONE);

		text_DisplayFormat = createText(formComposite, "", SWT.NONE);
		text_DisplayFormat.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// screen tip format
		createLabel(formComposite, "Screen Tip Format:", SWT.NONE);
		text_ScreenTipFormat = createText(formComposite, "", SWT.NONE);
		text_ScreenTipFormat.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

//		cmbViewer_FontSize = new ComboViewer(cmb_FontSize);
//		cmbViewer_FontSize.setContentProvider(new ArrayContentProvider());
	}

	public void disableDisplayControls(){
		enableDisplayControl = false;
	}

	public void enableDisplayControls(){
		enableDisplayControl = true;
	}

	@Override
	public void enableAll() {
		for (Control control : controls) {
			if (control == clrsel_FontColor.getButton()) {
				clrsel_FontColor.getButton().setEnabled(enableDisplayControl);
			}
			else if (control == fontStyleWidget) {
				fontStyleWidget.setEnabled(enableDisplayControl);
			}
			else if (control == text_DisplayFormat) {
				text_DisplayFormat.setEnabled(enableDisplayControl);
			}
			else {
				control.setEnabled(true);
			}
		}
		for (BaseForm form : forms) {
			form.enableAll();
		}
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		if (newLocalElement == null){
			localVisualAlertAction = new LocalVisualAlertAction();
			return;
		}
		if (newLocalElement instanceof LocalVisualAlertAction){
			this.localVisualAlertAction = (LocalVisualAlertAction) newLocalElement;
		}
		else {
			throw new IllegalArgumentException(newLocalElement+" is not a visual alert action");
		}

	}

	@Override
	protected void doEnableListeners() {
//		if (btn_EnabledSelectionListener == null) {
//			btn_EnabledSelectionListener = new AbstractSelectionListener() {
//
//				@Override
//				public void widgetSelected(SelectionEvent e) {
//					enableDisableAlert();
//				}
//
//			};
//		}
//		btn_Enabled.addSelectionListener(btn_EnabledSelectionListener);

		if (clrsel_FillColorChangeListener == null){
			clrsel_FillColorChangeListener = new IPropertyChangeListener(){

				@Override
				public void propertyChange(PropertyChangeEvent event) {
					fillColorChanged();
				}

			};
		}
		clrsel_FillColor.addListener(clrsel_FillColorChangeListener);

		if (clrsel_FontColorChangeListener == null){
			clrsel_FontColorChangeListener = new IPropertyChangeListener(){

				@Override
				public void propertyChange(PropertyChangeEvent event) {
					fontColorChanged();
				}

			};
		}
		clrsel_FontColor.addListener(clrsel_FontColorChangeListener);

		if (fontStyleWidget_PropertyChangeListener == null){
			fontStyleWidget_PropertyChangeListener = new PropertyChangeListener(){

				@Override
				public void propertyChange(java.beans.PropertyChangeEvent evt) {
					fontStyleChanged();
				}


			};
		}
		fontStyleWidget.addPropertyChangeListener(fontStyleWidget_PropertyChangeListener);

//		if (cmb_FontSizeSelectionListener == null){
//			cmb_FontSizeSelectionListener = new AbstractSelectionListener(){
//
//				@Override
//				public void widgetSelected(SelectionEvent e) {
//					fontSizeChanged();
//				}
//
//			};
//		}
//
//		cmb_FontSize.addSelectionListener(cmb_FontSizeSelectionListener);

		if (txt_DisplayFormatModifyListener == null){
			txt_DisplayFormatModifyListener = new ModifyListener(){

				@Override
				public void modifyText(ModifyEvent e) {
					displayFormatChanged();
				}

			};
		}

		text_DisplayFormat.addModifyListener(txt_DisplayFormatModifyListener);

		if (txt_ScreenTipFormatModifyListener == null){
			txt_ScreenTipFormatModifyListener = new ModifyListener(){

				@Override
				public void modifyText(ModifyEvent e) {
					screenTipFormatChanged();
				}

			};
		}

		text_ScreenTipFormat.addModifyListener(txt_ScreenTipFormatModifyListener);
	}



	@Override
	protected void doDisableListeners() {
//		btn_Enabled.removeSelectionListener(btn_EnabledSelectionListener);
		clrsel_FillColor.removeListener(clrsel_FillColorChangeListener);
		clrsel_FontColor.removeListener(clrsel_FontColorChangeListener);
		fontStyleWidget.removePropertyChangeListener(fontStyleWidget_PropertyChangeListener);
//		cmb_FontSize.removeSelectionListener(cmb_FontSizeSelectionListener);
		text_DisplayFormat.removeModifyListener(txt_DisplayFormatModifyListener);
		text_ScreenTipFormat.removeModifyListener(txt_ScreenTipFormatModifyListener);
	}

	@Override
	public void refreshEnumerations() {
//		cmbViewer_FontSize.setInput(localVisualAlertAction.getEnumerations(LocalVisualAlertAction.PROP_KEY_FONT_SIZE));
	}

	@Override
	public void refreshSelections() {
		try {
//			btn_Enabled.setSelection(Boolean.valueOf(localVisualAlertAction.getPropertyValue(LocalVisualAlertAction.PROP_KEY_ENABLED)));
			clrsel_FillColor.setColorValue(SynColorUtils.StringToRGB(localVisualAlertAction.getPropertyValue(LocalVisualAlertAction.PROP_KEY_FILL_COLOR)));
			clrsel_FontColor.setColorValue(SynColorUtils.StringToRGB(localVisualAlertAction.getPropertyValue(LocalVisualAlertAction.PROP_KEY_FONT_COLOR)));
			String fontStyle = localVisualAlertAction.getPropertyValue(LocalVisualAlertAction.PROP_KEY_FONT_STYLE).toLowerCase();
			FontStyleEnum fontStyleNum = FontStyleEnum.NORMAL;
			for (FontStyleEnum styleEnum : FontStyleEnum.VALUES) {
				if (styleEnum.getLiteral().equals(fontStyle) == true){
					fontStyleNum = styleEnum;
					break;
				}
			}
			fontStyleWidget.setFontStyle(fontStyleNum);

//			cmbViewer_FontSize.setSelection(new StructuredSelection(localVisualAlertAction.getPropertyValue(LocalVisualAlertAction.PROP_KEY_FONT_SIZE)));

			text_DisplayFormat.setText(localVisualAlertAction.getPropertyValue(LocalVisualAlertAction.PROP_KEY_DISPLAY_FORMAT));

			text_ScreenTipFormat.setText(localVisualAlertAction.getPropertyValue(LocalVisualAlertAction.PROP_KEY_TOOLTIP_FORMAT));
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
			disableAll();
		}
	}

//	protected void enableDisableAlert() {
//		try {
//			boolean enabled = btn_Enabled.getSelection();
//			localVisualAlertAction.setPropertyValue(LocalRangeAlert.ENABLED, Boolean.toString(enabled));
//		} catch (Exception e) {
//			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process enable selection", e));
//		}
//	}

	protected void fillColorChanged(){
		try {
			localVisualAlertAction.setPropertyValue(LocalVisualAlertAction.PROP_KEY_FILL_COLOR, SynColorUtils.RGBToString(clrsel_FillColor.getColorValue()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process fill color change", e));
		}
	}

	protected void fontColorChanged(){
		try {
			localVisualAlertAction.setPropertyValue(LocalVisualAlertAction.PROP_KEY_FONT_COLOR, SynColorUtils.RGBToString(clrsel_FontColor.getColorValue()));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process font color change", e));
		}
	}

	protected void fontStyleChanged(){
		try {
			localVisualAlertAction.setPropertyValue(LocalVisualAlertAction.PROP_KEY_FONT_STYLE, fontStyleWidget.getFontStyle().toString());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process font style change", e));
		}
	}

//	protected void fontSizeChanged(){
//		try {
//			String fontSize = (String) ((StructuredSelection)cmbViewer_FontSize.getSelection()).getFirstElement();
//			localVisualAlertAction.setPropertyValue(LocalVisualAlertAction.PROP_KEY_FONT_SIZE, fontSize);
//		} catch (Exception e) {
//			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process font size change", e));
//		}
//	}

	protected void displayFormatChanged(){
		try {
			localVisualAlertAction.setPropertyValue(LocalVisualAlertAction.PROP_KEY_DISPLAY_FORMAT, text_DisplayFormat.getText());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process display format change", e));
		}
	}

	protected void screenTipFormatChanged(){
		try {
			localVisualAlertAction.setPropertyValue(LocalVisualAlertAction.PROP_KEY_TOOLTIP_FORMAT, text_ScreenTipFormat.getText());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process screen tip format change", e));
		}
	}

}