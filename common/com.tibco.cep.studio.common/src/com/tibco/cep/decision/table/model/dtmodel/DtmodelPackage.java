/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory
 * @model kind="package"
 * @generated
 */
public interface DtmodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "dtmodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/decision/table/model/DecisionTable.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "Table";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DtmodelPackage eINSTANCE = com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentImpl <em>Argument</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getArgument()
	 * @generated
	 */
	int ARGUMENT = 0;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT__PROPERTY = 0;

	/**
	 * The feature id for the '<em><b>Direction</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT__DIRECTION = 1;

	/**
	 * The number of structural features of the '<em>Argument</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.MetadatableImpl <em>Metadatable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.MetadatableImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getMetadatable()
	 * @generated
	 */
	int METADATABLE = 11;

	/**
	 * The feature id for the '<em><b>Md</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATABLE__MD = 0;

	/**
	 * The number of structural features of the '<em>Metadatable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATABLE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleSetImpl <em>Table Rule Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleSetImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getTableRuleSet()
	 * @generated
	 */
	int TABLE_RULE_SET = 1;

	/**
	 * The feature id for the '<em><b>Md</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_SET__MD = METADATABLE__MD;

	/**
	 * The feature id for the '<em><b>Rule</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_SET__RULE = METADATABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Columns</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_SET__COLUMNS = METADATABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Table Rule Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_SET_FEATURE_COUNT = METADATABLE_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleImpl <em>Table Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getTableRule()
	 * @generated
	 */
	int TABLE_RULE = 2;

	/**
	 * The feature id for the '<em><b>Md</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE__MD = METADATABLE__MD;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE__ID = METADATABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE__COMMENT = METADATABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Cond</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE__COND = METADATABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Act</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE__ACT = METADATABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Cond</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @ordered
	 */
	int TABLE_RULE__CONDITION = 3;

	/**
	 * The feature id for the '<em><b>Act</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * @ordered
	 */
	int TABLE_RULE__ACTION = 4;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE__MODIFIED = METADATABLE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Table Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_FEATURE_COUNT = METADATABLE_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentPropertyImpl <em>Argument Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentPropertyImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getArgumentProperty()
	 * @generated
	 */
	int ARGUMENT_PROPERTY = 3;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_PROPERTY__PATH = 0;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_PROPERTY__ALIAS = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_PROPERTY__TYPE = 2;

	/**
	 * The feature id for the '<em><b>Graphical Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_PROPERTY__GRAPHICAL_PATH = 3;

	/**
	 * The feature id for the '<em><b>Resource Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_PROPERTY__RESOURCE_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Domain Overridden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_PROPERTY__DOMAIN_OVERRIDDEN = 5;

	/**
	 * The feature id for the '<em><b>Array</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_PROPERTY__ARRAY = 6;

	/**
	 * The number of structural features of the '<em>Argument Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ARGUMENT_PROPERTY_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.ExpressionImpl <em>Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.ExpressionImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 4;

	/**
	 * The feature id for the '<em><b>Var String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__VAR_STRING = 0;

	/**
	 * The number of structural features of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl <em>Table</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getTable()
	 * @generated
	 */
	int TABLE = 5;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__MODIFIED = OntologyPackage.IMPLEMENTATION__MODIFIED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__NAME = OntologyPackage.IMPLEMENTATION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__DESCRIPTION = OntologyPackage.IMPLEMENTATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__FOLDER = OntologyPackage.IMPLEMENTATION__FOLDER;

	/**
	 * The feature id for the '<em><b>Style</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__STYLE = OntologyPackage.IMPLEMENTATION__STYLE;

	/**
	 * The feature id for the '<em><b>Implements</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__IMPLEMENTS = OntologyPackage.IMPLEMENTATION__IMPLEMENTS;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__VERSION = OntologyPackage.IMPLEMENTATION__VERSION;

	/**
	 * The feature id for the '<em><b>Last Modified By</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__LAST_MODIFIED_BY = OntologyPackage.IMPLEMENTATION__LAST_MODIFIED_BY;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__LAST_MODIFIED = OntologyPackage.IMPLEMENTATION__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>Dirty</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__DIRTY = OntologyPackage.IMPLEMENTATION__DIRTY;

	/**
	 * The feature id for the '<em><b>Locked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__LOCKED = OntologyPackage.IMPLEMENTATION__LOCKED;

	/**
	 * The feature id for the '<em><b>Md</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__MD = OntologyPackage.IMPLEMENTATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Decision Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__DECISION_TABLE = OntologyPackage.IMPLEMENTATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Exception Table</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__EXCEPTION_TABLE = OntologyPackage.IMPLEMENTATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Argument</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__ARGUMENT = OntologyPackage.IMPLEMENTATION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Since</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE__SINCE = OntologyPackage.IMPLEMENTATION_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Table</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_FEATURE_COUNT = OntologyPackage.IMPLEMENTATION_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.MetaDataImpl <em>Meta Data</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.MetaDataImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getMetaData()
	 * @generated
	 */
	int META_DATA = 6;

	/**
	 * The feature id for the '<em><b>Prop</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_DATA__PROP = 0;

	/**
	 * The number of structural features of the '<em>Meta Data</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int META_DATA_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl <em>Table Rule Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getTableRuleVariable()
	 * @generated
	 */
	int TABLE_RULE_VARIABLE = 7;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_VARIABLE__MODIFIED = OntologyPackage.ACCESS_CONTROL_CANDIDATE__MODIFIED;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_VARIABLE__COMMENT = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_VARIABLE__ENABLED = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Metat Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_VARIABLE__METAT_DATA = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_VARIABLE__ID = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Col Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_VARIABLE__COL_ID = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Expr</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_VARIABLE__EXPR = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Column Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_VARIABLE__COLUMN_NAME = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_VARIABLE__PATH = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Display Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_VARIABLE__DISPLAY_VALUE = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Table Rule Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TABLE_RULE_VARIABLE_FEATURE_COUNT = OntologyPackage.ACCESS_CONTROL_CANDIDATE_FEATURE_COUNT + 9;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.PropertyImpl <em>Property</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.PropertyImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getProperty()
	 * @generated
	 */
	int PROPERTY = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Property</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnsImpl <em>Columns</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.ColumnsImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getColumns()
	 * @generated
	 */
	int COLUMNS = 9;

	/**
	 * The feature id for the '<em><b>Column</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMNS__COLUMN = 0;

	/**
	 * The number of structural features of the '<em>Columns</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMNS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl <em>Column</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getColumn()
	 * @generated
	 */
	int COLUMN = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN__ID = 0;

	/**
	 * The feature id for the '<em><b>Substitution</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN__SUBSTITUTION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN__NAME = 2;

	/**
	 * The feature id for the '<em><b>Property Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN__PROPERTY_PATH = 3;

	/**
	 * The feature id for the '<em><b>Property Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN__PROPERTY_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Column Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN__COLUMN_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Property Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN__PROPERTY_REF = 6;

	/**
	 * The feature id for the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN__ALIAS = 7;

	/**
	 * The feature id for the '<em><b>Array Property</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN__ARRAY_PROPERTY = 8;

	/**
	 * The feature id for the '<em><b>Default Cell Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN__DEFAULT_CELL_TEXT = 9;

	/**
	 * The number of structural features of the '<em>Column</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLUMN_FEATURE_COUNT = 10;

	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.ResourceType <em>Resource Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.ResourceType
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getResourceType()
	 * @generated
	 */
	int RESOURCE_TYPE = 12;


	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.ColumnType <em>Column Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.ColumnType
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getColumnType()
	 * @generated
	 */
	int COLUMN_TYPE = 13;


	/**
	 * The meta object id for the '{@link com.tibco.cep.decision.table.model.dtmodel.PropertyType <em>Property Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.decision.table.model.dtmodel.PropertyType
	 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getPropertyType()
	 * @generated
	 */
	int PROPERTY_TYPE = 14;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.Argument <em>Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Argument</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Argument
	 * @generated
	 */
	EClass getArgument();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decision.table.model.dtmodel.Argument#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Argument#getProperty()
	 * @see #getArgument()
	 * @generated
	 */
	EReference getArgument_Property();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Argument#getDirection <em>Direction</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Direction</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Argument#getDirection()
	 * @see #getArgument()
	 * @generated
	 */
	EAttribute getArgument_Direction();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleSet <em>Table Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table Rule Set</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleSet
	 * @generated
	 */
	EClass getTableRuleSet();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleSet#getRule <em>Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rule</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleSet#getRule()
	 * @see #getTableRuleSet()
	 * @generated
	 */
	EReference getTableRuleSet_Rule();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleSet#getColumns <em>Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Columns</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleSet#getColumns()
	 * @see #getTableRuleSet()
	 * @generated
	 */
	EReference getTableRuleSet_Columns();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.TableRule <em>Table Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table Rule</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRule
	 * @generated
	 */
	EClass getTableRule();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#isModified <em>Modified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Modified</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRule#isModified()
	 * @see #getTableRule()
	 * @generated
	 */
	EAttribute getTableRule_Modified();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRule#getId()
	 * @see #getTableRule()
	 * @generated
	 */
	EAttribute getTableRule_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRule#getComment()
	 * @see #getTableRule()
	 * @generated
	 */
	EAttribute getTableRule_Comment();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#getCond <em>Cond</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cond</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRule#getCond()
	 * @see #getTableRule()
	 * @generated
	 */
	EReference getTableRule_Cond();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decision.table.model.dtmodel.TableRule#getAct <em>Act</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Act</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRule#getAct()
	 * @see #getTableRule()
	 * @generated
	 */
	EReference getTableRule_Act();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty <em>Argument Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Argument Property</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty
	 * @generated
	 */
	EClass getArgumentProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getPath()
	 * @see #getArgumentProperty()
	 * @generated
	 */
	EAttribute getArgumentProperty_Path();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getAlias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alias</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getAlias()
	 * @see #getArgumentProperty()
	 * @generated
	 */
	EAttribute getArgumentProperty_Alias();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getType()
	 * @see #getArgumentProperty()
	 * @generated
	 */
	EAttribute getArgumentProperty_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getGraphicalPath <em>Graphical Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Graphical Path</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getGraphicalPath()
	 * @see #getArgumentProperty()
	 * @generated
	 */
	EAttribute getArgumentProperty_GraphicalPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getResourceType <em>Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Resource Type</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getResourceType()
	 * @see #getArgumentProperty()
	 * @generated
	 */
	EAttribute getArgumentProperty_ResourceType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#isDomainOverridden <em>Domain Overridden</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Domain Overridden</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#isDomainOverridden()
	 * @see #getArgumentProperty()
	 * @generated
	 */
	EAttribute getArgumentProperty_DomainOverridden();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#isArray <em>Array</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#isArray()
	 * @see #getArgumentProperty()
	 * @generated
	 */
	EAttribute getArgumentProperty_Array();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Expression#getVarString <em>Var String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Var String</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Expression#getVarString()
	 * @see #getExpression()
	 * @generated
	 */
	EAttribute getExpression_VarString();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.Table <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Table
	 * @generated
	 */
	EClass getTable();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decision.table.model.dtmodel.Table#getDecisionTable <em>Decision Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Decision Table</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Table#getDecisionTable()
	 * @see #getTable()
	 * @generated
	 */
	EReference getTable_DecisionTable();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decision.table.model.dtmodel.Table#getExceptionTable <em>Exception Table</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exception Table</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Table#getExceptionTable()
	 * @see #getTable()
	 * @generated
	 */
	EReference getTable_ExceptionTable();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decision.table.model.dtmodel.Table#getArgument <em>Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Argument</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Table#getArgument()
	 * @see #getTable()
	 * @generated
	 */
	EReference getTable_Argument();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Table#getSince <em>Since</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Since</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Table#getSince()
	 * @see #getTable()
	 * @generated
	 */
	EAttribute getTable_Since();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.MetaData <em>Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Meta Data</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.MetaData
	 * @generated
	 */
	EClass getMetaData();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decision.table.model.dtmodel.MetaData#getProp <em>Prop</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Prop</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.MetaData#getProp()
	 * @see #getMetaData()
	 * @generated
	 */
	EReference getMetaData_Prop();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable <em>Table Rule Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Table Rule Variable</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable
	 * @generated
	 */
	EClass getTableRuleVariable();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#isEnabled()
	 * @see #getTableRuleVariable()
	 * @generated
	 */
	EAttribute getTableRuleVariable_Enabled();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getMetatData <em>Metat Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Metat Data</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getMetatData()
	 * @see #getTableRuleVariable()
	 * @generated
	 */
	EReference getTableRuleVariable_MetatData();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getId()
	 * @see #getTableRuleVariable()
	 * @generated
	 */
	EAttribute getTableRuleVariable_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getColId <em>Col Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Col Id</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getColId()
	 * @see #getTableRuleVariable()
	 * @generated
	 */
	EAttribute getTableRuleVariable_ColId();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getExpr <em>Expr</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expr</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getExpr()
	 * @see #getTableRuleVariable()
	 * @generated
	 */
	EAttribute getTableRuleVariable_Expr();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getColumnName <em>Column Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column Name</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getColumnName()
	 * @see #getTableRuleVariable()
	 * @generated
	 */
	EAttribute getTableRuleVariable_ColumnName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getPath <em>Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Path</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getPath()
	 * @see #getTableRuleVariable()
	 * @generated
	 */
	EAttribute getTableRuleVariable_Path();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getDisplayValue <em>Display Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Display Value</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getDisplayValue()
	 * @see #getTableRuleVariable()
	 * @generated
	 */
	EAttribute getTableRuleVariable_DisplayValue();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable#getComment()
	 * @see #getTableRuleVariable()
	 * @generated
	 */
	EAttribute getTableRuleVariable_Comment();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Property
	 * @generated
	 */
	EClass getProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Property#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Property#getName()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Property#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Property#getType()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Property#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Property#getValue()
	 * @see #getProperty()
	 * @generated
	 */
	EAttribute getProperty_Value();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.Columns <em>Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Columns</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Columns
	 * @generated
	 */
	EClass getColumns();

	/**
	 * Returns the meta object for the containment reference list '{@link com.tibco.cep.decision.table.model.dtmodel.Columns#getColumn <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Column</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Columns#getColumn()
	 * @see #getColumns()
	 * @generated
	 */
	EReference getColumns_Column();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.Column <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Column</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column
	 * @generated
	 */
	EClass getColumn();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column#getId()
	 * @see #getColumn()
	 * @generated
	 */
	EAttribute getColumn_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Column#isSubstitution <em>Substitution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Substitution</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column#isSubstitution()
	 * @see #getColumn()
	 * @generated
	 */
	EAttribute getColumn_Substitution();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column#getName()
	 * @see #getColumn()
	 * @generated
	 */
	EAttribute getColumn_Name();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyPath <em>Property Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Path</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyPath()
	 * @see #getColumn()
	 * @generated
	 */
	EAttribute getColumn_PropertyPath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Type</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyType()
	 * @see #getColumn()
	 * @generated
	 */
	EAttribute getColumn_PropertyType();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getColumnType <em>Column Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Column Type</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column#getColumnType()
	 * @see #getColumn()
	 * @generated
	 */
	EAttribute getColumn_ColumnType();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyRef <em>Property Ref</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Property Ref</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyRef()
	 * @see #getColumn()
	 * @generated
	 */
	EReference getColumn_PropertyRef();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getAlias <em>Alias</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Alias</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column#getAlias()
	 * @see #getColumn()
	 * @generated
	 */
	EAttribute getColumn_Alias();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Column#isArrayProperty <em>Array Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Array Property</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column#isArrayProperty()
	 * @see #getColumn()
	 * @generated
	 */
	EAttribute getColumn_ArrayProperty();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getDefaultCellText <em>Default Cell Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default Cell Text</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column#getDefaultCellText()
	 * @see #getColumn()
	 * @generated
	 */
	EAttribute getColumn_DefaultCellText();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.decision.table.model.dtmodel.Metadatable <em>Metadatable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metadatable</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Metadatable
	 * @generated
	 */
	EClass getMetadatable();

	/**
	 * Returns the meta object for the containment reference '{@link com.tibco.cep.decision.table.model.dtmodel.Metadatable#getMd <em>Md</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Md</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Metadatable#getMd()
	 * @see #getMetadatable()
	 * @generated
	 */
	EReference getMetadatable_Md();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.decision.table.model.dtmodel.ResourceType <em>Resource Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Resource Type</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ResourceType
	 * @generated
	 */
	EEnum getResourceType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.decision.table.model.dtmodel.ColumnType <em>Column Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Column Type</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ColumnType
	 * @generated
	 */
	EEnum getColumnType();

	/**
	 * Returns the meta object for enum '{@link com.tibco.cep.decision.table.model.dtmodel.PropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Property Type</em>'.
	 * @see com.tibco.cep.decision.table.model.dtmodel.PropertyType
	 * @generated
	 */
	EEnum getPropertyType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DtmodelFactory getDtmodelFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentImpl <em>Argument</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getArgument()
		 * @generated
		 */
		EClass ARGUMENT = eINSTANCE.getArgument();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ARGUMENT__PROPERTY = eINSTANCE.getArgument_Property();

		/**
		 * The meta object literal for the '<em><b>Direction</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARGUMENT__DIRECTION = eINSTANCE.getArgument_Direction();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleSetImpl <em>Table Rule Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleSetImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getTableRuleSet()
		 * @generated
		 */
		EClass TABLE_RULE_SET = eINSTANCE.getTableRuleSet();

		/**
		 * The meta object literal for the '<em><b>Rule</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE_RULE_SET__RULE = eINSTANCE.getTableRuleSet_Rule();

		/**
		 * The meta object literal for the '<em><b>Columns</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE_RULE_SET__COLUMNS = eINSTANCE.getTableRuleSet_Columns();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleImpl <em>Table Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getTableRule()
		 * @generated
		 */
		EClass TABLE_RULE = eINSTANCE.getTableRule();

		/**
		 * The meta object literal for the '<em><b>Modified</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_RULE__MODIFIED = eINSTANCE.getTableRule_Modified();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_RULE__ID = eINSTANCE.getTableRule_Id();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_RULE__COMMENT = eINSTANCE.getTableRule_Comment();

		/**
		 * The meta object literal for the '<em><b>Cond</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE_RULE__COND = eINSTANCE.getTableRule_Cond();

		/**
		 * The meta object literal for the '<em><b>Act</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE_RULE__ACT = eINSTANCE.getTableRule_Act();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentPropertyImpl <em>Argument Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.ArgumentPropertyImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getArgumentProperty()
		 * @generated
		 */
		EClass ARGUMENT_PROPERTY = eINSTANCE.getArgumentProperty();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARGUMENT_PROPERTY__PATH = eINSTANCE.getArgumentProperty_Path();

		/**
		 * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARGUMENT_PROPERTY__ALIAS = eINSTANCE.getArgumentProperty_Alias();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARGUMENT_PROPERTY__TYPE = eINSTANCE.getArgumentProperty_Type();

		/**
		 * The meta object literal for the '<em><b>Graphical Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARGUMENT_PROPERTY__GRAPHICAL_PATH = eINSTANCE.getArgumentProperty_GraphicalPath();

		/**
		 * The meta object literal for the '<em><b>Resource Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARGUMENT_PROPERTY__RESOURCE_TYPE = eINSTANCE.getArgumentProperty_ResourceType();

		/**
		 * The meta object literal for the '<em><b>Domain Overridden</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARGUMENT_PROPERTY__DOMAIN_OVERRIDDEN = eINSTANCE.getArgumentProperty_DomainOverridden();

		/**
		 * The meta object literal for the '<em><b>Array</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ARGUMENT_PROPERTY__ARRAY = eINSTANCE.getArgumentProperty_Array();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.ExpressionImpl <em>Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.ExpressionImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '<em><b>Var String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPRESSION__VAR_STRING = eINSTANCE.getExpression_VarString();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl <em>Table</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getTable()
		 * @generated
		 */
		EClass TABLE = eINSTANCE.getTable();

		/**
		 * The meta object literal for the '<em><b>Decision Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE__DECISION_TABLE = eINSTANCE.getTable_DecisionTable();

		/**
		 * The meta object literal for the '<em><b>Exception Table</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE__EXCEPTION_TABLE = eINSTANCE.getTable_ExceptionTable();

		/**
		 * The meta object literal for the '<em><b>Argument</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE__ARGUMENT = eINSTANCE.getTable_Argument();

		/**
		 * The meta object literal for the '<em><b>Since</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE__SINCE = eINSTANCE.getTable_Since();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.MetaDataImpl <em>Meta Data</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.MetaDataImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getMetaData()
		 * @generated
		 */
		EClass META_DATA = eINSTANCE.getMetaData();

		/**
		 * The meta object literal for the '<em><b>Prop</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference META_DATA__PROP = eINSTANCE.getMetaData_Prop();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl <em>Table Rule Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.TableRuleVariableImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getTableRuleVariable()
		 * @generated
		 */
		EClass TABLE_RULE_VARIABLE = eINSTANCE.getTableRuleVariable();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_RULE_VARIABLE__ENABLED = eINSTANCE.getTableRuleVariable_Enabled();

		/**
		 * The meta object literal for the '<em><b>Metat Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TABLE_RULE_VARIABLE__METAT_DATA = eINSTANCE.getTableRuleVariable_MetatData();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_RULE_VARIABLE__ID = eINSTANCE.getTableRuleVariable_Id();

		/**
		 * The meta object literal for the '<em><b>Col Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_RULE_VARIABLE__COL_ID = eINSTANCE.getTableRuleVariable_ColId();

		/**
		 * The meta object literal for the '<em><b>Expr</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_RULE_VARIABLE__EXPR = eINSTANCE.getTableRuleVariable_Expr();

		/**
		 * The meta object literal for the '<em><b>Column Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_RULE_VARIABLE__COLUMN_NAME = eINSTANCE.getTableRuleVariable_ColumnName();

		/**
		 * The meta object literal for the '<em><b>Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_RULE_VARIABLE__PATH = eINSTANCE.getTableRuleVariable_Path();

		/**
		 * The meta object literal for the '<em><b>Display Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_RULE_VARIABLE__DISPLAY_VALUE = eINSTANCE.getTableRuleVariable_DisplayValue();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TABLE_RULE_VARIABLE__COMMENT = eINSTANCE.getTableRuleVariable_Comment();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.PropertyImpl <em>Property</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.PropertyImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getProperty()
		 * @generated
		 */
		EClass PROPERTY = eINSTANCE.getProperty();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__NAME = eINSTANCE.getProperty_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__TYPE = eINSTANCE.getProperty_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY__VALUE = eINSTANCE.getProperty_Value();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnsImpl <em>Columns</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.ColumnsImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getColumns()
		 * @generated
		 */
		EClass COLUMNS = eINSTANCE.getColumns();

		/**
		 * The meta object literal for the '<em><b>Column</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLUMNS__COLUMN = eINSTANCE.getColumns_Column();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl <em>Column</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.ColumnImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getColumn()
		 * @generated
		 */
		EClass COLUMN = eINSTANCE.getColumn();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN__ID = eINSTANCE.getColumn_Id();

		/**
		 * The meta object literal for the '<em><b>Substitution</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN__SUBSTITUTION = eINSTANCE.getColumn_Substitution();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN__NAME = eINSTANCE.getColumn_Name();

		/**
		 * The meta object literal for the '<em><b>Property Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN__PROPERTY_PATH = eINSTANCE.getColumn_PropertyPath();

		/**
		 * The meta object literal for the '<em><b>Property Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN__PROPERTY_TYPE = eINSTANCE.getColumn_PropertyType();

		/**
		 * The meta object literal for the '<em><b>Column Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN__COLUMN_TYPE = eINSTANCE.getColumn_ColumnType();

		/**
		 * The meta object literal for the '<em><b>Property Ref</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLUMN__PROPERTY_REF = eINSTANCE.getColumn_PropertyRef();

		/**
		 * The meta object literal for the '<em><b>Alias</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN__ALIAS = eINSTANCE.getColumn_Alias();

		/**
		 * The meta object literal for the '<em><b>Array Property</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN__ARRAY_PROPERTY = eINSTANCE.getColumn_ArrayProperty();

		/**
		 * The meta object literal for the '<em><b>Default Cell Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLUMN__DEFAULT_CELL_TEXT = eINSTANCE.getColumn_DefaultCellText();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.impl.MetadatableImpl <em>Metadatable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.MetadatableImpl
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getMetadatable()
		 * @generated
		 */
		EClass METADATABLE = eINSTANCE.getMetadatable();

		/**
		 * The meta object literal for the '<em><b>Md</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METADATABLE__MD = eINSTANCE.getMetadatable_Md();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.ResourceType <em>Resource Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.ResourceType
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getResourceType()
		 * @generated
		 */
		EEnum RESOURCE_TYPE = eINSTANCE.getResourceType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.ColumnType <em>Column Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.ColumnType
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getColumnType()
		 * @generated
		 */
		EEnum COLUMN_TYPE = eINSTANCE.getColumnType();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.decision.table.model.dtmodel.PropertyType <em>Property Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.decision.table.model.dtmodel.PropertyType
		 * @see com.tibco.cep.decision.table.model.dtmodel.impl.DtmodelPackageImpl#getPropertyType()
		 * @generated
		 */
		EEnum PROPERTY_TYPE = eINSTANCE.getPropertyType();

	}

} //DtmodelPackage
