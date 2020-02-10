/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.decision.table.model.dtmodel;

import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.decisionproject.ontology.Property;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Column</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Column#getId <em>Id</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Column#isSubstitution <em>Substitution</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Column#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyPath <em>Property Path</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyType <em>Property Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Column#getColumnType <em>Column Type</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyRef <em>Property Ref</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Column#getAlias <em>Alias</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Column#isArrayProperty <em>Array Property</em>}</li>
 *   <li>{@link com.tibco.cep.decision.table.model.dtmodel.Column#getDefaultCellText <em>Default Cell Text</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumn()
 * @model
 * @generated
 */
public interface Column extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumn_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Substitution</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Substitution</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Substitution</em>' attribute.
	 * @see #setSubstitution(boolean)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumn_Substitution()
	 * @model required="true"
	 * @generated
	 */
	boolean isSubstitution();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Column#isSubstitution <em>Substitution</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Substitution</em>' attribute.
	 * @see #isSubstitution()
	 * @generated
	 */
	void setSubstitution(boolean value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumn_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Property Path</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Path</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Path</em>' attribute.
	 * @see #setPropertyPath(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumn_PropertyPath()
	 * @model
	 * @generated
	 */
	String getPropertyPath();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyPath <em>Property Path</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Path</em>' attribute.
	 * @see #getPropertyPath()
	 * @generated
	 */
	void setPropertyPath(String value);

	/**
	 * Returns the value of the '<em><b>Property Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Type</em>' attribute.
	 * @see #setPropertyType(int)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumn_PropertyType()
	 * @model
	 * @generated
	 */
	int getPropertyType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyType <em>Property Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Type</em>' attribute.
	 * @see #getPropertyType()
	 * @generated
	 */
	void setPropertyType(int value);

	/**
	 * Returns the value of the '<em><b>Column Type</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.decision.table.model.dtmodel.ColumnType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Column Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Column Type</em>' attribute.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ColumnType
	 * @see #setColumnType(ColumnType)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumn_ColumnType()
	 * @model required="true"
	 * @generated
	 */
	ColumnType getColumnType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getColumnType <em>Column Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Column Type</em>' attribute.
	 * @see com.tibco.cep.decision.table.model.dtmodel.ColumnType
	 * @see #getColumnType()
	 * @generated
	 */
	void setColumnType(ColumnType value);

	/**
	 * Returns the value of the '<em><b>Property Ref</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Property Ref</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Property Ref</em>' reference.
	 * @see #setPropertyRef(Property)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumn_PropertyRef()
	 * @model transient="true"
	 * @generated
	 */
	Property getPropertyRef();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getPropertyRef <em>Property Ref</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Property Ref</em>' reference.
	 * @see #getPropertyRef()
	 * @generated
	 */
	void setPropertyRef(Property value);

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
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumn_Alias()
	 * @model
	 * @generated
	 */
	String getAlias();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getAlias <em>Alias</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alias</em>' attribute.
	 * @see #getAlias()
	 * @generated
	 */
	void setAlias(String value);

	/**
	 * Returns the value of the '<em><b>Array Property</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array Property</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Property</em>' attribute.
	 * @see #setArrayProperty(boolean)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumn_ArrayProperty()
	 * @model default=""
	 * @generated
	 */
	boolean isArrayProperty();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Column#isArrayProperty <em>Array Property</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array Property</em>' attribute.
	 * @see #isArrayProperty()
	 * @generated
	 */
	void setArrayProperty(boolean value);

	/**
	 * Returns the value of the '<em><b>Default Cell Text</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default Cell Text</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default Cell Text</em>' attribute.
	 * @see #setDefaultCellText(String)
	 * @see com.tibco.cep.decision.table.model.dtmodel.DtmodelPackage#getColumn_DefaultCellText()
	 * @model
	 * @generated
	 */
	String getDefaultCellText();

	/**
	 * Sets the value of the '{@link com.tibco.cep.decision.table.model.dtmodel.Column#getDefaultCellText <em>Default Cell Text</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default Cell Text</em>' attribute.
	 * @see #getDefaultCellText()
	 * @generated
	 */
	void setDefaultCellText(String value);

} // Column
