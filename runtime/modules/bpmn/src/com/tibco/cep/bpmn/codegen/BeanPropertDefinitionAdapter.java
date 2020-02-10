package com.tibco.cep.bpmn.codegen;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.swing.Icon;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;

public class BeanPropertDefinitionAdapter implements PropertyDefinition {


	protected static final String INTERNAL_REPRESENTATION_SEPARATOR_STRING = ":";
	protected boolean _isMetricTrackingAuditField = false;
	private PropertyDescriptor pd;
	private String name;
	private boolean isArray;
	private int type;
	private String conceptPath;
	
	
	public BeanPropertDefinitionAdapter(PropertyDescriptor pd) {
		this.pd = pd;
		this.name = pd.getName();
		this.type = getTypeInfo(pd.getPropertyType());
		
		
	}



	private int getTypeInfo(Class<?> propertyType) {
		if(propertyType == boolean.class || propertyType == Boolean.class) {
			return PropertyDefinition.PROPERTY_TYPE_BOOLEAN;
		} else if( propertyType == int.class || propertyType == Integer.class) {
			return PropertyDefinition.PROPERTY_TYPE_INTEGER;
		} else if( propertyType == long.class || propertyType == Long.class) {
			return PropertyDefinition.PROPERTY_TYPE_LONG;
		} else if( propertyType == double.class || propertyType == Double.class) {
			return PropertyDefinition.PROPERTY_TYPE_REAL;
		} else if( propertyType == float.class || propertyType == Float.class) {
			return PropertyDefinition.PROPERTY_TYPE_REAL;
		} else if( propertyType == String.class ) {
			return PropertyDefinition.PROPERTY_TYPE_STRING;
		} else if( propertyType == Calendar.class ) {
			return PropertyDefinition.PROPERTY_TYPE_DATETIME;
		} else if( propertyType.isMemberClass()){
			return PropertyDefinition.PROPERTY_TYPE_CONCEPT;
		}
		
		if(propertyType == boolean[].class || propertyType == Boolean[].class) {
			this.isArray = true;
			return PropertyDefinition.PROPERTY_TYPE_BOOLEAN;
		} else if( propertyType == int[].class || propertyType == Integer[].class) {
			this.isArray = true;
			return PropertyDefinition.PROPERTY_TYPE_INTEGER;
		} else if( propertyType == long[].class || propertyType == Long[].class) {
			this.isArray = true;
			return PropertyDefinition.PROPERTY_TYPE_LONG;
		} else if( propertyType == double[].class || propertyType == Double[].class) {
			this.isArray = true;
			return PropertyDefinition.PROPERTY_TYPE_REAL;
		} else if( propertyType == float[].class || propertyType == Float[].class) {
			this.isArray = true;
			return PropertyDefinition.PROPERTY_TYPE_REAL;
		} else if( propertyType == String[].class ) {
			this.isArray = true;
			return PropertyDefinition.PROPERTY_TYPE_STRING;
		} else if( propertyType == Calendar[].class ) {
			this.isArray = true;
			return PropertyDefinition.PROPERTY_TYPE_DATETIME;
		} else if(propertyType.isArray() && propertyType.getComponentType().isMemberClass()) {
			this.isArray = true;
			this.conceptPath = ModelNameUtil.generatedClassNameToModelPath(propertyType.getComponentType().getName()+"Impl");
			return PropertyDefinition.PROPERTY_TYPE_CONCEPT;
		}
        return 0;
	}



	public PropertyDefinition getAttributeDefinition(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<PropertyDefinition> getAttributeDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Concept getConceptType() {
		return null;
	}

	public String getConceptTypePath() {
		return this.conceptPath;
	}

	public String getDefaultValue() {
		return null;
	}

	public Collection<?> getDisjointSet() {
		return Collections.emptySet();
	}

	public Collection<?> getEquivalentProperties() {
		return Collections.emptyList();
	}

	public int getHistoryPolicy() {
		return 0;
	}

	public int getHistorySize() {
		return 0;
	}


	public Concept getOwner() {
		return null;
	}

	public PropertyDefinition getParent() {
		return null;
	}

	public int getType() {
		return this.type;
	}

	public boolean isA(PropertyDefinition propertyDefinition) {
		return false;
	}

	public boolean isArray() {
		return this.isArray;
	}

	public boolean isDisjointFrom(PropertyDefinition pd) {
		return false;
	}
	
	protected String toInternalRepresentation(PropertyDefinition pd) {
        return null;
    }

	public boolean isSameAs(PropertyDefinition pd) {
		return false;
	}

	public boolean isTransitive() {
		return false;
	}
	
	@Override
	public int getOrder() {
		return 0;
	}


	

	
	public boolean isMetricTrackingAuditField() {
		return false;
	}
	

	
	public String getName() {
		return this.name;
	}
	
	@Override
	public Set getInstances() {
		return null;
	}


	@Override
	public Ontology getOntology() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getGUID() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void serialize(OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		
	}



	@Override
	public String getFullPath() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getFolderPath() {
		// TODO Auto-generated method stub
		return null;
	}







	@Override
	public String getIconPath() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Object getTransientProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Map getTransientProperties() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getHiddenProperty(String key) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Map getHiddenProperties() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Map getExtendedProperties() {
		return Collections.EMPTY_MAP;
	}



	@Override
	public String getBindingString() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getLastModified() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getAlias() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Folder getFolder() {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
