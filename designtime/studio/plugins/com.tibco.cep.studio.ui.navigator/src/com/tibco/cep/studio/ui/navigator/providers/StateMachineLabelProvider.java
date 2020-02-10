package com.tibco.cep.studio.ui.navigator.providers;

import org.eclipse.swt.graphics.Image;

import com.tibco.cep.designtime.core.model.states.State;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateEnd;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateSimple;
import com.tibco.cep.designtime.core.model.states.StateStart;
import com.tibco.cep.designtime.core.model.states.StateSubmachine;
import com.tibco.cep.designtime.core.model.states.StateTransition;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.navigator.model.StateEntityNode;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class StateMachineLabelProvider extends EntityLabelProvider {

	public Image getImage(Object element) {
		
		if (element instanceof StateEntityNode) {
			element = ((StateEntityNode)element).getEntity();
		}
		if (element instanceof StateSimple) {
			return StudioUIPlugin.getDefault().getImage("icons/SimpleState.png");
		}
		if (element instanceof StateComposite) {
			StateComposite stateComposite = (StateComposite)element;
			if(stateComposite.isConcurrentState()){
				return StudioUIPlugin.getDefault().getImage("icons/concurrent.png");
			}
			if(stateComposite.isRegion()){
				return StudioUIPlugin.getDefault().getImage("icons/region.png");
			}
			if(stateComposite instanceof StateSubmachine){
				return StudioUIPlugin.getDefault().getImage("icons/sub_machinestate.png");
			}
			return StudioUIPlugin.getDefault().getImage("icons/composite.png");
		}
		if (element instanceof StateStart) {
			return StudioUIPlugin.getDefault().getImage("icons/StartState.png");
		}
		if (element instanceof StateEnd) {
			return StudioUIPlugin.getDefault().getImage("icons/EndState.png");
		}
		if (element instanceof State) {
			return StudioUIPlugin.getDefault().getImage("icons/SimpleState.png");
		}
		if (element instanceof StateTransition) {
			return StudioUIPlugin.getDefault().getImage("icons/transition.png");
		}
		return super.getImage(element);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.navigator.providers.EntityLabelProvider#getDescription(java.lang.Object)
	 */
	@Override
	public String getDescription(Object anElement) {
		if (anElement instanceof StateEntityNode) {
			StateEntity stateEntity = (StateEntity)((StateEntityNode)anElement).getEntity();
			return StudioUIUtils.getStateGraphPath(stateEntity);
		}
		return null;
	}

}
