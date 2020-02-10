/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.beviewsconfig.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.beviewsconfig.BEViewsConfigurationPackage;
import com.tibco.cep.designtime.core.model.beviewsconfig.DisplayModeEnum;
import com.tibco.cep.designtime.core.model.beviewsconfig.Page;
import com.tibco.cep.designtime.core.model.beviewsconfig.PageVisualization;
import com.tibco.cep.designtime.core.model.beviewsconfig.Partition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Page</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PageImpl#getDisplayName <em>Display Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PageImpl#getDisplayMode <em>Display Mode</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PageImpl#getPartition <em>Partition</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.beviewsconfig.impl.PageImpl#getVisualization <em>Visualization</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class PageImpl extends BEViewsElementImpl implements Page {
	/**
	 * The default value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected static final String DISPLAY_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDisplayName() <em>Display Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayName()
	 * @generated
	 * @ordered
	 */
	protected String displayName = DISPLAY_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDisplayMode() <em>Display Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayMode()
	 * @generated
	 * @ordered
	 */
	protected static final DisplayModeEnum DISPLAY_MODE_EDEFAULT = DisplayModeEnum.INPLACE;

	/**
	 * The cached value of the '{@link #getDisplayMode() <em>Display Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDisplayMode()
	 * @generated
	 * @ordered
	 */
	protected DisplayModeEnum displayMode = DISPLAY_MODE_EDEFAULT;

	/**
	 * This is true if the Display Mode attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean displayModeESet;

	/**
	 * The cached value of the '{@link #getPartition() <em>Partition</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPartition()
	 * @generated
	 * @ordered
	 */
	protected EList<Partition> partition;

	/**
	 * The cached value of the '{@link #getVisualization() <em>Visualization</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVisualization()
	 * @generated
	 * @ordered
	 */
	protected PageVisualization visualization;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BEViewsConfigurationPackage.eINSTANCE.getPage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayName(String newDisplayName) {
		String oldDisplayName = displayName;
		displayName = newDisplayName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PAGE__DISPLAY_NAME, oldDisplayName, displayName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DisplayModeEnum getDisplayMode() {
		return displayMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDisplayMode(DisplayModeEnum newDisplayMode) {
		DisplayModeEnum oldDisplayMode = displayMode;
		displayMode = newDisplayMode == null ? DISPLAY_MODE_EDEFAULT : newDisplayMode;
		boolean oldDisplayModeESet = displayModeESet;
		displayModeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PAGE__DISPLAY_MODE, oldDisplayMode, displayMode, !oldDisplayModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDisplayMode() {
		DisplayModeEnum oldDisplayMode = displayMode;
		boolean oldDisplayModeESet = displayModeESet;
		displayMode = DISPLAY_MODE_EDEFAULT;
		displayModeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BEViewsConfigurationPackage.PAGE__DISPLAY_MODE, oldDisplayMode, DISPLAY_MODE_EDEFAULT, oldDisplayModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDisplayMode() {
		return displayModeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Partition> getPartition() {
		if (partition == null) {
			partition = new EObjectContainmentEList<Partition>(Partition.class, this, BEViewsConfigurationPackage.PAGE__PARTITION);
		}
		return partition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PageVisualization getVisualization() {
		return visualization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetVisualization(PageVisualization newVisualization, NotificationChain msgs) {
		PageVisualization oldVisualization = visualization;
		visualization = newVisualization;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PAGE__VISUALIZATION, oldVisualization, newVisualization);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisualization(PageVisualization newVisualization) {
		if (newVisualization != visualization) {
			NotificationChain msgs = null;
			if (visualization != null)
				msgs = ((InternalEObject)visualization).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PAGE__VISUALIZATION, null, msgs);
			if (newVisualization != null)
				msgs = ((InternalEObject)newVisualization).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BEViewsConfigurationPackage.PAGE__VISUALIZATION, null, msgs);
			msgs = basicSetVisualization(newVisualization, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BEViewsConfigurationPackage.PAGE__VISUALIZATION, newVisualization, newVisualization));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BEViewsConfigurationPackage.PAGE__PARTITION:
				return ((InternalEList<?>)getPartition()).basicRemove(otherEnd, msgs);
			case BEViewsConfigurationPackage.PAGE__VISUALIZATION:
				return basicSetVisualization(null, msgs);
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
			case BEViewsConfigurationPackage.PAGE__DISPLAY_NAME:
				return getDisplayName();
			case BEViewsConfigurationPackage.PAGE__DISPLAY_MODE:
				return getDisplayMode();
			case BEViewsConfigurationPackage.PAGE__PARTITION:
				return getPartition();
			case BEViewsConfigurationPackage.PAGE__VISUALIZATION:
				return getVisualization();
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
			case BEViewsConfigurationPackage.PAGE__DISPLAY_NAME:
				setDisplayName((String)newValue);
				return;
			case BEViewsConfigurationPackage.PAGE__DISPLAY_MODE:
				setDisplayMode((DisplayModeEnum)newValue);
				return;
			case BEViewsConfigurationPackage.PAGE__PARTITION:
				getPartition().clear();
				getPartition().addAll((Collection<? extends Partition>)newValue);
				return;
			case BEViewsConfigurationPackage.PAGE__VISUALIZATION:
				setVisualization((PageVisualization)newValue);
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
			case BEViewsConfigurationPackage.PAGE__DISPLAY_NAME:
				setDisplayName(DISPLAY_NAME_EDEFAULT);
				return;
			case BEViewsConfigurationPackage.PAGE__DISPLAY_MODE:
				unsetDisplayMode();
				return;
			case BEViewsConfigurationPackage.PAGE__PARTITION:
				getPartition().clear();
				return;
			case BEViewsConfigurationPackage.PAGE__VISUALIZATION:
				setVisualization((PageVisualization)null);
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
			case BEViewsConfigurationPackage.PAGE__DISPLAY_NAME:
				return DISPLAY_NAME_EDEFAULT == null ? displayName != null : !DISPLAY_NAME_EDEFAULT.equals(displayName);
			case BEViewsConfigurationPackage.PAGE__DISPLAY_MODE:
				return isSetDisplayMode();
			case BEViewsConfigurationPackage.PAGE__PARTITION:
				return partition != null && !partition.isEmpty();
			case BEViewsConfigurationPackage.PAGE__VISUALIZATION:
				return visualization != null;
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
		result.append(" (displayName: ");
		result.append(displayName);
		result.append(", displayMode: ");
		if (displayModeESet) result.append(displayMode); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //PageImpl
