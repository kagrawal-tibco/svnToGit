package com.tibco.cep.persister;

/**
 * Created by IntelliJ IDEA.
 * User: pgowrish
 * Date: 1/10/12
 * Time: 11:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class ASPersistenceConstants {

    public static final String PersistenceMetaSpaceName = "MetaSpace";
    public static final String PersistenceMembershipSpace = "PersisterSpace";
    public static final String KeyName = "PersisterKey";
    public static final String ValueName = "Ownership";
    public static final String SpaceKey = "Owner"; //this is the key on which the space will be locked
    public static final String PersistenceMetaInfoFileName = "owner.meta";
    
    
    public static final String DefaultPersistenceDirectory = "./datastore";
    
    public static final String ViewDelimiter = "::";
    public static final int ListenerTimeout = 3000; //milliseconds
    public static final int LocalMemberRetry = 10; //milliseconds

    public static final int readBatch = 5000;
    public static final int writeBatch = 5000;
    public static final int takeBatch = 5000;


}
