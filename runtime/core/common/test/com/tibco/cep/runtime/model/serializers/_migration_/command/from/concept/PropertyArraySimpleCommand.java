package com.tibco.cep.runtime.model.serializers._migration_.command.from.concept;

import com.tibco.cep.runtime.model.serializers._migration_.ModelType;
import com.tibco.cep.runtime.model.element.impl.property.simple.*;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionKey;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionScratchpad;

import java.io.DataInput;

/*
* Author: Ashwin Jayaprakash Date: Jan 19, 2009 Time: 3:00:40 PM
*/
public class PropertyArraySimpleCommand extends PropertyAtomSimpleCommand {
    protected ModelType propertyArrayType;

    public PropertyArraySimpleCommand() {
    }

    public ModelType getPropertyArrayType() {
        return propertyArrayType;
    }

    public void setPropertyArrayType(ModelType propertyArrayType) {
        this.propertyArrayType = propertyArrayType;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public void execute(ConversionScratchpad scratchpad) throws Exception {
        DataInput dataInput = scratchpad.getFromDataInput();

        int length = dataInput.readInt();
        if (length <= 0) {
            return;
        }

        AtomData<? extends PropertyArraySimple> arrayAtom = createArray(length);
        ConversionKey key = arrayAtom.getKey();
        PropertyArraySimple value = arrayAtom.getValue();

        for (int i = 0; i < length; i++) {
            AtomData atomData = readAtom(dataInput, scratchpad);

            if (atomData != null) {
                Object arrayElement = atomData.getValue();

                value.add(i, arrayElement);
            }
        }

        scratchpad.addIntermediateDatum(key, value);
    }

    protected AtomData<? extends PropertyArraySimple> createArray(int length) {
        switch (propertyArrayType) {
            case PROPERTY_ARRAY_BOOLEAN:
                return new AtomData<PropertyArrayBooleanSimple>(propertyName,
                        PropertyArrayBooleanSimple.class,
                        new PropertyArrayBooleanSimple(null, length));

            case PROPERTY_ARRAY_CONCEPT_REFERENCE:
                return new AtomData<PropertyArrayConceptReferenceSimple>(propertyName,
                        PropertyArrayConceptReferenceSimple.class,
                        new PropertyArrayConceptReferenceSimple(null, length));

            case PROPERTY_ARRAY_CONTAINED_CONCEPT:
                return new AtomData<PropertyArrayContainedConceptSimple>(propertyName,
                        PropertyArrayContainedConceptSimple.class,
                        new PropertyArrayContainedConceptSimple(null, length));

            case PROPERTY_ARRAY_DATETIME:
                return new AtomData<PropertyArrayDateTimeSimple>(propertyName,
                        PropertyArrayDateTimeSimple.class,
                        new PropertyArrayDateTimeSimple(null, length));

            case PROPERTY_ARRAY_DOUBLE:
                return new AtomData<PropertyArrayDoubleSimple>(propertyName,
                        PropertyArrayDoubleSimple.class,
                        new PropertyArrayDoubleSimple(null, length));

            case PROPERTY_ARRAY_INT:
                return new AtomData<PropertyArrayIntSimple>(propertyName,
                        PropertyArrayIntSimple.class, new PropertyArrayIntSimple(null, length));

            case PROPERTY_ARRAY_LONG:
                return new AtomData<PropertyArrayLongSimple>(propertyName,
                        PropertyArrayLongSimple.class, new PropertyArrayLongSimple(null, length));

            case PROPERTY_ARRAY_STRING:
                return new AtomData<PropertyArrayStringSimple>(propertyName,
                        PropertyArrayStringSimple.class,
                        new PropertyArrayStringSimple(null, length));

            default:
                throw new IllegalArgumentException("The property array type [" + propertyArrayType +
                        "] is not supported.");
        }
    }
}