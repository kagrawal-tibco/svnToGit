package com.tibco.store.query.exec;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 5/12/13
 * Time: 10:10 AM
 *
 * When we perform (set A intersect set B),
 * the criterion in terms of what needs to be matched for
 * deciding whether an element in A is also present in B.
 * <p>
 *     This is query specific and cannot be generalized
 *     to an equals implementation which is essentially static in nature.
 * </p>
 */
public interface IntersectionComparator<T> {

    /**
     * Every query join (and operator) will decide
     * what attributes in streams need to match.
     * @param attribute
     * @see com.tibco.store.query.model.ResultStream
     * @see com.tibco.store.persistence.model.MemoryTuple
     */
    public void addInterestAttribute(String attribute);

    /**
     * Perform criterion evaluation to decide whether
     * entry1 and entry2 are intersecting.
     */
    public boolean intersect(T entry1, T entry2);
}
