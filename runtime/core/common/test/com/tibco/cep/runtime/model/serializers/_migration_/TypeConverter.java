package com.tibco.cep.runtime.model.serializers._migration_;

/*
* Author: Ashwin Jayaprakash Date: Jan 21, 2009 Time: 4:04:07 PM
*/
public interface TypeConverter {
    /**
     * @param fromType
     * @param fromObject
     * @param toType
     * @return Converted data of the type - {@link ModelType#getInternalDataType()}.
     */
    Object extractAndConvertToInternal(ModelType fromType, Object fromObject, ModelType toType);
}
