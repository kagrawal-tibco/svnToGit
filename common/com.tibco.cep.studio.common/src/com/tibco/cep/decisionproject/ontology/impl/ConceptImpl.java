/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decisionproject.ontology.impl;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.decisionproject.ontology.AbstractResource;
import com.tibco.cep.decisionproject.ontology.ArgumentResource;
import com.tibco.cep.decisionproject.ontology.Concept;
import com.tibco.cep.decisionproject.ontology.OntologyPackage;
import com.tibco.cep.decisionproject.ontology.Property;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Concept</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ConceptImpl#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ConceptImpl#getSuperConceptPath <em>Super Concept Path</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ConceptImpl#isScoreCard <em>Score Card</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ConceptImpl#getProperty <em>Property</em>}</li>
 *   <li>{@link com.tibco.cep.decisionproject.ontology.impl.ConceptImpl#isDbConcept <em>Db Concept</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConceptImpl extends ParentResourceImpl implements Concept {
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
	 * The default value of the '{@link #isScoreCard() <em>Score Card</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isScoreCard()
	 * @generated
	 * @ordered
	 */
	protected static final boolean SCORE_CARD_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isScoreCard() <em>Score Card</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isScoreCard()
	 * @generated
	 * @ordered
	 */
	protected boolean scoreCard = SCORE_CARD_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProperty() <em>Property</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperty()
	 * @generated NOT
	 * @ordered
	 */
	protected EList<AbstractResource> property;

	/**
	 * The default value of the '{@link #isDbConcept() <em>Db Concept</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDbConcept()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DB_CONCEPT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isDbConcept() <em>Db Concept</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDbConcept()
	 * @generated
	 * @ordered
	 */
	protected boolean dbConcept = DB_CONCEPT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
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
		return OntologyPackage.Literals.CONCEPT;
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
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.CONCEPT__ALIAS, oldAlias, alias));
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
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.CONCEPT__SUPER_CONCEPT_PATH, oldSuperConceptPath, superConceptPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isScoreCard() {
		return scoreCard;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScoreCard(boolean newScoreCard) {
		boolean oldScoreCard = scoreCard;
		scoreCard = newScoreCard;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.CONCEPT__SCORE_CARD, oldScoreCard, scoreCard));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AbstractResource> getProperty() {
		if (property == null) {
			property = new EObjectContainmentEList<AbstractResource>(AbstractResource.class, this, OntologyPackage.ABSTRACT_RESOURCE);
		}
		return property;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isDbConcept() {
		return dbConcept;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDbConcept(boolean newDbConcept) {
		boolean oldDbConcept = dbConcept;
		dbConcept = newDbConcept;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, OntologyPackage.CONCEPT__DB_CONCEPT, oldDbConcept, dbConcept));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case OntologyPackage.CONCEPT__PROPERTY:
				return ((InternalEList<?>)getProperty()).basicRemove(otherEnd, msgs);
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
			case OntologyPackage.CONCEPT__ALIAS:
				return getAlias();
			case OntologyPackage.CONCEPT__SUPER_CONCEPT_PATH:
				return getSuperConceptPath();
			case OntologyPackage.CONCEPT__SCORE_CARD:
				return isScoreCard() ? Boolean.TRUE : Boolean.FALSE;
			case OntologyPackage.CONCEPT__PROPERTY:
				return getProperty();
			case OntologyPackage.CONCEPT__DB_CONCEPT:
				return isDbConcept() ? Boolean.TRUE : Boolean.FALSE;
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
			case OntologyPackage.CONCEPT__ALIAS:
				setAlias((String)newValue);
				return;
			case OntologyPackage.CONCEPT__SUPER_CONCEPT_PATH:
				setSuperConceptPath((String)newValue);
				return;
			case OntologyPackage.CONCEPT__SCORE_CARD:
				setScoreCard(((Boolean)newValue).booleanValue());
				return;
			case OntologyPackage.CONCEPT__PROPERTY:
				getProperty().clear();
				getProperty().addAll((Collection<? extends Property>)newValue);
				return;
			case OntologyPackage.CONCEPT__DB_CONCEPT:
				setDbConcept(((Boolean)newValue).booleanValue());
				return;
		}
		super.eSet(featureID, newValue);
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 * not sure why it was made generated not, so commented
	 */
	
	/*
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case OntologyPackage.CONCEPT__SUPER_CONCEPT_PATH:
				setSuperConceptPath((String)newValue);
				return;
			case OntologyPackage.CONCEPT__SCORE_CARD:
				setScoreCard(((Boolean)newValue).booleanValue());
				return;
			case OntologyPackage.CONCEPT__PROPERTY:
				if (property != null) {
					property.clear();
					property.addAll((Collection<? extends Property>)newValue);
				}
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
			case OntologyPackage.CONCEPT__ALIAS:
				setAlias(ALIAS_EDEFAULT);
				return;
			case OntologyPackage.CONCEPT__SUPER_CONCEPT_PATH:
				setSuperConceptPath(SUPER_CONCEPT_PATH_EDEFAULT);
				return;
			case OntologyPackage.CONCEPT__SCORE_CARD:
				setScoreCard(SCORE_CARD_EDEFAULT);
				return;
			case OntologyPackage.CONCEPT__PROPERTY:
				getProperty().clear();
				return;
			case OntologyPackage.CONCEPT__DB_CONCEPT:
				setDbConcept(DB_CONCEPT_EDEFAULT);
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
			case OntologyPackage.CONCEPT__ALIAS:
				return ALIAS_EDEFAULT == null ? alias != null : !ALIAS_EDEFAULT.equals(alias);
			case OntologyPackage.CONCEPT__SUPER_CONCEPT_PATH:
				return SUPER_CONCEPT_PATH_EDEFAULT == null ? superConceptPath != null : !SUPER_CONCEPT_PATH_EDEFAULT.equals(superConceptPath);
			case OntologyPackage.CONCEPT__SCORE_CARD:
				return scoreCard != SCORE_CARD_EDEFAULT;
			case OntologyPackage.CONCEPT__PROPERTY:
				return property != null && !property.isEmpty();
			case OntologyPackage.CONCEPT__DB_CONCEPT:
				return dbConcept != DB_CONCEPT_EDEFAULT;
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
		if (baseClass == ArgumentResource.class) {
			switch (derivedFeatureID) {
				case OntologyPackage.CONCEPT__ALIAS: return OntologyPackage.ARGUMENT_RESOURCE__ALIAS;
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
		if (baseClass == ArgumentResource.class) {
			switch (baseFeatureID) {
				case OntologyPackage.ARGUMENT_RESOURCE__ALIAS: return OntologyPackage.CONCEPT__ALIAS;
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
		result.append(" (alias: ");
		result.append(alias);
		result.append(", superConceptPath: ");
		result.append(superConceptPath);
		result.append(", scoreCard: ");
		result.append(scoreCard);
		result.append(", dbConcept: ");
		result.append(dbConcept);
		result.append(')');
		return result.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.model.impl.ParentResourceImpl#getChildren()
	 */
	@Override
	public Iterator<AbstractResource> getChildren() {
		return getProperty().iterator();
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.decisionproject.ontology.ParentResource#addChild(com.tibco.cep.decisionproject.ontology.AbstractResource)
	 */
	public boolean addChild(AbstractResource abstractResource) {
		/*if (!(abstractResource instanceof Property)) {
			return false;
		}*/
		if (property == null){
			property = new EObjectContainmentEList<AbstractResource>(AbstractResource.class, this, OntologyPackage.ABSTRACT_RESOURCE);
		}
		return property.add(abstractResource);
	}
} //ConceptImpl
