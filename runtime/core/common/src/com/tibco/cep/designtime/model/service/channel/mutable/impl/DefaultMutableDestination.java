package com.tibco.cep.designtime.model.service.channel.mutable.impl;


import java.util.Properties;

import com.tibco.be.model.util.EntityNameHelper;
import com.tibco.cep.designtime.model.BEModelBundle;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.Ontology;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.service.channel.Channel;
import com.tibco.cep.designtime.model.service.channel.DestinationDescriptor;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.model.service.channel.PropertyDescriptor;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableChannel;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableDestination;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableDriverConfig;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/*
 * User: nprade
 * Date: Jul 22, 2004
 * Time: 8:35:50 PM
 */


public class DefaultMutableDestination extends AbstractMutableEntity implements MutableDestination {


    protected static final ExpandedName NAME_DESTINATION = ExpandedName.makeName("destination");
    protected static final ExpandedName NAME_SERIALIZERDESERIALIZER_CLASS = ExpandedName.makeName("serializer");
    protected static final ExpandedName NAME_NAME = ExpandedName.makeName("name");
    protected static final ExpandedName NAME_EVENT = ExpandedName.makeName("event");
    protected static final ExpandedName NAME_NAMESPACE = ExpandedName.makeName("namespace");
    protected static final ExpandedName NAME_INPUT = ExpandedName.makeName("isInput");
    protected static final ExpandedName NAME_OUTPUT = ExpandedName.makeName("isOutput");
    protected static final ExpandedName NAME_PROPERTIES = ExpandedName.makeName("properties");
    protected static final ExpandedName NAME_INSTANCE = ExpandedName.makeName("instance");

    protected String m_name;
    protected String m_serializerDeserializerClass;
    protected MutableDriverConfig m_driverConfig;
    protected boolean m_input;
    protected boolean m_output;
    protected Properties m_properties;
    protected String m_eventURI;

    public DefaultMutableDestination(String name, MutableDriverConfig driverConfig) throws ModelException {
        this(name, driverConfig, false, true, null, "", "");
    }//constr


    public DefaultMutableDestination(
            String name,
            MutableDriverConfig driverConfig,
            boolean input,
            boolean output,
            Properties properties,
            String eventURI,
            String sdClass)
            throws ModelException {

        super(null, null, name);

        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }//if
        else if (driverConfig == null) {
            throw new IllegalArgumentException("driver config is null");
        }//if

        this.m_properties = new Properties();
        final DestinationDescriptor dd = driverConfig.getDestinationDescriptor();
        for (PropertyDescriptor propertyDescriptor : dd.values()) {
            this.m_properties.put(propertyDescriptor.getName(), propertyDescriptor.getDefaultValue());
        }

        if (properties != null) {
            for (String pName: properties.stringPropertyNames()) {
                if (!dd.containsKey(pName)) {
                    throw new ModelException("invalid property name: " + pName);
                }//if
                this.m_properties.setProperty(pName, properties.getProperty(pName));
            }
        }

