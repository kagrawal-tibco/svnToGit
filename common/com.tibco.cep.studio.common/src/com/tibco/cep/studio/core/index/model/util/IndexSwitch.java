/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

import com.tibco.cep.designtime.core.model.element.BaseInstance;
import com.tibco.cep.studio.core.index.model.*;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ElementContainer;
import com.tibco.cep.studio.core.index.model.ElementReference;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.EventElement;
import com.tibco.cep.studio.core.index.model.Folder;
import com.tibco.cep.studio.core.index.model.GlobalVariableDef;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.InstanceElement;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.MemberElement;
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
import com.tibco.cep.studio.core.index.model.StructuredElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.core.index.model.IndexPackage
 * @generated
 */
public class IndexSwitch<T1> extends Switch<T1> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IndexPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexSwitch() {
		if (modelPackage == null) {
			modelPackage = IndexPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T1 doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case IndexPackage.DESIGNER_PROJECT: {
				DesignerProject designerProject = (DesignerProject)theEObject;
				T1 result = caseDesignerProject(designerProject);
				if (result == null) result = caseElementContainer(designerProject);
				if (result == null) result = caseDesignerElement(designerProject);
				if (result == null) result = caseStructuredElement(designerProject);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.STRUCTURED_ELEMENT: {
				StructuredElement structuredElement = (StructuredElement)theEObject;
				T1 result = caseStructuredElement(structuredElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.DESIGNER_ELEMENT: {
				DesignerElement designerElement = (DesignerElement)theEObject;
				T1 result = caseDesignerElement(designerElement);
				if (result == null) result = caseStructuredElement(designerElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.ELEMENT_CONTAINER: {
				ElementContainer elementContainer = (ElementContainer)theEObject;
				T1 result = caseElementContainer(elementContainer);
				if (result == null) result = caseDesignerElement(elementContainer);
				if (result == null) result = caseStructuredElement(elementContainer);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.FOLDER: {
				Folder folder = (Folder)theEObject;
				T1 result = caseFolder(folder);
				if (result == null) result = caseElementContainer(folder);
				if (result == null) result = caseDesignerElement(folder);
				if (result == null) result = caseStructuredElement(folder);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.MEMBER_ELEMENT: {
				MemberElement memberElement = (MemberElement)theEObject;
				T1 result = caseMemberElement(memberElement);
				if (result == null) result = caseDesignerElement(memberElement);
				if (result == null) result = caseStructuredElement(memberElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.ELEMENT_REFERENCE: {
				ElementReference elementReference = (ElementReference)theEObject;
				T1 result = caseElementReference(elementReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.TYPE_ELEMENT: {
				TypeElement typeElement = (TypeElement)theEObject;
				T1 result = caseTypeElement(typeElement);
				if (result == null) result = caseDesignerElement(typeElement);
				if (result == null) result = caseStructuredElement(typeElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.STATE_MACHINE_ELEMENT: {
				StateMachineElement stateMachineElement = (StateMachineElement)theEObject;
				T1 result = caseStateMachineElement(stateMachineElement);
				if (result == null) result = caseEntityElement(stateMachineElement);
				if (result == null) result = caseTypeElement(stateMachineElement);
				if (result == null) result = caseDesignerElement(stateMachineElement);
				if (result == null) result = caseStructuredElement(stateMachineElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.EVENT_ELEMENT: {
				EventElement eventElement = (EventElement)theEObject;
				T1 result = caseEventElement(eventElement);
				if (result == null) result = caseEntityElement(eventElement);
				if (result == null) result = caseTypeElement(eventElement);
				if (result == null) result = caseDesignerElement(eventElement);
				if (result == null) result = caseStructuredElement(eventElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.ENTITY_ELEMENT: {
				EntityElement entityElement = (EntityElement)theEObject;
				T1 result = caseEntityElement(entityElement);
				if (result == null) result = caseTypeElement(entityElement);
				if (result == null) result = caseDesignerElement(entityElement);
				if (result == null) result = caseStructuredElement(entityElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.DECISION_TABLE_ELEMENT: {
				DecisionTableElement decisionTableElement = (DecisionTableElement)theEObject;
				T1 result = caseDecisionTableElement(decisionTableElement);
				if (result == null) result = caseTypeElement(decisionTableElement);
				if (result == null) result = caseDesignerElement(decisionTableElement);
				if (result == null) result = caseStructuredElement(decisionTableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.ARCHIVE_ELEMENT: {
				ArchiveElement archiveElement = (ArchiveElement)theEObject;
				T1 result = caseArchiveElement(archiveElement);
				if (result == null) result = caseTypeElement(archiveElement);
				if (result == null) result = caseDesignerElement(archiveElement);
				if (result == null) result = caseStructuredElement(archiveElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.RULE_ELEMENT: {
				RuleElement ruleElement = (RuleElement)theEObject;
				T1 result = caseRuleElement(ruleElement);
				if (result == null) result = caseTypeElement(ruleElement);
				if (result == null) result = caseDesignerElement(ruleElement);
				if (result == null) result = caseStructuredElement(ruleElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.VARIABLE_DEFINITION: {
				VariableDefinition variableDefinition = (VariableDefinition)theEObject;
				T1 result = caseVariableDefinition(variableDefinition);
				if (result == null) result = caseMemberElement(variableDefinition);
				if (result == null) result = caseDesignerElement(variableDefinition);
				if (result == null) result = caseStructuredElement(variableDefinition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.LOCAL_VARIABLE_DEF: {
				LocalVariableDef localVariableDef = (LocalVariableDef)theEObject;
				T1 result = caseLocalVariableDef(localVariableDef);
				if (result == null) result = caseVariableDefinition(localVariableDef);
				if (result == null) result = caseMemberElement(localVariableDef);
				if (result == null) result = caseDesignerElement(localVariableDef);
				if (result == null) result = caseStructuredElement(localVariableDef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.GLOBAL_VARIABLE_DEF: {
				GlobalVariableDef globalVariableDef = (GlobalVariableDef)theEObject;
				T1 result = caseGlobalVariableDef(globalVariableDef);
				if (result == null) result = caseVariableDefinition(globalVariableDef);
				if (result == null) result = caseMemberElement(globalVariableDef);
				if (result == null) result = caseDesignerElement(globalVariableDef);
				if (result == null) result = caseStructuredElement(globalVariableDef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.INSTANCE_ELEMENT: {
				InstanceElement<?> instanceElement = (InstanceElement<?>)theEObject;
				T1 result = caseInstanceElement(instanceElement);
				if (result == null) result = caseTypeElement(instanceElement);
				if (result == null) result = caseDesignerElement(instanceElement);
				if (result == null) result = caseStructuredElement(instanceElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.SHARED_ELEMENT: {
				SharedElement sharedElement = (SharedElement)theEObject;
				T1 result = caseSharedElement(sharedElement);
				if (result == null) result = caseDesignerElement(sharedElement);
				if (result == null) result = caseStructuredElement(sharedElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.SHARED_DECISION_TABLE_ELEMENT: {
				SharedDecisionTableElement sharedDecisionTableElement = (SharedDecisionTableElement)theEObject;
				T1 result = caseSharedDecisionTableElement(sharedDecisionTableElement);
				if (result == null) result = caseSharedElement(sharedDecisionTableElement);
				if (result == null) result = caseDecisionTableElement(sharedDecisionTableElement);
				if (result == null) result = caseTypeElement(sharedDecisionTableElement);
				if (result == null) result = caseDesignerElement(sharedDecisionTableElement);
				if (result == null) result = caseStructuredElement(sharedDecisionTableElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.SHARED_RULE_ELEMENT: {
				SharedRuleElement sharedRuleElement = (SharedRuleElement)theEObject;
				T1 result = caseSharedRuleElement(sharedRuleElement);
				if (result == null) result = caseSharedElement(sharedRuleElement);
				if (result == null) result = caseRuleElement(sharedRuleElement);
				if (result == null) result = caseTypeElement(sharedRuleElement);
				if (result == null) result = caseDesignerElement(sharedRuleElement);
				if (result == null) result = caseStructuredElement(sharedRuleElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.SHARED_STATE_MACHINE_ELEMENT: {
				SharedStateMachineElement sharedStateMachineElement = (SharedStateMachineElement)theEObject;
				T1 result = caseSharedStateMachineElement(sharedStateMachineElement);
				if (result == null) result = caseStateMachineElement(sharedStateMachineElement);
				if (result == null) result = caseSharedEntityElement(sharedStateMachineElement);
				if (result == null) result = caseEntityElement(sharedStateMachineElement);
				if (result == null) result = caseSharedElement(sharedStateMachineElement);
				if (result == null) result = caseTypeElement(sharedStateMachineElement);
				if (result == null) result = caseDesignerElement(sharedStateMachineElement);
				if (result == null) result = caseStructuredElement(sharedStateMachineElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.SHARED_EVENT_ELEMENT: {
				SharedEventElement sharedEventElement = (SharedEventElement)theEObject;
				T1 result = caseSharedEventElement(sharedEventElement);
				if (result == null) result = caseSharedEntityElement(sharedEventElement);
				if (result == null) result = caseEventElement(sharedEventElement);
				if (result == null) result = caseSharedElement(sharedEventElement);
				if (result == null) result = caseEntityElement(sharedEventElement);
				if (result == null) result = caseTypeElement(sharedEventElement);
				if (result == null) result = caseDesignerElement(sharedEventElement);
				if (result == null) result = caseStructuredElement(sharedEventElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.SHARED_ENTITY_ELEMENT: {
				SharedEntityElement sharedEntityElement = (SharedEntityElement)theEObject;
				T1 result = caseSharedEntityElement(sharedEntityElement);
				if (result == null) result = caseSharedElement(sharedEntityElement);
				if (result == null) result = caseEntityElement(sharedEntityElement);
				if (result == null) result = caseTypeElement(sharedEntityElement);
				if (result == null) result = caseDesignerElement(sharedEntityElement);
				if (result == null) result = caseStructuredElement(sharedEntityElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.SHARED_PROCESS_ELEMENT: {
				SharedProcessElement sharedProcessElement = (SharedProcessElement)theEObject;
				T1 result = caseSharedProcessElement(sharedProcessElement);
				if (result == null) result = caseSharedElement(sharedProcessElement);
				if (result == null) result = caseProcessElement(sharedProcessElement);
				if (result == null) result = caseEntityElement(sharedProcessElement);
				if (result == null) result = caseStructuredElement(sharedProcessElement);
				if (result == null) result = caseTypeElement(sharedProcessElement);
				if (result == null) result = caseDesignerElement(sharedProcessElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.PROCESS_ELEMENT: {
				ProcessElement processElement = (ProcessElement)theEObject;
				T1 result = caseProcessElement(processElement);
				if (result == null) result = caseEntityElement(processElement);
				if (result == null) result = caseTypeElement(processElement);
				if (result == null) result = caseDesignerElement(processElement);
				if (result == null) result = caseStructuredElement(processElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.ISTRUCTURED_ELEMENT_VISITOR: {
				IStructuredElementVisitor iStructuredElementVisitor = (IStructuredElementVisitor)theEObject;
				T1 result = caseIStructuredElementVisitor(iStructuredElementVisitor);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.JAVA_ELEMENT: {
				JavaElement javaElement = (JavaElement)theEObject;
				T1 result = caseJavaElement(javaElement);
				if (result == null) result = caseEntityElement(javaElement);
				if (result == null) result = caseTypeElement(javaElement);
				if (result == null) result = caseDesignerElement(javaElement);
				if (result == null) result = caseStructuredElement(javaElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.SHARED_JAVA_ELEMENT: {
				SharedJavaElement sharedJavaElement = (SharedJavaElement)theEObject;
				T1 result = caseSharedJavaElement(sharedJavaElement);
				if (result == null) result = caseJavaElement(sharedJavaElement);
				if (result == null) result = caseSharedEntityElement(sharedJavaElement);
				if (result == null) result = caseEntityElement(sharedJavaElement);
				if (result == null) result = caseSharedElement(sharedJavaElement);
				if (result == null) result = caseTypeElement(sharedJavaElement);
				if (result == null) result = caseDesignerElement(sharedJavaElement);
				if (result == null) result = caseStructuredElement(sharedJavaElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.JAVA_RESOURCE_ELEMENT: {
				JavaResourceElement javaResourceElement = (JavaResourceElement)theEObject;
				T1 result = caseJavaResourceElement(javaResourceElement);
				if (result == null) result = caseEntityElement(javaResourceElement);
				if (result == null) result = caseTypeElement(javaResourceElement);
				if (result == null) result = caseDesignerElement(javaResourceElement);
				if (result == null) result = caseStructuredElement(javaResourceElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.SHARED_JAVA_RESOURCE_ELEMENT: {
				SharedJavaResourceElement sharedJavaResourceElement = (SharedJavaResourceElement)theEObject;
				T1 result = caseSharedJavaResourceElement(sharedJavaResourceElement);
				if (result == null) result = caseJavaResourceElement(sharedJavaResourceElement);
				if (result == null) result = caseSharedEntityElement(sharedJavaResourceElement);
				if (result == null) result = caseEntityElement(sharedJavaResourceElement);
				if (result == null) result = caseSharedElement(sharedJavaResourceElement);
				if (result == null) result = caseTypeElement(sharedJavaResourceElement);
				if (result == null) result = caseDesignerElement(sharedJavaResourceElement);
				if (result == null) result = caseStructuredElement(sharedJavaResourceElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case IndexPackage.BINDING_VARIABLE_DEF: {
				BindingVariableDef bindingVariableDef = (BindingVariableDef)theEObject;
				T1 result = caseBindingVariableDef(bindingVariableDef);
				if (result == null) result = caseGlobalVariableDef(bindingVariableDef);
				if (result == null) result = caseVariableDefinition(bindingVariableDef);
				if (result == null) result = caseMemberElement(bindingVariableDef);
				if (result == null) result = caseDesignerElement(bindingVariableDef);
				if (result == null) result = caseStructuredElement(bindingVariableDef);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Designer Project</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Designer Project</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDesignerProject(DesignerProject object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Structured Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Structured Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStructuredElement(StructuredElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Designer Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Designer Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDesignerElement(DesignerElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Member Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Member Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseMemberElement(MemberElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseElementReference(ElementReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Type Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Type Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseTypeElement(TypeElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entity Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entity Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEntityElement(EntityElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State Machine Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State Machine Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseStateMachineElement(StateMachineElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Event Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Event Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseEventElement(EventElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Decision Table Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Decision Table Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseDecisionTableElement(DecisionTableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Archive Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Archive Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseArchiveElement(ArchiveElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rule Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rule Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseRuleElement(RuleElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable Definition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable Definition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseVariableDefinition(VariableDefinition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Local Variable Def</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Local Variable Def</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseLocalVariableDef(LocalVariableDef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Global Variable Def</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Global Variable Def</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseGlobalVariableDef(GlobalVariableDef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Instance Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Instance Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <T extends BaseInstance> T1 caseInstanceElement(InstanceElement<T> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSharedElement(SharedElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared Decision Table Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared Decision Table Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSharedDecisionTableElement(SharedDecisionTableElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared Rule Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared Rule Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSharedRuleElement(SharedRuleElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared State Machine Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared State Machine Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSharedStateMachineElement(SharedStateMachineElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared Event Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared Event Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSharedEventElement(SharedEventElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared Entity Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared Entity Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSharedEntityElement(SharedEntityElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared Process Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared Process Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSharedProcessElement(SharedProcessElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Process Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Process Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseProcessElement(ProcessElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IStructured Element Visitor</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IStructured Element Visitor</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseIStructuredElementVisitor(IStructuredElementVisitor object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseJavaElement(JavaElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared Java Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared Java Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSharedJavaElement(SharedJavaElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Java Resource Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Java Resource Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseJavaResourceElement(JavaResourceElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Shared Java Resource Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Shared Java Resource Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseSharedJavaResourceElement(SharedJavaResourceElement object) {
		return null;
	}


	/**
	 * Returns the result of interpreting the object as an instance of '<em>Binding Variable Def</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Binding Variable Def</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseBindingVariableDef(BindingVariableDef object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Element Container</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Element Container</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseElementContainer(ElementContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Folder</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Folder</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T1 caseFolder(Folder object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T1 defaultCase(EObject object) {
		return null;
	}

} //IndexSwitch
