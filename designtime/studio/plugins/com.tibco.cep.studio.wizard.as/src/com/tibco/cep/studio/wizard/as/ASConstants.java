package com.tibco.cep.studio.wizard.as;

import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_DISCOVERYURL;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_ENABLE_SECURITY;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_LISTENURL;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_METASPACENAME;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_POLICY_FILE;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_ID_PASSWORD;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_SECURITY_ROLE;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_TOKEN_FILE;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_AUTH_CREDENTIAL;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_AUTH_DOMAIN;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_AUTH_USERNAME;
import static com.tibco.cep.driver.as.ASConstants.CHANNEL_PROPERTY_AUTH_PASSWORD;
import static com.tibco.cep.driver.as.ASConstants.K_BE_EVENT_PROP_BROWSER_TYPE;
import static com.tibco.cep.driver.as.ASConstants.K_BE_EVENT_PROP_CONSUMPTION_MODE;
import static com.tibco.cep.driver.as.ASConstants.K_BE_EVENT_PROP_EVENT_TYPE;
import static com.tibco.cep.studio.wizard.as.commons.utils.BeanUtils.getBeanInfo;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.tibco.cep.designtime.core.model.event.SimpleEvent;
import com.tibco.cep.designtime.core.model.service.channel.Destination;
import com.tibco.cep.driver.as.serializers.ActiveSpacesSerializer;

public class ASConstants {

	// context
	private static final String             _CTX_AS_GROUP_PREFIX                         = "as/";                                                    //$NON-NLS-1$
	private static final String             _CTX_SERVICES_GROUP_PREFIX                   = "services/";                                              //$NON-NLS-1$
	private static final String             _CTX_UI_GROUP_PREFIX                         = "ui/";                                                    //$NON-NLS-1$
	private static final String             _CTX_WIZARD_GROUP_PREFIX                     = "wizard/";                                                //$NON-NLS-1$
	public static final String              _K_CTX_AS_CONTROLLER_SPACE_SELECTION         = _CTX_AS_GROUP_PREFIX + "controllers/spaceSelection";      //$NON-NLS-1$
	public static final String              _K_CTX_AS_CONTROLLER_DESTINATION             = _CTX_AS_GROUP_PREFIX + "controllers/destination";         //$NON-NLS-1$
	public static final String              _K_CTX_AS_CONTROLLER_EVENT_AND_DESTINATION   = _CTX_AS_GROUP_PREFIX + "controllers/eventAndDestination"; //$NON-NLS-1$
	public static final String              _K_CTX_AS_CONTROLLER_EVENT                   = _CTX_AS_GROUP_PREFIX + "controllers/event";               //$NON-NLS-1$
	public static final String              _K_CTX_SERVICE_PERSISTENCE_EXISTING_RESOURCE = _CTX_SERVICES_GROUP_PREFIX + "persistence/existingResource";                   //$NON-NLS-1$
	public static final String              _K_CTX_SERVICE_PERSISTENCE_ORIGINAL_RESOURCE = _CTX_SERVICES_GROUP_PREFIX + "persistence/originalResource";                   //$NON-NLS-1$
	public static final String              _K_CTX_SERVICE_PERSISTENCE_TARGET_RESOURCE   = _CTX_SERVICES_GROUP_PREFIX + "persistence/targetResource"; //$NON-NLS-1$
	public static final String              _K_CTX_UI_CONTROL_SHELL                      = _CTX_UI_GROUP_PREFIX + "controls/shell";                  //$NON-NLS-1$
	public static final String              _K_CTX_WIZARD_CONTROLLER_NEW                 = _CTX_WIZARD_GROUP_PREFIX + "controllers/new";             //$NON-NLS-1$

