package com.tibco.cep.persister.bdb;

/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 2/10/12
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class BDBPersistenceConstants {
    public static final String KeyName = "k";
    public static final String ValueName = "v";
    public static final String SecondaryDbNameSuffix = "_INDEX";
    public static int defaultCodecNumFields = 2;
    public static final String JMX_ROOT_NAME = "com.tibco.be:type=Persistence,subType=BerkeleyDB";
}
