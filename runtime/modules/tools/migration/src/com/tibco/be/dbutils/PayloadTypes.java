package com.tibco.be.dbutils;

/**
 * Created by IntelliJ IDEA.
 * User: aamaya
 * Date: Nov 25, 2006
 * Time: 4:36:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class PayloadTypes {
    public final static int XINODE_PAYLOAD_STRING_TYPE_SQL = -1;
    public final static int XINODE_PAYLOAD_OBJECT_TYPE_SQL = -999;
    public final static int OBJECT_PAYLOAD_OBJECT_TYPE_SQL = -1000;
    public final static int TIBRVMSG_PAYLOAD_TYPE_SQL = -1001;
    public final static int XINODE_PAYLOAD_TYPE_ENGINE = 1;
    public final static int OBJECT_PAYLOAD_TYPE_ENGINE = 2;
    public final static int TIBRVMSG_PAYLOAD_TYPE_ENGINE = 3;

    public static int convertPayloadTypeEngineToSQL(int engineType) {
        switch(engineType) {
            case OBJECT_PAYLOAD_TYPE_ENGINE:
                return OBJECT_PAYLOAD_OBJECT_TYPE_SQL;
            case XINODE_PAYLOAD_TYPE_ENGINE:
                return XINODE_PAYLOAD_STRING_TYPE_SQL;
            case TIBRVMSG_PAYLOAD_TYPE_ENGINE:
                return TIBRVMSG_PAYLOAD_TYPE_SQL;
            default:
                return XINODE_PAYLOAD_STRING_TYPE_SQL;
        }
    }

    public static int convertPayloadTypeSQLToEngine(int sqlType) {
        //sqlType > 0 indicates XiNodePayload and the value
        //of sqlType is the lenght of the payload in bytes 
        if(sqlType > 0) {
            return XINODE_PAYLOAD_TYPE_ENGINE;
        }

        switch(sqlType) {
            case XINODE_PAYLOAD_STRING_TYPE_SQL:
                return XINODE_PAYLOAD_TYPE_ENGINE;
        case XINODE_PAYLOAD_OBJECT_TYPE_SQL:
            return -1;
        case OBJECT_PAYLOAD_OBJECT_TYPE_SQL:
            return OBJECT_PAYLOAD_TYPE_ENGINE;
        case TIBRVMSG_PAYLOAD_TYPE_SQL:
            return TIBRVMSG_PAYLOAD_TYPE_ENGINE;
        default:
            return XINODE_PAYLOAD_TYPE_ENGINE;
        }
    }
}
