package com.tibco.be.oracle;

/**
 * Created by IntelliJ IDEA.
 * User: pdhar
 * Date: Oct 16, 2008
 * Time: 11:05:55 AM
 * To change this template use File | Settings | File Templates.
 */
public interface OracleStoreMBean {

    String getCacheName();

    String getClassName();

    Long getPartitionId();

    String getOracleTableName();

    boolean isDebug();

    void setDebug(boolean debug);
}
