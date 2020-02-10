package com.tibco.be.oracle;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;

import oracle.sql.ArrayDescriptor;
import oracle.sql.Datum;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.STRUCT;

import com.tibco.cep.designtime.model.element.Concept;
import com.tibco.cep.designtime.model.event.Event;
import com.tibco.cep.runtime.model.serializers.SerializableLite;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Oct 11, 2006
 * Time: 11:04:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class OracleEntityAdapter implements SerializableLite, ORAData, ORADataFactory {

    OracleEntityDescription entityDescription;
    Map type2class;
    ByteArrayOutputStream cachedBytes = new ByteArrayOutputStream();
    /* Contains the byes converted from Oracle datum to cache */

    DataOutputStream cachedByteStream = new DataOutputStream(cachedBytes);
    /* Contains the bytes converted from cache to Oracle datum */

    Datum oracleBytes;
    Connection connection;

    protected OracleEntityAdapter (OracleEntityDescription entityDescription,
                                   Connection connection) {
        this.entityDescription=entityDescription;
        this.connection=connection;
    }

    protected OracleEntityAdapter (Map type2class, Connection connection) {
        this.type2class=type2class;
        this.connection=connection;
    }

    /**
     * Deserialize the byte stream from Tangosol
     * @param dataInput
     * @throws java.io.IOException
     */
    public void readExternal(DataInput dataInput) throws IOException {
        // Deserialize the header
        try {
            int i=0;
            Object [] attributes = new Object[entityDescription.getOracleType().getLength()];
            attributes[i++] = new oracle.sql.NUMBER(dataInput.readLong());
            if (dataInput.readBoolean()) {
                attributes[i++] = new oracle.sql.CHAR(dataInput.readUTF(),null);
            } else {
                attributes[i++]=null;
            }
            attributes[i++]= new oracle.sql.CHAR("C", null); // Place Holder for state information
            if (entityDescription.getEntity() instanceof Concept) {
                attributes[i++]= new oracle.sql.TIMESTAMP(new java.sql.Date(System.currentTimeMillis())); // Place Holder for state information
                attributes[i++]= new oracle.sql.TIMESTAMP(new java.sql.Date(System.currentTimeMillis())); // Place Holder for state information
            } else {
                attributes[i++]= new oracle.sql.TIMESTAMP(new java.sql.Date(System.currentTimeMillis())); // Place Holder for state information
                attributes[i++]= new oracle.sql.TIMESTAMP(new java.sql.Date(System.currentTimeMillis())); // Place Holder for state information
                attributes[i++]= new oracle.sql.TIMESTAMP(new java.sql.Date(System.currentTimeMillis())); // Place Holder for state information
            }
            Iterator it= entityDescription.getPropertyDescriptions().iterator();
            while (it.hasNext()) {

                PropertyDescriptor pd = (PropertyDescriptor) it.next();
                OracleBEMetadata.PropertyConverter pc= pd.getPropertyConverter();
                if (pd.getOracleType() instanceof ArrayDescriptor) {
                    attributes[i++]=pc.cache2OracleArray(dataInput,pd,connection);
                } else {
                    attributes[i++]=pc.cache2Oracle(dataInput,pd,connection);
                }
            }
            oracleBytes=new STRUCT(entityDescription.getOracleType(), connection, attributes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // deserializeInstanceFromCache(c,dataInput); // convert dataInput to Datum
        // Get the class
        // Get the id
        // Get the extId
    }

    public void writeExternal(DataOutput dataOutput) throws IOException {
        dataOutput.write(cachedBytes.toByteArray());
    }

    public Datum toDatum(Connection connection) throws SQLException {
        return oracleBytes;
    }

    public ORAData create(Datum datum, int i) throws SQLException {
        // Get the type
        STRUCT s = (STRUCT) datum;
        String typeName= s.getDescriptor().getName();
        this.entityDescription= (OracleEntityDescription) type2class.get(typeName);

        try {
            cachedByteStream.writeUTF(entityDescription.getEntity().getFullPath());
            Object[] attributes= s.getOracleAttributes();
            cachedByteStream.writeLong(((oracle.sql.NUMBER) attributes[0]).longValue());
            if (attributes[1] != null)  {
                cachedByteStream.writeBoolean(true);
                cachedByteStream.writeUTF(((oracle.sql.CHAR) attributes[1]).stringValue());
            } else {
                cachedByteStream.writeBoolean(false);
            }

            int idx;
            if (entityDescription.getEntity() instanceof Event) {
                idx=6;
            } else {
                idx=5;
            }
            Iterator it= entityDescription.getPropertyDescriptions().iterator();
            while (it.hasNext()) {
                PropertyDescriptor pd = (PropertyDescriptor) it.next();
                OracleBEMetadata.PropertyConverter pc= pd.getPropertyConverter();
                if (pd.getOracleType() instanceof ArrayDescriptor) {
                    pc.oracle2CacheArray(attributes[idx++], cachedByteStream, pd,connection);
                } else {
                  pc.oracle2Cache(attributes[idx++], cachedByteStream, pd,connection);
                }
            }
            // Payload
            if (entityDescription.getEntity() instanceof Event) {
                oracle.sql.BLOB payload= (oracle.sql.BLOB) attributes[idx++];
                if (payload != null) {
                    byte[] buf= payload.getBytes();
                    cachedByteStream.writeBoolean(true);
                    cachedByteStream.writeInt(buf.length);
                    cachedByteStream.write(buf);
                } else {
                    cachedByteStream.writeBoolean(false);
                }
            }
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return this;
        }
    }
}
