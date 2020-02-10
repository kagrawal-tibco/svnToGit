package com.tibco.cep.runtime.model.serializers.as;

import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;
import com.tibco.cep.as.kit.tuple.DataType;

import java.util.HashMap;
import java.util.Map;

/*
* Author: Ashwin Jayaprakash / Date: 6/9/11 / Time: 1:43 PM
*/
public class BeAsTypeMap {
    public static final Map<RDFUberType, FieldType> BeToAs =
            new HashMap<RDFUberType, FieldType>() {
                {
                    put(RDFTypes.BOOLEAN, DataType.Boolean.getFieldType());
                    put(RDFTypes.DATETIME, DataType.Date.getFieldType());
                    put(RDFTypes.STRING, DataType.String.getFieldType());

                    put(RDFTypes.INTEGER, DataType.Integer.getFieldType());
                    put(RDFTypes.LONG, DataType.Long.getFieldType());
                    put(RDFTypes.DOUBLE, DataType.Double.getFieldType());

                    put(RDFTypes.CONCEPT, DataType.Long.getFieldType());
                    put(RDFTypes.CONCEPT_REFERENCE, DataType.Long.getFieldType());

                    put(RDFTypes.OBJECT, DataType.RawBlob.getFieldType());
                }
            };

    public static final Map<FieldType, RDFUberType> AsToBe =
            new HashMap<FieldType, RDFUberType>() {
                {
                    put(DataType.Boolean.getFieldType(), RDFTypes.BOOLEAN);
                    put(DataType.Date.getFieldType(), RDFTypes.DATETIME);
                    put(DataType.String.getFieldType(), RDFTypes.STRING);

                    put(DataType.Integer.getFieldType(), RDFTypes.INTEGER);
                    put(DataType.Long.getFieldType(), RDFTypes.LONG);
                    put(DataType.Double.getFieldType(), RDFTypes.DOUBLE);

                    put(DataType.RawBlob.getFieldType(), RDFTypes.OBJECT);
                }
            };
}
