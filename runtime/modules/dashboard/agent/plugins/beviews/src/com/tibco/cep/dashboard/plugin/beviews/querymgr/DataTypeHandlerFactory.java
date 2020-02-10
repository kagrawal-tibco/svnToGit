package com.tibco.cep.dashboard.plugin.beviews.querymgr;

import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.dashboard.common.data.BuiltInTypes;
import com.tibco.cep.dashboard.common.data.DataType;

/**
 * @author apatil
 *
 */
public class DataTypeHandlerFactory {

    private static DataTypeHandlerFactory instance = null;

    public static final synchronized DataTypeHandlerFactory getInstance() {
        if (instance == null) {
            instance = new DataTypeHandlerFactory();
        }
        return instance;
    }

    private Map<String,DataTypeHandler> dataTypeToHandlerMap;

    private DataTypeHandlerFactory() {
        dataTypeToHandlerMap = new HashMap<String,DataTypeHandler>(8);
        dataTypeToHandlerMap.put(BuiltInTypes.BOOLEAN.getDataTypeID(), new BooleanDataTypeHandler());
        dataTypeToHandlerMap.put(BuiltInTypes.INTEGER.getDataTypeID(), new NumericDataTypeHandler(BuiltInTypes.INTEGER));
        dataTypeToHandlerMap.put(BuiltInTypes.LONG.getDataTypeID(), new NumericDataTypeHandler(BuiltInTypes.LONG));
        dataTypeToHandlerMap.put(BuiltInTypes.FLOAT.getDataTypeID(), new NumericDataTypeHandler(BuiltInTypes.FLOAT));
        dataTypeToHandlerMap.put(BuiltInTypes.DOUBLE.getDataTypeID(), new NumericDataTypeHandler(BuiltInTypes.DOUBLE));
        dataTypeToHandlerMap.put(BuiltInTypes.SHORT.getDataTypeID(), new NumericDataTypeHandler(BuiltInTypes.SHORT));
        dataTypeToHandlerMap.put(BuiltInTypes.DATETIME.getDataTypeID(), new DateDataTypeHandler());
        dataTypeToHandlerMap.put(BuiltInTypes.STRING.getDataTypeID(), new StringDataTypeHandler());
    }

    public final DataTypeHandler getDataTypeHandler(String dataTypeID){
        return dataTypeToHandlerMap.get(dataTypeID);
    }

    public final DataTypeHandler getDataTypeHandler(DataType dataType){
    	return getDataTypeHandler(dataType.getDataTypeID());
    }
}
