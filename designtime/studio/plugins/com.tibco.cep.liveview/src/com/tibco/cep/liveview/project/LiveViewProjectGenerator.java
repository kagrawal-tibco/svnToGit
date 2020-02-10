/**
 * 
 */
package com.tibco.cep.liveview.project;

import static com.tibco.cep.liveview.project.LVConfigConstants.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;

import com.streambase.sb.DataType;
import com.tibco.be.util.config.ConfigNS.Elements;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.event.Event;
import com.tibco.cep.studio.core.StudioCorePlugin;
import com.tibco.cep.studio.core.clusterconfig.ClusterConfigProjectUtils;
import com.tibco.cep.studio.core.index.model.EntityElement;

/**
 * @author vpatil
 *
 */
public class LiveViewProjectGenerator implements ILiveViewProjectGenerator {
	private Map<String, String> uriToField;
	private Map<String, LVConfig> uriToLVConfig;
	
	public LiveViewProjectGenerator() {
		super();
		
		uriToField = new HashMap<String, String>();
		uriToLVConfig = new HashMap<String, LVConfig>();
	}
	
	public void generateLVConfigFiles(Map<String, Map<String,String>> entityUriToTrimmingMap, IProject project, String outputPath, boolean isSharedNothing, Map<String, Map<String, String>> overrideModelMap) {
		uriToField.clear();
		uriToLVConfig.clear();
		
		File lvProjectFolder = createLVProjectFolder(outputPath, project.getName());
		
		for (String entityUri : entityUriToTrimmingMap.keySet()) {
			EntityElement selectedEntity = ClusterConfigProjectUtils.getEntityElementForPath(project, entityUri);
			Entity entity = selectedEntity.getEntity();
			LVConfig lvConf = createLVConfig(entity, isSharedNothing, overrideModelMap.get(entity.getFullPath()));
			
			addTrimmingRules(lvConf, entityUriToTrimmingMap.get(entityUri));
			
			uriToLVConfig.put(entity.getFullPath(), lvConf);
		}
		
		for (LVConfig lvConfig : uriToLVConfig.values()) {
			File lvConfigFile = new File(lvProjectFolder, lvConfig.getName() + "." + LV_CONFIG_FILE_EXTN);
			writeToFile(lvConfig.serialize(), lvConfigFile);
		}
	}
	
	private File createLVProjectFolder(String outputPath, String projectName) {
		File lvProjectFolder = null;
		File outputFolder = new File(outputPath);
		if (outputFolder.exists() && outputFolder.isDirectory()) {
			lvProjectFolder = new File(outputFolder, projectName);
			if (!lvProjectFolder.exists()) {
				lvProjectFolder.mkdirs();
			}
		} else {
			StudioCorePlugin.log(new RuntimeException(String.format("%s either does not exist or is not a directory", outputPath)));
		}
		return lvProjectFolder;
	}
	
	private LVConfig createLVConfig(Entity entity, boolean isSN, Map<String, String> overrideModelMap) {
		LVConfig lvConf = new LVConfig(getLVConfigName(entity.getFullPath()));
		
		List<PropertyDefinition> pdList = getEntityProperties(entity);
		for (PropertyDefinition pd : pdList) {
			String type = getLVFieldType(pd.getType(), pd.isArray(), pd.getConceptTypePath());
			lvConf.addField(lvConf.new Field(pd.getName(), type));
		}
		
		// add System Fields
		lvConf.addFields(getSystemFields(entity.getFullPath(), (entity instanceof Event)));
		
		lvConf.setPrimaryKey(LV_BE_SYSTEM_FIELD_ID);
		
		// Add indexes
		lvConf.setIndexes(getIndexes(overrideModelMap, isSN));
		
		return lvConf;
	}
	
	private String getLVConfigName(String entityPath) {
		return entityPath.substring(1).replace("/", "_");
	}
	
	private String getLVFieldType(PROPERTY_TYPES type, boolean isArray, String conceptOrRefPath) {
		String fieldType = null;
		if (isArray) {
			fieldType = DataType.STRING.getName();
			switch(type) {
			case CONCEPT:
				setReferenceFields(LV_BE_SYSTEM_FIELD_PARENT, conceptOrRefPath);
				break;
			case CONCEPT_REFERENCE:
				setReferenceFields(LV_BE_SYSTEM_FIELD_REVERSE_REF, conceptOrRefPath);
				break;
			}
		} else {
			switch(type) {
			case STRING: fieldType = DataType.STRING.getName(); break;
			case INTEGER: fieldType = DataType.INT.getName(); break;
			case DOUBLE: fieldType = DataType.DOUBLE.getName(); break;
			case BOOLEAN: fieldType = DataType.BOOL.getName(); break;
			case LONG: fieldType = DataType.LONG.getName(); break;
			case DATE_TIME: fieldType = DataType.TIMESTAMP.getName(); break;
			case CONCEPT:
				fieldType = DataType.LONG.getName();
				setReferenceFields(LV_BE_SYSTEM_FIELD_PARENT, conceptOrRefPath);
				break;
			case CONCEPT_REFERENCE:
				fieldType = DataType.LONG.getName();
				setReferenceFields(LV_BE_SYSTEM_FIELD_REVERSE_REF, conceptOrRefPath);
				break;
			default:{
				StudioCorePlugin.log(new RuntimeException(String.format("%s is not supported.", type.name())));
			}
			}
		}
		return fieldType;
	}
	
