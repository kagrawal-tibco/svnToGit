package com.tibco.cep.studio.ui.actions;

import static com.tibco.cep.studio.ui.util.StudioUIUtils.openElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.StudioElementCollector;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.ui.dialog.OpenStudioElementDialog;

public class OpenStudioElementAction implements
		IWorkbenchWindowActionDelegate {

	public void dispose() {
	}

	public void init(IWorkbenchWindow window) {
	}

	public void run(IAction action) {
		Collection<DesignerProject> allIndexes = StudioCorePlugin.getDesignerModelManager().getAllIndexes();
		List<EObject> allElements = new ArrayList<EObject>();
		StudioElementCollector collector = new StudioElementCollector(allElements, false);
		for (DesignerProject designerProject : allIndexes) {
			designerProject.accept(collector);
		}
		List<EObject> elements = collector.getElements();
        OpenStudioElementDialog selectionDialog = new OpenStudioElementDialog(Display.getDefault().getActiveShell(), false);
        selectionDialog.setElements(elements.toArray());
        selectionDialog.setMultipleSelection(false);
        selectionDialog.setBlockOnOpen(true);

        selectionDialog.open();
        Object[] result = selectionDialog.getResult();
        if (result != null && result.length > 0) {
        	openElement(result[0]);
        }
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

}
