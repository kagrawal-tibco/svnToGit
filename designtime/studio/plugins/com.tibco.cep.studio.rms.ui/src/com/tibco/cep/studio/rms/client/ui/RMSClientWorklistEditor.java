package com.tibco.cep.studio.rms.client.ui;

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

public class RMSClientWorklistEditor extends AbstractStudioResourceEditorPart {

	public static final String ID = "com.tibco.cep.studio.rms.worklist.editor"; 
	
	public RMSClientWorklistFormViewer worklistFormViewer;
	
	protected int pageIndex;
	
	private RMSClientWorklistEditorPartListener worklistEditorPartListener;
	
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
		worklistFormViewer = new RMSClientWorklistFormViewer(this);
		worklistFormViewer.createPartControl(getContainer());
		pageIndex = addPage(worklistFormViewer.getControl());
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
		if (worklistEditorPartListener != null) {
			getSite().getPage().removePartListener(worklistEditorPartListener);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		super.init(site, input);
		if (worklistEditorPartListener == null) {
			worklistEditorPartListener = new RMSClientWorklistEditorPartListener(this);
			getSite().getPage().addPartListener(worklistEditorPartListener);
		}
	}

	public RMSClientWorklistFormViewer getWorklistFormViewer() {
		return worklistFormViewer;
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}
	
	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName("Worklist");
		setTitleToolTip(input.getToolTipText());
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
	  
	public void refresh() {
		worklistFormViewer.refreshClientTaskViewer();
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}
}