	// images
	public static final String              _IMAGE_ERROR_16X16                           = "icons/error_16x16.gif";                                  //$NON-NLS-1$
	public static final String              _IMAGE_WARNING_16X16                         = "icons/warning_16x16.gif";                                //$NON-NLS-1$
	public static final String              _IMAGE_CHANNEL_16X16                         = "icons/channel.png";                                      //$NON-NLS-1$
	public static final String              _IMAGE_DESTINATION_16X16                     = "icons/destination.png";                                  //$NON-NLS-1$
	public static final String              _IMAGE_WIZBAN_CHANNEL                        = "icons/wizard/asWizard.png";                              //$NON-NLS-1$
	public static final String              _IMAGE_WIZBAN_SIMPLE_EVENT                   = "icons/wizard/asWizard.png";                              //$NON-NLS-1$
	public static final String              _IMAGE_WIZBAN_DESTINATION                    = "icons/wizard/asWizard.png";                              //$NON-NLS-1$
	public static final String              _IMAGE_WIZBAN_EVENT_AND_DESTINATION          = "icons/wizard/asWizard.png";                              //$NON-NLS-1$
	public static final String              _IMAGE_PROPTYPES_INTEGER_16X16               = "icons/property/iconInteger16.gif";                       //$NON-NLS-1$
	public static final String              _IMAGE_PROPTYPES_LONG_16X16                  = "icons/property/iconLong16.gif";                          //$NON-NLS-1$
	public static final String              _IMAGE_PROPTYPES_STRING_16X16                = "icons/property/iconString16.gif";                        //$NON-NLS-1$
	public static final String              _IMAGE_PROPTYPES_BOOLEAN_16X16               = "icons/property/iconBoolean16.gif";                       //$NON-NLS-1$
	public static final String              _IMAGE_PROPTYPES_DATETIME_16X16              = "icons/property/iconDate16.gif";                          //$NON-NLS-1$
	public static final String              _IMAGE_PROPTYPES_DOUBLE_16X16                = "icons/property/iconReal16.gif";                          //$NON-NLS-1$

	// Prefix and suffix of channel, destination and event
	public static final String              _NAME_SUFFIX_SIMPLE_EVENT                    = "Event";                                                  //$NON-NLS-1$
	public static final String              _NAME_SUFFIX_DESTINATION                     = "Dest";                                                   //$NON-NLS-1$

	// Folder name
	public static final String              _EVENT_FOLDER                                = "/Events/";                                               //$NON-NLS-1$

	// Channel properties
	public static final String[]            _CHANNEL_PROPERTY_NAMES                      =
	{
		CHANNEL_PROPERTY_METASPACENAME.localName,
		CHANNEL_PROPERTY_DISCOVERYURL.localName,
		CHANNEL_PROPERTY_LISTENURL.localName,
		CHANNEL_PROPERTY_ENABLE_SECURITY.localName, 
		CHANNEL_PROPERTY_SECURITY_ROLE.localName,
		CHANNEL_PROPERTY_POLICY_FILE.localName,
		CHANNEL_PROPERTY_TOKEN_FILE.localName,
        CHANNEL_PROPERTY_ID_PASSWORD.localName,
        CHANNEL_PROPERTY_AUTH_CREDENTIAL.localName,
        CHANNEL_PROPERTY_AUTH_DOMAIN.localName,
        CHANNEL_PROPERTY_AUTH_USERNAME.localName,
        CHANNEL_PROPERTY_AUTH_PASSWORD.localName
	};

	// Additional SimpleEvent Management Properties
	public static final String[]            _ADDITIONAL_MANAGEMENT_PROPERTY_NAMES        = {
		K_BE_EVENT_PROP_CONSUMPTION_MODE,
		K_BE_EVENT_PROP_EVENT_TYPE,
		K_BE_EVENT_PROP_BROWSER_TYPE
	};

	// Bean properties of Channel, Event and Destination
	public static final String              _CLASSNAME_AS_DESTINATION_SERIALIZER         = ActiveSpacesSerializer.class.getCanonicalName();
	public static final Map<String, Method> _BEAN_PROP_SETTERS_SIMPLE_EVENT;
	public static final Map<String, Method> _BEAN_PROP_SETTERS_DESTINATION;
	public static final Map<String, Method> _BEAN_PROP_GETTERS_SIMPLE_EVENT;
	public static final Map<String, Method> _BEAN_PROP_GETTERS_DESTINATION;

	static {
		// Event bean info
		_BEAN_PROP_SETTERS_SIMPLE_EVENT = createBeanMethods(SimpleEvent.class, true);
		_BEAN_PROP_GETTERS_SIMPLE_EVENT = createBeanMethods(SimpleEvent.class, false);

		// Destination bean info
		_BEAN_PROP_SETTERS_DESTINATION = createBeanMethods(Destination.class, true);
		_BEAN_PROP_GETTERS_DESTINATION = createBeanMethods(Destination.class, false);
	}

	private static Map<String, Method> createBeanMethods(Class<?> clazz, boolean setterOrGetter /*
																								 * true
																								 * means
																								 * setter
																								 */) {
		Map<String, Method> propMethods = new HashMap<String, Method>();

		BeanInfo beanInfo = getBeanInfo(clazz);
		if (null != beanInfo) {
			PropertyDescriptor[] propDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor propDescriptor : propDescriptors) {
				String propName = propDescriptor.getName();
				Method propMethod = null;
				if (setterOrGetter) {
					propMethod = propDescriptor.getWriteMethod();
				}
				else {
					propMethod = propDescriptor.getReadMethod();
				}
				propMethods.put(propName, propMethod);
			}
		}

		return propMethods;
	}

}
