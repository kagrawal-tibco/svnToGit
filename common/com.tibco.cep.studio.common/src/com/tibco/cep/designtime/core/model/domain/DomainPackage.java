/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.domain;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.element.ElementPackage;

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
 * @see com.tibco.cep.designtime.core.model.domain.DomainFactory
 * @model kind="package"
 * @generated
 */
public interface DomainPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "domain";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/domain";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "dm";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DomainPackage eINSTANCE = com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.domain.impl.DomainImpl <em>Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainImpl
	 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl#getDomain()
	 * @generated
	 */
	int DOMAIN = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__ID = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__ENTRIES = ModelPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Data Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__DATA_TYPE = ModelPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Super Domain Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__SUPER_DOMAIN_PATH = ModelPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Super Domain</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN__SUPER_DOMAIN = ModelPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.domain.impl.DomainEntryImpl <em>Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainEntryImpl
	 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl#getDomainEntry()
	 * @generated
	 */
	int DOMAIN_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_ENTRY__ID = ModelPackage.COMPARABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_ENTRY__PARENT = ModelPackage.COMPARABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_ENTRY__DESCRIPTION = ModelPackage.COMPARABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_ENTRY__VALUE = ModelPackage.COMPARABLE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_ENTRY_FEATURE_COUNT = ModelPackage.COMPARABLE_FEATURE_COUNT + 4;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.domain.impl.DomainInstanceImpl <em>Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainInstanceImpl
	 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl#getDomainInstance()
	 * @generated
	 */
	int DOMAIN_INSTANCE = 2;

	/**
	 * The feature id for the '<em><b>Resource Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_INSTANCE__RESOURCE_PATH = ElementPackage.BASE_INSTANCE__RESOURCE_PATH;

	/**
	 * The feature id for the '<em><b>Owner Property</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_INSTANCE__OWNER_PROPERTY = ElementPackage.BASE_INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_INSTANCE__GUID = ElementPackage.BASE_INSTANCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_INSTANCE__OWNER_PROJECT_NAME = ElementPackage.BASE_INSTANCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_INSTANCE_FEATURE_COUNT = ElementPackage.BASE_INSTANCE_FEATURE_COUNT + 3;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.domain.impl.RangeImpl <em>Range</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.domain.impl.RangeImpl
	 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl#getRange()
	 * @generated
	 */
	int RANGE = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE__ID = DOMAIN_ENTRY__ID;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE__PARENT = DOMAIN_ENTRY__PARENT;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE__DESCRIPTION = DOMAIN_ENTRY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE__VALUE = DOMAIN_ENTRY__VALUE;

	/**
	 * The feature id for the '<em><b>Lower</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE__LOWER = DOMAIN_ENTRY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Upper</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE__UPPER = DOMAIN_ENTRY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lower Inclusive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE__LOWER_INCLUSIVE = DOMAIN_ENTRY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Upper Inclusive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE__UPPER_INCLUSIVE = DOMAIN_ENTRY_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_FEATURE_COUNT = DOMAIN_ENTRY_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.domain.impl.SingleImpl <em>Single</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.domain.impl.SingleImpl
	 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl#getSingle()
	 * @generated
	 */
	int SINGLE = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE__ID = DOMAIN_ENTRY__ID;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE__PARENT = DOMAIN_ENTRY__PARENT;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE__DESCRIPTION = DOMAIN_ENTRY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE__VALUE = DOMAIN_ENTRY__VALUE;

	/**
	 * The number of structural features of the '<em>Single</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_FEATURE_COUNT = DOMAIN_ENTRY_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.domain.Domain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Domain
	 * @generated
	 */
	EClass getDomain();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.Domain#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Domain#getId()
	 * @see #getDomain()
	 * @generated
	 */
	EAttribute getDomain_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.domain.Domain#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Domain#getEntries()
	 * @see #getDomain()
	 * @generated
	 */
	EReference getDomain_Entries();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.Domain#getDataType <em>Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Data Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Domain#getDataType()
	 * @see #getDomain()
	 * @generated
	 */
	EAttribute getDomain_DataType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.Domain#getSuperDomainPath <em>Super Domain Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Super Domain Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Domain#getSuperDomainPath()
	 * @see #getDomain()
	 * @generated
	 */
	EAttribute getDomain_SuperDomainPath();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.domain.Domain#getSuperDomain <em>Super Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Super Domain</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Domain#getSuperDomain()
	 * @see #getDomain()
	 * @generated
	 */
	EReference getDomain_SuperDomain();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.domain.DomainEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.DomainEntry
	 * @generated
	 */
	EClass getDomainEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.DomainEntry#getId()
	 * @see #getDomainEntry()
	 * @generated
	 */
	EAttribute getDomainEntry_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.DomainEntry#getValue()
	 * @see #getDomainEntry()
	 * @generated
	 */
	EAttribute getDomainEntry_Value();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.DomainEntry#getParent()
	 * @see #getDomainEntry()
	 * @generated
	 */
	EReference getDomainEntry_Parent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.DomainEntry#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.DomainEntry#getDescription()
	 * @see #getDomainEntry()
	 * @generated
	 */
	EAttribute getDomainEntry_Description();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.domain.DomainInstance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instance</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.DomainInstance
	 * @generated
	 */
	EClass getDomainInstance();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.domain.DomainInstance#getOwnerProperty <em>Owner Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner Property</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.DomainInstance#getOwnerProperty()
	 * @see #getDomainInstance()
	 * @generated
	 */
	EReference getDomainInstance_OwnerProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.DomainInstance#getGUID <em>GUID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>GUID</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.DomainInstance#getGUID()
	 * @see #getDomainInstance()
	 * @generated
	 */
	EAttribute getDomainInstance_GUID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.DomainInstance#getOwnerProjectName <em>Owner Project Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Owner Project Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.DomainInstance#getOwnerProjectName()
	 * @see #getDomainInstance()
	 * @generated
	 */
	EAttribute getDomainInstance_OwnerProjectName();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.domain.Range <em>Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Range</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Range
	 * @generated
	 */
	EClass getRange();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.Range#getLower <em>Lower</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Range#getLower()
	 * @see #getRange()
	 * @generated
	 */
	EAttribute getRange_Lower();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.Range#getUpper <em>Upper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Range#getUpper()
	 * @see #getRange()
	 * @generated
	 */
	EAttribute getRange_Upper();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.Range#isLowerInclusive <em>Lower Inclusive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lower Inclusive</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Range#isLowerInclusive()
	 * @see #getRange()
	 * @generated
	 */
	EAttribute getRange_LowerInclusive();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.domain.Range#isUpperInclusive <em>Upper Inclusive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upper Inclusive</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Range#isUpperInclusive()
	 * @see #getRange()
	 * @generated
	 */
	EAttribute getRange_UpperInclusive();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.domain.Single <em>Single</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Single</em>'.
	 * @see com.tibco.cep.designtime.core.model.domain.Single
	 * @generated
	 */
	EClass getSingle();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DomainFactory getDomainFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.domain.impl.DomainImpl <em>Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainImpl
		 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl#getDomain()
		 * @generated
		 */
		EClass DOMAIN = eINSTANCE.getDomain();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN__ID = eINSTANCE.getDomain_Id();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN__ENTRIES = eINSTANCE.getDomain_Entries();

		/**
		 * The meta object literal for the '<em><b>Data Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN__DATA_TYPE = eINSTANCE.getDomain_DataType();

		/**
		 * The meta object literal for the '<em><b>Super Domain Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN__SUPER_DOMAIN_PATH = eINSTANCE.getDomain_SuperDomainPath();

		/**
		 * The meta object literal for the '<em><b>Super Domain</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN__SUPER_DOMAIN = eINSTANCE.getDomain_SuperDomain();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.domain.impl.DomainEntryImpl <em>Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainEntryImpl
		 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl#getDomainEntry()
		 * @generated
		 */
		EClass DOMAIN_ENTRY = eINSTANCE.getDomainEntry();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN_ENTRY__ID = eINSTANCE.getDomainEntry_Id();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN_ENTRY__VALUE = eINSTANCE.getDomainEntry_Value();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_ENTRY__PARENT = eINSTANCE.getDomainEntry_Parent();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN_ENTRY__DESCRIPTION = eINSTANCE.getDomainEntry_Description();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.domain.impl.DomainInstanceImpl <em>Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainInstanceImpl
		 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl#getDomainInstance()
		 * @generated
		 */
		EClass DOMAIN_INSTANCE = eINSTANCE.getDomainInstance();

		/**
		 * The meta object literal for the '<em><b>Owner Property</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOMAIN_INSTANCE__OWNER_PROPERTY = eINSTANCE.getDomainInstance_OwnerProperty();

		/**
		 * The meta object literal for the '<em><b>GUID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN_INSTANCE__GUID = eINSTANCE.getDomainInstance_GUID();

		/**
		 * The meta object literal for the '<em><b>Owner Project Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOMAIN_INSTANCE__OWNER_PROJECT_NAME = eINSTANCE.getDomainInstance_OwnerProjectName();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.domain.impl.RangeImpl <em>Range</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.domain.impl.RangeImpl
		 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl#getRange()
		 * @generated
		 */
		EClass RANGE = eINSTANCE.getRange();

		/**
		 * The meta object literal for the '<em><b>Lower</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGE__LOWER = eINSTANCE.getRange_Lower();

		/**
		 * The meta object literal for the '<em><b>Upper</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGE__UPPER = eINSTANCE.getRange_Upper();

		/**
		 * The meta object literal for the '<em><b>Lower Inclusive</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGE__LOWER_INCLUSIVE = eINSTANCE.getRange_LowerInclusive();

		/**
		 * The meta object literal for the '<em><b>Upper Inclusive</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGE__UPPER_INCLUSIVE = eINSTANCE.getRange_UpperInclusive();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.domain.impl.SingleImpl <em>Single</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.domain.impl.SingleImpl
		 * @see com.tibco.cep.designtime.core.model.domain.impl.DomainPackageImpl#getSingle()
		 * @generated
		 */
		EClass SINGLE = eINSTANCE.getSingle();

	}

} //DomainPackage
