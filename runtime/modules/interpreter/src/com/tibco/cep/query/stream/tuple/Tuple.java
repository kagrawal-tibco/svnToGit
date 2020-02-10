package com.tibco.cep.query.stream.tuple;

/*
 * Author: Ashwin Jayaprakash Date: Oct 1, 2007 Time: 2:31:38 PM
 */

/**
 * Use {@link #getId()} for {@link Object#hashCode()} and {@link Object#equals(Object)}. Nothing
 * else. This should not change once the object is created.
 */
public interface Tuple {
    /**
     * Optional.
     */
    public void incrementRefCount();

    /**
     * Optional.
     */
    public void decrementRefCount();

    /**
     * Should never return <code>null</code>.
     *
     * @return
     */
    public Number getId();

    /**
     * @return Each element could be wrapped and might not be a direct reference to the elements.
     * @see #getColumn(int)
     */
    public Object[] getRawColumns();

    //todo Need to get rid of these 2 set/getAll methods.
    public void setColumns(Object[] columns);

    /**
     * @param index
     * @return Individual column value. Direct reference to the object.
     */
    public Object getColumn(int index);

    /**
     * Optional.
     *
     * @param index
     * @return
     */
    public int getColumnAsInteger(int index);

    /**
     * Optional.
     *
     * @param index
     * @return
     */
    public long getColumnAsLong(int index);

    /**
     * Optional.
     *
     * @param index
     * @return
     */
    public float getColumnAsFloat(int index);

    /**
     * Optional.
     *
     * @param index
     * @return
     */
    public double getColumnAsDouble(int index);

    public Long getTimestamp();

    public void setTimestamp(Long timestamp);
}