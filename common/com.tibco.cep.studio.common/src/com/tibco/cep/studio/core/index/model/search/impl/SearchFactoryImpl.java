/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search.impl;

import com.tibco.cep.studio.core.index.model.search.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.studio.core.index.model.search.ElementMatch;
import com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch;
import com.tibco.cep.studio.core.index.model.search.NonEntityMatch;
import com.tibco.cep.studio.core.index.model.search.RuleSourceMatch;
import com.tibco.cep.studio.core.index.model.search.SearchFactory;
import com.tibco.cep.studio.core.index.model.search.SearchPackage;
import com.tibco.cep.studio.core.index.model.search.StringLiteralMatch;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SearchFactoryImpl extends EFactoryImpl implements SearchFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SearchFactory init() {
		try {
			SearchFactory theSearchFactory = (SearchFactory)EPackage.Registry.INSTANCE.getEFactory(SearchPackage.eNS_URI);
			if (theSearchFactory != null) {
				return theSearchFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SearchFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SearchFactoryImpl() {
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
			case SearchPackage.ELEMENT_MATCH: return createElementMatch();
			case SearchPackage.METHOD_ARGUMENT_MATCH: return createMethodArgumentMatch();
			case SearchPackage.STRING_LITERAL_MATCH: return createStringLiteralMatch();
			case SearchPackage.NON_ENTITY_MATCH: return createNonEntityMatch();
			case SearchPackage.RULE_SOURCE_MATCH: return createRuleSourceMatch();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementMatch createElementMatch() {
		ElementMatchImpl elementMatch = new ElementMatchImpl();
		return elementMatch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MethodArgumentMatch createMethodArgumentMatch() {
		MethodArgumentMatchImpl methodArgumentMatch = new MethodArgumentMatchImpl();
		return methodArgumentMatch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringLiteralMatch createStringLiteralMatch() {
		StringLiteralMatchImpl stringLiteralMatch = new StringLiteralMatchImpl();
		return stringLiteralMatch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NonEntityMatch createNonEntityMatch() {
		NonEntityMatchImpl nonEntityMatch = new NonEntityMatchImpl();
		return nonEntityMatch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleSourceMatch createRuleSourceMatch() {
		RuleSourceMatchImpl ruleSourceMatch = new RuleSourceMatchImpl();
		return ruleSourceMatch;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SearchPackage getSearchPackage() {
		return (SearchPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SearchPackage getPackage() {
		return SearchPackage.eINSTANCE;
	}

} //SearchFactoryImpl
