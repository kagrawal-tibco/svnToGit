/**
 * 
 */
package com.tibco.cep.store.util;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.element.ContainedConcept;
import com.tibco.cep.runtime.model.element.Property.PropertyConcept;
import com.tibco.cep.runtime.model.element.Property.PropertyContainedConcept;
import com.tibco.cep.runtime.model.element.PropertyArrayContainedConcept;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.element.impl.property.simple.PropertyAtomContainedConceptSimple;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.store.Item;
import com.tibco.cep.store.functions.StoreFunctions;

/**
 * @author vpatil
 *
 */
public class StoreUtil {
	
	public static void putConcepts(String url, Object entity) throws Exception {
		if (entity instanceof ConceptImpl) {
			ConceptImpl concept = (ConceptImpl) entity;
			
			List<Object> values = new ArrayList<Object>();
			String containerName = null;
			for (PropertyConcept pcc : concept.getContainedConceptProperties()) {
				String propertyName = pcc.getName();

				values.clear();
				if ((pcc instanceof PropertyArrayContainedConcept)) {
					PropertyArrayContainedConcept array = ((PropertyArrayContainedConcept) concept.getPropertyArray(propertyName));
					for (int i=0; i < array.length(); i++) {
						values.add(array.get(i).getValue());
					}
				} else {
					values.add(concept.getPropertyValue(propertyName));
				}
				if (values.size() > 0 && values.get(0) != null) {
					containerName = ((ConceptImpl)values.get(0)).getExpandedName().localName;
	
					StoreFunctions.open(url, containerName);
					for (Object value : values) {
						StoreFunctions.put(url, containerName, value);
					}
	
					StoreFunctions.close(url, containerName);
				}
			}
		}
	}
	
	private static void getConcepts(String url, String containerName, String propertyName, List<Object> values, ConceptImpl targetConcept, boolean isArray) throws Exception {
		boolean clearArray = false;
		ConceptImpl loadedConcept = null;
		for (Object value : values) {
			loadedConcept = (ConceptImpl) StoreFunctions.get(url, containerName, value);
			
			PropertyContainedConcept ccProperty = (PropertyContainedConcept) targetConcept.getProperty(propertyName);
			if (isArray) {
				if (!clearArray) {
					((PropertyArrayContainedConcept)ccProperty).clear();
					clearArray = true;
				}
				((PropertyArrayContainedConcept)ccProperty).add(loadedConcept);
			} else {
				((PropertyAtomContainedConceptSimple) ccProperty).setContainedConcept((ContainedConcept)loadedConcept);
			}
		}
	}
	
	private static void deleteConcepts(String url, String containerName, String propertyName, List<Object> values, boolean isArray) {
		for (Object value : values) {
			StoreFunctions.delete(url, containerName, value);
		}
	}
	
	public static void loadConcepts(String url, Object sourceEntity, Object targetEntity, boolean delete) throws Exception {
		if (sourceEntity instanceof ConceptImpl) {
			ConceptImpl sourceConcept = (ConceptImpl) sourceEntity;
			ConceptImpl targetConcept = (ConceptImpl) targetEntity;
			
			List<Object> values = new ArrayList<Object>();
			String containerName = null;
			boolean isArray = false;
			
			for (PropertyContainedConcept pcc : sourceConcept.getContainedConceptProperties()) {
				values.clear();
				isArray = false;
				
				String propertyName = pcc.getName();
				
				if ((pcc instanceof PropertyArrayContainedConcept)) {
					isArray = true;
					PropertyArrayContainedConcept array = ((PropertyArrayContainedConcept) sourceConcept.getPropertyArray(propertyName));
					for (int i=0; i < array.length(); i++) {
						values.add(((PropertyAtomContainedConceptSimple) array.get(i)).getM_value());
					}
				} else {
					values.add(((PropertyAtomContainedConceptSimple) pcc).getM_value());
				}
				
				if (values.size() > 0 && values.get(0) != null) {
					containerName = ((ConceptImpl)values.get(0)).getExpandedName().localName;

					StoreFunctions.open(url, containerName);

					if (!delete) getConcepts(url, containerName, propertyName, values, targetConcept, isArray);
					else deleteConcepts(url, containerName, propertyName, values, isArray);

					StoreFunctions.close(url, containerName);
				}
			}
		}
	}
	
	public static void setPrimaryKey(Item item, Entity entity, String[] primaryKeys) throws Exception {
		for (String primaryKeyName : primaryKeys) {
			Object propertyDef = getProperty(entity.getType(), primaryKeyName);

			String primaryKeyType = null;
			Object primaryKeyValue = null;
			if (propertyDef instanceof PropertyDefinition) {
				primaryKeyType = RDFTypes.getTypeString(((PropertyDefinition)propertyDef).getType()).toLowerCase();
				primaryKeyValue = entity.getPropertyValue(((PropertyDefinition)propertyDef).getName());

			} else if (propertyDef instanceof EventPropertyDefinition) {
				primaryKeyType = ((EventPropertyDefinition)propertyDef).getType().getName().toLowerCase();
				primaryKeyValue = entity.getPropertyValue(((EventPropertyDefinition)propertyDef).getPropertyName());
			} 

			if (primaryKeyType != null && primaryKeyValue != null) {
				item.setValue(primaryKeyName, primaryKeyType, primaryKeyValue);
			}
		}
	}
	
	public static Object getProperty(String entityType, String propertyName) {
		if (entityType.startsWith(TypeManager.DEFAULT_BE_NAMESPACE_URI)) {
			entityType = entityType.substring(TypeManager.DEFAULT_BE_NAMESPACE_URI.length());
		}
		RuleServiceProvider rsp = RuleServiceProviderManager.getInstance().getDefaultProvider();
		Ontology ontology = rsp.getProject().getOntology();

		com.tibco.cep.designtime.model.Entity designTimEntity = ontology.getEntity(entityType);

		Object propertyDef = null;
		if (designTimEntity instanceof Concept) {
			for (Object pd : ((Concept)designTimEntity).getAllPropertyDefinitions()) {
				if (pd instanceof PropertyDefinition && ((PropertyDefinition)pd).getName().toLowerCase().equals(propertyName)) {
					propertyDef = pd;
					break;
				}
			}
		} else {
			propertyDef = ((Event)designTimEntity).getPropertyDefinition(propertyName, true);
			for (Object pd : ((Event)designTimEntity).getAllUserProperties()) {
				if (pd instanceof EventPropertyDefinition && ((EventPropertyDefinition)pd).getPropertyName().toLowerCase().equals(propertyName)) {
					propertyDef = pd;
					break;
				}
			}
		}

		return propertyDef;
	}
}
