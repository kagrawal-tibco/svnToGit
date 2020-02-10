package com.tibco.cep.query.stream.join;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;
import com.tibco.cep.query.stream.util.AppendOnlyQueue;
import com.tibco.cep.query.stream.util.CustomHashSet;
import com.tibco.cep.query.stream.util.SingleElementCollection;

/*
 * Author: Ashwin Jayaprakash Date: Oct 22, 2007 Time: 4:09:58 PM
 */

public abstract class GenericExistsPetey2 extends AbstractExistsPetey {
    protected final String[] outerAliases;

    protected final HashMap<String, Integer> outerAliasAndPositions;

    protected final HashMap<String, HashMap<Number, TupleHolder>> outerAliasIdAndHolders;

    protected final HashMap<Number, TupleHolder> innerIdAndHolders;

    protected final HashMap<Long, Group> existingGroupHashAndGroups;

    private Collection<Group> disconnectedOuterTuples;

    public GenericExistsPetey2(ExistsPeteyInfo peteyInfo) {
        super(peteyInfo);

        Map<String, TupleInfo> map = peteyInfo.getOuterTupleAliasAndInfos();
        this.outerAliases = new String[map.size()];
        int i = 0;
        for (String alias : map.keySet()) {
            this.outerAliases[i++] = alias;
        }

        this.outerAliasAndPositions = new HashMap<String, Integer>();
        for (i = 0; i < this.outerAliases.length; i++) {
            this.outerAliasAndPositions.put(this.outerAliases[i], i);
        }

        this.outerAliasIdAndHolders = new HashMap<String, HashMap<Number, TupleHolder>>();
        for (String alias : this.outerAliases) {
            outerAliasIdAndHolders.put(alias, new HashMap<Number, TupleHolder>());
        }

        this.innerIdAndHolders = new HashMap<Number, TupleHolder>();

        this.existingGroupHashAndGroups = new HashMap<Long, Group>();
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param outerTupleAliases
     * @param outerTuples       Same order as <code>outerTupleAliases</code>.
     * @param innerTuple
     * @return
     */
    protected abstract boolean match(GlobalContext globalContext, DefaultQueryContext queryContext,
                                     String[] outerTupleAliases, Tuple[] outerTuples,
                                     Tuple innerTuple);

    /**
     * @param outerTuple
     * @return <code>true</code> if this outer Tuple found a matching inner Tuple.
     */
    @Override
    public Boolean addOuter(GlobalContext globalContext, DefaultQueryContext queryContext, String alias,
                            Tuple outerTuple) {
        HashMap<Number, TupleHolder> outerHolders = outerAliasIdAndHolders.get(alias);
        outerHolders.put(outerTuple.getId(), new TupleHolder(outerTuple));

        // ---------

        return findMatchAndJoinOuter(globalContext, queryContext, alias, outerTuple);
    }

    /**
     * @param innerTuple
     * @return Collection of Groups that match this inner Tuple or <code>null</code>.
     */
    @Override
    public Collection<Group> addInner(GlobalContext globalContext, DefaultQueryContext queryContext,
                                      Tuple innerTuple) {
        innerIdAndHolders.put(innerTuple.getId(), new TupleHolder(innerTuple));

        // ---------

        return findMatchAndJoin(globalContext, queryContext, new Tuple[outerAliases.length],
                innerTuple);
    }

    /**
     * @param innerTuple
     * @return Collection of Groups that were connected to this inner Tuple and do not have a match
     *         anymore or <code>null</code>.
     */
    @Override
    public Collection<Group> removeInner(GlobalContext globalContext, DefaultQueryContext queryContext,
                                         Tuple innerTuple) {
        TupleHolder innerHolder = innerIdAndHolders.remove(innerTuple.getId());

        Collection<Group> results = null;

        if (disconnectedOuterTuples != null) {
            disconnectedOuterTuples.clear();
        }
        else {
            disconnectedOuterTuples = new AppendOnlyQueue<Group>(queryContext.getArrayPool());
        }

        // ---------

        if (innerHolder.isMemberOfAnyGroup()) {
            Tuple[] outerRetriedWithAnotherInner = new Tuple[outerAliases.length];
            Set<Group> groups = innerHolder.getMemberOfGroups();
            for (Group group : groups) {
                Number[] outerTupleIds = group.getOuterTupleIds();

                for (int i = 0; i < outerTupleIds.length; i++) {
                    HashMap<Number, TupleHolder> holderMap = outerAliasIdAndHolders
                            .get(outerAliases[i]);
                    TupleHolder outerHolder = holderMap.get(outerTupleIds[i]);
                    outerHolder.removeGroup(group);

                    outerRetriedWithAnotherInner[i] = outerHolder.getTuple();
                }

                Collection<Group> newMatches = findMatchAndJoin(globalContext, queryContext,
                        outerRetriedWithAnotherInner, null);
                if (newMatches == null) {
                    if (results == null) {
                        results = disconnectedOuterTuples;
                    }

                    results.add(group);
                }
            }
        }

        // ---------

        innerHolder.discard();

        return results;
    }

    /**
     * @param outerTuple
     * @return <code>true</code> if this Tuple was matched with an inner Tuple.
     */
    @Override
    public Boolean removeOuter(GlobalContext globalContext, DefaultQueryContext queryContext,
                               String alias, Tuple outerTuple) {
        HashMap<Number, TupleHolder> map = outerAliasIdAndHolders.get(alias);
        TupleHolder outerHolder = map.get(outerTuple.getId());

        boolean b = outerHolder.isMemberOfAnyGroup();
        if (b) {
            Set<Group> groups = outerHolder.getMemberOfGroups();
            for (Group group : groups) {
                Number[] outerTupleIds = group.getOuterTupleIds();

                for (int i = 0; i < outerTupleIds.length; i++) {
                    HashMap<Number, TupleHolder> holderMap = outerAliasIdAndHolders
                            .get(outerAliases[i]);
                    TupleHolder outerGroupMember = holderMap.get(outerTupleIds[i]);
                    // Don't remove self - causes Concurrent mod error.
                    if (outerGroupMember != outerHolder) {
                        outerGroupMember.removeGroup(group);
                    }
                }
            }
        }

        outerHolder.discard();

        return b;
    }

    /**
     * @param globalContext
     * @param queryContext
     * @param alias
     * @param outerTuple
     * @return <code>true</code> if match was found and connection was made.
     */
    protected boolean findMatchAndJoinOuter(GlobalContext globalContext, DefaultQueryContext queryContext,
                                            String alias, Tuple outerTuple) {
        Tuple[] tempTupleArr = new Tuple[outerAliases.length];
        // Set Tuple into the correct position.
        tempTupleArr[outerAliasAndPositions.get(alias)] = outerTuple;

        Collection<Group> newJoins = findMatchAndJoin(globalContext, queryContext, tempTupleArr,
                null);
        return newJoins != null && newJoins.isEmpty() == false;
    }

    private final CustomHashSet<Integer> fixedOuterPositions = new CustomHashSet<Integer>();

    private final String retValNewlyJoinedGroups = "$$exists2.new.joins";

    /**
     * @param globalContext
     * @param queryContext
     * @param outerTuples   Can be <code>null</code>.
     * @param innerTuple    Can be <code>null</code>.
     * @return Collection of newly joined {@link Group}s or <code>null</code>.
     */
    @SuppressWarnings("unchecked")
    protected Collection<Group> findMatchAndJoin(GlobalContext globalContext,
                                                 DefaultQueryContext queryContext, Tuple[] outerTuples,
                                                 Tuple innerTuple) {
        queryContext.getGenericStore().remove(retValNewlyJoinedGroups);

        fixedOuterPositions.clear();
        if (outerTuples != null) {
            for (int i = 0; i < outerTuples.length; i++) {
                if (outerTuples[i] != null) {
                    fixedOuterPositions.add(i);
                }
            }
        }

        recursiveLoopJoin(globalContext, queryContext,
                (outerTuples == null ? new Tuple[outerAliases.length] : outerTuples), innerTuple,
                fixedOuterPositions, 0);

        Collection<Group> newJoins = (Collection<Group>) queryContext.getGenericStore().remove(
                retValNewlyJoinedGroups);
        return newJoins;
    }

    private final SingleElementCollection<TupleHolder> tempOuterTuples =
            new SingleElementCollection<TupleHolder>(
                    null);

    private final SingleElementCollection<TupleHolder> tempInnerTuples =
            new SingleElementCollection<TupleHolder>(
                    null);

    /**
     * @param globalContext
     * @param queryContext
     * @param outerTuples       <b>Cannot</b> be <code>null</code>. If not, any or all positions in
     *                          the Array can be fixed with an outer Tuple.
     * @param innerTuple        Can be <code>null</code>.
     * @param fixedPositions    Cannot be <code>null</code>. Can contain the positions of the items
     *                          in <code>outerTuples</code> that are fixed.
     * @param currAliasArrayPos
     */
    @SuppressWarnings("unchecked")
    protected void recursiveLoopJoin(GlobalContext globalContext, DefaultQueryContext queryContext,
                                     Tuple[] outerTuples, Tuple innerTuple,
                                     CustomHashSet<Integer> fixedPositions,
                                     int currAliasArrayPos) {
        Collection<TupleHolder> outerTupleCollection = null;

        if (outerTuples[currAliasArrayPos] != null) {
            if (tempOuterTuples.isEmpty()) {
                tempOuterTuples.setElement(new TupleHolder());
            }

            // Fixed single Tuple list.
            tempOuterTuples.getElement().setTuple(outerTuples[currAliasArrayPos]);
            outerTupleCollection = tempOuterTuples;
        }
        else {
            HashMap<Number, TupleHolder> outerIdAndTuples = outerAliasIdAndHolders
                    .get(outerAliases[currAliasArrayPos]);

            outerTupleCollection = outerIdAndTuples.values();
        }

        for (TupleHolder tupleHolder : outerTupleCollection) {
            outerTuples[currAliasArrayPos] = tupleHolder.getTuple();

            // Recursion end.
            if (currAliasArrayPos == outerAliases.length - 1) {
                //todo IMP: This hashcode is not sufficient if it clashes.
                long outerTuplesGroupHash = Group.findHash(outerTuples);
                /*
                 * This group of outer Tuples already have a matching inner
                 * Tuple.
                 */
                if (existingGroupHashAndGroups.containsKey(outerTuplesGroupHash)) {
                    continue;
                }

                // ---------

                Iterator<TupleHolder> innerTuplesIter = null;

                // Use the fixed inner Tuple, if provided.
                if (innerTuple != null) {
                    if (tempInnerTuples.isEmpty()) {
                        tempInnerTuples.setElement(new TupleHolder());
                    }

                    // Fixed single Tuple list.
                    tempInnerTuples.getElement().setTuple(innerTuple);
                    innerTuplesIter = tempInnerTuples.iterator();
                }
                // Otherwise, use the entire list of registered inner Tuples.
                else {
                    innerTuplesIter = innerIdAndHolders.values().iterator();
                }

                for (; innerTuplesIter.hasNext();) {
                    Tuple currInner = innerTuplesIter.next().getTuple();

                    boolean match = match(globalContext, queryContext, outerAliases, outerTuples,
                            currInner);
                    if (match) {
                        Group group = addJoin(outerTuples, currInner);

                        Collection<Group> newlyJoinedGroups = (Collection<Group>) queryContext
                                .getGenericStore().get(retValNewlyJoinedGroups);
                        if (newlyJoinedGroups == null) {
                            newlyJoinedGroups = new LinkedList<Group>();
                            queryContext.getGenericStore().put(retValNewlyJoinedGroups,
                                    newlyJoinedGroups);
                        }
                        newlyJoinedGroups.add(group);

                        break;
                    }
                }
            }
            else {
                recursiveLoopJoin(globalContext, queryContext, outerTuples, innerTuple,
                        fixedPositions, currAliasArrayPos + 1);
            }

            // Clean up while unrolling recursion.
            if (fixedPositions.contains(currAliasArrayPos) == false) {
                outerTuples[currAliasArrayPos] = null;
            }
        }
    }

    /**
     * @param outerTuples
     * @param innerTuple
     * @return
     */
    protected Group addJoin(Tuple[] outerTuples, Tuple innerTuple) {
        Group group = new Group(outerTuples, innerTuple);

        for (int i = 0; i < outerTuples.length; i++) {
            String alias = outerAliases[i];
            HashMap<Number, TupleHolder> outerTupleMap = outerAliasIdAndHolders.get(alias);
            TupleHolder holder = outerTupleMap.get(outerTuples[i].getId());
            holder.addGroup(group);
        }

        TupleHolder holder = innerIdAndHolders.get(innerTuple.getId());
        holder.addGroup(group);

        existingGroupHashAndGroups.put(group.getOuterTuplesHash(), group);

        return group;
    }

    // ---------

    public static class Group {
        private static final int PRIME_HASH = 31;

        protected final Number[] outerTupleIds;

        protected final Number innerTupleId;

        protected final Long outerTuplesHash;

        protected final int hash;

        /**
         * @param outerTuples Same sequence as {@link GenericExistsPetey2#outerAliases}.
         * @param innerTuple
         */
        public Group(Tuple[] outerTuples, Tuple innerTuple) {
            this.outerTupleIds = new Number[outerTuples.length];
            for (int i = 0; i < outerTuples.length; i++) {
                this.outerTupleIds[i] = outerTuples[i].getId();
            }

            this.innerTupleId = innerTuple.getId();

            long h = findHash(outerTuples);
            this.outerTuplesHash = h;

            h = (h * PRIME_HASH) + this.innerTupleId.hashCode();
            this.hash = new Long(h).hashCode();
        }

        /**
         * @param outerTuples Same sequence as {@link GenericExistsPetey2#outerAliases}.
         * @return
         */
        public static Long findHash(Tuple[] outerTuples) {
            long h = 0;

            for (int i = 0; i < outerTuples.length; i++) {
                h = (h * PRIME_HASH) + outerTuples[i].getId().hashCode();
            }

            return h;
        }

        public Number getInnerTupleId() {
            return innerTupleId;
        }

        public Number[] getOuterTupleIds() {
            return outerTupleIds;
        }

        public Long getOuterTuplesHash() {
            return outerTuplesHash;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null) {
                return false;
            }

            if ((obj instanceof Group) == false) {
                return false;
            }

            Group other = (Group) obj;

            if (hash != other.hash) {
                return false;
            }

            if (innerTupleId != other.innerTupleId) {
                return false;
            }

            if (!Arrays.equals(outerTupleIds, other.outerTupleIds)) {
                return false;
            }

            return true;
        }
    }

    public static class TupleHolder {
        protected Tuple tuple;

        protected HashSet<Group> memberOfGroups;

        public TupleHolder(Tuple tuple) {
            this.tuple = tuple;
        }

        protected TupleHolder() {
            this(null);
        }

        protected void setTuple(Tuple tuple) {
            this.tuple = tuple;
        }

        public Tuple getTuple() {
            return tuple;
        }

        public boolean isMemberOfAnyGroup() {
            return memberOfGroups != null && memberOfGroups.isEmpty() == false;
        }

        /**
         * @return Can be <code>null</code>.
         */
        public HashSet<Group> getMemberOfGroups() {
            return memberOfGroups;
        }

        public void addGroup(Group group) {
            if (memberOfGroups == null) {
                memberOfGroups = new HashSet<Group>();
            }

            memberOfGroups.add(group);
        }

        public void removeGroup(Group group) {
            memberOfGroups.remove(group);
        }

        public void discard() {
            if (memberOfGroups != null) {
                memberOfGroups.clear();
                memberOfGroups = null;
            }
        }
    }
}
