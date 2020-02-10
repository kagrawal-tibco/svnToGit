/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.be.util.config.cdd;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mm Send Email Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getCc <em>Cc</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getMsg <em>Msg</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getSubject <em>Subject</em>}</li>
 *   <li>{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.be.util.config.cdd.CddPackage#getMmSendEmailConfig()
 * @model extendedMetaData="name='mm-send-email-type' kind='empty'"
 * @generated
 */
public interface MmSendEmailConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Cc</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cc</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cc</em>' attribute.
	 * @see #setCc(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmSendEmailConfig_Cc()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='cc'"
	 * @generated
	 */
	String getCc();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getCc <em>Cc</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cc</em>' attribute.
	 * @see #getCc()
	 * @generated
	 */
	void setCc(String value);

	/**
	 * Returns the value of the '<em><b>Msg</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Msg</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Msg</em>' attribute.
	 * @see #setMsg(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmSendEmailConfig_Msg()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='msg'"
	 * @generated
	 */
	String getMsg();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getMsg <em>Msg</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Msg</em>' attribute.
	 * @see #getMsg()
	 * @generated
	 */
	void setMsg(String value);

	/**
	 * Returns the value of the '<em><b>Subject</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Subject</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Subject</em>' attribute.
	 * @see #setSubject(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmSendEmailConfig_Subject()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='subject'"
	 * @generated
	 */
	String getSubject();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getSubject <em>Subject</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Subject</em>' attribute.
	 * @see #getSubject()
	 * @generated
	 */
	void setSubject(String value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' attribute.
	 * @see #setTo(String)
	 * @see com.tibco.be.util.config.cdd.CddPackage#getMmSendEmailConfig_To()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='to'"
	 * @generated
	 */
	String getTo();

	/**
	 * Sets the value of the '{@link com.tibco.be.util.config.cdd.MmSendEmailConfig#getTo <em>To</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' attribute.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(String value);

} // MmSendEmailConfig
