/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.validation.ModelError;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entity</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.impl.EntityImpl#getNamespace <em>Namespace</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.impl.EntityImpl#getFolder <em>Folder</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.impl.EntityImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.impl.EntityImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.impl.EntityImpl#getLastModified <em>Last Modified</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.impl.EntityImpl#getGUID <em>GUID</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.impl.EntityImpl#getOntology <em>Ontology</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.impl.EntityImpl#getExtendedProperties <em>Extended Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.impl.EntityImpl#getHiddenProperties <em>Hidden Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.impl.EntityImpl#getTransientProperties <em>Transient Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.impl.EntityImpl#getOwnerProjectName <em>Owner Project Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class EntityImpl extends EObjectImpl implements Entity {
	/**
	 * The default value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected static final String NAMESPACE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getNamespace() <em>Namespace</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNamespace()
	 * @generated
	 * @ordered
	 */
	protected String namespace = NAMESPACE_EDEFAULT;

	/**
	 * The default value of the '{@link #getFolder() <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolder()
	 * @generated
	 * @ordered
	 */
	protected static final String FOLDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFolder() <em>Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFolder()
	 * @generated
	 * @ordered
	 */
	protected String folder = FOLDER_EDEFAULT;

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
	 * The default value of the '{@link #getLastModified() <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModified()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_MODIFIED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastModified() <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModified()
	 * @generated
	 * @ordered
	 */
	protected String lastModified = LAST_MODIFIED_EDEFAULT;

	/**
	 * The default value of the '{@link #getGUID() <em>GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGUID()
	 * @generated
	 * @ordered
	 */
	protected static final String GUID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGUID() <em>GUID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGUID()
	 * @generated
	 * @ordered
	 */
	protected String guid = GUID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getOntology() <em>Ontology</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOntology()
	 * @generated
	 * @ordered
	 */
	protected EObject ontology;

	/**
	 * The cached value of the '{@link #getExtendedProperties() <em>Extended Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtendedProperties()
	 * @generated
	 * @ordered
	 */
	protected PropertyMap extendedProperties;

	/**
	 * The cached value of the '{@link #getHiddenProperties() <em>Hidden Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHiddenProperties()
	 * @generated
	 * @ordered
	 */
	protected PropertyMap hiddenProperties;

	/**
	 * The cached value of the '{@link #getTransientProperties() <em>Transient Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransientProperties()
	 * @generated
	 * @ordered
	 */
	protected PropertyMap transientProperties;

	/**
	 * The default value of the '{@link #getOwnerProjectName() <em>Owner Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerProjectName()
	 * @generated
	 * @ordered
	 */
	protected static final String OWNER_PROJECT_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOwnerProjectName() <em>Owner Project Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnerProjectName()
	 * @generated
	 * @ordered
	 */
	protected String ownerProjectName = OWNER_PROJECT_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected EntityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.ENTITY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNamespace() {
		return namespace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNamespace(String newNamespace) {
		String oldNamespace = namespace;
		namespace = newNamespace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__NAMESPACE, oldNamespace, namespace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFolder(String newFolder) {
		String oldFolder = folder;
		folder = newFolder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__FOLDER, oldFolder, folder));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastModified() {
		return lastModified;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastModified(String newLastModified) {
		String oldLastModified = lastModified;
		lastModified = newLastModified;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__LAST_MODIFIED, oldLastModified, lastModified));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGUID() {
		return guid;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGUID(String newGUID) {
		String oldGUID = guid;
		guid = newGUID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__GUID, oldGUID, guid));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject getOntology() {
		if (ontology != null && ontology.eIsProxy()) {
			InternalEObject oldOntology = (InternalEObject)ontology;
			ontology = eResolveProxy(oldOntology);
			if (ontology != oldOntology) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ModelPackage.ENTITY__ONTOLOGY, oldOntology, ontology));
			}
		}
		return ontology;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EObject basicGetOntology() {
		return ontology;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOntology(EObject newOntology) {
		EObject oldOntology = ontology;
		ontology = newOntology;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__ONTOLOGY, oldOntology, ontology));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyMap getExtendedProperties() {
		return extendedProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExtendedProperties(PropertyMap newExtendedProperties, NotificationChain msgs) {
		PropertyMap oldExtendedProperties = extendedProperties;
		extendedProperties = newExtendedProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__EXTENDED_PROPERTIES, oldExtendedProperties, newExtendedProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtendedProperties(PropertyMap newExtendedProperties) {
		if (newExtendedProperties != extendedProperties) {
			NotificationChain msgs = null;
			if (extendedProperties != null)
				msgs = ((InternalEObject)extendedProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ENTITY__EXTENDED_PROPERTIES, null, msgs);
			if (newExtendedProperties != null)
				msgs = ((InternalEObject)newExtendedProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ENTITY__EXTENDED_PROPERTIES, null, msgs);
			msgs = basicSetExtendedProperties(newExtendedProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__EXTENDED_PROPERTIES, newExtendedProperties, newExtendedProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyMap getHiddenProperties() {
		return hiddenProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetHiddenProperties(PropertyMap newHiddenProperties, NotificationChain msgs) {
		PropertyMap oldHiddenProperties = hiddenProperties;
		hiddenProperties = newHiddenProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__HIDDEN_PROPERTIES, oldHiddenProperties, newHiddenProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHiddenProperties(PropertyMap newHiddenProperties) {
		if (newHiddenProperties != hiddenProperties) {
			NotificationChain msgs = null;
			if (hiddenProperties != null)
				msgs = ((InternalEObject)hiddenProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ENTITY__HIDDEN_PROPERTIES, null, msgs);
			if (newHiddenProperties != null)
				msgs = ((InternalEObject)newHiddenProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ENTITY__HIDDEN_PROPERTIES, null, msgs);
			msgs = basicSetHiddenProperties(newHiddenProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__HIDDEN_PROPERTIES, newHiddenProperties, newHiddenProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyMap getTransientProperties() {
		return transientProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTransientProperties(PropertyMap newTransientProperties, NotificationChain msgs) {
		PropertyMap oldTransientProperties = transientProperties;
		transientProperties = newTransientProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__TRANSIENT_PROPERTIES, oldTransientProperties, newTransientProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransientProperties(PropertyMap newTransientProperties) {
		if (newTransientProperties != transientProperties) {
			NotificationChain msgs = null;
			if (transientProperties != null)
				msgs = ((InternalEObject)transientProperties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ENTITY__TRANSIENT_PROPERTIES, null, msgs);
			if (newTransientProperties != null)
				msgs = ((InternalEObject)newTransientProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.ENTITY__TRANSIENT_PROPERTIES, null, msgs);
			msgs = basicSetTransientProperties(newTransientProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__TRANSIENT_PROPERTIES, newTransientProperties, newTransientProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOwnerProjectName() {
		return ownerProjectName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOwnerProjectName(String newOwnerProjectName) {
		String oldOwnerProjectName = ownerProjectName;
		ownerProjectName = newOwnerProjectName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.ENTITY__OWNER_PROJECT_NAME, oldOwnerProjectName, ownerProjectName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFullPath() {
		return getFolder() + getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ModelError> getModelErrors() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isValid() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.ENTITY__EXTENDED_PROPERTIES:
				return basicSetExtendedProperties(null, msgs);
			case ModelPackage.ENTITY__HIDDEN_PROPERTIES:
				return basicSetHiddenProperties(null, msgs);
			case ModelPackage.ENTITY__TRANSIENT_PROPERTIES:
				return basicSetTransientProperties(null, msgs);
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
			case ModelPackage.ENTITY__NAMESPACE:
				return getNamespace();
			case ModelPackage.ENTITY__FOLDER:
				return getFolder();
			case ModelPackage.ENTITY__NAME:
				return getName();
			case ModelPackage.ENTITY__DESCRIPTION:
				return getDescription();
			case ModelPackage.ENTITY__LAST_MODIFIED:
				return getLastModified();
			case ModelPackage.ENTITY__GUID:
				return getGUID();
			case ModelPackage.ENTITY__ONTOLOGY:
				if (resolve) return getOntology();
				return basicGetOntology();
			case ModelPackage.ENTITY__EXTENDED_PROPERTIES:
				return getExtendedProperties();
			case ModelPackage.ENTITY__HIDDEN_PROPERTIES:
				return getHiddenProperties();
			case ModelPackage.ENTITY__TRANSIENT_PROPERTIES:
				return getTransientProperties();
			case ModelPackage.ENTITY__OWNER_PROJECT_NAME:
				return getOwnerProjectName();
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
			case ModelPackage.ENTITY__NAMESPACE:
				setNamespace((String)newValue);
				return;
			case ModelPackage.ENTITY__FOLDER:
				setFolder((String)newValue);
				return;
			case ModelPackage.ENTITY__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.ENTITY__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ModelPackage.ENTITY__LAST_MODIFIED:
				setLastModified((String)newValue);
				return;
			case ModelPackage.ENTITY__GUID:
				setGUID((String)newValue);
				return;
			case ModelPackage.ENTITY__ONTOLOGY:
				setOntology((EObject)newValue);
				return;
			case ModelPackage.ENTITY__EXTENDED_PROPERTIES:
				setExtendedProperties((PropertyMap)newValue);
				return;
			case ModelPackage.ENTITY__HIDDEN_PROPERTIES:
				setHiddenProperties((PropertyMap)newValue);
				return;
			case ModelPackage.ENTITY__TRANSIENT_PROPERTIES:
				setTransientProperties((PropertyMap)newValue);
				return;
			case ModelPackage.ENTITY__OWNER_PROJECT_NAME:
				setOwnerProjectName((String)newValue);
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
			case ModelPackage.ENTITY__NAMESPACE:
				setNamespace(NAMESPACE_EDEFAULT);
				return;
			case ModelPackage.ENTITY__FOLDER:
				setFolder(FOLDER_EDEFAULT);
				return;
			case ModelPackage.ENTITY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.ENTITY__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ModelPackage.ENTITY__LAST_MODIFIED:
				setLastModified(LAST_MODIFIED_EDEFAULT);
				return;
			case ModelPackage.ENTITY__GUID:
				setGUID(GUID_EDEFAULT);
				return;
			case ModelPackage.ENTITY__ONTOLOGY:
				setOntology((EObject)null);
				return;
			case ModelPackage.ENTITY__EXTENDED_PROPERTIES:
				setExtendedProperties((PropertyMap)null);
				return;
			case ModelPackage.ENTITY__HIDDEN_PROPERTIES:
				setHiddenProperties((PropertyMap)null);
				return;
			case ModelPackage.ENTITY__TRANSIENT_PROPERTIES:
				setTransientProperties((PropertyMap)null);
				return;
			case ModelPackage.ENTITY__OWNER_PROJECT_NAME:
				setOwnerProjectName(OWNER_PROJECT_NAME_EDEFAULT);
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
			case ModelPackage.ENTITY__NAMESPACE:
				return NAMESPACE_EDEFAULT == null ? namespace != null : !NAMESPACE_EDEFAULT.equals(namespace);
			case ModelPackage.ENTITY__FOLDER:
				return FOLDER_EDEFAULT == null ? folder != null : !FOLDER_EDEFAULT.equals(folder);
			case ModelPackage.ENTITY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.ENTITY__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case ModelPackage.ENTITY__LAST_MODIFIED:
				return LAST_MODIFIED_EDEFAULT == null ? lastModified != null : !LAST_MODIFIED_EDEFAULT.equals(lastModified);
			case ModelPackage.ENTITY__GUID:
				return GUID_EDEFAULT == null ? guid != null : !GUID_EDEFAULT.equals(guid);
			case ModelPackage.ENTITY__ONTOLOGY:
				return ontology != null;
			case ModelPackage.ENTITY__EXTENDED_PROPERTIES:
				return extendedProperties != null;
			case ModelPackage.ENTITY__HIDDEN_PROPERTIES:
				return hiddenProperties != null;
			case ModelPackage.ENTITY__TRANSIENT_PROPERTIES:
				return transientProperties != null;
			case ModelPackage.ENTITY__OWNER_PROJECT_NAME:
				return OWNER_PROJECT_NAME_EDEFAULT == null ? ownerProjectName != null : !OWNER_PROJECT_NAME_EDEFAULT.equals(ownerProjectName);
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
		result.append(" (namespace: ");
		result.append(namespace);
		result.append(", folder: ");
		result.append(folder);
		result.append(", name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", lastModified: ");
		result.append(lastModified);
		result.append(", GUID: ");
		result.append(guid);
		result.append(", ownerProjectName: ");
		result.append(ownerProjectName);
		result.append(')');
		return result.toString();
	}

} //EntityImpl
