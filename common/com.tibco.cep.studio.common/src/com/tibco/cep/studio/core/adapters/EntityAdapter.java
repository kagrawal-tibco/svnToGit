package com.tibco.cep.studio.core.adapters;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.ModelFactory;
import com.tibco.cep.designtime.core.model.ObjectProperty;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.Validatable;

public abstract class EntityAdapter<E extends com.tibco.cep.designtime.core.model.Entity> implements Entity {

	protected E adapted;
	
	/**
	 * Keep a reference to the ontology from where this adapter is created
	 */
	protected Ontology emfOntology;

	public EntityAdapter(E adapted, Ontology emfOntology) {
		if (adapted == null) {
			throw new IllegalArgumentException("Adapted entity cannot be null");
		}
		this.adapted = adapted;
		this.emfOntology = emfOntology;
	}

	protected abstract E getAdapted();
	
	
	public String getName() {
		return adapted.getName();
	}

	public String getFolderPath() {
		return adapted.getFolder();
	}

	public String getDescription() {
		return adapted.getDescription();
	}

	public String getAlias() {
		return (String) getExtendedProperties().get("alias");
	}

	public String getBindingString() {
		// TODO Auto-generated method stub
		return null;
	}

	public Map getExtendedProperties() {
        /* FIX THIS - Needs to retest this - VWC
		//TODO Get properties later
		PropertyMap pmap = adapted.getExtendedProperties();
		if (pmap == null) {
			return new HashMap();
		}
		List list = pmap.getProperties();
		if (list == null) {
			return new HashMap();
		}
		Map map = new HashMap();
		for (int i=0; i<list.size(); i++) {
			Object value = list.get(i);
			if (value instanceof SimpleProperty) {
				SimpleProperty sprop = (SimpleProperty) value;
				map.put(sprop.getName(), sprop.getValue());
			}
		}
		return map;
        */
		Map<Object, Object> propMap = new HashMap<Object, Object>();
		PropertyMap map = adapted.getExtendedProperties();
		if (map != null) {
			EList<com.tibco.cep.designtime.core.model.Entity> properties = map.getProperties();
			for (int i=0; i<properties.size(); i++) {
				com.tibco.cep.designtime.core.model.Entity entity = properties.get(i);
				if (entity instanceof SimpleProperty) {
					SimpleProperty prop = (SimpleProperty) entity;
					propMap.put(prop.getName(), prop.getValue());
				} else if (entity instanceof ObjectProperty) {
					propMap.put(entity.getName(), createProperty(((ObjectProperty) entity).getValue()));
				}
			}
		}
		return propMap;
	}

	private Object createProperty(EObject value) {
		if (value instanceof PropertyMap) {
			PropertyMap map = (PropertyMap) value;
			Map<Object, Object> propMap = new HashMap<Object, Object>();
			EList<com.tibco.cep.designtime.core.model.Entity> properties = map.getProperties();
			for (int i=0; i<properties.size(); i++) {
				com.tibco.cep.designtime.core.model.Entity entity = properties.get(i);
				if (entity instanceof SimpleProperty) {
					SimpleProperty prop = (SimpleProperty) entity;
					propMap.put(prop.getName(), prop.getValue());
				} else if (entity instanceof ObjectProperty) {
					propMap.put(entity.getName(), createProperty(((ObjectProperty) entity).getValue()));
				}
			}
			return propMap;
		}
		// can it be anything else besides PropertyMap here?
		return null;
	}

	public Folder getFolder() {
		String folderString = adapted.getFolder();
		Folder folder = emfOntology.getFolder(folderString);
		
		return folder;
	}

	public String getFullPath() {
		return adapted.getFullPath();
	}

	public String getGUID() {
		return adapted.getGUID();
	}

	public Map<?, ?> getHiddenProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHiddenProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getIconPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLastModified() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getNamespace() {
		return adapted.getNamespace();
	}

	public Ontology getOntology() {
		return emfOntology;
	}

	public Map getTransientProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getTransientProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void serialize(OutputStream out) throws IOException {
		// TODO Auto-generated method stub

	}

	public Validatable[] getInvalidObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> getModelErrors() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getStatusMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValid(boolean recurse) {
		// TODO Auto-generated method stub
		return false;
	}

	public void makeValid(boolean recurse) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EntityAdapter)) {
			return false;
		}
		EntityAdapter ea = (EntityAdapter) obj;
		com.tibco.cep.designtime.core.model.Entity eAdapted = ea.getAdapted();
		
		Class<? extends com.tibco.cep.designtime.core.model.Entity> class1 = adapted.getClass();
		if (!(eAdapted.getClass().equals(class1))) {
			return false;
		}
		if (!(getName().equalsIgnoreCase(ea.getName()))) {
			return false;
		}
		if (!(getFullPath().equalsIgnoreCase(ea.getFullPath()))) {
			return false;
		}
		return getAdapted().equals(eAdapted);
	}
	
	@Override
	public String toString() {
		if(adapted != null) {
			return adapted.toString();
		}
		return super.toString();
	}
	
	protected com.tibco.cep.designtime.core.model.Entity createProperty(String key, Object val) {
		if (val instanceof String) {
			SimpleProperty simpleProperty = ModelFactory.eINSTANCE.createSimpleProperty();
			simpleProperty.setName(key);
			simpleProperty.setValue((String) val);
			return simpleProperty;
		} else if (val instanceof Map) {
			Map propMap = (Map) val;
			PropertyMap propertyMap = ModelFactory.eINSTANCE.createPropertyMap();
			
			for (Object prop : propMap.keySet()) {
				String propKey = (String) prop;
				Object propObj = propMap.get(propKey);
				com.tibco.cep.designtime.core.model.Entity subProperty = createProperty(propKey, propObj);
				propertyMap.getProperties().add(subProperty);
			}

			ObjectProperty objProperty = ModelFactory.eINSTANCE.createObjectProperty();
			objProperty.setName(key);
			objProperty.setValue(propertyMap);

			return objProperty;
		} else {
			SimpleProperty simpleProperty = ModelFactory.eINSTANCE.createSimpleProperty();
			simpleProperty.setName(key);
			simpleProperty.setValue(String.valueOf(val));
			return simpleProperty;
		}
	}

}
