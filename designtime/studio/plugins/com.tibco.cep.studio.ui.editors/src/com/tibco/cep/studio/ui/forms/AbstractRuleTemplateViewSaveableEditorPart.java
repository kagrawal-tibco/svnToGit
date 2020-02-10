package com.tibco.cep.studio.ui.forms;

import java.util.HashSet;
import java.util.Set;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.rule.RuleTemplateView;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractRuleTemplateViewSaveableEditorPart extends AbstractSaveableEntityEditorPart{

	protected RuleTemplateView ruleTemplateView;
    protected Set<String> ruleTemplatePathSet = new HashSet<String>();
    protected Set<String> ruleTemplatePathSetFromTemplateEditor = new HashSet<String>();
	
    public RuleTemplateView getRuleTemplateView() {
		return (RuleTemplateView)getEntity();
	}
    
	public Set<String> getStateMachinePathSet() {
		return ruleTemplatePathSet;
	}
	
	/**
	 * If editor closes when there are removal of State Machine Association externally
	 * @param concept
	 */
//	protected void saveStateMachineAssociation(RuleTemplateView ruleTemplateView){
//		//Handling StateMachine Associations
//		if(isSmAssociationFlag() && ruleTemplatePathSet.size()>0){
//			for(String s:ruleTemplatePathSet){
//				if(!ruleTemplateView.getStateMachinePaths().contains(s)){
//					ruleTemplateView.getStateMachinePaths().add(s);
//				}
//			}
//		}
//	}
	
    /**
	 * This is how the framework determines which interfaces we implement.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class key) {
		if (key.equals(Concept.class))
			//return getConcept();
			return getEntity();
		return super.getAdapter(key);
	}

	public Set<String> getStateMachinePathSetFromConceptEditor() {
		return ruleTemplatePathSetFromTemplateEditor;
	}
}