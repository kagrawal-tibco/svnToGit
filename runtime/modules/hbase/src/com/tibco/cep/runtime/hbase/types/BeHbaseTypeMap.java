package com.tibco.cep.runtime.hbase.types;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFUberType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 10/10/13
 * Time: 2:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeHbaseTypeMap {
    public static final Map<RDFUberType, HDataType> BeToHbase =
            new HashMap<RDFUberType, HDataType>() {
                {
                    put(RDFTypes.BOOLEAN, HDataType.Boolean);
                    put(RDFTypes.DATETIME, HDataType.DateTime);
                    put(RDFTypes.STRING, HDataType.String);

                    put(RDFTypes.INTEGER, HDataType.Integer);
                    put(RDFTypes.LONG, HDataType.Long);
                    put(RDFTypes.DOUBLE, HDataType.Double);

                    put(RDFTypes.CONCEPT, HDataType.Long);
                    put(RDFTypes.CONCEPT_REFERENCE, HDataType.Long);

                    put(RDFTypes.OBJECT, HDataType.Object);
                }
            };

    public static final Map<HDataType, RDFUberType> HbaseToBe =
            new HashMap<HDataType, RDFUberType>() {
                {
                    put(HDataType.Boolean, RDFTypes.BOOLEAN);
                    put(HDataType.DateTime, RDFTypes.DATETIME);
                    put(HDataType.String, RDFTypes.STRING);

                    put(HDataType.Integer, RDFTypes.INTEGER);
                    put(HDataType.Long, RDFTypes.LONG);
                    put(HDataType.Double, RDFTypes.DOUBLE);

                    put(HDataType.Object, RDFTypes.OBJECT);
                }
            };

}
