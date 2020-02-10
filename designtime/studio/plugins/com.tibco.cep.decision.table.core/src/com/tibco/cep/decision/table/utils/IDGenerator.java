package com.tibco.cep.decision.table.utils;

/**
 * An interface which can generate an unique ID.
 */
public interface IDGenerator<T> {
    /**
     * Gets a new ID.
     *
     * @return a new ID
     */
    T getNextID();
    
    T getCurrentID();

   

    /**
     * Releases the old ID.
     *
     * @param oldID old ID
     */
    void releaseID(T oldID);

    /**
     * Releases all IDs.
     */
    void releaseAll();

    /**
     * Reserve an ID. The ID is used before but for some reason DockID lost track of it (such as application is
     * restarted), this method will let DockID reserve it.
     *
     * @param id ID to be reserved
     * @return true if reserved successfully. Otherwise false.
     */
    boolean reserveID(T id);
    
    void setID(T id);
    
    void reset();
    
    /**
     * set an id created from previous operation
     * @return
     */
    void restorePreviousID();
}
