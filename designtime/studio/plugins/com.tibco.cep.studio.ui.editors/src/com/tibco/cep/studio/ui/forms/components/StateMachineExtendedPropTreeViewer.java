package com.tibco.cep.studio.ui.forms.components;

import java.util.List;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
import com.tibco.cep.studio.ui.editors.utils.EditorUtils;
import com.tibco.cep.studio.ui.forms.extendedPropTreeViewer.ExtendedPropTreeViewer;

public class StateMachineExtendedPropTreeViewer extends ExtendedPropTreeViewer {

//	public StateMachineExtendedPropTreeViewer(
//			AbstractSaveableEntityEditorPart editor) {
//		super(editor);
//	}

	public StateMachineExtendedPropTreeViewer(AbstractSaveableEntityEditorPart editor) {
		super(editor);
		this.setInvokingObj(this);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getInputFromModel(){
		
		prList.clear();
		getMap().clear();
		if(editor!=null && entity!=null && entity.getExtendedProperties()!=null){
				if(entity.getExtendedProperties()!=null &&! (entity.getExtendedProperties().getProperties().isEmpty())){
					EditorUtils.getExtendedProperties(entity.getExtendedProperties(),getMap());
				}
				else{
					if(entity instanceof StateMachine){
						entity.setExtendedProperties(EditorUtils.getDefaultEntityPropertiesMap());
						EditorUtils.getExtendedProperties(entity.getExtendedProperties(),getMap());
					}
					else{
						entity.setExtendedProperties(EditorUtils.getDefaultStateMachinePropertiesMap());
						EditorUtils.getExtendedProperties(entity.getExtendedProperties(),getMap());
					}
					setDirty(true);
				}
				
		
			prList=getChldElementList(getMap(),null);
			return prList;
		}
		else
			return null;
		
	}
	
	
	public void changeEntity(Entity ent){
		this.entity=ent;
		m_treeViewer.setInput(getInputFromModel());
		m_treeViewer.refresh();
	}
}
