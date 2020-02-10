package com.tibco.cep.query.stream._retired_;

import com.tibco.cep.kernel.model.rule.Action;
import com.tibco.cep.kernel.model.rule.Condition;
import com.tibco.cep.kernel.model.rule.Identifier;
import com.tibco.cep.kernel.model.rule.impl.ActionImpl;
import com.tibco.cep.kernel.model.rule.impl.ConditionImpl;
import com.tibco.cep.kernel.model.rule.impl.IdentifierImpl;
import com.tibco.cep.kernel.model.rule.impl.RuleImpl;
import com.tibco.cep.query.stream.cache.SharedObjectManager;
import com.tibco.cep.query.stream.core.Registry;
import com.tibco.cep.query.stream.impl.rete.input.QObjectManager;
import com.tibco.cep.query.stream.impl.rete.query.ReteQuery;
import com.tibco.cep.query.stream.monitor.Logger;
import com.tibco.cep.query.stream.monitor.Logger.LogLevel;

/*
 * Author: Ashwin Jayaprakash Date: Nov 16, 2007 Time: 4:14:18 PM
 */

/**
 * Starts feeding the objects to the {@link ReteQuery} when this Rule is
 * deployed and the {@link #trigger} is asserted.
 */
public abstract class FindMatchesRule extends RuleImpl {
    protected final ReteQuery query;

    protected final Identifier identTrigger;

    protected final Identifier identTarget;

    protected final Long trigger;

    protected final String classOfEntitiesToBeFetched;

    protected final SharedObjectManager som;

    protected final QObjectManager qom;

    public FindMatchesRule(ReteQuery query, String name, String classOfEntitiesToBeFetched,
            Long trigger, SharedObjectManager som, QObjectManager qom)
            throws ClassNotFoundException {
        super(name);

        this.query = query;
        this.identTrigger = new IdentifierImpl(trigger.getClass(), "$trigger");
        this.classOfEntitiesToBeFetched = classOfEntitiesToBeFetched;
        Class clazz = Class.forName(classOfEntitiesToBeFetched, true, Thread.currentThread()
                .getContextClassLoader());
        this.identTarget = new IdentifierImpl(clazz, "$target");
        this.trigger = trigger;
        this.som = som;
        this.qom = qom;
    }

    public Object getTrigger() {
        return trigger;
    }

    public void init() {
        m_identifiers = createIdentifiers();
        m_conditions = createConditions();
        m_actions = createActions();
    }

    protected Identifier[] createIdentifiers() {
        return new Identifier[] { identTrigger, identTarget };
    }

    protected Condition[] createConditions() {
        return new Condition[] { new ConditionImpl(this, new Identifier[] { identTrigger,
                identTarget }) {
            @Override
            public boolean eval(Object[] objects) {
                if (objects[0] instanceof Number) {
                    return ((Number) objects[0]).longValue() == trigger.longValue();
                }

                return false;
            }
        } };
    }

    protected Action[] createActions() {
        return new Action[] { new ActionImpl(this) {
            @Override
            public void execute(Object[] objects) {
                Object object = objects[1];

                try {
                    FindMatchesRule.this.handleEntity(object);
                }
                catch (Exception e) {
                    Logger logger = Registry.getInstance().getComponent(Logger.class);
                    logger.log(LogLevel.ERROR, getName(), e);
                }
            }
        } };
    }

    protected abstract void handleEntity(Object reteObject) throws Exception;

    public abstract void end() throws Exception;
}