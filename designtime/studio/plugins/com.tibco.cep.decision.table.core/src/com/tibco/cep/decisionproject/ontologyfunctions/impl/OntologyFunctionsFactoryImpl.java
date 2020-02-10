/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontologyfunctions.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.decisionproject.ontologyfunctions.AbstractResource;
import com.tibco.cep.decisionproject.ontologyfunctions.Concept;
import com.tibco.cep.decisionproject.ontologyfunctions.Event;
import com.tibco.cep.decisionproject.ontologyfunctions.Folder;
import com.tibco.cep.decisionproject.ontologyfunctions.FunctionLabel;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctions;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsFactory;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsPackage;
import com.tibco.cep.decisionproject.ontologyfunctions.OntologyFunctionsRoot;
import com.tibco.cep.decisionproject.ontologyfunctions.RuleFunction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OntologyFunctionsFactoryImpl extends EFactoryImpl implements OntologyFunctionsFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OntologyFunctionsFactory init() {
		try {
			OntologyFunctionsFactory theOntologyFunctionsFactory = (OntologyFunctionsFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/ontologyfunctions/model/ontologyfunctions.ecore"); 
			if (theOntologyFunctionsFactory != null) {
				return theOntologyFunctionsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new OntologyFunctionsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyFunctionsFactoryImpl() {
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
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS_ROOT: return createOntologyFunctionsRoot();
			case OntologyFunctionsPackage.ONTOLOGY_FUNCTIONS: return createOntologyFunctions();
			case OntologyFunctionsPackage.ABSTRACT_RESOURCE: return createAbstractResource();
			case OntologyFunctionsPackage.CONCEPT: return createConcept();
			case OntologyFunctionsPackage.FOLDER: return createFolder();
			case OntologyFunctionsPackage.EVENT: return createEvent();
			case OntologyFunctionsPackage.RULE_FUNCTION: return createRuleFunction();
			case OntologyFunctionsPackage.FUNCTION_LABEL: return createFunctionLabel();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyFunctionsRoot createOntologyFunctionsRoot() {
		OntologyFunctionsRootImpl ontologyFunctionsRoot = new OntologyFunctionsRootImpl();
		return ontologyFunctionsRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyFunctions createOntologyFunctions() {
		OntologyFunctionsImpl ontologyFunctions = new OntologyFunctionsImpl();
		return ontologyFunctions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractResource createAbstractResource() {
		AbstractResourceImpl abstractResource = new AbstractResourceImpl();
		return abstractResource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Concept createConcept() {
		ConceptImpl concept = new ConceptImpl();
		return concept;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Folder createFolder() {
		FolderImpl folder = new FolderImpl();
		return folder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Event createEvent() {
		EventImpl event = new EventImpl();
		return event;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleFunction createRuleFunction() {
		RuleFunctionImpl ruleFunction = new RuleFunctionImpl();
		return ruleFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FunctionLabel createFunctionLabel() {
		FunctionLabelImpl functionLabel = new FunctionLabelImpl();
		return functionLabel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyFunctionsPackage getOntologyFunctionsPackage() {
		return (OntologyFunctionsPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static OntologyFunctionsPackage getPackage() {
		return OntologyFunctionsPackage.eINSTANCE;
	}

} //OntologyFunctionsFactoryImpl
