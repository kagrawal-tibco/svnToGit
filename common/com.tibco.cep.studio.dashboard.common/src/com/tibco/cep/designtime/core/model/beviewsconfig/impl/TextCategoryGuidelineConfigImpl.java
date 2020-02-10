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
import com.tibco.cep.designtime.core.model.beviewsconfig.TextCategoryGuidelineConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Text Category Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextCategoryGuidelineConfigImpl#getHeaderAlignment <em>Header Alignment</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextCategoryGuidelineConfigImpl#getLabelAlignment <em>Label Alignment</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextCategoryGuidelineConfigImpl#getWidth <em>Width</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TextCategoryGuidelineConfigImpl extends CategoryGuidelineConfigImpl implements TextCategoryGuidelineConfig {
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
	 * The default value of the '{@link #getLabelAlignment() <em>Label Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelAlignment()
	 * @generated
	 * @ordered
	 */
	protected static final FieldAlignmentEnum LABEL_ALIGNMENT_EDEFAULT = FieldAlignmentEnum.CENTER;

	/**
	 * The cached value of the '{@link #getLabelAlignment() <em>Label Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelAlignment()
	 * @generated
	 * @ordered
	 */
	protected FieldAlignmentEnum labelAlignment = LABEL_ALIGNMENT_EDEFAULT;

	/**
	 * This is true if the Label Alignment attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean labelAlignmentESet;

	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final String WIDTH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected String width = WIDTH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TextCategoryGuidelineConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getTextCategoryGuidelineConfig();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__HEADER_ALIGNMENT, oldHeaderAlignment, headerAlignment, !oldHeaderAlignmentESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__HEADER_ALIGNMENT, oldHeaderAlignment, HEADER_ALIGNMENT_EDEFAULT, oldHeaderAlignmentESet));
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
	public FieldAlignmentEnum getLabelAlignment() {
		return labelAlignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabelAlignment(FieldAlignmentEnum newLabelAlignment) {
		FieldAlignmentEnum oldLabelAlignment = labelAlignment;
		labelAlignment = newLabelAlignment == null ? LABEL_ALIGNMENT_EDEFAULT : newLabelAlignment;
		boolean oldLabelAlignmentESet = labelAlignmentESet;
		labelAlignmentESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__LABEL_ALIGNMENT, oldLabelAlignment, labelAlignment, !oldLabelAlignmentESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLabelAlignment() {
		FieldAlignmentEnum oldLabelAlignment = labelAlignment;
		boolean oldLabelAlignmentESet = labelAlignmentESet;
		labelAlignment = LABEL_ALIGNMENT_EDEFAULT;
		labelAlignmentESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__LABEL_ALIGNMENT, oldLabelAlignment, LABEL_ALIGNMENT_EDEFAULT, oldLabelAlignmentESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLabelAlignment() {
		return labelAlignmentESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidth(String newWidth) {
		String oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__HEADER_ALIGNMENT:
				return getHeaderAlignment();
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__LABEL_ALIGNMENT:
				return getLabelAlignment();
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__WIDTH:
				return getWidth();
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
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__HEADER_ALIGNMENT:
				setHeaderAlignment((FieldAlignmentEnum)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__LABEL_ALIGNMENT:
				setLabelAlignment((FieldAlignmentEnum)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__WIDTH:
				setWidth((String)newValue);
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
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__HEADER_ALIGNMENT:
				unsetHeaderAlignment();
				return;
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__LABEL_ALIGNMENT:
				unsetLabelAlignment();
				return;
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__WIDTH:
				setWidth(WIDTH_EDEFAULT);
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
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__HEADER_ALIGNMENT:
				return isSetHeaderAlignment();
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__LABEL_ALIGNMENT:
				return isSetLabelAlignment();
			case BEViewsConfigurationPackage.TEXT_CATEGORY_GUIDELINE_CONFIG__WIDTH:
				return WIDTH_EDEFAULT == null ? width != null : !WIDTH_EDEFAULT.equals(width);
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
		result.append(", labelAlignment: ");
		if (labelAlignmentESet) result.append(labelAlignment); else result.append("<unset>");
		result.append(", width: ");
		result.append(width);
		result.append(')');
		return result.toString();
	}

} //TextCategoryGuidelineConfigImpl
