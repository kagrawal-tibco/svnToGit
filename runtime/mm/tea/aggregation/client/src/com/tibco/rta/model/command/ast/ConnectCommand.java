package com.tibco.rta.model.command.ast;

import com.tibco.rta.ConfigProperty;
import com.tibco.rta.property.PropertyAtom;
import com.tibco.rta.model.command.CommandLineSession;
import com.tibco.rta.model.command.CommandObject;
import com.tibco.rta.model.command.CommandType;
import com.tibco.rta.property.impl.PropertyAtomString;

import java.util.HashMap;
import java.util.Map;

import static com.tibco.rta.model.serialize.impl.ModelSerializationConstants.*;

/**
 * Created by IntelliJ IDEA.
 * User: aathalye
 * Date: 7/12/12
 * Time: 12:47 PM
 * Command to perform connect operation
 */
public class ConnectCommand extends Command {

    public void execute(CommandLineSession session) throws Exception {
        CommandObject commandObject = commandObjects.get(0);
        CommandType commandType = commandObject.getCommandType();

        if (commandType == CommandType.URL) {
            //Get url
            String connectionUrl = commandObject.getAttributeValue(ELEM_URL);
            String username = commandObject.getAttributeValue(ATTR_USERNAME);
            String password = commandObject.getAttributeValue(ATTR_PASSWORD);

            Map<ConfigProperty, PropertyAtom<?>> configuration = new HashMap<ConfigProperty, PropertyAtom<?>>();
            configuration.put(ConfigProperty.CONNECTION_USERNAME, new PropertyAtomString(username));
            configuration.put(ConfigProperty.CONNECTION_PASSWORD, new PropertyAtomString(password));

            session.open(connectionUrl, configuration);
        }
    }
}
