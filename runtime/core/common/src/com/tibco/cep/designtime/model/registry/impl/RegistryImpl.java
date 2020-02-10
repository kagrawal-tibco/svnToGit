/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.Registry;
import com.tibco.cep.designtime.model.registry.RegistryPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Registry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.RegistryImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.RegistryImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.RegistryImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.RegistryImpl#getAddOn <em>Add On</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RegistryImpl extends EObjectImpl implements Registry {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RegistryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RegistryPackage.Literals.REGISTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, RegistryPackage.REGISTRY__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, RegistryPackage.REGISTRY__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, RegistryPackage.REGISTRY__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddOn getAddOn() {
		return (AddOn)getMixed().get(RegistryPackage.Literals.REGISTRY__ADD_ON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAddOn(AddOn newAddOn, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(RegistryPackage.Literals.REGISTRY__ADD_ON, newAddOn, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddOn(AddOn newAddOn) {
		((FeatureMap.Internal)getMixed()).set(RegistryPackage.Literals.REGISTRY__ADD_ON, newAddOn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RegistryPackage.REGISTRY__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case RegistryPackage.REGISTRY__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case RegistryPackage.REGISTRY__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case RegistryPackage.REGISTRY__ADD_ON:
				return basicSetAddOn(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RegistryPackage.REGISTRY__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case RegistryPackage.REGISTRY__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case RegistryPackage.REGISTRY__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case RegistryPackage.REGISTRY__ADD_ON:
				return getAddOn();
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
			case RegistryPackage.REGISTRY__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case RegistryPackage.REGISTRY__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case RegistryPackage.REGISTRY__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case RegistryPackage.REGISTRY__ADD_ON:
				setAddOn((AddOn)newValue);
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
			case RegistryPackage.REGISTRY__MIXED:
				getMixed().clear();
				return;
			case RegistryPackage.REGISTRY__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case RegistryPackage.REGISTRY__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case RegistryPackage.REGISTRY__ADD_ON:
				setAddOn((AddOn)null);
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
			case RegistryPackage.REGISTRY__MIXED:
				return mixed != null && !mixed.isEmpty();
			case RegistryPackage.REGISTRY__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case RegistryPackage.REGISTRY__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case RegistryPackage.REGISTRY__ADD_ON:
				return getAddOn() != null;
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //RegistryImpl
