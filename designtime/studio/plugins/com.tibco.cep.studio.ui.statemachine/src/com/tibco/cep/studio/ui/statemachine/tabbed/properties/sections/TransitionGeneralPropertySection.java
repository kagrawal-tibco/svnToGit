package com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.addSymbol;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
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

import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.Messages;
import com.tomsawyer.graphicaldrawing.TSEEdge;
import com.tomsawyer.graphicaldrawing.TSEEdgeLabel;

/**
 * 
 * @author sasahoo
 *
 */
public class TransitionGeneralPropertySection extends AbstractStateMachinePropertySection{
	
	protected Text nameText;
	protected Text labelText;
	protected Button noConditionButton;
	protected Button timeoutActionButton;
	protected Button timeoutStateBrowseButton;
	protected CCombo priorityCombo;

	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		
		super.createControls(parent, tabbedPropertySheetPage);
		Composite composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2, false));
		
		getWidgetFactory().createLabel(composite, Messages.getString("Name"),  SWT.NONE);
		nameText = getWidgetFactory().createText(composite,"",  SWT.BORDER | SWT.READ_ONLY);
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nameText.setLayoutData(gd);
		nameText.setEnabled(false);
			
        getWidgetFactory().createLabel(composite, Messages.getString("Label"),  SWT.NONE);
        labelText = getWidgetFactory().createText(composite,"",  SWT.BORDER);
        gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		labelText.setLayoutData(gd);
		
		labelText.addModifyListener(new ModifyListener(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				modifyLabel();
			}});
		
		getWidgetFactory().createLabel(composite, Messages.getString("No_Condition"), SWT.NONE);
		noConditionButton = getWidgetFactory().createButton(composite, "", SWT.CHECK);
		noConditionButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(noConditionButton.getSelection()){
					priorityCombo.setEnabled(false);
				}else{
					priorityCombo.setEnabled(true);
				}
				EditingDomain editingDomain = editor.getEditingDomain();
				Command setCommand=new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getStateTransition_Lambda(),noConditionButton.getSelection()) ;			
				EditorUtils.executeCommand(editor, 
						setCommand);
			}});
		
		getWidgetFactory().createLabel(composite, Messages.getString("Priority"), SWT.NONE);
		priorityCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);
		priorityCombo.setItems(new String[]{"1(Highest)","2","3","4","5","6","7","8","9","10(Lowest)"});
		priorityCombo.setText("5");
		gd = new GridData();
		gd.widthHint = 200;
		priorityCombo.setLayoutData(gd);
		priorityCombo.addModifyListener(new ModifyListener(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				int i = 5;
				if(priorityCombo.getText().endsWith("1(Highest)")){
					i = 1;
				}else if(priorityCombo.getText().endsWith("10(Lowest)")){
					i = 10;
				}else{
					i = Integer.parseInt(priorityCombo.getText());
				}

				Rule rule = ((StateTransition)eObject).getGuardRule();
				if(rule.getPriority()!= i ){
					rule.setPriority(i);
					editor.modified();
				}
			}});
	}
	
	private void modifyLabel(){
		try{
			if(((StateTransition)eObject).getLabel() == null){
				((StateTransition)eObject).setLabel("");
			}
			String label = labelText.getText().trim();
			if(!((StateTransition)eObject).getLabel().equalsIgnoreCase(label)){
				((TSEEdgeLabel)(((TSEEdge)tsedge).labels().get(0))).setName(label);
				tsedge.setName(labelText.getText());
				EditingDomain editingDomain = editor.getEditingDomain();
				Command setCommand=new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getStateTransition_Label(),label) ;			
				EditorUtils.executeCommand(editor, 
						setCommand);
			}
			doRefreshAll((TSEEdge)tsedge);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if (eObject instanceof StateTransition) {
			try {
				StateTransition transition = (StateTransition) eObject;
				nameText.setText(transition.getName());
				if (transition.getLabel() != null) {
					labelText.setText(transition.getLabel());
				}
				noConditionButton.setSelection(transition.isLambda());
				if (transition.getGuardRule() != null) {
					String priority = transition.getGuardRule().getPriority() != -1
							? Integer.toString(transition.getGuardRule().getPriority())
							: "5";
					if (priority.equals("1")) {
						priority = "1(Highest)";
					} else if (priority.equals("10")) {
						priority = "10(Lowest)";
					}
					priorityCombo.setText(priority);
				} else {
					Rule rule = RuleFactory.eINSTANCE.createRule();
					rule.setPriority(5);
					StateMachine stateMachine = editor.getStateMachine();
					addSymbol(stateMachine.getOwnerConceptPath(), stateMachine.getOwnerProjectName(),
							rule.getSymbols().getSymbolMap());
					transition.setGuardRule(rule);
				}
				if (transition.isLambda()) {
					priorityCombo.setEnabled(false);
				} else {
					priorityCombo.setEnabled(true);
				}
				if (!editor.isEnabled()) {
					readOnlyWidgets();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void readOnlyWidgets() {
		nameText.setEditable(false);
		labelText.setEditable(false);
		noConditionButton.setEnabled(false);
		priorityCombo.setEnabled(false);
	}
}