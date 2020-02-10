package com.tibco.cep.interpreter;

import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.utils.TypeHelper;


/**
 * User: nprade
 * Date: 2/7/12
 * Time: 11:03 AM
 */
public class SimpleTuple
    implements Tuple {

    private Object[] columns;
    private Long timestamp;

    
    public SimpleTuple(
            Object o) {
        this.columns = new Object[]{o};
    }


    @Override
    public void decrementRefCount() {}


    @Override
    public Object getColumn(
            int index) {

        return this.columns[index];
    }


    @Override
    public double getColumnAsDouble(
            int index) {

        return TypeHelper.toDouble(this.columns[index]);//todo remove dependency on query
    }


    @Override
    public float getColumnAsFloat(
            int index) {

        return TypeHelper.toFloat(this.columns[index]);
    }


    @Override
    public int getColumnAsInteger(
            int index) {

        return TypeHelper.toInteger(this.columns[index]);
    }


    @Override
    public long getColumnAsLong(
            int index) {

        return TypeHelper.toLong(this.columns[index]);
    }


    @Override
    public Number getId() {
        return 0;
    }


    @Override
    public Object[] getRawColumns() {
        return this.columns;
    }


    @Override
    public Long getTimestamp() {
        return this.timestamp;
    }


    @Override
    public void incrementRefCount() {}


    @Override
    public void setColumns(
            Object[] columns) {

        this.columns = columns;
    }


    @Override
    public void setTimestamp(
            Long timestamp) {

        this.timestamp = timestamp;
    }
}
