/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Property;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Meta Data</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.MetaDataImpl#getProp <em>Prop</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetaDataImpl extends EObjectImpl implements MetaData {
	/**
	 * The cached value of the '{@link #getProp() <em>Prop</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProp()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> prop;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetaDataImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DtmodelPackage.Literals.META_DATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Property> getProp() {
		if (prop == null) {
			prop = new EObjectContainmentEList<Property>(Property.class, this, DtmodelPackage.META_DATA__PROP);
		}
		return prop;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DtmodelPackage.META_DATA__PROP:
				return ((InternalEList<?>)getProp()).basicRemove(otherEnd, msgs);
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
			case DtmodelPackage.META_DATA__PROP:
				return getProp();
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
			case DtmodelPackage.META_DATA__PROP:
				getProp().clear();
				getProp().addAll((Collection<? extends Property>)newValue);
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
			case DtmodelPackage.META_DATA__PROP:
				getProp().clear();
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
			case DtmodelPackage.META_DATA__PROP:
				return prop != null && !prop.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.dtmodel.MetaData#search(java.lang.String)
	 */
	public Property search(String name) {
		if (name == null) {
			return null;
		}
		if (this.prop == null) {
			return null;
		}
		final List<Property> properties = this.prop;
		for (Property p : properties) {
			String pName = p.getName();
			if (pName == null) {
				pName = "";
			}
			if (pName.intern() == name.intern()) {
				return p;
			}
		}
		return null;
	}
	
		
	/* (non-Javadoc)
	 * @see com.tibco.cep.decision.table.model.dtmodel.MetaData#searchAll(java.lang.String)
	 */
	public List<Property> searchAll(String name) {
		List<Property> matchingProperties = new ArrayList<Property>();
		if (name == null) {
			return matchingProperties;
		}
		if (this.prop == null) {
			return null;
		}
		final List<Property> properties = this.prop;
		for (Property p : properties) {
			String pName = p.getName();
			if (pName == null) {
				pName = "";
			}
			if (pName.intern() == name.intern()) {
				matchingProperties.add(p);
			}
		}
		return matchingProperties;
	}

	public boolean remove(final Property property) {
		if (property == null) {
			return false;
		}
		return this.prop.remove(property);
	}
} //MetaDataImpl
