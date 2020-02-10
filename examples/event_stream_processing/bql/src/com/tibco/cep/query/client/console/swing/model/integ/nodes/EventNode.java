package com.tibco.cep.query.client.console.swing.model.integ.nodes;

import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author ksubrama
 */
public class EventNode extends AbstractNode {
    private static final long serialVersionUID = 1L;
    private final Event event;
    private final Map<String, String> otherProps;

    public EventNode(Event event) {
        super(event);
        this.event = event;
        otherProps = getOtherProperties();
        initProperties();
    }

    @Override
    protected void initProperties() {
        properties.put(NAME, getNonNullValue(event.getName()));
        properties.put(ALIAS, getNonNullValue(event.getAlias()));
        properties.put(NAMESPACE, getNonNullValue(event.getNamespace()));
        properties.put(URI, getNonNullValue(event.getChannelURI()));
        properties.put(DESTINATION, getNonNullValue(event.getDestinationName()));
        properties.put(TTL, String.valueOf(event.getTTL()) + " " +
                Event.TTL_UNITS_DESCRIPTIONS[event.getTTLUnits()]);
        if(otherProps.size() != 0) {
            properties.put(OTHER_PROPS, otherProps);
        }
    }

    private Map<String, String> getOtherProperties() {
        Map<String, String> propsMap = new HashMap<String, String>();
        Iterator<? extends EventPropertyDefinition> iterator = event.getUserProperties();
        for (; iterator.hasNext();) {
            EventPropertyDefinition propertyDefinition = iterator.next();
            propsMap.put(propertyDefinition.getPropertyName(),
                    propertyDefinition.getType().getName());
        }
        return propsMap;
    }
}
