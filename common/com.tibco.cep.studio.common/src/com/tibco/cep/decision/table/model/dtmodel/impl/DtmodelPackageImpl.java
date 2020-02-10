/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.ColumnType;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelFactory;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.Expression;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Metadatable;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.PropertyType;
import com.tibco.cep.decision.table.model.dtmodel.ResourceType;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.impl.OntologyPackageImpl;
import com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage;
import com.tibco.cep.decisionprojectmodel.impl.DecisionProjectModelPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class DtmodelPackageImpl extends EPackageImpl implements DtmodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argumentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tableRuleSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tableRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass argumentPropertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metaDataEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass tableRuleVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass columnsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass columnEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metadatableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum resourceTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum columnTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum propertyTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private DtmodelPackageImpl() {
		super(eNS_URI, DtmodelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated NOT
	 */
	public static DtmodelPackage init() {
		if (isInited) return (DtmodelPackage)EPackage.Registry.INSTANCE.getEPackage(DtmodelPackage.eNS_URI);

		// Obtain or create and register package
		DtmodelPackageImpl theDtmodelPackage = (DtmodelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof DtmodelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new DtmodelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		DecisionProjectModelPackageImpl theDecisionProjectModelPackage = (DecisionProjectModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DecisionProjectModelPackage.eNS_URI) instanceof DecisionProjectModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DecisionProjectModelPackage.eNS_URI) : DecisionProjectModelPackage.eINSTANCE);
		OntologyPackageImpl theOntologyPackage = (OntologyPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OntologyPackage.eNS_URI) instanceof OntologyPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OntologyPackage.eNS_URI) : OntologyPackage.eINSTANCE);
		//DomainModelPackageImpl theDomainModelPackage = (DomainModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DomainModelPackage.eNS_URI) instanceof DomainModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DomainModelPackage.eNS_URI) : DomainModelPackage.eINSTANCE);

		// Create package meta-data objects
		theDtmodelPackage.createPackageContents();
		theDecisionProjectModelPackage.createPackageContents();
		theOntologyPackage.createPackageContents();
		//theDomainModelPackage.createPackageContents();

		// Initialize created meta-data
		theDtmodelPackage.initializePackageContents();
		theDecisionProjectModelPackage.initializePackageContents();
		theOntologyPackage.initializePackageContents();
		//theDomainModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theDtmodelPackage.freeze();

		return theDtmodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArgument() {
		return argumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArgument_Property() {
		return (EReference)argumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArgument_Direction() {
		return (EAttribute)argumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTableRuleSet() {
		return tableRuleSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTableRuleSet_Rule() {
		return (EReference)tableRuleSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTableRuleSet_Columns() {
		return (EReference)tableRuleSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTableRule() {
		return tableRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTableRule_Id() {
		return (EAttribute)tableRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTableRule_Comment() {
		return (EAttribute)tableRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTableRule_Cond() {
		return (EReference)tableRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTableRule_Act() {
		return (EReference)tableRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EReference getTableRule_Condition() {
		return (EReference)tableRuleEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EReference getTableRule_Action() {
		return (EReference)tableRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTableRule_Modified() {
		return (EAttribute)tableRuleEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArgumentProperty() {
		return argumentPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArgumentProperty_Path() {
		return (EAttribute)argumentPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArgumentProperty_Alias() {
		return (EAttribute)argumentPropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArgumentProperty_Type() {
		return (EAttribute)argumentPropertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArgumentProperty_GraphicalPath() {
		return (EAttribute)argumentPropertyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArgumentProperty_ResourceType() {
		return (EAttribute)argumentPropertyEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArgumentProperty_DomainOverridden() {
		return (EAttribute)argumentPropertyEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getArgumentProperty_Array() {
		return (EAttribute)argumentPropertyEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpression() {
		return expressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpression_VarString() {
		return (EAttribute)expressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTable() {
		return tableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTable_DecisionTable() {
		return (EReference)tableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTable_ExceptionTable() {
		return (EReference)tableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTable_Argument() {
		return (EReference)tableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTable_Since() {
		return (EAttribute)tableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetaData() {
		return metaDataEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetaData_Prop() {
		return (EReference)metaDataEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTableRuleVariable() {
		return tableRuleVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTableRuleVariable_Enabled() {
		return (EAttribute)tableRuleVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTableRuleVariable_MetatData() {
		return (EReference)tableRuleVariableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTableRuleVariable_Id() {
		return (EAttribute)tableRuleVariableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTableRuleVariable_ColId() {
		return (EAttribute)tableRuleVariableEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTableRuleVariable_Expr() {
		return (EAttribute)tableRuleVariableEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTableRuleVariable_ColumnName() {
		return (EAttribute)tableRuleVariableEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTableRuleVariable_Path() {
		return (EAttribute)tableRuleVariableEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTableRuleVariable_DisplayValue() {
		return (EAttribute)tableRuleVariableEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTableRuleVariable_Comment() {
		return (EAttribute)tableRuleVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProperty() {
		return propertyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Name() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Type() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProperty_Value() {
		return (EAttribute)propertyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getColumns() {
		return columnsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getColumns_Column() {
		return (EReference)columnsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getColumn() {
		return columnEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColumn_Id() {
		return (EAttribute)columnEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColumn_Substitution() {
		return (EAttribute)columnEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColumn_Name() {
		return (EAttribute)columnEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColumn_PropertyPath() {
		return (EAttribute)columnEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColumn_PropertyType() {
		return (EAttribute)columnEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColumn_ColumnType() {
		return (EAttribute)columnEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getColumn_PropertyRef() {
		return (EReference)columnEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColumn_Alias() {
		return (EAttribute)columnEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColumn_ArrayProperty() {
		return (EAttribute)columnEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColumn_DefaultCellText() {
		return (EAttribute)columnEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetadatable() {
		return metadatableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetadatable_Md() {
		return (EReference)metadatableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getResourceType() {
		return resourceTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getColumnType() {
		return columnTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getPropertyType() {
		return propertyTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DtmodelFactory getDtmodelFactory() {
		return (DtmodelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		argumentEClass = createEClass(ARGUMENT);
		createEReference(argumentEClass, ARGUMENT__PROPERTY);
		createEAttribute(argumentEClass, ARGUMENT__DIRECTION);

		tableRuleSetEClass = createEClass(TABLE_RULE_SET);
		createEReference(tableRuleSetEClass, TABLE_RULE_SET__RULE);
		createEReference(tableRuleSetEClass, TABLE_RULE_SET__COLUMNS);

		tableRuleEClass = createEClass(TABLE_RULE);
		createEAttribute(tableRuleEClass, TABLE_RULE__ID);
		createEAttribute(tableRuleEClass, TABLE_RULE__COMMENT);
		createEReference(tableRuleEClass, TABLE_RULE__COND);
		createEReference(tableRuleEClass, TABLE_RULE__ACT);
		createEAttribute(tableRuleEClass, TABLE_RULE__MODIFIED);

		argumentPropertyEClass = createEClass(ARGUMENT_PROPERTY);
		createEAttribute(argumentPropertyEClass, ARGUMENT_PROPERTY__PATH);
		createEAttribute(argumentPropertyEClass, ARGUMENT_PROPERTY__ALIAS);
		createEAttribute(argumentPropertyEClass, ARGUMENT_PROPERTY__TYPE);
		createEAttribute(argumentPropertyEClass, ARGUMENT_PROPERTY__GRAPHICAL_PATH);
		createEAttribute(argumentPropertyEClass, ARGUMENT_PROPERTY__RESOURCE_TYPE);
		createEAttribute(argumentPropertyEClass, ARGUMENT_PROPERTY__DOMAIN_OVERRIDDEN);
		createEAttribute(argumentPropertyEClass, ARGUMENT_PROPERTY__ARRAY);

		expressionEClass = createEClass(EXPRESSION);
		createEAttribute(expressionEClass, EXPRESSION__VAR_STRING);

		tableEClass = createEClass(TABLE);
		createEReference(tableEClass, TABLE__DECISION_TABLE);
		createEReference(tableEClass, TABLE__EXCEPTION_TABLE);
		createEReference(tableEClass, TABLE__ARGUMENT);
		createEAttribute(tableEClass, TABLE__SINCE);

		metaDataEClass = createEClass(META_DATA);
		createEReference(metaDataEClass, META_DATA__PROP);

		tableRuleVariableEClass = createEClass(TABLE_RULE_VARIABLE);
		createEAttribute(tableRuleVariableEClass, TABLE_RULE_VARIABLE__COMMENT);
		createEAttribute(tableRuleVariableEClass, TABLE_RULE_VARIABLE__ENABLED);
		createEReference(tableRuleVariableEClass, TABLE_RULE_VARIABLE__METAT_DATA);
		createEAttribute(tableRuleVariableEClass, TABLE_RULE_VARIABLE__ID);
		createEAttribute(tableRuleVariableEClass, TABLE_RULE_VARIABLE__COL_ID);
		createEAttribute(tableRuleVariableEClass, TABLE_RULE_VARIABLE__EXPR);
		createEAttribute(tableRuleVariableEClass, TABLE_RULE_VARIABLE__COLUMN_NAME);
		createEAttribute(tableRuleVariableEClass, TABLE_RULE_VARIABLE__PATH);
		createEAttribute(tableRuleVariableEClass, TABLE_RULE_VARIABLE__DISPLAY_VALUE);

		propertyEClass = createEClass(PROPERTY);
		createEAttribute(propertyEClass, PROPERTY__NAME);
		createEAttribute(propertyEClass, PROPERTY__TYPE);
		createEAttribute(propertyEClass, PROPERTY__VALUE);

		columnsEClass = createEClass(COLUMNS);
		createEReference(columnsEClass, COLUMNS__COLUMN);

		columnEClass = createEClass(COLUMN);
		createEAttribute(columnEClass, COLUMN__ID);
		createEAttribute(columnEClass, COLUMN__SUBSTITUTION);
		createEAttribute(columnEClass, COLUMN__NAME);
		createEAttribute(columnEClass, COLUMN__PROPERTY_PATH);
		createEAttribute(columnEClass, COLUMN__PROPERTY_TYPE);
		createEAttribute(columnEClass, COLUMN__COLUMN_TYPE);
		createEReference(columnEClass, COLUMN__PROPERTY_REF);
		createEAttribute(columnEClass, COLUMN__ALIAS);
		createEAttribute(columnEClass, COLUMN__ARRAY_PROPERTY);
		createEAttribute(columnEClass, COLUMN__DEFAULT_CELL_TEXT);

		metadatableEClass = createEClass(METADATABLE);
		createEReference(metadatableEClass, METADATABLE__MD);

		// Create enums
		resourceTypeEEnum = createEEnum(RESOURCE_TYPE);
		columnTypeEEnum = createEEnum(COLUMN_TYPE);
		propertyTypeEEnum = createEEnum(PROPERTY_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		OntologyPackage theOntologyPackage = (OntologyPackage)EPackage.Registry.INSTANCE.getEPackage(OntologyPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tableRuleSetEClass.getESuperTypes().add(this.getMetadatable());
		tableRuleEClass.getESuperTypes().add(this.getMetadatable());
		tableEClass.getESuperTypes().add(theOntologyPackage.getImplementation());
		tableEClass.getESuperTypes().add(this.getMetadatable());
		tableRuleVariableEClass.getESuperTypes().add(theOntologyPackage.getAccessControlCandidate());

		// Initialize classes and features; add operations and parameters
		initEClass(argumentEClass, Argument.class, "Argument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArgument_Property(), this.getArgumentProperty(), null, "property", null, 0, 1, Argument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArgument_Direction(), ecorePackage.getEString(), "direction", null, 0, 1, Argument.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tableRuleSetEClass, TableRuleSet.class, "TableRuleSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTableRuleSet_Rule(), this.getTableRule(), null, "rule", null, 0, -1, TableRuleSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTableRuleSet_Columns(), this.getColumns(), null, "columns", null, 0, 1, TableRuleSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tableRuleEClass, TableRule.class, "TableRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTableRule_Id(), theEcorePackage.getEString(), "id", null, 0, 1, TableRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableRule_Comment(), theEcorePackage.getEString(), "comment", null, 0, 1, TableRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTableRule_Cond(), this.getTableRuleVariable(), null, "cond", null, 0, -1, TableRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTableRule_Act(), this.getTableRuleVariable(), null, "act", null, 0, -1, TableRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableRule_Modified(), theEcorePackage.getEBoolean(), "modified", null, 0, 1, TableRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(argumentPropertyEClass, ArgumentProperty.class, "ArgumentProperty", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getArgumentProperty_Path(), ecorePackage.getEString(), "path", null, 0, 1, ArgumentProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArgumentProperty_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, ArgumentProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArgumentProperty_Type(), ecorePackage.getEString(), "type", null, 0, 1, ArgumentProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArgumentProperty_GraphicalPath(), ecorePackage.getEString(), "graphicalPath", null, 0, 1, ArgumentProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArgumentProperty_ResourceType(), this.getResourceType(), "resourceType", "UNDEFINED", 0, 1, ArgumentProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArgumentProperty_DomainOverridden(), theEcorePackage.getEBoolean(), "domainOverridden", null, 0, 1, ArgumentProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getArgumentProperty_Array(), ecorePackage.getEBoolean(), "array", "false", 0, 1, ArgumentProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expressionEClass, Expression.class, "Expression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExpression_VarString(), theEcorePackage.getEString(), "varString", null, 0, 1, Expression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tableEClass, Table.class, "Table", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTable_DecisionTable(), this.getTableRuleSet(), null, "decisionTable", null, 0, 1, Table.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTable_ExceptionTable(), this.getTableRuleSet(), null, "exceptionTable", null, 0, 1, Table.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTable_Argument(), this.getArgument(), null, "argument", null, 0, -1, Table.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTable_Since(), ecorePackage.getEString(), "since", null, 0, 1, Table.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metaDataEClass, MetaData.class, "MetaData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetaData_Prop(), this.getProperty(), null, "prop", null, 0, -1, MetaData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(tableRuleVariableEClass, TableRuleVariable.class, "TableRuleVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTableRuleVariable_Comment(), theEcorePackage.getEString(), "comment", null, 0, 1, TableRuleVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableRuleVariable_Enabled(), theEcorePackage.getEBoolean(), "enabled", "true", 0, 1, TableRuleVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTableRuleVariable_MetatData(), this.getMetaData(), null, "metatData", null, 0, 1, TableRuleVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableRuleVariable_Id(), theEcorePackage.getEString(), "id", null, 0, 1, TableRuleVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableRuleVariable_ColId(), theEcorePackage.getEString(), "colId", null, 0, 1, TableRuleVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableRuleVariable_Expr(), ecorePackage.getEString(), "expr", null, 0, 1, TableRuleVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableRuleVariable_ColumnName(), theEcorePackage.getEString(), "columnName", null, 0, 1, TableRuleVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableRuleVariable_Path(), theEcorePackage.getEString(), "path", null, 0, 1, TableRuleVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTableRuleVariable_DisplayValue(), theEcorePackage.getEString(), "displayValue", "", 0, 1, TableRuleVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyEClass, Property.class, "Property", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProperty_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Type(), theEcorePackage.getEString(), "type", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProperty_Value(), theEcorePackage.getEString(), "value", null, 0, 1, Property.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(columnsEClass, Columns.class, "Columns", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getColumns_Column(), this.getColumn(), null, "column", null, 0, -1, Columns.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(columnsEClass, ecorePackage.getEBoolean(), "add", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getColumn(), "column", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(columnsEClass, ecorePackage.getEBoolean(), "add", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "index", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getColumn(), "column", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(columnsEClass, this.getColumn(), "move", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "index", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getColumn(), "column", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(columnsEClass, this.getColumn(), "move", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "newIndex", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "oldIndex", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(columnEClass, Column.class, "Column", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getColumn_Id(), theEcorePackage.getEString(), "id", null, 1, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Substitution(), theEcorePackage.getEBoolean(), "substitution", null, 1, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Name(), theEcorePackage.getEString(), "name", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_PropertyPath(), theEcorePackage.getEString(), "propertyPath", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_PropertyType(), theEcorePackage.getEInt(), "propertyType", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_ColumnType(), this.getColumnType(), "columnType", null, 1, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getColumn_PropertyRef(), theOntologyPackage.getProperty(), null, "propertyRef", null, 0, 1, Column.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_Alias(), ecorePackage.getEString(), "alias", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_ArrayProperty(), theEcorePackage.getEBoolean(), "arrayProperty", "", 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColumn_DefaultCellText(), ecorePackage.getEString(), "defaultCellText", null, 0, 1, Column.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metadatableEClass, Metadatable.class, "Metadatable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetadatable_Md(), this.getMetaData(), null, "md", null, 0, 1, Metadatable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(resourceTypeEEnum, ResourceType.class, "ResourceType");
		addEEnumLiteral(resourceTypeEEnum, ResourceType.UNDEFINED);
		addEEnumLiteral(resourceTypeEEnum, ResourceType.CONCEPT);
		addEEnumLiteral(resourceTypeEEnum, ResourceType.RULESET);
		addEEnumLiteral(resourceTypeEEnum, ResourceType.RULE);
		addEEnumLiteral(resourceTypeEEnum, ResourceType.EVENT);
		addEEnumLiteral(resourceTypeEEnum, ResourceType.DECISION_TABLE);
		addEEnumLiteral(resourceTypeEEnum, ResourceType.PROPERTY);
		addEEnumLiteral(resourceTypeEEnum, ResourceType.ATTRIBUTE);
		addEEnumLiteral(resourceTypeEEnum, ResourceType.RULE_FUNCTION);
		addEEnumLiteral(resourceTypeEEnum, ResourceType.FOLDER);

		initEEnum(columnTypeEEnum, ColumnType.class, "ColumnType");
		addEEnumLiteral(columnTypeEEnum, ColumnType.UNDEFINED);
		addEEnumLiteral(columnTypeEEnum, ColumnType.CONDITION);
		addEEnumLiteral(columnTypeEEnum, ColumnType.ACTION);
		addEEnumLiteral(columnTypeEEnum, ColumnType.CUSTOM_CONDITION);
		addEEnumLiteral(columnTypeEEnum, ColumnType.CUSTOM_ACTION);

		initEEnum(propertyTypeEEnum, PropertyType.class, "PropertyType");
		addEEnumLiteral(propertyTypeEEnum, PropertyType.UNDEFINED);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.PROPERTY_TYPE_REAL);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.PROPERTY_TYPE_STRING);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.PROPERTY_TYPE_INTEGER);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.PROPERTY_TYPE_BOOLEAN);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.PROPERTY_TYPE_LONG);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.PROPERTY_TYPE_DATETIME);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.PROPERTY_TYPE_CONCEPT);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.PROPERTY_TYPE_CONCEPTREFERENCE);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.PROPERTY_TYPE_TUPLE);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.PROPERTY_TYPE_BLOB);
		addEEnumLiteral(propertyTypeEEnum, PropertyType.PROPERTY_TYPE_LIST);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";	
		addAnnotation
		  (expressionEClass, 
		   source, 
		   new String[] {
			 "name", "Expression",
			 "kind", "simple"
		   });
	}

} //DtmodelPackageImpl
