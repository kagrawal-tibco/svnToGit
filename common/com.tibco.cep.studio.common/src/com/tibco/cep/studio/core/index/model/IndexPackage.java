/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model;

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
 * @see com.tibco.cep.studio.core.index.model.IndexFactory
 * @model kind="package"
 * @generated
 */
public interface IndexPackage extends EPackage {
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
	String eNS_URI = "http:///com/tibco/cep/designer/index/core/model/ontology_index.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	IndexPackage eINSTANCE = com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.StructuredElement <em>Structured Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.StructuredElement
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getStructuredElement()
	 * @generated
	 */
	int STRUCTURED_ELEMENT = 1;

	/**
	 * The number of structural features of the '<em>Structured Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRUCTURED_ELEMENT_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl <em>Designer Project</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getDesignerProject()
	 * @generated
	 */
	int DESIGNER_PROJECT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.FolderImpl <em>Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.FolderImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getFolder()
	 * @generated
	 */
	int FOLDER = 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl <em>Designer Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getDesignerElement()
	 * @generated
	 */
	int DESIGNER_ELEMENT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_ELEMENT__NAME = STRUCTURED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_ELEMENT__ELEMENT_TYPE = STRUCTURED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_ELEMENT__LAZILY_CREATED = STRUCTURED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_ELEMENT__LAST_MODIFIED = STRUCTURED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_ELEMENT__CREATION_DATE = STRUCTURED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Designer Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_ELEMENT_FEATURE_COUNT = STRUCTURED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.MemberElementImpl <em>Member Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.MemberElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getMemberElement()
	 * @generated
	 */
	int MEMBER_ELEMENT = 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl <em>Element Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getElementReference()
	 * @generated
	 */
	int ELEMENT_REFERENCE = 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.TypeElementImpl <em>Type Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.TypeElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getTypeElement()
	 * @generated
	 */
	int TYPE_ELEMENT = 7;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.EntityElementImpl <em>Entity Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.EntityElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getEntityElement()
	 * @generated
	 */
	int ENTITY_ELEMENT = 10;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.StateMachineElementImpl <em>State Machine Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.StateMachineElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getStateMachineElement()
	 * @generated
	 */
	int STATE_MACHINE_ELEMENT = 8;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.DecisionTableElementImpl <em>Decision Table Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.DecisionTableElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getDecisionTableElement()
	 * @generated
	 */
	int DECISION_TABLE_ELEMENT = 11;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.ArchiveElementImpl <em>Archive Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.ArchiveElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getArchiveElement()
	 * @generated
	 */
	int ARCHIVE_ELEMENT = 12;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.RuleElementImpl <em>Rule Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.RuleElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getRuleElement()
	 * @generated
	 */
	int RULE_ELEMENT = 13;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.VariableDefinitionImpl <em>Variable Definition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.VariableDefinitionImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getVariableDefinition()
	 * @generated
	 */
	int VARIABLE_DEFINITION = 14;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.LocalVariableDefImpl <em>Local Variable Def</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.LocalVariableDefImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getLocalVariableDef()
	 * @generated
	 */
	int LOCAL_VARIABLE_DEF = 15;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.GlobalVariableDefImpl <em>Global Variable Def</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.GlobalVariableDefImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getGlobalVariableDef()
	 * @generated
	 */
	int GLOBAL_VARIABLE_DEF = 16;
	
	
	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.BindingVariableDefImpl <em>Binding Variable Def</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.BindingVariableDefImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getBindingVariableDef()
	 * @generated
	 */
	int BINDING_VARIABLE_DEF = 31;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.ElementContainer <em>Element Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.ElementContainer
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getElementContainer()
	 * @generated
	 */
	int ELEMENT_CONTAINER = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_CONTAINER__NAME = DESIGNER_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_CONTAINER__ELEMENT_TYPE = DESIGNER_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_CONTAINER__LAZILY_CREATED = DESIGNER_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_CONTAINER__LAST_MODIFIED = DESIGNER_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_CONTAINER__CREATION_DATE = DESIGNER_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_CONTAINER__ENTRIES = DESIGNER_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Element Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_CONTAINER_FEATURE_COUNT = DESIGNER_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__NAME = ELEMENT_CONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__ELEMENT_TYPE = ELEMENT_CONTAINER__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__LAZILY_CREATED = ELEMENT_CONTAINER__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__LAST_MODIFIED = ELEMENT_CONTAINER__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__CREATION_DATE = ELEMENT_CONTAINER__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__ENTRIES = ELEMENT_CONTAINER__ENTRIES;

	/**
	 * The feature id for the '<em><b>Entity Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__ENTITY_ELEMENTS = ELEMENT_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Decision Table Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__DECISION_TABLE_ELEMENTS = ELEMENT_CONTAINER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Archive Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__ARCHIVE_ELEMENTS = ELEMENT_CONTAINER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Rule Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__RULE_ELEMENTS = ELEMENT_CONTAINER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Root Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__ROOT_PATH = ELEMENT_CONTAINER_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Last Persisted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__LAST_PERSISTED = ELEMENT_CONTAINER_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Referenced Projects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__REFERENCED_PROJECTS = ELEMENT_CONTAINER_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Driver Manager</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__DRIVER_MANAGER = ELEMENT_CONTAINER_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Domain Instance Elements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__DOMAIN_INSTANCE_ELEMENTS = ELEMENT_CONTAINER_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__ARCHIVE_PATH = ELEMENT_CONTAINER_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT__VERSION = ELEMENT_CONTAINER_FEATURE_COUNT + 10;

	/**
	 * The number of structural features of the '<em>Designer Project</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DESIGNER_PROJECT_FEATURE_COUNT = ELEMENT_CONTAINER_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__NAME = ELEMENT_CONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__ELEMENT_TYPE = ELEMENT_CONTAINER__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__LAZILY_CREATED = ELEMENT_CONTAINER__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__LAST_MODIFIED = ELEMENT_CONTAINER__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__CREATION_DATE = ELEMENT_CONTAINER__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__ENTRIES = ELEMENT_CONTAINER__ENTRIES;

	/**
	 * The number of structural features of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_FEATURE_COUNT = ELEMENT_CONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_ELEMENT__NAME = DESIGNER_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_ELEMENT__ELEMENT_TYPE = DESIGNER_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_ELEMENT__LAZILY_CREATED = DESIGNER_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_ELEMENT__LAST_MODIFIED = DESIGNER_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_ELEMENT__CREATION_DATE = DESIGNER_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_ELEMENT__OFFSET = DESIGNER_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_ELEMENT__LENGTH = DESIGNER_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Member Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMBER_ELEMENT_FEATURE_COUNT = DESIGNER_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Att Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE__ATT_REF = 1;

	/**
	 * The feature id for the '<em><b>Type Ref</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE__TYPE_REF = 2;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE__OFFSET = 3;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE__LENGTH = 4;

	/**
	 * The feature id for the '<em><b>Qualifier</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE__QUALIFIER = 5;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE__ARRAY = 6;

	/**
	 * The feature id for the '<em><b>Method</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE__METHOD = 7;

	/**
	 * The feature id for the '<em><b>Binding</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE__BINDING = 8;

	/**
	 * The number of structural features of the '<em>Element Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_REFERENCE_FEATURE_COUNT = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ELEMENT__NAME = DESIGNER_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ELEMENT__ELEMENT_TYPE = DESIGNER_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ELEMENT__LAZILY_CREATED = DESIGNER_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ELEMENT__LAST_MODIFIED = DESIGNER_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ELEMENT__CREATION_DATE = DESIGNER_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ELEMENT__FOLDER = DESIGNER_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_ELEMENT_FEATURE_COUNT = DESIGNER_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_ELEMENT__NAME = TYPE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_ELEMENT__ELEMENT_TYPE = TYPE_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_ELEMENT__LAZILY_CREATED = TYPE_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_ELEMENT__LAST_MODIFIED = TYPE_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_ELEMENT__CREATION_DATE = TYPE_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_ELEMENT__FOLDER = TYPE_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_ELEMENT__ENTITY = TYPE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Entity Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_ELEMENT_FEATURE_COUNT = TYPE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_ELEMENT__NAME = ENTITY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_ELEMENT__ELEMENT_TYPE = ENTITY_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_ELEMENT__LAZILY_CREATED = ENTITY_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_ELEMENT__LAST_MODIFIED = ENTITY_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_ELEMENT__CREATION_DATE = ENTITY_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_ELEMENT__FOLDER = ENTITY_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_ELEMENT__ENTITY = ENTITY_ELEMENT__ENTITY;

	/**
	 * The feature id for the '<em><b>Compilable Scopes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_ELEMENT__COMPILABLE_SCOPES = ENTITY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Index Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_ELEMENT__INDEX_NAME = ENTITY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>State Machine Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_MACHINE_ELEMENT_FEATURE_COUNT = ENTITY_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.EventElementImpl <em>Event Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.EventElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getEventElement()
	 * @generated
	 */
	int EVENT_ELEMENT = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_ELEMENT__NAME = ENTITY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_ELEMENT__ELEMENT_TYPE = ENTITY_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_ELEMENT__LAZILY_CREATED = ENTITY_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_ELEMENT__LAST_MODIFIED = ENTITY_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_ELEMENT__CREATION_DATE = ENTITY_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_ELEMENT__FOLDER = ENTITY_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_ELEMENT__ENTITY = ENTITY_ELEMENT__ENTITY;

