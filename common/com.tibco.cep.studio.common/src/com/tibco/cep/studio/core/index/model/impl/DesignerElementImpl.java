/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.IndexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Designer Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl#getElementType <em>Element Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl#isLazilyCreated <em>Lazily Created</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl#getLastModified <em>Last Modified</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl#getCreationDate <em>Creation Date</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class DesignerElementImpl extends EObjectImpl implements DesignerElement {
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
	 * The default value of the '{@link #getElementType() <em>Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected static final ELEMENT_TYPES ELEMENT_TYPE_EDEFAULT = ELEMENT_TYPES.UNKNOWN;

	/**
	 * The cached value of the '{@link #getElementType() <em>Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected ELEMENT_TYPES elementType = ELEMENT_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isLazilyCreated() <em>Lazily Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLazilyCreated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LAZILY_CREATED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLazilyCreated() <em>Lazily Created</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLazilyCreated()
	 * @generated
	 * @ordered
	 */
	protected boolean lazilyCreated = LAZILY_CREATED_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastModified() <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModified()
	 * @generated
	 * @ordered
	 */
	protected static final Date LAST_MODIFIED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastModified() <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModified()
	 * @generated
	 * @ordered
	 */
	protected Date lastModified = LAST_MODIFIED_EDEFAULT;

	/**
	 * The default value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationDate()
	 * @generated
	 * @ordered
	 */
	protected static final Date CREATION_DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCreationDate() <em>Creation Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCreationDate()
	 * @generated
	 * @ordered
	 */
	protected Date creationDate = CREATION_DATE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DesignerElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.DESIGNER_ELEMENT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.DESIGNER_ELEMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ELEMENT_TYPES getElementType() {
		return elementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setElementType(ELEMENT_TYPES newElementType) {
		ELEMENT_TYPES oldElementType = elementType;
		elementType = newElementType == null ? ELEMENT_TYPE_EDEFAULT : newElementType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.DESIGNER_ELEMENT__ELEMENT_TYPE, oldElementType, elementType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLazilyCreated() {
		return lazilyCreated;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLazilyCreated(boolean newLazilyCreated) {
		boolean oldLazilyCreated = lazilyCreated;
		lazilyCreated = newLazilyCreated;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.DESIGNER_ELEMENT__LAZILY_CREATED, oldLazilyCreated, lazilyCreated));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastModified(Date newLastModified) {
		Date oldLastModified = lastModified;
		lastModified = newLastModified;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.DESIGNER_ELEMENT__LAST_MODIFIED, oldLastModified, lastModified));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCreationDate(Date newCreationDate) {
		Date oldCreationDate = creationDate;
		creationDate = newCreationDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.DESIGNER_ELEMENT__CREATION_DATE, oldCreationDate, creationDate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void accept(IStructuredElementVisitor visitor) {
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
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IndexPackage.DESIGNER_ELEMENT__NAME:
				return getName();
			case IndexPackage.DESIGNER_ELEMENT__ELEMENT_TYPE:
				return getElementType();
			case IndexPackage.DESIGNER_ELEMENT__LAZILY_CREATED:
				return isLazilyCreated();
			case IndexPackage.DESIGNER_ELEMENT__LAST_MODIFIED:
				return getLastModified();
			case IndexPackage.DESIGNER_ELEMENT__CREATION_DATE:
				return getCreationDate();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case IndexPackage.DESIGNER_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case IndexPackage.DESIGNER_ELEMENT__ELEMENT_TYPE:
				setElementType((ELEMENT_TYPES)newValue);
				return;
			case IndexPackage.DESIGNER_ELEMENT__LAZILY_CREATED:
				setLazilyCreated((Boolean)newValue);
				return;
			case IndexPackage.DESIGNER_ELEMENT__LAST_MODIFIED:
				setLastModified((Date)newValue);
				return;
			case IndexPackage.DESIGNER_ELEMENT__CREATION_DATE:
				setCreationDate((Date)newValue);
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
			case IndexPackage.DESIGNER_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case IndexPackage.DESIGNER_ELEMENT__ELEMENT_TYPE:
				setElementType(ELEMENT_TYPE_EDEFAULT);
				return;
			case IndexPackage.DESIGNER_ELEMENT__LAZILY_CREATED:
				setLazilyCreated(LAZILY_CREATED_EDEFAULT);
				return;
			case IndexPackage.DESIGNER_ELEMENT__LAST_MODIFIED:
				setLastModified(LAST_MODIFIED_EDEFAULT);
				return;
			case IndexPackage.DESIGNER_ELEMENT__CREATION_DATE:
				setCreationDate(CREATION_DATE_EDEFAULT);
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
			case IndexPackage.DESIGNER_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case IndexPackage.DESIGNER_ELEMENT__ELEMENT_TYPE:
				return elementType != ELEMENT_TYPE_EDEFAULT;
			case IndexPackage.DESIGNER_ELEMENT__LAZILY_CREATED:
				return lazilyCreated != LAZILY_CREATED_EDEFAULT;
			case IndexPackage.DESIGNER_ELEMENT__LAST_MODIFIED:
				return LAST_MODIFIED_EDEFAULT == null ? lastModified != null : !LAST_MODIFIED_EDEFAULT.equals(lastModified);
			case IndexPackage.DESIGNER_ELEMENT__CREATION_DATE:
				return CREATION_DATE_EDEFAULT == null ? creationDate != null : !CREATION_DATE_EDEFAULT.equals(creationDate);
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
		result.append(", elementType: ");
		result.append(elementType);
		result.append(", lazilyCreated: ");
		result.append(lazilyCreated);
		result.append(", lastModified: ");
		result.append(lastModified);
		result.append(", creationDate: ");
		result.append(creationDate);
		result.append(')');
		return result.toString();
	}

} //DesignerElementImpl
