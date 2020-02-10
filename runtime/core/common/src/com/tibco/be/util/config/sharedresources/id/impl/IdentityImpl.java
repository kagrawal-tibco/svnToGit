/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.sharedresources.id.impl;

import com.tibco.be.util.config.sharedresources.id.Designer;
import com.tibco.be.util.config.sharedresources.id.IdPackage;
import com.tibco.be.util.config.sharedresources.id.Identity;
import com.tibco.be.util.config.sharedresources.id.ObjectType;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Identity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl#getDesigner <em>Designer</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl#getObjectType <em>Object Type</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl#getUsername <em>Username</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl#getPassword <em>Password</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl#getFileType <em>File Type</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl#getUrl <em>Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl#getPassPhrase <em>Pass Phrase</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl#getCertUrl <em>Cert Url</em>}</li>
 *   <li>{@link com.tibco.be.util.config.sharedresources.id.impl.IdentityImpl#getPrivateKeyUrl <em>Private Key Url</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class IdentityImpl extends EObjectImpl implements Identity {
	/**
	 * The cached value of the '{@link #getDesigner() <em>Designer</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDesigner()
	 * @generated
	 * @ordered
	 */
	protected Designer designer;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getObjectType() <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectType()
	 * @generated
	 * @ordered
	 */
	protected static final Object OBJECT_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getObjectType() <em>Object Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObjectType()
	 * @generated
	 * @ordered
	 */
	protected Object objectType = OBJECT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getUsername() <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsername()
	 * @generated
	 * @ordered
	 */
	protected static final String USERNAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUsername() <em>Username</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUsername()
	 * @generated
	 * @ordered
	 */
	protected String username = USERNAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected static final String PASSWORD_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassword() <em>Password</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassword()
	 * @generated
	 * @ordered
	 */
	protected String password = PASSWORD_EDEFAULT;

	/**
	 * The default value of the '{@link #getFileType() <em>File Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileType()
	 * @generated
	 * @ordered
	 */
	protected static final Object FILE_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFileType() <em>File Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileType()
	 * @generated
	 * @ordered
	 */
	protected Object fileType = FILE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUrl() <em>Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUrl()
	 * @generated
	 * @ordered
	 */
	protected String url = URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getPassPhrase() <em>Pass Phrase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassPhrase()
	 * @generated
	 * @ordered
	 */
	protected static final String PASS_PHRASE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPassPhrase() <em>Pass Phrase</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPassPhrase()
	 * @generated
	 * @ordered
	 */
	protected String passPhrase = PASS_PHRASE_EDEFAULT;

	/**
	 * The default value of the '{@link #getCertUrl() <em>Cert Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCertUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String CERT_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCertUrl() <em>Cert Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCertUrl()
	 * @generated
	 * @ordered
	 */
	protected String certUrl = CERT_URL_EDEFAULT;

	/**
	 * The default value of the '{@link #getPrivateKeyUrl() <em>Private Key Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrivateKeyUrl()
	 * @generated
	 * @ordered
	 */
	protected static final String PRIVATE_KEY_URL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPrivateKeyUrl() <em>Private Key Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrivateKeyUrl()
	 * @generated
	 * @ordered
	 */
	protected String privateKeyUrl = PRIVATE_KEY_URL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IdentityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IdPackage.Literals.IDENTITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Designer getDesigner() {
		return designer;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDesigner(Designer newDesigner, NotificationChain msgs) {
		Designer oldDesigner = designer;
		designer = newDesigner;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__DESIGNER, oldDesigner, newDesigner);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDesigner(Designer newDesigner) {
		if (newDesigner != designer) {
			NotificationChain msgs = null;
			if (designer != null)
				msgs = ((InternalEObject)designer).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - IdPackage.IDENTITY__DESIGNER, null, msgs);
			if (newDesigner != null)
				msgs = ((InternalEObject)newDesigner).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - IdPackage.IDENTITY__DESIGNER, null, msgs);
			msgs = basicSetDesigner(newDesigner, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__DESIGNER, newDesigner, newDesigner));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getObjectType() {
		return objectType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObjectType(Object newObjectType) {
		Object oldObjectType = objectType;
		objectType = newObjectType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__OBJECT_TYPE, oldObjectType, objectType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUsername(String newUsername) {
		String oldUsername = username;
		username = newUsername;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__USERNAME, oldUsername, username));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPassword(String newPassword) {
		String oldPassword = password;
		password = newPassword;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__PASSWORD, oldPassword, password));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Object getFileType() {
		return fileType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFileType(Object newFileType) {
		Object oldFileType = fileType;
		fileType = newFileType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__FILE_TYPE, oldFileType, fileType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUrl(String newUrl) {
		String oldUrl = url;
		url = newUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__URL, oldUrl, url));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPassPhrase() {
		return passPhrase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPassPhrase(String newPassPhrase) {
		String oldPassPhrase = passPhrase;
		passPhrase = newPassPhrase;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__PASS_PHRASE, oldPassPhrase, passPhrase));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCertUrl() {
		return certUrl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCertUrl(String newCertUrl) {
		String oldCertUrl = certUrl;
		certUrl = newCertUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__CERT_URL, oldCertUrl, certUrl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPrivateKeyUrl() {
		return privateKeyUrl;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrivateKeyUrl(String newPrivateKeyUrl) {
		String oldPrivateKeyUrl = privateKeyUrl;
		privateKeyUrl = newPrivateKeyUrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IdPackage.IDENTITY__PRIVATE_KEY_URL, oldPrivateKeyUrl, privateKeyUrl));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IdPackage.IDENTITY__DESIGNER:
				return basicSetDesigner(null, msgs);
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
			case IdPackage.IDENTITY__DESIGNER:
				return getDesigner();
			case IdPackage.IDENTITY__NAME:
				return getName();
			case IdPackage.IDENTITY__DESCRIPTION:
				return getDescription();
			case IdPackage.IDENTITY__OBJECT_TYPE:
				return getObjectType();
			case IdPackage.IDENTITY__USERNAME:
				return getUsername();
			case IdPackage.IDENTITY__PASSWORD:
				return getPassword();
			case IdPackage.IDENTITY__FILE_TYPE:
				return getFileType();
			case IdPackage.IDENTITY__URL:
				return getUrl();
			case IdPackage.IDENTITY__PASS_PHRASE:
				return getPassPhrase();
			case IdPackage.IDENTITY__CERT_URL:
				return getCertUrl();
			case IdPackage.IDENTITY__PRIVATE_KEY_URL:
				return getPrivateKeyUrl();
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
			case IdPackage.IDENTITY__DESIGNER:
				setDesigner((Designer)newValue);
				return;
			case IdPackage.IDENTITY__NAME:
				setName((String)newValue);
				return;
			case IdPackage.IDENTITY__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case IdPackage.IDENTITY__OBJECT_TYPE:
				setObjectType(newValue);
				return;
			case IdPackage.IDENTITY__USERNAME:
				setUsername((String)newValue);
				return;
			case IdPackage.IDENTITY__PASSWORD:
				setPassword((String)newValue);
				return;
			case IdPackage.IDENTITY__FILE_TYPE:
				setFileType(newValue);
				return;
			case IdPackage.IDENTITY__URL:
				setUrl((String)newValue);
				return;
			case IdPackage.IDENTITY__PASS_PHRASE:
				setPassPhrase((String)newValue);
				return;
			case IdPackage.IDENTITY__CERT_URL:
				setCertUrl((String)newValue);
				return;
			case IdPackage.IDENTITY__PRIVATE_KEY_URL:
				setPrivateKeyUrl((String)newValue);
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
			case IdPackage.IDENTITY__DESIGNER:
				setDesigner((Designer)null);
				return;
			case IdPackage.IDENTITY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IdPackage.IDENTITY__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case IdPackage.IDENTITY__OBJECT_TYPE:
				setObjectType(OBJECT_TYPE_EDEFAULT);
				return;
			case IdPackage.IDENTITY__USERNAME:
				setUsername(USERNAME_EDEFAULT);
				return;
			case IdPackage.IDENTITY__PASSWORD:
				setPassword(PASSWORD_EDEFAULT);
				return;
			case IdPackage.IDENTITY__FILE_TYPE:
				setFileType(FILE_TYPE_EDEFAULT);
				return;
			case IdPackage.IDENTITY__URL:
				setUrl(URL_EDEFAULT);
				return;
			case IdPackage.IDENTITY__PASS_PHRASE:
				setPassPhrase(PASS_PHRASE_EDEFAULT);
				return;
			case IdPackage.IDENTITY__CERT_URL:
				setCertUrl(CERT_URL_EDEFAULT);
				return;
			case IdPackage.IDENTITY__PRIVATE_KEY_URL:
				setPrivateKeyUrl(PRIVATE_KEY_URL_EDEFAULT);
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
			case IdPackage.IDENTITY__DESIGNER:
				return designer != null;
			case IdPackage.IDENTITY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case IdPackage.IDENTITY__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case IdPackage.IDENTITY__OBJECT_TYPE:
				return OBJECT_TYPE_EDEFAULT == null ? objectType != null : !OBJECT_TYPE_EDEFAULT.equals(objectType);
			case IdPackage.IDENTITY__USERNAME:
				return USERNAME_EDEFAULT == null ? username != null : !USERNAME_EDEFAULT.equals(username);
			case IdPackage.IDENTITY__PASSWORD:
				return PASSWORD_EDEFAULT == null ? password != null : !PASSWORD_EDEFAULT.equals(password);
			case IdPackage.IDENTITY__FILE_TYPE:
				return FILE_TYPE_EDEFAULT == null ? fileType != null : !FILE_TYPE_EDEFAULT.equals(fileType);
			case IdPackage.IDENTITY__URL:
				return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
			case IdPackage.IDENTITY__PASS_PHRASE:
				return PASS_PHRASE_EDEFAULT == null ? passPhrase != null : !PASS_PHRASE_EDEFAULT.equals(passPhrase);
			case IdPackage.IDENTITY__CERT_URL:
				return CERT_URL_EDEFAULT == null ? certUrl != null : !CERT_URL_EDEFAULT.equals(certUrl);
			case IdPackage.IDENTITY__PRIVATE_KEY_URL:
				return PRIVATE_KEY_URL_EDEFAULT == null ? privateKeyUrl != null : !PRIVATE_KEY_URL_EDEFAULT.equals(privateKeyUrl);
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
		result.append(" (name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", objectType: ");
		result.append(objectType);
		result.append(", username: ");
		result.append(username);
		result.append(", password: ");
		result.append(password);
		result.append(", fileType: ");
		result.append(fileType);
		result.append(", url: ");
		result.append(url);
		result.append(", passPhrase: ");
		result.append(passPhrase);
		result.append(", certUrl: ");
		result.append(certUrl);
		result.append(", privateKeyUrl: ");
		result.append(privateKeyUrl);
		result.append(')');
		return result.toString();
	}

} //IdentityImpl
