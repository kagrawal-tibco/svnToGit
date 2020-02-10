/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.decisionproject.ontology.Implementation;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Implementation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl#getStyle <em>Style</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl#getImplements <em>Implements</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl#getVersion <em>Version</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl#getLastModifiedBy <em>Last Modified By</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl#getLastModified <em>Last Modified</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl#isDirty <em>Dirty</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl#isLocked <em>Locked</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl#isShowDescription <em>Show Description</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ImplementationImpl extends AbstractResourceImpl implements Implementation {
	/**
	 * The default value of the '{@link #getStyle() <em>Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
	protected static final String STYLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStyle() <em>Style</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStyle()
	 * @generated
	 * @ordered
	 */
	protected String style = STYLE_EDEFAULT;

	/**
	 * The default value of the '{@link #getImplements() <em>Implements</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplements()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplements() <em>Implements</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplements()
	 * @generated
	 * @ordered
	 */
	protected String implements_ = IMPLEMENTS_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final double VERSION_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected double version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastModifiedBy() <em>Last Modified By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModifiedBy()
	 * @generated
	 * @ordered
	 */
	protected static final String LAST_MODIFIED_BY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastModifiedBy() <em>Last Modified By</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModifiedBy()
	 * @generated
	 * @ordered
	 */
	protected String lastModifiedBy = LAST_MODIFIED_BY_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastModified() <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModified()
	 * @generated
	 * @ordered
	 */
	protected static final Date LAST_MODIFIED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastModified() <em>Last Modified</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastModified()
	 * @generated
	 * @ordered
	 */
	protected Date lastModified = LAST_MODIFIED_EDEFAULT;

	/**
	 * The default value of the '{@link #isDirty() <em>Dirty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirty()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DIRTY_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDirty() <em>Dirty</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDirty()
	 * @generated
	 * @ordered
	 */
	protected boolean dirty = DIRTY_EDEFAULT;

	/**
	 * The default value of the '{@link #isLocked() <em>Locked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLocked()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOCKED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLocked() <em>Locked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLocked()
	 * @generated
	 * @ordered
	 */
	protected boolean locked = LOCKED_EDEFAULT;

	/**
	 * The default value of the '{@link #isShowDescription() <em>Show Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowDescription()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SHOW_DESCRIPTION_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isShowDescription() <em>Show Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isShowDescription()
	 * @generated
	 * @ordered
	 */
	protected boolean showDescription = SHOW_DESCRIPTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImplementationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OntologyPackage.Literals.IMPLEMENTATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStyle() {
		return style;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStyle(String newStyle) {
		String oldStyle = style;
		style = newStyle;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.IMPLEMENTATION__STYLE, oldStyle, style));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplements() {
		return implements_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplements(String newImplements) {
		String oldImplements = implements_;
		implements_ = newImplements;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.IMPLEMENTATION__IMPLEMENTS, oldImplements, implements_));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(double newVersion) {
		double oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.IMPLEMENTATION__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastModifiedBy(String newLastModifiedBy) {
		String oldLastModifiedBy = lastModifiedBy;
		lastModifiedBy = newLastModifiedBy;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.IMPLEMENTATION__LAST_MODIFIED_BY, oldLastModifiedBy, lastModifiedBy));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getLastModified() {
		return lastModified;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastModified(Date newLastModified) {
		Date oldLastModified = lastModified;
		lastModified = newLastModified;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.IMPLEMENTATION__LAST_MODIFIED, oldLastModified, lastModified));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDirty() {
		return dirty;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDirty(boolean newDirty) {
		boolean oldDirty = dirty;
		dirty = newDirty;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.IMPLEMENTATION__DIRTY, oldDirty, dirty));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocked(boolean newLocked) {
		boolean oldLocked = locked;
		locked = newLocked;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.IMPLEMENTATION__LOCKED, oldLocked, locked));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isShowDescription() {
		return showDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShowDescription(boolean newShowDescription) {
		boolean oldShowDescription = showDescription;
		showDescription = newShowDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.IMPLEMENTATION__SHOW_DESCRIPTION, oldShowDescription, showDescription));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case OntologyPackage.IMPLEMENTATION__STYLE:
				return getStyle();
			case OntologyPackage.IMPLEMENTATION__IMPLEMENTS:
				return getImplements();
			case OntologyPackage.IMPLEMENTATION__VERSION:
				return new Double(getVersion());
			case OntologyPackage.IMPLEMENTATION__LAST_MODIFIED_BY:
				return getLastModifiedBy();
			case OntologyPackage.IMPLEMENTATION__LAST_MODIFIED:
				return getLastModified();
			case OntologyPackage.IMPLEMENTATION__DIRTY:
				return isDirty() ? Boolean.TRUE : Boolean.FALSE;
			case OntologyPackage.IMPLEMENTATION__LOCKED:
				return isLocked() ? Boolean.TRUE : Boolean.FALSE;
			case OntologyPackage.IMPLEMENTATION__SHOW_DESCRIPTION:
				return isShowDescription() ? Boolean.TRUE : Boolean.FALSE;
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
			case OntologyPackage.IMPLEMENTATION__STYLE:
				setStyle((String)newValue);
				return;
			case OntologyPackage.IMPLEMENTATION__IMPLEMENTS:
				setImplements((String)newValue);
				return;
			case OntologyPackage.IMPLEMENTATION__VERSION:
				setVersion(((Double)newValue).doubleValue());
				return;
			case OntologyPackage.IMPLEMENTATION__LAST_MODIFIED_BY:
				setLastModifiedBy((String)newValue);
				return;
			case OntologyPackage.IMPLEMENTATION__LAST_MODIFIED:
				setLastModified((Date)newValue);
				return;
			case OntologyPackage.IMPLEMENTATION__DIRTY:
				setDirty(((Boolean)newValue).booleanValue());
				return;
			case OntologyPackage.IMPLEMENTATION__LOCKED:
				setLocked(((Boolean)newValue).booleanValue());
				return;
			case OntologyPackage.IMPLEMENTATION__SHOW_DESCRIPTION:
				setShowDescription(((Boolean)newValue).booleanValue());
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
			case OntologyPackage.IMPLEMENTATION__STYLE:
				setStyle(STYLE_EDEFAULT);
				return;
			case OntologyPackage.IMPLEMENTATION__IMPLEMENTS:
				setImplements(IMPLEMENTS_EDEFAULT);
				return;
			case OntologyPackage.IMPLEMENTATION__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case OntologyPackage.IMPLEMENTATION__LAST_MODIFIED_BY:
				setLastModifiedBy(LAST_MODIFIED_BY_EDEFAULT);
				return;
			case OntologyPackage.IMPLEMENTATION__LAST_MODIFIED:
				setLastModified(LAST_MODIFIED_EDEFAULT);
				return;
			case OntologyPackage.IMPLEMENTATION__DIRTY:
				setDirty(DIRTY_EDEFAULT);
				return;
			case OntologyPackage.IMPLEMENTATION__LOCKED:
				setLocked(LOCKED_EDEFAULT);
				return;
			case OntologyPackage.IMPLEMENTATION__SHOW_DESCRIPTION:
				setShowDescription(SHOW_DESCRIPTION_EDEFAULT);
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
			case OntologyPackage.IMPLEMENTATION__STYLE:
				return STYLE_EDEFAULT == null ? style != null : !STYLE_EDEFAULT.equals(style);
			case OntologyPackage.IMPLEMENTATION__IMPLEMENTS:
				return IMPLEMENTS_EDEFAULT == null ? implements_ != null : !IMPLEMENTS_EDEFAULT.equals(implements_);
			case OntologyPackage.IMPLEMENTATION__VERSION:
				return version != VERSION_EDEFAULT;
			case OntologyPackage.IMPLEMENTATION__LAST_MODIFIED_BY:
				return LAST_MODIFIED_BY_EDEFAULT == null ? lastModifiedBy != null : !LAST_MODIFIED_BY_EDEFAULT.equals(lastModifiedBy);
			case OntologyPackage.IMPLEMENTATION__LAST_MODIFIED:
				return LAST_MODIFIED_EDEFAULT == null ? lastModified != null : !LAST_MODIFIED_EDEFAULT.equals(lastModified);
			case OntologyPackage.IMPLEMENTATION__DIRTY:
				return dirty != DIRTY_EDEFAULT;
			case OntologyPackage.IMPLEMENTATION__LOCKED:
				return locked != LOCKED_EDEFAULT;
			case OntologyPackage.IMPLEMENTATION__SHOW_DESCRIPTION:
				return showDescription != SHOW_DESCRIPTION_EDEFAULT;
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
		result.append(" (style: ");
		result.append(style);
		result.append(", implements: ");
		result.append(implements_);
		result.append(", version: ");
		result.append(version);
		result.append(", lastModifiedBy: ");
		result.append(lastModifiedBy);
		result.append(", lastModified: ");
		result.append(lastModified);
		result.append(", dirty: ");
		result.append(dirty);
		result.append(", locked: ");
		result.append(locked);
		result.append(", showDescription: ");
		result.append(showDescription);
		result.append(')');
		return result.toString();
	}

} //ImplementationImpl