	/**
	 * The feature id for the '<em><b>Expiry Action Scope Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY = ENTITY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Event Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EVENT_ELEMENT_FEATURE_COUNT = ENTITY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TABLE_ELEMENT__NAME = TYPE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TABLE_ELEMENT__ELEMENT_TYPE = TYPE_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TABLE_ELEMENT__LAZILY_CREATED = TYPE_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TABLE_ELEMENT__LAST_MODIFIED = TYPE_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TABLE_ELEMENT__CREATION_DATE = TYPE_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TABLE_ELEMENT__FOLDER = TYPE_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Implementation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TABLE_ELEMENT__IMPLEMENTATION = TYPE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Decision Table Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECISION_TABLE_ELEMENT_FEATURE_COUNT = TYPE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_ELEMENT__NAME = TYPE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_ELEMENT__ELEMENT_TYPE = TYPE_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_ELEMENT__LAZILY_CREATED = TYPE_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_ELEMENT__LAST_MODIFIED = TYPE_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_ELEMENT__CREATION_DATE = TYPE_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_ELEMENT__FOLDER = TYPE_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Archive</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_ELEMENT__ARCHIVE = TYPE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Archive Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARCHIVE_ELEMENT_FEATURE_COUNT = TYPE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT__NAME = TYPE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT__ELEMENT_TYPE = TYPE_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT__LAZILY_CREATED = TYPE_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT__LAST_MODIFIED = TYPE_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT__CREATION_DATE = TYPE_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT__FOLDER = TYPE_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT__RULE = TYPE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Virtual</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT__VIRTUAL = TYPE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT__SCOPE = TYPE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Global Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT__GLOBAL_VARIABLES = TYPE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Index Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT__INDEX_NAME = TYPE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Rule Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_ELEMENT_FEATURE_COUNT = TYPE_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DEFINITION__NAME = MEMBER_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DEFINITION__ELEMENT_TYPE = MEMBER_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DEFINITION__LAZILY_CREATED = MEMBER_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DEFINITION__LAST_MODIFIED = MEMBER_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DEFINITION__CREATION_DATE = MEMBER_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DEFINITION__OFFSET = MEMBER_ELEMENT__OFFSET;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DEFINITION__LENGTH = MEMBER_ELEMENT__LENGTH;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DEFINITION__TYPE = MEMBER_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DEFINITION__ARRAY = MEMBER_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Variable Definition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_DEFINITION_FEATURE_COUNT = MEMBER_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_DEF__NAME = VARIABLE_DEFINITION__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_DEF__ELEMENT_TYPE = VARIABLE_DEFINITION__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_DEF__LAZILY_CREATED = VARIABLE_DEFINITION__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_DEF__LAST_MODIFIED = VARIABLE_DEFINITION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_DEF__CREATION_DATE = VARIABLE_DEFINITION__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_DEF__OFFSET = VARIABLE_DEFINITION__OFFSET;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_DEF__LENGTH = VARIABLE_DEFINITION__LENGTH;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_DEF__TYPE = VARIABLE_DEFINITION__TYPE;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_DEF__ARRAY = VARIABLE_DEFINITION__ARRAY;

	/**
	 * The number of structural features of the '<em>Local Variable Def</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCAL_VARIABLE_DEF_FEATURE_COUNT = VARIABLE_DEFINITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE_DEF__NAME = VARIABLE_DEFINITION__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE_DEF__ELEMENT_TYPE = VARIABLE_DEFINITION__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE_DEF__LAZILY_CREATED = VARIABLE_DEFINITION__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE_DEF__LAST_MODIFIED = VARIABLE_DEFINITION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE_DEF__CREATION_DATE = VARIABLE_DEFINITION__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE_DEF__OFFSET = VARIABLE_DEFINITION__OFFSET;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE_DEF__LENGTH = VARIABLE_DEFINITION__LENGTH;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE_DEF__TYPE = VARIABLE_DEFINITION__TYPE;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE_DEF__ARRAY = VARIABLE_DEFINITION__ARRAY;

	/**
	 * The number of structural features of the '<em>Global Variable Def</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_VARIABLE_DEF_FEATURE_COUNT = VARIABLE_DEFINITION_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.InstanceElementImpl <em>Instance Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.InstanceElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getInstanceElement()
	 * @generated
	 */
	int INSTANCE_ELEMENT = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_ELEMENT__NAME = TYPE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_ELEMENT__ELEMENT_TYPE = TYPE_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_ELEMENT__LAZILY_CREATED = TYPE_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_ELEMENT__LAST_MODIFIED = TYPE_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_ELEMENT__CREATION_DATE = TYPE_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_ELEMENT__FOLDER = TYPE_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Instances</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_ELEMENT__INSTANCES = TYPE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entity Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_ELEMENT__ENTITY_PATH = TYPE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Instance Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INSTANCE_ELEMENT_FEATURE_COUNT = TYPE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedElementImpl <em>Shared Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.SharedElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedElement()
	 * @generated
	 */
	int SHARED_ELEMENT = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ELEMENT__NAME = DESIGNER_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ELEMENT__ELEMENT_TYPE = DESIGNER_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ELEMENT__LAZILY_CREATED = DESIGNER_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ELEMENT__LAST_MODIFIED = DESIGNER_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ELEMENT__CREATION_DATE = DESIGNER_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ELEMENT__ARCHIVE_PATH = DESIGNER_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entry Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ELEMENT__ENTRY_PATH = DESIGNER_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ELEMENT__FILE_NAME = DESIGNER_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Shared Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ELEMENT_FEATURE_COUNT = DESIGNER_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl <em>Shared Decision Table Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedDecisionTableElement()
	 * @generated
	 */
	int SHARED_DECISION_TABLE_ELEMENT = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT__NAME = SHARED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT__ELEMENT_TYPE = SHARED_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT__LAZILY_CREATED = SHARED_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT__LAST_MODIFIED = SHARED_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT__CREATION_DATE = SHARED_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT__ARCHIVE_PATH = SHARED_ELEMENT__ARCHIVE_PATH;

