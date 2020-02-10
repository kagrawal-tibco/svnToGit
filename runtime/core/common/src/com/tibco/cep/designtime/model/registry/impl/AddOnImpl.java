/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.model.registry.AddOn;
import com.tibco.cep.designtime.model.registry.AddOnType;
import com.tibco.cep.designtime.model.registry.FileExtensionMapType;
import com.tibco.cep.designtime.model.registry.RegistryPackage;
import com.tibco.cep.designtime.model.registry.SupportedElementTypes;
import com.tibco.cep.designtime.model.registry.SupportedExtensions;
import com.tibco.cep.designtime.model.registry.TnsEntityExtensions;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Add On</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getIndexResourceProviderClass <em>Index Resource Provider Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getIndexCacheClass <em>Index Cache Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getOntologyAdapterClass <em>Ontology Adapter Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getAdapterFactoryClass <em>Adapter Factory Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getTnsEntityHandlerClass <em>Tns Entity Handler Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getCddAgentClass <em>Cdd Agent Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getSupportedEntityTypes <em>Supported Entity Types</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getSupportedExtensions <em>Supported Extensions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getFileExtensionMap <em>File Extension Map</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getTnsEntityExtensions <em>Tns Entity Extensions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.impl.AddOnImpl#getFunctionCatalogProvider <em>Function Catalog Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AddOnImpl extends EObjectImpl implements AddOn {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final AddOnType TYPE_EDEFAULT = AddOnType.CORE;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected AddOnType type = TYPE_EDEFAULT;

	/**
	 * This is true if the Type attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean typeESet;

	/**
	 * The default value of the '{@link #getIndexResourceProviderClass() <em>Index Resource Provider Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexResourceProviderClass()
	 * @generated
	 * @ordered
	 */
	protected static final String INDEX_RESOURCE_PROVIDER_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIndexResourceProviderClass() <em>Index Resource Provider Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexResourceProviderClass()
	 * @generated
	 * @ordered
	 */
	protected String indexResourceProviderClass = INDEX_RESOURCE_PROVIDER_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getIndexCacheClass() <em>Index Cache Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexCacheClass()
	 * @generated
	 * @ordered
	 */
	protected static final String INDEX_CACHE_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getIndexCacheClass() <em>Index Cache Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexCacheClass()
	 * @generated
	 * @ordered
	 */
	protected String indexCacheClass = INDEX_CACHE_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getOntologyAdapterClass() <em>Ontology Adapter Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOntologyAdapterClass()
	 * @generated
	 * @ordered
	 */
	protected static final String ONTOLOGY_ADAPTER_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOntologyAdapterClass() <em>Ontology Adapter Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOntologyAdapterClass()
	 * @generated
	 * @ordered
	 */
	protected String ontologyAdapterClass = ONTOLOGY_ADAPTER_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getAdapterFactoryClass() <em>Adapter Factory Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdapterFactoryClass()
	 * @generated
	 * @ordered
	 */
	protected static final String ADAPTER_FACTORY_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAdapterFactoryClass() <em>Adapter Factory Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAdapterFactoryClass()
	 * @generated
	 * @ordered
	 */
	protected String adapterFactoryClass = ADAPTER_FACTORY_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getTnsEntityHandlerClass() <em>Tns Entity Handler Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTnsEntityHandlerClass()
	 * @generated
	 * @ordered
	 */
	protected static final String TNS_ENTITY_HANDLER_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTnsEntityHandlerClass() <em>Tns Entity Handler Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTnsEntityHandlerClass()
	 * @generated
	 * @ordered
	 */
	protected String tnsEntityHandlerClass = TNS_ENTITY_HANDLER_CLASS_EDEFAULT;

	/**
	 * The default value of the '{@link #getCddAgentClass() <em>Cdd Agent Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCddAgentClass()
	 * @generated
	 * @ordered
	 */
	protected static final String CDD_AGENT_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getCddAgentClass() <em>Cdd Agent Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCddAgentClass()
	 * @generated
	 * @ordered
	 */
	protected String cddAgentClass = CDD_AGENT_CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSupportedEntityTypes() <em>Supported Entity Types</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSupportedEntityTypes()
	 * @generated
	 * @ordered
	 */
	protected SupportedElementTypes supportedEntityTypes;

	/**
	 * The cached value of the '{@link #getSupportedExtensions() <em>Supported Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSupportedExtensions()
	 * @generated
	 * @ordered
	 */
	protected SupportedExtensions supportedExtensions;

	/**
	 * The cached value of the '{@link #getFileExtensionMap() <em>File Extension Map</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFileExtensionMap()
	 * @generated
	 * @ordered
	 */
	protected EList<FileExtensionMapType> fileExtensionMap;

	/**
	 * The cached value of the '{@link #getTnsEntityExtensions() <em>Tns Entity Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTnsEntityExtensions()
	 * @generated
	 * @ordered
	 */
	protected TnsEntityExtensions tnsEntityExtensions;

	/**
	 * The default value of the '{@link #getFunctionCatalogProvider() <em>Function Catalog Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionCatalogProvider()
	 * @generated
	 * @ordered
	 */
	protected static final String FUNCTION_CATALOG_PROVIDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getFunctionCatalogProvider() <em>Function Catalog Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionCatalogProvider()
	 * @generated
	 * @ordered
	 */
	protected String functionCatalogProvider = FUNCTION_CATALOG_PROVIDER_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AddOnImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RegistryPackage.Literals.ADD_ON;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddOnType getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(AddOnType newType) {
		AddOnType oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		boolean oldTypeESet = typeESet;
		typeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__TYPE, oldType, type, !oldTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetType() {
		AddOnType oldType = type;
		boolean oldTypeESet = typeESet;
		type = TYPE_EDEFAULT;
		typeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, RegistryPackage.ADD_ON__TYPE, oldType, TYPE_EDEFAULT, oldTypeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetType() {
		return typeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIndexResourceProviderClass() {
		return indexResourceProviderClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndexResourceProviderClass(String newIndexResourceProviderClass) {
		String oldIndexResourceProviderClass = indexResourceProviderClass;
		indexResourceProviderClass = newIndexResourceProviderClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__INDEX_RESOURCE_PROVIDER_CLASS, oldIndexResourceProviderClass, indexResourceProviderClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIndexCacheClass() {
		return indexCacheClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndexCacheClass(String newIndexCacheClass) {
		String oldIndexCacheClass = indexCacheClass;
		indexCacheClass = newIndexCacheClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__INDEX_CACHE_CLASS, oldIndexCacheClass, indexCacheClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOntologyAdapterClass() {
		return ontologyAdapterClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOntologyAdapterClass(String newOntologyAdapterClass) {
		String oldOntologyAdapterClass = ontologyAdapterClass;
		ontologyAdapterClass = newOntologyAdapterClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__ONTOLOGY_ADAPTER_CLASS, oldOntologyAdapterClass, ontologyAdapterClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAdapterFactoryClass() {
		return adapterFactoryClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAdapterFactoryClass(String newAdapterFactoryClass) {
		String oldAdapterFactoryClass = adapterFactoryClass;
		adapterFactoryClass = newAdapterFactoryClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__ADAPTER_FACTORY_CLASS, oldAdapterFactoryClass, adapterFactoryClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTnsEntityHandlerClass() {
		return tnsEntityHandlerClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTnsEntityHandlerClass(String newTnsEntityHandlerClass) {
		String oldTnsEntityHandlerClass = tnsEntityHandlerClass;
		tnsEntityHandlerClass = newTnsEntityHandlerClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__TNS_ENTITY_HANDLER_CLASS, oldTnsEntityHandlerClass, tnsEntityHandlerClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCddAgentClass() {
		return cddAgentClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCddAgentClass(String newCddAgentClass) {
		String oldCddAgentClass = cddAgentClass;
		cddAgentClass = newCddAgentClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__CDD_AGENT_CLASS, oldCddAgentClass, cddAgentClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SupportedElementTypes getSupportedEntityTypes() {
		return supportedEntityTypes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSupportedEntityTypes(SupportedElementTypes newSupportedEntityTypes, NotificationChain msgs) {
		SupportedElementTypes oldSupportedEntityTypes = supportedEntityTypes;
		supportedEntityTypes = newSupportedEntityTypes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__SUPPORTED_ENTITY_TYPES, oldSupportedEntityTypes, newSupportedEntityTypes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSupportedEntityTypes(SupportedElementTypes newSupportedEntityTypes) {
		if (newSupportedEntityTypes != supportedEntityTypes) {
			NotificationChain msgs = null;
			if (supportedEntityTypes != null)
				msgs = ((InternalEObject)supportedEntityTypes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RegistryPackage.ADD_ON__SUPPORTED_ENTITY_TYPES, null, msgs);
			if (newSupportedEntityTypes != null)
				msgs = ((InternalEObject)newSupportedEntityTypes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RegistryPackage.ADD_ON__SUPPORTED_ENTITY_TYPES, null, msgs);
			msgs = basicSetSupportedEntityTypes(newSupportedEntityTypes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__SUPPORTED_ENTITY_TYPES, newSupportedEntityTypes, newSupportedEntityTypes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SupportedExtensions getSupportedExtensions() {
		return supportedExtensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSupportedExtensions(SupportedExtensions newSupportedExtensions, NotificationChain msgs) {
		SupportedExtensions oldSupportedExtensions = supportedExtensions;
		supportedExtensions = newSupportedExtensions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__SUPPORTED_EXTENSIONS, oldSupportedExtensions, newSupportedExtensions);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSupportedExtensions(SupportedExtensions newSupportedExtensions) {
		if (newSupportedExtensions != supportedExtensions) {
			NotificationChain msgs = null;
			if (supportedExtensions != null)
				msgs = ((InternalEObject)supportedExtensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RegistryPackage.ADD_ON__SUPPORTED_EXTENSIONS, null, msgs);
			if (newSupportedExtensions != null)
				msgs = ((InternalEObject)newSupportedExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RegistryPackage.ADD_ON__SUPPORTED_EXTENSIONS, null, msgs);
			msgs = basicSetSupportedExtensions(newSupportedExtensions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__SUPPORTED_EXTENSIONS, newSupportedExtensions, newSupportedExtensions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FileExtensionMapType> getFileExtensionMap() {
		if (fileExtensionMap == null) {
			fileExtensionMap = new EObjectContainmentEList<FileExtensionMapType>(FileExtensionMapType.class, this, RegistryPackage.ADD_ON__FILE_EXTENSION_MAP);
		}
		return fileExtensionMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TnsEntityExtensions getTnsEntityExtensions() {
		return tnsEntityExtensions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTnsEntityExtensions(TnsEntityExtensions newTnsEntityExtensions, NotificationChain msgs) {
		TnsEntityExtensions oldTnsEntityExtensions = tnsEntityExtensions;
		tnsEntityExtensions = newTnsEntityExtensions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__TNS_ENTITY_EXTENSIONS, oldTnsEntityExtensions, newTnsEntityExtensions);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTnsEntityExtensions(TnsEntityExtensions newTnsEntityExtensions) {
		if (newTnsEntityExtensions != tnsEntityExtensions) {
			NotificationChain msgs = null;
			if (tnsEntityExtensions != null)
				msgs = ((InternalEObject)tnsEntityExtensions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - RegistryPackage.ADD_ON__TNS_ENTITY_EXTENSIONS, null, msgs);
			if (newTnsEntityExtensions != null)
				msgs = ((InternalEObject)newTnsEntityExtensions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - RegistryPackage.ADD_ON__TNS_ENTITY_EXTENSIONS, null, msgs);
			msgs = basicSetTnsEntityExtensions(newTnsEntityExtensions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__TNS_ENTITY_EXTENSIONS, newTnsEntityExtensions, newTnsEntityExtensions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFunctionCatalogProvider() {
		return functionCatalogProvider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFunctionCatalogProvider(String newFunctionCatalogProvider) {
		String oldFunctionCatalogProvider = functionCatalogProvider;
		functionCatalogProvider = newFunctionCatalogProvider;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RegistryPackage.ADD_ON__FUNCTION_CATALOG_PROVIDER, oldFunctionCatalogProvider, functionCatalogProvider));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RegistryPackage.ADD_ON__SUPPORTED_ENTITY_TYPES:
				return basicSetSupportedEntityTypes(null, msgs);
			case RegistryPackage.ADD_ON__SUPPORTED_EXTENSIONS:
				return basicSetSupportedExtensions(null, msgs);
			case RegistryPackage.ADD_ON__FILE_EXTENSION_MAP:
				return ((InternalEList<?>)getFileExtensionMap()).basicRemove(otherEnd, msgs);
			case RegistryPackage.ADD_ON__TNS_ENTITY_EXTENSIONS:
				return basicSetTnsEntityExtensions(null, msgs);
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
			case RegistryPackage.ADD_ON__TYPE:
				return getType();
			case RegistryPackage.ADD_ON__INDEX_RESOURCE_PROVIDER_CLASS:
				return getIndexResourceProviderClass();
			case RegistryPackage.ADD_ON__INDEX_CACHE_CLASS:
				return getIndexCacheClass();
			case RegistryPackage.ADD_ON__ONTOLOGY_ADAPTER_CLASS:
				return getOntologyAdapterClass();
			case RegistryPackage.ADD_ON__ADAPTER_FACTORY_CLASS:
				return getAdapterFactoryClass();
			case RegistryPackage.ADD_ON__TNS_ENTITY_HANDLER_CLASS:
				return getTnsEntityHandlerClass();
			case RegistryPackage.ADD_ON__CDD_AGENT_CLASS:
				return getCddAgentClass();
			case RegistryPackage.ADD_ON__SUPPORTED_ENTITY_TYPES:
				return getSupportedEntityTypes();
			case RegistryPackage.ADD_ON__SUPPORTED_EXTENSIONS:
				return getSupportedExtensions();
			case RegistryPackage.ADD_ON__FILE_EXTENSION_MAP:
				return getFileExtensionMap();
			case RegistryPackage.ADD_ON__TNS_ENTITY_EXTENSIONS:
				return getTnsEntityExtensions();
			case RegistryPackage.ADD_ON__FUNCTION_CATALOG_PROVIDER:
				return getFunctionCatalogProvider();
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
			case RegistryPackage.ADD_ON__TYPE:
				setType((AddOnType)newValue);
				return;
			case RegistryPackage.ADD_ON__INDEX_RESOURCE_PROVIDER_CLASS:
				setIndexResourceProviderClass((String)newValue);
				return;
			case RegistryPackage.ADD_ON__INDEX_CACHE_CLASS:
				setIndexCacheClass((String)newValue);
				return;
			case RegistryPackage.ADD_ON__ONTOLOGY_ADAPTER_CLASS:
				setOntologyAdapterClass((String)newValue);
				return;
			case RegistryPackage.ADD_ON__ADAPTER_FACTORY_CLASS:
				setAdapterFactoryClass((String)newValue);
				return;
			case RegistryPackage.ADD_ON__TNS_ENTITY_HANDLER_CLASS:
				setTnsEntityHandlerClass((String)newValue);
				return;
			case RegistryPackage.ADD_ON__CDD_AGENT_CLASS:
				setCddAgentClass((String)newValue);
				return;
			case RegistryPackage.ADD_ON__SUPPORTED_ENTITY_TYPES:
				setSupportedEntityTypes((SupportedElementTypes)newValue);
				return;
			case RegistryPackage.ADD_ON__SUPPORTED_EXTENSIONS:
				setSupportedExtensions((SupportedExtensions)newValue);
				return;
			case RegistryPackage.ADD_ON__FILE_EXTENSION_MAP:
				getFileExtensionMap().clear();
				getFileExtensionMap().addAll((Collection<? extends FileExtensionMapType>)newValue);
				return;
			case RegistryPackage.ADD_ON__TNS_ENTITY_EXTENSIONS:
				setTnsEntityExtensions((TnsEntityExtensions)newValue);
				return;
			case RegistryPackage.ADD_ON__FUNCTION_CATALOG_PROVIDER:
				setFunctionCatalogProvider((String)newValue);
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
			case RegistryPackage.ADD_ON__TYPE:
				unsetType();
				return;
			case RegistryPackage.ADD_ON__INDEX_RESOURCE_PROVIDER_CLASS:
				setIndexResourceProviderClass(INDEX_RESOURCE_PROVIDER_CLASS_EDEFAULT);
				return;
			case RegistryPackage.ADD_ON__INDEX_CACHE_CLASS:
				setIndexCacheClass(INDEX_CACHE_CLASS_EDEFAULT);
				return;
			case RegistryPackage.ADD_ON__ONTOLOGY_ADAPTER_CLASS:
				setOntologyAdapterClass(ONTOLOGY_ADAPTER_CLASS_EDEFAULT);
				return;
			case RegistryPackage.ADD_ON__ADAPTER_FACTORY_CLASS:
				setAdapterFactoryClass(ADAPTER_FACTORY_CLASS_EDEFAULT);
				return;
			case RegistryPackage.ADD_ON__TNS_ENTITY_HANDLER_CLASS:
				setTnsEntityHandlerClass(TNS_ENTITY_HANDLER_CLASS_EDEFAULT);
				return;
			case RegistryPackage.ADD_ON__CDD_AGENT_CLASS:
				setCddAgentClass(CDD_AGENT_CLASS_EDEFAULT);
				return;
			case RegistryPackage.ADD_ON__SUPPORTED_ENTITY_TYPES:
				setSupportedEntityTypes((SupportedElementTypes)null);
				return;
			case RegistryPackage.ADD_ON__SUPPORTED_EXTENSIONS:
				setSupportedExtensions((SupportedExtensions)null);
				return;
			case RegistryPackage.ADD_ON__FILE_EXTENSION_MAP:
				getFileExtensionMap().clear();
				return;
			case RegistryPackage.ADD_ON__TNS_ENTITY_EXTENSIONS:
				setTnsEntityExtensions((TnsEntityExtensions)null);
				return;
			case RegistryPackage.ADD_ON__FUNCTION_CATALOG_PROVIDER:
				setFunctionCatalogProvider(FUNCTION_CATALOG_PROVIDER_EDEFAULT);
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
			case RegistryPackage.ADD_ON__TYPE:
				return isSetType();
			case RegistryPackage.ADD_ON__INDEX_RESOURCE_PROVIDER_CLASS:
				return INDEX_RESOURCE_PROVIDER_CLASS_EDEFAULT == null ? indexResourceProviderClass != null : !INDEX_RESOURCE_PROVIDER_CLASS_EDEFAULT.equals(indexResourceProviderClass);
			case RegistryPackage.ADD_ON__INDEX_CACHE_CLASS:
				return INDEX_CACHE_CLASS_EDEFAULT == null ? indexCacheClass != null : !INDEX_CACHE_CLASS_EDEFAULT.equals(indexCacheClass);
			case RegistryPackage.ADD_ON__ONTOLOGY_ADAPTER_CLASS:
				return ONTOLOGY_ADAPTER_CLASS_EDEFAULT == null ? ontologyAdapterClass != null : !ONTOLOGY_ADAPTER_CLASS_EDEFAULT.equals(ontologyAdapterClass);
			case RegistryPackage.ADD_ON__ADAPTER_FACTORY_CLASS:
				return ADAPTER_FACTORY_CLASS_EDEFAULT == null ? adapterFactoryClass != null : !ADAPTER_FACTORY_CLASS_EDEFAULT.equals(adapterFactoryClass);
			case RegistryPackage.ADD_ON__TNS_ENTITY_HANDLER_CLASS:
				return TNS_ENTITY_HANDLER_CLASS_EDEFAULT == null ? tnsEntityHandlerClass != null : !TNS_ENTITY_HANDLER_CLASS_EDEFAULT.equals(tnsEntityHandlerClass);
			case RegistryPackage.ADD_ON__CDD_AGENT_CLASS:
				return CDD_AGENT_CLASS_EDEFAULT == null ? cddAgentClass != null : !CDD_AGENT_CLASS_EDEFAULT.equals(cddAgentClass);
			case RegistryPackage.ADD_ON__SUPPORTED_ENTITY_TYPES:
				return supportedEntityTypes != null;
			case RegistryPackage.ADD_ON__SUPPORTED_EXTENSIONS:
				return supportedExtensions != null;
			case RegistryPackage.ADD_ON__FILE_EXTENSION_MAP:
				return fileExtensionMap != null && !fileExtensionMap.isEmpty();
			case RegistryPackage.ADD_ON__TNS_ENTITY_EXTENSIONS:
				return tnsEntityExtensions != null;
			case RegistryPackage.ADD_ON__FUNCTION_CATALOG_PROVIDER:
				return FUNCTION_CATALOG_PROVIDER_EDEFAULT == null ? functionCatalogProvider != null : !FUNCTION_CATALOG_PROVIDER_EDEFAULT.equals(functionCatalogProvider);
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
		result.append(" (type: ");
		if (typeESet) result.append(type); else result.append("<unset>");
		result.append(", indexResourceProviderClass: ");
		result.append(indexResourceProviderClass);
		result.append(", indexCacheClass: ");
		result.append(indexCacheClass);
		result.append(", ontologyAdapterClass: ");
		result.append(ontologyAdapterClass);
		result.append(", adapterFactoryClass: ");
		result.append(adapterFactoryClass);
		result.append(", tnsEntityHandlerClass: ");
		result.append(tnsEntityHandlerClass);
		result.append(", cddAgentClass: ");
		result.append(cddAgentClass);
		result.append(", functionCatalogProvider: ");
		result.append(functionCatalogProvider);
		result.append(')');
		return result.toString();
	}

} //AddOnImpl
