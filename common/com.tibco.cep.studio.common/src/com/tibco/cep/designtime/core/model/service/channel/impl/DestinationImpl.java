/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_JMS;
import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_LOCAL;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import com.tibco.cep.designtime.core.model.Entity;
import com.tibco.cep.designtime.core.model.PropertyMap;
import com.tibco.cep.designtime.core.model.SimpleProperty;
import com.tibco.cep.designtime.core.model.impl.EntityImpl;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;
import com.tibco.cep.studio.core.index.model.ELEMENT_TYPES;
import com.tibco.cep.studio.core.index.utils.CommonIndexUtils;


/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Destination</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationImpl#getEventURI <em>Event URI</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationImpl#getSerializerDeserializerClass <em>Serializer Deserializer Class</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationImpl#getProperties <em>Properties</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationImpl#getDriverConfig <em>Driver Config</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationImpl#isInputEnabled <em>Input Enabled</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.DestinationImpl#isOutputEnabled <em>Output Enabled</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DestinationImpl extends EntityImpl implements Destination {
	/**
	 * The default value of the '{@link #getEventURI() <em>Event URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventURI()
	 * @generated
	 * @ordered
	 */
	protected static final String EVENT_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEventURI() <em>Event URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEventURI()
	 * @generated
	 * @ordered
	 */
	protected String eventURI = EVENT_URI_EDEFAULT;

	/**
	 * The default value of the '{@link #getSerializerDeserializerClass() <em>Serializer Deserializer Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializerDeserializerClass()
	 * @generated
	 * @ordered
	 */
	protected static final String SERIALIZER_DESERIALIZER_CLASS_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSerializerDeserializerClass() <em>Serializer Deserializer Class</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSerializerDeserializerClass()
	 * @generated
	 * @ordered
	 */
	protected String serializerDeserializerClass = SERIALIZER_DESERIALIZER_CLASS_EDEFAULT;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	protected PropertyMap properties;

	/**
	 * The cached value of the '{@link #getDriverConfig() <em>Driver Config</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDriverConfig()
	 * @generated
	 * @ordered
	 */
	protected DriverConfig driverConfig;

	/**
	 * The default value of the '{@link #isInputEnabled() <em>Input Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInputEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean INPUT_ENABLED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isInputEnabled() <em>Input Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isInputEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean inputEnabled = INPUT_ENABLED_EDEFAULT;

	/**
	 * The default value of the '{@link #isOutputEnabled() <em>Output Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOutputEnabled()
	 * @generated
	 * @ordered
	 */
	protected static final boolean OUTPUT_ENABLED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isOutputEnabled() <em>Output Enabled</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isOutputEnabled()
	 * @generated
	 * @ordered
	 */
	protected boolean outputEnabled = OUTPUT_ENABLED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DestinationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.DESTINATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEventURI() {
		return eventURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFolder() {
		if (folder == null) {
			Channel channel = getDriverConfig() != null ? getDriverConfig().getChannel() : null;
			if (channel != null) {
				folder = channel.getFullPath()+"/";
			}
		}
		return folder;
	}
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventURI(String newEventURI) {
		String oldEventURI = eventURI;
		eventURI = newEventURI;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DESTINATION__EVENT_URI, oldEventURI, eventURI));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSerializerDeserializerClass() {
		return serializerDeserializerClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSerializerDeserializerClass(String newSerializerDeserializerClass) {
		String oldSerializerDeserializerClass = serializerDeserializerClass;
		serializerDeserializerClass = newSerializerDeserializerClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DESTINATION__SERIALIZER_DESERIALIZER_CLASS, oldSerializerDeserializerClass, serializerDeserializerClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyMap getProperties() {
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProperties(PropertyMap newProperties, NotificationChain msgs) {
		PropertyMap oldProperties = properties;
		properties = newProperties;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ChannelPackage.DESTINATION__PROPERTIES, oldProperties, newProperties);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProperties(PropertyMap newProperties) {
		if (newProperties != properties) {
			NotificationChain msgs = null;
			if (properties != null)
				msgs = ((InternalEObject)properties).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.DESTINATION__PROPERTIES, null, msgs);
			if (newProperties != null)
				msgs = ((InternalEObject)newProperties).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.DESTINATION__PROPERTIES, null, msgs);
			msgs = basicSetProperties(newProperties, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DESTINATION__PROPERTIES, newProperties, newProperties));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DriverConfig getDriverConfig() {
		if (driverConfig != null && driverConfig.eIsProxy()) {
			InternalEObject oldDriverConfig = (InternalEObject)driverConfig;
			driverConfig = (DriverConfig)eResolveProxy(oldDriverConfig);
			if (driverConfig != oldDriverConfig) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelPackage.DESTINATION__DRIVER_CONFIG, oldDriverConfig, driverConfig));
			}
		}
		return driverConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DriverConfig basicGetDriverConfig() {
		return driverConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDriverConfig(DriverConfig newDriverConfig) {
		DriverConfig oldDriverConfig = driverConfig;
		driverConfig = newDriverConfig;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DESTINATION__DRIVER_CONFIG, oldDriverConfig, driverConfig));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isInputEnabled() {
		return inputEnabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInputEnabled(boolean newInputEnabled) {
		boolean oldInputEnabled = inputEnabled;
		inputEnabled = newInputEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DESTINATION__INPUT_ENABLED, oldInputEnabled, inputEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isOutputEnabled() {
		return outputEnabled;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputEnabled(boolean newOutputEnabled) {
		boolean oldOutputEnabled = outputEnabled;
		outputEnabled = newOutputEnabled;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.DESTINATION__OUTPUT_ENABLED, oldOutputEnabled, outputEnabled));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<ModelError> getModelErrors() {
		EList<ModelError> errorList = new BasicEList<ModelError>();
		DriverConfig dc = this.getDriverConfig();
		if (dc != null && dc.getChannel() != null) {
			PropertyMap instanceTemp = this.getProperties();
			Boolean isPageFlow = false ;
			for (Entity property : instanceTemp.getProperties()) {
				if (property instanceof SimpleProperty) {
					SimpleProperty simpleProperty = (SimpleProperty) property;
					if (simpleProperty.getName().equals("be.http.type")) {
						isPageFlow = simpleProperty.getValue().equals("PAGEFLOW") ? true : false;
					}
				}
			}
			Channel channel = dc.getChannel();
			String channelName = channel.getName();
			String eventURI = this.getEventURI();
			List<Object> args = new ArrayList<Object>();
			ModelError me = null;
			if (!isPageFlow) {
				if ((eventURI == null || eventURI.trim().length() == 0)) {
					if (!DRIVER_LOCAL.equals(dc.getDriverType().getName())) {
						args.add(this.getName());
						args.add(channelName);
						me = CommonValidationUtils.constructModelError(this,
								"Destination.error.eventURI.empty.description",
								args, true);
						errorList.add(me);
					}
				} else {
					// check if that Event Resource Exist
					// if event URI does not ends with extension then add
					// extension in the URI to resolve the reference
					String eventExt = CommonIndexUtils
							.getFileExtension(ELEMENT_TYPES.SIMPLE_EVENT);
					// if (!eventURI.endsWith(eventExt)) {
					eventURI = eventURI + "." + eventExt;
					// }
					// @TODO
					// event.getTransientProperty(SharedViewModel.Object.Prop.RESOURCE_POINTER)
					// check .
					if (!CommonValidationUtils.resolveReference(eventURI,
							ownerProjectName)) {
						args.clear();
						args.add(this.getName());
						args.add(channelName);
						args.add(eventURI);
						me = CommonValidationUtils
								.constructModelError(
										this,
										"Destination.error.eventURI.invalid.description",
										args, true);
						errorList.add(me);
					}
				}
			}
			// property Configuration
			PropertyMap instance = this.getProperties();
			if (instance == null) {
				// there is no property configured
				args.clear();
				args.add(getName());
				args.add(channelName);
				me = CommonValidationUtils.constructModelError(this, "Destination.errors.propertyNotConfigured", args, false);
				errorList.add(me);
				
			} else {
				if (instance.getProperties().size() > 0) {
					boolean isQueue = true; 
					for (int i = 0; i < instance.getProperties().size(); i++) {
						Entity entity = instance.getProperties().get(i);
						String propName = entity.getName();
						
						if (entity instanceof SimpleProperty) {
							SimpleProperty simpleProperty = (SimpleProperty)entity;
							if (DRIVER_JMS.equals(dc.getDriverType().getName())) {
								if (simpleProperty.getName().equals("Queue")) {
									isQueue = Boolean.parseBoolean(simpleProperty.getValue());	
									String name = isNameEmpty(isQueue);
									if (!name.equals("")) {
										args.clear();
										args.add(getName());
										args.add(channelName);
										args.add(name);
										me = CommonValidationUtils.constructModelError(this, "Destination.errors.propertyNotConfigured", args, true);
										errorList.add(me);
									}
								}
							}
							if (!simpleProperty.getName().equals("Name") && simpleProperty.isMandatory()) {
								if (simpleProperty.getValue() == null) {
									args.clear();
									args.add(getName());
									args.add(channelName);
									args.add(propName);
									me = CommonValidationUtils.constructModelError(this, "Destination.errors.propertyNotConfigured", args, true);
									errorList.add(me);
								}
								if (simpleProperty.getValue() != null && simpleProperty.getValue().trim().equals("")) {
									args.clear();
									args.add(getName());
									args.add(channelName);
									args.add(propName);
									me = CommonValidationUtils.constructModelError(this, "Destination.errors.propertyNotConfigured", args, true);
									errorList.add(me);
								}
							}
							if (isPageFlow) {
								if (simpleProperty.getName().equals("be.http.pageFlowFunction")) {
									String rfExt = CommonIndexUtils.getFileExtension(ELEMENT_TYPES.RULE_FUNCTION);
									String rf = simpleProperty.getValue() + "." + rfExt;
									if (!CommonValidationUtils.resolveReference(rf, ownerProjectName)) {
										args.clear();
										args.add(getName());
										args.add(channelName);
										args.add(rf);
										me = CommonValidationUtils.constructModelError(this,
												"Destination.error.rulefunction.invalid.description", args, true);
										errorList.add(me);
									}
								}
							}
						}
						}
						
				}
			}
		}
		
		return errorList;
	}
	
	/**
	 * @param queue
	 * 
	 * @return
	 * @generated NOT
	 */
	private String isNameEmpty(boolean queue) {
		PropertyMap instance = this.getProperties();
		if (instance != null) {
			for (int i = 0; i < instance.getProperties().size(); i++) {
				Entity entity = instance.getProperties().get(i);
				String propName = entity.getName();
				if (entity instanceof SimpleProperty) {
					SimpleProperty simpleProperty = (SimpleProperty)entity;
					if(simpleProperty.getName().equals("Name")) {
						if (simpleProperty.getValue() == null) {
							return getQueueOrTopicName(queue, propName);
						}
						if (simpleProperty.getValue() != null && simpleProperty.getValue().trim().equals("")) {
							return getQueueOrTopicName(queue, propName);
						}
					}
				}
			}
		}
		return "";
	}
	
	/**
	 * @param queue
	 * @param propName
	 * @return
	 * @generated NOT
	 */
	private String getQueueOrTopicName(boolean queue, String propName) {
		if (queue) {
			return "Queue: " + propName;
		} else {
			return "Topic: " + propName;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ChannelPackage.DESTINATION__PROPERTIES:
				return basicSetProperties(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ChannelPackage.DESTINATION__EVENT_URI:
				return getEventURI();
			case ChannelPackage.DESTINATION__SERIALIZER_DESERIALIZER_CLASS:
				return getSerializerDeserializerClass();
			case ChannelPackage.DESTINATION__PROPERTIES:
				return getProperties();
			case ChannelPackage.DESTINATION__DRIVER_CONFIG:
				if (resolve) return getDriverConfig();
				return basicGetDriverConfig();
			case ChannelPackage.DESTINATION__INPUT_ENABLED:
				return isInputEnabled();
			case ChannelPackage.DESTINATION__OUTPUT_ENABLED:
				return isOutputEnabled();
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
			case ChannelPackage.DESTINATION__EVENT_URI:
				setEventURI((String)newValue);
				return;
			case ChannelPackage.DESTINATION__SERIALIZER_DESERIALIZER_CLASS:
				setSerializerDeserializerClass((String)newValue);
				return;
			case ChannelPackage.DESTINATION__PROPERTIES:
				setProperties((PropertyMap)newValue);
				return;
			case ChannelPackage.DESTINATION__DRIVER_CONFIG:
				setDriverConfig((DriverConfig)newValue);
				return;
			case ChannelPackage.DESTINATION__INPUT_ENABLED:
				setInputEnabled((Boolean)newValue);
				return;
			case ChannelPackage.DESTINATION__OUTPUT_ENABLED:
				setOutputEnabled((Boolean)newValue);
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
			case ChannelPackage.DESTINATION__EVENT_URI:
				setEventURI(EVENT_URI_EDEFAULT);
				return;
			case ChannelPackage.DESTINATION__SERIALIZER_DESERIALIZER_CLASS:
				setSerializerDeserializerClass(SERIALIZER_DESERIALIZER_CLASS_EDEFAULT);
				return;
			case ChannelPackage.DESTINATION__PROPERTIES:
				setProperties((PropertyMap)null);
				return;
			case ChannelPackage.DESTINATION__DRIVER_CONFIG:
				setDriverConfig((DriverConfig)null);
				return;
			case ChannelPackage.DESTINATION__INPUT_ENABLED:
				setInputEnabled(INPUT_ENABLED_EDEFAULT);
				return;
			case ChannelPackage.DESTINATION__OUTPUT_ENABLED:
				setOutputEnabled(OUTPUT_ENABLED_EDEFAULT);
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
			case ChannelPackage.DESTINATION__EVENT_URI:
				return EVENT_URI_EDEFAULT == null ? eventURI != null : !EVENT_URI_EDEFAULT.equals(eventURI);
			case ChannelPackage.DESTINATION__SERIALIZER_DESERIALIZER_CLASS:
				return SERIALIZER_DESERIALIZER_CLASS_EDEFAULT == null ? serializerDeserializerClass != null : !SERIALIZER_DESERIALIZER_CLASS_EDEFAULT.equals(serializerDeserializerClass);
			case ChannelPackage.DESTINATION__PROPERTIES:
				return properties != null;
			case ChannelPackage.DESTINATION__DRIVER_CONFIG:
				return driverConfig != null;
			case ChannelPackage.DESTINATION__INPUT_ENABLED:
				return inputEnabled != INPUT_ENABLED_EDEFAULT;
			case ChannelPackage.DESTINATION__OUTPUT_ENABLED:
				return outputEnabled != OUTPUT_ENABLED_EDEFAULT;
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
		result.append(" (eventURI: ");
		result.append(eventURI);
		result.append(", serializerDeserializerClass: ");
		result.append(serializerDeserializerClass);
		result.append(", inputEnabled: ");
		result.append(inputEnabled);
		result.append(", outputEnabled: ");
		result.append(outputEnabled);
		result.append(')');
		return result.toString();
	}

} //DestinationImpl
