/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Argument Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getPath <em>Path</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getType <em>Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getGraphicalPath <em>Graphical Path</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getResourceType <em>Resource Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#isDomainOverridden <em>Domain Overridden</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#isArray <em>Array</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getArgumentProperty()
 * @model
 * @generated
 */
public interface ArgumentProperty extends EObject {
	/**
	 * Returns the value of the '<em><b>Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Path</em>' attribute.
	 * @see #setPath(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getArgumentProperty_Path()
	 * @model
	 * @generated
	 */
	String getPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getPath <em>Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Path</em>' attribute.
	 * @see #getPath()
	 * @generated
	 */
	void setPath(String value);

	/**
	 * Returns the value of the '<em><b>Alias</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alias</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alias</em>' attribute.
	 * @see #setAlias(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getArgumentProperty_Alias()
	 * @model
	 * @generated
	 */
	String getAlias();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getAlias <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alias</em>' attribute.
	 * @see #getAlias()
	 * @generated
	 */
	void setAlias(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see #setType(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getArgumentProperty_Type()
	 * @model
	 * @generated
	 */
	String getType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see #getType()
	 * @generated
	 */
	void setType(String value);

	/**
	 * Returns the value of the '<em><b>Graphical Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Graphical Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Graphical Path</em>' attribute.
	 * @see #setGraphicalPath(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getArgumentProperty_GraphicalPath()
	 * @model
	 * @generated
	 */
	String getGraphicalPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getGraphicalPath <em>Graphical Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Graphical Path</em>' attribute.
	 * @see #getGraphicalPath()
	 * @generated
	 */
	void setGraphicalPath(String value);

	/**
	 * Returns the value of the '<em><b>Resource Type</b></em>' attribute.
	 * The default value is <code>"UNDEFINED"</code>.
	 * The literals are from the enumeration {@link com.tibco.cep.decision.table.model.dtmodel.ResourceType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resource Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resource Type</em>' attribute.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ResourceType
	 * @see #setResourceType(ResourceType)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getArgumentProperty_ResourceType()
	 * @model default="UNDEFINED"
	 * @generated
	 */
	ResourceType getResourceType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#getResourceType <em>Resource Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Resource Type</em>' attribute.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ResourceType
	 * @see #getResourceType()
	 * @generated
	 */
	void setResourceType(ResourceType value);

	/**
	 * Returns the value of the '<em><b>Domain Overridden</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain Overridden</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain Overridden</em>' attribute.
	 * @see #setDomainOverridden(boolean)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getArgumentProperty_DomainOverridden()
	 * @model
	 * @generated
	 */
	boolean isDomainOverridden();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#isDomainOverridden <em>Domain Overridden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain Overridden</em>' attribute.
	 * @see #isDomainOverridden()
	 * @generated
	 */
	void setDomainOverridden(boolean value);

	/**
	 * Returns the value of the '<em><b>Array</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array</em>' attribute.
	 * @see #setArray(boolean)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getArgumentProperty_Array()
	 * @model default="false"
	 * @generated
	 */
	boolean isArray();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.ArgumentProperty#isArray <em>Array</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array</em>' attribute.
	 * @see #isArray()
	 * @generated
	 */
	void setArray(boolean value);

} // ArgumentProperty
