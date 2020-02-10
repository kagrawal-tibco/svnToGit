package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.beviewsconfig.AnchorEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.OrientationEnum;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;

public class ChartLegendForm extends BaseForm {
	
	private static final AnchorAndOrientationPair NO_LEGEND = new AnchorAndOrientationPair(AnchorEnum.NONE, OrientationEnum.NONE);
	
	private static final Map<String,AnchorAndOrientationPair> SIMPLIFIED_OPTIONS = new LinkedHashMap<String,AnchorAndOrientationPair>();  
	
	static {
		//top 
		SIMPLIFIED_OPTIONS.put("Top",new AnchorAndOrientationPair(AnchorEnum.NORTH,OrientationEnum.HORIZONTAL));
		SIMPLIFIED_OPTIONS.put("Bottom",new AnchorAndOrientationPair(AnchorEnum.SOUTH,OrientationEnum.HORIZONTAL));
		SIMPLIFIED_OPTIONS.put("Right",new AnchorAndOrientationPair(AnchorEnum.EAST,OrientationEnum.VERTICAL));
		SIMPLIFIED_OPTIONS.put("Left",new AnchorAndOrientationPair(AnchorEnum.WEST,OrientationEnum.VERTICAL));
	}
	
	private Button btn_ShowLegend;
	private SelectionListener btn_ShowLegendSelectionListener;
	
	private Combo cmb_Placement;
	private SelectionListener cmb_PlacementSelectionListener;
	
	public ChartLegendForm(FormToolkit formToolKit, Composite parent) {
		super("Legend", formToolKit, parent, false);
	}
	
	@Override
	public void init() {
		formComposite.setLayout(new GridLayout());
		
		btn_ShowLegend = createButton(formComposite, "Show Legend", SWT.CHECK);
		
		Composite cmp_Placement = createComposite(formComposite, SWT.NONE);
		cmp_Placement.setLayout(new GridLayout(2,false));
		GridData placementCompositeGridData = new GridData(SWT.CENTER,SWT.CENTER,false,false);
		placementCompositeGridData.horizontalIndent = 5;
		cmp_Placement.setLayoutData(placementCompositeGridData);
		
		Label lbl_Placement = createLabel(cmp_Placement, "Placement:", SWT.NONE);
		lbl_Placement.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,false,false));
		cmb_Placement = createCombo(cmp_Placement, SWT.READ_ONLY);
		cmb_Placement.setLayoutData(new GridData(SWT.CENTER,SWT.CENTER,false,false));
	}
	
	@Override
	protected void doEnableListeners() {
		if (btn_ShowLegendSelectionListener == null){
			btn_ShowLegendSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					showLegendButtonClicked();
				}
				
			};
		}
		btn_ShowLegend.addSelectionListener(btn_ShowLegendSelectionListener);
		
		if (cmb_PlacementSelectionListener == null){
			cmb_PlacementSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					placementComboSelected();
					
				}
				
			};
		}
		cmb_Placement.addSelectionListener(cmb_PlacementSelectionListener);		
	}

	@Override
	protected void doDisableListeners() {
		btn_ShowLegend.removeSelectionListener(btn_ShowLegendSelectionListener);
		cmb_Placement.removeSelectionListener(cmb_PlacementSelectionListener);
	}

	
	@Override
	public void refreshEnumerations() {
		cmb_Placement.removeAll();
		for (String key : SIMPLIFIED_OPTIONS.keySet()) {
			cmb_Placement.add(key);
		}
	}

	@Override
	public void refreshSelections() {
		try {
			String anchorValue = localElement.getPropertyValue("LegendAnchor");
			String orientationValue = localElement.getPropertyValue("LegendOrientation");
			if (NO_LEGEND.matches(anchorValue, orientationValue) == false) {
				btn_ShowLegend.setSelection(true);
				int i = 0;
				for (Map.Entry<String, AnchorAndOrientationPair> entry : SIMPLIFIED_OPTIONS.entrySet()) {
					if (entry.getValue().matches(anchorValue, orientationValue) == true){
						cmb_Placement.select(i);
						break;
					}
					i++;
				}
				if (cmb_Placement.getSelectionIndex() == -1){
					throw new IllegalArgumentException("The legend anchor['"+anchorValue+"'] and orientation['"+orientationValue+"'] combination is not valid");
				}
			}
			else {
				btn_ShowLegend.setSelection(false);
				cmb_Placement.setEnabled(false);
			}
		} catch (Exception e) {
			log(new Status(IStatus.ERROR,getPluginId(),"could not parse 'legend'",e));
			disableAll();
		}
	}
	
	protected void showLegendButtonClicked() {
		cmb_Placement.setEnabled(btn_ShowLegend.getSelection());
		if (btn_ShowLegend.getSelection() == true){
			//enforce a selection if none is selected
			if (cmb_Placement.getSelectionIndex() == -1){
				cmb_Placement.select(0);
			}
			//trigger selection handling  
			placementComboSelected();
		}
		else {
			try {
				NO_LEGEND.update(localElement);
			} catch (Exception e) {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not set 'legend'",e));
			}
		}
	}
	
	protected void placementComboSelected() {
		try {
			String placement = cmb_Placement.getItem(cmb_Placement.getSelectionIndex());
			AnchorAndOrientationPair pair = SIMPLIFIED_OPTIONS.get(placement);
			pair.update(localElement);
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not set 'legend'",e));
		}
	}
	
	static class AnchorAndOrientationPair {
		
		AnchorEnum anchor;
		
		OrientationEnum orientation;

		AnchorAndOrientationPair(AnchorEnum anchor, OrientationEnum orientation) {
			this.anchor = anchor;
			this.orientation = orientation;
		}
		
		boolean matches(String anchorValue,String orientationValue){
			return anchor.getLiteral().equalsIgnoreCase(anchorValue) && orientation.getLiteral().equalsIgnoreCase(orientationValue); 
		}
		
		void update(LocalElement localElement) throws Exception {
			localElement.setPropertyValue("LegendAnchor", anchor.getLiteral());
			localElement.setPropertyValue("LegendOrientation", orientation.getLiteral());
		}
		
	}
}
