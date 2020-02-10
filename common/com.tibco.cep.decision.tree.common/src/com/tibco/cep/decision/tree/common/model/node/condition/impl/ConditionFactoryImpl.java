/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.tree.common.model.node.condition.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.decision.tree.common.model.node.condition.BoolCond;
import com.tibco.cep.decision.tree.common.model.node.condition.Case;
import com.tibco.cep.decision.tree.common.model.node.condition.CaseGroup;
import com.tibco.cep.decision.tree.common.model.node.condition.Cond;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionFactory;
import com.tibco.cep.decision.tree.common.model.node.condition.ConditionPackage;
import com.tibco.cep.decision.tree.common.model.node.condition.SwitchCond;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ConditionFactoryImpl extends EFactoryImpl implements ConditionFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ConditionFactory init() {
		try {
			ConditionFactory theConditionFactory = (ConditionFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/decision/tree/model/node/condition"); 
			if (theConditionFactory != null) {
				return theConditionFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ConditionFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionFactoryImpl() {
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
			case ConditionPackage.COND: return createCond();
			case ConditionPackage.BOOL_COND: return createBoolCond();
			case ConditionPackage.SWITCH_COND: return createSwitchCond();
			case ConditionPackage.CASE: return createCase();
			case ConditionPackage.CASE_GROUP: return createCaseGroup();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Cond createCond() {
		CondImpl cond = new CondImpl();
		return cond;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BoolCond createBoolCond() {
		BoolCondImpl boolCond = new BoolCondImpl();
		return boolCond;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SwitchCond createSwitchCond() {
		SwitchCondImpl switchCond = new SwitchCondImpl();
		return switchCond;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Case createCase() {
		CaseImpl case_ = new CaseImpl();
		return case_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CaseGroup createCaseGroup() {
		CaseGroupImpl caseGroup = new CaseGroupImpl();
		return caseGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionPackage getConditionPackage() {
		return (ConditionPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ConditionPackage getPackage() {
		return ConditionPackage.eINSTANCE;
	}

} //ConditionFactoryImpl
