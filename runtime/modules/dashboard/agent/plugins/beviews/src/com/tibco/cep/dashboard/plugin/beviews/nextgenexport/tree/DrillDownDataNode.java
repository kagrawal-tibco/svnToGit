package com.tibco.cep.dashboard.plugin.beviews.nextgenexport.tree;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.FieldValue;
import com.tibco.cep.dashboard.common.data.FieldValueArray;

public class DrillDownDataNode {

	public static enum KIND {
		INSTANCE, TYPE, GROUP_BY
	}

	private String identifier;

	private String name;

	private KIND type;

	private String entityID;

	private boolean dynamicData;

	private Map<String, DrillDownDataNodeValue> values;

	private List<DrillDownDataNode> children;

	public DrillDownDataNode(String identifier, String name) {
		super();
		this.identifier = identifier;
		this.name = name;
		values = new LinkedHashMap<String, DrillDownDataNode.DrillDownDataNodeValue>();
		children = new LinkedList<DrillDownDataNode>();
	}

	public String getIdentifier() {
		return identifier;
	}

	public String getName() {
		return name;
	}

	public void setKind(KIND type) {
		this.type = type;
	}

	public KIND getKind() {
		return type;
	}

	public void setEntityID(String entityID) {
		this.entityID = entityID;
	}

	public String getEntityID() {
		return entityID;
	}

	public void setDynamicData(boolean dynamicData) {
		this.dynamicData = dynamicData;
	}

	public boolean isDynamicData() {
		return dynamicData;
	}

	public void addField(String fieldName, String fieldDisplayName, FieldValue fieldValue, String formattedValue, boolean isSystemField) {
		values.put(fieldName, new DrillDownDataNodeValue(fieldDisplayName, isSystemField, fieldValue, formattedValue));
	}

	public Iterator<String> getFieldNames(final boolean includeSystemFields) {
		return new Iterator<String>() {

			Iterator<String> rawFieldNameIterator = values.keySet().iterator();

			private String next = fetchNext(includeSystemFields);

			@Override
			public boolean hasNext() {
				return next != null;
			}

			private String fetchNext(final boolean includeSystemFields) {
				if (includeSystemFields == true) {
					if (rawFieldNameIterator.hasNext() == true) {
						return rawFieldNameIterator.next();
					}
				} else {
					while (rawFieldNameIterator.hasNext() == true) {
						String fieldName = rawFieldNameIterator.next();
						if (values.get(fieldName).isSystem == false) {
							return fieldName;
						}
					}
				}
				return null;
			}

			@Override
			public String next() {
				String temp = next;
				next = fetchNext(includeSystemFields);
				return temp;
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException("remove");
			}

		};
	}

	public String getDisplayName(String fieldName) {
		return values.get(fieldName).displayName;
	}

	public FieldValue getValue(String fieldName) {
		return values.get(fieldName).value;
	}

	public String getFormattedValue(String fieldName) {
		return values.get(fieldName).formattedValue;
	}

	public boolean isArray(String fieldName) {
		return getValue(fieldName) instanceof FieldValueArray;
	}

	public boolean isSystemField(String fieldName) {
		return values.get(fieldName).isSystem;
	}

	public boolean hasValues() {
		return values.isEmpty() == false;
	}

	public void addChild(DrillDownDataNode child) {
		children.add(child);
	}

	public boolean removeChild(DrillDownDataNode child) {
		return children.remove(child);
	}

	public void removeAllChildren() {
		children.clear();
	}

	public List<DrillDownDataNode> getChildren() {
		return Collections.unmodifiableList(children);
	}

	private class DrillDownDataNodeValue {

		String displayName;

		boolean isSystem;

		FieldValue value;

		String formattedValue;

		DrillDownDataNodeValue(String displayName, boolean isSystem, FieldValue value, String formattedValue) {
			super();
			this.displayName = displayName;
			this.isSystem = isSystem;
			this.value = value;
			this.formattedValue = formattedValue;
		}

	}
}
