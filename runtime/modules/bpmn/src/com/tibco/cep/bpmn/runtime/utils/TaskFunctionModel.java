package com.tibco.cep.bpmn.runtime.utils;

import java.util.ArrayList;
import java.util.List;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;

/**
 * @author Pranab Dhar
 *
 */
public class TaskFunctionModel {
	public static class TypeSymbol {
		private String name;
		private TaskFunctionModel.PROPERTY_TYPE pType;
		private String uri;
		private Boolean isArray;
		private Boolean primitive;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public TaskFunctionModel.PROPERTY_TYPE getPropertyType() {
			return pType;
		}

		public void setPropertyType(TaskFunctionModel.PROPERTY_TYPE pType) {
			this.pType = pType;
		}

		public String getUri() {
			return uri;
		}

		public void setUri(String uri) {
			this.uri = uri;
		}

		public Boolean isArray() {
			return isArray;
		}

		public void setArray(boolean isArray) {
			this.isArray = isArray;
		}

		public Boolean isPrimitive() {
			return primitive;
		}

		public void setPrimitive(boolean primitive) {
			this.primitive = primitive;
		}

		@Override
		public String toString() {
			return "TypeSymbol [name=" + name + ", pType=" + pType + ", uri=" + uri + ", isArray=" + isArray + ", primitive=" + primitive + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + (isArray ? 1231 : 1237);
			result = prime * result + ((pType == null) ? 0 : pType.hashCode());
			result = prime * result + (primitive ? 1231 : 1237);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TypeSymbol other = (TypeSymbol) obj;
			if (isArray != other.isArray)
				return false;
			if (pType != other.pType)
				return false;
			if (primitive != other.primitive)
				return false;
			return true;
		}

	}

	public static enum PROPERTY_TYPE {

		STRING(RDFTypes.STRING),

		INTEGER(RDFTypes.INTEGER), INTEGER_WRAP(RDFTypes.INTEGER_WRAP),

		LONG(RDFTypes.LONG), LONG_WRAP(RDFTypes.LONG_WRAP),

		DOUBLE(RDFTypes.DOUBLE), DOUBLE_WRAP(RDFTypes.DOUBLE_WRAP),

		BOOLEAN(RDFTypes.BOOLEAN), BOOLEAN_WRAP(RDFTypes.BOOLEAN_WRAP),

		DATE_TIME(RDFTypes.DATETIME),

		CONCEPT(RDFTypes.CONCEPT),

		CONCEPT_REFERENCE(RDFTypes.CONCEPT_REFERENCE),

		BASE_PROCESS(RDFTypes.BASE_PROCESS);

		private final RDFUberType value;

		private PROPERTY_TYPE(RDFUberType value) {
			this.value = value;

		}

		public RDFUberType getValue() {
			return value;
		}

		public String getName() {
			return value.toString();
		}

	}

	List<TaskFunctionModel.TypeSymbol> atypes = new ArrayList<>();
	TaskFunctionModel.TypeSymbol rType;
	String name;
	String signature;

	public TaskFunctionModel() {

	}
	

	public void setName(String name) {
		this.name = name;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getName() {
		return name;
	}

	public String getSignature() {
		return signature;
	}

	public List<TaskFunctionModel.TypeSymbol> getArgTypes() {
		return atypes;
	}

	public TaskFunctionModel.TypeSymbol getReturnType() {
		return rType;
	}

	public void setReturnType(TaskFunctionModel.TypeSymbol rType) {
		this.rType = rType;
	}
}