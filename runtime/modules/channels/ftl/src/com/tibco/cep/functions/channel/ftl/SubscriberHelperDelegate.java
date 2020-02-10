package com.tibco.cep.functions.channel.ftl;

public class SubscriberHelperDelegate {

    public static void setRuleFunction (Object subscriber, String ruleFunction) {
        FTLSubscriber ftlSubscriber = (FTLSubscriber) subscriber;
        ftlSubscriber.setRuleFunctionName(ruleFunction);

        return;
    }
    
    public static void setNumThreads(Object subscriber, int numberOfThreads){
    	FTLSubscriber ftlSubscriber = (FTLSubscriber) subscriber;
    	ftlSubscriber.setNumThreads(numberOfThreads);
    }

    public static void setExecuteRules (Object subscriber, boolean executeRules) {
        FTLSubscriber ftlSubscriber = (FTLSubscriber) subscriber;
        ftlSubscriber.setExecuteRules(executeRules);

        return;
    }

    public static void startListening (Object subscriber) {
    	FTLSubscriber ftlSubscriber = FTLSubscriber.class.cast(subscriber);
        try {
           ftlSubscriber.startListening();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static void bind(Object subscriber, String eventUri, String messageFormat, Object contentMap){
    	try{
    		FTLSubscriber sub = (FTLSubscriber)subscriber;
    		sub.bind(eventUri, messageFormat, contentMap);
    	}
    	catch(Exception exp){
    		throw new RuntimeException(exp);
    	}
    	FTLSubscriber sub = (FTLSubscriber)subscriber; 	
    }
    
    public static void close (Object subscriber) {
        FTLSubscriber ftlSubscriber = (FTLSubscriber) subscriber;
        try {
            ftlSubscriber.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
