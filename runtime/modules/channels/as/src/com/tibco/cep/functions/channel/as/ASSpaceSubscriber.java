package com.tibco.cep.functions.channel.as;

import java.lang.reflect.Constructor;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.tibco.as.space.FieldDef.FieldType;
import com.tibco.as.space.Space;
import com.tibco.as.space.Tuple;
import com.tibco.as.space.event.EvictEvent;
import com.tibco.as.space.event.ExpireEvent;
import com.tibco.as.space.event.PutEvent;
import com.tibco.as.space.event.TakeEvent;
import com.tibco.as.space.listener.EvictListener;
import com.tibco.as.space.listener.ExpireListener;
import com.tibco.as.space.listener.Listener;
import com.tibco.as.space.listener.ListenerDef;
import com.tibco.as.space.listener.PutListener;
import com.tibco.as.space.listener.TakeListener;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;

import static com.tibco.be.model.functions.FunctionDomain.ACTION;

public class ASSpaceSubscriber  {

    String ruleFunctionName;
    String requestEventType;
    boolean executeRules;
    String contentMatcher;

    private RuleSessionImpl ruleSession;

    private RuleFunction ruleFunc;

    private ListenerDef listenerDef;

    private Object spaceOrName;

    private String filter;
    Logger logger = LogManagerFactory.getLogManager().getLogger(ASSpaceSubscriber.class);

    private ASSubscriberType listenerType;

    private Listener listener;

    public static enum ASSubscriberType {
        TAKE(ASTakeListener.class),
        PUT(ASPutListener.class),
        EXPIRE(ASExpireListener.class),
        EVICT(ASEvictListener.class);

        private Class<?> listenerClazz;

        private ASSubscriberType(Class<?> clazz) {
            this.listenerClazz = clazz;
        }
        public Class<?> getListenerClazz() {
            return listenerClazz;
        }
    };

//    /**
//     * Creates a Listener Definition
//     * @param timeScope
//     * @param distributionScope
//     * @return
//     */
//    @com.tibco.be.model.functions.BEFunction(
//        name = "createListenerDef",
//        synopsis = "Creates a listener definition",
//        signature = "Object createListenerDef (String timeScope, String distributionScope)",
//        params = {
//                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "timeScope", type = "String", desc = "The timed scope of the space entries, valid values are ALL, NEW, NEW_EVENTS, SNAPSHOT<br/> see AS documentation for details. "),
//                @com.tibco.be.model.functions.FunctionParamDescriptor(name = "distributionScope", type = "String", desc = "The distribution scope of the space entries, valid values are ALL, SEEDED <br/> see AS documentation for details. ")
//        },
//        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Object", desc = "ListenerDef object"),
//        version = "5.1",
//        see = "",
//        mapper = @com.tibco.be.model.functions.BEMapper(),
//        description = "Creates a listener definition",
//        cautions = "none",
//        fndomain = {ACTION},
//        example = ""
//    )
//
//    public static Object createListenerDef(String timeScope,String distributionScope ) {
//        ListenerDef.TimeScope ts = ListenerDef.TimeScope.ALL;
//        ListenerDef.DistributionScope ds = ListenerDef.DistributionScope.ALL;
//        if (timeScope != null && !timeScope.isEmpty()) {
//            ts = ListenerDef.TimeScope.valueOf(timeScope);
//        }
//        if (distributionScope != null && !distributionScope.isEmpty()) {
//            ds = ListenerDef.DistributionScope.valueOf(distributionScope);
//        }
//        return ListenerDef.create(ts, ds);
//    }

    public ASSpaceSubscriber(Object spaceOrName, Object listenerDef, String listenerType, String filter) {
        this.spaceOrName = spaceOrName;
        if (listenerDef != null && listenerDef instanceof ListenerDef) {
            this.listenerDef = (ListenerDef) listenerDef;
        } else {
            this.listenerDef = (ListenerDef) ASSubscriberHelper.createListenerDef(null, null);
        }
        this.filter = filter ;
        this.listenerType = listenerType ==null || listenerType.isEmpty()?
                ASSubscriberType.PUT: ASSubscriberType.valueOf(listenerType);
    }

    private Space getSpace() {
        if (spaceOrName instanceof Space) {
            return (Space) spaceOrName;
        } else  if (spaceOrName instanceof String) {
            return ASSpaceHelper.getSpace((String)spaceOrName);
        } else {
            throw new RuntimeException("Invalid space object");
        }
    }

    public static abstract class AbstractASListener {
        private RuleFunction ruleFunc;
        private RuleSessionImpl ruleSession;
        private Logger logger = LogManagerFactory.getLogManager().getLogger(AbstractASListener.class);
        private String requestEventType;
        private boolean executeRules;

        public AbstractASListener(RuleSessionImpl ruleSession, RuleFunction ruleFunction, String requestEventType, boolean executeRules) {
            this.ruleSession = ruleSession;
            this.ruleFunc = ruleFunction;
            this.requestEventType = requestEventType;
            this.executeRules = executeRules;
        }

