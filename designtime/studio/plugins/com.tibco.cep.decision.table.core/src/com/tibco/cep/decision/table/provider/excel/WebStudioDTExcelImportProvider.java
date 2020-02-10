package com.tibco.cep.decision.table.provider.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.emf.common.util.EList;

import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.spi.ExcelConversionOps;
import com.tibco.cep.decisionproject.acl.ValidationError;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.studio.common.legacy.adapters.RuleFunctionModelTransformer;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

public class WebStudioDTExcelImportProvider {

	private String projectName = null;
	private String vrfPath = null;
	private com.tibco.cep.decisionproject.ontology.RuleFunction rulefunction = null;
	private String dtName = null;
	private List<String> errors = null;
	
	public WebStudioDTExcelImportProvider(String projectName, String vrfPath, RuleFunction rulefunction, String dtName) {
		this.projectName = projectName;
		this.vrfPath = vrfPath;
		this.dtName = dtName;
		transformRuleFunction(rulefunction);
		errors = new ArrayList<String>();
	}
	
	public Table importDT(InputStream is) throws IOException {
		Table tableEModel = null;
		POIFSFileSystem poifs = new POIFSFileSystem(is);
		InputStream din = poifs.createDocumentInputStream("Workbook");
		HSSFRequest req = new HSSFRequest();
		ExcelImportListener excelImportListener = new ExcelImportListener(projectName, rulefunction, false, true, new HSSFWorkbook(poifs));
		ExcelConversionOps.registerRecordListeners(req, excelImportListener);
		HSSFEventFactory factory = new HSSFEventFactory();
		factory.processWorkbookEvents(req, poifs);
		Collection<ValidationError> errorCollection = excelImportListener.getExcelVldErrorCollection();
		
		if (!errorCollection.isEmpty()) {
			for (ValidationError ve : errorCollection) {
				errors.add(ve.getErrorMessage());
			}
		}
		
		tableEModel = excelImportListener.getTableEModel();
		tableEModel.setName(dtName);
		tableEModel.setImplements(vrfPath);
		tableEModel.setVersion(0.0);
		//tableEModel.setModified(true);
		
		String folderPath = vrfPath;
		int folderIndx = vrfPath.lastIndexOf(CommonIndexUtils.PATH_SEPARATOR);
		if (folderIndx != -1) {
			folderPath = vrfPath.substring(0, folderIndx + 1);
		}
		tableEModel.setFolder(folderPath.toString().trim());
//		tableEModel.setShowDescription(showDescription);

		if (tableEModel.getMd() == null) {
			MetaData metaData = DtmodelFactory.eINSTANCE.createMetaData();
			EList<Property> property = metaData.getProp();
			Property effDateProperty = DtmodelFactory.eINSTANCE.createProperty();
			effDateProperty.setName("EffectiveDate");
			effDateProperty.setType("DateTime");
			effDateProperty.setValue("");
			Property expDateProperty = DtmodelFactory.eINSTANCE.createProperty();
			expDateProperty.setName("ExpiryDate");
			expDateProperty.setType("DateTime");
			expDateProperty.setValue("");
			property.add(effDateProperty);
			property.add(expDateProperty);

			//Added SingleRowExecution boolean property
			Property singleRowExecutionProperty = DtmodelFactory.eINSTANCE.createProperty();
			singleRowExecutionProperty.setName("SingleRowExecution");
			singleRowExecutionProperty.setType("Boolean");
			singleRowExecutionProperty.setValue("false");
			property.add(singleRowExecutionProperty);

			tableEModel.setMd(metaData);
		}
		
		return tableEModel;
	}
	
	private void transformRuleFunction(RuleFunction vrf) {
		rulefunction = OntologyFactory.eINSTANCE.createRuleFunction();
		new RuleFunctionModelTransformer().transform(vrf, (com.tibco.cep.decisionproject.ontology.RuleFunction) rulefunction);
	}
	
	public List<String> getValidationErrors() {
		return Collections.unmodifiableList(errors);
	}
}
