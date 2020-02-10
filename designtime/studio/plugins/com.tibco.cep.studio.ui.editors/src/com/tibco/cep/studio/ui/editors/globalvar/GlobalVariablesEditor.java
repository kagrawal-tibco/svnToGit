package com.tibco.cep.studio.ui.editors.globalvar;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormPage;

import com.tibco.cep.sharedresource.ui.AbstractSharedResourceEditorPage;
import com.tibco.cep.sharedresource.ui.editors.AbstractSharedResourceEditor;
import com.tibco.cep.studio.ui.StudioUIManager;

/*
@author ssailapp
@date Jan 6, 2010 2:14:33 PM
 */

public class GlobalVariablesEditor extends AbstractSharedResourceEditor {

	private GlobalVariablesModelMgr modelmgr;
	private ArrayList<String> gvFiles;
	private IProject activeProject;
	private GlobalVariablesGeneralPage page;
	
	public GlobalVariablesEditor() {
		super();
		gvFiles = new ArrayList<String>();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		StudioUIManager.getInstance();
		activeProject = getEditorFile().getProject();
		if (input != null) {
			setPartName(getTitle());
			setTitleToolTip(input.getToolTipText());
		}
	}
	
	protected void createGeneralPage() throws PartInitException {
		page = new GlobalVariablesGeneralPage(this, modelmgr);
		addPage(page);
		controls.add(page.getPartControl());
	}

	public String getTitle() {
		String name = "Global Variables";
		if (getEditorFile() != null && activeProject != null) {
			name += ": " + activeProject.getName();
		}
		return (name);
	}

	protected void loadModel() {
		IFolder folder = activeProject.getFolder(GlobalVariablesModelMgr.GV_DIR);
		fetchGvFiles(folder.getLocation().toFile());
		modelmgr = new GlobalVariablesModelMgr(activeProject.getLocation().toString(), gvFiles, this);
		modelmgr.parseModel();
	}

	protected void addPages() {
		loadModel();
		try {
			createGeneralPage();
			emptyBottomTab();
		} catch (PartInitException pie) {
			pie.printStackTrace();
			// TODO 
		}
		if (isDirty()) {
			doSave(null);
		}
	}
	
	@Override
	public void doSave(IProgressMonitor monitor) {
		IRunnableWithProgress operation = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					modelmgr.saveModel();
					refreshProject();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};

		try {
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);
			if (isDirty()) {
				isEditorDirty = false;
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
	
	private void refreshProject() { 
		IFolder folder = activeProject.getFolder("/defaultVars");
		if (folder != null) {
			try {
				if (!folder.isSynchronized(IResource.DEPTH_INFINITE)) {
					folder.refreshLocal(IResource.DEPTH_INFINITE, null);
				}
			} catch (CoreException e) {
			}
		}
	}
	
	@Override
	public void doSaveAs() {
	}
	
	private void fetchGvFiles(File dir) {
		gvFiles.clear();
		visitGvFolders(dir);
	}
	
	private void visitGvFolders(File dir) {
	    processGvFolder(dir);
	    if (dir.isDirectory()) {
	        String[] gvFolders = dir.list();
	        for (int i=0; i<gvFolders.length; i++) {
	            visitGvFolders(new File(dir, gvFolders[i]));
	        }
	    }
	}
	
	private void processGvFolder(File dir) {
		if (dir == null || !dir.isDirectory())
			return;
		FilenameFilter filter = new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".substvar");
		    }
		};

		File[] files = dir.listFiles(filter);
		if (files != null) {
		    for (int i=0; i<files.length; i++) {
	    		gvFiles.add(files[i].getPath());
		    }
		}
	}

	@Override
	protected String saveModel() {
		return null;
	}

	@Override
	protected FormPage[] getEditorPages() {
		return new FormPage[] { page };
	}

	@Override
	protected AbstractSharedResourceEditorPage[] getSchemaEditorPages() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(true, newPageIndex);
	}
	
}
