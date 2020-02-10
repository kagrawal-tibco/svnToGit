package com.tibco.rta.model.command.ast;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.RtaConnectionEx;
import com.tibco.rta.RtaConnectionFactory;
import com.tibco.rta.model.command.CommandLineSession;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 27/11/12
 * Time: 2:50 PM
 * This class will be called from the CLI admin
 */
public class CommandLineIterpreter {

    public static void main(String[] r) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        for (String r0 : r) {
            stringBuilder.append(r0);
            stringBuilder.append(" ");
        }
        //TODO take these params from command line as well.
        String connectionUrl = "http://localhost:4448";
        RtaConnectionFactory connectionFac = new RtaConnectionFactory();
        RtaConnectionEx connection =
                (RtaConnectionEx)connectionFac.getConnection(connectionUrl, null, "", null);

        Map<ConfigProperty, String> configuration = new HashMap<ConfigProperty, String>();
        configuration.put(ConfigProperty.FACT_BATCH_SIZE, "5");
        configuration.put(ConfigProperty.FACT_BATCH_EXPIRY, "3");

        CommandASTNode astNode = CommandTreeParser.parse(stringBuilder.toString());
        CommandLineSession commandLineSession = new CommandLineSession();
        DefaultCommandASTNodeVisitor commandASTNodeVisitor = new DefaultCommandASTNodeVisitor(commandLineSession, configuration);
        astNode.accept(commandASTNodeVisitor);
        commandASTNodeVisitor.performOp();
    }
}