        public void onMsg(Tuple t)  {
            try {
                SimpleEvent se = this.requestEventType == null ? null : transform2Event(t);
                Object[] args = new Object[] { se == null ? t : se };
                if (executeRules)
                    ruleSession.preprocessPassthru(ruleFunc, se);
                else
                    ruleSession.invokeFunction(ruleFunc, args, true);
            } catch(Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public SimpleEvent transform2Event(Tuple t) throws Exception {
            SimpleEvent se = (SimpleEvent) ruleSession.getRuleServiceProvider().getTypeManager().createEntity(requestEventType);
            for (String propName : se.getPropertyNames()) {
                FieldType fieldType = t.getFieldType(propName);
                if (fieldType.equals(FieldType.LONG)) {
                    se.setProperty(propName, t.getLong(propName));
                } else if (fieldType.equals(FieldType.DOUBLE)) {
                    se.setProperty(propName, t.getDouble(propName));
                } else if (fieldType.equals(FieldType.STRING)) {
                    se.setProperty(propName, t.getString(propName));
                } else if (fieldType.equals(FieldType.INTEGER)) {
                    se.setProperty(propName, t.getInt(propName));
                } else if (fieldType.equals(FieldType.FLOAT)) {
                    se.setProperty(propName, t.getDouble(propName));
                } else if (fieldType.equals(FieldType.SHORT)) {
                    se.setProperty(propName, t.getInt(propName));
                } else if (fieldType.equals(FieldType.BOOLEAN)) {
                    se.setProperty(propName, t.getBoolean(propName));
                } else if (fieldType.equals(FieldType.DATETIME)) {
                    Calendar calendar = GregorianCalendar.getInstance();
                    calendar.setTimeInMillis(t.getDateTime(propName).getTimeInMillis());
                    se.setProperty(propName, calendar);
                }
            }
            return se;
        }
    }

    public static class ASTakeListener extends AbstractASListener implements TakeListener {

        public ASTakeListener(RuleSessionImpl ruleSession,RuleFunction ruleFunction,String requestEventType,boolean executeRules) {
            super(ruleSession,ruleFunction,requestEventType,executeRules);
        }

        @Override
        public void onTake(TakeEvent ev) {
            onMsg(ev.getTuple());
        }
    }

    public static class ASPutListener extends AbstractASListener implements PutListener {

        public ASPutListener(RuleSessionImpl ruleSession,RuleFunction ruleFunction,String requestEventType,boolean executeRules) {
            super(ruleSession,ruleFunction,requestEventType,executeRules);
        }

        @Override
        public void onPut(PutEvent ev) {
            onMsg(ev.getTuple());
        }
    }

    public static class ASExpireListener extends AbstractASListener implements ExpireListener {

        public ASExpireListener(RuleSessionImpl ruleSession,RuleFunction ruleFunction,String requestEventType,boolean executeRules) {
            super(ruleSession,ruleFunction,requestEventType,executeRules);
        }

        @Override
        public void onExpire(ExpireEvent ev) {
            onMsg(ev.getTuple());
        }
    }

    public static class ASEvictListener extends AbstractASListener implements EvictListener {

        public ASEvictListener(RuleSessionImpl ruleSession,RuleFunction ruleFunction,String requestEventType,boolean executeRules) {
                super(ruleSession,ruleFunction,requestEventType,executeRules);
        }
        @Override
        public void onEvict(EvictEvent ev) {
            onMsg(ev.getTuple());
        }
    }

    private Listener createListener(ASSubscriberType type, RuleSession ruleSession, RuleFunction ruleFunction, String requestEventType, boolean executeRules) {
        Space space = getSpace();
        ListenerDef ld = getListenerDef();
        try {
            Class<?> listenerClazz = type.getListenerClazz();
            Constructor<?> ctor = listenerClazz.getConstructor(RuleSessionImpl.class, RuleFunction.class, String.class, boolean.class);
            Listener listener = (Listener) ctor.newInstance(ruleSession, ruleFunction, requestEventType, executeRules);
            if (getFilter() != null) {
                space.listen(Listener.class.cast(listener), ld, getFilter());
            } else {
                space.listen(Listener.class.cast(listener), ld);
            }
            return listener;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void startListening() {
        ruleSession = (RuleSessionImpl) getDefaultRuleSession();
        if (ruleSession == null)
            throw new RuntimeException("No Rulesession configured...");
        if (this.ruleFunctionName == null)
            throw new RuntimeException("No Rulefunction configured...");
        ruleFunc = ruleSession.getRuleFunction(this.ruleFunctionName);
        this.listener = createListener(getListenerType(), ruleSession, getRuleFunction(), getRequestEventType(), isExecuteRules());
    }

    public void close() {
        try {
            getSpace().stopListener(this.listener);
        } catch (Throwable ex) {
            throw new RuntimeException(ex);
        }
    }

    private RuleSession  getDefaultRuleSession() {
        RuleSession[] ruleSessions = RuleServiceProviderManager.getInstance().getDefaultProvider().getRuleRuntime().getRuleSessions();
        if (ruleSessions.length > 0) return ruleSessions[0];
        return null;
    }

    private ASSubscriberType getListenerType() {
        return listenerType;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getRuleFunctionName() {
        return ruleFunctionName;
    }

    public void setRuleFunctionName(String ruleFunctionName) {
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

    public void setExecuteRules(boolean executeRules) {
        this.executeRules = executeRules;
    }

    public String getContentMatcher() {
        return contentMatcher;
    }

    public void setContentMatcher(String contentMatcher) {
        this.contentMatcher = contentMatcher;
    }

    public RuleSessionImpl getRuleSession() {
        return ruleSession;
    }

    public void setRuleSession(RuleSessionImpl ruleSession) {
        this.ruleSession = ruleSession;
    }

    public RuleFunction getRuleFunction() {
        return ruleFunc;
    }

    public void setRuleFunction(RuleFunction ruleFunc) {
        this.ruleFunc = ruleFunc;
    }

    public ListenerDef getListenerDef() {
        return listenerDef;
    }

    public void setListenerDef(ListenerDef listenerDef) {
        this.listenerDef = listenerDef;
    }

    public Object getSpaceOrName() {
        return spaceOrName;
    }

    public void setSpaceName(Object spaceName) {
        this.spaceOrName = spaceName;
    }
}