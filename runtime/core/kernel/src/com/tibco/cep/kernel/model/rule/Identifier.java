package com.tibco.cep.kernel.model.rule;


import com.tibco.cep.kernel.model.entity.Mutable;

/*
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: Apr 13, 2006
 * Time: 2:14:34 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * An identifier in the declaration section of a rule, rule function, etc.
 *
 * @version 2.0.0
 * @.category public-api
 * @since 2.0.0
 */
public interface Identifier {


    /**
     * Gets the type of the identifier
     *
     * @return the <code>Class</code> type for this identifier
     * @.category public-api
     * @since 2.0.0
     */
    Class getType();


    /**
     * Gets the name of the identifier.
     *
     * @return the name of this identifier.
     * @.category public-api
     * @since 2.0.0
     */
    String getName();


    /**
     * Gets the dependency bit array.
     * This specifies the dependency of this identifier for the rule.
     * Use it to mask the <code>modifiedBitArray</code> of a <code>Mutable</code> and see if the result is non zero.
     * <ul>
     * <li>Non zero means something the identifier depends on is modified, the rule need to be re-evaluated.</li>
     * <li>null means depends on everything</li>
     * <li>0 size array means depends on nothing</li>
     * </ul>
     *
     * @return the dependency bit array
     * @since 2.0.0
     */
    int[] getDependencyBitArray();


    /**
     * Checks if mutable object has any dependency on this identifier.
     *
     * @param mutable object for the test
     * @param overrideDirtyBitArray override the dirtyBitArray of mutable for checking on dependency
     * @return true if there is a dependency, false otherwise.
     * @since 2.0.0
     */
    boolean hasDependency(Mutable mutable, int[] overrideDirtyBitArray);
}
