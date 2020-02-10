/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

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
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see com.tibco.cep.studio.core.index.model.IndexPackage
 * @generated
 */
public class IndexAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static IndexPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IndexPackage.eINSTANCE;
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
	protected IndexSwitch<Adapter> modelSwitch =
		new IndexSwitch<Adapter>() {
			@Override
			public Adapter caseDesignerProject(DesignerProject object) {
				return createDesignerProjectAdapter();
			}
			@Override
			public Adapter caseStructuredElement(StructuredElement object) {
				return createStructuredElementAdapter();
			}
			@Override
			public Adapter caseDesignerElement(DesignerElement object) {
				return createDesignerElementAdapter();
			}
			@Override
			public Adapter caseElementContainer(ElementContainer object) {
				return createElementContainerAdapter();
			}
			@Override
			public Adapter caseFolder(Folder object) {
				return createFolderAdapter();
			}
			@Override
			public Adapter caseMemberElement(MemberElement object) {
				return createMemberElementAdapter();
			}
			@Override
			public Adapter caseElementReference(ElementReference object) {
				return createElementReferenceAdapter();
			}
			@Override
			public Adapter caseTypeElement(TypeElement object) {
				return createTypeElementAdapter();
			}
			@Override
			public Adapter caseStateMachineElement(StateMachineElement object) {
				return createStateMachineElementAdapter();
			}
			@Override
			public Adapter caseEventElement(EventElement object) {
				return createEventElementAdapter();
			}
			@Override
			public Adapter caseEntityElement(EntityElement object) {
				return createEntityElementAdapter();
			}
			@Override
			public Adapter caseDecisionTableElement(DecisionTableElement object) {
				return createDecisionTableElementAdapter();
			}
			@Override
			public Adapter caseArchiveElement(ArchiveElement object) {
				return createArchiveElementAdapter();
			}
			@Override
			public Adapter caseRuleElement(RuleElement object) {
				return createRuleElementAdapter();
			}
			@Override
			public Adapter caseVariableDefinition(VariableDefinition object) {
				return createVariableDefinitionAdapter();
			}
			@Override
			public Adapter caseLocalVariableDef(LocalVariableDef object) {
				return createLocalVariableDefAdapter();
			}
			@Override
			public Adapter caseGlobalVariableDef(GlobalVariableDef object) {
				return createGlobalVariableDefAdapter();
			}
			@Override
			public <T extends BaseInstance> Adapter caseInstanceElement(InstanceElement<T> object) {
				return createInstanceElementAdapter();
			}
			@Override
			public Adapter caseSharedElement(SharedElement object) {
				return createSharedElementAdapter();
			}
			@Override
			public Adapter caseSharedDecisionTableElement(SharedDecisionTableElement object) {
				return createSharedDecisionTableElementAdapter();
			}
			@Override
			public Adapter caseSharedRuleElement(SharedRuleElement object) {
				return createSharedRuleElementAdapter();
			}
			@Override
			public Adapter caseSharedStateMachineElement(SharedStateMachineElement object) {
				return createSharedStateMachineElementAdapter();
			}
			@Override
			public Adapter caseSharedEventElement(SharedEventElement object) {
				return createSharedEventElementAdapter();
			}
			@Override
			public Adapter caseSharedEntityElement(SharedEntityElement object) {
				return createSharedEntityElementAdapter();
			}
			@Override
			public Adapter caseSharedProcessElement(SharedProcessElement object) {
				return createSharedProcessElementAdapter();
			}
			@Override
			public Adapter caseProcessElement(ProcessElement object) {
				return createProcessElementAdapter();
			}
			@Override
			public Adapter caseIStructuredElementVisitor(IStructuredElementVisitor object) {
				return createIStructuredElementVisitorAdapter();
			}
			@Override
			public Adapter caseJavaElement(JavaElement object) {
				return createJavaElementAdapter();
			}
			@Override
			public Adapter caseSharedJavaElement(SharedJavaElement object) {
				return createSharedJavaElementAdapter();
			}
			@Override
			public Adapter caseJavaResourceElement(JavaResourceElement object) {
				return createJavaResourceElementAdapter();
			}
			@Override
			public Adapter caseSharedJavaResourceElement(SharedJavaResourceElement object) {
				return createSharedJavaResourceElementAdapter();
			}
			@Override
			public Adapter caseBindingVariableDef(BindingVariableDef object) {
				return createBindingVariableDefAdapter();
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
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.DesignerProject <em>Designer Project</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.DesignerProject
	 * @generated
	 */
	public Adapter createDesignerProjectAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.StructuredElement <em>Structured Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.StructuredElement
	 * @generated
	 */
	public Adapter createStructuredElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.DesignerElement <em>Designer Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.DesignerElement
	 * @generated
	 */
	public Adapter createDesignerElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.MemberElement <em>Member Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.MemberElement
	 * @generated
	 */
	public Adapter createMemberElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.ElementReference <em>Element Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.ElementReference
	 * @generated
	 */
	public Adapter createElementReferenceAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.TypeElement <em>Type Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.TypeElement
	 * @generated
	 */
	public Adapter createTypeElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.EntityElement <em>Entity Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.EntityElement
	 * @generated
	 */
	public Adapter createEntityElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.StateMachineElement <em>State Machine Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.StateMachineElement
	 * @generated
	 */
	public Adapter createStateMachineElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.EventElement <em>Event Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.EventElement
	 * @generated
	 */
	public Adapter createEventElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.DecisionTableElement <em>Decision Table Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.DecisionTableElement
	 * @generated
	 */
	public Adapter createDecisionTableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.ArchiveElement <em>Archive Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.ArchiveElement
	 * @generated
	 */
	public Adapter createArchiveElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.RuleElement <em>Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.RuleElement
	 * @generated
	 */
	public Adapter createRuleElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.VariableDefinition <em>Variable Definition</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.VariableDefinition
	 * @generated
	 */
	public Adapter createVariableDefinitionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.LocalVariableDef <em>Local Variable Def</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.LocalVariableDef
	 * @generated
	 */
	public Adapter createLocalVariableDefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.GlobalVariableDef <em>Global Variable Def</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.GlobalVariableDef
	 * @generated
	 */
	public Adapter createGlobalVariableDefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.InstanceElement <em>Instance Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.InstanceElement
	 * @generated
	 */
	public Adapter createInstanceElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.SharedElement <em>Shared Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.SharedElement
	 * @generated
	 */
	public Adapter createSharedElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.SharedDecisionTableElement <em>Shared Decision Table Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.SharedDecisionTableElement
	 * @generated
	 */
	public Adapter createSharedDecisionTableElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.SharedRuleElement <em>Shared Rule Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.SharedRuleElement
	 * @generated
	 */
	public Adapter createSharedRuleElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.SharedStateMachineElement <em>Shared State Machine Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.SharedStateMachineElement
	 * @generated
	 */
	public Adapter createSharedStateMachineElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.SharedEventElement <em>Shared Event Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.SharedEventElement
	 * @generated
	 */
	public Adapter createSharedEventElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.SharedEntityElement <em>Shared Entity Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.SharedEntityElement
	 * @generated
	 */
	public Adapter createSharedEntityElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.SharedProcessElement <em>Shared Process Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.SharedProcessElement
	 * @generated
	 */
	public Adapter createSharedProcessElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.ProcessElement <em>Process Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.ProcessElement
	 * @generated
	 */
	public Adapter createProcessElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.IStructuredElementVisitor <em>IStructured Element Visitor</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.IStructuredElementVisitor
	 * @generated
	 */
	public Adapter createIStructuredElementVisitorAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.JavaElement <em>Java Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.JavaElement
	 * @generated
	 */
	public Adapter createJavaElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.SharedJavaElement <em>Shared Java Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.SharedJavaElement
	 * @generated
	 */
	public Adapter createSharedJavaElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.JavaResourceElement <em>Java Resource Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.JavaResourceElement
	 * @generated
	 */
	public Adapter createJavaResourceElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.SharedJavaResourceElement <em>Shared Java Resource Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.SharedJavaResourceElement
	 * @generated
	 */
	public Adapter createSharedJavaResourceElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.BindingVariableDef <em>Binding Variable Def</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.BindingVariableDef
	 * @generated
	 */
	public Adapter createBindingVariableDefAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.ElementContainer <em>Element Container</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.ElementContainer
	 * @generated
	 */
	public Adapter createElementContainerAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link com.tibco.cep.studio.core.index.model.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see com.tibco.cep.studio.core.index.model.Folder
	 * @generated
	 */
	public Adapter createFolderAdapter() {
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

} //IndexAdapterFactory
