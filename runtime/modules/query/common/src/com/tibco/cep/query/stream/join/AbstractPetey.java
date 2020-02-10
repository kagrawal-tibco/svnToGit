package com.tibco.cep.query.stream.join;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.tibco.cep.query.stream.context.DefaultQueryContext;
import com.tibco.cep.query.stream.context.GlobalContext;
import com.tibco.cep.query.stream.expression.ExpressionEvaluator;
import com.tibco.cep.query.stream.monitor.CustomRuntimeException;
import com.tibco.cep.query.stream.monitor.KnownResource;
import com.tibco.cep.query.stream.monitor.ResourceId;
import com.tibco.cep.query.stream.tuple.Tuple;
import com.tibco.cep.query.stream.tuple.TupleInfo;

/*
 * Author: Ashwin Jayaprakash Date: Oct 16, 2007 Time: 11:33:44 AM
 */

/**
 * The name is a pun on Rete.
 */
public abstract class AbstractPetey implements KnownResource {
    protected final ResourceId resourceId;

    protected Map<String, TupleInfo> outerTupleAliasAndInfos;

    protected final TupleInfo innerTupleInfo;

    protected final String innerTupleAlias;

    protected ReteAdapter rete;

    public AbstractPetey(PeteyInfo peteyInfo) {
        this.resourceId = peteyInfo.getResourceId();
        this.outerTupleAliasAndInfos = peteyInfo.getOuterTupleAliasAndInfos();
        this.innerTupleInfo = peteyInfo.getInnerTupleInfo();
        this.innerTupleAlias = peteyInfo.getInnerTupleAlias();

        HashSet<Class> set = new HashSet<Class>();
        for (TupleInfo info : this.outerTupleAliasAndInfos.values()) {
            set.add(info.getContainerClass());
        }
        if (set.size() != this.outerTupleAliasAndInfos.size()) {
            throw new CustomRuntimeException(resourceId,
                    "Outer Tuples have to be unique Tuple types.");
        }
        if (set.contains(this.innerTupleInfo.getContainerClass())) {
            /*
             * If the 2 Tuple are of the same type, then they will clash in the
             * Rete when the same Tuple is being inserted from both Streams.
             */
            throw new CustomRuntimeException(resourceId,
                    "Inner and Outer Tuples cannot be of the same output Tuple type");
        }
    }

    public ResourceId getResourceId() {
        return resourceId;
    }

    public void init(ReteAdapter reteWM) {
        this.rete = reteWM;
    }

    public abstract Object addOuter(GlobalContext globalContext, DefaultQueryContext queryContext,
                                    String alias, Tuple tuple);

    public abstract Object addInner(GlobalContext globalContext, DefaultQueryContext queryContext,
                                    Tuple tuple);

    public abstract Object removeInner(GlobalContext globalContext, DefaultQueryContext queryContext,
                                       Tuple tuple);

    public abstract Object removeOuter(GlobalContext globalContext, DefaultQueryContext queryContext,
                                       String alias, Tuple tuple);

    public abstract ExpressionEvaluator generateExpressionEvaluator();

    public Map<String, TupleInfo> getOuterTupleAliasAndInfos() {
        return outerTupleAliasAndInfos;
    }

    public Set<String> getOuterTupleAliases() {
        return outerTupleAliasAndInfos.keySet();
    }

    public String getInnerTupleAlias() {
        return innerTupleAlias;
    }

    public TupleInfo getInnerTupleInfo() {
        return innerTupleInfo;
    }

    public void discard() {
        this.rete = null;
    }

    // ----------

    /**
     * Builder class.
     */
    public static class PeteyInfo implements KnownResource {
        protected ResourceId resourceId;

        protected final HashMap<String, TupleInfo> outerTupleAliasAndInfos;

        protected TupleInfo innerTupleInfo;

        protected String innerTupleAlias;

        public PeteyInfo() {
            this.outerTupleAliasAndInfos = new HashMap<String, TupleInfo>();
        }

        public ResourceId getResourceId() {
            return resourceId;
        }

        public void setResourceId(ResourceId resourceId) {
            this.resourceId = resourceId;
        }

        public String getInnerTupleAlias() {
            return innerTupleAlias;
        }

        public void setInnerTupleAlias(String innerTupleAlias) {
            this.innerTupleAlias = innerTupleAlias;
        }

        public TupleInfo getInnerTupleInfo() {
            return innerTupleInfo;
        }

        public void setInnerTupleInfo(TupleInfo innerTupleInfo) {
            this.innerTupleInfo = innerTupleInfo;
        }

        public Set<String> getOuterTupleAliases() {
            return outerTupleAliasAndInfos.keySet();
        }

        public void addOuterTupleAliasAndInfo(String alias, TupleInfo info) {
            outerTupleAliasAndInfos.put(alias, info);
        }

        public Map<String, TupleInfo> getOuterTupleAliasAndInfos() {
            return outerTupleAliasAndInfos;
        }
    }
}
