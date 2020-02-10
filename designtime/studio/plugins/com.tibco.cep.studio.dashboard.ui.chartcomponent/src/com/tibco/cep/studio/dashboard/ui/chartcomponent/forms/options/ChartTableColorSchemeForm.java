package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalSkin;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.ui.chartcomponent.model.LocalUnifiedComponent;
import com.tibco.cep.studio.dashboard.ui.forms.AbstractSelectionListener;
import com.tibco.cep.studio.dashboard.ui.forms.BaseForm;
import com.tibco.cep.studio.dashboard.ui.forms.LocalElementLabelProvider;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class ChartTableColorSchemeForm extends BaseForm {

	private Button btn_DefaultColorSet;

	private SelectionListener btn_DefaultColorSetSelectionListener;

	private Combo cmb_ColorSet;

	private ComboViewer cmbViewer_ColorSet;

	private ISelectionChangedListener cmbViewer_ColorSetSelectionChangedListener;

	private List<LocalElement> colorSets;

	public ChartTableColorSchemeForm(FormToolkit formToolKit, Composite parent) {
		super("Color Scheme", formToolKit, parent, false);
	}

	@Override
	public void init() {
		formComposite.setLayout(new GridLayout(3,false));

		Label label = createLabel(formComposite, "Chart Color Set:", SWT.NONE);
		GridData layoutData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		layoutData.widthHint = 125;
		label.setLayoutData(layoutData);

		btn_DefaultColorSet = createButton(formComposite, "Default", SWT.CHECK);
		btn_DefaultColorSet.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));

		cmb_ColorSet = createCombo(formComposite, SWT.READ_ONLY);
		cmb_ColorSet.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
		cmbViewer_ColorSet = new ComboViewer(cmb_ColorSet);
		cmbViewer_ColorSet.setContentProvider(new ArrayContentProvider());
		cmbViewer_ColorSet.setLabelProvider(new LocalElementLabelProvider(false));

	}

	@Override
	protected void doEnableListeners() {
		if (btn_DefaultColorSetSelectionListener == null) {
			btn_DefaultColorSetSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					try {
						disableListeners();
						defaultColorSetButtonClicked();
					} finally {
						enableListeners();
					}
				}

			};
		}
		btn_DefaultColorSet.addSelectionListener(btn_DefaultColorSetSelectionListener);

		if (cmbViewer_ColorSetSelectionChangedListener == null) {
			cmbViewer_ColorSetSelectionChangedListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					try {
						disableListeners();
						colorSetSelectionChanged();
					} finally {
						enableListeners();
					}
				}

			};
		}
		cmbViewer_ColorSet.addSelectionChangedListener(cmbViewer_ColorSetSelectionChangedListener);

	}

	@Override
	protected void doDisableListeners() {
		btn_DefaultColorSet.removeSelectionListener(btn_DefaultColorSetSelectionListener);
		cmbViewer_ColorSet.removeSelectionChangedListener(cmbViewer_ColorSetSelectionChangedListener);
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		colorSets = new ArrayList<LocalElement>();;
		if (newLocalElement != null) {
			//get default skin
			List<LocalElement> skins = newLocalElement.getRoot().getChildren(BEViewsElementNames.SKIN);
			for (LocalElement skin : skins) {
				if (((LocalSkin)skin).isSystem() == true) {
					String nativeType = ((LocalUnifiedComponent)localElement).getNativeType();
					List<LocalElement> colorSetsInSkin = skin.getChildren(BEViewsElementNames.COMPONENT_COLOR_SET);
					for (LocalElement colorSet : colorSetsInSkin) {
						if (colorSet.getElementType().startsWith(nativeType) == true) {
							colorSets.add(colorSet);
						}
					}
					break;
				}
			}
		}
	}

	@Override
	public void refreshEnumerations() {
		//update the content provider for color set combo
		cmbViewer_ColorSet.setInput(colorSets);
	}

	@Override
	public void refreshSelections() {
		if (colorSets.isEmpty() == true) {
			disableAll();
			return;
		}
		btn_DefaultColorSet.setEnabled(true);
		String propertyName = "ColorSet";
		try {
			//get the component color set index
			String value = localElement.getPropertyValue(propertyName);
			int colorSetIndex = Integer.parseInt(value);
			propertyName = null;
			if (colorSetIndex == -1) {
				switchColorSetToDefault();
			}
			else {
				switchColorSetToIndex(colorSetIndex);
			}
		} catch (NumberFormatException e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Invalid value for "+propertyName,e));
		} catch (Exception e) {
			if (propertyName != null) {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not read "+propertyName,e));
			}
			else {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not refresh selections in "+getTitle(),e));
			}
		}
	}

	protected void defaultColorSetButtonClicked() {
		String propertyName = "ColorSet";
		try {
			if (btn_DefaultColorSet.getSelection() == true) {
				//update to defaults
				localElement.setPropertyValue(propertyName, "-1");
				propertyName = null;
				switchColorSetToDefault();
			}
			else {
				localElement.setPropertyValue(propertyName, "0");
				propertyName = null;
				switchColorSetToIndex(0);
			}
		} catch (Exception e) {
			if (propertyName != null) {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not update "+propertyName,e));
			}
			else {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not refresh selections in "+getTitle(),e));
			}
		}
	}

	protected void colorSetSelectionChanged(){
		IStructuredSelection selection = (IStructuredSelection) cmbViewer_ColorSet.getSelection();
		LocalElement selectedColorSet = (LocalElement) selection.getFirstElement();
		int index = colorSets.indexOf(selectedColorSet);
		String propertyName = "ColorSet";
		try {
			localElement.setPropertyValue(propertyName, String.valueOf(index));
			propertyName = null;
			switchColorSetToIndex(index);
		} catch (Exception e) {
			if (propertyName != null) {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not update "+propertyName,e));
			}
			else {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not refresh selections in "+getTitle(),e));
			}
		}
	}

	protected void switchColorSetToDefault(){
		//select the default color set button
		btn_DefaultColorSet.setSelection(true);
		//disable the color set dropdown
		cmb_ColorSet.setEnabled(false);
		//reset the selection in the drop down
		cmbViewer_ColorSet.setSelection(StructuredSelection.EMPTY);
	}

	protected void switchColorSetToIndex(int colorSetIndex) {
		if (colorSetIndex == -1) {
			throw new IllegalArgumentException("Invalid color set index");
		}
		//unselect the default color set button
		btn_DefaultColorSet.setSelection(false);
		//enable the color set dropdown
		cmb_ColorSet.setEnabled(true);
		//update the selection in the drop down
		LocalElement selectedColorSet = colorSets.get(colorSetIndex);
		cmbViewer_ColorSet.setSelection(new StructuredSelection(selectedColorSet));
	}


}