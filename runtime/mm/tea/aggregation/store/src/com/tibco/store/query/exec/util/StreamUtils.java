package com.tibco.store.query.exec.util;

import com.google.common.collect.Sets;
import com.tibco.store.persistence.model.MemoryTuple;
import com.tibco.store.query.exec.IntersectionComparator;
import com.tibco.store.query.model.ResultStream;
import com.tibco.store.query.model.impl.MutableResultStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 3/12/13
 * Time: 3:39 PM
 *
 * Utility functions to perform set operations on one or more streams.
 */
public class StreamUtils {

    /**
     *
     * @param resultStreams
     * @param <R>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <R extends ResultStream> R join(R... resultStreams) {
        if (resultStreams == null) {
            throw new IllegalArgumentException("Result streams cannot be null");
        }
        
        Collection<MemoryTuple> containedTuples = new HashSet<MemoryTuple>();
        
       /* Collection<MemoryTuple> containedTuples = new ArrayList<MemoryTuple>();*/
        for (R otherStream : resultStreams) {
            /*containedTuples.addAll(otherStream.getTuples());*/
        	containedTuples.addAll(otherStream.getTuples());
        }
        
        R resultantStream = (R) new MutableResultStream();
        resultantStream.addMemoryTuples(containedTuples);
        return resultantStream;
    }

    /**
     *
     * @param stream1
     * @param stream2
     * @param intersectComparator
     * @param <R>
     * @param <I>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <R extends ResultStream, I extends IntersectionComparator<MemoryTuple>> R intersect(R stream1, R stream2, I intersectComparator) {
        if (stream1 == null || stream2 == null) {
            throw new IllegalArgumentException("Input Result streams cannot be null");
        }
//        List<IntersectableMemoryTuple> containedTuples = new ArrayList<IntersectableMemoryTuple>();
//        Set<IntersectableMemoryTuple> stream1TupleSet = new HashSet<IntersectableMemoryTuple>();

//        for (MemoryTuple memoryTuple : stream1.getTuples()) {
//            stream1TupleSet.add(new IntersectableMemoryTuple())
//        }

        /**
         * The naive way of finding intersection does it in O(n * n)
         * TODO check if some form of hashing can help here
         */
//        for (MemoryTuple memoryTuple1 : stream1.getTuples()) {
//            for (MemoryTuple memoryTuple2 : stream2.getTuples()) {
//                if (intersectComparator.intersect(memoryTuple1, memoryTuple2)) {
//                    containedTuples.add(memoryTuple1);
//                }
//            }
//        }
        R resultantStream = (R) new MutableResultStream();
//        resultantStream.addMemoryTuples(containedTuples);
        return resultantStream;
    }

    /**
     *
     * @param stream1
     * @param stream2
     * @param <R>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <R extends ResultStream> R union(R stream1, R stream2) {
        if (stream1 == null || stream2 == null) {
            throw new IllegalArgumentException("Input Result streams cannot be null");
        }

        Collection<MemoryTuple> collection1 = stream1.getTuples();
        HashSet<MemoryTuple> unionSetView =
                (collection1 instanceof HashSet) ? (HashSet) collection1 : Sets.newHashSet(stream1.getTuples());
        unionSetView.addAll(stream2.getTuples());

        MutableResultStream resultStream = new MutableResultStream();
        resultStream.addMemoryTuples(unionSetView);
        return (R) resultStream;
    }
}
