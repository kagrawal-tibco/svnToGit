/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.studio.core.functions;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Function</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.studio.core.functions.Function#getName <em>Name</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.Function#getClass_ <em>Class</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.Function#getDesc <em>Desc</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.Function#getHelpUrl <em>Help Url</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.Function#isMapper <em>Mapper</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.Function#isAsync <em>Async</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.Function#isTimesensitive <em>Timesensitive</em>}</li>
 *   <li>{@link com.tibco.cep.studio.core.functions.Function#isModify <em>Modify</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getFunction()
 * @model extendedMetaData="name='function' kind='elementOnly'"
 * @generated
 */
public interface Function extends EObject {
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
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getFunction_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.functions.Function#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class</em>' attribute.
	 * @see #setClass(String)
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getFunction_Class()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='element' name='class' namespace='##targetNamespace'"
	 * @generated
	 */
	String getClass_();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.functions.Function#getClass_ <em>Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Class</em>' attribute.
	 * @see #getClass_()
	 * @generated
	 */
	void setClass(String value);

	/**
	 * Returns the value of the '<em><b>Desc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Desc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Desc</em>' attribute.
	 * @see #setDesc(String)
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getFunction_Desc()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='desc' namespace='##targetNamespace'"
	 * @generated
	 */
	String getDesc();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.functions.Function#getDesc <em>Desc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Desc</em>' attribute.
	 * @see #getDesc()
	 * @generated
	 */
	void setDesc(String value);

	/**
	 * Returns the value of the '<em><b>Help Url</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Help Url</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Help Url</em>' attribute.
	 * @see #setHelpUrl(String)
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getFunction_HelpUrl()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='element' name='helpUrl' namespace='##targetNamespace'"
	 * @generated
	 */
	String getHelpUrl();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.functions.Function#getHelpUrl <em>Help Url</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Help Url</em>' attribute.
	 * @see #getHelpUrl()
	 * @generated
	 */
	void setHelpUrl(String value);

	/**
	 * Returns the value of the '<em><b>Mapper</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mapper</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mapper</em>' attribute.
	 * @see #isSetMapper()
	 * @see #unsetMapper()
	 * @see #setMapper(boolean)
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getFunction_Mapper()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='element' name='mapper' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isMapper();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.functions.Function#isMapper <em>Mapper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mapper</em>' attribute.
	 * @see #isSetMapper()
	 * @see #unsetMapper()
	 * @see #isMapper()
	 * @generated
	 */
	void setMapper(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.core.functions.Function#isMapper <em>Mapper</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMapper()
	 * @see #isMapper()
	 * @see #setMapper(boolean)
	 * @generated
	 */
	void unsetMapper();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.core.functions.Function#isMapper <em>Mapper</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Mapper</em>' attribute is set.
	 * @see #unsetMapper()
	 * @see #isMapper()
	 * @see #setMapper(boolean)
	 * @generated
	 */
	boolean isSetMapper();

	/**
	 * Returns the value of the '<em><b>Async</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Async</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Async</em>' attribute.
	 * @see #isSetAsync()
	 * @see #unsetAsync()
	 * @see #setAsync(boolean)
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getFunction_Async()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='element' name='async' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isAsync();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.functions.Function#isAsync <em>Async</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Async</em>' attribute.
	 * @see #isSetAsync()
	 * @see #unsetAsync()
	 * @see #isAsync()
	 * @generated
	 */
	void setAsync(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.core.functions.Function#isAsync <em>Async</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAsync()
	 * @see #isAsync()
	 * @see #setAsync(boolean)
	 * @generated
	 */
	void unsetAsync();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.core.functions.Function#isAsync <em>Async</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Async</em>' attribute is set.
	 * @see #unsetAsync()
	 * @see #isAsync()
	 * @see #setAsync(boolean)
	 * @generated
	 */
	boolean isSetAsync();

	/**
	 * Returns the value of the '<em><b>Timesensitive</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Timesensitive</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Timesensitive</em>' attribute.
	 * @see #isSetTimesensitive()
	 * @see #unsetTimesensitive()
	 * @see #setTimesensitive(boolean)
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getFunction_Timesensitive()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='element' name='timesensitive' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isTimesensitive();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.functions.Function#isTimesensitive <em>Timesensitive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Timesensitive</em>' attribute.
	 * @see #isSetTimesensitive()
	 * @see #unsetTimesensitive()
	 * @see #isTimesensitive()
	 * @generated
	 */
	void setTimesensitive(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.core.functions.Function#isTimesensitive <em>Timesensitive</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTimesensitive()
	 * @see #isTimesensitive()
	 * @see #setTimesensitive(boolean)
	 * @generated
	 */
	void unsetTimesensitive();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.core.functions.Function#isTimesensitive <em>Timesensitive</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Timesensitive</em>' attribute is set.
	 * @see #unsetTimesensitive()
	 * @see #isTimesensitive()
	 * @see #setTimesensitive(boolean)
	 * @generated
	 */
	boolean isSetTimesensitive();

	/**
	 * Returns the value of the '<em><b>Modify</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Modify</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Modify</em>' attribute.
	 * @see #isSetModify()
	 * @see #unsetModify()
	 * @see #setModify(boolean)
	 * @see com.tibco.cep.studio.core.functions.FunctionsPackage#getFunction_Modify()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='element' name='modify' namespace='##targetNamespace'"
	 * @generated
	 */
	boolean isModify();

	/**
	 * Sets the value of the '{@link com.tibco.cep.studio.core.functions.Function#isModify <em>Modify</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Modify</em>' attribute.
	 * @see #isSetModify()
	 * @see #unsetModify()
	 * @see #isModify()
	 * @generated
	 */
	void setModify(boolean value);

	/**
	 * Unsets the value of the '{@link com.tibco.cep.studio.core.functions.Function#isModify <em>Modify</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetModify()
	 * @see #isModify()
	 * @see #setModify(boolean)
	 * @generated
	 */
	void unsetModify();

	/**
	 * Returns whether the value of the '{@link com.tibco.cep.studio.core.functions.Function#isModify <em>Modify</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Modify</em>' attribute is set.
	 * @see #unsetModify()
	 * @see #isModify()
	 * @see #setModify(boolean)
	 * @generated
	 */
	boolean isSetModify();

} // Function
