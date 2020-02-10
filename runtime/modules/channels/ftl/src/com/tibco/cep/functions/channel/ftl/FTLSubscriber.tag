/*
 * $HeadURL:  $ $Revision:  $ $Date: $
 *
 * Copyright(c) 2007-2012 TIBCO Software Inc. All rights reserved.
 *
 * cep-modules.jar Version Information
 *
 */

/*





        AUTOMATICALLY GENERATED AT BUILD TIME !!!!

        DO NOT EDIT !!!





 * "FTLSuscriber.java" is automatically generated at
 * build time from "FTLSubscriber.tag"
 *
 * Any maintenance changes MUST be applied to "FTLSubscriber.tag"
 * and an official build triggered to propagate such changes to
 * "FTLSubscriber.java"
 *
 * If maintenance changes must be applied immediately without going
 * through an official build, then they MUST be applied to *BOTH*
 * "FTLSubscriber.tag" *AND* "FTLSubscriber.java"
 *
 */
 
 package com.tibco.cep.functions.channel.ftl;
 
 import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.ftl.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FTLSubscriber implements SubscriberListener {
	
	Realm realm;
	String endPointName;
	String ruleFunctionName;
	String requestEventType;
	boolean executeRules;
	Subscriber nativeSubscriber;
	EventQueue eventQueue;
	ExecutorService executorService;
	String contentMatcher;
	TibProperties subscriberProps;
	private RuleSessionImpl ruleSession;
	private RuleFunction ruleFunc;
	
	public FTLSubscriber(Object realm, String endPointName, Object subscriberProps){
		this.realm = (Realm) realm;
		this.endPointName = endPointName;
		this.subscriberProps = (TibProperties) subscriberProps;
		executorService = Executors.newSingleThreadExecutor();
	}
	
	public String getRuleFunctionName() {
		return ruleFunctionName;
	}
	
	public void setRuleFunctionName(String ruleFunctionName){
		this.ruleFunctionName = ruleFunctionName;
	}
	
	public String getRequestEventType() {
		return requestEventType;
	}
	
	public void setRequestEventType(String requestEventType) {
		this.requestEventType = requestEventType;
	}
	
	public boolean isExecuteRules() {
		return executeRules;
	}
	
	public void setExecuteRules(boolean executeRules){
		this.executeRules = executeRules;
	}
	
	public Subscriber getNativeSubscriber() {
		return nativeSubscriber;
	}
	
	public void setNativeSubscriber(Subscriber nativeSubscriber){
		this.nativeSubscriber = nativeSubscriber;
	}
	
	public String getContentMatcher() {
        return contentMatcher;
    }

    public void setContentMatcher(String contentMatcher) {
        this.contentMatcher = contentMatcher;
    }
    
        public void startListening(TibProperties eventQproperties) throws Exception {
        ContentMatcher cm = this.contentMatcher == null ? null : realm.createContentMatcher(this.contentMatcher);
        this.nativeSubscriber = this.realm.createSubscriber(endPointName, cm, this.subscriberProps);

        this.eventQueue = realm.createEventQueue(eventQproperties);
        this.eventQueue.addSubscriber(this.nativeSubscriber, this);

        ruleSession = (RuleSessionImpl) getDefaultRuleSession();

        if (ruleSession == null) throw new RuntimeException("No Rulesession configured...");
        if (this.ruleFunctionName == null) throw new RuntimeException("No Rulefunction configured...");

        ruleFunc = ruleSession.getRuleFunction(this.ruleFunctionName);

        this.executorService.submit(new Runnable() {

            @Override
            public void run() {
                while(true) {
                    try {
                        FTLSubscriber.this.eventQueue.dispatch();
                    } catch (FTLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void messagesReceived(List<Message> messages, EventQueue eventQueue) {
        try {
            for (Message msg : messages) {
                SimpleEvent se = this.requestEventType == null ? null : transform2Event(msg);
                Object[] args = new Object[] {se == null ? msg : se} ;
                if (executeRules)
                    ruleSession.preprocessPassthru(ruleFunc, se);
                else
                    ruleSession.invokeFunction(ruleFunc, args, true);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private RuleSession  getDefaultRuleSession() {
        RuleSession[] ruleSessions = RuleServiceProviderManager.getInstance().getDefaultProvider().getRuleRuntime().getRuleSessions();
        if (ruleSessions.length  > 0 ) return ruleSessions[0];
        return null;
    }

    public void close() {
        try {
            this.eventQueue.destroy();
            this.nativeSubscriber.close();
            this.executorService.shutdown();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected SimpleEvent transform2Event(Message msg) throws Exception  {

        SimpleEvent se = (SimpleEvent) ruleSession.getRuleServiceProvider().getTypeManager().createEntity(requestEventType);

        for (String propName : se.getPropertyNames()) {
            MessageFieldRef mfr = MessageHelper.getOrCreateRef(this.realm, propName);

            int fieldType = msg.getFieldType(mfr);
            switch (fieldType) {
                case Message.TIB_FIELD_TYPE_LONG:
                    se.setProperty(propName, msg.getLong(mfr));
                    break;

                case Message.TIB_FIELD_TYPE_DOUBLE:
                    se.setProperty(propName, msg.getDouble(mfr));
                    break;

                case Message.TIB_FIELD_TYPE_STRING:
                    se.setProperty(propName, msg.getString(mfr));
                    break;

                case Message.TIB_FIELD_TYPE_DATETIME:
                    TibDateTime tdt = msg.getDateTime(mfr);
                    Calendar cal = new GregorianCalendar();
                    cal.setTime(tdt.toDate());
                    se.setProperty(propName, cal);
                    break;

                case Message.TIB_FIELD_TYPE_OPAQUE:
                default:
                    break;
            }
        }
        return se;
    }
   }
