/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.decision.table.model.domainmodel.Domain;
import com.tibco.cep.decision.table.model.domainmodel.DomainEntry;
import com.tibco.cep.decision.table.model.domainmodel.DomainModelFactory;
import com.tibco.cep.decision.table.model.domainmodel.DomainModelPackage;
import com.tibco.cep.decision.table.model.domainmodel.RangeInfo;
import com.tibco.cep.decision.table.model.domainmodel.SingleValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DomainModelFactoryImpl extends EFactoryImpl implements DomainModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DomainModelFactory init() {
		try {
			DomainModelFactory theDomainModelFactory = (DomainModelFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/decision/table/model/DomainModel.ecore"); 
			if (theDomainModelFactory != null) {
				return theDomainModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DomainModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainModelFactoryImpl() {
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
			case DomainModelPackage.DOMAIN_ENTRY: return createDomainEntry();
			case DomainModelPackage.RANGE_INFO: return createRangeInfo();
			case DomainModelPackage.SINGLE_VALUE: return createSingleValue();
			case DomainModelPackage.DOMAIN: return createDomain();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
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
	public RangeInfo createRangeInfo() {
		RangeInfoImpl rangeInfo = new RangeInfoImpl();
		return rangeInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SingleValue createSingleValue() {
		SingleValueImpl singleValue = new SingleValueImpl();
		return singleValue;
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
	public DomainModelPackage getDomainModelPackage() {
		return (DomainModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DomainModelPackage getPackage() {
		return DomainModelPackage.eINSTANCE;
	}

} //DomainModelFactoryImpl
