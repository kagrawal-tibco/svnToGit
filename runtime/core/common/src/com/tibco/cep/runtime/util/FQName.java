package com.tibco.cep.runtime.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/*
* Author: Ashwin Jayaprakash Date: Feb 9, 2009 Time: 2:04:17 PM
*/
public class FQName implements Externalizable {
    /**
     * {@value}.
     */
    public static final char COLLATION_CHAR = ':';

    protected String[] componentNames;

    protected String collatedName;

    public FQName() {
    }

    public FQName(FQName parentFQN, String... componentNames) {
        ctorWork(concatenate(parentFQN.getComponentNames(), componentNames));
    }

    public FQName(String... componentNames) {
        ctorWork(componentNames);
    }

    private static String[] concatenate(String[] one, String[] two) {
        String[] combo = new String[one.length + two.length];

        int i = 0;

        for (String s : one) {
            combo[i++] = s;
        }

        for (String s : two) {
            combo[i++] = s;
        }

        return combo;
    }

    private void ctorWork(String... componentNames) {
        this.componentNames = componentNames;

        StringBuilder builder = new StringBuilder(16);
        for (String componentName : componentNames) {
            if (builder.length() > 0) {
                builder.append(COLLATION_CHAR);
            }

            builder.append(componentName);
        }
        this.collatedName = builder.toString();
    }

    public String[] getComponentNames() {
        return componentNames;
    }

    public String getCollatedName() {
        return collatedName;
    }

    //------------

    public void writeExternal(ObjectOutput out) throws IOException {
        if (componentNames == null) {
            out.writeInt(0);

            return;
        }

        out.writeInt(componentNames.length);

        for (String componentName : componentNames) {
            out.writeUTF(componentName);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int length = in.readInt();
        if (length == 0) {
            return;
        }

        String[] names = new String[length];
        for (int i = 0; i < length; i++) {
            names[i] = in.readUTF();
        }

        ctorWork(names);
    }

    @Override
    public String toString() {
        return collatedName;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof FQName)) {
            return false;
        }

        FQName fqName = (FQName) o;

        return collatedName.equals(fqName.collatedName);
    }

    public int hashCode() {
        return collatedName.hashCode();
    }
}
