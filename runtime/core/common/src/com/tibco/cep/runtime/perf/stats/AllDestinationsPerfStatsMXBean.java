package com.tibco.cep.runtime.perf.stats;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;
import com.tibco.cep.runtime.jmx.Parameter;

@Description("Provides statistics for all the destinations")
public interface AllDestinationsPerfStatsMXBean {

    @Description("The statistics of destinations filtered by the given name or regular expression")
    @Impact(MBeanOperationInfo.INFO)
    public DestinationStats[] getStats(@Parameter("nameOrExpression") @Description("The name or a regular expression") String nameOrExpression);

    @Description("The statistics of destinations filtered by the given name or regular expression and ordered by the events received")
    @Impact(MBeanOperationInfo.INFO)
    public DestinationStats[] getStatsByEventsReceived(@Parameter("nameOrExpression") @Description("The name or a regular expression") String nameOrExpression,
                                                        @Parameter("ascending") @Description("The ordering flag") boolean ascending);

    @Description("The statistics of destinations filtered by the given name or regular expression and ordered by the events sent")
    @Impact(MBeanOperationInfo.INFO)
    public DestinationStats[] getStatsByEventsSent(@Parameter("nameOrExpression") @Description("The name or a regular expression") String nameOrExpression,
                                                    @Parameter("ascending") @Description("The ordering flag") boolean ascending);

}
