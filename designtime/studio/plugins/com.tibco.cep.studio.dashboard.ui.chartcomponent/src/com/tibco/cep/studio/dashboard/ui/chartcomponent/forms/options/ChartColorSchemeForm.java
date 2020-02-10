package com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options;

import java.util.ArrayList;
import java.util.Collections;
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

//PATCH reuse the code by extending from com.tibco.cep.studio.dashboard.ui.chartcomponent.forms.options.ChartTableColorSchemeForm
public class ChartColorSchemeForm extends BaseForm {

	private Button btn_DefaultColorSet;

	private SelectionListener btn_DefaultColorSetSelectionListener;

	private Combo cmb_ColorSet;

	private ComboViewer cmbViewer_ColorSet;

	private ISelectionChangedListener cmbViewer_ColorSetSelectionChangedListener;

	private Button btn_DefaultSeriesColor;

	private SelectionListener btn_DefaultSeriesColorSelectionListener;

	private Combo cmb_SeriesColor;

	private ComboViewer cmbViewer_SeriesColor;

	private ISelectionChangedListener cmbViewer_SeriesColorSelectionChangedListener;

	private List<LocalElement> colorSets;

	public ChartColorSchemeForm(FormToolkit formToolKit, Composite parent) {
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

		label = createLabel(formComposite, "Series Color:", SWT.NONE);
		layoutData = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
		layoutData.widthHint = 125;
		label.setLayoutData(layoutData);

		btn_DefaultSeriesColor = createButton(formComposite, "Default", SWT.CHECK);
		btn_DefaultSeriesColor.setLayoutData(new GridData(SWT.FILL,SWT.FILL,false,false));

		cmb_SeriesColor = createCombo(formComposite, SWT.READ_ONLY);
		cmb_SeriesColor.setLayoutData(new GridData(SWT.FILL,SWT.FILL,true,false));
		cmbViewer_SeriesColor = new ComboViewer(cmb_SeriesColor);
		cmbViewer_SeriesColor.setContentProvider(new ArrayContentProvider());
		cmbViewer_SeriesColor.setLabelProvider(new LocalElementLabelProvider(false));
	}

