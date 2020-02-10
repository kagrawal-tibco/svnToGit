/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element.impl;

import com.tibco.cep.designtime.core.model.element.*;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.element.AttributeDefinition;
import com.tibco.cep.designtime.core.model.element.BaseInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.Metric;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.element.Scorecard;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ElementFactoryImpl extends EFactoryImpl implements ElementFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ElementFactory init() {
		try {
			ElementFactory theElementFactory = (ElementFactory)EPackage.Registry.INSTANCE.getEFactory(ElementPackage.eNS_URI);
			if (theElementFactory != null) {
				return theElementFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ElementFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementFactoryImpl() {
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
			case ElementPackage.CONCEPT: return createConcept();
			case ElementPackage.SCORECARD: return createScorecard();
			case ElementPackage.PROPERTY_DEFINITION: return createPropertyDefinition();
			case ElementPackage.ATTRIBUTE_DEFINITION: return createAttributeDefinition();
			case ElementPackage.METRIC: return createMetric();
			case ElementPackage.BASE_INSTANCE: return createBaseInstance();
			case ElementPackage.PROCESS_ENTITY: return createProcessEntity();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
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
	public Scorecard createScorecard() {
		ScorecardImpl scorecard = new ScorecardImpl();
		return scorecard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyDefinition createPropertyDefinition() {
		PropertyDefinitionImpl propertyDefinition = new PropertyDefinitionImpl();
		return propertyDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AttributeDefinition createAttributeDefinition() {
		AttributeDefinitionImpl attributeDefinition = new AttributeDefinitionImpl();
		return attributeDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Metric createMetric() {
		MetricImpl metric = new MetricImpl();
		return metric;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BaseInstance createBaseInstance() {
		BaseInstanceImpl baseInstance = new BaseInstanceImpl();
		return baseInstance;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessEntity createProcessEntity() {
		ProcessEntityImpl processEntity = new ProcessEntityImpl();
		return processEntity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementPackage getElementPackage() {
		return (ElementPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ElementPackage getPackage() {
		return ElementPackage.eINSTANCE;
	}

} //ElementFactoryImpl
