package com.tibco.cep.query.stream.cache;

/*
 * Author: Ashwin Jayaprakash Date: Mar 17, 2008 Time: 6:54:33 PM
 */

public interface SharedObjectSource {
    public Object fetch(Object key, boolean refreshFromSource);

    public void purge(Object key);

    public void softPurge(Object key);

    public void discard();

    //----------

    /**
     * <p>Similar to {@link #fetch(Object, boolean)} but the return value is either the value itself
     * like - {@link #fetch(Object, boolean)} or {@link java.lang.ref.Reference} that points to
     * {@link com.tibco.cep.query.stream.cache.Cache.InternalEntry} which is returned by the {@link
     * com.tibco.cep.query.stream.cache.Cache}.</p> <p>{@link java.lang.ref.Reference} is only on
     * the {@link #getPrimaryCache() primary-cache}, provided it is {@link
     * Cache#isGetInternalEntrySupported() supported}.</p>
     *
     * @param key
     * @param refreshFromSource
     * @return
     */
    public Object fetchValueOrInternalEntryRef(Object key, boolean refreshFromSource);

    /**
     * @return The actual source. Could very well be self. Implementation dependent.
     */
    public Object getInternalSource();

    /**
     * Optional implementation.
     *
     * @return Can be <code>null</code>.
     */
    public Cache getPrimaryCache();

    /**
     * Optional implementation.
     *
     * @return Can be <code>null</code>.
     */
    public Cache getDeadPoolCache();
}
