package com.tibco.rta.runtime.model.impl;

import java.util.List;

import com.tibco.rta.Fact;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.runtime.model.MetricNode;
import com.tibco.rta.service.persistence.PersistenceService;

/**
 * @author ntamhank
 *         Browser impl which gives all facts associated with a given metric node. The given metric node need not be a leaf node.
 *         It could be any node in the dimension hierarchy.
 */
public class MetricAllFactsBrowser implements Browser<Fact> {

    private Browser<Fact> currentFactBrowser;

    private boolean isStopped;

    public MetricAllFactsBrowser(PersistenceService pServ, MetricNode metricNode, List<MetricFieldTuple> orderByList) throws Exception {
        currentFactBrowser= pServ.getFactBrowser(metricNode, orderByList);
    }

    @Override
    public boolean hasNext() {
    	return !isStopped && currentFactBrowser.hasNext();
    }


    @Override
    public Fact next() {
        if (isStopped) {
            throw new RuntimeException("Browser stopped");
        }
    	return currentFactBrowser.next();
    }

    @Override
    public void remove() {
        // not supported

    }

    @Override
    public void stop() {
        currentFactBrowser.stop();
        isStopped = true;
    }
}
