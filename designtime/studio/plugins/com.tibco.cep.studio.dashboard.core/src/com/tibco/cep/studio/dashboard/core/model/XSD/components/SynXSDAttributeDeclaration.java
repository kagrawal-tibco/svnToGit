package com.tibco.cep.studio.dashboard.core.model.XSD.components;

import com.tibco.cep.studio.dashboard.core.enums.InternalStatusEnum;
import com.tibco.cep.studio.dashboard.core.model.ISynInternalStatusProvider;
import com.tibco.cep.studio.dashboard.core.model.ISynValidationProvider;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAnnotation;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDAttributeDeclaration;
import com.tibco.cep.studio.dashboard.core.model.XSD.interfaces.ISynXSDSimpleTypeDefinition;

/**
 *
 */
public abstract class SynXSDAttributeDeclaration extends SynXSDSchemaElement implements ISynXSDAttributeDeclaration, ISynValidationProvider, ISynInternalStatusProvider, Comparable<SynXSDAttributeDeclaration> {

	/**
	 * The internal status is default to Existing. NOTE: This class actually supports the dynamic creation of new attributes (therefore the need to support all internal status) but the current Architect implementation does
	 * not allow the creation of new attributes and that is why the default is Existing. When/if we support the dynamic creation of attributes the default should be set to New.
	 */
	protected transient InternalStatusEnum internalStatus = InternalStatusEnum.StatusExisting;

	private boolean isSystemStatus = false;

	private ISynXSDSimpleTypeDefinition typeDefinition;

	/**
	 * An attribute can be a ref to another attribute
	 */
	private SynXSDAttributeDeclaration ref;

	/**
	 * The default value for this attribute. Only takes effect when the usageType == AttributeUsageEnum.usageOptional
	 */
	private String defaultValue = "";

	private String fixed;

	private ISynXSDAnnotation annotation;

	/**
	 * This null constructor is here just to satisfy the contract with Serializable and is not recommended to be used in implementation
	 */
	public SynXSDAttributeDeclaration() {
		super();
	}

	/**
	 *
	 * @param name
	 * @param type
	 */
	public SynXSDAttributeDeclaration(String name, ISynXSDSimpleTypeDefinition type) {
		if (null == name || name.length() < 1) {
			throw new IllegalArgumentException("name can not be null");
		}
		setName(name);
		this.typeDefinition = type;
	}

	/**
	 * @return Returns the annotation.
	 */
	public ISynXSDAnnotation getAnnotation() {
		return annotation;
	}

	/**
	 * @param annotation The annotation to set.
	 */
	public void setAnnotation(ISynXSDAnnotation annotation) {
		this.annotation = annotation;
	}

	/**
	 * Attributes are equal if their names and datatypes are equal
	 */
	public boolean equals(Object obj) {
		if (obj instanceof SynXSDAttributeDeclaration) {
			SynXSDAttributeDeclaration attribute = (SynXSDAttributeDeclaration) obj;
			if (false == attribute.getName().equals(getName())) {
				return false;
			}
			if (false == typeDefinition.equals(attribute.getTypeDefinition())) {
				return false;
			}
			return true;
		}
		return super.equals(obj);
	}

	public int compareTo(SynXSDAttributeDeclaration obj) {
		if (equals(obj))
			return 0;
		else {
			try {
				String name = getName();
				if (name != null) {
					return name.compareTo(obj.getName());
				}
			} catch (Exception e) {
			}
			return -1;
		}
	}

	public final String getDefault() {
		return defaultValue == null ? "" : defaultValue;
	}

	public final void setDefault(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public final ISynXSDSimpleTypeDefinition getTypeDefinition() {
		return typeDefinition;
	}

	public void setTypeDefinition(ISynXSDSimpleTypeDefinition typeDefinition) {
		this.typeDefinition = typeDefinition;
	}

	public String getFixed() {
		return fixed;
	}

	public void setFixed(String fixed) {
		this.fixed = fixed;
	}

	/**
	 * @return Returns the internalStatus.
	 */
	public InternalStatusEnum getInternalStatus() {
		return internalStatus;
	}

	/**
	 * @param internalStatus The internalStatus to set.
	 */
	public void setInternalStatus(InternalStatusEnum internalStatus) {
		this.internalStatus = internalStatus;
	}

	public void setModified() {
		this.internalStatus = InternalStatusEnum.StatusModified;
	}

	/**
	 * @param internalStatus The internalStatus to set.
	 */
	public void setInternalStatus(InternalStatusEnum internalStatus, boolean synchAllChildren) {
		this.internalStatus = internalStatus;
	}

	/**
	 * @return Returns the isSystemStatus.
	 */
	public boolean isSystemStatus() {
		return isSystemStatus;
	}

	/**
	 * @param isSystemStatus The isSystemStatus to set.
	 */
	public void setSystemStatus(boolean isSystemStatus) {
		this.isSystemStatus = isSystemStatus;
	}

	/**
	 * @return Returns the ref
	 */
	public SynXSDAttributeDeclaration getRef() {
		return ref;
	}

	/**
	 * @param ref The ref to set.
	 */
	public void setRef(SynXSDAttributeDeclaration ref) {
		this.ref = ref;
	}
}