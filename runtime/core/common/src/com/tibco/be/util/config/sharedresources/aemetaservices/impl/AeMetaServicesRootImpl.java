/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.aemetaservices.impl;

import com.tibco.be.util.config.sharedresources.aemetaservices.AeMetaServicesRoot;
import com.tibco.be.util.config.sharedresources.aemetaservices.AemetaservicesPackage;
import com.tibco.be.util.config.sharedresources.aemetaservices.Ssl;

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

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ae Meta Services Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.AeMetaServicesRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.AeMetaServicesRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.AeMetaServicesRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.aemetaservices.impl.AeMetaServicesRootImpl#getSsl <em>Ssl</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AeMetaServicesRootImpl extends EObjectImpl implements AeMetaServicesRoot {
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
	protected AeMetaServicesRootImpl() {
        super();
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	protected EClass eStaticClass() {
        return AemetaservicesPackage.Literals.AE_META_SERVICES_ROOT;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public FeatureMap getMixed() {
        if (mixed == null)
        {
            mixed = new BasicFeatureMap(this, AemetaservicesPackage.AE_META_SERVICES_ROOT__MIXED);
        }
        return mixed;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EMap<String, String> getXMLNSPrefixMap() {
        if (xMLNSPrefixMap == null)
        {
            xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, AemetaservicesPackage.AE_META_SERVICES_ROOT__XMLNS_PREFIX_MAP);
        }
        return xMLNSPrefixMap;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public EMap<String, String> getXSISchemaLocation() {
        if (xSISchemaLocation == null)
        {
            xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, AemetaservicesPackage.AE_META_SERVICES_ROOT__XSI_SCHEMA_LOCATION);
        }
        return xSISchemaLocation;
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public Ssl getSsl() {
        return (Ssl)getMixed().get(AemetaservicesPackage.Literals.AE_META_SERVICES_ROOT__SSL, true);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public NotificationChain basicSetSsl(Ssl newSsl, NotificationChain msgs) {
        return ((FeatureMap.Internal)getMixed()).basicAdd(AemetaservicesPackage.Literals.AE_META_SERVICES_ROOT__SSL, newSsl, msgs);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public void setSsl(Ssl newSsl) {
        ((FeatureMap.Internal)getMixed()).set(AemetaservicesPackage.Literals.AE_META_SERVICES_ROOT__SSL, newSsl);
    }

	/**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID)
        {
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__MIXED:
                return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__XMLNS_PREFIX_MAP:
                return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__XSI_SCHEMA_LOCATION:
                return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__SSL:
                return basicSetSsl(null, msgs);
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
        switch (featureID)
        {
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__MIXED:
                if (coreType) return getMixed();
                return ((FeatureMap.Internal)getMixed()).getWrapper();
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__XMLNS_PREFIX_MAP:
                if (coreType) return getXMLNSPrefixMap();
                else return getXMLNSPrefixMap().map();
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__XSI_SCHEMA_LOCATION:
                if (coreType) return getXSISchemaLocation();
                else return getXSISchemaLocation().map();
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__SSL:
                return getSsl();
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
        switch (featureID)
        {
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__MIXED:
                ((FeatureMap.Internal)getMixed()).set(newValue);
                return;
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__XMLNS_PREFIX_MAP:
                ((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
                return;
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__XSI_SCHEMA_LOCATION:
                ((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
                return;
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__SSL:
                setSsl((Ssl)newValue);
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
        switch (featureID)
        {
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__MIXED:
                getMixed().clear();
                return;
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__XMLNS_PREFIX_MAP:
                getXMLNSPrefixMap().clear();
                return;
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__XSI_SCHEMA_LOCATION:
                getXSISchemaLocation().clear();
                return;
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__SSL:
                setSsl((Ssl)null);
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
        switch (featureID)
        {
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__MIXED:
                return mixed != null && !mixed.isEmpty();
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__XMLNS_PREFIX_MAP:
                return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__XSI_SCHEMA_LOCATION:
                return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
            case AemetaservicesPackage.AE_META_SERVICES_ROOT__SSL:
                return getSsl() != null;
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

} //AeMetaServicesRootImpl
