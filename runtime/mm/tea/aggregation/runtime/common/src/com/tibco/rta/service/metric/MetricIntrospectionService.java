package com.tibco.rta.service.metric;

import java.util.List;

import com.tibco.rta.Fact;
import com.tibco.rta.Metric;
import com.tibco.rta.common.service.StartStopService;
import com.tibco.rta.query.Browser;
import com.tibco.rta.query.MetricFieldTuple;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/2/13
 * Time: 1:32 PM
 * To change this template use File | Settings | File Templates.
 */

public interface MetricIntrospectionService extends StartStopService {
    /**
     *
     * @param metric
     * @param <S>
     * @param <T>
     * @return
     */
    public <S, T extends Metric<S>> String getChildMetricBrowser(Metric<S> metric, List<MetricFieldTuple> orderList) throws Exception;

    /**
     * Return a {@link Browser} identified with a correlation id.
     * @param metric
     * @param <S>
     * @return
     */
    public <S, T extends Fact> String getConstituentFactsBrowser(Metric<S> metric, List<MetricFieldTuple> orderList) throws Exception;

    /**
     * Get next fact from the browser identified by this correlationId
     * @param correlationId
     * @return
     * @throws Exception
     */
    public Object next(String correlationId) throws Exception;

    /**
     * Check if next fact is available from the browser identified by this correlationId
     * @param correlationId
     * @return
     * @throws Exception
     */
    public boolean hasNext(String correlationId) throws Exception;

	/**
	 * @param browserCorId
	 */
	public void removeBrowserMapping(String browserCorId);
}
