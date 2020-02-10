/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Web Application Descriptor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.WebApplicationDescriptorImpl#getContextURI <em>Context URI</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.WebApplicationDescriptorImpl#getWebAppSourcePath <em>Web App Source Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class WebApplicationDescriptorImpl extends EObjectImpl implements WebApplicationDescriptor {
	/**
	 * The default value of the '{@link #getContextURI() <em>Context URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextURI()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTEXT_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContextURI() <em>Context URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContextURI()
	 * @generated
	 * @ordered
	 */
	protected String contextURI = CONTEXT_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #getWebAppSourcePath() <em>Web App Source Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWebAppSourcePath()
	 * @generated
	 * @ordered
	 */
	protected static final String WEB_APP_SOURCE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWebAppSourcePath() <em>Web App Source Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWebAppSourcePath()
	 * @generated
	 * @ordered
	 */
	protected String webAppSourcePath = WEB_APP_SOURCE_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected WebApplicationDescriptorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.WEB_APPLICATION_DESCRIPTOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getContextURI() {
		return contextURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContextURI(String newContextURI) {
		String oldContextURI = contextURI;
		contextURI = newContextURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.WEB_APPLICATION_DESCRIPTOR__CONTEXT_URI, oldContextURI, contextURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWebAppSourcePath() {
		return webAppSourcePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWebAppSourcePath(String newWebAppSourcePath) {
		String oldWebAppSourcePath = webAppSourcePath;
		webAppSourcePath = newWebAppSourcePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.WEB_APPLICATION_DESCRIPTOR__WEB_APP_SOURCE_PATH, oldWebAppSourcePath, webAppSourcePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.WEB_APPLICATION_DESCRIPTOR__CONTEXT_URI:
				return getContextURI();
			case ChannelPackage.WEB_APPLICATION_DESCRIPTOR__WEB_APP_SOURCE_PATH:
				return getWebAppSourcePath();
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
			case ChannelPackage.WEB_APPLICATION_DESCRIPTOR__CONTEXT_URI:
				setContextURI((String)newValue);
				return;
			case ChannelPackage.WEB_APPLICATION_DESCRIPTOR__WEB_APP_SOURCE_PATH:
				setWebAppSourcePath((String)newValue);
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
			case ChannelPackage.WEB_APPLICATION_DESCRIPTOR__CONTEXT_URI:
				setContextURI(CONTEXT_URI_EDEFAULT);
				return;
			case ChannelPackage.WEB_APPLICATION_DESCRIPTOR__WEB_APP_SOURCE_PATH:
				setWebAppSourcePath(WEB_APP_SOURCE_PATH_EDEFAULT);
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
			case ChannelPackage.WEB_APPLICATION_DESCRIPTOR__CONTEXT_URI:
				return CONTEXT_URI_EDEFAULT == null ? contextURI != null : !CONTEXT_URI_EDEFAULT.equals(contextURI);
			case ChannelPackage.WEB_APPLICATION_DESCRIPTOR__WEB_APP_SOURCE_PATH:
				return WEB_APP_SOURCE_PATH_EDEFAULT == null ? webAppSourcePath != null : !WEB_APP_SOURCE_PATH_EDEFAULT.equals(webAppSourcePath);
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
		result.append(" (contextURI: ");
		result.append(contextURI);
		result.append(", webAppSourcePath: ");
		result.append(webAppSourcePath);
		result.append(')');
		return result.toString();
	}

} //WebApplicationDescriptorImpl
