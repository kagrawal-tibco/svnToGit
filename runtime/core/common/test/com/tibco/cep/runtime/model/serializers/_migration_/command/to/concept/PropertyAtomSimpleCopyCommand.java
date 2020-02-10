package com.tibco.cep.runtime.model.serializers._migration_.command.to.concept;

import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.model.element.impl.ConceptImpl;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionKey;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionScratchpad;
import com.tibco.cep.runtime.model.serializers._migration_.ModelType;
import com.tibco.cep.runtime.model.serializers._migration_.TypeConverter;
import com.tibco.cep.runtime.model.serializers._migration_.command.Command;

/*
* Author: Ashwin Jayaprakash Date: Jan 21, 2009 Time: 1:12:19 PM
*/
public class PropertyAtomSimpleCopyCommand implements Command {
    protected String fromPropertyName;

    protected ModelType fromPropertyType;

    protected String toPropertyName;

    protected ModelType toPropertyType;

    public String getFromPropertyName() {
        return fromPropertyName;
    }

    public void setFromPropertyName(String fromPropertyName) {
        this.fromPropertyName = fromPropertyName;
    }

    public ModelType getFromPropertyType() {
        return fromPropertyType;
    }

    public void setFromPropertyType(ModelType fromPropertyType) {
        this.fromPropertyType = fromPropertyType;
    }

    public String getToPropertyName() {
        return toPropertyName;
    }

    public void setToPropertyName(String toPropertyName) {
        this.toPropertyName = toPropertyName;
    }

    public ModelType getToPropertyType() {
        return toPropertyType;
    }

    public void setToPropertyType(ModelType toPropertyType) {
        this.toPropertyType = toPropertyType;
    }

    @SuppressWarnings({"unchecked"})
    public void execute(ConversionScratchpad scratchpad) throws Exception {
        Class fromDataType = fromPropertyType.getContainerDataType();

        ConversionKey key = new ConversionKey(fromPropertyName, fromDataType);
        Object fromProperty = scratchpad.removeIntermediateDatum(key);
        if (fromProperty == null) {
            return;
        }

        TypeConverter typeConverter = scratchpad.getTypeConverter();

        ConceptImpl concept = (ConceptImpl) scratchpad.getToObject();
        PropertyAtom propertyAtom = concept.getPropertyAtom(toPropertyName);
        if (propertyAtom != null) {
            Object internalConvertedValue = typeConverter
                    .extractAndConvertToInternal(fromPropertyType, fromProperty, toPropertyType);

            propertyAtom.setValue(internalConvertedValue);
        }
    }
}
