package com.tibco.cep.designtime.model.service.channel.mutable.impl;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.xml.sax.InputSource;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.service.channel.ChannelDescriptor;
import com.tibco.cep.designtime.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.model.service.channel.ExtendedConfiguration;
import com.tibco.cep.designtime.model.service.channel.PropertyDescriptor;
import com.tibco.cep.designtime.model.service.channel.impl.ChannelDescriptorImpl;
import com.tibco.cep.designtime.model.service.channel.impl.DestinationDescriptorImpl;
import com.tibco.cep.designtime.model.service.channel.impl.PropertyDescriptorImpl;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableChannel;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableDriverConfig;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableDriverManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.XiParser;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.datamodel.helpers.XiChild;

/*
 * User: nprade
 * Date: Jul 23, 2004
 * Time: 8:09:46 PM
 */

/**
 * Manages a collection of DriverConfig objects.
 */
public class DefaultMutableDriverManager implements MutableDriverManager {


    public static final String PATTERN_PROPERTY_NAME = "pattern";

    protected static final XiParser PARSER = XiParserFactory.newInstance();
    protected static final ExpandedName NAME_DRIVERS = ExpandedName.makeName("drivers");
    protected static final ExpandedName NAME_DRIVER = ExpandedName.makeName("driver");
    protected static final ExpandedName NAME_TYPE = ExpandedName.makeName("type");
    protected static final ExpandedName NAME_VERSION = ExpandedName.makeName("version");
    protected static final ExpandedName NAME_PROPERTIES = ExpandedName.makeName("properties");
    protected static final ExpandedName NAME_PROPERTY = ExpandedName.makeName("property");
    protected static final ExpandedName NAME_DESTINATIONS = ExpandedName.makeName("destinations");
    protected static final ExpandedName NAME_DESCRIPTION = ExpandedName.makeName("description");
    protected static final ExpandedName NAME_LABEL = ExpandedName.makeName("label");
    protected static final ExpandedName NAME_REFERENCES = ExpandedName.makeName("references");
    protected static final ExpandedName NAME_NAME = ExpandedName.makeName("name");
    protected static final ExpandedName NAME_DEFAULT = ExpandedName.makeName("default");
    //protected static final ExpandedName NAME_SERVER_TYPE = ExpandedName.makeName("serverType");
    protected static final ExpandedName NAME_PATTERN = ExpandedName.makeName(PATTERN_PROPERTY_NAME);


    /**
     * Stores the driver registrations.
     */
    protected LinkedHashMap m_registrations = new LinkedHashMap();


    public DefaultMutableDriverManager() {
        try {
            initializeDrivers();
        } catch (IOException e) {
            e.printStackTrace();
        }//catch
    }//constr