	/**
	 * The feature id for the '<em><b>Entry Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT__ENTRY_PATH = SHARED_ELEMENT__ENTRY_PATH;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT__FILE_NAME = SHARED_ELEMENT__FILE_NAME;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT__FOLDER = SHARED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Implementation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT__IMPLEMENTATION = SHARED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Shared Implementation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION = SHARED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Shared Decision Table Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_DECISION_TABLE_ELEMENT_FEATURE_COUNT = SHARED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedRuleElementImpl <em>Shared Rule Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.SharedRuleElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedRuleElement()
	 * @generated
	 */
	int SHARED_RULE_ELEMENT = 20;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__NAME = SHARED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__ELEMENT_TYPE = SHARED_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__LAZILY_CREATED = SHARED_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__LAST_MODIFIED = SHARED_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__CREATION_DATE = SHARED_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__ARCHIVE_PATH = SHARED_ELEMENT__ARCHIVE_PATH;

	/**
	 * The feature id for the '<em><b>Entry Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__ENTRY_PATH = SHARED_ELEMENT__ENTRY_PATH;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__FILE_NAME = SHARED_ELEMENT__FILE_NAME;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__FOLDER = SHARED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__RULE = SHARED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Virtual</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__VIRTUAL = SHARED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__SCOPE = SHARED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Global Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__GLOBAL_VARIABLES = SHARED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Index Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT__INDEX_NAME = SHARED_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Shared Rule Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_RULE_ELEMENT_FEATURE_COUNT = SHARED_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedStateMachineElementImpl <em>Shared State Machine Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.SharedStateMachineElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedStateMachineElement()
	 * @generated
	 */
	int SHARED_STATE_MACHINE_ELEMENT = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__NAME = STATE_MACHINE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__ELEMENT_TYPE = STATE_MACHINE_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__LAZILY_CREATED = STATE_MACHINE_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__LAST_MODIFIED = STATE_MACHINE_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__CREATION_DATE = STATE_MACHINE_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__FOLDER = STATE_MACHINE_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__ENTITY = STATE_MACHINE_ELEMENT__ENTITY;

	/**
	 * The feature id for the '<em><b>Compilable Scopes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__COMPILABLE_SCOPES = STATE_MACHINE_ELEMENT__COMPILABLE_SCOPES;

	/**
	 * The feature id for the '<em><b>Index Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__INDEX_NAME = STATE_MACHINE_ELEMENT__INDEX_NAME;

	/**
	 * The feature id for the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__ARCHIVE_PATH = STATE_MACHINE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entry Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__ENTRY_PATH = STATE_MACHINE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__FILE_NAME = STATE_MACHINE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Shared Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT__SHARED_ENTITY = STATE_MACHINE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Shared State Machine Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_STATE_MACHINE_ELEMENT_FEATURE_COUNT = STATE_MACHINE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedEntityElementImpl <em>Shared Entity Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.SharedEntityElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedEntityElement()
	 * @generated
	 */
	int SHARED_ENTITY_ELEMENT = 23;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT__NAME = SHARED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT__ELEMENT_TYPE = SHARED_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT__LAZILY_CREATED = SHARED_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT__LAST_MODIFIED = SHARED_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT__CREATION_DATE = SHARED_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT__ARCHIVE_PATH = SHARED_ELEMENT__ARCHIVE_PATH;

	/**
	 * The feature id for the '<em><b>Entry Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT__ENTRY_PATH = SHARED_ELEMENT__ENTRY_PATH;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT__FILE_NAME = SHARED_ELEMENT__FILE_NAME;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT__FOLDER = SHARED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT__ENTITY = SHARED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Shared Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT__SHARED_ENTITY = SHARED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Shared Entity Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_ENTITY_ELEMENT_FEATURE_COUNT = SHARED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedEventElementImpl <em>Shared Event Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.SharedEventElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedEventElement()
	 * @generated
	 */
	int SHARED_EVENT_ELEMENT = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__NAME = SHARED_ENTITY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__ELEMENT_TYPE = SHARED_ENTITY_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__LAZILY_CREATED = SHARED_ENTITY_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__LAST_MODIFIED = SHARED_ENTITY_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__CREATION_DATE = SHARED_ENTITY_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__ARCHIVE_PATH = SHARED_ENTITY_ELEMENT__ARCHIVE_PATH;

	/**
	 * The feature id for the '<em><b>Entry Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__ENTRY_PATH = SHARED_ENTITY_ELEMENT__ENTRY_PATH;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__FILE_NAME = SHARED_ENTITY_ELEMENT__FILE_NAME;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__FOLDER = SHARED_ENTITY_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__ENTITY = SHARED_ENTITY_ELEMENT__ENTITY;

	/**
	 * The feature id for the '<em><b>Shared Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__SHARED_ENTITY = SHARED_ENTITY_ELEMENT__SHARED_ENTITY;

	/**
	 * The feature id for the '<em><b>Expiry Action Scope Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY = SHARED_ENTITY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Shared Event Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_EVENT_ELEMENT_FEATURE_COUNT = SHARED_ENTITY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedProcessElementImpl <em>Shared Process Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.SharedProcessElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedProcessElement()
	 * @generated
	 */
	int SHARED_PROCESS_ELEMENT = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__NAME = SHARED_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__ELEMENT_TYPE = SHARED_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__LAZILY_CREATED = SHARED_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__LAST_MODIFIED = SHARED_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__CREATION_DATE = SHARED_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__ARCHIVE_PATH = SHARED_ELEMENT__ARCHIVE_PATH;

	/**
	 * The feature id for the '<em><b>Entry Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__ENTRY_PATH = SHARED_ELEMENT__ENTRY_PATH;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__FILE_NAME = SHARED_ELEMENT__FILE_NAME;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__FOLDER = SHARED_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__ENTITY = SHARED_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__PROCESS = SHARED_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Shared Process</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT__SHARED_PROCESS = SHARED_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Shared Process Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_PROCESS_ELEMENT_FEATURE_COUNT = SHARED_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.ProcessElementImpl <em>Process Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.ProcessElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getProcessElement()
	 * @generated
	 */
	int PROCESS_ELEMENT = 25;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ELEMENT__NAME = ENTITY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ELEMENT__ELEMENT_TYPE = ENTITY_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ELEMENT__LAZILY_CREATED = ENTITY_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ELEMENT__LAST_MODIFIED = ENTITY_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ELEMENT__CREATION_DATE = ENTITY_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ELEMENT__FOLDER = ENTITY_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ELEMENT__ENTITY = ENTITY_ELEMENT__ENTITY;

	/**
	 * The feature id for the '<em><b>Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ELEMENT__PROCESS = ENTITY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Process Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ELEMENT_FEATURE_COUNT = ENTITY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.IStructuredElementVisitor <em>IStructured Element Visitor</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.IStructuredElementVisitor
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getIStructuredElementVisitor()
	 * @generated
	 */
	int ISTRUCTURED_ELEMENT_VISITOR = 26;

