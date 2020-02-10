/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.search;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.tibco.cep.studio.core.index.model.search.SearchFactory
 * @model kind="package"
 * @generated
 */
public interface SearchPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "search";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designer/index/core/model/search/ontology_index.ecore";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "search";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SearchPackage eINSTANCE = com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.search.impl.ElementMatchImpl <em>Element Match</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.search.impl.ElementMatchImpl
	 * @see com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl#getElementMatch()
	 * @generated
	 */
	int ELEMENT_MATCH = 0;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_MATCH__FEATURE = 0;

	/**
	 * The feature id for the '<em><b>Matched Element</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_MATCH__MATCHED_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_MATCH__LABEL = 2;

	/**
	 * The number of structural features of the '<em>Element Match</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_MATCH_FEATURE_COUNT = 3;


	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.search.impl.RuleSourceMatchImpl <em>Rule Source Match</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.search.impl.RuleSourceMatchImpl
	 * @see com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl#getRuleSourceMatch()
	 * @generated
	 */
	int RULE_SOURCE_MATCH = 4;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SOURCE_MATCH__OFFSET = 0;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SOURCE_MATCH__LENGTH = 1;

	/**
	 * The feature id for the '<em><b>Containing Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SOURCE_MATCH__CONTAINING_RULE = 2;

	/**
	 * The number of structural features of the '<em>Rule Source Match</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_SOURCE_MATCH_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.search.impl.MethodArgumentMatchImpl <em>Method Argument Match</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.search.impl.MethodArgumentMatchImpl
	 * @see com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl#getMethodArgumentMatch()
	 * @generated
	 */
	int METHOD_ARGUMENT_MATCH = 1;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_ARGUMENT_MATCH__OFFSET = RULE_SOURCE_MATCH__OFFSET;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_ARGUMENT_MATCH__LENGTH = RULE_SOURCE_MATCH__LENGTH;

	/**
	 * The feature id for the '<em><b>Containing Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_ARGUMENT_MATCH__CONTAINING_RULE = RULE_SOURCE_MATCH__CONTAINING_RULE;

	/**
	 * The feature id for the '<em><b>Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_ARGUMENT_MATCH__FUNCTION = RULE_SOURCE_MATCH_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Arg Node</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_ARGUMENT_MATCH__ARG_NODE = RULE_SOURCE_MATCH_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Method Argument Match</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METHOD_ARGUMENT_MATCH_FEATURE_COUNT = RULE_SOURCE_MATCH_FEATURE_COUNT + 2;


	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.search.impl.StringLiteralMatchImpl <em>String Literal Match</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.search.impl.StringLiteralMatchImpl
	 * @see com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl#getStringLiteralMatch()
	 * @generated
	 */
	int STRING_LITERAL_MATCH = 2;

	/**
	 * The feature id for the '<em><b>Offset</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_MATCH__OFFSET = RULE_SOURCE_MATCH__OFFSET;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_MATCH__LENGTH = RULE_SOURCE_MATCH__LENGTH;

	/**
	 * The feature id for the '<em><b>Containing Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_MATCH__CONTAINING_RULE = RULE_SOURCE_MATCH__CONTAINING_RULE;

	/**
	 * The number of structural features of the '<em>String Literal Match</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_LITERAL_MATCH_FEATURE_COUNT = RULE_SOURCE_MATCH_FEATURE_COUNT + 0;


	/**
	 * The meta object id for the '{@link com.tibco.cep.studio.core.index.model.search.impl.NonEntityMatchImpl <em>Non Entity Match</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.studio.core.index.model.search.impl.NonEntityMatchImpl
	 * @see com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl#getNonEntityMatch()
	 * @generated
	 */
	int NON_ENTITY_MATCH = 3;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_ENTITY_MATCH__PROJECT_NAME = 0;

	/**
	 * The feature id for the '<em><b>File Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_ENTITY_MATCH__FILE_PATH = 1;

	/**
	 * The feature id for the '<em><b>Match</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_ENTITY_MATCH__MATCH = 2;

	/**
	 * The number of structural features of the '<em>Non Entity Match</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NON_ENTITY_MATCH_FEATURE_COUNT = 3;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.search.ElementMatch <em>Element Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Match</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.ElementMatch
	 * @generated
	 */
	EClass getElementMatch();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.studio.core.index.model.search.ElementMatch#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Feature</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.ElementMatch#getFeature()
	 * @see #getElementMatch()
	 * @generated
	 */
	EReference getElementMatch_Feature();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.studio.core.index.model.search.ElementMatch#getMatchedElement <em>Matched Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Matched Element</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.ElementMatch#getMatchedElement()
	 * @see #getElementMatch()
	 * @generated
	 */
	EReference getElementMatch_MatchedElement();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.search.ElementMatch#getLabel <em>Label</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Label</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.ElementMatch#getLabel()
	 * @see #getElementMatch()
	 * @generated
	 */
	EAttribute getElementMatch_Label();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch <em>Method Argument Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Method Argument Match</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch
	 * @generated
	 */
	EClass getMethodArgumentMatch();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Function</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch#getFunction()
	 * @see #getMethodArgumentMatch()
	 * @generated
	 */
	EAttribute getMethodArgumentMatch_Function();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch#getArgNode <em>Arg Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Arg Node</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.MethodArgumentMatch#getArgNode()
	 * @see #getMethodArgumentMatch()
	 * @generated
	 */
	EAttribute getMethodArgumentMatch_ArgNode();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.search.StringLiteralMatch <em>String Literal Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Literal Match</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.StringLiteralMatch
	 * @generated
	 */
	EClass getStringLiteralMatch();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.search.NonEntityMatch <em>Non Entity Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Non Entity Match</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.NonEntityMatch
	 * @generated
	 */
	EClass getNonEntityMatch();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getProjectName <em>Project Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Name</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getProjectName()
	 * @see #getNonEntityMatch()
	 * @generated
	 */
	EAttribute getNonEntityMatch_ProjectName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getFilePath <em>File Path</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>File Path</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getFilePath()
	 * @see #getNonEntityMatch()
	 * @generated
	 */
	EAttribute getNonEntityMatch_FilePath();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getMatch <em>Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Match</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.NonEntityMatch#getMatch()
	 * @see #getNonEntityMatch()
	 * @generated
	 */
	EAttribute getNonEntityMatch_Match();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.studio.core.index.model.search.RuleSourceMatch <em>Rule Source Match</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Source Match</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.RuleSourceMatch
	 * @generated
	 */
	EClass getRuleSourceMatch();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getOffset <em>Offset</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Offset</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getOffset()
	 * @see #getRuleSourceMatch()
	 * @generated
	 */
	EAttribute getRuleSourceMatch_Offset();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getLength()
	 * @see #getRuleSourceMatch()
	 * @generated
	 */
	EAttribute getRuleSourceMatch_Length();

	/**
	 * Returns the meta object for the reference '{@link com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getContainingRule <em>Containing Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Containing Rule</em>'.
	 * @see com.tibco.cep.studio.core.index.model.search.RuleSourceMatch#getContainingRule()
	 * @see #getRuleSourceMatch()
	 * @generated
	 */
	EReference getRuleSourceMatch_ContainingRule();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SearchFactory getSearchFactory();

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
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.search.impl.ElementMatchImpl <em>Element Match</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.search.impl.ElementMatchImpl
		 * @see com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl#getElementMatch()
		 * @generated
		 */
		EClass ELEMENT_MATCH = eINSTANCE.getElementMatch();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_MATCH__FEATURE = eINSTANCE.getElementMatch_Feature();

		/**
		 * The meta object literal for the '<em><b>Matched Element</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_MATCH__MATCHED_ELEMENT = eINSTANCE.getElementMatch_MatchedElement();

		/**
		 * The meta object literal for the '<em><b>Label</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_MATCH__LABEL = eINSTANCE.getElementMatch_Label();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.search.impl.MethodArgumentMatchImpl <em>Method Argument Match</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.search.impl.MethodArgumentMatchImpl
		 * @see com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl#getMethodArgumentMatch()
		 * @generated
		 */
		EClass METHOD_ARGUMENT_MATCH = eINSTANCE.getMethodArgumentMatch();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD_ARGUMENT_MATCH__FUNCTION = eINSTANCE.getMethodArgumentMatch_Function();

		/**
		 * The meta object literal for the '<em><b>Arg Node</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METHOD_ARGUMENT_MATCH__ARG_NODE = eINSTANCE.getMethodArgumentMatch_ArgNode();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.search.impl.StringLiteralMatchImpl <em>String Literal Match</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.search.impl.StringLiteralMatchImpl
		 * @see com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl#getStringLiteralMatch()
		 * @generated
		 */
		EClass STRING_LITERAL_MATCH = eINSTANCE.getStringLiteralMatch();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.search.impl.NonEntityMatchImpl <em>Non Entity Match</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.search.impl.NonEntityMatchImpl
		 * @see com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl#getNonEntityMatch()
		 * @generated
		 */
		EClass NON_ENTITY_MATCH = eINSTANCE.getNonEntityMatch();

		/**
		 * The meta object literal for the '<em><b>Project Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NON_ENTITY_MATCH__PROJECT_NAME = eINSTANCE.getNonEntityMatch_ProjectName();

		/**
		 * The meta object literal for the '<em><b>File Path</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NON_ENTITY_MATCH__FILE_PATH = eINSTANCE.getNonEntityMatch_FilePath();

		/**
		 * The meta object literal for the '<em><b>Match</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NON_ENTITY_MATCH__MATCH = eINSTANCE.getNonEntityMatch_Match();

		/**
		 * The meta object literal for the '{@link com.tibco.cep.studio.core.index.model.search.impl.RuleSourceMatchImpl <em>Rule Source Match</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.studio.core.index.model.search.impl.RuleSourceMatchImpl
		 * @see com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl#getRuleSourceMatch()
		 * @generated
		 */
		EClass RULE_SOURCE_MATCH = eINSTANCE.getRuleSourceMatch();

		/**
		 * The meta object literal for the '<em><b>Offset</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SOURCE_MATCH__OFFSET = eINSTANCE.getRuleSourceMatch_Offset();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RULE_SOURCE_MATCH__LENGTH = eINSTANCE.getRuleSourceMatch_Length();

		/**
		 * The meta object literal for the '<em><b>Containing Rule</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_SOURCE_MATCH__CONTAINING_RULE = eINSTANCE.getRuleSourceMatch_ContainingRule();

	}

} //SearchPackage
