package com.tibco.cep.studio.ui.editors.rules;

import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import com.tibco.cep.studio.core.rules.ast.RulesASTNode;
import com.tibco.cep.studio.ui.editors.rules.text.IReconcilingListener;

public class RulesEditorOutlinePage extends ContentOutlinePage implements IReconcilingListener {

	private Object fInput;
	private RulesEditor fEditor;

	public RulesEditorOutlinePage(RulesEditor editor) {
		super();
		this.fEditor = editor;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
	    
		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new RulesContentProvider());
		viewer.setLabelProvider(new RulesLabelProvider());
		if (fInput != null) {
			viewer.setInput(fInput);
		}
		viewer.addDoubleClickListener(new IDoubleClickListener() {
		
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof StructuredSelection) {
					StructuredSelection ss = (StructuredSelection) selection;
					RulesASTNode node = (RulesASTNode) ss.getFirstElement();
					fEditor.selectAndReveal(node.getOffset(), node.getLength());
				}
			}
		});
		
	}

	public void setInput(Object input) {
		fInput = input;
		TreeViewer viewer = getTreeViewer();
		if (viewer != null 
				&& viewer.getControl() != null 
				&& !viewer.getControl().isDisposed()) {
			viewer.setInput(input);
		}
	}

	public void reconciled(Object result) {
		setInput(result);
	}
}
