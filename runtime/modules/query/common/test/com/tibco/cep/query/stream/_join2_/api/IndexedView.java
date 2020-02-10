package com.tibco.cep.query.stream._join2_.api;

import com.tibco.cep.query.stream.util.CustomIterable;

import java.util.Collection;

/*
* Author: Ashwin Jayaprakash Date: May 27, 2009 Time: 10:01:22 PM
*/
public interface IndexedView<C extends Comparable, V> extends View<V> {
    V getAnyMatch(Operator operator, C c);

    V getAnyMatch(Operator operator1, C c1, Operator operator2, C c2);

    V getAnyMatch(Operator operator, C[] c);

    V getAnyMatch(Operator operator, Collection<C> c);

    //------------

    CustomIterable<V> getMatches(Operator operator, C c);

    CustomIterable<V> getMatches(Operator operator1, C c1, Operator operator2, C c2);

    CustomIterable<V> getMatches(Operator operator, C[] c);

    CustomIterable<V> getMatches(Operator operator, Collection<C> c);
}