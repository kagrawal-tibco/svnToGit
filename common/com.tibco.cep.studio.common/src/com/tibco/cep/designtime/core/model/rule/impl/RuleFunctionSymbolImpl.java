/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.rule.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.rule.RuleFunctionSymbol;
import com.tibco.cep.designtime.core.model.rule.RulePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Function Symbol</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionSymbolImpl#getDirection <em>Direction</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionSymbolImpl#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionSymbolImpl#isDomainOverridden <em>Domain Overridden</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionSymbolImpl#getGraphicalPath <em>Graphical Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.rule.impl.RuleFunctionSymbolImpl#getPrimitiveType <em>Primitive Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleFunctionSymbolImpl extends SymbolImpl implements RuleFunctionSymbol {
	/**
	 * The default value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected static final String DIRECTION_EDEFAULT = "IN";

	/**
	 * The cached value of the '{@link #getDirection() <em>Direction</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDirection()
	 * @generated
	 * @ordered
	 */
	protected String direction = DIRECTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceType()
	 * @generated
	 * @ordered
	 */
	protected static final String RESOURCE_TYPE_EDEFAULT = "NULL";

	/**
	 * The cached value of the '{@link #getResourceType() <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResourceType()
	 * @generated
	 * @ordered
	 */
	protected String resourceType = RESOURCE_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isDomainOverridden() <em>Domain Overridden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDomainOverridden()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DOMAIN_OVERRIDDEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDomainOverridden() <em>Domain Overridden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDomainOverridden()
	 * @generated
	 * @ordered
	 */
	protected boolean domainOverridden = DOMAIN_OVERRIDDEN_EDEFAULT;

	/**
	 * The default value of the '{@link #getGraphicalPath() <em>Graphical Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphicalPath()
	 * @generated
	 * @ordered
	 */
	protected static final String GRAPHICAL_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getGraphicalPath() <em>Graphical Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGraphicalPath()
	 * @generated
	 * @ordered
	 */
	protected String graphicalPath = GRAPHICAL_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getPrimitiveType() <em>Primitive Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimitiveType()
	 * @generated
	 * @ordered
	 */
	protected static final String PRIMITIVE_TYPE_EDEFAULT = "NULL";

	/**
	 * The cached value of the '{@link #getPrimitiveType() <em>Primitive Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPrimitiveType()
	 * @generated
	 * @ordered
	 */
	protected String primitiveType = PRIMITIVE_TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleFunctionSymbolImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RulePackage.Literals.RULE_FUNCTION_SYMBOL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDirection() {
		return direction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirection(String newDirection) {
		String oldDirection = direction;
		direction = newDirection;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION_SYMBOL__DIRECTION, oldDirection, direction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResourceType(String newResourceType) {
		String oldResourceType = resourceType;
		resourceType = newResourceType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION_SYMBOL__RESOURCE_TYPE, oldResourceType, resourceType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDomainOverridden() {
		return domainOverridden;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainOverridden(boolean newDomainOverridden) {
		boolean oldDomainOverridden = domainOverridden;
		domainOverridden = newDomainOverridden;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION_SYMBOL__DOMAIN_OVERRIDDEN, oldDomainOverridden, domainOverridden));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getGraphicalPath() {
		return graphicalPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGraphicalPath(String newGraphicalPath) {
		String oldGraphicalPath = graphicalPath;
		graphicalPath = newGraphicalPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION_SYMBOL__GRAPHICAL_PATH, oldGraphicalPath, graphicalPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPrimitiveType() {
		return primitiveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrimitiveType(String newPrimitiveType) {
		String oldPrimitiveType = primitiveType;
		primitiveType = newPrimitiveType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RulePackage.RULE_FUNCTION_SYMBOL__PRIMITIVE_TYPE, oldPrimitiveType, primitiveType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RulePackage.RULE_FUNCTION_SYMBOL__DIRECTION:
				return getDirection();
			case RulePackage.RULE_FUNCTION_SYMBOL__RESOURCE_TYPE:
				return getResourceType();
			case RulePackage.RULE_FUNCTION_SYMBOL__DOMAIN_OVERRIDDEN:
				return isDomainOverridden();
			case RulePackage.RULE_FUNCTION_SYMBOL__GRAPHICAL_PATH:
				return getGraphicalPath();
			case RulePackage.RULE_FUNCTION_SYMBOL__PRIMITIVE_TYPE:
				return getPrimitiveType();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RulePackage.RULE_FUNCTION_SYMBOL__DIRECTION:
				setDirection((String)newValue);
				return;
			case RulePackage.RULE_FUNCTION_SYMBOL__RESOURCE_TYPE:
				setResourceType((String)newValue);
				return;
			case RulePackage.RULE_FUNCTION_SYMBOL__DOMAIN_OVERRIDDEN:
				setDomainOverridden((Boolean)newValue);
				return;
			case RulePackage.RULE_FUNCTION_SYMBOL__GRAPHICAL_PATH:
				setGraphicalPath((String)newValue);
				return;
			case RulePackage.RULE_FUNCTION_SYMBOL__PRIMITIVE_TYPE:
				setPrimitiveType((String)newValue);
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
			case RulePackage.RULE_FUNCTION_SYMBOL__DIRECTION:
				setDirection(DIRECTION_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION_SYMBOL__RESOURCE_TYPE:
				setResourceType(RESOURCE_TYPE_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION_SYMBOL__DOMAIN_OVERRIDDEN:
				setDomainOverridden(DOMAIN_OVERRIDDEN_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION_SYMBOL__GRAPHICAL_PATH:
				setGraphicalPath(GRAPHICAL_PATH_EDEFAULT);
				return;
			case RulePackage.RULE_FUNCTION_SYMBOL__PRIMITIVE_TYPE:
				setPrimitiveType(PRIMITIVE_TYPE_EDEFAULT);
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
			case RulePackage.RULE_FUNCTION_SYMBOL__DIRECTION:
				return DIRECTION_EDEFAULT == null ? direction != null : !DIRECTION_EDEFAULT.equals(direction);
			case RulePackage.RULE_FUNCTION_SYMBOL__RESOURCE_TYPE:
				return RESOURCE_TYPE_EDEFAULT == null ? resourceType != null : !RESOURCE_TYPE_EDEFAULT.equals(resourceType);
			case RulePackage.RULE_FUNCTION_SYMBOL__DOMAIN_OVERRIDDEN:
				return domainOverridden != DOMAIN_OVERRIDDEN_EDEFAULT;
			case RulePackage.RULE_FUNCTION_SYMBOL__GRAPHICAL_PATH:
				return GRAPHICAL_PATH_EDEFAULT == null ? graphicalPath != null : !GRAPHICAL_PATH_EDEFAULT.equals(graphicalPath);
			case RulePackage.RULE_FUNCTION_SYMBOL__PRIMITIVE_TYPE:
				return PRIMITIVE_TYPE_EDEFAULT == null ? primitiveType != null : !PRIMITIVE_TYPE_EDEFAULT.equals(primitiveType);
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
		result.append(" (direction: ");
		result.append(direction);
		result.append(", resourceType: ");
		result.append(resourceType);
		result.append(", domainOverridden: ");
		result.append(domainOverridden);
		result.append(", graphicalPath: ");
		result.append(graphicalPath);
		result.append(", primitiveType: ");
		result.append(primitiveType);
		result.append(')');
		return result.toString();
	}

} //RuleFunctionSymbolImpl
