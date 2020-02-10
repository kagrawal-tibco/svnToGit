package com.tibco.cep.studio.dashboard.ui.forms;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalActionRule;
import com.tibco.cep.studio.dashboard.core.insight.model.configs.LocalRangeAlert;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalParticle;
import com.tibco.cep.studio.dashboard.utils.BEViewsElementNames;

public class AlertSettingsForm extends BaseForm {

	private List lst_Ranges;
	private ListViewer lstViewer_Ranges;
	private AbstractSelectionListener lst_RangesSelectionListener;

	private Button btn_Add;
	private AbstractSelectionListener btn_AddSelectionListener;
	private Button btn_Remove;
	private AbstractSelectionListener btn_RemoveSelectionListener;
	private Button btn_Up;
	private AbstractSelectionListener btn_UpSelectionListener;
	private Button btn_Down;
	private AbstractSelectionListener btn_DownSelectionListener;

	private RangeDetailsForm rangeDetailsForm;
	private PropertyChangeListener rangeNameChangeListener;

	private VisualAlertActionSettingsForm visualAlertActionSettingsForm;
	private LocalActionRule localActionRule;

	private int orientation;

	private LocalElement currAlert;

	public AlertSettingsForm(FormToolkit formToolKit, Composite parent, int orientation) {
		super("Ranges", formToolKit, parent, true);
		if (orientation != SWT.HORIZONTAL && orientation != SWT.VERTICAL){
			throw new IllegalArgumentException("orientation should either be SWT.HORIZONTAL or SWT.VERTICAL");
		}
		this.orientation = orientation;
	}

	@Override
	public void init() {
		if (orientation == SWT.HORIZONTAL){
			formComposite.setLayout(new GridLayout(4, false));
		}
		else {
			formComposite.setLayout(new GridLayout(3, false));
		}

		// range list
		lst_Ranges = createList(formComposite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		GridData lst_RangesLayoutData = new GridData(SWT.FILL, SWT.FILL, false, false);
		lst_RangesLayoutData.widthHint = 50;
		lst_RangesLayoutData.heightHint = 120;
		if (orientation == SWT.VERTICAL){
			lst_RangesLayoutData.verticalSpan = 2;
		}
		lst_Ranges.setLayoutData(lst_RangesLayoutData);

		// buttons
		Composite buttonsComposite = createComposite(formComposite, SWT.NONE);
		FillLayout buttonsCompositeLayout = new FillLayout(SWT.VERTICAL);
		buttonsCompositeLayout.spacing = 5;
		buttonsComposite.setLayout(buttonsCompositeLayout);
		btn_Add = createButton(buttonsComposite, "Add", SWT.PUSH);
		btn_Remove = createButton(buttonsComposite, "Remove", SWT.PUSH);
		btn_Up = createButton(buttonsComposite, "Up", SWT.PUSH);
		btn_Down = createButton(buttonsComposite, "Down", SWT.PUSH);
		GridData buttonsCompositeLayoutData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		if (orientation == SWT.VERTICAL){
			buttonsCompositeLayoutData.verticalSpan = 2;
		}
		buttonsComposite.setLayoutData(buttonsCompositeLayoutData);

		// range details
		rangeDetailsForm = new RangeDetailsForm(formToolKit,formComposite);
		rangeDetailsForm.init();
		rangeDetailsForm.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//INFO we do not add range details form as child form, since we want to give a different input to it

		// action details
		visualAlertActionSettingsForm = new VisualAlertActionSettingsForm(formToolKit,formComposite);
		visualAlertActionSettingsForm.init();
		visualAlertActionSettingsForm.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		//INFO INFO we do not add visual alert action details form as child form, since we want to give a different input to it

		lstViewer_Ranges = new ListViewer(lst_Ranges);
		lstViewer_Ranges.setContentProvider(new ArrayContentProvider());
		lstViewer_Ranges.setLabelProvider(new LocalElementLabelProvider(false));

	}

	@Override
	protected void inputChanged(LocalElement oldLocalElement, LocalElement newLocalElement) throws Exception {
		this.localActionRule = new LocalActionRule();
		if (newLocalElement != null) {
//		if (newLocalElement instanceof LocalSeriesConfig){
			this.localActionRule = (LocalActionRule)newLocalElement.getElement(BEViewsElementNames.ACTION_RULE);
//		}
//		else {
//			throw new IllegalArgumentException(newLocalElement+" is not a series config");
//		}
		}
	}

	@Override
	protected void doEnableListeners() {
		if (lst_RangesSelectionListener == null){
			lst_RangesSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					rangeSelectionChanged();
				}

			};
		}
		lst_Ranges.addSelectionListener(lst_RangesSelectionListener);

