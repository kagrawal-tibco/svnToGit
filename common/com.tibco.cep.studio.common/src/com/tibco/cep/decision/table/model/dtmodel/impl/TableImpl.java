/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.decision.table.model.dtmodel.Argument;
import com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage;
import com.tibco.cep.decision.table.model.dtmodel.MetaData;
import com.tibco.cep.decision.table.model.dtmodel.Metadatable;
import com.tibco.cep.decision.table.model.dtmodel.Table;
import com.tibco.cep.decision.table.model.dtmodel.TableRuleSet;
import com.tibco.cep.decisionproject.ontology.impl.ImplementationImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Table</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl#getMd <em>Md</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl#getDecisionTable <em>Decision Table</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl#getExceptionTable <em>Exception Table</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl#getArgument <em>Argument</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.impl.TableImpl#getSince <em>Since</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TableImpl extends ImplementationImpl implements Table {
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
	 * The cached value of the '{@link #getDecisionTable() <em>Decision Table</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDecisionTable()
	 * @generated
	 * @ordered
	 */
	protected TableRuleSet decisionTable;

	/**
	 * The cached value of the '{@link #getExceptionTable() <em>Exception Table</em>}' containment reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getExceptionTable()
	 * @generated
	 * @ordered
	 */
	protected TableRuleSet exceptionTable;

	/**
	 * The cached value of the '{@link #getArgument() <em>Argument</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getArgument()
	 * @generated
	 * @ordered
	 */
	protected EList<Argument> argument;

	/**
	 * The default value of the '{@link #getSince() <em>Since</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSince()
	 * @generated
	 * @ordered
	 */
	protected static final String SINCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSince() <em>Since</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSince()
	 * @generated
	 * @ordered
	 */
	protected String since = SINCE_EDEFAULT;

	/**
	 * @generated Not
	 */
	protected static final boolean DEPLOYED_EDEFAULT = false;
	
	/**
	 * @generated Not
	 */
	protected boolean deployed = DEPLOYED_EDEFAULT;

	// whether the decision/exception tables have been lazily loaded
	// used only for exception table handling
	private boolean tablesInitialized = false;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected TableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DtmodelPackage.Literals.TABLE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE__MD, oldMd, newMd);
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
				msgs = ((InternalEObject)md).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.TABLE__MD, null, msgs);
			if (newMd != null)
				msgs = ((InternalEObject)newMd).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.TABLE__MD, null, msgs);
			msgs = basicSetMd(newMd, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE__MD, newMd, newMd));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public synchronized TableRuleSet getDecisionTable() {
		if (decisionTable == null && eResource() != null) {
			initTables();
		}
		return decisionTable;
	}

	private void initTables() {
		
		Map<String, String> options = new HashMap<String, String>();
		options.put("RELOAD", "true");
		try {
			Resource res = eResource();
			if (res.getURI() == null || res.getURI().isEmpty()) {
				return;
			}
			// create a new resource to load the Table, rather than using the
			// existing resource
			
			Resource.Factory resourceFactory = Resource.Factory.Registry.INSTANCE
					.getFactory(res.getURI());
			Resource resource = resourceFactory != null ? resourceFactory
					.createResource(res.getURI()) : res;
			if (resource == null) {
				resource = res;
			}
			resource.load(options);

			if (resource.getContents().size() > 0) {
				Table resolvedTable = (Table) resource.getContents().remove(
						resource.getContents().size() - 1);
				// decisionTable = resolvedTable.getDecisionTable(); // this
				// does not setup proper containment
				// exceptionTable = resolvedTable.getExceptionTable(); // this
				// does not setup proper containment
				setDecisionTable(resolvedTable.getDecisionTable());
				setExceptionTable(resolvedTable.getExceptionTable());
				tablesInitialized  = true;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDecisionTable(
			TableRuleSet newDecisionTable, NotificationChain msgs) {
		TableRuleSet oldDecisionTable = decisionTable;
		decisionTable = newDecisionTable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE__DECISION_TABLE, oldDecisionTable, newDecisionTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setDecisionTable(TableRuleSet newDecisionTable) {
		if (newDecisionTable != decisionTable) {
			NotificationChain msgs = null;
			if (decisionTable != null)
				msgs = ((InternalEObject)decisionTable).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.TABLE__DECISION_TABLE, null, msgs);
			if (newDecisionTable != null)
				msgs = ((InternalEObject)newDecisionTable).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DtmodelPackage.TABLE__DECISION_TABLE, null, msgs);
			msgs = basicSetDecisionTable(newDecisionTable, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE__DECISION_TABLE, newDecisionTable, newDecisionTable));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public TableRuleSet getExceptionTable() {
		if (exceptionTable == null && eResource() != null && !tablesInitialized) {
			initTables();
		}
		return exceptionTable;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExceptionTable(
			TableRuleSet newExceptionTable, NotificationChain msgs) {
		TableRuleSet oldExceptionTable = exceptionTable;
		exceptionTable = newExceptionTable;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE__EXCEPTION_TABLE, oldExceptionTable, newExceptionTable);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public void setExceptionTable(TableRuleSet newExceptionTable) {
		if (newExceptionTable == null) {
			tablesInitialized = false;
		}
		if (newExceptionTable != exceptionTable) {
			NotificationChain msgs = null;
			if (exceptionTable != null)
				msgs = ((InternalEObject) exceptionTable).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- DtmodelPackage.TABLE__EXCEPTION_TABLE, null,
						msgs);
			if (newExceptionTable != null)
				msgs = ((InternalEObject) newExceptionTable).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- DtmodelPackage.TABLE__EXCEPTION_TABLE, null,
						msgs);
			msgs = basicSetExceptionTable(newExceptionTable, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					DtmodelPackage.TABLE__EXCEPTION_TABLE, newExceptionTable,
					newExceptionTable));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated not
	 */
	public EList<Argument> getArgument() {
		if (argument == null) {
			// argument = new EObjectContainmentEList<Argument>(Argument.class,
			// this, DtmodelPackage.TABLE__ARGUMENT);
			argument = new EDataTypeEList(Argument.class, this,
					DtmodelPackage.TABLE__ARGUMENT);
		}

		return argument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSince() {
		return since;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSince(String newSince) {
		String oldSince = since;
		since = newSince;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DtmodelPackage.TABLE__SINCE, oldSince, since));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DtmodelPackage.TABLE__MD:
				return basicSetMd(null, msgs);
			case DtmodelPackage.TABLE__DECISION_TABLE:
				return basicSetDecisionTable(null, msgs);
			case DtmodelPackage.TABLE__EXCEPTION_TABLE:
				return basicSetExceptionTable(null, msgs);
			case DtmodelPackage.TABLE__ARGUMENT:
				return ((InternalEList<?>)getArgument()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case DtmodelPackage.TABLE__MD:
				return getMd();
			case DtmodelPackage.TABLE__DECISION_TABLE:
				return getDecisionTable();
			case DtmodelPackage.TABLE__EXCEPTION_TABLE:
				return getExceptionTable();
			case DtmodelPackage.TABLE__ARGUMENT:
				return getArgument();
			case DtmodelPackage.TABLE__SINCE:
				return getSince();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case DtmodelPackage.TABLE__MD:
				setMd((MetaData)newValue);
				return;
			case DtmodelPackage.TABLE__DECISION_TABLE:
				setDecisionTable((TableRuleSet)newValue);
				return;
			case DtmodelPackage.TABLE__EXCEPTION_TABLE:
				setExceptionTable((TableRuleSet)newValue);
				return;
			case DtmodelPackage.TABLE__ARGUMENT:
				getArgument().clear();
				getArgument().addAll((Collection<? extends Argument>)newValue);
				return;
			case DtmodelPackage.TABLE__SINCE:
				setSince((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case DtmodelPackage.TABLE__MD:
				setMd((MetaData)null);
				return;
			case DtmodelPackage.TABLE__DECISION_TABLE:
				setDecisionTable((TableRuleSet)null);
				return;
			case DtmodelPackage.TABLE__EXCEPTION_TABLE:
				setExceptionTable((TableRuleSet)null);
				return;
			case DtmodelPackage.TABLE__ARGUMENT:
				getArgument().clear();
				return;
			case DtmodelPackage.TABLE__SINCE:
				setSince(SINCE_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case DtmodelPackage.TABLE__MD:
				return md != null;
			case DtmodelPackage.TABLE__DECISION_TABLE:
				return decisionTable != null;
			case DtmodelPackage.TABLE__EXCEPTION_TABLE:
				return exceptionTable != null;
			case DtmodelPackage.TABLE__ARGUMENT:
				return argument != null && !argument.isEmpty();
			case DtmodelPackage.TABLE__SINCE:
				return SINCE_EDEFAULT == null ? since != null : !SINCE_EDEFAULT.equals(since);
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
		if (baseClass == Metadatable.class) {
			switch (derivedFeatureID) {
				case DtmodelPackage.TABLE__MD: return DtmodelPackage.METADATABLE__MD;
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
		if (baseClass == Metadatable.class) {
			switch (baseFeatureID) {
				case DtmodelPackage.METADATABLE__MD: return DtmodelPackage.TABLE__MD;
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
		result.append(" (since: ");
		result.append(since);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated Not
	 */
	public boolean isDeployed() {
		return deployed;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated Not
	 */
	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}

} // TableImpl
