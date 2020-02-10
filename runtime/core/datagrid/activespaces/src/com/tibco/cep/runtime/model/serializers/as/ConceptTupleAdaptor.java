package com.tibco.cep.runtime.model.serializers.as;

import java.util.LinkedList;
import java.util.List;

import com.tibco.as.space.FieldDef;
import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.as.space.Tuple;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.as.kit.map.SpaceMapConstants;
import com.tibco.cep.as.kit.map.SpaceMapCreator.Parameters;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.DataTypeRefMap;
import com.tibco.cep.as.kit.tuple.TupleCodec;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.element.stategraph.StateMachine;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.TypeManager.TypeDescriptor;
import com.tibco.cep.runtime.model.pojo.exim.EximParent;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.cluster.system.MetadataCache;
import com.tibco.cep.runtime.util.SystemProperty;

/*
* Author: Ashwin Jayaprakash / Date: 6/10/11 / Time: 1:09 PM
*/
public class ConceptTupleAdaptor extends AbstractTupleAdaptor {
    protected ConceptTupleAdaptor(String keyColumnName, Class<Long> keyClass, String[] fieldNames, DataType[] dataTypes,
                                  TupleCodec tupleCodec, TypeManager tm, MetadataCache mdc, int entityTypeId) throws Exception {
        super(keyColumnName, keyClass, fieldNames, dataTypes, tupleCodec, tm, mdc, entityTypeId);
    }

    public static boolean canHandle(Cluster cluster, Parameters<Long, Entity> parameters, Concept c) {
        TypeManager typeManager = cluster.getRuleServiceProvider().getTypeManager();
        MetadataCache metadataCache = cluster.getMetadataCache();

        TypeDescriptor td = typeManager.getTypeDescriptor(c.getFullPath());
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

        //don't change this without updating extractVersion()
        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_NAME_VERSION, BeAsTypeMap.BeToAs.get(RDFTypes.INTEGER))
                .setNullable(false);
        fieldDefs.add(fieldDef);

        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_CONCEPT_NAME_DELETED, BeAsTypeMap.BeToAs.get(RDFTypes.BOOLEAN))
                .setNullable(false);
        fieldDefs.add(fieldDef);

        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_NAME_EXT_ID, BeAsTypeMap.BeToAs.get(RDFTypes.STRING))
                .setNullable(true);
        fieldDefs.add(fieldDef);

        // Add "@parent" as a meta property if this is a child of another concept
        Concept parent = c.getParentConcept();
        for(Concept c2 = c; parent == null && c2 != null;) {
        	c2=c2.getSuperConcept();
        	if (c2 != null) {
        		parent = c2.getParentConcept();
        	}
        }
        if (parent != null) {
            fieldDef = FieldDef
                    .create(PortablePojoConstants.PROPERTY_CONCEPT_NAME_PARENT, BeAsTypeMap.BeToAs.get(RDFTypes.LONG))
                    .setNullable(true);
            fieldDefs.add(fieldDef);
        }

        // Add "rrf - a.k.a. reverse-references" as a meta property
        fieldDef = FieldDef
                .create(PortablePojoConstants.PROPERTY_CONCEPT_NAME_REV_REFERENCES, FieldType.BLOB)
                .setNullable(true);
        fieldDefs.add(fieldDef);

        //------------

        // Enable/Disable History support as blob fields
        boolean historyAsBlob = true;
        historyAsBlob = Boolean.parseBoolean(System.getProperty(
                SystemProperty.AS_TUPLE_EXPLICIT_STORE_HISTORY.getPropertyName(), "true"));
                
        //------------

        List<PropertyDefinition> properties = c.getPropertyDefinitions(false);
        for (PropertyDefinition property : properties) {
            int typeIndex = property.getType();

            FieldType fieldType = BeAsTypeMap.BeToAs.get(RDFTypes.types[typeIndex]);

            if (property.getHistorySize() > 0) {
                if (historyAsBlob) {
                    fieldDef = FieldDef
                        .create(property.getName(), FieldType.BLOB)
                        .setNullable(true);
                } else {
                    return false;
                }
            }
            else if (property.isArray() || fieldType == null) {
                fieldDef = FieldDef
                        .create(property.getName(), FieldType.BLOB)
                        .setNullable(true);
            }
            else {
                fieldDef = FieldDef
                        .create(property.getName(), fieldType)
                        .setNullable(true);
                
                //add timezone for datetimes
                if(typeIndex == RDFTypes.DATETIME_TYPEID && EximParent.separateTimezone) {
                	String tzName = EximParent.timeZoneName(property.getName());
                	fieldDefs.add(
                			FieldDef.create(tzName, 
                					BeAsTypeMap.BeToAs.get(RDFTypes.types[RDFTypes.STRING_TYPEID]))
                            .setNullable(true));
                }
            }

            fieldDefs.add(fieldDef);
        }

        List<? extends StateMachine> stateMachines = c.getAllStateMachines();
        for (StateMachine stateMachine : stateMachines) {
            String n = c.getName() + "_" + stateMachine.getName();

            fieldDef = FieldDef
                    .create(n, FieldType.LONG)
                    .setNullable(true);

            fieldDefs.add(fieldDef);
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
                    new ConceptTupleAdaptor(SpaceMapConstants.TUPLE_COLUMN_NAME_KEY, Long.class, fieldNames,
                            dataTypes, tupleCodec, typeManager, metadataCache, entityTypeId);
        }
        catch (Exception e) {
            Logger logger = LogManagerFactory.getLogManager().getLogger(ConceptTupleAdaptor.class);
            logger.log(Level.WARN, e,
                    "Error occurred while attempting to create tuple adaptor for concept [" + c.getName() + "]");

            return false;
        }
        parameters.setTupleCodec(tupleCodec);

        return true;
    }
    
    // This depends on how version is defined by the FieldDef in canHandle
    public static Integer extractVersion(Tuple value) {
    	if(value == null) return null;
    	return value.getInt(PortablePojoConstants.PROPERTY_NAME_VERSION);
    }
}
