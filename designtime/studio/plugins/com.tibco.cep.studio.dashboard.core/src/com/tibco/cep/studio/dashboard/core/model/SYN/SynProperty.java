package com.tibco.cep.studio.dashboard.core.model.SYN;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationErrorMessage;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationInfoMessage;
import com.tibco.cep.studio.dashboard.core.exception.SynValidationMessage;
import com.tibco.cep.studio.dashboard.core.model.SYN.interfaces.ISynAttributeInstance;
import com.tibco.cep.studio.dashboard.core.model.XSD.components.SynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynNameType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.util.StringUtil;

/**
 *
 */
public abstract class SynProperty extends SynXSDAttributeDeclaration implements ISynAttributeInstance {

	private boolean isSystem = false;

	private boolean alreadySet = false;

	private boolean isArray = false;

	private LocalElement parent;

	/**
	 * The value of this attribute; will be used in validation If this property is not an array then use the first item in the list to store the value
	 */
	private List<String> values = new ArrayList<String>();

	private transient SynValidationMessage validationMessage = null;

	/**
	 * All subclasses can make use of the default logger or use their own logger implementation
	 */
	// protected Logger logger = Logger.getLogger(SynProperty.class.getName());

	public SynProperty(String name, ISynXSDSimpleTypeDefinition type) {
		super(name, type);
	}

	public SynProperty(String name, ISynXSDSimpleTypeDefinition type, String defaultValue) {
		super(name, type);
		setDefault(defaultValue);
	}

	public SynProperty(String name, ISynXSDSimpleTypeDefinition type, String defaultValue, boolean isSystem) {
		super(name, type);
		setSystem(isSystem);
		setDefault(defaultValue);
	}

	/**
	 * Convenience method for determining the validity of the currently set value for this type
	 *
	 * @return true if the value is acceptable for this type
	 */
	public final boolean isValid() throws Exception {
		return isValid(getValue());
	}

	/**
	 * Return whether the given value is appropriate for the type returned by getTypeDefinition()
	 *
	 */
	@SuppressWarnings("unchecked")
	public boolean isValid(Object testValue) throws Exception {
		String prefix = "";
		setValidationMessage(null);
		ISynXSDSimpleTypeDefinition type = getTypeDefinition();

		try {
			prefix = getName() + ": Value \"" + getValue() + "\" ";
		} catch (Exception ignore) {
		}

		if (testValue instanceof List) {
			List<String> valueList = (List<String>) testValue;
			for (int i = 0; i < valueList.size(); i++) {
				String newValue = valueList.get(i);
				if (false == type.isValid(newValue)) {
					if (type instanceof SynNameType) {
						// Is the old value available?
						if (i < this.values.size()) {
							String oldValue = this.values.get(i);
							if (newValue.equals(oldValue)) {
								return true;
							} else {
								setValidationMessage(new SynValidationErrorMessage(prefix + "error: \"" + newValue + "\" is an invalid name. Valid names must start and end with an alphanumerical character, "
										+ "\rmay contain letters, numerals, underscores(_), and periods (.), and must be no longer than 128 characters."));
								return false;
							}
						} else {
							setValidationMessage(new SynValidationErrorMessage(prefix + "error: \"" + newValue + "\" is an invalid name. Valid names must start and end with an alphanumerical character, "
									+ "\rmay contain letters, numerals, underscores(_), and periods (.), and must be no longer than 128 characters."));
							return false;
						}
					}
					setValidationMessage(new SynValidationErrorMessage(type.getValidationMessage().getMessage() + " for " + getName()));
					return false;
				}
			}
		} else {
			if (StringUtil.isEmpty((String) testValue)) {
				if (this instanceof SynRequiredProperty) {
					// ntamhank Dec 06, 2010: Fix for BE-9506: calling makePrettyName(this.getName) distorts the original property name 'ImageURL' as 'Image U R L'
					// Removing the function call.
					setValidationMessage(new SynValidationErrorMessage("Missing '" + this.getName() + "' in " + getParent().getDisplayableName()));
					// setValidationMessage(new SynValidationErrorMessage("Missing '"+StringUtil.makePrettyName(this.getName())+"' in "+getParent().getDisplayableName()));
					// setValidationMessage(new SynValidationErrorMessage("'"+StringUtil.makePrettyName(this.getName())+"' is a required property; please enter an appropriate value."));
					return false;
				}
				return true;
			}
			// At this point, we have a required property or a non-empty
			// property value.
			if (false == type.isValid(testValue)) {
				if (type instanceof SynNameType) {
					// Is the old value available?
					if (0 < this.values.size()) {
						String oldValue = this.values.get(0);
						if (testValue.equals(oldValue)) {
							return true;
						} else {
							setValidationMessage(new SynValidationErrorMessage("Error: \"" + testValue + "\" is an invalid name. Valid names must start and end with an alpha-numerical character, "
									+ "\rmay contain letters, numerals, underscores(_), and periods (.), and must be no longer than 128 characters."));
							return false;
						}
					} else {
						setValidationMessage(new SynValidationErrorMessage("Error: \"" + testValue + "\" is an invalid name. Valid names must start and end with an alpha-numerical character, "
								+ "\rmay contain letters, numerals, underscores(_), and periods (.), and must be no longer than 128 characters."));
						return false;
					}
				} else {
					setValidationMessage(new SynValidationErrorMessage(type.getValidationMessage().getMessage() + " for " + getName()));
				}
				return false;
			}
		}
		return true;
	}

