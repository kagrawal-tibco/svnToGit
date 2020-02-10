package com.tibco.cep.studio.tester.ui.editor.data;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 * @author sasahoo
 *
 */
public class TestResultsContentProvider implements ITreeContentProvider {

	public class ParentNode {
		
		Object[] children;
		String label;
		boolean startState = false;
		
		public ParentNode(String label, Object[] children, boolean startState) {
			this.children = children;
			this.label = label;
			this.startState = startState;
		}
		
		public String getLabel() {
			return label;
		}
		
		public Object[] getChildren() {
			return children;
		}
		
		public boolean isStartState() {
			return startState;
		}
		
	}
	protected static Object[] EMPTY_ARRAY = new Object[0];
	
	@SuppressWarnings("unused")
	private TreeViewer treeViewer;
	
	/**
	 * @param treeViewer
	 */
	public TestResultsContentProvider(TreeViewer treeViewer) {
		this.treeViewer = treeViewer;
	}
	
	@Override
	public Object[] getChildren(Object parentElement) {
		try {
			if (parentElement instanceof TestResultRoot) {
				TestResultRoot root = (TestResultRoot)parentElement;
				return root.getReteObjectElements().toArray();
			}
			if (parentElement instanceof ReteObjectElement) {
				ReteObjectElement reteObject = (ReteObjectElement)parentElement;
				if (reteObject.getInvocationElement() != null) {
					return new Object[] {reteObject.getInvocationElement()};
				}
			}
			if (parentElement instanceof InvocationObjectElement) {
				InvocationObjectElement invokObject = (InvocationObjectElement)parentElement;
				Object[] causalObjs = invokObject.getCausalObjects().toArray();
				Object[] causalObjsEndState = invokObject.getCausalObjectsEndState().toArray();
				return new Object[] { new ParentNode("Start State of Input Objects", causalObjs, true), new ParentNode("End State of Input Objects", causalObjsEndState, false) };
			}
			if (parentElement instanceof ParentNode) {
				return ((ParentNode) parentElement).getChildren();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return EMPTY_ARRAY;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return true;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return getChildren(inputElement);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
}