/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.domainmodel;

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
 * @see com.tibco.cep.decision.table.model.domainmodel.DomainModelFactory
 * @model kind="package"
 * @generated
 */
public interface DomainModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "domainmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/decision/table/model/DomainModel.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "DomainModel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DomainModelPackage eINSTANCE = com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainEntryImpl <em>Domain Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainEntryImpl
	 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelPackageImpl#getDomainEntry()
	 * @generated
	 */
	int DOMAIN_ENTRY = 0;

	/**
	 * The feature id for the '<em><b>Entry Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_ENTRY__ENTRY_NAME = 0;

	/**
	 * The feature id for the '<em><b>Entry Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_ENTRY__ENTRY_VALUE = 1;

	/**
	 * The number of structural features of the '<em>Domain Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_ENTRY_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.domainmodel.impl.EntryValueImpl <em>Entry Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.domainmodel.impl.EntryValueImpl
	 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelPackageImpl#getEntryValue()
	 * @generated
	 */
	int ENTRY_VALUE = 3;

	/**
	 * The feature id for the '<em><b>Multiple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_VALUE__MULTIPLE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_VALUE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Entry Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_VALUE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.domainmodel.impl.RangeInfoImpl <em>Range Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.domainmodel.impl.RangeInfoImpl
	 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelPackageImpl#getRangeInfo()
	 * @generated
	 */
	int RANGE_INFO = 1;

	/**
	 * The feature id for the '<em><b>Multiple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_INFO__MULTIPLE = ENTRY_VALUE__MULTIPLE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_INFO__VALUE = ENTRY_VALUE__VALUE;

	/**
	 * The feature id for the '<em><b>Lower Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_INFO__LOWER_BOUND = ENTRY_VALUE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Upper Bound</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_INFO__UPPER_BOUND = ENTRY_VALUE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lower Bound Included</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_INFO__LOWER_BOUND_INCLUDED = ENTRY_VALUE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Upper Bound Included</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_INFO__UPPER_BOUND_INCLUDED = ENTRY_VALUE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Range Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_INFO_FEATURE_COUNT = ENTRY_VALUE_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.domainmodel.impl.SingleValueImpl <em>Single Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.domainmodel.impl.SingleValueImpl
	 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelPackageImpl#getSingleValue()
	 * @generated
	 */
	int SINGLE_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Multiple</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_VALUE__MULTIPLE = ENTRY_VALUE__MULTIPLE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_VALUE__VALUE = ENTRY_VALUE__VALUE;

	/**
	 * The number of structural features of the '<em>Single Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_VALUE_FEATURE_COUNT = ENTRY_VALUE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainImpl <em>Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainImpl
	 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelPackageImpl#getDomain()
	 * @generated
	 */
	int DOMAIN = 4;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__MODIFIED = OntologyPackage.ACCESS_CONTROL_CANDIDATE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Domain Entry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__DOMAIN_ENTRY = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__TYPE = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Resource Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__RESOURCE_TYPE = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__RESOURCE = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Override</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__OVERRIDE = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Db Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__DB_REF = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_FEATURE_COUNT = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 6;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.domainmodel.DomainEntry <em>Domain Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain Entry</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainEntry
	 * @generated
	 */
	EClass getDomainEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.DomainEntry#getEntryName <em>Entry Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entry Name</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainEntry#getEntryName()
	 * @see #getDomainEntry()
	 * @generated
	 */
	EAttribute getDomainEntry_EntryName();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decision.table.model.domainmodel.DomainEntry#getEntryValue <em>Entry Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entry Value</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.DomainEntry#getEntryValue()
	 * @see #getDomainEntry()
	 * @generated
	 */
	EReference getDomainEntry_EntryValue();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo <em>Range Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Range Info</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.RangeInfo
	 * @generated
	 */
	EClass getRangeInfo();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#getLowerBound <em>Lower Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Bound</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.RangeInfo#getLowerBound()
	 * @see #getRangeInfo()
	 * @generated
	 */
	EAttribute getRangeInfo_LowerBound();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#getUpperBound <em>Upper Bound</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Bound</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.RangeInfo#getUpperBound()
	 * @see #getRangeInfo()
	 * @generated
	 */
	EAttribute getRangeInfo_UpperBound();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#isLowerBoundIncluded <em>Lower Bound Included</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Bound Included</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.RangeInfo#isLowerBoundIncluded()
	 * @see #getRangeInfo()
	 * @generated
	 */
	EAttribute getRangeInfo_LowerBoundIncluded();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.RangeInfo#isUpperBoundIncluded <em>Upper Bound Included</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Bound Included</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.RangeInfo#isUpperBoundIncluded()
	 * @see #getRangeInfo()
	 * @generated
	 */
	EAttribute getRangeInfo_UpperBoundIncluded();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.domainmodel.SingleValue <em>Single Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Single Value</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.SingleValue
	 * @generated
	 */
	EClass getSingleValue();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.domainmodel.EntryValue <em>Entry Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry Value</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.EntryValue
	 * @generated
	 */
	EClass getEntryValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.EntryValue#isMultiple <em>Multiple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Multiple</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.EntryValue#isMultiple()
	 * @see #getEntryValue()
	 * @generated
	 */
	EAttribute getEntryValue_Multiple();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.EntryValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.EntryValue#getValue()
	 * @see #getEntryValue()
	 * @generated
	 */
	EAttribute getEntryValue_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.domainmodel.Domain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.Domain
	 * @generated
	 */
	EClass getDomain();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getDomainEntry <em>Domain Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Domain Entry</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.Domain#getDomainEntry()
	 * @see #getDomain()
	 * @generated
	 */
	EReference getDomain_DomainEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.Domain#getType()
	 * @see #getDomain()
	 * @generated
	 */
	EAttribute getDomain_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getResourceType <em>Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource Type</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.Domain#getResourceType()
	 * @see #getDomain()
	 * @generated
	 */
	EAttribute getDomain_ResourceType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.Domain#getResource()
	 * @see #getDomain()
	 * @generated
	 */
	EAttribute getDomain_Resource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.Domain#isOverride <em>Override</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Override</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.Domain#isOverride()
	 * @see #getDomain()
	 * @generated
	 */
	EAttribute getDomain_Override();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.domainmodel.Domain#getDbRef <em>Db Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Db Ref</em>'.
	 * @see com.tibco.cep.decision.table.model.domainmodel.Domain#getDbRef()
	 * @see #getDomain()
	 * @generated
	 */
	EAttribute getDomain_DbRef();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DomainModelFactory getDomainModelFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainEntryImpl <em>Domain Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainEntryImpl
		 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelPackageImpl#getDomainEntry()
		 * @generated
		 */
		EClass DOMAIN_ENTRY = eINSTANCE.getDomainEntry();

		/**
		 * The meta object literal for the '<em><b>Entry Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN_ENTRY__ENTRY_NAME = eINSTANCE.getDomainEntry_EntryName();

		/**
		 * The meta object literal for the '<em><b>Entry Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_ENTRY__ENTRY_VALUE = eINSTANCE.getDomainEntry_EntryValue();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.domainmodel.impl.RangeInfoImpl <em>Range Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.domainmodel.impl.RangeInfoImpl
		 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelPackageImpl#getRangeInfo()
		 * @generated
		 */
		EClass RANGE_INFO = eINSTANCE.getRangeInfo();

		/**
		 * The meta object literal for the '<em><b>Lower Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGE_INFO__LOWER_BOUND = eINSTANCE.getRangeInfo_LowerBound();

		/**
		 * The meta object literal for the '<em><b>Upper Bound</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGE_INFO__UPPER_BOUND = eINSTANCE.getRangeInfo_UpperBound();

		/**
		 * The meta object literal for the '<em><b>Lower Bound Included</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGE_INFO__LOWER_BOUND_INCLUDED = eINSTANCE.getRangeInfo_LowerBoundIncluded();

		/**
		 * The meta object literal for the '<em><b>Upper Bound Included</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGE_INFO__UPPER_BOUND_INCLUDED = eINSTANCE.getRangeInfo_UpperBoundIncluded();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.domainmodel.impl.SingleValueImpl <em>Single Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.domainmodel.impl.SingleValueImpl
		 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelPackageImpl#getSingleValue()
		 * @generated
		 */
		EClass SINGLE_VALUE = eINSTANCE.getSingleValue();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.domainmodel.impl.EntryValueImpl <em>Entry Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.domainmodel.impl.EntryValueImpl
		 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelPackageImpl#getEntryValue()
		 * @generated
		 */
		EClass ENTRY_VALUE = eINSTANCE.getEntryValue();

		/**
		 * The meta object literal for the '<em><b>Multiple</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_VALUE__MULTIPLE = eINSTANCE.getEntryValue_Multiple();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_VALUE__VALUE = eINSTANCE.getEntryValue_Value();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.domainmodel.impl.DomainImpl <em>Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainImpl
		 * @see com.tibco.cep.decision.table.model.domainmodel.impl.DomainModelPackageImpl#getDomain()
		 * @generated
		 */
		EClass DOMAIN = eINSTANCE.getDomain();

		/**
		 * The meta object literal for the '<em><b>Domain Entry</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN__DOMAIN_ENTRY = eINSTANCE.getDomain_DomainEntry();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN__TYPE = eINSTANCE.getDomain_Type();

		/**
		 * The meta object literal for the '<em><b>Resource Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN__RESOURCE_TYPE = eINSTANCE.getDomain_ResourceType();

		/**
		 * The meta object literal for the '<em><b>Resource</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN__RESOURCE = eINSTANCE.getDomain_Resource();

		/**
		 * The meta object literal for the '<em><b>Override</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN__OVERRIDE = eINSTANCE.getDomain_Override();

		/**
		 * The meta object literal for the '<em><b>Db Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN__DB_REF = eINSTANCE.getDomain_DbRef();

	}

} //DomainModelPackage
