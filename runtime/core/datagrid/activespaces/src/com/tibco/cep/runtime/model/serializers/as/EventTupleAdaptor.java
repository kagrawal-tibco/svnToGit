package com.tibco.cep.runtime.model.serializers.as;

import java.util.LinkedList;
import java.util.List;

import com.tibco.as.space.FieldDef;
import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.as.kit.map.SpaceMapConstants;
import com.tibco.cep.as.kit.map.SpaceMapCreator.Parameters;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.DataTypeRefMap;
import com.tibco.cep.as.kit.tuple.TupleCodec;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;

/*
* Author: Ashwin Jayaprakash / Date: 6/10/11 / Time: 1:09 PM
*/
public class EventTupleAdaptor extends AbstractTupleAdaptor {
    protected EventTupleAdaptor(String keyColumnName, Class<Long> keyClass, String[] fieldNames, DataType[] dataTypes,
                                TupleCodec tupleCodec, TypeManager tm, MetadataCache mdc, int entityTypeId) throws Exception {
        super(keyColumnName, keyClass, fieldNames, dataTypes, tupleCodec, tm, mdc, entityTypeId);
    }

    public static boolean canHandle(Cluster cluster, Parameters<Long, Entity> parameters, Event e) {
        TypeManager typeManager = cluster.getRuleServiceProvider().getTypeManager();
        MetadataCache metadataCache = cluster.getMetadataCache();

        TypeDescriptor td = typeManager.getTypeDescriptor(e.getFullPath());
        int entityTypeId = td.getTypeId();

        //------------

        LinkedList<FieldDef> fieldDefs = new LinkedList<FieldDef>();

        FieldDef fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_NAME_ID, BeAsTypeMap.BeToAs.get(RDFTypes.LONG))
                .setNullable(false);
        fieldDefs.add(fieldDef);

        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_NAME_TYPE_ID, BeAsTypeMap.BeToAs.get(RDFTypes.INTEGER))
                .setNullable(false);
        fieldDefs.add(fieldDef);

        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_NAME_VERSION, BeAsTypeMap.BeToAs.get(RDFTypes.INTEGER))
                .setNullable(false);
        fieldDefs.add(fieldDef);

        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_EVENT_NAME_RECOVERED, BeAsTypeMap.BeToAs.get(RDFTypes.BOOLEAN))
                .setNullable(false);
        fieldDefs.add(fieldDef);

        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_EVENT_NAME_PAYLOAD, BeAsTypeMap.BeToAs.get(RDFTypes.OBJECT))
                .setNullable(true);
        fieldDefs.add(fieldDef);

        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_NAME_EXT_ID, BeAsTypeMap.BeToAs.get(RDFTypes.STRING))
                .setNullable(true);
        fieldDefs.add(fieldDef);

        //------------

        List<EventPropertyDefinition> properties = e.getAllUserProperties();
        for (EventPropertyDefinition property : properties) {
            int typeIndex = property.getType().getTypeId();

            FieldType fieldType = BeAsTypeMap.BeToAs.get(RDFTypes.types[typeIndex]);

            fieldDef = FieldDef
                    .create(property.getPropertyName(), fieldType)
                    .setNullable(true);

            fieldDefs.add(fieldDef);
        }

        //Processing for time event
        if( e.getType() == Event.TIME_EVENT ) {
            addTimeEventFieldDefs(fieldDefs,parameters,e);
        }

        parameters.setExplicitFieldDefs(fieldDefs);

        //------------

        String[] fieldNames = new String[fieldDefs.size()];
        DataType[] dataTypes = new DataType[fieldDefs.size()];
        int i = 0;

        for (FieldDef fd : fieldDefs) {
            fieldNames[i] = fd.getName();
            dataTypes[i++] = DataTypeRefMap.mapToDataType(fd.getType());
        }

        TupleCodec tupleCodec = parameters.getTupleCodec();
        try {
            tupleCodec =
                    new EventTupleAdaptor(SpaceMapConstants.TUPLE_COLUMN_NAME_KEY, Long.class, fieldNames,
                            dataTypes, tupleCodec, typeManager, metadataCache, entityTypeId);
        }
        catch (Exception ex) {
            Logger logger = LogManagerFactory.getLogManager().getLogger(EventTupleAdaptor.class);
            logger.log(Level.WARN, ex,
                    "Error occurred while attempting to create tuple adaptor for event [" + e.getName() + "]");

            return false;
        }
        parameters.setTupleCodec(tupleCodec);

        return true;
    }

    public static boolean addTimeEventFieldDefs( LinkedList<FieldDef> fieldDefs,Parameters<Long, Entity> parameters, Event e ) {

        //Add the meta-data fields for the TimeEvent
        FieldDef fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_CLOSURE, BeAsTypeMap.BeToAs.get(RDFTypes.STRING))
                .setNullable(true);
        fieldDefs.add(fieldDef);
        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_NEXT, BeAsTypeMap.BeToAs.get(RDFTypes.LONG))
                .setNullable(true);
        fieldDefs.add(fieldDef);
        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_TTL, BeAsTypeMap.BeToAs.get(RDFTypes.LONG))
                .setNullable(true);
        fieldDefs.add(fieldDef);
        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_NAME_TIME_EVENT_FIRED, BeAsTypeMap.BeToAs.get(RDFTypes.BOOLEAN))
                .setNullable(true);
        fieldDefs.add(fieldDef);

        parameters.setExplicitFieldDefs(fieldDefs);

        return true;
    }
}
