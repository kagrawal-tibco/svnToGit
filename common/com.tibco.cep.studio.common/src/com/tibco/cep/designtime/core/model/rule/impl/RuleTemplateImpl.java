/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import com.tibco.cep.designtime.core.model.SimpleProperty;
import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.rule.ActionContext;
import com.tibco.cep.designtime.core.model.rule.Binding;
import com.tibco.cep.designtime.core.model.rule.RulePackage;
import com.tibco.cep.designtime.core.model.rule.RuleTemplate;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateImpl#getBindings <em>Bindings</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateImpl#getDisplayProperties <em>Display Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateImpl#getActionContext <em>Action Context</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleTemplateImpl#getViews <em>Views</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleTemplateImpl extends RuleImpl implements RuleTemplate {
	/**
	 * The cached value of the '{@link #getBindings() <em>Bindings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBindings()
	 * @generated
	 * @ordered
	 */
	protected EList<Binding> bindings;
	/**
	 * The cached value of the '{@link #getDisplayProperties() <em>Display Properties</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<SimpleProperty> displayProperties;
	/**
	 * The cached value of the '{@link #getActionContext() <em>Action Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActionContext()
	 * @generated
	 * @ordered
	 */
	protected ActionContext actionContext;

	/**
	 * The cached value of the '{@link #getViews() <em>Views</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getViews()
	 * @generated
	 * @ordered
	 */
	protected EList<String> views;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulePackage.Literals.RULE_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Binding> getBindings() {
		if (bindings == null) {
			bindings = new EObjectContainmentEList<Binding>(Binding.class, this, RulePackage.RULE_TEMPLATE__BINDINGS);
		}
		return bindings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<SimpleProperty> getDisplayProperties() {
		if (displayProperties == null) {
			displayProperties = new EObjectContainmentEList<SimpleProperty>(SimpleProperty.class, this, RulePackage.RULE_TEMPLATE__DISPLAY_PROPERTIES);
		}
		return displayProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionContext getActionContext() {
		return actionContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetActionContext(ActionContext newActionContext, NotificationChain msgs) {
		ActionContext oldActionContext = actionContext;
		actionContext = newActionContext;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RulePackage.RULE_TEMPLATE__ACTION_CONTEXT, oldActionContext, newActionContext);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setActionContext(ActionContext newActionContext) {
		if (newActionContext != actionContext) {
			NotificationChain msgs = null;
			if (actionContext != null)
				msgs = ((InternalEObject)actionContext).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RulePackage.RULE_TEMPLATE__ACTION_CONTEXT, null, msgs);
			if (newActionContext != null)
				msgs = ((InternalEObject)newActionContext).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RulePackage.RULE_TEMPLATE__ACTION_CONTEXT, null, msgs);
			msgs = basicSetActionContext(newActionContext, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_TEMPLATE__ACTION_CONTEXT, newActionContext, newActionContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getViews() {
		if (views == null) {
			views = new EDataTypeUniqueEList<String>(String.class, this, RulePackage.RULE_TEMPLATE__VIEWS);
		}
		return views;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RulePackage.RULE_TEMPLATE__BINDINGS:
				return ((InternalEList<?>)getBindings()).basicRemove(otherEnd, msgs);
			case RulePackage.RULE_TEMPLATE__DISPLAY_PROPERTIES:
				return ((InternalEList<?>)getDisplayProperties()).basicRemove(otherEnd, msgs);
			case RulePackage.RULE_TEMPLATE__ACTION_CONTEXT:
				return basicSetActionContext(null, msgs);
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
			case RulePackage.RULE_TEMPLATE__BINDINGS:
				return getBindings();
			case RulePackage.RULE_TEMPLATE__DISPLAY_PROPERTIES:
				return getDisplayProperties();
			case RulePackage.RULE_TEMPLATE__ACTION_CONTEXT:
				return getActionContext();
			case RulePackage.RULE_TEMPLATE__VIEWS:
				return getViews();
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
			case RulePackage.RULE_TEMPLATE__BINDINGS:
				getBindings().clear();
				getBindings().addAll((Collection<? extends Binding>)newValue);
				return;
			case RulePackage.RULE_TEMPLATE__DISPLAY_PROPERTIES:
				getDisplayProperties().clear();
				getDisplayProperties().addAll((Collection<? extends SimpleProperty>)newValue);
				return;
			case RulePackage.RULE_TEMPLATE__ACTION_CONTEXT:
				setActionContext((ActionContext)newValue);
				return;
			case RulePackage.RULE_TEMPLATE__VIEWS:
				getViews().clear();
				getViews().addAll((Collection<? extends String>)newValue);
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
			case RulePackage.RULE_TEMPLATE__BINDINGS:
				getBindings().clear();
				return;
			case RulePackage.RULE_TEMPLATE__DISPLAY_PROPERTIES:
				getDisplayProperties().clear();
				return;
			case RulePackage.RULE_TEMPLATE__ACTION_CONTEXT:
				setActionContext((ActionContext)null);
				return;
			case RulePackage.RULE_TEMPLATE__VIEWS:
				getViews().clear();
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
			case RulePackage.RULE_TEMPLATE__BINDINGS:
				return bindings != null && !bindings.isEmpty();
			case RulePackage.RULE_TEMPLATE__DISPLAY_PROPERTIES:
				return displayProperties != null && !displayProperties.isEmpty();
			case RulePackage.RULE_TEMPLATE__ACTION_CONTEXT:
				return actionContext != null;
			case RulePackage.RULE_TEMPLATE__VIEWS:
				return views != null && !views.isEmpty();
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
		result.append(" (views: ");
		result.append(views);
		result.append(')');
		return result.toString();
	}

} //RuleTemplateImpl
