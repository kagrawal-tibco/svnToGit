package com.tibco.cep.runtime.hbase.adaptor;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.model.entity.Entity;
import com.tibco.cep.runtime.hbase.types.BeHbaseTypeMap;
import com.tibco.cep.runtime.hbase.types.HDataType;
import com.tibco.cep.runtime.hbase.HMapCreator;
import com.tibco.cep.runtime.model.pojo.exim.EximParent;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojo;
import com.tibco.cep.runtime.model.pojo.exim.PortablePojoConstants;
import com.tibco.cep.runtime.service.cluster.Cluster;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 10/10/13
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */
public class HConceptTupleAdaptor {

    public static boolean canHandle(Cluster cluster, HMapCreator.Parameters<Long, Entity> parameters, Concept c)
    {
        Map<String, HDataType> fieldDefs = new HashMap<>();

        fieldDefs.put(PortablePojo.PROPERTY_NAME_ID, BeHbaseTypeMap.BeToHbase.get(RDFTypes.LONG));

        fieldDefs.put(PortablePojo.PROPERTY_NAME_TYPE_ID, BeHbaseTypeMap.BeToHbase.get(RDFTypes.INTEGER));

        fieldDefs.put(PortablePojo.PROPERTY_NAME_VERSION, BeHbaseTypeMap.BeToHbase.get(RDFTypes.INTEGER));

        fieldDefs.put(PortablePojoConstants.PROPERTY_CONCEPT_NAME_DELETED, BeHbaseTypeMap.BeToHbase.get(RDFTypes.BOOLEAN));

        fieldDefs.put(PortablePojo.PROPERTY_NAME_EXT_ID, BeHbaseTypeMap.BeToHbase.get(RDFTypes.STRING));

        Concept parent  = c.getParentConcept();

        if(parent != null)
        {
            fieldDefs.put(PortablePojo.PROPERTY_CONCEPT_NAME_PARENT, BeHbaseTypeMap.BeToHbase.get(RDFTypes.LONG));
        }

        List<PropertyDefinition> properties = c.getPropertyDefinitions(false);
        for(PropertyDefinition property : properties)
        {
            int typeIndex = property.getType();

            HDataType fieldType = BeHbaseTypeMap.BeToHbase.get(RDFTypes.types[typeIndex]);

            if(property.getHistorySize() > 0)
            {
                return false;
            }
            else if(property.isArray() || fieldType == null)
            {
                fieldDefs.put(property.getName(), HDataType.Object);
            }
            else
            {
                fieldDefs.put(property.getName(), fieldType);

                if(typeIndex == RDFTypes.DATETIME_TYPEID && EximParent.separateTimezone) {
                String tzName = EximParent.timeZoneName(property.getName());
                fieldDefs.put(tzName, BeHbaseTypeMap.BeToHbase.get(RDFTypes.types[RDFTypes.STRING_TYPEID]));
              }
            }

            //TODO : StateMAchines in ConceptTupleAdaptor

            parameters.setExplicitFieldDefs(fieldDefs);
        }

        return true;
    }
}
