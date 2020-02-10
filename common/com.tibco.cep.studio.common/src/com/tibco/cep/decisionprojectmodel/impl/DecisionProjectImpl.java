/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionprojectmodel.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decisionproject.ontology.Ontology;
import com.tibco.cep.decisionprojectmodel.DecisionProject;
import com.tibco.cep.decisionprojectmodel.DecisionProjectModelPackage;
import com.tibco.cep.decisionprojectmodel.DomainModel;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Decision Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.impl.DecisionProjectImpl#getOntology <em>Ontology</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.impl.DecisionProjectImpl#getDomainModel <em>Domain Model</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.impl.DecisionProjectImpl#getAuthToken <em>Auth Token</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.impl.DecisionProjectImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.impl.DecisionProjectImpl#getMd <em>Md</em>}</li>
 *   <li>{@link com.tibco.cep.decisionprojectmodel.impl.DecisionProjectImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DecisionProjectImpl extends EObjectImpl implements DecisionProject {
	/**
	 * The cached value of the '{@link #getOntology() <em>Ontology</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOntology()
	 * @generated
	 * @ordered
	 */
	protected Ontology ontology;

	/**
	 * The cached value of the '{@link #getDomainModel() <em>Domain Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainModel()
	 * @generated
	 * @ordered
	 */
	protected DomainModel domainModel;

	/**
	 * The default value of the '{@link #getAuthToken() <em>Auth Token</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthToken()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTH_TOKEN_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthToken() <em>Auth Token</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAuthToken()
	 * @generated
	 * @ordered
	 */
	protected String authToken = AUTH_TOKEN_EDEFAULT;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getMd() <em>Md</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMd()
	 * @generated
	 * @ordered
	 */
	protected MetaData md;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated NOT
	 * @ordered
	 */
	protected static final double VERSION_EDEFAULT = Double.MIN_VALUE;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DecisionProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DecisionProjectModelPackage.Literals.DECISION_PROJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Ontology getOntology() {
		return ontology;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOntology(Ontology newOntology, NotificationChain msgs) {
		Ontology oldOntology = ontology;
		ontology = newOntology;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DecisionProjectModelPackage.DECISION_PROJECT__ONTOLOGY, oldOntology, newOntology);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOntology(Ontology newOntology) {
		if (newOntology != ontology) {
			NotificationChain msgs = null;
			if (ontology != null)
				msgs = ((InternalEObject)ontology).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DecisionProjectModelPackage.DECISION_PROJECT__ONTOLOGY, null, msgs);
			if (newOntology != null)
				msgs = ((InternalEObject)newOntology).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DecisionProjectModelPackage.DECISION_PROJECT__ONTOLOGY, null, msgs);
			msgs = basicSetOntology(newOntology, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DecisionProjectModelPackage.DECISION_PROJECT__ONTOLOGY, newOntology, newOntology));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainModel getDomainModel() {
		if (domainModel != null && domainModel.eIsProxy()) {
			InternalEObject oldDomainModel = (InternalEObject)domainModel;
			domainModel = (DomainModel)eResolveProxy(oldDomainModel);
			if (domainModel != oldDomainModel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DecisionProjectModelPackage.DECISION_PROJECT__DOMAIN_MODEL, oldDomainModel, domainModel));
			}
		}
		return domainModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainModel basicGetDomainModel() {
		return domainModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomainModel(DomainModel newDomainModel) {
		DomainModel oldDomainModel = domainModel;
		domainModel = newDomainModel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DecisionProjectModelPackage.DECISION_PROJECT__DOMAIN_MODEL, oldDomainModel, domainModel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAuthToken() {
		return authToken;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAuthToken(String newAuthToken) {
		String oldAuthToken = authToken;
		authToken = newAuthToken;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DecisionProjectModelPackage.DECISION_PROJECT__AUTH_TOKEN, oldAuthToken, authToken));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DecisionProjectModelPackage.DECISION_PROJECT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetaData getMd() {
		return md;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMd(MetaData newMd, NotificationChain msgs) {
		MetaData oldMd = md;
		md = newMd;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DecisionProjectModelPackage.DECISION_PROJECT__MD, oldMd, newMd);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMd(MetaData newMd) {
		if (newMd != md) {
			NotificationChain msgs = null;
			if (md != null)
				msgs = ((InternalEObject)md).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DecisionProjectModelPackage.DECISION_PROJECT__MD, null, msgs);
			if (newMd != null)
				msgs = ((InternalEObject)newMd).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DecisionProjectModelPackage.DECISION_PROJECT__MD, null, msgs);
			msgs = basicSetMd(newMd, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DecisionProjectModelPackage.DECISION_PROJECT__MD, newMd, newMd));
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
			eNotify(new ENotificationImpl(this, Notification.SET, DecisionProjectModelPackage.DECISION_PROJECT__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DecisionProjectModelPackage.DECISION_PROJECT__ONTOLOGY:
				return basicSetOntology(null, msgs);
			case DecisionProjectModelPackage.DECISION_PROJECT__MD:
				return basicSetMd(null, msgs);
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
			case DecisionProjectModelPackage.DECISION_PROJECT__ONTOLOGY:
				return getOntology();
			case DecisionProjectModelPackage.DECISION_PROJECT__DOMAIN_MODEL:
				if (resolve) return getDomainModel();
				return basicGetDomainModel();
			case DecisionProjectModelPackage.DECISION_PROJECT__AUTH_TOKEN:
				return getAuthToken();
			case DecisionProjectModelPackage.DECISION_PROJECT__NAME:
				return getName();
			case DecisionProjectModelPackage.DECISION_PROJECT__MD:
				return getMd();
			case DecisionProjectModelPackage.DECISION_PROJECT__VERSION:
				return new Double(getVersion());
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
			case DecisionProjectModelPackage.DECISION_PROJECT__ONTOLOGY:
				setOntology((Ontology)newValue);
				return;
			case DecisionProjectModelPackage.DECISION_PROJECT__DOMAIN_MODEL:
				setDomainModel((DomainModel)newValue);
				return;
			case DecisionProjectModelPackage.DECISION_PROJECT__AUTH_TOKEN:
				setAuthToken((String)newValue);
				return;
			case DecisionProjectModelPackage.DECISION_PROJECT__NAME:
				setName((String)newValue);
				return;
			case DecisionProjectModelPackage.DECISION_PROJECT__MD:
				setMd((MetaData)newValue);
				return;
			case DecisionProjectModelPackage.DECISION_PROJECT__VERSION:
				setVersion(((Double)newValue).doubleValue());
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
			case DecisionProjectModelPackage.DECISION_PROJECT__ONTOLOGY:
				setOntology((Ontology)null);
				return;
			case DecisionProjectModelPackage.DECISION_PROJECT__DOMAIN_MODEL:
				setDomainModel((DomainModel)null);
				return;
			case DecisionProjectModelPackage.DECISION_PROJECT__AUTH_TOKEN:
				setAuthToken(AUTH_TOKEN_EDEFAULT);
				return;
			case DecisionProjectModelPackage.DECISION_PROJECT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case DecisionProjectModelPackage.DECISION_PROJECT__MD:
				setMd((MetaData)null);
				return;
			case DecisionProjectModelPackage.DECISION_PROJECT__VERSION:
				setVersion(VERSION_EDEFAULT);
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
			case DecisionProjectModelPackage.DECISION_PROJECT__ONTOLOGY:
				return ontology != null;
			case DecisionProjectModelPackage.DECISION_PROJECT__DOMAIN_MODEL:
				return domainModel != null;
			case DecisionProjectModelPackage.DECISION_PROJECT__AUTH_TOKEN:
				return AUTH_TOKEN_EDEFAULT == null ? authToken != null : !AUTH_TOKEN_EDEFAULT.equals(authToken);
			case DecisionProjectModelPackage.DECISION_PROJECT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case DecisionProjectModelPackage.DECISION_PROJECT__MD:
				return md != null;
			case DecisionProjectModelPackage.DECISION_PROJECT__VERSION:
				return version != VERSION_EDEFAULT;
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
		result.append(" (authToken: ");
		result.append(authToken);
		result.append(", name: ");
		result.append(name);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //DecisionProjectImpl
