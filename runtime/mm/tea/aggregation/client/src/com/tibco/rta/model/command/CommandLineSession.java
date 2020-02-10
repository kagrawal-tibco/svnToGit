package com.tibco.rta.model.command;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.RtaConnection;
import com.tibco.rta.RtaException;
import com.tibco.rta.RtaSession;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.model.RtaSchema;
import com.tibco.rta.model.command.ast.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/12
 * Time: 2:47 PM
 * This class represents activities done in a command line session,
 * and using underlying {@link RtaSession} actually communicates with server.
 */
public class CommandLineSession {

	private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger("spm.client");

    /**
     * The actual session to be used.
     */
    private RtaSession session;


    private List<Command> commands = new ArrayList<Command>();

    /**
     * Whether session is open or not
     */
    private boolean open;

    /**
     * Opens this command line session when connection
     * to remote metric engine is successfully made.
     * @param connectionUrl The connection url for remote engine
     * @param configuration Configuration required to create connection and {@link RtaSession}
     */
    public void open(String connectionUrl, Map<ConfigProperty, PropertyAtom<?>> configuration) throws Exception {
        if (!isOpen()) {
            try {
                RtaConnection rtaConnection = null;
//                        new RtaConnectionFactory().getConnection(connectionUrl,
//                                configuration.get(ConfigProperty.CONNECTION_USERNAME),
//                                configuration.get(ConfigProperty.CONNECTION_PASSWORD),
//                                configuration);
                session = rtaConnection.createSession(configuration);
            } catch (RtaException e) {
            	LOGGER.log(Level.ERROR, String.format("Error in connecting to %s", connectionUrl), e);
                throw new Exception(e);
            }
            this.open = true;
        }
    }
    
    public boolean isOpen() {
        return open;
    }

    /**
     * Close this command line session.
     * @throws Exception
     */
    public void close() throws Exception {
        if (isOpen()) {
            this.open = false;
            session.close();
        }
    }

    /**
     *
     * @param commandOp
     */
    public void record(Command commandOp) {
        commands.add(commandOp);
    }

    /**
     * Commit local changes made in this session to the server.
     */
    public void commit() throws Exception {
        for (Command commandOp : commands) {
            commandOp.execute(this);
        }
    }

    public <T extends RtaSchema> T getSchema(String schemaName) {
        try {
            return session.getSchema(schemaName);
        } catch (Exception e) {
        	LOGGER.log(Level.ERROR, String.format("Error in fetching schema %s", schemaName), e);            
        }
        return null;
    }
}
