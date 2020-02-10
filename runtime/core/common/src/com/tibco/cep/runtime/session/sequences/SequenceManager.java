package com.tibco.cep.runtime.session.sequences;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Mar 11, 2009
 * Time: 3:11:40 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SequenceManager {

    void createSequence(String name, long start, long end, int batchSize, boolean useDB) throws Exception;

    void resetSequence(String name, long start) throws Exception;

    void removeSequence(String name) throws Exception;

    long nextSequence(String name) throws Exception;
}
