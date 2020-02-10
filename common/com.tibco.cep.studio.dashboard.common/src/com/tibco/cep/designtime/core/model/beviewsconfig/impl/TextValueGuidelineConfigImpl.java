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
import com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.TextValueGuidelineConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Text Value Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextValueGuidelineConfigImpl#getHeaderAlignment <em>Header Alignment</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TextValueGuidelineConfigImpl extends ValueGuidelineConfigImpl implements TextValueGuidelineConfig {
	/**
	 * The default value of the '{@link #getHeaderAlignment() <em>Header Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderAlignment()
	 * @generated
	 * @ordered
	 */
	protected static final FieldAlignmentEnum HEADER_ALIGNMENT_EDEFAULT = FieldAlignmentEnum.CENTER;

	/**
	 * The cached value of the '{@link #getHeaderAlignment() <em>Header Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderAlignment()
	 * @generated
	 * @ordered
	 */
	protected FieldAlignmentEnum headerAlignment = HEADER_ALIGNMENT_EDEFAULT;

	/**
	 * This is true if the Header Alignment attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean headerAlignmentESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TextValueGuidelineConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getTextValueGuidelineConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldAlignmentEnum getHeaderAlignment() {
		return headerAlignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderAlignment(FieldAlignmentEnum newHeaderAlignment) {
		FieldAlignmentEnum oldHeaderAlignment = headerAlignment;
		headerAlignment = newHeaderAlignment == null ? HEADER_ALIGNMENT_EDEFAULT : newHeaderAlignment;
		boolean oldHeaderAlignmentESet = headerAlignmentESet;
		headerAlignmentESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_VALUE_GUIDELINE_CONFIG__HEADER_ALIGNMENT, oldHeaderAlignment, headerAlignment, !oldHeaderAlignmentESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetHeaderAlignment() {
		FieldAlignmentEnum oldHeaderAlignment = headerAlignment;
		boolean oldHeaderAlignmentESet = headerAlignmentESet;
		headerAlignment = HEADER_ALIGNMENT_EDEFAULT;
		headerAlignmentESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.TEXT_VALUE_GUIDELINE_CONFIG__HEADER_ALIGNMENT, oldHeaderAlignment, HEADER_ALIGNMENT_EDEFAULT, oldHeaderAlignmentESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetHeaderAlignment() {
		return headerAlignmentESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.TEXT_VALUE_GUIDELINE_CONFIG__HEADER_ALIGNMENT:
				return getHeaderAlignment();
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
			case BEViewsConfigurationPackage.TEXT_VALUE_GUIDELINE_CONFIG__HEADER_ALIGNMENT:
				setHeaderAlignment((FieldAlignmentEnum)newValue);
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
			case BEViewsConfigurationPackage.TEXT_VALUE_GUIDELINE_CONFIG__HEADER_ALIGNMENT:
				unsetHeaderAlignment();
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
			case BEViewsConfigurationPackage.TEXT_VALUE_GUIDELINE_CONFIG__HEADER_ALIGNMENT:
				return isSetHeaderAlignment();
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
		result.append(" (headerAlignment: ");
		if (headerAlignmentESet) result.append(headerAlignment); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //TextValueGuidelineConfigImpl
