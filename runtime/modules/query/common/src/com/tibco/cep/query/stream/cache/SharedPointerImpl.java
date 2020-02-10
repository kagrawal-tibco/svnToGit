package com.tibco.cep.query.stream.cache;

import java.lang.ref.Reference;

/*
 * Author: Ashwin Jayaprakash Date: Dec 17, 2007 Time: 2:56:45 PM
 */

public class SharedPointerImpl implements SharedPointer {
    protected Reference<Cache.InternalEntry> quickRef;

    protected Object key;

    protected SharedObjectSource source;

    public SharedPointerImpl(Object key, SharedObjectSource source) {
        this.key = key;
        this.source = source;
    }

    public SharedObjectSource getSource() {
        return source;
    }

    public Object getKey() {
        return key;
    }

    public Object getObject() {
        for (; ;) {
            if (quickRef != null) {
                Cache.InternalEntry internalEntry = quickRef.get();

                if (internalEntry != null) {
                    /*
                    Get the value before checking if its valid, because the thread could bumped
                    off in-between and the entry could get invalidated during that time - if it is
                    done the other way round.
                     */
                    Object value = internalEntry.getValue();

                    if (internalEntry.isValid()) {
                        return value;
                    }
                }

                //This ref is outdated then. Clear it.
                quickRef = null;
            }

            //------------

            Object valueOrRef = source.fetchValueOrInternalEntryRef(key, false);

            if (valueOrRef != null && valueOrRef instanceof Reference) {
                quickRef = (Reference<Cache.InternalEntry>) valueOrRef;

                /*
                Now that we have the reference, it has to be de-referenced correctly such that
                if it gets invalidated by the time we were fetching it from the "source" it will be
                handled correctly.
                */
                continue;
            }

            return valueOrRef;
        }
    }

    /**
     * Uses {@link Object#hashCode()}.
     */
    @Override
    public final int hashCode() {
        return super.hashCode();
    }

    /**
     * Uses {@link Object#equals(Object)}.
     */
    @Override
    public final boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Key: " + getKey() + ", Source: " + source;
    }
}