	/**
	 * The number of structural features of the '<em>IStructured Element Visitor</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISTRUCTURED_ELEMENT_VISITOR_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.JavaElementImpl <em>Java Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.JavaElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getJavaElement()
	 * @generated
	 */
	int JAVA_ELEMENT = 27;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_ELEMENT__NAME = ENTITY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_ELEMENT__ELEMENT_TYPE = ENTITY_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_ELEMENT__LAZILY_CREATED = ENTITY_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_ELEMENT__LAST_MODIFIED = ENTITY_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_ELEMENT__CREATION_DATE = ENTITY_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_ELEMENT__FOLDER = ENTITY_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_ELEMENT__ENTITY = ENTITY_ELEMENT__ENTITY;

	/**
	 * The feature id for the '<em><b>Java Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_ELEMENT__JAVA_SOURCE = ENTITY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_ELEMENT_FEATURE_COUNT = ENTITY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedJavaElementImpl <em>Shared Java Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.SharedJavaElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedJavaElement()
	 * @generated
	 */
	int SHARED_JAVA_ELEMENT = 28;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__NAME = JAVA_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__ELEMENT_TYPE = JAVA_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__LAZILY_CREATED = JAVA_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__LAST_MODIFIED = JAVA_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__CREATION_DATE = JAVA_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__FOLDER = JAVA_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__ENTITY = JAVA_ELEMENT__ENTITY;

	/**
	 * The feature id for the '<em><b>Java Source</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__JAVA_SOURCE = JAVA_ELEMENT__JAVA_SOURCE;

	/**
	 * The feature id for the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__ARCHIVE_PATH = JAVA_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entry Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__ENTRY_PATH = JAVA_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__FILE_NAME = JAVA_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Shared Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT__SHARED_ENTITY = JAVA_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Shared Java Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_ELEMENT_FEATURE_COUNT = JAVA_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.JavaResourceElementImpl <em>Java Resource Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.JavaResourceElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getJavaResourceElement()
	 * @generated
	 */
	int JAVA_RESOURCE_ELEMENT = 29;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE_ELEMENT__NAME = ENTITY_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE_ELEMENT__ELEMENT_TYPE = ENTITY_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE_ELEMENT__LAZILY_CREATED = ENTITY_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE_ELEMENT__LAST_MODIFIED = ENTITY_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE_ELEMENT__CREATION_DATE = ENTITY_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE_ELEMENT__FOLDER = ENTITY_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE_ELEMENT__ENTITY = ENTITY_ELEMENT__ENTITY;

