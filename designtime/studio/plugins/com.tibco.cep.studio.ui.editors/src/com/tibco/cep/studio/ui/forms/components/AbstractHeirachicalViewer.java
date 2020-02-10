package com.tibco.cep.studio.ui.forms.components;

import java.util.List;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableFontProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.ui.AbstractSaveableEntityEditorPart;
/*
 * @author sshekhar
 */
public abstract class AbstractHeirachicalViewer implements ITreeContentProvider,ITableLabelProvider,ITableColorProvider ,ITableFontProvider,ICellModifier{
	
	protected TreeViewer m_treeViewer;
	protected Composite comp;

	protected boolean isDirty = false;

	protected AbstractSaveableEntityEditorPart editor;
	protected Entity entity;

	public AbstractHeirachicalViewer(AbstractSaveableEntityEditorPart editor) {
		this.editor=editor;
		entity=editor.getEntity();
	}

	public void createHierarchicalTree(Composite parent) {
		comp = parent;
		GridData gdtableComp = new GridData(GridData.BEGINNING);
		gdtableComp.horizontalSpan = SWT.FILL;
		gdtableComp.grabExcessHorizontalSpace = true;
		gdtableComp.grabExcessVerticalSpace = true;
		comp.setLayoutData(gdtableComp);
		GridLayout tablelayout = new GridLayout(1, false);
		tablelayout.numColumns = 1;
		comp.setLayout(tablelayout);

		createToolBar();
		// Create Tree
		Composite treeComp = new Composite(parent, GridData.BEGINNING);
		GridData gdtable = new GridData(GridData.FILL_BOTH);
		gdtable.widthHint = 500;
		treeComp.setLayoutData(gdtable);
		GridLayout gridtablelayout = new GridLayout(1, false);
		treeComp.setLayout(gridtablelayout);

		final Tree propTree = new Tree(treeComp, SWT.BORDER
				| SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL);

		m_treeViewer = new TreeViewer(propTree);

		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.grabExcessHorizontalSpace = false;
		gd.grabExcessVerticalSpace = false;
		m_treeViewer.getTree().setLayoutData(gd);
		// create tree columns
		createTreeColumns(propTree, treeComp);

		setContentandLabelProvider();
		m_treeViewer.setInput(getInputFromModel());

	}
	
	
	protected abstract void createToolBar();
	
	protected abstract void setContentandLabelProvider();
	
	protected abstract void createTreeColumns(Tree tree ,Composite treeComp);
	
	 /**
     * Is this field dirty?
     */
    protected boolean isDirty() {
        // return the current dirty state
        return this.isDirty;
    }


    protected abstract void setDirty(boolean tf);

    

	public abstract List<Object> getInputFromModel();
	
	
	
	protected abstract int getDepth(Object Parentobj);

	public TreeViewer getM_treeViewer() {
		return m_treeViewer;
	}

	public void setM_treeViewer(TreeViewer m_treeViewer) {
		this.m_treeViewer = m_treeViewer;
	}
	
	abstract public void dispose();
	
}
