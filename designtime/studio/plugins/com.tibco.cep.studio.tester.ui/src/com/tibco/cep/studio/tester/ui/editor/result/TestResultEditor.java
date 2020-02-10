package com.tibco.cep.studio.tester.ui.editor.result;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.tester.core.model.TesterResultsType;
import com.tibco.cep.studio.tester.core.provider.TestResultsDeserializer;
import com.tibco.cep.studio.tester.ui.StudioTesterUIPlugin;
import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;


/**
 * 
 * @author sasahoo
 *
 */
public class TestResultEditor extends AbstractStudioResourceEditorPart {

	public static final String ID = "com.tibco.cep.studio.tester.ui.result.editor";
	public TestResultFormViewer testResultFormViewer;
	protected int pageIndex;
	private TestResultEditorPartListener testDataEditorPartListener;
	protected IEditorSite site;
	protected TesterResultsType testerResultsObject;;
	protected String testerRunURI;
	protected String projectName;
	protected TestResultEditorInput testResultEditorInput;
	private String ruleSessionName;

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
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#updateTitle()
	 */
	protected void updateTitle() {
		IEditorInput input = getEditorInput();
//		int index = ((TestResultEditorInput)input).getFileName().lastIndexOf("/");
		String title = input.getName()/*.substring(index + 1)*/;
		setPartName(title);
		setTitleToolTip(input.getToolTipText());
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#addFormPage()
	 */
	protected void addFormPage() throws PartInitException {
		testResultFormViewer = new TestResultFormViewer(this);
		testResultFormViewer.createPartControl(getContainer());
		pageIndex = addPage(testResultFormViewer.getControl());
		this.setActivePage(0);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		//TODO
	}


	@Override
	public void dispose() {
		if (testDataEditorPartListener != null) {
			getSite().getPage().removePartListener(testDataEditorPartListener);
		}
		super.dispose();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		this.site = site;
		if (input instanceof TestResultEditorInput) {
			testResultEditorInput = (TestResultEditorInput)input;
			projectName = testResultEditorInput.getProjectName();
			testerResultsObject = testResultEditorInput.getTesterResultsObject();
			testerRunURI = testerResultsObject.getRunName();
			super.init(site, testResultEditorInput);
		} else if (input instanceof FileEditorInput) {
			file = ((FileEditorInput)input).getFile();
			initialize(file, true);
			super.init(site, testResultEditorInput);
		} else {
			super.init(site, input);
		}
		if (testDataEditorPartListener == null) {
			testDataEditorPartListener =  new TestResultEditorPartListener(this);
			getSite().getPage().addPartListener(testDataEditorPartListener);
		}
	}


	public TestResultFormViewer getTestResultFormViewer() {
		return testResultFormViewer;
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

	public String getTesterRunURI() {
		return testerRunURI;
	}

	public String getRuleSessionName() {
		return ruleSessionName;
	}

	public String getProjectName() {
		return projectName;
	}

	public TesterResultsType getTesterResultsObject() {
		return testerResultsObject;
	}
	
	/**
	 * @param file
	 * @param isSiteInput
	 */
	private void initialize(IFile file, boolean isSiteInput) {
		File testResultFile  = file.getLocation().toFile();
		TestResultsDeserializer deserializer = new TestResultsDeserializer();
		try {
			TesterResultsType testerResultsType = deserializer.serializeWMResultType(testResultFile);
//			TesterResultsType testerResultsType = TestResultMarshaller.deserialize(testResultFile);
			testResultEditorInput = new TestResultEditorInput(file, testerResultsType);
			if (!isSiteInput) {
				setInput(testResultEditorInput);
			}
			projectName = testResultEditorInput.getProjectName();
			testerResultsObject = testResultEditorInput.getTesterResultsObject();
			testerRunURI = testerResultsObject.getRunName();
		} catch (Exception e) {
			StudioTesterUIPlugin.log(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart#doRefresh(org.eclipse.core.resources.IFile)
	 */
	public void doRefresh(IFile file) {
		initialize(file, false);
		projectName = testResultEditorInput.getProjectName();
		testerResultsObject = testResultEditorInput.getTesterResultsObject();
		testerRunURI = testerResultsObject.getRunName();
		if(testResultFormViewer != null)
			testResultFormViewer.refresh(this);
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}
}