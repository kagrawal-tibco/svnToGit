package com.tibco.cep.driver.ancillary.tcp.catalog;

import static com.tibco.be.model.functions.FunctionDomain.*;
import com.tibco.cep.driver.ancillary.api.Reader;
import com.tibco.cep.driver.ancillary.api.Session;
import com.tibco.cep.driver.ancillary.api.SessionManager;
import com.tibco.cep.driver.ancillary.api.Writer;
import com.tibco.cep.driver.ancillary.tcp.RawByteArrayPayload;
import com.tibco.cep.driver.ancillary.tcp.client.TCPClient;
import com.tibco.cep.driver.ancillary.tcp.server.ServerSession;
import com.tibco.cep.driver.ancillary.tcp.server.TCPServer;
import com.tibco.cep.kernel.model.entity.Event;
import com.tibco.cep.kernel.model.rule.RuleFunction;
import com.tibco.cep.runtime.model.event.EventPayload;
import com.tibco.cep.runtime.model.event.SimpleEvent;
import com.tibco.cep.runtime.service.loader.BEClassLoader;
import com.tibco.cep.runtime.session.RuleServiceProvider;
import com.tibco.cep.runtime.session.RuleSession;
import com.tibco.cep.runtime.session.RuleSessionManager;
import com.tibco.cep.runtime.session.impl.RuleSessionImpl;
import com.tibco.cep.runtime.util.SimpleThreadFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.*;

@com.tibco.be.model.functions.BEPackage(
		catalog = "Communication",
        category = "TCP",
        synopsis = "TCP client/server communication functions. There are functions to run and manage local TCP servers and\nalso for remote TCP client connections.")
/*
* Author: Ashwin Jayaprakash Date: Mar 19, 2009 Time: 9:58:27 AM
*/
public class TCPHelper {
    protected static final HashMap<String, LocalServerKeeper> LOCAL_SERVERS =
            new HashMap<String, LocalServerKeeper>();

    protected static final ConcurrentHashMap<String, Session> ALL_SESSIONS =
            new ConcurrentHashMap<String, Session>();

    protected static final SecondarySessionListener SECONDARY_SESSION_LISTENER =
            new SecondarySessionListener();

    protected static final ExecutorService EXECUTOR_FOR_REMOTE_SESSIONS =
            new ThreadPoolExecutor(1, 512,
                    3 * 60, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<Runnable>(),
                    new SimpleThreadFactory(TCPHelper.class.getSimpleName() + ".RemoteSession"));

    protected static final String MSG_RULE_SESSION_IS_NULL =
            "This function must be invoked in the context of a RuleSession.";

    protected static final String MSG_WRONG_SESSION_LISTENER_SIGN =
            "The session listener must accept 2 parameters of type: " +
                    String.class.getName() + " - (serverNickName, sessionNickName)";

    protected static final String MSGPRE_NO_SUCH_SERVER =
            "There is no server registered under the name provided: ";


    protected static final String MSGPRE_BYTE_ARRAY_ARG_WRONG =
            "The byteArray argument has to be a byte[]" +
                    " with a minumum length of: ";

    //-------------

