package com.tibco.cep.studio.tester.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractTestDataExportHandler {
	
    public static final String TEST_DATA_HEADER 		= "TEST DATA";
	public static final String TEST_DATA_PROJECT_NAME 	= "PROJECT NAME: ";
	public static final String TEST_DATA_NAME 			= "NAME: ";
	public static final String TEST_DATA_TYPE 			= "TYPE: ";
	public static final String TEST_DATA_FOLDER 		= "FOLDER: ";
	public static final String TEST_DATA_EXT_ID 		= "ExtId";
	public static final String TEST_DATA_PAYLOAD 		= "Payload";
	public static final String TEST_DATA_SELECT 		= "Use";
	
	protected String 		rootPath = null;
	protected String 		projectName;
	protected String 		projectPath;
	protected List<Entity> 	entityList;
		
	public AbstractTestDataExportHandler( String projectName,
									   String projectPath,
									   String outputRootPath,
									   List<Entity> entities) {
		this.projectPath	= projectPath;
		this.rootPath 		= outputRootPath;
		this.entityList 	= entities;
		this.projectName 	= projectName;
	}
	
	public final void execute() {
		for (Entity en: entityList) {
			traverseEntity(en);
		}
 	}
	
	private void traverseEntity(Entity entity) {
		List<PropertyDefinition> propertyDefitionList = new ArrayList<PropertyDefinition>();
		if (entity instanceof Concept) {
			Concept con = (Concept)entity;
			collectProperties(con, propertyDefitionList);			
						
		} else if (entity instanceof Event) {
			Event event = (Event)entity;
		//	propertyDefitionList = event.getAllUserProperties();     //Somehow getAllUserProperties() is not giving all the properties. So get using below method.
			collectProperties(event,propertyDefitionList);
			
		}
		try {
			exportTestData(entity, propertyDefitionList);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void collectProperties(Concept concept, List<PropertyDefinition> properties) {
		
		EObject superObject = readEObjectArgument(new File(projectPath), concept.getSuperConceptPath(), ".concept");
		Concept superConcept = (Concept)superObject;
	
		if (superConcept != null){
			collectProperties(superConcept, properties);
		}
		
		// add the local properties *after* the super properties
		properties.addAll(concept.getProperties());
	}
	
	private void collectProperties(Event event, List<PropertyDefinition> properties) {
		
		EObject superObject = readEObjectArgument(new File(projectPath), event.getSuperEventPath(), ".event");
		Event superEvent = (Event)superObject;
	
		if (superEvent != null){
			collectProperties(superEvent, properties);
		}
		
		// add the local properties *after* the super properties
		properties.addAll(event.getProperties());
	}
	
	private EObject readEObjectArgument(File projectRootDir, String argumentPath, String extension) {
		File argumentFullPath = new File(projectRootDir.getAbsolutePath(), argumentPath + extension);
		EObject argument = null;
		if (argumentFullPath.exists()) {
			try {
				argument = CommonIndexUtils.deserializeEObject(new FileInputStream(argumentFullPath));
			} catch (Exception e) {
				StudioCorePlugin.log(e);
			}
		}
		return argument;
	}

	
	/**
	 * Export the {@link Entity} to excel file
	 * @param saveWorkbook -> If true persist the workbook.
	 * @return {@link ExportTestData} containing position info of exported test data.
	 * @throws IOException
	 */
	public abstract ExportTestData exportTestData(Entity entity, List<PropertyDefinition> propertyDefitionList) throws IOException;
	
}
