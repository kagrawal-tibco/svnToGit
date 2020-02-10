package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynProperty;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;

public class ChartGridValueFieldSettingsForm extends ChartValueFieldSettingsForm {
	
	private static final Anchor[] INDICATOR_ANCHORS = new Anchor[]{
		new Anchor("Right Of Text",AnchorEnum.WEST), //we are anchoring the text in a indicator column <text> {o}
		new Anchor("Left Of Text",AnchorEnum.EAST) //we are anchoring the text in a indicator column {o} <text>
	};
	
	private static final Map<String,String> INDICATOR_TO_TEXT_PROP_MAPPING = new HashMap<String, String>();
	
	static {
		INDICATOR_TO_TEXT_PROP_MAPPING.put("IndicatorValueColumnField", "TextValueColumnField");
		INDICATOR_TO_TEXT_PROP_MAPPING.put("IndicatorValueColumnFieldHeaderName", "TextValueColumnFieldHeaderName");
		INDICATOR_TO_TEXT_PROP_MAPPING.put("IndicatorValueColumnFieldWidth", "TextValueColumnFieldWidth");
		INDICATOR_TO_TEXT_PROP_MAPPING.put("IndicatorValueColumnFieldAlignment", "TextValueColumnFieldAlignment");
		INDICATOR_TO_TEXT_PROP_MAPPING.put("IndicatorShowDataValue", "TextShowDataValue");
		INDICATOR_TO_TEXT_PROP_MAPPING.put("IndicatorValueColumnFieldDisplayFormat", "TextValueColumnFieldDisplayFormat");
		INDICATOR_TO_TEXT_PROP_MAPPING.put("IndicatorValueColumnFieldDisplayFontSize", "TextValueColumnFieldDisplayFontSize");
		INDICATOR_TO_TEXT_PROP_MAPPING.put("IndicatorValueColumnFieldDisplayFontStyle", "TextValueColumnFieldDisplayFontStyle");
		INDICATOR_TO_TEXT_PROP_MAPPING.put("IndicatorValueColumnFieldTooltipFormat", "TextValueColumnFieldTooltipFormat");
	}

	private Combo cmb_Alignment;
	private ComboViewer cmbViewer_Alignment;
	private ISelectionChangedListener cmbViewer_AlignmentSelectionChangedListener;
	
	private Button btn_ShowIndicator;
	private SelectionListener btn_ShowIndicatorSelectionListener;
	
	private Combo cmb_IndicatorAnchor;
	private ComboViewer cmbViewer_IndicatorAnchor;
	private ISelectionChangedListener cmbViewer_IndicatorAnchorSelectionChangedListener;
	
	private boolean useIndicator;
	
	public ChartGridValueFieldSettingsForm(FormToolkit formToolKit, Composite parent) {
		super(formToolKit, parent);
	}
	