	@Override
	protected void doEnableListeners() {
		if (btn_DefaultColorSetSelectionListener == null) {
			btn_DefaultColorSetSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					defaultColorSetButtonClicked();
				}

			};
		}
		btn_DefaultColorSet.addSelectionListener(btn_DefaultColorSetSelectionListener);

		if (cmbViewer_ColorSetSelectionChangedListener == null) {
			cmbViewer_ColorSetSelectionChangedListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					colorSetSelectionChanged();
				}

			};
		}
		cmbViewer_ColorSet.addSelectionChangedListener(cmbViewer_ColorSetSelectionChangedListener);

		if (btn_DefaultSeriesColorSelectionListener == null) {
			btn_DefaultSeriesColorSelectionListener = new AbstractSelectionListener() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					defaultSeriesColorButtonClicked();
				}

			};
		}
		btn_DefaultSeriesColor.addSelectionListener(btn_DefaultSeriesColorSelectionListener);

		if (cmbViewer_SeriesColorSelectionChangedListener == null) {
			cmbViewer_SeriesColorSelectionChangedListener = new ISelectionChangedListener() {

				@Override
				public void selectionChanged(SelectionChangedEvent event) {
					seriesColorSelectionChanged();
				}

			};
		}
		cmbViewer_SeriesColor.addSelectionChangedListener(cmbViewer_SeriesColorSelectionChangedListener);
	}

	@Override
	protected void doDisableListeners() {
		btn_DefaultColorSet.removeSelectionListener(btn_DefaultColorSetSelectionListener);
		cmbViewer_ColorSet.removeSelectionChangedListener(cmbViewer_ColorSetSelectionChangedListener);

		btn_DefaultSeriesColor.removeSelectionListener(btn_DefaultSeriesColorSelectionListener);
		cmbViewer_SeriesColor.removeSelectionChangedListener(cmbViewer_SeriesColorSelectionChangedListener);
	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		colorSets = new ArrayList<LocalElement>();
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
		//update the content provider for series color combo
		cmbViewer_SeriesColor.setInput(Collections.emptyList());
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
			propertyName = "SeriesColor";
			value = localElement.getPropertyValue(propertyName);
			int seriesColorIndex = Integer.parseInt(value);
			propertyName = null;
			if (colorSetIndex == -1 && seriesColorIndex == -1) {
				switchColorSetsToDefault();
			}
			else if (colorSetIndex != -1 && seriesColorIndex != -1) {
				switchColorSetsToIndex(colorSetIndex, seriesColorIndex);
			}
			else {
				switchColorSetsToDefault();
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

	private void defaultColorSetButtonClicked() {
		disableListeners();
		String propertyName = "ColorSet";
		try {
			if (btn_DefaultColorSet.getSelection() == true) {
				//update to defaults
				localElement.setPropertyValue(propertyName, "-1");
				propertyName = "SeriesColor";
				localElement.setPropertyValue(propertyName, "-1");
				propertyName = null;
				switchColorSetsToDefault();
			}
			else {
				localElement.setPropertyValue(propertyName, "0");
				propertyName = "SeriesColor";
				localElement.setPropertyValue(propertyName, "-1");
				propertyName = null;
				switchColorSetsToIndex(0, -1);
			}
		} catch (Exception e) {
			if (propertyName != null) {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not update "+propertyName,e));
			}
			else {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not refresh selections in "+getTitle(),e));
			}
		} finally {
			enableListeners();
		}
	}

	private void colorSetSelectionChanged(){
		disableListeners();
		IStructuredSelection selection = (IStructuredSelection) cmbViewer_ColorSet.getSelection();
		LocalElement selectedColorSet = (LocalElement) selection.getFirstElement();
		int index = colorSets.indexOf(selectedColorSet);
		String propertyName = "ColorSet";
		try {
			localElement.setPropertyValue(propertyName, String.valueOf(index));
			propertyName = "SeriesColor";
			localElement.setPropertyValue(propertyName, "-1");
			propertyName = null;
			switchColorSetsToIndex(index, -1);
		} catch (Exception e) {
			if (propertyName != null) {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not update "+propertyName,e));
			}
			else {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not refresh selections in "+getTitle(),e));
			}
		} finally {
			enableListeners();
		}
	}

	private void defaultSeriesColorButtonClicked(){
		disableListeners();
		String propertyName = "SeriesColor";
		try {
			if (btn_DefaultSeriesColor.getSelection() == true) {
				localElement.setPropertyValue(propertyName, "-1");
				propertyName = null;
				switchSeriesColorToDefault();
			}
			else {
				localElement.setPropertyValue(propertyName, "0");
				propertyName = null;
				switchSeriesColorToIndex(0);
			}
		} catch (Exception e) {
			if (propertyName != null) {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not update "+propertyName,e));
			}
			else {
				logAndAlert(new Status(IStatus.ERROR,getPluginId(),"Could not refresh selections in "+getTitle(),e));
			}
		} finally {
			enableListeners();
		}
	}

	private void seriesColorSelectionChanged() {
		disableListeners();
		try {
			//get the current component color set
			IStructuredSelection selection = (IStructuredSelection) cmbViewer_ColorSet.getSelection();
			LocalElement selectedColorSet = (LocalElement) selection.getFirstElement();
			selection = (IStructuredSelection) cmbViewer_SeriesColor.getSelection();
			LocalElement selectedSeriesColor = (LocalElement) selection.getFirstElement();
			int index = selectedColorSet.getChildren(BEViewsElementNames.SERIES_COLOR).indexOf(selectedSeriesColor);
			localElement.setPropertyValue("SeriesColor", String.valueOf(index));
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR,getPluginId(),"could not update series color index in "+localElement.getDisplayableName(),e));
		} finally {
			enableListeners();
		}
	}

	private void switchColorSetsToDefault(){
		//select the default color set button
		btn_DefaultColorSet.setSelection(true);
		//disable the color set dropdown
		cmb_ColorSet.setEnabled(false);
		//reset the selection in the drop down
		cmbViewer_ColorSet.setSelection(StructuredSelection.EMPTY);
		//select the default series color button
		btn_DefaultSeriesColor.setSelection(true);
		//disable the series color button
		btn_DefaultSeriesColor.setEnabled(false);
		//disable the series color dropdown
		cmb_SeriesColor.setEnabled(false);
		//reset the input to the series color dropdown
		cmbViewer_SeriesColor.setInput(Collections.emptyList());
		//reset the selection in the series color dropdown
		cmbViewer_SeriesColor.setSelection(StructuredSelection.EMPTY);
	}

	private void switchColorSetsToIndex(int colorSetIndex, int seriesColorIndex) throws Exception{
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
		//enable the series color button
		btn_DefaultSeriesColor.setEnabled(true);
		//select the default series color button
		btn_DefaultSeriesColor.setSelection(seriesColorIndex == -1);
		//enable/disable the series color dropdown
		cmb_SeriesColor.setEnabled(seriesColorIndex != -1);
		//update the input to the series color dropdown
		List<LocalElement> seriesColors = selectedColorSet.getChildren(BEViewsElementNames.SERIES_COLOR);
		cmbViewer_SeriesColor.setInput(seriesColors);
		//reset the selection in the series color dropdown
		if (seriesColorIndex == -1) {
			cmbViewer_SeriesColor.setSelection(StructuredSelection.EMPTY);
		}
		else {
			cmbViewer_SeriesColor.setSelection(new StructuredSelection(seriesColors.get(seriesColorIndex)));
		}
	}

	private void switchSeriesColorToDefault(){
		btn_DefaultSeriesColor.setSelection(true);
		//disable the series color dropdown
		cmb_SeriesColor.setEnabled(false);
		//reset the selection in the series color dropdown
		cmbViewer_SeriesColor.setSelection(StructuredSelection.EMPTY);
	}

	private void switchSeriesColorToIndex(int seriesColorIndex) throws Exception{
		if (seriesColorIndex == -1) {
			throw new IllegalArgumentException("Invalid series color index");
		}
		btn_DefaultSeriesColor.setSelection(false);
		//disable the series color dropdown
		cmb_SeriesColor.setEnabled(true);
		//update the selection in the series color dropdown
		IStructuredSelection selection = (IStructuredSelection) cmbViewer_ColorSet.getSelection();
		LocalElement selectedColorSet = (LocalElement) selection.getFirstElement();
		List<LocalElement> seriesColors = selectedColorSet.getChildren(BEViewsElementNames.SERIES_COLOR);
		if (seriesColorIndex >= seriesColors.size()) {
			cmbViewer_SeriesColor.setSelection(StructuredSelection.EMPTY);
		}
		else {
			cmbViewer_SeriesColor.setSelection(new StructuredSelection(seriesColors.get(seriesColorIndex)));
		}
	}

}