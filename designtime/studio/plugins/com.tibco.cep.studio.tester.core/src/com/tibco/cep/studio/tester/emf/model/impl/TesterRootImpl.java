/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.tester.emf.model.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.studio.tester.emf.model.ConceptType;
import com.tibco.cep.studio.tester.emf.model.EventType;
import com.tibco.cep.studio.tester.emf.model.ExecutionObjectType;
import com.tibco.cep.studio.tester.emf.model.ModelPackage;
import com.tibco.cep.studio.tester.emf.model.OperationObjectType;
import com.tibco.cep.studio.tester.emf.model.ReteObjectType;
import com.tibco.cep.studio.tester.emf.model.TesterResultsType;
import com.tibco.cep.studio.tester.emf.model.TesterRoot;
import com.tibco.cep.studio.tester.emf.model.ValueType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tester Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl#getMixed <em>Mixed</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl#getConcept <em>Concept</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl#getEvent <em>Event</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl#getExecutionObject <em>Execution Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl#getNewValue <em>New Value</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl#getOldValue <em>Old Value</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl#getOperationObject <em>Operation Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl#getReteObject <em>Rete Object</em>}</li>
 *   <li>{@link com.tibco.cep.studio.tester.emf.model.impl.TesterRootImpl#getTesterResults <em>Tester Results</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TesterRootImpl extends EObjectImpl implements TesterRoot {
	/**
	 * The cached value of the '{@link #getMixed() <em>Mixed</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMixed()
	 * @generated
	 * @ordered
	 */
	protected FeatureMap mixed;

	/**
	 * The cached value of the '{@link #getXMLNSPrefixMap() <em>XMLNS Prefix Map</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXMLNSPrefixMap()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xMLNSPrefixMap;

	/**
	 * The cached value of the '{@link #getXSISchemaLocation() <em>XSI Schema Location</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXSISchemaLocation()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> xSISchemaLocation;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TesterRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.TESTER_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatureMap getMixed() {
		if (mixed == null) {
			mixed = new BasicFeatureMap(this, ModelPackage.TESTER_ROOT__MIXED);
		}
		return mixed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXMLNSPrefixMap() {
		if (xMLNSPrefixMap == null) {
			xMLNSPrefixMap = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, ModelPackage.TESTER_ROOT__XMLNS_PREFIX_MAP);
		}
		return xMLNSPrefixMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getXSISchemaLocation() {
		if (xSISchemaLocation == null) {
			xSISchemaLocation = new EcoreEMap<String,String>(EcorePackage.Literals.ESTRING_TO_STRING_MAP_ENTRY, EStringToStringMapEntryImpl.class, this, ModelPackage.TESTER_ROOT__XSI_SCHEMA_LOCATION);
		}
		return xSISchemaLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConceptType getConcept() {
		return (ConceptType)getMixed().get(ModelPackage.Literals.TESTER_ROOT__CONCEPT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConcept(ConceptType newConcept, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.TESTER_ROOT__CONCEPT, newConcept, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConcept(ConceptType newConcept) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.TESTER_ROOT__CONCEPT, newConcept);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EventType getEvent() {
		return (EventType)getMixed().get(ModelPackage.Literals.TESTER_ROOT__EVENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvent(EventType newEvent, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.TESTER_ROOT__EVENT, newEvent, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvent(EventType newEvent) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.TESTER_ROOT__EVENT, newEvent);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionObjectType getExecutionObject() {
		return (ExecutionObjectType)getMixed().get(ModelPackage.Literals.TESTER_ROOT__EXECUTION_OBJECT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExecutionObject(ExecutionObjectType newExecutionObject, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.TESTER_ROOT__EXECUTION_OBJECT, newExecutionObject, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecutionObject(ExecutionObjectType newExecutionObject) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.TESTER_ROOT__EXECUTION_OBJECT, newExecutionObject);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueType getNewValue() {
		return (ValueType)getMixed().get(ModelPackage.Literals.TESTER_ROOT__NEW_VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNewValue(ValueType newNewValue, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.TESTER_ROOT__NEW_VALUE, newNewValue, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNewValue(ValueType newNewValue) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.TESTER_ROOT__NEW_VALUE, newNewValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueType getOldValue() {
		return (ValueType)getMixed().get(ModelPackage.Literals.TESTER_ROOT__OLD_VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOldValue(ValueType newOldValue, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.TESTER_ROOT__OLD_VALUE, newOldValue, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOldValue(ValueType newOldValue) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.TESTER_ROOT__OLD_VALUE, newOldValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperationObjectType getOperationObject() {
		return (OperationObjectType)getMixed().get(ModelPackage.Literals.TESTER_ROOT__OPERATION_OBJECT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOperationObject(OperationObjectType newOperationObject, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.TESTER_ROOT__OPERATION_OBJECT, newOperationObject, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperationObject(OperationObjectType newOperationObject) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.TESTER_ROOT__OPERATION_OBJECT, newOperationObject);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ReteObjectType getReteObject() {
		return (ReteObjectType)getMixed().get(ModelPackage.Literals.TESTER_ROOT__RETE_OBJECT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetReteObject(ReteObjectType newReteObject, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.TESTER_ROOT__RETE_OBJECT, newReteObject, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReteObject(ReteObjectType newReteObject) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.TESTER_ROOT__RETE_OBJECT, newReteObject);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TesterResultsType getTesterResults() {
		return (TesterResultsType)getMixed().get(ModelPackage.Literals.TESTER_ROOT__TESTER_RESULTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTesterResults(TesterResultsType newTesterResults, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.TESTER_ROOT__TESTER_RESULTS, newTesterResults, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTesterResults(TesterResultsType newTesterResults) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.TESTER_ROOT__TESTER_RESULTS, newTesterResults);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.TESTER_ROOT__MIXED:
				return ((InternalEList<?>)getMixed()).basicRemove(otherEnd, msgs);
			case ModelPackage.TESTER_ROOT__XMLNS_PREFIX_MAP:
				return ((InternalEList<?>)getXMLNSPrefixMap()).basicRemove(otherEnd, msgs);
			case ModelPackage.TESTER_ROOT__XSI_SCHEMA_LOCATION:
				return ((InternalEList<?>)getXSISchemaLocation()).basicRemove(otherEnd, msgs);
			case ModelPackage.TESTER_ROOT__CONCEPT:
				return basicSetConcept(null, msgs);
			case ModelPackage.TESTER_ROOT__EVENT:
				return basicSetEvent(null, msgs);
			case ModelPackage.TESTER_ROOT__EXECUTION_OBJECT:
				return basicSetExecutionObject(null, msgs);
			case ModelPackage.TESTER_ROOT__NEW_VALUE:
				return basicSetNewValue(null, msgs);
			case ModelPackage.TESTER_ROOT__OLD_VALUE:
				return basicSetOldValue(null, msgs);
			case ModelPackage.TESTER_ROOT__OPERATION_OBJECT:
				return basicSetOperationObject(null, msgs);
			case ModelPackage.TESTER_ROOT__RETE_OBJECT:
				return basicSetReteObject(null, msgs);
			case ModelPackage.TESTER_ROOT__TESTER_RESULTS:
				return basicSetTesterResults(null, msgs);
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
			case ModelPackage.TESTER_ROOT__MIXED:
				if (coreType) return getMixed();
				return ((FeatureMap.Internal)getMixed()).getWrapper();
			case ModelPackage.TESTER_ROOT__XMLNS_PREFIX_MAP:
				if (coreType) return getXMLNSPrefixMap();
				else return getXMLNSPrefixMap().map();
			case ModelPackage.TESTER_ROOT__XSI_SCHEMA_LOCATION:
				if (coreType) return getXSISchemaLocation();
				else return getXSISchemaLocation().map();
			case ModelPackage.TESTER_ROOT__CONCEPT:
				return getConcept();
			case ModelPackage.TESTER_ROOT__EVENT:
				return getEvent();
			case ModelPackage.TESTER_ROOT__EXECUTION_OBJECT:
				return getExecutionObject();
			case ModelPackage.TESTER_ROOT__NEW_VALUE:
				return getNewValue();
			case ModelPackage.TESTER_ROOT__OLD_VALUE:
				return getOldValue();
			case ModelPackage.TESTER_ROOT__OPERATION_OBJECT:
				return getOperationObject();
			case ModelPackage.TESTER_ROOT__RETE_OBJECT:
				return getReteObject();
			case ModelPackage.TESTER_ROOT__TESTER_RESULTS:
				return getTesterResults();
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
			case ModelPackage.TESTER_ROOT__MIXED:
				((FeatureMap.Internal)getMixed()).set(newValue);
				return;
			case ModelPackage.TESTER_ROOT__XMLNS_PREFIX_MAP:
				((EStructuralFeature.Setting)getXMLNSPrefixMap()).set(newValue);
				return;
			case ModelPackage.TESTER_ROOT__XSI_SCHEMA_LOCATION:
				((EStructuralFeature.Setting)getXSISchemaLocation()).set(newValue);
				return;
			case ModelPackage.TESTER_ROOT__CONCEPT:
				setConcept((ConceptType)newValue);
				return;
			case ModelPackage.TESTER_ROOT__EVENT:
				setEvent((EventType)newValue);
				return;
			case ModelPackage.TESTER_ROOT__EXECUTION_OBJECT:
				setExecutionObject((ExecutionObjectType)newValue);
				return;
			case ModelPackage.TESTER_ROOT__NEW_VALUE:
				setNewValue((ValueType)newValue);
				return;
			case ModelPackage.TESTER_ROOT__OLD_VALUE:
				setOldValue((ValueType)newValue);
				return;
			case ModelPackage.TESTER_ROOT__OPERATION_OBJECT:
				setOperationObject((OperationObjectType)newValue);
				return;
			case ModelPackage.TESTER_ROOT__RETE_OBJECT:
				setReteObject((ReteObjectType)newValue);
				return;
			case ModelPackage.TESTER_ROOT__TESTER_RESULTS:
				setTesterResults((TesterResultsType)newValue);
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
			case ModelPackage.TESTER_ROOT__MIXED:
				getMixed().clear();
				return;
			case ModelPackage.TESTER_ROOT__XMLNS_PREFIX_MAP:
				getXMLNSPrefixMap().clear();
				return;
			case ModelPackage.TESTER_ROOT__XSI_SCHEMA_LOCATION:
				getXSISchemaLocation().clear();
				return;
			case ModelPackage.TESTER_ROOT__CONCEPT:
				setConcept((ConceptType)null);
				return;
			case ModelPackage.TESTER_ROOT__EVENT:
				setEvent((EventType)null);
				return;
			case ModelPackage.TESTER_ROOT__EXECUTION_OBJECT:
				setExecutionObject((ExecutionObjectType)null);
				return;
			case ModelPackage.TESTER_ROOT__NEW_VALUE:
				setNewValue((ValueType)null);
				return;
			case ModelPackage.TESTER_ROOT__OLD_VALUE:
				setOldValue((ValueType)null);
				return;
			case ModelPackage.TESTER_ROOT__OPERATION_OBJECT:
				setOperationObject((OperationObjectType)null);
				return;
			case ModelPackage.TESTER_ROOT__RETE_OBJECT:
				setReteObject((ReteObjectType)null);
				return;
			case ModelPackage.TESTER_ROOT__TESTER_RESULTS:
				setTesterResults((TesterResultsType)null);
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
			case ModelPackage.TESTER_ROOT__MIXED:
				return mixed != null && !mixed.isEmpty();
			case ModelPackage.TESTER_ROOT__XMLNS_PREFIX_MAP:
				return xMLNSPrefixMap != null && !xMLNSPrefixMap.isEmpty();
			case ModelPackage.TESTER_ROOT__XSI_SCHEMA_LOCATION:
				return xSISchemaLocation != null && !xSISchemaLocation.isEmpty();
			case ModelPackage.TESTER_ROOT__CONCEPT:
				return getConcept() != null;
			case ModelPackage.TESTER_ROOT__EVENT:
				return getEvent() != null;
			case ModelPackage.TESTER_ROOT__EXECUTION_OBJECT:
				return getExecutionObject() != null;
			case ModelPackage.TESTER_ROOT__NEW_VALUE:
				return getNewValue() != null;
			case ModelPackage.TESTER_ROOT__OLD_VALUE:
				return getOldValue() != null;
			case ModelPackage.TESTER_ROOT__OPERATION_OBJECT:
				return getOperationObject() != null;
			case ModelPackage.TESTER_ROOT__RETE_OBJECT:
				return getReteObject() != null;
			case ModelPackage.TESTER_ROOT__TESTER_RESULTS:
				return getTesterResults() != null;
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
		result.append(" (mixed: ");
		result.append(mixed);
		result.append(')');
		return result.toString();
	}

} //TesterRootImpl
