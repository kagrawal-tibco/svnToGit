/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.archive.ArchiveResource;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.IndexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Archive Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.ArchiveElementImpl#getArchive <em>Archive</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ArchiveElementImpl extends TypeElementImpl implements ArchiveElement {
	/**
	 * The cached value of the '{@link #getArchive() <em>Archive</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchive()
	 * @generated
	 * @ordered
	 */
	protected ArchiveResource archive;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArchiveElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.ARCHIVE_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchiveResource getArchive() {
		if (archive != null && archive.eIsProxy()) {
			InternalEObject oldArchive = (InternalEObject)archive;
			archive = (ArchiveResource)eResolveProxy(oldArchive);
			if (archive != oldArchive) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IndexPackage.ARCHIVE_ELEMENT__ARCHIVE, oldArchive, archive));
			}
		}
		return archive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchiveResource basicGetArchive() {
		return archive;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArchive(ArchiveResource newArchive) {
		ArchiveResource oldArchive = archive;
		archive = newArchive;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.ARCHIVE_ELEMENT__ARCHIVE, oldArchive, archive));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case IndexPackage.ARCHIVE_ELEMENT__ARCHIVE:
				if (resolve) return getArchive();
				return basicGetArchive();
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
			case IndexPackage.ARCHIVE_ELEMENT__ARCHIVE:
				setArchive((ArchiveResource)newValue);
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
			case IndexPackage.ARCHIVE_ELEMENT__ARCHIVE:
				setArchive((ArchiveResource)null);
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
			case IndexPackage.ARCHIVE_ELEMENT__ARCHIVE:
				return archive != null;
		}
		return super.eIsSet(featureID);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl#accept(com.tibco.cep.studio.core.index.model.IStructuredElementVisitor)
	 * @generated NOT
	 */
	public void accept(IStructuredElementVisitor visitor) {
        visitor.preVisit(this);
        if (visitor.visit(this)) {
        	// do nothing, no children to visit
        }
        visitor.postVisit(this);
	}

} //ArchiveElementImpl
