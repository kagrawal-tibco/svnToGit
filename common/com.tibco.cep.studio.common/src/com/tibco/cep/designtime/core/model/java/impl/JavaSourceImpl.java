/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.java.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URISyntaxException;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.impl.EntityImpl;
import com.tibco.cep.designtime.core.model.java.JavaPackage;
import com.tibco.cep.designtime.core.model.java.JavaSource;
import com.tibco.cep.designtime.core.model.rule.Compilable;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.adapters.CoreAdapterFactory;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.JavaElement;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedEntityElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Source</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.java.impl.JavaSourceImpl#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.java.impl.JavaSourceImpl#getFullSourceText <em>Full Source Text</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaSourceImpl extends EntityImpl implements JavaSource {
	/**
	 * The default value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_NAME_EDEFAULT = "null";
	/**
	 * The cached value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected String packageName = PACKAGE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getFullSourceText() <em>Full Source Text</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getFullSourceText()
	 * @generated
	 * @ordered
	 */
	protected static final byte[] FULL_SOURCE_TEXT_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getFullSourceText() <em>Full Source Text</em>}' attribute.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #getFullSourceText()
	 * @generated
	 * @ordered
	 */
	protected byte[] fullSourceText = FULL_SOURCE_TEXT_EDEFAULT;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaSourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JavaPackage.Literals.JAVA_SOURCE;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackageName(String newPackageName) {
		String oldPackageName = packageName;
		packageName = newPackageName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaPackage.JAVA_SOURCE__PACKAGE_NAME, oldPackageName, packageName));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getFolder() {
		if (folder == null) {
			EObject rootContainer = CommonIndexUtils.getRootContainer(this);
			if (rootContainer instanceof Entity) {
				folder = ((Entity) rootContainer).getFullPath();
			}
		}
		return folder;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public String getOwnerProjectName() {
		if (ownerProjectName == null) {
			EObject rootContainer = CommonIndexUtils.getRootContainer(this);
			if (rootContainer == this) {
				return null; // avoid loop
			}
			if (rootContainer instanceof Entity) {
				ownerProjectName = ((Entity) rootContainer).getOwnerProjectName();
			} else if (rootContainer instanceof RuleElement) {
				ownerProjectName = ((RuleElement) rootContainer).getIndexName();
			} else if (rootContainer instanceof DesignerProject) {
				ownerProjectName = ((DesignerProject) rootContainer).getName();
			}
		}
		return ownerProjectName;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public byte[] getFullSourceText() {
		InputStream sourceStream = null;
		DesignerElement designerElement = CommonIndexUtils.getElement(getOwnerProjectName(), CommonIndexUtils.getFullPath(this));
		try {
			if (designerElement instanceof SharedEntityElement) {
				String archivePath = ((SharedEntityElement) designerElement).getArchivePath();
				Path packageFolder = new Path(getFolder());
				if(packageFolder.segmentCount() > 0){
					if(packageFolder.segment(0).indexOf(".projlib") != -1){
						packageFolder = packageFolder.removeFirstSegments(1);
					}
					packageFolder = packageFolder.removeTrailingSeparator().makeRelative();
				}
				String sourcePath = String.format("jar:file:///%s!/%s/%s.%s", archivePath, packageFolder.toPortableString(), getName(), CommonIndexUtils.JAVA_EXTENSION);
				sourcePath = sourcePath.replaceAll(" ", "%20");
				java.net.URI sourceURI = new java.net.URI(sourcePath);
				sourceStream = sourceURI.toURL().openStream();
			} else {

				Path rootPath = new Path(CommonIndexUtils.getIndexRootPath(getOwnerProjectName()));
				Path fullPath = rootPath.append(CommonIndexUtils.getFullPath(this));
				fullPath = fullPath.addFileExtension(CommonIndexUtils.JAVA_EXTENSION);
				sourceStream = new FileInputStream(fullPath.toOSString());
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[16384];
			int nRead = 0;
			while ((nRead = sourceStream.read(buffer, 0, buffer.length)) != -1) {
				baos.write(buffer, 0, nRead);
			}
			fullSourceText = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			fullSourceText = new byte[0];
		}
		return fullSourceText;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setFullSourceText(byte[] newFullSourceText) {
		byte[] oldFullSourceText = fullSourceText;
		fullSourceText = newFullSourceText;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaPackage.JAVA_SOURCE__FULL_SOURCE_TEXT, oldFullSourceText, fullSourceText));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JavaPackage.JAVA_SOURCE__PACKAGE_NAME:
				return getPackageName();
			case JavaPackage.JAVA_SOURCE__FULL_SOURCE_TEXT:
				return getFullSourceText();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case JavaPackage.JAVA_SOURCE__PACKAGE_NAME:
				setPackageName((String)newValue);
				return;
			case JavaPackage.JAVA_SOURCE__FULL_SOURCE_TEXT:
				setFullSourceText((byte[])newValue);
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
			case JavaPackage.JAVA_SOURCE__PACKAGE_NAME:
				setPackageName(PACKAGE_NAME_EDEFAULT);
				return;
			case JavaPackage.JAVA_SOURCE__FULL_SOURCE_TEXT:
				setFullSourceText(FULL_SOURCE_TEXT_EDEFAULT);
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
			case JavaPackage.JAVA_SOURCE__PACKAGE_NAME:
				return PACKAGE_NAME_EDEFAULT == null ? packageName != null : !PACKAGE_NAME_EDEFAULT.equals(packageName);
			case JavaPackage.JAVA_SOURCE__FULL_SOURCE_TEXT:
				return FULL_SOURCE_TEXT_EDEFAULT == null ? fullSourceText != null : !FULL_SOURCE_TEXT_EDEFAULT.equals(fullSourceText);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (packageName: ");
		result.append(packageName);
		result.append(", fullSourceText: ");
		result.append(fullSourceText);
		result.append(')');
		return result.toString();
	}

} // JavaSourceImpl
