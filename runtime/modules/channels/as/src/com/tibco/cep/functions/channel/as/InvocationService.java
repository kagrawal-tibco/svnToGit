package com.tibco.cep.functions.channel.as;

import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.remote.Invocable;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.util.Set;

/*
* Author: Suresh Subramani / Date: 9/14/12 / Time: 5:51 PM
*/

public class InvocationService implements Invocable {

    static ThreadLocal<InvocationService> services = new ThreadLocal<InvocationService>();

    private SimpleEvent responseEvent;

    private boolean isCanceled;

    private String cancelReason;

    public InvocationService() {
    }

    @Override
    public Tuple invoke(Space space, Tuple keyTuple, Tuple contextTuple) {
        try {
//            if (true) {
//                Tuple t = Tuple.create();
//                t.putLong("invocation-time", 0);
//                   t.putLong("xform-time", 0);
//                return t;
//            }
            long sttime = System.currentTimeMillis();
            Tuple returnTuple = null;
            RuleSession ruleSession = getDefaultRuleSession();
            if (ruleSession == null) return null; // No RuleSession configured.
            //String serviceName = String.format("%s.%s", space.getMetaspaceName(), space.getName());
            //InvocationServiceDefinition svcDefn = InvocationServiceHelper.getServiceDefinition(serviceName);
            //if (svcDefn == null) return null;
            SimpleEvent se = null;
            String eventType = space.getSpaceDef().getContext().getString("service$inputType");
            boolean useOptimizedSer = Boolean.parseBoolean(space.getSpaceDef().getContext().getString("service$serialization$enabled"));
            se = transformTuple2EventEx(contextTuple, eventType, useOptimizedSer);
            long txtime = System.currentTimeMillis();
            //ruleSession.assertObject(se, false);
            services.set(this);
            String ruleFuncString = space.getSpaceDef().getContext().getString("service$bindingfunction");
            RuleFunction ruleFunc = ((RuleSessionImpl)ruleSession).getRuleFunction(ruleFuncString);
            if (ruleFunc != null){
                boolean executeRules = space.getSpaceDef().getContext().getBoolean("service$executerules");
                if (executeRules)
                    ((RuleSessionImpl)ruleSession).preprocessPassthru(ruleFunc, se);
                else
                    ruleSession.invokeFunction(ruleFuncString, new Object[] {se}, true);
            }

            //TODO cancel processing.
            if (responseEvent != null) {
                returnTuple = transformEvent2Tuple(responseEvent);
                long endtime = System.currentTimeMillis();
                returnTuple.putLong("xform-time", txtime-sttime);
                returnTuple.putLong("invocation-time", endtime-sttime);
            }
            return returnTuple;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        finally {
            services.set(null);
        }
    }

    private Tuple transformEvent2Tuple(SimpleEvent responseEvent) throws Exception {
        Tuple tuple = Tuple.create();
        for (String propName : responseEvent.getPropertyNames()) {
            Object value = responseEvent.getProperty(propName);
            if (value != null) tuple.put(propName, value);
        }
        //TODO set the payload.
        return tuple;
    }

    private SimpleEvent transformTuple2Event(Tuple contextTuple, String requestEventType) throws Exception {
        RuleSession ruleSession = getDefaultRuleSession();
        SimpleEvent se = (SimpleEvent) ruleSession.getRuleServiceProvider().getTypeManager().createEntity(requestEventType);
        for (String propName : se.getPropertyNames()) {
            Object value = contextTuple.get(propName);
            if (value != null) {
                se.setProperty(propName, value);;
            }
        }
        return se;
    }

    private SimpleEvent transformTuple2EventEx(Tuple contextTuple, String requestEventType, boolean useOptimized) throws Exception {
        byte[] buf = contextTuple.getBlob("value");
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(buf));
        RuleSession ruleSession = getDefaultRuleSession();
        SimpleEvent se = (SimpleEvent) ruleSession.getRuleServiceProvider().getTypeManager().createEntity(requestEventType);
        if (useOptimized) {
            String[] propertyNames =  se.getPropertyNames();
            for (String name : propertyNames) {
                boolean isNull = dis.readBoolean();
                if (!isNull) {
                    se.setProperty(name, dis.readUTF());
                }
            }
        }
        else {
            while(dis.available() > 0) {
                Set propertyNames = se.getPropertyNamesAsSet();
                boolean isNull = dis.readBoolean();
                if (!isNull) {
                    String propName = readChars(dis);
                    if (propertyNames.contains(propName)) {
                        se.setProperty(propName, readChars(dis));
                    }
                    else {
                        readChars(dis); //eat it
                    }
                }
            }
        }
        dis.close();
        return se;
    }

    static String readChars(DataInput dis) throws Exception {
        char[] chars = new char[dis.readInt()];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = dis.readChar();
        }
        return  new String(chars);
    }

    public static InvocationService getCurrentService() {
        return services.get();
    }

    public void setResponse(SimpleEvent responseEvent) {
        this.responseEvent = responseEvent;
    }

    private RuleSession  getDefaultRuleSession() {
        RuleSession[] ruleSessions = RuleServiceProviderManager.getInstance().getDefaultProvider().getRuleRuntime().getRuleSessions();
        if (ruleSessions.length > 0) return ruleSessions[0];
        return null;
    }

    public void cancel(String reason) {
        this.isCanceled = true;
        this.cancelReason = reason;
    }
}
