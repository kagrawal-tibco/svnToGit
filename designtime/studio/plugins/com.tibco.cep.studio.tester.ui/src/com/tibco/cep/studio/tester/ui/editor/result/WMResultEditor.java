package com.tibco.cep.studio.tester.ui.editor.result;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import com.tibco.cep.studio.debug.core.model.IRuleRunTarget;
import com.tibco.cep.studio.tester.core.model.TesterResultsType;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;

/**
 * 
 * @author sasahoo
 *
 */
public class WMResultEditor extends AbstractStudioResourceEditorPart {

	public static final String ID = "com.tibco.cep.studio.tester.ui.wm.result.editor";
	public WMResultFormViewer wmResultFormViewer;
	protected int pageIndex;
	private WMResultEditorPartListener wmResultEditorPartListener;
	protected IEditorSite site;
	private String ruleSessionName;
	private String projectName;
	private String tooltip;
	protected TesterResultsType testerResultsObject;
	private IRuleRunTarget runTarget;

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
	
	protected void updateTitle() {
		setPartName(ruleSessionName);
		setTitleToolTip(tooltip);
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
		wmResultFormViewer = new WMResultFormViewer(this);
		wmResultFormViewer.createPartControl(getContainer());
		pageIndex = addPage(wmResultFormViewer.getControl());
		this.setActivePage(0);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		
	}

	@Override
	public void dispose() {
		if (wmResultEditorPartListener != null) {
			getSite().getPage().removePartListener(wmResultEditorPartListener);
		}
		super.dispose();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.site = site;
		if (input instanceof WMResultEditorInput) {
			WMResultEditorInput wmResultEditorInput = (WMResultEditorInput)input;
			ruleSessionName = wmResultEditorInput.getSessionName();
			projectName = wmResultEditorInput.getProjectName();
			testerResultsObject = wmResultEditorInput.getTesterResultsObject();
			tooltip = wmResultEditorInput.getFullPath();
			runTarget = wmResultEditorInput.getRunTarget();
			StudioTesterUIPlugin.debug("Current project Name:{0}", projectName);
			super.init(site, wmResultEditorInput);
		} else {
			super.init(site, input);
		}
		if (wmResultEditorPartListener == null) {
			wmResultEditorPartListener =  new WMResultEditorPartListener(this);
			getSite().getPage().addPartListener(wmResultEditorPartListener);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorPart#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * @return
	 */
	public String getRuleSessionName() {
		return ruleSessionName;
	}

	/**
	 * @return
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @return
	 */
	public TesterResultsType getTesterResultsObject() {
		return testerResultsObject;
	}

	/**
	 * @return
	 */
	public IRuleRunTarget getRunTarget() {
		return runTarget;
	}
	
	/**
	 * @param testerResultsObject
	 */
	public void doRefresh(TesterResultsType testerResultsObject) {
		wmResultFormViewer.setTesterResultsObject(testerResultsObject);
		wmResultFormViewer.refresh();
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}
}