package com.tibco.cep.dashboard.plugin.beviews.mal;

import java.util.Iterator;
import java.util.List;

import com.tibco.cep.dashboard.common.utils.StringUtil;
import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;

public class EntityUtils {

//	public static String getName(Entity entity){
//		return entity.getName();
//	}

	public static boolean isDVM(Entity entity){
		if (entity instanceof Metric){
			Metric metric = (Metric) entity;
			return isDVM(metric);
		}
		return false;
	}

//	public static Map<String, String> getDisplayableFields(Entity entity) {
//		Map<String, String> fields = new LinkedHashMap<String, String>();
//		TupleSchema tupleSchema = TupleSchemaFactory.getInstance().getTupleSchema(entity);
//		int fieldCount = tupleSchema.getFieldCount();
//		for(int i = 0; i < fieldCount; i++){
//			TupleSchemaField field = tupleSchema.getFieldByPosition(i);
//			//INFO hiding system fields and array fields
//			if (field.isSystemField() == false && field.isArray() == false){
//				fields.put(field.getFieldName(), field.getFieldName());
//			}
//		}
//		return fields;
//	}

	//PATCH clean up and centralize the access to URL LINK
//	static String getURL(Entity entity, String fieldName) {
//		if (entity instanceof Metric){
//			Metric metric = (Metric) entity;
//			for (PropertyDefinition field : metric.getAllProperties()) {
//				if (field.getName().equals(fieldName) == true){
//					ExternalURL externalURL = getURL(field);
//					if (externalURL != null) {
//						return externalURL.getURL();
//					}
//
//				}
//			}
//		}
//		return null;
//	}
//
	static ExternalURL getURL(PropertyDefinition field) {
		PropertyMap extendedProperties = field.getExtendedProperties();
		if (extendedProperties != null) {
			List<Entity> properties = extendedProperties.getProperties();
			String name = null;
			String url = null;
			for (Entity rawProperty : properties) {
				if (rawProperty instanceof SimpleProperty) {
					SimpleProperty property = (SimpleProperty) rawProperty;
					if (property.getName().equals("urlname") == true) {
						name = property.getValue();
					} else if (property.getName().equals("urllink") == true) {
						url = property.getValue();
					}
				}
			}
			if (StringUtil.isEmptyOrBlank(name) == false && StringUtil.isEmptyOrBlank(url) == false) {
				return new ExternalURL(name, url);
			}
		}
		return null;
	}

	static String getPropertyValue(PropertyDefinition field, String name) {
		PropertyMap extendedProperties = field.getExtendedProperties();
		if (extendedProperties != null) {
			List<Entity> properties = extendedProperties.getProperties();
			for (Entity rawProperty : properties) {
				if (rawProperty instanceof SimpleProperty) {
					SimpleProperty property = (SimpleProperty) rawProperty;
					if (property.getName().equals(name) == true) {
						return property.getValue();
					}
				}
			}
		}
		return null;
	}

	public static Entity getChild(Entity entity){
		if (entity instanceof Metric){
			if (EntityUtils.isDVM(entity) == true){
				return null;
			}
			Iterator<Entity> transientObjects = EntityCache.getInstance().getAllTransientObjects();
			while (transientObjects.hasNext()) {
				Entity possibleDVM = (Entity) transientObjects.next();
				Entity possibleEntity = EntityUtils.getParent(possibleDVM);
				if (possibleEntity != null && possibleEntity.getGUID().equals(entity.getGUID()) == true && EntityUtils.isDVM(possibleDVM) == true) {
					return possibleDVM;
				}
			}
		}
		return null;
	}

	public static Entity getParent(Entity entity){
		if (entity instanceof Metric){
			if (EntityUtils.isDVM(entity) == false){
				return null;
			}
			PropertyMap extendedPropertiesHolder = entity.getExtendedProperties();
			if (extendedPropertiesHolder != null) {
				List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
				if (extendedPropertyList != null){
					for (Entity property : extendedPropertyList) {
						if (property instanceof SimpleProperty && property.getName().equals("original") == true){
							return EntityCache.getInstance().getEntity(((SimpleProperty) property).getValue());
						}
					}
				}
			}
		}
		return null;
	}

	public static PropertyDefinition getParentIdentifier(Entity entity) {
		if (entity instanceof Metric){
			if (EntityUtils.isDVM(entity) == false){
				return null;
			}
			List<PropertyDefinition> fields = ((Metric) entity).getProperties();
			for (PropertyDefinition field : fields) {
				PropertyMap extendedPropertiesHolder = field.getExtendedProperties();
				if (extendedPropertiesHolder != null) {
					List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
					if (extendedPropertyList != null){
						for (Entity property : extendedPropertyList) {
							if (property instanceof SimpleProperty && property.getName().equals("parentidentifier") == true){
								return field;
							}
						}
					}
				}
			}
		}
		return null;
	}

	static void markAsDVM(Metric metricDVM, Metric metric) {
		PropertyMap extendedPropertiesHolder = metricDVM.getExtendedProperties();
    	if (extendedPropertiesHolder == null) {
    		extendedPropertiesHolder = ModelFactory.eINSTANCE.createPropertyMap();
    		metricDVM.setExtendedProperties(extendedPropertiesHolder);
    	}

    	List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
    	//INFO setting a special property 'dvm' to 'true' for DVM Metric
    	//mark the metric as DVM
    	SimpleProperty propertyObject = ModelFactory.eINSTANCE.createSimpleProperty();
    	propertyObject.setName("dvm");
    	propertyObject.setValue("true");
    	extendedPropertyList.add(propertyObject);
    	//connect with the orignal metric
    	//INFO setting a special property 'original' to metric's guid to navigate from DVM to parent
    	propertyObject = ModelFactory.eINSTANCE.createSimpleProperty();
    	propertyObject.setName("original");
    	propertyObject.setValue(metric.getGUID());
    	extendedPropertyList.add(propertyObject);
	}

	static boolean isDVM(Metric metric){
		PropertyMap extendedPropertiesHolder = metric.getExtendedProperties();
		if (extendedPropertiesHolder == null){
			return false;
		}
		List<Entity> extendedPropertyList = extendedPropertiesHolder.getProperties();
		for (Entity propertyObject : extendedPropertyList) {
			if (propertyObject instanceof SimpleProperty){
				SimpleProperty property = (SimpleProperty) propertyObject;
				if (property.getName().equals("dvm") && property.getValue().equals("true")){
					return true;
				}
			}
		}
		return false;
	}
}
