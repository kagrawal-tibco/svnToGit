/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.Component;
import com.tibco.cep.designtime.core.model.beviewsconfig.ComponentGalleryFolder;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component Gallery Folder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentGalleryFolderImpl#getSubFolder <em>Sub Folder</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.ComponentGalleryFolderImpl#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentGalleryFolderImpl extends BEViewsElementImpl implements ComponentGalleryFolder {
	/**
	 * The cached value of the '{@link #getSubFolder() <em>Sub Folder</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubFolder()
	 * @generated
	 * @ordered
	 */
	protected EList<ComponentGalleryFolder> subFolder;

	/**
	 * The cached value of the '{@link #getComponent() <em>Component</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponent()
	 * @generated
	 * @ordered
	 */
	protected EList<Component> component;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComponentGalleryFolderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getComponentGalleryFolder();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ComponentGalleryFolder> getSubFolder() {
		if (subFolder == null) {
			subFolder = new EObjectContainmentEList<ComponentGalleryFolder>(ComponentGalleryFolder.class, this, BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER__SUB_FOLDER);
		}
		return subFolder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Component> getComponent() {
		if (component == null) {
			component = new EObjectResolvingEList<Component>(Component.class, this, BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER__COMPONENT);
		}
		return component;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER__SUB_FOLDER:
				return ((InternalEList<?>)getSubFolder()).basicRemove(otherEnd, msgs);
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
			case BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER__SUB_FOLDER:
				return getSubFolder();
			case BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER__COMPONENT:
				return getComponent();
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
			case BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER__SUB_FOLDER:
				getSubFolder().clear();
				getSubFolder().addAll((Collection<? extends ComponentGalleryFolder>)newValue);
				return;
			case BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER__COMPONENT:
				getComponent().clear();
				getComponent().addAll((Collection<? extends Component>)newValue);
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
			case BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER__SUB_FOLDER:
				getSubFolder().clear();
				return;
			case BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER__COMPONENT:
				getComponent().clear();
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
			case BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER__SUB_FOLDER:
				return subFolder != null && !subFolder.isEmpty();
			case BEViewsConfigurationPackage.COMPONENT_GALLERY_FOLDER__COMPONENT:
				return component != null && !component.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ComponentGalleryFolderImpl