    @com.tibco.be.model.functions.BEFunction(
        name = "createLocalServer",
        synopsis = "Starts a local TCP server using the host name and port provided. The server will be registered under\nthe given nick name.",
        signature = "void createLocalServer(String serverNickName, String host, int port)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "serverNickName", type = "String", desc = "Nick name of the server being created"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "host", type = "String", desc = "Host name of the server"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "port", type = "int", desc = "Port on which to listen to")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void createLocalServer(String serverNickName, String host, int port) {
        synchronized (LOCAL_SERVERS) {
            if (LOCAL_SERVERS.containsKey(serverNickName)) {
                throw new RuntimeException(
                        "Another server already exists with the name provided: " + serverNickName);
            }

            RuleSessionImpl rs = (RuleSessionImpl) RuleSessionManager.getCurrentRuleSession();
            if (rs == null) {
                throw new RuntimeException(MSG_RULE_SESSION_IS_NULL);
            }
            RuleServiceProvider rsp = rs.getRuleServiceProvider();

            TCPServer server = new TCPServer();
            TCPServer.Parameters parameters = new TCPServer.Parameters(serverNickName, host, port);
            try {
                server.init(null, parameters, rsp.getLogger(server.getClass()));
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }

            LocalServerKeeper serverKeeper = new LocalServerKeeper();
            serverKeeper.init(server, rs);
            LOCAL_SERVERS.put(serverNickName, serverKeeper);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "registerSessionListener",
        synopsis = "This must be performed before calling startLocalServer(serverNickName).",
        signature = "void registerSessionListener(String serverNickName, String uriOfRuleFunction)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "serverNickName", type = "String", desc = "Nick name of the server"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uriOfRuleFunction", type = "String", desc = "name of the new session when a remote cluent connects to this server.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, BUI, QUERY, CONDITION},
        example = ""
    )
    public static void registerSessionListener(String serverNickName,
                                               String uriOfRuleFunction) {
        RuleSession rs = RuleSessionManager.getCurrentRuleSession();
        if (rs == null) {
            throw new RuntimeException(MSG_RULE_SESSION_IS_NULL);
        }
        RuleFunction rf = ((RuleSessionImpl) rs).getRuleFunction(uriOfRuleFunction);
        RuleFunction.ParameterDescriptor[] pds = rf.getParameterDescriptors();

        if (pds == null || pds.length != (2 + 1 /*1 for return type.*/)) {
            throw new IllegalArgumentException(MSG_WRONG_SESSION_LISTENER_SIGN);
        }

        for (int i = 0; i <= 1; i++) {
            Class paramType = pds[i].getType();
            if (String.class.isAssignableFrom(paramType) == false) {
                throw new IllegalArgumentException(MSG_WRONG_SESSION_LISTENER_SIGN);
            }
        }

        //-----------

        synchronized (LOCAL_SERVERS) {
            LocalServerKeeper serverKeeper = LOCAL_SERVERS.get(serverNickName);
            if (serverKeeper == null) {
                throw new IllegalArgumentException(MSGPRE_NO_SUCH_SERVER + serverNickName);
            }

            serverKeeper.start(rf, SECONDARY_SESSION_LISTENER);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "startLocalServer",
        synopsis = "Starts the local server that was registered previously using createLocalServer(serverNickName, host,\nport).",
        signature = "void startLocalServer(String serverNickName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "serverNickName", type = "String", desc = "Nick name of the server")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void startLocalServer(String serverNickName) {
        synchronized (LOCAL_SERVERS) {
            LocalServerKeeper serverKeeper = LOCAL_SERVERS.get(serverNickName);
            if (serverKeeper == null) {
                throw new IllegalArgumentException(MSGPRE_NO_SUCH_SERVER + serverNickName);
            }

            try {
                TCPServer tcpServer = serverKeeper.getTcpServer();
                tcpServer.start();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "disconnectLocalSession",
        synopsis = "Disconnects the session that was connected to the local server.",
        signature = "void disconnectLocalSession(String sessionNickName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sessionNickName", type = "String", desc = "Nick name of the local session being disconnected")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void disconnectLocalSession(String sessionNickName) {
        Session existingSession = ALL_SESSIONS.get(sessionNickName);

        if (existingSession == null) {
            throw new RuntimeException(
                    "There is no session registered under the name provided: " + sessionNickName);
        }

        if ((existingSession instanceof ServerSession) == false) {
            throw new RuntimeException(
                    "There is no valid local session registered under the name provided: " +
                            sessionNickName);
        }

        ServerSession serverSession = (ServerSession) existingSession;
        try {
            String serverId = serverSession.getServerId();
            LocalServerKeeper serverKeeper = LOCAL_SERVERS.get(serverId);

            TCPServer tcpServer = serverKeeper.getTcpServer();
            tcpServer.stopSession(serverSession);

            serverKeeper.removeSession(serverSession);

            ALL_SESSIONS.remove(sessionNickName);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "stopLocalServer",
        synopsis = "Stops the local server that was registered under the given nick name.",
        signature = "void stopLocalServer(String serverNickName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "serverNickName", type = "String", desc = "Nick name of the server being stopped")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void stopLocalServer(String serverNickName) {
        synchronized (LOCAL_SERVERS) {
            LocalServerKeeper serverKeeper = LOCAL_SERVERS.get(serverNickName);
            if (serverKeeper == null) {
                throw new IllegalArgumentException(MSGPRE_NO_SUCH_SERVER + serverNickName);
            }

            try {
                TCPServer tcpServer = serverKeeper.getTcpServer();
                tcpServer.stop();

                ConcurrentMap<String, Session> allSessions = serverKeeper.getAllSessions();
                for (String sessionName : allSessions.keySet()) {
                    ALL_SESSIONS.remove(sessionName);
                }

                serverKeeper.discard();
            }
            catch (Exception e) {
                throw new RuntimeException(e);
            }

            LOCAL_SERVERS.remove(serverNickName);
        }
    }

    //------------

    @com.tibco.be.model.functions.BEFunction(
        name = "connectToRemoteServer",
        synopsis = "Connects to a remote TCP server running on the given host name and port. This remote session will be\nregistered under the nick name provided.",
        signature = "void connectToRemoteServer(String sessionNickName, String host, int port)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sessionNickName", type = "String", desc = "Nick name of the session that will be used for this remote connection"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "host", type = "String", desc = "Remote host name"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "port", type = "int", desc = "Remote port number to connect to")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void connectToRemoteServer(String sessionNickName, String host, int port) {
        RuleSession rs = RuleSessionManager.getCurrentRuleSession();
        if (rs == null) {
            throw new RuntimeException(MSG_RULE_SESSION_IS_NULL);
        }
        RuleServiceProvider rsp = rs.getRuleServiceProvider();

        TCPClient tcpClient = new TCPClient();

        try {

            TCPClient.Parameters parameters = new TCPClient.Parameters(sessionNickName, host, port);
            tcpClient.init(parameters, rsp.getLogger(TCPClient.class), EXECUTOR_FOR_REMOTE_SESSIONS);

            Session existingSession = ALL_SESSIONS.putIfAbsent(sessionNickName, tcpClient);
            if (existingSession != null) {
                throw new RuntimeException(
                        "Another session already exists with the name provided: " +
                                sessionNickName);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            tcpClient.start();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "disconnectFromRemoteServer",
        synopsis = "Disconnects the remote session that was created previously using connectToRemoteServer(sessionNickName,\nhost, port).",
        signature = "void disconnectFromRemoteServer(String sessionNickName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sessionNickName", type = "String", desc = "Nick name of the remote connection")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void disconnectFromRemoteServer(String sessionNickName) {
        Session existingSession = ALL_SESSIONS.get(sessionNickName);

        if (existingSession == null) {
            throw new RuntimeException(
                    "There is no session registered under the name provided: " + sessionNickName);
        }

        if ((existingSession instanceof TCPClient) == false) {
            throw new RuntimeException(
                    "There is no valid remote session registered under the name provided: " +
                            sessionNickName);
        }

        TCPClient tcpClient = (TCPClient) existingSession;
        try {
            tcpClient.stop();
            ALL_SESSIONS.remove(sessionNickName);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //------------

    @com.tibco.be.model.functions.BEFunction(
        name = "write",
        synopsis = "Writes the payload of the event provided to the TCP session. '$1$10' at the end of the payload string can\nbe written to mark the EOF (Useful for Flash).",
        signature = "void write(String sessionNickName, Event eventWithPayload)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sessionNickName", type = "String", desc = "Nick name of the session to which data is being written"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "eventWithPayload", type = "SimpleEvent", desc = "session")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void write(String sessionNickName, Event eventWithPayload) {
        Session session = ALL_SESSIONS.get(sessionNickName);

        try {
            SimpleEvent se = (SimpleEvent) eventWithPayload;
            EventPayload payload = se.getPayload();
            byte[] bytes = convertPayloadToBytes(payload);

            Writer writer = session.getWriter();
            writer.write(bytes, 0, bytes.length);
            writer.flush();
        }
        catch (Exception e1) {
            throw new RuntimeException(e1);
        }
    }

    protected static byte[] convertPayloadToBytes(EventPayload payload) throws Exception {
        Object actualPayload = payload.getObject();

        if (actualPayload instanceof String) {
            return ((String) actualPayload).getBytes();
        }

        return payload.toBytes();
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "endWrite",
        synopsis = "Flushes the writes and signals the end of this write session.",
        signature = "void endWrite(String sessionNickName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sessionNickName", type = "String", desc = "Nick name of the session whose OutputStream will be closed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void endWrite(String sessionNickName) {
        Session session = ALL_SESSIONS.get(sessionNickName);

        Writer writer = session.getWriter();
        try {
            writer.stop();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "readIntoPayload",
        synopsis = "Attempts to read the maximum number of bytes specified into the payload of a new event whose URI is\nprovided.",
        signature = "Event readIntoPayload(String sessionNickName, int maxNumBytesToRead, String uriOfResponseEvent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sessionNickName", type = "String", desc = "Nick name of the session"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "maxNumBytesToRead", type = "int", desc = "reading stops and returns the data collected so far."),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uriOfResponseEvent", type = "String", desc = "that event before returning it.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = "The event with the payload containing the data. <code>null</code> if End-of-stream is reached."),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Event readIntoPayload(String sessionNickName, int maxNumBytesToRead,
                                        String uriOfResponseEvent) {
        RuleSession rs = RuleSessionManager.getCurrentRuleSession();
        if (rs == null) {
            throw new RuntimeException(MSG_RULE_SESSION_IS_NULL);
        }

        //------------

        Session session = ALL_SESSIONS.get(sessionNickName);

        Reader reader = session.getReader();
        try {
            boolean eof = false;
            byte[] bytes = new byte[maxNumBytesToRead];
            int offset = 0;
            int remainingToRead = maxNumBytesToRead;

            for (; remainingToRead > 0;) {
                int numRead = reader.read(bytes, offset, remainingToRead);

                //EOF.
                if (numRead == -1) {
                    eof = true;
                    break;
                }

                offset = offset + numRead;
                remainingToRead = remainingToRead - numRead;
            }

            RuleServiceProvider rsp = rs.getRuleServiceProvider();
            BEClassLoader beClassLoader = (BEClassLoader) rsp.getTypeManager();
            SimpleEvent event = (SimpleEvent) beClassLoader.createEntity(uriOfResponseEvent);

            //Received less than expected. Resize the byte array.
            if (remainingToRead > 0) {
                if (eof && remainingToRead == maxNumBytesToRead) {
                    return null;
                }

                byte[] correctSizeArray = new byte[maxNumBytesToRead - remainingToRead];
                System.arraycopy(bytes, 0, correctSizeArray, 0, correctSizeArray.length);
                bytes = correctSizeArray;
            }

            EventPayload payload = new RawByteArrayPayload(bytes);
            event.setPayload(payload);

            return event;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @com.tibco.be.model.functions.BEFunction(
        name = "readIntoPayloadFully",
        synopsis = "Attempts to read all the bytes from the stream into the payload of a new event whose URI is provided.\nIf the stream is not going to fit into memory or is going to be very large, avoid using this method.",
        signature = "Event readIntoPayloadFully(String sessionNickName, String uriOfResponseEvent)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sessionNickName", type = "String", desc = "Nick name of the session from which to read"),
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "uriOfResponseEvent", type = "String", desc = "that event before returning it.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "Event", desc = "The event with the payload containing the data."),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static Event readIntoPayloadFully(String sessionNickName, String uriOfResponseEvent) {
        RuleSession rs = RuleSessionManager.getCurrentRuleSession();
        if (rs == null) {
            throw new RuntimeException(MSG_RULE_SESSION_IS_NULL);
        }

        //------------

        Session session = ALL_SESSIONS.get(sessionNickName);

        Reader reader = session.getReader();

        try {
            LinkedList<byte[]> data = new LinkedList<byte[]>();
            //lets read 1K bytes @ a time
            byte[] batch = new byte[1024];
            int numRead = reader.read(batch, 0, 1024);
            while (numRead != -1) {
                if (numRead < 1024) {
                    //reduce the batch to its right size
                    byte[] shorterBatch = new byte[numRead];
                    System.arraycopy(batch, 0, shorterBatch, 0, numRead);
                    //add shorter batch and break
                    data.add(shorterBatch);
                    break;
                }
                //add the batch and read the next one
                data.add(batch);
                //create a new batch
                batch = new byte[1024];
                numRead = reader.read(batch, 0, 1024);
            }
            int total = 1024 * (data.size() - 1) + data.getLast().length;
            if (total == -1) {
                return null;
            }
            byte[] bytes = data.getLast();
            if (data.size() > 1) {
                bytes = new byte[total];
                int idx = 0;
                for (byte[] dataBatch : data) {
                    System.arraycopy(dataBatch, 0, bytes, idx, dataBatch.length);
                    idx = idx + dataBatch.length;
                }
            }

            RuleServiceProvider rsp = rs.getRuleServiceProvider();
            BEClassLoader beClassLoader = (BEClassLoader) rsp.getTypeManager();
            SimpleEvent event = (SimpleEvent) beClassLoader.createEntity(uriOfResponseEvent);

            EventPayload payload = new RawByteArrayPayload(bytes);
            event.setPayload(payload);

            return event;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @com.tibco.be.model.functions.BEFunction(
        name = "endRead",
        synopsis = "Stops reading and signals the end of this read session.",
        signature = "void endRead(String sessionNickName)",
        params = {
            @com.tibco.be.model.functions.FunctionParamDescriptor(name = "sessionNickName", type = "String", desc = "Nick name of the session whose InputStream will be closed.")
        },
        freturn = @com.tibco.be.model.functions.FunctionParamDescriptor(name = "", type = "void", desc = ""),
        version = "",
        see = "",
        mapper = @com.tibco.be.model.functions.BEMapper(),
        description = "",
        cautions = "",
        fndomain = {ACTION, CONDITION, QUERY, BUI},
        example = ""
    )
    public static void endRead(String sessionNickName) {
        Session session = ALL_SESSIONS.get(sessionNickName);

        Reader reader = session.getReader();
        try {
            reader.stop();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static class SecondarySessionListener implements SessionManager.SessionListener {
        public void onNewSession(Session session) {
            TCPHelper.ALL_SESSIONS.put(session.getId(), session);
        }

        @Override
        public void removeSession(Session session) {
        }
    }
}
