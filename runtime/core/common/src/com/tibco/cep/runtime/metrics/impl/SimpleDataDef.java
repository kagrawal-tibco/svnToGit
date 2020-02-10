package com.tibco.cep.runtime.metrics.impl;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

import com.tibco.cep.runtime.metrics.DataDef;

/*
* Author: Ashwin Jayaprakash Date: Jan 23, 2009 Time: 6:29:08 PM
*/
public class SimpleDataDef implements DataDef, Externalizable {
    protected ColumnDef[] columnDefs;

    public SimpleDataDef() {
    }

    public SimpleDataDef(ColumnDef... columnDefs) {
        this.columnDefs = columnDefs;
    }

    public ColumnDef[] getColumnDefs() {
        return columnDefs;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        if (columnDefs == null) {
            out.writeInt(0);

            return;
        }

        //-----------

        out.writeInt(columnDefs.length);

        for (ColumnDef columnDef : columnDefs) {
            out.writeObject(columnDef);
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int x = in.readInt();
        if (x == 0) {
            return;
        }

        columnDefs = new ColumnDef[x];
        for (int i = 0; i < columnDefs.length; i++) {
            columnDefs[i] = (ColumnDef) in.readObject();
        }
    }

    @Override
    public String toString() {
    	return Arrays.asList(columnDefs).toString();
    }

    //----------

    public static class SimpleColumnDef implements ColumnDef, Externalizable {
        protected String name;

        protected Class type;

        protected ColumnDef[] nestedTypes;

        public SimpleColumnDef() {
        }

        public SimpleColumnDef(String name, Class type) {
            this.name = name;
            this.type = type;
        }

        /**
         * @param name
         * @param type
         * @param nestedTypes
         */
        public SimpleColumnDef(String name, Class type, ColumnDef... nestedTypes) {
            this.name = name;
            this.type = type;

            if (nestedTypes.length > 0) {
                this.nestedTypes = nestedTypes;
            }
        }

        public String getName() {
            return name;
        }

        public Class getType() {
            return type;
        }

        public ColumnDef[] getNestedTypes() {
            return nestedTypes;
        }

        public void writeExternal(ObjectOutput out) throws IOException {
            out.writeUTF(name);
            out.writeObject(type);
            out.writeObject(nestedTypes);
        }

        public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
            name = in.readUTF();
            type = (Class) in.readObject();
            nestedTypes = (ColumnDef[]) in.readObject();
        }

        @Override
        public String toString() {
        	StringBuilder sb = new StringBuilder("DataDef[name="+name);
        	sb.append(",type="+type.toString());
        	if (nestedTypes != null) {
        		sb.append(",childtypes="+type.toString());
        		for (ColumnDef nestedType : nestedTypes) {
					sb.append(nestedType.toString());
				}
        	}
        	return sb.toString();
        }
    }
}
