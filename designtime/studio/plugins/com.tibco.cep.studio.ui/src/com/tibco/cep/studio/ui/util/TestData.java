/**
 * 
 */
package com.tibco.cep.studio.ui.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.event.Event;

/**
 * @author mgujrath
 *
 */
public class TestData {

	private String entityInfo;

	private List<List<String>> testData;

	private List<Boolean> selectRowData = new ArrayList<Boolean>();

	private Vector<Vector> dataVectors = new Vector<Vector>();

	private List<LinkedHashMap<String, String>> input = new ArrayList<LinkedHashMap<String, String>>();

	private Vector<Vector> appendedVectors = new Vector<Vector>();

	private List<String> tableColumnNames = new ArrayList<String>();

	private Entity entity;

	public TestData(Entity entity, List<String> tableColumnNames,
			List<List<String>> testData) {
		this.entity = entity;
		this.tableColumnNames = tableColumnNames;
		this.testData = testData;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public List<String> getTableColumnNames() {
		return tableColumnNames;
	}

	public void setTableColumnNames(ArrayList<String> tableColumnNames) {
		this.tableColumnNames = tableColumnNames;
	}

	public Vector<Vector> getVectors() {
		return dataVectors;
	}

	public Vector<Vector> getAppendedVectors() {
		return appendedVectors;
	}

	public void setAppendedVectors(Vector<Vector> appendedVectors) {
		this.appendedVectors = appendedVectors;
	}

	public void setVectors(Vector<Vector> vectors) {
		this.dataVectors = vectors;
	}

	public List<LinkedHashMap<String, String>> getInput() {
		return input;
	}

	public void setInput(List<LinkedHashMap<String, String>> list) {
		this.input = list;
	}

	public String getEntityInfo() {
		return entityInfo;
	}

	public void setEntityInfo(String entityInfo) {
		this.entityInfo = entityInfo;
	}

	public List<List<String>> getTestData() {
		return testData;
	}

	public void setTestData(List<List<String>> list) {
		this.testData = list;
	}

	public List<Boolean> getSelectRowData() {
		return selectRowData;
	}

	public void setSelectRowData(List<Boolean> list) {
		this.selectRowData = list;
	}

	public void generateVectorforCLI() {
		for (int i = 0; i < this.getTestData().size(); i++) {
			List<String> list = this.getTestData().get(i);
			LinkedHashMap<String, String> lhm = new LinkedHashMap<String, String>();
			this.getTableColumnNames();
			for (int j = 1; j < this.getTableColumnNames().size(); j++) {
				lhm.put(this.getTableColumnNames().get(j), list.get(j - 1));
			}
			Vector<String> dataVector = new Vector<String>();
			dataVector.add(this.getEntity().getFullPath());
			if (this.getEntity() instanceof Event) {
				dataVector.add(this.getTestData().get(i).get(1));
			}
			dataVector.add(this.getTestData().get(i).get(0));
			Set<String> keySet = lhm.keySet();
			for (String str : keySet) {
				dataVector.add(lhm.get(str).toString());
			}
			this.getVectors().add(dataVector);

		}
	}
}
