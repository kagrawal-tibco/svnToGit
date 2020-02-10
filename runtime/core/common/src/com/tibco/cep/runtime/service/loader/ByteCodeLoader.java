package com.tibco.cep.runtime.service.loader;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Nov 7, 2007
 * Time: 9:58:46 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ByteCodeLoader {

    /**
     * Returns the parent ByteCodeLoader
     * @return  ByteCodeLoader
     */
    ByteCodeLoader getParentLoader();

    /**
     * Extracts bytecode of runtime classes loaded by the classloader.
     * @param classname
     * @return  byte[]
     */
    byte[] getByteCode(String classname);

    /**
     *  Returns a collectioo of classes loaded by the classloader
     * @return  Collection
     */
    public Collection getClasses();

    /**
     * Returns true if the specified class is known to the loader else false
     * @param classname
     * @return boolean
     */
    boolean containsClass(String classname);

    /**
     * returns the number of classes in the loader
     * @return int
     */
    int size();
}