		if (btn_AddSelectionListener == null){
			btn_AddSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					addRange();
				}

			};
		}
		btn_Add.addSelectionListener(btn_AddSelectionListener);

		if (btn_RemoveSelectionListener == null){
			btn_RemoveSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					removeRange();
				}

			};
		}
		btn_Remove.addSelectionListener(btn_RemoveSelectionListener);

		if (btn_UpSelectionListener == null){
			btn_UpSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					moveRangeUp();
				}

			};
		}
		btn_Up.addSelectionListener(btn_UpSelectionListener);

		if (btn_DownSelectionListener == null){
			btn_DownSelectionListener = new AbstractSelectionListener(){

				@Override
				public void widgetSelected(SelectionEvent e) {
					moveRangeDown();
				}

			};
		}
		btn_Down.addSelectionListener(btn_DownSelectionListener);

		if (rangeNameChangeListener == null){
			rangeNameChangeListener = new PropertyChangeListener(){

				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					rangeNameChanged();
				}

			};
		}
		rangeDetailsForm.addPropertyChangeListener(RangeDetailsForm.PROP_KEY_RANGE_NAME, rangeNameChangeListener);

		rangeDetailsForm.enableListeners();
		visualAlertActionSettingsForm.enableListeners();
	}

	@Override
	protected void doDisableListeners() {
		lst_Ranges.removeSelectionListener(lst_RangesSelectionListener);
		btn_Add.removeSelectionListener(btn_AddSelectionListener);
		btn_Remove.removeSelectionListener(btn_RemoveSelectionListener);
		btn_Up.removeSelectionListener(btn_UpSelectionListener);
		btn_Down.removeSelectionListener(btn_DownSelectionListener);

		rangeDetailsForm.removePropertyChangeListener(RangeDetailsForm.PROP_KEY_RANGE_NAME, rangeNameChangeListener);

		rangeDetailsForm.disableListeners();
		visualAlertActionSettingsForm.disableListeners();
	}



	@Override
	public void refreshEnumerations() {
		try {
			//store current alert
			IStructuredSelection selection = (IStructuredSelection) lstViewer_Ranges.getSelection();
			if (selection.isEmpty() == true) {
				currAlert = null;
			}
			else {
				currAlert = (LocalElement) selection.getFirstElement();
			}
			lstViewer_Ranges.setInput(localActionRule.getChildren(BEViewsElementNames.ALERT));
			if (rangeDetailsForm.getInput() != null) {
				rangeDetailsForm.refreshEnumerations();
			}
			if (visualAlertActionSettingsForm.getInput() != null) {
				visualAlertActionSettingsForm.refreshEnumerations();
			}
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh enumerations in " + this.getClass().getName(), e));
			disableAll();
		}
	}

	@Override
	public void refreshSelections() {
		try {
			java.util.List<LocalElement> alerts = localActionRule.getChildren(BEViewsElementNames.ALERT);
			boolean noAlerts = alerts.isEmpty();
			if (noAlerts == false) {
				if (currAlert == null || alerts.indexOf(currAlert) == -1) {
					currAlert = alerts.get(0);
				}
				lstViewer_Ranges.setSelection(new StructuredSelection(currAlert),true);
			}
			//we need to fire the range selection logic since the listener are not attached during refresh cycles
			//FIXME we have a double firing problem when refreshSelection will be called independently
			rangeSelectionChanged();
		} catch (Exception e) {
			log(new Status(IStatus.ERROR, getPluginId(), "could not refresh selections in " + this.getClass().getName(), e));
			disableAll();
		} finally {
			currAlert = null;
		}
	}

	@Override
	public void disableAll() {
		super.disableAll();
		rangeDetailsForm.disableAll();
		visualAlertActionSettingsForm.disableAll();
	}

	@Override
	public void enableAll() {
		super.enableAll();
		rangeDetailsForm.enableAll();
		visualAlertActionSettingsForm.enableAll();
	}

	protected void addRange() {
		try {
			LocalElement rangeAlert = localActionRule.createLocalElement(BEViewsElementNames.ALERT);
			rangeAlert.createLocalElement(LocalRangeAlert.ELEMENT_KEY_ACTION);
			//reset the range list
			lstViewer_Ranges.setInput(localActionRule.getChildren(BEViewsElementNames.ALERT));
			//update the range list selection to the newly created range alert
			lstViewer_Ranges.setSelection(new StructuredSelection(rangeAlert));
			//fire range selection to update the range details & visual alert settings forms
			rangeSelectionChanged();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not add a new range", e));
		}
	}

	protected void removeRange() {
		try {
			//retrieve existing selection and it's index
			LocalElement selectedRange = (LocalElement) ((IStructuredSelection)lstViewer_Ranges.getSelection()).getFirstElement();
			int selectedRangeIndex = localActionRule.getChildren(BEViewsElementNames.ALERT).indexOf(selectedRange);
			//remove range from action rule
			localActionRule.removeElementByID(BEViewsElementNames.ALERT, selectedRange.getID(), selectedRange.getFolder());
			java.util.List<LocalElement> ranges = localActionRule.getChildren(BEViewsElementNames.ALERT);
			//update the list with new list
			lstViewer_Ranges.setInput(ranges);
			//update the range list selection to new range value
			//update selection to range below the deleted range
			//except when the deleted range is last, in that case select the range above the deleted range as
			if (selectedRangeIndex == ranges.size()){
				selectedRangeIndex--;
			}
			if (selectedRangeIndex >= 0){
				lstViewer_Ranges.setSelection(new StructuredSelection(ranges.get(selectedRangeIndex)));
			}
			//fire range selection to update the range details & visual alert settings forms
			rangeSelectionChanged();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not remove selected range", e));
		}
	}

	protected void moveRangeUp(){
		try {
			//get selected range
			IStructuredSelection selection = (IStructuredSelection)lstViewer_Ranges.getSelection();
			LocalElement selectedRange = (LocalElement) selection.getFirstElement();

			LocalParticle rangeAlertsParticle = localActionRule.getParticle(BEViewsElementNames.ALERT);
			java.util.List<LocalElement> ranges = rangeAlertsParticle.getElements(true);

			//get the range above the selected range
			LocalElement rangeAboveSelection = ranges.get(ranges.indexOf(selectedRange)-1);
			//swap select range with selection above it , resulting in upward movement of selection
			rangeAlertsParticle.swapElements(selectedRange, rangeAboveSelection);
			//reset the range list
			lstViewer_Ranges.setInput(localActionRule.getChildren(BEViewsElementNames.ALERT));
			//update the range list selection to the existing selection (since setInput wipes out selection)
			lstViewer_Ranges.setSelection(selection,true);
			//only update the range buttons , since the range detail & visual alert settings form is already showing the selected range
			updateRangeButtonState();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not move selected range up", e));
		}
	}

	protected void moveRangeDown(){
		try {
			//get selected range
			IStructuredSelection selection = (IStructuredSelection)lstViewer_Ranges.getSelection();
			LocalElement selectedRange = (LocalElement) selection.getFirstElement();

			LocalParticle rangeAlertsParticle = localActionRule.getParticle(BEViewsElementNames.ALERT);
			java.util.List<LocalElement> ranges = rangeAlertsParticle.getElements(true);

			//get the range below the selected range
			LocalElement rangeBelowSelection = ranges.get(ranges.indexOf(selectedRange)+1);
			//swap select range with selection below it , resulting in downward movement of selection
			rangeAlertsParticle.swapElements(selectedRange, rangeBelowSelection);
			//reset the range list
			lstViewer_Ranges.setInput(localActionRule.getChildren(BEViewsElementNames.ALERT));
			//update the range list selection to the existing selection (since setInput wipes out selection)
			lstViewer_Ranges.setSelection(selection);
			//only update the range buttons , since the range detail & visual alert settings form is already showing the selected range
			updateRangeButtonState();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not move selected range down", e));
		}
	}

	protected void rangeSelectionChanged() {
		try {
			StructuredSelection selection = (StructuredSelection)lstViewer_Ranges.getSelection();
			if (selection.isEmpty() == true){
				rangeDetailsForm.setInput(null);
				rangeDetailsForm.disableAll();
				visualAlertActionSettingsForm.setInput(null);
				visualAlertActionSettingsForm.disableAll();
			}
			else {
				rangeDetailsForm.enableAll();
				LocalRangeAlert rangeAlert = (LocalRangeAlert) selection.getFirstElement();
				rangeDetailsForm.setInput(rangeAlert);
				java.util.List<LocalElement> alerts = rangeAlert.getChildren(LocalRangeAlert.ELEMENT_KEY_ACTION);
				if (alerts.isEmpty() == false) {
					visualAlertActionSettingsForm.enableAll();
					visualAlertActionSettingsForm.setInput(alerts.get(0));
				}
				else {
					visualAlertActionSettingsForm.disableAll();
				}
			}
			//update range buttons
			updateRangeButtonState();
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not range selection", e));
		}
	}

	@SuppressWarnings("unchecked")
	private void updateRangeButtonState() {
		//btn_Add is always enabled
		ISelection selection = lstViewer_Ranges.getSelection();
		if (selection.isEmpty() == true){
			//we got empty selection, disable all buttons except btn_Add
			btn_Remove.setEnabled(false);
			btn_Up.setEnabled(false);
			btn_Down.setEnabled(false);
			return;
		}
		//we have a selection
		//enable remove button
		btn_Remove.setEnabled(true);
		java.util.List<LocalElement> ranges = (java.util.List<LocalElement>) lstViewer_Ranges.getInput();
		if (ranges.size() == 1){
			//we have only one series , disable up and down button
			btn_Up.setEnabled(false);
			btn_Down.setEnabled(false);
			return;
		}
		LocalElement selectedRange = (LocalElement) ((StructuredSelection)selection).getFirstElement();
		//enable up button for any range which is not at the top
		btn_Up.setEnabled(ranges.indexOf(selectedRange) != 0);
		//enable down button for any range which is not at the bottom
		btn_Down.setEnabled(ranges.indexOf(selectedRange) != ranges.size() - 1);
	}

	protected void rangeNameChanged(){
		try {
			//get selection
			ISelection selection = lstViewer_Ranges.getSelection();
			//reset the range list
			lstViewer_Ranges.setInput(localActionRule.getChildren(BEViewsElementNames.ALERT));
			//update the range list selection to the existing selection (since setInput wipes out selection)
			lstViewer_Ranges.setSelection(selection);
			//nothing else needs update , since the selection has not changed
		} catch (Exception e) {
			logAndAlert(new Status(IStatus.ERROR, getPluginId(), "could not process range name changes", e));
		}
	}

	protected VisualAlertActionSettingsForm getVisualAlertActionSettingsForm() {
		return visualAlertActionSettingsForm;
	}
}
