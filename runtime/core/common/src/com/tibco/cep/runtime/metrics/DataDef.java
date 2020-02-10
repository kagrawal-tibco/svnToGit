package com.tibco.cep.runtime.metrics;

/*
* Author: Ashwin Jayaprakash Date: Jan 23, 2009 Time: 6:27:18 PM
*/
public interface DataDef {
    ColumnDef[] getColumnDefs();

    public interface ColumnDef {
        String getName();

        /**
         * Optionally - can be composite of {@link #getNestedTypes() nested} types.
         *
         * @return
         */
        Class getType();

        /**
         * @return Can be <code>null</code>.
         */
        ColumnDef[] getNestedTypes();
    }
}