	@Override
	public void init() {
		super.init();
		
		//show indicator button
		btn_ShowIndicator = createButton(formComposite, "Show Indicator", SWT.CHECK);
		btn_ShowIndicator.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Composite cmp_IndicatorSettingsComposite = createComposite(formComposite, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		cmp_IndicatorSettingsComposite.setLayout(layout);
		//Alignment 
 		createLabel(cmp_IndicatorSettingsComposite, "Anchor:", SWT.NONE);
		//display value font size drop down 
		cmb_IndicatorAnchor = createCombo(cmp_IndicatorSettingsComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_IndicatorAnchor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		cmbViewer_IndicatorAnchor = new ComboViewer(cmb_IndicatorAnchor);
		cmbViewer_IndicatorAnchor.setContentProvider(new ArrayContentProvider());
		cmbViewer_IndicatorAnchor.setLabelProvider(new LabelProvider() {
			
			@Override
			public String getText(Object element) {
				if (element instanceof Anchor){
					return ((Anchor) element).label;
				}
				return super.getText(element);
			}

		});
		
		GridData cmp_IndicatorSettingsCompositeGridData = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		cmp_IndicatorSettingsCompositeGridData.horizontalIndent = 18;
		cmp_IndicatorSettingsComposite.setLayoutData(cmp_IndicatorSettingsCompositeGridData);
	}
	
	@Override
	protected Composite createDisplayLabelComposite() {
		Composite composite = super.createDisplayLabelComposite();
		//display value pattern type label    
		createLabel(composite, "Alignment:", SWT.NONE);
		//display value pattern drop down  
		cmb_Alignment = createCombo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_Alignment.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		cmbViewer_Alignment = new ComboViewer(cmb_Alignment);
		cmbViewer_Alignment.setContentProvider(new ArrayContentProvider());
		cmbViewer_Alignment.setLabelProvider(new LabelProvider() {
			
			@Override
			public String getText(Object element) {
				if (element instanceof FieldAlignmentEnum){
					String text = ((FieldAlignmentEnum) element).getLiteral();
					StringBuilder sb = new StringBuilder();
					sb.append(Character.toUpperCase(text.charAt(0)));
					sb.append(text.substring(1));
					return sb.toString();
				}
				return super.getText(element);
			}
		});
		return composite;
	}
	
	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		super.inputChanged(oldLocalElement, newLocalElement);
		useIndicator = false;
	}
	
	@Override
	protected void doEnableListeners() {
		super.doEnableListeners();
		if (cmbViewer_AlignmentSelectionChangedListener == null){
			cmbViewer_AlignmentSelectionChangedListener = new ISelectionChangedListener(){

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					alignmentChanged();
				}
				
			};
		}
		cmbViewer_Alignment.addSelectionChangedListener(cmbViewer_AlignmentSelectionChangedListener);
		
		if (btn_ShowIndicatorSelectionListener == null){
			btn_ShowIndicatorSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					showIndicatorClicked();
				}
				
			};
		}
		btn_ShowIndicator.addSelectionListener(btn_ShowIndicatorSelectionListener);
		
		if (cmbViewer_IndicatorAnchorSelectionChangedListener == null){
			cmbViewer_IndicatorAnchorSelectionChangedListener = new ISelectionChangedListener(){

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					anchorChanged();
				}
				
			};
		}
		cmbViewer_IndicatorAnchor.addSelectionChangedListener(cmbViewer_IndicatorAnchorSelectionChangedListener);
	}

	@Override
	protected void doDisableListeners() {
		super.doDisableListeners();
		cmbViewer_Alignment.removeSelectionChangedListener(cmbViewer_AlignmentSelectionChangedListener);
		btn_ShowIndicator.removeSelectionListener(btn_ShowIndicatorSelectionListener);
		cmbViewer_IndicatorAnchor.removeSelectionChangedListener(cmbViewer_IndicatorAnchorSelectionChangedListener);
	}

	@Override
	public void refreshEnumerations() {
		super.refreshEnumerations();
		//enumerate the alignment values
		cmbViewer_Alignment.setInput(FieldAlignmentEnum.values());
		//enumerate the indicator anchor values 
		cmbViewer_IndicatorAnchor.setInput(Arrays.asList(INDICATOR_ANCHORS));
	}

	@Override
	public void refreshSelections() {
		cmbViewer_Alignment.setSelection(StructuredSelection.EMPTY);
		btn_ShowIndicator.setSelection(false);
		cmbViewer_IndicatorAnchor.setSelection(StructuredSelection.EMPTY);
		if (localElement != null) {
			String propertyName = "IndicatorValueColumnField";
			try {
				SynProperty property = (SynProperty) localElement.getProperty(propertyName);
				//initialize useIndicator
				useIndicator = !property.getDefault().equals(localElement.getPropertyValue(propertyName));
				//now let the super class refresh it's selections
				super.refreshSelections();
				//update alignment selection
				propertyName = getAlignmentPropertyName();
				cmbViewer_Alignment.setSelection(new StructuredSelection(FieldAlignmentEnum.get(localElement.getPropertyValue(propertyName).toLowerCase())));
				//update the show indicator selection
				btn_ShowIndicator.setSelection(useIndicator);
				cmb_IndicatorAnchor.setEnabled(useIndicator);
				if (useIndicator == true){
					propertyName = "IndicatorValueColumnFieldTextValueAnchor";
					for (Anchor anchor : INDICATOR_ANCHORS) {
						if (anchor.isAcceptable(localElement) == true){
							cmbViewer_IndicatorAnchor.setSelection(new StructuredSelection(anchor));
							break;
						}
					}
				}
			} catch (Exception e) {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not read '"+propertyName+"'",e));
			}
		}
	}
	
	@Override
	protected void showDisplayClicked() {
		super.showDisplayClicked();
		boolean displayEnabled = btn_ShowDisplayLabel.getSelection();
		cmb_Alignment.setEnabled(displayEnabled);
	}
	
	private void alignmentChanged(){
		try {
			IStructuredSelection selection = (IStructuredSelection) cmbViewer_Alignment.getSelection();
			localElement.setPropertyValue(getAlignmentPropertyName(), ((FieldAlignmentEnum)selection.getFirstElement()).getLiteral());
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update 'Alignment'",e));
		}
	}
	
	private void showIndicatorClicked(){
		try {
			useIndicator = btn_ShowIndicator.getSelection();
			//enable/disable the cmbViewer_IndicatorAnchor
			cmb_IndicatorAnchor.setEnabled(useIndicator);
			//update the 
			localElement.setPropertyValue("IndicatorShowValueColumnFieldTextValue", Boolean.toString(useIndicator));
			if (useIndicator == true){
				//transfer all text values to indicator and reset text values 
				for (Map.Entry<String, String> entry : INDICATOR_TO_TEXT_PROP_MAPPING.entrySet()) {
					//transfer the value from text to indicator
					localElement.setPropertyValue(entry.getKey(), localElement.getPropertyValue(entry.getValue()));
					//reset the text property 
					localElement.setPropertyValue(entry.getValue(),((SynProperty)localElement.getProperty(entry.getValue())).getDefault());
					//TODO do we need to set the resetted property as existing 
					//((SynProperty)localElement.getProperty(entry.getValue())).setInternalStatus(InternalStatusEnum.StatusExisting);
				}
				//update IndicatorShowValueColumnFieldTextValue to true 
				localElement.setPropertyValue("IndicatorShowValueColumnFieldTextValue", Boolean.toString(useIndicator));
				//update IndicatorValueColumnFieldTextValueAnchor to current value of cmbViewer_IndicatorAnchor
				if (cmbViewer_IndicatorAnchor.getSelection().isEmpty() == true){
					cmbViewer_IndicatorAnchor.setSelection(new StructuredSelection(INDICATOR_ANCHORS[0]));
				}
				((Anchor)((IStructuredSelection)cmbViewer_IndicatorAnchor.getSelection()).getFirstElement()).update(localElement);
			}
			else {
				//transfer all indicator values to text and reset indicator values 
				for (Map.Entry<String, String> entry : INDICATOR_TO_TEXT_PROP_MAPPING.entrySet()) {
					//transfer the value from indicator to text
					localElement.setPropertyValue(entry.getValue(), localElement.getPropertyValue(entry.getKey()));
					//reset the indicator property 
					localElement.setPropertyValue(entry.getKey(),((SynProperty)localElement.getProperty(entry.getKey())).getDefault());
					//TODO do we need to set the resetted property as existing 
					//((SynProperty)localElement.getProperty(entry.getKey())).setInternalStatus(InternalStatusEnum.StatusExisting);
				}				
				//TODO Should I nullify IndicatorShowValueColumnFieldTextValue
				//localElement.setPropertyValue("IndicatorShowValueColumnFieldTextValue", ((SynProperty)localElement.getProperty("IndicatorShowValueColumnFieldTextValue")).getDefault());
				//TODO Should I nullify IndicatorValueColumnFieldTextValueAnchor
				//localElement.setPropertyValue("IndicatorValueColumnFieldTextValueAnchor", ((SynProperty)localElement.getProperty("IndicatorValueColumnFieldTextValueAnchor")).getDefault());
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not process 'Show Indicator'",e));
		}
	}
	
	private void anchorChanged(){
		try {
			((Anchor)((IStructuredSelection)cmbViewer_IndicatorAnchor.getSelection()).getFirstElement()).update(localElement);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update 'Indicator Anchor'",e));
		}
	}

	@Override
	protected String getFieldPropertyName() {
		if (useIndicator == true){
			return "IndicatorValueColumnField";
		}
		return "TextValueColumnField";
	}
	
	@Override
	protected String getShowDataLabelPropertyName() {
		if (useIndicator == true){
			return "IndicatorShowDataValue";
		}
		return "TextShowDataValue";
	}
	
	@Override
	protected String getFontSizePropertyName() {
		if (useIndicator == true){
			return "IndicatorValueColumnFieldDisplayFontSize";
		}
		return "TextValueColumnFieldDisplayFontSize";
	}

	@Override
	protected String getFontStylePropertyName() {
		if (useIndicator == true){
			return "IndicatorValueColumnFieldDisplayFontStyle";
		}
		return "TextValueColumnFieldDisplayFontStyle";
	}
	
	@Override
	protected String getDisplayLabelPropertyName() {
		if (useIndicator == true){
			return "IndicatorValueColumnFieldDisplayFormat";
		}
		return "TextValueColumnFieldDisplayFormat";
	}
	
	private String getAlignmentPropertyName() {
		if (useIndicator == true){
			return "IndicatorValueColumnFieldAlignment";
		}
		return "TextValueColumnFieldAlignment";
	}

	protected static class Anchor {
		
		private AnchorEnum anchorEnum;
		
		private String label;

		public Anchor(String label, AnchorEnum anchorEnum) {
			super();
			this.label = label;
			this.anchorEnum = anchorEnum;
		}
		
		boolean isAcceptable(LocalElement localEment) throws Exception {
			AnchorEnum anchorEnumValue = AnchorEnum.get(localEment.getPropertyValue("IndicatorValueColumnFieldTextValueAnchor").toLowerCase());
			return anchorEnum.compareTo(anchorEnumValue) == 0;
		}
		
		void update(LocalElement localElement) throws Exception {
			localElement.setPropertyValue("IndicatorValueColumnFieldTextValueAnchor", anchorEnum.getLiteral());
		}
		
	}	
	
}