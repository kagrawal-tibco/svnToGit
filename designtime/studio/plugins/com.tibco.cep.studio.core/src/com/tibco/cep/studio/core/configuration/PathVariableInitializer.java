package com.tibco.cep.studio.core.configuration;

import com.tibco.cep.studio.core.StudioCore;


public abstract class PathVariableInitializer {


    /**
     * Creates a new classpath variable initializer.
     */
    public PathVariableInitializer() {
    	// a classpath variable initializer must have a public 0-argument constructor
    }

    /**
     * Binds a value to the workspace classpath variable with the given name,
     * or fails silently if this cannot be done. 
     * <p>
     * A variable initializer is automatically activated whenever a variable value
     * is needed and none has been recorded so far. The implementation of
     * the initializer can set the corresponding variable using 
     * <code>StudioCore#setClasspathVariable</code>.
     * 
     * @param variable the name of the workspace classpath variable
     *    that requires a binding
     * 
     * @see StudioCore#getClasspathVariable(String)
     * @see StudioCore#setClasspathVariable(String, org.eclipse.core.runtime.IPath, org.eclipse.core.runtime.IProgressMonitor)
     * @see StudioCore#setClasspathVariables(String[], org.eclipse.core.runtime.IPath[], org.eclipse.core.runtime.IProgressMonitor)
     */
    public abstract void initialize(String variable);
}