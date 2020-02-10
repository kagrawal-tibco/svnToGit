package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.data;

import java.util.Arrays;

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
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.designtime.core.model.beviewsconfig.SeriesAnchorEnum;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;

public class ChartBarValueFieldSettingsForm extends ChartValueFieldSettingsForm {

	private Combo cmb_Anchor;
	private ComboViewer cmbViewer_Anchor;
	private ISelectionChangedListener cmbViewer_AnchorSelectionChangedListener;
	private Anchor[] anchors;

	public ChartBarValueFieldSettingsForm(FormToolkit formToolKit, Composite parent) {
		super(formToolKit, parent);
		anchors = new Anchor[]{new Anchor("Right",SeriesAnchorEnum.Q1),new Anchor("Left",SeriesAnchorEnum.Q2)};
	}

	@Override
	public void init() {
		super.init();
		formComposite.setLayout(new GridLayout(2, false));
		// anchor label 
		createLabel(formComposite, getType()+" Direction:", SWT.NONE);
		// value field drop down
		cmb_Anchor = createCombo(formComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		cmb_Anchor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));	
		cmbViewer_Anchor = new ComboViewer(cmb_Anchor);
		cmbViewer_Anchor.setContentProvider(new ArrayContentProvider());
		cmbViewer_Anchor.setLabelProvider(new LabelProvider() {
			
			@Override
			public String getText(Object element) {
				if (element instanceof Anchor){
					return ((Anchor) element).label;
				}
				return super.getText(element);
			}

		});
	}

	@Override
	protected void doEnableListeners() {
		super.doEnableListeners();
		
		if (cmbViewer_AnchorSelectionChangedListener == null){
			cmbViewer_AnchorSelectionChangedListener = new ISelectionChangedListener(){

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					anchorSelectionChanged();
				}
				
			};
		}
		cmbViewer_Anchor.addSelectionChangedListener(cmbViewer_AnchorSelectionChangedListener);
	}
	
	@Override
	protected void doDisableListeners() {
		super.doDisableListeners();
		cmbViewer_Anchor.removeSelectionChangedListener(cmbViewer_AnchorSelectionChangedListener);
	}
	
	@Override
	public void refreshEnumerations() {
		super.refreshEnumerations();
		cmbViewer_Anchor.setInput(Arrays.asList(getAnchorEnumerations()));
	}
	
	@Override
	public void refreshSelections() {
		super.refreshSelections();
		try {
			for (Anchor anchor : getAnchorEnumerations()) {
				if (anchor.isAcceptable(localElement) == true){
					cmbViewer_Anchor.setSelection(new StructuredSelection(anchor));
					break;
				}
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not read 'Anchor'",e));
		}
	}
	
	private void anchorSelectionChanged(){
		try {
			IStructuredSelection selection = (IStructuredSelection) cmbViewer_Anchor.getSelection();
			if (selection.isEmpty() == false){
				((Anchor)selection.getFirstElement()).update(localElement);
			}
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update 'Anchor'",e));
		}
	}

	protected String getType() {
		return "Bar";
	}
	
	protected Anchor[] getAnchorEnumerations(){
		
		return anchors;
	}
	
	protected class Anchor {
		
		private SeriesAnchorEnum anchorEnum;
		
		private String label;

		public Anchor(String label, SeriesAnchorEnum anchorEnum) {
			super();
			this.label = label;
			this.anchorEnum = anchorEnum;
		}
		
		boolean isAcceptable(LocalElement localement) throws Exception {
			SeriesAnchorEnum anchorEnumValue = SeriesAnchorEnum.get(localElement.getPropertyValue("Anchor"));
			if (anchorEnumValue == null){
				return false;
			}
			return anchorEnum.compareTo(anchorEnumValue) == 0;
		}
		
		void update(LocalElement localElement) throws Exception {
			localElement.setPropertyValue("Anchor", anchorEnum.getLiteral());
		}
		
	}
}
