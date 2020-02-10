package com.tibco.cep.query.stream.tuple;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 2:48:23 PM
 */
public interface TupleInfo {
    public Class<? extends Tuple> getContainerClass();

    public Integer getColumnPosition(String alias);

    public String[] getColumnAliases();

    public Class[] getColumnTypes();

    /**
     * @param id The tuple must return this id in {@link com.tibco.cep.query.stream.tuple.Tuple#getId()}.
     * @return
     */
    public Tuple createTuple(Number id);
}
