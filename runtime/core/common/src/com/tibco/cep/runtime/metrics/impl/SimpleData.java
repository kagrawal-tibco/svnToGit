package com.tibco.cep.runtime.metrics.impl;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

import com.tibco.cep.runtime.metrics.Data;

/*
* Author: Ashwin Jayaprakash Date: Jan 23, 2009 Time: 6:30:12 PM
*/
public class SimpleData implements Data, Externalizable {
    protected Object[] columns;

    public SimpleData() {
    }

    public SimpleData(Object column) {
        this.columns = new Object[]{column};
    }

    public SimpleData(Object... columns) {
        this.columns = columns;
    }

    public Object[] getColumns() {
        return columns;
    }

    //-----------

    public void writeExternal(ObjectOutput out) throws IOException {
        if (columns == null) {
            out.writeInt(0);

            return;
        }

        out.writeInt(columns.length);
        for (Object column : columns) {
            out.writeObject(column);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int length = in.readInt();
        if (length == 0) {
            return;
        }

        columns = new Object[length];
        for (int i = 0; i < length; i++) {
            columns[i] = in.readObject();
        }
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SimpleData [columns=");
		builder.append(Arrays.toString(columns));
		builder.append("]");
		return builder.toString();
	}

}
