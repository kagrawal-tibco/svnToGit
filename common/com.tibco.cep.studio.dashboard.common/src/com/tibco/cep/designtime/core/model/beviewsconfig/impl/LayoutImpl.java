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
import com.tibco.cep.designtime.core.model.beviewsconfig.Layout;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Layout</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LayoutImpl#isRepositioningAllowed <em>Repositioning Allowed</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LayoutImpl#getComponentWidth <em>Component Width</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.LayoutImpl#getComponentHeight <em>Component Height</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class LayoutImpl extends BEViewsElementImpl implements Layout {
	/**
	 * The default value of the '{@link #isRepositioningAllowed() <em>Repositioning Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRepositioningAllowed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REPOSITIONING_ALLOWED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRepositioningAllowed() <em>Repositioning Allowed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRepositioningAllowed()
	 * @generated
	 * @ordered
	 */
	protected boolean repositioningAllowed = REPOSITIONING_ALLOWED_EDEFAULT;

	/**
	 * This is true if the Repositioning Allowed attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean repositioningAllowedESet;

	/**
	 * The default value of the '{@link #getComponentWidth() <em>Component Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int COMPONENT_WIDTH_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getComponentWidth() <em>Component Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentWidth()
	 * @generated
	 * @ordered
	 */
	protected int componentWidth = COMPONENT_WIDTH_EDEFAULT;

	/**
	 * This is true if the Component Width attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean componentWidthESet;

	/**
	 * The default value of the '{@link #getComponentHeight() <em>Component Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int COMPONENT_HEIGHT_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getComponentHeight() <em>Component Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentHeight()
	 * @generated
	 * @ordered
	 */
	protected int componentHeight = COMPONENT_HEIGHT_EDEFAULT;

	/**
	 * This is true if the Component Height attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean componentHeightESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LayoutImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getLayout();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRepositioningAllowed() {
		return repositioningAllowed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRepositioningAllowed(boolean newRepositioningAllowed) {
		boolean oldRepositioningAllowed = repositioningAllowed;
		repositioningAllowed = newRepositioningAllowed;
		boolean oldRepositioningAllowedESet = repositioningAllowedESet;
		repositioningAllowedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LAYOUT__REPOSITIONING_ALLOWED, oldRepositioningAllowed, repositioningAllowed, !oldRepositioningAllowedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRepositioningAllowed() {
		boolean oldRepositioningAllowed = repositioningAllowed;
		boolean oldRepositioningAllowedESet = repositioningAllowedESet;
		repositioningAllowed = REPOSITIONING_ALLOWED_EDEFAULT;
		repositioningAllowedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.LAYOUT__REPOSITIONING_ALLOWED, oldRepositioningAllowed, REPOSITIONING_ALLOWED_EDEFAULT, oldRepositioningAllowedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRepositioningAllowed() {
		return repositioningAllowedESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getComponentWidth() {
		return componentWidth;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentWidth(int newComponentWidth) {
		int oldComponentWidth = componentWidth;
		componentWidth = newComponentWidth;
		boolean oldComponentWidthESet = componentWidthESet;
		componentWidthESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LAYOUT__COMPONENT_WIDTH, oldComponentWidth, componentWidth, !oldComponentWidthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetComponentWidth() {
		int oldComponentWidth = componentWidth;
		boolean oldComponentWidthESet = componentWidthESet;
		componentWidth = COMPONENT_WIDTH_EDEFAULT;
		componentWidthESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.LAYOUT__COMPONENT_WIDTH, oldComponentWidth, COMPONENT_WIDTH_EDEFAULT, oldComponentWidthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetComponentWidth() {
		return componentWidthESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getComponentHeight() {
		return componentHeight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentHeight(int newComponentHeight) {
		int oldComponentHeight = componentHeight;
		componentHeight = newComponentHeight;
		boolean oldComponentHeightESet = componentHeightESet;
		componentHeightESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.LAYOUT__COMPONENT_HEIGHT, oldComponentHeight, componentHeight, !oldComponentHeightESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetComponentHeight() {
		int oldComponentHeight = componentHeight;
		boolean oldComponentHeightESet = componentHeightESet;
		componentHeight = COMPONENT_HEIGHT_EDEFAULT;
		componentHeightESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.LAYOUT__COMPONENT_HEIGHT, oldComponentHeight, COMPONENT_HEIGHT_EDEFAULT, oldComponentHeightESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetComponentHeight() {
		return componentHeightESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.LAYOUT__REPOSITIONING_ALLOWED:
				return isRepositioningAllowed();
			case BEViewsConfigurationPackage.LAYOUT__COMPONENT_WIDTH:
				return getComponentWidth();
			case BEViewsConfigurationPackage.LAYOUT__COMPONENT_HEIGHT:
				return getComponentHeight();
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
			case BEViewsConfigurationPackage.LAYOUT__REPOSITIONING_ALLOWED:
				setRepositioningAllowed((Boolean)newValue);
				return;
			case BEViewsConfigurationPackage.LAYOUT__COMPONENT_WIDTH:
				setComponentWidth((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.LAYOUT__COMPONENT_HEIGHT:
				setComponentHeight((Integer)newValue);
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
			case BEViewsConfigurationPackage.LAYOUT__REPOSITIONING_ALLOWED:
				unsetRepositioningAllowed();
				return;
			case BEViewsConfigurationPackage.LAYOUT__COMPONENT_WIDTH:
				unsetComponentWidth();
				return;
			case BEViewsConfigurationPackage.LAYOUT__COMPONENT_HEIGHT:
				unsetComponentHeight();
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
			case BEViewsConfigurationPackage.LAYOUT__REPOSITIONING_ALLOWED:
				return isSetRepositioningAllowed();
			case BEViewsConfigurationPackage.LAYOUT__COMPONENT_WIDTH:
				return isSetComponentWidth();
			case BEViewsConfigurationPackage.LAYOUT__COMPONENT_HEIGHT:
				return isSetComponentHeight();
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
		result.append(" (repositioningAllowed: ");
		if (repositioningAllowedESet) result.append(repositioningAllowed); else result.append("<unset>");
		result.append(", componentWidth: ");
		if (componentWidthESet) result.append(componentWidth); else result.append("<unset>");
		result.append(", componentHeight: ");
		if (componentHeightESet) result.append(componentHeight); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //LayoutImpl
