package com.tibco.rta.model.command.ast;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_DATATYPE_NAME;
import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_NAME_NAME;

import com.tibco.rta.model.Cube;
import com.tibco.rta.model.DataType;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.UndefinedSchemaElementException;
import com.tibco.rta.model.command.CommandLineSession;
import com.tibco.rta.model.command.CommandObject;
import com.tibco.rta.model.command.CommandType;
import com.tibco.rta.model.impl.RtaSchemaImpl;
import com.tibco.rta.model.mutable.MutableCube;
import com.tibco.rta.model.mutable.MutableMeasurement;
import com.tibco.rta.model.mutable.MutableRtaSchema;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Time: 10:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreateCommand extends Command {

    public void execute(CommandLineSession session) throws Exception {

        for (CommandObject commandObject : commandObjects) {
            CommandType commandType = commandObject.getCommandType();

            switch (commandType) {
                case SCHEMA : {
                    createSchema(commandObject);
                }
                break;
                case CUBE : {
                    createCube(session, commandObject);
                }
                break;
                case MEASUREMENT : {
                    createMeasurement(session, commandObject);
                }
                break;
                case ATTRIBUTE : {
                    createAttribute(session, commandObject);
                }
                break;
            }
        }
    }

    private void createSchema(CommandObject commandObject) throws DuplicateSchemaElementException {
        MutableRtaSchema schema = new RtaSchemaImpl();
        schema.setName(commandObject.getAttributeValue(ATTR_NAME_NAME));
        affectedSchemaObject = schema;
    }

    /**
     *
     * @param session
     * @param commandObject
     * @throws DuplicateSchemaElementException
     * @throws UndefinedSchemaElementException 
     */
    private void createCube(CommandLineSession session, CommandObject commandObject) throws DuplicateSchemaElementException, UndefinedSchemaElementException {
        //Process stack
        CommandObject poppedObject = prepositionStack.peek();
        //This should be schema name
        if (poppedObject.getCommandType() == CommandType.SCHEMA) {
            String schemaName = poppedObject.getAttributeValue(ATTR_NAME_NAME);
            MutableRtaSchema schema = session.getSchema(schemaName);
//            schema.newCube(commandObject.getAttributeValue(ATTR_NAME_NAME));
            affectedSchemaObject = schema;
        }
    }

    /**
     *
     * @param session
     * @param commandObject
     * @throws DuplicateSchemaElementException
     */
    private void createMeasurement(CommandLineSession session, CommandObject commandObject) throws DuplicateSchemaElementException {
        RtaSchema schema = null;
        for (int loop = prepositionStack.size() - 1; loop >= 0; loop--) {
            CommandObject poppedObject = prepositionStack.get(loop);

            if (poppedObject.getCommandType() == CommandType.SCHEMA) {
                String schemaName = poppedObject.getAttributeValue(ATTR_NAME_NAME);
                schema = session.getSchema(schemaName);
            } else if (poppedObject.getCommandType() == CommandType.CUBE) {
                String cubeName = poppedObject.getAttributeValue(ATTR_NAME_NAME);
                if (schema != null) {
                    MutableCube cube = schema.getCube(cubeName);
//                    if (cube != null) {
//                        cube.createMeasurement(commandObject.getAttributeValue(ATTR_NAME_NAME));
//                    }
                }
            }
        }
        affectedSchemaObject = schema;
    }

    /**
     *
     * @param session
     * @param commandObject
     * @throws DuplicateSchemaElementException
     */
    private void createAttribute(CommandLineSession session, CommandObject commandObject) throws DuplicateSchemaElementException {
    	MutableRtaSchema schema = null;
        MutableCube cube = null;
        for (int loop = prepositionStack.size() - 1; loop >= 0; loop--) {
            CommandObject poppedObject = prepositionStack.get(loop);

            if (poppedObject.getCommandType() == CommandType.SCHEMA) {
                String schemaName = poppedObject.getAttributeValue(ATTR_NAME_NAME);
                schema = session.getSchema(schemaName);
            } else if (poppedObject.getCommandType() == CommandType.CUBE) {
                String cubeName = poppedObject.getAttributeValue(ATTR_NAME_NAME);
                if (schema != null) {
                    cube = schema.getCube(cubeName);
                }
            } else if (poppedObject.getCommandType() == CommandType.MEASUREMENT) {
                if (schema != null) {
                    if (cube != null) {
//                    	MutableMeasurement measurement = cube.getMeasurement();
//                        if (measurement != null) {
//                            measurement.newAttribute(commandObject.getAttributeValue(ATTR_NAME_NAME),
//                                    DataType.getByLiteral(commandObject.getAttributeValue(ATTR_DATATYPE_NAME)));
//                        }
                    }
                }
            }
        }
        affectedSchemaObject = schema;
    }
}