	/**
	 * The feature id for the '<em><b>Java Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE = ENTITY_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Java Resource Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE_ELEMENT_FEATURE_COUNT = ENTITY_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedJavaResourceElementImpl <em>Shared Java Resource Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.impl.SharedJavaResourceElementImpl
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedJavaResourceElement()
	 * @generated
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT = 30;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__NAME = JAVA_RESOURCE_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__ELEMENT_TYPE = JAVA_RESOURCE_ELEMENT__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__LAZILY_CREATED = JAVA_RESOURCE_ELEMENT__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__LAST_MODIFIED = JAVA_RESOURCE_ELEMENT__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__CREATION_DATE = JAVA_RESOURCE_ELEMENT__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__FOLDER = JAVA_RESOURCE_ELEMENT__FOLDER;

	/**
	 * The feature id for the '<em><b>Entity</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__ENTITY = JAVA_RESOURCE_ELEMENT__ENTITY;

	/**
	 * The feature id for the '<em><b>Java Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE = JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE;

	/**
	 * The feature id for the '<em><b>Archive Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__ARCHIVE_PATH = JAVA_RESOURCE_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Entry Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__ENTRY_PATH = JAVA_RESOURCE_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>File Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__FILE_NAME = JAVA_RESOURCE_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Shared Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT__SHARED_ENTITY = JAVA_RESOURCE_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Shared Java Resource Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SHARED_JAVA_RESOURCE_ELEMENT_FEATURE_COUNT = JAVA_RESOURCE_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_VARIABLE_DEF__NAME = GLOBAL_VARIABLE_DEF__NAME;

	/**
	 * The feature id for the '<em><b>Element Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_VARIABLE_DEF__ELEMENT_TYPE = GLOBAL_VARIABLE_DEF__ELEMENT_TYPE;

	/**
	 * The feature id for the '<em><b>Lazily Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_VARIABLE_DEF__LAZILY_CREATED = GLOBAL_VARIABLE_DEF__LAZILY_CREATED;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_VARIABLE_DEF__LAST_MODIFIED = GLOBAL_VARIABLE_DEF__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Creation Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_VARIABLE_DEF__CREATION_DATE = GLOBAL_VARIABLE_DEF__CREATION_DATE;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_VARIABLE_DEF__OFFSET = GLOBAL_VARIABLE_DEF__OFFSET;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_VARIABLE_DEF__LENGTH = GLOBAL_VARIABLE_DEF__LENGTH;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_VARIABLE_DEF__TYPE = GLOBAL_VARIABLE_DEF__TYPE;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_VARIABLE_DEF__ARRAY = GLOBAL_VARIABLE_DEF__ARRAY;

	/**
	 * The number of structural features of the '<em>Binding Variable Def</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINDING_VARIABLE_DEF_FEATURE_COUNT = GLOBAL_VARIABLE_DEF_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.ELEMENT_TYPES <em>ELEMENT TYPES</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.ELEMENT_TYPES
	 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getELEMENT_TYPES()
	 * @generated
	 */
	int ELEMENT_TYPES = 32;

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.DesignerProject <em>Designer Project</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Designer Project</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject
	 * @generated
	 */
	EClass getDesignerProject();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getEntityElements <em>Entity Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Entity Elements</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject#getEntityElements()
	 * @see #getDesignerProject()
	 * @generated
	 */
	EReference getDesignerProject_EntityElements();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getDecisionTableElements <em>Decision Table Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Decision Table Elements</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject#getDecisionTableElements()
	 * @see #getDesignerProject()
	 * @generated
	 */
	EReference getDesignerProject_DecisionTableElements();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getArchiveElements <em>Archive Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Archive Elements</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject#getArchiveElements()
	 * @see #getDesignerProject()
	 * @generated
	 */
	EReference getDesignerProject_ArchiveElements();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getRuleElements <em>Rule Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Rule Elements</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject#getRuleElements()
	 * @see #getDesignerProject()
	 * @generated
	 */
	EReference getDesignerProject_RuleElements();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getRootPath <em>Root Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Root Path</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject#getRootPath()
	 * @see #getDesignerProject()
	 * @generated
	 */
	EAttribute getDesignerProject_RootPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getLastPersisted <em>Last Persisted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Persisted</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject#getLastPersisted()
	 * @see #getDesignerProject()
	 * @generated
	 */
	EAttribute getDesignerProject_LastPersisted();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getReferencedProjects <em>Referenced Projects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Referenced Projects</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject#getReferencedProjects()
	 * @see #getDesignerProject()
	 * @generated
	 */
	EReference getDesignerProject_ReferencedProjects();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getDriverManager <em>Driver Manager</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Driver Manager</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject#getDriverManager()
	 * @see #getDesignerProject()
	 * @generated
	 */
	EReference getDesignerProject_DriverManager();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getDomainInstanceElements <em>Domain Instance Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Domain Instance Elements</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject#getDomainInstanceElements()
	 * @see #getDesignerProject()
	 * @generated
	 */
	EReference getDesignerProject_DomainInstanceElements();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getArchivePath <em>Archive Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Archive Path</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject#getArchivePath()
	 * @see #getDesignerProject()
	 * @generated
	 */
	EAttribute getDesignerProject_ArchivePath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.DesignerProject#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject#getVersion()
	 * @see #getDesignerProject()
	 * @generated
	 */
	EAttribute getDesignerProject_Version();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.StructuredElement <em>Structured Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Structured Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.StructuredElement
	 * @generated
	 */
	EClass getStructuredElement();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.DesignerElement <em>Designer Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Designer Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerElement
	 * @generated
	 */
	EClass getDesignerElement();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.DesignerElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerElement#getName()
	 * @see #getDesignerElement()
	 * @generated
	 */
	EAttribute getDesignerElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.DesignerElement#getElementType <em>Element Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element Type</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerElement#getElementType()
	 * @see #getDesignerElement()
	 * @generated
	 */
	EAttribute getDesignerElement_ElementType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.DesignerElement#isLazilyCreated <em>Lazily Created</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Lazily Created</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerElement#isLazilyCreated()
	 * @see #getDesignerElement()
	 * @generated
	 */
	EAttribute getDesignerElement_LazilyCreated();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.DesignerElement#getLastModified <em>Last Modified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Modified</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerElement#getLastModified()
	 * @see #getDesignerElement()
	 * @generated
	 */
	EAttribute getDesignerElement_LastModified();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.DesignerElement#getCreationDate <em>Creation Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Creation Date</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DesignerElement#getCreationDate()
	 * @see #getDesignerElement()
	 * @generated
	 */
	EAttribute getDesignerElement_CreationDate();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.MemberElement <em>Member Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Member Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.MemberElement
	 * @generated
	 */
	EClass getMemberElement();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.MemberElement#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see com.tibco.cep.studio.core.index.model.MemberElement#getOffset()
	 * @see #getMemberElement()
	 * @generated
	 */
	EAttribute getMemberElement_Offset();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.MemberElement#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see com.tibco.cep.studio.core.index.model.MemberElement#getLength()
	 * @see #getMemberElement()
	 * @generated
	 */
	EAttribute getMemberElement_Length();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.ElementReference <em>Element Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Reference</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementReference
	 * @generated
	 */
	EClass getElementReference();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.ElementReference#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementReference#getName()
	 * @see #getElementReference()
	 * @generated
	 */
	EAttribute getElementReference_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.ElementReference#isAttRef <em>Att Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Att Ref</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementReference#isAttRef()
	 * @see #getElementReference()
	 * @generated
	 */
	EAttribute getElementReference_AttRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.ElementReference#isTypeRef <em>Type Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type Ref</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementReference#isTypeRef()
	 * @see #getElementReference()
	 * @generated
	 */
	EAttribute getElementReference_TypeRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.ElementReference#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementReference#getOffset()
	 * @see #getElementReference()
	 * @generated
	 */
	EAttribute getElementReference_Offset();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.ElementReference#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementReference#getLength()
	 * @see #getElementReference()
	 * @generated
	 */
	EAttribute getElementReference_Length();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.ElementReference#getQualifier <em>Qualifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Qualifier</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementReference#getQualifier()
	 * @see #getElementReference()
	 * @generated
	 */
	EReference getElementReference_Qualifier();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.ElementReference#isArray <em>Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementReference#isArray()
	 * @see #getElementReference()
	 * @generated
	 */
	EAttribute getElementReference_Array();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.ElementReference#isMethod <em>Method</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Method</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementReference#isMethod()
	 * @see #getElementReference()
	 * @generated
	 */
	EAttribute getElementReference_Method();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.ElementReference#getBinding <em>Binding</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Binding</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementReference#getBinding()
	 * @see #getElementReference()
	 * @generated
	 */
	EAttribute getElementReference_Binding();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.TypeElement <em>Type Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.TypeElement
	 * @generated
	 */
	EClass getTypeElement();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.TypeElement#getFolder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Folder</em>'.
	 * @see com.tibco.cep.studio.core.index.model.TypeElement#getFolder()
	 * @see #getTypeElement()
	 * @generated
	 */
	EAttribute getTypeElement_Folder();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.EntityElement <em>Entity Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.EntityElement
	 * @generated
	 */
	EClass getEntityElement();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.studio.core.index.model.EntityElement#getEntity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entity</em>'.
	 * @see com.tibco.cep.studio.core.index.model.EntityElement#getEntity()
	 * @see #getEntityElement()
	 * @generated
	 */
	EReference getEntityElement_Entity();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.StateMachineElement <em>State Machine Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State Machine Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.StateMachineElement
	 * @generated
	 */
	EClass getStateMachineElement();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.index.model.StateMachineElement#getCompilableScopes <em>Compilable Scopes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Compilable Scopes</em>'.
	 * @see com.tibco.cep.studio.core.index.model.StateMachineElement#getCompilableScopes()
	 * @see #getStateMachineElement()
	 * @generated
	 */
	EReference getStateMachineElement_CompilableScopes();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.StateMachineElement#getIndexName <em>Index Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index Name</em>'.
	 * @see com.tibco.cep.studio.core.index.model.StateMachineElement#getIndexName()
	 * @see #getStateMachineElement()
	 * @generated
	 */
	EAttribute getStateMachineElement_IndexName();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.EventElement <em>Event Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Event Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.EventElement
	 * @generated
	 */
	EClass getEventElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.EventElement#getExpiryActionScopeEntry <em>Expiry Action Scope Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expiry Action Scope Entry</em>'.
	 * @see com.tibco.cep.studio.core.index.model.EventElement#getExpiryActionScopeEntry()
	 * @see #getEventElement()
	 * @generated
	 */
	EReference getEventElement_ExpiryActionScopeEntry();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.DecisionTableElement <em>Decision Table Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Decision Table Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DecisionTableElement
	 * @generated
	 */
	EClass getDecisionTableElement();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.studio.core.index.model.DecisionTableElement#getImplementation <em>Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Implementation</em>'.
	 * @see com.tibco.cep.studio.core.index.model.DecisionTableElement#getImplementation()
	 * @see #getDecisionTableElement()
	 * @generated
	 */
	EReference getDecisionTableElement_Implementation();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.ArchiveElement <em>Archive Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Archive Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ArchiveElement
	 * @generated
	 */
	EClass getArchiveElement();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.studio.core.index.model.ArchiveElement#getArchive <em>Archive</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Archive</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ArchiveElement#getArchive()
	 * @see #getArchiveElement()
	 * @generated
	 */
	EReference getArchiveElement_Archive();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.RuleElement <em>Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.RuleElement
	 * @generated
	 */
	EClass getRuleElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.RuleElement#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule</em>'.
	 * @see com.tibco.cep.studio.core.index.model.RuleElement#getRule()
	 * @see #getRuleElement()
	 * @generated
	 */
	EReference getRuleElement_Rule();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.RuleElement#isVirtual <em>Virtual</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Virtual</em>'.
	 * @see com.tibco.cep.studio.core.index.model.RuleElement#isVirtual()
	 * @see #getRuleElement()
	 * @generated
	 */
	EAttribute getRuleElement_Virtual();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.RuleElement#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Scope</em>'.
	 * @see com.tibco.cep.studio.core.index.model.RuleElement#getScope()
	 * @see #getRuleElement()
	 * @generated
	 */
	EReference getRuleElement_Scope();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.index.model.RuleElement#getGlobalVariables <em>Global Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Global Variables</em>'.
	 * @see com.tibco.cep.studio.core.index.model.RuleElement#getGlobalVariables()
	 * @see #getRuleElement()
	 * @generated
	 */
	EReference getRuleElement_GlobalVariables();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.RuleElement#getIndexName <em>Index Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index Name</em>'.
	 * @see com.tibco.cep.studio.core.index.model.RuleElement#getIndexName()
	 * @see #getRuleElement()
	 * @generated
	 */
	EAttribute getRuleElement_IndexName();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.VariableDefinition <em>Variable Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Definition</em>'.
	 * @see com.tibco.cep.studio.core.index.model.VariableDefinition
	 * @generated
	 */
	EClass getVariableDefinition();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.VariableDefinition#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.studio.core.index.model.VariableDefinition#getType()
	 * @see #getVariableDefinition()
	 * @generated
	 */
	EAttribute getVariableDefinition_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.VariableDefinition#isArray <em>Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array</em>'.
	 * @see com.tibco.cep.studio.core.index.model.VariableDefinition#isArray()
	 * @see #getVariableDefinition()
	 * @generated
	 */
	EAttribute getVariableDefinition_Array();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.LocalVariableDef <em>Local Variable Def</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Local Variable Def</em>'.
	 * @see com.tibco.cep.studio.core.index.model.LocalVariableDef
	 * @generated
	 */
	EClass getLocalVariableDef();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.GlobalVariableDef <em>Global Variable Def</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Variable Def</em>'.
	 * @see com.tibco.cep.studio.core.index.model.GlobalVariableDef
	 * @generated
	 */
	EClass getGlobalVariableDef();
	
	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.BindingVariableDef <em>Binding Variable Def</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binding Variable Def</em>'.
	 * @see com.tibco.cep.studio.core.index.model.BindingVariableDef
	 * @generated
	 */
	EClass getBindingVariableDef();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.InstanceElement <em>Instance Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Instance Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.InstanceElement
	 * @generated
	 */
	EClass getInstanceElement();

