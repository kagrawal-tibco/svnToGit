/**
 */
package com.tibco.be.util.config.cdd.impl;

import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.util.config.CddTools;
import com.tibco.be.util.config.ConfigTools;
import com.tibco.be.util.config.cdd.BackingStoreForDomainObjectConfig;
import com.tibco.be.util.config.cdd.CddPackage;
import com.tibco.be.util.config.cdd.CompositeIndexesConfig;
import com.tibco.be.util.config.cdd.DomainObjectConfig;
import com.tibco.be.util.config.cdd.DomainObjectModeConfig;
import com.tibco.be.util.config.cdd.FieldEncryptionConfig;
import com.tibco.be.util.config.cdd.IndexesConfig;
import com.tibco.be.util.config.cdd.OverrideConfig;

import java.util.Map;
import java.util.Properties;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Domain Object Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getBackingStore <em>Backing Store</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getCacheLimited <em>Cache Limited</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getCheckForVersion <em>Check For Version</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getConstant <em>Constant</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getEnableTracking <em>Enable Tracking</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getEncryption <em>Encryption</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getEvictOnUpdate <em>Evict On Update</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getIndexes <em>Indexes</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getMode <em>Mode</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getPreLoadEnabled <em>Pre Load Enabled</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getPreLoadFetchSize <em>Pre Load Fetch Size</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getPreLoadHandles <em>Pre Load Handles</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getPreProcessor <em>Pre Processor</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getSubscribe <em>Subscribe</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getUri <em>Uri</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getConceptTTL <em>Concept TTL</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.impl.DomainObjectConfigImpl#getCompositeIndexes <em>Composite Indexes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DomainObjectConfigImpl extends EObjectImpl implements DomainObjectConfig {
	/**
	 * The cached value of the '{@link #getBackingStore() <em>Backing Store</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBackingStore()
	 * @generated
	 * @ordered
	 */
	protected BackingStoreForDomainObjectConfig backingStore;

	/**
	 * The cached value of the '{@link #getCacheLimited() <em>Cache Limited</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCacheLimited()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig cacheLimited;

	/**
	 * The cached value of the '{@link #getCheckForVersion() <em>Check For Version</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCheckForVersion()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig checkForVersion;

	/**
	 * The cached value of the '{@link #getConstant() <em>Constant</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConstant()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig constant;

	/**
	 * The cached value of the '{@link #getEnableTracking() <em>Enable Tracking</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEnableTracking()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig enableTracking;

	/**
	 * The cached value of the '{@link #getEncryption() <em>Encryption</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEncryption()
	 * @generated
	 * @ordered
	 */
	protected FieldEncryptionConfig encryption;

	/**
	 * The cached value of the '{@link #getEvictOnUpdate() <em>Evict On Update</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEvictOnUpdate()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig evictOnUpdate;

	/**
	 * The cached value of the '{@link #getIndexes() <em>Indexes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIndexes()
	 * @generated
	 * @ordered
	 */
	protected IndexesConfig indexes;

	/**
	 * The default value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected static final DomainObjectModeConfig MODE_EDEFAULT = DomainObjectModeConfig.CACHE;

	/**
	 * The cached value of the '{@link #getMode() <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMode()
	 * @generated
	 * @ordered
	 */
	protected DomainObjectModeConfig mode = MODE_EDEFAULT;

	/**
	 * This is true if the Mode attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean modeESet;

	/**
	 * The cached value of the '{@link #getPreLoadEnabled() <em>Pre Load Enabled</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreLoadEnabled()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig preLoadEnabled;

	/**
	 * The cached value of the '{@link #getPreLoadFetchSize() <em>Pre Load Fetch Size</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreLoadFetchSize()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig preLoadFetchSize;

	/**
	 * The cached value of the '{@link #getPreLoadHandles() <em>Pre Load Handles</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreLoadHandles()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig preLoadHandles;

	/**
	 * The default value of the '{@link #getPreProcessor() <em>Pre Processor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreProcessor()
	 * @generated
	 * @ordered
	 */
	protected static final String PRE_PROCESSOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPreProcessor() <em>Pre Processor</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreProcessor()
	 * @generated
	 * @ordered
	 */
	protected String preProcessor = PRE_PROCESSOR_EDEFAULT;

	/**
	 * The cached value of the '{@link #getSubscribe() <em>Subscribe</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubscribe()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig subscribe;

	/**
	 * The default value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected static final String URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUri() <em>Uri</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUri()
	 * @generated
	 * @ordered
	 */
	protected String uri = URI_EDEFAULT;

	/**
	 * The cached value of the '{@link #getConceptTTL() <em>Concept TTL</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConceptTTL()
	 * @generated
	 * @ordered
	 */
	protected OverrideConfig conceptTTL;

	/**
	 * The cached value of the '{@link #getCompositeIndexes() <em>Composite Indexes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCompositeIndexes()
	 * @generated
	 * @ordered
	 */
	protected CompositeIndexesConfig compositeIndexes;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DomainObjectConfigImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CddPackage.eINSTANCE.getDomainObjectConfig();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BackingStoreForDomainObjectConfig getBackingStore() {
		return backingStore;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBackingStore(BackingStoreForDomainObjectConfig newBackingStore, NotificationChain msgs) {
		BackingStoreForDomainObjectConfig oldBackingStore = backingStore;
		backingStore = newBackingStore;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__BACKING_STORE, oldBackingStore, newBackingStore);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBackingStore(BackingStoreForDomainObjectConfig newBackingStore) {
		if (newBackingStore != backingStore) {
			NotificationChain msgs = null;
			if (backingStore != null)
				msgs = ((InternalEObject)backingStore).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__BACKING_STORE, null, msgs);
			if (newBackingStore != null)
				msgs = ((InternalEObject)newBackingStore).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__BACKING_STORE, null, msgs);
			msgs = basicSetBackingStore(newBackingStore, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__BACKING_STORE, newBackingStore, newBackingStore));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCacheLimited() {
		return cacheLimited;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCacheLimited(OverrideConfig newCacheLimited, NotificationChain msgs) {
		OverrideConfig oldCacheLimited = cacheLimited;
		cacheLimited = newCacheLimited;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__CACHE_LIMITED, oldCacheLimited, newCacheLimited);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCacheLimited(OverrideConfig newCacheLimited) {
		if (newCacheLimited != cacheLimited) {
			NotificationChain msgs = null;
			if (cacheLimited != null)
				msgs = ((InternalEObject)cacheLimited).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__CACHE_LIMITED, null, msgs);
			if (newCacheLimited != null)
				msgs = ((InternalEObject)newCacheLimited).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__CACHE_LIMITED, null, msgs);
			msgs = basicSetCacheLimited(newCacheLimited, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__CACHE_LIMITED, newCacheLimited, newCacheLimited));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getCheckForVersion() {
		return checkForVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCheckForVersion(OverrideConfig newCheckForVersion, NotificationChain msgs) {
		OverrideConfig oldCheckForVersion = checkForVersion;
		checkForVersion = newCheckForVersion;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__CHECK_FOR_VERSION, oldCheckForVersion, newCheckForVersion);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCheckForVersion(OverrideConfig newCheckForVersion) {
		if (newCheckForVersion != checkForVersion) {
			NotificationChain msgs = null;
			if (checkForVersion != null)
				msgs = ((InternalEObject)checkForVersion).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__CHECK_FOR_VERSION, null, msgs);
			if (newCheckForVersion != null)
				msgs = ((InternalEObject)newCheckForVersion).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__CHECK_FOR_VERSION, null, msgs);
			msgs = basicSetCheckForVersion(newCheckForVersion, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__CHECK_FOR_VERSION, newCheckForVersion, newCheckForVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getConstant() {
		return constant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConstant(OverrideConfig newConstant, NotificationChain msgs) {
		OverrideConfig oldConstant = constant;
		constant = newConstant;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__CONSTANT, oldConstant, newConstant);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConstant(OverrideConfig newConstant) {
		if (newConstant != constant) {
			NotificationChain msgs = null;
			if (constant != null)
				msgs = ((InternalEObject)constant).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__CONSTANT, null, msgs);
			if (newConstant != null)
				msgs = ((InternalEObject)newConstant).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__CONSTANT, null, msgs);
			msgs = basicSetConstant(newConstant, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__CONSTANT, newConstant, newConstant));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEnableTracking() {
		return enableTracking;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnableTracking(OverrideConfig newEnableTracking, NotificationChain msgs) {
		OverrideConfig oldEnableTracking = enableTracking;
		enableTracking = newEnableTracking;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__ENABLE_TRACKING, oldEnableTracking, newEnableTracking);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnableTracking(OverrideConfig newEnableTracking) {
		if (newEnableTracking != enableTracking) {
			NotificationChain msgs = null;
			if (enableTracking != null)
				msgs = ((InternalEObject)enableTracking).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__ENABLE_TRACKING, null, msgs);
			if (newEnableTracking != null)
				msgs = ((InternalEObject)newEnableTracking).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__ENABLE_TRACKING, null, msgs);
			msgs = basicSetEnableTracking(newEnableTracking, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__ENABLE_TRACKING, newEnableTracking, newEnableTracking));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FieldEncryptionConfig getEncryption() {
		return encryption;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEncryption(FieldEncryptionConfig newEncryption, NotificationChain msgs) {
		FieldEncryptionConfig oldEncryption = encryption;
		encryption = newEncryption;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__ENCRYPTION, oldEncryption, newEncryption);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEncryption(FieldEncryptionConfig newEncryption) {
		if (newEncryption != encryption) {
			NotificationChain msgs = null;
			if (encryption != null)
				msgs = ((InternalEObject)encryption).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__ENCRYPTION, null, msgs);
			if (newEncryption != null)
				msgs = ((InternalEObject)newEncryption).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__ENCRYPTION, null, msgs);
			msgs = basicSetEncryption(newEncryption, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__ENCRYPTION, newEncryption, newEncryption));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getEvictOnUpdate() {
		return evictOnUpdate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEvictOnUpdate(OverrideConfig newEvictOnUpdate, NotificationChain msgs) {
		OverrideConfig oldEvictOnUpdate = evictOnUpdate;
		evictOnUpdate = newEvictOnUpdate;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__EVICT_ON_UPDATE, oldEvictOnUpdate, newEvictOnUpdate);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEvictOnUpdate(OverrideConfig newEvictOnUpdate) {
		if (newEvictOnUpdate != evictOnUpdate) {
			NotificationChain msgs = null;
			if (evictOnUpdate != null)
				msgs = ((InternalEObject)evictOnUpdate).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__EVICT_ON_UPDATE, null, msgs);
			if (newEvictOnUpdate != null)
				msgs = ((InternalEObject)newEvictOnUpdate).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__EVICT_ON_UPDATE, null, msgs);
			msgs = basicSetEvictOnUpdate(newEvictOnUpdate, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__EVICT_ON_UPDATE, newEvictOnUpdate, newEvictOnUpdate));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IndexesConfig getIndexes() {
		return indexes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetIndexes(IndexesConfig newIndexes, NotificationChain msgs) {
		IndexesConfig oldIndexes = indexes;
		indexes = newIndexes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__INDEXES, oldIndexes, newIndexes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIndexes(IndexesConfig newIndexes) {
		if (newIndexes != indexes) {
			NotificationChain msgs = null;
			if (indexes != null)
				msgs = ((InternalEObject)indexes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__INDEXES, null, msgs);
			if (newIndexes != null)
				msgs = ((InternalEObject)newIndexes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__INDEXES, null, msgs);
			msgs = basicSetIndexes(newIndexes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__INDEXES, newIndexes, newIndexes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainObjectModeConfig getMode() {
		return mode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMode(DomainObjectModeConfig newMode) {
		DomainObjectModeConfig oldMode = mode;
		mode = newMode == null ? MODE_EDEFAULT : newMode;
		boolean oldModeESet = modeESet;
		modeESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__MODE, oldMode, mode, !oldModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMode() {
		DomainObjectModeConfig oldMode = mode;
		boolean oldModeESet = modeESet;
		mode = MODE_EDEFAULT;
		modeESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, CddPackage.DOMAIN_OBJECT_CONFIG__MODE, oldMode, MODE_EDEFAULT, oldModeESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMode() {
		return modeESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPreLoadEnabled() {
		return preLoadEnabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreLoadEnabled(OverrideConfig newPreLoadEnabled, NotificationChain msgs) {
		OverrideConfig oldPreLoadEnabled = preLoadEnabled;
		preLoadEnabled = newPreLoadEnabled;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_ENABLED, oldPreLoadEnabled, newPreLoadEnabled);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreLoadEnabled(OverrideConfig newPreLoadEnabled) {
		if (newPreLoadEnabled != preLoadEnabled) {
			NotificationChain msgs = null;
			if (preLoadEnabled != null)
				msgs = ((InternalEObject)preLoadEnabled).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_ENABLED, null, msgs);
			if (newPreLoadEnabled != null)
				msgs = ((InternalEObject)newPreLoadEnabled).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_ENABLED, null, msgs);
			msgs = basicSetPreLoadEnabled(newPreLoadEnabled, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_ENABLED, newPreLoadEnabled, newPreLoadEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPreLoadFetchSize() {
		return preLoadFetchSize;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreLoadFetchSize(OverrideConfig newPreLoadFetchSize, NotificationChain msgs) {
		OverrideConfig oldPreLoadFetchSize = preLoadFetchSize;
		preLoadFetchSize = newPreLoadFetchSize;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_FETCH_SIZE, oldPreLoadFetchSize, newPreLoadFetchSize);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreLoadFetchSize(OverrideConfig newPreLoadFetchSize) {
		if (newPreLoadFetchSize != preLoadFetchSize) {
			NotificationChain msgs = null;
			if (preLoadFetchSize != null)
				msgs = ((InternalEObject)preLoadFetchSize).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_FETCH_SIZE, null, msgs);
			if (newPreLoadFetchSize != null)
				msgs = ((InternalEObject)newPreLoadFetchSize).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_FETCH_SIZE, null, msgs);
			msgs = basicSetPreLoadFetchSize(newPreLoadFetchSize, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_FETCH_SIZE, newPreLoadFetchSize, newPreLoadFetchSize));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getPreLoadHandles() {
		return preLoadHandles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPreLoadHandles(OverrideConfig newPreLoadHandles, NotificationChain msgs) {
		OverrideConfig oldPreLoadHandles = preLoadHandles;
		preLoadHandles = newPreLoadHandles;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_HANDLES, oldPreLoadHandles, newPreLoadHandles);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreLoadHandles(OverrideConfig newPreLoadHandles) {
		if (newPreLoadHandles != preLoadHandles) {
			NotificationChain msgs = null;
			if (preLoadHandles != null)
				msgs = ((InternalEObject)preLoadHandles).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_HANDLES, null, msgs);
			if (newPreLoadHandles != null)
				msgs = ((InternalEObject)newPreLoadHandles).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_HANDLES, null, msgs);
			msgs = basicSetPreLoadHandles(newPreLoadHandles, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_HANDLES, newPreLoadHandles, newPreLoadHandles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPreProcessor() {
		return preProcessor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPreProcessor(String newPreProcessor) {
		String oldPreProcessor = preProcessor;
		preProcessor = newPreProcessor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__PRE_PROCESSOR, oldPreProcessor, preProcessor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getSubscribe() {
		return subscribe;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubscribe(OverrideConfig newSubscribe, NotificationChain msgs) {
		OverrideConfig oldSubscribe = subscribe;
		subscribe = newSubscribe;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__SUBSCRIBE, oldSubscribe, newSubscribe);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubscribe(OverrideConfig newSubscribe) {
		if (newSubscribe != subscribe) {
			NotificationChain msgs = null;
			if (subscribe != null)
				msgs = ((InternalEObject)subscribe).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__SUBSCRIBE, null, msgs);
			if (newSubscribe != null)
				msgs = ((InternalEObject)newSubscribe).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__SUBSCRIBE, null, msgs);
			msgs = basicSetSubscribe(newSubscribe, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__SUBSCRIBE, newSubscribe, newSubscribe));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUri(String newUri) {
		String oldUri = uri;
		uri = newUri;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__URI, oldUri, uri));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OverrideConfig getConceptTTL() {
		return conceptTTL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConceptTTL(OverrideConfig newConceptTTL, NotificationChain msgs) {
		OverrideConfig oldConceptTTL = conceptTTL;
		conceptTTL = newConceptTTL;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__CONCEPT_TTL, oldConceptTTL, newConceptTTL);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConceptTTL(OverrideConfig newConceptTTL) {
		if (newConceptTTL != conceptTTL) {
			NotificationChain msgs = null;
			if (conceptTTL != null)
				msgs = ((InternalEObject)conceptTTL).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__CONCEPT_TTL, null, msgs);
			if (newConceptTTL != null)
				msgs = ((InternalEObject)newConceptTTL).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__CONCEPT_TTL, null, msgs);
			msgs = basicSetConceptTTL(newConceptTTL, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__CONCEPT_TTL, newConceptTTL, newConceptTTL));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompositeIndexesConfig getCompositeIndexes() {
		return compositeIndexes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCompositeIndexes(CompositeIndexesConfig newCompositeIndexes, NotificationChain msgs) {
		CompositeIndexesConfig oldCompositeIndexes = compositeIndexes;
		compositeIndexes = newCompositeIndexes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__COMPOSITE_INDEXES, oldCompositeIndexes, newCompositeIndexes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCompositeIndexes(CompositeIndexesConfig newCompositeIndexes) {
		if (newCompositeIndexes != compositeIndexes) {
			NotificationChain msgs = null;
			if (compositeIndexes != null)
				msgs = ((InternalEObject)compositeIndexes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__COMPOSITE_INDEXES, null, msgs);
			if (newCompositeIndexes != null)
				msgs = ((InternalEObject)newCompositeIndexes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - CddPackage.DOMAIN_OBJECT_CONFIG__COMPOSITE_INDEXES, null, msgs);
			msgs = basicSetCompositeIndexes(newCompositeIndexes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, CddPackage.DOMAIN_OBJECT_CONFIG__COMPOSITE_INDEXES, newCompositeIndexes, newCompositeIndexes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case CddPackage.DOMAIN_OBJECT_CONFIG__BACKING_STORE:
				return basicSetBackingStore(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__CACHE_LIMITED:
				return basicSetCacheLimited(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__CHECK_FOR_VERSION:
				return basicSetCheckForVersion(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__CONSTANT:
				return basicSetConstant(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__ENABLE_TRACKING:
				return basicSetEnableTracking(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__ENCRYPTION:
				return basicSetEncryption(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__EVICT_ON_UPDATE:
				return basicSetEvictOnUpdate(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__INDEXES:
				return basicSetIndexes(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_ENABLED:
				return basicSetPreLoadEnabled(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_FETCH_SIZE:
				return basicSetPreLoadFetchSize(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_HANDLES:
				return basicSetPreLoadHandles(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__SUBSCRIBE:
				return basicSetSubscribe(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__CONCEPT_TTL:
				return basicSetConceptTTL(null, msgs);
			case CddPackage.DOMAIN_OBJECT_CONFIG__COMPOSITE_INDEXES:
				return basicSetCompositeIndexes(null, msgs);
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
			case CddPackage.DOMAIN_OBJECT_CONFIG__BACKING_STORE:
				return getBackingStore();
			case CddPackage.DOMAIN_OBJECT_CONFIG__CACHE_LIMITED:
				return getCacheLimited();
			case CddPackage.DOMAIN_OBJECT_CONFIG__CHECK_FOR_VERSION:
				return getCheckForVersion();
			case CddPackage.DOMAIN_OBJECT_CONFIG__CONSTANT:
				return getConstant();
			case CddPackage.DOMAIN_OBJECT_CONFIG__ENABLE_TRACKING:
				return getEnableTracking();
			case CddPackage.DOMAIN_OBJECT_CONFIG__ENCRYPTION:
				return getEncryption();
			case CddPackage.DOMAIN_OBJECT_CONFIG__EVICT_ON_UPDATE:
				return getEvictOnUpdate();
			case CddPackage.DOMAIN_OBJECT_CONFIG__INDEXES:
				return getIndexes();
			case CddPackage.DOMAIN_OBJECT_CONFIG__MODE:
				return getMode();
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_ENABLED:
				return getPreLoadEnabled();
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_FETCH_SIZE:
				return getPreLoadFetchSize();
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_HANDLES:
				return getPreLoadHandles();
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_PROCESSOR:
				return getPreProcessor();
			case CddPackage.DOMAIN_OBJECT_CONFIG__SUBSCRIBE:
				return getSubscribe();
			case CddPackage.DOMAIN_OBJECT_CONFIG__URI:
				return getUri();
			case CddPackage.DOMAIN_OBJECT_CONFIG__CONCEPT_TTL:
				return getConceptTTL();
			case CddPackage.DOMAIN_OBJECT_CONFIG__COMPOSITE_INDEXES:
				return getCompositeIndexes();
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
			case CddPackage.DOMAIN_OBJECT_CONFIG__BACKING_STORE:
				setBackingStore((BackingStoreForDomainObjectConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__CACHE_LIMITED:
				setCacheLimited((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__CHECK_FOR_VERSION:
				setCheckForVersion((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__CONSTANT:
				setConstant((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__ENABLE_TRACKING:
				setEnableTracking((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__ENCRYPTION:
				setEncryption((FieldEncryptionConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__EVICT_ON_UPDATE:
				setEvictOnUpdate((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__INDEXES:
				setIndexes((IndexesConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__MODE:
				setMode((DomainObjectModeConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_ENABLED:
				setPreLoadEnabled((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_FETCH_SIZE:
				setPreLoadFetchSize((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_HANDLES:
				setPreLoadHandles((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_PROCESSOR:
				setPreProcessor((String)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__SUBSCRIBE:
				setSubscribe((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__URI:
				setUri((String)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__CONCEPT_TTL:
				setConceptTTL((OverrideConfig)newValue);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__COMPOSITE_INDEXES:
				setCompositeIndexes((CompositeIndexesConfig)newValue);
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
			case CddPackage.DOMAIN_OBJECT_CONFIG__BACKING_STORE:
				setBackingStore((BackingStoreForDomainObjectConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__CACHE_LIMITED:
				setCacheLimited((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__CHECK_FOR_VERSION:
				setCheckForVersion((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__CONSTANT:
				setConstant((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__ENABLE_TRACKING:
				setEnableTracking((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__ENCRYPTION:
				setEncryption((FieldEncryptionConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__EVICT_ON_UPDATE:
				setEvictOnUpdate((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__INDEXES:
				setIndexes((IndexesConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__MODE:
				unsetMode();
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_ENABLED:
				setPreLoadEnabled((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_FETCH_SIZE:
				setPreLoadFetchSize((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_HANDLES:
				setPreLoadHandles((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_PROCESSOR:
				setPreProcessor(PRE_PROCESSOR_EDEFAULT);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__SUBSCRIBE:
				setSubscribe((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__URI:
				setUri(URI_EDEFAULT);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__CONCEPT_TTL:
				setConceptTTL((OverrideConfig)null);
				return;
			case CddPackage.DOMAIN_OBJECT_CONFIG__COMPOSITE_INDEXES:
				setCompositeIndexes((CompositeIndexesConfig)null);
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
			case CddPackage.DOMAIN_OBJECT_CONFIG__BACKING_STORE:
				return backingStore != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__CACHE_LIMITED:
				return cacheLimited != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__CHECK_FOR_VERSION:
				return checkForVersion != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__CONSTANT:
				return constant != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__ENABLE_TRACKING:
				return enableTracking != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__ENCRYPTION:
				return encryption != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__EVICT_ON_UPDATE:
				return evictOnUpdate != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__INDEXES:
				return indexes != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__MODE:
				return isSetMode();
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_ENABLED:
				return preLoadEnabled != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_FETCH_SIZE:
				return preLoadFetchSize != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_LOAD_HANDLES:
				return preLoadHandles != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__PRE_PROCESSOR:
				return PRE_PROCESSOR_EDEFAULT == null ? preProcessor != null : !PRE_PROCESSOR_EDEFAULT.equals(preProcessor);
			case CddPackage.DOMAIN_OBJECT_CONFIG__SUBSCRIBE:
				return subscribe != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__URI:
				return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
			case CddPackage.DOMAIN_OBJECT_CONFIG__CONCEPT_TTL:
				return conceptTTL != null;
			case CddPackage.DOMAIN_OBJECT_CONFIG__COMPOSITE_INDEXES:
				return compositeIndexes != null;
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
		result.append(" (mode: ");
		if (modeESet) result.append(mode); else result.append("<unset>");
		result.append(", preProcessor: ");
		result.append(preProcessor);
		result.append(", uri: ");
		result.append(uri);
		result.append(')');
		return result.toString();
	}

	/**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public Map<Object, Object> toProperties() {
		final Properties p = new Properties();

		if ((null != this.uri) && !this.uri.isEmpty()) {
			final String uri = ModelNameUtil.modelPathToGeneratedClassName(this.uri);
			final String baseName = "be.engine.cluster." + uri;
			if (null != this.mode) {
				p.put(baseName + ".mode", this.mode.getName());
			}

			CddTools.addEntryFromMixed(p, baseName + ".preload", this.preLoadEnabled, true);
			CddTools.addEntryFromMixed(p, baseName + ".preload.fetchSize", this.preLoadFetchSize, true);
			CddTools.addEntryFromMixed(p, baseName + ".preload.handles", this.preLoadHandles, true);
			CddTools.addEntryFromMixed(p, baseName + ".isCacheLimited", this.cacheLimited, true);
			CddTools.addEntryFromMixed(p, baseName + ".checkForVersion", this.checkForVersion, true);
			CddTools.addEntryFromMixed(p, baseName + ".constant", this.constant, true);
			CddTools.addEntryFromMixed(p, baseName + ".evictFromCacheOnUpdate", this.evictOnUpdate, true);

			if (null != this.backingStore) {
				p.putAll(ConfigTools.replaceInKeys(this.backingStore.toProperties(), "${DomainObject}", uri));
			}

			if (null != this.indexes) {
				p.putAll(ConfigTools.replaceInKeys(this.indexes.toProperties(), "${DomainObject}", uri));
			}
		}
		return p;
	}

} //DomainObjectConfigImpl
