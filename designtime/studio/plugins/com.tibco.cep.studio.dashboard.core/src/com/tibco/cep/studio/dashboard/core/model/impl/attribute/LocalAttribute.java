package com.tibco.cep.studio.dashboard.core.model.impl.attribute;

import java.util.logging.Level;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynOptionalProperty;
import com.tibco.cep.studio.dashboard.core.model.SYN.SynRequiredProperty;
import com.tibco.cep.studio.dashboard.core.model.XSD.simpletypes.SynStringType;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalElement;
import com.tibco.cep.studio.dashboard.core.model.impl.LocalEntity;

/**
 * @ *
 */
public abstract class LocalAttribute extends LocalEntity {

	public static final String PROP_KEY_DATA_TYPE = PROP_KEY_PREFIX + "Datatype";

	// public static final String PROP_KEY_REF_TYPE = PROP_KEY_PREFIX + "ReferenceType";
	//
	// public static final String PROP_KEY_DATA_SIZE = PROP_KEY_PREFIX + "DataSize";
	//
	// public static final String PROP_KEY_IS_OPTIONAL = PROP_KEY_PREFIX + "Optional";
	//
	// public static final String PROP_KEY_IS_DERIVED = PROP_KEY_PREFIX + "Derived";
	//
	// public static final String PROP_KEY_IS_ARRAY = PROP_KEY_PREFIX + "Array";
	//
	// public static final String PROP_KEY_IS_ORDERED = PROP_KEY_PREFIX + "Ordered";
	//
	// public static final String PROP_KEY_IS_UNIQUE = PROP_KEY_PREFIX + "Unique";
	//
	// public static final String PROP_KEY_IS_KEY = PROP_KEY_PREFIX + "Key";
	//
	// public static final String PROP_KEY_IS_NULL_ALLOWED = PROP_KEY_PREFIX + "NullAllowed";
	//
	// public static final String PROP_KEY_VALUE = PROP_KEY_PREFIX + "Value";
	//
	public static final String PROP_KEY_URL_NAME = PROP_KEY_PREFIX + "UrlName";

	public static final String PROP_KEY_URL_LINK = PROP_KEY_PREFIX + "UrlLink";
	//
	// public static final String PROP_KEY_VALUE_SOURCE = PROP_KEY_PREFIX + "ValueSource";

	public static String[] SUPPORTED_DATA_TYPES = { PROPERTY_TYPES.STRING.getLiteral(), PROPERTY_TYPES.INTEGER.getLiteral(), PROPERTY_TYPES.LONG.getLiteral(), PROPERTY_TYPES.DOUBLE.getLiteral(),
			PROPERTY_TYPES.BOOLEAN.getLiteral(), PROPERTY_TYPES.DATE_TIME.getLiteral() };

	/**
     *
     */
	public LocalAttribute() {
		this(null, "Field");
	}

