package com.tibco.cep.runtime.service.om.impl.datastore.berkeleydb;

import com.tibco.cep.runtime.service.om.impl.datastore._retired_.Serializer;

/**
 * Created by IntelliJ IDEA.
 * User: nbansal
 * Date: Jul 13, 2004
 * Time: 4:13:09 PM
 * To change this template use File | Settings | File Templates.
 */
public final class BerkeleyDBConfig {

    String dbname = "BEObjects";
    String secondarydb = "BEUriMap";
    Serializer serializer;

    BerkeleyDBConfig() {

    }

    BerkeleyDBConfig(String dbname, String secondaryDbname) {
        this.dbname = dbname;
        this.secondarydb = secondaryDbname;
    }

    void setDbName(String dbname) {
        this.dbname = dbname;
    }

    void setSecondayDbName(String dbname) {
        this.secondarydb = dbname;
    }

    void setSerializer(Serializer s) {
        this.serializer = s;
    }
}