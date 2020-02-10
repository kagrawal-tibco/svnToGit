/*
 * Copyright (c) TIBCO Software Inc 2010.
 * All rights reserved.
 *
 * This software is confidential and proprietary information of
 * TIBCO Software Inc.
 *
 * Author: Suresh Subramani (suresh.subramani@tibco.com)
 * Date  : 11/8/2010
 */

package com.tibco.cep.runtime.service.om.impl.coherence.cluster.util;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Comparator;

import com.tangosol.io.ExternalizableLite;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.service.cluster.CacheClusterProvider;
import com.tibco.cep.runtime.service.cluster.Cluster;
import com.tibco.cep.runtime.service.loader.BEClassLoader;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 22, 2009
 * Time: 6:07:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class RuleFunctionComparator implements Comparator, ExternalizableLite {
    private String s_ruleFunction;
    private RuleFunction ruleFunction;

    public RuleFunctionComparator(String ruleFunction) {
        this.s_ruleFunction = ruleFunction;
    }

    public int compare(Object o1, Object o2) {
        if (ruleFunction == null) {
            Cluster cluster = CacheClusterProvider.getInstance().getCacheCluster();
            ruleFunction = ((BEClassLoader) cluster.getRuleServiceProvider().getTypeManager()).getRuleFunctionInstance(s_ruleFunction);
        }
        Object[] args = {o1, o2};
        Integer ret = (Integer) ruleFunction.invoke(args);
        return ret.intValue();
    }

    public void readExternal(DataInput dataInput) throws IOException {
        s_ruleFunction = dataInput.readUTF();
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(s_ruleFunction);
    }
}
