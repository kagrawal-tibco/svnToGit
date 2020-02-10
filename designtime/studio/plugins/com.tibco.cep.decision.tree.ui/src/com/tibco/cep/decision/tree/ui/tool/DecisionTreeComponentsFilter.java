package com.tibco.cep.decision.tree.ui.tool;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.tibco.cep.decision.tree.common.model.DecisionTree;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.studio.ui.navigator.model.StateEntityNode;

public class DecisionTreeComponentsFilter extends ViewerFilter{
	
	@SuppressWarnings("unused")
	private StateEntity currentState;
	private StateEntity parentState;
	private List<StateEntity> stateGraphList ;
	
	/**
	 * @param currentState
	 */
	public DecisionTreeComponentsFilter(StateEntity currentState){
		this.currentState =  currentState;
		parentState  = currentState.getParent();
		stateGraphList =  getStateGraphPath(currentState);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if(element instanceof StateEntityNode){
			StateEntityNode stateEntityNode = (StateEntityNode)element;
			StateEntity entity  = (StateEntity)stateEntityNode.getEntity();
//			if(entity ==  currentState){
//				return false;
//			}
			if(entity.getParent() == parentState){
				return true;
			}
			if(stateGraphList.contains(entity)){
				return true;
			}
			if(entity instanceof  StateEnd && 
					stateGraphList.contains(entity.getParent())){
				return true;
			}
		}
		return false;
	}

	
	/**
	 * @param entity
	 * @return
	 */
	public static List<StateEntity> getStateGraphPath(StateEntity entity){
		List<StateEntity> statesList = new ArrayList<StateEntity>();
		getStateEntityPath(entity,statesList);
		return statesList;
	}

	/**
	 * @param entity
	 * @param statesList
	 */
	public static void getStateEntityPath(StateEntity entity, List<StateEntity> statesList){
		if(entity instanceof DecisionTree){
			statesList.add( entity);
			return;
		}else{
			statesList.add( entity);
			if(entity.eContainer() !=null && entity.eContainer() instanceof StateEntity){
				getStateEntityPath((StateEntity)entity.eContainer(),statesList);
			}
		}
	}
}
