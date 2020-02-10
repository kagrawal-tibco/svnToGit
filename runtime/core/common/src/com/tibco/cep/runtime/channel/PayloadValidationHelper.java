package com.tibco.cep.runtime.channel;

import java.io.IOException;
import java.io.StringReader;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.xml.datamodel.XiParserFactory;
import com.tibco.xml.schema.impl.SmNamespaceProviderImpl;
import com.tibco.xml.tns.impl.TargetNamespaceCache;

/*
* Created by IntelliJ IDEA.
* User: nprade
* Date: Apr 9, 2009
* Time: 4:43:37 PM
*/
public class PayloadValidationHelper {

    public static final boolean ENABLED = Boolean.getBoolean("com.tibco.cep.runtime.channel.payload.validation");

    /**
     * Validates the event in the current RuleSession.
     * @param event SimpleEvent whose payload will be validated, if present.
     * @throws IOException upon problem.
     * @throws SAXException upon problem.
     */
    public static void validate(
            SimpleEvent event)
            throws IOException, SAXException { 
    	if(ENABLED) {
    		validateEvent(event);
    	}
    }


    /**
     * Validates the event in the current RuleSession.
     * @param event SimpleEvent whose payload will be validated, if present.
     * @throws IOException upon problem.
     * @throws SAXException upon problem.
     */
    public static void validateEvent(
            SimpleEvent event)
            throws IOException, SAXException {

            final String payloadAsString = event.getPayloadAsString();
            if (null != payloadAsString) {
                final RuleServiceProvider rsp = RuleSessionManager.getCurrentRuleSession().getRuleServiceProvider();
                final TargetNamespaceCache tnsCache = (TargetNamespaceCache) rsp.getProject().getTnsCache();
                final SmNamespaceProviderImpl nsProvider = new SmNamespaceProviderImpl(tnsCache.getNamespaceProvider());
                final InputSource is = new InputSource(new StringReader(payloadAsString));
                XiParserFactory.newInstance().parse(is, nsProvider);
            }

    }


    /**
     * Validates the event in the given SerializationContext.
     * @param event SimpleEvent whose payload will be validated, if present.
     * @param sc SerializationContext used to determine the available TNS.
     * @throws IOException upon problem.
     * @throws SAXException upon problem.
     */
    public static void validate(
            SimpleEvent event,
            SerializationContext sc)
            throws IOException, SAXException {

        if (ENABLED) {
            validateEvent(event, sc);
        }
    }
    
    /**
     * Validates the event in the given SerializationContext.
     * @param event SimpleEvent whose payload will be validated, if present.
     * @param sc SerializationContext used to determine the available TNS.
     * @throws IOException upon problem.
     * @throws SAXException upon problem.
     */
    public static void validateEvent(
            SimpleEvent event,
            SerializationContext sc)
            throws IOException, SAXException {

            final String payloadAsString = event.getPayloadAsString();
            if (null != payloadAsString) {
                final RuleServiceProvider rsp = sc.getRuleSession().getRuleServiceProvider();
                final TargetNamespaceCache tnsCache = (TargetNamespaceCache) rsp.getProject().getTnsCache();
                final SmNamespaceProviderImpl nsProvider = new SmNamespaceProviderImpl(tnsCache.getNamespaceProvider());
                final InputSource is = new InputSource(new StringReader(payloadAsString));
                XiParserFactory.newInstance().parse(is, nsProvider);
            }
        }


    /**
     * Validates the event in the given SerializationContext.
     * @param event SimpleEvent whose payload will be validated, if present.
     * @param rsp RuleServiceProvider used to determine the available TNS.
     * @throws IOException upon problem.
     * @throws SAXException upon problem.
     */
    public static void validate(
            SimpleEvent event,
            RuleServiceProvider rsp)
            throws IOException, SAXException {

        if (ENABLED) {
        	validateEvent(event, rsp);
        }
    }
    
    /**
     * Validates the event in the given SerializationContext.
     * @param event SimpleEvent whose payload will be validated, if present.
     * @param rsp RuleServiceProvider used to determine the available TNS.
     * @throws IOException upon problem.
     * @throws SAXException upon problem.
     */
    public static void validateEvent(
            SimpleEvent event,
            RuleServiceProvider rsp)
            throws IOException, SAXException {

            final String payloadAsString = event.getPayloadAsString();
            if (null != payloadAsString) {
                final TargetNamespaceCache tnsCache = (TargetNamespaceCache) rsp.getProject().getTnsCache();
                final SmNamespaceProviderImpl nsProvider = new SmNamespaceProviderImpl(tnsCache.getNamespaceProvider());
                final InputSource is = new InputSource(new StringReader(payloadAsString));
                XiParserFactory.newInstance().parse(is, nsProvider);
            }

    }

}
