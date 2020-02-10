/** Copyright 2004 Tibco Software Inc. All rights reserved.
 * User: rogert
 * Date: Sep 9, 2004
 */

package com.tibco.cep.designtime.model;


public class ModelError {


    protected String m_errorMessage = null;
    protected Object m_invalid = null;
    protected boolean m_isWarning = false;


    public ModelError(
            Object invalidObject,
            String errorMessage) {
        m_invalid = invalidObject;
        m_errorMessage = errorMessage;
    }// end ModelError


    /**
     * Gets the object that is invalid.
     *
     * @return The Object that is invalid.
     */
    public Object getInvalidObject() {
        return m_invalid;
    }// end getEntity


    /**
     * Gets the error message describing why the associated Object is invalid.
     *
     * @return the error message describing why the associated Object is invalid.
     */
    public String getErrorMessage() {
        return m_errorMessage;
    }// end getErrorMessage


    /**
     * Sets the Object that is invalid.
     *
     * @param invalidObject
     */
    public void setInvalidObject(
            Object invalidObject) {
        m_invalid = invalidObject;
    }// end setEntity


    /**
     * Sets the error message describing why the associated Object is invalid.
     *
     * @param errorMessage the error message describing why the associated Object is invalid.
     */
    public void setErrorMessage(
            String errorMessage) {
        m_errorMessage = errorMessage;
    }// end setErrorMessage


    public boolean isWarning() {
        return m_isWarning;
    }


    public void setIsWarning(boolean isWarning) {
        m_isWarning = isWarning;
    }

}// end class ModelError
