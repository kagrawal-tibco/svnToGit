/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.java;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import com.tibco.cep.designtime.core.model.ModelPackage;
import org.eclipse.emf.ecore.EAttribute;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.tibco.cep.designtime.core.model.java.JavaFactory
 * @model kind="package"
 * @generated
 */
public interface JavaPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "java";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///com/tibco/cep/designtime/core/model/java";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "java";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	JavaPackage eINSTANCE = com.tibco.cep.designtime.core.model.java.impl.JavaPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.java.impl.JavaSourceImpl <em>Source</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.java.impl.JavaSourceImpl
	 * @see com.tibco.cep.designtime.core.model.java.impl.JavaPackageImpl#getJavaSource()
	 * @generated
	 */
	int JAVA_SOURCE = 0;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__PACKAGE_NAME = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Full Source Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE__FULL_SOURCE_TEXT = ModelPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Source</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_SOURCE_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 2;


	/**
	 * The meta object id for the '{@link com.tibco.cep.designtime.core.model.java.impl.JavaResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.tibco.cep.designtime.core.model.java.impl.JavaResourceImpl
	 * @see com.tibco.cep.designtime.core.model.java.impl.JavaPackageImpl#getJavaResource()
	 * @generated
	 */
	int JAVA_RESOURCE = 1;

	/**
	 * The feature id for the '<em><b>Namespace</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__NAMESPACE = ModelPackage.ENTITY__NAMESPACE;

	/**
	 * The feature id for the '<em><b>Folder</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__FOLDER = ModelPackage.ENTITY__FOLDER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__NAME = ModelPackage.ENTITY__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__DESCRIPTION = ModelPackage.ENTITY__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Last Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__LAST_MODIFIED = ModelPackage.ENTITY__LAST_MODIFIED;

	/**
	 * The feature id for the '<em><b>GUID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__GUID = ModelPackage.ENTITY__GUID;

	/**
	 * The feature id for the '<em><b>Ontology</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__ONTOLOGY = ModelPackage.ENTITY__ONTOLOGY;

	/**
	 * The feature id for the '<em><b>Extended Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__EXTENDED_PROPERTIES = ModelPackage.ENTITY__EXTENDED_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Hidden Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__HIDDEN_PROPERTIES = ModelPackage.ENTITY__HIDDEN_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Transient Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__TRANSIENT_PROPERTIES = ModelPackage.ENTITY__TRANSIENT_PROPERTIES;

	/**
	 * The feature id for the '<em><b>Owner Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__OWNER_PROJECT_NAME = ModelPackage.ENTITY__OWNER_PROJECT_NAME;

	/**
	 * The feature id for the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__PACKAGE_NAME = ModelPackage.ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Content</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__CONTENT = ModelPackage.ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE__EXTENSION = ModelPackage.ENTITY_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int JAVA_RESOURCE_FEATURE_COUNT = ModelPackage.ENTITY_FEATURE_COUNT + 3;


	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.java.JavaSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Source</em>'.
	 * @see com.tibco.cep.designtime.core.model.java.JavaSource
	 * @generated
	 */
	EClass getJavaSource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.java.JavaSource#getPackageName <em>Package Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.java.JavaSource#getPackageName()
	 * @see #getJavaSource()
	 * @generated
	 */
	EAttribute getJavaSource_PackageName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.java.JavaSource#getFullSourceText <em>Full Source Text</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Full Source Text</em>'.
	 * @see com.tibco.cep.designtime.core.model.java.JavaSource#getFullSourceText()
	 * @see #getJavaSource()
	 * @generated
	 */
	EAttribute getJavaSource_FullSourceText();

	/**
	 * Returns the meta object for class '{@link com.tibco.cep.designtime.core.model.java.JavaResource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see com.tibco.cep.designtime.core.model.java.JavaResource
	 * @generated
	 */
	EClass getJavaResource();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.java.JavaResource#getPackageName <em>Package Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package Name</em>'.
	 * @see com.tibco.cep.designtime.core.model.java.JavaResource#getPackageName()
	 * @see #getJavaResource()
	 * @generated
	 */
	EAttribute getJavaResource_PackageName();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.java.JavaResource#getContent <em>Content</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Content</em>'.
	 * @see com.tibco.cep.designtime.core.model.java.JavaResource#getContent()
	 * @see #getJavaResource()
	 * @generated
	 */
	EAttribute getJavaResource_Content();

	/**
	 * Returns the meta object for the attribute '{@link com.tibco.cep.designtime.core.model.java.JavaResource#getExtension <em>Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Extension</em>'.
	 * @see com.tibco.cep.designtime.core.model.java.JavaResource#getExtension()
	 * @see #getJavaResource()
	 * @generated
	 */
	EAttribute getJavaResource_Extension();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	JavaFactory getJavaFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.java.impl.JavaSourceImpl <em>Source</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.java.impl.JavaSourceImpl
		 * @see com.tibco.cep.designtime.core.model.java.impl.JavaPackageImpl#getJavaSource()
		 * @generated
		 */
		EClass JAVA_SOURCE = eINSTANCE.getJavaSource();
		/**
		 * The meta object literal for the '<em><b>Package Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_SOURCE__PACKAGE_NAME = eINSTANCE.getJavaSource_PackageName();
		/**
		 * The meta object literal for the '<em><b>Full Source Text</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_SOURCE__FULL_SOURCE_TEXT = eINSTANCE.getJavaSource_FullSourceText();
		/**
		 * The meta object literal for the '{@link com.tibco.cep.designtime.core.model.java.impl.JavaResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.tibco.cep.designtime.core.model.java.impl.JavaResourceImpl
		 * @see com.tibco.cep.designtime.core.model.java.impl.JavaPackageImpl#getJavaResource()
		 * @generated
		 */
		EClass JAVA_RESOURCE = eINSTANCE.getJavaResource();
		/**
		 * The meta object literal for the '<em><b>Package Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_RESOURCE__PACKAGE_NAME = eINSTANCE.getJavaResource_PackageName();
		/**
		 * The meta object literal for the '<em><b>Content</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_RESOURCE__CONTENT = eINSTANCE.getJavaResource_Content();
		/**
		 * The meta object literal for the '<em><b>Extension</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute JAVA_RESOURCE__EXTENSION = eINSTANCE.getJavaResource_Extension();

	}

} //JavaPackage
