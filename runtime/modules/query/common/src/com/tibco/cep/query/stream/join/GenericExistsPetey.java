package com.tibco.cep.query.stream.join;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CustomHashSet;

/*
 * Author: Ashwin Jayaprakash Date: Oct 22, 2007 Time: 4:09:58 PM
 */

public abstract class GenericExistsPetey extends AbstractExistsPetey {
    protected final HashMap<Number, Tuple> outerExistsIdsAndTuples;

    protected final HashMap<Number, Tuple> outerNotExistsIdsAndTuples;

    protected final HashMap<Number, Tuple> innerIdsAndTuples;

    protected final HashMap<Number, Number> outerAndInnerTupleJoin;

    protected final HashMap<Number, CustomHashSet<Number>> innerAndOuterTupleJoin;

    private Collection<Tuple> matchedOuterTuples;

    private Collection<Tuple> disconnectedOuterTuples;

    public GenericExistsPetey(ExistsPeteyInfo peteyInfo) {
        super(peteyInfo);

        this.outerExistsIdsAndTuples = new HashMap<Number, Tuple>();
        this.outerNotExistsIdsAndTuples = new HashMap<Number, Tuple>();
        this.innerIdsAndTuples = new HashMap<Number, Tuple>();
        this.outerAndInnerTupleJoin = new HashMap<Number, Number>();
        this.innerAndOuterTupleJoin = new HashMap<Number, CustomHashSet<Number>>();
    }

    protected abstract boolean match(GlobalContext globalContext, DefaultQueryContext queryContext,
                                     Tuple outerTuple, Tuple innerTuple);

    /**
     * @param outerTuple
     * @return <code>true</code> if this outer Tuple found a matching inner Tuple.
     */
    @Override
    public Boolean addOuter(GlobalContext globalContext, DefaultQueryContext queryContext, String alias,
                            Tuple outerTuple) {
        boolean retVal = findMatchAndConnect(globalContext, queryContext, outerTuple);

        if (retVal == false) {
            outerNotExistsIdsAndTuples.put(outerTuple.getId(), outerTuple);
        }

        return retVal;
    }

    /**
     * @param innerTuple
     * @return Collection of outer Tuples that match this inner Tuple or <code>null</code>.
     */
    @Override
    public Collection<Tuple> addInner(GlobalContext globalContext, DefaultQueryContext queryContext,
                                      Tuple innerTuple) {
        Collection<Tuple> results = null;

        if (matchedOuterTuples != null) {
            matchedOuterTuples.clear();
        }
        else {
            matchedOuterTuples = new AppendOnlyQueue<Tuple>(queryContext.getArrayPool());
        }

        innerIdsAndTuples.put(innerTuple.getId(), innerTuple);

        for (Iterator<? extends Tuple> iter = outerNotExistsIdsAndTuples.values().iterator(); iter
                .hasNext();) {
            Tuple outerTuple = iter.next();

            boolean match = match(globalContext, queryContext, outerTuple, innerTuple);
            if (match) {
                outerExistsIdsAndTuples.put(outerTuple.getId(), outerTuple);
                // Remove from not-exists list.
                iter.remove();

                addJoin(outerTuple, innerTuple);

                if (results == null) {
                    results = matchedOuterTuples;
                }

                results.add(outerTuple);
            }
        }

        return results;
    }

    /**
     * @param innerTuple
     * @return Collection of outer Tuples that were connected to this inner Tuple and do not have a
     *         match anymore or <code>null</code>.
     */
    @Override
    public Collection<Tuple> removeInner(GlobalContext globalContext, DefaultQueryContext queryContext,
                                         Tuple innerTuple) {
        Collection<Tuple> results = null;

        if (disconnectedOuterTuples != null) {
            disconnectedOuterTuples.clear();
        }
        else {
            disconnectedOuterTuples = new AppendOnlyQueue<Tuple>(queryContext.getArrayPool());
        }

        innerIdsAndTuples.remove(innerTuple.getId());

        CustomHashSet<Number> outerTupleIds = innerAndOuterTupleJoin.remove(innerTuple.getId());
        if (outerTupleIds != null) {
            for (Number outerTupleId : outerTupleIds) {
                Tuple outerTuple = outerExistsIdsAndTuples.remove(outerTupleId);

                removeJoin(outerTuple);

                if (findMatchAndConnect(globalContext, queryContext, outerTuple) == false) {
                    outerNotExistsIdsAndTuples.put(outerTupleId, outerTuple);

                    if (results == null) {
                        results = disconnectedOuterTuples;
                    }

                    results.add(outerTuple);
                }
            }
        }

        return results;
    }

    /**
     * @param outerTuple
     * @return <code>true</code> if this Tuple was matched with an inner Tuple.
     */
    @Override
    public Boolean removeOuter(GlobalContext globalContext, DefaultQueryContext queryContext,
                               String alias, Tuple outerTuple) {
        Tuple foundOuter = outerExistsIdsAndTuples.remove(outerTuple.getId());
        if (foundOuter != null) {
            removeJoin(outerTuple);
        }
        else {
            outerNotExistsIdsAndTuples.remove(outerTuple.getId());
        }

        return foundOuter != null;
    }

    /**
     * @param outerTuple
     * @return <code>true</code> if match was found and connection was made.
     */
    protected boolean findMatchAndConnect(GlobalContext globalContext, DefaultQueryContext queryContext,
                                          Tuple outerTuple) {
        boolean retVal = false;

        for (Tuple innerTuple : innerIdsAndTuples.values()) {
            boolean match = match(globalContext, queryContext, outerTuple, innerTuple);
            if (match) {
                outerExistsIdsAndTuples.put(outerTuple.getId(), outerTuple);
                addJoin(outerTuple, innerTuple);

                retVal = true;
                break;
            }
        }

        return retVal;
    }

    protected void addJoin(Tuple outerTuple, Tuple innerTuple) {
        Number innerTupleId = innerTuple.getId();

        outerAndInnerTupleJoin.put(outerTuple.getId(), innerTupleId);

        CustomHashSet<Number> outerTupleIds = innerAndOuterTupleJoin.get(innerTupleId);
        if (outerTupleIds == null) {
            outerTupleIds = new CustomHashSet<Number>();
            innerAndOuterTupleJoin.put(innerTupleId, outerTupleIds);
        }
        outerTupleIds.add(outerTuple.getId());
    }

    protected void removeJoin(Tuple outerTuple) {
        Number innerTupleId = outerAndInnerTupleJoin.remove(outerTuple.getId());

        CustomHashSet<Number> outerTupleIds = innerAndOuterTupleJoin.get(innerTupleId);
        outerTupleIds.remove(outerTuple.getId());

        if (outerTupleIds.isEmpty()) {
            innerAndOuterTupleJoin.remove(innerTupleId);
        }
    }
}
