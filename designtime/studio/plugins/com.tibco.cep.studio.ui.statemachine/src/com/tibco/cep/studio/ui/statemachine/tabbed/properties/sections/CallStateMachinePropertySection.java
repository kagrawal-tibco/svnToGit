package com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections;

import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.doStateNameValidation;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditor;
import com.tibco.cep.studio.ui.statemachine.diagram.editors.StateMachineEditorInput;
import com.tibco.cep.studio.ui.statemachine.diagram.tool.StateMachineSelector;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.Messages;
import com.tibco.cep.studio.ui.statemachine.tabbed.properties.TextChangeHelper;

/**
 * 
 * @author sasahoo
 *
 */
public class CallStateMachinePropertySection extends AbstractStateMachinePropertySection {
	/**
	 * the text control for the section.
	 */
	protected Text nameText;
	protected Text descriptionText;
	protected Text stateMachineName;
	protected Button callExplicitlyButton;
	protected Button stateMachineBrowseButton;
	private StateMachine selectedStateMachine;
	private StateURIModifyListener stateURIModifyListener;
	
	/**
	 * A helper to listen for events that indicate that a text field has been
	 * changed.
	 */
	protected TextChangeHelper listener;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
        getWidgetFactory().createLabel(composite, Messages.getString("Name"),  SWT.NONE);
        nameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
        GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nameText.setLayoutData(gd);

