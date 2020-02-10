package com.tibco.cep.runtime.service.tester.core;

import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_EVENT;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_PAYLOAD;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_PROPERTY;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_PROPERTY_NAME;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.xml.data.primitive.XmlTypedValue;
import com.tibco.xml.datamodel.XiNode;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: May 5, 2010
 * Time: 5:15:53 AM
 * <!--
 * Add Description of the class here
 * -->
 */
public class CustomEventSerializer extends EntitySerializer<SimpleEvent> {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(CustomEventSerializer.class);

    public CustomEventSerializer(SimpleEvent entity) {
        super(entity);
    }

    public void serialize(XiNode documentNode) throws Exception {
        //Create a event element

        XiNode eventElement = factory.createElement(EX_EVENT);

        //Serialize base attrs
        serializeBaseAttributes(eventElement);
        //Set name and namespace
        String namespace = entity.getExpandedName().getNamespaceURI();
        String name = entity.getExpandedName().getLocalName();

        serializeNS(eventElement, name, namespace);

        String[] propertyNames = entity.getPropertyNames();

        serializeProperties(propertyNames, eventElement);

        documentNode.appendChild(eventElement);
    }

    protected void serializeProperties(String[] propertyNames, XiNode eventElement) throws Exception {

        for (String propertyName : propertyNames) {

            LOGGER.log(Level.TRACE, "Event Property Name %s", propertyName);

            XmlTypedValue propertyValue = entity.getPropertyAsXMLTypedValue(propertyName);

            if (propertyValue != null) {

                XiNode propertyElement = factory.createElement(EX_PROPERTY);
                propertyElement.setAttributeStringValue(EX_PROPERTY_NAME, propertyName);

                LOGGER.log(Level.TRACE, "Event Property Value %s", propertyValue);
                //Set its value
                propertyElement.appendText(propertyValue);

                eventElement.appendChild(propertyElement);
            }
        }
    }

    protected void serializePayload(XiNode eventElement) {
        String payloadString = entity.getPayloadAsString();

        if (payloadString != null) {

            XiNode payloadElement = factory.createElement(EX_PAYLOAD);

            payloadElement.setStringValue(payloadString);

            eventElement.appendChild(payloadElement);
        }
    }
}
