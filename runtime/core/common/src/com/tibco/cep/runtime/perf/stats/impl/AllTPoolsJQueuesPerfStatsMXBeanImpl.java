package com.tibco.cep.runtime.perf.stats.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import com.tibco.cep.runtime.perf.stats.AllTPoolsJQueuesPerfStatsMXBean;
import com.tibco.cep.runtime.perf.stats.TPoolJQueueStats;

public class AllTPoolsJQueuesPerfStatsMXBeanImpl implements AllTPoolsJQueuesPerfStatsMXBean {

    private TPoolJQueueStatsAggregator aggregator;

    public AllTPoolsJQueuesPerfStatsMXBeanImpl(TPoolJQueueStatsAggregator aggregator) {
        super();
        this.aggregator = aggregator;
    }

    @Override
    public TPoolJQueueStats[] getStats(String nameOrExpression) {
        List<TPoolJQueueStats> stats = getFilteredStats(nameOrExpression);
        return stats.toArray(new TPoolJQueueStats[stats.size()]);
    }

    @Override
    public TPoolJQueueStats[] getStatsByActiveThread(String nameOrExpression, final boolean ascending) {
        List<TPoolJQueueStats> stats = getFilteredStats(nameOrExpression);
        Collections.sort(stats, new Comparator<TPoolJQueueStats>() {

            @Override
            public int compare(TPoolJQueueStats o1, TPoolJQueueStats o2) {
            	int i = o1.getActiveThreads() - o2.getActiveThreads();
        		return ascending == true ? i : -i;
            }
        });
        return stats.toArray(new TPoolJQueueStats[stats.size()]);
    }

    @Override
    public TPoolJQueueStats[] getStatsByQueueSize(String nameOrExpression, final boolean ascending) {
        List<TPoolJQueueStats> stats = getFilteredStats(nameOrExpression);
        Collections.sort(stats, new Comparator<TPoolJQueueStats>() {

            @Override
            public int compare(TPoolJQueueStats o1, TPoolJQueueStats o2) {
            	int i = o1.getQueueSize() - o2.getQueueSize();
        		return ascending == true ? i : -i;
            }
        });
        return stats.toArray(new TPoolJQueueStats[stats.size()]);
    }

    private List<TPoolJQueueStats> getFilteredStats(String nameOrPattern) {
        LinkedList<TPoolJQueueStats> statsList = new LinkedList<TPoolJQueueStats>(aggregator.getThreadPoolJobQueueStats());
        if (nameOrPattern != null && "*".equals(nameOrPattern) == false && nameOrPattern.trim().length() != 0) {
            Pattern pattern = Pattern.compile(nameOrPattern, Pattern.CASE_INSENSITIVE);
            ListIterator<TPoolJQueueStats> iterator = statsList.listIterator();
            while (iterator.hasNext()) {
                TPoolJQueueStats stat = (TPoolJQueueStats) iterator.next();
                if (pattern.matcher(stat.getName()).matches() == false) {
                    iterator.remove();
                }
            }
        }
        return statsList;
    }

}