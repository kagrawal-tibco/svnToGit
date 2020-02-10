package com.tibco.cep.query.stream.cache;

import java.lang.ref.Reference;

import com.tibco.cep.query.stream.core.ControllableResource;

/*
 * Author: Ashwin Jayaprakash Date: Dec 17, 2007 Time: 1:42:46 PM
 */

public interface Cache extends ControllableResource {
    /**
     * Overwrites the value associated with this key, if the pair is present. Creates a new pair, if
     * no previous mapping was present.
     *
     * @param key
     * @param value
     * @return <code>null</code> or the previously bound value.
     */
    public Object put(Object key, Object value);

    public Object get(Object key);

    public Object remove(Object key);

    public int size();

    //------------

    /**
     * @return
     * @see #getInternalEntry(Object)
     */
    public boolean isGetInternalEntrySupported();

    /**
     * <p>Similar to {@link #get(Object)} but retrieves the internal entry used by the cache through
     * a {@link java.lang.ref.Reference}. Always use it through the reference and not by
     * de-referencing it.</p> <p>Optional implementation! If supported, {@link
     * #isGetInternalEntrySupported()} must return <code>true</code>.</p>
     *
     * @param key
     * @return Can be <code>null</code>.
     */
    public Reference<InternalEntry> getInternalEntry(Object key);

    //-------------

    /**
     * All accesses must be preceded by a call to {@link #isValid()}.
     */
    public interface InternalEntry {
        /**
         * @return <code>true</code> if this entry is still valid. Otherwise there is no guarantee
         *         on what the other methods will return/behave.
         */
        public boolean isValid();

        public Object getKey();

        public Object getValue();
    }
}
