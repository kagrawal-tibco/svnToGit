/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.element.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.HISTORY_POLICY;
import com.tibco.cep.designtime.core.model.PROPERTY_TYPES;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.element.Concept;
import com.tibco.cep.designtime.core.model.element.ElementFactory;
import com.tibco.cep.designtime.core.model.element.ElementPackage;
import com.tibco.cep.designtime.core.model.element.PropertyDefinition;
import com.tibco.cep.designtime.core.model.impl.RuleParticipantImpl;
import com.tibco.cep.designtime.core.model.rule.RuleSet;
import com.tibco.cep.designtime.core.model.states.StateMachine;
import com.tibco.cep.designtime.core.model.states.StatesPackage;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.designtime.core.model.validation.ValidationFactory;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Concept</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#getSubConcepts <em>Sub Concepts</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#getRuleSet <em>Rule Set</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#getStateMachinePaths <em>State Machine Paths</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#getRootStateMachinePath <em>Root State Machine Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#getParentConcept <em>Parent Concept</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#getParentConceptPath <em>Parent Concept Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#getSuperConcept <em>Super Concept</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#getSuperConceptPath <em>Super Concept Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#isScorecard <em>Scorecard</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#isMetric <em>Metric</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#isContained <em>Contained</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#isPOJO <em>POJO</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#isTransient <em>Transient</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#getImplClass <em>Impl Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#getSubConceptsPath <em>Sub Concepts Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.element.impl.ConceptImpl#isAutoStartStateMachine <em>Auto Start State Machine</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConceptImpl extends RuleParticipantImpl implements Concept {


	/**
	 * The cached value of the '{@link #getSubConcepts() <em>Sub Concepts</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubConcepts()
	 * @generated
	 * @ordered
	 */
	protected EList<Concept> subConcepts;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<PropertyDefinition> properties;

	/**
	 * The cached value of the '{@link #getRuleSet() <em>Rule Set</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleSet()
	 * @generated
	 * @ordered
	 */
	protected RuleSet ruleSet;

	/**
	 * The cached value of the '{@link #getStateMachinePaths() <em>State Machine Paths</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStateMachinePaths()
	 * @generated
	 * @ordered
	 */
	protected EList<String> stateMachinePaths;

	/**
	 * The default value of the '{@link #getRootStateMachinePath() <em>Root State Machine Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootStateMachinePath()
	 * @generated
	 * @ordered
	 */
	protected static final String ROOT_STATE_MACHINE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRootStateMachinePath() <em>Root State Machine Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootStateMachinePath()
	 * @generated
	 * @ordered
	 */
	protected String rootStateMachinePath = ROOT_STATE_MACHINE_PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getParentConcept() <em>Parent Concept</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentConcept()
	 * @generated
	 * @ordered
	 */
	protected Concept parentConcept;

	/**
	 * The default value of the '{@link #getParentConceptPath() <em>Parent Concept Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentConceptPath()
	 * @generated
	 * @ordered
	 */
	protected static final String PARENT_CONCEPT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getParentConceptPath() <em>Parent Concept Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParentConceptPath()
	 * @generated
	 * @ordered
	 */
	protected String parentConceptPath = PARENT_CONCEPT_PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSuperConcept() <em>Super Concept</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperConcept()
	 * @generated
	 * @ordered
	 */
	protected Concept superConcept;

	/**
	 * The default value of the '{@link #getSuperConceptPath() <em>Super Concept Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperConceptPath()
	 * @generated
	 * @ordered
	 */
	protected static final String SUPER_CONCEPT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSuperConceptPath() <em>Super Concept Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperConceptPath()
	 * @generated
	 * @ordered
	 */
	protected String superConceptPath = SUPER_CONCEPT_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #isScorecard() <em>Scorecard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isScorecard()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SCORECARD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isScorecard() <em>Scorecard</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isScorecard()
	 * @generated
	 * @ordered
	 */
	protected boolean scorecard = SCORECARD_EDEFAULT;

	/**
	 * The default value of the '{@link #isMetric() <em>Metric</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMetric()
	 * @generated
	 * @ordered
	 */
	protected static final boolean METRIC_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isMetric() <em>Metric</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isMetric()
	 * @generated
	 * @ordered
	 */
	protected boolean metric = METRIC_EDEFAULT;

	/**
	 * The default value of the '{@link #isContained() <em>Contained</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isContained()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONTAINED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isContained() <em>Contained</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isContained()
	 * @generated
	 * @ordered
	 */
	protected boolean contained = CONTAINED_EDEFAULT;

	/**
	 * The default value of the '{@link #isPOJO() <em>POJO</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPOJO()
	 * @generated
	 * @ordered
	 */
	protected static final boolean POJO_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPOJO() <em>POJO</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPOJO()
	 * @generated
	 * @ordered
	 */
	protected boolean pojo = POJO_EDEFAULT;

	/**
	 * The default value of the '{@link #isTransient() <em>Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTransient()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TRANSIENT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isTransient() <em>Transient</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTransient()
	 * @generated
	 * @ordered
	 */
	protected boolean transient_ = TRANSIENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getImplClass() <em>Impl Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplClass()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPL_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplClass() <em>Impl Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplClass()
	 * @generated
	 * @ordered
	 */
	protected String implClass = IMPL_CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSubConceptsPath() <em>Sub Concepts Path</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubConceptsPath()
	 * @generated
	 * @ordered
	 */
	protected EList<String> subConceptsPath;

	/**
	 * The default value of the '{@link #isAutoStartStateMachine() <em>Auto Start State Machine</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoStartStateMachine()
	 * @generated
	 * @ordered
	 */
	protected static final boolean AUTO_START_STATE_MACHINE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isAutoStartStateMachine() <em>Auto Start State Machine</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isAutoStartStateMachine()
	 * @generated
	 * @ordered
	 */
	protected boolean autoStartStateMachine = AUTO_START_STATE_MACHINE_EDEFAULT;
	
	/**
	 * @generated NOT
	 * 
	 */
	protected Map<String,PropertyDefinition> attrDefinitionMap = new LinkedHashMap<String,PropertyDefinition>();
	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConceptImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementPackage.Literals.CONCEPT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Concept> getSubConcepts() {
		if (subConcepts == null) {
			subConcepts = new EObjectResolvingEList<Concept>(Concept.class, this, ElementPackage.CONCEPT__SUB_CONCEPTS);
		}
		return subConcepts;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<PropertyDefinition> getProperties() {
		if (properties == null) {
			properties = new EObjectContainmentEList<PropertyDefinition>(PropertyDefinition.class, this, ElementPackage.CONCEPT__PROPERTIES);
		}
		ECollections.sort(properties, new Comparator<com.tibco.cep.designtime.core.model.element.PropertyDefinition>() {
            public int compare(com.tibco.cep.designtime.core.model.element.PropertyDefinition o1, com.tibco.cep.designtime.core.model.element.PropertyDefinition o2) {
            	com.tibco.cep.designtime.core.model.element.PropertyDefinition dpd1 =  o1;
            	com.tibco.cep.designtime.core.model.element.PropertyDefinition dpd2 = o2;
                return dpd1.getOrder() - dpd2.getOrder();
            }
        });
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleSet getRuleSet() {
		if (ruleSet != null && ruleSet.eIsProxy()) {
			InternalEObject oldRuleSet = (InternalEObject)ruleSet;
			ruleSet = (RuleSet)eResolveProxy(oldRuleSet);
			if (ruleSet != oldRuleSet) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ElementPackage.CONCEPT__RULE_SET, oldRuleSet, ruleSet));
			}
		}
		return ruleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuleSet basicGetRuleSet() {
		return ruleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleSet(RuleSet newRuleSet) {
		RuleSet oldRuleSet = ruleSet;
		ruleSet = newRuleSet;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__RULE_SET, oldRuleSet, ruleSet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getStateMachinePaths() {
		if (stateMachinePaths == null) {
			stateMachinePaths = new EDataTypeUniqueEList<String>(String.class, this, ElementPackage.CONCEPT__STATE_MACHINE_PATHS);
		}
		return stateMachinePaths;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRootStateMachinePath() {
		return rootStateMachinePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootStateMachinePath(String newRootStateMachinePath) {
		String oldRootStateMachinePath = rootStateMachinePath;
		rootStateMachinePath = newRootStateMachinePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__ROOT_STATE_MACHINE_PATH, oldRootStateMachinePath, rootStateMachinePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<StateMachine> getStateMachines() {
		EList<StateMachine> stateMachines = 
			new EObjectResolvingEList<StateMachine>(StateMachine.class, this, StatesPackage.STATE_MACHINE);
		if (stateMachinePaths != null) {
			for (String path : stateMachinePaths) {
				StateMachine stateMachine = 
					(StateMachine)CommonIndexUtils.getEntity(ownerProjectName, path, ELEMENT_TYPES.STATE_MACHINE);
				if (stateMachine == null) {
					System.err.println("Could not find state machine at "+path);
					continue;
				}
				stateMachines.add(stateMachine);
			}
		}
		return stateMachines;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StateMachine getRootStateMachine() {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Concept getParentConcept() {
		if (parentConcept != null && parentConcept.eIsProxy()) {
			InternalEObject oldParentConcept = (InternalEObject)parentConcept;
			parentConcept = (Concept)eResolveProxy(oldParentConcept);
			if (parentConcept != oldParentConcept) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ElementPackage.CONCEPT__PARENT_CONCEPT, oldParentConcept, parentConcept));
			}
		}
		if (parentConcept == null){
			String parentConceptPath = this.getParentConceptPath();
			if (parentConceptPath != null && parentConceptPath.trim().length() >0){
				parentConcept = CommonIndexUtils.getConcept(ownerProjectName, parentConceptPath);
			}
		}
		return parentConcept;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Concept basicGetParentConcept() {
		return parentConcept;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentConcept(Concept newParentConcept) {
		Concept oldParentConcept = parentConcept;
		parentConcept = newParentConcept;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__PARENT_CONCEPT, oldParentConcept, parentConcept));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Concept getSuperConcept() {
		if (superConcept != null && superConcept.eIsProxy()) {
			InternalEObject oldSuperConcept = (InternalEObject)superConcept;
			superConcept = (Concept)eResolveProxy(oldSuperConcept);
			if (superConcept != oldSuperConcept) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ElementPackage.CONCEPT__SUPER_CONCEPT, oldSuperConcept, superConcept));
			}
		}
		//if (superConcept == null){
			String superConceptPath = getSuperConceptPath();			
			if (superConceptPath != null && superConceptPath.trim().length() > 0){
				superConcept = CommonIndexUtils.getConcept(ownerProjectName, superConceptPath);
			}
		//}
		return superConcept;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Concept basicGetSuperConcept() {
		return superConcept;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperConcept(Concept newSuperConcept) {
		Concept oldSuperConcept = superConcept;
		superConcept = newSuperConcept;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__SUPER_CONCEPT, oldSuperConcept, superConcept));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSuperConceptPath() {
		return superConceptPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperConceptPath(String newSuperConceptPath) {
		String oldSuperConceptPath = superConceptPath;
		superConceptPath = newSuperConceptPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__SUPER_CONCEPT_PATH, oldSuperConceptPath, superConceptPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isScorecard() {
		return scorecard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isMetric() {
		return metric;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetric(boolean newMetric) {
		boolean oldMetric = metric;
		metric = newMetric;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__METRIC, oldMetric, metric));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScorecard(boolean newScorecard) {
		boolean oldScorecard = scorecard;
		scorecard = newScorecard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__SCORECARD, oldScorecard, scorecard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isContained() {
		return getParentConceptPath() != null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContained(boolean newContained) {
		boolean oldContained = contained;
		contained = newContained;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__CONTAINED, oldContained, contained));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPOJO() {
		return pojo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPOJO(boolean newPOJO) {
		boolean oldPOJO = pojo;
		pojo = newPOJO;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__POJO, oldPOJO, pojo));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTransient() {
		return transient_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransient(boolean newTransient) {
		boolean oldTransient = transient_;
		transient_ = newTransient;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__TRANSIENT, oldTransient, transient_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplClass() {
		return implClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplClass(String newImplClass) {
		String oldImplClass = implClass;
		implClass = newImplClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__IMPL_CLASS, oldImplClass, implClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getSubConceptsPath() {
		if (subConceptsPath == null) {
			subConceptsPath = new EDataTypeUniqueEList<String>(String.class, this, ElementPackage.CONCEPT__SUB_CONCEPTS_PATH);
		}
		return subConceptsPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getParentConceptPath() {
//		if (parentConceptPath == null) {
			// loop through all concepts and calculate path.  Do not cache result, as it could be reset later
			List<Entity> list = CommonIndexUtils.getAllEntities(getOwnerProjectName(), ELEMENT_TYPES.CONCEPT);
			for(Entity entity:list){
				Concept concept = (Concept)entity;
				for(PropertyDefinition propertyDefinition:concept.getAllPropertyDefinitions()){
					if(propertyDefinition.getType() == PROPERTY_TYPES.CONCEPT && getFullPath().equals(propertyDefinition.getConceptTypePath())) {
						return propertyDefinition.getOwnerPath();
					}
				}
			}
//		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParentConceptPath(String newParentConceptPath) {
		String oldParentConceptPath = parentConceptPath;
		parentConceptPath = newParentConceptPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__PARENT_CONCEPT_PATH, oldParentConceptPath, parentConceptPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAutoStartStateMachine() {
		return autoStartStateMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAutoStartStateMachine(boolean newAutoStartStateMachine) {
		boolean oldAutoStartStateMachine = autoStartStateMachine;
		autoStartStateMachine = newAutoStartStateMachine;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementPackage.CONCEPT__AUTO_START_STATE_MACHINE, oldAutoStartStateMachine, autoStartStateMachine));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<PropertyDefinition> getAllProperties() {
		EList<PropertyDefinition> allProperties = new EDataTypeEList<PropertyDefinition>(PropertyDefinition.class, this,  ElementPackage.CONCEPT__PROPERTIES);
		collectProperties(this, allProperties);
		return allProperties;
	}

	private void collectProperties(Concept concept, EList<PropertyDefinition> properties) {
		Concept superConcept = concept.getSuperConcept();	

		if (superConcept != null){
			collectProperties(superConcept, properties);
		}
		
		// add the local properties *after* the super properties
		properties.addAll(concept.getProperties());
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<DomainInstance> getAllDomainInstances() {
		EList<DomainInstance> allDomainInstances = 
			new EDataTypeEList<DomainInstance>(DomainInstance.class, 
					                           this,  
					                           ElementPackage.PROPERTY_DEFINITION__DOMAIN_INSTANCES);
		EList<PropertyDefinition> conceptProperties = getProperties();
		
		for (PropertyDefinition propertyDefinition : conceptProperties) {
			allDomainInstances.addAll(propertyDefinition.getDomainInstances());
		}
		return allDomainInstances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * 
	 */
	public EList<PropertyDefinition> getPropertyDefinitions(boolean localOnly) {
		EList<PropertyDefinition> pDefs = 
			(localOnly == true) ? getProperties(): _getAllPropertyDefinitions();
		return pDefs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void _getPropertyDefinitions(Concept cept, EList<PropertyDefinition> props) {
		if(cept.getSuperConcept() != null) {
			_getPropertyDefinitions(cept.getSuperConcept(),props);
		}
		java.util.Iterator<com.tibco.cep.designtime.core.model.element.PropertyDefinition> localProperties = cept.getProperties().iterator();
		while(localProperties.hasNext()) {
			props.add(localProperties.next());
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<PropertyDefinition> getAllPropertyDefinitions() {
		EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> 
		allProperties = new EDataTypeEList<com.tibco.cep.designtime.core.model.element.PropertyDefinition>(PropertyDefinition.class, this, ElementPackage.CONCEPT__PROPERTIES);
		_getPropertyDefinitions(this,allProperties);
		return allProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<PropertyDefinition> _getAllPropertyDefinitions() {
		EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> allProperties = new EDataTypeEList<com.tibco.cep.designtime.core.model.element.PropertyDefinition>(PropertyDefinition.class, this, ElementPackage.CONCEPT__PROPERTIES);
		EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> localPropertyDefinitions = getProperties();
		java.util.Iterator<com.tibco.cep.designtime.core.model.element.PropertyDefinition> lIterator = localPropertyDefinitions
				.iterator();
		while (lIterator.hasNext()) {
			allProperties.add(lIterator.next());
		}
		com.tibco.cep.designtime.core.model.element.Concept ancestor = getSuperConcept();
		while (ancestor != null) {
			EList<com.tibco.cep.designtime.core.model.element.PropertyDefinition> inheritedPDs = ancestor
					.getProperties();
			java.util.Iterator<com.tibco.cep.designtime.core.model.element.PropertyDefinition> inhIter = inheritedPDs
					.iterator();
			while (inhIter.hasNext()) {
				allProperties.add(inhIter.next());
			}
			ancestor = ancestor.getSuperConcept();
		}
		return allProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ElementPackage.CONCEPT__PROPERTIES:
				return ((InternalEList<?>)getProperties()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ElementPackage.CONCEPT__SUB_CONCEPTS:
				return getSubConcepts();
			case ElementPackage.CONCEPT__PROPERTIES:
				return getProperties();
			case ElementPackage.CONCEPT__RULE_SET:
				if (resolve) return getRuleSet();
				return basicGetRuleSet();
			case ElementPackage.CONCEPT__STATE_MACHINE_PATHS:
				return getStateMachinePaths();
			case ElementPackage.CONCEPT__ROOT_STATE_MACHINE_PATH:
				return getRootStateMachinePath();
			case ElementPackage.CONCEPT__PARENT_CONCEPT:
				if (resolve) return getParentConcept();
				return basicGetParentConcept();
			case ElementPackage.CONCEPT__PARENT_CONCEPT_PATH:
				return getParentConceptPath();
			case ElementPackage.CONCEPT__SUPER_CONCEPT:
				if (resolve) return getSuperConcept();
				return basicGetSuperConcept();
			case ElementPackage.CONCEPT__SUPER_CONCEPT_PATH:
				return getSuperConceptPath();
			case ElementPackage.CONCEPT__SCORECARD:
				return isScorecard();
			case ElementPackage.CONCEPT__METRIC:
				return isMetric();
			case ElementPackage.CONCEPT__CONTAINED:
				return isContained();
			case ElementPackage.CONCEPT__POJO:
				return isPOJO();
			case ElementPackage.CONCEPT__TRANSIENT:
				return isTransient();
			case ElementPackage.CONCEPT__IMPL_CLASS:
				return getImplClass();
			case ElementPackage.CONCEPT__SUB_CONCEPTS_PATH:
				return getSubConceptsPath();
			case ElementPackage.CONCEPT__AUTO_START_STATE_MACHINE:
				return isAutoStartStateMachine();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ElementPackage.CONCEPT__SUB_CONCEPTS:
				getSubConcepts().clear();
				getSubConcepts().addAll((Collection<? extends Concept>)newValue);
				return;
			case ElementPackage.CONCEPT__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends PropertyDefinition>)newValue);
				return;
			case ElementPackage.CONCEPT__RULE_SET:
				setRuleSet((RuleSet)newValue);
				return;
			case ElementPackage.CONCEPT__STATE_MACHINE_PATHS:
				getStateMachinePaths().clear();
				getStateMachinePaths().addAll((Collection<? extends String>)newValue);
				return;
			case ElementPackage.CONCEPT__ROOT_STATE_MACHINE_PATH:
				setRootStateMachinePath((String)newValue);
				return;
			case ElementPackage.CONCEPT__PARENT_CONCEPT:
				setParentConcept((Concept)newValue);
				return;
			case ElementPackage.CONCEPT__PARENT_CONCEPT_PATH:
				setParentConceptPath((String)newValue);
				return;
			case ElementPackage.CONCEPT__SUPER_CONCEPT:
				setSuperConcept((Concept)newValue);
				return;
			case ElementPackage.CONCEPT__SUPER_CONCEPT_PATH:
				setSuperConceptPath((String)newValue);
				return;
			case ElementPackage.CONCEPT__SCORECARD:
				setScorecard((Boolean)newValue);
				return;
			case ElementPackage.CONCEPT__METRIC:
				setMetric((Boolean)newValue);
				return;
			case ElementPackage.CONCEPT__CONTAINED:
				setContained((Boolean)newValue);
				return;
			case ElementPackage.CONCEPT__POJO:
				setPOJO((Boolean)newValue);
				return;
			case ElementPackage.CONCEPT__TRANSIENT:
				setTransient((Boolean)newValue);
				return;
			case ElementPackage.CONCEPT__IMPL_CLASS:
				setImplClass((String)newValue);
				return;
			case ElementPackage.CONCEPT__SUB_CONCEPTS_PATH:
				getSubConceptsPath().clear();
				getSubConceptsPath().addAll((Collection<? extends String>)newValue);
				return;
			case ElementPackage.CONCEPT__AUTO_START_STATE_MACHINE:
				setAutoStartStateMachine((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ElementPackage.CONCEPT__SUB_CONCEPTS:
				getSubConcepts().clear();
				return;
			case ElementPackage.CONCEPT__PROPERTIES:
				getProperties().clear();
				return;
			case ElementPackage.CONCEPT__RULE_SET:
				setRuleSet((RuleSet)null);
				return;
			case ElementPackage.CONCEPT__STATE_MACHINE_PATHS:
				getStateMachinePaths().clear();
				return;
			case ElementPackage.CONCEPT__ROOT_STATE_MACHINE_PATH:
				setRootStateMachinePath(ROOT_STATE_MACHINE_PATH_EDEFAULT);
				return;
			case ElementPackage.CONCEPT__PARENT_CONCEPT:
				setParentConcept((Concept)null);
				return;
			case ElementPackage.CONCEPT__PARENT_CONCEPT_PATH:
				setParentConceptPath(PARENT_CONCEPT_PATH_EDEFAULT);
				return;
			case ElementPackage.CONCEPT__SUPER_CONCEPT:
				setSuperConcept((Concept)null);
				return;
			case ElementPackage.CONCEPT__SUPER_CONCEPT_PATH:
				setSuperConceptPath(SUPER_CONCEPT_PATH_EDEFAULT);
				return;
			case ElementPackage.CONCEPT__SCORECARD:
				setScorecard(SCORECARD_EDEFAULT);
				return;
			case ElementPackage.CONCEPT__METRIC:
				setMetric(METRIC_EDEFAULT);
				return;
			case ElementPackage.CONCEPT__CONTAINED:
				setContained(CONTAINED_EDEFAULT);
				return;
			case ElementPackage.CONCEPT__POJO:
				setPOJO(POJO_EDEFAULT);
				return;
			case ElementPackage.CONCEPT__TRANSIENT:
				setTransient(TRANSIENT_EDEFAULT);
				return;
			case ElementPackage.CONCEPT__IMPL_CLASS:
				setImplClass(IMPL_CLASS_EDEFAULT);
				return;
			case ElementPackage.CONCEPT__SUB_CONCEPTS_PATH:
				getSubConceptsPath().clear();
				return;
			case ElementPackage.CONCEPT__AUTO_START_STATE_MACHINE:
				setAutoStartStateMachine(AUTO_START_STATE_MACHINE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ElementPackage.CONCEPT__SUB_CONCEPTS:
				return subConcepts != null && !subConcepts.isEmpty();
			case ElementPackage.CONCEPT__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case ElementPackage.CONCEPT__RULE_SET:
				return ruleSet != null;
			case ElementPackage.CONCEPT__STATE_MACHINE_PATHS:
				return stateMachinePaths != null && !stateMachinePaths.isEmpty();
			case ElementPackage.CONCEPT__ROOT_STATE_MACHINE_PATH:
				return ROOT_STATE_MACHINE_PATH_EDEFAULT == null ? rootStateMachinePath != null : !ROOT_STATE_MACHINE_PATH_EDEFAULT.equals(rootStateMachinePath);
			case ElementPackage.CONCEPT__PARENT_CONCEPT:
				return parentConcept != null;
			case ElementPackage.CONCEPT__PARENT_CONCEPT_PATH:
				return PARENT_CONCEPT_PATH_EDEFAULT == null ? parentConceptPath != null : !PARENT_CONCEPT_PATH_EDEFAULT.equals(parentConceptPath);
			case ElementPackage.CONCEPT__SUPER_CONCEPT:
				return superConcept != null;
			case ElementPackage.CONCEPT__SUPER_CONCEPT_PATH:
				return SUPER_CONCEPT_PATH_EDEFAULT == null ? superConceptPath != null : !SUPER_CONCEPT_PATH_EDEFAULT.equals(superConceptPath);
			case ElementPackage.CONCEPT__SCORECARD:
				return scorecard != SCORECARD_EDEFAULT;
			case ElementPackage.CONCEPT__METRIC:
				return metric != METRIC_EDEFAULT;
			case ElementPackage.CONCEPT__CONTAINED:
				return contained != CONTAINED_EDEFAULT;
			case ElementPackage.CONCEPT__POJO:
				return pojo != POJO_EDEFAULT;
			case ElementPackage.CONCEPT__TRANSIENT:
				return transient_ != TRANSIENT_EDEFAULT;
			case ElementPackage.CONCEPT__IMPL_CLASS:
				return IMPL_CLASS_EDEFAULT == null ? implClass != null : !IMPL_CLASS_EDEFAULT.equals(implClass);
			case ElementPackage.CONCEPT__SUB_CONCEPTS_PATH:
				return subConceptsPath != null && !subConceptsPath.isEmpty();
			case ElementPackage.CONCEPT__AUTO_START_STATE_MACHINE:
				return autoStartStateMachine != AUTO_START_STATE_MACHINE_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (stateMachinePaths: ");
		result.append(stateMachinePaths);
		result.append(", rootStateMachinePath: ");
		result.append(rootStateMachinePath);
		result.append(", parentConceptPath: ");
		result.append(parentConceptPath);
		result.append(", superConceptPath: ");
		result.append(superConceptPath);
		result.append(", scorecard: ");
		result.append(scorecard);
		result.append(", metric: ");
		result.append(metric);
		result.append(", contained: ");
		result.append(contained);
		result.append(", POJO: ");
		result.append(pojo);
		result.append(", transient: ");
		result.append(transient_);
		result.append(", implClass: ");
		result.append(implClass);
		result.append(", subConceptsPath: ");
		result.append(subConceptsPath);
		result.append(", autoStartStateMachine: ");
		result.append(autoStartStateMachine);
		result.append(')');
		return result.toString();
	}

	public PropertyDefinition getPropertyDefinition(String name , boolean localOnly) {
		// TODO Auto-generated method stub
		if (name == null) return null;
		for (PropertyDefinition propdDefinition : getProperties()){
			if (name.equals(propdDefinition.getName())){
				return propdDefinition;
			}
		}
		if (!localOnly){
			Concept superConcept = this.getSuperConcept();
			/*
			if (superConcept == null){
				String superConceptPath = this.getSuperConceptPath();
				if (superConceptPath != null && superConceptPath.trim().length() > 0){
					superConcept = IndexUtils.getConcept(ownerProjectName, superConceptPath);
				}
			}
			*/
			while (superConcept != null){
				for (PropertyDefinition propDef : superConcept.getProperties()){
					if (name.equals(propDef.getName())) return propDef;						
				}
				//Concept temp = superConcept;
				superConcept = superConcept.getSuperConcept();
				/*
				if (superConcept == null ){
					String superConceptpath = temp.getSuperConceptPath();
					if (superConceptpath != null && superConceptpath.trim().length() > 0){
						superConcept = IndexUtils.getConcept(ownerProjectName, superConceptpath);
					}
					
				}
				*/
			
			}
		}
		return null;
		
	}
	public PropertyDefinition getPropertyDefinition(String name) {
		if (name == null) return null;
		PropertyDefinition propDef = getPropertyDefinition(name, false);
		// check for sub concepts also
		if (propDef == null){
			List<String> subConceptsPath = this.getSubConceptsPath();
			for (String subConceptPath : subConceptsPath){
				if (subConceptPath.trim().length() > 0){
					Concept subConcept = CommonIndexUtils.getConcept(ownerProjectName, subConceptPath);
					for (PropertyDefinition pDef : subConcept.getProperties()){
						if (name.equals(pDef.getName())){
							return pDef;
						}
					}
				}
			}
		}
		return null;
	}

	
	public boolean isA(Concept concept) {
	    if (concept == null) {
	        return false;
	    }
	    if (this.isScorecard() && !concept.isScorecard()){
	    	return false;
	    }
	    if (!this.isScorecard() && concept.isScorecard()){
	    	return false;
	    }

	    Concept ptr = this;
	    while (ptr != null) {
	    	/* Found a match */
	    	// can't use equals here, as the concepts could have been loaded in two different places (thus, == would fail)
	    	if (ptr.getFullPath().equals(concept.getFullPath())) {
	    		return true;
	    	}

	    	/* Advance the pointer */
	    	//Concept temp = ptr;
	    	ptr = ptr.getSuperConcept();
	    	/*
	         if (ptr == null){
	        	 String superConceptPath = temp.getSuperConceptPath();
	        	 if (superConceptPath != null && superConceptPath.trim().length() > 0){
	        		 ptr = IndexUtils.getConcept(ownerProjectName, superConceptPath);
	        	 }
	         }
	    	 */
	    }
	     return false;
	}
	
	/**
	 * @generated NOT
	 */
	@Override
	public Collection<PropertyDefinition> getAttributeDefinitions() {
		return attrDefinitionMap.values();
	}
	
	/**
	 * @generated NOT
	 * @param allAttributeDefinitions
	 */
	public void setAttributeDefinitions(List<PropertyDefinition> allAttributeDefinitions) {
		attrDefinitionMap.clear();
		for(PropertyDefinition p: allAttributeDefinitions) {
			if(!attrDefinitionMap.containsKey(p.getName())) {
				attrDefinitionMap.put(p.getName(), p);
			}
		}
	}
	
	
	
	/**
	 * @generated NOT
	 */
	public PropertyDefinition getAttributeDefinition (String attributeName){
		if (attributeName.equals("parent") && isContained()) {
        	if(attrDefinitionMap.containsKey(attributeName)){
        		return attrDefinitionMap.get(attributeName);
        	}
           // return new DefaultMutableAttributeDefinition((DefaultMutableOntology) this.getOntology(), "parent", this, false, RDFTypes.CONCEPT_REFERENCE_TYPEID, this.getParentConcept().getFullPath(), PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, null, 1);
            PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
            propDef.setName("parent");
            propDef.setOwnerPath(getFullPath());
            propDef.setArray(false);
            propDef.setType(PROPERTY_TYPES.CONCEPT_REFERENCE);
            propDef.setConceptTypePath(this.getParentConcept().getFullPath());         
            propDef.setHistoryPolicy(HISTORY_POLICY.CHANGES_ONLY_VALUE);
            propDef.setHistorySize(0);
            propDef.setDefaultValue(null);
            propDef.setOrder(1);
            attrDefinitionMap.put("parent",propDef);
            return propDef;
        }
		int result = Arrays.binarySearch(BASE_ATTRIBUTE_NAMES, attributeName);
		if (result >= 0) {
			if(attrDefinitionMap.containsKey(attributeName)){
				return attrDefinitionMap.get(attributeName);
        	}
            PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
            propDef.setName(BASE_ATTRIBUTE_NAMES[result]);
            propDef.setOwnerPath(getFullPath());
            propDef.setArray(false);
            propDef.setConceptTypePath(this.getFullPath());    
            propDef.setHistoryPolicy(HISTORY_POLICY.CHANGES_ONLY_VALUE);
            propDef.setHistorySize(0);
            propDef.setDefaultValue(null);
            propDef.setOrder(1);
            
            // cannot use 'result' in a switch statement here,
            // as the sort changes the order
            if ("extId".equals(attributeName)) {
	            propDef.setType(PROPERTY_TYPES.STRING);
            } else if ("id".equals(attributeName)) {
	            propDef.setType(PROPERTY_TYPES.LONG);
            } else if ("length".equals(attributeName)) {
				// is 'length' an int?
	            propDef.setType(PROPERTY_TYPES.INTEGER);
            }
            attrDefinitionMap.put(propDef.getName(),propDef);
            return propDef;
		}
//		if (attributeName.equals(BASE_ATTRIBUTE_NAMES[0])) {
//            //return new DefaultMutableAttributeDefinition((DefaultMutableOntology) this.getOntology(), BASE_ATTRIBUTE_NAMES[0], this, false, RDFTypes.LONG_TYPEID, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, null, 1);
//            PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
//            propDef.setName(BASE_ATTRIBUTE_NAMES[0]);
//            propDef.setType(PROPERTY_TYPES.LONG);
//            propDef.setOwner(this);
//            propDef.setArray(false);
//            propDef.setConceptTypePath(this.getFullPath());    
//            propDef.setHistoryPolicy(HISTORY_POLICY.CHANGES_ONLY_VALUE);
//            propDef.setHistorySize(0);
//            propDef.setDefaultValue(null);
//            propDef.setOrder(1);
//            return propDef;
//            
//        } else if (attributeName.equals(BASE_ATTRIBUTE_NAMES[1])) {
//            //return new DefaultMutableAttributeDefinition((DefaultMutableOntology) this.getOntology(), BASE_ATTRIBUTE_NAMES[1], this, false, RDFTypes.STRING_TYPEID, null, PropertyDefinition.HISTORY_POLICY_CHANGES_ONLY, 0, null, 1);
//            PropertyDefinition propDef = ElementFactory.eINSTANCE.createPropertyDefinition();
//            propDef.setName(BASE_ATTRIBUTE_NAMES[1]);
//            propDef.setType(PROPERTY_TYPES.STRING);
//            propDef.setOwner(this);
//            propDef.setArray(false);
//            propDef.setConceptTypePath(this.getFullPath());       
//            propDef.setHistoryPolicy(HISTORY_POLICY.CHANGES_ONLY_VALUE);
//            propDef.setHistorySize(0);
//            propDef.setDefaultValue(null);
//            propDef.setOrder(1);
//            return propDef;
//        } 
		return attrDefinitionMap.get(attributeName);
	}
	/**
	 * @generated not
	 * returns the Model Error for Concept Configuration
	 */
	@Override
	public EList<ModelError> getModelErrors() {		
		EList<ModelError> modelErrorList = super.getModelErrors();
		List<Object> argList = new ArrayList<Object>();
        String fullPath = getFullPath();
        /* Check that our super class exists */
        String superConceptPath = getSuperConceptPath();
        if (superConceptPath != null && superConceptPath.trim().length() != 0){
        	Concept superConcept = getSuperConcept();
        	if (superConcept == null){
              	argList.clear();      
            	argList.add(fullPath);
            	argList.add(superConceptPath);        	
                String msg = CommonValidationUtils.formatMessage("ConceptImpl.errors.superConceptIsNull",argList);
               
                ModelError modelError = ValidationFactory.eINSTANCE.createModelError();            
                modelError.setMessage(msg);
                modelError.setSource(this);
                modelErrorList.add(modelError);
        	}
        }
        
        for (PropertyDefinition propDefinition : getProperties()){
        	if (propDefinition.getName().trim().equals("id")) {
        		argList.clear(); 
        		argList.add(propDefinition.getName());
        		String msg = CommonValidationUtils.formatMessage("EnityImpl.errors.propertyNameId", argList);
                ModelError modelError = ValidationFactory.eINSTANCE.createModelError();            
                modelError.setMessage(msg);
                modelError.setSource(this);
                modelErrorList.add(modelError);
        	}
        	if (propDefinition.getHistorySize() > Short.MAX_VALUE) {
        		argList.clear();        		
        		argList.add(propDefinition.getName());
        		String msg = CommonValidationUtils.formatMessage("ConceptImpl.errors.propertyHistorySizeTooLarge", argList);
                ModelError modelError = ValidationFactory.eINSTANCE.createModelError();            
                modelError.setMessage(msg);
                modelError.setSource(this);
                modelErrorList.add(modelError);
        	}
        }
        /* Check that the Concepts to whom we refer exist */
        for (PropertyDefinition propDefinition : getAllProperties()){
        	if (!(propDefinition.getType() == PROPERTY_TYPES.CONCEPT || 
        			        propDefinition.getType() == PROPERTY_TYPES.CONCEPT_REFERENCE)){
        		continue;
        	}
        	String conceptTypePath = propDefinition.getConceptTypePath();
        	if (conceptTypePath == null){
        		argList.clear();        		
        		argList.add(fullPath);
        		argList.add(propDefinition.getName());
        		argList.add(conceptTypePath);
        		String msg = CommonValidationUtils.formatMessage("ConceptImpl.errors.propertyConceptMissing", argList);
                ModelError modelError = ValidationFactory.eINSTANCE.createModelError();            
                modelError.setMessage(msg);
                modelError.setSource(this);
                modelErrorList.add(modelError);
        	} else {
        		Concept concept = CommonIndexUtils.getConcept(getOwnerProjectName(), conceptTypePath);
        		if (concept == null){
              		argList.clear();
            		argList.add(fullPath);
            		argList.add(propDefinition.getName());
            		argList.add(conceptTypePath);
            		String msg = CommonValidationUtils.formatMessage("ConceptImpl.errors.propertyConceptMissing", argList);
                    ModelError modelError = ValidationFactory.eINSTANCE.createModelError();            
                    modelError.setMessage(msg);
                    modelError.setSource(this);
                    modelErrorList.add(modelError);
        		}
        	}
        }
         
        // Validate StateMachines
        for (String smPath : getStateMachinePaths()){
       	if (smPath == null || smPath.trim().length() == 0) continue;
        	// get resource reference 
        	//Entity smEntity = IndexUtils.getEntity(getOwnerProjectName(), smPath, ELEMENT_TYPES.STATE_MACHINE);
       		String ext = CommonIndexUtils.getFileExtension(ELEMENT_TYPES.STATE_MACHINE);
       		if (!smPath.endsWith("."+ext)){
       			smPath = smPath + "." + ext;
       		}
        	if (!CommonValidationUtils.resolveReference(smPath, getOwnerProjectName())){
        		// dangling references 
        		argList.clear();        	
        		argList.add(getFullPath());
        		argList.add(smPath);
        		ModelError me = CommonValidationUtils.constructModelError(this, "Concept.error.hasDangligStateMachineReference", argList, false);
        		modelErrorList.add(me);
        	}
        }
        return modelErrorList;
		
	}

	
} //ConceptImpl
