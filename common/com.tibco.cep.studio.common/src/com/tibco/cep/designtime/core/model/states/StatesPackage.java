/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.states;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import com.tibco.cep.designtime.core.model.ModelPackage;

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
 * @see com.tibco.cep.designtime.core.model.states.StatesFactory
 * @model kind="package"
 * @generated
 */
public interface StatesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "states";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/states";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "states";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StatesPackage eINSTANCE = com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateEntityImpl <em>State Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateEntityImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateEntity()
	 * @generated
	 */
	int STATE_ENTITY = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__OWNER_STATE_MACHINE = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__OWNER_STATE_MACHINE_PATH = ModelPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__PARENT = ModelPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__TIMEOUT = ModelPackage.ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY__TIMEOUT_UNITS = ModelPackage.ENTITY_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>State Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ENTITY_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateLinkImpl <em>State Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateLinkImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateLink()
	 * @generated
	 */
	int STATE_LINK = 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__NAMESPACE = STATE_ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__FOLDER = STATE_ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__NAME = STATE_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__DESCRIPTION = STATE_ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__LAST_MODIFIED = STATE_ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__GUID = STATE_ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__ONTOLOGY = STATE_ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__EXTENDED_PROPERTIES = STATE_ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__HIDDEN_PROPERTIES = STATE_ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__TRANSIENT_PROPERTIES = STATE_ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__OWNER_PROJECT_NAME = STATE_ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__OWNER_STATE_MACHINE = STATE_ENTITY__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__OWNER_STATE_MACHINE_PATH = STATE_ENTITY__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__PARENT = STATE_ENTITY__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__TIMEOUT = STATE_ENTITY__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK__TIMEOUT_UNITS = STATE_ENTITY__TIMEOUT_UNITS;

	/**
	 * The number of structural features of the '<em>State Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_LINK_FEATURE_COUNT = STATE_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateAnnotationLinkImpl <em>State Annotation Link</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateAnnotationLinkImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateAnnotationLink()
	 * @generated
	 */
	int STATE_ANNOTATION_LINK = 2;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__NAMESPACE = STATE_LINK__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__FOLDER = STATE_LINK__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__NAME = STATE_LINK__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__DESCRIPTION = STATE_LINK__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__LAST_MODIFIED = STATE_LINK__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__GUID = STATE_LINK__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__ONTOLOGY = STATE_LINK__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__EXTENDED_PROPERTIES = STATE_LINK__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__HIDDEN_PROPERTIES = STATE_LINK__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__TRANSIENT_PROPERTIES = STATE_LINK__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__OWNER_PROJECT_NAME = STATE_LINK__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__OWNER_STATE_MACHINE = STATE_LINK__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__OWNER_STATE_MACHINE_PATH = STATE_LINK__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__PARENT = STATE_LINK__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__TIMEOUT = STATE_LINK__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__TIMEOUT_UNITS = STATE_LINK__TIMEOUT_UNITS;

	/**
	 * The feature id for the '<em><b>To State Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__TO_STATE_ENTITY = STATE_LINK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>From Annotation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK__FROM_ANNOTATION = STATE_LINK_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>State Annotation Link</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_ANNOTATION_LINK_FEATURE_COUNT = STATE_LINK_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateTransitionImpl <em>State Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateTransitionImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateTransition()
	 * @generated
	 */
	int STATE_TRANSITION = 3;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__NAMESPACE = STATE_LINK__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__FOLDER = STATE_LINK__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__NAME = STATE_LINK__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__DESCRIPTION = STATE_LINK__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__LAST_MODIFIED = STATE_LINK__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__GUID = STATE_LINK__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__ONTOLOGY = STATE_LINK__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__EXTENDED_PROPERTIES = STATE_LINK__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__HIDDEN_PROPERTIES = STATE_LINK__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__TRANSIENT_PROPERTIES = STATE_LINK__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__OWNER_PROJECT_NAME = STATE_LINK__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__OWNER_STATE_MACHINE = STATE_LINK__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__OWNER_STATE_MACHINE_PATH = STATE_LINK__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__PARENT = STATE_LINK__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__TIMEOUT = STATE_LINK__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__TIMEOUT_UNITS = STATE_LINK__TIMEOUT_UNITS;

	/**
	 * The feature id for the '<em><b>To State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__TO_STATE = STATE_LINK_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>From State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__FROM_STATE = STATE_LINK_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Guard Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__GUARD_RULE = STATE_LINK_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Lambda</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__LAMBDA = STATE_LINK_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Fwd Correlates</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__FWD_CORRELATES = STATE_LINK_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION__LABEL = STATE_LINK_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>State Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_TRANSITION_FEATURE_COUNT = STATE_LINK_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.InternalStateTransitionImpl <em>Internal State Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.InternalStateTransitionImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getInternalStateTransition()
	 * @generated
	 */
	int INTERNAL_STATE_TRANSITION = 4;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__NAMESPACE = STATE_TRANSITION__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__FOLDER = STATE_TRANSITION__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__NAME = STATE_TRANSITION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__DESCRIPTION = STATE_TRANSITION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__LAST_MODIFIED = STATE_TRANSITION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__GUID = STATE_TRANSITION__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__ONTOLOGY = STATE_TRANSITION__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__EXTENDED_PROPERTIES = STATE_TRANSITION__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__HIDDEN_PROPERTIES = STATE_TRANSITION__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__TRANSIENT_PROPERTIES = STATE_TRANSITION__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__OWNER_PROJECT_NAME = STATE_TRANSITION__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__OWNER_STATE_MACHINE = STATE_TRANSITION__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__OWNER_STATE_MACHINE_PATH = STATE_TRANSITION__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__PARENT = STATE_TRANSITION__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__TIMEOUT = STATE_TRANSITION__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__TIMEOUT_UNITS = STATE_TRANSITION__TIMEOUT_UNITS;

	/**
	 * The feature id for the '<em><b>To State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__TO_STATE = STATE_TRANSITION__TO_STATE;

	/**
	 * The feature id for the '<em><b>From State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__FROM_STATE = STATE_TRANSITION__FROM_STATE;

	/**
	 * The feature id for the '<em><b>Guard Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__GUARD_RULE = STATE_TRANSITION__GUARD_RULE;

	/**
	 * The feature id for the '<em><b>Lambda</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__LAMBDA = STATE_TRANSITION__LAMBDA;

	/**
	 * The feature id for the '<em><b>Fwd Correlates</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__FWD_CORRELATES = STATE_TRANSITION__FWD_CORRELATES;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__LABEL = STATE_TRANSITION__LABEL;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__RULE = STATE_TRANSITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Owner State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION__OWNER_STATE = STATE_TRANSITION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Internal State Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_STATE_TRANSITION_FEATURE_COUNT = STATE_TRANSITION_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateMachineImpl <em>State Machine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateMachineImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateMachine()
	 * @generated
	 */
	int STATE_MACHINE = 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateVertexImpl <em>State Vertex</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateVertexImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateVertex()
	 * @generated
	 */
	int STATE_VERTEX = 6;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__NAMESPACE = STATE_ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__FOLDER = STATE_ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__NAME = STATE_ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__DESCRIPTION = STATE_ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__LAST_MODIFIED = STATE_ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__GUID = STATE_ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__ONTOLOGY = STATE_ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__EXTENDED_PROPERTIES = STATE_ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__HIDDEN_PROPERTIES = STATE_ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__TRANSIENT_PROPERTIES = STATE_ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__OWNER_PROJECT_NAME = STATE_ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__OWNER_STATE_MACHINE = STATE_ENTITY__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__OWNER_STATE_MACHINE_PATH = STATE_ENTITY__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__PARENT = STATE_ENTITY__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__TIMEOUT = STATE_ENTITY__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__TIMEOUT_UNITS = STATE_ENTITY__TIMEOUT_UNITS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__INCOMING_TRANSITIONS = STATE_ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX__OUTGOING_TRANSITIONS = STATE_ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>State Vertex</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_VERTEX_FEATURE_COUNT = STATE_ENTITY_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getState()
	 * @generated
	 */
	int STATE = 7;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NAMESPACE = STATE_VERTEX__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__FOLDER = STATE_VERTEX__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NAME = STATE_VERTEX__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__DESCRIPTION = STATE_VERTEX__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__LAST_MODIFIED = STATE_VERTEX__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__GUID = STATE_VERTEX__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ONTOLOGY = STATE_VERTEX__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__EXTENDED_PROPERTIES = STATE_VERTEX__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__HIDDEN_PROPERTIES = STATE_VERTEX__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__TRANSIENT_PROPERTIES = STATE_VERTEX__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OWNER_PROJECT_NAME = STATE_VERTEX__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OWNER_STATE_MACHINE = STATE_VERTEX__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OWNER_STATE_MACHINE_PATH = STATE_VERTEX__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__PARENT = STATE_VERTEX__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__TIMEOUT = STATE_VERTEX__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__TIMEOUT_UNITS = STATE_VERTEX__TIMEOUT_UNITS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__INCOMING_TRANSITIONS = STATE_VERTEX__INCOMING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OUTGOING_TRANSITIONS = STATE_VERTEX__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Internal Transition Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__INTERNAL_TRANSITION_ENABLED = STATE_VERTEX_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ENTRY_ACTION = STATE_VERTEX_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Exit Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__EXIT_ACTION = STATE_VERTEX_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Timeout Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__TIMEOUT_ACTION = STATE_VERTEX_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Internal Transition Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__INTERNAL_TRANSITION_RULE = STATE_VERTEX_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Timeout Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__TIMEOUT_EXPRESSION = STATE_VERTEX_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Timeout State GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__TIMEOUT_STATE_GUID = STATE_VERTEX_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Timeout Transition GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__TIMEOUT_TRANSITION_GUID = STATE_VERTEX_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Timeout Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__TIMEOUT_POLICY = STATE_VERTEX_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Timeout State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__TIMEOUT_STATE = STATE_VERTEX_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = STATE_VERTEX_FEATURE_COUNT + 10;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateCompositeImpl <em>State Composite</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateCompositeImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateComposite()
	 * @generated
	 */
	int STATE_COMPOSITE = 8;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__NAMESPACE = STATE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__FOLDER = STATE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__DESCRIPTION = STATE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__LAST_MODIFIED = STATE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__GUID = STATE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__ONTOLOGY = STATE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__EXTENDED_PROPERTIES = STATE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__HIDDEN_PROPERTIES = STATE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__TRANSIENT_PROPERTIES = STATE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__OWNER_PROJECT_NAME = STATE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__OWNER_STATE_MACHINE = STATE__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__OWNER_STATE_MACHINE_PATH = STATE__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__PARENT = STATE__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__TIMEOUT = STATE__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__TIMEOUT_UNITS = STATE__TIMEOUT_UNITS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__INCOMING_TRANSITIONS = STATE__INCOMING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__OUTGOING_TRANSITIONS = STATE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Internal Transition Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__INTERNAL_TRANSITION_ENABLED = STATE__INTERNAL_TRANSITION_ENABLED;

	/**
	 * The feature id for the '<em><b>Entry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__ENTRY_ACTION = STATE__ENTRY_ACTION;

	/**
	 * The feature id for the '<em><b>Exit Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__EXIT_ACTION = STATE__EXIT_ACTION;

	/**
	 * The feature id for the '<em><b>Timeout Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__TIMEOUT_ACTION = STATE__TIMEOUT_ACTION;

	/**
	 * The feature id for the '<em><b>Internal Transition Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__INTERNAL_TRANSITION_RULE = STATE__INTERNAL_TRANSITION_RULE;

	/**
	 * The feature id for the '<em><b>Timeout Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__TIMEOUT_EXPRESSION = STATE__TIMEOUT_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Timeout State GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__TIMEOUT_STATE_GUID = STATE__TIMEOUT_STATE_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Transition GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__TIMEOUT_TRANSITION_GUID = STATE__TIMEOUT_TRANSITION_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__TIMEOUT_POLICY = STATE__TIMEOUT_POLICY;

	/**
	 * The feature id for the '<em><b>Timeout State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__TIMEOUT_STATE = STATE__TIMEOUT_STATE;

	/**
	 * The feature id for the '<em><b>Timeout Composite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__TIMEOUT_COMPOSITE = STATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Concurrent State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__CONCURRENT_STATE = STATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Regions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__REGIONS = STATE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>State Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE__STATE_ENTITIES = STATE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>State Composite</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_COMPOSITE_FEATURE_COUNT = STATE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__NAMESPACE = STATE_COMPOSITE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__FOLDER = STATE_COMPOSITE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__NAME = STATE_COMPOSITE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__DESCRIPTION = STATE_COMPOSITE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__LAST_MODIFIED = STATE_COMPOSITE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__GUID = STATE_COMPOSITE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__ONTOLOGY = STATE_COMPOSITE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__EXTENDED_PROPERTIES = STATE_COMPOSITE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__HIDDEN_PROPERTIES = STATE_COMPOSITE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TRANSIENT_PROPERTIES = STATE_COMPOSITE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__OWNER_PROJECT_NAME = STATE_COMPOSITE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__OWNER_STATE_MACHINE = STATE_COMPOSITE__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__OWNER_STATE_MACHINE_PATH = STATE_COMPOSITE__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__PARENT = STATE_COMPOSITE__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TIMEOUT = STATE_COMPOSITE__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TIMEOUT_UNITS = STATE_COMPOSITE__TIMEOUT_UNITS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__INCOMING_TRANSITIONS = STATE_COMPOSITE__INCOMING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__OUTGOING_TRANSITIONS = STATE_COMPOSITE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Internal Transition Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__INTERNAL_TRANSITION_ENABLED = STATE_COMPOSITE__INTERNAL_TRANSITION_ENABLED;

	/**
	 * The feature id for the '<em><b>Entry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__ENTRY_ACTION = STATE_COMPOSITE__ENTRY_ACTION;

	/**
	 * The feature id for the '<em><b>Exit Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__EXIT_ACTION = STATE_COMPOSITE__EXIT_ACTION;

	/**
	 * The feature id for the '<em><b>Timeout Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TIMEOUT_ACTION = STATE_COMPOSITE__TIMEOUT_ACTION;

	/**
	 * The feature id for the '<em><b>Internal Transition Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__INTERNAL_TRANSITION_RULE = STATE_COMPOSITE__INTERNAL_TRANSITION_RULE;

	/**
	 * The feature id for the '<em><b>Timeout Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TIMEOUT_EXPRESSION = STATE_COMPOSITE__TIMEOUT_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Timeout State GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TIMEOUT_STATE_GUID = STATE_COMPOSITE__TIMEOUT_STATE_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Transition GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TIMEOUT_TRANSITION_GUID = STATE_COMPOSITE__TIMEOUT_TRANSITION_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TIMEOUT_POLICY = STATE_COMPOSITE__TIMEOUT_POLICY;

	/**
	 * The feature id for the '<em><b>Timeout State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TIMEOUT_STATE = STATE_COMPOSITE__TIMEOUT_STATE;

	/**
	 * The feature id for the '<em><b>Timeout Composite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__TIMEOUT_COMPOSITE = STATE_COMPOSITE__TIMEOUT_COMPOSITE;

	/**
	 * The feature id for the '<em><b>Concurrent State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__CONCURRENT_STATE = STATE_COMPOSITE__CONCURRENT_STATE;

	/**
	 * The feature id for the '<em><b>Regions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__REGIONS = STATE_COMPOSITE__REGIONS;

	/**
	 * The feature id for the '<em><b>State Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__STATE_ENTITIES = STATE_COMPOSITE__STATE_ENTITIES;

	/**
	 * The feature id for the '<em><b>State Transitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__STATE_TRANSITIONS = STATE_COMPOSITE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Annotation Links</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__ANNOTATION_LINKS = STATE_COMPOSITE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Fwd Correlates</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__FWD_CORRELATES = STATE_COMPOSITE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Main</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__MAIN = STATE_COMPOSITE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Owner Concept Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE__OWNER_CONCEPT_PATH = STATE_COMPOSITE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>State Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_FEATURE_COUNT = STATE_COMPOSITE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateSubmachineImpl <em>State Submachine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateSubmachineImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateSubmachine()
	 * @generated
	 */
	int STATE_SUBMACHINE = 9;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__NAMESPACE = STATE_COMPOSITE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__FOLDER = STATE_COMPOSITE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__NAME = STATE_COMPOSITE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__DESCRIPTION = STATE_COMPOSITE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__LAST_MODIFIED = STATE_COMPOSITE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__GUID = STATE_COMPOSITE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__ONTOLOGY = STATE_COMPOSITE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__EXTENDED_PROPERTIES = STATE_COMPOSITE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__HIDDEN_PROPERTIES = STATE_COMPOSITE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__TRANSIENT_PROPERTIES = STATE_COMPOSITE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__OWNER_PROJECT_NAME = STATE_COMPOSITE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__OWNER_STATE_MACHINE = STATE_COMPOSITE__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__OWNER_STATE_MACHINE_PATH = STATE_COMPOSITE__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__PARENT = STATE_COMPOSITE__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__TIMEOUT = STATE_COMPOSITE__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__TIMEOUT_UNITS = STATE_COMPOSITE__TIMEOUT_UNITS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__INCOMING_TRANSITIONS = STATE_COMPOSITE__INCOMING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__OUTGOING_TRANSITIONS = STATE_COMPOSITE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Internal Transition Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__INTERNAL_TRANSITION_ENABLED = STATE_COMPOSITE__INTERNAL_TRANSITION_ENABLED;

	/**
	 * The feature id for the '<em><b>Entry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__ENTRY_ACTION = STATE_COMPOSITE__ENTRY_ACTION;

	/**
	 * The feature id for the '<em><b>Exit Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__EXIT_ACTION = STATE_COMPOSITE__EXIT_ACTION;

	/**
	 * The feature id for the '<em><b>Timeout Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__TIMEOUT_ACTION = STATE_COMPOSITE__TIMEOUT_ACTION;

	/**
	 * The feature id for the '<em><b>Internal Transition Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__INTERNAL_TRANSITION_RULE = STATE_COMPOSITE__INTERNAL_TRANSITION_RULE;

	/**
	 * The feature id for the '<em><b>Timeout Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__TIMEOUT_EXPRESSION = STATE_COMPOSITE__TIMEOUT_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Timeout State GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__TIMEOUT_STATE_GUID = STATE_COMPOSITE__TIMEOUT_STATE_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Transition GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__TIMEOUT_TRANSITION_GUID = STATE_COMPOSITE__TIMEOUT_TRANSITION_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__TIMEOUT_POLICY = STATE_COMPOSITE__TIMEOUT_POLICY;

	/**
	 * The feature id for the '<em><b>Timeout State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__TIMEOUT_STATE = STATE_COMPOSITE__TIMEOUT_STATE;

	/**
	 * The feature id for the '<em><b>Timeout Composite</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__TIMEOUT_COMPOSITE = STATE_COMPOSITE__TIMEOUT_COMPOSITE;

	/**
	 * The feature id for the '<em><b>Concurrent State</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__CONCURRENT_STATE = STATE_COMPOSITE__CONCURRENT_STATE;

	/**
	 * The feature id for the '<em><b>Regions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__REGIONS = STATE_COMPOSITE__REGIONS;

	/**
	 * The feature id for the '<em><b>State Entities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__STATE_ENTITIES = STATE_COMPOSITE__STATE_ENTITIES;

	/**
	 * The feature id for the '<em><b>URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__URI = STATE_COMPOSITE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Call Explicitly</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__CALL_EXPLICITLY = STATE_COMPOSITE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Preserve Forward Correlation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE__PRESERVE_FORWARD_CORRELATION = STATE_COMPOSITE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>State Submachine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SUBMACHINE_FEATURE_COUNT = STATE_COMPOSITE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateEndImpl <em>State End</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateEndImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateEnd()
	 * @generated
	 */
	int STATE_END = 10;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__NAMESPACE = STATE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__FOLDER = STATE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__DESCRIPTION = STATE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__LAST_MODIFIED = STATE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__GUID = STATE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__ONTOLOGY = STATE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__EXTENDED_PROPERTIES = STATE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__HIDDEN_PROPERTIES = STATE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__TRANSIENT_PROPERTIES = STATE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__OWNER_PROJECT_NAME = STATE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__OWNER_STATE_MACHINE = STATE__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__OWNER_STATE_MACHINE_PATH = STATE__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__PARENT = STATE__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__TIMEOUT = STATE__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__TIMEOUT_UNITS = STATE__TIMEOUT_UNITS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__INCOMING_TRANSITIONS = STATE__INCOMING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__OUTGOING_TRANSITIONS = STATE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Internal Transition Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__INTERNAL_TRANSITION_ENABLED = STATE__INTERNAL_TRANSITION_ENABLED;

	/**
	 * The feature id for the '<em><b>Entry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__ENTRY_ACTION = STATE__ENTRY_ACTION;

	/**
	 * The feature id for the '<em><b>Exit Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__EXIT_ACTION = STATE__EXIT_ACTION;

	/**
	 * The feature id for the '<em><b>Timeout Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__TIMEOUT_ACTION = STATE__TIMEOUT_ACTION;

	/**
	 * The feature id for the '<em><b>Internal Transition Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__INTERNAL_TRANSITION_RULE = STATE__INTERNAL_TRANSITION_RULE;

	/**
	 * The feature id for the '<em><b>Timeout Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__TIMEOUT_EXPRESSION = STATE__TIMEOUT_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Timeout State GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__TIMEOUT_STATE_GUID = STATE__TIMEOUT_STATE_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Transition GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__TIMEOUT_TRANSITION_GUID = STATE__TIMEOUT_TRANSITION_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__TIMEOUT_POLICY = STATE__TIMEOUT_POLICY;

	/**
	 * The feature id for the '<em><b>Timeout State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END__TIMEOUT_STATE = STATE__TIMEOUT_STATE;

	/**
	 * The number of structural features of the '<em>State End</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_END_FEATURE_COUNT = STATE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateSimpleImpl <em>State Simple</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateSimpleImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateSimple()
	 * @generated
	 */
	int STATE_SIMPLE = 11;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__NAMESPACE = STATE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__FOLDER = STATE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__DESCRIPTION = STATE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__LAST_MODIFIED = STATE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__GUID = STATE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__ONTOLOGY = STATE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__EXTENDED_PROPERTIES = STATE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__HIDDEN_PROPERTIES = STATE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__TRANSIENT_PROPERTIES = STATE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__OWNER_PROJECT_NAME = STATE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__OWNER_STATE_MACHINE = STATE__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__OWNER_STATE_MACHINE_PATH = STATE__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__PARENT = STATE__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__TIMEOUT = STATE__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__TIMEOUT_UNITS = STATE__TIMEOUT_UNITS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__INCOMING_TRANSITIONS = STATE__INCOMING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__OUTGOING_TRANSITIONS = STATE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Internal Transition Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__INTERNAL_TRANSITION_ENABLED = STATE__INTERNAL_TRANSITION_ENABLED;

	/**
	 * The feature id for the '<em><b>Entry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__ENTRY_ACTION = STATE__ENTRY_ACTION;

	/**
	 * The feature id for the '<em><b>Exit Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__EXIT_ACTION = STATE__EXIT_ACTION;

	/**
	 * The feature id for the '<em><b>Timeout Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__TIMEOUT_ACTION = STATE__TIMEOUT_ACTION;

	/**
	 * The feature id for the '<em><b>Internal Transition Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__INTERNAL_TRANSITION_RULE = STATE__INTERNAL_TRANSITION_RULE;

	/**
	 * The feature id for the '<em><b>Timeout Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__TIMEOUT_EXPRESSION = STATE__TIMEOUT_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Timeout State GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__TIMEOUT_STATE_GUID = STATE__TIMEOUT_STATE_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Transition GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__TIMEOUT_TRANSITION_GUID = STATE__TIMEOUT_TRANSITION_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__TIMEOUT_POLICY = STATE__TIMEOUT_POLICY;

	/**
	 * The feature id for the '<em><b>Timeout State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE__TIMEOUT_STATE = STATE__TIMEOUT_STATE;

	/**
	 * The number of structural features of the '<em>State Simple</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_SIMPLE_FEATURE_COUNT = STATE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateStartImpl <em>State Start</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.impl.StateStartImpl
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateStart()
	 * @generated
	 */
	int STATE_START = 12;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__NAMESPACE = STATE__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__FOLDER = STATE__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__DESCRIPTION = STATE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__LAST_MODIFIED = STATE__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__GUID = STATE__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__ONTOLOGY = STATE__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__EXTENDED_PROPERTIES = STATE__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__HIDDEN_PROPERTIES = STATE__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__TRANSIENT_PROPERTIES = STATE__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__OWNER_PROJECT_NAME = STATE__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Owner State Machine</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__OWNER_STATE_MACHINE = STATE__OWNER_STATE_MACHINE;

	/**
	 * The feature id for the '<em><b>Owner State Machine Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__OWNER_STATE_MACHINE_PATH = STATE__OWNER_STATE_MACHINE_PATH;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__PARENT = STATE__PARENT;

	/**
	 * The feature id for the '<em><b>Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__TIMEOUT = STATE__TIMEOUT;

	/**
	 * The feature id for the '<em><b>Timeout Units</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__TIMEOUT_UNITS = STATE__TIMEOUT_UNITS;

	/**
	 * The feature id for the '<em><b>Incoming Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__INCOMING_TRANSITIONS = STATE__INCOMING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Outgoing Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__OUTGOING_TRANSITIONS = STATE__OUTGOING_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Internal Transition Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__INTERNAL_TRANSITION_ENABLED = STATE__INTERNAL_TRANSITION_ENABLED;

	/**
	 * The feature id for the '<em><b>Entry Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__ENTRY_ACTION = STATE__ENTRY_ACTION;

	/**
	 * The feature id for the '<em><b>Exit Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__EXIT_ACTION = STATE__EXIT_ACTION;

	/**
	 * The feature id for the '<em><b>Timeout Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__TIMEOUT_ACTION = STATE__TIMEOUT_ACTION;

	/**
	 * The feature id for the '<em><b>Internal Transition Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__INTERNAL_TRANSITION_RULE = STATE__INTERNAL_TRANSITION_RULE;

	/**
	 * The feature id for the '<em><b>Timeout Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__TIMEOUT_EXPRESSION = STATE__TIMEOUT_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Timeout State GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__TIMEOUT_STATE_GUID = STATE__TIMEOUT_STATE_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Transition GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__TIMEOUT_TRANSITION_GUID = STATE__TIMEOUT_TRANSITION_GUID;

	/**
	 * The feature id for the '<em><b>Timeout Policy</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__TIMEOUT_POLICY = STATE__TIMEOUT_POLICY;

	/**
	 * The feature id for the '<em><b>Timeout State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START__TIMEOUT_STATE = STATE__TIMEOUT_STATE;

	/**
	 * The number of structural features of the '<em>State Start</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_START_FEATURE_COUNT = STATE_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY <em>STATE TIMEOUT POLICY</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY
	 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getSTATE_TIMEOUT_POLICY()
	 * @generated
	 */
	int STATE_TIMEOUT_POLICY = 13;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.StateEntity <em>State Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Entity</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateEntity
	 * @generated
	 */
	EClass getStateEntity();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.states.StateEntity#getOwnerStateMachine <em>Owner State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner State Machine</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateEntity#getOwnerStateMachine()
	 * @see #getStateEntity()
	 * @generated
	 */
	EReference getStateEntity_OwnerStateMachine();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateEntity#getOwnerStateMachinePath <em>Owner State Machine Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Owner State Machine Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateEntity#getOwnerStateMachinePath()
	 * @see #getStateEntity()
	 * @generated
	 */
	EAttribute getStateEntity_OwnerStateMachinePath();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.states.StateEntity#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateEntity#getParent()
	 * @see #getStateEntity()
	 * @generated
	 */
	EReference getStateEntity_Parent();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateEntity#getTimeout <em>Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timeout</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateEntity#getTimeout()
	 * @see #getStateEntity()
	 * @generated
	 */
	EAttribute getStateEntity_Timeout();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateEntity#getTimeoutUnits <em>Timeout Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timeout Units</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateEntity#getTimeoutUnits()
	 * @see #getStateEntity()
	 * @generated
	 */
	EAttribute getStateEntity_TimeoutUnits();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.StateLink <em>State Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Link</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateLink
	 * @generated
	 */
	EClass getStateLink();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.StateAnnotationLink <em>State Annotation Link</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Annotation Link</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateAnnotationLink
	 * @generated
	 */
	EClass getStateAnnotationLink();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.states.StateAnnotationLink#getToStateEntity <em>To State Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To State Entity</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateAnnotationLink#getToStateEntity()
	 * @see #getStateAnnotationLink()
	 * @generated
	 */
	EReference getStateAnnotationLink_ToStateEntity();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateAnnotationLink#getFromAnnotation <em>From Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>From Annotation</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateAnnotationLink#getFromAnnotation()
	 * @see #getStateAnnotationLink()
	 * @generated
	 */
	EAttribute getStateAnnotationLink_FromAnnotation();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.StateTransition <em>State Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Transition</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateTransition
	 * @generated
	 */
	EClass getStateTransition();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.states.StateTransition#getToState <em>To State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To State</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateTransition#getToState()
	 * @see #getStateTransition()
	 * @generated
	 */
	EReference getStateTransition_ToState();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.states.StateTransition#getFromState <em>From State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From State</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateTransition#getFromState()
	 * @see #getStateTransition()
	 * @generated
	 */
	EReference getStateTransition_FromState();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.states.StateTransition#getGuardRule <em>Guard Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Guard Rule</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateTransition#getGuardRule()
	 * @see #getStateTransition()
	 * @generated
	 */
	EReference getStateTransition_GuardRule();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateTransition#isLambda <em>Lambda</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lambda</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateTransition#isLambda()
	 * @see #getStateTransition()
	 * @generated
	 */
	EAttribute getStateTransition_Lambda();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateTransition#isFwdCorrelates <em>Fwd Correlates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fwd Correlates</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateTransition#isFwdCorrelates()
	 * @see #getStateTransition()
	 * @generated
	 */
	EAttribute getStateTransition_FwdCorrelates();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateTransition#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateTransition#getLabel()
	 * @see #getStateTransition()
	 * @generated
	 */
	EAttribute getStateTransition_Label();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.InternalStateTransition <em>Internal State Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Internal State Transition</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.InternalStateTransition
	 * @generated
	 */
	EClass getInternalStateTransition();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.states.InternalStateTransition#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Rule</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.InternalStateTransition#getRule()
	 * @see #getInternalStateTransition()
	 * @generated
	 */
	EReference getInternalStateTransition_Rule();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.states.InternalStateTransition#getOwnerState <em>Owner State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner State</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.InternalStateTransition#getOwnerState()
	 * @see #getInternalStateTransition()
	 * @generated
	 */
	EReference getInternalStateTransition_OwnerState();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.StateMachine <em>State Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Machine</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateMachine
	 * @generated
	 */
	EClass getStateMachine();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.states.StateMachine#getStateTransitions <em>State Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>State Transitions</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateMachine#getStateTransitions()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_StateTransitions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.states.StateMachine#getAnnotationLinks <em>Annotation Links</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotation Links</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateMachine#getAnnotationLinks()
	 * @see #getStateMachine()
	 * @generated
	 */
	EReference getStateMachine_AnnotationLinks();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateMachine#isFwdCorrelates <em>Fwd Correlates</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fwd Correlates</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateMachine#isFwdCorrelates()
	 * @see #getStateMachine()
	 * @generated
	 */
	EAttribute getStateMachine_FwdCorrelates();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateMachine#isMain <em>Main</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Main</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateMachine#isMain()
	 * @see #getStateMachine()
	 * @generated
	 */
	EAttribute getStateMachine_Main();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateMachine#getOwnerConceptPath <em>Owner Concept Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Owner Concept Path</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateMachine#getOwnerConceptPath()
	 * @see #getStateMachine()
	 * @generated
	 */
	EAttribute getStateMachine_OwnerConceptPath();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.StateVertex <em>State Vertex</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Vertex</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateVertex
	 * @generated
	 */
	EClass getStateVertex();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.states.StateVertex#getIncomingTransitions <em>Incoming Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Transitions</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateVertex#getIncomingTransitions()
	 * @see #getStateVertex()
	 * @generated
	 */
	EReference getStateVertex_IncomingTransitions();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.designtime.core.model.states.StateVertex#getOutgoingTransitions <em>Outgoing Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Transitions</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateVertex#getOutgoingTransitions()
	 * @see #getStateVertex()
	 * @generated
	 */
	EReference getStateVertex_OutgoingTransitions();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.State#isInternalTransitionEnabled <em>Internal Transition Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Internal Transition Enabled</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.State#isInternalTransitionEnabled()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_InternalTransitionEnabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.states.State#getEntryAction <em>Entry Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Entry Action</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.State#getEntryAction()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_EntryAction();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.states.State#getExitAction <em>Exit Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exit Action</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.State#getExitAction()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_ExitAction();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutAction <em>Timeout Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Timeout Action</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.State#getTimeoutAction()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_TimeoutAction();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.states.State#getInternalTransitionRule <em>Internal Transition Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Internal Transition Rule</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.State#getInternalTransitionRule()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_InternalTransitionRule();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutExpression <em>Timeout Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Timeout Expression</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.State#getTimeoutExpression()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_TimeoutExpression();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutStateGUID <em>Timeout State GUID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timeout State GUID</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.State#getTimeoutStateGUID()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_TimeoutStateGUID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutTransitionGUID <em>Timeout Transition GUID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timeout Transition GUID</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.State#getTimeoutTransitionGUID()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_TimeoutTransitionGUID();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutPolicy <em>Timeout Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timeout Policy</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.State#getTimeoutPolicy()
	 * @see #getState()
	 * @generated
	 */
	EAttribute getState_TimeoutPolicy();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.designtime.core.model.states.State#getTimeoutState <em>Timeout State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Timeout State</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.State#getTimeoutState()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_TimeoutState();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.StateComposite <em>State Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Composite</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateComposite
	 * @generated
	 */
	EClass getStateComposite();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateComposite#getTimeoutComposite <em>Timeout Composite</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timeout Composite</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateComposite#getTimeoutComposite()
	 * @see #getStateComposite()
	 * @generated
	 */
	EAttribute getStateComposite_TimeoutComposite();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateComposite#isConcurrentState <em>Concurrent State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Concurrent State</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateComposite#isConcurrentState()
	 * @see #getStateComposite()
	 * @generated
	 */
	EAttribute getStateComposite_ConcurrentState();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.states.StateComposite#getRegions <em>Regions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Regions</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateComposite#getRegions()
	 * @see #getStateComposite()
	 * @generated
	 */
	EReference getStateComposite_Regions();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.designtime.core.model.states.StateComposite#getStateEntities <em>State Entities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>State Entities</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateComposite#getStateEntities()
	 * @see #getStateComposite()
	 * @generated
	 */
	EReference getStateComposite_StateEntities();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.StateSubmachine <em>State Submachine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Submachine</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateSubmachine
	 * @generated
	 */
	EClass getStateSubmachine();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateSubmachine#getURI <em>URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>URI</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateSubmachine#getURI()
	 * @see #getStateSubmachine()
	 * @generated
	 */
	EAttribute getStateSubmachine_URI();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateSubmachine#isCallExplicitly <em>Call Explicitly</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Call Explicitly</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateSubmachine#isCallExplicitly()
	 * @see #getStateSubmachine()
	 * @generated
	 */
	EAttribute getStateSubmachine_CallExplicitly();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.states.StateSubmachine#isPreserveForwardCorrelation <em>Preserve Forward Correlation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Preserve Forward Correlation</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateSubmachine#isPreserveForwardCorrelation()
	 * @see #getStateSubmachine()
	 * @generated
	 */
	EAttribute getStateSubmachine_PreserveForwardCorrelation();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.StateEnd <em>State End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State End</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateEnd
	 * @generated
	 */
	EClass getStateEnd();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.StateSimple <em>State Simple</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Simple</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateSimple
	 * @generated
	 */
	EClass getStateSimple();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.states.StateStart <em>State Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Start</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.StateStart
	 * @generated
	 */
	EClass getStateStart();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY <em>STATE TIMEOUT POLICY</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>STATE TIMEOUT POLICY</em>'.
	 * @see com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY
	 * @generated
	 */
	EEnum getSTATE_TIMEOUT_POLICY();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	StatesFactory getStatesFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateEntityImpl <em>State Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateEntityImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateEntity()
		 * @generated
		 */
		EClass STATE_ENTITY = eINSTANCE.getStateEntity();

		/**
		 * The meta object literal for the '<em><b>Owner State Machine</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_ENTITY__OWNER_STATE_MACHINE = eINSTANCE.getStateEntity_OwnerStateMachine();

		/**
		 * The meta object literal for the '<em><b>Owner State Machine Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_ENTITY__OWNER_STATE_MACHINE_PATH = eINSTANCE.getStateEntity_OwnerStateMachinePath();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_ENTITY__PARENT = eINSTANCE.getStateEntity_Parent();

		/**
		 * The meta object literal for the '<em><b>Timeout</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_ENTITY__TIMEOUT = eINSTANCE.getStateEntity_Timeout();

		/**
		 * The meta object literal for the '<em><b>Timeout Units</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_ENTITY__TIMEOUT_UNITS = eINSTANCE.getStateEntity_TimeoutUnits();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateLinkImpl <em>State Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateLinkImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateLink()
		 * @generated
		 */
		EClass STATE_LINK = eINSTANCE.getStateLink();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateAnnotationLinkImpl <em>State Annotation Link</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateAnnotationLinkImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateAnnotationLink()
		 * @generated
		 */
		EClass STATE_ANNOTATION_LINK = eINSTANCE.getStateAnnotationLink();

		/**
		 * The meta object literal for the '<em><b>To State Entity</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_ANNOTATION_LINK__TO_STATE_ENTITY = eINSTANCE.getStateAnnotationLink_ToStateEntity();

		/**
		 * The meta object literal for the '<em><b>From Annotation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_ANNOTATION_LINK__FROM_ANNOTATION = eINSTANCE.getStateAnnotationLink_FromAnnotation();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateTransitionImpl <em>State Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateTransitionImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateTransition()
		 * @generated
		 */
		EClass STATE_TRANSITION = eINSTANCE.getStateTransition();

		/**
		 * The meta object literal for the '<em><b>To State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_TRANSITION__TO_STATE = eINSTANCE.getStateTransition_ToState();

		/**
		 * The meta object literal for the '<em><b>From State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_TRANSITION__FROM_STATE = eINSTANCE.getStateTransition_FromState();

		/**
		 * The meta object literal for the '<em><b>Guard Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_TRANSITION__GUARD_RULE = eINSTANCE.getStateTransition_GuardRule();

		/**
		 * The meta object literal for the '<em><b>Lambda</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_TRANSITION__LAMBDA = eINSTANCE.getStateTransition_Lambda();

		/**
		 * The meta object literal for the '<em><b>Fwd Correlates</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_TRANSITION__FWD_CORRELATES = eINSTANCE.getStateTransition_FwdCorrelates();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_TRANSITION__LABEL = eINSTANCE.getStateTransition_Label();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.InternalStateTransitionImpl <em>Internal State Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.InternalStateTransitionImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getInternalStateTransition()
		 * @generated
		 */
		EClass INTERNAL_STATE_TRANSITION = eINSTANCE.getInternalStateTransition();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERNAL_STATE_TRANSITION__RULE = eINSTANCE.getInternalStateTransition_Rule();

		/**
		 * The meta object literal for the '<em><b>Owner State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERNAL_STATE_TRANSITION__OWNER_STATE = eINSTANCE.getInternalStateTransition_OwnerState();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateMachineImpl <em>State Machine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateMachineImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateMachine()
		 * @generated
		 */
		EClass STATE_MACHINE = eINSTANCE.getStateMachine();

		/**
		 * The meta object literal for the '<em><b>State Transitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE__STATE_TRANSITIONS = eINSTANCE.getStateMachine_StateTransitions();

		/**
		 * The meta object literal for the '<em><b>Annotation Links</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE__ANNOTATION_LINKS = eINSTANCE.getStateMachine_AnnotationLinks();

		/**
		 * The meta object literal for the '<em><b>Fwd Correlates</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_MACHINE__FWD_CORRELATES = eINSTANCE.getStateMachine_FwdCorrelates();

		/**
		 * The meta object literal for the '<em><b>Main</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_MACHINE__MAIN = eINSTANCE.getStateMachine_Main();

		/**
		 * The meta object literal for the '<em><b>Owner Concept Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_MACHINE__OWNER_CONCEPT_PATH = eINSTANCE.getStateMachine_OwnerConceptPath();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateVertexImpl <em>State Vertex</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateVertexImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateVertex()
		 * @generated
		 */
		EClass STATE_VERTEX = eINSTANCE.getStateVertex();

		/**
		 * The meta object literal for the '<em><b>Incoming Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_VERTEX__INCOMING_TRANSITIONS = eINSTANCE.getStateVertex_IncomingTransitions();

		/**
		 * The meta object literal for the '<em><b>Outgoing Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_VERTEX__OUTGOING_TRANSITIONS = eINSTANCE.getStateVertex_OutgoingTransitions();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Internal Transition Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__INTERNAL_TRANSITION_ENABLED = eINSTANCE.getState_InternalTransitionEnabled();

		/**
		 * The meta object literal for the '<em><b>Entry Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__ENTRY_ACTION = eINSTANCE.getState_EntryAction();

		/**
		 * The meta object literal for the '<em><b>Exit Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__EXIT_ACTION = eINSTANCE.getState_ExitAction();

		/**
		 * The meta object literal for the '<em><b>Timeout Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__TIMEOUT_ACTION = eINSTANCE.getState_TimeoutAction();

		/**
		 * The meta object literal for the '<em><b>Internal Transition Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__INTERNAL_TRANSITION_RULE = eINSTANCE.getState_InternalTransitionRule();

		/**
		 * The meta object literal for the '<em><b>Timeout Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__TIMEOUT_EXPRESSION = eINSTANCE.getState_TimeoutExpression();

		/**
		 * The meta object literal for the '<em><b>Timeout State GUID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__TIMEOUT_STATE_GUID = eINSTANCE.getState_TimeoutStateGUID();

		/**
		 * The meta object literal for the '<em><b>Timeout Transition GUID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__TIMEOUT_TRANSITION_GUID = eINSTANCE.getState_TimeoutTransitionGUID();

		/**
		 * The meta object literal for the '<em><b>Timeout Policy</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE__TIMEOUT_POLICY = eINSTANCE.getState_TimeoutPolicy();

		/**
		 * The meta object literal for the '<em><b>Timeout State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__TIMEOUT_STATE = eINSTANCE.getState_TimeoutState();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateCompositeImpl <em>State Composite</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateCompositeImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateComposite()
		 * @generated
		 */
		EClass STATE_COMPOSITE = eINSTANCE.getStateComposite();

		/**
		 * The meta object literal for the '<em><b>Timeout Composite</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_COMPOSITE__TIMEOUT_COMPOSITE = eINSTANCE.getStateComposite_TimeoutComposite();

		/**
		 * The meta object literal for the '<em><b>Concurrent State</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_COMPOSITE__CONCURRENT_STATE = eINSTANCE.getStateComposite_ConcurrentState();

		/**
		 * The meta object literal for the '<em><b>Regions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_COMPOSITE__REGIONS = eINSTANCE.getStateComposite_Regions();

		/**
		 * The meta object literal for the '<em><b>State Entities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_COMPOSITE__STATE_ENTITIES = eINSTANCE.getStateComposite_StateEntities();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateSubmachineImpl <em>State Submachine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateSubmachineImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateSubmachine()
		 * @generated
		 */
		EClass STATE_SUBMACHINE = eINSTANCE.getStateSubmachine();

		/**
		 * The meta object literal for the '<em><b>URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SUBMACHINE__URI = eINSTANCE.getStateSubmachine_URI();

		/**
		 * The meta object literal for the '<em><b>Call Explicitly</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SUBMACHINE__CALL_EXPLICITLY = eINSTANCE.getStateSubmachine_CallExplicitly();

		/**
		 * The meta object literal for the '<em><b>Preserve Forward Correlation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_SUBMACHINE__PRESERVE_FORWARD_CORRELATION = eINSTANCE.getStateSubmachine_PreserveForwardCorrelation();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateEndImpl <em>State End</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateEndImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateEnd()
		 * @generated
		 */
		EClass STATE_END = eINSTANCE.getStateEnd();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateSimpleImpl <em>State Simple</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateSimpleImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateSimple()
		 * @generated
		 */
		EClass STATE_SIMPLE = eINSTANCE.getStateSimple();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.impl.StateStartImpl <em>State Start</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.impl.StateStartImpl
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getStateStart()
		 * @generated
		 */
		EClass STATE_START = eINSTANCE.getStateStart();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY <em>STATE TIMEOUT POLICY</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.states.STATE_TIMEOUT_POLICY
		 * @see com.tibco.cep.designtime.core.model.states.impl.StatesPackageImpl#getSTATE_TIMEOUT_POLICY()
		 * @generated
		 */
		EEnum STATE_TIMEOUT_POLICY = eINSTANCE.getSTATE_TIMEOUT_POLICY();

	}

} //StatesPackage
