package com.tibco.cep.runtime.service.om.impl.datastore;

import com.tibco.cep.runtime.service.om.exception.OMAddException;
import com.tibco.cep.runtime.service.om.exception.OMFetchException;
import com.tibco.cep.runtime.service.om.exception.OMModifyException;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Nov 3, 2006
 * Time: 12:56:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyBDBKey {
        private long subjectId;
        //id assigned to the property's class
        private int fieldId = -1;
    
        public static final String MARKER_RECORD_KEY = "$MarkerRecord$";

        public PropertyBDBKey(PropertyKey pKey, DataStore propertyIndexDs, DBTransaction txn) throws OMFetchException, OMModifyException, OMAddException {
            this(pKey.propClassName, pKey.subjectId,  propertyIndexDs, txn);
        }

        public PropertyBDBKey(String propertyClassName, long subjectId, DataStore propertyIndexDs, DBTransaction txn) throws OMFetchException, OMAddException, OMModifyException {
            this.subjectId = subjectId;
            Object result = propertyIndexDs.fetch(txn, propertyClassName, this);
            if(fieldId == -1) {
                if(propertyClassName == MARKER_RECORD_KEY) {
                    fieldId = 0;
                    propertyIndexDs.add(txn, propertyClassName, new Integer(fieldId));
                } else {
                    PropertyBDBKey markerRecord = new PropertyBDBKey(MARKER_RECORD_KEY, -1, propertyIndexDs, txn);
                    fieldId = markerRecord.fieldId;
                    markerRecord.fieldId++;
                    //update marker record
                    propertyIndexDs.modify(txn, MARKER_RECORD_KEY, new Integer(markerRecord.fieldId));
                    //add this property class to the table
                    propertyIndexDs.add(txn, propertyClassName, new Integer(fieldId));
                }
            }
        }
    
        public long getSubjectId() {
            return subjectId;
        }
        public int getFieldId() {
            return fieldId;
        }
        public void setFieldId(int id) {
            fieldId = id;
        }
}