	/**
	 * Returns the meta object for the reference list '{@link com.tibco.cep.studio.core.index.model.InstanceElement#getInstances <em>Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Instances</em>'.
	 * @see com.tibco.cep.studio.core.index.model.InstanceElement#getInstances()
	 * @see #getInstanceElement()
	 * @generated
	 */
	EReference getInstanceElement_Instances();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.InstanceElement#getEntityPath <em>Entity Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entity Path</em>'.
	 * @see com.tibco.cep.studio.core.index.model.InstanceElement#getEntityPath()
	 * @see #getInstanceElement()
	 * @generated
	 */
	EAttribute getInstanceElement_EntityPath();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.SharedElement <em>Shared Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedElement
	 * @generated
	 */
	EClass getSharedElement();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.SharedElement#getArchivePath <em>Archive Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Archive Path</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedElement#getArchivePath()
	 * @see #getSharedElement()
	 * @generated
	 */
	EAttribute getSharedElement_ArchivePath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.SharedElement#getEntryPath <em>Entry Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Entry Path</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedElement#getEntryPath()
	 * @see #getSharedElement()
	 * @generated
	 */
	EAttribute getSharedElement_EntryPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.SharedElement#getFileName <em>File Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Name</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedElement#getFileName()
	 * @see #getSharedElement()
	 * @generated
	 */
	EAttribute getSharedElement_FileName();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.SharedDecisionTableElement <em>Shared Decision Table Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Decision Table Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedDecisionTableElement
	 * @generated
	 */
	EClass getSharedDecisionTableElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.SharedDecisionTableElement#getSharedImplementation <em>Shared Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shared Implementation</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedDecisionTableElement#getSharedImplementation()
	 * @see #getSharedDecisionTableElement()
	 * @generated
	 */
	EReference getSharedDecisionTableElement_SharedImplementation();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.SharedRuleElement <em>Shared Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Rule Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedRuleElement
	 * @generated
	 */
	EClass getSharedRuleElement();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.SharedStateMachineElement <em>Shared State Machine Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared State Machine Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedStateMachineElement
	 * @generated
	 */
	EClass getSharedStateMachineElement();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.SharedEventElement <em>Shared Event Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Event Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedEventElement
	 * @generated
	 */
	EClass getSharedEventElement();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.SharedEntityElement <em>Shared Entity Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Entity Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedEntityElement
	 * @generated
	 */
	EClass getSharedEntityElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.SharedEntityElement#getSharedEntity <em>Shared Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Shared Entity</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedEntityElement#getSharedEntity()
	 * @see #getSharedEntityElement()
	 * @generated
	 */
	EReference getSharedEntityElement_SharedEntity();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.SharedProcessElement <em>Shared Process Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Process Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedProcessElement
	 * @generated
	 */
	EClass getSharedProcessElement();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.studio.core.index.model.SharedProcessElement#getSharedProcess <em>Shared Process</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Shared Process</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedProcessElement#getSharedProcess()
	 * @see #getSharedProcessElement()
	 * @generated
	 */
	EReference getSharedProcessElement_SharedProcess();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.ProcessElement <em>Process Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ProcessElement
	 * @generated
	 */
	EClass getProcessElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.ProcessElement#getProcess <em>Process</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Process</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ProcessElement#getProcess()
	 * @see #getProcessElement()
	 * @generated
	 */
	EReference getProcessElement_Process();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.IStructuredElementVisitor <em>IStructured Element Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IStructured Element Visitor</em>'.
	 * @see com.tibco.cep.studio.core.index.model.IStructuredElementVisitor
	 * @model instanceClass="com.tibco.cep.studio.core.index.model.IStructuredElementVisitor"
	 * @generated
	 */
	EClass getIStructuredElementVisitor();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.JavaElement <em>Java Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.JavaElement
	 * @generated
	 */
	EClass getJavaElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.JavaElement#getJavaSource <em>Java Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Java Source</em>'.
	 * @see com.tibco.cep.studio.core.index.model.JavaElement#getJavaSource()
	 * @see #getJavaElement()
	 * @generated
	 */
	EReference getJavaElement_JavaSource();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.SharedJavaElement <em>Shared Java Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Java Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedJavaElement
	 * @generated
	 */
	EClass getSharedJavaElement();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.JavaResourceElement <em>Java Resource Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Java Resource Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.JavaResourceElement
	 * @generated
	 */
	EClass getJavaResourceElement();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.studio.core.index.model.JavaResourceElement#getJavaResource <em>Java Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Java Resource</em>'.
	 * @see com.tibco.cep.studio.core.index.model.JavaResourceElement#getJavaResource()
	 * @see #getJavaResourceElement()
	 * @generated
	 */
	EReference getJavaResourceElement_JavaResource();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.SharedJavaResourceElement <em>Shared Java Resource Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Shared Java Resource Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.SharedJavaResourceElement
	 * @generated
	 */
	EClass getSharedJavaResourceElement();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.ElementContainer <em>Element Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Container</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementContainer
	 * @generated
	 */
	EClass getElementContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.studio.core.index.model.ElementContainer#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ElementContainer#getEntries()
	 * @see #getElementContainer()
	 * @generated
	 */
	EReference getElementContainer_Entries();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Folder</em>'.
	 * @see com.tibco.cep.studio.core.index.model.Folder
	 * @generated
	 */
	EClass getFolder();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.studio.core.index.model.ELEMENT_TYPES <em>ELEMENT TYPES</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>ELEMENT TYPES</em>'.
	 * @see com.tibco.cep.studio.core.index.model.ELEMENT_TYPES
	 * @generated
	 */
	EEnum getELEMENT_TYPES();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	IndexFactory getIndexFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl <em>Designer Project</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getDesignerProject()
		 * @generated
		 */
		EClass DESIGNER_PROJECT = eINSTANCE.getDesignerProject();

