package com.tibco.cep.util;

import java.util.concurrent.atomic.AtomicReference;


/**
 * This class is modeled very similar on the AtomicStampedReference, except that it contains long instead of int.
 */
public class AtomicLongReferencePair<V> {
    private static class ReferenceLongPair<T> {
        private final T reference;
        private final long stamp;
        ReferenceLongPair(T r, long l) {
            reference = r; stamp = l;
        }
    }


    private final AtomicReference<ReferenceLongPair<V>> atomicRef;


    public AtomicLongReferencePair(V initialRef, long initialStamp) {
        atomicRef = new AtomicReference<ReferenceLongPair<V>>
            (new ReferenceLongPair<V>(initialRef, initialStamp));
    }

    public V getReference() {
        return atomicRef.get().reference;
    }


    public long getStamp() {
        return atomicRef.get().stamp;
    }



    /**
     * Atomically sets the value of both the reference and stamp
     * to the given update values if the
     * current reference is <tt>==</tt> to the expected reference
     * and the current stamp is equal to the expected stamp.
     *
     * @param expectedReference the expected value of the reference
     * @param newReference the new value for the reference
     * @param expectedStamp the expected value of the stamp
     * @param newStamp the new value for the stamp
     * @return true if successful
     */
    public boolean compareAndSet(V expectedReference, V  newReference,  long expectedStamp, long newStamp) {
        ReferenceLongPair current = atomicRef.get();
        return  expectedReference == current.reference &&
            expectedStamp == current.stamp &&
            ((newReference == current.reference &&
              newStamp == current.stamp) ||
             atomicRef.compareAndSet(current, new ReferenceLongPair<V>(newReference,newStamp)));
    }


    /**
     * Unconditionally sets the value of both the reference and stamp.
     *
     * @param newReference the new value for the reference
     * @param newStamp the new value for the stamp
     */
    public void set(V newReference, int newStamp) {
        ReferenceLongPair current = atomicRef.get();
        if (newReference != current.reference || newStamp != current.stamp)
            atomicRef.set(new ReferenceLongPair<V>(newReference, newStamp));
    }


}