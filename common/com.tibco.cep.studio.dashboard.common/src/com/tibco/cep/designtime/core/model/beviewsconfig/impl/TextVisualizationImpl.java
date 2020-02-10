/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.TextVisualization;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Text Visualization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextVisualizationImpl#isShowHeader <em>Show Header</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TextVisualizationImpl extends TwoDimVisualizationImpl implements TextVisualization {
	/**
	 * The default value of the '{@link #isShowHeader() <em>Show Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowHeader()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_HEADER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowHeader() <em>Show Header</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowHeader()
	 * @generated
	 * @ordered
	 */
	protected boolean showHeader = SHOW_HEADER_EDEFAULT;

	/**
	 * This is true if the Show Header attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean showHeaderESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TextVisualizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getTextVisualization();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowHeader() {
		return showHeader;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowHeader(boolean newShowHeader) {
		boolean oldShowHeader = showHeader;
		showHeader = newShowHeader;
		boolean oldShowHeaderESet = showHeaderESet;
		showHeaderESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_VISUALIZATION__SHOW_HEADER, oldShowHeader, showHeader, !oldShowHeaderESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetShowHeader() {
		boolean oldShowHeader = showHeader;
		boolean oldShowHeaderESet = showHeaderESet;
		showHeader = SHOW_HEADER_EDEFAULT;
		showHeaderESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.TEXT_VISUALIZATION__SHOW_HEADER, oldShowHeader, SHOW_HEADER_EDEFAULT, oldShowHeaderESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetShowHeader() {
		return showHeaderESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.TEXT_VISUALIZATION__SHOW_HEADER:
				return isShowHeader();
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
			case BEViewsConfigurationPackage.TEXT_VISUALIZATION__SHOW_HEADER:
				setShowHeader((Boolean)newValue);
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
			case BEViewsConfigurationPackage.TEXT_VISUALIZATION__SHOW_HEADER:
				unsetShowHeader();
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
			case BEViewsConfigurationPackage.TEXT_VISUALIZATION__SHOW_HEADER:
				return isSetShowHeader();
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
		result.append(" (showHeader: ");
		if (showHeaderESet) result.append(showHeader); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //TextVisualizationImpl
