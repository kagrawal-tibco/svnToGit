package com.tibco.cep.query.service;

/**
 * Created with IntelliJ IDEA.
 * User: mgharat
 * Date: 7/25/13
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
public interface QueryFeatures {
    boolean isSelfJoin();

    void setSelfJoin(boolean selfJoin);
}
