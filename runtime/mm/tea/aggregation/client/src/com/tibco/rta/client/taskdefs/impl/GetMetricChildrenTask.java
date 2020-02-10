package com.tibco.rta.client.taskdefs.impl;

import java.util.List;

import com.tibco.rta.Metric;
import com.tibco.rta.client.ServiceResponse;
import com.tibco.rta.client.taskdefs.AbstractClientTask;
import com.tibco.rta.client.transport.MessageTransmissionStrategy;
import com.tibco.rta.common.service.ServiceType;
import com.tibco.rta.client.util.RtaSessionUtil;
import com.tibco.rta.metric.MetricChildrenBrowserProxy;
import com.tibco.rta.query.MetricFieldTuple;
import com.tibco.rta.util.ChildMetricData;
import com.tibco.rta.util.IOUtils;
import com.tibco.rta.util.ServiceConstants;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 18/2/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class GetMetricChildrenTask extends AbstractClientTask {

    private Metric<?> metric;

    private String taskName;

	private List<MetricFieldTuple> orderByList;

    public GetMetricChildrenTask(MessageTransmissionStrategy messageTransmissionStrategy, String taskName) {
        super(messageTransmissionStrategy);
        this.taskName = taskName;
    }

    @Override
    public Object perform() throws Exception {
        //Requires to go on dedicated query queue.
        String endpoint = RtaSessionUtil.getEndpoint(ServiceType.METRICS_INTROSPECTION, messageTransmissionStrategy.getOwnerConnection(), true);
        setBaseProps(ServiceType.METRICS_INTROSPECTION.getServiceURI());
        //Serialize
        ChildMetricData childMetricData = new ChildMetricData(metric, orderByList);
        
        byte[] serialized = IOUtils.serialize(childMetricData);
        ServiceResponse serviceResponse = messageTransmissionStrategy.transmit(endpoint,
                                                                               getTaskName(),
                                                                               properties,
                                                                               serialized);

        String browserCorId = serviceResponse.getResponseProperties().getProperty(ServiceConstants.BROWSER_ID);
        MetricChildrenBrowserProxy proxy = null;

        if (browserCorId != null) {
            proxy = new MetricChildrenBrowserProxy();
            proxy.setId(browserCorId);
        }
        return proxy;
    }

    @Override
    public String getTaskName() {
        return taskName;
    }

    public void setMetric(Metric<?> metric) {
        this.metric = metric;
    }

	public void setOrderList(List<MetricFieldTuple> orderByList) {
		this.orderByList = orderByList;
	}
}