    protected void initializeDrivers() throws IOException {
        HashMap urlMap = new HashMap();
        Enumeration configs =Thread.currentThread().getContextClassLoader().getResources("drivers.xml");
        while(configs.hasMoreElements()) {
            final URL url = (URL) configs.nextElement();
            urlMap.put(url.toString(),url);
        }
        configs = DefaultMutableDriverManager.class.getClassLoader().getSystemResources("drivers.xml");
        while (configs.hasMoreElements()) {
            final URL url = (URL) configs.nextElement();
            urlMap.put(url.toString(),url);
        }
        configs = DefaultMutableDriverManager.class.getClassLoader().getResources("drivers.xml");
        while (configs.hasMoreElements()) {
            final URL url = (URL) configs.nextElement();
            urlMap.put(url.toString(),url);
        }
        //final Enumeration driverConfigs = Thread.currentThread().getContextClassLoader().getResources("drivers.xml");
        Iterator driverConfigs = urlMap.values().iterator();
        while (driverConfigs.hasNext()) {
            try {
                final URL url = (URL) driverConfigs.next();
                final InputStream is = url.openStream();
                final XiNode document = PARSER.parse(new InputSource(is));
                final XiNode drivers = XiChild.getChild(document, NAME_DRIVERS);
                for (Iterator it = XiChild.getIterator(drivers, NAME_DRIVER); it.hasNext();) {
                    final XiNode driverNode = (XiNode) it.next();
                    try {
                        initializeDriver(driverNode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }//catch
                }//for
            } catch (Exception e) {
                e.printStackTrace();
            }//catch
        }//while
    }//loadDrivers


    protected void initializeDriver(XiNode node) throws ModelException {
        final XiNode typeNode = XiChild.getChild(node, NAME_TYPE);
        final String driverType = typeNode.getStringValue();

        final XiNode versionNode = XiChild.getChild(node, NAME_VERSION);
        final String version = versionNode.getStringValue();

        /*final XiNode serverTypeNode = XiChild.getChild(node, NAME_SERVER_TYPE);

        final String serverType = (serverTypeNode != null) ?
                                serverTypeNode.getStringValue() : null;*/

        if (!canRegister(driverType, version)) {
            return;
        }//if

        final DestinationDescriptor destinationDescriptor = new DestinationDescriptorImpl();
        final XiNode destinationsNode = XiChild.getChild(node, NAME_DESTINATIONS);
        populateDescriptor(destinationDescriptor, destinationsNode);

        final ChannelDescriptor channelDescriptor = new ChannelDescriptorImpl(destinationDescriptor);
        final XiNode propertiesNode = XiChild.getChild(node, NAME_PROPERTIES);
        populateDescriptor(channelDescriptor, propertiesNode);

        final XiNode descriptionNode = XiChild.getChild(node, NAME_DESCRIPTION);
        final String description = (descriptionNode == null) ? "" : descriptionNode.getStringValue();

        final XiNode labelNode = XiChild.getChild(node, NAME_LABEL);
        final String label = (labelNode == null) ? driverType : labelNode.getStringValue();

        final XiNode typesAllowedNode = XiChild.getChild(node, NAME_REFERENCES);
        final List typesAllowedList = new ArrayList();
        if (typesAllowedNode != null) {
            for (Iterator it = XiChild.getIterator(typesAllowedNode, NAME_TYPE); it.hasNext();) {
                final XiNode typeAllowedNode = (XiNode) it.next();
                final String name = typeAllowedNode.getStringValue();
                typesAllowedList.add(name);
            }//for
        }//if
        final String[] typesAllowedArray = (String[]) typesAllowedList.toArray(new String[0]);

        final XiNode sersNode = XiChild.getChild(node, ExpandedName.makeName("serializers"));
        SerializerConfig sConfig = createSerializerConfig(sersNode);
        /**
        <configuration>
            <property>
            <name>DeliveryMode</name>
            <parent>destination</parent>
            <type>combo-box</type>
            <choices default="PERSISTENT">
                <choice displayed="PERSISTENT" value="1" />
                <choice displayed="NON-PERSISTENT" value="2" />
            </choices>
            </property>
        </configuration>
        **/
        List configuration = new ArrayList();
        final XiNode configNode = XiChild.getChild(node, ExpandedName.makeName("configuration"));
        if (configNode != null) {
            for (Iterator it = XiChild.getIterator(configNode, ExpandedName.makeName("property")); it.hasNext();) {
                List displayedValues= new ArrayList();
                List values = new ArrayList();
                final XiNode propertyNode = (XiNode) it.next();
                final String propertyName = XiChild.getChild(propertyNode, ExpandedName.makeName("name")).getStringValue();
                final String propertyParent = XiChild.getChild(propertyNode, ExpandedName.makeName("parent")).getStringValue();
                final String configType = XiChild.getChild(propertyNode, ExpandedName.makeName("type")).getStringValue();
                final XiNode choicesNode = XiChild.getChild(propertyNode, ExpandedName.makeName("choices"));
                final String defaultValue= choicesNode.getAttributeStringValue(ExpandedName.makeName("default"));
                for (Iterator it1 = XiChild.getIterator(choicesNode, ExpandedName.makeName("choice")); it1.hasNext();) {
                    final XiNode choiceNode=(XiNode) it1.next();
                    displayedValues.add(choiceNode.getAttributeStringValue(ExpandedName.makeName("displayed")));
                    values.add(choiceNode.getAttributeStringValue(ExpandedName.makeName("value")));
                }

                configuration.add(new PropertyChoiceConfig(propertyName,propertyParent, configType, displayedValues, values, defaultValue));
            }//for
        }

        final DriverRegistration registration = new DriverRegistration(driverType, label, version, description,
                null, channelDescriptor, destinationDescriptor, typesAllowedArray, sConfig, configuration);

        m_registrations.put(driverType, registration);

    }//initializeDriver

    public  List getPropertyConfigurations(String driverType) {
        DriverRegistration registration=(DriverRegistration) m_registrations.get(driverType);
        if (registration != null) {
            return registration.m_configurations;
        } else {
            return new ArrayList();
        }
    }

    private static void populateDescriptor(
            Map<String, PropertyDescriptor> map,
            XiNode sourceNode)
            throws ModelException {

        if (sourceNode != null) {
            for (Iterator it = XiChild.getIterator(sourceNode, NAME_PROPERTY); it.hasNext();) {
                final XiNode propertyNode = (XiNode) it.next();
                final String name = propertyNode.getAttributeStringValue(NAME_NAME);
                final String typeName = propertyNode.getAttributeStringValue(NAME_TYPE);
                final String defaultValue = propertyNode.getAttributeStringValue(NAME_DEFAULT);
                final String patternString = propertyNode.getAttributeStringValue(NAME_PATTERN);
                int type = 0;
                final String[] types = RDFTypes.driverTypeStrings;
                if (types[PropertyDefinition.PROPERTY_TYPE_STRING].equalsIgnoreCase(typeName)) {
                    type = PropertyDefinition.PROPERTY_TYPE_STRING;
                } else if (types[PropertyDefinition.PROPERTY_TYPE_INTEGER].equalsIgnoreCase(typeName)) {
                    type = PropertyDefinition.PROPERTY_TYPE_INTEGER;
                } else if (types[PropertyDefinition.PROPERTY_TYPE_REAL].equalsIgnoreCase(typeName)) {
                    type = PropertyDefinition.PROPERTY_TYPE_REAL;
                } else if (types[PropertyDefinition.PROPERTY_TYPE_BOOLEAN].equalsIgnoreCase(typeName)) {
                    type = PropertyDefinition.PROPERTY_TYPE_BOOLEAN;
                } else if (types[PropertyDefinition.PROPERTY_TYPE_DATETIME].equalsIgnoreCase(typeName)) {
                    type = PropertyDefinition.PROPERTY_TYPE_DATETIME;
                } else if (types[PropertyDefinition.PROPERTY_TYPE_LONG].equalsIgnoreCase(typeName)) {
                    type = PropertyDefinition.PROPERTY_TYPE_LONG;
                } else {
                    throw new IllegalArgumentException("bad type");
                }//else
                Pattern pattern = null;
                if (!((null == patternString) || ("".equals(patternString.trim())))) {
                    try {
                        pattern = Pattern.compile(patternString.trim());
                    } catch (PatternSyntaxException e) {
                        e.printStackTrace();
                    }
                }
                final PropertyDescriptor prop = new PropertyDescriptorImpl(name, type, defaultValue, pattern);
                map.put(name, prop);
            }//for
        }//if
    }//populateConceptProperties

    public ExtendedConfiguration getExtendedConfiguration() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Finds all types registered with this MutableDriverManager.
     *
     * @return an Iterator of Strings.
     */
    public Iterator getDriverTypes() {
        return m_registrations.keySet().iterator();
        //return m_propertiesConcepts.keySet().iterator();
    }//getDriverTypes


    /**
     * Finds the labels for all types registered with this DriverManager.
     *
     * @return a Map of String type to String label.
     */
    public Map getDriverLabelsByTypes() {
        final Map map = new HashMap(m_registrations.size());
        for (Iterator it = m_registrations.keySet().iterator(); it.hasNext();) {
            final Object type = it.next();
            map.put(type, ((DriverRegistration) m_registrations.get(type)).getLabel());
        }
        return map;
    }

    /**
     * Returns a new DriverConfig for the requested type.
     *
     * @param type a String identifying the DriverConfig to return.
     * @return the MutableDriverConfig, or null if no DriverConfig could be created.
     */
    public MutableDriverConfig createDriverConfig(String type, MutableChannel channel) {
        try {
            final List destinations = new ArrayList();
            return new DefaultMutableDriverConfig(channel, type, DriverConfig.CONFIG_BY_REFERENCE, "", destinations);
        } catch (Exception e) {
            return null;
        }//catch
    }//createDriverConfig


    /**
     * Finds the allowed resource types for the DriverConfig registered under the requested type
     * with this MutableDriverManager.
     *
     * @param type a String identifying the DriverConfig to return.
     * @return a String[], or null if no DriverConfig was found.
     */
    public String[] getAllowedResourceTypes(String type) {
        final DriverRegistration registration = (DriverRegistration) m_registrations.get(type);
        if (registration == null) {
            return null;
        } else {
            return registration.getAllowedResourceTypes();
        }//else
    }//getAllowedResourceTypes


    public boolean isSerializerUserDefined(String type) {
        final DriverRegistration registration = (DriverRegistration) m_registrations.get(type);
        if (registration == null) {
            return false;
        } else {
            return registration.isSerializerUserDefined();
        }//else
    }


    public String[] getSerializerTypes(String type) {
        final DriverRegistration registration = (DriverRegistration) m_registrations.get(type);
        if (registration == null) {
            return null;
        } else {
            return registration.getSerializerTypes();
        }//else
    }//getAllowedResourceTypes


    public String[] getSerializerClasses(String type) {
        final DriverRegistration registration = (DriverRegistration) m_registrations.get(type);
        if (registration == null) {
            return null;
        } else {
            return registration.getSerializerClasses();
        }//else
    }//getAllowedResourceTypes


    /**
     * Finds a description of the Driver registered under the given type.
     *
     * @param type a String identifying the DriverConfig type.
     * @return a String, or null if no DriverConfig was found for this type.
     */
    public String getDescription(String type) {
        final DriverRegistration registration = (DriverRegistration) m_registrations.get(type);
        if (registration == null) {
            return null;
        } else {
            return registration.getDescription();
        }//else
    }//getDescription


    public ChannelDescriptor getChannelDescriptor(
            String type) {
        final DriverRegistration registration = (DriverRegistration) m_registrations.get(type);
        if (registration == null) {
            return null;
        } else {
            return registration.getChannelDescriptor();
        }//else
    }//createDriverConfig


    public DestinationDescriptor getDestinationDescriptor(
            String type) {
        final DriverRegistration registration = (DriverRegistration) m_registrations.get(type);
        if (registration == null) {
            return null;
        } else {
            return registration.getDestinationDescriptor();
        }//else
    }//getDestinationDescriptor


    /**
     * Finds the version of the Driver registration for the given type.
     *
     * @param type a String identifying the DriverConfig type.
     * @return a String, or null if no DriverConfig was found for this type.
     */
    public String getVersion(String type) {
        final DriverRegistration registration = (DriverRegistration) m_registrations.get(type);
        if (registration == null) {
            return null;
        } else {
            return registration.getVersion();
        }//else
    }//getVersion

    /**
     * Finds the version of the Driver registration for the given type.
     *
     * @param type a String identifying the DriverConfig type.
     * @return a String, or null if no DriverConfig was found for this type.
     */
    public String getServerType(String type) {
        final DriverRegistration registration = (DriverRegistration) m_registrations.get(type);
        if (registration == null) {
            return null;
        } else {
            return registration.getServerType();
        }//else
    }//getServerType


    /**
     * @param type    the String type of the DriverConfig
     * @param version the String version of the DriverConfig
     * @return true iif this version of this type can be registered.
     */
    public boolean canRegister(String type, String version) {
        final DriverRegistration registration = (DriverRegistration) m_registrations.get(type);
        if (registration == null) {
            return true;
        } else {
            return registration.isLessRecentThan(version);
        }//else
    }//canRegister


    public MutableDriverManager registerDriver(
            String type,
            String label,
            String version,
            String description,
            ChannelDescriptor channelDescriptor,
            DestinationDescriptor destinationDescriptor,
            String[] resourceTypes) {
        if (type == null) {
            throw new IllegalArgumentException("invalid type");
        }//if
        if (version == null) {
            throw new IllegalArgumentException("invalid version");
        }//if

        if (description == null) {
            description = "";
        }//if

        final DriverRegistration existingRegistration = (DriverRegistration) m_registrations.get(type);
        if ((existingRegistration == null) || existingRegistration.isLessRecentThan(version)) {
            final DriverRegistration newRegistration = new DriverRegistration(type, label, version, description.trim(),
                    channelDescriptor, destinationDescriptor, resourceTypes);
            m_registrations.put(type, newRegistration);
        }//if

        return this;
    }//registerDriver


    /**
     * Removes the DriverConfig associated to the given type from the registry,
     * if it was registered.
     *
     * @param type a String identifying the DriverConfig to deregister.
     * @return the MutableDriverManager itself.
     */
    public MutableDriverManager deregisterDriver(String type) {
        m_registrations.remove(type);
        return this;
    }//deregisterDriver

    public String getDefaultSerializer(String type) {

        final DriverRegistration reg = (DriverRegistration) m_registrations.get(type);
        if (reg == null) return null;
        if (reg.m_sconfig == null) return null;
        return reg.m_sconfig.defaultSerializer;

    }


    static SerializerConfig createSerializerConfig(XiNode sersNode) {

        if (null == sersNode) {
            return null;
        }

        boolean userDefined = Boolean.valueOf(sersNode.getAttributeStringValue(ExpandedName.makeName("userdefined"))).booleanValue();
        Iterator r = XiChild.getIterator(sersNode, ExpandedName.makeName("serializer"));
        List types = new ArrayList();
        List classes = new ArrayList();
        String defaultSerializer = null;
        boolean isFirst = true;

        while (r.hasNext()) {
            String serializer;
            XiNode serNode = (XiNode) r.next();
            types.add(serNode.getAttributeStringValue(ExpandedName.makeName("type")));

            serializer = serNode.getAttributeStringValue(ExpandedName.makeName("class"));

            if (isFirst) {
                defaultSerializer = serializer;
                isFirst = false;
            }

            classes.add(serializer);
        }

        return new SerializerConfig(userDefined, types, classes, defaultSerializer);
    }


    class DriverRegistration {


        private String m_type;
        private String m_label;
        private String m_version;
        private String m_serverType;
        private String m_description;
        private ChannelDescriptor channelDescriptor;
        private DestinationDescriptor destinationDescriptor;
        private String[] m_resourcesAllowed;
        private SerializerConfig m_sconfig;
        private List m_configurations;

        public DriverRegistration(String type, String label, String version, String description,
                                  ChannelDescriptor propsConcept, DestinationDescriptor destsConcept, String[] resourcesAllowed) {
            this(type, label, version, description, null, propsConcept, destsConcept, resourcesAllowed, null, null);

        }//constr


        public DriverRegistration(
                String type,
                String label,
                String version,
                String description,
                String serverType,
                ChannelDescriptor propsConcept,
                DestinationDescriptor destsConcept,
                String[] resourcesAllowed,
                SerializerConfig sconfig,
                List configurations) {
            m_type = type;
            m_label = label;
            m_version = version;
            m_description = description;
            m_serverType = serverType;
            this.channelDescriptor = propsConcept;
            this.destinationDescriptor = destsConcept;
            m_resourcesAllowed = resourcesAllowed;
            m_sconfig = sconfig;
            m_configurations=configurations;
        }//constr


        public String getType() {
            return m_type;
        }


        public String getLabel() {
            return m_label;
        }


        public String getVersion() {
            return m_version;
        }

        public String getServerType() {
            return m_serverType;
        }


        public String getDescription() {
            return m_description;
        }


        public ChannelDescriptor getChannelDescriptor() {
            return this.channelDescriptor;
        }


        public DestinationDescriptor getDestinationDescriptor() {
            return this.destinationDescriptor;
        }


        public String[] getAllowedResourceTypes() {
            return m_resourcesAllowed;
        }


        public boolean isSerializerUserDefined() {
            if (m_sconfig == null) {
                return false;
            }
            return m_sconfig.isSerializerUserDefined();
        }


        public String[] getSerializerTypes() {
            if (m_sconfig == null) {
                return new String[0];
            }
            return m_sconfig.getSerializerTypes();
        }


        public String[] getSerializerClasses() {
            if (m_sconfig == null) {
                return new String[0];
            }
            return m_sconfig.getSerializerClasses();
        }


        public boolean isLessRecentThan(String version) {
            final int[] myVersion = convertVersion(m_version);
            final int[] yourVersion = convertVersion(version);
            
            if (yourVersion.length == myVersion.length) {
            	for (int i = 0; i < yourVersion.length; i ++) {
            		if (yourVersion[i] < myVersion[i]) {
            			return false;
            		}
            	}
            }

//            if (yourVersion[0] < myVersion[0]) {
//                return false;
//            }//if
//            if (yourVersion[1] < myVersion[1]) {
//                return false;
//            }//if
//            if (yourVersion[2] < myVersion[2]) {
//                return false;
//            }//if
//            if (yourVersion[3] < myVersion[3]) {
//                return false;
//            }//if

            return true;
        }//isLessRecentThan


        private int[] convertVersion(String version) {
        	ArrayList<Integer> list = new ArrayList<Integer>();
        	String[] arr = version.split("\\.");
        	for (String s : arr) {
        		list.add(Integer.parseInt(s));
        	}
            final int[] array = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
            	array[i] = list.get(i);
            }
//            int[] array = new int[list.size()];
//            int start = 0;
//            int stop = version.indexOf('.');
//            String number = version.substring(start, stop);
//            array[0] = Integer.parseInt(number);
//            start = stop + 1;
//            stop = version.indexOf('.', start);
//            number = version.substring(start, stop);
//            array[1] = Integer.parseInt(number);
//            start = stop + 1;
//            stop = version.indexOf('.', start);
//            if (stop >= start) {
//            	number = version.substring(start, stop);
//            }
//            array[2] = Integer.parseInt(number);
//            start = stop + 1;
//            number = version.substring(start);
//            array[3] = Integer.parseInt(number);
            return array;
        }//convertVersion

    }//class DriverRegistration

