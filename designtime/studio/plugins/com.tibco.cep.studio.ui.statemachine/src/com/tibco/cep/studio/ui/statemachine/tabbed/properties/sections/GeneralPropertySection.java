package com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections;

import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.doStateNameValidation;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.updateStateMachineRuleSymbols;
import static com.tibco.cep.studio.ui.statemachine.diagram.utils.StateMachineUtils.updateStateTransitionRulesSymbols;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.TIMEOUT_UNITS;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.common.util.TimeOutUnitsUtils;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.statemachine.diagram.utils.Messages;
import com.tibco.cep.studio.ui.statemachine.wizards.OwnerConceptSelector;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class GeneralPropertySection extends AbstractStateMachinePropertySection {

	protected Text nameText;
	protected Text descriptionText;
	protected Text ownerConceptText;
	protected Button isMainButton;
	protected Label isMainLabel;
	protected Label ownerConceptLabel;
	protected Label unitsLabel;
    protected Button browseButton;
    protected CCombo unitsCombo;
    protected Composite composite;
	private FocusListener fFocusListener;
    
	/**
	 * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
	 *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		composite = getWidgetFactory().createComposite(parent);
		composite.setLayout(new GridLayout(2,false));
		getWidgetFactory().createLabel(composite, Messages.getString("Name"),  SWT.NONE);
		nameText = getWidgetFactory().createText(composite,"",  SWT.BORDER);

		nameText.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				if(!doStateNameValidation(tseNode, nameText)) return;
				handleTextModified(nameText.getText().trim(), ModelPackage.eINSTANCE.getEntity_Name());
			}});
		nameText.addFocusListener(getFocusListener());
		GridData gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 615;
		nameText.setLayoutData(gd);
	
		getWidgetFactory().createLabel(composite, Messages.getString("Description"),  SWT.NONE).setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
		descriptionText = getWidgetFactory().createText(composite,"",  SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.WRAP);
		
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 600;
		gd.heightHint = 40;
		descriptionText.setLayoutData(gd);

		descriptionText.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent e) {
				handleTextModified(descriptionText.getText(), ModelPackage.eINSTANCE.getEntity_Description());
			}});
		descriptionText.addFocusListener(getFocusListener());

		ownerConceptLabel = getWidgetFactory().createLabel(composite, Messages.getString("ownerConcept"),  SWT.NONE);
		
		Composite browseComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2,false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		browseComposite.setLayout(layout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		browseComposite.setLayoutData(gd);
		
		ownerConceptText = getWidgetFactory().createText(browseComposite,"",  SWT.BORDER | SWT.READ_ONLY);
		gd = new GridData(/*GridData.FILL_HORIZONTAL*/);
		gd.widthHint = 617;
		ownerConceptText.setLayoutData(gd);
		
		browseButton = new Button(browseComposite, SWT.NONE);
		browseButton.setText(Messages.getString("Browse"));
		browseButton.addSelectionListener(new SelectionAdapter() {
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			public void widgetSelected(SelectionEvent e) {
				try{
					OwnerConceptSelector picker = new OwnerConceptSelector(Display.getDefault().getActiveShell(),((StateMachine)eObject).getOwnerProjectName(), ownerConceptText.getText().trim());
					if (picker.open() == Dialog.OK) {
						if(picker.getFirstResult() instanceof Concept){
							ownerConceptText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
							StateMachine stateMachine = (StateMachine)eObject;
							String oldConceptPath = stateMachine.getOwnerConceptPath();
							Concept newConcept = (Concept) picker.getFirstResult();
							ownerConceptText.setText(newConcept.getFullPath());
							//if(oldConceptPath.intern() != newConcept.getFullPath().intern()){
							editor.setOldOwnerConceptPath(oldConceptPath);
							editor.setNewOwnerConceptPath(newConcept.getFullPath());
							Command isMainCommand=new SetCommand(editor.getEditingDomain(),
									stateMachine,StatesPackage.eINSTANCE.getStateMachine_OwnerConceptPath(),newConcept.getFullPath()) ;			
							EditorUtils.executeCommand(editor, 
									isMainCommand);
							editor.setOwnerConceptDefined(true);
							try{
								updateStateMachineRuleSymbols(stateMachine, newConcept.getFullPath(), newConcept.getOwnerProjectName());
								updateStateTransitionRulesSymbols(stateMachine, oldConceptPath, newConcept.getFullPath(), newConcept.getOwnerProjectName());
							}catch(Exception ex){
								ex.printStackTrace();
							}
							if(oldConceptPath != null && oldConceptPath.intern() != newConcept.getFullPath().intern() /*&& 
								!StudioUIUtils.isValidOwnerConcept((StateMachine)tseGraph.getUserObject())*/){
								//							ownerConceptText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
								editor.setOwnerConceptDefined(false);
							}
							//}
						}
					}
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		isMainLabel = getWidgetFactory().createLabel(composite, Messages.getString("isMain"),  SWT.NONE);

		isMainButton = new Button(composite, SWT.CHECK);
		isMainButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					StateMachine stateMachine = (StateMachine)eObject;
					if(stateMachine.isMain() != isMainButton.getSelection()){
						Command isMainCommand=new SetCommand(editor.getEditingDomain(),
								stateMachine,StatesPackage.eINSTANCE.getStateMachine_Main(),isMainButton.getSelection()) ;			
						EditorUtils.executeCommand(editor, 
								isMainCommand);
						editor.mainChanged(true);
					}
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
				
			}});

		List<String> list= new ArrayList<String>();
		//Modified by Anand - 01/17/2011 to fix BE-10395		
		for(TIMEOUT_UNITS timeout_units:TimeOutUnitsUtils.getValidTimeOutUnits()){
			list.add(timeout_units.getName());
		}
		String[] timout_unitArray = new String[list.size()];
		list.toArray(timout_unitArray);

		unitsLabel = getWidgetFactory().createLabel(composite, Messages.getString("Units"), SWT.NONE);
		unitsCombo = getWidgetFactory().createCCombo(composite, SWT.READ_ONLY);
          
		unitsCombo.setItems(timout_unitArray);

		gd = new GridData();
		gd.widthHint = 200;
		unitsCombo.setLayoutData(gd);
		unitsCombo.addModifyListener(new ModifyListener(){
			/* (non-Javadoc)
			 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
			 */
			@Override
			public void modifyText(ModifyEvent e) {
				State state = (State)eObject;
				if (state == null) {
					return;
				}
				if(state.getTimeoutUnits()!=null && state.getTimeoutUnits().compareTo(TIMEOUT_UNITS.getByName(unitsCombo.getText()))!=0){
					EditingDomain editingDomain = editor.getEditingDomain();
					Command command=new SetCommand(editingDomain,eObject,StatesPackage.eINSTANCE.getStateEntity_TimeoutUnits(),TIMEOUT_UNITS.getByName(unitsCombo.getText())) ;			
					EditorUtils.executeCommand(editor, 
							command);
				}
			}});
	}

	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		
		nameText.setEditable(true);
		isMainLabel.setVisible(false);
		isMainButton.setVisible(false);
		ownerConceptLabel.setVisible(false);
		ownerConceptText.setVisible(false);
		browseButton.setVisible(false);
		unitsCombo.setVisible(false);
		unitsLabel.setVisible(false);
		composite.redraw();
		
		if(tseNode !=null && tseNode.getUserObject()!=null ){
			nameText.setText(((State)tseNode.getUserObject()).getName());
			if(((State)tseNode.getUserObject()).getDescription()!=null){
				descriptionText.setText(((State)tseNode.getUserObject()).getDescription());
			}else{
				descriptionText.setText("");
			}
		}
		if(tsedge !=null && tsedge.getUserObject()!=null){
			nameText.setText(((StateTransition)tsedge.getUserObject()).getName());
			if(((StateTransition)tsedge.getUserObject()).getDescription()!=null)
				descriptionText.setText(((StateTransition)tsedge.getUserObject()).getDescription());
			else{
				descriptionText.setText("");
			}
		}
		if(tseGraph !=null && tseGraph.getUserObject()!=null){
			if(tseGraph.getUserObject() instanceof StateMachine){
				isMainLabel.setVisible(true);
				isMainButton.setVisible(true);
				ownerConceptLabel.setVisible(true);
				ownerConceptText.setVisible(true);
				browseButton.setVisible(true);
				unitsLabel.setVisible(true);
				unitsCombo.setVisible(true);
				ownerConceptText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
				if(!StudioUIUtils.isValidOwnerConcept((StateMachine)tseGraph.getUserObject())){
					ownerConceptText.setForeground(Display.getDefault().getSystemColor(SWT.COLOR_RED));
					editor.setOwnerConceptDefined(false);
				}
				String conceptPath = ((StateMachine)tseGraph.getUserObject()).getOwnerConceptPath();
				if(conceptPath != null){
					ownerConceptText.setText(conceptPath);
				}
				editor.setOwnerConceptTextField(ownerConceptText);
				isMainButton.setSelection(((StateMachine)tseGraph.getUserObject()).isMain());
				nameText.setText(((StateMachine)tseGraph.getUserObject()).getName());
				nameText.setEditable(false);
				unitsCombo.setText(((StateMachine)tseGraph.getUserObject()).getTimeoutUnits().getName()!=null?(
						(StateMachine)tseGraph.getUserObject()).getTimeoutUnits().getName():TIMEOUT_UNITS.SECONDS.getName());
				if(((StateMachine)tseGraph.getUserObject()).getDescription()!=null){
					descriptionText.setText(((StateMachine)tseGraph.getUserObject()).getDescription());
				}else{
					descriptionText.setText("");
				}
			}
			if(tseGraph.getUserObject() instanceof StateComposite){
				nameText.setText(((StateComposite)tseGraph.getUserObject()).getName());
				if(((StateComposite)tseGraph.getUserObject()).getDescription()!=null){
					descriptionText.setText(((StateComposite)tseGraph.getUserObject()).getDescription());
				}else{
					descriptionText.setText("");
				}
			}
		}
		//Making readonly widgets
		if(!editor.isEnabled()){
			readOnlyWidgets();
		}
	}
	
	private void readOnlyWidgets(){
		nameText.setEditable(false);
		descriptionText.setEditable(false);
		if(tseGraph !=null && tseGraph.getUserObject()!=null){
			if(tseGraph.getUserObject() instanceof StateMachine){
				ownerConceptText.setEditable(false);
				isMainButton.setEnabled(false);
				browseButton.setEnabled(false);
				unitsCombo.setEnabled(false);
			}
		}
	}
	
	private FocusListener getFocusListener() {
		if (fFocusListener == null) {
			fFocusListener = new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent arg0) {
					editor.enableEdit(true);
				}
				
				@Override
				public void focusGained(FocusEvent arg0) {
					editor.enableEdit(false);
				}
			};
		}
		return fFocusListener;
	}
	
	@Override
	public void dispose() {
		FocusListener focusListener = getFocusListener();
		if (focusListener != null && nameText != null && !nameText.isDisposed()) {
			nameText.removeFocusListener(focusListener);
		}
		if (focusListener != null && descriptionText != null && !descriptionText.isDisposed()) {
			descriptionText.removeFocusListener(focusListener);
		}
		super.dispose();
	}
}