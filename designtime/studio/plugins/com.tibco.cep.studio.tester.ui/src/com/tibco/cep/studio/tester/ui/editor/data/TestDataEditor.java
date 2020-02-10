package com.tibco.cep.studio.tester.ui.editor.data;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.tester.core.utils.TesterCoreUtils;
import com.tibco.cep.studio.ui.AbstractStudioResourceEditorPart;
import com.tibco.cep.studio.ui.resources.TestDataEditorInput;

/**
 * 
 * @author sasahoo
 *
 */
public class TestDataEditor extends AbstractStudioResourceEditorPart {

	public TestDataDesignViewer testDataDesignViewer;
	protected int pageIndex;
	private TestDataEditorPartListener testDataEditorPartListener;
	private TestDataEditorInput testDataEditorInput;
	@SuppressWarnings("unused")
	private File testFile;
	private String testFilePath;
	public String getTestFilePath() {
		return testFilePath;
	}

	protected IEditorSite site;
	protected Entity entity;
	private String projectName;
//	private TestDataLoader testDataLoader;
	@SuppressWarnings("rawtypes")
	private Vector testTableRowData = new Vector();
	private boolean invalidPart = false;
	
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
		setCatalogFunctionDrag(true);
		/*if (invalidPart) {
			MessageDialog.openError(getSite().getShell(), "Test Data Editor Error", "Test Data Editor can't be opened");
		}*/
	}
	
	protected void updateTitle() {
		IEditorInput input = getEditorInput();
		if (invalidPart) {
			setPartName(getEditorInput().getName() + " [Invalid]");
			setTitleToolTip(getEditorInput().getToolTipText());
		} else {
			setPartName(input.getName());
			setTitleToolTip(input.getToolTipText());
		}
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
		testDataDesignViewer = new TestDataDesignViewer(this, invalidPart);
		testDataDesignViewer.createPartControl(getContainer());
		pageIndex = addPage(testDataDesignViewer.getControl());
		if (!invalidPart) {
		//	populateTestData();
		}
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
					boolean fromTableAnalyser=false;
					TesterCoreUtils.exportDataToXMLDataFile(testFilePath,testDataDesignViewer.testerPropertiesTable.getModel(),fromTableAnalyser,false,testDataDesignViewer.testerPropertiesTable.getDependentConceptItems(),null);
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
		if (getSite() != null && testDataEditorPartListener != null) {
			getSite().getPage().removePartListener(testDataEditorPartListener);
		}
		super.dispose();
	}


	@SuppressWarnings("unused")
	private void removeResultData(File file) {
		if (file.isDirectory()) {
			String[] children = file.list();
			for (int i = 0; i < children.length; i++) {

				removeResultData(new File(file, children[i]));
			}
		} else {
			if (file.getName().endsWith(".concepttestdataresult")
					|| file.getName().endsWith(".eventtestdataresult")) {

				file.delete();
			}
		}
	}
   
	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.forms.AbstractSaveableEntityEditorPart#init(org.eclipse.ui.IEditorSite, org.eclipse.ui.IEditorInput)
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		
		//BE-23373 -Since indexes are not loaded when the test data loads , this creates issues while finding the dependent resource.
		//Hence loading the indexes beforehand to handle such issue.Revisit with alternative implementation.
		StudioCorePlugin.getDesignerModelManager().loadAllIndexes();
		
		this.site = site;
		Entity entity=null;
		if(input instanceof TestDataEditorInput){
			entity=((TestDataEditorInput) input).getEntity();
		}
		super.init(site, input);
		//Check whether valid Test Data Table Editor
		if (isInvalidEditor(input)) {
			return;
		}
		if (input instanceof TestDataEditorInput) {
			file = ((TestDataEditorInput)input).getFile();
			setProject(file.getProject());
			setEntity(((TestDataEditorInput)input).getEntity());
			projectName = ((TestDataEditorInput)input).getEntity().getOwnerProjectName();
			File f = ((TestDataEditorInput)input).getTestFile();
			setTestFile(f);
			if (f != null) {
				setTestFilePath(f.toString());
			}
			super.init(site, (TestDataEditorInput)input);
			testDataEditorPartListener = new TestDataEditorPartListener(this);
			getSite().getPage().addPartListener(testDataEditorPartListener);
		}else if (input instanceof FileEditorInput) {
			file = ((FileEditorInput)input).getFile();
			File testDataFile=null;
			if(!file.exists()){
				String filePath=file.getFullPath().toString();
				filePath=filePath.replaceFirst("/TestData", "");
				testDataFile= new File(filePath);
			}
			setProject(file.getProject());
			testDataEditorInput = new TestDataEditorInput(file);
		//	File testFile  = file.getLocation().toFile();
			String fileLocation=null;
			if(!file.exists()){
				fileLocation=file.getLocation().toString().replaceFirst("/TestData", "");
			}else{
				fileLocation=file.getLocation().toString();
			}
			File testFile = testDataFile;
			setTestFile(testFile);
			setTestFilePath(fileLocation);
			testDataEditorInput.setTestFile(testFile);
			try {
				String fullPath=TesterCoreUtils.getEntityInfo(fileLocation);
				entity = IndexUtils.getEntity(getProject().getName(), fullPath);
				setEntity(entity);
				projectName = entity.getOwnerProjectName();
				testDataEditorInput.setEntity(entity);
				super.init(site, testDataEditorInput);
				testDataEditorPartListener = new TestDataEditorPartListener(this);
				getSite().getPage().addPartListener(testDataEditorPartListener);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		} 
		else {
			super.init(site, input);
		}
	}

	/**
	 * @param input
	 * @return
	 */
	private boolean isInvalidEditor(IEditorInput input) {
		if (input instanceof FileEditorInput) {
					
			if(input instanceof TestDataEditorInput){
				Entity entity = ((TestDataEditorInput) input).getEntity();
				if (entity == null) {
					invalidPart = true;
					if (invalidPart) {
						MessageDialog.openError(getSite().getShell(), "Test Data Editor Error", "Test Data Editor can't be opened " );
					}
				//	return invalidPart;
				} else {
					return false;
				}
			}
				
			IFile file = ((FileEditorInput)input).getFile();
			String fullPath=TesterCoreUtils.getEntityInfo(file.getLocation().toString());
			Entity entity = IndexUtils.getEntity(file.getProject().getName(), fullPath);
			if (entity == null) {
				invalidPart = true;
				if (invalidPart) {
					MessageDialog.openError(getSite().getShell(), "Test Data Editor Error", "Test Data Editor can't be opened, " + fullPath + " is not present in this project." );
				}
		//		return invalidPart;
			} else {
				return false;
			}
		}
		return invalidPart;
	}
	
	public TestDataDesignViewer getTestDataDesignViewer() {
		return testDataDesignViewer;
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	public void setTestFile(File testFile) {
		this.testFile = testFile;
	}

	public void setTestFilePath(String testFilePath) {
		this.testFilePath = testFilePath;
	}
	
	public String getProjectName(){
		return projectName;
	}
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}
	
	
	
	/**
	 * Refresh domain values Table on each editor focus
	 * Creating new domain cell editor for domain table update
	 */
	public Entity getEntity() {
		return entity;
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Vector getTestTableRowData() {
		return testTableRowData;
	}

	@Override
	public String getPerspectiveId() {
		return "com.tibco.cep.studio.application.perspective";
	}

}