package com.tibco.cep.studio.application.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.studio.application.ICommandIds;

public class PreferenceAction extends Action {

	private final IWorkbenchWindow window;

	public PreferenceAction(String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		setId(ICommandIds.CMD_PREFERENCES);
		setActionDefinitionId(ICommandIds.CMD_PREFERENCES);
	}

	public void run() {
		PreferenceManager manager = window.getWorkbench().getPreferenceManager();
//		IPreferenceNode[] rootNodes = manager.getRootSubNodes();
//		IPreferenceNode smNode = null;
//		IPreferenceNode conceptNode = null;
//		for (int i = 0; i < rootNodes.length; ++i) {
//			IPreferenceNode preferenceNode = rootNodes[i];
//			if ((preferenceNode.getId().equalsIgnoreCase(PreferencePage.Id))) {
//				smNode = preferenceNode;
//			}
//			if ((preferenceNode.getId().equalsIgnoreCase(ConceptPreference.Id))) {
//				conceptNode = preferenceNode;
//			}
//		}
//		manager.removeAll();
//		manager.addToRoot(smNode);
//		manager.addToRoot(conceptNode);
		PreferenceDialog prefDialog = new PreferenceDialog(window.getShell(),
				manager);
		prefDialog.open();
	}

}
