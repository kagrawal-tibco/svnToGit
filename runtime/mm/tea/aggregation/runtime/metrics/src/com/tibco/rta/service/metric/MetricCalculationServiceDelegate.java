package com.tibco.rta.service.metric;

import java.util.List;

import com.tibco.rta.Fact;
import com.tibco.rta.common.service.FactMessageContext;
import com.tibco.rta.common.service.ServiceProviderManager;
import com.tibco.rta.common.service.TransactionEvent;
import com.tibco.rta.common.service.impl.AbstractStartStopServiceImpl;
import com.tibco.rta.common.service.session.ServerSession;
import com.tibco.rta.common.service.session.ServerSessionRegistry;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 1/2/13
 * Time: 1:51 PM
 * A decorator for the real metric calculation service.
 */
public class MetricCalculationServiceDelegate extends AbstractStartStopServiceImpl {

    private MetricService delegatedService = null;

    public static final MetricCalculationServiceDelegate INSTANCE = new MetricCalculationServiceDelegate();

    private MetricCalculationServiceDelegate() {
        try {
            this.delegatedService = ServiceProviderManager.getInstance().getMetricsService();
            delegatedService.addTransactionContextListener(DefaultTransactionEventListener.INSTANCE);
            delegatedService.addFactListener(new AckingFactListener());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public <C extends FactMessageContext> TransactionEvent assertFact(C messageContext, Fact fact) throws Exception {
        return delegatedService.assertFact(messageContext, fact);
    }
    
    public <C extends FactMessageContext> TransactionEvent assertFact(C messageContext, List<Fact> fact) throws Exception {
        return delegatedService.assertFact(messageContext, fact);
    }

    /**
     * The internal id of the publisher client session.
     * @throws Exception
     */
    public <C extends FactMessageContext> TransactionEvent assertFact(String publisherId, C messageContext, Fact fact) throws Exception {
        ServerSession serverSession = ServerSessionRegistry.INSTANCE.getServerSession(publisherId);
        TransactionEvent transactionEvent = assertFact(messageContext, fact);
        if (serverSession != null) {
            serverSession.addTransactionEventId(transactionEvent.getTransactionId());
        }
        return transactionEvent;
    }
    
    public <C extends FactMessageContext> TransactionEvent assertFact(String publisherId, C messageContext, List<Fact> facts) throws Exception {
        ServerSession serverSession = ServerSessionRegistry.INSTANCE.getServerSession(publisherId);
        TransactionEvent transactionEvent = assertFact(messageContext, facts);
        if (serverSession != null) {
            serverSession.addTransactionEventId(transactionEvent.getTransactionId());
        }
        return transactionEvent;
    }

}
