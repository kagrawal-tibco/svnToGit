/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.impl.EntityImpl;
import com.tibco.cep.designtime.core.model.rule.RuleFactory;
import com.tibco.cep.designtime.core.model.rule.RuleFunction;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.ScopeContainer;
import com.tibco.cep.designtime.core.model.rule.Symbol;
import com.tibco.cep.designtime.core.model.rule.Symbols;
import com.tibco.cep.designtime.core.model.rule.Validity;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl#getSymbols <em>Symbols</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl#getCompilationStatus <em>Compilation Status</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl#getReturnType <em>Return Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl#getFullSourceText <em>Full Source Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl#getActionText <em>Action Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl#getConditionText <em>Condition Text</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl#getRank <em>Rank</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl#getValidity <em>Validity</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl#isVirtual <em>Virtual</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionImpl#getAlias <em>Alias</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleFunctionImpl extends EntityImpl implements RuleFunction {
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
	 * The default value of the '{@link #getValidity() <em>Validity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidity()
	 * @generated
	 * @ordered
	 */
	protected static final Validity VALIDITY_EDEFAULT = Validity.ACTION;

	/**
	 * The cached value of the '{@link #getValidity() <em>Validity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValidity()
	 * @generated
	 * @ordered
	 */
	protected Validity validity = VALIDITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isVirtual() <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVirtual()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VIRTUAL_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isVirtual() <em>Virtual</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVirtual()
	 * @generated
	 * @ordered
	 */
	protected boolean virtual = VIRTUAL_EDEFAULT;

	/**
	 * The default value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected static final String ALIAS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAlias() <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlias()
	 * @generated
	 * @ordered
	 */
	protected String alias = ALIAS_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleFunctionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulePackage.Literals.RULE_FUNCTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFolder() {
		if (folder == null) {
			EObject rootContainer = CommonIndexUtils.getRootContainer(this);
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION__SYMBOLS, oldSymbols, newSymbols);
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
				msgs = ((InternalEObject)symbols).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RulePackage.RULE_FUNCTION__SYMBOLS, null, msgs);
			if (newSymbols != null)
				msgs = ((InternalEObject)newSymbols).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RulePackage.RULE_FUNCTION__SYMBOLS, null, msgs);
			msgs = basicSetSymbols(newSymbols, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION__SYMBOLS, newSymbols, newSymbols));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION__COMPILATION_STATUS, oldCompilationStatus, compilationStatus));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION__RETURN_TYPE, oldReturnType, returnType));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION__FULL_SOURCE_TEXT, oldFullSourceText, fullSourceText));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION__ACTION_TEXT, oldActionText, actionText));
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
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION__CONDITION_TEXT, oldConditionText, conditionText));
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRank(String newRank) {
		String oldRank = rank;
		rank = newRank;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION__RANK, oldRank, rank));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Validity getValidity() {
		return validity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValidity(Validity newValidity) {
		Validity oldValidity = validity;
		validity = newValidity == null ? VALIDITY_EDEFAULT : newValidity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION__VALIDITY, oldValidity, validity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVirtual() {
		return virtual;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVirtual(boolean newVirtual) {
		boolean oldVirtual = virtual;
		virtual = newVirtual;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION__VIRTUAL, oldVirtual, virtual));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlias(String newAlias) {
		String oldAlias = alias;
		alias = newAlias;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION__ALIAS, oldAlias, alias));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulePackage.RULE_FUNCTION__SYMBOLS:
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
			case RulePackage.RULE_FUNCTION__SYMBOLS:
				return getSymbols();
			case RulePackage.RULE_FUNCTION__COMPILATION_STATUS:
				return getCompilationStatus();
			case RulePackage.RULE_FUNCTION__RETURN_TYPE:
				return getReturnType();
			case RulePackage.RULE_FUNCTION__FULL_SOURCE_TEXT:
				return getFullSourceText();
			case RulePackage.RULE_FUNCTION__ACTION_TEXT:
				return getActionText();
			case RulePackage.RULE_FUNCTION__CONDITION_TEXT:
				return getConditionText();
			case RulePackage.RULE_FUNCTION__RANK:
				return getRank();
			case RulePackage.RULE_FUNCTION__VALIDITY:
				return getValidity();
			case RulePackage.RULE_FUNCTION__VIRTUAL:
				return isVirtual();
			case RulePackage.RULE_FUNCTION__ALIAS:
				return getAlias();
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
			case RulePackage.RULE_FUNCTION__SYMBOLS:
				setSymbols((Symbols)newValue);
				return;
			case RulePackage.RULE_FUNCTION__COMPILATION_STATUS:
				setCompilationStatus((Integer)newValue);
				return;
			case RulePackage.RULE_FUNCTION__RETURN_TYPE:
				setReturnType((String)newValue);
				return;
			case RulePackage.RULE_FUNCTION__FULL_SOURCE_TEXT:
				setFullSourceText((String)newValue);
				return;
			case RulePackage.RULE_FUNCTION__ACTION_TEXT:
				setActionText((String)newValue);
				return;
			case RulePackage.RULE_FUNCTION__CONDITION_TEXT:
				setConditionText((String)newValue);
				return;
			case RulePackage.RULE_FUNCTION__RANK:
				setRank((String)newValue);
				return;
			case RulePackage.RULE_FUNCTION__VALIDITY:
				setValidity((Validity)newValue);
				return;
			case RulePackage.RULE_FUNCTION__VIRTUAL:
				setVirtual((Boolean)newValue);
				return;
			case RulePackage.RULE_FUNCTION__ALIAS:
				setAlias((String)newValue);
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
			case RulePackage.RULE_FUNCTION__SYMBOLS:
				setSymbols((Symbols)null);
				return;
			case RulePackage.RULE_FUNCTION__COMPILATION_STATUS:
				setCompilationStatus(COMPILATION_STATUS_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION__RETURN_TYPE:
				setReturnType(RETURN_TYPE_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION__FULL_SOURCE_TEXT:
				setFullSourceText(FULL_SOURCE_TEXT_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION__ACTION_TEXT:
				setActionText(ACTION_TEXT_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION__CONDITION_TEXT:
				setConditionText(CONDITION_TEXT_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION__RANK:
				setRank(RANK_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION__VALIDITY:
				setValidity(VALIDITY_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION__VIRTUAL:
				setVirtual(VIRTUAL_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION__ALIAS:
				setAlias(ALIAS_EDEFAULT);
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
			case RulePackage.RULE_FUNCTION__SYMBOLS:
				return symbols != null;
			case RulePackage.RULE_FUNCTION__COMPILATION_STATUS:
				return compilationStatus != COMPILATION_STATUS_EDEFAULT;
			case RulePackage.RULE_FUNCTION__RETURN_TYPE:
				return RETURN_TYPE_EDEFAULT == null ? returnType != null : !RETURN_TYPE_EDEFAULT.equals(returnType);
			case RulePackage.RULE_FUNCTION__FULL_SOURCE_TEXT:
				return FULL_SOURCE_TEXT_EDEFAULT == null ? fullSourceText != null : !FULL_SOURCE_TEXT_EDEFAULT.equals(fullSourceText);
			case RulePackage.RULE_FUNCTION__ACTION_TEXT:
				return ACTION_TEXT_EDEFAULT == null ? actionText != null : !ACTION_TEXT_EDEFAULT.equals(actionText);
			case RulePackage.RULE_FUNCTION__CONDITION_TEXT:
				return CONDITION_TEXT_EDEFAULT == null ? conditionText != null : !CONDITION_TEXT_EDEFAULT.equals(conditionText);
			case RulePackage.RULE_FUNCTION__RANK:
				return RANK_EDEFAULT == null ? rank != null : !RANK_EDEFAULT.equals(rank);
			case RulePackage.RULE_FUNCTION__VALIDITY:
				return validity != VALIDITY_EDEFAULT;
			case RulePackage.RULE_FUNCTION__VIRTUAL:
				return virtual != VIRTUAL_EDEFAULT;
			case RulePackage.RULE_FUNCTION__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
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
				case RulePackage.RULE_FUNCTION__SYMBOLS: return RulePackage.SCOPE_CONTAINER__SYMBOLS;
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
				case RulePackage.SCOPE_CONTAINER__SYMBOLS: return RulePackage.RULE_FUNCTION__SYMBOLS;
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
		result.append(", validity: ");
		result.append(validity);
		result.append(", virtual: ");
		result.append(virtual);
		result.append(", alias: ");
		result.append(alias);
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
	
	

} //RuleFunctionImpl
