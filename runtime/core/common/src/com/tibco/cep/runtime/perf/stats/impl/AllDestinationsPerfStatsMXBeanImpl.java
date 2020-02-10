package com.tibco.cep.runtime.perf.stats.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;

import com.tibco.cep.runtime.perf.stats.AllDestinationsPerfStatsMXBean;
import com.tibco.cep.runtime.perf.stats.DestinationStats;

public class AllDestinationsPerfStatsMXBeanImpl implements AllDestinationsPerfStatsMXBean {

    private DestinationPerfStatsAggregator aggregator;

    public AllDestinationsPerfStatsMXBeanImpl(DestinationPerfStatsAggregator aggregator) {
        this.aggregator = aggregator;
    }

    @Override
    public DestinationStats[] getStats(String nameOrExpression) {
        List<DestinationStats> stats = getFilteredStats(nameOrExpression);
        return stats.toArray(new DestinationStats[stats.size()]);
    }

    private List<DestinationStats> getFilteredStats(String nameOrPattern) {
        LinkedList<DestinationStats> statsList = new LinkedList<DestinationStats>(aggregator.getDestinationStats());
        if (nameOrPattern != null && "*".equals(nameOrPattern) == false && nameOrPattern.trim().length() != 0) {
            Pattern pattern = Pattern.compile(nameOrPattern, Pattern.CASE_INSENSITIVE);
            ListIterator<DestinationStats> iterator = statsList.listIterator();
            while (iterator.hasNext()) {
                DestinationStats stat = (DestinationStats) iterator.next();
                if (pattern.matcher(stat.getName()).matches() == false) {
                    iterator.remove();
                }
            }
        }
        return statsList;
    }

    @Override
    public DestinationStats[] getStatsByEventsReceived(String nameOrExpression, final boolean ascending) {
        List<DestinationStats> stats = getFilteredStats(nameOrExpression);
        Collections.sort(stats, new Comparator<DestinationStats>() {

            @Override
            public int compare(DestinationStats o1, DestinationStats o2) {
                long i = o1.getTotalEventsReceived() - o2.getTotalEventsReceived();
                return (int) (ascending == true ? i : -i);
            }
        });
        return stats.toArray(new DestinationStats[stats.size()]);

    }

    @Override
    public DestinationStats[] getStatsByEventsSent(String nameOrExpression, final boolean ascending) {
        List<DestinationStats> stats = getFilteredStats(nameOrExpression);
        Collections.sort(stats, new Comparator<DestinationStats>() {

            @Override
            public int compare(DestinationStats o1, DestinationStats o2) {
                long i = o1.getTotalEventsSent() - o2.getTotalEventsSent();
                return (int) (ascending == true ? i : -i);
            }
        });
        return stats.toArray(new DestinationStats[stats.size()]);
    }

}
