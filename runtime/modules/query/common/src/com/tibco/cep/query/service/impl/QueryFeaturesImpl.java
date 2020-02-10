package com.tibco.cep.query.service.impl;

import com.tibco.cep.query.service.QueryFeatures;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/25/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class QueryFeaturesImpl implements QueryFeatures {

    private boolean selfJoin = false;

    @Override
    public boolean isSelfJoin() {
        return selfJoin;
    }

    @Override
    public void setSelfJoin(boolean selfJoin) {
        this.selfJoin = selfJoin;
    }
}
