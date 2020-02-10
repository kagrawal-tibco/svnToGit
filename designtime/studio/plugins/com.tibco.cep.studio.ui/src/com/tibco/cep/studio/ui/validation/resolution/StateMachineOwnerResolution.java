package com.tibco.cep.studio.ui.validation.resolution;

import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.states.StateEntity;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.util.Messages;

public class StateMachineOwnerResolution extends
		EntityMarkerNamespaceResolution {

	@Override
	public String getLabel() {
		return Messages.getString("Entity.validate.statemachineowner.quickfix.label");
	}

	@Override
	protected void processEntity(Entity entity, IResource entityFile) {
		if (!(entity instanceof StateMachine)) {
			return;
		}
		boolean changed = false;
		StateMachine mach = (StateMachine) entity;
		String newFullPath = mach.getFullPath();
		if (entity instanceof StateEntity) {
			if (((StateEntity) entity).getOwnerStateMachine() == null && ((StateEntity) entity).getOwnerStateMachinePath() != null) {
				((StateEntity) entity).setOwnerStateMachinePath(newFullPath);
				changed = true;
			}
		}
		TreeIterator<EObject> allContents = entity.eAllContents();
		while (allContents.hasNext()) {
			EObject object = (EObject) allContents.next();
			if (object instanceof StateEntity) {
				if (((StateEntity) object).getOwnerStateMachine() == null && ((StateEntity) object).getOwnerStateMachinePath() != null) {
					((StateEntity) object).setOwnerStateMachinePath(newFullPath);
					changed = true;
				}
			}
		}
		
		if (changed) {
			try {
				ModelUtils.saveEObject(entity);
			} catch (IOException e) {
				e.printStackTrace();
			}
			CommonUtil.refresh(entityFile, 1, false);
		}

		
	}

}
