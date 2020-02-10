package com.tibco.cep.studio.ui.preferences.classpathvar;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IPath;

import com.tibco.cep.studio.core.StudioCore;
import com.tibco.cep.studio.ui.util.ClasspathVariableUiUtils;


public class ClasspathVariableElement {

	private String fName;
	private IPath fPath;

	/**
	 * @param name the variable name
	 * @param path the path
	 */
	public ClasspathVariableElement(String name, IPath path) {
		Assert.isNotNull(name);
		Assert.isNotNull(path);
		fName= name;
		fPath= path;
	}

	/**
	 * Gets the path
	 * @return Returns a IPath
	 */
	public IPath getPath() {
		return fPath;
	}

	/**
	 * Sets the path
	 * @param path The path to set
	 */
	public void setPath(IPath path) {
		Assert.isNotNull(path);
		fPath= path;
	}

	/**
	 * Gets the name
	 * @return Returns a String
	 */
	public String getName() {
		return fName;
	}

	/**
	 * Sets the name
	 * @param name The name to set
	 */
	public void setName(String name) {
		Assert.isNotNull(name);
		fName= name;
	}

	/*
	 * @see Object#equals()
	 */
	@Override
	public boolean equals(Object other) {
		if (other != null && other.getClass().equals(getClass())) {
			ClasspathVariableElement elem= (ClasspathVariableElement)other;
			return fName.equals(elem.fName);
		}
		return false;
	}

	/*
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return fName.hashCode();
	}

	/**
	 * @return <code>true</code> iff variable is read-only
	 */
	public boolean isReadOnly() {
		return StudioCore.isClasspathVariableReadOnly(fName);
	}

	/**
	 * @return whether this variable is deprecated
	 */
	public boolean isDeprecated() {
		return StudioCore.getClasspathVariableDeprecationMessage(fName) != null;
	}

	/**
	 * @return the deprecation message, or <code>null</code> iff the variable is not deprecated
	 */
	public String getDeprecationMessage() {
		return ClasspathVariableUiUtils.getDeprecationMessage(fName);
	}

}