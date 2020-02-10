/**
 * 
 */
package com.tibco.cep.decision.table.spi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.EOFRecord;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.record.FormatRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.provider.excel.ExcelImportListener;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.studio.common.legacy.adapters.RuleFunctionModelTransformer;
import com.tibco.cep.studio.core.api.IndexOps;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * Contains basic API set required for XL to DT conversion.
 * @author aathalye
 *
 */
public class ExcelConversionOps {
	
    static {
        com.tibco.cep.Bootstrap.ensureBootstrapped();
    }
    


	/**
	 * 
	 * @param fin
	 * @param poifs
	 * @param din
	 * @param req
	 * @throws Exception
	 */
	public static void processWorkbookEvents(FileInputStream fin,
			                                 POIFSFileSystem poifs,
			                                 InputStream din,
			                                 HSSFRequest req) throws Exception {
		HSSFEventFactory factory = null;
		try {
			factory = new HSSFEventFactory();
			/*
			 * process our events based on the document input stream
			 */
			factory.processWorkbookEvents(req, poifs);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fin.close();
			din.close();
		}
	}
	
	/**
	 * Register listeners 
	 * @param <T>
	 * @param req
	 * @param listener
	 */
	public static <T extends HSSFListener> void registerRecordListeners(HSSFRequest req, T listener) {
		req.addListener(listener, BOFRecord.sid);
		req.addListener(listener, SSTRecord.sid);
		req.addListener(listener, LabelSSTRecord.sid);
		req.addListener(listener, NumberRecord.sid);
		req.addListener(listener, FormatRecord.sid);
		req.addListener(listener, ExtendedFormatRecord.sid);
		req.addListener(listener, BlankRecord.sid);
		req.addListener(listener, EOFRecord.sid);
		req.addListener(listener, BoolErrRecord.sid);
		req.addListener(listener, FormulaRecord.sid);
		// added for sheet name
		req.addListener(listener, BoundSheetRecord.sid);
	}
	
	/**
	 * Take following parameters and convert an xl file to {@link Table}.
	 * <p>
	 * The returned object has attributes like <b>name</b>, <b>folder</b>,
	 * and <b>implements</b> populated.
	 * @param projectRootDirPath
	 * @param vrfTemplate
	 * @param xlFileAbsolutePath
	 * @param dtFolderPath
	 * @param dtName
	 * @param errorCollection
	 * @return
	 * @throws Exception
	 */
	public static Table convertToDecisionTable(String projectDirPath,
			                                   AbstractResource vrfTemplate,
			                                   String xlFileAbsolutePath,
			                                   String dtFolderPath,
			                                   String dtName,
			                                   Collection<ValidationError> errorCollection) throws Exception {
		System.out.printf("Beginning conversion of Excel File %s...", xlFileAbsolutePath);
		System.out.println();
		FileInputStream fin = new FileInputStream(xlFileAbsolutePath);
		POIFSFileSystem poifs = new POIFSFileSystem(fin);
		InputStream din = poifs.createDocumentInputStream("Workbook");
		//Request object is need for event model system
		HSSFRequest hssfRequest = new HSSFRequest();
		File projectFile = new File(projectDirPath);
		String projectName = projectFile.getName();

		//Create listener and register it
		ExcelImportListener excelImportListener = new ExcelImportListener(projectName, 
					                                                      vrfTemplate, 
					                                                      errorCollection, 
					                                                      true, new HSSFWorkbook(poifs));
		registerRecordListeners(hssfRequest, excelImportListener);
		processWorkbookEvents(fin, poifs, din, hssfRequest);
		excelImportListener.getExcelVldErrorCollection();
		RuleFunction virtualRuleFunction = (RuleFunction)vrfTemplate;
		//Get generated Table
		Table tableEModel = excelImportListener.getTableEModel();
		if (tableEModel != null) {
			System.out.println();
			System.out.println("Conversion completed");
			tableEModel.setName(dtName);
			tableEModel.setImplements(virtualRuleFunction.getPath());
			//tableEModel.setModified(true);
			if (!dtFolderPath.endsWith("/")) {
				dtFolderPath = dtFolderPath + "/";
			}
			if(dtFolderPath.contains("\\") || dtFolderPath.contains("//")){
				dtFolderPath = dtFolderPath.replace("\\", "/");
				dtFolderPath = dtFolderPath.replaceAll("//", "/");
			}
			
			if(!dtFolderPath.startsWith("/")){
				dtFolderPath = "/" + dtFolderPath;
			}	
			
			tableEModel.setFolder(dtFolderPath);
		}
		//Return it
		return tableEModel;
	}
	
