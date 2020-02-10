package com.tibco.rta.model.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.DataTypeMismatchException;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.UndefinedSchemaElementException;
import com.tibco.rta.model.mutable.MutableFunctionDescriptor;

@XmlAccessorType(XmlAccessType.NONE)
public class FunctionDescriptorImpl implements MutableFunctionDescriptor {

	private static final long serialVersionUID = -6859575486096978052L;

	protected String name;
	protected String category;
	protected String implClassName;
	protected String description;
	
	
	protected Map<String, FunctionParam> functionParams = new LinkedHashMap<String,FunctionParam>();
	
	protected Map<String, FunctionParam> functionContexts = new LinkedHashMap<String,FunctionParam>();


	public FunctionDescriptorImpl(String name, String implClassName, String category) {
		this.name = name;
		this.implClassName = implClassName;
		this.category = category;
	}
	
	public FunctionDescriptorImpl() {
		
	}

	@XmlAttribute(name=ATTR_NAME_NAME)
	@Override
	public String getName() {
		return name;
	}

	
	@Override
	public String getCategory() {
		return category;
	}

	
	@Override
	public String getDescription() {
		return description;
	}

	
	@Override
	public String getImplClass() {
		return implClassName;
	}

	@Override
	public FunctionParam getFunctionParam(String paramName) {
		return functionParams.get(paramName);
	}

	@Override
	public FunctionParam getFunctionParam(int index) {
		int i=0;
		for (FunctionParam p : functionParams.values()) {
			if (i++ == index) {
				return p;
			}
		}
		return null;
	}
		
//	@XmlElement(name=ELEM_FUNCTION_PARAMS, type=FunctionParamImpl.class)
	@Override
	@JsonDeserialize(using=com.tibco.rta.model.serialize.json.ArrayMapDeserializer.class)
	public List<FunctionParam> getFunctionParams() {
		return new ArrayList<FunctionParam>(functionParams.values());
	}
	
	@Override
	public FunctionParam getFunctionContext(String contextName) {
		return functionContexts.get(contextName);
	}

	@Override
	public FunctionParam getFunctionContext(int index) {
		int i=0;
		for (FunctionParam p : functionContexts.values()) {
			if (i++ == index) {
				return p;
			}
		}
		return null;
	}
	

	@Override
	@JsonDeserialize(using=com.tibco.rta.model.serialize.json.ArrayMapDeserializer.class)
	public List<FunctionParam> getFunctionContexts() {
		return new ArrayList<FunctionParam>(functionContexts.values());
	}
		
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setImplClass(String implClassName) {
		this.implClassName = implClassName;
	}

	@Override
	public void addFunctionParam(FunctionParam functionParam) throws UndefinedSchemaElementException, DuplicateSchemaElementException {
		if (null == functionParam) {
			throw new UndefinedSchemaElementException("Function Parameter is not specified");
		}
        // Check if it is already present in the map
		if (! functionParams.containsKey(functionParam.getName())) {
			functionParams.put(functionParam.getName(), functionParam);
        } else {
            throw new DuplicateSchemaElementException(String.format("Function Parameter with name '%s' already exists in function '%s'", functionParam.getName(), name));
        }
	}
		
	@Override
	public void addFunctionContext(FunctionParam functionParam)
			throws UndefinedSchemaElementException,
			DuplicateSchemaElementException {
		if (null == functionParam) {
			throw new UndefinedSchemaElementException("Function Parameter is not specified");
		}
        // Check if it is already present in the map
		if (! functionContexts.containsKey(functionParam.getName())) {
			functionContexts.put(functionParam.getName(), functionParam);
        } else {
            throw new DuplicateSchemaElementException(String.format("Function Parameter with name '%s' already exists in function '%s'", functionParam.getName(), name));
        }		
	}
		
	@XmlAccessorType(XmlAccessType.NONE)	
	public static class FunctionParamImpl implements MutableFunctionParam {
		
		private static final long serialVersionUID = -2946809713499863277L;
		
		protected String name;
		protected DataType dataType;
		protected int index;
		protected String description;

		@Override
		@XmlAttribute(name=ATTR_REF_NAME)
		public String getName() {
			return name;
		}

		@XmlAttribute(name=ATTR_DATATYPE_NAME)
		@Override		
		public DataType getDataType() {
			return dataType;
		}

		@XmlAttribute(name=ATTR_INDEX_NAME)
		@Override
		public int getIndex() {
			return index;
		}

		@XmlAttribute(name=ATTR_DESCRIPTION)
		@Override
		public String getDescription() {
			return description;
		}

		@Override
		public void setName(String paramName) {
			this.name = paramName;
		}

		@Override
		public void setDataType(DataType dataType) {
			this.dataType = dataType;
			
		}
		
		@Override
		public void setIndex(int index) {
			this.index = index;
		}

		@Override
		public void setDescription(String paramDesc) {
			this.description = paramDesc;
		}

		@Override
		public boolean equals (Object obj) {
			if (null == obj) {
				return false;
			}
			if (! (obj instanceof FunctionDescriptorImpl)) {
				return false;
			}
			FunctionParamImpl cObj = (FunctionParamImpl) obj;
            return (cObj.name.equalsIgnoreCase(this.name) &&
            					cObj.dataType.equals(this.dataType));

		}
	}
	
	public static class FunctionParamValueImpl extends FunctionParamImpl implements FunctionParamValue {
	
		private static final long serialVersionUID = -1696357266226100865L;
	
		protected Object value;
		
		@XmlElement(name=ATTR_VALUE_NAME)
		@Override
		public Object getValue() {
			return this.value;
		}

		@Override
		public void setValue(Object value) throws DataTypeMismatchException {
			if (!ModelValidations.validateDataType(dataType, value)) {
                throw new DataTypeMismatchException(dataType, value);
            }
			this.value = value;
		}
	}

}
