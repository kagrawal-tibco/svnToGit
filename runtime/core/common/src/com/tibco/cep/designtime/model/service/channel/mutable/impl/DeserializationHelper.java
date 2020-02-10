package com.tibco.cep.designtime.model.service.channel.mutable.impl;


import java.util.Iterator;
import java.util.Properties;

import com.tibco.xml.data.primitive.ExpandedName;
import com.tibco.xml.datamodel.XiNode;
import com.tibco.xml.datamodel.helpers.XiChild;

/*
* User: Nicolas Prade
* Date: Oct 1, 2009
* Time: 6:08:22 PM
*/


public class DeserializationHelper {

    protected static final ExpandedName XNAME_DEFINITION = ExpandedName.makeName("definition");
    protected static final ExpandedName XNAME_PROPERTIES = ExpandedName.makeName("properties");
    protected static final ExpandedName XNAME_PROPERTY_INSTANCE = ExpandedName.makeName("propertyInstance");
    protected static final ExpandedName XNAME_VALUE = ExpandedName.makeName("value");


    public static Properties readInstanceAsProperties(
            XiNode instanceNode) {

        final Properties props = new Properties();

        final XiNode propsNode = XiChild.getChild(instanceNode, XNAME_PROPERTIES);

        for (Iterator i = XiChild.getIterator(propsNode, XNAME_PROPERTY_INSTANCE); i.hasNext(); ) {
            final XiNode pi = (XiNode) i.next();
            props.setProperty(
                    pi.getAttributeStringValue(XNAME_DEFINITION),
                    pi.getAttributeStringValue(XNAME_VALUE));
        }

        return props;
    }

}
