package com.tibco.cep.loadbalancer.impl.client.integ;

import java.util.concurrent.ConcurrentHashMap;

import com.tibco.cep.driver.local.LocalChannel;
import com.tibco.cep.driver.local.LocalQueueDestination;
import com.tibco.cep.loadbalancer.client.core.MessageCustomizer;
import com.tibco.cep.loadbalancer.message.DistributableMessage;
import com.tibco.cep.loadbalancer.message.DistributableMessage.KnownHeader;
import com.tibco.cep.loadbalancer.util.Utils;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.util.annotation.LogCategory;

/*
* Author: Ashwin Jayaprakash / Date: Apr 14, 2010 / Time: 11:32:29 AM
*/

@LogCategory("loadbalancer.be.client.destination.local")
public class PseudoLocalDestination extends PseudoDestination<LocalChannel, LocalQueueDestination> {
    @Override
    protected MessageCustomizer createMessageCustomizer() {
        return new LocalMessageCustomizer();
    }

    @Override
    public Object unpackAsEventOrEventContext(Object message) {
        return message;
    }

    //--------------

    protected static class LocalMessageCustomizer extends AbstractMessageCustomizer {
        static final ConcurrentHashMap<Class, ConcurrentHashMap<String, Object>> headersOfEvents =
                new ConcurrentHashMap<Class, ConcurrentHashMap<String, Object>>();

        @Override
        public DistributableMessage customize(DistributableMessage distributableMessage) throws Exception {
            byte[] bytes = (byte[]) distributableMessage.getContent();
            SimpleEvent event = Utils.$deserializeEvent(bytes);
            distributableMessage.setContent(event);

            return super.customize(distributableMessage);
        }

        private void setProp(DistributableMessage distributableMessage, KnownHeader knownHeader, Object value)
                throws Exception {
            SimpleEvent simpleEvent = (SimpleEvent) distributableMessage.getContent();

            ConcurrentHashMap<String, Object> headers = headersOfEvents.get(simpleEvent.getClass());
            if (headers == null) {
                headers = new ConcurrentHashMap<String, Object>();
                String[] strings = simpleEvent.getPropertyNames();
                for (String string : strings) {
                    headers.put(string, string);
                }

                headersOfEvents.putIfAbsent(simpleEvent.getClass(), headers);

                headers = headersOfEvents.get(simpleEvent.getClass());
            }

            if (headers.contains(knownHeader.name())) {
                simpleEvent.setProperty(knownHeader.name(), value);
            }
        }

        @Override
        protected void setBoolean(DistributableMessage distributableMessage, KnownHeader knownHeader, boolean value)
                throws Exception {
            setProp(distributableMessage, knownHeader, value);
        }

        @Override
        protected void setString(DistributableMessage distributableMessage, KnownHeader knownHeader, String value)
                throws Exception {
            setProp(distributableMessage, knownHeader, value);
        }

        @Override
        protected void setLong(DistributableMessage distributableMessage, KnownHeader knownHeader, long value)
                throws Exception {
            setProp(distributableMessage, knownHeader, value);
        }
    }
}