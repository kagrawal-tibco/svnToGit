package com.tibco.cep.runtime.service.cluster.deploy.template.transaction;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.interpreter.template.TemplatedRuleFactory;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.deploy.template.operation.RuleTemplateDeploymentOperationDescriptor;
import com.tibco.cep.runtime.service.cluster.deploy.RuleTemplateDeployer;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleServiceProviderManager;
import com.tibco.cep.runtime.session.RuleSession;


/**
 * User: nprade
 * Date: 3/14/12
 * Time: 3:33 PM
 */
public class RuleTemplateDeploymentTransaction {


    protected static enum State {
        CREATING,
        CREATED,
        PREPARING,
        PREPARED,
        COMMITTING,
        COMMITTED,
        ROLLING_BACK,
        CLOSING,
        CLOSED,
    }


    protected final static String LOG_MSG = RuleTemplateDeploymentTransaction.class.getSimpleName() + "[%s] %s %s";

    protected final long id;
    protected final RuleServiceProvider rsp =
    		RuleServiceProviderManager.getInstance().getDefaultProvider();
    protected final Logger logger = rsp.getLogger(RuleTemplateDeployer.class);
    protected final Set<RuleTemplateDeploymentOperationDescriptor> operations;
    protected final Map<String, RuleTemplateDeploymentInRuleSession> ruleSessionNameToDeployment =
            new HashMap<String, RuleTemplateDeploymentInRuleSession>();
    protected final TemplatedRuleFactory templatedRuleFactory;

    protected State state;


    public RuleTemplateDeploymentTransaction(
            long id,
            TemplatedRuleFactory templatedRuleFactory,
            Set<RuleTemplateDeploymentOperationDescriptor> operations)
            throws Exception {

        this.state = State.CREATING;
        logger.log(Level.DEBUG, LOG_MSG, id, state, "");

        this.id = id;
        this.operations = operations;
        this.templatedRuleFactory = templatedRuleFactory;

        this.state = State.CREATED;
        logger.log(Level.INFO, LOG_MSG, id, state, "");
    }


    public void close()
            throws Exception {

        this.state = State.CLOSING;
        logger.log(Level.INFO, LOG_MSG, id, state, "");

        rsp.getChannelManager().resumeChannels();
        logger.log(Level.INFO, LOG_MSG, id, state, "Resumed channels.");

        this.state = State.CLOSED;
        logger.log(Level.INFO, LOG_MSG, id, state, "");
    }


    public void commit()
            throws Exception {

        if (state != State.PREPARED) {
            throw new IllegalStateException();
        }
        state = State.COMMITTING;
        logger.log(Level.INFO, LOG_MSG, id, state, "");

        rsp.getRuleRuntime().suspendWMs(false);
        logger.log(Level.INFO, LOG_MSG, id, state, "Suspended WMs.");

        try {
            for (final Map.Entry<String, RuleTemplateDeploymentInRuleSession> e :
                    ruleSessionNameToDeployment.entrySet()) {
                logger.log(Level.DEBUG, LOG_MSG, id, state, e.getKey());
                e.getValue().commit();
            }
        }
        finally {
            rsp.getRuleRuntime().releaseWMs();
            logger.log(Level.INFO, LOG_MSG, id, state, "Resumed WMs.");
        }

        state = State.COMMITTED;
        logger.log(Level.INFO, LOG_MSG, id, state, "");
    }


    private RuleTemplateDeploymentInRuleSession findRuleTemplateDeploymentInRuleSession(String rsName)
            throws Exception {

        RuleTemplateDeploymentInRuleSession deployment = ruleSessionNameToDeployment.get(rsName);
        if (null == deployment) {
            final RuleSession ruleSession = rsp.getRuleRuntime().getRuleSession(rsName);
            if (null != ruleSession) {
                deployment = new RuleTemplateDeploymentInRuleSession(ruleSession, templatedRuleFactory);
                ruleSessionNameToDeployment.put(rsName, deployment);
            }
        }
        return deployment;
    }


    @Override
    public boolean equals(
            Object o) {

        return (this == o) || (
                (o != null) && (getClass() == o.getClass()) && (id == ((RuleTemplateDeploymentTransaction) o).id)
        );

    }


    protected void finish() {


    }


    public long getId() {
        return id;
    }


    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


    public void prepare()
            throws Exception {

        this.state = State.PREPARING;
        logger.log(Level.INFO, LOG_MSG, id, state, "");

        rsp.getChannelManager().suspendChannels();
        logger.log(Level.INFO, LOG_MSG, id, state, "Suspended channels.");

        for (final RuleTemplateDeploymentOperationDescriptor operation : this.operations) {
            final RuleTemplateDeploymentInRuleSession rtdirs =
                    findRuleTemplateDeploymentInRuleSession(operation.getRuleSessionName());
            if (null != rtdirs) {
                rtdirs.prepare(operation);
            }
        }

        state = State.PREPARED;
        logger.log(Level.INFO, LOG_MSG, id, state, "");
    }


    public void rollback()
            throws Exception {

        switch (state) {

            case CREATING:
            case CREATED:
            case PREPARING:
            case PREPARED:
            {
                logger.log(Level.DEBUG, LOG_MSG, id, state, "nothing to roll back");
                return;
            }

            case COMMITTING:
            case COMMITTED:
            case ROLLING_BACK:
            {
                state = State.ROLLING_BACK;
                logger.log(Level.INFO, LOG_MSG, id, state, "");

                for (final Map.Entry<String, RuleTemplateDeploymentInRuleSession> e
                        : ruleSessionNameToDeployment.entrySet()) {
                    logger.log(Level.DEBUG, LOG_MSG, this, state, e.getKey());
                    e.getValue().rollback(true); // todo true or not?
                }

                finish();

                state = State.PREPARED;
                logger.log(Level.INFO, LOG_MSG, id, state, "");
                break;
            }

            default:
                throw new IllegalStateException();
        }

    }


}
