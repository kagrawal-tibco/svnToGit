package com.tibco.cep.bpmn.ui;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.bpmn.core.index.IBpmnIndexUpdate;

/**
 * 
 * @author majha
 *
 */
public class BpmnIndexUpdateHandler implements IBpmnIndexUpdate {

	private static Set<IBpmnUiIndexUpdateHandler> handlers = new HashSet<IBpmnUiIndexUpdateHandler>();

	public BpmnIndexUpdateHandler() {

	}

	@Override
	public void onIndexUpdate( IProject project, EObject index)
			throws Exception {
		synchronized (handlers) {
			for (IBpmnUiIndexUpdateHandler handle : handlers) {
				handle.onIndexUpdate(project, index);
			}
		}
	}

	public static void registerHandlers(IBpmnUiIndexUpdateHandler handler) {
		synchronized (handlers) {
			handlers.add(handler);
		}
	}

}
