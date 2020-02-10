package com.tibco.cep.runtime.model.serializers._migration_;

import com.tibco.cep.runtime.session.ServiceLocator;

import java.io.DataInput;
import java.util.HashMap;
import java.util.Map;

/*
* Author: Ashwin Jayaprakash Date: Jan 19, 2009 Time: 1:34:28 PM
*/
public class ConversionScratchpad {
    protected DataInput fromDataInput;

    protected HashMap<ConversionKey, Object> intermediateData;

    protected HashMap<ConversionKey, Map<ConversionKey, Object>> extraIntermediateData;

    protected Object toObject;

    protected ServiceLocator locator;

    protected TypeConverter typeConverter;

    protected ConversionLog conversionLog;

    //------------

    public void prepare(ServiceLocator locator, TypeConverter typeConverter,
                      ConversionLog conversionLog, DataInput fromDataInput) {
        this.locator = locator;

        this.fromDataInput = fromDataInput;

        this.typeConverter = typeConverter;

        this.conversionLog = conversionLog;

        if (intermediateData == null) {
            intermediateData = new HashMap<ConversionKey, Object>();
        }
    }

    public void clear() {
        fromDataInput = null;

        if (intermediateData != null) {
            intermediateData.clear();
        }

        if (extraIntermediateData != null) {
            extraIntermediateData.clear();
        }

        toObject = null;
    }

    /**
     * Invoke {@link #clear()} first.
     */
    public void discard() {
        typeConverter = null;

        conversionLog = null;

        locator = null;
    }

    //------------

    public ConversionLog getConversionLog() {
        return conversionLog;
    }

    public TypeConverter getTypeConverter() {
        return typeConverter;
    }

    public DataInput getFromDataInput() {
        return fromDataInput;
    }

    public HashMap<ConversionKey, Object> getIntermediateData() {
        return intermediateData;
    }

    public HashMap<ConversionKey, Map<ConversionKey, Object>> getExtraIntermediateData() {
        return extraIntermediateData;
    }

    public Object getToObject() {
        return toObject;
    }

    public ServiceLocator getLocator() {
        return locator;
    }

    public void setToObject(Object toObject) {
        this.toObject = toObject;
    }

    //------------

    @SuppressWarnings({"unchecked"})
    public <T> T getIntermediateDatum(ConversionKey<T> key) {
        return (T) intermediateData.get(key);
    }

    @SuppressWarnings({"unchecked"})
    public <T> T removeIntermediateDatum(ConversionKey<T> key) {
        return (T) intermediateData.remove(key);
    }

    public <T> void addIntermediateDatum(ConversionKey<T> key, T value) {
        intermediateData.put(key, value);
    }
}
