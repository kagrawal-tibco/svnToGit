package com.tibco.cep.runtime.perf.stats;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;
import com.tibco.cep.runtime.jmx.Parameter;

@Description("Provides statistics for all the thread pools and job queues")
public interface AllTPoolsJQueuesPerfStatsMXBean {

    @Description("The statistics of thread pool and job queues filtered by the given name or regular expression")
    @Impact(MBeanOperationInfo.INFO)
    public TPoolJQueueStats[] getStats(@Parameter("nameOrExpression") @Description("The name or a regular expression") String nameOrExpression);

    @Description("The statistics of thread pool and job queue filtered by the given name or regular expression and ordered by the active thread count")
    @Impact(MBeanOperationInfo.INFO)
    public TPoolJQueueStats[] getStatsByActiveThread(@Parameter("nameOrExpression") @Description("The name or a regular expression") String nameOrExpression,
                                                                            @Parameter("ascending") @Description("The ordering flag") boolean ascending);

    @Description("The statistics of thread pool and job queue filtered by the given name or regular expression and ordered by the job queue size")
    @Impact(MBeanOperationInfo.INFO)
    public TPoolJQueueStats[] getStatsByQueueSize(@Parameter("nameOrExpression") @Description("The name or a regular expression") String nameOrExpression,
                                                            @Parameter("ascending") @Description("The ordering flag") boolean ascending);

}