	/**
	 * Take following parameters and convert an xl file to {@link Table}.
	 * <p>
	 * The returned object has attributes like <b>name</b>, <b>folder</b>,
	 * and <b>implements</b> populated.
	 * @param projectDirPath
	 * @param workspacePath (optional - if omitted will load from disk)
	 * @param vrfTemplatePath
	 * @param xlFileAbsolutePath
	 * @param dtFolderPath
	 * @param dtName
	 * @param errorCollection
	 * @return
	 * @throws Exception
	 */
	public static Table convertToDecisionTable(String projectDirPath,
											   String workspacePath,
			                                   String vrfTemplatePath,
									           String xlFileAbsolutePath,
									           String dtFolderPath,
									           String dtName,
									           Collection<ValidationError> errorCollection) throws Exception {
		//Load index upfront
		if (workspacePath != null) {
			IndexOps.loadIndex(workspacePath, projectDirPath);
		} else {
			IndexOps.loadIndex(projectDirPath);
		}
		RuleFunction vrfTemplate = readVRF(projectDirPath, vrfTemplatePath);
		return convertToDecisionTable(projectDirPath, vrfTemplate, xlFileAbsolutePath, dtFolderPath, dtName, errorCollection);
	}
	
	/**
	 * Read the virtual rule function {@link com.tibco.cep.designtime.core.model.rule.RuleFunction} object
	 * and transform it to a {@link RuleFunction} object.
	 * <p>
	 * <b>
	 * This requires the index for the project to be loaded.
	 * </b>
	 * </p> 
	 * @param projectDirPath
	 * @param vrfPath
	 * @return
	 */
	private static RuleFunction readVRF(String projectDirPath,
			                            String vrfPath) {
		System.out.printf("Attempting to load Virtual Rule Function %s" , vrfPath);
		File projectFile = new File(projectDirPath);
		String projectName = projectFile.getName();
		RuleElement virtualRuleFunctionElement = 
				(RuleElement)CommonIndexUtils.getElement(projectName, vrfPath);
		System.out.println();
		if (virtualRuleFunctionElement == null) {
			throw new RuntimeException("Virtual Rule Function " + vrfPath + " could not be loaded");
		}
		RuleFunction template = OntologyFactory.eINSTANCE.createRuleFunction();
		new RuleFunctionModelTransformer().
			transform((com.tibco.cep.designtime.core.model.rule.RuleFunction)virtualRuleFunctionElement.getRule(), (RuleFunction)template);
		System.out.println("Completed Loading Virtual Rule Function");
		return template;
	}
	
	public static void main(String[] r) throws Exception {
		String studioProjectPath = "E:/Eclipse-Workspaces/4.0HFStudio/CreditCardApplication";
		String projectWorkspacePath = "E:/Eclipse-Workspaces/4.0HFStudio";
		String vrfPath = "/Virtual_RF/BankUser_VirtualRuleFunction";
		String excelFilePath = "E:/SVN/BE/4.0/examples/decision_manager/CreditCardApplication/ExcelFiles/BankUser_LinkedToDomainModel.xls";
		String dtFolderPath = "/Virtual_RF";
		String dtName = "TestDT";
		convertToDecisionTable(studioProjectPath, null, vrfPath, excelFilePath, dtFolderPath, dtName, null);
	}
}
