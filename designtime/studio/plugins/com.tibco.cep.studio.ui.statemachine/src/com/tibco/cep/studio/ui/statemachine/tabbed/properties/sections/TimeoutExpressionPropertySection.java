package com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections;

import org.eclipse.swt.custom.SashForm;

import com.tibco.be.parser.tree.NodeType;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.studio.core.rules.IResolutionContextProviderExtension;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * 
 * @author sasahoo
 *
 */
public class TimeoutExpressionPropertySection extends AbstractStateMachineRulePropertySection implements IResolutionContextProviderExtension {
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.statemachine.tabbed.properties.sections.AbstractStateMachineRulePropertySection#createSections(org.eclipse.swt.custom.SashForm)
	 */
	@Override
	protected void createSections(SashForm sashForm) {
//		createDeclarationsPart(sashForm);
		createActionsPart(sashForm);
		sashForm.setWeights(new int[] { /*30, */70 } );	}
	
	/*
	 * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
	 */
	public void refresh() {
		if(eObject instanceof State){
			try{
				State state = (State)eObject;
				StateMachine stateMachine = editor.getStateMachine();
				rule = state.getTimeoutExpression();
				if(rule == null){
					rule = ModelUtils.generateDefaultTimeoutExpression(state);
//					rule = RuleFactory.eINSTANCE.createRuleFunction();
//					addSymbol(stateMachine.getOwnerConceptPath(), stateMachine.getOwnerProjectName(), rule.getSymbols().getSymbolMap());
//					rule.setActionText("return 0;");
					state.setTimeoutExpression((RuleFunction)rule);
				}
				rule.setOwnerProjectName(stateMachine.getOwnerProjectName());
//				if(rule.getActionText() == null || rule.getActionText().equals("")){
//					rule.setActionText("return 0;");
//				}
				String feature = StatesPackage.eINSTANCE.getState_TimeoutExpression().getName();
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

	@Override
	public NodeType getReturnType() {
		return new NodeType(NodeType.NodeTypeFlag.LONG_FLAG, NodeType.TypeContext.PRIMITIVE_CONTEXT, false);
	}
	
	private void readOnlyWidgets(){
		actionsSourceViewer.setEditable(false);
	}
}