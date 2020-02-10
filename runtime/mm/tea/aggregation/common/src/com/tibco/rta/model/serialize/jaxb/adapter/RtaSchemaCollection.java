package com.tibco.rta.model.serialize.jaxb.adapter;

import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.impl.RtaSchemaImpl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ELEM_SCHEMAS_NAME;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class RtaSchemaCollection {

    private List<RtaSchema> schemas = new ArrayList<RtaSchema>();

    public RtaSchemaCollection(List<RtaSchema> schemas) {
        this.schemas = schemas;
    }

    protected RtaSchemaCollection() {

    }

    @XmlElement(name = ELEM_SCHEMAS_NAME, type = RtaSchemaImpl.class)
    public List<RtaSchema> getSchemas() {
        return schemas;
    }

    private void setSchemas(List<RtaSchema> schemaList) {
        schemas = schemaList;
    }

}
