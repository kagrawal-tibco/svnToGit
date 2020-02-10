/**
 * 
 */
package com.tibco.cep.studio.ui.editors.wizards;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IImportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.Scorecard;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.testdata.exportHandler.TestData;
import com.tibco.cep.studio.core.util.CommonUtil;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.editors.domain.DomainFormEditor;
import com.tibco.cep.studio.ui.editors.domain.DomainFormEditorInput;
import com.tibco.cep.studio.ui.editors.wizardPages.ITestDataSourceWizardPage;
import com.tibco.cep.studio.ui.editors.wizardPages.TestDataImportSourceSelectionWizardPage;
import com.tibco.cep.studio.ui.util.Messages;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;
import com.tibco.cep.studio.ui.util.TesterImportExportUtils;


/**
 * @author mgujrath
 *
 */
public class ImportTestDataWizard<P extends WizardPage & ITestDataSourceWizardPage> extends Wizard implements IImportWizard {
	

	
	private TestDataImportSourceSelectionWizardPage mainPage;
	
	/**
	 * Child Page
	 */
	private P childPage;
	
	private IStructuredSelection selection;
	
	private IWorkbenchWindow workbenchWindow;

	private int NO_OPTION_SELECTED=0;
	
	private Entity entity;
	
	private String projectName;
	
	
	/**
	 * 
	 */
	public ImportTestDataWizard() {
		// TODO Auto-generated constructor stub
		
		
	}
	
