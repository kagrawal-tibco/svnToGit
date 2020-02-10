package com.tibco.rta.model.serialize.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tibco.rta.Fact;
import com.tibco.rta.RtaCommand;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.runtime.ServerConfigurationCollection;
import com.tibco.rta.query.QueryResultTupleCollection;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 15/3/13
 * Time: 1:58 PM
 * To change this template use File | Settings | File Templates.
 */
public class ModelJSONSerializer {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_CLIENT.getCategory());


    private ModelJSONSerializer() {
    }

    public static final ModelJSONSerializer INSTANCE = new ModelJSONSerializer();

    /**
     * Object mapper is safe for concurrent thread use.
     */
    private ObjectMapper objectMapper = new ObjectMapper();


    public byte[] serialize(Fact fact) throws Exception {
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Fact before serialize [%s]", fact);
        }

        byte[] bytes = objectMapper.writeValueAsBytes(fact);
        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Fact after serialize [%s] with objectmapper [%s]", new String(bytes), objectMapper);
        }
        return bytes;
    }


    public byte[] serialize(List<Fact> facts) throws Exception {
        byte[] bytes = objectMapper.writeValueAsBytes(facts);

        if (LOGGER.isEnabledFor(Level.DEBUG)) {
            LOGGER.log(Level.DEBUG, "Fact after serialize [%s]", new String(bytes));
        }
        return bytes;
    }

    public String serializeQueryResults(QueryResultTupleCollection resultTupleCollection) throws Exception {
        return objectMapper.writeValueAsString(resultTupleCollection);
    }

    public String serializeSchemaNames(Collection<String> schemaNames) throws Exception {
        return objectMapper.writeValueAsString(schemaNames);
    }

    public <R extends RtaCommand> byte[] serializeCommand(R command) throws Exception {
        return objectMapper.writeValueAsBytes(command);
    }

    public String serializeConfig(ServerConfigurationCollection serverConfigurationCollection) throws Exception {
        return objectMapper.writeValueAsString(serverConfigurationCollection);
    }
}
