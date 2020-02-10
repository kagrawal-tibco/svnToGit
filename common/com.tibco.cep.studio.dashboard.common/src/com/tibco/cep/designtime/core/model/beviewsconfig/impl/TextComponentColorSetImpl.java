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
import com.tibco.cep.designtime.core.model.beviewsconfig.TextComponentColorSet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Text Component Color Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentColorSetImpl#getHeaderColor <em>Header Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentColorSetImpl#getHeaderRollOverColor <em>Header Roll Over Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentColorSetImpl#getHeaderTextFontColor <em>Header Text Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentColorSetImpl#getHeaderSeparatorColor <em>Header Separator Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentColorSetImpl#getCellColor <em>Cell Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentColorSetImpl#getCellTextFontColor <em>Cell Text Font Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentColorSetImpl#getRowSeparatorColor <em>Row Separator Color</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.TextComponentColorSetImpl#getRowRollOverColor <em>Row Roll Over Color</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TextComponentColorSetImpl extends ComponentColorSetImpl implements TextComponentColorSet {
	/**
	 * The default value of the '{@link #getHeaderColor() <em>Header Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderColor()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADER_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaderColor() <em>Header Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderColor()
	 * @generated
	 * @ordered
	 */
	protected String headerColor = HEADER_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeaderRollOverColor() <em>Header Roll Over Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderRollOverColor()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADER_ROLL_OVER_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaderRollOverColor() <em>Header Roll Over Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderRollOverColor()
	 * @generated
	 * @ordered
	 */
	protected String headerRollOverColor = HEADER_ROLL_OVER_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeaderTextFontColor() <em>Header Text Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderTextFontColor()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADER_TEXT_FONT_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaderTextFontColor() <em>Header Text Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderTextFontColor()
	 * @generated
	 * @ordered
	 */
	protected String headerTextFontColor = HEADER_TEXT_FONT_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeaderSeparatorColor() <em>Header Separator Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderSeparatorColor()
	 * @generated
	 * @ordered
	 */
	protected static final String HEADER_SEPARATOR_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getHeaderSeparatorColor() <em>Header Separator Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeaderSeparatorColor()
	 * @generated
	 * @ordered
	 */
	protected String headerSeparatorColor = HEADER_SEPARATOR_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getCellColor() <em>Cell Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCellColor()
	 * @generated
	 * @ordered
	 */
	protected static final String CELL_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCellColor() <em>Cell Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCellColor()
	 * @generated
	 * @ordered
	 */
	protected String cellColor = CELL_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getCellTextFontColor() <em>Cell Text Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCellTextFontColor()
	 * @generated
	 * @ordered
	 */
	protected static final String CELL_TEXT_FONT_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCellTextFontColor() <em>Cell Text Font Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCellTextFontColor()
	 * @generated
	 * @ordered
	 */
	protected String cellTextFontColor = CELL_TEXT_FONT_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getRowSeparatorColor() <em>Row Separator Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRowSeparatorColor()
	 * @generated
	 * @ordered
	 */
	protected static final String ROW_SEPARATOR_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRowSeparatorColor() <em>Row Separator Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRowSeparatorColor()
	 * @generated
	 * @ordered
	 */
	protected String rowSeparatorColor = ROW_SEPARATOR_COLOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getRowRollOverColor() <em>Row Roll Over Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRowRollOverColor()
	 * @generated
	 * @ordered
	 */
	protected static final String ROW_ROLL_OVER_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRowRollOverColor() <em>Row Roll Over Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRowRollOverColor()
	 * @generated
	 * @ordered
	 */
	protected String rowRollOverColor = ROW_ROLL_OVER_COLOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TextComponentColorSetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getTextComponentColorSet();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeaderColor() {
		return headerColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderColor(String newHeaderColor) {
		String oldHeaderColor = headerColor;
		headerColor = newHeaderColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_COLOR, oldHeaderColor, headerColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeaderRollOverColor() {
		return headerRollOverColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderRollOverColor(String newHeaderRollOverColor) {
		String oldHeaderRollOverColor = headerRollOverColor;
		headerRollOverColor = newHeaderRollOverColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_ROLL_OVER_COLOR, oldHeaderRollOverColor, headerRollOverColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeaderTextFontColor() {
		return headerTextFontColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderTextFontColor(String newHeaderTextFontColor) {
		String oldHeaderTextFontColor = headerTextFontColor;
		headerTextFontColor = newHeaderTextFontColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_TEXT_FONT_COLOR, oldHeaderTextFontColor, headerTextFontColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getHeaderSeparatorColor() {
		return headerSeparatorColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeaderSeparatorColor(String newHeaderSeparatorColor) {
		String oldHeaderSeparatorColor = headerSeparatorColor;
		headerSeparatorColor = newHeaderSeparatorColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_SEPARATOR_COLOR, oldHeaderSeparatorColor, headerSeparatorColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCellColor() {
		return cellColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCellColor(String newCellColor) {
		String oldCellColor = cellColor;
		cellColor = newCellColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__CELL_COLOR, oldCellColor, cellColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCellTextFontColor() {
		return cellTextFontColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCellTextFontColor(String newCellTextFontColor) {
		String oldCellTextFontColor = cellTextFontColor;
		cellTextFontColor = newCellTextFontColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__CELL_TEXT_FONT_COLOR, oldCellTextFontColor, cellTextFontColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRowSeparatorColor() {
		return rowSeparatorColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRowSeparatorColor(String newRowSeparatorColor) {
		String oldRowSeparatorColor = rowSeparatorColor;
		rowSeparatorColor = newRowSeparatorColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__ROW_SEPARATOR_COLOR, oldRowSeparatorColor, rowSeparatorColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRowRollOverColor() {
		return rowRollOverColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRowRollOverColor(String newRowRollOverColor) {
		String oldRowRollOverColor = rowRollOverColor;
		rowRollOverColor = newRowRollOverColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__ROW_ROLL_OVER_COLOR, oldRowRollOverColor, rowRollOverColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_COLOR:
				return getHeaderColor();
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_ROLL_OVER_COLOR:
				return getHeaderRollOverColor();
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_TEXT_FONT_COLOR:
				return getHeaderTextFontColor();
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_SEPARATOR_COLOR:
				return getHeaderSeparatorColor();
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__CELL_COLOR:
				return getCellColor();
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__CELL_TEXT_FONT_COLOR:
				return getCellTextFontColor();
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__ROW_SEPARATOR_COLOR:
				return getRowSeparatorColor();
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__ROW_ROLL_OVER_COLOR:
				return getRowRollOverColor();
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
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_COLOR:
				setHeaderColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_ROLL_OVER_COLOR:
				setHeaderRollOverColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_TEXT_FONT_COLOR:
				setHeaderTextFontColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_SEPARATOR_COLOR:
				setHeaderSeparatorColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__CELL_COLOR:
				setCellColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__CELL_TEXT_FONT_COLOR:
				setCellTextFontColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__ROW_SEPARATOR_COLOR:
				setRowSeparatorColor((String)newValue);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__ROW_ROLL_OVER_COLOR:
				setRowRollOverColor((String)newValue);
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
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_COLOR:
				setHeaderColor(HEADER_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_ROLL_OVER_COLOR:
				setHeaderRollOverColor(HEADER_ROLL_OVER_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_TEXT_FONT_COLOR:
				setHeaderTextFontColor(HEADER_TEXT_FONT_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_SEPARATOR_COLOR:
				setHeaderSeparatorColor(HEADER_SEPARATOR_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__CELL_COLOR:
				setCellColor(CELL_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__CELL_TEXT_FONT_COLOR:
				setCellTextFontColor(CELL_TEXT_FONT_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__ROW_SEPARATOR_COLOR:
				setRowSeparatorColor(ROW_SEPARATOR_COLOR_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__ROW_ROLL_OVER_COLOR:
				setRowRollOverColor(ROW_ROLL_OVER_COLOR_EDEFAULT);
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
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_COLOR:
				return HEADER_COLOR_EDEFAULT == null ? headerColor != null : !HEADER_COLOR_EDEFAULT.equals(headerColor);
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_ROLL_OVER_COLOR:
				return HEADER_ROLL_OVER_COLOR_EDEFAULT == null ? headerRollOverColor != null : !HEADER_ROLL_OVER_COLOR_EDEFAULT.equals(headerRollOverColor);
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_TEXT_FONT_COLOR:
				return HEADER_TEXT_FONT_COLOR_EDEFAULT == null ? headerTextFontColor != null : !HEADER_TEXT_FONT_COLOR_EDEFAULT.equals(headerTextFontColor);
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__HEADER_SEPARATOR_COLOR:
				return HEADER_SEPARATOR_COLOR_EDEFAULT == null ? headerSeparatorColor != null : !HEADER_SEPARATOR_COLOR_EDEFAULT.equals(headerSeparatorColor);
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__CELL_COLOR:
				return CELL_COLOR_EDEFAULT == null ? cellColor != null : !CELL_COLOR_EDEFAULT.equals(cellColor);
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__CELL_TEXT_FONT_COLOR:
				return CELL_TEXT_FONT_COLOR_EDEFAULT == null ? cellTextFontColor != null : !CELL_TEXT_FONT_COLOR_EDEFAULT.equals(cellTextFontColor);
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__ROW_SEPARATOR_COLOR:
				return ROW_SEPARATOR_COLOR_EDEFAULT == null ? rowSeparatorColor != null : !ROW_SEPARATOR_COLOR_EDEFAULT.equals(rowSeparatorColor);
			case BEViewsConfigurationPackage.TEXT_COMPONENT_COLOR_SET__ROW_ROLL_OVER_COLOR:
				return ROW_ROLL_OVER_COLOR_EDEFAULT == null ? rowRollOverColor != null : !ROW_ROLL_OVER_COLOR_EDEFAULT.equals(rowRollOverColor);
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
		result.append(" (headerColor: ");
		result.append(headerColor);
		result.append(", headerRollOverColor: ");
		result.append(headerRollOverColor);
		result.append(", headerTextFontColor: ");
		result.append(headerTextFontColor);
		result.append(", headerSeparatorColor: ");
		result.append(headerSeparatorColor);
		result.append(", cellColor: ");
		result.append(cellColor);
		result.append(", cellTextFontColor: ");
		result.append(cellTextFontColor);
		result.append(", rowSeparatorColor: ");
		result.append(rowSeparatorColor);
		result.append(", rowRollOverColor: ");
		result.append(rowRollOverColor);
		result.append(')');
		return result.toString();
	}

} //TextComponentColorSetImpl
