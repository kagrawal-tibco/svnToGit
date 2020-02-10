/**
 * 
 */
package com.tibco.cep.studio.core.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.studio.core.domain.exportHandler.DomainModelExcelExportHandler;
import com.tibco.cep.studio.core.domain.importHandler.DomainConfiguration;
import com.tibco.cep.studio.core.domain.importHandler.DomainModelImportHandlerFactory;
import com.tibco.cep.studio.core.domain.importHandler.IDomainModelImportHandler;
import com.tibco.cep.studio.core.domain.importSource.DOMAIN_IMPORT_SOURCES;
import com.tibco.cep.studio.core.domain.importSource.impl.DomainModelExcelDataSource;
import com.tibco.cep.studio.core.execption.DomainModelOpsException;
import com.tibco.cep.studio.core.index.utils.IndexUtils;
import com.tibco.cep.studio.core.validation.ValidationError;
import com.tibco.cep.util.StudioApi;


/**
 * Contains a set of APIs exposed related to operations performed on BE domain models.
 * @see Domain
 * @author aathalye
 *
 */
public class DomainModelOps {
	
	/**
	 * Allows importing a domain model from an excel file
	 * where the file format conforms to the format mandated by BE.
	 * <p>
	 * The {@link Domain} is created inside the project specified by
	 * projectRootDir as a file.
	 * </p>.
	 * @param projectRootDir -> Root directory of the studio project.
	 * @param excelFilePath -> Absolute file path of the Excel to import.
	 * @param domainFolderPath -> The path of the folder relative to project directory inside which to create domain.
	 * @param domainName -> Name of the domain model to create.
	 * @param domainDataType
	 * @param domainDescription -> Description for the newly created domain model.
	 * @param validationErrors -> To capture any validation errors during import.
	 * @throws DomainModelOpsException -> If projectRootDir | excelFilePath | domainName | domainDataType is null or there 
	 * was some exception during the import process.
	 */
	@StudioApi
	public static Domain importDomainModelFromExcel(String projectRootDir,
												    String excelFilePath, 
												    String domainFolderPath,
						                            String domainName,
						                            DOMAIN_DATA_TYPES domainDataType,
						                            String domainDescription,
						                            List<ValidationError> validationErrors) throws DomainModelOpsException {
		if (excelFilePath == null) {
			throw new DomainModelOpsException("Excel File Path cannot be null");
		}
		File excelFile = new File(excelFilePath);
		if (!excelFile.exists() || excelFile.isDirectory() || !excelFile.isAbsolute()) {
			throw new DomainModelOpsException("Excel File Path should be absolute file path.");
		}
		if (domainName == null) {
			throw new DomainModelOpsException("Domain Name cannot be null");
		}
		if (projectRootDir == null) {
			throw new DomainModelOpsException("Project Directory cannot be null");
		}
		if (domainDataType == null) {
			throw new DomainModelOpsException("Project Directory cannot be null");
		}
		if (domainFolderPath == null) {
			//Create at root
			domainFolderPath = "/";
		}
		if (!domainFolderPath.endsWith("/")) {
			//append it
			domainFolderPath = domainFolderPath + "/";
		}
		if (validationErrors == null) {
			validationErrors = new ArrayList<ValidationError>(0);
		}
		registerEPackages();
		
		DomainConfiguration domainConfiguration = new DomainConfiguration();
		domainConfiguration.setDomainDataType(domainDataType);
		domainConfiguration.setDomainName(domainName);
		domainConfiguration.setDomainDescription(domainDescription);
		domainConfiguration.setDomainFolderPath(domainFolderPath);
		domainConfiguration.setProjectDirectoryPath(projectRootDir);
		
		try {
//			DomainModelExcelDataSource domainModelExcelImportSource = DomainModelDataSourceFactory.INSTANCE.getDataSource(DOMAIN_IMPORT_SOURCES.EXCEL, excelFilePath);
			IDomainModelImportHandler<String, DomainModelExcelDataSource> domainModelExcelImportHandler =
				DomainModelImportHandlerFactory.INSTANCE.getImportHandler(DOMAIN_IMPORT_SOURCES.EXCEL, validationErrors, excelFilePath);
			domainModelExcelImportHandler.importDomain(domainConfiguration);
			return domainModelExcelImportHandler.getImportedDomain();
			//Add save logic below in client code.
//			String pathToSave = 
//				new StringBuilder(projectDirectoryPath).
//			                      append("/").
//			                      append(domainFolderPath).
//			                      toString();
		} catch (Exception e) {
			throw new DomainModelOpsException(e);
		}
	}
	
	/**
	 * Allows exporting a {@link Domain} to an excel file.
	 * <p>
	 * Successful execution of this API will create a new Excel File with the
	 * domain model exported to it.
	 * </p>
	 * @param projectRootDir -> The studio project which contains this domain model.
	 * @param projectWorkspacePath -> The studio workspace absolute path.
	 * @param domainModelPath -> The path of domain model relative to project (e.g : /Domains/Domain) 
	 * @param excelFilePath -> Absolute file path of the Excel to export domain to.
	 * @throws DomainModelOpsException If any of the arguments is null or any exception
	 *                         was encountered during export process.
	 */
	@StudioApi
	public static void exportDomainModelToExcel(String projectRootDir,
			                        			String projectWorkspacePath,
			                        			String domainModelPath,
			                        			String excelFilePath) throws DomainModelOpsException {
		if (excelFilePath == null) {
			throw new DomainModelOpsException("Excel File Path cannot be null");
		}
		if (domainModelPath == null) {
			throw new DomainModelOpsException("Domain Model Path cannot be null");
		}
		if (projectRootDir == null) {
			throw new DomainModelOpsException("Project Directory cannot be null");
		}
		if (projectWorkspacePath == null) {
			throw new DomainModelOpsException("Studio or project workspace path cannot be null");
		}
		File projectFile = new File(projectRootDir);
		String projectName = projectFile.getName();
		
		try { 
			IndexOps.loadIndex(projectWorkspacePath, projectRootDir);
			//Load the domain instance
			Domain domainEModel = IndexUtils.getDomain(projectName, domainModelPath);
			if (domainEModel == null) {
				throw new DomainModelOpsException("Domain Model not found at path " + domainModelPath);
			}
			DomainModelExcelExportHandler domainModelExcelExportHandler = 
				new DomainModelExcelExportHandler(projectName, excelFilePath, null, null, domainEModel);
			domainModelExcelExportHandler.exportDomain(true);
		} catch (Exception e) {
			throw new DomainModelOpsException(e);
		}
	}
	
	/**
	 * Register mandatory packages.
	 */
	private static void registerEPackages() {
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		EPackage.Registry.INSTANCE.put("http:///com/tibco/cep/designtime/core/model/designtime_ontology.ecore",
				ModelPackage.eINSTANCE);
	}
	
	public static void main(String[] r) throws Exception {
//		String studioProjectPath = "E:/Eclipse-Workspaces/4.0HFStudio/CreditCardApplication";
//		String projectWorkspacePath = "E:/Eclipse-Workspaces/4.0HFStudio";
//		String excelFilePath = "E:/SVN/BE/4.0/examples/decision_manager/CreditCardApplication/ExcelFiles/BankUser_LinkedToDomainModel.xls";
//		String domainFolder = "/Concepts";
//		String domainName = "TestDM";
//		String description = "Description";
//		importDomainModelFromExcel(studioProjectPath, excelFilePath, domainFolder, domainName, DOMAIN_DATA_TYPES.INTEGER, description, null);
//		exportDomain(studioProjectPath, projectWorkspacePath, domainPath, excelFilePath);
	}
}
