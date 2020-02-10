/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionprojectmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.cep.decisionproject.ontology.OntologyPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decisionprojectmodel.DecisionProjectModelFactory
 * @model kind="package"
 * @generated
 */
public interface DecisionProjectModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "decisionprojectmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/decisionproject/model/decisionproject.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "dp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DecisionProjectModelPackage eINSTANCE = com.tibco.cep.decisionprojectmodel.impl.DecisionProjectModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionprojectmodel.impl.DecisionProjectImpl <em>Decision Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionprojectmodel.impl.DecisionProjectImpl
	 * @see com.tibco.cep.decisionprojectmodel.impl.DecisionProjectModelPackageImpl#getDecisionProject()
	 * @generated
	 */
	int DECISION_PROJECT = 0;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_PROJECT__ONTOLOGY = 0;

	/**
	 * The feature id for the '<em><b>Domain Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_PROJECT__DOMAIN_MODEL = 1;

	/**
	 * The feature id for the '<em><b>Auth Token</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_PROJECT__AUTH_TOKEN = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_PROJECT__NAME = 3;

	/**
	 * The feature id for the '<em><b>Md</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_PROJECT__MD = 4;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_PROJECT__VERSION = 5;

	/**
	 * The number of structural features of the '<em>Decision Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_PROJECT_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decisionprojectmodel.impl.DomainModelImpl <em>Domain Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decisionprojectmodel.impl.DomainModelImpl
	 * @see com.tibco.cep.decisionprojectmodel.impl.DecisionProjectModelPackageImpl#getDomainModel()
	 * @generated
	 */
	int DOMAIN_MODEL = 1;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL__MODIFIED = OntologyPackage.ACCESS_CONTROL_CANDIDATE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL__DOMAIN = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL__VERSION = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Last Modified By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL__LAST_MODIFIED_BY = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Domain Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_MODEL_FEATURE_COUNT = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 3;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionprojectmodel.DecisionProject <em>Decision Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Decision Project</em>'.
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProject
	 * @generated
	 */
	EClass getDecisionProject();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getOntology <em>Ontology</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Ontology</em>'.
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProject#getOntology()
	 * @see #getDecisionProject()
	 * @generated
	 */
	EReference getDecisionProject_Ontology();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getDomainModel <em>Domain Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Domain Model</em>'.
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProject#getDomainModel()
	 * @see #getDecisionProject()
	 * @generated
	 */
	EReference getDecisionProject_DomainModel();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getAuthToken <em>Auth Token</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Auth Token</em>'.
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProject#getAuthToken()
	 * @see #getDecisionProject()
	 * @generated
	 */
	EAttribute getDecisionProject_AuthToken();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProject#getName()
	 * @see #getDecisionProject()
	 * @generated
	 */
	EAttribute getDecisionProject_Name();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getMd <em>Md</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Md</em>'.
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProject#getMd()
	 * @see #getDecisionProject()
	 * @generated
	 */
	EReference getDecisionProject_Md();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionprojectmodel.DecisionProject#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.cep.decisionprojectmodel.DecisionProject#getVersion()
	 * @see #getDecisionProject()
	 * @generated
	 */
	EAttribute getDecisionProject_Version();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decisionprojectmodel.DomainModel <em>Domain Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain Model</em>'.
	 * @see com.tibco.cep.decisionprojectmodel.DomainModel
	 * @generated
	 */
	EClass getDomainModel();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decisionprojectmodel.DomainModel#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Domain</em>'.
	 * @see com.tibco.cep.decisionprojectmodel.DomainModel#getDomain()
	 * @see #getDomainModel()
	 * @generated
	 */
	EReference getDomainModel_Domain();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionprojectmodel.DomainModel#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.cep.decisionprojectmodel.DomainModel#getVersion()
	 * @see #getDomainModel()
	 * @generated
	 */
	EAttribute getDomainModel_Version();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decisionprojectmodel.DomainModel#getLastModifiedBy <em>Last Modified By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Modified By</em>'.
	 * @see com.tibco.cep.decisionprojectmodel.DomainModel#getLastModifiedBy()
	 * @see #getDomainModel()
	 * @generated
	 */
	EAttribute getDomainModel_LastModifiedBy();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DecisionProjectModelFactory getDecisionProjectModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionprojectmodel.impl.DecisionProjectImpl <em>Decision Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionprojectmodel.impl.DecisionProjectImpl
		 * @see com.tibco.cep.decisionprojectmodel.impl.DecisionProjectModelPackageImpl#getDecisionProject()
		 * @generated
		 */
		EClass DECISION_PROJECT = eINSTANCE.getDecisionProject();

		/**
		 * The meta object literal for the '<em><b>Ontology</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DECISION_PROJECT__ONTOLOGY = eINSTANCE.getDecisionProject_Ontology();

		/**
		 * The meta object literal for the '<em><b>Domain Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DECISION_PROJECT__DOMAIN_MODEL = eINSTANCE.getDecisionProject_DomainModel();

		/**
		 * The meta object literal for the '<em><b>Auth Token</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECISION_PROJECT__AUTH_TOKEN = eINSTANCE.getDecisionProject_AuthToken();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECISION_PROJECT__NAME = eINSTANCE.getDecisionProject_Name();

		/**
		 * The meta object literal for the '<em><b>Md</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DECISION_PROJECT__MD = eINSTANCE.getDecisionProject_Md();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECISION_PROJECT__VERSION = eINSTANCE.getDecisionProject_Version();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decisionprojectmodel.impl.DomainModelImpl <em>Domain Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decisionprojectmodel.impl.DomainModelImpl
		 * @see com.tibco.cep.decisionprojectmodel.impl.DecisionProjectModelPackageImpl#getDomainModel()
		 * @generated
		 */
		EClass DOMAIN_MODEL = eINSTANCE.getDomainModel();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_MODEL__DOMAIN = eINSTANCE.getDomainModel_Domain();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN_MODEL__VERSION = eINSTANCE.getDomainModel_Version();

		/**
		 * The meta object literal for the '<em><b>Last Modified By</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN_MODEL__LAST_MODIFIED_BY = eINSTANCE.getDomainModel_LastModifiedBy();

	}

} //DecisionProjectModelPackage
