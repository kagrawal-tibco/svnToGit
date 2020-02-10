package com.tibco.rta.model.impl;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DATATYPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_STORAGE_DATATYPE_NAME;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

import com.tibco.rta.model.Attribute;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.RtaSchema;

/**
 * Attribute of the schema.
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class AttributeImpl extends MetadataElementImpl implements Attribute {

	private static final long serialVersionUID = -9198026448015210218L;

	@XmlAttribute(name = ATTR_DATATYPE_NAME)
	protected DataType dataType;

    public AttributeImpl() {
    }

    public AttributeImpl(String name, DataType dataType, RtaSchema ownerSchema) {
        super(name, ownerSchema);
        this.dataType = dataType;
    }

    /**
     * Attribute values will be constrained by the data type.
     *
     * @return
     */
    @Override
    public DataType getDataType() {
        return dataType;
    }
    
	@Override
	public String toString() {
		return "AttributeImpl [dataType=" + dataType + " " + super.toString() + "]";
	}

}