	public Object clone() {
		try {
			SynProperty pClone = (SynProperty) cloneThis();
			pClone.setArray(isArray());
			pClone.setSystem(isSystem());
			if (true == isAlreadySet()) {
				if (false == isArray()) {
					pClone.setValue(getValue());
				} else {
					for (Iterator<String> iter = getValues().iterator(); iter.hasNext();) {
						pClone.addValue(iter.next());
					}
				}
			}
			return pClone;
		} catch (Exception e) {
			throw new RuntimeException("could not clone " + getName(), e);
		}
	}

	/**
	 * Return a clone of this property
	 *
	 * @return a new instance of SynXAttribute with the same values and properties as this attribute
	 * @throws Exception
	 */
	public abstract Object cloneThis() throws Exception;

	/**
	 * Returns whether this property is always maintained by the system such as internal ID
	 *
	 * @return
	 */
	public final boolean isSystem() {
		return isSystem;
	}

	public final void setSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}

	/**
	 * @return Returns the validationMessage.
	 */
	public SynValidationMessage getValidationMessage() throws Exception {
		return validationMessage;
	}

	/**
	 * @param validationMessage
	 *            The validationMessage to set.
	 */
	public void setValidationMessage(SynValidationMessage validationMessage) {
		try {
			if (validationMessage == null)
				this.validationMessage = null;
			else {
				if (validationMessage instanceof SynValidationInfoMessage)
					this.validationMessage = new SynValidationInfoMessage(validationMessage.getMessage());
				else if (validationMessage instanceof SynValidationErrorMessage)
					this.validationMessage = new SynValidationErrorMessage(validationMessage.getMessage());
			}
		} catch (Exception e) {
		}
	}

	/**
	 * @return Returns the alreadySet.
	 */
	public boolean isAlreadySet() {
		return alreadySet;
	}

	/**
	 * @param alreadySet
	 *            The alreadySet to set.
	 */
	public void setAlreadySet(boolean alreadySet) {
		this.alreadySet = alreadySet;
	}

	public String getValue() {
		if (false == isAlreadySet()) {
			if (null != parent && parent.getInternalStatus().equals(InternalStatusEnum.StatusNew) == false) {
				parent.parseMDProperty(getName());
			}
		}

		//
		// ERNIE: lazy initialize the property by defaultValue.
		// There may be side effect. Let QA to verify.
		if (true == values.isEmpty()) {
			String defaultValue = getDefault();
			if (true == StringUtil.isEmpty(defaultValue)) {
				addValue(null, "");
			} else {
				addValue(null, defaultValue);
			}
			this.setAlreadySet(true);
		}

		// read the value back from instance variable.
		// There must be one.
		Object value = this.getValues().get(0);
		return value == null ? "" : value.toString();
	}

	public List<String> getValues() {
		return values;
	}

	public void setValue(String newValue) {
		// System.out.println(getParent().getElementType()+"@"+getName()+" = '"+newValue+"'");
		List<String> values = getValues();

		/*
		 * If there is no values yet then just add it
		 */
		if (true == values.isEmpty()) {
			addValue(newValue);
		}

		else {
			/*
			 * If the value has already been set then only set the value if it is different than the existing value
			 */
			if (true == isAlreadySet()) {
				if (true == getValue().equals(newValue)) {
					return;
				}
				/*
				 * Clears out the existing values before adding the new value
				 */
				String oldValue = getValue();
				values.clear();
				addValue(oldValue, newValue);
			} else {
				values.clear();
				addValue(null, newValue);
			}
		}
	}

	// Added on 12th May 2008 by ashima as Fix for Bug# 8523 and 8657
	// Fire Property Change is not triggered in case the modified
	// value name is same as earlier set value though their IDs are different
	public void setValueForce(String newValue) {

		List<String> values = getValues();

		/*
		 * If there is no values yet then just add it
		 */
		if (true == values.isEmpty()) {
			addValue(newValue);
		}

		else {
			/*
			 * If the value has already been set then only set the value if it is different than the existing value
			 */
			if (true == isAlreadySet()) {

				/*
				 * Clears out the existing values before adding the new value
				 */
				String oldValue = getValue();
				values.clear();
				addValueForce(oldValue, newValue);
			} else {
				values.clear();
				addValue(null, newValue);
			}
		}
	}

	public void setValues(List<String> newValues) {
		if (newValues != null) {
			values.clear();
			if (newValues.isEmpty() == false) {
				for (int i = 0; i < newValues.size(); ++i) {
					addValue(null, newValues.get(i));
				}
			}
			else {
				//we don't have any new values, but we still need to fire a property change
				if (true == isAlreadySet()) {
					setModified();
					if (null != getParent()) {
						getParent().notifyPropertyChanged(this, null, null);
					}
					firePropertyChange(this.getName(), null, null);
				} else {
					setAlreadySet(true);
				}
			}
		}
	}

	public void addValue(String oldValue, String newValue) {
		getValues().add(newValue);
		if (true == isAlreadySet()) {
			setModified();
			if (null != getParent()) {
				getParent().notifyPropertyChanged(this, oldValue, newValue);
			}
			firePropertyChange(this.getName(), oldValue, newValue);
		} else {
			setAlreadySet(true);
		}
	}

	// Added on 12th May 2008 by ashima as Fix for Bug# 8523 and 8657
	// Fire Property Change is not triggered in case the modified
	// value name is same as earlier set value though their IDs are different
	public void addValueForce(String oldValue, String newValue) {
		getValues().add(newValue);
		if (true == isAlreadySet()) {
			setModified();

			if (oldValue != null && oldValue.equals(newValue)) {
				oldValue = "#" + oldValue;
			}
			if (null != getParent()) {
				getParent().notifyPropertyChanged(this, oldValue, newValue);
			}
			firePropertyChange(this.getName(), oldValue, newValue);
		} else {
			setAlreadySet(true);
		}
	}

	public void addValue(String newValue) {
		addValue(null, newValue);
	}

	public void removeValue(String value) {
		values.remove(value);
	}

	public boolean isArray() {
		return this.isArray;
	}

	public void setArray(boolean isArray) {
		this.isArray = isArray;
	}

	public LocalElement getParent() {
		return parent;
	}

	public void setParent(LocalElement parent) {
		this.parent = parent;
	}

	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.getClass().getSimpleName());
		sb.append("[name=" + getName());
		sb.append(",default=" + getDefault());
		sb.append(",set=" + isAlreadySet());
		sb.append(",values=" + values);
		sb.append("]");
		return sb.toString();
	}

}