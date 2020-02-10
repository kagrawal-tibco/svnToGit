/**
 * 
 */
package com.tibco.cep.liveview.project;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import com.streambase.sb.DataType;

/**
 * @author vpatil
 *
 */
public class LVConfig {
	private String name;
	private List<Field> fields;
	private String primaryKey;
	private List<String> indexes;
	private String tableDeleteRule;
	private String trimmingField;
	private boolean enableTableTrimming;
	
	public LVConfig(String name) {
		this.name = name;
	}
	
	class Field {
		private String name;
		private String type;
		
		public Field(String name, String type) {
			this.name = name;
			this.type = type;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Field)) return false;
			
			if (obj == this) return true;
			
			if (obj instanceof Field) {
				Field newField = (Field) obj;
				if (newField.name.equals(this.name) && newField.type.equals(this.type)) return true;
			}
			return false;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = ((name == null) ? 0 : name.hashCode()) +
					((type == null) ? 0 : type.hashCode());
			result *= prime;
			return result;
		}
	}
	
	public void addField(Field field) {
		if (fields == null) {
			fields = new ArrayList<Field>();
		}
		if (!fields.contains(field)) fields.add(field);
	}
	
	public void addFields(List<Field> fields) {
		if (this.fields == null) {
			this.fields = new ArrayList<Field>();
		}
		for (Field field : fields) {
			this.addField(field);
		}
	}
	
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setIndexes(List<String> indexes) {
		this.indexes = indexes;
	}

	public String getName() {
		return name;
	}

	public void setEnableTableTrimming(boolean enableTableTrimming) {
		this.enableTableTrimming = enableTableTrimming;
	}

	public void setTableDeleteRule(String tableDeleteRule) {
		this.tableDeleteRule = StringEscapeUtils.escapeXml10(tableDeleteRule);
	}

	public void setTrimmingField(String trimmingField) {
		this.trimmingField = trimmingField;
	}

	public String serialize() {
		StringBuffer lvConfigContent = new StringBuffer();
		
		lvConfigContent.append(LVConfigConstants.LV_XML_PREFIX + "\n");
		lvConfigContent.append(LVConfigConstants.LV_CONFIGURATION_START_TAG + "\n");
		lvConfigContent.append(String.format(LVConfigConstants.LV_DATA_TABLE_START_TAG, name) + "\n");
		lvConfigContent.append(LVConfigConstants.LV_FIELDS_START_TAG + "\n");
		
		for (Field field : fields) {
			lvConfigContent.append(String.format(LVConfigConstants.LV_FIELD_TAG, field.name, field.type) + "\n");
		}
		if (enableTableTrimming && trimmingField != null && !trimmingField.isEmpty()) {
			lvConfigContent.append(String.format(LVConfigConstants.LV_FIELD_TAG, trimmingField, DataType.TIMESTAMP.getName()) + "\n");
		}
		
		lvConfigContent.append(LVConfigConstants.LV_FIELDS_END_TAG + "\n");
		
		// table delete rule
		if (enableTableTrimming && tableDeleteRule != null && !tableDeleteRule.isEmpty()) {
			lvConfigContent.append(String.format(LVConfigConstants.LV_DELETE_RULE_TAG, tableDeleteRule) + "\n");
		}
		
		lvConfigContent.append(LVConfigConstants.LV_PRIMARY_KEY_START_TAG + "\n");
		lvConfigContent.append(String.format(LVConfigConstants.LV_FIELD_REF_TAG, primaryKey) + "\n");
		lvConfigContent.append(LVConfigConstants.LV_PRIMARY_KEY_END_TAG + "\n");
		
		lvConfigContent.append(LVConfigConstants.LV_INDICES_START_TAG + "\n");
		
		for (String index : indexes) {
			lvConfigContent.append(LVConfigConstants.LV_INDEX_START_TAG + "\n");
			lvConfigContent.append(String.format(LVConfigConstants.LV_FIELD_REF_TAG, index) + "\n");
			lvConfigContent.append(LVConfigConstants.LV_INDEX_END_TAG + "\n");
		}
		
		lvConfigContent.append(LVConfigConstants.LV_INDICES_END_TAG + "\n");
		
		
		if (enableTableTrimming && trimmingField != null && !trimmingField.isEmpty()) {
			lvConfigContent.append(String.format(LVConfigConstants.LV_FIELD_RULES_TAG, trimmingField) + "\n");
		}
		
		lvConfigContent.append(LVConfigConstants.LV_DATA_TABLE_END_TAG + "\n");
		lvConfigContent.append(LVConfigConstants.LV_CONFIGURATION_END_TAG);
		
		return lvConfigContent.toString();
	}

}
