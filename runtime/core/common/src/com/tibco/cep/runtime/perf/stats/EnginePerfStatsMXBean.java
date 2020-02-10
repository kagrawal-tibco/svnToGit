package com.tibco.cep.runtime.perf.stats;

import javax.management.MBeanOperationInfo;

import com.tibco.cep.runtime.jmx.Description;
import com.tibco.cep.runtime.jmx.Impact;
import com.tibco.cep.runtime.jmx.Parameter;

@Description("Provides engine performance statistics")
public interface EnginePerfStatsMXBean {

    @Description("The total number of RTC's completed")
    @Impact(MBeanOperationInfo.INFO)
    public long getTotalRTCs();

    @Description("The average RTC processing time (in msecs)")
    @Impact(MBeanOperationInfo.INFO)
    public double getAverageRTCTime();

    @Description("The processing statistics of rules filtered by the given name or regular expression")
    @Impact(MBeanOperationInfo.INFO)
    public RuleStats[] getRuleStats(@Parameter("nameOrExpression") @Description("The name or a regular expression") String nameOrExpression);

    @Description("The processing statistics of rules filtered by the given name or regular expression and ordered by the rule's processing time")
    @Impact(MBeanOperationInfo.INFO)
    public RuleStats[] getRuleStatsByProcessingTime(@Parameter("nameOrPattern") @Description("The name or a regular expression") String nameOrExpression,
                                                                                        @Parameter("ascending") @Description("The ordering flag") boolean ascending);

    @Description("The processing statistics of rules filtered by the given name or regular expression and ordered by the rule's condition processing time")
    @Impact(MBeanOperationInfo.INFO)
    public RuleStats[] getRuleStatsByConditionProcessingTime(@Parameter("nameOrPattern") @Description("The name or a regular expression") String nameOrExpression,
                                                                                        @Parameter("ascending") @Description("The ordering flag") boolean ascending);


}
