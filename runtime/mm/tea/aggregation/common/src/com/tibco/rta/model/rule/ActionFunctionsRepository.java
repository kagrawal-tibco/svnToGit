package com.tibco.rta.model.rule;

import com.tibco.rta.client.util.RtaClientUtils;
import com.tibco.rta.log.Level;
import com.tibco.rta.log.LogManagerFactory;
import com.tibco.rta.log.Logger;
import com.tibco.rta.log.impl.LoggerCategory;
import com.tibco.rta.model.serialize.impl.SerializationUtils;

import java.util.Collection;
import java.util.Map;

/**
 * A repository of action function descriptors that can be used by design time for use in
 * rule definitions.
 */

public class ActionFunctionsRepository {

    private static final Logger LOGGER = LogManagerFactory.getLogManager().getLogger(LoggerCategory.RTA_COMMON.getCategory());

    /**
     * The only instance
     */
    public static final ActionFunctionsRepository INSTANCE = new ActionFunctionsRepository();

    private Map<String, ActionFunctionDescriptor> actions;

    private ActionFunctionsRepository() {
        try {
            actions = SerializationUtils.deserializeActionFunctions();
        } catch (Exception e) {
            LOGGER.log(Level.ERROR, "", e);
        }
    }

    public void addAction(String name, ActionFunctionDescriptor actionFunctionDescriptor) {
        actions.put(name, actionFunctionDescriptor);
    }

    /**
     * Returns the function descriptor given a action name
     *
     * @param name Name for which the descriptor is desired.
     * @return the corresponding descriptor
     */
    public ActionFunctionDescriptor getFunctionDescriptor(String name) {
        if (actions == null) {
            throw new RuntimeException("Actions registry not built");
        }
        ActionFunctionDescriptor descriptor = actions.get(name);
        return (ActionFunctionDescriptor) RtaClientUtils.cloneOf (descriptor);
    }


    /**
     * Returns a list of action descriptors.
     *
     * @return A list of associated action descriptors.
     */
    public Collection<ActionFunctionDescriptor> getActionFunctionDescriptors() {
        if (actions == null) {
            throw new RuntimeException("Actions registry not built");
        }
        return actions.values();
    }

    /**
     * Returns a list of names of function descriptors.
     *
     * @return A list of names of associated function descriptors.
     */
    public Collection<String> getMetricFunctionDescriptorNames() {
        if (actions == null) {
            throw new RuntimeException("Actions registry not built");
        }
        return actions.keySet();
    }
}
