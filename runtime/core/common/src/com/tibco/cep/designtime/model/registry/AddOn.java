/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.model.registry;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Add On</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getIndexResourceProviderClass <em>Index Resource Provider Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getIndexCacheClass <em>Index Cache Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getOntologyAdapterClass <em>Ontology Adapter Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getAdapterFactoryClass <em>Adapter Factory Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getTnsEntityHandlerClass <em>Tns Entity Handler Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getCddAgentClass <em>Cdd Agent Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getSupportedEntityTypes <em>Supported Entity Types</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getSupportedExtensions <em>Supported Extensions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getFileExtensionMap <em>File Extension Map</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getTnsEntityExtensions <em>Tns Entity Extensions</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.model.registry.AddOn#getFunctionCatalogProvider <em>Function Catalog Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn()
 * @model extendedMetaData="name='addon' kind='elementOnly'"
 * @generated
 */
public interface AddOn extends EObject {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.model.registry.AddOnType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.model.registry.AddOnType
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #setType(AddOnType)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_Type()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='element' name='type'"
	 * @generated
	 */
	AddOnType getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see com.tibco.cep.designtime.model.registry.AddOnType
	 * @see #isSetType()
	 * @see #unsetType()
	 * @see #getType()
	 * @generated
	 */
	void setType(AddOnType value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetType()
	 * @see #getType()
	 * @see #setType(AddOnType)
	 * @generated
	 */
	void unsetType();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getType <em>Type</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Type</em>' attribute is set.
	 * @see #unsetType()
	 * @see #getType()
	 * @see #setType(AddOnType)
	 * @generated
	 */
	boolean isSetType();

	/**
	 * Returns the value of the '<em><b>Index Resource Provider Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index Resource Provider Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index Resource Provider Class</em>' attribute.
	 * @see #setIndexResourceProviderClass(String)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_IndexResourceProviderClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='indexResourceProviderClass'"
	 * @generated
	 */
	String getIndexResourceProviderClass();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getIndexResourceProviderClass <em>Index Resource Provider Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index Resource Provider Class</em>' attribute.
	 * @see #getIndexResourceProviderClass()
	 * @generated
	 */
	void setIndexResourceProviderClass(String value);

	/**
	 * Returns the value of the '<em><b>Index Cache Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Index Cache Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Index Cache Class</em>' attribute.
	 * @see #setIndexCacheClass(String)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_IndexCacheClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='indexCacheClass'"
	 * @generated
	 */
	String getIndexCacheClass();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getIndexCacheClass <em>Index Cache Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Index Cache Class</em>' attribute.
	 * @see #getIndexCacheClass()
	 * @generated
	 */
	void setIndexCacheClass(String value);

	/**
	 * Returns the value of the '<em><b>Ontology Adapter Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ontology Adapter Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ontology Adapter Class</em>' attribute.
	 * @see #setOntologyAdapterClass(String)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_OntologyAdapterClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='ontologyAdapterClass'"
	 * @generated
	 */
	String getOntologyAdapterClass();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getOntologyAdapterClass <em>Ontology Adapter Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ontology Adapter Class</em>' attribute.
	 * @see #getOntologyAdapterClass()
	 * @generated
	 */
	void setOntologyAdapterClass(String value);

	/**
	 * Returns the value of the '<em><b>Adapter Factory Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Adapter Factory Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Adapter Factory Class</em>' attribute.
	 * @see #setAdapterFactoryClass(String)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_AdapterFactoryClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='adapterFactoryClass'"
	 * @generated
	 */
	String getAdapterFactoryClass();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getAdapterFactoryClass <em>Adapter Factory Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Adapter Factory Class</em>' attribute.
	 * @see #getAdapterFactoryClass()
	 * @generated
	 */
	void setAdapterFactoryClass(String value);

	/**
	 * Returns the value of the '<em><b>Tns Entity Handler Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tns Entity Handler Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tns Entity Handler Class</em>' attribute.
	 * @see #setTnsEntityHandlerClass(String)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_TnsEntityHandlerClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='tnsEntityHandlerClass'"
	 * @generated
	 */
	String getTnsEntityHandlerClass();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getTnsEntityHandlerClass <em>Tns Entity Handler Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tns Entity Handler Class</em>' attribute.
	 * @see #getTnsEntityHandlerClass()
	 * @generated
	 */
	void setTnsEntityHandlerClass(String value);

	/**
	 * Returns the value of the '<em><b>Cdd Agent Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cdd Agent Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cdd Agent Class</em>' attribute.
	 * @see #setCddAgentClass(String)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_CddAgentClass()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='cddAgentClass'"
	 * @generated
	 */
	String getCddAgentClass();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getCddAgentClass <em>Cdd Agent Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cdd Agent Class</em>' attribute.
	 * @see #getCddAgentClass()
	 * @generated
	 */
	void setCddAgentClass(String value);

	/**
	 * Returns the value of the '<em><b>Supported Entity Types</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Supported Entity Types</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Supported Entity Types</em>' containment reference.
	 * @see #setSupportedEntityTypes(SupportedElementTypes)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_SupportedEntityTypes()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='supportedTypes'"
	 * @generated
	 */
	SupportedElementTypes getSupportedEntityTypes();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getSupportedEntityTypes <em>Supported Entity Types</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Supported Entity Types</em>' containment reference.
	 * @see #getSupportedEntityTypes()
	 * @generated
	 */
	void setSupportedEntityTypes(SupportedElementTypes value);

	/**
	 * Returns the value of the '<em><b>Supported Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Supported Extensions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Supported Extensions</em>' containment reference.
	 * @see #setSupportedExtensions(SupportedExtensions)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_SupportedExtensions()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='supportedExtensions'"
	 * @generated
	 */
	SupportedExtensions getSupportedExtensions();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getSupportedExtensions <em>Supported Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Supported Extensions</em>' containment reference.
	 * @see #getSupportedExtensions()
	 * @generated
	 */
	void setSupportedExtensions(SupportedExtensions value);

	/**
	 * Returns the value of the '<em><b>File Extension Map</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.model.registry.FileExtensionMapType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>File Extension Map</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>File Extension Map</em>' containment reference list.
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_FileExtensionMap()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='fileExtensionMap'"
	 * @generated
	 */
	EList<FileExtensionMapType> getFileExtensionMap();

	/**
	 * Returns the value of the '<em><b>Tns Entity Extensions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tns Entity Extensions</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tns Entity Extensions</em>' containment reference.
	 * @see #setTnsEntityExtensions(TnsEntityExtensions)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_TnsEntityExtensions()
	 * @model containment="true" required="true"
	 *        extendedMetaData="kind='element' name='tnsEntityExtensions'"
	 * @generated
	 */
	TnsEntityExtensions getTnsEntityExtensions();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getTnsEntityExtensions <em>Tns Entity Extensions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tns Entity Extensions</em>' containment reference.
	 * @see #getTnsEntityExtensions()
	 * @generated
	 */
	void setTnsEntityExtensions(TnsEntityExtensions value);

	/**
	 * Returns the value of the '<em><b>Function Catalog Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function Catalog Provider</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function Catalog Provider</em>' attribute.
	 * @see #setFunctionCatalogProvider(String)
	 * @see com.tibco.cep.designtime.model.registry.RegistryPackage#getAddOn_FunctionCatalogProvider()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='functionCatalogProvider'"
	 * @generated
	 */
	String getFunctionCatalogProvider();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.model.registry.AddOn#getFunctionCatalogProvider <em>Function Catalog Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function Catalog Provider</em>' attribute.
	 * @see #getFunctionCatalogProvider()
	 * @generated
	 */
	void setFunctionCatalogProvider(String value);

} // AddOn
