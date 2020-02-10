package com.tibco.cep.runtime.perf.stats.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import com.tibco.cep.runtime.perf.stats.EnginePerfStatsMXBean;
import com.tibco.cep.runtime.perf.stats.RuleStats;

public class EnginePerfStatsMXBeanImpl implements EnginePerfStatsMXBean {

    private EnginePerfStatsAggregator aggregator;

    public EnginePerfStatsMXBeanImpl(EnginePerfStatsAggregator aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public long getTotalRTCs() {
        return aggregator.getTotalRTCs();
    }

    @Override
    public double getAverageRTCTime() {
        return aggregator.getAverageRTCTime();
    }

    @Override
    public RuleStats[] getRuleStats(String nameOrExpression) {
        List<RuleStats> stats = getFilteredStats(nameOrExpression);
        return stats.toArray(new RuleStats[stats.size()]);
    }

    @Override
    public RuleStats[] getRuleStatsByProcessingTime(String nameOrExpression, final boolean ascending) {
        List<RuleStats> stats = getFilteredStats(nameOrExpression);
        Collections.sort(stats, new Comparator<RuleStats>() {

            @Override
            public int compare(RuleStats o1, RuleStats o2) {
                double i = o1.getAverageProcessingTime() - o2.getAverageProcessingTime();
                return (int) (ascending == true ? i : -i);
            }
        });
        return stats.toArray(new RuleStats[stats.size()]);
    }

    @Override
    public RuleStats[] getRuleStatsByConditionProcessingTime(String nameOrExpression, final boolean ascending) {
        List<RuleStats> stats = getFilteredStats(nameOrExpression);
        Collections.sort(stats, new Comparator<RuleStats>() {

            @Override
            public int compare(RuleStats o1, RuleStats o2) {
                double i = o1.getAverageConditionProcessingTime() - o2.getAverageConditionProcessingTime();
                return (int) (ascending == true ? i : -i);
            }
        });
        return stats.toArray(new RuleStats[stats.size()]);
    }

    private List<RuleStats> getFilteredStats(String nameOrPattern) {
        LinkedList<RuleStats> statsList = new LinkedList<RuleStats>(aggregator.getRuleStats());
        if (nameOrPattern != null && "*".equals(nameOrPattern) == false && nameOrPattern.trim().length() != 0) {
            Pattern pattern = Pattern.compile(nameOrPattern, Pattern.CASE_INSENSITIVE);
            ListIterator<RuleStats> iterator = statsList.listIterator();
            while (iterator.hasNext()) {
                RuleStats stat = (RuleStats) iterator.next();
                if (pattern.matcher(stat.getURI()).matches() == false) {
                    iterator.remove();
                }
            }
        }
        return statsList;
    }


}
