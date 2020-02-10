package com.tibco.cep.functions.channel.as;

import com.tibco.as.space.*;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.session.RuleSession;

/*
* Author: Suresh Subramani / Date: 9/14/12 / Time: 5:50 PM
*/

public class InvocationServiceDefinition {
    Metaspace metaspace;
    String serviceName;
    String ruleFunctionName;
    String requestEventType;
    boolean executeRules;
    Space space;

    public InvocationServiceDefinition(Metaspace ms, String serviceName, String ruleFunctionName, String requestEventType, boolean executeRules, RuleSession session) throws Exception {
        this.metaspace = ms;
        this.serviceName = serviceName;
        this.ruleFunctionName = ruleFunctionName;
        this.requestEventType = requestEventType;
        this.executeRules = executeRules;
        _init(session);
    }

    private void _init(RuleSession session) throws Exception {
        SpaceDef spaceDef = SpaceDef.create(serviceName);
        spaceDef.putFieldDef(FieldDef.create("key", FieldDef.FieldType.STRING));
        spaceDef.setKey("key");
        Tuple tuple = Tuple.create();
        tuple.put("service$name", serviceName);
        tuple.put("service$inputType", requestEventType);
        tuple.put("service$bindingfunction", ruleFunctionName);
        tuple.put("service$executerules", this.executeRules);
        tuple.put("service$implclass", InvocationService.class.getName());
        tuple.put("service$inputfields", getPropertyNamesAsByteArray(session));
        tuple.put("service$serialization$enabled", System.getProperty("serialization.optimization.enabled", "false"));
        spaceDef.setContext(tuple);
        metaspace.defineSpace(spaceDef);
        space = metaspace.getSpace(serviceName, Member.DistributionRole.SEEDER);
    }

    private String getPropertyNamesAsByteArray(RuleSession session) throws Exception {
        SimpleEvent se = (SimpleEvent) session.getRuleServiceProvider().getTypeManager().createEntity(requestEventType);
        String[] propertyNames =  se.getPropertyNames();
        StringBuilder builder = new StringBuilder();
        int i=0;
        for (String name : propertyNames) {
            builder.append(name);
            if (++i < propertyNames.length) builder.append(", ");
        }
        return builder.toString();
    }

    public Metaspace getMetaspace() {
        return metaspace;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getRuleFunctionName() {
        return ruleFunctionName;
    }

    public String getRequestEventType() {
        return requestEventType;
    }

    public boolean isExecuteRules() {
        return executeRules;
    }
}
