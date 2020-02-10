package com.tibco.cep.runtime.model.element.impl.property;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.tibco.cep.runtime.model.element.impl.property.history.ImplSerializerHelper;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyArrayImpl;
import com.tibco.cep.runtime.model.element.impl.property.history.PropertyAtomImpl;

public class AbstractPropertyImplSerializerHelper {
    public static void serialize(PropertyArrayImpl pa, DataOutput dout) throws IOException {
        AbstractPropertyAtom [] atoms = pa.m_properties;
        if (atoms != null) {
            dout.writeInt(atoms.length);
            for (int i = 0; i < atoms.length; i++) {
            	ImplSerializerHelper.serialize((PropertyAtomImpl)atoms[i], dout);
            }
        } else
            dout.writeInt(0);
    }

    public static void deserialize(PropertyArrayImpl arr, DataInput din) throws IOException {
        int numatoms = din.readInt();
        PropertyAtomImpl[] atoms = null;

        if (numatoms != 0) {
            atoms = new PropertyAtomImpl[numatoms];
            for (int i = 0; i < numatoms; i++) {
                ImplSerializerHelper.deserialize(atoms, i, din, arr);
            }
        }

        arr.m_properties = atoms;
    }
}
