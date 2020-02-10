/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.scope.impl;

import com.tibco.cep.studio.core.index.model.scope.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.studio.core.index.model.scope.CompilableScope;
import com.tibco.cep.studio.core.index.model.scope.CompilableScopeEntry;
import com.tibco.cep.studio.core.index.model.scope.RootScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopeBlock;
import com.tibco.cep.studio.core.index.model.scope.ScopeFactory;
import com.tibco.cep.studio.core.index.model.scope.ScopePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ScopeFactoryImpl extends EFactoryImpl implements ScopeFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScopeFactory init() {
		try {
			ScopeFactory theScopeFactory = (ScopeFactory)EPackage.Registry.INSTANCE.getEFactory(ScopePackage.eNS_URI);
			if (theScopeFactory != null) {
				return theScopeFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ScopeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopeFactoryImpl() {
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
			case ScopePackage.SCOPE_BLOCK: return createScopeBlock();
			case ScopePackage.ROOT_SCOPE_BLOCK: return createRootScopeBlock();
			case ScopePackage.COMPILABLE_SCOPE: return createCompilableScope();
			case ScopePackage.COMPILABLE_SCOPE_ENTRY: return createCompilableScopeEntry();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopeBlock createScopeBlock() {
		ScopeBlockImpl scopeBlock = new ScopeBlockImpl();
		return scopeBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RootScopeBlock createRootScopeBlock() {
		RootScopeBlockImpl rootScopeBlock = new RootScopeBlockImpl();
		return rootScopeBlock;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompilableScope createCompilableScope() {
		CompilableScopeImpl compilableScope = new CompilableScopeImpl();
		return compilableScope;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompilableScopeEntry createCompilableScopeEntry() {
		CompilableScopeEntryImpl compilableScopeEntry = new CompilableScopeEntryImpl();
		return compilableScopeEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScopePackage getScopePackage() {
		return (ScopePackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ScopePackage getPackage() {
		return ScopePackage.eINSTANCE;
	}

} //ScopeFactoryImpl
