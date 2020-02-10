package com.tibco.cep.designtime.model;


import java.util.List;


/**
 * This interface encapsulates the requirements for determining whether or not an object conforms
 * to some set of guidelines.  Validatable objects can be tested for their validity, as well as the
 * validitiy of their "sub" objects, creating a validation hierarchy.  The validate() method causes
 * an object, and possibly its sub objects, to conform to its requirements.
 *
 * @author ishaan
 * @version Mar 18, 2004 1:42:40 PM
 */

public interface Validatable {


    /**
     * Checks if this portion of the data model is valid.
     *
     * @param recurse If true, isValid() is called on all sub-Validatables, and returns false if any are invalid.
     * @return True if valid, false otherwise.
     */
    public boolean isValid(boolean recurse);


    /**
     * Returns a message providing information about any validity failures in the data model.
     *
     * @return A String containing the validity message.
     */
    public String getStatusMessage();


    /**
     * Returns an array of invalid objects, including any sub-objects.
     *
     * @return An array containing any invalid (sub) objects.
     */
    public Validatable[] getInvalidObjects();


    /**
     * Returns a List of ModelError objects.  Each ModelError object contains
     * an invalid model object and a message describing why it is invalid.
     *
     * @return A List of ModelError objects.
     */
    public List getModelErrors();


    /**
     * Goes through and corrects the state the object.
     *
     * @param recurse If true, goes through and validates the sub objects contained by this object.
     */
    public void makeValid(boolean recurse);
}
