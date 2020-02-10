/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.util;

import com.tibco.cep.decision.table.model.dtmodel.*;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty;
import com.tibco.cep.decision.table.model.dtmodel.Column;
import com.tibco.cep.decision.table.model.dtmodel.Columns;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.Expression;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Metadatable;
import com.tibco.cep.decision.table.model.dtmodel.Property;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRule;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable;
import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.AccessControlCandidate;
import com.tibco.cep.decisionproject.ontology.Implementation;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage
 * @generated
 */
public class DtmodelAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static DtmodelPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DtmodelAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = DtmodelPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DtmodelSwitch<Adapter> modelSwitch =
		new DtmodelSwitch<Adapter>() {
			@Override
			public Adapter caseArgument(Argument object) {
				return createArgumentAdapter();
			}
			@Override
			public Adapter caseTableRuleSet(TableRuleSet object) {
				return createTableRuleSetAdapter();
			}
			@Override
			public Adapter caseTableRule(TableRule object) {
				return createTableRuleAdapter();
			}
			@Override
			public Adapter caseArgumentProperty(ArgumentProperty object) {
				return createArgumentPropertyAdapter();
			}
			@Override
			public Adapter caseExpression(Expression object) {
				return createExpressionAdapter();
			}
			@Override
			public Adapter caseTable(Table object) {
				return createTableAdapter();
			}
			@Override
			public Adapter caseMetaData(MetaData object) {
				return createMetaDataAdapter();
			}
			@Override
			public Adapter caseTableRuleVariable(TableRuleVariable object) {
				return createTableRuleVariableAdapter();
			}
			@Override
			public Adapter caseProperty(Property object) {
				return createPropertyAdapter();
			}
			@Override
			public Adapter caseColumns(Columns object) {
				return createColumnsAdapter();
			}
			@Override
			public Adapter caseColumn(Column object) {
				return createColumnAdapter();
			}
			@Override
			public Adapter caseMetadatable(Metadatable object) {
				return createMetadatableAdapter();
			}
			@Override
			public Adapter caseAccessControlCandidate(AccessControlCandidate object) {
				return createAccessControlCandidateAdapter();
			}
			@Override
			public Adapter caseAbstractResource(AbstractResource object) {
				return createAbstractResourceAdapter();
			}
			@Override
			public Adapter caseImplementation(Implementation object) {
				return createImplementationAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.Argument <em>Argument</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Argument
	 * @generated
	 */
	public Adapter createArgumentAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleSet <em>Table Rule Set</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleSet
	 * @generated
	 */
	public Adapter createTableRuleSetAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.TableRule <em>Table Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRule
	 * @generated
	 */
	public Adapter createTableRuleAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty <em>Argument Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty
	 * @generated
	 */
	public Adapter createArgumentPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Expression
	 * @generated
	 */
	public Adapter createExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.Table <em>Table</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Table
	 * @generated
	 */
	public Adapter createTableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.MetaData <em>Meta Data</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.MetaData
	 * @generated
	 */
	public Adapter createMetaDataAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable <em>Table Rule Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.TableRuleVariable
	 * @generated
	 */
	public Adapter createTableRuleVariableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.Property <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Property
	 * @generated
	 */
	public Adapter createPropertyAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.Columns <em>Columns</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Columns
	 * @generated
	 */
	public Adapter createColumnsAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.Column <em>Column</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Column
	 * @generated
	 */
	public Adapter createColumnAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decision.table.model.dtmodel.Metadatable <em>Metadatable</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decision.table.model.dtmodel.Metadatable
	 * @generated
	 */
	public Adapter createMetadatableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.AccessControlCandidate <em>Access Control Candidate</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.AccessControlCandidate
	 * @generated
	 */
	public Adapter createAccessControlCandidateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.AbstractResource <em>Abstract Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.AbstractResource
	 * @generated
	 */
	public Adapter createAbstractResourceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.decisionproject.ontology.Implementation <em>Implementation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.decisionproject.ontology.Implementation
	 * @generated
	 */
	public Adapter createImplementationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //DtmodelAdapterFactory
