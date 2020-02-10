/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.IndexPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Folder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.FolderImpl#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FolderImpl extends DesignerElementImpl implements Folder {
	/**
	 * The cached value of the '{@link #getEntries() <em>Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<DesignerElement> entries;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FolderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.FOLDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DesignerElement> getEntries() {
		if (entries == null) {
			entries = new EObjectContainmentEList<DesignerElement>(DesignerElement.class, this, IndexPackage.FOLDER__ENTRIES);
		}
		return entries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.FOLDER__ENTRIES:
				return ((InternalEList<?>)getEntries()).basicRemove(otherEnd, msgs);
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
			case IndexPackage.FOLDER__ENTRIES:
				return getEntries();
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
			case IndexPackage.FOLDER__ENTRIES:
				getEntries().clear();
				getEntries().addAll((Collection<? extends DesignerElement>)newValue);
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
			case IndexPackage.FOLDER__ENTRIES:
				getEntries().clear();
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
			case IndexPackage.FOLDER__ENTRIES:
				return entries != null && !entries.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
     * @generated NOT
     * @param visitor
     */
    private void doVisitChildren(IStructuredElementVisitor visitor) {
    	EList<DesignerElement> entries = getEntries();
    	for (int i = 0; i < entries.size(); i++) {
			DesignerElement designerElement = entries.get(i);
			designerElement.accept(visitor);
		}
    }

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.model.DesignerElement#accept(com.tibco.cep.studio.core.index.IStructuredElementVisitor)
	 * @generated NOT
	 */
	public void accept(IStructuredElementVisitor visitor) {
        visitor.preVisit(this);
        if (visitor.visit(this)) {
            doVisitChildren(visitor);
        }
        visitor.postVisit(this);
	}

} //FolderImpl
