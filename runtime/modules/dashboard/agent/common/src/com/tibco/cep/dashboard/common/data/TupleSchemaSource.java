package com.tibco.cep.dashboard.common.data;

import java.io.Externalizable;

import com.tibco.cep.dashboard.common.utils.StringUtil;

/**
 * @author anpatil
 *
 */
public abstract class TupleSchemaSource implements Externalizable {

	private String scopeName;
	private String typeName;
	private String typeID;	

	protected String stringRepresentation;
	
	protected int hashCode;
	
	public TupleSchemaSource() {
		
	}
	
	private void buildStringRepresentation() {
		if (StringUtil.isEmptyOrBlank(scopeName) == true){
			throw new IllegalStateException("ScopeName cannot be null or empty");
		}
		if (StringUtil.isEmptyOrBlank(typeName) == true){
			throw new IllegalStateException("TypeName cannot be null or empty");
		}
		if (StringUtil.isEmptyOrBlank(typeID) == true){
			throw new IllegalStateException("TypeID cannot be null or empty");
		}		
		StringBuilder builder = new StringBuilder(TupleSchemaSource.class.getName());
		builder.append("[id=");
		builder.append(typeID);
		builder.append(",name=");
		builder.append(typeName);
		builder.append(",scopename=");
		builder.append(scopeName);
		builder.append("]");
		
		stringRepresentation = builder.toString();
		
	}


	public final String getScopeName() {
		return scopeName;
	}


	public final String getTypeName() {
		return typeName;
	}


	public final String getTypeID() {
		return typeID;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this){
			return true;
		}
		if (obj instanceof TupleSchemaSource){
			TupleSchemaSource castedObj = (TupleSchemaSource) obj;
			return castedObj.toString().equals(toString());
		}
		return false;
	}


	@Override
	public int hashCode() {
		if (stringRepresentation == null){
			buildStringRepresentation();
			hashCode = stringRepresentation.hashCode();
		}
		return hashCode;
	}


	@Override
	public String toString() {
		if (stringRepresentation == null){
			buildStringRepresentation();
		}
		return stringRepresentation;
	}

	public final void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	public final void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public final void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	
	
}