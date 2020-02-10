/**
 * @author ishaan
 * @version May 18, 2004, 2:51:57 PM
 */
package com.tibco.cep.designtime.model.service.channel.mutable.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import com.tibco.cep.designtime.model.Folder;
import com.tibco.cep.designtime.model.ModelException;
import com.tibco.cep.designtime.model.ModelUtils;
import com.tibco.cep.designtime.model.event.mutable.impl.DefaultMutableEvent;
import com.tibco.cep.designtime.model.mutable.impl.AbstractMutableEntity;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableFolder;
import com.tibco.cep.designtime.model.mutable.impl.DefaultMutableOntology;
import com.tibco.cep.designtime.model.service.channel.DriverConfig;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableChannel;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableDriverConfig;
import com.tibco.cep.designtime.model.service.channel.mutable.MutableDriverManager;
import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiFactory;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;


public class DefaultMutableChannel extends AbstractMutableEntity implements MutableChannel {


    protected static final ExpandedName NAME_CHANNEL = ExpandedName.makeName("channel");
    protected static final ExpandedName NAME_CONFIGMETHOD = ExpandedName.makeName("configMethod");
    protected static final ExpandedName NAME_DESCRIPTION = ExpandedName.makeName("description");
    protected static final ExpandedName NAME_DESTINATIONS = ExpandedName.makeName("destinations");
    protected static final ExpandedName NAME_FOLDER = ExpandedName.makeName("folder");
    protected static final ExpandedName NAME_GUID = ExpandedName.makeName("guid");
    protected static final ExpandedName NAME_INSTANCE = ExpandedName.makeName("instance");
    protected static final ExpandedName NAME_NAME = ExpandedName.makeName("name");
    protected static final ExpandedName NAME_PROPERTIES = ExpandedName.makeName("properties");
    protected static final ExpandedName NAME_REFERENCE = ExpandedName.makeName("reference");
    protected static final ExpandedName NAME_TYPE = ExpandedName.makeName("type");
    protected static final ExpandedName NAME_VERSION = ExpandedName.makeName("version");
    protected static final ExpandedName NAME_SERVER_TYPE = ExpandedName.makeName("serverType");


    public static MutableDriverManager DRIVER_MANAGER = new DefaultMutableDriverManager();

    protected MutableDriverConfig m_driver;


    public DefaultMutableChannel(DefaultMutableOntology ontology, DefaultMutableFolder folder, String name) {
        super(ontology, folder, name);
        m_driver = null;
    }//constr


    /**
     * Creates the XiNode representation of this MutableChannel.
     *
     * @return an XiNode.
     */
    public XiNode toXiNode(XiFactory factory) {
//        final XiNode channelNode = factory.createElement(NAME_CHANNEL);
//
//        channelNode.setAttributeStringValue(NAME_FOLDER, m_folder);
//        channelNode.setAttributeStringValue(NAME_NAME, m_name);
//        channelNode.setAttributeStringValue(NAME_DESCRIPTION, m_description);
//
//        final DriverConfig driver = getDriver();
//        if (driver != null) {
//            final String driverType = driver.getDriverType();
//            channelNode.appendElement(NAME_TYPE, driverType);
//
//            final String version = DRIVER_MANAGER.getVersion(driverType);
//            channelNode.appendElement(NAME_VERSION, version);
//
//            final String serverType = driver.getServerType();
//            if (serverType != null) {
//                channelNode.appendElement(NAME_SERVER_TYPE, serverType);
//            }
//
//            final String configMethod = driver.getConfigMethod();
//            channelNode.appendElement(NAME_CONFIGMETHOD, configMethod);
//
//            if (DriverConfig.CONFIG_BY_REFERENCE.equals(configMethod)) {
//                final String reference = driver.getReference();
//                channelNode.appendElement(NAME_REFERENCE, reference);
//            } else {
//                if (DriverConfig.CONFIG_BY_PROPERTIES.equals(configMethod)) {
//                    final XiNode params = channelNode.appendElement(NAME_PROPERTIES);
//                    final DefaultMutableInstance instance = (DefaultMutableInstance) driver.getChannelProperties();
//                    final XiNode instanceNode = instance.toXiNode(factory);
//                    params.appendChild(instanceNode);
//                }//if
//            }//else
//
//            final XiNode destinationsNode = channelNode.appendElement(NAME_DESTINATIONS);
//            for (Iterator it = driver.getDestinations(); it.hasNext();) {
//                final DefaultMutableDestination destination = (DefaultMutableDestination) it.next();
//                destinationsNode.appendChild(destination.toXiNode(factory));
//            }//for
//
//        }//if
//        return channelNode;
        throw new UnsupportedOperationException();
    }//toXiNode


