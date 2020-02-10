package com.tibco.cep.runtime.hbase.cacheaside;

import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 6/14/13
 * Time: 11:33 AM
 * To change this template use File | Settings | File Templates.
 */
public class HbaseSpaceCreator {

    static final HashMap<String, HbaseSpaceDescriptor> hbaseSpaceMap = new HashMap<String, HbaseSpaceDescriptor>();
    static HbaseManager hbaseManager;

    public static void create(Concept c) {
        if (hbaseManager == null) {
            hbaseManager = HbaseManager.getInstance();
        }

        Logger logger = LogManagerFactory.getLogManager().getLogger(HbaseSpaceCreator.class);
        HbaseSpaceDescriptor hbaseSpaceDescriptor = new HbaseSpaceDescriptor();
        List<PropertyDefinition> properties = c.getPropertyDefinitions(false);

        //*******URI**********
        String conceptUri = c.getFullPath();
        if(conceptUri.contains("/"))
        {
            conceptUri = conceptUri.replaceAll("/","_");
        }

        for (PropertyDefinition property : properties) {
            String fieldName = property.getName();
            PROPERTY_TYPES dataType = PROPERTY_TYPES.get(property.getType());
            hbaseSpaceDescriptor.put(fieldName, dataType.toString());
        }

        //hbaseSpaceMap.put(conceptName, hbaseSpaceDescriptor);
        hbaseManager.loadSpaceMap(conceptUri, hbaseSpaceDescriptor);
    }

//    public static HashMap<String, HbaseSpaceDescriptor> getHbaseSpaceMap() {
//        return hbaseSpaceMap;
//    }


}
//logger.log(Level.WARN, "name : "+name+ " type : "+dataType.toString());
