package com.tibco.cep.studio.tester.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;

/**
 * 
 * @author sasahoo
 *
 */
public class TestDataCsvExportHandler  extends AbstractTestDataExportHandler{
	
	String columnSeparator;
	String NEWLINE="\n";
	String EXTENTION=".csv";
	
    public TestDataCsvExportHandler( String projectName,
									   String projectPath,
									   String outputRootPath,
									   String columnSeparator,
									   List<Entity> entities) {
    	super(projectName, projectPath, outputRootPath, entities);
		this.columnSeparator = columnSeparator;
	}

	@Override
	public ExportTestData exportTestData(Entity entity,
			List<PropertyDefinition> propertyDefitionList) throws IOException {
		
		ExportTestData exportTestData = new ExportTestData();
		exportTestData.setTestDataName(entity.getName());
		String csvContent = createTestDataHeader(entity) + createTestDataSubHeaders(entity,propertyDefitionList);
		write(entity, csvContent); 
		return exportTestData;
	}
	
	private String createTestDataHeader(Entity entity) {
		StringBuilder testDataHeader  = new StringBuilder();
		testDataHeader.append(TEST_DATA_HEADER);
		testDataHeader.append(NEWLINE);
		testDataHeader.append(TEST_DATA_PROJECT_NAME + projectName);
		testDataHeader.append(NEWLINE);
		testDataHeader.append(TEST_DATA_NAME + entity.getName());
		testDataHeader.append(NEWLINE);
		String testDataType = TEST_DATA_TYPE;
		if (entity instanceof Concept) {
			if (((Concept)entity).isScorecard()){
				testDataType = testDataType + "Scorecard";
			}
			else{
				testDataType = testDataType + "Concept";
			}
		}
		else if (entity instanceof Event) {
			testDataType = testDataType + "Event";
		}
		testDataHeader.append(testDataType);
		testDataHeader.append(NEWLINE);
		
		testDataHeader.append(TEST_DATA_FOLDER + entity.getFolder());
		testDataHeader.append(NEWLINE);
		return testDataHeader.toString();
	}
	
	private String createTestDataSubHeaders(Entity entity, List<PropertyDefinition> propertyDefitionList) {
		
		StringBuilder testDataSubHeader  = new StringBuilder();
		
		testDataSubHeader.append(TEST_DATA_SELECT+" (boolean)");
		if(entity instanceof Event){
			Event event = (Event)entity;
	//		if (event.getPayloadString() != null && !event.getPayloadString().isEmpty()) {
				testDataSubHeader.append(this.columnSeparator);
				testDataSubHeader.append(TEST_DATA_PAYLOAD+" (String)");
	//		}
		}
		testDataSubHeader.append(this.columnSeparator);
		testDataSubHeader.append(TEST_DATA_EXT_ID+" (String)");
		
		for (PropertyDefinition propDef : propertyDefitionList) {
			String columnHeader = propDef.getName();
			if(propDef.getType().equals(PROPERTY_TYPES.CONCEPT)){
				columnHeader = columnHeader + " (" +propDef.getConceptTypePath()+")" ;
			}else if (propDef.getType().equals(PROPERTY_TYPES.CONCEPT_REFERENCE)){
				columnHeader = columnHeader + " (" +propDef.getConceptTypePath()+")" ;
			}else{
				columnHeader = columnHeader + " (" +propDef.getType()+")" ;
			}
			if(propDef.isArray()){
				columnHeader=columnHeader+ " [M]";
			}
			testDataSubHeader.append(this.columnSeparator);
			testDataSubHeader.append(columnHeader);
		}
		testDataSubHeader.append(NEWLINE);
		return testDataSubHeader.toString();
		
	}
	
	private void write(Entity entity,String content) throws IOException {
		String outputDirPath = rootPath + entity.getFolder();
		File dir = new File(outputDirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String path  = outputDirPath + entity.getName() + EXTENTION;
		File f = new File(path);
		if (!f.exists()) {
			f.createNewFile();
		} else {
			f.delete();
		}
		FileOutputStream fos = new FileOutputStream(outputDirPath + entity.getName() + EXTENTION);
		fos.write(content.getBytes());
		fos.close();
	}

}
