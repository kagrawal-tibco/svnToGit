/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionprojectmodel.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.decisionprojectmodel.DecisionProject;
import com.tibco.cep.decisionprojectmodel.DecisionProjectModelFactory;
import com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage;
import com.tibco.cep.decisionprojectmodel.DomainModel;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DecisionProjectModelFactoryImpl extends EFactoryImpl implements DecisionProjectModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DecisionProjectModelFactory init() {
		try {
			DecisionProjectModelFactory theDecisionProjectModelFactory = (DecisionProjectModelFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/decisionproject/model/decisionproject.ecore"); 
			if (theDecisionProjectModelFactory != null) {
				return theDecisionProjectModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new DecisionProjectModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionProjectModelFactoryImpl() {
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
			case DecisionProjectModelPackage.DECISION_PROJECT: return createDecisionProject();
			case DecisionProjectModelPackage.DOMAIN_MODEL: return createDomainModel();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionProject createDecisionProject() {
		DecisionProjectImpl decisionProject = new DecisionProjectImpl();
		return decisionProject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainModel createDomainModel() {
		DomainModelImpl domainModel = new DomainModelImpl();
		return domainModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionProjectModelPackage getDecisionProjectModelPackage() {
		return (DecisionProjectModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static DecisionProjectModelPackage getPackage() {
		return DecisionProjectModelPackage.eINSTANCE;
	}

} //DecisionProjectModelFactoryImpl
