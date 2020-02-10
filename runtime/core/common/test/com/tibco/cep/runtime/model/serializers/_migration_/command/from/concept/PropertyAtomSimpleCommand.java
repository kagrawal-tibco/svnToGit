package com.tibco.cep.runtime.model.serializers._migration_.command.from.concept;

import com.tibco.cep.runtime.model.element.DateTimeTuple;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.DateTimeTupleImpl;
import com.tibco.cep.runtime.model.element.impl.Reference;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionKey;
import com.tibco.cep.runtime.model.serializers._migration_.ConversionScratchpad;
import com.tibco.cep.runtime.model.serializers._migration_.ModelType;
import com.tibco.cep.runtime.model.serializers._migration_.command.Command;

import java.io.DataInput;
import java.io.IOException;

/*
* Author: Ashwin Jayaprakash Date: Jan 19, 2009 Time: 3:00:40 PM
*/
public class PropertyAtomSimpleCommand implements Command {
    protected String propertyName;

    protected ModelType propertyAtomType;

    public PropertyAtomSimpleCommand() {
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public ModelType getPropertyAtomType() {
        return propertyAtomType;
    }

    public void setPropertyAtomType(ModelType propertyAtomType) {
        this.propertyAtomType = propertyAtomType;
    }

    public void execute(ConversionScratchpad scratchpad) throws Exception {
        DataInput dataInput = scratchpad.getFromDataInput();

        if (dataInput.readBoolean()) {
            AtomData atomData = readAtom(dataInput, scratchpad);

            if (atomData != null) {
                scratchpad.addIntermediateDatum(atomData.getKey(), atomData.getValue());
            }
        }
    }

    /**
     * @param dataInput
     * @param scratchpad
     * @return Can be <code>null</code>.
     * @throws IOException
     */
    protected AtomData readAtom(DataInput dataInput, ConversionScratchpad scratchpad)
            throws IOException {
        switch (propertyAtomType) {
            case PROPERTY_ATOM_BOOLEAN: {
                boolean b = dataInput.readBoolean();

                return new AtomData<Boolean>(propertyName, Boolean.class, b);
            }

            case PROPERTY_ATOM_CONCEPT_REFERENCE:
            case PROPERTY_ATOM_CONTAINED_CONCEPT: {
                if (dataInput.readBoolean()) {
                    long refId = dataInput.readLong();
                    Reference reverseRef = new Reference(refId);

                    return new AtomData<ConceptOrReference>(propertyName, ConceptOrReference.class,
                            reverseRef);
                }
            }

            case PROPERTY_ATOM_DATETIME: {
                DateTimeTuple tuple = null;

                if (dataInput.readBoolean()) {
                    long l = dataInput.readLong();
                    String s = dataInput.readUTF();

                    tuple = new DateTimeTupleImpl(l, s);
                }
                else {
                    tuple = new DateTimeTupleImpl(0, null);
                }

                return new AtomData<DateTimeTuple>(propertyName, DateTimeTuple.class, tuple);
            }

            case PROPERTY_ATOM_DOUBLE: {
                double d = dataInput.readDouble();

                return new AtomData<Double>(propertyName, Double.class, d);
            }

            case PROPERTY_ATOM_INT: {
                int i = dataInput.readInt();

                return new AtomData<Integer>(propertyName, Integer.class, i);
            }

            case PROPERTY_ATOM_LONG: {
                long l = dataInput.readLong();

                return new AtomData<Long>(propertyName, Long.class, l);
            }

            case PROPERTY_ATOM_STRING: {
                String s = dataInput.readUTF();

                return new AtomData<String>(propertyName, String.class, s);
            }

            default:
                throw new IllegalArgumentException("The property type [" + propertyAtomType +
                        "] is not supported.");
        }
    }

    //---------

    protected static class AtomData<T> {
        protected ConversionKey<T> key;

        protected T value;

        public AtomData(String propertyName, Class<T> propertyType, T value) {
            this.key = new ConversionKey<T>(propertyName, propertyType);
            this.value = value;
        }

        public ConversionKey<T> getKey() {
            return key;
        }

        public T getValue() {
            return value;
        }
    }
}
