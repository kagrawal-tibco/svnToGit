package com.tibco.cep.kernel.model.entity;

/**
 * Created by IntelliJ IDEA.
 * User: nleong
 * Date: May 30, 2006
 * Time: 4:52:08 PM
 * To change this template use File | Settings | File Templates.
 */

/**
 * Base for mutable kernel classes.
 *
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Mutable {


    /**
     * Gets the dirty bit array.  This specifies what dependency is modified (dirty) for
     * this object.  A relative bit is set to 1 when something is modifed in the object
     * It will be used to mask with Identifier's dependencyBitArray for determining if
     * this dependency is changed and if the rule need to be re-evaluated.
     *
     * @return null means all dependencies changed.
     */
    int[] getDirtyBitArray();


    /**
     * Clears the modifiedBitArray.
     */
    void clearDirtyBitArray();

    /**
     * 
     */
    void clearChildrenDirtyBits();
}
