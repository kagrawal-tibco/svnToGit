package com.tibco.cep.runtime.model.serializers;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;

import com.tibco.cep.runtime.model.element.StateMachineDeserializer;
import com.tibco.cep.runtime.model.element.impl.ConceptOrReference;
import com.tibco.cep.runtime.model.element.impl.Reference;

/**
 * Created by IntelliJ IDEA.
 * User: apuneet
 * Date: Feb 8, 2009
 * Time: 11:47:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class DataInputStateMachineDeserializer implements StateMachineDeserializer {
    boolean error=false;
    String msg;

    public DataInput buf;
    long id;
    int version;
    String extId;
    boolean isDeleted=false;
    int typeId;
    long parentId;

    /**
     * {@value}.
     */
    public static final int BYTE_POSITION_TYPE_ID_INT = 0;

    /**
     * {@value}.
     */
    public static final int BYTE_POSITION_VERSION_INT = 4;

    /**
     * {@value}.
     */
    public static final int BYTE_POSITION_DELETED_BOOLEAN = 8;

    /**
     * <b>Note:</b> Make sure the first 3 item positions match the positions in these properties -
     * {@link #BYTE_POSITION_TYPE_ID_INT}, {@link #BYTE_POSITION_TYPE_ID_INT} and
     * {@link #BYTE_POSITION_DELETED_BOOLEAN}.
     * @param buf
     */
    public DataInputStateMachineDeserializer(DataInput buf) throws IOException {
        this.buf=buf;
        typeId=buf.readInt();
        version=buf.readInt();
        isDeleted=buf.readBoolean();
        id = buf.readLong();
        if (buf.readBoolean()) {
            extId= buf.readUTF();
        }
        parentId=buf.readLong();
    }


    public boolean isDeleted() {
        return isDeleted;
    }

    public int getTypeId() {
        return typeId;
    }

    public void endStateMachine() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public ConceptOrReference getParentConcept() {
        return new Reference(parentId);
    }

    public int nextStateIndex() {
        try {
            if (!error) {
                return buf.readInt();
            } else {
                throw new RuntimeException("StateMachineDeserializer: Invalid State " + msg);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }

    }

    public int nextStateValue() {
        try {
            if (!error) {
                return buf.readInt();
            } else {
                throw new RuntimeException("StateMachineDeserializer: Invalid State " + msg);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @return
     */
    public long startStateMachine() {
        try {
            if (!error) {
                return id;
            } else {
                throw new RuntimeException("StateMachineDeserializer: Invalid State " + msg);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @return
     */
    public long getId() {
        try {
            if (!error) {
                return id;
            } else {
                throw new RuntimeException("StateMachineDeserializer: Invalid State " + msg);
            }
        } catch (Exception ex) {
            error=true;
            msg= ex.getMessage();
            throw new RuntimeException(msg);
        }
    }

    /**
     *
     * @return
     */
    public String getExtId() {
        return extId;
    }

    public int getVersion() {
        return version;
    }

    /**
     * @see #BYTE_POSITION_TYPE_ID_INT
     * @param buf
     * @return
     * @throws IOException
     */
    public final static int getTypeId (DataInputStream buf) throws IOException{
        buf.reset();
        return buf.readInt();
    }

    /**
     * @see #BYTE_POSITION_VERSION_INT
     * @param buf
     * @return
     * @throws IOException
     */
    public final static int getVersion (DataInputStream buf) throws IOException{
        buf.reset();
        buf.readInt();
        return buf.readInt();
    }

    /**
     * @see #BYTE_POSITION_DELETED_BOOLEAN
     * @param buf
     * @return
     * @throws IOException
     */
    public final static boolean isDeleted (DataInputStream buf) throws IOException{
        buf.reset();
        buf.readInt();
        buf.readInt();
        return buf.readBoolean();
    }

}