        if (eventURI == null) {
            eventURI = "";
        }//if
        m_name = name;
        m_driverConfig = driverConfig;
        m_input = input;
        m_output = output;
        m_eventURI = eventURI;
        m_serializerDeserializerClass = sdClass;
    }//constr


    /**
     * @return the DriverConfig that contains this Destination.
     */
    public DriverConfig getDriverConfig() {
        return m_driverConfig;
    }


    /**
     * @return true iif this Destination is enabled for Input.
     */
    public boolean isInputEnabled() {
        return m_input;
    }//isInputEnabled


    /**
     * Enables / disables this Destination for Input.
     *
     * @param enabled true to enable Input, false to disable Input.
     * @return the Destination itself.
     */
    public MutableDestination setInputEnabled(boolean enabled) {
        m_input = enabled;
        return this;
    }//setInputEnabled


    /**
     * @return true iif this Destination is enabled for Output.
     */
    public boolean isOutputEnabled() {
        return m_output;
    }


    /**
     * Enables / disables this Destination for Output.
     *
     * @param enabled true to enable Output, false to disable Output.
     * @return the Destination itself.
     */
    public MutableDestination setOutputEnabled(boolean enabled) {
        m_output = enabled;
        return this;
    }//setOutputEnabled


    /**
     * @return String the name of this Destination
     */
    public String getType() {
        return m_name;
    }//getName  */


    public String getSerializerDeserializerClass() {
        return m_serializerDeserializerClass;
    }


    public void setSerializerDeserializerClass(String sdc) {
        m_serializerDeserializerClass = sdc.trim();
    }


    public void setName(String name, boolean renameOnConflict) throws ModelException {
        final BEModelBundle bundle = BEModelBundle.getBundle();

        if ((null == name) || (0 == name.length())) {
            final String msg = bundle.getString(AbstractMutableEntity.EMPTY_NAME_KEY);
            throw new ModelException(msg);
        }//if

        if (name.equals(m_name)) {
            return;
        }//if

        final MutableChannel channel = (MutableChannel) m_driverConfig.getChannel();
        final String channelPath = channel.getFullPath();

        if (null != m_driverConfig.getDestination(name)) {
            final String msg = bundle.formatString(NAME_CONFLICT_KEY, name, channelPath);
            throw new ModelException(msg);
        }//if

        m_driverConfig.removeDestination(this);
        m_name = name;
        m_driverConfig.addDestination(this);

        //pathChanged(channelPath, oldName, channelPath, m_name);

        notifyListeners();
        DefaultMutableChannel dc = (DefaultMutableChannel) channel;
        dc.notifyOntologyOnChange();
    }//setName


    public Properties getProperties() {
        return this.m_properties;
    }//getProperties


    /**
     * Returns the name of the Destination
     *
     * @return a String
     */
    public String getName() {
        return m_name;
    }//getname


    /**
     * Returns the namespace of the Event associated to this Destination.
     *
     * @return a String
     */
    public String getEventURI() {
        return m_eventURI;
    }


    /**
     * Stores the URI of the event associated to this Destination
     *
     * @param uri a String.
     * @return the Destination itself
     */
    public MutableDestination setEventURI(String uri) {
        if (uri == null) {
            m_eventURI = "";
        } else {
            m_eventURI = uri;
        }//else
        return this;
    }//setEventURI


    public Ontology getOntology() {
        if (m_driverConfig == null) {
            return null;
        }
        Channel channel = m_driverConfig.getChannel();

        if (channel == null) {
            return null;
        }
        return channel.getOntology();
    }


    /**
     * Creates the XiNode representation of this Destination.
     *
     * @return an XiNode.
     */
    public XiNode toXiNode(XiFactory factory) {
//        final XiNode destinationNode = factory.createElement(NAME_DESTINATION);
//        destinationNode.setAttributeStringValue(NAME_NAME, getName());
//
//        try {
//            final String eventURI = getEventURI();
//            final XiNode eventNode = destinationNode.appendElement(NAME_EVENT);
//            eventNode.setStringValue(eventURI);
//            final String nameSpace;
//            final Ontology ontology = getDriverConfig().getChannel().getOntology();
//            if (null == ontology) {
//                final int indexOfLastSlash = (null == eventURI) ? -1 : eventURI.lastIndexOf("/");
//                if (indexOfLastSlash < 0) {
//                    nameSpace = null;
//                } else {
//                    nameSpace = eventURI.substring(0, indexOfLastSlash);
//                }
//            } else {
//                nameSpace = ontology.getEvent(eventURI).getNamespace();
//            }
//            eventNode.setAttributeStringValue(NAME_NAMESPACE, nameSpace);
////            destinationNode.appendChild(eventNode);
//        } catch (Exception ignored) {
////            ignored.printStackTrace();
//        }//catch
//
//        destinationNode.setAttributeStringValue(NAME_SERIALIZERDESERIALIZER_CLASS, getSerializerDeserializerClass());
//        final XiNode propsNode = destinationNode.appendElement(NAME_PROPERTIES);
//        final DefaultMutableInstance props = (DefaultMutableInstance) getProperties();
//        propsNode.appendChild(props.toXiNode(factory));
//
//        return destinationNode;
        throw new UnsupportedOperationException();
    }//toXiNode


    public static DefaultMutableDestination createDefaultDestinationFromNode(XiNode root, MutableDriverConfig driverConfig)
            throws ModelException {

        final String name = EntityNameHelper.convertToEntityIdentifier(root.getAttributeStringValue(NAME_NAME));
        final String description = root.getAttributeStringValue(DESCRIPTION_NAME);
        //final String nameSpace   = root.getAttributeStringValue(NAME_NAMESPACE);
        final boolean isInput = XiChild.getBoolean(root, NAME_INPUT, false);
        final boolean isOutput = XiChild.getBoolean(root, NAME_OUTPUT, false);

        final XiNode eventNode = XiChild.getChild(root, NAME_EVENT);
        final String eventURI = (null == eventNode) ? null : eventNode.getStringValue();

        // if not defined - set an empty string; else set self.
        final String sdClass = ((root.getAttributeStringValue(NAME_SERIALIZERDESERIALIZER_CLASS) == null) ?
                "" : root.getAttributeStringValue(NAME_SERIALIZERDESERIALIZER_CLASS));

        final XiNode propsNode = XiChild.getChild(root, NAME_PROPERTIES);
        final XiNode instanceNode = XiChild.getChild(propsNode, NAME_INSTANCE);
        final Properties properties = DeserializationHelper.readInstanceAsProperties(instanceNode);

        final DefaultMutableDestination dd;
        dd = new DefaultMutableDestination(name, driverConfig, isInput, isOutput, properties, eventURI, sdClass);
        dd.setDescription(description);
        driverConfig.addDestination(dd);

        return dd;
    }//createDefaultDestinationFromNode


}//class
