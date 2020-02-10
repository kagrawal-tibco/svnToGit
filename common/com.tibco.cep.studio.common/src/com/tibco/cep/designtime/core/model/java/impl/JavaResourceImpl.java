/**
 */
package com.tibco.cep.designtime.core.model.java.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.impl.EntityImpl;
import com.tibco.cep.designtime.core.model.java.JavaPackage;
import com.tibco.cep.designtime.core.model.java.JavaResource;
import com.tibco.cep.studio.common.util.Path;
import com.tibco.cep.studio.core.index.model.DesignerElement;
import com.tibco.cep.studio.core.index.model.DesignerProject;
import com.tibco.cep.studio.core.index.model.RuleElement;
import com.tibco.cep.studio.core.index.model.SharedElement;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Resource</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.java.impl.JavaResourceImpl#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.java.impl.JavaResourceImpl#getContent <em>Content</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.java.impl.JavaResourceImpl#getExtension <em>Extension</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class JavaResourceImpl extends EntityImpl implements JavaResource {
	/**
	 * The default value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_NAME_EDEFAULT = "null";

	/**
	 * The cached value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected String packageName = PACKAGE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected static final byte[] CONTENT_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
	protected byte[] content = CONTENT_EDEFAULT;

	/**
	 * The default value of the '{@link #getExtension() <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtension()
	 * @generated
	 * @ordered
	 */
	protected static final String EXTENSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getExtension() <em>Extension</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtension()
	 * @generated
	 * @ordered
	 */
	protected String extension = EXTENSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected JavaResourceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return JavaPackage.Literals.JAVA_RESOURCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackageName(String newPackageName) {
		String oldPackageName = packageName;
		packageName = newPackageName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaPackage.JAVA_RESOURCE__PACKAGE_NAME, oldPackageName, packageName));
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public byte[] getContent() {
		InputStream sourceStream = null;
		DesignerElement designerElement = CommonIndexUtils.getElement(getOwnerProjectName(), CommonIndexUtils.getFullPath(this));
		try {
			if (designerElement == null && eContainer() instanceof SharedElement) {
				designerElement = (DesignerElement) eContainer();
			}
			if (designerElement instanceof SharedElement) {
				String archivePath = ((SharedElement) designerElement).getArchivePath();
				Path packageFolder = new Path(getFolder());
				if(packageFolder.segmentCount() > 0){
					if(packageFolder.segment(0).indexOf(".projlib") != -1){
						packageFolder = packageFolder.removeFirstSegments(1);
					}
					packageFolder = packageFolder.removeTrailingSeparator().makeRelative();
				}
				String sourcePath = String.format("jar:file:///%s!/%s/%s.%s", archivePath, packageFolder.toPortableString(), getName(), getExtension());
				java.net.URI sourceURI = new java.net.URI(sourcePath);
				sourceStream = sourceURI.toURL().openStream();
			} else {
				Path rootPath = new Path(CommonIndexUtils.getIndexRootPath(getOwnerProjectName()));
				Path fullPath = rootPath.append(CommonIndexUtils.getFullPath(this));
				if (!fullPath.toFile().getName().endsWith(getExtension())) {
					fullPath = fullPath.addFileExtension(getExtension());
				}
				sourceStream = new FileInputStream(fullPath.toOSString());
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[16384];
			int nRead = 0;
			while ((nRead = sourceStream.read(buffer, 0, buffer.length)) != -1) {
				baos.write(buffer, 0, nRead);
			}
			content = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			content = new byte[0];
		}
		return content;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContent(byte[] newContent) {
		byte[] oldContent = content;
		content = newContent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaPackage.JAVA_RESOURCE__CONTENT, oldContent, content));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtension(String newExtension) {
		String oldExtension = extension;
		extension = newExtension;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, JavaPackage.JAVA_RESOURCE__EXTENSION, oldExtension, extension));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case JavaPackage.JAVA_RESOURCE__PACKAGE_NAME:
				return getPackageName();
			case JavaPackage.JAVA_RESOURCE__CONTENT:
				return getContent();
			case JavaPackage.JAVA_RESOURCE__EXTENSION:
				return getExtension();
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
			case JavaPackage.JAVA_RESOURCE__PACKAGE_NAME:
				setPackageName((String)newValue);
				return;
			case JavaPackage.JAVA_RESOURCE__CONTENT:
				setContent((byte[])newValue);
				return;
			case JavaPackage.JAVA_RESOURCE__EXTENSION:
				setExtension((String)newValue);
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
			case JavaPackage.JAVA_RESOURCE__PACKAGE_NAME:
				setPackageName(PACKAGE_NAME_EDEFAULT);
				return;
			case JavaPackage.JAVA_RESOURCE__CONTENT:
				setContent(CONTENT_EDEFAULT);
				return;
			case JavaPackage.JAVA_RESOURCE__EXTENSION:
				setExtension(EXTENSION_EDEFAULT);
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
			case JavaPackage.JAVA_RESOURCE__PACKAGE_NAME:
				return PACKAGE_NAME_EDEFAULT == null ? packageName != null : !PACKAGE_NAME_EDEFAULT.equals(packageName);
			case JavaPackage.JAVA_RESOURCE__CONTENT:
				return CONTENT_EDEFAULT == null ? content != null : !CONTENT_EDEFAULT.equals(content);
			case JavaPackage.JAVA_RESOURCE__EXTENSION:
				return EXTENSION_EDEFAULT == null ? extension != null : !EXTENSION_EDEFAULT.equals(extension);
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
		result.append(" (packageName: ");
		result.append(packageName);
		result.append(", content: ");
		result.append(content);
		result.append(", extension: ");
		result.append(extension);
		result.append(')');
		return result.toString();
	}

} //JavaResourceImpl
