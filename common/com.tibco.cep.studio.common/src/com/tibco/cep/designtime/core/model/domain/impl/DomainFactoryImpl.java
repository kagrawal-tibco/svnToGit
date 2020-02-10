/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.domain.impl;

import com.tibco.cep.designtime.core.model.domain.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainFactory;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.domain.DomainPackage;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.domain.Single;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DomainFactoryImpl extends EFactoryImpl implements DomainFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DomainFactory init() {
		try {
			DomainFactory theDomainFactory = (DomainFactory)EPackage.Registry.INSTANCE.getEFactory(DomainPackage.eNS_URI);
			if (theDomainFactory != null) {
				return theDomainFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DomainFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case DomainPackage.DOMAIN: return createDomain();
			case DomainPackage.DOMAIN_ENTRY: return createDomainEntry();
			case DomainPackage.DOMAIN_INSTANCE: return createDomainInstance();
			case DomainPackage.RANGE: return createRange();
			case DomainPackage.SINGLE: return createSingle();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain createDomain() {
		DomainImpl domain = new DomainImpl();
		return domain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainEntry createDomainEntry() {
		DomainEntryImpl domainEntry = new DomainEntryImpl();
		return domainEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainInstance createDomainInstance() {
		DomainInstanceImpl domainInstance = new DomainInstanceImpl();
		return domainInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Range createRange() {
		RangeImpl range = new RangeImpl();
		return range;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Single createSingle() {
		SingleImpl single = new SingleImpl();
		return single;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainPackage getDomainPackage() {
		return (DomainPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DomainPackage getPackage() {
		return DomainPackage.eINSTANCE;
	}

} //DomainFactoryImpl
