package com.tibco.rta.runtime.model.serialize;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.tibco.rta.Fact;
import com.tibco.rta.Key;
import com.tibco.rta.common.registry.ModelRegistry;
import com.tibco.rta.impl.FactImpl;
import com.tibco.rta.impl.FactKeyImpl;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelDeserializer;
import com.tibco.rta.query.QueryDef;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 15/3/13
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuntimeModelJSONDeserializer implements ModelDeserializer {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

    private static final String FIELD_KEY = "key";

    private static final String FIELD_TYPE = "type";

    private static final String FIELD_ATTRS = "attributes";

    private static final String FIELD_IS_ASSET = "isAssetFact";

    private static final String FIELD_UID = "uid";

    private static final String FIELD_SCHEMA_NAME = "schemaName";

    private static final String FIELD_OWNER_SCHEMA_NAME = "ownerSchemaName";

    private static final String FIELD_NAME = "name";

    private static final String FIELD_INCARNATION_ID = "incarnationId";

    private FactImpl currentFact;

    private Key currentKey;

    private JsonFactory jsonFactory = new JsonFactory();

    private String currentUid;

    private String currentSchemaName;

    private String currentAssetName;

    private String currentIncarnationId;

    private boolean currentIsAssetFact;

    @Override
    public RtaSchema deserialize(File file) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RtaSchema deserializeSchema(InputSource InputSource) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Fact deserializeFact(InputSource InputSource) throws Exception {
        return null;
    }

    //TEMP arrangement
    public List<Fact> deserializeFacts(byte[] bytes) throws Exception {
        JsonParser parser = jsonFactory.createParser(bytes);
        Map<String, Object> currentAttributes = new LinkedHashMap<String, Object>();
        return parse(parser, currentAttributes);
    }

    private String getValueAsString(JsonParser parser) throws IOException {
        int length = parser.getTextLength();
        char[] chars = parser.getTextCharacters();
        //We want to intern to avoid recreating repetitive strings.
        //Also starting JDK 7, interned strings go to main heap.
        return new String(chars, 0, length).intern();
    }

    private List<Fact> parse(JsonParser parser, Map<String, Object> currentAttributes) throws Exception {
        List<Fact> facts = new ArrayList<Fact>();

        parser.nextToken();
        try {
            while (parser.hasCurrentToken()) {

                JsonToken currentToken = parser.getCurrentToken();

                if (currentToken == JsonToken.START_OBJECT) {
                    if (parser.getCurrentName() == null) {
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG,
                                    "Starting fact creation from json");
                        }
                    } else if (FIELD_KEY.equals(parser.getCurrentName())) {
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG, "Field name Key found");
                        }
                    } else if (FIELD_ATTRS.equals(parser.getCurrentName())) {
                        currentAttributes = new LinkedHashMap<String, Object>();
                    } else if (FIELD_IS_ASSET.equals(parser.getCurrentName())) {
                        currentIsAssetFact = parser.getValueAsBoolean();
                    }
                } else if (currentToken == JsonToken.VALUE_STRING) {
                    if (FIELD_UID.equals(parser.getCurrentName())) {
                        String uid = parser.getValueAsString();

                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(Level.DEBUG,
                                    "Field name [uid] found with value [%s]",
                                    uid);
                        }
                        currentUid = uid;
                    } else if (FIELD_SCHEMA_NAME.equals(parser.getCurrentName())) {
                        String schemaName = getValueAsString(parser);
                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(
                                    Level.DEBUG,
                                    "Field name [schemaName] found with value [%s]",
                                    schemaName);
                        }
                        currentSchemaName = schemaName;
                    } else if (FIELD_NAME.equals(parser.getCurrentName())) {
                        currentAssetName = getValueAsString(parser);

                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(
                                    Level.DEBUG,
                                    "Field name [assetName] found with value [%s]",
                                    currentAssetName);
                        }
                    } else if (FIELD_INCARNATION_ID.equals(parser.getCurrentName())) {
                        currentIncarnationId = getValueAsString(parser);

                        if (LOGGER.isEnabledFor(Level.DEBUG)) {
                            LOGGER.log(
                                    Level.DEBUG,
                                    "Field name [Incarnation ID] found with value [%s]",
                                    currentIncarnationId);
                        }
                    } else if (currentAttributes != null) {
                        String attributeName = parser.getCurrentName();
                        if ((!FIELD_TYPE.equals(attributeName))  && (!FIELD_OWNER_SCHEMA_NAME.equals(attributeName))) {
                            Object attributeValue = parser.getValueAsString();

                            if (LOGGER.isEnabledFor(Level.DEBUG)) {
                                LOGGER.log(
                                        Level.DEBUG,
                                        "Attribute name [%s] found with value [%s]",
                                        attributeName, attributeValue);
                            }
                            currentAttributes.put(attributeName, attributeValue);
                        }
                    }
                } else if (currentToken == JsonToken.VALUE_NUMBER_INT
                        && currentAttributes != null) {
                    String attributeName = parser.getCurrentName();
                    Object attributeValue = getAtributeValue(parser,
                            attributeName);
                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG,
                                "Attribute name [%s] found with value [%s]",
                                attributeName, attributeValue);
                    }
                    currentAttributes.put(attributeName, attributeValue);
                } else if (currentToken == JsonToken.VALUE_NUMBER_FLOAT
                        && currentAttributes != null) {
                    String attributeName = parser.getCurrentName();
                    Object attributeValue = parser.getValueAsDouble();

                    if (LOGGER.isEnabledFor(Level.DEBUG)) {
                        LOGGER.log(Level.DEBUG,
                                "Attribute name [%s] found with value [%s]",
                                attributeName, attributeValue);
                    }
                    currentAttributes.put(attributeName, attributeValue);
                } else if (currentToken == JsonToken.VALUE_TRUE) {
                    if (FIELD_IS_ASSET.equals(parser.getCurrentName())) {
                        currentIsAssetFact = true;
                    }
                } else if (currentToken == JsonToken.END_OBJECT) {
                    if (parser.getCurrentName() == null) {
                        // Convert to real fact
                        if (!currentIsAssetFact) {
                            currentKey = new FactKeyImpl();
                            ((FactKeyImpl) currentKey).setSchemaName(currentSchemaName);
                            ((FactKeyImpl) currentKey).setUid(currentUid);
                            RtaSchema ownerSchema = ModelRegistry.INSTANCE
                                    .getRegistryEntry(((FactKeyImpl) currentKey)
                                            .getSchemaName());
                            currentFact = new FactImpl();
                            currentFact.setOwnerSchema(ownerSchema);
                            currentFact.setKey(currentKey);
                            currentFact.setAttributes(currentAttributes);
                        }
                        facts.add(currentFact);
                        currentKey = null;
                        currentAttributes = null;
                        currentFact = null;
                        currentIsAssetFact = false;
                    }
                }
                parser.nextToken();
            }
        } catch (Exception e) {
            throw e;
        }
        return facts;
    }

    private Object getAtributeValue(JsonParser parser, String attributeName) throws IOException, JsonParseException {
        RtaSchema ownerSchema = ModelRegistry.INSTANCE.getRegistryEntry(currentSchemaName);
        if (ownerSchema == null) {
        	throw new RuntimeException("No Schema found with name [" + currentSchemaName + "]");
        }
        Object attributeValue = null;
        switch (ownerSchema.getAttribute(attributeName).getDataType()) {
            case SHORT:
                attributeValue = (short) parser.getValueAsInt();
                break;
            case BYTE:
                attributeValue = (byte) parser.getValueAsInt();
                break;
            case INTEGER:
                attributeValue = parser.getValueAsInt();
                break;
            case LONG:
                attributeValue = parser.getValueAsLong();
                break;
        }
        return attributeValue;
    }

    @Override
    public List<Fact> deserializeFacts(InputSource inputSource) throws Exception {
        JsonParser parser = jsonFactory.createJsonParser(inputSource.getByteStream());
        Map<String, Object> currentAttributes = new LinkedHashMap<String, Object>();
        return parse(parser, currentAttributes);
    }

    @Override
    public QueryDef deserializeQuery(InputSource InputSource) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public RuleDef deserializeRule(InputSource InputSource) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<RuleDef> deserializeRules(InputSource InputSource) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
}
