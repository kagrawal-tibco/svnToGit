/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.Property;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.PropertyImpl#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.PropertyImpl#getHistorySize <em>History Size</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.PropertyImpl#getHistoryPolicy <em>History Policy</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.PropertyImpl#isMultiple <em>Multiple</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.PropertyImpl#getDefaultValue <em>Default Value</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.PropertyImpl#getPropertyResourceType <em>Property Resource Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PropertyImpl extends AbstractResourceImpl implements Property {
	/**
	 * The default value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected static final int DATA_TYPE_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected int dataType = DATA_TYPE_EDEFAULT;
	
	/**
	 * The cached value of the '{@link #getHistorySize() <em>History Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHistorySize()
	 * @generated NOT
	 * @ordered
	 */
	protected int historySize = HISTORY_SIZE_EDEFAULT;

	/**
	 * The default value of the '{@link #getHistorySize() <em>History Size</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHistorySize()
	 * @generated
	 * @ordered
	 */
	protected static final int HISTORY_SIZE_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getHistoryPolicy() <em>History Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHistoryPolicy()
	 * @generated
	 * @ordered
	 */
	protected static final int HISTORY_POLICY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getHistoryPolicy() <em>History Policy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHistoryPolicy()
	 * @generated
	 * @ordered
	 */
	protected int historyPolicy = HISTORY_POLICY_EDEFAULT;

	/**
	 * The default value of the '{@link #isMultiple() <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiple()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MULTIPLE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMultiple() <em>Multiple</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMultiple()
	 * @generated
	 * @ordered
	 */
	protected boolean multiple = MULTIPLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected static final String DEFAULT_VALUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDefaultValue() <em>Default Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefaultValue()
	 * @generated
	 * @ordered
	 */
	protected String defaultValue = DEFAULT_VALUE_EDEFAULT;

	/**
	 * The default value of the '{@link #getPropertyResourceType() <em>Property Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyResourceType()
	 * @generated
	 * @ordered
	 */
	protected static final String PROPERTY_RESOURCE_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPropertyResourceType() <em>Property Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPropertyResourceType()
	 * @generated
	 * @ordered
	 */
	protected String propertyResourceType = PROPERTY_RESOURCE_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyPackage.Literals.PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDataType() {
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataType(int newDataType) {
		int oldDataType = dataType;
		dataType = newDataType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.PROPERTY__DATA_TYPE, oldDataType, dataType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public int getHistorySize() {
		return historySize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void setHistorySize(int newHistorySize) {
		int oldHistorySize = historySize;
		historySize = newHistorySize;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.PROPERTY__HISTORY_SIZE, oldHistorySize, historySize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getHistoryPolicy() {
		return historyPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHistoryPolicy(int newHistoryPolicy) {
		int oldHistoryPolicy = historyPolicy;
		historyPolicy = newHistoryPolicy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.PROPERTY__HISTORY_POLICY, oldHistoryPolicy, historyPolicy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMultiple() {
		return multiple;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMultiple(boolean newMultiple) {
		boolean oldMultiple = multiple;
		multiple = newMultiple;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.PROPERTY__MULTIPLE, oldMultiple, multiple));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDefaultValue(String newDefaultValue) {
		String oldDefaultValue = defaultValue;
		defaultValue = newDefaultValue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.PROPERTY__DEFAULT_VALUE, oldDefaultValue, defaultValue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPropertyResourceType() {
		return propertyResourceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPropertyResourceType(String newPropertyResourceType) {
		String oldPropertyResourceType = propertyResourceType;
		propertyResourceType = newPropertyResourceType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.PROPERTY__PROPERTY_RESOURCE_TYPE, oldPropertyResourceType, propertyResourceType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OntologyPackage.PROPERTY__DATA_TYPE:
				return new Integer(getDataType());
			case OntologyPackage.PROPERTY__HISTORY_SIZE:
				return new Integer(getHistorySize());
			case OntologyPackage.PROPERTY__HISTORY_POLICY:
				return new Integer(getHistoryPolicy());
			case OntologyPackage.PROPERTY__MULTIPLE:
				return isMultiple() ? Boolean.TRUE : Boolean.FALSE;
			case OntologyPackage.PROPERTY__DEFAULT_VALUE:
				return getDefaultValue();
			case OntologyPackage.PROPERTY__PROPERTY_RESOURCE_TYPE:
				return getPropertyResourceType();
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
			case OntologyPackage.PROPERTY__DATA_TYPE:
				setDataType(((Integer)newValue).intValue());
				return;
			case OntologyPackage.PROPERTY__HISTORY_SIZE:
				setHistorySize(((Integer)newValue).intValue());
				return;
			case OntologyPackage.PROPERTY__HISTORY_POLICY:
				setHistoryPolicy(((Integer)newValue).intValue());
				return;
			case OntologyPackage.PROPERTY__MULTIPLE:
				setMultiple(((Boolean)newValue).booleanValue());
				return;
			case OntologyPackage.PROPERTY__DEFAULT_VALUE:
				setDefaultValue((String)newValue);
				return;
			case OntologyPackage.PROPERTY__PROPERTY_RESOURCE_TYPE:
				setPropertyResourceType((String)newValue);
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
			case OntologyPackage.PROPERTY__DATA_TYPE:
				setDataType(DATA_TYPE_EDEFAULT);
				return;
			case OntologyPackage.PROPERTY__HISTORY_SIZE:
				setHistorySize(HISTORY_SIZE_EDEFAULT);
				return;
			case OntologyPackage.PROPERTY__HISTORY_POLICY:
				setHistoryPolicy(HISTORY_POLICY_EDEFAULT);
				return;
			case OntologyPackage.PROPERTY__MULTIPLE:
				setMultiple(MULTIPLE_EDEFAULT);
				return;
			case OntologyPackage.PROPERTY__DEFAULT_VALUE:
				setDefaultValue(DEFAULT_VALUE_EDEFAULT);
				return;
			case OntologyPackage.PROPERTY__PROPERTY_RESOURCE_TYPE:
				setPropertyResourceType(PROPERTY_RESOURCE_TYPE_EDEFAULT);
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
			case OntologyPackage.PROPERTY__DATA_TYPE:
				return dataType != DATA_TYPE_EDEFAULT;
			case OntologyPackage.PROPERTY__HISTORY_SIZE:
				return getHistorySize() != HISTORY_SIZE_EDEFAULT;
			case OntologyPackage.PROPERTY__HISTORY_POLICY:
				return historyPolicy != HISTORY_POLICY_EDEFAULT;
			case OntologyPackage.PROPERTY__MULTIPLE:
				return multiple != MULTIPLE_EDEFAULT;
			case OntologyPackage.PROPERTY__DEFAULT_VALUE:
				return DEFAULT_VALUE_EDEFAULT == null ? defaultValue != null : !DEFAULT_VALUE_EDEFAULT.equals(defaultValue);
			case OntologyPackage.PROPERTY__PROPERTY_RESOURCE_TYPE:
				return PROPERTY_RESOURCE_TYPE_EDEFAULT == null ? propertyResourceType != null : !PROPERTY_RESOURCE_TYPE_EDEFAULT.equals(propertyResourceType);
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
		result.append(" (dataType: ");
		result.append(dataType);
		result.append(", historyPolicy: ");
		result.append(historyPolicy);
		result.append(", multiple: ");
		result.append(multiple);
		result.append(", defaultValue: ");
		result.append(defaultValue);
		result.append(", propertyResourceType: ");
		result.append(propertyResourceType);
		result.append(')');
		return result.toString();
	}

} //PropertyImpl
