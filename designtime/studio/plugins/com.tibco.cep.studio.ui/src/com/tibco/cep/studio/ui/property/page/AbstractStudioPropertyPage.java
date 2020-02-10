package com.tibco.cep.studio.ui.property.page;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.PropertyPage;

import com.tibco.cep.studio.core.util.CommonUtil;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractStudioPropertyPage extends PropertyPage{
	
	protected TabFolder mainFolder;
	boolean isDefaultApplyButton = false;
	
	public AbstractStudioPropertyPage(boolean isDefaultApplyButton) {
		super();
		this.isDefaultApplyButton = isDefaultApplyButton;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		if(!isDefaultApplyButton){
			noDefaultAndApplyButton();
		}
		GridLayout layout = new GridLayout(1,false);
		layout.marginLeft = 5;
		layout.marginRight = 5;
		layout.horizontalSpacing = 5;
		parent.setLayout(layout);
		IAdaptable element = getElement();
		if(element instanceof IProject) {
			IProject project = (IProject) element;
			if(CommonUtil.isStudioProject(project)){
				mainFolder = new TabFolder(parent, SWT.NONE);
				mainFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
				createTabbedPages(mainFolder);
				createXPathField(parent);
			} else {
				Composite c = new Composite(parent,SWT.NONE);
			}
		}
		return parent;
	}
	
	/**
	 * @param item
	 */
	protected void setDefaultSelection(TabItem item){
		mainFolder.setSelection(item);
	}
	
    /**
     * Create tab's tree viewer.
     * @param parent
     * @param treeContentProvider
     * @param treeLabelProvider
     * @return
     */
    protected TreeViewer createTreeViewer(Composite parent, ITreeContentProvider treeContentProvider, IBaseLabelProvider treeLabelProvider) {
        return createTreeViewer(parent, treeContentProvider, treeLabelProvider,SWT.BORDER);    	
    }
    
    /**
     * Create tab's tree viewer.
     * @param parent
     * @param treeContentProvider
     * @param treeLabelProvider
     * @return
     */
    protected TreeViewer createTreeViewer(Composite parent, ITreeContentProvider treeContentProvider, IBaseLabelProvider treeLabelProvider,int style) {
        Tree tree = new Tree(parent,style);
        GridData data = new GridData(GridData.FILL_BOTH);
        data.verticalSpan = 2;
        tree.setLayoutData(data);
        tree.setFont(parent.getFont());

        TreeViewer treeViewer = new TreeViewer(tree);
        if(treeContentProvider !=null){
        	treeViewer.setContentProvider(treeContentProvider);
        }
        if(treeLabelProvider != null){
        	treeViewer.setLabelProvider(treeLabelProvider);
        }
        return treeViewer;
    }
    
    protected abstract void createTabbedPages(TabFolder parent);
    
    protected abstract void createXPathField(Composite parent);
   
}