		/**
		 * The meta object literal for the '<em><b>Entity Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESIGNER_PROJECT__ENTITY_ELEMENTS = eINSTANCE.getDesignerProject_EntityElements();

		/**
		 * The meta object literal for the '<em><b>Decision Table Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESIGNER_PROJECT__DECISION_TABLE_ELEMENTS = eINSTANCE.getDesignerProject_DecisionTableElements();

		/**
		 * The meta object literal for the '<em><b>Archive Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESIGNER_PROJECT__ARCHIVE_ELEMENTS = eINSTANCE.getDesignerProject_ArchiveElements();

		/**
		 * The meta object literal for the '<em><b>Rule Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESIGNER_PROJECT__RULE_ELEMENTS = eINSTANCE.getDesignerProject_RuleElements();

		/**
		 * The meta object literal for the '<em><b>Root Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_PROJECT__ROOT_PATH = eINSTANCE.getDesignerProject_RootPath();

		/**
		 * The meta object literal for the '<em><b>Last Persisted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_PROJECT__LAST_PERSISTED = eINSTANCE.getDesignerProject_LastPersisted();

		/**
		 * The meta object literal for the '<em><b>Referenced Projects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESIGNER_PROJECT__REFERENCED_PROJECTS = eINSTANCE.getDesignerProject_ReferencedProjects();

		/**
		 * The meta object literal for the '<em><b>Driver Manager</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESIGNER_PROJECT__DRIVER_MANAGER = eINSTANCE.getDesignerProject_DriverManager();

		/**
		 * The meta object literal for the '<em><b>Domain Instance Elements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DESIGNER_PROJECT__DOMAIN_INSTANCE_ELEMENTS = eINSTANCE.getDesignerProject_DomainInstanceElements();

		/**
		 * The meta object literal for the '<em><b>Archive Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_PROJECT__ARCHIVE_PATH = eINSTANCE.getDesignerProject_ArchivePath();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_PROJECT__VERSION = eINSTANCE.getDesignerProject_Version();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.StructuredElement <em>Structured Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.StructuredElement
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getStructuredElement()
		 * @generated
		 */
		EClass STRUCTURED_ELEMENT = eINSTANCE.getStructuredElement();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl <em>Designer Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.DesignerElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getDesignerElement()
		 * @generated
		 */
		EClass DESIGNER_ELEMENT = eINSTANCE.getDesignerElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_ELEMENT__NAME = eINSTANCE.getDesignerElement_Name();

		/**
		 * The meta object literal for the '<em><b>Element Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_ELEMENT__ELEMENT_TYPE = eINSTANCE.getDesignerElement_ElementType();

		/**
		 * The meta object literal for the '<em><b>Lazily Created</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_ELEMENT__LAZILY_CREATED = eINSTANCE.getDesignerElement_LazilyCreated();

		/**
		 * The meta object literal for the '<em><b>Last Modified</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_ELEMENT__LAST_MODIFIED = eINSTANCE.getDesignerElement_LastModified();

		/**
		 * The meta object literal for the '<em><b>Creation Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DESIGNER_ELEMENT__CREATION_DATE = eINSTANCE.getDesignerElement_CreationDate();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.MemberElementImpl <em>Member Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.MemberElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getMemberElement()
		 * @generated
		 */
		EClass MEMBER_ELEMENT = eINSTANCE.getMemberElement();

		/**
		 * The meta object literal for the '<em><b>Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEMBER_ELEMENT__OFFSET = eINSTANCE.getMemberElement_Offset();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEMBER_ELEMENT__LENGTH = eINSTANCE.getMemberElement_Length();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl <em>Element Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.ElementReferenceImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getElementReference()
		 * @generated
		 */
		EClass ELEMENT_REFERENCE = eINSTANCE.getElementReference();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_REFERENCE__NAME = eINSTANCE.getElementReference_Name();

		/**
		 * The meta object literal for the '<em><b>Att Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_REFERENCE__ATT_REF = eINSTANCE.getElementReference_AttRef();

		/**
		 * The meta object literal for the '<em><b>Type Ref</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_REFERENCE__TYPE_REF = eINSTANCE.getElementReference_TypeRef();

		/**
		 * The meta object literal for the '<em><b>Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_REFERENCE__OFFSET = eINSTANCE.getElementReference_Offset();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_REFERENCE__LENGTH = eINSTANCE.getElementReference_Length();

		/**
		 * The meta object literal for the '<em><b>Qualifier</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_REFERENCE__QUALIFIER = eINSTANCE.getElementReference_Qualifier();

		/**
		 * The meta object literal for the '<em><b>Array</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_REFERENCE__ARRAY = eINSTANCE.getElementReference_Array();

		/**
		 * The meta object literal for the '<em><b>Method</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_REFERENCE__METHOD = eINSTANCE.getElementReference_Method();

		/**
		 * The meta object literal for the '<em><b>Binding</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_REFERENCE__BINDING = eINSTANCE.getElementReference_Binding();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.TypeElementImpl <em>Type Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.TypeElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getTypeElement()
		 * @generated
		 */
		EClass TYPE_ELEMENT = eINSTANCE.getTypeElement();

		/**
		 * The meta object literal for the '<em><b>Folder</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TYPE_ELEMENT__FOLDER = eINSTANCE.getTypeElement_Folder();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.EntityElementImpl <em>Entity Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.EntityElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getEntityElement()
		 * @generated
		 */
		EClass ENTITY_ELEMENT = eINSTANCE.getEntityElement();

		/**
		 * The meta object literal for the '<em><b>Entity</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTITY_ELEMENT__ENTITY = eINSTANCE.getEntityElement_Entity();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.StateMachineElementImpl <em>State Machine Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.StateMachineElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getStateMachineElement()
		 * @generated
		 */
		EClass STATE_MACHINE_ELEMENT = eINSTANCE.getStateMachineElement();

		/**
		 * The meta object literal for the '<em><b>Compilable Scopes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE_MACHINE_ELEMENT__COMPILABLE_SCOPES = eINSTANCE.getStateMachineElement_CompilableScopes();

		/**
		 * The meta object literal for the '<em><b>Index Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STATE_MACHINE_ELEMENT__INDEX_NAME = eINSTANCE.getStateMachineElement_IndexName();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.EventElementImpl <em>Event Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.EventElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getEventElement()
		 * @generated
		 */
		EClass EVENT_ELEMENT = eINSTANCE.getEventElement();

		/**
		 * The meta object literal for the '<em><b>Expiry Action Scope Entry</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY = eINSTANCE.getEventElement_ExpiryActionScopeEntry();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.DecisionTableElementImpl <em>Decision Table Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.DecisionTableElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getDecisionTableElement()
		 * @generated
		 */
		EClass DECISION_TABLE_ELEMENT = eINSTANCE.getDecisionTableElement();

		/**
		 * The meta object literal for the '<em><b>Implementation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DECISION_TABLE_ELEMENT__IMPLEMENTATION = eINSTANCE.getDecisionTableElement_Implementation();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.ArchiveElementImpl <em>Archive Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.ArchiveElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getArchiveElement()
		 * @generated
		 */
		EClass ARCHIVE_ELEMENT = eINSTANCE.getArchiveElement();

		/**
		 * The meta object literal for the '<em><b>Archive</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARCHIVE_ELEMENT__ARCHIVE = eINSTANCE.getArchiveElement_Archive();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.RuleElementImpl <em>Rule Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.RuleElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getRuleElement()
		 * @generated
		 */
		EClass RULE_ELEMENT = eINSTANCE.getRuleElement();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_ELEMENT__RULE = eINSTANCE.getRuleElement_Rule();