    public void pathChanged(String oldPath, String newPath) throws ModelException {
        super.pathChanged(oldPath, newPath);

        if (m_ontology == null) {
            return;
        }
        if (ModelUtils.IsEmptyString(oldPath)) {
            return;
        }
        if (newPath == null) {
            newPath = "";
        }

        Iterator it = m_driver.getDestinations();
        while (it.hasNext()) {
            DefaultMutableDestination dd = (DefaultMutableDestination) it.next();
            DefaultMutableEvent event = (DefaultMutableEvent) m_ontology.getEvent(dd.m_eventURI);
            if (event == null) {
                continue;
            }

            if (oldPath.equals(event.getChannelURI())) {
                event.setChannelURI(newPath);
            }
        }
    }


    public DriverConfig getDriver() {
        return m_driver;
    }


    public static final String BAD_DRIVER_NAME_KEY = "DefaultMutableChannel.setDriver.badDriverName";
    public static final String NULL_DRIVER_KEY = "DefaultMutableChannel.setDriver.nullDriver";


    public MutableChannel setDriver(String driverClassName) {
        final MutableDriverConfig driver = DRIVER_MANAGER.createDriverConfig(driverClassName, this);
        if (driver == null) {
            throw new IllegalArgumentException(BAD_DRIVER_NAME_KEY);
        }//if
        return setDriver(driver);
    }//setDriver


    public MutableChannel setDriver(MutableDriverConfig driver) {
        if (driver == null) {
            throw new IllegalArgumentException(NULL_DRIVER_KEY);
        }//if
        m_driver = driver;
        return this;
    }//setDriver


    public static DefaultMutableChannel createDefaultMutableChannelFromNode(XiNode root) throws ModelException {
        final String name = root.getAttributeStringValue(NAME_NAME);
        final String description = root.getAttributeStringValue(NAME_DESCRIPTION);
        final String folderPath = root.getAttributeStringValue(NAME_FOLDER);

        final DefaultMutableFolder rootFolder = new DefaultMutableFolder(null, null, String.valueOf(Folder.FOLDER_SEPARATOR_CHAR));
        final DefaultMutableFolder folder = DefaultMutableOntology.createFolder(rootFolder, folderPath, false);

        final DefaultMutableChannel dch = new DefaultMutableChannel(null, folder, name);
        dch.setDescription(description);


        final String type = XiChild.getString(root, NAME_TYPE);
        final String serverType = XiChild.getString(root, NAME_SERVER_TYPE);

//        final String version = XiChild.getString(root, NAME_VERSION);
//        final String registeredVersion = DefaultMutableChannel.DRIVER_MANAGER.getVersion(type);

        final List emptyDestList = new ArrayList();
        MutableDriverConfig driver = null;
        final String configMethod = XiChild.getString(root, NAME_CONFIGMETHOD);

        if (DriverConfig.CONFIG_BY_REFERENCE.equals(configMethod)) {
            final String reference = XiChild.getString(root, NAME_REFERENCE);
            driver = new DefaultMutableDriverConfig(dch, type, DriverConfig.CONFIG_BY_REFERENCE, reference, emptyDestList);
            driver.setServerType(serverType);
            dch.setDriver(driver);


        } else if (DriverConfig.CONFIG_BY_PROPERTIES.equals(configMethod)) {
            final XiNode propertiesNode = XiChild.getChild(root, NAME_PROPERTIES);
            final XiNode instanceNode = XiChild.getChild(propertiesNode, NAME_INSTANCE);
            final Properties properties = DeserializationHelper.readInstanceAsProperties(instanceNode);
            driver = new DefaultMutableDriverConfig(dch, type, DriverConfig.CONFIG_BY_PROPERTIES,
                    properties, emptyDestList);
            driver.setServerType(serverType);
            dch.setDriver(driver);
        }//else

        final XiNode destinationsNode = XiChild.getChild(root, NAME_DESTINATIONS);
        for (Iterator it = destinationsNode.getChildren(); it.hasNext();) {
            final XiNode destinationNode = (XiNode) it.next();
            DefaultMutableDestination.createDefaultDestinationFromNode(destinationNode, driver);
        }//for

        return dch;
    }//createDefaultMutableChannelFromNode

}//class
