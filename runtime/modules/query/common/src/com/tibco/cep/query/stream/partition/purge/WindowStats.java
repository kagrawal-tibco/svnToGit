package com.tibco.cep.query.stream.partition.purge;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * Author: Ashwin Jayaprakash Date: Nov 20, 2007 Time: 4:52:09 PM
 */

public class WindowStats {
    /**
     * <p> Key/alias to access the number of unprocessed Tuples in the Window in {@link #stats}.
     * </p> <p> {@value} </p>
     */
    public static final String ALIAS_PENDING_COUNT = "$$PENDING_COUNT$$";

    protected final Object[] groupColumns;

    protected final Set<String> aliases;

    protected final HashMap<String, Object> stats;

    /**
     * @param groupColumns
     * @param otherAggregateAliases Can be <code>null</code> if no aggregate is required/specified.
     *                              Even if this is <code>null</code> the {@link
     *                              #ALIAS_PENDING_COUNT} will always be available.
     */
    public WindowStats(Object[] groupColumns, String[] otherAggregateAliases) {
        this.groupColumns = groupColumns;

        HashSet<String> set = new HashSet<String>(otherAggregateAliases.length * 2);
        if (otherAggregateAliases != null) {
            for (String string : otherAggregateAliases) {
                set.add(string);
            }
        }
        set.add(ALIAS_PENDING_COUNT);
        this.aliases = Collections.unmodifiableSet(set);

        this.stats = new HashMap<String, Object>();
    }

    public Object[] getGroupColumns() {
        return groupColumns;
    }

    public Set<String> getAliases() {
        return aliases;
    }

    /**
     * @return Map of the latest stats. The Keys are {@link #getAliases()}. If the key is absent,
     *         then it means that the value was not computed/available for the cycle.
     */
    public Map<String, Object> getStats() {
        return stats;
    }

    /**
     * @param alias See {@link #getAliases()}.
     * @return <code>null</code> if the key was wrong or there was no value computed/available for
     *         the cycle.
     */
    public Object getStat(String alias) {
        return stats.get(alias);
    }

    public void clearStats() {
        stats.clear();
    }
}
