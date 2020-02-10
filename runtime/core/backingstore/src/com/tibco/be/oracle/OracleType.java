package com.tibco.be.oracle;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tibco.be.parser.codegen.CGConstants;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 20, 2006
 * Time: 10:34:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleType {

    public final static int TYPE_ORACLE_PRIMITIVE= 1;

    public final static OracleType TYPE_STRING   = new OracleType("varchar2(255)", true);
    public final static OracleType TYPE_NUMBER   = new OracleType("NUMBER", true);
    public final static OracleType TYPE_INTEGER  = new OracleType("INTEGER", true);
    public final static OracleType TYPE_DATETIME = new OracleType("T_DATETIME", true);
    public final static OracleType TYPE_BOOLEAN  = new OracleType("INT", true);
    public final static OracleType TYPE_XML      = new OracleType("XMLTYPE", true);
    public final static OracleType TYPE_CLOB     = new OracleType("CLOB", true);
    public final static OracleType TYPE_BLOB     = new OracleType("BLOB", true);

    public final static String     STRING_COL_ORACLE_TYPE                         = "T_STRING_COL";
    public final static String     STRING_HIST_TUPLE_ORACLE_TYPE                  = "T_STRING_HIST_TUPLE";
    public final static String     STRING_HIST_TABLE_ORACLE_TYPE                  = "T_STRING_HIST_TABLE";
    public final static String     STRING_HIST_ORACLE_TYPE                        = "T_STRING_HIST";
    public final static String     STRING_COL_HIST_TUPLE_ORACLE_TYPE              = "T_STRING_COL_HIST_TUPLE";
    public final static String     STRING_COL_HIST_TABLE_ORACLE_TYPE              = "T_STRING_COL_HIST_TABLE";
    public final static String     STRING_COL_HIST_ORACLE_TYPE                    = "T_STRING_COL_HIST";

    public final static String     NUMBER_COL_ORACLE_TYPE                         = "T_NUMBER_COL";
    public final static String     NUMBER_HIST_TUPLE_ORACLE_TYPE                  = "T_NUMBER_HIST_TUPLE";
    public final static String     NUMBER_HIST_TABLE_ORACLE_TYPE                  = "T_NUMBER_HIST_TABLE";
    public final static String     NUMBER_HIST_ORACLE_TYPE                        = "T_NUMBER_HIST";
    public final static String     NUMBER_COL_HIST_TUPLE_ORACLE_TYPE              = "T_NUMBER_COL_HIST_TUPLE";
    public final static String     NUMBER_COL_HIST_TABLE_ORACLE_TYPE              = "T_NUMBER_COL_HIST_TABLE";
    public final static String     NUMBER_COL_HIST_ORACLE_TYPE                    = "T_NUMBER_COL_HIST";

    public final static String     INTEGER_COL_ORACLE_TYPE                        = "T_INT_COL";
    public final static String     INTEGER_HIST_TUPLE_ORACLE_TYPE                 = "T_INT_HIST_TUPLE";
    public final static String     INTEGER_HIST_TABLE_ORACLE_TYPE                 = "T_INT_HIST_TABLE";
    public final static String     INTEGER_HIST_ORACLE_TYPE                       = "T_INT_HIST";
    public final static String     INTEGER_COL_HIST_TUPLE_ORACLE_TYPE             = "T_INT_COL_HIST_TUPLE";
    public final static String     INTEGER_COL_HIST_TABLE_ORACLE_TYPE             = "T_INT_COL_HIST_TABLE";
    public final static String     INTEGER_COL_HIST_ORACLE_TYPE                   = "T_INT_COL_HIST";

    public final static String     BOOLEAN_COL_ORACLE_TYPE                        = "T_BOOLEAN_COL";
    public final static String     BOOLEAN_HIST_TUPLE_ORACLE_TYPE                 = "T_BOOLEAN_HIST_TUPLE";
    public final static String     BOOLEAN_HIST_TABLE_ORACLE_TYPE                 = "T_BOOLEAN_HIST_TABLE";
    public final static String     BOOLEAN_HIST_ORACLE_TYPE                       = "T_BOOLEAN_HIST";
    public final static String     BOOLEAN_COL_HIST_TUPLE_ORACLE_TYPE             = "T_BOOLEAN_COL_HIST_TUPLE";
    public final static String     BOOLEAN_COL_HIST_TABLE_ORACLE_TYPE             = "T_BOOLEAN_COL_HIST_TABLE";
    public final static String     BOOLEAN_COL_HIST_ORACLE_TYPE                   = "T_BOOLEAN_COL_HIST";

    public final static String     DATETIME_COL_ORACLE_TYPE                       = "T_DATETIME_COL";
    public final static String     DATETIME_HIST_TUPLE_ORACLE_TYPE                = "T_DATETIME_HIST_TUPLE";
    public final static String     DATETIME_HIST_TABLE_ORACLE_TYPE                = "T_DATETIME_HIST_TABLE";
    public final static String     DATETIME_HIST_ORACLE_TYPE                      = "T_DATETIME_HIST";
    public final static String     DATETIME_COL_HIST_TUPLE_ORACLE_TYPE            = "T_DATETIME_COL_HIST_TUPLE";
    public final static String     DATETIME_COL_HIST_TABLE_ORACLE_TYPE            = "T_DATETIME_COL_HIST_TABLE";
    public final static String     DATETIME_COL_HIST_ORACLE_TYPE                  = "T_DATETIME_COL_HIST";

    public final static String     BASE_ENTITY_ORACLE_TYPE                        = "T_ENTITY";
    public final static String     BASE_ENTITYREF_ORACLE_TYPE                     = "T_ENTITY_REF";
    public final static String     BASE_CONCEPT_ORACLE_TYPE                       = "T_CONCEPT";
    public final static String     BASE_EVENT_ORACLE_TYPE                         = "T_EVENT";
    public final static String     BASE_SIMPLEEVENT_ORACLE_TYPE                   = "T_SIMPLE_EVENT";
    public final static String     BASE_TIMEEVENT_ORACLE_TYPE                     = "T_TIME_EVENT";
    public final static String     BASE_ENTITYREF_TABLE_ORACLE_TYPE               = "T_ENTITY_REF_TABLE";
    public final static String     BASE_ENTITYREF_HIST_TUPLE_ORACLE_TYPE          = "T_ENTITYREF_HIST_TUPLE";
    public final static String     BASE_ENTITYREF_HIST_TABLE_ORACLE_TYPE          = "T_ENTITYREF_HIST_TABLE";
    public final static String     BASE_ENTITYREF_COL_ORACLE_TYPE                 = "T_ENTITY_REF_TABLE";

    public final static String     BASE_ENTITYREF_HIST_ORACLE_TYPE                =  "T_ENTITYREF_HIST";
    public final static String     BASE_ENTITYREF_COL_HIST_ORACLE_TYPE            =  "T_ENTITYREF_COL_HIST";

    public final static String     BASE_STATEMACHINE_CONCEPT_ORACLE_TYPE          = "T_STATEMACHINE_CONCEPT";

    protected String name;
    private static final String BRK = CGConstants.BRK;

    protected boolean isPrimitive = false;

    //To help generate migration script. This is the state of this Type
    public static enum State { NEW, MODIFIED, UNCHANGED, DELETED };
    protected  State state = State.NEW;

    protected String superType = null;
    protected String tableOf=null;

    protected List members = new ArrayList();
    protected Map<String,  MemberInfo> memberMap = new LinkedHashMap<String,  MemberInfo>();

    /**
     *
     * @param name
     * @param isPrimitive
     */
    public OracleType(String name, boolean isPrimitive) {
        this.name = name;
        this.isPrimitive=isPrimitive;
    }

    public OracleType(String name, boolean isPrimitive, String tableOf) {
        this.name = name;
        this.isPrimitive=isPrimitive;
        this.tableOf = tableOf;
    }

    public OracleType(String name, boolean isPrimitive, String[] memberNames, String[] memberTypes) {
        this.name = name;
        this.isPrimitive=isPrimitive;
        if (memberNames != null) {
            for (int i=0; i < memberNames.length;i++) {
                addMember(memberNames[i], memberTypes[i]);
            }
        }
    }

    /**
     *
     * @param superType
     */
    public void setSuperType(String superType) {
        this.superType = superType;
    }

    /**
     *
     * @param tableOf
     */
    public void setTableType (String tableOf) {
        this.tableOf=tableOf;
    }

    /**
     *
     * @param memberName
     * @param memberType
     */
    public MemberInfo addMember (String memberName, String memberType) {
        members.add(memberName + " " + memberType);
        MemberInfo mInfo = new MemberInfo(memberName,memberType);
        memberMap.put(memberName.toUpperCase(), mInfo);
        return mInfo;
    }

    public MemberInfo addMember (String memberName, String memberType, int len) {
        members.add(memberName + " " + memberType);
        MemberInfo mInfo = new MemberInfo(memberName,memberType);
        mInfo.length = len;
        memberMap.put(memberName.toUpperCase(), mInfo);
        return mInfo;
    }

    public static class MemberInfo {

        public static final int Deleted =1;
        public static final int Added =2;
        public static final int Altered =3;
        public static final int NotChanged =4;
        public static final int Error =5;

        String memberName;
        String memberType;
        int length=-1;
        // This is the state of this member (field)
        int state;

        public MemberInfo(String name, String type){
         memberName=name;
         memberType=type;
         state = Added;
        }
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    @Deprecated
    public StringBuffer toStringBuffer(boolean generateAlter) {
        if (generateAlter) {
            return genAlterScript();
        }
        StringBuffer ret = new StringBuffer();

        ret.append("CREATE OR REPLACE TYPE " + name + " ");
        if (superType != null) {
            ret.append(" UNDER " + superType + " ");
        }
        if (tableOf != null) {
            ret.append(" AS TABLE OF " + tableOf + " " +  BRK );
        } else {
            if (members.size() > 0) {
                if (superType == null) {
                    ret.append(" AS OBJECT( " + BRK );
                } else {
                    ret.append(" ( " + BRK );
                }
                for (int i=0; i < members.size();i++) {
                    if (i > 0) ret.append(",");
                    ret.append(members.get(i));
                    ret.append(BRK);
                }
                ret.append(") NOT FINAL" );
            } else {
                ret.append("() NOT FINAL");
            }
        }
        ret.append(";" + BRK);
        return ret;
    }

    @Deprecated 
    public StringBuffer toStringBuffer() {
        StringBuffer ret = new StringBuffer();

        if (members.size() > 0) {
            for (int i=0; i < members.size();i++) {
                ret.append("ALTER TYPE " + name + " ADD ATTRIBUTE " + members.get(i) + "  INVALIDATE ;" + BRK);
            }
        }
        return ret;
    }

    public StringBuffer genAlterScript() {
        StringBuffer ret = new StringBuffer();
        for (MemberInfo memberInfo : memberMap.values()) {
            if (memberInfo.state == MemberInfo.Added)
                ret.append("ALTER TYPE " + name + " ADD ATTRIBUTE " + memberInfo.memberName + " " + memberInfo.memberType + "  INVALIDATE ;" + BRK);
            else if (memberInfo.state == MemberInfo.Deleted)
                ret.append("ALTER TYPE " + name + " DROP ATTRIBUTE " + memberInfo.memberName + " " + memberInfo.memberType + "  INVALIDATE ;" + BRK);
            else if (memberInfo.state == MemberInfo.Altered)
                 ret.append("ALTER TYPE " + name + " MODIFY ATTRIBUTE " + memberInfo.memberName + " " + memberInfo.memberType + "  INVALIDATE ;" + BRK);
        }
        return ret;
    }

    public String toString() {
        return toStringBuffer(false).toString();
    }
}
