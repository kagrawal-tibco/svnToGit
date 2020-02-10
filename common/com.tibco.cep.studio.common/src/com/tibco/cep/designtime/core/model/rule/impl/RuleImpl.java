/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.impl.EntityImpl;
import com.tibco.cep.designtime.core.model.rule.Rule;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.ScopeContainer;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getSymbols <em>Symbols</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getCompilationStatus <em>Compilation Status</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getFullSourceText <em>Full Source Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getActionText <em>Action Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getConditionText <em>Condition Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getRank <em>Rank</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getMaxRules <em>Max Rules</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getTestInterval <em>Test Interval</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getStartTime <em>Start Time</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#isRequeue <em>Requeue</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getRequeueVars <em>Requeue Vars</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#isBackwardChain <em>Backward Chain</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#isForwardChain <em>Forward Chain</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#isConditionFunction <em>Condition Function</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#isFunction <em>Function</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleImpl#getAuthor <em>Author</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleImpl extends EntityImpl implements Rule {
	/**
	 * The cached value of the '{@link #getSymbols() <em>Symbols</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSymbols()
	 * @generated
	 * @ordered
	 */
	protected Symbols symbols;

	/**
	 * The default value of the '{@link #getCompilationStatus() <em>Compilation Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompilationStatus()
	 * @generated
	 * @ordered
	 */
	protected static final int COMPILATION_STATUS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getCompilationStatus() <em>Compilation Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompilationStatus()
	 * @generated
	 * @ordered
	 */
	protected int compilationStatus = COMPILATION_STATUS_EDEFAULT;

	/**
	 * The default value of the '{@link #getReturnType() <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected static final String RETURN_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReturnType() <em>Return Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReturnType()
	 * @generated
	 * @ordered
	 */
	protected String returnType = RETURN_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getFullSourceText() <em>Full Source Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFullSourceText()
	 * @generated
	 * @ordered
	 */
	protected static final String FULL_SOURCE_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFullSourceText() <em>Full Source Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFullSourceText()
	 * @generated
	 * @ordered
	 */
	protected String fullSourceText = FULL_SOURCE_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getActionText() <em>Action Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionText()
	 * @generated
	 * @ordered
	 */
	protected static final String ACTION_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getActionText() <em>Action Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionText()
	 * @generated
	 * @ordered
	 */
	protected String actionText = ACTION_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getConditionText() <em>Condition Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConditionText()
	 * @generated
	 * @ordered
	 */
	protected static final String CONDITION_TEXT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConditionText() <em>Condition Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConditionText()
	 * @generated
	 * @ordered
	 */
	protected String conditionText = CONDITION_TEXT_EDEFAULT;

	/**
	 * The default value of the '{@link #getRank() <em>Rank</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRank()
	 * @generated
	 * @ordered
	 */
	protected static final String RANK_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRank() <em>Rank</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRank()
	 * @generated
	 * @ordered
	 */
	protected String rank = RANK_EDEFAULT;

	/**
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected static final int PRIORITY_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected int priority = PRIORITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getMaxRules() <em>Max Rules</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxRules()
	 * @generated
	 * @ordered
	 */
	protected static final int MAX_RULES_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getMaxRules() <em>Max Rules</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxRules()
	 * @generated
	 * @ordered
	 */
	protected int maxRules = MAX_RULES_EDEFAULT;

	/**
	 * The default value of the '{@link #getTestInterval() <em>Test Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTestInterval()
	 * @generated
	 * @ordered
	 */
	protected static final long TEST_INTERVAL_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getTestInterval() <em>Test Interval</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTestInterval()
	 * @generated
	 * @ordered
	 */
	protected long testInterval = TEST_INTERVAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getStartTime() <em>Start Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartTime()
	 * @generated
	 * @ordered
	 */
	protected static final long START_TIME_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getStartTime() <em>Start Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartTime()
	 * @generated
	 * @ordered
	 */
	protected long startTime = START_TIME_EDEFAULT;

	/**
	 * The default value of the '{@link #isRequeue() <em>Requeue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRequeue()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REQUEUE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isRequeue() <em>Requeue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRequeue()
	 * @generated
	 * @ordered
	 */
	protected boolean requeue = REQUEUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRequeueVars() <em>Requeue Vars</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequeueVars()
	 * @generated
	 * @ordered
	 */
	protected EList<String> requeueVars;

	/**
	 * The default value of the '{@link #isBackwardChain() <em>Backward Chain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBackwardChain()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BACKWARD_CHAIN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isBackwardChain() <em>Backward Chain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBackwardChain()
	 * @generated
	 * @ordered
	 */
	protected boolean backwardChain = BACKWARD_CHAIN_EDEFAULT;

	/**
	 * The default value of the '{@link #isForwardChain() <em>Forward Chain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForwardChain()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FORWARD_CHAIN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isForwardChain() <em>Forward Chain</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isForwardChain()
	 * @generated
	 * @ordered
	 */
	protected boolean forwardChain = FORWARD_CHAIN_EDEFAULT;

	/**
	 * The default value of the '{@link #isConditionFunction() <em>Condition Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConditionFunction()
	 * @generated
	 * @ordered
	 */
	protected static final boolean CONDITION_FUNCTION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isConditionFunction() <em>Condition Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isConditionFunction()
	 * @generated
	 * @ordered
	 */
	protected boolean conditionFunction = CONDITION_FUNCTION_EDEFAULT;

	/**
	 * The default value of the '{@link #isFunction() <em>Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFunction()
	 * @generated
	 * @ordered
	 */
	protected static final boolean FUNCTION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isFunction() <em>Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isFunction()
	 * @generated
	 * @ordered
	 */
	protected boolean function = FUNCTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected String author = AUTHOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulePackage.Literals.RULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFolder() {
		if (folder == null) {
			EObject rootContainer = this;
			while (rootContainer.eContainer() != null) {
				if (rootContainer.eContainer() instanceof SharedElement) {
					break;
				}
				rootContainer = rootContainer.eContainer();
			}
			if (rootContainer instanceof Entity) {
				folder = ((Entity) rootContainer).getFullPath();
			}
		}
		return folder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getOwnerProjectName() {
		if (ownerProjectName == null) {
			EObject rootContainer = CommonIndexUtils.getRootContainer(this);
			if (rootContainer == this) {
				return null; // avoid loop
			}
			if (rootContainer instanceof Entity) {
				ownerProjectName = ((Entity) rootContainer).getOwnerProjectName();
			} else if (rootContainer instanceof RuleElement) {
				ownerProjectName = ((RuleElement) rootContainer).getIndexName();
			} else if (rootContainer instanceof DesignerProject) {
				ownerProjectName = ((DesignerProject) rootContainer).getName();
			}
		}
		return ownerProjectName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Symbols getSymbols() {
		if (symbols == null) {
			symbols = RuleFactory.eINSTANCE.createSymbols();
		}
		return symbols;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSymbols(Symbols newSymbols, NotificationChain msgs) {
		Symbols oldSymbols = symbols;
		symbols = newSymbols;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RulePackage.RULE__SYMBOLS, oldSymbols, newSymbols);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSymbols(Symbols newSymbols) {
		if (newSymbols != symbols) {
			NotificationChain msgs = null;
			if (symbols != null)
				msgs = ((InternalEObject)symbols).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RulePackage.RULE__SYMBOLS, null, msgs);
			if (newSymbols != null)
				msgs = ((InternalEObject)newSymbols).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RulePackage.RULE__SYMBOLS, null, msgs);
			msgs = basicSetSymbols(newSymbols, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__SYMBOLS, newSymbols, newSymbols));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCompilationStatus() {
		return compilationStatus;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompilationStatus(int newCompilationStatus) {
		int oldCompilationStatus = compilationStatus;
		compilationStatus = newCompilationStatus;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__COMPILATION_STATUS, oldCompilationStatus, compilationStatus));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReturnType() {
		return returnType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReturnType(String newReturnType) {
		String oldReturnType = returnType;
		returnType = newReturnType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__RETURN_TYPE, oldReturnType, returnType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFullSourceText() {
		return fullSourceText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFullSourceText(String newFullSourceText) {
		String oldFullSourceText = fullSourceText;
		fullSourceText = newFullSourceText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__FULL_SOURCE_TEXT, oldFullSourceText, fullSourceText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getActionText() {
		if (actionText == null) {
			actionText = "";
		}
		return actionText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActionText(String newActionText) {
		String oldActionText = actionText;
		actionText = newActionText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__ACTION_TEXT, oldActionText, actionText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getConditionText() {
		if (conditionText == null) {
			conditionText = "";
		}
		return conditionText;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConditionText(String newConditionText) {
		String oldConditionText = conditionText;
		conditionText = newConditionText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__CONDITION_TEXT, oldConditionText, conditionText));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * @generated NOT
	 * @return
	 */
	public RuleFunction getRankRuleFunction() {
		String rankPath = getRank();			
		if (rankPath == null || rankPath.length() == 0 || getOwnerProjectName() == null) {
			return null;
		}
		RuleFunction rankRuleFunction = (RuleFunction) CommonIndexUtils.getRule(getOwnerProjectName(), rankPath, ELEMENT_TYPES.RULE_FUNCTION);
		return rankRuleFunction;
	
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRank(String newRank) {
		String oldRank = rank;
		rank = newRank;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__RANK, oldRank, rank));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(int newPriority) {
		int oldPriority = priority;
		priority = newPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__PRIORITY, oldPriority, priority));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxRules() {
		return maxRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxRules(int newMaxRules) {
		int oldMaxRules = maxRules;
		maxRules = newMaxRules;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__MAX_RULES, oldMaxRules, maxRules));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getTestInterval() {
		return testInterval;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTestInterval(long newTestInterval) {
		long oldTestInterval = testInterval;
		testInterval = newTestInterval;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__TEST_INTERVAL, oldTestInterval, testInterval));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getStartTime() {
		return startTime;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartTime(long newStartTime) {
		long oldStartTime = startTime;
		startTime = newStartTime;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__START_TIME, oldStartTime, startTime));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRequeue() {
		return requeue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequeue(boolean newRequeue) {
		boolean oldRequeue = requeue;
		requeue = newRequeue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__REQUEUE, oldRequeue, requeue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getRequeueVars() {
		if (requeueVars == null) {
			requeueVars = new EDataTypeUniqueEList<String>(String.class, this, RulePackage.RULE__REQUEUE_VARS);
		}
		return requeueVars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBackwardChain() {
		return backwardChain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackwardChain(boolean newBackwardChain) {
		boolean oldBackwardChain = backwardChain;
		backwardChain = newBackwardChain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__BACKWARD_CHAIN, oldBackwardChain, backwardChain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isForwardChain() {
		return forwardChain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setForwardChain(boolean newForwardChain) {
		boolean oldForwardChain = forwardChain;
		forwardChain = newForwardChain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__FORWARD_CHAIN, oldForwardChain, forwardChain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isConditionFunction() {
		return conditionFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConditionFunction(boolean newConditionFunction) {
		boolean oldConditionFunction = conditionFunction;
		conditionFunction = newConditionFunction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__CONDITION_FUNCTION, oldConditionFunction, conditionFunction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isFunction() {
		return function;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunction(boolean newFunction) {
		boolean oldFunction = function;
		function = newFunction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__FUNCTION, oldFunction, function));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthor(String newAuthor) {
		String oldAuthor = author;
		author = newAuthor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE__AUTHOR, oldAuthor, author));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public boolean isEmpty() {
		boolean isEmpty = true;
		if (((null != this.getActionText()) && (this.getActionText().trim().length() > 0))
				|| ((null != this.getConditionText()) && (this.getConditionText().trim()
						.length() > 0))) {
			return false;
		}

		return isEmpty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulePackage.RULE__SYMBOLS:
				return basicSetSymbols(null, msgs);
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
			case RulePackage.RULE__SYMBOLS:
				return getSymbols();
			case RulePackage.RULE__COMPILATION_STATUS:
				return getCompilationStatus();
			case RulePackage.RULE__RETURN_TYPE:
				return getReturnType();
			case RulePackage.RULE__FULL_SOURCE_TEXT:
				return getFullSourceText();
			case RulePackage.RULE__ACTION_TEXT:
				return getActionText();
			case RulePackage.RULE__CONDITION_TEXT:
				return getConditionText();
			case RulePackage.RULE__RANK:
				return getRank();
			case RulePackage.RULE__PRIORITY:
				return getPriority();
			case RulePackage.RULE__MAX_RULES:
				return getMaxRules();
			case RulePackage.RULE__TEST_INTERVAL:
				return getTestInterval();
			case RulePackage.RULE__START_TIME:
				return getStartTime();
			case RulePackage.RULE__REQUEUE:
				return isRequeue();
			case RulePackage.RULE__REQUEUE_VARS:
				return getRequeueVars();
			case RulePackage.RULE__BACKWARD_CHAIN:
				return isBackwardChain();
			case RulePackage.RULE__FORWARD_CHAIN:
				return isForwardChain();
			case RulePackage.RULE__CONDITION_FUNCTION:
				return isConditionFunction();
			case RulePackage.RULE__FUNCTION:
				return isFunction();
			case RulePackage.RULE__AUTHOR:
				return getAuthor();
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
			case RulePackage.RULE__SYMBOLS:
				setSymbols((Symbols)newValue);
				return;
			case RulePackage.RULE__COMPILATION_STATUS:
				setCompilationStatus((Integer)newValue);
				return;
			case RulePackage.RULE__RETURN_TYPE:
				setReturnType((String)newValue);
				return;
			case RulePackage.RULE__FULL_SOURCE_TEXT:
				setFullSourceText((String)newValue);
				return;
			case RulePackage.RULE__ACTION_TEXT:
				setActionText((String)newValue);
				return;
			case RulePackage.RULE__CONDITION_TEXT:
				setConditionText((String)newValue);
				return;
			case RulePackage.RULE__RANK:
				setRank((String)newValue);
				return;
			case RulePackage.RULE__PRIORITY:
				setPriority((Integer)newValue);
				return;
			case RulePackage.RULE__MAX_RULES:
				setMaxRules((Integer)newValue);
				return;
			case RulePackage.RULE__TEST_INTERVAL:
				setTestInterval((Long)newValue);
				return;
			case RulePackage.RULE__START_TIME:
				setStartTime((Long)newValue);
				return;
			case RulePackage.RULE__REQUEUE:
				setRequeue((Boolean)newValue);
				return;
			case RulePackage.RULE__REQUEUE_VARS:
				getRequeueVars().clear();
				getRequeueVars().addAll((Collection<? extends String>)newValue);
				return;
			case RulePackage.RULE__BACKWARD_CHAIN:
				setBackwardChain((Boolean)newValue);
				return;
			case RulePackage.RULE__FORWARD_CHAIN:
				setForwardChain((Boolean)newValue);
				return;
			case RulePackage.RULE__CONDITION_FUNCTION:
				setConditionFunction((Boolean)newValue);
				return;
			case RulePackage.RULE__FUNCTION:
				setFunction((Boolean)newValue);
				return;
			case RulePackage.RULE__AUTHOR:
				setAuthor((String)newValue);
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
			case RulePackage.RULE__SYMBOLS:
				setSymbols((Symbols)null);
				return;
			case RulePackage.RULE__COMPILATION_STATUS:
				setCompilationStatus(COMPILATION_STATUS_EDEFAULT);
				return;
			case RulePackage.RULE__RETURN_TYPE:
				setReturnType(RETURN_TYPE_EDEFAULT);
				return;
			case RulePackage.RULE__FULL_SOURCE_TEXT:
				setFullSourceText(FULL_SOURCE_TEXT_EDEFAULT);
				return;
			case RulePackage.RULE__ACTION_TEXT:
				setActionText(ACTION_TEXT_EDEFAULT);
				return;
			case RulePackage.RULE__CONDITION_TEXT:
				setConditionText(CONDITION_TEXT_EDEFAULT);
				return;
			case RulePackage.RULE__RANK:
				setRank(RANK_EDEFAULT);
				return;
			case RulePackage.RULE__PRIORITY:
				setPriority(PRIORITY_EDEFAULT);
				return;
			case RulePackage.RULE__MAX_RULES:
				setMaxRules(MAX_RULES_EDEFAULT);
				return;
			case RulePackage.RULE__TEST_INTERVAL:
				setTestInterval(TEST_INTERVAL_EDEFAULT);
				return;
			case RulePackage.RULE__START_TIME:
				setStartTime(START_TIME_EDEFAULT);
				return;
			case RulePackage.RULE__REQUEUE:
				setRequeue(REQUEUE_EDEFAULT);
				return;
			case RulePackage.RULE__REQUEUE_VARS:
				getRequeueVars().clear();
				return;
			case RulePackage.RULE__BACKWARD_CHAIN:
				setBackwardChain(BACKWARD_CHAIN_EDEFAULT);
				return;
			case RulePackage.RULE__FORWARD_CHAIN:
				setForwardChain(FORWARD_CHAIN_EDEFAULT);
				return;
			case RulePackage.RULE__CONDITION_FUNCTION:
				setConditionFunction(CONDITION_FUNCTION_EDEFAULT);
				return;
			case RulePackage.RULE__FUNCTION:
				setFunction(FUNCTION_EDEFAULT);
				return;
			case RulePackage.RULE__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
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
			case RulePackage.RULE__SYMBOLS:
				return symbols != null;
			case RulePackage.RULE__COMPILATION_STATUS:
				return compilationStatus != COMPILATION_STATUS_EDEFAULT;
			case RulePackage.RULE__RETURN_TYPE:
				return RETURN_TYPE_EDEFAULT == null ? returnType != null : !RETURN_TYPE_EDEFAULT.equals(returnType);
			case RulePackage.RULE__FULL_SOURCE_TEXT:
				return FULL_SOURCE_TEXT_EDEFAULT == null ? fullSourceText != null : !FULL_SOURCE_TEXT_EDEFAULT.equals(fullSourceText);
			case RulePackage.RULE__ACTION_TEXT:
				return ACTION_TEXT_EDEFAULT == null ? actionText != null : !ACTION_TEXT_EDEFAULT.equals(actionText);
			case RulePackage.RULE__CONDITION_TEXT:
				return CONDITION_TEXT_EDEFAULT == null ? conditionText != null : !CONDITION_TEXT_EDEFAULT.equals(conditionText);
			case RulePackage.RULE__RANK:
				return RANK_EDEFAULT == null ? rank != null : !RANK_EDEFAULT.equals(rank);
			case RulePackage.RULE__PRIORITY:
				return priority != PRIORITY_EDEFAULT;
			case RulePackage.RULE__MAX_RULES:
				return maxRules != MAX_RULES_EDEFAULT;
			case RulePackage.RULE__TEST_INTERVAL:
				return testInterval != TEST_INTERVAL_EDEFAULT;
			case RulePackage.RULE__START_TIME:
				return startTime != START_TIME_EDEFAULT;
			case RulePackage.RULE__REQUEUE:
				return requeue != REQUEUE_EDEFAULT;
			case RulePackage.RULE__REQUEUE_VARS:
				return requeueVars != null && !requeueVars.isEmpty();
			case RulePackage.RULE__BACKWARD_CHAIN:
				return backwardChain != BACKWARD_CHAIN_EDEFAULT;
			case RulePackage.RULE__FORWARD_CHAIN:
				return forwardChain != FORWARD_CHAIN_EDEFAULT;
			case RulePackage.RULE__CONDITION_FUNCTION:
				return conditionFunction != CONDITION_FUNCTION_EDEFAULT;
			case RulePackage.RULE__FUNCTION:
				return function != FUNCTION_EDEFAULT;
			case RulePackage.RULE__AUTHOR:
				return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ScopeContainer.class) {
			switch (derivedFeatureID) {
				case RulePackage.RULE__SYMBOLS: return RulePackage.SCOPE_CONTAINER__SYMBOLS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ScopeContainer.class) {
			switch (baseFeatureID) {
				case RulePackage.SCOPE_CONTAINER__SYMBOLS: return RulePackage.RULE__SYMBOLS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
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
		result.append(" (compilationStatus: ");
		result.append(compilationStatus);
		result.append(", returnType: ");
		result.append(returnType);
		result.append(", fullSourceText: ");
		result.append(fullSourceText);
		result.append(", actionText: ");
		result.append(actionText);
		result.append(", conditionText: ");
		result.append(conditionText);
		result.append(", rank: ");
		result.append(rank);
		result.append(", priority: ");
		result.append(priority);
		result.append(", maxRules: ");
		result.append(maxRules);
		result.append(", testInterval: ");
		result.append(testInterval);
		result.append(", startTime: ");
		result.append(startTime);
		result.append(", requeue: ");
		result.append(requeue);
		result.append(", requeueVars: ");
		result.append(requeueVars);
		result.append(", backwardChain: ");
		result.append(backwardChain);
		result.append(", forwardChain: ");
		result.append(forwardChain);
		result.append(", conditionFunction: ");
		result.append(conditionFunction);
		result.append(", function: ");
		result.append(function);
		result.append(", author: ");
		result.append(author);
		result.append(')');
		return result.toString();
	}
	/**
	 * @generated NOT
	 */
	public Symbol getSymbol(String id) {
		// TODO Auto-generated method stub
		if (id == null) return null;
		return getSymbols().getSymbolMap().get(id);
//		for (Symbol symbol : getSymbols()){
//			if (id.equals(symbol.getIdName())){
//				return symbol;
//			}
//		}
//		return null;
	}
} //RuleImpl
