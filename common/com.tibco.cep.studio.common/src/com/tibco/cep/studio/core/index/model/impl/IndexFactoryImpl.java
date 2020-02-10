/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import com.tibco.cep.designtime.core.model.element.BaseInstance;
import com.tibco.cep.studio.core.index.model.*;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.EventElement;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.InstanceElement;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.ProcessElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedDecisionTableElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.SharedEventElement;
import com.tibco.cep.studio.core.index.model.SharedProcessElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.model.SharedStateMachineElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IndexFactoryImpl extends EFactoryImpl implements IndexFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static IndexFactory init() {
		try {
			IndexFactory theIndexFactory = (IndexFactory)EPackage.Registry.INSTANCE.getEFactory(IndexPackage.eNS_URI);
			if (theIndexFactory != null) {
				return theIndexFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IndexFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexFactoryImpl() {
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
			case IndexPackage.DESIGNER_PROJECT: return createDesignerProject();
			case IndexPackage.FOLDER: return createFolder();
			case IndexPackage.ELEMENT_REFERENCE: return createElementReference();
			case IndexPackage.STATE_MACHINE_ELEMENT: return createStateMachineElement();
			case IndexPackage.EVENT_ELEMENT: return createEventElement();
			case IndexPackage.ENTITY_ELEMENT: return createEntityElement();
			case IndexPackage.DECISION_TABLE_ELEMENT: return createDecisionTableElement();
			case IndexPackage.ARCHIVE_ELEMENT: return createArchiveElement();
			case IndexPackage.RULE_ELEMENT: return createRuleElement();
			case IndexPackage.VARIABLE_DEFINITION: return createVariableDefinition();
			case IndexPackage.LOCAL_VARIABLE_DEF: return createLocalVariableDef();
			case IndexPackage.GLOBAL_VARIABLE_DEF: return createGlobalVariableDef();
			case IndexPackage.INSTANCE_ELEMENT: return createInstanceElement();
			case IndexPackage.SHARED_ELEMENT: return createSharedElement();
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT: return createSharedDecisionTableElement();
			case IndexPackage.SHARED_RULE_ELEMENT: return createSharedRuleElement();
			case IndexPackage.SHARED_STATE_MACHINE_ELEMENT: return createSharedStateMachineElement();
			case IndexPackage.SHARED_EVENT_ELEMENT: return createSharedEventElement();
			case IndexPackage.SHARED_ENTITY_ELEMENT: return createSharedEntityElement();
			case IndexPackage.SHARED_PROCESS_ELEMENT: return createSharedProcessElement();
			case IndexPackage.PROCESS_ELEMENT: return createProcessElement();
			case IndexPackage.JAVA_ELEMENT: return createJavaElement();
			case IndexPackage.SHARED_JAVA_ELEMENT: return createSharedJavaElement();
			case IndexPackage.JAVA_RESOURCE_ELEMENT: return createJavaResourceElement();
			case IndexPackage.SHARED_JAVA_RESOURCE_ELEMENT: return createSharedJavaResourceElement();
			case IndexPackage.BINDING_VARIABLE_DEF: return createBindingVariableDef();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case IndexPackage.ELEMENT_TYPES:
				return createELEMENT_TYPESFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case IndexPackage.ELEMENT_TYPES:
				return convertELEMENT_TYPESToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DesignerProject createDesignerProject() {
		DesignerProjectImpl designerProject = new DesignerProjectImpl();
		return designerProject;
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
	public ElementReference createElementReference() {
		ElementReferenceImpl elementReference = new ElementReferenceImpl();
		return elementReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntityElement createEntityElement() {
		EntityElementImpl entityElement = new EntityElementImpl();
		return entityElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachineElement createStateMachineElement() {
		StateMachineElementImpl stateMachineElement = new StateMachineElementImpl();
		return stateMachineElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventElement createEventElement() {
		EventElementImpl eventElement = new EventElementImpl();
		return eventElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecisionTableElement createDecisionTableElement() {
		DecisionTableElementImpl decisionTableElement = new DecisionTableElementImpl();
		return decisionTableElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchiveElement createArchiveElement() {
		ArchiveElementImpl archiveElement = new ArchiveElementImpl();
		return archiveElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleElement createRuleElement() {
		RuleElementImpl ruleElement = new RuleElementImpl();
		return ruleElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VariableDefinition createVariableDefinition() {
		VariableDefinitionImpl variableDefinition = new VariableDefinitionImpl();
		return variableDefinition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocalVariableDef createLocalVariableDef() {
		LocalVariableDefImpl localVariableDef = new LocalVariableDefImpl();
		return localVariableDef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalVariableDef createGlobalVariableDef() {
		GlobalVariableDefImpl globalVariableDef = new GlobalVariableDefImpl();
		return globalVariableDef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public <T extends BaseInstance> InstanceElement<T> createInstanceElement() {
		InstanceElementImpl<T> instanceElement = new InstanceElementImpl<T>();
		return instanceElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedElement createSharedElement() {
		SharedElementImpl sharedElement = new SharedElementImpl();
		return sharedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedDecisionTableElement createSharedDecisionTableElement() {
		SharedDecisionTableElementImpl sharedDecisionTableElement = new SharedDecisionTableElementImpl();
		return sharedDecisionTableElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedRuleElement createSharedRuleElement() {
		SharedRuleElementImpl sharedRuleElement = new SharedRuleElementImpl();
		return sharedRuleElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedStateMachineElement createSharedStateMachineElement() {
		SharedStateMachineElementImpl sharedStateMachineElement = new SharedStateMachineElementImpl();
		return sharedStateMachineElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedEventElement createSharedEventElement() {
		SharedEventElementImpl sharedEventElement = new SharedEventElementImpl();
		return sharedEventElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedEntityElement createSharedEntityElement() {
		SharedEntityElementImpl sharedEntityElement = new SharedEntityElementImpl();
		return sharedEntityElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedProcessElement createSharedProcessElement() {
		SharedProcessElementImpl sharedProcessElement = new SharedProcessElementImpl();
		return sharedProcessElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessElement createProcessElement() {
		ProcessElementImpl processElement = new ProcessElementImpl();
		return processElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaElement createJavaElement() {
		JavaElementImpl javaElement = new JavaElementImpl();
		return javaElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedJavaElement createSharedJavaElement() {
		SharedJavaElementImpl sharedJavaElement = new SharedJavaElementImpl();
		return sharedJavaElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JavaResourceElement createJavaResourceElement() {
		JavaResourceElementImpl javaResourceElement = new JavaResourceElementImpl();
		return javaResourceElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SharedJavaResourceElement createSharedJavaResourceElement() {
		SharedJavaResourceElementImpl sharedJavaResourceElement = new SharedJavaResourceElementImpl();
		return sharedJavaResourceElement;
	}


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BindingVariableDef createBindingVariableDef() {
		BindingVariableDefImpl bindingVariableDef = new BindingVariableDefImpl();
		return bindingVariableDef;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ELEMENT_TYPES createELEMENT_TYPESFromString(EDataType eDataType, String initialValue) {
		ELEMENT_TYPES result = ELEMENT_TYPES.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertELEMENT_TYPESToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexPackage getIndexPackage() {
		return (IndexPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IndexPackage getPackage() {
		return IndexPackage.eINSTANCE;
	}

} //IndexFactoryImpl
