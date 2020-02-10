/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see com.tibco.cep.designtime.core.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/designtime_ontology.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "designtime";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = com.tibco.cep.designtime.core.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.impl.EntityImpl <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.impl.EntityImpl
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getEntity()
	 * @generated
	 */
	int ENTITY = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__NAMESPACE = 0;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__FOLDER = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__NAME = 2;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__DESCRIPTION = 3;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__LAST_MODIFIED = 4;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__GUID = 5;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__ONTOLOGY = 6;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__EXTENDED_PROPERTIES = 7;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__HIDDEN_PROPERTIES = 8;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__TRANSIENT_PROPERTIES = 9;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY__OWNER_PROJECT_NAME = 10;

	/**
	 * The number of structural features of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_FEATURE_COUNT = 11;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.impl.SimplePropertyImpl <em>Simple Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.impl.SimplePropertyImpl
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getSimpleProperty()
	 * @generated
	 */
	int SIMPLE_PROPERTY = 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__NAMESPACE = ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__FOLDER = ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__NAME = ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__DESCRIPTION = ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__LAST_MODIFIED = ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__GUID = ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__ONTOLOGY = ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__EXTENDED_PROPERTIES = ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__HIDDEN_PROPERTIES = ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__TRANSIENT_PROPERTIES = ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__OWNER_PROJECT_NAME = ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__VALUE = ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__MANDATORY = ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__TYPE = ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Display Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY__DISPLAY_NAME = ENTITY_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Simple Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_PROPERTY_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.impl.ObjectPropertyImpl <em>Object Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.impl.ObjectPropertyImpl
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getObjectProperty()
	 * @generated
	 */
	int OBJECT_PROPERTY = 2;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__NAMESPACE = ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__FOLDER = ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__NAME = ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__DESCRIPTION = ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__LAST_MODIFIED = ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__GUID = ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__ONTOLOGY = ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__EXTENDED_PROPERTIES = ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__HIDDEN_PROPERTIES = ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__TRANSIENT_PROPERTIES = ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__OWNER_PROJECT_NAME = ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY__VALUE = ENTITY_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Object Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBJECT_PROPERTY_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.impl.RuleParticipantImpl <em>Rule Participant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.impl.RuleParticipantImpl
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getRuleParticipant()
	 * @generated
	 */
	int RULE_PARTICIPANT = 3;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT__NAMESPACE = ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT__FOLDER = ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT__NAME = ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT__DESCRIPTION = ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT__LAST_MODIFIED = ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT__GUID = ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT__ONTOLOGY = ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT__EXTENDED_PROPERTIES = ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT__HIDDEN_PROPERTIES = ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT__TRANSIENT_PROPERTIES = ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT__OWNER_PROJECT_NAME = ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The number of structural features of the '<em>Rule Participant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_PARTICIPANT_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.impl.PropertyMapImpl <em>Property Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.impl.PropertyMapImpl
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getPropertyMap()
	 * @generated
	 */
	int PROPERTY_MAP = 4;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_MAP__PROPERTIES = 0;

	/**
	 * The number of structural features of the '<em>Property Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_MAP_FEATURE_COUNT = 1;


	/**
	 * The meta object id for the '{@link java.lang.Comparable <em>Comparable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.Comparable
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getComparable()
	 * @generated
	 */
	int COMPARABLE = 5;

	/**
	 * The number of structural features of the '<em>Comparable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARABLE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.PROPERTY_TYPES <em>PROPERTY TYPES</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.PROPERTY_TYPES
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getPROPERTY_TYPES()
	 * @generated
	 */
	int PROPERTY_TYPES = 6;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.TIMEOUT_UNITS <em>TIMEOUT UNITS</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.TIMEOUT_UNITS
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getTIMEOUT_UNITS()
	 * @generated
	 */
	int TIMEOUT_UNITS = 7;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.METRIC_TYPE <em>METRIC TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.METRIC_TYPE
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getMETRIC_TYPE()
	 * @generated
	 */
	int METRIC_TYPE = 8;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE <em>METRIC AGGR TYPE</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getMETRIC_AGGR_TYPE()
	 * @generated
	 */
	int METRIC_AGGR_TYPE = 9;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.HISTORY_POLICY <em>HISTORY POLICY</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.HISTORY_POLICY
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getHISTORY_POLICY()
	 * @generated
	 */
	int HISTORY_POLICY = 10;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES <em>DOMAIN DATA TYPES</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getDOMAIN_DATA_TYPES()
	 * @generated
	 */
	int DOMAIN_DATA_TYPES = 11;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.TIME_UNITS <em>TIME UNITS</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.TIME_UNITS
	 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getTIME_UNITS()
	 * @generated
	 */
	int TIME_UNITS = 12;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity
	 * @generated
	 */
	EClass getEntity();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.Entity#getNamespace <em>Namespace</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Namespace</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity#getNamespace()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_Namespace();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.Entity#getFolder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Folder</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity#getFolder()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_Folder();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.Entity#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity#getName()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.Entity#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity#getDescription()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_Description();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.Entity#getLastModified <em>Last Modified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Modified</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity#getLastModified()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_LastModified();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.Entity#getGUID <em>GUID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>GUID</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity#getGUID()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_GUID();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.Entity#getOntology <em>Ontology</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ontology</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity#getOntology()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_Ontology();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.Entity#getExtendedProperties <em>Extended Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Extended Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity#getExtendedProperties()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_ExtendedProperties();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.Entity#getHiddenProperties <em>Hidden Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Hidden Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity#getHiddenProperties()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_HiddenProperties();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.Entity#getTransientProperties <em>Transient Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Transient Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity#getTransientProperties()
	 * @see #getEntity()
	 * @generated
	 */
	EReference getEntity_TransientProperties();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.Entity#getOwnerProjectName <em>Owner Project Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Owner Project Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.Entity#getOwnerProjectName()
	 * @see #getEntity()
	 * @generated
	 */
	EAttribute getEntity_OwnerProjectName();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.SimpleProperty <em>Simple Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Property</em>'.
	 * @see com.tibco.cep.designtime.core.model.SimpleProperty
	 * @generated
	 */
	EClass getSimpleProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.SimpleProperty#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.SimpleProperty#getValue()
	 * @see #getSimpleProperty()
	 * @generated
	 */
	EAttribute getSimpleProperty_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.SimpleProperty#isMandatory <em>Mandatory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mandatory</em>'.
	 * @see com.tibco.cep.designtime.core.model.SimpleProperty#isMandatory()
	 * @see #getSimpleProperty()
	 * @generated
	 */
	EAttribute getSimpleProperty_Mandatory();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.SimpleProperty#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.designtime.core.model.SimpleProperty#getType()
	 * @see #getSimpleProperty()
	 * @generated
	 */
	EAttribute getSimpleProperty_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.SimpleProperty#getDisplayName <em>Display Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.SimpleProperty#getDisplayName()
	 * @see #getSimpleProperty()
	 * @generated
	 */
	EAttribute getSimpleProperty_DisplayName();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.ObjectProperty <em>Object Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Object Property</em>'.
	 * @see com.tibco.cep.designtime.core.model.ObjectProperty
	 * @generated
	 */
	EClass getObjectProperty();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.ObjectProperty#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see com.tibco.cep.designtime.core.model.ObjectProperty#getValue()
	 * @see #getObjectProperty()
	 * @generated
	 */
	EReference getObjectProperty_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.RuleParticipant <em>Rule Participant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Participant</em>'.
	 * @see com.tibco.cep.designtime.core.model.RuleParticipant
	 * @generated
	 */
	EClass getRuleParticipant();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.PropertyMap <em>Property Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Map</em>'.
	 * @see com.tibco.cep.designtime.core.model.PropertyMap
	 * @generated
	 */
	EClass getPropertyMap();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.PropertyMap#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see com.tibco.cep.designtime.core.model.PropertyMap#getProperties()
	 * @see #getPropertyMap()
	 * @generated
	 */
	EReference getPropertyMap_Properties();

	/**
	 * Returns the meta object for class '{@link java.lang.Comparable <em>Comparable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comparable</em>'.
	 * @see java.lang.Comparable
	 * @model instanceClass="java.lang.Comparable"
	 * @generated
	 */
	EClass getComparable();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.PROPERTY_TYPES <em>PROPERTY TYPES</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>PROPERTY TYPES</em>'.
	 * @see com.tibco.cep.designtime.core.model.PROPERTY_TYPES
	 * @generated
	 */
	EEnum getPROPERTY_TYPES();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.TIMEOUT_UNITS <em>TIMEOUT UNITS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>TIMEOUT UNITS</em>'.
	 * @see com.tibco.cep.designtime.core.model.TIMEOUT_UNITS
	 * @generated
	 */
	EEnum getTIMEOUT_UNITS();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.METRIC_TYPE <em>METRIC TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>METRIC TYPE</em>'.
	 * @see com.tibco.cep.designtime.core.model.METRIC_TYPE
	 * @generated
	 */
	EEnum getMETRIC_TYPE();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE <em>METRIC AGGR TYPE</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>METRIC AGGR TYPE</em>'.
	 * @see com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE
	 * @generated
	 */
	EEnum getMETRIC_AGGR_TYPE();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.HISTORY_POLICY <em>HISTORY POLICY</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>HISTORY POLICY</em>'.
	 * @see com.tibco.cep.designtime.core.model.HISTORY_POLICY
	 * @generated
	 */
	EEnum getHISTORY_POLICY();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES <em>DOMAIN DATA TYPES</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>DOMAIN DATA TYPES</em>'.
	 * @see com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES
	 * @generated
	 */
	EEnum getDOMAIN_DATA_TYPES();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.TIME_UNITS <em>TIME UNITS</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>TIME UNITS</em>'.
	 * @see com.tibco.cep.designtime.core.model.TIME_UNITS
	 * @generated
	 */
	EEnum getTIME_UNITS();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.impl.EntityImpl <em>Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.impl.EntityImpl
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getEntity()
		 * @generated
		 */
		EClass ENTITY = eINSTANCE.getEntity();

		/**
		 * The meta object literal for the '<em><b>Namespace</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__NAMESPACE = eINSTANCE.getEntity_Namespace();

		/**
		 * The meta object literal for the '<em><b>Folder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__FOLDER = eINSTANCE.getEntity_Folder();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__NAME = eINSTANCE.getEntity_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__DESCRIPTION = eINSTANCE.getEntity_Description();

		/**
		 * The meta object literal for the '<em><b>Last Modified</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__LAST_MODIFIED = eINSTANCE.getEntity_LastModified();

		/**
		 * The meta object literal for the '<em><b>GUID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__GUID = eINSTANCE.getEntity_GUID();

		/**
		 * The meta object literal for the '<em><b>Ontology</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__ONTOLOGY = eINSTANCE.getEntity_Ontology();

		/**
		 * The meta object literal for the '<em><b>Extended Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__EXTENDED_PROPERTIES = eINSTANCE.getEntity_ExtendedProperties();

		/**
		 * The meta object literal for the '<em><b>Hidden Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__HIDDEN_PROPERTIES = eINSTANCE.getEntity_HiddenProperties();

		/**
		 * The meta object literal for the '<em><b>Transient Properties</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY__TRANSIENT_PROPERTIES = eINSTANCE.getEntity_TransientProperties();

		/**
		 * The meta object literal for the '<em><b>Owner Project Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTITY__OWNER_PROJECT_NAME = eINSTANCE.getEntity_OwnerProjectName();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.impl.SimplePropertyImpl <em>Simple Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.impl.SimplePropertyImpl
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getSimpleProperty()
		 * @generated
		 */
		EClass SIMPLE_PROPERTY = eINSTANCE.getSimpleProperty();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_PROPERTY__VALUE = eINSTANCE.getSimpleProperty_Value();

		/**
		 * The meta object literal for the '<em><b>Mandatory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_PROPERTY__MANDATORY = eINSTANCE.getSimpleProperty_Mandatory();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_PROPERTY__TYPE = eINSTANCE.getSimpleProperty_Type();

		/**
		 * The meta object literal for the '<em><b>Display Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_PROPERTY__DISPLAY_NAME = eINSTANCE.getSimpleProperty_DisplayName();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.impl.ObjectPropertyImpl <em>Object Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.impl.ObjectPropertyImpl
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getObjectProperty()
		 * @generated
		 */
		EClass OBJECT_PROPERTY = eINSTANCE.getObjectProperty();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBJECT_PROPERTY__VALUE = eINSTANCE.getObjectProperty_Value();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.impl.RuleParticipantImpl <em>Rule Participant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.impl.RuleParticipantImpl
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getRuleParticipant()
		 * @generated
		 */
		EClass RULE_PARTICIPANT = eINSTANCE.getRuleParticipant();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.impl.PropertyMapImpl <em>Property Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.impl.PropertyMapImpl
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getPropertyMap()
		 * @generated
		 */
		EClass PROPERTY_MAP = eINSTANCE.getPropertyMap();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_MAP__PROPERTIES = eINSTANCE.getPropertyMap_Properties();

		/**
		 * The meta object literal for the '{@link java.lang.Comparable <em>Comparable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.Comparable
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getComparable()
		 * @generated
		 */
		EClass COMPARABLE = eINSTANCE.getComparable();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.PROPERTY_TYPES <em>PROPERTY TYPES</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.PROPERTY_TYPES
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getPROPERTY_TYPES()
		 * @generated
		 */
		EEnum PROPERTY_TYPES = eINSTANCE.getPROPERTY_TYPES();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.TIMEOUT_UNITS <em>TIMEOUT UNITS</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.TIMEOUT_UNITS
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getTIMEOUT_UNITS()
		 * @generated
		 */
		EEnum TIMEOUT_UNITS = eINSTANCE.getTIMEOUT_UNITS();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.METRIC_TYPE <em>METRIC TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.METRIC_TYPE
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getMETRIC_TYPE()
		 * @generated
		 */
		EEnum METRIC_TYPE = eINSTANCE.getMETRIC_TYPE();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE <em>METRIC AGGR TYPE</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.METRIC_AGGR_TYPE
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getMETRIC_AGGR_TYPE()
		 * @generated
		 */
		EEnum METRIC_AGGR_TYPE = eINSTANCE.getMETRIC_AGGR_TYPE();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.HISTORY_POLICY <em>HISTORY POLICY</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.HISTORY_POLICY
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getHISTORY_POLICY()
		 * @generated
		 */
		EEnum HISTORY_POLICY = eINSTANCE.getHISTORY_POLICY();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES <em>DOMAIN DATA TYPES</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getDOMAIN_DATA_TYPES()
		 * @generated
		 */
		EEnum DOMAIN_DATA_TYPES = eINSTANCE.getDOMAIN_DATA_TYPES();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.TIME_UNITS <em>TIME UNITS</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.TIME_UNITS
		 * @see com.tibco.cep.designtime.core.model.impl.ModelPackageImpl#getTIME_UNITS()
		 * @generated
		 */
		EEnum TIME_UNITS = eINSTANCE.getTIME_UNITS();

	}

} //ModelPackage
