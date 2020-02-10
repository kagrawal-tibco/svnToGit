package com.tibco.cep.studio.tester.ui.editor.data;

import org.eclipse.jface.viewers.TreeViewer;

/**
 * 
 * @author sasahoo
 *
 */
public class WMResultContentProvider extends TestResultsContentProvider {

	/**
	 * @param treeViewer
	 */
	public WMResultContentProvider(TreeViewer treeViewer) {
		super(treeViewer);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.tester.ui.data.TestResultsContentProvider#getChildren(java.lang.Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		try {
			if (parentElement instanceof TestResultRoot) {
				TestResultRoot root = (TestResultRoot)parentElement;
				return root.getReteObjectElements().toArray();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return EMPTY_ARRAY;
	}

}
