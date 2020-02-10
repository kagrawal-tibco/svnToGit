/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import com.tibco.cep.designtime.core.model.ModelPackage;
import com.tibco.cep.designtime.core.model.archive.ArchivePackage;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.java.JavaPackage;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.BindingVariableDef;
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
import com.tibco.cep.studio.core.index.model.IndexFactory;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.InstanceElement;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.JavaResourceElement;
import com.tibco.cep.studio.core.index.model.LocalVariableDef;
import com.tibco.cep.studio.core.index.model.MemberElement;
import com.tibco.cep.studio.core.index.model.ProcessElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedDecisionTableElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.model.SharedEventElement;
import com.tibco.cep.studio.core.index.model.SharedJavaElement;
import com.tibco.cep.studio.core.index.model.SharedJavaResourceElement;
import com.tibco.cep.studio.core.index.model.SharedProcessElement;
import com.tibco.cep.studio.core.index.model.SharedRuleElement;
import com.tibco.cep.studio.core.index.model.SharedStateMachineElement;
import com.tibco.cep.studio.core.index.model.StateMachineElement;
import com.tibco.cep.studio.core.index.model.StructuredElement;
import com.tibco.cep.studio.core.index.model.TypeElement;
import com.tibco.cep.studio.core.index.model.VariableDefinition;
import com.tibco.cep.studio.core.index.model.scope.ScopePackage;
import com.tibco.cep.studio.core.index.model.scope.impl.ScopePackageImpl;
import com.tibco.cep.studio.core.index.model.search.SearchPackage;
import com.tibco.cep.studio.core.index.model.search.impl.SearchPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class IndexPackageImpl extends EPackageImpl implements IndexPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass designerProjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass structuredElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass designerElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass memberElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stateMachineElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eventElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass decisionTableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass archiveElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass variableDefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass localVariableDefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalVariableDefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass instanceElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedDecisionTableElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedRuleElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedStateMachineElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedEventElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedEntityElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedProcessElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iStructuredElementVisitorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedJavaElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass javaResourceElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sharedJavaResourceElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bindingVariableDefEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass folderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum elemenT_TYPESEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.tibco.cep.studio.core.index.model.IndexPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private IndexPackageImpl() {
		super(eNS_URI, IndexFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link IndexPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static IndexPackage init() {
		if (isInited) return (IndexPackage)EPackage.Registry.INSTANCE.getEPackage(IndexPackage.eNS_URI);

		// Obtain or create and register package
		IndexPackageImpl theIndexPackage = (IndexPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof IndexPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new IndexPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ModelPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		ScopePackageImpl theScopePackage = (ScopePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ScopePackage.eNS_URI) instanceof ScopePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ScopePackage.eNS_URI) : ScopePackage.eINSTANCE);
		SearchPackageImpl theSearchPackage = (SearchPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SearchPackage.eNS_URI) instanceof SearchPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SearchPackage.eNS_URI) : SearchPackage.eINSTANCE);

		// Create package meta-data objects
		theIndexPackage.createPackageContents();
		theScopePackage.createPackageContents();
		theSearchPackage.createPackageContents();

		// Initialize created meta-data
		theIndexPackage.initializePackageContents();
		theScopePackage.initializePackageContents();
		theSearchPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theIndexPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(IndexPackage.eNS_URI, theIndexPackage);
		return theIndexPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDesignerProject() {
		return designerProjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesignerProject_EntityElements() {
		return (EReference)designerProjectEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesignerProject_DecisionTableElements() {
		return (EReference)designerProjectEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesignerProject_ArchiveElements() {
		return (EReference)designerProjectEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesignerProject_RuleElements() {
		return (EReference)designerProjectEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignerProject_RootPath() {
		return (EAttribute)designerProjectEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignerProject_LastPersisted() {
		return (EAttribute)designerProjectEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesignerProject_ReferencedProjects() {
		return (EReference)designerProjectEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesignerProject_DriverManager() {
		return (EReference)designerProjectEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDesignerProject_DomainInstanceElements() {
		return (EReference)designerProjectEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignerProject_ArchivePath() {
		return (EAttribute)designerProjectEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignerProject_Version() {
		return (EAttribute)designerProjectEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStructuredElement() {
		return structuredElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDesignerElement() {
		return designerElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignerElement_Name() {
		return (EAttribute)designerElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignerElement_ElementType() {
		return (EAttribute)designerElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignerElement_LazilyCreated() {
		return (EAttribute)designerElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignerElement_LastModified() {
		return (EAttribute)designerElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDesignerElement_CreationDate() {
		return (EAttribute)designerElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMemberElement() {
		return memberElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMemberElement_Offset() {
		return (EAttribute)memberElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMemberElement_Length() {
		return (EAttribute)memberElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElementReference() {
		return elementReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementReference_Name() {
		return (EAttribute)elementReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementReference_AttRef() {
		return (EAttribute)elementReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementReference_TypeRef() {
		return (EAttribute)elementReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementReference_Offset() {
		return (EAttribute)elementReferenceEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementReference_Length() {
		return (EAttribute)elementReferenceEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementReference_Qualifier() {
		return (EReference)elementReferenceEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementReference_Array() {
		return (EAttribute)elementReferenceEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementReference_Method() {
		return (EAttribute)elementReferenceEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementReference_Binding() {
		return (EAttribute)elementReferenceEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTypeElement() {
		return typeElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTypeElement_Folder() {
		return (EAttribute)typeElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntityElement() {
		return entityElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEntityElement_Entity() {
		return (EReference)entityElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStateMachineElement() {
		return stateMachineElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getStateMachineElement_CompilableScopes() {
		return (EReference)stateMachineElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStateMachineElement_IndexName() {
		return (EAttribute)stateMachineElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEventElement() {
		return eventElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEventElement_ExpiryActionScopeEntry() {
		return (EReference)eventElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDecisionTableElement() {
		return decisionTableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDecisionTableElement_Implementation() {
		return (EReference)decisionTableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getArchiveElement() {
		return archiveElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getArchiveElement_Archive() {
		return (EReference)archiveElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRuleElement() {
		return ruleElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleElement_Rule() {
		return (EReference)ruleElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleElement_Virtual() {
		return (EAttribute)ruleElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleElement_Scope() {
		return (EReference)ruleElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRuleElement_GlobalVariables() {
		return (EReference)ruleElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRuleElement_IndexName() {
		return (EAttribute)ruleElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVariableDefinition() {
		return variableDefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariableDefinition_Type() {
		return (EAttribute)variableDefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVariableDefinition_Array() {
		return (EAttribute)variableDefinitionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocalVariableDef() {
		return localVariableDefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGlobalVariableDef() {
		return globalVariableDefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getInstanceElement() {
		return instanceElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getInstanceElement_Instances() {
		return (EReference)instanceElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getInstanceElement_EntityPath() {
		return (EAttribute)instanceElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedElement() {
		return sharedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSharedElement_ArchivePath() {
		return (EAttribute)sharedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSharedElement_EntryPath() {
		return (EAttribute)sharedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSharedElement_FileName() {
		return (EAttribute)sharedElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedDecisionTableElement() {
		return sharedDecisionTableElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSharedDecisionTableElement_SharedImplementation() {
		return (EReference)sharedDecisionTableElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedRuleElement() {
		return sharedRuleElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedStateMachineElement() {
		return sharedStateMachineElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedEventElement() {
		return sharedEventElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedEntityElement() {
		return sharedEntityElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSharedEntityElement_SharedEntity() {
		return (EReference)sharedEntityElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedProcessElement() {
		return sharedProcessElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSharedProcessElement_SharedProcess() {
		return (EReference)sharedProcessElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessElement() {
		return processElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessElement_Process() {
		return (EReference)processElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIStructuredElementVisitor() {
		return iStructuredElementVisitorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaElement() {
		return javaElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJavaElement_JavaSource() {
		return (EReference)javaElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedJavaElement() {
		return sharedJavaElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getJavaResourceElement() {
		return javaResourceElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getJavaResourceElement_JavaResource() {
		return (EReference)javaResourceElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSharedJavaResourceElement() {
		return sharedJavaResourceElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBindingVariableDef() {
		return bindingVariableDefEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElementContainer() {
		return elementContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementContainer_Entries() {
		return (EReference)elementContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFolder() {
		return folderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getELEMENT_TYPES() {
		return elemenT_TYPESEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexFactory getIndexFactory() {
		return (IndexFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		designerProjectEClass = createEClass(DESIGNER_PROJECT);
		createEReference(designerProjectEClass, DESIGNER_PROJECT__ENTITY_ELEMENTS);
		createEReference(designerProjectEClass, DESIGNER_PROJECT__DECISION_TABLE_ELEMENTS);
		createEReference(designerProjectEClass, DESIGNER_PROJECT__ARCHIVE_ELEMENTS);
		createEReference(designerProjectEClass, DESIGNER_PROJECT__RULE_ELEMENTS);
		createEAttribute(designerProjectEClass, DESIGNER_PROJECT__ROOT_PATH);
		createEAttribute(designerProjectEClass, DESIGNER_PROJECT__LAST_PERSISTED);
		createEReference(designerProjectEClass, DESIGNER_PROJECT__REFERENCED_PROJECTS);
		createEReference(designerProjectEClass, DESIGNER_PROJECT__DRIVER_MANAGER);
		createEReference(designerProjectEClass, DESIGNER_PROJECT__DOMAIN_INSTANCE_ELEMENTS);
		createEAttribute(designerProjectEClass, DESIGNER_PROJECT__ARCHIVE_PATH);
		createEAttribute(designerProjectEClass, DESIGNER_PROJECT__VERSION);

		structuredElementEClass = createEClass(STRUCTURED_ELEMENT);

		designerElementEClass = createEClass(DESIGNER_ELEMENT);
		createEAttribute(designerElementEClass, DESIGNER_ELEMENT__NAME);
		createEAttribute(designerElementEClass, DESIGNER_ELEMENT__ELEMENT_TYPE);
		createEAttribute(designerElementEClass, DESIGNER_ELEMENT__LAZILY_CREATED);
		createEAttribute(designerElementEClass, DESIGNER_ELEMENT__LAST_MODIFIED);
		createEAttribute(designerElementEClass, DESIGNER_ELEMENT__CREATION_DATE);

		elementContainerEClass = createEClass(ELEMENT_CONTAINER);
		createEReference(elementContainerEClass, ELEMENT_CONTAINER__ENTRIES);

		folderEClass = createEClass(FOLDER);

		memberElementEClass = createEClass(MEMBER_ELEMENT);
		createEAttribute(memberElementEClass, MEMBER_ELEMENT__OFFSET);
		createEAttribute(memberElementEClass, MEMBER_ELEMENT__LENGTH);

		elementReferenceEClass = createEClass(ELEMENT_REFERENCE);
		createEAttribute(elementReferenceEClass, ELEMENT_REFERENCE__NAME);
		createEAttribute(elementReferenceEClass, ELEMENT_REFERENCE__ATT_REF);
		createEAttribute(elementReferenceEClass, ELEMENT_REFERENCE__TYPE_REF);
		createEAttribute(elementReferenceEClass, ELEMENT_REFERENCE__OFFSET);
		createEAttribute(elementReferenceEClass, ELEMENT_REFERENCE__LENGTH);
		createEReference(elementReferenceEClass, ELEMENT_REFERENCE__QUALIFIER);
		createEAttribute(elementReferenceEClass, ELEMENT_REFERENCE__ARRAY);
		createEAttribute(elementReferenceEClass, ELEMENT_REFERENCE__METHOD);
		createEAttribute(elementReferenceEClass, ELEMENT_REFERENCE__BINDING);

		typeElementEClass = createEClass(TYPE_ELEMENT);
		createEAttribute(typeElementEClass, TYPE_ELEMENT__FOLDER);

		stateMachineElementEClass = createEClass(STATE_MACHINE_ELEMENT);
		createEReference(stateMachineElementEClass, STATE_MACHINE_ELEMENT__COMPILABLE_SCOPES);
		createEAttribute(stateMachineElementEClass, STATE_MACHINE_ELEMENT__INDEX_NAME);

		eventElementEClass = createEClass(EVENT_ELEMENT);
		createEReference(eventElementEClass, EVENT_ELEMENT__EXPIRY_ACTION_SCOPE_ENTRY);

		entityElementEClass = createEClass(ENTITY_ELEMENT);
		createEReference(entityElementEClass, ENTITY_ELEMENT__ENTITY);

		decisionTableElementEClass = createEClass(DECISION_TABLE_ELEMENT);
		createEReference(decisionTableElementEClass, DECISION_TABLE_ELEMENT__IMPLEMENTATION);

		archiveElementEClass = createEClass(ARCHIVE_ELEMENT);
		createEReference(archiveElementEClass, ARCHIVE_ELEMENT__ARCHIVE);

		ruleElementEClass = createEClass(RULE_ELEMENT);
		createEReference(ruleElementEClass, RULE_ELEMENT__RULE);
		createEAttribute(ruleElementEClass, RULE_ELEMENT__VIRTUAL);
		createEReference(ruleElementEClass, RULE_ELEMENT__SCOPE);
		createEReference(ruleElementEClass, RULE_ELEMENT__GLOBAL_VARIABLES);
		createEAttribute(ruleElementEClass, RULE_ELEMENT__INDEX_NAME);

		variableDefinitionEClass = createEClass(VARIABLE_DEFINITION);
		createEAttribute(variableDefinitionEClass, VARIABLE_DEFINITION__TYPE);
		createEAttribute(variableDefinitionEClass, VARIABLE_DEFINITION__ARRAY);

		localVariableDefEClass = createEClass(LOCAL_VARIABLE_DEF);

		globalVariableDefEClass = createEClass(GLOBAL_VARIABLE_DEF);

		instanceElementEClass = createEClass(INSTANCE_ELEMENT);
		createEReference(instanceElementEClass, INSTANCE_ELEMENT__INSTANCES);
		createEAttribute(instanceElementEClass, INSTANCE_ELEMENT__ENTITY_PATH);

		sharedElementEClass = createEClass(SHARED_ELEMENT);
		createEAttribute(sharedElementEClass, SHARED_ELEMENT__ARCHIVE_PATH);
		createEAttribute(sharedElementEClass, SHARED_ELEMENT__ENTRY_PATH);
		createEAttribute(sharedElementEClass, SHARED_ELEMENT__FILE_NAME);

		sharedDecisionTableElementEClass = createEClass(SHARED_DECISION_TABLE_ELEMENT);
		createEReference(sharedDecisionTableElementEClass, SHARED_DECISION_TABLE_ELEMENT__SHARED_IMPLEMENTATION);

		sharedRuleElementEClass = createEClass(SHARED_RULE_ELEMENT);

		sharedStateMachineElementEClass = createEClass(SHARED_STATE_MACHINE_ELEMENT);

		sharedEventElementEClass = createEClass(SHARED_EVENT_ELEMENT);

		sharedEntityElementEClass = createEClass(SHARED_ENTITY_ELEMENT);
		createEReference(sharedEntityElementEClass, SHARED_ENTITY_ELEMENT__SHARED_ENTITY);

		sharedProcessElementEClass = createEClass(SHARED_PROCESS_ELEMENT);
		createEReference(sharedProcessElementEClass, SHARED_PROCESS_ELEMENT__SHARED_PROCESS);

		processElementEClass = createEClass(PROCESS_ELEMENT);
		createEReference(processElementEClass, PROCESS_ELEMENT__PROCESS);

		iStructuredElementVisitorEClass = createEClass(ISTRUCTURED_ELEMENT_VISITOR);

		javaElementEClass = createEClass(JAVA_ELEMENT);
		createEReference(javaElementEClass, JAVA_ELEMENT__JAVA_SOURCE);

		sharedJavaElementEClass = createEClass(SHARED_JAVA_ELEMENT);

		javaResourceElementEClass = createEClass(JAVA_RESOURCE_ELEMENT);
		createEReference(javaResourceElementEClass, JAVA_RESOURCE_ELEMENT__JAVA_RESOURCE);

		sharedJavaResourceElementEClass = createEClass(SHARED_JAVA_RESOURCE_ELEMENT);

		bindingVariableDefEClass = createEClass(BINDING_VARIABLE_DEF);

		// Create enums
		elemenT_TYPESEEnum = createEEnum(ELEMENT_TYPES);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ScopePackage theScopePackage = (ScopePackage)EPackage.Registry.INSTANCE.getEPackage(ScopePackage.eNS_URI);
		SearchPackage theSearchPackage = (SearchPackage)EPackage.Registry.INSTANCE.getEPackage(SearchPackage.eNS_URI);
		ChannelPackage theChannelPackage = (ChannelPackage)EPackage.Registry.INSTANCE.getEPackage(ChannelPackage.eNS_URI);
		ModelPackage theModelPackage = (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);
		ArchivePackage theArchivePackage = (ArchivePackage)EPackage.Registry.INSTANCE.getEPackage(ArchivePackage.eNS_URI);
		RulePackage theRulePackage = (RulePackage)EPackage.Registry.INSTANCE.getEPackage(RulePackage.eNS_URI);
		ElementPackage theElementPackage = (ElementPackage)EPackage.Registry.INSTANCE.getEPackage(ElementPackage.eNS_URI);
		JavaPackage theJavaPackage = (JavaPackage)EPackage.Registry.INSTANCE.getEPackage(JavaPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theScopePackage);
		getESubpackages().add(theSearchPackage);

		// Create type parameters
		ETypeParameter instanceElementEClass_T = addETypeParameter(instanceElementEClass, "T");

		// Set bounds for type parameters
		EGenericType g1 = createEGenericType(theElementPackage.getBaseInstance());
		instanceElementEClass_T.getEBounds().add(g1);

		// Add supertypes to classes
		designerProjectEClass.getESuperTypes().add(this.getElementContainer());
		designerElementEClass.getESuperTypes().add(this.getStructuredElement());
		elementContainerEClass.getESuperTypes().add(this.getDesignerElement());
		folderEClass.getESuperTypes().add(this.getElementContainer());
		memberElementEClass.getESuperTypes().add(this.getDesignerElement());
		typeElementEClass.getESuperTypes().add(this.getDesignerElement());
		stateMachineElementEClass.getESuperTypes().add(this.getEntityElement());
		eventElementEClass.getESuperTypes().add(this.getEntityElement());
		entityElementEClass.getESuperTypes().add(this.getTypeElement());
		decisionTableElementEClass.getESuperTypes().add(this.getTypeElement());
		archiveElementEClass.getESuperTypes().add(this.getTypeElement());
		ruleElementEClass.getESuperTypes().add(this.getTypeElement());
		variableDefinitionEClass.getESuperTypes().add(this.getMemberElement());
		localVariableDefEClass.getESuperTypes().add(this.getVariableDefinition());
		globalVariableDefEClass.getESuperTypes().add(this.getVariableDefinition());
		instanceElementEClass.getESuperTypes().add(this.getTypeElement());
		sharedElementEClass.getESuperTypes().add(this.getDesignerElement());
		sharedDecisionTableElementEClass.getESuperTypes().add(this.getSharedElement());
		sharedDecisionTableElementEClass.getESuperTypes().add(this.getDecisionTableElement());
		sharedRuleElementEClass.getESuperTypes().add(this.getSharedElement());
		sharedRuleElementEClass.getESuperTypes().add(this.getRuleElement());
		sharedStateMachineElementEClass.getESuperTypes().add(this.getStateMachineElement());
		sharedStateMachineElementEClass.getESuperTypes().add(this.getSharedEntityElement());
		sharedEventElementEClass.getESuperTypes().add(this.getSharedEntityElement());
		sharedEventElementEClass.getESuperTypes().add(this.getEventElement());
		sharedEntityElementEClass.getESuperTypes().add(this.getSharedElement());
		sharedEntityElementEClass.getESuperTypes().add(this.getEntityElement());
		sharedProcessElementEClass.getESuperTypes().add(this.getSharedElement());
		sharedProcessElementEClass.getESuperTypes().add(this.getProcessElement());
		processElementEClass.getESuperTypes().add(this.getEntityElement());
		javaElementEClass.getESuperTypes().add(this.getEntityElement());
		sharedJavaElementEClass.getESuperTypes().add(this.getJavaElement());
		sharedJavaElementEClass.getESuperTypes().add(this.getSharedEntityElement());
		javaResourceElementEClass.getESuperTypes().add(this.getEntityElement());
		sharedJavaResourceElementEClass.getESuperTypes().add(this.getJavaResourceElement());
		sharedJavaResourceElementEClass.getESuperTypes().add(this.getSharedEntityElement());
		bindingVariableDefEClass.getESuperTypes().add(this.getGlobalVariableDef());

		// Initialize classes and features; add operations and parameters
		initEClass(designerProjectEClass, DesignerProject.class, "DesignerProject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDesignerProject_EntityElements(), this.getEntityElement(), null, "entityElements", null, 0, -1, DesignerProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDesignerProject_DecisionTableElements(), this.getDecisionTableElement(), null, "decisionTableElements", null, 0, -1, DesignerProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDesignerProject_ArchiveElements(), this.getArchiveElement(), null, "archiveElements", null, 0, -1, DesignerProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDesignerProject_RuleElements(), this.getRuleElement(), null, "ruleElements", null, 0, -1, DesignerProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignerProject_RootPath(), ecorePackage.getEString(), "rootPath", null, 1, 1, DesignerProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignerProject_LastPersisted(), ecorePackage.getEDate(), "lastPersisted", null, 1, 1, DesignerProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDesignerProject_ReferencedProjects(), this.getDesignerProject(), null, "referencedProjects", null, 0, -1, DesignerProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDesignerProject_DriverManager(), theChannelPackage.getDriverManager(), null, "driverManager", null, 0, 1, DesignerProject.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		g1 = createEGenericType(this.getInstanceElement());
		EGenericType g2 = createEGenericType();
		g1.getETypeArguments().add(g2);
		initEReference(getDesignerProject_DomainInstanceElements(), g1, null, "domainInstanceElements", null, 0, -1, DesignerProject.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignerProject_ArchivePath(), ecorePackage.getEString(), "archivePath", null, 0, 1, DesignerProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignerProject_Version(), ecorePackage.getEInt(), "version", null, 0, 1, DesignerProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(structuredElementEClass, StructuredElement.class, "StructuredElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(designerElementEClass, DesignerElement.class, "DesignerElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDesignerElement_Name(), ecorePackage.getEString(), "name", null, 1, 1, DesignerElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignerElement_ElementType(), this.getELEMENT_TYPES(), "elementType", null, 1, 1, DesignerElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignerElement_LazilyCreated(), ecorePackage.getEBoolean(), "lazilyCreated", null, 1, 1, DesignerElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignerElement_LastModified(), ecorePackage.getEDate(), "lastModified", null, 1, 1, DesignerElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDesignerElement_CreationDate(), ecorePackage.getEDate(), "creationDate", null, 1, 1, DesignerElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = addEOperation(designerElementEClass, null, "accept", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIStructuredElementVisitor(), "visitor", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(elementContainerEClass, ElementContainer.class, "ElementContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getElementContainer_Entries(), this.getDesignerElement(), null, "entries", null, 0, -1, ElementContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(folderEClass, Folder.class, "Folder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(memberElementEClass, MemberElement.class, "MemberElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMemberElement_Offset(), ecorePackage.getEInt(), "offset", null, 1, 1, MemberElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMemberElement_Length(), ecorePackage.getEInt(), "length", null, 1, 1, MemberElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(elementReferenceEClass, ElementReference.class, "ElementReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getElementReference_Name(), ecorePackage.getEString(), "name", null, 1, 1, ElementReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElementReference_AttRef(), ecorePackage.getEBoolean(), "attRef", null, 1, 1, ElementReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElementReference_TypeRef(), ecorePackage.getEBoolean(), "typeRef", null, 1, 1, ElementReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElementReference_Offset(), ecorePackage.getEInt(), "offset", null, 1, 1, ElementReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElementReference_Length(), ecorePackage.getEInt(), "length", null, 1, 1, ElementReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElementReference_Qualifier(), this.getElementReference(), null, "qualifier", null, 0, 1, ElementReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElementReference_Array(), ecorePackage.getEBoolean(), "array", null, 0, 1, ElementReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElementReference_Method(), ecorePackage.getEBoolean(), "method", null, 0, 1, ElementReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElementReference_Binding(), ecorePackage.getEJavaObject(), "binding", null, 0, 1, ElementReference.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(typeElementEClass, TypeElement.class, "TypeElement", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTypeElement_Folder(), ecorePackage.getEString(), "folder", null, 1, 1, TypeElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stateMachineElementEClass, StateMachineElement.class, "StateMachineElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getStateMachineElement_CompilableScopes(), theScopePackage.getCompilableScopeEntry(), null, "compilableScopes", null, 0, -1, StateMachineElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStateMachineElement_IndexName(), ecorePackage.getEString(), "indexName", null, 1, 1, StateMachineElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eventElementEClass, EventElement.class, "EventElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEventElement_ExpiryActionScopeEntry(), theScopePackage.getCompilableScopeEntry(), null, "expiryActionScopeEntry", null, 0, 1, EventElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityElementEClass, EntityElement.class, "EntityElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEntityElement_Entity(), theModelPackage.getEntity(), null, "entity", null, 1, 1, EntityElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(entityElementEClass, null, "accept", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIStructuredElementVisitor(), "visitor", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(decisionTableElementEClass, DecisionTableElement.class, "DecisionTableElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDecisionTableElement_Implementation(), ecorePackage.getEObject(), null, "implementation", null, 1, 1, DecisionTableElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(decisionTableElementEClass, null, "accept", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIStructuredElementVisitor(), "visitor", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(archiveElementEClass, ArchiveElement.class, "ArchiveElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getArchiveElement_Archive(), theArchivePackage.getArchiveResource(), null, "archive", null, 1, 1, ArchiveElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleElementEClass, RuleElement.class, "RuleElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleElement_Rule(), theRulePackage.getCompilable(), null, "rule", null, 1, 1, RuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleElement_Virtual(), ecorePackage.getEBoolean(), "virtual", null, 0, 1, RuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleElement_Scope(), theScopePackage.getRootScopeBlock(), null, "scope", null, 0, 1, RuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRuleElement_GlobalVariables(), this.getGlobalVariableDef(), null, "globalVariables", null, 0, -1, RuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRuleElement_IndexName(), ecorePackage.getEString(), "indexName", null, 1, 1, RuleElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(ruleElementEClass, null, "accept", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIStructuredElementVisitor(), "visitor", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(ruleElementEClass, null, "doVisitChildren", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIStructuredElementVisitor(), "visitor", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(variableDefinitionEClass, VariableDefinition.class, "VariableDefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVariableDefinition_Type(), ecorePackage.getEString(), "type", null, 0, 1, VariableDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVariableDefinition_Array(), ecorePackage.getEBoolean(), "array", null, 0, 1, VariableDefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = addEOperation(variableDefinitionEClass, null, "accept", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getIStructuredElementVisitor(), "visitor", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(localVariableDefEClass, LocalVariableDef.class, "LocalVariableDef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(globalVariableDefEClass, GlobalVariableDef.class, "GlobalVariableDef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(instanceElementEClass, InstanceElement.class, "InstanceElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		g1 = createEGenericType(instanceElementEClass_T);
		initEReference(getInstanceElement_Instances(), g1, null, "instances", null, 0, -1, InstanceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInstanceElement_EntityPath(), ecorePackage.getEString(), "entityPath", null, 0, 1, InstanceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sharedElementEClass, SharedElement.class, "SharedElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSharedElement_ArchivePath(), ecorePackage.getEString(), "archivePath", null, 0, 1, SharedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSharedElement_EntryPath(), ecorePackage.getEString(), "entryPath", null, 0, 1, SharedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSharedElement_FileName(), ecorePackage.getEString(), "fileName", null, 0, 1, SharedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sharedDecisionTableElementEClass, SharedDecisionTableElement.class, "SharedDecisionTableElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSharedDecisionTableElement_SharedImplementation(), ecorePackage.getEObject(), null, "sharedImplementation", null, 1, 1, SharedDecisionTableElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sharedRuleElementEClass, SharedRuleElement.class, "SharedRuleElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(sharedStateMachineElementEClass, SharedStateMachineElement.class, "SharedStateMachineElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(sharedEventElementEClass, SharedEventElement.class, "SharedEventElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(sharedEntityElementEClass, SharedEntityElement.class, "SharedEntityElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSharedEntityElement_SharedEntity(), theModelPackage.getEntity(), null, "sharedEntity", null, 1, 1, SharedEntityElement.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sharedProcessElementEClass, SharedProcessElement.class, "SharedProcessElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSharedProcessElement_SharedProcess(), ecorePackage.getEObject(), null, "sharedProcess", null, 0, 1, SharedProcessElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(processElementEClass, ProcessElement.class, "ProcessElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getProcessElement_Process(), ecorePackage.getEObject(), null, "process", null, 0, 1, ProcessElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iStructuredElementVisitorEClass, IStructuredElementVisitor.class, "IStructuredElementVisitor", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "preVisit", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStructuredElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "postVisit", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStructuredElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visitChildren", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStructuredElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visit", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStructuredElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visitDesignerProject", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDesignerProject(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visitFolder", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getFolder(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visitStateMachineElement", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getStateMachineElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visitEntityElement", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getEntityElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visitDecisionTableElement", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getDecisionTableElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visitRuleElement", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRuleElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visitLocalVariableDefinition", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLocalVariableDef(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visitGlobalVariableDefinition", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getGlobalVariableDef(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visitScopeBlock", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theScopePackage.getScopeBlock(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		op = addEOperation(iStructuredElementVisitorEClass, ecorePackage.getEBoolean(), "visitArchiveElement", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getArchiveElement(), "element", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(javaElementEClass, JavaElement.class, "JavaElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJavaElement_JavaSource(), theJavaPackage.getJavaSource(), null, "javaSource", null, 1, 1, JavaElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sharedJavaElementEClass, SharedJavaElement.class, "SharedJavaElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(javaResourceElementEClass, JavaResourceElement.class, "JavaResourceElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getJavaResourceElement_JavaResource(), theJavaPackage.getJavaResource(), null, "javaResource", null, 1, 1, JavaResourceElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sharedJavaResourceElementEClass, SharedJavaResourceElement.class, "SharedJavaResourceElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(bindingVariableDefEClass, BindingVariableDef.class, "BindingVariableDef", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Initialize enums and add enum literals
		initEEnum(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.class, "ELEMENT_TYPES");
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.UNKNOWN);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.CONCEPT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.SCORECARD);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.INSTANCE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.SIMPLE_EVENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.CHANNEL);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.DESTINATION);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.RULE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.RULE_FUNCTION);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.RULE_SET);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.STATE_MACHINE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.TIME_EVENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.ENTERPRISE_ARCHIVE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.BE_ARCHIVE_RESOURCE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.SHARED_ARCHIVE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.PROCESS_ARCHIVE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.ADAPTER_ARCHIVE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.PRIMITIVE_TYPE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.DECISION_TABLE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.METRIC);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.DOMAIN);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.PROJECT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.FOLDER);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.DOMAIN_INSTANCE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.TEXT_CHART_COMPONENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.STATE_MACHINE_COMPONENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.PAGE_SELECTOR_COMPONENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.ALERT_COMPONENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.CONTEXT_ACTION_RULE_SET);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.BLUE_PRINT_COMPONENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.QUERY_MANAGER_COMPONENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.SEARCH_VIEW_COMPONENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.RELATED_ASSETS_COMPONENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.DRILLDOWN_COMPONENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.QUERY);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.VIEW);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.DASHBOARD_PAGE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.ASSET_PAGE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.SEARCH_PAGE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.PAGE_SET);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.SERIES_COLOR);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.TEXT_COMPONENT_COLOR_SET);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.CHART_COMPONENT_COLOR_SET);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.HEADER);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.LOGIN);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.SKIN);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.ROLE_PREFERENCE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.CHART_COMPONENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.TEXT_COMPONENT);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.DATA_SOURCE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.RULE_TEMPLATE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.RULE_TEMPLATE_VIEW);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.PROCESS);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.XSLT_FUNCTION);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.JAVA_SOURCE);
		addEEnumLiteral(elemenT_TYPESEEnum, com.tibco.cep.studio.core.index.model.ELEMENT_TYPES.JAVA_RESOURCE);

		// Create resource
		createResource(eNS_URI);
	}

} //IndexPackageImpl
