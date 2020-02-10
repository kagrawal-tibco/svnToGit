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
import com.tibco.cep.designtime.core.model.beviewsconfig.FieldAlignmentEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.FormatStyle;
import com.tibco.cep.designtime.core.model.beviewsconfig.TextValueDataConfig;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Text Value Data Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextValueDataConfigImpl#getHeaderName <em>Header Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextValueDataConfigImpl#getHeaderFormatStyle <em>Header Format Style</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextValueDataConfigImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextValueDataConfigImpl#getAlignment <em>Alignment</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TextValueDataConfigImpl extends DataConfigImpl implements TextValueDataConfig {
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
	 * The default value of the '{@link #getAlignment() <em>Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlignment()
	 * @generated
	 * @ordered
	 */
	protected static final FieldAlignmentEnum ALIGNMENT_EDEFAULT = FieldAlignmentEnum.CENTER;

	/**
	 * The cached value of the '{@link #getAlignment() <em>Alignment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlignment()
	 * @generated
	 * @ordered
	 */
	protected FieldAlignmentEnum alignment = ALIGNMENT_EDEFAULT;

	/**
	 * This is true if the Alignment attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean alignmentESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TextValueDataConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getTextValueDataConfig();
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_NAME, oldHeaderName, headerName));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_FORMAT_STYLE, oldHeaderFormatStyle, newHeaderFormatStyle);
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
				msgs = ((InternalEObject)headerFormatStyle).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_FORMAT_STYLE, null, msgs);
			if (newHeaderFormatStyle != null)
				msgs = ((InternalEObject)newHeaderFormatStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_FORMAT_STYLE, null, msgs);
			msgs = basicSetHeaderFormatStyle(newHeaderFormatStyle, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_FORMAT_STYLE, newHeaderFormatStyle, newHeaderFormatStyle));
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
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldAlignmentEnum getAlignment() {
		return alignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlignment(FieldAlignmentEnum newAlignment) {
		FieldAlignmentEnum oldAlignment = alignment;
		alignment = newAlignment == null ? ALIGNMENT_EDEFAULT : newAlignment;
		boolean oldAlignmentESet = alignmentESet;
		alignmentESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__ALIGNMENT, oldAlignment, alignment, !oldAlignmentESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAlignment() {
		FieldAlignmentEnum oldAlignment = alignment;
		boolean oldAlignmentESet = alignmentESet;
		alignment = ALIGNMENT_EDEFAULT;
		alignmentESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__ALIGNMENT, oldAlignment, ALIGNMENT_EDEFAULT, oldAlignmentESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAlignment() {
		return alignmentESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_FORMAT_STYLE:
				return basicSetHeaderFormatStyle(null, msgs);
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
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_NAME:
				return getHeaderName();
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_FORMAT_STYLE:
				return getHeaderFormatStyle();
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__WIDTH:
				return getWidth();
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__ALIGNMENT:
				return getAlignment();
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
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_NAME:
				setHeaderName((String)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_FORMAT_STYLE:
				setHeaderFormatStyle((FormatStyle)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__WIDTH:
				setWidth((String)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__ALIGNMENT:
				setAlignment((FieldAlignmentEnum)newValue);
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
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_NAME:
				setHeaderName(HEADER_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_FORMAT_STYLE:
				setHeaderFormatStyle((FormatStyle)null);
				return;
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__ALIGNMENT:
				unsetAlignment();
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
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_NAME:
				return HEADER_NAME_EDEFAULT == null ? headerName != null : !HEADER_NAME_EDEFAULT.equals(headerName);
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__HEADER_FORMAT_STYLE:
				return headerFormatStyle != null;
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__WIDTH:
				return WIDTH_EDEFAULT == null ? width != null : !WIDTH_EDEFAULT.equals(width);
			case BEViewsConfigurationPackage.TEXT_VALUE_DATA_CONFIG__ALIGNMENT:
				return isSetAlignment();
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
		result.append(", width: ");
		result.append(width);
		result.append(", alignment: ");
		if (alignmentESet) result.append(alignment); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //TextValueDataConfigImpl