	private void setReferenceFields(String propertyName, String conceptOrReferenceType) {
		if (propertyName != null) {
			LVConfig lvConfig = uriToLVConfig.get(conceptOrReferenceType);
			if (lvConfig != null) {
				String propertyType = (propertyName.equals(LV_BE_SYSTEM_FIELD_PARENT)) ? DataType.LONG.getName() : DataType.STRING.getName();
				lvConfig.addField(lvConfig.new Field(propertyName, propertyType));
			} else {
				String field = uriToField.get(conceptOrReferenceType);
				if (field != null && !field.isEmpty()) propertyName += "," + field;
				uriToField.put(conceptOrReferenceType, propertyName);
			}
		}
	}
	
	private List<LVConfig.Field> getSystemFields(String entityURI, boolean isEvent) {
		List<LVConfig.Field> fields = new ArrayList<LVConfig.Field>();
		LVConfig lvConfig = new LVConfig("");
		
		fields.add(lvConfig.new Field(LV_BE_SYSTEM_FIELD_ID, DataType.LONG.getName()));
		fields.add(lvConfig.new Field(LV_BE_SYSTEM_FIELD_EXTID, DataType.STRING.getName()));
		fields.add(lvConfig.new Field(LV_BE_SYSTEM_FIELD_VERSION, DataType.INT.getName()));
		fields.add(lvConfig.new Field(LV_BE_SYSTEM_FIELD_TYPEID, DataType.INT.getName()));
		
		if (isEvent) {
			fields.add(lvConfig.new Field(LV_BE_SYSTEM_FIELD_PAYLOAD, DataType.BLOB.getName()));
		} else {
			fields.add(lvConfig.new Field(LV_BE_SYSTEM_FIELD_DELETED, DataType.BOOL.getName()));
		}
		
		
		String fieldNames = uriToField.get(entityURI);
		if (null != fieldNames && !fieldNames.isEmpty()) {
			String[] fieldList = fieldNames.split(",");
			for (String field : fieldList) {
				String fieldType = (field.equals(LV_BE_SYSTEM_FIELD_PARENT)) ? DataType.LONG.getName() : DataType.STRING.getName();
				fields.add(lvConfig.new Field(field, fieldType));
			}
		}
		
		return fields;
	}
	
	private List<String> getIndexes(Map<String, String> entityProperties, boolean isSN) {
		List<String> indexes = new ArrayList<String>();
		
		// for now Concept/Event or persistence modes set an index on ExtId
		
//		if (isSN) {
			indexes.add(LV_BE_SYSTEM_FIELD_EXTID);
//		}
		if (entityProperties != null) {
			for (String key : entityProperties.keySet()) {
				String props = entityProperties.get(key);
				if (props.indexOf(Elements.INDEX.localName) > -1) {
					indexes.add(key);
				}
			}
		}
		return indexes;
	}
	
	private void writeToFile(String fileContent, File file) {
		FileWriter fWriter = null;
		try {
			fWriter = new FileWriter(file);
			fWriter.write(fileContent);
		} catch(IOException ioe) {
			StudioCorePlugin.log(ioe);
		} finally {
			try {
				fWriter.close();
			} catch (IOException e) {
				StudioCorePlugin.log(e);
			}
		}
	}
	
	private List<PropertyDefinition> getEntityProperties(Entity entity) {
		List<PropertyDefinition> pdList = (entity instanceof Concept) ? 
				((((Concept)entity).getSuperConcept() == null) ? 
						((Concept)entity).getProperties() : ((Concept)entity).getAllProperties()) :
				((((Event)entity).getSuperEvent() == null) ? 
						((Event)entity).getProperties() : ((Event)entity).getAllUserProperties());
		
		return pdList;
	}
	
	private void addTrimmingRules(LVConfig lvConfig, Map<String, String> trimmingMap) {
		if (trimmingMap != null && lvConfig != null) {
			String mapValue = trimmingMap.get(Elements.ENABLE_TRIMMING.localName);
			if (mapValue != null) {
				lvConfig.setEnableTableTrimming(Boolean.valueOf(mapValue));
			}
			lvConfig.setTableDeleteRule(trimmingMap.get(Elements.TRIMMING_RULE.localName));
			lvConfig.setTrimmingField(trimmingMap.get(Elements.TRIMMING_FIELD.localName));
		}
	}
	
}
