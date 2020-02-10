package com.tibco.cep.runtime.service.management.agent.ontology;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.element.PropertyArray;
import com.tibco.cep.runtime.model.element.PropertyAtom;
import com.tibco.cep.runtime.service.management.agent.impl.StatsMBeanException;

import javax.management.*;
import java.lang.management.ManagementFactory;

/**
 * Created with IntelliJ IDEA.
 * User: hlouro
 * Date: 10/15/12
 * Time: 6:54 PM
 */

public abstract class OntologyMBean implements DynamicMBean {
    protected Property[] props;
    private ObjectName on;
    protected Logger logger;

    OntologyMBean(ObjectName on, Logger logger) {
        this.on = on;
        this.logger = logger;
    }

    @Override
    //This method is only called if called from an external API.
    //It is not called by the not JConsole or JVisualVM.
    //Since it's unlikely that it will at all be used, I opted to
    //sacrifice a bit the efficiency of the individual attribute search,
    //in favor of the efficiency of other operations.
    //Furthermore, there should be only a few properties, so it should be
    //a very efficient algorithm in practice
    public synchronized Object getAttribute(String attName) throws
            AttributeNotFoundException,
            MBeanException,
            ReflectionException {

        if(props != null && attName != null && !attName.trim().isEmpty()) {
            try {
                for (Property prop : props) {
                    if (isValidProp(prop)) {
                        if (prop.getName().equals(attName))
                            //for now ignore props that are not PropertyAtom
                            if (prop instanceof PropertyAtom)
                                ((PropertyAtom) prop).getValue();
                            else if  (prop instanceof PropertyArray)
                                ((PropertyArray) prop).toArray();
                    }
                }
            } catch (Exception e) {
                logger.log(Level.DEBUG, "Exception occurred while " +
                           "adding attribute '%s' to attributes list", attName);
            }
        }

        throw new AttributeNotFoundException("No such property: " + attName);
    }

    @Override
    public synchronized void setAttribute(Attribute attribute) throws
            AttributeNotFoundException,
            InvalidAttributeValueException,
            MBeanException,
            ReflectionException {
        //No need to do anything because attributes are read only
    }

    @Override
    public synchronized AttributeList getAttributes(String[] attrNames) {
        reLoadProps();

        if (props == null)
            return null;

        AttributeList aList = new AttributeList();
        for (Property prop : props) {
            try {
                if (isValidProp(prop)) {
                    if (prop instanceof PropertyAtom) {
                        aList.add( new Attribute(prop.getName(),
                                   ((PropertyAtom) prop).getValue()) );
                    } else if (prop instanceof PropertyArray) {
                        aList.add( new Attribute(prop.getName(),
                                   ((PropertyArray) prop).toArray()) );
                    }
                }
            } catch (Exception e) {
                logger.log(Level.DEBUG, "Exception occurred while adding attribute" +
                           " '%s' to attributes list", prop.getName());
            }
        }
        return aList;
    }

    @Override
    public synchronized AttributeList setAttributes(AttributeList attributes) {
        return null;  //No need to do anything because attributes are read only
    }

    @Override
    public synchronized Object invoke(String actionName,
                                      Object[] params,
                                      String[] signature) throws MBeanException,
                                                                 ReflectionException {
        if (actionName.equals("Remove") &&
                (params == null || params.length == 0) &&
                (signature == null || signature.length == 0)) {
            remove();
            return null;
        }

        throw new ReflectionException(new NoSuchMethodException(actionName));
    }

    @Override
    public synchronized MBeanInfo getMBeanInfo() {
        reLoadProps();

        MBeanAttributeInfo[] attrs = new MBeanAttributeInfo[props.length];

        for (int i = 0; i < attrs.length; i++) {
            if (!isValidProp(props[i])) {
                continue;
            }

            final String attName = props[i].getName();

            attrs[i] = new MBeanAttributeInfo(
                    attName,
                    "java.lang.String",
                    attName,
                    true,   // is readable
                    false,  // is writable
                    false); // is is
        }

        MBeanOperationInfo[] opers = {
                new MBeanOperationInfo(
                        "Remove",
                        "Remove this MBean from the watched Stats list",
                        null,   // no parameters
                        "void",
                        MBeanOperationInfo.ACTION)
        };

        return new MBeanInfo(
                this.getClass().getName(),
                "Ontology Object MBean",
                attrs,
                null,  //no constructors
                opers,
                null); //no notifications
    }

    protected abstract void reLoadProps();

    protected boolean isValidProp(Property  prop) {
        return prop != null &&
               !prop.getName().trim().isEmpty();
    }

    private void remove() throws MBeanRegistrationException,
                                 ReflectionException,
                                 StatsMBeanException {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            if (mbs.isRegistered(on))
                mbs.unregisterMBean(on);
        } catch (InstanceNotFoundException e) {
            throw new StatsMBeanException(e);
        }
    }
}
