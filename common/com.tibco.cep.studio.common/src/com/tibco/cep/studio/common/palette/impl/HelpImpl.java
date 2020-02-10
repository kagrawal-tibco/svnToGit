/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.palette.impl;

import com.tibco.cep.studio.common.palette.Help;
import com.tibco.cep.studio.common.palette.PalettePackage;
import com.tibco.cep.studio.common.palette.Tab;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Help</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.HelpImpl#getTab <em>Tab</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.palette.impl.HelpImpl#getContent <em>Content</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HelpImpl extends EObjectImpl implements Help {
	/**
	 * The default value of the '{@link #getTab() <em>Tab</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTab()
	 * @generated
	 * @ordered
	 */
	protected static final Tab TAB_EDEFAULT = Tab.GENERAL;

	/**
	 * The cached value of the '{@link #getTab() <em>Tab</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTab()
	 * @generated
	 * @ordered
	 */
	protected Tab tab = TAB_EDEFAULT;

	/**
	 * This is true if the Tab attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean tabESet;

	/**
	 * The default value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTENT_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected String content = CONTENT_EDEFAULT;

	/**
	 * This is true if the Content attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean contentESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HelpImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PalettePackage.Literals.HELP;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContent() {
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContent(String newContent) {
		String oldContent = content;
		content = newContent;
		boolean oldContentESet = contentESet;
		contentESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.HELP__CONTENT, oldContent, content, !oldContentESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetContent() {
		String oldContent = content;
		boolean oldContentESet = contentESet;
		content = CONTENT_EDEFAULT;
		contentESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.HELP__CONTENT, oldContent, CONTENT_EDEFAULT, oldContentESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetContent() {
		return contentESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Tab getTab() {
		return tab;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTab(Tab newTab) {
		Tab oldTab = tab;
		tab = newTab == null ? TAB_EDEFAULT : newTab;
		boolean oldTabESet = tabESet;
		tabESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PalettePackage.HELP__TAB, oldTab, tab, !oldTabESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTab() {
		Tab oldTab = tab;
		boolean oldTabESet = tabESet;
		tab = TAB_EDEFAULT;
		tabESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, PalettePackage.HELP__TAB, oldTab, TAB_EDEFAULT, oldTabESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTab() {
		return tabESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case PalettePackage.HELP__TAB:
				return getTab();
			case PalettePackage.HELP__CONTENT:
				return getContent();
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
			case PalettePackage.HELP__TAB:
				setTab((Tab)newValue);
				return;
			case PalettePackage.HELP__CONTENT:
				setContent((String)newValue);
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
			case PalettePackage.HELP__TAB:
				unsetTab();
				return;
			case PalettePackage.HELP__CONTENT:
				unsetContent();
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
			case PalettePackage.HELP__TAB:
				return isSetTab();
			case PalettePackage.HELP__CONTENT:
				return isSetContent();
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
		result.append(" (tab: ");
		if (tabESet) result.append(tab); else result.append("<unset>");
		result.append(", content: ");
		if (contentESet) result.append(content); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //HelpImpl