		nameText.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				if(!doStateNameValidation(tseNode, nameText)) {
					return;
				}
				handleTextModified(nameText.getText(), ModelPackage.eINSTANCE.getEntity_Name());
			}});

		getWidgetFactory().createLabel(composite, Messages.getString("Description"),  SWT.NONE).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		descriptionText = getWidgetFactory().createText(composite,"",  SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
        gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 600;
		gd.heightHint = 30;
		descriptionText.setLayoutData( gd);
		descriptionText.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				handleTextModified(descriptionText.getText(), ModelPackage.eINSTANCE.getEntity_Description());
			}});
		
		getWidgetFactory().createLabel(composite, Messages.getString("statemodel.call.explicitly"),  SWT.NONE);
		callExplicitlyButton = getWidgetFactory().createButton(composite, "", SWT.CHECK);
		callExplicitlyButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				((StateSubmachine)eObject).setCallExplicitly(callExplicitlyButton.getSelection());
				editor.modified();
			}});
		
		getWidgetFactory().createLabel(composite,  Messages.getString("Call_State_Model_Name"),  SWT.NONE);
		Composite browseComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		browseComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		stateMachineName = getWidgetFactory().createText(browseComposite,"",  SWT.BORDER);
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 617;
		stateMachineName.setLayoutData( gd);
		stateMachineBrowseButton = getWidgetFactory().createButton(browseComposite, Messages.getString("Browse"), SWT.NONE);
		stateMachineBrowseButton.setToolTipText(Messages.getString("Call_State_Model_Browse"));
		stateMachineBrowseButton.addSelectionListener(new SelectionAdapter(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					selectedStateMachine = null;
					StateMachine statemachine = ((StateMachineEditorInput)editor.getEditorInput()).getStateMachine();
					StateSubmachine stateSubmachine = (StateSubmachine)tseNode.getUserObject();
//					EditingDomain editingDomain = editor.getEditingDomain();
					StateMachine ownerStateMachine =  (StateMachine)IndexUtils.getEntity(statemachine.getOwnerProjectName(), 
							stateSubmachine.getURI(), ELEMENT_TYPES.STATE_MACHINE);
					StateMachineSelector picker = new StateMachineSelector(	getPart().getSite().getShell(),
							editor.getProject().getName(),
							statemachine,
							statemachine.getOwnerConceptPath(),
							ownerStateMachine, 
							true);
					if (picker.open() == Dialog.OK) {
						if(picker.getFirstResult() instanceof StateMachine){
							selectedStateMachine = (StateMachine) picker.getFirstResult();
							stateMachineName.setText(selectedStateMachine.getFullPath());
						}
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}});
	}

	/**
	 * @param isSelected
	 * @param feature
	 */
	protected void handleButtonModified(boolean isSelected,EAttribute feature) {
		boolean equals = isEqualBoolean(isSelected, feature);
		if (!equals) {
			EditingDomain editingDomain = ((StateMachineEditor) getPart()).getEditingDomain();
			if (nodeList.size() == 1) {
				EditorUtils.executeCommand((StateMachineEditor) getPart(), 
						SetCommand.create(editingDomain, eObject, feature,isSelected));
			} 
		}
	}

	public void modifyStateMachineName() {
		try{
			if (!EditorUtils.checkPermissions((StateMachineEditor) getPart())) {
				return;
			}
			stateMachineName.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
			stateMachineName.setToolTipText("");
			StateSubmachine stateSubmachine = (StateSubmachine)tseNode.getUserObject();
			EditingDomain editingDomain = editor.getEditingDomain();
			if(stateMachineName.getText().equalsIgnoreCase("")){
				if(stateSubmachine.getOwnerStateMachine() !=  null){
//					Command command =new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), null) ;			
//					EditorUtils.executeCommand((StateMachineEditor) getPart(), command);
					Command command = new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getStateSubmachine_URI(), null) ;			
					EditorUtils.executeCommand(editor, command);
					return;
				}
			}
			Entity entity = IndexUtils.getEntity(getProject().getName(), stateMachineName.getText().trim());
			if(!stateMachineName.getText().trim().equals("")){ 
			    if(entity == null){
			    	stateMachineName.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", stateMachineName.getText(), Messages.getString("Call_State_Model_Name"));
					stateMachineName.setToolTipText(problemMessage);
					return;
			    }
			    if(!(entity instanceof StateMachine)){
			    	stateMachineName.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					String problemMessage = com.tibco.cep.studio.ui.util.Messages.getString("invalid.entry", stateMachineName.getText(), Messages.getString("Call_State_Model_Name"));
					stateMachineName.setToolTipText(problemMessage);
					return;
			    }
			    if(stateSubmachine.getURI() == null){
//			    	Command command =new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(),entity) ;			
//			    	EditorUtils.executeCommand(editor, command);
			    	Command command = new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getStateSubmachine_URI(), entity.getFullPath()) ;			
					EditorUtils.executeCommand(editor, command);
					return;
			    }
			    if( !stateSubmachine.getURI().equals(entity.getFullPath())){
//					Command command =new SetCommand(editingDomain, eObject, StatesPackage.eINSTANCE.getStateEntity_OwnerStateMachine(), entity) ;			
//					EditorUtils.executeCommand(editor, command);
			    	Command command = new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getStateSubmachine_URI(), entity.getFullPath()) ;			
					EditorUtils.executeCommand(editor, command);
			    }
			}
		}
		catch(Exception e1){
			e1.printStackTrace();
		}
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if(tseNode != null){
			StateSubmachine stateSubMachine = (StateSubmachine)tseNode.getUserObject();
			if(stateSubMachine == null) {
				return;
			}
			nameText.setText(stateSubMachine.getName());
			if(stateSubMachine.getDescription()!= null) {
				descriptionText.setText(stateSubMachine.getDescription());
			}
			callExplicitlyButton.setSelection(stateSubMachine.isCallExplicitly());
			
			if(stateURIModifyListener != null) {
				stateMachineName.removeModifyListener(stateURIModifyListener);
			}
			if(stateURIModifyListener == null) {
				stateURIModifyListener = new StateURIModifyListener();
			}
			if(stateSubMachine.getURI()!= null) {
				stateMachineName.setText(stateSubMachine.getURI());
			}else{
				stateMachineName.setText("");
			}
			stateMachineName.addModifyListener(stateURIModifyListener);

			//Making readonly widgets
			if(!editor.isEnabled()){
				readOnlyWidgets();
			}	
		}
	}

	/**
	 * @param boolValue
	 * @param feature
	 * @return
	 */
	protected boolean isEqualBoolean(boolean boolValue, EAttribute feature) {
		return getFeatureAsBoolean(feature)== boolValue;
	}
	
	/**
	 * @param feature
	 * @return
	 */
	protected boolean getFeatureAsBoolean(EAttribute feature) {
		return Boolean.parseBoolean((String)eObject.eGet(feature));
	}
	
	private void readOnlyWidgets(){
		nameText.setEditable(false);
		descriptionText.setEditable(false);
		stateMachineName.setEditable(false);
		callExplicitlyButton.setEnabled(false);
		stateMachineBrowseButton.setEnabled(false);
	}
	
	private class StateURIModifyListener implements ModifyListener{

		/* (non-Javadoc)
		 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
		 */
		@Override
		public void modifyText(ModifyEvent e) {
			modifyStateMachineName();
		}
	}
}