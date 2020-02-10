/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.common.configuration.impl;

import com.tibco.cep.studio.common.configuration.ConfigurationPackage;
import com.tibco.cep.studio.common.configuration.JAVA_CLASSPATH_ENTRY_TYPE;
import com.tibco.cep.studio.common.configuration.JavaClasspathEntry;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Java Classpath Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.JavaClasspathEntryImpl#getClasspathEntryType <em>Classpath Entry Type</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.JavaClasspathEntryImpl#getSourceFolder <em>Source Folder</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.JavaClasspathEntryImpl#getInclusionPattern <em>Inclusion Pattern</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.JavaClasspathEntryImpl#getExclusionPattern <em>Exclusion Pattern</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.JavaClasspathEntryImpl#getOutputLocation <em>Output Location</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.JavaClasspathEntryImpl#getSourceAttachmentPath <em>Source Attachment Path</em>}</li>
 *   <li>{@link com.tibco.cep.studio.common.configuration.impl.JavaClasspathEntryImpl#getSourceAttachmentRootPath <em>Source Attachment Root Path</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaClasspathEntryImpl extends BuildPathEntryImpl implements JavaClasspathEntry {
	/**
	 * The default value of the '{@link #getClasspathEntryType() <em>Classpath Entry Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClasspathEntryType()
	 * @generated
	 * @ordered
	 */
	protected static final JAVA_CLASSPATH_ENTRY_TYPE CLASSPATH_ENTRY_TYPE_EDEFAULT = JAVA_CLASSPATH_ENTRY_TYPE.CPE_SOURCE;

	/**
	 * The cached value of the '{@link #getClasspathEntryType() <em>Classpath Entry Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getClasspathEntryType()
	 * @generated
	 * @ordered
	 */
	protected JAVA_CLASSPATH_ENTRY_TYPE classpathEntryType = CLASSPATH_ENTRY_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSourceFolder() <em>Source Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceFolder()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_FOLDER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourceFolder() <em>Source Folder</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceFolder()
	 * @generated
	 * @ordered
	 */
	protected String sourceFolder = SOURCE_FOLDER_EDEFAULT;

	/**
	 * The cached value of the '{@link #getInclusionPattern() <em>Inclusion Pattern</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInclusionPattern()
	 * @generated
	 * @ordered
	 */
	protected EList<String> inclusionPattern;

	/**
	 * The cached value of the '{@link #getExclusionPattern() <em>Exclusion Pattern</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExclusionPattern()
	 * @generated
	 * @ordered
	 */
	protected EList<String> exclusionPattern;

	/**
	 * The default value of the '{@link #getOutputLocation() <em>Output Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputLocation()
	 * @generated
	 * @ordered
	 */
	protected static final String OUTPUT_LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOutputLocation() <em>Output Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputLocation()
	 * @generated
	 * @ordered
	 */
	protected String outputLocation = OUTPUT_LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getSourceAttachmentPath() <em>Source Attachment Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceAttachmentPath()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_ATTACHMENT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourceAttachmentPath() <em>Source Attachment Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceAttachmentPath()
	 * @generated
	 * @ordered
	 */
	protected String sourceAttachmentPath = SOURCE_ATTACHMENT_PATH_EDEFAULT;

	/**
	 * The default value of the '{@link #getSourceAttachmentRootPath() <em>Source Attachment Root Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceAttachmentRootPath()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_ATTACHMENT_ROOT_PATH_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSourceAttachmentRootPath() <em>Source Attachment Root Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSourceAttachmentRootPath()
	 * @generated
	 * @ordered
	 */
	protected String sourceAttachmentRootPath = SOURCE_ATTACHMENT_ROOT_PATH_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaClasspathEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.JAVA_CLASSPATH_ENTRY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public JAVA_CLASSPATH_ENTRY_TYPE getClasspathEntryType() {
		return classpathEntryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setClasspathEntryType(JAVA_CLASSPATH_ENTRY_TYPE newClasspathEntryType) {
		JAVA_CLASSPATH_ENTRY_TYPE oldClasspathEntryType = classpathEntryType;
		classpathEntryType = newClasspathEntryType == null ? CLASSPATH_ENTRY_TYPE_EDEFAULT : newClasspathEntryType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.JAVA_CLASSPATH_ENTRY__CLASSPATH_ENTRY_TYPE, oldClasspathEntryType, classpathEntryType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSourceFolder() {
		return sourceFolder;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceFolder(String newSourceFolder) {
		String oldSourceFolder = sourceFolder;
		sourceFolder = newSourceFolder;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_FOLDER, oldSourceFolder, sourceFolder));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getInclusionPattern() {
		if (inclusionPattern == null) {
			inclusionPattern = new EDataTypeUniqueEList<String>(String.class, this, ConfigurationPackage.JAVA_CLASSPATH_ENTRY__INCLUSION_PATTERN);
		}
		return inclusionPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<String> getExclusionPattern() {
		if (exclusionPattern == null) {
			exclusionPattern = new EDataTypeUniqueEList<String>(String.class, this, ConfigurationPackage.JAVA_CLASSPATH_ENTRY__EXCLUSION_PATTERN);
		}
		return exclusionPattern;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOutputLocation() {
		return outputLocation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputLocation(String newOutputLocation) {
		String oldOutputLocation = outputLocation;
		outputLocation = newOutputLocation;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.JAVA_CLASSPATH_ENTRY__OUTPUT_LOCATION, oldOutputLocation, outputLocation));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSourceAttachmentPath() {
		return sourceAttachmentPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceAttachmentPath(String newSourceAttachmentPath) {
		String oldSourceAttachmentPath = sourceAttachmentPath;
		sourceAttachmentPath = newSourceAttachmentPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_PATH, oldSourceAttachmentPath, sourceAttachmentPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSourceAttachmentRootPath() {
		return sourceAttachmentRootPath;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSourceAttachmentRootPath(String newSourceAttachmentRootPath) {
		String oldSourceAttachmentRootPath = sourceAttachmentRootPath;
		sourceAttachmentRootPath = newSourceAttachmentRootPath;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_ROOT_PATH, oldSourceAttachmentRootPath, sourceAttachmentRootPath));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__CLASSPATH_ENTRY_TYPE:
				return getClasspathEntryType();
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_FOLDER:
				return getSourceFolder();
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__INCLUSION_PATTERN:
				return getInclusionPattern();
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__EXCLUSION_PATTERN:
				return getExclusionPattern();
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__OUTPUT_LOCATION:
				return getOutputLocation();
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_PATH:
				return getSourceAttachmentPath();
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_ROOT_PATH:
				return getSourceAttachmentRootPath();
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
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__CLASSPATH_ENTRY_TYPE:
				setClasspathEntryType((JAVA_CLASSPATH_ENTRY_TYPE)newValue);
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_FOLDER:
				setSourceFolder((String)newValue);
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__INCLUSION_PATTERN:
				getInclusionPattern().clear();
				getInclusionPattern().addAll((Collection<? extends String>)newValue);
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__EXCLUSION_PATTERN:
				getExclusionPattern().clear();
				getExclusionPattern().addAll((Collection<? extends String>)newValue);
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__OUTPUT_LOCATION:
				setOutputLocation((String)newValue);
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_PATH:
				setSourceAttachmentPath((String)newValue);
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_ROOT_PATH:
				setSourceAttachmentRootPath((String)newValue);
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
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__CLASSPATH_ENTRY_TYPE:
				setClasspathEntryType(CLASSPATH_ENTRY_TYPE_EDEFAULT);
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_FOLDER:
				setSourceFolder(SOURCE_FOLDER_EDEFAULT);
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__INCLUSION_PATTERN:
				getInclusionPattern().clear();
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__EXCLUSION_PATTERN:
				getExclusionPattern().clear();
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__OUTPUT_LOCATION:
				setOutputLocation(OUTPUT_LOCATION_EDEFAULT);
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_PATH:
				setSourceAttachmentPath(SOURCE_ATTACHMENT_PATH_EDEFAULT);
				return;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_ROOT_PATH:
				setSourceAttachmentRootPath(SOURCE_ATTACHMENT_ROOT_PATH_EDEFAULT);
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
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__CLASSPATH_ENTRY_TYPE:
				return classpathEntryType != CLASSPATH_ENTRY_TYPE_EDEFAULT;
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_FOLDER:
				return SOURCE_FOLDER_EDEFAULT == null ? sourceFolder != null : !SOURCE_FOLDER_EDEFAULT.equals(sourceFolder);
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__INCLUSION_PATTERN:
				return inclusionPattern != null && !inclusionPattern.isEmpty();
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__EXCLUSION_PATTERN:
				return exclusionPattern != null && !exclusionPattern.isEmpty();
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__OUTPUT_LOCATION:
				return OUTPUT_LOCATION_EDEFAULT == null ? outputLocation != null : !OUTPUT_LOCATION_EDEFAULT.equals(outputLocation);
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_PATH:
				return SOURCE_ATTACHMENT_PATH_EDEFAULT == null ? sourceAttachmentPath != null : !SOURCE_ATTACHMENT_PATH_EDEFAULT.equals(sourceAttachmentPath);
			case ConfigurationPackage.JAVA_CLASSPATH_ENTRY__SOURCE_ATTACHMENT_ROOT_PATH:
				return SOURCE_ATTACHMENT_ROOT_PATH_EDEFAULT == null ? sourceAttachmentRootPath != null : !SOURCE_ATTACHMENT_ROOT_PATH_EDEFAULT.equals(sourceAttachmentRootPath);
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
		result.append(" (classpathEntryType: ");
		result.append(classpathEntryType);
		result.append(", sourceFolder: ");
		result.append(sourceFolder);
		result.append(", inclusionPattern: ");
		result.append(inclusionPattern);
		result.append(", exclusionPattern: ");
		result.append(exclusionPattern);
		result.append(", outputLocation: ");
		result.append(outputLocation);
		result.append(", sourceAttachmentPath: ");
		result.append(sourceAttachmentPath);
		result.append(", sourceAttachmentRootPath: ");
		result.append(sourceAttachmentRootPath);
		result.append(')');
		return result.toString();
	}

} //JavaClasspathEntryImpl