	/**
	 * @param parentElement
	 */
	public LocalAttribute(LocalEntity parentElement, String name) {
		super(parentElement, name);
		try {
			setDataType(PROPERTY_TYPES.STRING.getLiteral());
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, e.getMessage(), e);
		}
	}

	/**
	 * @param parentElement
	 * @param entity
	 */
	public LocalAttribute(LocalEntity parentElement, Entity entity) {
		super(parentElement, entity);
	}

	public void setupProperties() {
		// addProperty(this, new SynOptionalProperty(PROP_KEY_VALUE, new SynStringType(), ""));
		addProperty(this, new SynOptionalProperty(PROP_KEY_URL_NAME, new SynStringType(), ""));
		addProperty(this, new SynOptionalProperty(PROP_KEY_URL_LINK, new SynStringType(), ""));
		// addProperty(this, new SynOptionalProperty(PROP_KEY_VALUE_SOURCE, new SynStringType(), ""));

		addProperty(this, new SynRequiredProperty(PROP_KEY_DATA_TYPE, new SynStringType(SUPPORTED_DATA_TYPES), PROPERTY_TYPES.STRING.getLiteral()));
		// addProperty(this, new SynOptionalProperty(PROP_KEY_DATA_SIZE, new SynLongType(), "0"));
		// addProperty(this, new SynOptionalProperty(PROP_KEY_REF_TYPE, new SynStringType(), ""));

		/*
		 * These are currently unimplemented in MDS and are here set as system
		 */
		// addProperty(this, new SynOptionalProperty(PROP_KEY_IS_OPTIONAL, new SynBooleanType(), Boolean.FALSE.toString(), true));
		// addProperty(this, new SynOptionalProperty(PROP_KEY_IS_DERIVED, new SynBooleanType(), Boolean.FALSE.toString(), true));
		// addProperty(this, new SynOptionalProperty(PROP_KEY_IS_ARRAY, new SynBooleanType(), Boolean.FALSE.toString(), true));
		// addProperty(this, new SynOptionalProperty(PROP_KEY_IS_ORDERED, new SynBooleanType(), Boolean.FALSE.toString(), true));
		// addProperty(this, new SynOptionalProperty(PROP_KEY_IS_UNIQUE, new SynBooleanType(), Boolean.FALSE.toString(), true));
		// addProperty(this, new SynOptionalProperty(PROP_KEY_IS_KEY, new SynBooleanType(), Boolean.FALSE.toString(), true));
		// addProperty(this, new SynOptionalProperty(PROP_KEY_IS_NULL_ALLOWED, new SynBooleanType(), "false", true));

		removeProperty(PROP_KEY_NAMESPACE);
		removeProperty(PROP_KEY_FOLDER);
		addProperty(this, new SynOptionalProperty(PROP_KEY_NAMESPACE, new SynStringType(), "", true));
		addProperty(this, new SynOptionalProperty(PROP_KEY_FOLDER, new SynStringType(), "", true));

	}

	public void parseMDProperty(String propertyName) {
		if (true == super.isSuperProperty(propertyName)) {
			super.parseMDProperty(propertyName);
			return;
		}

		if (getEObject() instanceof PropertyDefinition) {
			PropertyDefinition mdAttribute = (PropertyDefinition) getEObject();
			if (true == PROP_KEY_DATA_TYPE.equals(propertyName)) {
				setDataType(mdAttribute.getType().getLiteral());
			} else if (true == PROP_KEY_URL_NAME.equals(propertyName)) {
				setUrlName(getExtendedPropertyValue(mdAttribute, "urlname"));
			} else if (true == PROP_KEY_URL_LINK.equals(propertyName)) {
				setUrlLink(getExtendedPropertyValue(mdAttribute, "urllink"));
			}
		}
	}

	protected boolean isSuperProperty(String propertyName) {
		if (true == super.isSuperProperty(propertyName)) {
			return true;
		}
		if (true == PROP_KEY_DATA_TYPE.equals(propertyName)) {
			return true;
		} else if (true == PROP_KEY_URL_NAME.equals(propertyName)) {
			return true;
		} else if (true == PROP_KEY_URL_LINK.equals(propertyName)) {
			return true;
		}
		return false;
	}

	public LocalEntity createLocalElement(String elementType) {
		return null;
	}

	/**
	 * There is no versionable element child, and so there is no addAdpater.
	 */
	@Override
	public Entity createMDChild(LocalElement localElement) {
		return null;
	}

	public void deleteMDChild(LocalElement localElement) {
	}

	public void loadChildren(String childrenType) {
	}

	public void loadChild(String childrenType, String childName) {
	}

	public void loadChildByID(String childrenType, String childID) {
	}

	protected void synchronizeElement(EObject mdElement) {
		super.synchronizeElement(mdElement);
		if (mdElement instanceof PropertyDefinition) {
			PropertyDefinition mdAttribute = (PropertyDefinition) mdElement;
			mdAttribute.eUnset(ModelPackage.eINSTANCE.getEntity_Namespace());
			mdAttribute.eUnset(ModelPackage.eINSTANCE.getEntity_Folder());
			mdAttribute.setType(PROPERTY_TYPES.get(getDataType()));
			mdAttribute.setOwnerPath(((LocalEntity) getParent()).getEObject().getFullPath());
			// Support for URL Info
			synchronizeUrlInfo(mdAttribute);
		}
	}

	public boolean isValid() throws Exception, Exception {

		if (false == super.isValid()) {
			return false;
		}

		setValidationMessage(null);

		/*
		 * Is it unique within context?
		 */
		if (null != getParent() && false == getParent().isNameUnique(getElementType(), getName())) {

			String message = "Field Name [" + getName() + "] is not unique";
			getLogger().log(Level.FINEST, message);
			addValidationErrorMessage(message);
			return false;
		}
		return true;

	}

	// ==================================================================
	// The following methods are convenience API's that delegates
	// to the reflection style API's for accessing attribute values
	// ==================================================================

	public String getDataType() {
		return getPropertyValue(PROP_KEY_DATA_TYPE);
	}

	public void setDataType(String value) {
		setPropertyValue(PROP_KEY_DATA_TYPE, value);
	}

	public String getUrlName() {
		return getPropertyValue(PROP_KEY_URL_NAME);
	}

	public void setUrlName(String value) {
		setPropertyValue(PROP_KEY_URL_NAME, value);
	}

	public String getUrlLink() {
		return getPropertyValue(PROP_KEY_URL_LINK);
	}

	public void setUrlLink(String value) {
		setPropertyValue(PROP_KEY_URL_LINK, value);
	}

	/**
	 * Synchronizes http reference value to a mdattribute.
	 *
	 * @param mdField
	 *            The MDAttribute in which the http reference values needs to be saved
	 * @since 3.1
	 */
	// Support for URL Links
	protected void synchronizeUrlInfo(PropertyDefinition mdField) {
		String urlName = getUrlName();
		String urlLink = getUrlLink();

		if (urlName != null && urlName.length() > 0) {
			setExtendedPropertyValue(mdField, "urlname", urlName);
			setExtendedPropertyValue(mdField, "urllink", urlLink);
		} else {
			// delete the 'urlname' & 'urllink' properties
			deleteExtendedPropertyValue(mdField, "urlname");
			deleteExtendedPropertyValue(mdField, "urllink");
		}
	}

	protected LabelMode getLabelMode() {
		return LabelMode.DEPENDS_ON_PARENT;
	}

	public String[] getURLInfo() throws Exception {
		return new String[] { getUrlName(), getUrlLink() };
	}

	public void setURLInfo(String[] URLInfo) throws Exception {
		if (URLInfo == null || URLInfo.length != 2) {
			throw new IllegalArgumentException("url info object is invalid");
		}
		setUrlName(URLInfo[0]);
		setUrlLink(URLInfo[1]);
	}

	public String[] getSupportedDataTypes() {
		return SUPPORTED_DATA_TYPES;
	}

}