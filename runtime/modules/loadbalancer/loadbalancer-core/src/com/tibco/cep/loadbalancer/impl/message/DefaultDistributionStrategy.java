package com.tibco.cep.loadbalancer.impl.message;

import static com.tibco.cep.loadbalancer.impl.message.DistributionKeyMaker.$makeKeys;

import com.tibco.cep.loadbalancer.message.DistributionKey;
import com.tibco.cep.loadbalancer.message.DistributionStrategy;

/*
* Author: Ashwin Jayaprakash / Date: Mar 19, 2010 / Time: 10:57:22 AM
*/
public class DefaultDistributionStrategy implements DistributionStrategy {
    protected DistributionKey[] bootstrapKeys;

    protected Object seed;

    protected int numKeys;

    public DefaultDistributionStrategy(Object seed) {
        this(seed, DistributionKeyMaker.DEFAULT_NUM_KEYS);
    }

    public DefaultDistributionStrategy(Object seed, int numKeys) {
        this.seed = seed;
        this.numKeys = numKeys;

        this.bootstrapKeys = $makeKeys(seed, numKeys);
    }

    @Override
    public DistributionKey[] getBootstrapKeys() {
        return bootstrapKeys;
    }

    public Object getSeed() {
        return seed;
    }
}
