package com.tibco.cep.decision.table.handler;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.decision.table.editors.DecisionTableEditor;
import com.tibco.cep.decision.table.utils.DecisionTableUtil;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.decisionproject.ontology.RuleFunction;

/**
 * 
 * @author sasahoo
 * 
 */
public class NavigationDragListener extends DragSourceAdapter {
	private StructuredViewer viewer;

	public NavigationDragListener(StructuredViewer viewer) {
		this.viewer = viewer;
	}

	public void dragStart(DragSourceEvent event) {
		event.doit = !viewer.getSelection().isEmpty();
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		if (selection.getFirstElement() instanceof Event) {
			event.doit = true;
		} else if (selection.getFirstElement() instanceof Concept) {
			event.doit = true;
		} else if (selection.getFirstElement() instanceof RuleFunction) {
			RuleFunction rf = (RuleFunction) selection.getFirstElement();
			if (DecisionTableUtil.isVirtual(rf)) {
				event.doit = true;
			} else
				event.doit = false;
		} else if (selection.getFirstElement() instanceof Property) {
			Property property = (Property) selection.getFirstElement();
			if (property.eContainer() instanceof Concept) {
				if (((Concept) property.eContainer()).isScoreCard())
					event.doit = true;
				else
					event.doit = false;
			} else
				event.doit = false;
		} else
			event.doit = false;
		IEditorPart editor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if (!(editor instanceof DecisionTableEditor)) {
			event.doit = false;
		}
	}

	public void dragFinished(DragSourceEvent event) {
		if (!event.doit)
			return;
	}

	public void dragSetData(DragSourceEvent event) {
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		event.data = getTransferable(selection);
	}

	private String getTransferable(IStructuredSelection selection) {
		String parent = "";
		if (selection != null) {
			if (selection.getFirstElement() instanceof Property) {
				Property prop = (Property) selection.getFirstElement();
				AbstractResource resource = (AbstractResource) prop
						.eContainer();
				parent = resource.getName() + "." + prop.getName();
			}
			if (selection.getFirstElement() instanceof Concept) {
				Concept concept = (Concept) selection.getFirstElement();
				String resource = concept.getFolder();
				String[] tokens = resource.split("/", -1);
				for (int i = 0; i < tokens.length; ++i) {
					parent = parent + tokens[i].trim();
				}
				parent = parent + "." + concept.getName();
			}
			if (selection.getFirstElement() instanceof Event) {
				Event event = (Event) selection.getFirstElement();
				String resource = event.getFolder();
				String[] tokens = resource.split("/", -1);
				for (int i = 0; i < tokens.length; ++i) {
					parent = parent + tokens[i].trim();
				}
				parent = parent + "." + event.getName();
			}
			if (selection.getFirstElement() instanceof RuleFunction) {
				RuleFunction rf = (RuleFunction) selection.getFirstElement();
				if (DecisionTableUtil.isVirtual(rf)) {
					String resource = rf.getFolder();
					String[] tokens = resource.split("/", -1);
					for (int i = 0; i < tokens.length; ++i) {
						parent = parent + tokens[i].trim();
					}
					parent = parent + "." + rf.getName();
				}
			}
		}
		return parent;
	}
}