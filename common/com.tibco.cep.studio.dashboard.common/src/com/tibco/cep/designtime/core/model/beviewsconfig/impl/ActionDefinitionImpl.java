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

import com.tibco.cep.designtime.core.model.beviewsconfig.ActionDefinition;
import com.tibco.cep.designtime.core.model.beviewsconfig.ActionEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Action Definition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionDefinitionImpl#getText <em>Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionDefinitionImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ActionDefinitionImpl#getCommandURL <em>Command URL</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActionDefinitionImpl extends BEViewsElementImpl implements ActionDefinition {
	/**
	 * The default value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected static final String TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getText() <em>Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getText()
	 * @generated
	 * @ordered
	 */
	protected String text = TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final ActionEnum TYPE_EDEFAULT = ActionEnum.LAUNCHQUERY;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected ActionEnum type = TYPE_EDEFAULT;

	/**
	 * This is true if the Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean typeESet;

	/**
	 * The default value of the '{@link #getCommandURL() <em>Command URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommandURL()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMAND_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCommandURL() <em>Command URL</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCommandURL()
	 * @generated
	 * @ordered
	 */
	protected String commandURL = COMMAND_URL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionDefinitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getActionDefinition();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getText() {
		return text;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setText(String newText) {
		String oldText = text;
		text = newText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ACTION_DEFINITION__TEXT, oldText, text));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionEnum getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ActionEnum newType) {
		ActionEnum oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		boolean oldTypeESet = typeESet;
		typeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ACTION_DEFINITION__TYPE, oldType, type, !oldTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetType() {
		ActionEnum oldType = type;
		boolean oldTypeESet = typeESet;
		type = TYPE_EDEFAULT;
		typeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.ACTION_DEFINITION__TYPE, oldType, TYPE_EDEFAULT, oldTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetType() {
		return typeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCommandURL() {
		return commandURL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCommandURL(String newCommandURL) {
		String oldCommandURL = commandURL;
		commandURL = newCommandURL;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.ACTION_DEFINITION__COMMAND_URL, oldCommandURL, commandURL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.ACTION_DEFINITION__TEXT:
				return getText();
			case BEViewsConfigurationPackage.ACTION_DEFINITION__TYPE:
				return getType();
			case BEViewsConfigurationPackage.ACTION_DEFINITION__COMMAND_URL:
				return getCommandURL();
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
			case BEViewsConfigurationPackage.ACTION_DEFINITION__TEXT:
				setText((String)newValue);
				return;
			case BEViewsConfigurationPackage.ACTION_DEFINITION__TYPE:
				setType((ActionEnum)newValue);
				return;
			case BEViewsConfigurationPackage.ACTION_DEFINITION__COMMAND_URL:
				setCommandURL((String)newValue);
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
			case BEViewsConfigurationPackage.ACTION_DEFINITION__TEXT:
				setText(TEXT_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.ACTION_DEFINITION__TYPE:
				unsetType();
				return;
			case BEViewsConfigurationPackage.ACTION_DEFINITION__COMMAND_URL:
				setCommandURL(COMMAND_URL_EDEFAULT);
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
			case BEViewsConfigurationPackage.ACTION_DEFINITION__TEXT:
				return TEXT_EDEFAULT == null ? text != null : !TEXT_EDEFAULT.equals(text);
			case BEViewsConfigurationPackage.ACTION_DEFINITION__TYPE:
				return isSetType();
			case BEViewsConfigurationPackage.ACTION_DEFINITION__COMMAND_URL:
				return COMMAND_URL_EDEFAULT == null ? commandURL != null : !COMMAND_URL_EDEFAULT.equals(commandURL);
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
		result.append(" (text: ");
		result.append(text);
		result.append(", type: ");
		if (typeESet) result.append(type); else result.append("<unset>");
		result.append(", commandURL: ");
		result.append(commandURL);
		result.append(')');
		return result.toString();
	}

} //ActionDefinitionImpl