    static class SerializerConfig {

        String defaultSerializer;
        boolean userDefined;

        List types;
        List classes;


        public SerializerConfig(boolean userDefined, List types, List classes, String defaultSerializer) {
            this.userDefined = userDefined;
            this.types = types;
            this.classes = classes;
            this.defaultSerializer = defaultSerializer;
        }


        public boolean isSerializerUserDefined() {
            return this.userDefined;
        }


        public String[] getSerializerTypes() {
            return (String[]) types.toArray(new String[0]);
        }


        public String[] getSerializerClasses() {
            return (String[]) classes.toArray(new String[0]);
        }

        public String getDefaultSerializer() {
            return defaultSerializer;
        }

    }

    public static class PropertyChoiceConfig {
        String propertyName;
        String propertyParent;
        String configType;
        List displayedValues;
        List values;
        Object defaultValue;


        public PropertyChoiceConfig(String propertyName, String propertyParent, String configType, List displayedValues, List values, Object defaultValue) {
            this.displayedValues=displayedValues;
            this.values= values;
            this.defaultValue=defaultValue;
            this.propertyName=propertyName;
            this.propertyParent=propertyParent;
            this.configType=configType;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public String getPropertyParent() {
            return propertyParent;
        }

        public String getConfigType() {
            return configType;
        }
        public List getDisplayedValues() {
            return this.displayedValues;
        }

        public List getValues() {
            return this.values;
        }

        public Object getDefaultValue() {
            return defaultValue;
        }
    }

}//class
