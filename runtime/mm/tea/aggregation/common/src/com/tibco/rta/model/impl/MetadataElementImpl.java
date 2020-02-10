package com.tibco.rta.model.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.serialize.ModelSerializer;
import com.tibco.rta.model.serialize.SerializationTarget;

/**
 * Default implementation of MetadataElement
 */
public class MetadataElementImpl extends BaseMetadadataElementImpl {

	private static final long serialVersionUID = -7310807551773207857L;

	protected RtaSchema ownerSchema;
	
	public MetadataElementImpl () {
        super(null);
	}
	
	public MetadataElementImpl (String name) {
		super(name);
	}
	
	public MetadataElementImpl (String name, RtaSchema ownerSchema) {
        super(name);
		this.ownerSchema = ownerSchema;
	}

	@Override
	@JsonIgnore
	synchronized public RtaSchema getOwnerSchema() {
		return ownerSchema;
	}


	@Override
	public <T extends SerializationTarget, S extends ModelSerializer<T>> void serialize(
			S serializer) {
	}

	@Override
	public String toString() {
		return "MetadataElementImpl [ownerSchemaName=" + ownerSchema.getName() + " " + super.toString()+ "]";
	}
	
	
}