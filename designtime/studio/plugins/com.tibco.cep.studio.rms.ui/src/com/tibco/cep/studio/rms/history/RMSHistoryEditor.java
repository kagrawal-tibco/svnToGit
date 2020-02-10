package com.tibco.cep.studio.rms.history;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
public class RMSHistoryEditor extends AbstractStudioResourceEditorPart {

	public static final String ID = "com.tibco.cep.studio.rms.history.editor"; 
	
	public RMSHistoryFormViewer rmsHistoryFormViewer;
	
	protected int pageIndex;
	
	private RMSHistoryEditorPartListener rmsHistoryEditorPartListener;
	
    
	  
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#createPages()
	 */
	@Override
	protected void createPages() {
		createUIEditorPage();
		if (getPageCount() == 1 && getContainer() instanceof CTabFolder) {
			((CTabFolder) getContainer()).setTabHeight(0);
		}
		updateTitle();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#createUIEditorPage()
	 */
	protected void createUIEditorPage() {
		try {
			addFormPage();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#addFormPage()
	 */
	protected void addFormPage() throws PartInitException {
		rmsHistoryFormViewer = new RMSHistoryFormViewer(this);
		rmsHistoryFormViewer.createPartControl(getContainer());
		pageIndex = addPage(rmsHistoryFormViewer.getControl());
		this.setActivePage(0);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		saving = true;
		IRunnableWithProgress operation = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				try {
					//TODO
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		try {
			new ProgressMonitorDialog(getSite().getShell()).run(true, false, operation);
			if (isDirty()) {
				isModified = false;
				firePropertyChange(IEditorPart.PROP_DIRTY);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			saving = false;
		}
	}


	@Override
	public void dispose() {
		if (rmsHistoryEditorPartListener != null) {
			getSite().getPage().removePartListener(rmsHistoryEditorPartListener);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		//TODO:set relevant information to RMSHistoryEditorInput
		super.init(site, input);
		if (rmsHistoryEditorPartListener == null) {
			rmsHistoryEditorPartListener = new RMSHistoryEditorPartListener(this);
			getSite().getPage().addPartListener(rmsHistoryEditorPartListener);
		}
	}

	public RMSHistoryFormViewer getRMSHistoryFormViewer() {
		return rmsHistoryFormViewer;
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}
	
	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName("History");
		setTitleToolTip(input.getToolTipText());
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}
	
	/**
	 * This refreshes the History editor, if selection changes
	 * @param historyURL
	 * @param resourcePath
	 * @param projectName
	 */
	public void refresh(String historyURL, String resourcePath, String projectName) {
		RMSHistoryEditorInput input =  (RMSHistoryEditorInput)getEditorInput();
		input.setHistoryURL(historyURL);
		input.setResourcePath(resourcePath);
		input.setProjectName(projectName);
		
		rmsHistoryFormViewer.doRefresh();
	}
}