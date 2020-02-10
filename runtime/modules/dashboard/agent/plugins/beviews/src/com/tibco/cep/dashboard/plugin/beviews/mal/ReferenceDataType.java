package com.tibco.cep.dashboard.plugin.beviews.mal;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;
import com.tibco.cep.designtime.core.model.Entity;

public class ReferenceDataType extends DataType {

	private static final long serialVersionUID = -2357613460359577232L;
	
	private Entity referenceType;
	
	public ReferenceDataType(Entity referenceType) {
		super("reference", "-1");
		if (referenceType == null){
			throw new IllegalArgumentException("reference type cannot be null");
		}
		this.referenceType = referenceType;
	}

	@Override
	public String toString(Object o) {
		return BuiltInTypes.LONG.toString(o);
	}

	@Override
	public Comparable<?> valueOf(String s) {
		return BuiltInTypes.LONG.valueOf(s);
	}
	
	@Override
	public int hashCode() {
		return this.referenceType.hashCode();
	}
	
	@Override
	public String toString() {
		return this.referenceType.getFullPath();
	}

	public final Entity getReferenceType() {
		return referenceType;
	}

	public DataType getPrimitiveDataType() {
		return BuiltInTypes.LONG;
	}
	
}