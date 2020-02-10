package com.tibco.cep.runtime.service.tester.core;

import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_EVENT;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.EX_IS_TIME_EVENT;
import static com.tibco.cep.runtime.service.tester.core.TesterConstants.TIBCO_BE_BASE_NS;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.TimeEvent;
import com.tibco.xml.datamodel.XiNode;

/**
 * 
 * @author sasahoo
 *
 */
public class CustomTimeEventSerializer extends EntitySerializer<TimeEvent> {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(CustomTimeEventSerializer.class);
    
    public CustomTimeEventSerializer(TimeEvent entity) {
        super(entity);
    }

    public void serialize(XiNode documentNode) throws Exception {
        //Create a Time event element
        XiNode eventElement = factory.createElement(EX_EVENT);
        //Serialize base attrs
        serializeBaseAttributes(eventElement);
        String s = "be.gen.";
        //Set name and namespace
        String s1 = entity.toString().replace(s, "");
        String s2 = s1.substring(0, s1.indexOf("@"));
        String name = s2.substring(s2.lastIndexOf(".") + 1);
        LOGGER.log(Level.DEBUG, "Time Event %s", name);
        String namespace = TIBCO_BE_BASE_NS + "/" + s2.replace(".", "/"); 
        LOGGER.log(Level.DEBUG, "Time Event Namespace %s", namespace);
        eventElement.setAttributeStringValue(EX_IS_TIME_EVENT, "true");
        serializeNS(eventElement, name, namespace);
        documentNode.appendChild(eventElement);
    }
}
