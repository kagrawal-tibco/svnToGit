/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.CategoryGuidelineConfig;
import com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle;
import com.tibco.cep.designtime.core.model.beviewsconfig.SortEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Category Guideline Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.CategoryGuidelineConfigImpl#getHeaderName <em>Header Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.CategoryGuidelineConfigImpl#getHeaderFormatStyle <em>Header Format Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.CategoryGuidelineConfigImpl#getLabelFormatStyle <em>Label Format Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.CategoryGuidelineConfigImpl#getSortOrder <em>Sort Order</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.CategoryGuidelineConfigImpl#isDuplicatesAllowed <em>Duplicates Allowed</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class CategoryGuidelineConfigImpl extends BEViewsElementImpl implements CategoryGuidelineConfig {
	/**
	 * The default value of the '{@link #getHeaderName() <em>Header Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderName()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADER_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaderName() <em>Header Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderName()
	 * @generated
	 * @ordered
	 */
	protected String headerName = HEADER_NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getHeaderFormatStyle() <em>Header Format Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderFormatStyle()
	 * @generated
	 * @ordered
	 */
	protected FormatStyle headerFormatStyle;

	/**
	 * The cached value of the '{@link #getLabelFormatStyle() <em>Label Format Style</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelFormatStyle()
	 * @generated
	 * @ordered
	 */
	protected FormatStyle labelFormatStyle;

	/**
	 * The default value of the '{@link #getSortOrder() <em>Sort Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSortOrder()
	 * @generated
	 * @ordered
	 */
	protected static final SortEnum SORT_ORDER_EDEFAULT = SortEnum.UNSORTED;

	/**
	 * The cached value of the '{@link #getSortOrder() <em>Sort Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSortOrder()
	 * @generated
	 * @ordered
	 */
	protected SortEnum sortOrder = SORT_ORDER_EDEFAULT;

	/**
	 * This is true if the Sort Order attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean sortOrderESet;

	/**
	 * The default value of the '{@link #isDuplicatesAllowed() <em>Duplicates Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDuplicatesAllowed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DUPLICATES_ALLOWED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDuplicatesAllowed() <em>Duplicates Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDuplicatesAllowed()
	 * @generated
	 * @ordered
	 */
	protected boolean duplicatesAllowed = DUPLICATES_ALLOWED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CategoryGuidelineConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getCategoryGuidelineConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeaderName() {
		return headerName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderName(String newHeaderName) {
		String oldHeaderName = headerName;
		headerName = newHeaderName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_NAME, oldHeaderName, headerName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FormatStyle getHeaderFormatStyle() {
		return headerFormatStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHeaderFormatStyle(FormatStyle newHeaderFormatStyle, NotificationChain msgs) {
		FormatStyle oldHeaderFormatStyle = headerFormatStyle;
		headerFormatStyle = newHeaderFormatStyle;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE, oldHeaderFormatStyle, newHeaderFormatStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderFormatStyle(FormatStyle newHeaderFormatStyle) {
		if (newHeaderFormatStyle != headerFormatStyle) {
			NotificationChain msgs = null;
			if (headerFormatStyle != null)
				msgs = ((InternalEObject)headerFormatStyle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE, null, msgs);
			if (newHeaderFormatStyle != null)
				msgs = ((InternalEObject)newHeaderFormatStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE, null, msgs);
			msgs = basicSetHeaderFormatStyle(newHeaderFormatStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE, newHeaderFormatStyle, newHeaderFormatStyle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FormatStyle getLabelFormatStyle() {
		return labelFormatStyle;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLabelFormatStyle(FormatStyle newLabelFormatStyle, NotificationChain msgs) {
		FormatStyle oldLabelFormatStyle = labelFormatStyle;
		labelFormatStyle = newLabelFormatStyle;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE, oldLabelFormatStyle, newLabelFormatStyle);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLabelFormatStyle(FormatStyle newLabelFormatStyle) {
		if (newLabelFormatStyle != labelFormatStyle) {
			NotificationChain msgs = null;
			if (labelFormatStyle != null)
				msgs = ((InternalEObject)labelFormatStyle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE, null, msgs);
			if (newLabelFormatStyle != null)
				msgs = ((InternalEObject)newLabelFormatStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE, null, msgs);
			msgs = basicSetLabelFormatStyle(newLabelFormatStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE, newLabelFormatStyle, newLabelFormatStyle));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SortEnum getSortOrder() {
		return sortOrder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSortOrder(SortEnum newSortOrder) {
		SortEnum oldSortOrder = sortOrder;
		sortOrder = newSortOrder == null ? SORT_ORDER_EDEFAULT : newSortOrder;
		boolean oldSortOrderESet = sortOrderESet;
		sortOrderESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__SORT_ORDER, oldSortOrder, sortOrder, !oldSortOrderESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSortOrder() {
		SortEnum oldSortOrder = sortOrder;
		boolean oldSortOrderESet = sortOrderESet;
		sortOrder = SORT_ORDER_EDEFAULT;
		sortOrderESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__SORT_ORDER, oldSortOrder, SORT_ORDER_EDEFAULT, oldSortOrderESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSortOrder() {
		return sortOrderESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDuplicatesAllowed() {
		return duplicatesAllowed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDuplicatesAllowed(boolean newDuplicatesAllowed) {
		boolean oldDuplicatesAllowed = duplicatesAllowed;
		duplicatesAllowed = newDuplicatesAllowed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__DUPLICATES_ALLOWED, oldDuplicatesAllowed, duplicatesAllowed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE:
				return basicSetHeaderFormatStyle(null, msgs);
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE:
				return basicSetLabelFormatStyle(null, msgs);
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
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_NAME:
				return getHeaderName();
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE:
				return getHeaderFormatStyle();
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE:
				return getLabelFormatStyle();
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__SORT_ORDER:
				return getSortOrder();
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__DUPLICATES_ALLOWED:
				return isDuplicatesAllowed();
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
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_NAME:
				setHeaderName((String)newValue);
				return;
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE:
				setHeaderFormatStyle((FormatStyle)newValue);
				return;
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE:
				setLabelFormatStyle((FormatStyle)newValue);
				return;
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__SORT_ORDER:
				setSortOrder((SortEnum)newValue);
				return;
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__DUPLICATES_ALLOWED:
				setDuplicatesAllowed((Boolean)newValue);
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
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_NAME:
				setHeaderName(HEADER_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE:
				setHeaderFormatStyle((FormatStyle)null);
				return;
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE:
				setLabelFormatStyle((FormatStyle)null);
				return;
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__SORT_ORDER:
				unsetSortOrder();
				return;
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__DUPLICATES_ALLOWED:
				setDuplicatesAllowed(DUPLICATES_ALLOWED_EDEFAULT);
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
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_NAME:
				return HEADER_NAME_EDEFAULT == null ? headerName != null : !HEADER_NAME_EDEFAULT.equals(headerName);
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__HEADER_FORMAT_STYLE:
				return headerFormatStyle != null;
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__LABEL_FORMAT_STYLE:
				return labelFormatStyle != null;
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__SORT_ORDER:
				return isSetSortOrder();
			case BEViewsConfigurationPackage.CATEGORY_GUIDELINE_CONFIG__DUPLICATES_ALLOWED:
				return duplicatesAllowed != DUPLICATES_ALLOWED_EDEFAULT;
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
		result.append(" (headerName: ");
		result.append(headerName);
		result.append(", sortOrder: ");
		if (sortOrderESet) result.append(sortOrder); else result.append("<unset>");
		result.append(", duplicatesAllowed: ");
		result.append(duplicatesAllowed);
		result.append(')');
		return result.toString();
	}

} //CategoryGuidelineConfigImpl
