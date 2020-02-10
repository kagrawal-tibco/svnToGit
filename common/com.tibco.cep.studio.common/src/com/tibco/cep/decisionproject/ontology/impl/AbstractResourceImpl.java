/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.Folder;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.decisionproject.ontology.Rule;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.ontology.RuleSet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abstract Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.AbstractResourceImpl#isModified <em>Modified</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.AbstractResourceImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.AbstractResourceImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.AbstractResourceImpl#getFolder <em>Folder</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class AbstractResourceImpl extends EObjectImpl implements AbstractResource {
	/**
	 * The default value of the '{@link #isModified() <em>Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModified()
	 * @generated
	 * @ordered
	 */
	protected static final boolean MODIFIED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isModified() <em>Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isModified()
	 * @generated
	 * @ordered
	 */
	protected boolean modified = MODIFIED_EDEFAULT;

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
	
	protected String ownerProjectName;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AbstractResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyPackage.Literals.ABSTRACT_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModified(boolean newModified) {
		boolean oldModified = modified;
		modified = newModified;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.ABSTRACT_RESOURCE__MODIFIED, oldModified, modified));
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
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.ABSTRACT_RESOURCE__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.ABSTRACT_RESOURCE__DESCRIPTION, oldDescription, description));
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
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.ABSTRACT_RESOURCE__FOLDER, oldFolder, folder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OntologyPackage.ABSTRACT_RESOURCE__MODIFIED:
				return isModified() ? Boolean.TRUE : Boolean.FALSE;
			case OntologyPackage.ABSTRACT_RESOURCE__NAME:
				return getName();
			case OntologyPackage.ABSTRACT_RESOURCE__DESCRIPTION:
				return getDescription();
			case OntologyPackage.ABSTRACT_RESOURCE__FOLDER:
				return getFolder();
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
			case OntologyPackage.ABSTRACT_RESOURCE__MODIFIED:
				setModified(((Boolean)newValue).booleanValue());
				return;
			case OntologyPackage.ABSTRACT_RESOURCE__NAME:
				setName((String)newValue);
				return;
			case OntologyPackage.ABSTRACT_RESOURCE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case OntologyPackage.ABSTRACT_RESOURCE__FOLDER:
				setFolder((String)newValue);
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
			case OntologyPackage.ABSTRACT_RESOURCE__MODIFIED:
				setModified(MODIFIED_EDEFAULT);
				return;
			case OntologyPackage.ABSTRACT_RESOURCE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case OntologyPackage.ABSTRACT_RESOURCE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case OntologyPackage.ABSTRACT_RESOURCE__FOLDER:
				setFolder(FOLDER_EDEFAULT);
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
			case OntologyPackage.ABSTRACT_RESOURCE__MODIFIED:
				return modified != MODIFIED_EDEFAULT;
			case OntologyPackage.ABSTRACT_RESOURCE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case OntologyPackage.ABSTRACT_RESOURCE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case OntologyPackage.ABSTRACT_RESOURCE__FOLDER:
				return FOLDER_EDEFAULT == null ? folder != null : !FOLDER_EDEFAULT.equals(folder);
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
		result.append(" (modified: ");
		result.append(modified);
		result.append(", name: ");
		result.append(name);
		result.append(", description: ");
		result.append(description);
		result.append(", folder: ");
		result.append(folder);
		result.append(')');
		return result.toString();
	}
	
	protected ResourceType getResourceType(AbstractResource resource){		
		if (resource instanceof Concept) return ResourceType.CONCEPT;
		if (resource instanceof Event) return ResourceType.EVENT;
		if (resource instanceof Folder) return ResourceType.FOLDER;
		if (resource instanceof Property) return ResourceType.PROPERTY;
		if (resource instanceof RuleFunction) return ResourceType.RULE_FUNCTION;
		if (resource instanceof RuleSet) return ResourceType.RULESET;
		if (resource instanceof Rule) return ResourceType.RULE;
		if (resource instanceof Table) return ResourceType.DECISION_TABLE;
		return null;
	}

	/**
	 * @return the ownerProjectName
	 */
	public final String getOwnerProjectName() {
		return ownerProjectName;
	}

	/**
	 * @param ownerProjectName the ownerProjectName to set
	 */
	public final void setOwnerProjectName(String ownerProjectName) {
		this.ownerProjectName = ownerProjectName;
	}
	
	public String getPath() {
		String folder = getFolder();
		StringBuilder sb = new StringBuilder(folder);
		if(!folder.endsWith("/")) sb.append("/");
		sb.append(getName());
		return sb.toString();
	}

} //AbstractResourceImpl
