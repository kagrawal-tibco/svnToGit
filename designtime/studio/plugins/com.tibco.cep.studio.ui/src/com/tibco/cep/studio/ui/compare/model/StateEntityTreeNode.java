package com.tibco.cep.studio.ui.compare.model;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.studio.ui.util.StudioUIUtils;

public class StateEntityTreeNode extends AbstractTreeNode {

	public StateEntityTreeNode(EObject input, int featureId) {
		super(input, featureId);
	}

	@Override
	public boolean isEqualTo(Object obj) {
//		Object in = ((AbstractTreeNode)obj).getInput();
		if (input instanceof StateEntity && ((AbstractTreeNode)obj).getInput() instanceof StateEntity) {
			StateEntity s1 = (StateEntity) input;
			StateEntity s2 = (StateEntity) ((AbstractTreeNode)obj).getInput();
			
			if ( s1.getName().equals(s2.getName())) {
			   	
				String path1 = StudioUIUtils.getStateGraphRelativePath(s1);
				String path2 = StudioUIUtils.getStateGraphRelativePath(s2);
				
				if (path1.equals(path2)) {
					return true;
				}	
			}
		}
		return false;
	}

	@Override
	public void handleReplace(AbstractTreeNode dest, AbstractTreeNode src, EObject newObject) {
		super.handleReplace(dest, src, newObject);
	}

	@Override
	public boolean isUnsettable() {
		return true;
	}

}
