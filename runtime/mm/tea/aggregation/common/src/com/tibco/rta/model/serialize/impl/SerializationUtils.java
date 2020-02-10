package com.tibco.rta.model.serialize.impl;

import com.tibco.rta.model.MetricFunctionDescriptor;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.rule.ActionFunctionDescriptor;
import com.tibco.rta.model.rule.RuleDef;
import com.tibco.rta.model.serialize.ModelDeserializer;
import com.tibco.rta.model.serialize.ModelSerializer;
import com.tibco.rta.query.QueryDef;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SerializationUtils {

    private static boolean useJaxb;
    private static boolean useJaxbForQuery;

    static {
        useJaxbForQuery = System.getProperty("useJaxbForQuery", "true").equals("true");
        useJaxb = System.getProperty("useJaxb", "true").equals("true");
    }

    public static String serializeQuery(QueryDef queryDef) throws Exception {
        if (!useJaxbForQuery) {
            XMLSerializationTarget serializationTarget = new XMLSerializationTarget(true);
            ModelSerializer<Document> modelSerializer = new ModelXMLSerializer();
            modelSerializer.serialize(queryDef);

            StringWriter stringWriter = new StringWriter();
            serializationTarget.persist(stringWriter, modelSerializer.getTransformed());
            return stringWriter.toString();
        } else {
            return serializeQueryUsingJaxb(queryDef);
        }
    }

    private static String serializeQueryUsingJaxb(QueryDef queryDef) throws Exception {
        ModelJAXBSerializer modelSer = new ModelJAXBSerializer();
        StringWriter stringWriter = new StringWriter();

        modelSer.serialize(queryDef, stringWriter);
        return stringWriter.toString();
    }

    private static String serializeRuleUsingJaxb(RuleDef ruleDef) throws Exception {
        ModelJAXBSerializer modelSer = new ModelJAXBSerializer();
        StringWriter stringWriter = new StringWriter();

        modelSer.serialize(ruleDef, stringWriter);
        return stringWriter.toString();
    }
    
    private static void serializeRuleUsingJaxb(RuleDef ruleDef,File file) throws Exception {
        ModelJAXBSerializer modelSer = new ModelJAXBSerializer();
        modelSer.serialize(ruleDef, file);
    }

    public static String serializeRule(RuleDef ruleDef) throws Exception {
        if (!useJaxbForQuery) {
            XMLSerializationTarget serializationTarget = new XMLSerializationTarget(true);
            ModelSerializer<Document> modelSerializer = new ModelXMLSerializer();
            modelSerializer.serialize(ruleDef);

            StringWriter stringWriter = new StringWriter();
            serializationTarget.persist(stringWriter, modelSerializer.getTransformed());
            return stringWriter.toString();
        } else {
            return serializeRuleUsingJaxb(ruleDef);
        }
    }
    
    public static void serializeRule(RuleDef ruleDef,File file) throws Exception {
        if (!useJaxbForQuery) {
        	 XMLSerializationTarget serializationTarget = new XMLSerializationTarget(true);
             ModelSerializer<Document> modelSerializer = new ModelXMLSerializer();
             modelSerializer.serialize(ruleDef);
             serializationTarget.persist(file, modelSerializer.getTransformed());
        } else {
            serializeRuleUsingJaxb(ruleDef,file);   
        }
    }

    public static String serializeRuleDefs(List<RuleDef> ruleDefs) throws Exception {
        if (!useJaxbForQuery) {
            StringWriter stringWriter = new StringWriter();
            XMLSerializationTarget serializationTarget = new XMLSerializationTarget(true);
            ModelSerializer<Document> modelSerializer = new ModelXMLSerializer();
            modelSerializer.serializeRules(ruleDefs);
            serializationTarget.persist(stringWriter, modelSerializer.getTransformed());
            return stringWriter.toString();
        } else {
            return serializeRuleDefsUsingJaxb(ruleDefs);
        }
    }

    public static String serializeSchemas(List<RtaSchema> schemas) throws Exception {
        if (useJaxb) {
            StringWriter stringWriter = new StringWriter();
            ModelJAXBSerializer ser = new ModelJAXBSerializer();
            ser.serializeSchemas(schemas, stringWriter);
            return stringWriter.toString();
        }
        return null;
    }

    private static String serializeRuleDefsUsingJaxb(List<RuleDef> ruleDefs) throws JAXBException {
        StringWriter stringWriter = new StringWriter();
        ModelJAXBSerializer ser = new ModelJAXBSerializer();
        ser.serializeRules(ruleDefs, stringWriter);
        return stringWriter.toString();
    }

    public static void serializeSchema(RtaSchema schema, File file) throws Exception {
        if (!useJaxb) {
            XMLSerializationTarget serializationTarget = new XMLSerializationTarget(true);
            ModelSerializer<Document> modelSerializer = new ModelXMLSerializer();
            modelSerializer.serialize(schema);
            serializationTarget.persist(file, modelSerializer.getTransformed());
        } else {
            ModelJAXBSerializer ser = new ModelJAXBSerializer();
            ser.serialize(schema, file);
        }
    }


    public static String serializeSchema(RtaSchema schema) throws Exception {
        StringWriter stringWriter = new StringWriter();
        if (!useJaxb) {
            XMLSerializationTarget serializationTarget = new XMLSerializationTarget(true);
            ModelSerializer<Document> modelSerializer = new ModelXMLSerializer();
            modelSerializer.serialize(schema);
            serializationTarget.persist(stringWriter, modelSerializer.getTransformed());
        } else {
            ModelJAXBSerializer ser = new ModelJAXBSerializer();
            ser.serialize(schema, stringWriter);
        }
        return stringWriter.toString();
    }

    public static RtaSchema deserialize(File file) throws Exception {
        ModelDeserializer modelDeserializer;
        if (useJaxb) {
            modelDeserializer = new ModelJAXBDeserializer();
        } else {
            modelDeserializer = new ModelXMLDeserializer();
        }
        return modelDeserializer.deserialize(file);
    }

    public static RtaSchema deserializeSchema(InputSource inputSource) throws Exception {
        ModelDeserializer modelDeserializer;
        if (useJaxb) {
            modelDeserializer = new ModelJAXBDeserializer();
        } else {
            modelDeserializer = new ModelXMLDeserializer();
        }
        return modelDeserializer.deserializeSchema(inputSource);
    }

    public static Collection<RtaSchema> deserializeSchemas(InputSource inputSource) throws Exception {
        if (useJaxb) {
            ModelJAXBDeserializer modelDeserializer = new ModelJAXBDeserializer();
            return modelDeserializer.deserializeSchemas(inputSource);
        } else {
            throw new Exception("NA");
        }
    }


    public static QueryDef deserializeQuery(InputSource inputSource) throws Exception {
        ModelDeserializer modelDeserializer;
        if (useJaxbForQuery) {
            modelDeserializer = new ModelJAXBDeserializer();
        } else {
            modelDeserializer = new ModelXMLDeserializer();
        }
        return modelDeserializer.deserializeQuery(inputSource);
    }

    public static RuleDef deserializeRule(InputSource inputSource) throws Exception {
        ModelDeserializer modelDeserializer;
        if (useJaxbForQuery) {
            modelDeserializer = new ModelJAXBDeserializer();
        } else {
            modelDeserializer = new ModelXMLDeserializer();
        }
        return modelDeserializer.deserializeRule(inputSource);
    }


    public static Collection<RuleDef> deserializeRules(InputSource inputSource) throws Exception {
        ModelDeserializer modelDeserializer;
        if (useJaxbForQuery) {
            modelDeserializer = new ModelJAXBDeserializer();
        } else {
            modelDeserializer = new ModelXMLDeserializer();
        }
        return modelDeserializer.deserializeRules(inputSource);
    }

    public static Map<String, ActionFunctionDescriptor> deserializeActionFunctions() throws Exception {
        ActionsCatalogDeserializer actionsDeserializer = new ActionsCatalogDeserializer();
        return actionsDeserializer.deserialize();
    }

    public static Map<String, ActionFunctionDescriptor> deserializeActionFunctions(InputStream functionsStream) throws Exception {
        ActionsCatalogDeserializer actionsDeserializer = new ActionsCatalogDeserializer();
        Map<String, ActionFunctionDescriptor> actionFunctionDescriptors = new LinkedHashMap<String, ActionFunctionDescriptor>();
        actionsDeserializer.deserializeCatalogElements(functionsStream, actionFunctionDescriptors);
        return actionFunctionDescriptors;
    }

    public static String serializeAllFunctionDesc(List<MetricFunctionDescriptor> mfds) throws Exception {
        XMLSerializationTarget serializationTarget = new XMLSerializationTarget(true);
        ModelSerializer<Document> modelSerializer = new ModelXMLSerializer();
        modelSerializer.serializeAllFunctionDesc(mfds);
        StringWriter stringWriter = new StringWriter();
        serializationTarget.persist(stringWriter, modelSerializer.getTransformed());
        return stringWriter.toString();
    }


    public static String serializeAllActionDesc(Collection<ActionFunctionDescriptor> ads) throws Exception {
        XMLSerializationTarget serializationTarget = new XMLSerializationTarget(true);
        ModelSerializer<Document> modelSerializer = new ModelXMLSerializer();
        modelSerializer.serializeAllActionDesc(ads);
        StringWriter stringWriter = new StringWriter();
        serializationTarget.persist(stringWriter, modelSerializer.getTransformed());
        return stringWriter.toString();
    }

    public static String serialize(MetricFunctionDescriptor mfd) throws Exception {
        XMLSerializationTarget serializationTarget = new XMLSerializationTarget(true);
        ModelSerializer<Document> modelSerializer = new ModelXMLSerializer();
        modelSerializer.serialize(mfd);
        StringWriter stringWriter = new StringWriter();
        serializationTarget.persist(stringWriter, modelSerializer.getTransformed());
        return stringWriter.toString();
    }

}
