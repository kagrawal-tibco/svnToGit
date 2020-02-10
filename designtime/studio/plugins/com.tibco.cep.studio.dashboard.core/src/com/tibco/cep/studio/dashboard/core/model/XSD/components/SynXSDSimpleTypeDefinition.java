package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import java.util.ArrayList;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationMessage;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAnnotation;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAtomicSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeContent;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.modifiers.SynXSDSimpleTypeFinalModifier;

/**
 * @ *
 */
public abstract class SynXSDSimpleTypeDefinition extends SynXSDSchemaElement implements ISynXSDSimpleTypeDefinition {

	private List<SynXSDSimpleTypeFinalModifier> finalModifiers;

	private ISynXSDSimpleTypeDefinition base;

	private List<ISynXSDSimpleTypeContent> contents;

	private ISynXSDAnnotation annotation;

	private SynValidationMessage validationMessage;

	private boolean isNullable = false;

	public SynXSDSimpleTypeDefinition() {
		super();
	}

	public SynXSDSimpleTypeDefinition(String name) {
		super(name);
	}

	public SynXSDSimpleTypeDefinition(String name, SynXSDSimpleTypeDefinition base) {
		super(name);
		this.base = base;
	}

	public ISynXSDSimpleTypeDefinition getBase() {
		return base;
	}

	public void setBase(ISynXSDSimpleTypeDefinition base) {
		this.base = base;
	}

	public List<ISynXSDSimpleTypeContent> getContents() {
		return contents;
	}

	public void addContent(ISynXSDSimpleTypeContent content) {
		if (null == contents) {
			contents = new ArrayList<ISynXSDSimpleTypeContent>();
		}
		contents.add(content);
	}

	public void removeContent(ISynXSDSimpleTypeContent content) {
		if (null != contents) {
			contents.remove(content);
		}
	}

	public List<SynXSDSimpleTypeFinalModifier> getFinalModifiers() {
		return finalModifiers;
	}

	public void addFinalModifier(SynXSDSimpleTypeFinalModifier modifier) {
		if (null == finalModifiers) {
			finalModifiers = new ArrayList<SynXSDSimpleTypeFinalModifier>();
		}
		finalModifiers.add(modifier);
	}

	public void removeFinalModifier(SynXSDSimpleTypeFinalModifier modifier) {
		if (null != finalModifiers) {
			finalModifiers.remove(modifier);
		}
	}

	public ISynXSDAnnotation getAnnotation() {
		return annotation;
	}

	public void setAnnotation(ISynXSDAnnotation annotation) {
		this.annotation = annotation;
	}

	/**
	 * If this is an atomic type then return it, else recurse towards the root until found then return the root.
	 *
	 * If the base is null then return null (this is actually an illegal state because all simple types should have an atomic root)
	 */
	public ISynXSDAtomicSimpleTypeDefinition getAtomicTypeDefinition() {
		if (this instanceof ISynXSDAtomicSimpleTypeDefinition) {
			return (ISynXSDAtomicSimpleTypeDefinition) this;
		}
		return null == getBase() ? null : getBase().getAtomicTypeDefinition();
	}

	public SynValidationMessage getValidationMessage() throws Exception {
		return validationMessage;
	}

	/**
	 * @param validationMessage
	 *            The validationMessage to set.
	 */
	public void setValidationMessage(SynValidationMessage validationMessage) {
		this.validationMessage = validationMessage;
	}

	public boolean isValid() throws Exception, Exception {
		return true;
	}

	/**
	 * This is the lowest level of validation for the given value against the java primitive class.
	 *
	 * Subclasses should extend this to encompass more higher-level data validation
	 */
	public boolean isValid(Object value) {
		setValidationMessage(null);
		ISynXSDAtomicSimpleTypeDefinition atomicType = getAtomicTypeDefinition();
		if (null != atomicType) {
			Class<?> javaTypeClass = atomicType.getJavaType();
			if (null != javaTypeClass) {

				String javaTypeName = javaTypeClass.getName();
				boolean errorFound = false;
				try {
					if (true == javaTypeName.equals(int.class.getName()) || true == javaTypeName.equals(Integer.class.getName())) {
						Integer.parseInt(value.toString());
					} else if (true == javaTypeName.equals(long.class.getName()) || true == javaTypeName.equals(Long.class.getName())) {
						Long.parseLong(value.toString());
					} else if (true == javaTypeName.equals(short.class.getName()) || true == javaTypeName.equals(Short.class.getName())) {
						Short.parseShort(value.toString());
					} else if (true == javaTypeName.equals(byte.class.getName()) || true == javaTypeName.equals(Byte.class.getName())) {
						Byte.parseByte(value.toString());
					} else if (true == javaTypeName.equals(float.class.getName()) || true == javaTypeName.equals(Float.class.getName())) {
						Float.parseFloat(value.toString());
					} else if (true == javaTypeName.equals(double.class.getName()) || true == javaTypeName.equals(Double.class.getName())) {
						Double.parseDouble(value.toString());
					} else if (true == javaTypeName.equals(String.class.getName())) {
						if (!(value instanceof String)) {
							errorFound = true;
						}
					} else if (true == javaTypeName.equals(boolean.class.getName()) || true == javaTypeName.equals(Boolean.class.getName())) {
						if (false == value.toString().equalsIgnoreCase("true") && false == value.toString().equalsIgnoreCase("false")) {
							errorFound = true;
						}
					}

					if (true == errorFound) {
						setValidationMessage(new SynValidationErrorMessage(value.toString() + " is not appropriate for the " + javaTypeName + " Java primitive datatype"));
						return false;

					}

				} catch (NumberFormatException e) {
					setValidationMessage(new SynValidationErrorMessage(value.toString() + " is not appropriate for the " + javaTypeName + " Java primitive datatype"));
					return false;
				}

			} else {
				throw new IllegalArgumentException("Java primitive type is not found");

			}
		} else {
			throw new IllegalArgumentException("Atomic type is not found");
		}
		return true;
	}

	/**
	 * @return Returns the isNullable.
	 */
	public boolean isNullable() {
		return isNullable;
	}

	/**
	 * @param isNullable
	 *            The isNullable to set.
	 */
	public void setNullable(boolean isNullable) {
		this.isNullable = isNullable;
	}

	public void cloneThis(SynXSDSimpleTypeDefinition clone) throws Exception {
		super.cloneThis(clone);
		clone.finalModifiers = new ArrayList<SynXSDSimpleTypeFinalModifier>();
		if (this.finalModifiers != null) {
			clone.finalModifiers.addAll(this.finalModifiers);
		}
		if (this.base == null) {
			clone.base = null;
		} else {
			clone.base = (ISynXSDSimpleTypeDefinition) this.base.cloneThis();
		}
		clone.contents = new ArrayList<ISynXSDSimpleTypeContent>();
		if (this.contents != null) {
			clone.contents.addAll(this.contents);
		}
		if (this.annotation == null) {
			clone.annotation = null;
		} else {
			clone.annotation = (ISynXSDAnnotation) this.annotation.cloneThis();
		}
		if (this.validationMessage == null) {
			clone.validationMessage = null;
		} else {
			clone.validationMessage = (SynValidationMessage) this.validationMessage.cloneThis();
		}
		clone.isNullable = this.isNullable;
	}
}
