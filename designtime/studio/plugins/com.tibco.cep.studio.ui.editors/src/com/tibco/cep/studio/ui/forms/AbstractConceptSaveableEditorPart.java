package com.tibco.cep.studio.ui.forms;

import java.util.HashSet;
import java.util.Set;

import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractConceptSaveableEditorPart extends AbstractSaveableEntityEditorPart{

	protected Concept concept;
    protected boolean smAssociationFlag = false;
    protected Set<String> stateMachinePathSet = new HashSet<String>();
    protected Set<String> statemachinePathSetFromConceptEditor = new HashSet<String>();
	
    public Concept getConcept() {
		return (Concept)getEntity();
	}
    
    public boolean isSmAssociationFlag() {
		return smAssociationFlag;
	}

	public void setSmAssociationFlag(boolean smAssociationFlag) {
		this.smAssociationFlag = smAssociationFlag;
	}
	
	public Set<String> getStateMachinePathSet() {
		return stateMachinePathSet;
	}
	
	/**
	 * If editor closes when there are removal of State Machine Association externally
	 * @param concept
	 */
	protected void saveStateMachineAssociation(Concept concept){
		//Handling StateMachine Associations
		if(isSmAssociationFlag() && stateMachinePathSet.size()>0){
			for(String s:stateMachinePathSet){
				if(!concept.getStateMachinePaths().contains(s)){
					concept.getStateMachinePaths().add(s);
				}
			}
		}
	}
	
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
		return statemachinePathSetFromConceptEditor;
	}
}