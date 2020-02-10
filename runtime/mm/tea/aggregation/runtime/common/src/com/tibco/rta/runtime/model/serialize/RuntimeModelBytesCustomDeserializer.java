package com.tibco.rta.runtime.model.serialize;

import com.tibco.rta.Fact;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.RtaSchema;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 25/6/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuntimeModelBytesCustomDeserializer {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

    private FactImpl currentFact;

    public List<Fact> deserializeFacts(byte[] bytes) throws Exception {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        //Start tokenizing
        try {
            return tokenize(dataInputStream);
        } catch (Throwable throwable) {
            throw new Exception(throwable);
        } finally {
            dataInputStream.close();
            byteArrayInputStream.close();
        }
    }

    private List<Fact> tokenize(DataInputStream dataInputStream) throws Throwable {
        List<Fact> facts = new ArrayList<Fact>();

        while (true) {
            try {
                //Read char
                byte readChar = dataInputStream.readByte();

                if (readChar == '|') {
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG, "Fact delimiter found.");
                    }
                    currentFact = new FactImpl();
                    facts.add(currentFact);
                }
                //Read key info
                RtaSchema ownerSchema = readFactKey(dataInputStream);
                //Now read attributes
                readAttributes(dataInputStream, ownerSchema);
            } catch (EOFException eofException) {
                //End of stream reached
                break;
            }
        }
        return facts;
    }

    /**
     * Read fact key.
     * @param dataInputStream
     * @return
     * @throws IOException
     */
    private RtaSchema readFactKey(DataInputStream dataInputStream) throws IOException {
        //Read schema value
        String schemaName = dataInputStream.readUTF();

        RtaSchema ownerSchema = ModelRegistry.INSTANCE.getRegistryEntry(schemaName.trim());
        currentFact.setOwnerSchema(ownerSchema);
        FactKeyImpl currentFactKey = new FactKeyImpl();
        currentFact.setKey(currentFactKey);
        currentFactKey.setSchemaName(schemaName);

        //Read ID
        String idValue = dataInputStream.readUTF();
        currentFactKey.setUid(idValue);

        return ownerSchema;
    }

    /**
     * Read fact attrs.
     * @param dataInputStream
     * @param ownerSchema
     * @throws Exception
     */
    private void readAttributes(DataInputStream dataInputStream, RtaSchema ownerSchema) throws Exception {
        //Read number of attrs to follow
        byte numberOfAttrs = dataInputStream.readByte();
        for (int loop = 0; loop < numberOfAttrs; loop++) {
            //Read attr name followed by value
            String attributeName = dataInputStream.readUTF();
            DataType attrDataType = getAttributeDataType(ownerSchema, attributeName);
            Object attributeValue = null;

            switch (attrDataType) {
                case SHORT : {
                    attributeValue = dataInputStream.readShort();
                    break;
                }
                case BYTE : {
                    attributeValue = dataInputStream.readByte();
                    break;
                }
                case INTEGER : {
                    //The value is unicode byte sequence.
                    attributeValue = dataInputStream.readInt();
                    break;
                }
                case LONG : {
                    attributeValue = dataInputStream.readLong();
                    break;
                }
                case STRING : {
                    attributeValue = dataInputStream.readUTF();
                    break;
                }
                case DOUBLE : {
                    attributeValue = dataInputStream.readDouble();
                    break;
                }
                case BOOLEAN: {
                    attributeValue = dataInputStream.readBoolean();
                    break;
                }
            }
            currentFact.setAttribute(attributeName, attributeValue);
        }
    }


    private DataType getAttributeDataType(RtaSchema ownerSchema, String attributeName) {
        return ownerSchema.getAttribute(attributeName).getDataType();
    }
}
