package com.tibco.be.jdbcstore;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Sep 20, 2006
 * Time: 10:34:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class JdbcType {

    public final static int TYPE_PRIMITIVE = 1;

    public final static JdbcType TYPE_STRING   = new JdbcType("STRING", true); // All primitive types need to be translated to the final database type based on the database used.
    public final static JdbcType TYPE_NUMBER   = new JdbcType("NUMBER", true);
    public final static JdbcType TYPE_INTEGER  = new JdbcType("INTEGER", true);
    public final static JdbcType TYPE_DATETIME = new JdbcType("T_DATETIME", true);
    public final static JdbcType TYPE_BOOLEAN  = new JdbcType("BOOLEAN", true);
    public final static JdbcType TYPE_LONG     = new JdbcType("LONG", true);
    public final static JdbcType TYPE_DOUBLE   = new JdbcType("DOUBLE", true);
    public final static JdbcType TYPE_XML      = new JdbcType("XMLTYPE", true);
    public final static JdbcType TYPE_CLOB     = new JdbcType("CLOB", true);
    public final static JdbcType TYPE_BLOB     = new JdbcType("BLOB", true);

    
    public final static String     STRING_COL_TYPE                         = "T_STRING_COL";
    public final static String     STRING_HIST_TUPLE_TYPE                  = "T_STRING_HIST_TUPLE";
    public final static String     STRING_HIST_TABLE_TYPE                  = "T_STRING_HIST_TABLE";
    public final static String     STRING_HIST_TYPE                        = "T_STRING_HIST";
    public final static String     STRING_COL_HIST_TUPLE_TYPE              = "T_STRING_COL_HIST_TUPLE";
    public final static String     STRING_COL_HIST_TABLE_TYPE              = "T_STRING_COL_HIST_TABLE";
    public final static String     STRING_COL_HIST_TYPE                    = "T_STRING_COL_HIST";

    public final static String     NUMBER_COL_TYPE                         = "T_NUMBER_COL";
    public final static String     NUMBER_HIST_TUPLE_TYPE                  = "T_NUMBER_HIST_TUPLE";
    public final static String     NUMBER_HIST_TABLE_TYPE                  = "T_NUMBER_HIST_TABLE";
    public final static String     NUMBER_HIST_TYPE                        = "T_NUMBER_HIST";
    public final static String     NUMBER_COL_HIST_TUPLE_TYPE              = "T_NUMBER_COL_HIST_TUPLE";
    public final static String     NUMBER_COL_HIST_TABLE_TYPE              = "T_NUMBER_COL_HIST_TABLE";
    public final static String     NUMBER_COL_HIST_TYPE                    = "T_NUMBER_COL_HIST";

    public final static String     INTEGER_COL_TYPE                        = "T_INT_COL";
    public final static String     INTEGER_HIST_TUPLE_TYPE                 = "T_INT_HIST_TUPLE";
    public final static String     INTEGER_HIST_TABLE_TYPE                 = "T_INT_HIST_TABLE";
    public final static String     INTEGER_HIST_TYPE                       = "T_INT_HIST";
    public final static String     INTEGER_COL_HIST_TUPLE_TYPE             = "T_INT_COL_HIST_TUPLE";
    public final static String     INTEGER_COL_HIST_TABLE_TYPE             = "T_INT_COL_HIST_TABLE";
    public final static String     INTEGER_COL_HIST_TYPE                   = "T_INT_COL_HIST";

    public final static String     LONG_COL_TYPE                           = "T_LONG_COL";
    public final static String     LONG_HIST_TUPLE_TYPE                    = "T_LONG_HIST_TUPLE";
    public final static String     LONG_HIST_TABLE_TYPE                    = "T_LONG_HIST_TABLE";
    public final static String     LONG_HIST_TYPE                          = "T_LONG_HIST";
    public final static String     LONG_COL_HIST_TUPLE_TYPE                = "T_LONG_COL_HIST_TUPLE";
    public final static String     LONG_COL_HIST_TABLE_TYPE                = "T_LONG_COL_HIST_TABLE";
    public final static String     LONG_COL_HIST_TYPE                      = "T_LONG_COL_HIST";

    public final static String     DOUBLE_COL_TYPE                         = "T_DOUBLE_COL";
    public final static String     DOUBLE_HIST_TUPLE_TYPE                  = "T_DOUBLE_HIST_TUPLE";
    public final static String     DOUBLE_HIST_TABLE_TYPE                  = "T_DOUBLE_HIST_TABLE";
    public final static String     DOUBLE_HIST_TYPE                        = "T_DOUBLE_HIST";
    public final static String     DOUBLE_COL_HIST_TUPLE_TYPE              = "T_DOUBLE_COL_HIST_TUPLE";
    public final static String     DOUBLE_COL_HIST_TABLE_TYPE              = "T_DOUBLE_COL_HIST_TABLE";
    public final static String     DOUBLE_COL_HIST_TYPE                    = "T_DOUBLE_COL_HIST";

    public final static String     BOOLEAN_COL_TYPE                        = "T_BOOLEAN_COL";
    public final static String     BOOLEAN_HIST_TUPLE_TYPE                 = "T_BOOLEAN_HIST_TUPLE";
    public final static String     BOOLEAN_HIST_TABLE_TYPE                 = "T_BOOLEAN_HIST_TABLE";
    public final static String     BOOLEAN_HIST_TYPE                       = "T_BOOLEAN_HIST";
    public final static String     BOOLEAN_COL_HIST_TUPLE_TYPE             = "T_BOOLEAN_COL_HIST_TUPLE";
    public final static String     BOOLEAN_COL_HIST_TABLE_TYPE             = "T_BOOLEAN_COL_HIST_TABLE";
    public final static String     BOOLEAN_COL_HIST_TYPE                   = "T_BOOLEAN_COL_HIST";

    public final static String     DATETIME_TYPE                           = "T_DATETIME";
    public final static String     DATETIME_COL_TYPE                       = "T_DATETIME_COL";
    public final static String     DATETIME_HIST_TUPLE_TYPE                = "T_DATETIME_HIST_TUPLE";
    public final static String     DATETIME_HIST_TABLE_TYPE                = "T_DATETIME_HIST_TABLE";
    public final static String     DATETIME_HIST_TYPE                      = "T_DATETIME_HIST";
    public final static String     DATETIME_COL_HIST_TUPLE_TYPE            = "T_DATETIME_COL_HIST_TUPLE";
    public final static String     DATETIME_COL_HIST_TABLE_TYPE            = "T_DATETIME_COL_HIST_TABLE";
    public final static String     DATETIME_COL_HIST_TYPE                  = "T_DATETIME_COL_HIST";

    public final static String     BASE_ENTITY_TYPE                        = "T_ENTITY";
    public final static String     BASE_ENTITYREF_TYPE                     = "T_ENTITY_REF";
    public final static String     BASE_CONCEPT_TYPE                       = "T_CONCEPT";
    public final static String     BASE_PROCESS_TYPE                       = "T_PROCESS";
    public final static String     BASE_EVENT_TYPE                         = "T_EVENT";
    public final static String     BASE_SIMPLEEVENT_TYPE                   = "T_SIMPLE_EVENT";
    public final static String     BASE_TIMEEVENT_TYPE                     = "T_TIME_EVENT";
    public final static String     BASE_ENTITYREF_TABLE_TYPE               = "T_ENTITY_REF_TABLE";
    public final static String     BASE_ENTITYREF_HIST_TUPLE_TYPE          = "T_ENTITYREF_HIST_TUPLE";
    public final static String     BASE_ENTITYREF_HIST_TABLE_TYPE          = "T_ENTITYREF_HIST_TABLE";
    public final static String     BASE_ENTITYREF_COL_TYPE                 = "T_ENTITY_REF_TABLE";

    public final static String     BASE_ENTITYREF_HIST_TYPE                = "T_ENTITYREF_HIST";
    public final static String     BASE_ENTITYREF_COL_HIST_TYPE            = "T_ENTITYREF_COL_HIST";

    public final static String     BASE_STATEMACHINE_CONCEPT_TYPE          = "T_STATEMACHINE_CONCEPT";

    protected String name;

    protected boolean isPrimitive = false;

    // To help generate migration script. This is the state of this Type
    public static enum State { NEW, MODIFIED, UNCHANGED, DELETED };
    protected  State state = State.NEW;

    protected String superType = null;
    protected String tableOf=null;

    protected List<String> members = new ArrayList<String>();
    protected Map<String,MemberInfo> memberMap = new LinkedHashMap<String,MemberInfo>();

    /**
     *
     * @param name
     * @param isPrimitive
     */
    public JdbcType(String name, boolean isPrimitive) {
        this.name = name;
        this.isPrimitive=isPrimitive;
    }

    public JdbcType(String name, boolean isPrimitive, String tableOf) {
        this.name = name;
        this.isPrimitive=isPrimitive;
        this.tableOf = tableOf;
    }

    public JdbcType(String name, boolean isPrimitive, String[] memberNames, String[] memberTypes) {
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
     * @return
     */
    public String getName() {
        return name;
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
        this.tableOf = tableOf;
    }

    /**
     *
     * @param memberName
     * @param memberType
     */
    public void addMember(String memberName, String memberType) {
        members.add(memberName + " " + memberType);
        memberMap.put(memberName.toUpperCase(), new MemberInfo(memberName,memberType));
    }

    /**
     *
     * @param memberName
     * @param memberType
     */
    /*
    public void addMember (String memberName, int memberType) {
        memberMap.put(memberName.toUpperCase(), new MemberInfo(memberName, memberType));
    }
    */

    /**
     *
     * @param memberName
     * @param memberType
     */
    public void addMember (String memberName, String memberType, boolean isArray) {
        members.add(memberName + " " + memberType);
        memberMap.put(memberName.toUpperCase(), new MemberInfo(memberName, memberType, isArray));
    }

    /**
     *
     * @param memberName
     * @param memberType
     */
    public void addMember (String memberName, String memberType, boolean isArray, int size) {
        members.add(memberName + " " + memberType);
        memberMap.put(memberName.toUpperCase(), new MemberInfo(memberName, memberType, isArray, size));
    }

    public static class MemberInfo {

        public static final int Deleted =1;
        public static final int Added =2;
        public static final int Altered =3;
        public static final int NotChanged =4;
        public static final int Incompatible =5;

        boolean isArray = false;
        String memberName;
        String memberType;
        int size = 0;
        int state;

        public MemberInfo(String name, String type){
            memberName = name;
            memberType = type;
            state = Added;
        }

        public MemberInfo(String name, String type, boolean isArray){
            memberName = name;
            memberType = type;
            this.isArray = isArray;
            state = Added;
        }

        public MemberInfo(String name, String type, boolean isArray, int size){
            memberName = name;
            memberType = type;
            this.isArray = isArray;
            this.size = size;
            state = Added;
        }
    }
}
