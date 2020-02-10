/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.FlowLayoutConstraint;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Flow Layout Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.FlowLayoutConstraintImpl#getComponentRowSpan <em>Component Row Span</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.FlowLayoutConstraintImpl#getComponentColSpan <em>Component Col Span</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FlowLayoutConstraintImpl extends LayoutConstraintImpl implements FlowLayoutConstraint {
	/**
	 * The default value of the '{@link #getComponentRowSpan() <em>Component Row Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentRowSpan()
	 * @generated
	 * @ordered
	 */
	protected static final int COMPONENT_ROW_SPAN_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getComponentRowSpan() <em>Component Row Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentRowSpan()
	 * @generated
	 * @ordered
	 */
	protected int componentRowSpan = COMPONENT_ROW_SPAN_EDEFAULT;

	/**
	 * This is true if the Component Row Span attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean componentRowSpanESet;

	/**
	 * The default value of the '{@link #getComponentColSpan() <em>Component Col Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentColSpan()
	 * @generated
	 * @ordered
	 */
	protected static final int COMPONENT_COL_SPAN_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getComponentColSpan() <em>Component Col Span</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComponentColSpan()
	 * @generated
	 * @ordered
	 */
	protected int componentColSpan = COMPONENT_COL_SPAN_EDEFAULT;

	/**
	 * This is true if the Component Col Span attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean componentColSpanESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FlowLayoutConstraintImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getFlowLayoutConstraint();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getComponentRowSpan() {
		return componentRowSpan;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentRowSpan(int newComponentRowSpan) {
		int oldComponentRowSpan = componentRowSpan;
		componentRowSpan = newComponentRowSpan;
		boolean oldComponentRowSpanESet = componentRowSpanESet;
		componentRowSpanESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_ROW_SPAN, oldComponentRowSpan, componentRowSpan, !oldComponentRowSpanESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetComponentRowSpan() {
		int oldComponentRowSpan = componentRowSpan;
		boolean oldComponentRowSpanESet = componentRowSpanESet;
		componentRowSpan = COMPONENT_ROW_SPAN_EDEFAULT;
		componentRowSpanESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_ROW_SPAN, oldComponentRowSpan, COMPONENT_ROW_SPAN_EDEFAULT, oldComponentRowSpanESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetComponentRowSpan() {
		return componentRowSpanESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getComponentColSpan() {
		return componentColSpan;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentColSpan(int newComponentColSpan) {
		int oldComponentColSpan = componentColSpan;
		componentColSpan = newComponentColSpan;
		boolean oldComponentColSpanESet = componentColSpanESet;
		componentColSpanESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_COL_SPAN, oldComponentColSpan, componentColSpan, !oldComponentColSpanESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetComponentColSpan() {
		int oldComponentColSpan = componentColSpan;
		boolean oldComponentColSpanESet = componentColSpanESet;
		componentColSpan = COMPONENT_COL_SPAN_EDEFAULT;
		componentColSpanESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_COL_SPAN, oldComponentColSpan, COMPONENT_COL_SPAN_EDEFAULT, oldComponentColSpanESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetComponentColSpan() {
		return componentColSpanESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_ROW_SPAN:
				return getComponentRowSpan();
			case BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_COL_SPAN:
				return getComponentColSpan();
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
			case BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_ROW_SPAN:
				setComponentRowSpan((Integer)newValue);
				return;
			case BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_COL_SPAN:
				setComponentColSpan((Integer)newValue);
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
			case BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_ROW_SPAN:
				unsetComponentRowSpan();
				return;
			case BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_COL_SPAN:
				unsetComponentColSpan();
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
			case BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_ROW_SPAN:
				return isSetComponentRowSpan();
			case BEViewsConfigurationPackage.FLOW_LAYOUT_CONSTRAINT__COMPONENT_COL_SPAN:
				return isSetComponentColSpan();
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
		result.append(" (componentRowSpan: ");
		if (componentRowSpanESet) result.append(componentRowSpan); else result.append("<unset>");
		result.append(", componentColSpan: ");
		if (componentColSpanESet) result.append(componentColSpan); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //FlowLayoutConstraintImpl
