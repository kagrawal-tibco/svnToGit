package com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections;

import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.createTooltip;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getStateGraphPath;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.common.util.TimeOutUnitsUtils;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditorInput;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineComponentsSelector;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class TimeoutPropertySection extends AbstractStateMachinePropertySection{

	protected Text timeoutStateText;
	protected Button timeoutStateBrowseButton;
	protected CCombo unitsCombo;
	protected Button currentButton;
	protected Button allButton;
	protected Button specifiedButton;
	protected int policy; 
	protected Command command;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);

		List<String> list= new ArrayList<String>();
		//Modified by Anand - 01/17/2011 to fix BE-10395		
		for(TIMEOUT_UNITS timeout_units:TimeOutUnitsUtils.getValidTimeOutUnits()){
			list.add(timeout_units.getName());
		}
		String[] timout_unitArray = new String[list.size()];
		list.toArray(timout_unitArray);

		getWidgetFactory().createLabel(composite, Messages.getString("Units"), SWT.NONE);
		unitsCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);

		unitsCombo.setItems(timout_unitArray);

		gd = new GridData();
		gd.widthHint = 200;
		gd.horizontalIndent = 6;
		unitsCombo.setLayoutData(gd);
		unitsCombo.addModifyListener(new ModifyListener(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				State state = (State)eObject;

				if(state.getTimeoutUnits()!=null && state.getTimeoutUnits().compareTo(TIMEOUT_UNITS.getByName(unitsCombo.getText()))!=0){					
					EditingDomain editingDomain = editor.getEditingDomain();
					command=new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getStateEntity_TimeoutUnits(),TIMEOUT_UNITS.getByName(unitsCombo.getText())) ;			
					EditorUtils.executeCommand(editor, 
							command);
					tseNode.setTooltipText(createTooltip((State)eObject));
				}
				
			}});

		TimeoutStateChoiceSelectioListener listener = new TimeoutStateChoiceSelectioListener(); 
		getWidgetFactory().createLabel(composite, Messages.getString("Timeout_State_Choice"), SWT.NONE);
		Composite buttonsComposite = getWidgetFactory().createComposite(composite);
		buttonsComposite.setLayout(new GridLayout(3,false));
		buttonsComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		currentButton = getWidgetFactory().createButton(buttonsComposite, Messages.getString("Current"), SWT.RADIO);
		currentButton.setSelection(true);
		currentButton.addSelectionListener(listener);

		allButton = getWidgetFactory().createButton(buttonsComposite, Messages.getString("All"), SWT.RADIO);
		specifiedButton = getWidgetFactory().createButton(buttonsComposite, Messages.getString("Specified"), SWT.RADIO);
		
		allButton.addSelectionListener(listener);
		specifiedButton.addSelectionListener(listener);

		getWidgetFactory().createLabel(composite, Messages.getString("Timeout_State"), SWT.NONE);
		
		Composite browseComposite = getWidgetFactory().createComposite(composite);
		browseComposite.setLayout(new GridLayout(2,false));
		browseComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		timeoutStateText = getWidgetFactory().createText(browseComposite,"",  SWT.BORDER);
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
//		gd.horizontalIndent = 5;
		gd.widthHint = 617;
		timeoutStateText.setLayoutData(gd);

		timeoutStateBrowseButton = getWidgetFactory().createButton(browseComposite,  Messages.getString("Browse"), SWT.NONE);
		timeoutStateBrowseButton.addSelectionListener(listener);

		timeoutStateText.setEnabled(false);
		timeoutStateBrowseButton.setEnabled(false);
	}

	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if(eObject instanceof State){
			try{
				State state = (State)eObject;
				unitsCombo.setText(state.getTimeoutUnits().getName());
				if(state.getTimeoutPolicy() != -1){
					setTimeoutStatePolicy(state.getTimeoutPolicy());
				}
				if(state.getTimeoutState()!=null){
					timeoutStateText.setText(getStateGraphPath(state.getTimeoutState()));
				}else{
					timeoutStateText.setText("");
				}
				//Making readonly widgets
				if(!editor.isEnabled()){
					readOnlyWidgets();
				}		
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param policy
	 * @return
	 */
	public void setTimeoutStatePolicy(int policy){

		switch(policy){
		case 0:
			currentButton.setSelection(true);
			allButton.setSelection(false);
			specifiedButton.setSelection(false);
			timeoutStateText.setEnabled(false);
			timeoutStateBrowseButton.setEnabled(false);
			break;
		case 1:
			allButton.setSelection(true);
			specifiedButton.setSelection(false);
			currentButton.setSelection(false);
			timeoutStateText.setEnabled(false);
			timeoutStateBrowseButton.setEnabled(false);
			break;
		case 2:
			specifiedButton.setSelection(true);
			allButton.setSelection(false);
			currentButton.setSelection(false);
			timeoutStateText.setEnabled(true);
			timeoutStateBrowseButton.setEnabled(true);
			break;
		default:
			break;
		}
	}

	protected class TimeoutStateChoiceSelectioListener extends SelectionAdapter{

		/* (non-Javadoc)
		 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
		 */
		@Override
		public void widgetSelected(SelectionEvent e) {
			State state = (State)eObject;
			StateMachine statemachine = ((StateMachineEditorInput)editor.getEditorInput()).getStateMachine();
			EditingDomain editingDomain = editor.getEditingDomain();

			boolean sel = ((Button)e.getSource()).getSelection();
			if(e.getSource() ==  currentButton){
				if(sel){
					command=new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getState_TimeoutPolicy(),0) ;			
					EditorUtils.executeCommand(editor, 
							command);
//					if(state.getTimeoutState()!=null){
//						command=new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getState_TimeoutState(),null) ;			
//						EditorUtils.executeCommand(editor, 
//							command);
//					}
//					timeoutStateText.setText("");
					timeoutStateText.setEnabled(false);
					timeoutStateBrowseButton.setEnabled(false);
				}
			}
			if(e.getSource() ==  allButton){
				if(sel){
					command=new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getState_TimeoutPolicy(),1) ;			
					EditorUtils.executeCommand(editor, 
							command);
//					if(state.getTimeoutState()!=null){
//						command=new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getState_TimeoutState(),null) ;			
//						EditorUtils.executeCommand(editor, 
//							command);
//					}
//					timeoutStateText.setText("");
					timeoutStateText.setEnabled(false);
					timeoutStateBrowseButton.setEnabled(false);
				}
			}
			if(e.getSource() ==  specifiedButton){
				if(sel){
					command=new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getState_TimeoutPolicy(),2) ;			
					EditorUtils.executeCommand(editor, 
							command);
					timeoutStateText.setEnabled(true);
					timeoutStateBrowseButton.setEnabled(true);
				}
			}
			if(e.getSource() == timeoutStateBrowseButton){
				try{
					StateMachineComponentsSelector picker = new StateMachineComponentsSelector(getPart().getSite().getShell(),editor.getProject().getName(),statemachine,state,((State)eObject).getTimeoutState());
					if (picker.open() == Dialog.OK) {
						Object selState = picker.getFirstResult();
						if(selState !=null ){
							timeoutStateText.setText(getStateGraphPath((StateEntity)selState));
							command=new SetCommand(editingDomain, eObject, StatesPackage.eINSTANCE.getState_TimeoutState(), (State)selState) ;			
							EditorUtils.executeCommand(editor, 
									command);
						}
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}
	}
	
	private void readOnlyWidgets(){
		timeoutStateText.setEditable(false);
		timeoutStateBrowseButton.setEnabled(false);
		unitsCombo.setEnabled(false);
		currentButton.setEnabled(false);
		allButton.setEnabled(false);
		specifiedButton.setEnabled(false);
	}
}