package com.tibco.cep.studio.decision.table.ui.constraintpane;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.tester.core.model.TestDataModel;

public class DecisionTableTestDataCoverageUtils {

	public static PropertyDefinition getConceptPropertyDefinition(TestDataModel testDataModel, String testDataColumnName) {
		PropertyDefinition property = null;
		Entity entity = testDataModel.getEntity();
		EList<PropertyDefinition> properties = null;
		if (entity instanceof Concept) {
			Concept concept = (Concept) entity;
			properties = concept.getAllPropertyDefinitions();
			Iterator<PropertyDefinition> itrProp = properties.iterator();
			while (itrProp.hasNext()) {
				PropertyDefinition prop = itrProp.next();
				if (prop.getName().equals(testDataColumnName)) {					
					property = prop;
					break;
				}
			}
		}
		
		return property;
	}
	
	public static boolean isContainedOrReferenceConcept(PropertyDefinition property, String testDataColumnName) {
		boolean isContainedOrReferenceConcept = false;
		if (property != null && property.getName().equals(testDataColumnName) 
					&& (property.getType().equals(PROPERTY_TYPES.CONCEPT_REFERENCE) || property.getType().equals(PROPERTY_TYPES.CONCEPT))) {
			isContainedOrReferenceConcept = true;
		}
		return isContainedOrReferenceConcept;
	}
	
	public static List<List<String>> getSelectedTestData(TestDataModel testDataModel) {
		List<List<String>> testData = testDataModel.getTestData();
		List<List<String>> selectedTestData = new ArrayList<List<String>>();
		List<Boolean> rowSelection = testDataModel.getSelectRowData();
		for (int row = 0; row < testData.size(); row++) {
			if (rowSelection.get(row))
				selectedTestData.add(testData.get(row));				
		}
		
		return selectedTestData;
	}	
}
