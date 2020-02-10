package com.tibco.rta.model.command;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.RtaSession;
import com.tibco.rta.impl.DefaultRtaSession;
import com.tibco.rta.model.Dimension;
import com.tibco.rta.model.DuplicateSchemaElementException;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.UndefinedSchemaElementException;
import com.tibco.rta.model.impl.RtaSchemaImpl;
import com.tibco.rta.model.mutable.MutableCube;
import com.tibco.rta.model.mutable.MutableDimensionHierarchy;
import com.tibco.rta.model.mutable.MutableMeasurement;
import com.tibco.rta.model.mutable.MutableRtaSchema;

import java.util.HashMap;
import java.util.Stack;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.ATTR_NAME_NAME;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 23/10/12
 * Time: 10:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class CommandProcessor {

    private RtaSession defaultSession = new DefaultRtaSession(null, new HashMap<ConfigProperty, PropertyAtom<?>>());

    /**
     * A volatile parent model object.
     */
    private Object parentObject;

    /**
     * Process command stack for every individual command.
     *
     * @param commandAction
     * @param commandQueue
     */
    public void processCommandStack(CommandActions commandAction, Stack<CommandObject> commandQueue) throws Exception {

        switch (commandAction) {
            case CREATE: {
                while (!commandQueue.isEmpty()) {
                    CommandObject commandObject = commandQueue.pop();
                    processCommandObject_create(commandObject);
                }
                parentObject = null;
                break;
            }
            case REMOVE: {
                while (!commandQueue.isEmpty()) {
                    CommandObject commandObject = commandQueue.pop();
                    processCommandObject_remove(commandObject, false);
                }
                parentObject = null;
                break;
            }
            case LINK: {
                while (!commandQueue.isEmpty()) {
                    CommandObject commandObject = commandQueue.pop();
                    processCommandObject_create(commandObject);
                }
                parentObject = null;
                break;
            }
        }
    }

    private void processCommandObject_remove(CommandObject commandObject, boolean remove) throws DuplicateSchemaElementException {
        CommandType commandType = commandObject.getCommandType();

        switch (commandType) {
            case SCHEMA: {
                //Check whether this schema exists in the session if not throw error
                String schemaName = commandObject.getAttributeValue(ATTR_NAME_NAME);
                RtaSchema schema = null;
                try {
                    schema = defaultSession.getSchema(schemaName);
                    if (schema == null) {
                        throw new RuntimeException(String.format("No schema exists in this session with name %s", schemaName));
                    }
                    parentObject = schema;
                    if (remove) {
                        //Only then remove
//                        defaultSession.deleteSchema(schema);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case CUBE: {
                if (parentObject instanceof MutableRtaSchema) {
                    MutableRtaSchema schema = (MutableRtaSchema) parentObject;
//                    schema.removeCube();
                    try {
						parentObject = schema.newCube(commandObject.getAttributeValue(ATTR_NAME_NAME));
					} catch (UndefinedSchemaElementException e) {
	                    throw new RuntimeException(e);
					}
                    break;
                }
            }
        }
    }

    private void processCommandObject_link(CommandObject commandObject) throws Exception {

    }


    private void processCommandObject_create(CommandObject commandObject) throws Exception {
        CommandType commandType = commandObject.getCommandType();

        switch (commandType) {
            case SCHEMA: {
            	String schemaName=commandObject.getAttributeValue(ATTR_NAME_NAME);            	
            	//Checking whether this schema exists in the session if not create otherwise throw exception
            	if(defaultSession.getSchema(schemaName)!=null)
                   throw new Exception("Schema with name="+schemaName+" allready exist");                
            	MutableRtaSchema schema = new RtaSchemaImpl();
                schema.setName(schemaName);
                parentObject = defaultSession.registerSchema(schema);
                break;
            }
            case CUBE: {
                if (parentObject instanceof MutableRtaSchema) {
                	MutableRtaSchema schema = (MutableRtaSchema) parentObject;
                	MutableCube cube = schema.getCube(commandObject.getAttributeValue(ATTR_NAME_NAME));
                    if (cube == null) {
                        cube = schema.newCube(commandObject.getAttributeValue(ATTR_NAME_NAME));
                    }
                    parentObject = cube;
                    break;
                }
            }
            case HIERARCHY: {
                if (parentObject instanceof MutableCube) {
                	MutableCube cube = (MutableCube) parentObject;
                    MutableDimensionHierarchy dimensionHierarchy = cube.getDimensionHierarchy(commandObject.getAttributeValue(ATTR_NAME_NAME));
                    if (dimensionHierarchy == null) {
                        dimensionHierarchy = cube.newDimensionHierarchy(commandObject.getAttributeValue(ATTR_NAME_NAME));
                    }
                    parentObject = dimensionHierarchy;
                    break;
                }
            }
            case MEASUREMENT: {
                if (parentObject instanceof MutableCube) {
//                    MutableCube cube = (MutableCube) parentObject;
//                    Measurement measurement = cube.getMeasurement();
//                    if (measurement == null) {
//                        measurement = cube.createMeasurement(commandObject.getAttributeValue(ATTR_NAME_NAME));
//                    }
//                    parentObject = measurement;
                    break;
                }
            }
            case DIMENSION: {
                Dimension dimension = null;

//                if (parentObject instanceof DimensionHierarchy) {
//                    DimensionHierarchy dimensionHierarchy = (DimensionHierarchy)parentObject;
//                    if (parent == null || parent.isEmpty()) {
//                        if (!dimension.equals(dimensionHierarchy.getRootDimension())) {
//                            dimensionHierarchy.setRootDimension(dimension);
//                            parentObject = dimension;
//                        } else {
//                            parentObject = dimensionHierarchy.getRootDimension();
//                        }
//                    } else {
//                        Dimension currentDimension = dimensionHierarchy.getRootDimension();
//                        if (currentDimension.getName().equals(parent)) {
//                            //Found match
//                            dimensionHierarchy.associateParentAndChild(currentDimension, dimension);
//                        } else {
//                            //Go down till match is found
//                            while (currentDimension != null) {
//                                currentDimension = currentDimension.
//                                if (currentDimension.getName().equals(parent)) {
//                                    //Found match
//                                    dimensionHierarchy.appendChildDimension(currentDimension, dimension);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                    break;
//                }
                if (parentObject instanceof MutableMeasurement) {
//                	MutableMeasurement measurement = (MutableMeasurement) parentObject;
//                    dimension = measurement.getDimension(commandObject.getAttributeValue(ATTR_NAME_NAME));
//                    if (dimension == null) {
//                        //Create
//                        dimension = measurement.newDimension(commandObject.getAttributeValue(ATTR_NAME_NAME), null);
//                    }
                }
            }
        }
    }
}
