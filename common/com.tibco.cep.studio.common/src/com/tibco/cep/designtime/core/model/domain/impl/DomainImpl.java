/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.domain.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeEList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.DOMAIN_DATA_TYPES;
import com.tibco.cep.designtime.core.model.domain.Domain;
import com.tibco.cep.designtime.core.model.domain.DomainEntry;
import com.tibco.cep.designtime.core.model.domain.DomainInstance;
import com.tibco.cep.designtime.core.model.domain.DomainPackage;
import com.tibco.cep.designtime.core.model.domain.Range;
import com.tibco.cep.designtime.core.model.impl.EntityImpl;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.model.InstanceElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;
import com.tibco.cep.studio.core.utils.ModelUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.impl.DomainImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.impl.DomainImpl#getEntries <em>Entries</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.impl.DomainImpl#getDataType <em>Data Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.impl.DomainImpl#getSuperDomainPath <em>Super Domain Path</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.domain.impl.DomainImpl#getSuperDomain <em>Super Domain</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DomainImpl extends EntityImpl implements Domain {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final long ID_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected long id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getEntries() <em>Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<DomainEntry> entries;

	/**
	 * The default value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected static final DOMAIN_DATA_TYPES DATA_TYPE_EDEFAULT = DOMAIN_DATA_TYPES.STRING;

	/**
	 * The cached value of the '{@link #getDataType() <em>Data Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDataType()
	 * @generated
	 * @ordered
	 */
	protected DOMAIN_DATA_TYPES dataType = DATA_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSuperDomainPath() <em>Super Domain Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperDomainPath()
	 * @generated
	 * @ordered
	 */
	protected static final String SUPER_DOMAIN_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSuperDomainPath() <em>Super Domain Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperDomainPath()
	 * @generated
	 * @ordered
	 */
	protected String superDomainPath = SUPER_DOMAIN_PATH_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSuperDomain() <em>Super Domain</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSuperDomain()
	 * @generated
	 * @ordered
	 */
	protected Domain superDomain;
	
	/**
	* @generated NOT
	 */
	protected EList<ModelError> errorList;


	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DomainPackage.Literals.DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(long newId) {
		long oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.DOMAIN__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DomainEntry> getEntries() {
		if (entries == null) {
			entries = new EObjectContainmentEList<DomainEntry>(DomainEntry.class, this, DomainPackage.DOMAIN__ENTRIES);
		}
		return entries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DOMAIN_DATA_TYPES getDataType() {
		return dataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDataType(DOMAIN_DATA_TYPES newDataType) {
		DOMAIN_DATA_TYPES oldDataType = dataType;
		dataType = newDataType == null ? DATA_TYPE_EDEFAULT : newDataType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.DOMAIN__DATA_TYPE, oldDataType, dataType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSuperDomainPath() {
		return superDomainPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperDomainPath(String newSuperDomainPath) {
		String oldSuperDomainPath = superDomainPath;
		superDomainPath = newSuperDomainPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.DOMAIN__SUPER_DOMAIN_PATH, oldSuperDomainPath, superDomainPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Domain getSuperDomain() {
		if (superDomain != null && superDomain.eIsProxy()) {
			InternalEObject oldSuperDomain = (InternalEObject)superDomain;
			superDomain = (Domain)eResolveProxy(oldSuperDomain);
			if (superDomain != oldSuperDomain) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DomainPackage.DOMAIN__SUPER_DOMAIN, oldSuperDomain, superDomain));
			}
		}
		if (superDomain == null){
			String superDomainPath = getSuperDomainPath();			
			if (superDomainPath != null && superDomainPath.trim().length() > 0){
				superDomain = 
					CommonIndexUtils.getDomain(ownerProjectName, superDomainPath);
			}
		}
		return superDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain basicGetSuperDomain() {
		return superDomain;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSuperDomain(Domain newSuperDomain) {
		Domain oldSuperDomain = superDomain;
		superDomain = newSuperDomain;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, DomainPackage.DOMAIN__SUPER_DOMAIN, oldSuperDomain, superDomain));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<DomainInstance> getInstances() {
		//Get the path of the domain
		String fullPath = getFullPath();
		List<InstanceElement<DomainInstance>> instanceElements = 
			CommonIndexUtils.getInstances(ownerProjectName, fullPath, ELEMENT_TYPES.DOMAIN_INSTANCE);
		EList<DomainInstance> domainInstances = 
			new EObjectContainmentEList<DomainInstance>(DomainInstance.class, this, DomainPackage.DOMAIN_INSTANCE) {
			 @Override
			  protected boolean hasInverse()
			  {
			    return false;
			  }
		};
		
		for (InstanceElement<DomainInstance> instanceElement : instanceElements) {
			for(DomainInstance domainInstance : instanceElement.getInstances()) {
				domainInstances.add(domainInstance);
			}
		}
		return domainInstances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFullPath() {
		return getFolder() + getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case DomainPackage.DOMAIN__ENTRIES:
				return ((InternalEList<?>)getEntries()).basicRemove(otherEnd, msgs);
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
			case DomainPackage.DOMAIN__ID:
				return getId();
			case DomainPackage.DOMAIN__ENTRIES:
				return getEntries();
			case DomainPackage.DOMAIN__DATA_TYPE:
				return getDataType();
			case DomainPackage.DOMAIN__SUPER_DOMAIN_PATH:
				return getSuperDomainPath();
			case DomainPackage.DOMAIN__SUPER_DOMAIN:
				if (resolve) return getSuperDomain();
				return basicGetSuperDomain();
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
			case DomainPackage.DOMAIN__ID:
				setId((Long)newValue);
				return;
			case DomainPackage.DOMAIN__ENTRIES:
				getEntries().clear();
				getEntries().addAll((Collection<? extends DomainEntry>)newValue);
				return;
			case DomainPackage.DOMAIN__DATA_TYPE:
				setDataType((DOMAIN_DATA_TYPES)newValue);
				return;
			case DomainPackage.DOMAIN__SUPER_DOMAIN_PATH:
				setSuperDomainPath((String)newValue);
				return;
			case DomainPackage.DOMAIN__SUPER_DOMAIN:
				setSuperDomain((Domain)newValue);
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
			case DomainPackage.DOMAIN__ID:
				setId(ID_EDEFAULT);
				return;
			case DomainPackage.DOMAIN__ENTRIES:
				getEntries().clear();
				return;
			case DomainPackage.DOMAIN__DATA_TYPE:
				setDataType(DATA_TYPE_EDEFAULT);
				return;
			case DomainPackage.DOMAIN__SUPER_DOMAIN_PATH:
				setSuperDomainPath(SUPER_DOMAIN_PATH_EDEFAULT);
				return;
			case DomainPackage.DOMAIN__SUPER_DOMAIN:
				setSuperDomain((Domain)null);
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
			case DomainPackage.DOMAIN__ID:
				return id != ID_EDEFAULT;
			case DomainPackage.DOMAIN__ENTRIES:
				return entries != null && !entries.isEmpty();
			case DomainPackage.DOMAIN__DATA_TYPE:
				return dataType != DATA_TYPE_EDEFAULT;
			case DomainPackage.DOMAIN__SUPER_DOMAIN_PATH:
				return SUPER_DOMAIN_PATH_EDEFAULT == null ? superDomainPath != null : !SUPER_DOMAIN_PATH_EDEFAULT.equals(superDomainPath);
			case DomainPackage.DOMAIN__SUPER_DOMAIN:
				return superDomain != null;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", dataType: ");
		result.append(dataType);
		result.append(", superDomainPath: ");
		result.append(superDomainPath);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	@Override
	public EList<ModelError> getModelErrors() {
		if (errorList == null ) {
			errorList = new BasicEList<ModelError>();
		}
		errorList.clear();
		
		List<String> dupList = checkParentEntries();
		dupList.addAll(checkEntries());
		if (dupList.size() > 0) {
			for (String entry : dupList) {
				List<Object> argList = new ArrayList<Object>();
				argList.add(entry);
				ModelError me = CommonValidationUtils.constructModelError(this, "Domain_Validation_Error_Duplicate_Value", argList, false);
				errorList.add(me);
			}
		}
		
		List<Compare> list = new ArrayList<Compare>();
		for (DomainEntry entry : getEntries()) {
			if (entry instanceof Range) {
				Range range = (Range)entry;
				String upperValue = range.getUpper();
				String lowerValue = range.getLower();
				if (!isValidValue(upperValue)) {
					createErrors(errorList, upperValue);
				}
				if (!isValidValue(lowerValue)) {
					createErrors(errorList, lowerValue);
				}
				boolean comp = compare(getDataType().getName(), lowerValue, upperValue);
				if (!comp) {
					list.add(new Compare(lowerValue,upperValue, comp));
				}
			} else {
				String value = entry.getValue().toString();
				if (!getDataType().getName().equals("String")) {				
					if (!isValidValue(value)) {
						createErrors(errorList, value);
					}
				}
			}
		}
		if (list.size() > 0) {
			for (Compare compare : list) {
				List<Object> argList = new ArrayList<Object>();
				argList.add(compare.getLowerValue());
				argList.add(compare.getUpperValue());
				ModelError me = CommonValidationUtils.constructModelError(this, "domain.error.invalid.range", argList, false);
				errorList.add(me);
			}
		}
		return errorList;
	}
	
	/**
	 * @param viewer
	 * @return
	 * @generated NOT 
	 */
	public List<String> checkEntries() {
		List<String> list = new ArrayList<String>();
		String[] items = new String[getEntries().size()];
		int count = 0;
		for (DomainEntry entry : getEntries()) {
			String item = "";
			if (entry instanceof Range) {
				Range rf = (Range)entry;
				String uv = "Undefined".equalsIgnoreCase(rf.getUpper()) ? "": normalizeValue(rf.getUpper());
				String lv = "Undefined".equalsIgnoreCase(rf.getLower()) ? "": normalizeValue(rf.getLower());
				item = (rf.isLowerInclusive()?"[":"(")+lv
													  +","
													  +uv
													  +(rf.isUpperInclusive()?"]":")");
			} else {
				item = normalizeValue(entry.getValue().toString());
			}
			items[count] = item;
			count++;
		}
		if (items != null && items.length > 0) {
			for (int ii = 0; ii < items.length; ii++) {
				for (int jj = 0; jj < items.length; jj++) {
					if (ii == jj)
						continue;
					if (items[ii].trim().equals(
							items[jj].trim()))
						if (!checkList(items[ii], list))
							list.add(items[ii]);
				}
			}
		}
		return list;
	}

	/**
	 * Normalizes the values of INTEGER, DOUBLE & LONG type domains
	 * @return Normalized value
	 * @generated NOT 
	 */
	private String normalizeValue(String value) {
		if (value != null) {
			try {
				switch(dataType) {
				case INTEGER :				
					return Integer.valueOf(value).toString();
				case DOUBLE :
					return Double.valueOf(value).toString();
				case LONG :
					return Long.valueOf(value).toString();
				default :
					return value;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return value;
	}
	
	/**
	 * Returns List of entries in this domain that are duplicate of entries in parent domain
	 * @return List of Duplicate entries
	 * @generated NOT 
	 */
	private List<String> checkParentEntries() {
		List<String> list = new ArrayList<String>();

		List<String> items = getDomainItems(getEntries());		
		EList<DomainEntry> parentEntries = getParentEntries();
		List<String> parentItems = getDomainItems(parentEntries);

		if (items != null && items.size() > 0 && parentItems != null && parentItems.size() > 0) {
			for(String item : items) {
				if (parentItems.contains(item)) {
					if (!checkList(item, list))
						list.add(item);
				}
			}
		}
		return list;
	}

	/**
	 * @return List of Parent Domain entries
	 * @generated NOT
	 */
	private EList<DomainEntry> getParentEntries() {	
		EList<DomainEntry> entries = new EDataTypeEList<DomainEntry>(DomainEntry.class, this,  DomainPackage.DOMAIN__ENTRIES);
		Domain superDomain = getSuperDomain();
		if(superDomain != null) {
			collectEntries(superDomain, entries);
		}
		return entries;
	}
	
	/**
	 * Collects all the parent domain entries
	 * @param domain
	 * @param entries
	 * @generated NOT
	 */
	private void collectEntries(Domain domain, EList<DomainEntry> entries) {
		Domain superDomain = domain.getSuperDomain();
		if(superDomain != null) {
			collectEntries(superDomain, entries);
		}
		
		entries.addAll(domain.getEntries());
	}
	
	/**
	 * @param entries
	 * @return List of Domain items as String
	 * @generated NOT
	 */
	private List<String> getDomainItems(EList<DomainEntry> entries) {
		List<String> items = new ArrayList<String>(entries.size());
		for (DomainEntry entry : entries) {
			String item = "";
			if (entry instanceof Range) {
				Range rf = (Range)entry;
				String uv = "Undefined".equalsIgnoreCase(rf.getUpper()) ? "": rf.getUpper();
				String lv = "Undefined".equalsIgnoreCase(rf.getLower()) ? "": rf.getLower();
				item = (rf.isLowerInclusive()?"[":"(")+lv
													  +","
													  +uv
													  +(rf.isUpperInclusive()?"]":")");
			} else {
				item =entry.getValue().toString();
			}
			items.add(item);
		}
		
		return items;
	}
	
	/**
	 * @param value
	 * @param list
	 * @return
	 * @generated NOT 
	 */
	private boolean checkList(String value, List<String> list) {
		for (String entry : list) {
			if (entry.equals(value)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @param errorList
	 * @param value
	 * @generated NOT 
	 */
	public void createErrors(EList<ModelError> errorList , String value) {
		List<Object> argList = new ArrayList<Object>();
		argList.add(value);
		argList.add(getDataType().getName());
		ModelError me = CommonValidationUtils.constructModelError(this, "domain.error.valid.data", argList, false);
		errorList.add(me);
	}
	
	/**
	 * @param str
	 * @return
	 * @generated NOT 
	 */
	public boolean isValidValue(String str) {
		String type = getDataType().getName();
		if (type.equals("int")) {
			return isNumeric(str);
		}
		if (type.equals("long")) {
			return isLong(str);
		}
		if (type.equals("double")) {
			return isDouble(str);
		}
		if (type.equals("boolean")) {
			if (str.equalsIgnoreCase("true") ||
					str.equalsIgnoreCase("false")) {
				return true;
			}else{
				return false;
			}
		}
		if (type.equals("DateTime")) {
			try {
				ModelUtils.SIMPLE_DATE_FORMAT().parse(str);
				return true;
			} catch (ParseException e) {
				return false;
			}
		}
		if(type.equals("String")){
			return true; 
		}
		return false;
	}
	
	/**
	 * @param str
	 * @return
	 * @generated NOT
	 */
	public static boolean isNumeric(String str) {
		try {
				Integer.parseInt(str);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}

	/**
	 * @param str
	 * @return
	 * @generated NOT 
	 */
	public static boolean isDouble(String str) {
		try {
				Double.parseDouble(str);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	
	/**
	 * @param str
	 * @return
	 * @generated NOT 
	 */
	public static boolean isLong(String str) {
		try {
			Long.parseLong(str);
			return true;
		} catch (NumberFormatException err) {
			return false;
		}
	}
	
	/**
	 * @param type
	 * @param lowerValue
	 * @param upperValue
	 * @return
	 * @generated NOT 
	 */
	private boolean compare(String type, String lowerValue, String upperValue) {
		try{
			if (!lowerValue.trim().equalsIgnoreCase("")
					&& !upperValue.equalsIgnoreCase("")) {
				if (type.equalsIgnoreCase("Int")) {
					int low = Integer.parseInt(lowerValue);
					int high = Integer.parseInt(upperValue);
					if (low < high)
						return true;
				}
				if (type.equalsIgnoreCase("Double")) {
					Double low = Double.parseDouble(lowerValue);
					Double high = Double.parseDouble(upperValue);
					if (low < high)
						return true;
				}
				if (type.equalsIgnoreCase("Long")) {
					Long low = Long.parseLong(lowerValue);
					Long high = Long.parseLong(upperValue);
					if (low < high)
						return true;
				}
				if (type.equalsIgnoreCase("DateTime")) {
					Date low;
					Date high;
					try {
						low = ModelUtils.SIMPLE_DATE_FORMAT().parse(lowerValue);
						high = ModelUtils.SIMPLE_DATE_FORMAT().parse(upperValue);
						if (low.compareTo(high) < 0)
							return true;
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				if(type.equalsIgnoreCase("String")){
					return true; 
				}
			}
			if (lowerValue.trim().equalsIgnoreCase("")
					&& !upperValue.equalsIgnoreCase("")) {
				return true;
			}
			if (!lowerValue.trim().equalsIgnoreCase("")
					&& upperValue.equalsIgnoreCase("")) {
				return true;
			}
		}catch(Exception e){
			return false;
		}
		return false;
	}

	/**
	 * @generated NOT 
	 */
	private class Compare {
		String lowerValue;
		String upperValue;
		boolean status;

		/**
		 * @param source
		 * @param lowerValue
		 * @param upperValue
		 * @param status
		 */
		Compare(String lowerValue, String upperValue, boolean status) {
			this.lowerValue = lowerValue;
			this.upperValue = upperValue;
			this.status = status;
		}

		public String getLowerValue() {
			return lowerValue;
		}

		public void setLowerValue(String lowerValue) {
			this.lowerValue = lowerValue;
		}

		public String getUpperValue() {
			return upperValue;
		}

		public void setUpperValue(String upperValue) {
			this.upperValue = upperValue;
		}

		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}
	}
	
} //DomainImpl