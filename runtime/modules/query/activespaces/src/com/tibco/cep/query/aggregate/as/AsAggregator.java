package com.tibco.cep.query.aggregate.as;

import com.tibco.as.space.*;
import com.tibco.as.space.remote.InvokeResult;
import com.tibco.as.space.remote.InvokeResultList;
import com.tibco.cep.as.kit.tuple.DataType;
import com.tibco.cep.as.kit.tuple.DataTypeRefMap;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.query.aggregate.Aggregate;
import com.tibco.cep.query.aggregate.Aggregates;
import com.tibco.cep.query.aggregate.Aggregator;
import com.tibco.cep.query.aggregate.common.TypeConstants;

import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 11/16/12
 * Time: 3:35 PM
 */
public class AsAggregator implements Aggregator {
    final Space space;
    final SpaceDef spaceDef;
    LinkedHashMap<Tuple, Class> operationsMap;

    public AsAggregator(Space space) {
        this.space = space;
        try {
            this.spaceDef = space.getSpaceDef();
        } catch (ASException e) {
            throw new RuntimeException(e);
        }

        this.operationsMap = new LinkedHashMap<Tuple, Class>();
    }

    @Override
    public Class<? extends Aggregate> findAggregate(Aggregates type) {
        switch (type) {
            case MIN:
                return AsMinAggregator.class;
            case MAX:
                return AsMaxAggregator.class;
            case SUM:
                return AsSumAggregator.class;
            case COUNT:
                return AsCountAggregator.class;
            case AVG:
                return AsAvgAggregator.class;
        }

        throw new UnsupportedOperationException();
    }

    @Override
    public Aggregator add(Class<? extends Aggregate> aggregator, String fieldName, String filter, String... groupByFieldNames) {
        try {
            Tuple contextTuple = getContextTuple(fieldName, filter, aggregator, groupByFieldNames);
            operationsMap.put(contextTuple, aggregator);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return this;
    }

    @Override
    public Map[] execute() {
        Map[] finalResult = new Map[operationsMap.size()];
        int index = 0;
        Logger logger = LogManagerFactory.getLogManager().getLogger(AsAggregator.class);
        try {
            // Iterate over the list of operations to be performed
            for (Map.Entry mEntry : operationsMap.entrySet()) {
                Class operator = (Class) mEntry.getValue();
                Tuple context = (Tuple) mEntry.getKey();

                // FAULT TOLERANCE
                InvokeResultList results = null;
                for (int i = 0; i < 5; i++) {
                    results = space.invokeSeeders(operator.getName(),InvokeOptions.create().setContext(context));

                    //logger.log(Level.DEBUG, "Aggregate operator class Name: "+operator.getName());
                    if (!results.hasError()) {
                        break;
                    } else {
                        //logger.log(Level.WARN,"Aggregate %s operation result has errors. Re-trying %s attempt.", operator.getName(), (i + 1));
                    }
                }
                //TODO: Put close statement or ask user to restart?
                Collection<Map<Object, Object>> nodeResultCollector = new ArrayList<Map<Object, Object>>();

                for (InvokeResult result : results) {
                    Tuple tmpTuple = result.getReturn();
                    //logger.log(Level.DEBUG,"Result Content=%s Tuple=%s", result, tmpTuple);
                    HashMap<Object, Object> tmpMap = (HashMap<Object, Object>) AsHelper.getResultData(tmpTuple);//, context);//, AsConstants.MIN_RESULT);
                    nodeResultCollector.add(tmpMap);
                }

                Aggregate aggregate;

                if (operator == AsMinAggregator.class) {
                    aggregate = new AsMinAggregator();
                } else if (operator == AsMaxAggregator.class) {
                    aggregate = new AsMaxAggregator();
                } else if (operator == AsCountAggregator.class) {
                    aggregate = new AsCountAggregator();
                } else if (operator == AsSumAggregator.class) {
                    aggregate = new AsSumAggregator();
                } else if (operator == AsAvgAggregator.class) {
                    aggregate = new AsAvgAggregator();
                } else {
                    aggregate = (Aggregate) operator.getConstructor().newInstance();
                }

                HashMap<Object, Object> resultMap = (HashMap<Object, Object>) aggregate.reduce(nodeResultCollector);//,context.getString(AsConstants.DATA_TYPE)); //context tuple is passed to check the datatype and also get the field name for uniquely identifying the result in the final map
                finalResult[index] = resultMap;
                index++;
            }
        } catch (RuntimeException r) {
            throw r;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return finalResult;
    }

    private Class translateToJava(String fieldName) {
        FieldDef fieldDef = spaceDef.getFieldDef(fieldName);
        DataType dataType = DataTypeRefMap.mapToDataType(fieldDef.getType());

        return dataType.getSupportedJavaClass();
    }

    private Tuple getContextTuple(String fieldName, String filter, Class c, String... groupByFieldNames) throws IOException {
        Class javaType = null;
        if (fieldName != null) {
            javaType = translateToJava(fieldName);
        }
        Tuple t = Tuple.create();
        UUID uuid = UUID.randomUUID();
        t.put(AsConstants.UID, uuid.toString());

        if (c != AsCountAggregator.class && javaType == Object.class) {//!tmpFieldMap.containsKey(fieldName)) {
            throw new IllegalArgumentException(fieldName);
        } else {
            if (fieldName != null && javaType != Object.class) {//tmpFieldMap.containsKey(fieldName)) {
                t.put(AsConstants.FIELD_NAME, fieldName);
                if (javaType == String.class) {
                    t.put(AsConstants.DATA_TYPE, TypeConstants.STRING);
                } else if (javaType == Integer.class) {
                    t.put(AsConstants.DATA_TYPE, TypeConstants.INTEGER);
                } else if (javaType == Float.class) {
                    t.put(AsConstants.DATA_TYPE, TypeConstants.FLOAT);
                } else if (javaType == Double.class) {
                    t.put(AsConstants.DATA_TYPE, TypeConstants.DOUBLE);
                } else if (javaType == Long.class) {
                    t.put(AsConstants.DATA_TYPE, TypeConstants.LONG);
                }
            }
            if (filter != null) {
                t.put(AsConstants.FIELD_FILTER, filter);
            }

            Serializer ser = new Serializer();
            if (groupByFieldNames.length > 0) {
                t.putBlob(AsConstants.GROUP_BY, ser.serialize(groupByFieldNames));
                ArrayList<String> groupByFieldList = new ArrayList<String>();

                for (String s : groupByFieldNames) {
                    groupByFieldList.add(s);
                }
                t.putBlob(AsConstants.GROUP_BY_LIST, ser.serialize(groupByFieldList));
            }
        }

        return t;
    }
}
