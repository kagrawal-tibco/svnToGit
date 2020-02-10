/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import com.tibco.cep.designtime.core.model.PropertyMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Driver Config</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getConfigMethod <em>Config Method</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getReference <em>Reference</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getLabel <em>Label</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getChannel <em>Channel</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getDestinations <em>Destinations</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getExtendedConfiguration <em>Extended Configuration</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getDriverType <em>Driver Type</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getChoice <em>Choice</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverConfig()
 * @model
 * @generated
 */
public interface DriverConfig extends EObject {
	/**
	 * Returns the value of the '<em><b>Driver Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Driver Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Driver Type</em>' containment reference.
	 * @see #setDriverType(DRIVER_TYPE)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverConfig_DriverType()
	 * @model containment="true"
	 * @generated
	 */
	DRIVER_TYPE getDriverType();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getDriverType <em>Driver Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Driver Type</em>' containment reference.
	 * @see #getDriverType()
	 * @generated
	 */
	void setDriverType(DRIVER_TYPE value);

	/**
	 * Returns the value of the '<em><b>Choice</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Choice</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Choice</em>' containment reference.
	 * @see #setChoice(Choice)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverConfig_Choice()
	 * @model containment="true"
	 * @generated
	 */
	Choice getChoice();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getChoice <em>Choice</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Choice</em>' containment reference.
	 * @see #getChoice()
	 * @generated
	 */
	void setChoice(Choice value);

	/**
	 * Returns the value of the '<em><b>Config Method</b></em>' attribute.
	 * The literals are from the enumeration {@link com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Config Method</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Config Method</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD
	 * @see #setConfigMethod(CONFIG_METHOD)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverConfig_ConfigMethod()
	 * @model
	 * @generated
	 */
	CONFIG_METHOD getConfigMethod();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getConfigMethod <em>Config Method</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Config Method</em>' attribute.
	 * @see com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD
	 * @see #getConfigMethod()
	 * @generated
	 */
	void setConfigMethod(CONFIG_METHOD value);

	/**
	 * Returns the value of the '<em><b>Reference</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Reference</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Reference</em>' attribute.
	 * @see #setReference(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverConfig_Reference()
	 * @model
	 * @generated
	 */
	String getReference();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getReference <em>Reference</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Reference</em>' attribute.
	 * @see #getReference()
	 * @generated
	 */
	void setReference(String value);

	/**
	 * Returns the value of the '<em><b>Label</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Label</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Label</em>' attribute.
	 * @see #setLabel(String)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverConfig_Label()
	 * @model
	 * @generated
	 */
	String getLabel();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getLabel <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Label</em>' attribute.
	 * @see #getLabel()
	 * @generated
	 */
	void setLabel(String value);

	/**
	 * Returns the value of the '<em><b>Channel</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Channel</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Channel</em>' reference.
	 * @see #setChannel(Channel)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverConfig_Channel()
	 * @model
	 * @generated
	 */
	Channel getChannel();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getChannel <em>Channel</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Channel</em>' reference.
	 * @see #getChannel()
	 * @generated
	 */
	void setChannel(Channel value);

	/**
	 * Returns the value of the '<em><b>Properties</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Properties</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Properties</em>' containment reference.
	 * @see #setProperties(PropertyMap)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverConfig_Properties()
	 * @model containment="true"
	 * @generated
	 */
	PropertyMap getProperties();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getProperties <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Properties</em>' containment reference.
	 * @see #getProperties()
	 * @generated
	 */
	void setProperties(PropertyMap value);

	/**
	 * Returns the value of the '<em><b>Destinations</b></em>' containment reference list.
	 * The list contents are of type {@link com.tibco.cep.designtime.core.model.service.channel.Destination}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Destinations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Destinations</em>' containment reference list.
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverConfig_Destinations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Destination> getDestinations();

	/**
	 * Returns the value of the '<em><b>Extended Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extended Configuration</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extended Configuration</em>' containment reference.
	 * @see #setExtendedConfiguration(ExtendedConfiguration)
	 * @see com.tibco.cep.designtime.core.model.service.channel.ChannelPackage#getDriverConfig_ExtendedConfiguration()
	 * @model containment="true"
	 * @generated
	 */
	ExtendedConfiguration getExtendedConfiguration();

	/**
	 * Sets the value of the '{@link com.tibco.cep.designtime.core.model.service.channel.DriverConfig#getExtendedConfiguration <em>Extended Configuration</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Extended Configuration</em>' containment reference.
	 * @see #getExtendedConfiguration()
	 * @generated
	 */
	void setExtendedConfiguration(ExtendedConfiguration value);

} // DriverConfig
