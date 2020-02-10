package com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections;

import static com.tibco.cep.studio.ui.editors.utils.EditorUtils.updateDeclarations;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.addSymbol;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.getUniqueTag;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.isSymbolPresent;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.ui.editors.utils.DeclarationSelector;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class RulePropertySection extends AbstractStateMachineRulePropertySection {
	
	private Text rankRuleFunctionText;
	private Button browseButton;
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections.AbstractStateMachinePropertySection#createControls(org.eclipse.swt.widgets.Composite, org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
	 */
	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		rankRuleFunctionText = new Text(parent,SWT.NONE);
		rankRuleFunctionText.setText("");
		browseButton = new Button(parent, SWT.PUSH);
	}
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections.AbstractStateMachineRulePropertySection#createSections(org.eclipse.swt.custom.SashForm)
	 */
	@Override
	protected void createSections(SashForm sashForm) {
//		createDeclarationsPart(sashForm);
		createConditionsPart(sashForm);
		createActionsPart(sashForm);
		sashForm.setWeights(new int[] { /*20,*/ 40, 40 } );
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if(eObject instanceof StateTransition){
			try{
				StateTransition transition = (StateTransition)eObject;
				StateMachine stateMachine = editor.getStateMachine();
				rule = transition.getGuardRule();
				if(rule == null){
					rule = RuleFactory.eINSTANCE.createRule();
					addSymbol(stateMachine.getOwnerConceptPath(), stateMachine.getOwnerProjectName(), rule.getSymbols().getSymbolMap());
					transition.setGuardRule((Rule)rule);
				}
				rule.setOwnerProjectName(stateMachine.getOwnerProjectName());
				String feature = StatesPackage.eINSTANCE.getStateTransition_GuardRule().getName();
				rule.setName(stateMachine.getName()+"_" + transition.getName() + "_"+ feature);
				init(true);	
				if(rule.getRank() != null){
					rankRuleFunctionText.setText(rule.getRank());
				}
				//Making readonly widgets
				if(declarationsTable.getItemCount() >1){
					removeDeclButton.setEnabled(true);
				} else {
					removeDeclButton.setEnabled(false);
				}
				if(!editor.isEnabled()){
					readOnlyWidgets();
				}	
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections.AbstractStateMachineRulePropertySection#editDeclarationTable()
	 */
	@Override
	protected void editDeclarationTable(){
		createToolbar(ruleDeclClient);
	}
	
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections.AbstractStateMachineRulePropertySection#getDeclarationTableHeight()
	 */
	@Override
	protected int getDeclarationTableHeight(){
		return 60;
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections.AbstractStateMachineRulePropertySection#addControlListener()
	 */
	protected void addControlListener(){
		if (editor != null) {
			StudioUIUtils.addRemoveControlListener(this, editor.isEnabled());
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections.AbstractStateMachineRulePropertySection#isDeclarationEditable()
	 */
	protected boolean isDeclarationEditable(){
		return true;
	}
	
	protected boolean isFirstDeclarationEditable(){
		return false;
	}
	
	/**
	 * @param type
	 * @return
	 */
	private boolean isOwnerConceptDeclarationsPresent(String type){
		int count = 0;
		for(Symbol symbol:rule.getSymbols().getSymbolList()){
			if(symbol.getType().equals(type)){
				count++;
			}
		}
		if(count > 1){
			return true;
		}
		return false;
	}
	
	private void readOnlyWidgets(){
		rankRuleFunctionText.setEditable(false);
		browseButton.setEnabled(false);
		condSourceViewer.setEditable(false);
		actionsSourceViewer.setEditable(false);
		
		addDeclButton.setEnabled(false);
		removeDeclButton.setEnabled(false);
	}

	@Override
	protected void add() {
		DeclarationSelector picker = new DeclarationSelector(Display.getDefault().getActiveShell(),
				com.tibco.cep.studio.ui.statemachine.diagram.utils.Messages.getString("statemachine_transition_rule_select_declration"),
				editor.getProject().getName(), ELEMENT_TYPES.RULE);
		if (picker.open() == Dialog.OK) {
			try{
				String idName = picker.getIdName();
				String type = picker.getType();
				
				if(isSymbolPresent(rule.getSymbols().getSymbolList(), idName.toLowerCase())){
					idName = getUniqueTag(rule.getSymbols().getSymbolList(), idName+"_");
				}
				
				Symbol symbol = RuleFactory.eINSTANCE.createSymbol();
				symbol.setType(type);
				symbol.setIdName(idName);
				rule.getSymbols().getSymbolMap().put(symbol.getIdName(),symbol);
				updateDeclarations(declarationsTable, rule, getProject().getName());
				editor.modified();
				removeDeclButton.setEnabled(true);
			}
			catch(Exception e ){
				e.printStackTrace();
			}
		}
		
	}

	@Override
	protected void remove() {
		try{
			int index = declarationsTable.getSelectionIndex();
			if(index !=-1){
				
				StateMachine stateMachine = editor.getStateMachine();
				
				if(rule.getSymbols().getSymbolList().get(index).getType().equalsIgnoreCase(stateMachine.getOwnerConceptPath())
						&& !isOwnerConceptDeclarationsPresent(stateMachine.getOwnerConceptPath())){
					MessageDialog.openError(getPart().getSite().getShell(), 
							com.tibco.cep.studio.ui.statemachine.diagram.utils.Messages.getString("Delete.Transition.Declaration.title"), 
							com.tibco.cep.studio.ui.statemachine.diagram.utils.Messages.getString("Delete.Transition.Declaration.message"));
					
					return;
				}
				rule.getSymbols().getSymbolList().remove(index);
				updateDeclarations(declarationsTable, rule, getProject().getName());
				editor.modified();
				if(declarationsTable.getItemCount() <=1){
					removeDeclButton.setEnabled(false);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}