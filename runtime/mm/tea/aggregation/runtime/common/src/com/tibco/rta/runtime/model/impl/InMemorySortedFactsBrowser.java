package com.tibco.rta.runtime.model.impl;

import com.tibco.rta.Fact;
import com.tibco.rta.query.Browser;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author ntamhank
 *         In memory impl of sorted facts browser
 */
class InMemorySortedFactsBrowser<T extends Fact> extends SortedFactsBrowser<T> {

    // in memory sorted set to store all facts in sorted order
    SortedSet<T> sortedFactSet = new TreeSet<T>(new FactComparator());
    Iterator<T> sortedIterator;

    public InMemorySortedFactsBrowser(Browser<T> factsBrowser) {
        super(factsBrowser);
        while (factsBrowser != null && factsBrowser.hasNext()) {
            sortedFactSet.add(factsBrowser.next());
        }
        factsBrowser.stop();
        sortedIterator = sortedFactSet.iterator();
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean hasNext() {
        return sortedIterator.hasNext();
    }

    @Override
    public T next() {
        return sortedIterator.next();
    }

    @Override
    public void remove() {
        // TODO Auto-generated method stub

    }

    class FactComparator implements Comparator<Fact> {

        @Override
        public int compare(Fact fact1, Fact fact2) {
            return fact1.getKey().toString().compareTo(fact2.getKey().toString());
        }

    }

}
