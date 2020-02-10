package com.tibco.cep.runtime.service.management.agent.ontology;

import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.element.Property;
import com.tibco.cep.runtime.model.event.SimpleEvent;

import javax.management.ObjectName;

/**
 * Created with IntelliJ IDEA.
 * User: hlouro
 * Date: 10/23/12
 * Time: 5:42 PM
 */
public class SimpleEventMBean extends OntologyMBean {
    private SimpleEvent se;

    public SimpleEventMBean(SimpleEvent se, ObjectName on, Logger logger) {
        super(on, logger);
        this.se = se;
        reLoadProps();
    }


    @Override
    protected void reLoadProps() {
        if (se == null) {
            props = null;
            return;
        }

        final String[] pns = se.getPropertyNames();

        if (pns == null || pns.length == 0)
            return;

        props = new Property[pns.length];

        for (int i=0; i<pns.length; i++) {
            try {
                Object p = se.getProperty(pns[i]);
                if(p instanceof Property)
                    props[i] = (Property) p;
            } catch (NoSuchFieldException e) {
                logger.log(Level.DEBUG, e, e.getMessage());
            }

        }
    }
}
