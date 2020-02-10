package com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.addSymbol;

import org.eclipse.swt.custom.SashForm;

import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;

/**
 * 
 * @author sasahoo
 *
 */
public class TimeoutStatePropertySection extends AbstractStateMachineRulePropertySection {
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections.AbstractStateMachineRulePropertySection#createSections(org.eclipse.swt.custom.SashForm)
	 */
	@Override
	protected void createSections(SashForm sashForm) {
//		createDeclarationsPart(sashForm);
		createActionsPart(sashForm);
		sashForm.setWeights(new int[] { /*30, */70 } );
	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if(eObject instanceof State){
			try{
				State state = (State)eObject;
				StateMachine stateMachine = editor.getStateMachine();
				rule = state.getTimeoutAction();
				if(rule == null){
					rule = RuleFactory.eINSTANCE.createRule();
					addSymbol(stateMachine.getOwnerConceptPath(), stateMachine.getOwnerProjectName(), rule.getSymbols().getSymbolMap());
					state.setTimeoutAction((Rule)rule);
				}
				rule.setOwnerProjectName(stateMachine.getOwnerProjectName());
				String feature = StatesPackage.eINSTANCE.getState_TimeoutAction().getName();
				if(state instanceof StateMachine){
					rule.setName(stateMachine.getName() +"_"+ feature);
				}else{
					rule.setName(stateMachine.getName()+"_" + state.getName() + "_"+ feature);
				}
				init(false);
				
				//Making readonly widgets
				if(!editor.isEnabled()){
					readOnlyWidgets();
				}	
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void readOnlyWidgets(){
		actionsSourceViewer.setEditable(false);
	}
}