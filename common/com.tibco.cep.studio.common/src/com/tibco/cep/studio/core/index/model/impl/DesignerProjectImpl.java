/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.index.model.impl;

import java.util.Collection;
import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

import com.tibco.cep.designtime.core.model.service.channel.DriverManager;
import com.tibco.cep.studio.core.index.model.ArchiveElement;
import com.tibco.cep.studio.core.index.model.DecisionTableElement;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.EntityElement;
import com.tibco.cep.studio.core.index.model.IStructuredElementVisitor;
import com.tibco.cep.studio.core.index.model.IndexPackage;
import com.tibco.cep.studio.core.index.model.InstanceElement;
import com.tibco.cep.studio.core.index.model.RuleElement;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Designer Project</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getEntries <em>Entries</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getEntityElements <em>Entity Elements</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getDecisionTableElements <em>Decision Table Elements</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getArchiveElements <em>Archive Elements</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getRuleElements <em>Rule Elements</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getRootPath <em>Root Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getLastPersisted <em>Last Persisted</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getReferencedProjects <em>Referenced Projects</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getDriverManager <em>Driver Manager</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getDomainInstanceElements <em>Domain Instance Elements</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getArchivePath <em>Archive Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.index.model.impl.DesignerProjectImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DesignerProjectImpl extends DesignerElementImpl implements DesignerProject {
	/**
	 * The cached value of the '{@link #getEntries() <em>Entries</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntries()
	 * @generated
	 * @ordered
	 */
	protected EList<DesignerElement> entries;

	/**
	 * The cached value of the '{@link #getEntityElements() <em>Entity Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEntityElements()
	 * @generated
	 * @ordered
	 */
	protected EList<EntityElement> entityElements;

	/**
	 * The cached value of the '{@link #getDecisionTableElements() <em>Decision Table Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDecisionTableElements()
	 * @generated
	 * @ordered
	 */
	protected EList<DecisionTableElement> decisionTableElements;

	/**
	 * The cached value of the '{@link #getArchiveElements() <em>Archive Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchiveElements()
	 * @generated
	 * @ordered
	 */
	protected EList<ArchiveElement> archiveElements;

	/**
	 * The cached value of the '{@link #getRuleElements() <em>Rule Elements</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleElements()
	 * @generated
	 * @ordered
	 */
	protected EList<RuleElement> ruleElements;

	/**
	 * The default value of the '{@link #getRootPath() <em>Root Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootPath()
	 * @generated
	 * @ordered
	 */
	protected static final String ROOT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRootPath() <em>Root Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRootPath()
	 * @generated
	 * @ordered
	 */
	protected String rootPath = ROOT_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getLastPersisted() <em>Last Persisted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastPersisted()
	 * @generated
	 * @ordered
	 */
	protected static final Date LAST_PERSISTED_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLastPersisted() <em>Last Persisted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLastPersisted()
	 * @generated
	 * @ordered
	 */
	protected Date lastPersisted = LAST_PERSISTED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getReferencedProjects() <em>Referenced Projects</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedProjects()
	 * @generated
	 * @ordered
	 */
	protected EList<DesignerProject> referencedProjects;

	/**
	 * The cached value of the '{@link #getDriverManager() <em>Driver Manager</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDriverManager()
	 * @generated
	 * @ordered
	 */
	protected DriverManager driverManager;

	/**
	 * The cached value of the '{@link #getDomainInstanceElements() <em>Domain Instance Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDomainInstanceElements()
	 * @generated
	 * @ordered
	 */
	protected EList<InstanceElement<?>> domainInstanceElements;

	/**
	 * The default value of the '{@link #getArchivePath() <em>Archive Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchivePath()
	 * @generated
	 * @ordered
	 */
	protected static final String ARCHIVE_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getArchivePath() <em>Archive Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getArchivePath()
	 * @generated
	 * @ordered
	 */
	protected String archivePath = ARCHIVE_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final int VERSION_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected int version = VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DesignerProjectImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return IndexPackage.Literals.DESIGNER_PROJECT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DesignerElement> getEntries() {
		if (entries == null) {
			entries = new EObjectContainmentEList<DesignerElement>(DesignerElement.class, this, IndexPackage.DESIGNER_PROJECT__ENTRIES);
		}
		return entries;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<EntityElement> getEntityElements() {
		if (entityElements == null) {
			entityElements = new EObjectResolvingEList<EntityElement>(EntityElement.class, this, IndexPackage.DESIGNER_PROJECT__ENTITY_ELEMENTS);
		}
		return entityElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DecisionTableElement> getDecisionTableElements() {
		if (decisionTableElements == null) {
			decisionTableElements = new EObjectResolvingEList<DecisionTableElement>(DecisionTableElement.class, this, IndexPackage.DESIGNER_PROJECT__DECISION_TABLE_ELEMENTS);
		}
		return decisionTableElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ArchiveElement> getArchiveElements() {
		if (archiveElements == null) {
			archiveElements = new EObjectResolvingEList<ArchiveElement>(ArchiveElement.class, this, IndexPackage.DESIGNER_PROJECT__ARCHIVE_ELEMENTS);
		}
		return archiveElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RuleElement> getRuleElements() {
		if (ruleElements == null) {
			ruleElements = new EObjectResolvingEList<RuleElement>(RuleElement.class, this, IndexPackage.DESIGNER_PROJECT__RULE_ELEMENTS);
		}
		return ruleElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRootPath() {
		return rootPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootPath(String newRootPath) {
		String oldRootPath = rootPath;
		rootPath = newRootPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.DESIGNER_PROJECT__ROOT_PATH, oldRootPath, rootPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getLastPersisted() {
		return lastPersisted;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastPersisted(Date newLastPersisted) {
		Date oldLastPersisted = lastPersisted;
		lastPersisted = newLastPersisted;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.DESIGNER_PROJECT__LAST_PERSISTED, oldLastPersisted, lastPersisted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<DesignerProject> getReferencedProjects() {
		if (referencedProjects == null) {
			referencedProjects = new EObjectContainmentEList<DesignerProject>(DesignerProject.class, this, IndexPackage.DESIGNER_PROJECT__REFERENCED_PROJECTS);
		}
		return referencedProjects;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DriverManager getDriverManager() {
		if (driverManager != null && driverManager.eIsProxy()) {
			InternalEObject oldDriverManager = (InternalEObject)driverManager;
			driverManager = (DriverManager)eResolveProxy(oldDriverManager);
			if (driverManager != oldDriverManager) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, IndexPackage.DESIGNER_PROJECT__DRIVER_MANAGER, oldDriverManager, driverManager));
			}
		}
		return driverManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DriverManager basicGetDriverManager() {
		return driverManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDriverManager(DriverManager newDriverManager) {
		DriverManager oldDriverManager = driverManager;
		driverManager = newDriverManager;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.DESIGNER_PROJECT__DRIVER_MANAGER, oldDriverManager, driverManager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<InstanceElement<?>> getDomainInstanceElements() {
		if (domainInstanceElements == null) {
			domainInstanceElements = new EObjectContainmentEList<InstanceElement<?>>(InstanceElement.class, this, IndexPackage.DESIGNER_PROJECT__DOMAIN_INSTANCE_ELEMENTS);
		}
		return domainInstanceElements;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getArchivePath() {
		return archivePath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setArchivePath(String newArchivePath) {
		String oldArchivePath = archivePath;
		archivePath = newArchivePath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.DESIGNER_PROJECT__ARCHIVE_PATH, oldArchivePath, archivePath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(int newVersion) {
		int oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, IndexPackage.DESIGNER_PROJECT__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case IndexPackage.DESIGNER_PROJECT__ENTRIES:
				return ((InternalEList<?>)getEntries()).basicRemove(otherEnd, msgs);
			case IndexPackage.DESIGNER_PROJECT__REFERENCED_PROJECTS:
				return ((InternalEList<?>)getReferencedProjects()).basicRemove(otherEnd, msgs);
			case IndexPackage.DESIGNER_PROJECT__DOMAIN_INSTANCE_ELEMENTS:
				return ((InternalEList<?>)getDomainInstanceElements()).basicRemove(otherEnd, msgs);
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
			case IndexPackage.DESIGNER_PROJECT__ENTRIES:
				return getEntries();
			case IndexPackage.DESIGNER_PROJECT__ENTITY_ELEMENTS:
				return getEntityElements();
			case IndexPackage.DESIGNER_PROJECT__DECISION_TABLE_ELEMENTS:
				return getDecisionTableElements();
			case IndexPackage.DESIGNER_PROJECT__ARCHIVE_ELEMENTS:
				return getArchiveElements();
			case IndexPackage.DESIGNER_PROJECT__RULE_ELEMENTS:
				return getRuleElements();
			case IndexPackage.DESIGNER_PROJECT__ROOT_PATH:
				return getRootPath();
			case IndexPackage.DESIGNER_PROJECT__LAST_PERSISTED:
				return getLastPersisted();
			case IndexPackage.DESIGNER_PROJECT__REFERENCED_PROJECTS:
				return getReferencedProjects();
			case IndexPackage.DESIGNER_PROJECT__DRIVER_MANAGER:
				if (resolve) return getDriverManager();
				return basicGetDriverManager();
			case IndexPackage.DESIGNER_PROJECT__DOMAIN_INSTANCE_ELEMENTS:
				return getDomainInstanceElements();
			case IndexPackage.DESIGNER_PROJECT__ARCHIVE_PATH:
				return getArchivePath();
			case IndexPackage.DESIGNER_PROJECT__VERSION:
				return getVersion();
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
			case IndexPackage.DESIGNER_PROJECT__ENTRIES:
				getEntries().clear();
				getEntries().addAll((Collection<? extends DesignerElement>)newValue);
				return;
			case IndexPackage.DESIGNER_PROJECT__ENTITY_ELEMENTS:
				getEntityElements().clear();
				getEntityElements().addAll((Collection<? extends EntityElement>)newValue);
				return;
			case IndexPackage.DESIGNER_PROJECT__DECISION_TABLE_ELEMENTS:
				getDecisionTableElements().clear();
				getDecisionTableElements().addAll((Collection<? extends DecisionTableElement>)newValue);
				return;
			case IndexPackage.DESIGNER_PROJECT__ARCHIVE_ELEMENTS:
				getArchiveElements().clear();
				getArchiveElements().addAll((Collection<? extends ArchiveElement>)newValue);
				return;
			case IndexPackage.DESIGNER_PROJECT__RULE_ELEMENTS:
				getRuleElements().clear();
				getRuleElements().addAll((Collection<? extends RuleElement>)newValue);
				return;
			case IndexPackage.DESIGNER_PROJECT__ROOT_PATH:
				setRootPath((String)newValue);
				return;
			case IndexPackage.DESIGNER_PROJECT__LAST_PERSISTED:
				setLastPersisted((Date)newValue);
				return;
			case IndexPackage.DESIGNER_PROJECT__REFERENCED_PROJECTS:
				getReferencedProjects().clear();
				getReferencedProjects().addAll((Collection<? extends DesignerProject>)newValue);
				return;
			case IndexPackage.DESIGNER_PROJECT__DRIVER_MANAGER:
				setDriverManager((DriverManager)newValue);
				return;
			case IndexPackage.DESIGNER_PROJECT__DOMAIN_INSTANCE_ELEMENTS:
				getDomainInstanceElements().clear();
				getDomainInstanceElements().addAll((Collection<? extends InstanceElement<?>>)newValue);
				return;
			case IndexPackage.DESIGNER_PROJECT__ARCHIVE_PATH:
				setArchivePath((String)newValue);
				return;
			case IndexPackage.DESIGNER_PROJECT__VERSION:
				setVersion((Integer)newValue);
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
			case IndexPackage.DESIGNER_PROJECT__ENTRIES:
				getEntries().clear();
				return;
			case IndexPackage.DESIGNER_PROJECT__ENTITY_ELEMENTS:
				getEntityElements().clear();
				return;
			case IndexPackage.DESIGNER_PROJECT__DECISION_TABLE_ELEMENTS:
				getDecisionTableElements().clear();
				return;
			case IndexPackage.DESIGNER_PROJECT__ARCHIVE_ELEMENTS:
				getArchiveElements().clear();
				return;
			case IndexPackage.DESIGNER_PROJECT__RULE_ELEMENTS:
				getRuleElements().clear();
				return;
			case IndexPackage.DESIGNER_PROJECT__ROOT_PATH:
				setRootPath(ROOT_PATH_EDEFAULT);
				return;
			case IndexPackage.DESIGNER_PROJECT__LAST_PERSISTED:
				setLastPersisted(LAST_PERSISTED_EDEFAULT);
				return;
			case IndexPackage.DESIGNER_PROJECT__REFERENCED_PROJECTS:
				getReferencedProjects().clear();
				return;
			case IndexPackage.DESIGNER_PROJECT__DRIVER_MANAGER:
				setDriverManager((DriverManager)null);
				return;
			case IndexPackage.DESIGNER_PROJECT__DOMAIN_INSTANCE_ELEMENTS:
				getDomainInstanceElements().clear();
				return;
			case IndexPackage.DESIGNER_PROJECT__ARCHIVE_PATH:
				setArchivePath(ARCHIVE_PATH_EDEFAULT);
				return;
			case IndexPackage.DESIGNER_PROJECT__VERSION:
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
			case IndexPackage.DESIGNER_PROJECT__ENTRIES:
				return entries != null && !entries.isEmpty();
			case IndexPackage.DESIGNER_PROJECT__ENTITY_ELEMENTS:
				return entityElements != null && !entityElements.isEmpty();
			case IndexPackage.DESIGNER_PROJECT__DECISION_TABLE_ELEMENTS:
				return decisionTableElements != null && !decisionTableElements.isEmpty();
			case IndexPackage.DESIGNER_PROJECT__ARCHIVE_ELEMENTS:
				return archiveElements != null && !archiveElements.isEmpty();
			case IndexPackage.DESIGNER_PROJECT__RULE_ELEMENTS:
				return ruleElements != null && !ruleElements.isEmpty();
			case IndexPackage.DESIGNER_PROJECT__ROOT_PATH:
				return ROOT_PATH_EDEFAULT == null ? rootPath != null : !ROOT_PATH_EDEFAULT.equals(rootPath);
			case IndexPackage.DESIGNER_PROJECT__LAST_PERSISTED:
				return LAST_PERSISTED_EDEFAULT == null ? lastPersisted != null : !LAST_PERSISTED_EDEFAULT.equals(lastPersisted);
			case IndexPackage.DESIGNER_PROJECT__REFERENCED_PROJECTS:
				return referencedProjects != null && !referencedProjects.isEmpty();
			case IndexPackage.DESIGNER_PROJECT__DRIVER_MANAGER:
				return driverManager != null;
			case IndexPackage.DESIGNER_PROJECT__DOMAIN_INSTANCE_ELEMENTS:
				return domainInstanceElements != null && !domainInstanceElements.isEmpty();
			case IndexPackage.DESIGNER_PROJECT__ARCHIVE_PATH:
				return ARCHIVE_PATH_EDEFAULT == null ? archivePath != null : !ARCHIVE_PATH_EDEFAULT.equals(archivePath);
			case IndexPackage.DESIGNER_PROJECT__VERSION:
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
		result.append(" (rootPath: ");
		result.append(rootPath);
		result.append(", lastPersisted: ");
		result.append(lastPersisted);
		result.append(", archivePath: ");
		result.append(archivePath);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

	/**
	 * @param visitor
	 * @generated NOT
	 */
	private void doVisitChildren(IStructuredElementVisitor visitor) {
		EList<DesignerElement> entries = getEntries();
		for (int i = 0; i < entries.size(); i++) {
			DesignerElement element = entries.get(i);
			element.accept(visitor);
		}
		EList<DesignerProject> refProjs = getReferencedProjects();
		for (int i = 0; i < refProjs.size(); i++) {
			DesignerProject designerProject = refProjs.get(i);
			designerProject.accept(visitor);
		}
	}

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.core.index.model.DesignerElement#accept(com.tibco.cep.studio.core.index.IStructuredElementVisitor)
	 * @generated NOT
	 */
	public void accept(IStructuredElementVisitor visitor) {
        visitor.preVisit(this);
        if (visitor.visit(this)) {
            doVisitChildren(visitor);
        }
        visitor.postVisit(this);
	}

} //DesignerProjectImpl
