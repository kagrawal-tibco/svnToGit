/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package com.tibco.cep.designtime.core.model.service.channel.impl;

import static com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants.DRIVER_LOCAL;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
import com.tibco.cep.designtime.core.model.impl.SimplePropertyImpl;
import com.tibco.cep.designtime.core.model.service.channel.CONFIG_METHOD;
import com.tibco.cep.designtime.core.model.service.channel.Channel;
import com.tibco.cep.designtime.core.model.service.channel.ChannelPackage;
import com.tibco.cep.designtime.core.model.service.channel.DRIVER_TYPE;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.designtime.core.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.DriverManager;
import com.tibco.cep.designtime.core.model.service.channel.HttpChannelDriverConfig;
import com.tibco.cep.designtime.core.model.service.channel.WebApplicationDescriptor;
import com.tibco.cep.designtime.core.model.service.channel.util.DriverManagerConstants;
import com.tibco.cep.designtime.core.model.validation.ModelError;
import com.tibco.cep.studio.common.validation.utils.CommonValidationUtils;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Channel</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChannelImpl#getDriver <em>Driver</em>}</li>
 *   <li>{@link com.tibco.cep.designtime.core.model.service.channel.impl.ChannelImpl#getDriverManager <em>Driver Manager</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ChannelImpl extends EntityImpl implements Channel {
	/**
	 * The cached value of the '{@link #getDriver() <em>Driver</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDriver()
	 * @generated
	 * @ordered
	 */
	protected DriverConfig driver;

	/**
	 * The cached value of the '{@link #getDriverManager() <em>Driver Manager</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDriverManager()
	 * @generated
	 * @ordered
	 */
	protected DriverManager driverManager;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ChannelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ChannelPackage.Literals.CHANNEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DriverConfig getDriver() {
		return driver;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDriver(DriverConfig newDriver, NotificationChain msgs) {
		DriverConfig oldDriver = driver;
		driver = newDriver;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ChannelPackage.CHANNEL__DRIVER, oldDriver, newDriver);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDriver(DriverConfig newDriver) {
		if (newDriver != driver) {
			NotificationChain msgs = null;
			if (driver != null)
				msgs = ((InternalEObject)driver).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.CHANNEL__DRIVER, null, msgs);
			if (newDriver != null)
				msgs = ((InternalEObject)newDriver).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ChannelPackage.CHANNEL__DRIVER, null, msgs);
			msgs = basicSetDriver(newDriver, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.CHANNEL__DRIVER, newDriver, newDriver));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DriverManager getDriverManager() {
		if (driverManager != null && driverManager.eIsProxy()) {
			InternalEObject oldDriverManager = (InternalEObject)driverManager;
			driverManager = (DriverManager)eResolveProxy(oldDriverManager);
			if (driverManager != oldDriverManager) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ChannelPackage.CHANNEL__DRIVER_MANAGER, oldDriverManager, driverManager));
			}
		}
		return driverManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DriverManager basicGetDriverManager() {
		return driverManager;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDriverManager(DriverManager newDriverManager) {
		DriverManager oldDriverManager = driverManager;
		driverManager = newDriverManager;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ChannelPackage.CHANNEL__DRIVER_MANAGER, oldDriverManager, driverManager));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getFullPath() {
		return getFolder() + getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public EList<ModelError> getModelErrors() {
		EList<ModelError> errorList = new BasicEList<ModelError>();
		DriverConfig dc = this.getDriver();
		List<Object> args = new ArrayList<Object>();
		ModelError me = null;
		if (dc == null){
			// Driver Configuration is not present 
			args.add(this.getName());
			me = CommonValidationUtils.constructModelError(this, "Channel.errors.driverConfig", args, false);
			errorList.add(me);
		} else {
			DRIVER_TYPE driverType = dc.getDriverType();
			if (driverType == null){
				me = CommonValidationUtils.constructModelError(this, "Channel.errors.driverConfig.driverType", null, false);
				errorList.add(me);
			}
			
			getHttpChannelModelErrors(dc, args, errorList);
			getKafkaChannelModelErrors(dc, args, errorList);
			getKafkaStreamsChannelModelErrors(dc, args, errorList);
			getKinesisChannelModelErrors(dc, args, errorList);
			getMqttChannelModelErrors(dc, args, errorList);
			getAS3xChannelModelErrors(dc, args, errorList);
			
			CONFIG_METHOD confMethod = dc.getConfigMethod();
			if (confMethod == null){
				me = CommonValidationUtils.constructModelError(this, "Channel.errors.driverConfig.noConfigurationMethod", null, false);
				errorList.add(me);
			} else {
				if (CONFIG_METHOD.REFERENCE == confMethod) {
					// resource Reference configuration
					String reference = dc.getReference();
					if (reference == null || reference.trim().length() == 0) {
						// There is no resource reference
						// if Driver Type is not local
						if (!DRIVER_LOCAL.equals(driverType.getName())) {
							// The referred Resource can not be null or blank
							me = CommonValidationUtils.constructModelError(this, "Channel.errors.methodOfConfiguartion.resource.reference", null, false);
							errorList.add(me);							
						}
					} 
					else {
						// resource reference is there
						if (!CommonValidationUtils.resolveReference(reference, this.getOwnerProjectName())){
							// Resource Reference is not resolved
							args.clear();
							args.add(reference);
							me = CommonValidationUtils.constructModelError(this, "Channel.errors.methodOfConfiguartion.resource.reference.notResolved", args, false);
							errorList.add(me);
						}
					}
				
				} else {
					// property Configuration
					PropertyMap instance = dc.getProperties();
					if (instance == null) {
						// there is no property configured
						me = CommonValidationUtils.constructModelError(this, "Channel.errors.methodOfConfiguartion.property.propertyNotConfigured", null, true);
						errorList.add(me);
						
					} else {
						if (instance.getProperties().size() > 0) {
							for (int i = 0; i < instance.getProperties().size(); i++) {
								Entity entity = instance.getProperties().get(i);
								String propName = entity.getName();
								if (entity instanceof SimpleProperty) {
									SimpleProperty simpleProperty = (SimpleProperty)entity;
									//This hardcoded value will be removed once the model is in place.
//									if(simpleProperty.isMandatory()){
									if (simpleProperty.getName().equals("ProviderURL")) {
										if(simpleProperty.getValue() == null){
											args.clear();
											args.add(getName());
											args.add(propName);
											me = CommonValidationUtils.constructModelError(this, "Channel.errors.propertyNotConfigured", args, true);
											errorList.add(me);
										}
										if (simpleProperty.getValue() != null && simpleProperty.getValue().trim().equals("")) {
											args.clear();
											args.add(getName());
											args.add(propName);
											me = CommonValidationUtils.constructModelError(this, "Channel.errors.propertyNotConfigured", args, true);
											errorList.add(me);
										}
									}
								}
							}
						}
					}
				}
			}
			List<Destination> desList = dc.getDestinations();
			if (desList.size() == 0) {
				// there is no destination associated with Channel
				me = CommonValidationUtils.constructModelError(this, "Channel.error.destination.empty", null, false);
				errorList.add(me);
			} 
//			else {
//				for (int i = 0; i < desList.size(); i++) {
//					Destination destination = desList.get(i);
//					String eventURI = destination.getEventURI();
//					if (eventURI == null || eventURI.length() == 0) {
//						continue; // this will be marked as a warning
//					} else {
//						Event simpleEvent = CommonIndexUtils.getSimpleEvent(getOwnerProjectName(), eventURI);
//						if (simpleEvent == null) {
//							args = new ArrayList<Object>();
//							args.add(eventURI);
//							args.add(destination.getName());
//							me = CommonValidationUtils.constructModelError(this, "Channel.error.destination.invalidEventURI", args, true);
//							errorList.add(me);
//						}
//					}
//				}
//			}
		}
		
		
		
	return errorList;
		
	}

	/**
	 * 
	 * @param dc
	 * @param args
	 * @param errorList
	 * @generated NOT
	 */
	private void getHttpChannelModelErrors(DriverConfig dc, List<Object> args, EList<ModelError> errorList) {
		if (dc instanceof HttpChannelDriverConfig) {
			HttpChannelDriverConfig httpChannelDriverConfig = (HttpChannelDriverConfig)dc;
			List<String> duplicates = new ArrayList<String>(); 
			Set<WebApplicationDescriptor> webAppDescSet = new TreeSet<WebApplicationDescriptor>(new WebAppDescriptorComparator()); 
			for(WebApplicationDescriptor desc : httpChannelDriverConfig.getWebApplicationDescriptors()) { 
				if(!webAppDescSet.add(desc)) { 
					duplicates.add(desc.getContextURI()); 
				} 
			} 
			if (duplicates.size() > 0) {
				args.add(this.getName());
				StringBuffer str = new StringBuffer(); 
				for (String dup : duplicates) {
					str.append("'");
					str.append(dup);
					str.append("'");
					str.append(",");
				}
				String dupArg = str.toString().substring(0, str.toString().length() - 1);
				args.add(dupArg);
				ModelError me = CommonValidationUtils.constructModelError(this, "Channel.errors.duplicate.context.uri", args, false);
				errorList.add(me);
			}
		}
	}
	
	private void getKafkaChannelModelErrors(DriverConfig dc, List<Object> args, EList<ModelError> errorList) {
		if (dc instanceof DriverConfigImpl && (DriverManagerConstants.DRIVER_KAFKA.equals(dc.getDriverType().getName())
				|| DriverManagerConstants.DRIVER_KAFKA_STREAMS.equals(dc.getDriverType().getName()))) {
			for (Entity entity : dc.getProperties().getProperties()) {
				if ("kafka.broker.urls".equals(entity.getName())
						&& entity instanceof SimplePropertyImpl
						&& ((SimplePropertyImpl)entity).getValue().trim().isEmpty()) {//BEKafkaProducer.validateAddresses(Arrays.asList(url.split(",")))
					ModelError me = CommonValidationUtils.constructModelError(this, "Channel.kafka.error.brokerUrlMissing", null, false);
					errorList.add(me);
				}
			}
			for (Destination dest : dc.getDestinations()) {
				for (Entity entity : dest.getProperties().getProperties()) {
					if ("topic.name".equals(entity.getName())
							&& entity instanceof SimplePropertyImpl
							&& ((SimplePropertyImpl)entity).getValue().trim().isEmpty()) {
						ModelError me = CommonValidationUtils.constructModelError(this, "Destination.kafka.error.topicNameMissing", null, false);
						errorList.add(me);
					}
				}
			}
		}
	}
	
	private void getKafkaStreamsChannelModelErrors(DriverConfig dc, List<Object> args, EList<ModelError> errorList) {
		if (DriverManagerConstants.DRIVER_KAFKA_STREAMS.equals(dc.getDriverType().getName())) {
			Set<String> appIds = new HashSet<String>();
			for (Destination dest : dc.getDestinations()) {
				for (Entity entity : dest.getProperties().getProperties()) {
					if ("application.id".equals(entity.getName())
							&& entity instanceof SimplePropertyImpl) {
						if(((SimplePropertyImpl)entity).getValue().trim().isEmpty()) {
							ModelError me = CommonValidationUtils.constructModelError(this, "Destination.kafkastreams.error.applicatiohIdMissing", null, false);
							errorList.add(me);
						} else if(appIds.contains(((SimplePropertyImpl)entity).getValue())) {
							ModelError me = CommonValidationUtils.constructModelError(this, "Destination.kafkastreams.error.applicatiohIdDuplicate", null, false);
							errorList.add(me);
						}
						appIds.add(((SimplePropertyImpl)entity).getValue());
					}
				}
			}
		}
	}
	
	private void getKinesisChannelModelErrors(DriverConfig dc, List<Object> args, EList<ModelError> errorList) {
		if (dc instanceof DriverConfigImpl && "Kinesis".equals(dc.getDriverType().getName())) {
			for (Entity entity : dc.getProperties().getProperties()) {
				if ("access_key".equals(entity.getName()) && entity instanceof SimplePropertyImpl
						&& ((SimplePropertyImpl) entity).getValue().trim().isEmpty()) {
					ModelError me = CommonValidationUtils.constructModelError(this, "Channel.kinesis.error.accessKeyMissing", null, false);
					errorList.add(me);
				}
				if ("secret_key".equals(entity.getName()) && entity instanceof SimplePropertyImpl
						&& ((SimplePropertyImpl) entity).getValue().trim().isEmpty()) {
					ModelError me = CommonValidationUtils.constructModelError(this, "Channel.kinesis.error.secretKeyMissing", null, false);
					errorList.add(me);
				}
			}
			for (Destination dest : dc.getDestinations()) {
				for (Entity entity : dest.getProperties().getProperties()) {
					if ("stream.name".equals(entity.getName()) && entity instanceof SimplePropertyImpl
							&& ((SimplePropertyImpl) entity).getValue().trim().isEmpty()) {
						ModelError me = CommonValidationUtils.constructModelError(this, "Destination.kinesis.error.streamNameMissing", null, false);
						errorList.add(me);
					} else if ("application.name".equals(entity.getName()) && entity instanceof SimplePropertyImpl
							&& ((SimplePropertyImpl) entity).getValue().trim().isEmpty()) {
						ModelError me = CommonValidationUtils.constructModelError(this, "Destination.kinesis.error.applicationNameMissing", null, false);
						errorList.add(me);
					} else if ("region.name".equals(entity.getName()) && entity instanceof SimplePropertyImpl
							&& ((SimplePropertyImpl) entity).getValue().trim().isEmpty()) {
						ModelError me = CommonValidationUtils.constructModelError(this, "Destination.kinesis.error.regionNameMissing", null, false);
						errorList.add(me);
					} else if ("max.records".equals(entity.getName()) && entity instanceof SimplePropertyImpl
							&& ((SimplePropertyImpl) entity).getValue().trim().isEmpty()) {
						ModelError me = CommonValidationUtils.constructModelError(this, "Destination.kinesis.error.maxRecordsMissing", null, false);
						errorList.add(me);
					}
				}
			}
		}
	}
	
	private void getMqttChannelModelErrors(DriverConfig dc, List<Object> args, EList<ModelError> errorList) {
		if (dc instanceof DriverConfigImpl && "MQTT".equals(dc.getDriverType().getName())) {
			if(dc.getProperties()!=null){
				for (Entity entity : dc.getProperties().getProperties()) {
					if ("mqtt.broker.urls".equals(entity.getName())
							&& entity instanceof SimplePropertyImpl
							&& ((SimplePropertyImpl)entity).getValue().trim().isEmpty()) {
						ModelError me = CommonValidationUtils.constructModelError(this, "Channel.mqtt.error.brokerUrlMissing", null, false);
						errorList.add(me);
					}
				}
			}
			for (Destination dest : dc.getDestinations()) {
				for (Entity entity : dest.getProperties().getProperties()) {
					if ("publish.topic.name".equals(entity.getName())
							&& entity instanceof SimplePropertyImpl	&& ((SimplePropertyImpl)entity).getValue().trim().isEmpty()) {
						ModelError me = CommonValidationUtils.constructModelError(this, "Destination.mqtt.error.publishTopicNameMissing", null, false);
						errorList.add(me);
					}else if("subscribe.topic.name".equals(entity.getName())
							&& entity instanceof SimplePropertyImpl	&& ((SimplePropertyImpl)entity).getValue().trim().isEmpty()) {
						ModelError me = CommonValidationUtils.constructModelError(this, "Destination.mqtt.error.subscribeTopicNameMissing", null, false);
						errorList.add(me);
					}else if("clientId".equals(entity.getName())
							&& entity instanceof SimplePropertyImpl	&& ((SimplePropertyImpl)entity).getValue().trim().isEmpty()) {
						ModelError me = CommonValidationUtils.constructModelError(this, "Destination.mqtt.error.clientIdMissing", null, false);
						errorList.add(me);
					}else if("maxInflight".equals(entity.getName())
							&& entity instanceof SimplePropertyImpl	&& ((SimplePropertyImpl)entity).getValue().trim().isEmpty()) {
						ModelError me = CommonValidationUtils.constructModelError(this, "Destination.mqtt.error.maxInflightMissing", null, false);
						errorList.add(me);
					}
				}
			}
		}
	}
	
	private void getAS3xChannelModelErrors(DriverConfig dc, List<Object> args, EList<ModelError> errorList) {
		if (dc instanceof DriverConfigImpl && "ActiveSpaces 3.x".equals(dc.getDriverType().getName())) {
			for (Destination dest : dc.getDestinations()) {
				for (Entity entity : dest.getProperties().getProperties()) {
					if ("TableName".equals(entity.getName())
							&& entity instanceof SimplePropertyImpl	&& ((SimplePropertyImpl)entity).getValue().trim().isEmpty()) {
						ModelError me = CommonValidationUtils.constructModelError(this, "Destination.as3x.tableNameMissing", null, false);
						errorList.add(me);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * @generated NOT
	 */
	class WebAppDescriptorComparator implements Comparator<WebApplicationDescriptor> 
	{ 
	    public int compare(WebApplicationDescriptor URI1, WebApplicationDescriptor URI2) 
	    { 
	        return URI1.getContextURI().compareTo(URI2.getContextURI()); 
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
			case ChannelPackage.CHANNEL__DRIVER:
				return basicSetDriver(null, msgs);
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
			case ChannelPackage.CHANNEL__DRIVER:
				return getDriver();
			case ChannelPackage.CHANNEL__DRIVER_MANAGER:
				if (resolve) return getDriverManager();
				return basicGetDriverManager();
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
			case ChannelPackage.CHANNEL__DRIVER:
				setDriver((DriverConfig)newValue);
				return;
			case ChannelPackage.CHANNEL__DRIVER_MANAGER:
				setDriverManager((DriverManager)newValue);
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
			case ChannelPackage.CHANNEL__DRIVER:
				setDriver((DriverConfig)null);
				return;
			case ChannelPackage.CHANNEL__DRIVER_MANAGER:
				setDriverManager((DriverManager)null);
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
			case ChannelPackage.CHANNEL__DRIVER:
				return driver != null;
			case ChannelPackage.CHANNEL__DRIVER_MANAGER:
				return driverManager != null;
		}
		return super.eIsSet(featureID);
	}
	

} //ChannelImpl
