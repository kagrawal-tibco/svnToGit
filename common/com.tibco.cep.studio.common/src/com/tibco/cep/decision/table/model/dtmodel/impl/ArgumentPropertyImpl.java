/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.impl;


import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.ResourceType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Argument Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentPropertyImpl#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentPropertyImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentPropertyImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentPropertyImpl#getGraphicalPath <em>Graphical Path</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentPropertyImpl#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentPropertyImpl#isDomainOverridden <em>Domain Overridden</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentPropertyImpl#isArray <em>Array</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArgumentPropertyImpl extends EObjectImpl implements ArgumentProperty {
	/**
	 * The default value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPath() <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPath()
	 * @generated
	 * @ordered
	 */
	protected String path = PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIAS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected String alias = ALIAS_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getGraphicalPath() <em>Graphical Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphicalPath()
	 * @generated
	 * @ordered
	 */
	protected static final String GRAPHICAL_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGraphicalPath() <em>Graphical Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphicalPath()
	 * @generated
	 * @ordered
	 */
	protected String graphicalPath = GRAPHICAL_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceType()
	 * @generated
	 * @ordered
	 */
	protected static final ResourceType RESOURCE_TYPE_EDEFAULT = ResourceType.UNDEFINED;

	/**
	 * The cached value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceType()
	 * @generated
	 * @ordered
	 */
	protected ResourceType resourceType = RESOURCE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isDomainOverridden() <em>Domain Overridden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDomainOverridden()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DOMAIN_OVERRIDDEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDomainOverridden() <em>Domain Overridden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDomainOverridden()
	 * @generated
	 * @ordered
	 */
	protected boolean domainOverridden = DOMAIN_OVERRIDDEN_EDEFAULT;

	/**
	 * The default value of the '{@link #isArray() <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArray()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ARRAY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isArray() <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isArray()
	 * @generated
	 * @ordered
	 */
	protected boolean array = ARRAY_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArgumentPropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DtmodelPackage.Literals.ARGUMENT_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPath() {
		return path;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPath(String newPath) {
		String oldPath = path;
		path = newPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.ARGUMENT_PROPERTY__PATH, oldPath, path));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.ARGUMENT_PROPERTY__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.ARGUMENT_PROPERTY__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGraphicalPath() {
		return graphicalPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraphicalPath(String newGraphicalPath) {
		String oldGraphicalPath = graphicalPath;
		graphicalPath = newGraphicalPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.ARGUMENT_PROPERTY__GRAPHICAL_PATH, oldGraphicalPath, graphicalPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceType getResourceType() {
		return resourceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceType(ResourceType newResourceType) {
		ResourceType oldResourceType = resourceType;
		resourceType = newResourceType == null ? RESOURCE_TYPE_EDEFAULT : newResourceType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.ARGUMENT_PROPERTY__RESOURCE_TYPE, oldResourceType, resourceType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDomainOverridden() {
		return domainOverridden;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainOverridden(boolean newDomainOverridden) {
		boolean oldDomainOverridden = domainOverridden;
		domainOverridden = newDomainOverridden;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.ARGUMENT_PROPERTY__DOMAIN_OVERRIDDEN, oldDomainOverridden, domainOverridden));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isArray() {
		return array;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArray(boolean newArray) {
		boolean oldArray = array;
		array = newArray;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.ARGUMENT_PROPERTY__ARRAY, oldArray, array));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DtmodelPackage.ARGUMENT_PROPERTY__PATH:
				return getPath();
			case DtmodelPackage.ARGUMENT_PROPERTY__ALIAS:
				return getAlias();
			case DtmodelPackage.ARGUMENT_PROPERTY__TYPE:
				return getType();
			case DtmodelPackage.ARGUMENT_PROPERTY__GRAPHICAL_PATH:
				return getGraphicalPath();
			case DtmodelPackage.ARGUMENT_PROPERTY__RESOURCE_TYPE:
				return getResourceType();
			case DtmodelPackage.ARGUMENT_PROPERTY__DOMAIN_OVERRIDDEN:
				return isDomainOverridden();
			case DtmodelPackage.ARGUMENT_PROPERTY__ARRAY:
				return isArray();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DtmodelPackage.ARGUMENT_PROPERTY__PATH:
				setPath((String)newValue);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__ALIAS:
				setAlias((String)newValue);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__TYPE:
				setType((String)newValue);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__GRAPHICAL_PATH:
				setGraphicalPath((String)newValue);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__RESOURCE_TYPE:
				setResourceType((ResourceType)newValue);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__DOMAIN_OVERRIDDEN:
				setDomainOverridden((Boolean)newValue);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__ARRAY:
				setArray((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DtmodelPackage.ARGUMENT_PROPERTY__PATH:
				setPath(PATH_EDEFAULT);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__ALIAS:
				setAlias(ALIAS_EDEFAULT);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__GRAPHICAL_PATH:
				setGraphicalPath(GRAPHICAL_PATH_EDEFAULT);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__RESOURCE_TYPE:
				setResourceType(RESOURCE_TYPE_EDEFAULT);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__DOMAIN_OVERRIDDEN:
				setDomainOverridden(DOMAIN_OVERRIDDEN_EDEFAULT);
				return;
			case DtmodelPackage.ARGUMENT_PROPERTY__ARRAY:
				setArray(ARRAY_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DtmodelPackage.ARGUMENT_PROPERTY__PATH:
				return PATH_EDEFAULT == null ? path != null : !PATH_EDEFAULT.equals(path);
			case DtmodelPackage.ARGUMENT_PROPERTY__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
			case DtmodelPackage.ARGUMENT_PROPERTY__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
			case DtmodelPackage.ARGUMENT_PROPERTY__GRAPHICAL_PATH:
				return GRAPHICAL_PATH_EDEFAULT == null ? graphicalPath != null : !GRAPHICAL_PATH_EDEFAULT.equals(graphicalPath);
			case DtmodelPackage.ARGUMENT_PROPERTY__RESOURCE_TYPE:
				return resourceType != RESOURCE_TYPE_EDEFAULT;
			case DtmodelPackage.ARGUMENT_PROPERTY__DOMAIN_OVERRIDDEN:
				return domainOverridden != DOMAIN_OVERRIDDEN_EDEFAULT;
			case DtmodelPackage.ARGUMENT_PROPERTY__ARRAY:
				return array != ARRAY_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (path: ");
		result.append(path);
		result.append(", alias: ");
		result.append(alias);
		result.append(", type: ");
		result.append(type);
		result.append(", graphicalPath: ");
		result.append(graphicalPath);
		result.append(", resourceType: ");
		result.append(resourceType);
		result.append(", domainOverridden: ");
		result.append(domainOverridden);
		result.append(", array: ");
		result.append(array);
		result.append(')');
		return result.toString();
	}

} //ArgumentPropertyImpl
