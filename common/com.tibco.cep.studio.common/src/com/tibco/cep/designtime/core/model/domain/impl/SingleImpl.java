/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.domain.impl;

import org.eclipse.emf.ecore.EClass;

import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainPackage;
import com.tibco.cep.designtime.core.model.domain.Single;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Single</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class SingleImpl extends DomainEntryImpl implements Single {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SingleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainPackage.Literals.SINGLE;
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainEntryImpl#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (!super.equals(obj)) {
			return false;
		}
		DomainEntry other = (DomainEntry)obj;
		
		Object thisValue = this.value;
		Object otherValue = other.getValue();
		
		if (!thisValue.equals(otherValue)) {
			return false;
		}
		return true;
	}
	
	

} //SingleImpl