		/**
		 * The meta object literal for the '<em><b>Virtual</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_ELEMENT__VIRTUAL = eINSTANCE.getRuleElement_Virtual();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_ELEMENT__SCOPE = eINSTANCE.getRuleElement_Scope();

		/**
		 * The meta object literal for the '<em><b>Global Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_ELEMENT__GLOBAL_VARIABLES = eINSTANCE.getRuleElement_GlobalVariables();

		/**
		 * The meta object literal for the '<em><b>Index Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_ELEMENT__INDEX_NAME = eINSTANCE.getRuleElement_IndexName();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.VariableDefinitionImpl <em>Variable Definition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.VariableDefinitionImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getVariableDefinition()
		 * @generated
		 */
		EClass VARIABLE_DEFINITION = eINSTANCE.getVariableDefinition();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE_DEFINITION__TYPE = eINSTANCE.getVariableDefinition_Type();

		/**
		 * The meta object literal for the '<em><b>Array</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE_DEFINITION__ARRAY = eINSTANCE.getVariableDefinition_Array();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.LocalVariableDefImpl <em>Local Variable Def</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.LocalVariableDefImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getLocalVariableDef()
		 * @generated
		 */
		EClass LOCAL_VARIABLE_DEF = eINSTANCE.getLocalVariableDef();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.GlobalVariableDefImpl <em>Global Variable Def</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.GlobalVariableDefImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getGlobalVariableDef()
		 * @generated
		 */
		EClass GLOBAL_VARIABLE_DEF = eINSTANCE.getGlobalVariableDef();
		
		
		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.BindingVariableDefImpl <em>Binding Variable Def</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.BindingVariableDefImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getBindingVariableDef()
		 * @generated
		 */
		EClass BINDING_VARIABLE_DEF = eINSTANCE.getBindingVariableDef();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.InstanceElementImpl <em>Instance Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.InstanceElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getInstanceElement()
		 * @generated
		 */
		EClass INSTANCE_ELEMENT = eINSTANCE.getInstanceElement();

		/**
		 * The meta object literal for the '<em><b>Instances</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INSTANCE_ELEMENT__INSTANCES = eINSTANCE.getInstanceElement_Instances();

		/**
		 * The meta object literal for the '<em><b>Entity Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INSTANCE_ELEMENT__ENTITY_PATH = eINSTANCE.getInstanceElement_EntityPath();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedElementImpl <em>Shared Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.SharedElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedElement()
		 * @generated
		 */
		EClass SHARED_ELEMENT = eINSTANCE.getSharedElement();

		/**
		 * The meta object literal for the '<em><b>Archive Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHARED_ELEMENT__ARCHIVE_PATH = eINSTANCE.getSharedElement_ArchivePath();

		/**
		 * The meta object literal for the '<em><b>Entry Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHARED_ELEMENT__ENTRY_PATH = eINSTANCE.getSharedElement_EntryPath();

		/**
		 * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SHARED_ELEMENT__FILE_NAME = eINSTANCE.getSharedElement_FileName();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl <em>Shared Decision Table Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.SharedDecisionTableElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedDecisionTableElement()
		 * @generated
		 */
		EClass SHARED_DECISION_TABLE_ELEMENT = eINSTANCE.getSharedDecisionTableElement();

		/**
		 * The meta object literal for the '<em><b>Shared Implementation</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION = eINSTANCE.getSharedDecisionTableElement_SharedImplementation();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedRuleElementImpl <em>Shared Rule Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.SharedRuleElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedRuleElement()
		 * @generated
		 */
		EClass SHARED_RULE_ELEMENT = eINSTANCE.getSharedRuleElement();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedStateMachineElementImpl <em>Shared State Machine Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.SharedStateMachineElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedStateMachineElement()
		 * @generated
		 */
		EClass SHARED_STATE_MACHINE_ELEMENT = eINSTANCE.getSharedStateMachineElement();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedEventElementImpl <em>Shared Event Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.SharedEventElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedEventElement()
		 * @generated
		 */
		EClass SHARED_EVENT_ELEMENT = eINSTANCE.getSharedEventElement();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedEntityElementImpl <em>Shared Entity Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.SharedEntityElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedEntityElement()
		 * @generated
		 */
		EClass SHARED_ENTITY_ELEMENT = eINSTANCE.getSharedEntityElement();

		/**
		 * The meta object literal for the '<em><b>Shared Entity</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHARED_ENTITY_ELEMENT__SHARED_ENTITY = eINSTANCE.getSharedEntityElement_SharedEntity();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedProcessElementImpl <em>Shared Process Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.SharedProcessElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedProcessElement()
		 * @generated
		 */
		EClass SHARED_PROCESS_ELEMENT = eINSTANCE.getSharedProcessElement();

		/**
		 * The meta object literal for the '<em><b>Shared Process</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SHARED_PROCESS_ELEMENT__SHARED_PROCESS = eINSTANCE.getSharedProcessElement_SharedProcess();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.ProcessElementImpl <em>Process Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.ProcessElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getProcessElement()
		 * @generated
		 */
		EClass PROCESS_ELEMENT = eINSTANCE.getProcessElement();

		/**
		 * The meta object literal for the '<em><b>Process</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_ELEMENT__PROCESS = eINSTANCE.getProcessElement_Process();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.IStructuredElementVisitor <em>IStructured Element Visitor</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.IStructuredElementVisitor
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getIStructuredElementVisitor()
		 * @generated
		 */
		EClass ISTRUCTURED_ELEMENT_VISITOR = eINSTANCE.getIStructuredElementVisitor();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.JavaElementImpl <em>Java Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.JavaElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getJavaElement()
		 * @generated
		 */
		EClass JAVA_ELEMENT = eINSTANCE.getJavaElement();

		/**
		 * The meta object literal for the '<em><b>Java Source</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JAVA_ELEMENT__JAVA_SOURCE = eINSTANCE.getJavaElement_JavaSource();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedJavaElementImpl <em>Shared Java Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.SharedJavaElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedJavaElement()
		 * @generated
		 */
		EClass SHARED_JAVA_ELEMENT = eINSTANCE.getSharedJavaElement();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.JavaResourceElementImpl <em>Java Resource Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.JavaResourceElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getJavaResourceElement()
		 * @generated
		 */
		EClass JAVA_RESOURCE_ELEMENT = eINSTANCE.getJavaResourceElement();

		/**
		 * The meta object literal for the '<em><b>Java Resource</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE = eINSTANCE.getJavaResourceElement_JavaResource();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.SharedJavaResourceElementImpl <em>Shared Java Resource Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.SharedJavaResourceElementImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getSharedJavaResourceElement()
		 * @generated
		 */
		EClass SHARED_JAVA_RESOURCE_ELEMENT = eINSTANCE.getSharedJavaResourceElement();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.ElementContainer <em>Element Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.ElementContainer
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getElementContainer()
		 * @generated
		 */
		EClass ELEMENT_CONTAINER = eINSTANCE.getElementContainer();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_CONTAINER__ENTRIES = eINSTANCE.getElementContainer_Entries();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.impl.FolderImpl <em>Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.impl.FolderImpl
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getFolder()
		 * @generated
		 */
		EClass FOLDER = eINSTANCE.getFolder();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.ELEMENT_TYPES <em>ELEMENT TYPES</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.ELEMENT_TYPES
		 * @see com.tibco.cep.studio.core.index.model.impl.IndexPackageImpl#getELEMENT_TYPES()
		 * @generated
		 */
		EEnum ELEMENT_TYPES = eINSTANCE.getELEMENT_TYPES();

	}

} //IndexPackage
