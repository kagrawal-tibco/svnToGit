package com.tibco.cep.studio.core.util;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.ClasspathContainerInitializer;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.JavaCore;

public class ClassPathListElementAttribute {

	private String fKey;
	private Object fValue;
	private ClassPathListElement fParent;
	private boolean fBuiltIn;
	private IStatus fStatus;

	public ClassPathListElementAttribute(ClassPathListElement parent, String key, Object value, boolean builtIn) {
		fKey= key;
		fValue= value;
		fParent= parent;
		fBuiltIn= builtIn;
		fBuiltIn= builtIn;
		if (!builtIn) {
			Assert.isTrue(value instanceof String || value == null);
		}
		fStatus= getContainerChildStatus();
	};
	
	private ClassPathListElementAttribute(boolean buildIn) {
    	fBuiltIn= buildIn;
    }
	
	public IClasspathAttribute getClasspathAttribute() {
		Assert.isTrue(!fBuiltIn);
		return JavaCore.newClasspathAttribute(fKey, (String) fValue);
	}
	
	String getKey() {
		return fKey;
	}
	
	Object getValue() {
		return fValue;
	}
	
	/**
	 * @return Returns <code>true</code> if the attribute is a built in attribute.
	 */
	public boolean isBuiltIn() {
		return fBuiltIn;
	}

	/**
	 * @return Returns <code>true</code> if the attribute a on a container child and is read-only
	 */
	public boolean isNonModifiable() {
		return fStatus != null && !fStatus.isOK();
	}

	/**
	 * @return Returns <code>true</code> if the attribute a on a container child and is not supported
	 */
	public boolean isNotSupported() {
		return fStatus != null && fStatus.getCode() == ClasspathContainerInitializer.ATTRIBUTE_NOT_SUPPORTED;
	}

	/**
	 * @return Returns the container child status or <code>null</code> if the attribute is not in a container child
	 */
	private IStatus getContainerChildStatus() {
		return fParent.getContainerChildStatus(this);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ClassPathListElementAttribute))
            return false;
        ClassPathListElementAttribute attrib= (ClassPathListElementAttribute)obj;
        return attrib.fKey == this.fKey && attrib.getParent().getPath().equals(fParent.getPath());
	}

	private ClassPathListElement getParent() {
		return fParent;
	}

	public void setValue(Object value) {
		fValue = value;
	}

}
