package com.tibco.be.oracle;

import java.io.DataInput;
import java.io.DataOutput;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleResultSet;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.CharacterSet;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import oracle.sql.TIMESTAMP;

import com.tibco.be.model.rdf.RDFTypes;
import com.tibco.be.model.rdf.primitives.RDFPrimitiveTerm;
import com.tibco.be.model.util.ModelNameUtil;
import com.tibco.be.oracle.impl.OracleConceptMap;
import com.tibco.be.oracle.serializers.OracleConceptDeserializer;
import com.tibco.be.oracle.serializers.OracleConceptSerializerImpl;
import com.tibco.be.oracle.serializers.OracleEventDeserializer;
import com.tibco.be.oracle.serializers.OracleEventSerializer;
import com.tibco.be.parser.codegen.RDFUtil;
import com.tibco.cep.designtime.model.Entity;
import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.element.PropertyDefinition;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.designtime.model.event.EventPropertyDefinition;
import com.tibco.cep.kernel.service.logging.Level;
import com.tibco.cep.kernel.service.logging.LogManagerFactory;
import com.tibco.cep.kernel.service.logging.Logger;
import com.tibco.cep.runtime.model.TypeManager;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.model.event.TimeEvent;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 10, 2006
 * Time: 9:39:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class OracleBEMetadata {

    private final static Logger logger = LogManagerFactory.getLogManager().getLogger(OracleBEMetadata.class);
    private HashMap entityPropertiesMap = new HashMap();
    private OracleConnection sqlConnection;
    private HashMap oracleTypes2Description = new HashMap();

    /**
     *
     * @param concept
     */
    public void loadConcept(Concept concept) throws SQLException{
        List propDefs= concept.getAllPropertyDefinitions();
        ArrayList propDescs = new ArrayList();

        for(Iterator it = propDefs.iterator(); it.hasNext();) {
            PropertyDefinition pd = (PropertyDefinition)it.next();
            switch(pd.getType()) {
                case PropertyDefinition.PROPERTY_TYPE_BOOLEAN:
                    propDescs.add(createBooleanColumn(sqlConnection, concept, pd));
                    break;
                case PropertyDefinition.PROPERTY_TYPE_INTEGER:
                    propDescs.add(createIntegerColumn(sqlConnection, concept, pd));
                    break;
                case PropertyDefinition.PROPERTY_TYPE_DATETIME:
                    propDescs.add(createDateTimeColumn(sqlConnection, concept, pd));
                    break;
                case PropertyDefinition.PROPERTY_TYPE_LONG:
                    propDescs.add(createLongColumn(sqlConnection, concept, pd));
                    break;
                case PropertyDefinition.PROPERTY_TYPE_REAL:
                    propDescs.add(createDoubleColumn(sqlConnection, concept, pd));
                    break;
                case PropertyDefinition.PROPERTY_TYPE_STRING:
                    propDescs.add(createStringColumn(sqlConnection, concept, pd));
                    break;
                case PropertyDefinition.PROPERTY_TYPE_CONCEPT:
                case PropertyDefinition.PROPERTY_TYPE_CONCEPTREFERENCE:
                    propDescs.add(createConceptReferenceColumn(sqlConnection, concept, pd));
                    break;
                default:
                    logger.log(Level.WARN, "Unknown Type %d", pd.getType());
            }
        }
        StructDescriptor sd=StructDescriptor.createDescriptor(name2OracleType(concept),sqlConnection);
        OracleEntityDescription oracleConcept= new OracleEntityDescription(concept,sd, propDescs);
        try {
            OracleConceptMap tryMap = new OracleConceptMap(concept, sd, null, sqlConnection);
        } catch (Exception e) {
            logger.log(Level.WARN, e, e.getMessage());
        }
        String className= ModelNameUtil.modelPathToGeneratedClassName(concept.getFullPath());
        entityPropertiesMap.put(className,oracleConcept);
        oracleTypes2Description.put(sd.getName(), oracleConcept);
    }

    public void loadEvent(Event event) throws SQLException{
        ArrayList propDescs = new ArrayList();
        Iterator propDefs = event.getAllUserProperties().iterator();
        while (propDefs.hasNext()) {
            EventPropertyDefinition property = (EventPropertyDefinition) propDefs.next();
            RDFPrimitiveTerm type = property.getType();
            int typeFlag = RDFUtil.getRDFTermTypeFlag(type);
            switch (typeFlag) {
                case RDFTypes.STRING_TYPEID:
                propDescs.add(new PropertyDescriptor(property, new PropertyAtomSimpleStringConverter(false)));
                break;
                case RDFTypes.BOOLEAN_TYPEID:
                propDescs.add(new PropertyDescriptor(property, new PropertyAtomSimpleBooleanConverter(false)));
                break;
                case RDFTypes.INTEGER_TYPEID:
                propDescs.add(new PropertyDescriptor(property, new PropertyAtomSimpleIntConverter(false)));
                break;
                case RDFTypes.DATETIME_TYPEID:
                propDescs.add(new PropertyDescriptor(property, new PropertyAtomSimpleDatetimeConverter(false)));
                break;
                case RDFTypes.LONG_TYPEID:
                propDescs.add(new PropertyDescriptor(property, new PropertyAtomSimpleLongConverter(false)));
                break;
                case RDFTypes.DOUBLE_TYPEID:
                propDescs.add(new PropertyDescriptor(property, new PropertyAtomSimpleDoubleConverter(false)));
                break;
            }
        }
        StructDescriptor sd=StructDescriptor.createDescriptor(name2OracleType(event),sqlConnection);
        OracleEntityDescription oracleEvent= new OracleEntityDescription(event, sd, propDescs);
        String className= ModelNameUtil.modelPathToGeneratedClassName(event.getFullPath());
        entityPropertiesMap.put(className,oracleEvent);
        oracleTypes2Description.put(sd.getName(), oracleEvent);
    }

    /**
     *
     * @param sqlConnection
     */
    public void setOracleConnection(OracleConnection sqlConnection) {
        this.sqlConnection=sqlConnection;
    }

    /**
     *
     * @return
     */
    public OracleConnection getOracleConnection() {
        return this.sqlConnection;
    }

    /**
     *
     * @throws Exception
     */
    public void purgeAll() throws Exception {
        OraclePreparedStatement ps = (OraclePreparedStatement) sqlConnection.prepareStatement("DELETE FROM ALL_ENTITIES ");
        ps.executeUpdate();
        ps.close();
    }
    /**
     *
     * @param id
     * @throws Exception
     */
    public void deleteEntityById(long id) throws Exception {
        OraclePreparedStatement ps = (OraclePreparedStatement) sqlConnection.prepareStatement("DELETE FROM ALL_ENTITIES P WHERE P.ENTITY.ID = ?");
        ps.setOracleObject(1, new oracle.sql.NUMBER(id));
        ps.executeUpdate();
        ps.close();
    }

    /**
     *
     * @param extId
     * @throws Exception
     */
    public void deleteEntityByExtId(String extId) throws Exception {
        OraclePreparedStatement ps = (OraclePreparedStatement) sqlConnection.prepareStatement("DELETE FROM ALL_ENTITIES P WHERE P.ENTITY.EXTID = ?");
        ps.setOracleObject(1, new oracle.sql.CHAR(extId,null));
        ps.executeUpdate();
        ps.close();
    }

    /**
     *
     * @param conceptClz
     * @param input
     * @throws Exception
     */
    @Deprecated
    @SuppressWarnings({"unused"})
    private void updateConcept(String conceptClz, DataInput input) throws Exception{
        OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);

        OracleEntityAdapter adapter = new OracleEntityAdapter(desc,sqlConnection);
        adapter.readExternal(input);
        Datum[] attrs = ((STRUCT) adapter.oracleBytes).getOracleAttributes();
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("UPDATE ALL_ENTITIES T SET T.ENTITY = " + desc.getOracleType().getName() + "(");
        for (int i=0; i < attrs.length; i++) {
            if (i > 0) {
                queryBuf.append(",");
            }
            queryBuf.append("?");
        }
        queryBuf.append(") WHERE T.ENTITY.ID = ?");

        OraclePreparedStatement ps= (OraclePreparedStatement) sqlConnection.prepareStatement(queryBuf.toString());
        for (int i=0; i < attrs.length; i++) {
            ps.setOracleObject(i+1, attrs[i]);
        }
        ps.setOracleObject(attrs.length+1, attrs[0]);
        //ps.setSTRUCT(1,(STRUCT) adapter.oracleBytes);
        //ps.setORAData(1, adapter);
        ps.executeUpdate();
        ps.close();
    }

    @Deprecated
    @SuppressWarnings({"unused","null"})
    private void insertEvent(com.tibco.cep.kernel.model.entity.Event event) throws Exception {
        OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(event.getClass().getName());
        OracleEventSerializer serializer= null;//new OracleEventSerializer(desc, sqlConnection);
        if (event instanceof SimpleEvent) {
            ((SimpleEvent)event).serialize(serializer);
        } else {
            ((TimeEvent)event).serialize(serializer);
        }
        Datum[] attrs = serializer.getOracleAttributes();
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("INSERT INTO ALL_ENTITIES VALUES(" + desc.getOracleType().getName() + "(");
        for (int i=0; i < attrs.length; i++) {
            if (i > 0) {
                queryBuf.append(",");
            }
            queryBuf.append("?");
        }
        queryBuf.append("))");
        OraclePreparedStatement ps= (OraclePreparedStatement) sqlConnection.prepareStatement(queryBuf.toString());
        for (int i=0; i < attrs.length; i++) {
            ps.setOracleObject(i+1, attrs[i]);
        }
        //ps.setSTRUCT(1,(STRUCT) adapter.oracleBytes);
        //ps.setORAData(1, adapter);
        ps.executeUpdate();
        ps.close();
    }

    @Deprecated
    @SuppressWarnings({"unused","null"})
    private void insertConcept(com.tibco.cep.runtime.model.element.Concept cept) throws Exception{
        OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(cept.getClass().getName());
        OracleConceptSerializerImpl serializer= null;//new OracleConceptSerializer(desc, sqlConnection);
        cept.serialize(serializer);
        Datum[] attrs = serializer.getOracleAttributes();
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("INSERT INTO ALL_ENTITIES VALUES(" + desc.getOracleType().getName() + "(");
        for (int i=0; i < attrs.length; i++) {
            if (i > 0) {
                queryBuf.append(",");
            }
            queryBuf.append("?");
        }
        queryBuf.append("))");
        OraclePreparedStatement ps= (OraclePreparedStatement) sqlConnection.prepareStatement(queryBuf.toString());
        for (int i=0; i < attrs.length; i++) {
            ps.setOracleObject(i+1, attrs[i]);
        }
        //ps.setSTRUCT(1,(STRUCT) adapter.oracleBytes);
        //ps.setORAData(1, adapter);
        ps.executeUpdate();
        ps.close();
    }

    /**
     *
     * @param conceptClz
     * @param input
     * @throws Exception
     */
    @Deprecated
    @SuppressWarnings({"unused"})
    private void insertConcept(String conceptClz, DataInput input) throws Exception{
        OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);

        OracleEntityAdapter adapter = new OracleEntityAdapter(desc,sqlConnection);
        adapter.readExternal(input);
        Datum[] attrs = ((STRUCT) adapter.oracleBytes).getOracleAttributes();
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("INSERT INTO ALL_ENTITIES VALUES(" + desc.getOracleType().getName() + "(");
        for (int i=0; i < attrs.length; i++) {
            if (i > 0) {
                queryBuf.append(",");
            }
            queryBuf.append("?");
        }
        queryBuf.append("))");
        OraclePreparedStatement ps= (OraclePreparedStatement) sqlConnection.prepareStatement(queryBuf.toString());
        for (int i=0; i < attrs.length; i++) {
            ps.setOracleObject(i+1, attrs[i]);
        }
        //ps.setSTRUCT(1,(STRUCT) adapter.oracleBytes);
        //ps.setORAData(1, adapter);
        ps.executeUpdate();
        ps.close();
    }

    @Deprecated
    @SuppressWarnings({"unused","null"})
    private com.tibco.cep.kernel.model.entity.Event getEventById(long id, TypeManager factory) throws Exception{
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);
        com.tibco.cep.kernel.model.entity.Event event=null;
        //OracleEntityAdapter adapter = new OracleEntityAdapter(this.oracleTypes2Description,sqlConnection);
        OraclePreparedStatement ps = (OraclePreparedStatement) sqlConnection.prepareStatement("SELECT * FROM ALL_ENTITIES P WHERE P.ENTITY.ID = ?");
        ps.setLong(1, id);
        OracleResultSet rs= (OracleResultSet) ps.executeQuery();
        STRUCT ret=null;
        while (rs.next()) {
            ret=rs.getSTRUCT(1);
            //rs.getORAData(1, adapter);
        }
        if (ret != null) {
            String type= ret.getDescriptor().getName();
            OracleEntityDescription desc= (OracleEntityDescription) oracleTypes2Description.get(type);
            OracleEventDeserializer deser= null;//new OracleEventDeserializer(desc, sqlConnection, ret.getOracleAttributes());
            Class entityClz= factory.getTypeDescriptor(desc.getEntity().getFullPath()).getImplClass();
            Constructor cons = entityClz.getConstructor(new Class[] {long.class, String.class});
            event= (com.tibco.cep.kernel.model.entity.Event) cons.newInstance(new Object[] {new Long(id), deser.getExtId()});
            if (event instanceof SimpleEvent) {
                ((SimpleEvent)event).deserialize(deser);
            } else {
                ((TimeEvent)event).deserialize(deser);
            }
        }
        rs.close();
        ps.close();
        return event;
    }

    /**
     *
     * @param id
     * @param factory
     * @return
     * @throws Exception
     */
    @Deprecated
    @SuppressWarnings({"unused","null"})
    private com.tibco.cep.runtime.model.element.Concept getConceptById(long id, TypeManager factory) throws Exception {
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);
        com.tibco.cep.runtime.model.element.Concept cept=null;
        OraclePreparedStatement ps = (OraclePreparedStatement) sqlConnection.prepareStatement("SELECT * FROM all_entities P WHERE P.ENTITY.ID = ?");
        ps.setLong(1, id);
        OracleResultSet rs= (OracleResultSet) ps.executeQuery();
        STRUCT ret=null;
        while (rs.next()) {
            ret=rs.getSTRUCT(1);
            //rs.getORAData(1, adapter);
        }
        if (ret != null) {
            String type= ret.getDescriptor().getName();
            OracleEntityDescription desc= (OracleEntityDescription) oracleTypes2Description.get(type);
            OracleConceptDeserializer deser= null;//new OracleConceptDeserializer(desc, sqlConnection, ret.getOracleAttributes());
            Class entityClz= factory.getTypeDescriptor(desc.getEntity().getFullPath()).getImplClass();
            Constructor cons = entityClz.getConstructor(new Class[] {long.class, String.class});
            cept= (com.tibco.cep.runtime.model.element.Concept) cons.newInstance(new Object[] {new Long(id), deser.getExtId()});
            cept.deserialize(deser);
        }
        rs.close();
        ps.close();
        return cept;
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public byte[] _getConceptById(long id) throws Exception{
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);

        OracleEntityAdapter adapter = new OracleEntityAdapter(this.oracleTypes2Description,sqlConnection);
        OraclePreparedStatement ps = (OraclePreparedStatement) sqlConnection.prepareStatement("SELECT * FROM ALL_ENTITIES P WHERE P.ENTITY.ID = ?");
        ps.setLong(1, id);
        OracleResultSet rs= (OracleResultSet) ps.executeQuery();
        while (rs.next()) {
            rs.getORAData(1, adapter);
        }
        rs.close();
        ps.close();
        return adapter.cachedBytes.toByteArray();
    }


    /**
     *
     * @param extId
     * @return
     * @throws Exception
     */
    public byte[] getConceptByExtId(String extId) throws Exception{
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);

        OracleEntityAdapter adapter = new OracleEntityAdapter(this.oracleTypes2Description,sqlConnection);
        OraclePreparedStatement ps = (OraclePreparedStatement) sqlConnection.prepareStatement("SELECT * FROM ALL_ENTITIES P WHERE P.ENTITY.EXTID = ?");
        ps.setString(1, extId);
        OracleResultSet rs= (OracleResultSet) ps.executeQuery();
        while (rs.next()) {
            rs.getORAData(1, adapter);
        }
        rs.close();
        ps.close();
        return adapter.cachedBytes.toByteArray();
    }

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public byte[] getEventById(long id) throws Exception{
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);

        OracleEntityAdapter adapter = new OracleEntityAdapter(this.oracleTypes2Description,sqlConnection);
        OraclePreparedStatement ps = (OraclePreparedStatement) sqlConnection.prepareStatement("SELECT * FROM ALL_ENTITIES P WHERE P.ENTITY.ID = ?");
        ps.setLong(1, id);
        OracleResultSet rs= (OracleResultSet) ps.executeQuery();
        while (rs.next()) {
            rs.getORAData(1, adapter);
        }
        rs.close();
        ps.close();
        return adapter.cachedBytes.toByteArray();
    }

    /**
     *
     * @param extId
     * @return
     * @throws Exception
     */
    public byte[] getEventByExtId(String extId) throws Exception{
        //OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(conceptClz);

        OracleEntityAdapter adapter = new OracleEntityAdapter(this.oracleTypes2Description,sqlConnection);
        OraclePreparedStatement ps = (OraclePreparedStatement) sqlConnection.prepareStatement("SELECT * FROM ALL_ENTITIES P WHERE P.ENTITY.EXTID = ?");
        ps.setString(1, extId);
        OracleResultSet rs= (OracleResultSet) ps.executeQuery();
        while (rs.next()) {
            rs.getORAData(1, adapter);
        }
        rs.close();
        ps.close();
        return adapter.cachedBytes.toByteArray();
    }

    /**
     *
     * @param eventClz
     * @param input
     * @throws Exception
     */
    public void insertEvent(String eventClz, DataInput input) throws Exception{
        OracleEntityDescription desc= (OracleEntityDescription) entityPropertiesMap.get(eventClz);

        OracleEntityAdapter adapter = new OracleEntityAdapter(desc,sqlConnection);
        adapter.readExternal(input);
        Datum[] attrs = ((STRUCT) adapter.oracleBytes).getOracleAttributes();
        StringBuffer queryBuf = new StringBuffer();
        queryBuf.append("INSERT INTO ALL_ENTITIES VALUES(" + desc.getOracleType().getName() + "(");
        for (int i=0; i < attrs.length; i++) {
            if (i > 0) {
                queryBuf.append(",");
            }
            queryBuf.append("?");
        }
        queryBuf.append("))");
        OraclePreparedStatement ps= (OraclePreparedStatement) sqlConnection.prepareStatement(queryBuf.toString());
        for (int i=0; i < attrs.length; i++) {
            ps.setOracleObject(i+1, attrs[i]);
        }
        // Payload
        if (input.readBoolean()) {
            int length= input.readInt();
            byte [] buf = new byte[length];
            input.readFully(buf);
            ps.setOracleObject(attrs.length,new oracle.sql.BLOB(sqlConnection,buf));
        } else {
            ps.setOracleObject(attrs.length, null);
        }

        //ps.setSTRUCT(1,(STRUCT) adapter.oracleBytes);
        //ps.setORAData(1, adapter);
        ps.executeUpdate();
        ps.close();
    }

    PropertyDescriptor createStringColumn(Connection sqlConnection, Concept concept, PropertyDefinition pd) throws SQLException {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            return new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.STRING_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.STRING_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.STRING_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomStringConverter());
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            return new PropertyDescriptor(pd, new PropertyAtomSimpleStringConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            return new PropertyDescriptor(pd,
                    ArrayDescriptor.createDescriptor(OracleType.STRING_COL_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomSimpleStringConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            //return null;
            return new PropertyDescriptor(pd,
                    ArrayDescriptor.createDescriptor(OracleType.STRING_COL_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.STRING_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.STRING_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomStringConverter());
            //conceptType.addMember(pd.getName(), OracleType.STRING_COL_HIST_ORACLE_TYPE);
        }
        return null;
    }

    PropertyDescriptor createIntegerColumn(Connection sqlConnection, Concept concept, PropertyDefinition pd) throws SQLException {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            return new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.INTEGER_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.INTEGER_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.INTEGER_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomIntConverter());
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            return new PropertyDescriptor(pd, new PropertyAtomSimpleIntConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            return new PropertyDescriptor(pd,
                    ArrayDescriptor.createDescriptor(OracleType.INTEGER_COL_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomSimpleIntConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            return null;
            /**
            return new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.STRING_COL_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.STRING_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.STRING_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomStringConverter());
            conceptType.addMember(pd.getName(), OracleType.STRING_COL_HIST_ORACLE_TYPE);
             **/
        }
        return null;
    }

    PropertyDescriptor createBooleanColumn(Connection sqlConnection, Concept concept, PropertyDefinition pd) throws SQLException {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            return new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.BOOLEAN_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.BOOLEAN_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.BOOLEAN_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomBooleanConverter());
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            return new PropertyDescriptor(pd, new PropertyAtomSimpleBooleanConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            return new PropertyDescriptor(pd,
                    ArrayDescriptor.createDescriptor(OracleType.BOOLEAN_COL_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomSimpleBooleanConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            return null;
            /**
            return new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.STRING_COL_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.STRING_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.STRING_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomStringConverter());
            conceptType.addMember(pd.getName(), OracleType.STRING_COL_HIST_ORACLE_TYPE);
             **/
        }
        return null;
    }

    PropertyDescriptor createLongColumn(Connection sqlConnection, Concept concept, PropertyDefinition pd) throws SQLException {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            return new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.NUMBER_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.NUMBER_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.NUMBER_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomLongConverter());
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            return new PropertyDescriptor(pd, new PropertyAtomSimpleLongConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            return new PropertyDescriptor(pd,
                    ArrayDescriptor.createDescriptor(OracleType.NUMBER_COL_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomSimpleLongConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            return null;
            /**
            return new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.STRING_COL_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleTypeNUMBE.STRING_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.STRING_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomStringConverter());
            conceptType.addMember(pd.getName(), OracleType.STRING_COL_HIST_ORACLE_TYPE);
             **/
        }
        return null;
    }

    PropertyDescriptor createDateTimeColumn(Connection sqlConnection, Concept concept, PropertyDefinition pd) throws SQLException {
        PropertyDescriptor desc=null;
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            desc=new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.TYPE_DATETIME.getName(),sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.DATETIME_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.DATETIME_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.DATETIME_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomDatetimeConverter());
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            desc=new PropertyDescriptor(pd, StructDescriptor.createDescriptor(OracleType.TYPE_DATETIME.getName(),sqlConnection),
                    new PropertyAtomSimpleDatetimeConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            desc=new PropertyDescriptor(pd,
                    ArrayDescriptor.createDescriptor(OracleType.DATETIME_COL_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomSimpleDatetimeConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            return null;
            /**
            return new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.STRING_COL_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleTypeNUMBE.STRING_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.STRING_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomStringConverter());
            conceptType.addMember(pd.getName(), OracleType.STRING_COL_HIST_ORACLE_TYPE);
             **/
        }
        desc.setPrimitiveDescriptor(StructDescriptor.createDescriptor(OracleType.TYPE_DATETIME.getName(), sqlConnection));
        return desc;
    }

    PropertyDescriptor createDoubleColumn(Connection sqlConnection, Concept concept, PropertyDefinition pd) throws SQLException {
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            return new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.NUMBER_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.NUMBER_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.NUMBER_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomDoubleConverter());
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            return new PropertyDescriptor(pd, new PropertyAtomSimpleDoubleConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            return new PropertyDescriptor(pd,
                    ArrayDescriptor.createDescriptor(OracleType.NUMBER_COL_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomSimpleDoubleConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            return null;
            /**
            return new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.STRING_COL_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.STRING_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.STRING_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomStringConverter());
            conceptType.addMember(pd.getName(), OracleType.STRING_COL_HIST_ORACLE_TYPE);
             **/
        }
        return null;
    }

    PropertyDescriptor createConceptReferenceColumn(Connection sqlConnection, Concept concept, PropertyDefinition pd) throws SQLException {
        PropertyDescriptor desc=null;
        if (!pd.isArray() && (pd.getHistorySize() > 0))  {
            desc=new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.BASE_ENTITYREF_ORACLE_TYPE,sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.BASE_ENTITYREF_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.BASE_ENTITYREF_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.BASE_ENTITYREF_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomConceptReferenceConverter());
        } else if (!pd.isArray() && (pd.getHistorySize() == 0))  {
            desc=new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.BASE_ENTITYREF_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomSimpleConceptReferenceConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() == 0))  {
            desc=new PropertyDescriptor(pd,
                    ArrayDescriptor.createDescriptor(OracleType.BASE_ENTITYREF_COL_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomSimpleConceptReferenceConverter(true));
        } else if (pd.isArray() && (pd.getHistorySize() > 0))  {
            return null;
            /**
            return new PropertyDescriptor(pd,
                    StructDescriptor.createDescriptor(OracleType.STRING_COL_HIST_ORACLE_TYPE, sqlConnection),
                    ArrayDescriptor.createDescriptor(OracleType.STRING_HIST_TABLE_ORACLE_TYPE, sqlConnection),
                    StructDescriptor.createDescriptor(OracleType.STRING_HIST_TUPLE_ORACLE_TYPE, sqlConnection),
                    new PropertyAtomStringConverter());
            conceptType.addMember(pd.getName(), OracleType.STRING_COL_HIST_ORACLE_TYPE);
             **/
        }
        desc.setPrimitiveDescriptor(StructDescriptor.createDescriptor(OracleType.BASE_ENTITYREF_ORACLE_TYPE, sqlConnection));
        return desc;
    }


    static String name2OracleType(Entity cept) {
        return "T_" + cept.getName().replaceAll("\\.", "\\$").toUpperCase();
    }

    static String name2OracleCollectionType(Concept cept) {
        return name2OracleType(cept) + "_TABLE";
    }

    static String name2OracleHistoryType(Concept cept) {
        return name2OracleType(cept) + "_HIST";
    }

    static String name2OracleCollectionHistoryType(Concept cept) {
        return name2OracleType(cept) + "_TABLE_HIST";
    }


    abstract class PropertyConverter {
        abstract protected void oracle2Cache(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception;
        abstract protected Datum cache2Oracle(DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception;

        /**
         *
         * @param oracleType
         * @param cacheOut
         * @param propertyDescriptor
         * @param sqlConnection
         * @throws Exception
         */
        protected void oracle2CacheArray(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            if (oracleType instanceof ARRAY) {
                Datum [] attributes= ((ARRAY) oracleType).getOracleArray();
                if (attributes.length > 0) {
                    cacheOut.writeBoolean(true);
                    cacheOut.writeInt(attributes.length);
                    for (int i=0; i < attributes.length; i++) {
                        oracle2Cache(attributes[i], cacheOut, propertyDescriptor, sqlConnection);
                    }
                } else {
                    cacheOut.writeBoolean(false);
                }
            } else {
                cacheOut.writeBoolean(false);
            }
        }

        /**
         *
         * @param cacheIn
         * @param propertyDescriptor
         * @param sqlConnection
         * @return
         * @throws Exception
         */
        protected Datum[] cache2OracleArray(DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            if (cacheIn.readBoolean()) {
                int length= cacheIn.readInt();
                Datum[] ret = new Datum[length];
                for (int i=0; i < length; i++) {
                    ret[i] = cache2Oracle(cacheIn, propertyDescriptor,sqlConnection);
                }
                return ret;
            } else {
                return null;
            }
        }
    }

    abstract class PropertyHistoryConverter extends PropertyConverter{

        protected void oracle2Cache(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            STRUCT propertyValue = (STRUCT) oracleType;
            if (propertyValue != null) {
                Datum[] attributes = propertyValue.getOracleAttributes();
                int howMany  = ((oracle.sql.NUMBER)attributes[0]).intValue();
                cacheOut.writeBoolean(true);
                cacheOut.writeInt(howMany);
                oracle2CacheHistory(attributes[1], cacheOut, propertyDescriptor, sqlConnection, howMany);
            } else {
                cacheOut.writeBoolean(false);
            }
        }

        protected abstract void oracle2CacheHistory(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception;
        protected abstract Datum cache2DatumHistory (DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception;

        protected Datum cache2Oracle(DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            if (cacheIn.readBoolean()) {
                Object [] attributes = new Object[2];
                int howMany = cacheIn.readInt();

                attributes[0]= new Integer(howMany);
                attributes[1]=cache2DatumHistory(cacheIn, propertyDescriptor, sqlConnection, howMany);
                return new STRUCT((StructDescriptor) propertyDescriptor.getOracleType(), sqlConnection, attributes);
            }
            return null;
        }

        /**
         *
         * @param oracleType
         * @param cacheOut
         * @param propertyDescriptor
         * @param sqlConnection
         * @throws Exception
         */
        protected void oracle2CacheArray(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            if (oracleType instanceof ARRAY) {
                Datum [] attributes= ((ARRAY) oracleType).getOracleArray();
                if (attributes.length > 0) {
                    cacheOut.writeBoolean(true);
                    for (int i=0; i < attributes.length; i++) {
                        oracle2Cache(attributes[i], cacheOut, propertyDescriptor, sqlConnection);
                    }
                } else {
                    cacheOut.writeBoolean(false);
                }
            } else {
                cacheOut.writeBoolean(false);
            }
        }

        /**
         *
         * @param cacheIn
         * @param propertyDescriptor
         * @param sqlConnection
         * @return
         * @throws Exception
         */
        protected Datum[] cache2OracleArray(DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            if (cacheIn.readBoolean()) {
                int length= cacheIn.readInt();
                Datum[] ret = new Datum[length];
                for (int i=0; i < length; i++) {
                    ret[i] = cache2Oracle(cacheIn, propertyDescriptor,sqlConnection);
                }
                return ret;
            } else {
                return null;
            }
        }

    }

    class PropertyAtomSimpleStringConverter extends PropertyConverter {
        boolean setIsSet;
        PropertyAtomSimpleStringConverter(boolean setIsSet){this.setIsSet=setIsSet;};
        /**
         *
         * @param oracleType
         * @param cacheOut
         * @param propertyDescriptor
         * @param sqlConnection
         * @throws Exception
         */
        protected void oracle2Cache(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            oracle.sql.CHAR propertyValue = (oracle.sql.CHAR) oracleType;
            if (propertyValue != null) {
                if (setIsSet) {
                    cacheOut.writeBoolean(true);
                }
                cacheOut.writeBoolean(true);
                cacheOut.writeUTF(propertyValue.stringValue());
            } else {
                cacheOut.writeBoolean(false);
            }
        }

        /**
         *
         * @param cacheIn
         * @param propertyDescriptor
         * @param sqlConnection
         * @return
         * @throws Exception
         */
        protected Datum cache2Oracle(DataInput cacheIn, PropertyDescriptor propertyDescriptor,Connection sqlConnection) throws Exception {
            if (setIsSet) {
                if (cacheIn.readBoolean()) {
                    if (cacheIn.readBoolean()) {
                        return new oracle.sql.CHAR(cacheIn.readUTF(), CharacterSet.make(CharacterSet.UTF8_CHARSET));
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                if (cacheIn.readBoolean()) {
                    return new oracle.sql.CHAR(cacheIn.readUTF(), CharacterSet.make(CharacterSet.UTF8_CHARSET));
                } else {
                    return null;
                }
            }
        }
    }

    class PropertyAtomSimpleIntConverter extends PropertyConverter {
        boolean setIsSet;
        PropertyAtomSimpleIntConverter(boolean setIsSet){this.setIsSet=setIsSet;}
        /**
         *
         * @param oracleType
         * @param cacheOut
         * @param propertyDescriptor
         * @param sqlConnection
         * @throws Exception
         */
        protected void oracle2Cache(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            oracle.sql.NUMBER propertyValue = (oracle.sql.NUMBER) oracleType;
            if (propertyValue != null) {
                if (setIsSet) {
                    cacheOut.writeBoolean(true);
                }
                cacheOut.writeInt(propertyValue.intValue());
            } else {
                cacheOut.writeBoolean(false);
            }
        }

        /**
         *
         * @param cacheIn
         * @param oracleType
         * @param propertyDescriptor
         * @param sqlConnection
         * @return
         * @throws Exception
         */
        protected Datum cache2Oracle(DataInput cacheIn, PropertyDescriptor propertyDescriptor,Connection sqlConnection) throws Exception {
            if (setIsSet) {
                if (cacheIn.readBoolean()) {
                    return new oracle.sql.NUMBER(cacheIn.readInt());
                } else {
                    return null;
                }
            } else {
                return new oracle.sql.NUMBER(cacheIn.readInt());
            }
        }
    }

    class PropertyAtomSimpleLongConverter extends PropertyConverter {
        boolean setIsSet;
        PropertyAtomSimpleLongConverter(boolean setIsSet){this.setIsSet=setIsSet;}
        /**
         *
         * @param oracleType
         * @param cacheOut
         * @param propertyDescriptor
         * @param sqlConnection
         * @throws Exception
         */
        protected void oracle2Cache(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            oracle.sql.NUMBER propertyValue = (oracle.sql.NUMBER) oracleType;
            if (propertyValue != null) {
                if (setIsSet) {
                    cacheOut.writeBoolean(true);
                }
                cacheOut.writeLong(propertyValue.longValue());
            } else {
                cacheOut.writeBoolean(false);
            }
        }

        /**
         *
         * @param cacheIn
         * @param oracleType
         * @param propertyDescriptor
         * @param sqlConnection
         * @return
         * @throws Exception
         */
        protected Datum cache2Oracle(DataInput cacheIn, PropertyDescriptor propertyDescriptor,Connection sqlConnection) throws Exception {
            if (setIsSet) {
                if (cacheIn.readBoolean()) {
                    return new oracle.sql.NUMBER(cacheIn.readLong());
                } else {
                    return null;
                }
            } else {
                return new oracle.sql.NUMBER(cacheIn.readLong());
            }
        }
    }

    class PropertyAtomSimpleDoubleConverter extends PropertyConverter {
        boolean setIsSet;
        PropertyAtomSimpleDoubleConverter(boolean setIsSet){this.setIsSet=setIsSet;}
        /**
         *
         * @param oracleType
         * @param cacheOut
         * @param propertyDescriptor
         * @param sqlConnection
         * @throws Exception
         */
        protected void oracle2Cache(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            oracle.sql.NUMBER propertyValue = (oracle.sql.NUMBER) oracleType;
            //propertyValue = (Long) oracleType;
            if (propertyValue != null) {
                if (setIsSet) {
                    cacheOut.writeBoolean(true);
                }
                cacheOut.writeDouble(propertyValue.doubleValue());
            } else {
                cacheOut.writeBoolean(false);
            }
        }

        /**
         *
         * @param cacheIn
         * @param oracleType
         * @param propertyDescriptor
         * @param sqlConnection
         * @return
         * @throws Exception
         */
        protected Datum cache2Oracle(DataInput cacheIn, PropertyDescriptor propertyDescriptor,Connection sqlConnection) throws Exception {
            if (setIsSet) {
                if (cacheIn.readBoolean()) {
                    return new oracle.sql.NUMBER(cacheIn.readLong());
                } else {
                    return null;
                }
            } else {
                return new oracle.sql.NUMBER(cacheIn.readLong());
            }
        }
    }

    class PropertyAtomSimpleDatetimeConverter extends PropertyConverter {
        boolean setIsSet;
        PropertyAtomSimpleDatetimeConverter(boolean setIsSet){this.setIsSet=setIsSet;}
        /**
         *
         * @param oracleType
         * @param cacheOut
         * @param propertyDescriptor
         * @param sqlConnection
         * @throws Exception
         */
        protected void oracle2Cache(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            STRUCT dt = (STRUCT) oracleType;
            if (dt != null) {
                Datum[] attributes = dt.getOracleAttributes();
                if (setIsSet) {
                    cacheOut.writeBoolean(true);
                }

                if (attributes[1] != null) {
                    cacheOut.writeBoolean(true);
                    cacheOut.writeLong(((oracle.sql.DATE)attributes[0]).dateValue().getTime());
                    cacheOut.writeUTF(((oracle.sql.CHAR) attributes[1]).stringValue());
                } else {
                    cacheOut.writeBoolean(false);
                }
            } else {
                cacheOut.writeBoolean(false);
            }
        }

        /**
         *
         * @param cacheIn
         * @param oracleType
         * @param propertyDescriptor
         * @param sqlConnection
         * @return
         * @throws Exception
         */
        protected Datum cache2Oracle(DataInput cacheIn, PropertyDescriptor propertyDescriptor,Connection sqlConnection) throws Exception {
            if (setIsSet) {
                if (cacheIn.readBoolean()) {
                    if (cacheIn.readBoolean()) {
                        Datum[] attributes = new Datum[2];
                        long t = cacheIn.readLong();
                        attributes[0]= new oracle.sql.TIMESTAMP(new java.sql.Date(t));
                        attributes[1]= new oracle.sql.CHAR(cacheIn.readUTF(),null);
                        return new STRUCT((StructDescriptor) propertyDescriptor.getOracleType(), sqlConnection, attributes);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                if (cacheIn.readBoolean()) {
                    Datum[] attributes = new Datum[2];
                    long t = cacheIn.readLong();
                    attributes[0]= new oracle.sql.TIMESTAMP(new java.sql.Date(t));
                    if (cacheIn.readBoolean()) {
                        attributes[1]= new oracle.sql.CHAR(cacheIn.readUTF(),null);
                    } else {
                        attributes[1]=null;
                    }
                    return new STRUCT((StructDescriptor) propertyDescriptor.getOracleType(), sqlConnection, attributes);
                } else {
                    return null;
                }
            }
        }
    }

    class PropertyAtomSimpleBooleanConverter extends PropertyConverter {
        boolean setIsSet;
        PropertyAtomSimpleBooleanConverter(boolean setIsSet){this.setIsSet=setIsSet;}
        /**
         *
         * @param oracleType
         * @param cacheOut
         * @param propertyDescriptor
         * @param sqlConnection
         * @throws Exception
         */
        protected void oracle2Cache(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            oracle.sql.NUMBER propertyValue = (oracle.sql.NUMBER) oracleType;
            if (propertyValue != null) {
                if (setIsSet) {
                    cacheOut.writeBoolean(true);
                }
                cacheOut.writeBoolean(propertyValue.intValue() == 1 ? true: false);
            } else {
                cacheOut.writeBoolean(false);
            }
        }

        /**
         *
         * @param cacheIn
         * @param oracleType
         * @param propertyDescriptor
         * @param sqlConnection
         * @return
         * @throws Exception
         */
        protected Datum cache2Oracle(DataInput cacheIn, PropertyDescriptor propertyDescriptor,Connection sqlConnection) throws Exception {
            if (setIsSet) {
                if (cacheIn.readBoolean()) {
                    boolean val=cacheIn.readBoolean();
                    return new oracle.sql.NUMBER(val? 1:0);
                } else {
                    return null;
                }
            } else {
                boolean val=cacheIn.readBoolean();
                return new oracle.sql.NUMBER(val? 1:0);
            }
        }
    }

    class PropertyAtomSimpleConceptReferenceConverter extends PropertyConverter {
        boolean setIsSet;
        PropertyAtomSimpleConceptReferenceConverter(boolean setIsSet){this.setIsSet=setIsSet;}
        /**
         *
         * @param oracleType
         * @param cacheOut
         * @param propertyDescriptor
         * @param sqlConnection
         * @throws Exception
         */
        protected void oracle2Cache(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection) throws Exception {
            STRUCT propertyValue = (STRUCT) oracleType;
            Object []attributes= propertyValue.getOracleAttributes();
            if (setIsSet) {
                cacheOut.writeBoolean(true);
            }
            if (attributes[0] != null) {
                cacheOut.writeBoolean(true);
                cacheOut.writeLong(((oracle.sql.NUMBER) attributes[0]).longValue());
            } else {
                cacheOut.writeBoolean(false);
            }
        }

        /**
         *
         * @param cacheIn
         * @param propertyDescriptor
         * @param sqlConnection
         * @return
         * @throws Exception
         */
        protected Datum cache2Oracle(DataInput cacheIn, PropertyDescriptor propertyDescriptor,Connection sqlConnection) throws Exception {
            Datum[] attributes = new Datum[1];
            if (setIsSet) {
                if (cacheIn.readBoolean()) {
                    if (cacheIn.readBoolean()) {
                        attributes[0]=new oracle.sql.NUMBER(cacheIn.readLong());
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                // should never come here
                attributes[0]=new oracle.sql.NUMBER(cacheIn.readLong());
            }
            return new STRUCT((StructDescriptor) propertyDescriptor.getOracleType(), sqlConnection,attributes);
        }
    }

    class PropertyAtomStringConverter extends PropertyHistoryConverter {
        PropertyAtomStringConverter(){}

        protected void oracle2CacheHistory(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {
            ARRAY propertyValue = (ARRAY) oracleType;
            Datum[] history= propertyValue.getOracleArray();
            //long [] time = new long[history.length];
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                cacheOut.writeLong(((oracle.sql.DATE) historyAttributes[0]).dateValue().getTime());
            }
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                if (historyAttributes[1] != null) {
                    cacheOut.writeBoolean(true);
                    cacheOut.writeUTF(((oracle.sql.CHAR) historyAttributes[1]).stringValue());
                } else {
                    cacheOut.writeBoolean(false);
                }
            }
        }

        protected Datum cache2DatumHistory(DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {

            TIMESTAMP []  time = new TIMESTAMP[length];
            oracle.sql.CHAR[] vals = new oracle.sql.CHAR[length];
            for(int i=0; i<length; i++) {
                time[i] = new TIMESTAMP(new java.sql.Date(cacheIn.readLong()));
            }
            for(int i=0; i<length; i++) {
                if (cacheIn.readBoolean()) {
                    vals[i] = new oracle.sql.CHAR(cacheIn.readUTF(), null);
                } else {
                    vals[i]=null;
                }
            }
            Datum[] attributes = new Datum[length];
            for (int i=0; i < length; i++) {
                Datum[] v = new Datum[2];
                v[0]= time[i];
                v[1]= vals[i];
                attributes[i] = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, v);
            }
            ARRAY table = new ARRAY(propertyDescriptor.getArrayDescriptor(),sqlConnection,attributes);
            //Datum[] attributes = new Datum[3];
            //attributes[0]=
            //STRUCT baseType = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, attributes);
            return table;
        }
    }

    class PropertyAtomBooleanConverter extends PropertyHistoryConverter {
        PropertyAtomBooleanConverter(){}

        protected void oracle2CacheHistory(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {
            ARRAY propertyValue = (ARRAY) oracleType;
            Datum[] history= propertyValue.getOracleArray();
            //long [] time = new long[history.length];
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                cacheOut.writeLong(((oracle.sql.DATE) historyAttributes[0]).dateValue().getTime());
            }
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                if (historyAttributes[1] != null) {
                    cacheOut.writeBoolean((((oracle.sql.NUMBER) historyAttributes[1]).intValue() == 1)? true: false);
                } else {
                    cacheOut.writeBoolean(false);
                }
            }
        }

        protected Datum cache2DatumHistory(DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {

            TIMESTAMP []  time = new TIMESTAMP[length];
            oracle.sql.NUMBER[] vals = new oracle.sql.NUMBER[length];
            for(int i=0; i<length; i++) {
                time[i] = new TIMESTAMP(new java.sql.Date(cacheIn.readLong()));
            }
            for(int i=0; i<length; i++) {
                vals[i] = new oracle.sql.NUMBER(cacheIn.readBoolean() ? 1:0);
            }
            Datum[] attributes = new Datum[length];
            for (int i=0; i < length; i++) {
                Datum[] v = new Datum[2];
                v[0]= time[i];
                v[1]= vals[i];
                attributes[i] = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, v);
            }
            ARRAY table = new ARRAY(propertyDescriptor.getArrayDescriptor(),sqlConnection,attributes);
            //Datum[] attributes = new Datum[3];
            //attributes[0]=
            //STRUCT baseType = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, attributes);
            return table;
        }
    }

    class PropertyAtomIntConverter extends PropertyHistoryConverter {
        PropertyAtomIntConverter(){}

        protected void oracle2CacheHistory(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {
            ARRAY propertyValue = (ARRAY) oracleType;
            Datum[] history= propertyValue.getOracleArray();
            //long [] time = new long[history.length];
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                cacheOut.writeLong(((oracle.sql.DATE) historyAttributes[0]).dateValue().getTime());
            }
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                if (historyAttributes[1] != null) {
                    cacheOut.writeBoolean(true);
                    cacheOut.writeInt(((oracle.sql.NUMBER) historyAttributes[1]).intValue());
                } else {
                    cacheOut.writeBoolean(false);
                }
            }
        }

        protected Datum cache2DatumHistory(DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {

            TIMESTAMP []  time = new TIMESTAMP[length];
            oracle.sql.NUMBER[] vals = new oracle.sql.NUMBER[length];
            for(int i=0; i<length; i++) {
                time[i] = new TIMESTAMP(new java.sql.Date(cacheIn.readLong()));
            }
            for(int i=0; i<length; i++) {
                vals[i] = new oracle.sql.NUMBER(cacheIn.readInt());
            }
            Datum[] attributes = new Datum[length];
            for (int i=0; i < length; i++) {
                Datum[] v = new Datum[2];
                v[0]= time[i];
                v[1]= vals[i];
                attributes[i] = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, v);
            }
            ARRAY table = new ARRAY(propertyDescriptor.getArrayDescriptor(),sqlConnection,attributes);
            //Datum[] attributes = new Datum[3];
            //attributes[0]=
            //STRUCT baseType = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, attributes);
            return table;
        }
    }

    class PropertyAtomDoubleConverter extends PropertyHistoryConverter {
        PropertyAtomDoubleConverter(){}

        protected void oracle2CacheHistory(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {
            ARRAY propertyValue = (ARRAY) oracleType;
            Datum[] history= propertyValue.getOracleArray();
            //long [] time = new long[history.length];
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                cacheOut.writeLong(((oracle.sql.DATE) historyAttributes[0]).dateValue().getTime());
            }
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                if (historyAttributes[1] != null) {
                    cacheOut.writeBoolean(true);
                    cacheOut.writeDouble(((oracle.sql.NUMBER) historyAttributes[1]).doubleValue());
                } else {
                    cacheOut.writeBoolean(false);
                }
            }
        }

        protected Datum cache2DatumHistory(DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {

            TIMESTAMP []  time = new TIMESTAMP[length];
            oracle.sql.NUMBER[] vals = new oracle.sql.NUMBER[length];
            for(int i=0; i<length; i++) {
                time[i] = new TIMESTAMP(new java.sql.Date(cacheIn.readLong()));
            }
            for(int i=0; i<length; i++) {
                vals[i] = new oracle.sql.NUMBER(cacheIn.readDouble());
            }
            Datum[] attributes = new Datum[length];
            for (int i=0; i < length; i++) {
                Datum[] v = new Datum[2];
                v[0]= time[i];
                v[1]= vals[i];
                attributes[i] = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, v);
            }
            ARRAY table = new ARRAY(propertyDescriptor.getArrayDescriptor(),sqlConnection,attributes);
            //Datum[] attributes = new Datum[3];
            //attributes[0]=
            //STRUCT baseType = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, attributes);
            return table;
        }
    }

    class PropertyAtomLongConverter extends PropertyHistoryConverter {
        PropertyAtomLongConverter(){}

        protected void oracle2CacheHistory(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {
            ARRAY propertyValue = (ARRAY) oracleType;
            Datum[] history= propertyValue.getOracleArray();
            //long [] time = new long[history.length];
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                cacheOut.writeLong(((oracle.sql.DATE) historyAttributes[0]).dateValue().getTime());
            }
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                if (historyAttributes[1] != null) {
                    //cacheOut.writeBoolean(true);
                    cacheOut.writeLong(((oracle.sql.NUMBER) historyAttributes[1]).longValue());
                } else {
                    cacheOut.writeBoolean(false);
                }
            }
        }

        protected Datum cache2DatumHistory(DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {

            TIMESTAMP []  time = new TIMESTAMP[length];
            oracle.sql.NUMBER[] vals = new oracle.sql.NUMBER[length];
            for(int i=0; i<length; i++) {
                time[i] = new TIMESTAMP(new java.sql.Date(cacheIn.readLong()));
            }
            for(int i=0; i<length; i++) {
                vals[i] = new oracle.sql.NUMBER(cacheIn.readLong());
            }
            Datum[] attributes = new Datum[length];
            for (int i=0; i < length; i++) {
                Datum[] v = new Datum[2];
                v[0]= time[i];
                v[1]= vals[i];
                attributes[i] = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, v);
            }
            ARRAY table = new ARRAY(propertyDescriptor.getArrayDescriptor(),sqlConnection,attributes);
            //Datum[] attributes = new Datum[3];
            //attributes[0]=
            //STRUCT baseType = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, attributes);
            return table;
        }
    }

    class PropertyAtomDatetimeConverter extends PropertyHistoryConverter {
        PropertyAtomDatetimeConverter(){}

        protected void oracle2CacheHistory(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {
            ARRAY propertyValue = (ARRAY) oracleType;
            Datum[] history= propertyValue.getOracleArray();
            //long [] time = new long[history.length];
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                cacheOut.writeLong(((oracle.sql.DATE) historyAttributes[0]).dateValue().getTime());
            }
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                if (historyAttributes[1] != null) {
                    STRUCT dateTuple= (STRUCT) historyAttributes[1];
                    Datum[] atts = dateTuple.getOracleAttributes();
                    cacheOut.writeLong(((oracle.sql.DATE) atts[0]).dateValue().getTime());
                    cacheOut.writeBoolean(true);
                    cacheOut.writeUTF(((oracle.sql.CHAR) atts[1]).stringValue());
                } else {
                    cacheOut.writeBoolean(false);
                }
            }
        }

        protected Datum cache2DatumHistory(DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {

            TIMESTAMP []  time = new TIMESTAMP[length];
            oracle.sql.STRUCT[] vals = new oracle.sql.STRUCT[length];
            for(int i=0; i<length; i++) {
                time[i] = new TIMESTAMP(new java.sql.Date(cacheIn.readLong()));
            }
            for(int i=0; i<length; i++) {
                Datum[] atts = new Datum[2];
                atts[0] = new oracle.sql.DATE(new java.sql.Date(cacheIn.readLong()));
                if (cacheIn.readBoolean()) {
                    atts[1] = new oracle.sql.CHAR(cacheIn.readUTF(),null);
                }
                vals[i] = new oracle.sql.STRUCT(propertyDescriptor.getPrimitiveDescriptor(), sqlConnection,atts);
            }
            Datum[] attributes = new Datum[length];
            for (int i=0; i < length; i++) {
                Datum[] v = new Datum[2];
                v[0]= time[i];
                v[1]= vals[i];
                attributes[i] = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, v);
            }
            ARRAY table = new ARRAY(propertyDescriptor.getArrayDescriptor(),sqlConnection,attributes);
            //Datum[] attributes = new Datum[3];
            //attributes[0]=
            //STRUCT baseType = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, attributes);
            return table;
        }
    }

    class PropertyAtomConceptReferenceConverter extends PropertyHistoryConverter {
        PropertyAtomConceptReferenceConverter(){}

        protected void oracle2CacheHistory(Object oracleType, DataOutput cacheOut, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {
            ARRAY propertyValue = (ARRAY) oracleType;
            Datum[] history= propertyValue.getOracleArray();
            //long [] time = new long[history.length];
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                cacheOut.writeLong(((oracle.sql.DATE) historyAttributes[0]).dateValue().getTime());
            }
            for (int i=0; i < history.length; i++) {
                STRUCT historyTuple = (STRUCT) history[i];
                Datum[] historyAttributes= historyTuple.getOracleAttributes();
                if (historyAttributes[1] != null) {
                    cacheOut.writeBoolean(true);
                    cacheOut.writeLong(((oracle.sql.NUMBER) historyAttributes[1]).longValue());
                } else {
                    cacheOut.writeBoolean(false);
                }
            }
        }

        protected Datum cache2DatumHistory(DataInput cacheIn, PropertyDescriptor propertyDescriptor, Connection sqlConnection, int length) throws Exception {

            TIMESTAMP []  time = new TIMESTAMP[length];
            oracle.sql.NUMBER[] vals = new oracle.sql.NUMBER[length];
            for(int i=0; i<length; i++) {
                time[i] = new TIMESTAMP(new java.sql.Date(cacheIn.readLong()));
            }
            for(int i=0; i<length; i++) {
                if (cacheIn.readBoolean()) {
                    vals[i] = new oracle.sql.NUMBER(cacheIn.readLong());
                } else {
                    vals[i]=null;
                }
            }
            Datum[] attributes = new Datum[length];
            for (int i=0; i < length; i++) {
                Datum[] v = new Datum[2];
                v[0]= time[i];
                v[1]= vals[i];
                attributes[i] = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, v);
            }
            ARRAY table = new ARRAY(propertyDescriptor.getArrayDescriptor(),sqlConnection,attributes);
            //Datum[] attributes = new Datum[3];
            //attributes[0]=
            //STRUCT baseType = new STRUCT(propertyDescriptor.getHistoryDescriptor(), sqlConnection, attributes);
            return table;
        }
    }
}
