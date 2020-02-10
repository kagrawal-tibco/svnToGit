package com.tibco.cep.studio.ui.compare.util;


import org.eclipse.compare.CompareUI;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.IDiffContainer;
import org.eclipse.compare.structuremergeviewer.IDiffElement;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.states.StateComposite;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.studio.core.compare.util.CoreCompareUtils;
import com.tibco.cep.studio.ui.compare.model.AbstractTreeNode;
import com.tibco.cep.studio.ui.compare.model.ChannelTreeNode;
import com.tibco.cep.studio.ui.compare.model.ConceptTreeNode;
import com.tibco.cep.studio.ui.compare.model.EMFResourceNode;
import com.tibco.cep.studio.ui.compare.model.EventTreeNode;
import com.tibco.cep.studio.ui.compare.model.StateEntityTreeNode;
import com.tibco.cep.studio.ui.compare.model.StateMachineTreeNode;
import com.tibco.cep.studio.ui.compare.model.TableTreeNode;

public class CompareUtils extends CoreCompareUtils{

	
	public static AbstractTreeNode getTreeNode(EObject input, int featureId) {
		if (input instanceof Concept) {
			return new ConceptTreeNode(input, featureId);
		}
		if (input instanceof Event) {
			return new EventTreeNode(input, featureId);
		}
		if (input instanceof Channel) {
			return new ChannelTreeNode(input, featureId);
		}
		if (input instanceof Table) {
			return new TableTreeNode(input, featureId);
		}
		if (input instanceof StateMachine) {
			return new StateMachineTreeNode(input, featureId);
		}
		if (input instanceof StateComposite) {
			return new StateEntityTreeNode(input, featureId);
		}
		return null;
	}
	
	/**
	 * @param table
	 * @param ontology
	 * @return
	 */
//	private static AbstractResource getTemplate(Table table, Object ontology) {
//		String implementsValue = table.getImplements();
//		if (implementsValue != null) {
//			if (ontology instanceof Ontology) {
//				AbstractResource abs = getTemplate(
//						(Ontology) ontology, implementsValue);
//				return abs;
//			}
//		}
//		return null;
//	}
	
	/**
	 * Determines whether there are structural
	 * differences between two Table objects
	 * @param table1
	 * @param table2
	 * @return true if there are structural differences,
	 * false otherwise
	 */
	public static boolean hasDifferences(Table table1, Table table2) {
		DiffNode differences = findDifferences(table1, table2);
		if (differences == null) {
			return false;
		}
		if (!differences.hasChildren()) {
			return false;
		}
		// the root DiffNode always contains one child.
		// If that child has children, then there are
		// differences between the inputs
		IDiffElement[] children = differences.getChildren();
		if (children.length > 0) {
			DiffNode child = (DiffNode) children[0];
			return child.hasChildren();
		}
		return false;
	}

	/**
	 * Calculates the structural
	 * differences between two Table objects
	 * @param table1
	 * @param table2
	 * @return a {@link DiffNode} object containing
	 * the differences between the Table objects
	 */
	public static DiffNode findDifferences(Table table1, Table table2) {
		return findDifferences(table1, table2, false);
	}

	/**
	 * @param table1
	 * @param table2
	 * @param autoMerge
	 * @return
	 */
	public static DiffNode findDifferences(Table table1, Table table2, boolean autoMerge) {
		EMFResourceNode	left = new EMFResourceNode(table1, "0", true, true);	
		EMFResourceNode	right = new EMFResourceNode(table2, "0", true, true);	
		Differencer d= new Differencer() {
			protected Object visit(Object parent, int description, Object ancestor, Object left, Object right) {
				return new DiffNode((IDiffContainer) parent, description, (ITypedElement)ancestor, (ITypedElement)left, (ITypedElement)right);
			}
	
			@Override
			protected void updateProgress(IProgressMonitor progressMonitor,
					Object node) {
				if (CompareUI.getPlugin() == null) {
					// we're running from the command line and cannot
					// update progress, just return
					return;
				} else {
					super.updateProgress(progressMonitor, node);
				}
			}
			
		};
	
		DiffNode root= (DiffNode) d.findDifferences(false, new NullProgressMonitor(), null, null, left, right);
		
		return root;
	}
	
	/**
	 * @param file
	 */
	public static void saveDirtyEditor(final IFile file) {
		Display.getDefault().asyncExec(new Runnable(){

			@Override
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				IEditorPart[] references = page.getDirtyEditors();
				for (IEditorPart editor : references) {
					IEditorInput input = editor.getEditorInput();
					if (input instanceof FileEditorInput) {
						IFile editorFile = ((FileEditorInput) input).getFile();
						if (file.equals(editorFile)) {
							page.saveEditor(editor, false);
							break;
						}
					}
				}
			}}); 
	}
}