	@Override
	public void addPages() {
		mainPage = new TestDataImportSourceSelectionWizardPage(Messages.getString("ImportTestDataWizard.Title"), selection);
		addPage(mainPage);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public IWizardPage getNextPage(IWizardPage page) {
		return null;
	
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
	 */
	/*@Override
	@SuppressWarnings("unchecked")
	public IWizardPage getNextPage(IWizardPage page) {
		childPage=TestDataImportSourceWizardPageFactory.INSTANCE.getWizardPage(mainPage.getImportSource(), Messages.getString("ImportTestDataWizard.Title"));
				
		//childPage.setDescription(Messages.getString("import.domain.wizard.description"));
		childPage.setProjectName(mainPage.getProject().getName());
		for (IWizardPage addedPage : getPages()) {
			if (addedPage.getClass().equals(childPage.getClass())) {
				//Already added
				//The cast below is safe
				return childPage = (P)addedPage;
			}
		}
		addPage(childPage);
		return childPage;
	}*/

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {

	//	String projectName=(StudioResourceUtils.getResourcePathFromContainerPath(mainPage.getContainerFullPath())).getName().toString();
	//	String projectName=mainPage.getContainerFullPath().segment(0);
		String filename=mainPage.getFileLocationText().getText();
		List<ArrayList<String>> testData=new ArrayList<ArrayList<String>>();
		List<String> tableColumnNames=new ArrayList<String>();
		List<Boolean> selectRowData=new ArrayList<Boolean>();
	
		List<String> fullPathandType=readExcelFile(filename,testData,tableColumnNames,selectRowData);
		String importPath=mainPage.getContainerFullPath().toString();
		entity=CommonIndexUtils.getEntity(projectName, fullPathandType.get(0));
		if (entity == null) {
			return true;
		}
		TestData model=new TestData(tableColumnNames,testData);
		List<LinkedHashMap<String,String>> input=new ArrayList<LinkedHashMap<String,String>>();
		Vector<Vector> dataVector=new Vector<Vector>();
		for(List<String> list:testData){
			LinkedHashMap<String,String> dataInput=new LinkedHashMap<String,String>();
			Vector<String> vector = new Vector<String>();
			
			vector.add(entity.getFullPath());
			if (entity instanceof Event) {
				vector.add(list.get(0));
			}
			for(int i=0;i<list.size();i++){
				dataInput.put(tableColumnNames.get(i),list.get(i));
				vector.add(list.get(i));
			}
			input.add(dataInput);
			dataVector.add(vector);
		}
		
		model.setInput(input);
		model.setVectors(dataVector);
		model.setSelectRowData(selectRowData);
		model.setEntity(entity);
		
		try {
			IFile file = IndexUtils.getFile(projectName, entity);
			/*String input_dir = StudioUIPlugin
					.getDefault()
					.getPreferenceStore()
					.getString(StudioUIPreferenceConstants.TEST_DATA_INPUT_PATH);*/
			
			IPath path = file.getProject().getLocation().removeLastSegments(1);
					
			String testFilePath = path.toString() + importPath +"/"+mainPage.getFileName() +"."
					+ fullPathandType.get(1);
			File testFile = new File(testFilePath);
			if (testFile.exists()) {
				
				MessageDialog messageDialog = new MessageDialog(
						Display.getDefault().getActiveShell(),
						com.tibco.cep.studio.ui.util.Messages.getString("test.data.new.message.title"),
						null,
						com.tibco.cep.studio.ui.util.Messages.getString("test.data.new.message.overwrite", entity.getFullPath(),""),
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
					NO_OPTION_SELECTED=1;
					Vector<Vector> vectors=appendTestData(testFilePath,tableColumnNames);
					vectors.addAll(dataVector);
					model.setAppendedVectors(vectors);
					break;
					
				case 2:
					return false;
			} 
			}
			
			else {
				if (!testFile.getParentFile().isDirectory()) {
					@SuppressWarnings("unused")
					boolean success = testFile.getParentFile().mkdirs();
				}
			}
			if(NO_OPTION_SELECTED==0){
				testFile.createNewFile();
				file.getProject().refreshLocal(IProject.DEPTH_INFINITE, null);
				TesterImportExportUtils.exportDataToXMLDataFile(testFilePath,model,false,false);
			}
			else{
				file.getProject().refreshLocal(IProject.DEPTH_INFINITE, null);
				TesterImportExportUtils.exportDataToXMLDataFileAppend(testFilePath, entity, model, tableColumnNames, false);
			}
			NO_OPTION_SELECTED=0;
			MessageDialog.openInformation(workbenchWindow.getShell(),Messages.getString("ImportTestDataWizard.Title"),"Test Data Import Successful");
			
		} catch (Exception e) {
			StudioUIPlugin.log(e);
		}
		return true;
		
	}
	
	private Vector<Vector> appendTestData(String testDataFilePath, List<String> tableColumnNames){
		String entityType=null;
		boolean flag=false;
		if (entity instanceof Scorecard) {
			entityType = "Scorecard";
			flag = true;
		} else if (entity instanceof Event) {
			entityType = "Event";
		} else if (entity instanceof Concept) {
			if (flag != true) {
				entityType = "Concept";
			}
		}
		TestData existingModel=null;
		try {
			existingModel = TesterImportExportUtils.getDataFromXML(testDataFilePath, entityType, entity,tableColumnNames);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Vector<Vector> vectors = new Vector<Vector>();
		for(List<String> list:existingModel.getTestData()){
			Vector<String> vector = new Vector<String>();
			vector.add(entity.getFullPath());
			for(int i=0;i<list.size();i++){
				vector.add(list.get(i));
			}
			vectors.add(vector);
		}
		return vectors;
	
	}

	private List<String> readExcelFile(String filename, 
			List<ArrayList<String>> testData, List<String> tableColumnNames,
			List<Boolean> selectRowData) {
		List<String> fullPathandType=new ArrayList<String>();
		int colCount = 0;
		String fullPath=null;
		String extension=null;
		try {
			FileInputStream file = new FileInputStream(new File(filename));
			// Get the workbook instance for XLS file
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			// Get first sheet from the workbook
			HSSFSheet sheet = workbook.getSheetAt(0);
			// Iterate through each rows from first sheet
			Iterator<Row> rowIterator = sheet.iterator();
			Row row = rowIterator.next();
			row=rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			Cell cell = cellIterator.next();
			projectName = cell.getStringCellValue().split(":")[1].trim();
			
			row=rowIterator.next();
			
			cellIterator = row.cellIterator();
			cell = cellIterator.next();
			String entityName = cell.getStringCellValue().split(":")[1].trim();
			row=rowIterator.next();
			cellIterator = row.cellIterator();
			cell = cellIterator.next();
			String type = cell.getStringCellValue().split(":")[1].trim();
			row=rowIterator.next();
			cellIterator = row.cellIterator();
			cell = cellIterator.next();
			String folder=cell.getStringCellValue().split(":")[1].trim();
			fullPath = folder+entityName;
			
			if (type.equalsIgnoreCase("Event")) {
				extension = "eventtestdata";
			} else if (type.equalsIgnoreCase("Concept")) {
				extension = "concepttestdata";
			}
			
			row = rowIterator.next();
			cellIterator = row.cellIterator();

			while (cellIterator.hasNext()) {
				cell = cellIterator.next();
				tableColumnNames.add(cell.getStringCellValue().split(" ")[0]);
			}
			tableColumnNames.remove(0);
			while (rowIterator.hasNext()) {
				Row dataRow = rowIterator.next();
				// For each row, iterate through each columns
				Iterator<Cell> dataCellIterator = dataRow.cellIterator();
				int loop = 0;
				while (loop < colCount) {
					dataCellIterator.next();
					loop = loop + 1;
				}
				List<String> data = new ArrayList<String>();
				while (dataCellIterator.hasNext()) {

					cell = dataCellIterator.next();
					switch (cell.getCellType()) {
					case BOOLEAN:
						if (cell.getColumnIndex() == 0) {
							selectRowData.add(cell.getBooleanCellValue());
						} else {
							data.add(cell.getBooleanCellValue() + "");
						}
						break;
					case NUMERIC:
						data.add(cell.getNumericCellValue() + "");
						break;
					case STRING:
						data.add(cell.getStringCellValue());
						break;
					case BLANK:
						data.add("");
						break;
					}
				}
				testData.add((ArrayList<String>) data);

			}
			file.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		fullPathandType.add(fullPath);
		fullPathandType.add(extension);
		return fullPathandType;
	}	

	/**
	 * 
	 * @param <O>
	 * @param domainName
	 */
	private <O extends Object> void importDomainModel(String domainName) {/*
		List<ValidationError> validationErrors = new ArrayList<ValidationError>(0);
		//Get values of each field
		String domainDataType = mainPage.getDomainDataType();
		DOMAIN_DATA_TYPES dataTypeEnum = DOMAIN_DATA_TYPES.get(domainDataType);
		O dataSource = childPage.getDataSource();
		IFile file = mainPage.getModelFile();
		String domainDescription = mainPage.getDomainDescription();
		IDomainModelImportHandler<O, IDomainImportSource<O>> domainModelImportHandler = null;
		boolean duplicateFlag = true;
		try {
			String domainFolderPath = StudioResourceUtils.getFolder(file);
			String rootDirectoryPath = getProject(mainPage.getProject().getName()).getLocation().toPortableString();
			DomainConfiguration domainConfiguration = new DomainConfiguration();
			domainConfiguration.setDomainDataType(dataTypeEnum);
			domainConfiguration.setDomainName(domainName);
			domainConfiguration.setDomainDescription(domainDescription);
			domainConfiguration.setDomainFolderPath(domainFolderPath);
			domainConfiguration.setProjectDirectoryPath(rootDirectoryPath);
			
			domainModelImportHandler =
				DomainModelImportHandlerFactory.INSTANCE.getImportHandler(mainPage.getImportSource(), validationErrors, dataSource);
			domainModelImportHandler.importDomain(domainConfiguration);
			//Save it
			if (domainModelImportHandler.getImportedDomain() != null) {
				String pathToSave = 
					new StringBuilder(rootDirectoryPath).
					append(File.separator).
					append(domainFolderPath).
					toString();
				ModelUtils.saveDomain(domainModelImportHandler.getImportedDomain(), pathToSave);
				file.refreshLocal(0, null);
			}
		} catch (Exception e) {
			StudioUIPlugin.log(e);
		}
		if (domainModelImportHandler.getImportedDomain() == null) {
			MessageDialog.openInformation(workbenchWindow.getShell(), 
					Messages.getString("ImportTestDataWizard.Title"), 
					                      "Imported Domain is Empty. Domain is not created");
		} else if (validationErrors.isEmpty()) {
			MessageDialog.openInformation(workbenchWindow.getShell(), 
					Messages.getString("ImportTestDataWizard.Title"), 
					                      "Domain Import Successful");
			openEditor(mainPage.getProject().getName(), domainModelImportHandler.getImportedDomain());
		} else {
			//TODO Find a better way to display errors
			for (ValidationError ve : validationErrors) {
				MessageDialog.openError(workbenchWindow.getShell(), 
						Messages.getString("ImportTestDataWizard.Title"),
						ve.getMessage());
				if(!ve.getMessage().contains("Duplicate entries in Domain Model")) {
					duplicateFlag = false;
				}
			}
			if(duplicateFlag) {
				openEditor(mainPage.getProject().getName(), domainModelImportHandler.getImportedDomain());
			}
		}
	*/}
	
	private void openEditor(String projectName, Domain importedDomain) {
		IFile file = IndexUtils.getFile(projectName, importedDomain);
		if (null == file) {
			MessageDialog.openError(workbenchWindow.getShell(), 
					Messages.getString("ImportTestDataWizard.Title"), 
					                "Failed to open file for editor");
			return;
		}
		DomainFormEditorInput domainFormEditorInput = new DomainFormEditorInput(file, importedDomain);
		IWorkbenchPage page = workbenchWindow.getActivePage();
		//Open editor
		try {
			page.openEditor(domainFormEditorInput, DomainFormEditor.ID);
		} catch (PartInitException pe) {
			StudioUIPlugin.log(pe);
			MessageDialog.openError(workbenchWindow.getShell(), 
	                "Import Domain", 
	                "Failed to open domain editor");
			return;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle(Messages.getString("ImportTestDataWizard.Title")); 
		workbenchWindow = workbench.getActiveWorkbenchWindow();
		this.selection = selection;
	}

	/**
	 * Checks if this is a duplicate entity.
	 * @param entity
	 * @param resourceName
	 * @param duplicateFileName
	 * @return
	 */
	protected boolean isDuplicateBEResource(IResource entity,
			String resourceName, StringBuilder duplicateFileName) {
		return StudioResourceUtils.isDuplicateBEResource(entity, resourceName, duplicateFileName);
	}
	
	public boolean canFinish() {
		return getContainer().getCurrentPage().isPageComplete();
	}

	/**
	 * @param projectName
	 * @return
	 */
	private IProject getProject(String projectName) {
		return CommonUtil.getStudioProject(projectName);
	}


}
