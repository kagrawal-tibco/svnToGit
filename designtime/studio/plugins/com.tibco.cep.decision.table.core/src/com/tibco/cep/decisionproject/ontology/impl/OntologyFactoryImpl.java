/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.decisionproject.ontology.Argument;
import com.tibco.cep.decisionproject.ontology.Arguments;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.DTRule;
import com.tibco.cep.decisionproject.ontology.Event;
import com.tibco.cep.decisionproject.ontology.Folder;
import com.tibco.cep.decisionproject.ontology.Header;
import com.tibco.cep.decisionproject.ontology.Ontology;
import com.tibco.cep.decisionproject.ontology.OntologyFactory;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.PrimitiveHolder;
import com.tibco.cep.decisionproject.ontology.Property;
import com.tibco.cep.decisionproject.ontology.Rule;
import com.tibco.cep.decisionproject.ontology.RuleFunction;
import com.tibco.cep.decisionproject.ontology.RuleSet;
import com.tibco.cep.decisionproject.ontology.RuleSetParticipant;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OntologyFactoryImpl extends EFactoryImpl implements OntologyFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OntologyFactory init() {
		try {
			OntologyFactory theOntologyFactory = (OntologyFactory)EPackage.Registry.INSTANCE.getEFactory("http:///com/tibco/cep/decisionproject/model/ontology.ecore"); 
			if (theOntologyFactory != null) {
				return theOntologyFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new OntologyFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyFactoryImpl() {
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
			case OntologyPackage.FOLDER: return createFolder();
			case OntologyPackage.CONCEPT: return createConcept();
			case OntologyPackage.EVENT: return createEvent();
			case OntologyPackage.RULE_SET: return createRuleSet();
			case OntologyPackage.RULE: return createRule();
			case OntologyPackage.RULE_FUNCTION: return createRuleFunction();
			case OntologyPackage.PROPERTY: return createProperty();
			case OntologyPackage.ONTOLOGY: return createOntology();
			case OntologyPackage.RULE_SET_PARTICIPANT: return createRuleSetParticipant();
			case OntologyPackage.DT_RULE: return createDTRule();
			case OntologyPackage.HEADER: return createHeader();
			case OntologyPackage.ARGUMENTS: return createArguments();
			case OntologyPackage.ARGUMENT: return createArgument();
			case OntologyPackage.PRIMITIVE_HOLDER: return createPrimitiveHolder();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
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
	public Concept createConcept() {
		ConceptImpl concept = new ConceptImpl();
		return concept;
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
	public RuleSet createRuleSet() {
		RuleSetImpl ruleSet = new RuleSetImpl();
		return ruleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rule createRule() {
		RuleImpl rule = new RuleImpl();
		return rule;
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
	public Property createProperty() {
		PropertyImpl property = new PropertyImpl();
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Ontology createOntology() {
		OntologyImpl ontology = new OntologyImpl();
		return ontology;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleSetParticipant createRuleSetParticipant() {
		RuleSetParticipantImpl ruleSetParticipant = new RuleSetParticipantImpl();
		return ruleSetParticipant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DTRule createDTRule() {
		DTRuleImpl dtRule = new DTRuleImpl();
		return dtRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Header createHeader() {
		HeaderImpl header = new HeaderImpl();
		return header;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Arguments createArguments() {
		ArgumentsImpl arguments = new ArgumentsImpl();
		return arguments;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Argument createArgument() {
		ArgumentImpl argument = new ArgumentImpl();
		return argument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PrimitiveHolder createPrimitiveHolder() {
		PrimitiveHolderImpl primitiveHolder = new PrimitiveHolderImpl();
		return primitiveHolder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OntologyPackage getOntologyPackage() {
		return (OntologyPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static OntologyPackage getPackage() {
		return OntologyPackage.eINSTANCE;
	}

} //OntologyFactoryImpl
