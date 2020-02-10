/**
 * 
 */
package com.tibco.cep.studio.ui.wizards;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.tibco.be.util.XiSupport;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.resources.TestDataEditorInput;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.serialization.DefaultXmlContentSerializer;

/**
 * @author mgujrath
 *
 */
public class NewTestDataWizard extends AbstractNewEntityWizard<EntityFileCreationWizard>{

	private NewTestDataWizardPage newTestDataWizardPage;
	private IWorkbenchWindow fWindow;
	private IProject fProject;
	private IStructuredSelection strSelection;
	private IProject project;	
	private IWorkbenchPage page;
	private IWorkbench workbench;

	public NewTestDataWizard() {
		setWindowTitle("Create Test Data");
		setDefaultPageImageDescriptor(getDefaultPageImageDescriptor());
	}
	/**
	 * @param window
	 * @param title
	 * @param selection 
	 */
	public NewTestDataWizard(IWorkbenchWindow window, String title, IProject project,IStructuredSelection selection) {
		setWindowTitle("Create Test Data");
		setDefaultPageImageDescriptor(getDefaultPageImageDescriptor());
		this.fWindow = window;
		this.fProject = project;
		this.strSelection=selection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
			this.newTestDataWizardPage = new NewTestDataWizardPage(strSelection);
			addPage(this.newTestDataWizardPage);
	}
	
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		
		page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		try {
			if(strSelection != null && strSelection instanceof IStructuredSelection){
				strSelection = (IStructuredSelection)strSelection;
			
				if (strSelection != null) {
					project = StudioResourceUtils.getProjectForWizard(strSelection);
				}
					
				String input_dir=newTestDataWizardPage.getContainerFullPath().removeFirstSegments(1).toString();
				String path = project.getLocation().toOSString() +File.separator+ input_dir;
						
					if(newTestDataWizardPage.getSelectedEntityPath().contains(".concept")){
						createTestData(project,
								input_dir,
								"com.tibco.cep.studio.tester.ui.editor.data.concepttestdataeditor",
								path, CommonIndexUtils.CONCEPT_EXTENSION,
								"concepttestdata");
					}
					if(newTestDataWizardPage.getSelectedEntityPath().contains(".event")){
						createTestData(project,
								input_dir,
								"com.tibco.cep.studio.tester.ui.editor.data.eventtestdataeditor",
								path, CommonIndexUtils.EVENT_EXTENSION,
								"eventtestdata");
						
					}
					if(newTestDataWizardPage.getSelectedEntityPath().contains(".scorecard")){
						createTestData(project,
								input_dir,
								"com.tibco.cep.studio.tester.ui.editor.data.scorecardtestdataeditor",
								path, CommonIndexUtils.SCORECARD_EXTENSION,
								"scorecardtestdata");
						
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}
	
	/**
	 * @param file
	 * @param inputDir
	 * @param editorId
	 * @param path
	 * @param extension
	 * @param testDataExtension
	 */
	private void createTestData(IProject project,
			                    String inputDir,
			                    String editorId, 
			                    String path, 
			                    String extension, 
			                    String testDataExtension) {
		try {
			Entity entity = IndexUtils.getEntity(project.getName(),newTestDataWizardPage.getSelectedEntityPath().split("\\.")[0]);
			String testFilePath =  path + File.separator +newTestDataWizardPage.getFileName();
			File testFile = new File(testFilePath);
			String testDataFilePath = inputDir + entity.getFolder() + testFile.getName();
			if (testFile.exists()) {
				MessageDialog messageDialog = new MessageDialog(
						Display.getDefault().getActiveShell(),
						com.tibco.cep.studio.ui.util.Messages.getString("test.data.new.message.title"),
						null,
						com.tibco.cep.studio.ui.util.Messages.getString("test.data.new.message.desc", testDataFilePath.substring(1)),
						MessageDialog.QUESTION_WITH_CANCEL, 
						new String[]{
							IDialogConstants.YES_LABEL, 
							IDialogConstants.NO_LABEL, 
							IDialogConstants.CANCEL_LABEL},
						0
						);
				
				switch(messageDialog.open()) {
				case 0: 
					testFile.delete();
					break;
				case 1:
					break;
				case 2:
					return;
					
				}
							
				//boolean confirm = MessageDialog.openConfirm(Display.getDefault().getActiveShell(), 
				//		com.tibco.cep.studio.ui.util.Messages.getString("test.data.new.message.title"), com.tibco.cep.studio.ui.util.Messages.getString("test.data.new.message.desc", testDataFilePath.substring(1)));
			//	if (confirm) {
			//		testFile.delete();
			//	} else {
			//		return;
			//	}
			} else {
				if (!testFile.getParentFile().isDirectory()) {
					boolean success = testFile.getParentFile().mkdirs();
					if (!success) {
						//TODO
					}
				}
			}
			testFile.createNewFile();
			createNewTestData(entity.getFullPath(), entity.getOwnerProjectName(), 
					newTestDataWizardPage.getFileName().replace(CommonIndexUtils.DOT + newTestDataWizardPage.getFileExtension(), ""), inputDir, testFile, CommonIndexUtils.getElementType(entity));
			project.refreshLocal(IProject.DEPTH_INFINITE,null);
			IFile tFile = project.getFile(newTestDataWizardPage.getContainerFullPath().removeFirstSegments(1).toString()+File.separator+newTestDataWizardPage.getFileName());
			TestDataEditorInput testDataEditorInput = new TestDataEditorInput(tFile);
			testDataEditorInput.setEntity(entity);
			testDataEditorInput.setTestFile(testFile);
			page.openEditor(testDataEditorInput, editorId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performCancel()
	 */
	@Override
	public boolean performCancel() {
		return true;
	}

	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		this.fWindow=workbench.getActiveWorkbenchWindow();
		this.strSelection = selection;
		if(selection.getFirstElement() instanceof IFile){
			this.fProject=((IFile)selection.getFirstElement()).getProject();
		}
		if(selection.getFirstElement() instanceof IProject){
			this.fProject=((IProject)selection.getFirstElement());
		}
	}
	@Override
	protected String getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected String getWizardDescription() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected String getWizardTitle() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	protected ImageDescriptor getDefaultPageImageDescriptor() {
		return StudioUIPlugin.getImageDescriptor("icons/wizard/TestDataWizard.png");
	}
	@Override
	protected void createEntity(String filename, String baseURI,
			IProgressMonitor monitor) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void createNewTestData(String entityPath, String projectName, String name, String folder, File filename, ELEMENT_TYPES type) {
		XiFactory factory = XiSupport.getXiFactory();
		XiNode root = factory.createDocument();
		XiNode testdataRoot = factory.createElement(ExpandedName.makeName("testdata"));
		XiNode nameAttribElement = factory.createAttribute(ExpandedName.makeName("name"), name);// Adding
		XiNode folderAttribElement = factory.createAttribute(ExpandedName.makeName("folder"), folder);// Adding
		XiNode projectNameAttribElement = factory.createAttribute(ExpandedName.makeName("project"), projectName);// Adding
		XiNode entPathAttribElement = factory.createAttribute(ExpandedName.makeName("entityPath"), entityPath);// Adding
		XiNode entTypeAttribElement = factory.createAttribute(ExpandedName.makeName("type"), type.getName());// Adding
		testdataRoot.appendChild(nameAttribElement);
		testdataRoot.appendChild(folderAttribElement);
		testdataRoot.appendChild(projectNameAttribElement);
		testdataRoot.appendChild(entPathAttribElement);
		testdataRoot.appendChild(entTypeAttribElement);
		root.appendChild(testdataRoot);
		FileOutputStream fos = null;
		BufferedWriter bufWriter = null;
		try {
			fos = new FileOutputStream(filename);
			bufWriter = new BufferedWriter(new OutputStreamWriter(fos,
					ModelUtils.DEFAULT_ENCODING));
			DefaultXmlContentSerializer handler = new DefaultXmlContentSerializer(
					bufWriter, ModelUtils.DEFAULT_ENCODING);
			root.serialize(handler);
//			IFile file = ResourcesPlugin.getWorkspace().getRoot()
//					.getFileForLocation(new Path(filename));
//			if (file.exists()) {
//				try {
//					CommonUtil.refresh(file, IResource.DEPTH_ZERO, false);
//				} catch (Exception ce) {
//					ce.printStackTrace();
//				}
//			}
		} catch (IOException e) {
			StudioUIPlugin.log(e);
		} catch (Exception e) {
			StudioUIPlugin.log(e);
		} finally {
			try {
				fos.close();
				bufWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
